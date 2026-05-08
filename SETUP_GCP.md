# 🚀 Setup Completo en Google Cloud Platform

## Paso 1: Crear Proyecto y Configuración Inicial

```bash
# 1.1 Crear proyecto
PROJECT_ID="concierge-app-$(date +%s)"
gcloud projects create $PROJECT_ID
gcloud config set project $PROJECT_ID

echo "Proyecto creado: $PROJECT_ID"

# 1.2 Activar APIs necesarias
gcloud services enable \
  run.googleapis.com \
  artifactregistry.googleapis.com \
  sqladmin.googleapis.com \
  iamcredentials.googleapis.com \
  cloudbuild.googleapis.com \
  compute.googleapis.com \
  cloudkms.googleapis.com
```

## Paso 2: Crear Artifact Registry

```bash
REGION="us-central1"

gcloud artifacts repositories create app-registry \
  --repository-format=docker \
  --location=$REGION \
  --description="Docker registry for microservices"

# Verificar
gcloud artifacts repositories list
```

## Paso 3: Crear Cloud SQL PostgreSQL

```bash
# 3.1 Crear instancia
INSTANCE_NAME="concierge-db"
ROOT_PASSWORD="$(openssl rand -base64 32)"

gcloud sql instances create $INSTANCE_NAME \
  --database-version=POSTGRES_15 \
  --tier=db-f1-micro \
  --region=$REGION \
  --root-password=$ROOT_PASSWORD \
  --availability-type=zonal \
  --enable-bin-log=false

echo "Instance Connection Name:"
gcloud sql instances describe $INSTANCE_NAME --format='value(connectionName)'

# 3.2 Crear bases de datos
gcloud sql databases create gateway_db --instance=$INSTANCE_NAME
gcloud sql databases create service_a_db --instance=$INSTANCE_NAME
gcloud sql databases create service_b_db --instance=$INSTANCE_NAME

# 3.3 Crear usuario de aplicación
APP_USER="app-user"
APP_PASSWORD="$(openssl rand -base64 16)"

gcloud sql users create $APP_USER \
  --instance=$INSTANCE_NAME \
  --password=$APP_PASSWORD

echo "Usuario creado: $APP_USER"
echo "Contraseña: $APP_PASSWORD"
```

## Paso 4: Configurar Service Account para GitHub Actions

```bash
# 4.1 Crear service account
SA_NAME="github-actions"
gcloud iam service-accounts create $SA_NAME \
  --display-name="GitHub Actions Deployment"

SA_EMAIL="${SA_NAME}@${PROJECT_ID}.iam.gserviceaccount.com"

# 4.2 Asignar roles
ROLES=(
  "roles/run.admin"
  "roles/artifactregistry.writer"
  "roles/iam.serviceAccountUser"
  "roles/cloudsql.client"
)

for role in "${ROLES[@]}"; do
  gcloud projects add-iam-policy-binding $PROJECT_ID \
    --member="serviceAccount:$SA_EMAIL" \
    --role="$role"
done

echo "Service Account: $SA_EMAIL"
```

## Paso 5: Configurar Workload Identity Federation

```bash
# 5.1 Crear Workload Identity Pool
POOL_NAME="github"
PROVIDER_NAME="github"

gcloud iam workload-identity-pools create $POOL_NAME \
  --project=$PROJECT_ID \
  --location=global \
  --display-name="GitHub"

POOL_ID=$(gcloud iam workload-identity-pools describe $POOL_NAME \
  --project=$PROJECT_ID \
  --location=global \
  --format='value(name)')

# 5.2 Crear Workload Identity Provider
gcloud iam workload-identity-pools providers create-oidc $PROVIDER_NAME \
  --project=$PROJECT_ID \
  --location=global \
  --workload-identity-pool=$POOL_NAME \
  --display-name="GitHub Provider" \
  --attribute-mapping="google.subject=assertion.sub,attribute.aud=assertion.aud,attribute.repository=assertion.repository" \
  --issuer-uri="https://token.actions.githubusercontent.com"

# 5.3 Obtener WORKLOAD_IDENTITY_PROVIDER
WORKLOAD_IDENTITY_PROVIDER=$(gcloud iam workload-identity-pools providers describe $PROVIDER_NAME \
  --project=$PROJECT_ID \
  --location=global \
  --workload-identity-pool=$POOL_NAME \
  --format='value(name)')

echo "WORKLOAD_IDENTITY_PROVIDER=$WORKLOAD_IDENTITY_PROVIDER"

# 5.4 Configurar Workload Identity Binding
PROJECT_NUMBER=$(gcloud projects describe $PROJECT_ID --format='value(projectNumber)')

gcloud iam service-accounts add-iam-policy-binding $SA_EMAIL \
  --project=$PROJECT_ID \
  --role="roles/iam.workloadIdentityUser" \
  --member="principalSet://iam.googleapis.com/projects/${PROJECT_NUMBER}/locations/global/workloadIdentityPools/${POOL_NAME}/attribute.repository/YOUR_GITHUB_ORG/YOUR_REPO_NAME"

# Reemplazar YOUR_GITHUB_ORG y YOUR_REPO_NAME con valores reales
```

## Paso 6: Generar Secretos y Variables

```bash
# 6.1 Generar JWT Secret
JWT_SECRET=$(openssl rand -base64 32)
echo "JWT_SECRET=$JWT_SECRET"

# 6.2 Obtener valores para secrets
INSTANCE_CONNECTION_NAME=$(gcloud sql instances describe $INSTANCE_NAME --format='value(connectionName)')

echo ""
echo "======================================="
echo "GITHUB SECRETS A CONFIGURAR"
echo "======================================="
echo "GCP_PROJECT_ID=$PROJECT_ID"
echo "GCP_REGION=$REGION"
echo "WORKLOAD_IDENTITY_PROVIDER=$WORKLOAD_IDENTITY_PROVIDER"
echo "SERVICE_ACCOUNT=$SA_EMAIL"
echo "INSTANCE_CONNECTION_NAME=$INSTANCE_CONNECTION_NAME"
echo "SPRING_DATASOURCE_USERNAME=$APP_USER"
echo "SPRING_DATASOURCE_PASSWORD=$APP_PASSWORD"
echo "JWT_SECRET=$JWT_SECRET"
```

## Paso 7: Configurar GitHub Secrets

```bash
# En GitHub: Settings → Secrets and variables → Actions

# Copiar y ejecutar en tu repositorio:
gh secret set GCP_PROJECT_ID --body "$PROJECT_ID"
gh secret set GCP_REGION --body "$REGION"
gh secret set WORKLOAD_IDENTITY_PROVIDER --body "$WORKLOAD_IDENTITY_PROVIDER"
gh secret set SERVICE_ACCOUNT --body "$SA_EMAIL"
gh secret set INSTANCE_CONNECTION_NAME --body "$INSTANCE_CONNECTION_NAME"
gh secret set SPRING_DATASOURCE_USERNAME --body "$APP_USER"
gh secret set SPRING_DATASOURCE_PASSWORD --body "$APP_PASSWORD"
gh secret set JWT_SECRET --body "$JWT_SECRET"
```

## Paso 8: Primer Despliegue

```bash
# 8.1 Hacer commit y push
git add .
git commit -m "Initial commit"
git push origin main

# 8.2 Monitorear workflows
# Ir a: GitHub → Actions

# 8.3 Esperar a que se completen (5-10 minutos por servicio)
```

## Paso 9: Obtener URLs de Cloud Run

```bash
# Una vez desplegados, obtener URLs
SERVICES=("gateway-service" "request-service" "chat-service" "frontend")

for service in "${SERVICES[@]}"; do
  URL=$(gcloud run services describe $service \
    --region=$REGION \
    --format='value(status.url)')
  echo "$service: $URL"
done

# Actualizar GitHub Secrets con URLs
# REQUEST_SERVICE_URL=...
# CHAT_SERVICE_URL=...
# GATEWAY_URL=...
```

## Paso 10: Verificar Despliegue

```bash
# 10.1 Verificar servicios en Cloud Run
gcloud run services list --region=$REGION

# 10.2 Ver logs
gcloud run services logs read gateway-service --region=$REGION --limit=50

# 10.3 Probar health check
curl https://gateway-service-XXX.run.app/api/auth/health
```

## Troubleshooting

### Error: "Permission denied"
```bash
# Verificar que la cuenta de usuario tiene rol Editor o Project Editor
gcloud projects get-iam-policy $PROJECT_ID
```

### Error: "API not enabled"
```bash
# Re-ejecutar Paso 1.2
gcloud services enable run.googleapis.com
```

### Error: "CloudSQL socket factory not found"
```bash
# Verificar que pom.xml tiene:
# <dependency>
#     <groupId>com.google.cloud.sql</groupId>
#     <artifactId>postgres-socket-factory</artifactId>
# </dependency>
```

### Error: "Workload Identity configuration not found"
```bash
# Reemplazar YOUR_GITHUB_ORG y YOUR_REPO_NAME en Paso 5.4
# Usar: asruiz_740 y el nombre del repo
```

## Cleanup (Si necesitas eliminar recursos)

```bash
# ⚠️ CUIDADO: Esto elimina todo

gcloud run services delete gateway-service --region=$REGION
gcloud run services delete request-service --region=$REGION
gcloud run services delete chat-service --region=$REGION
gcloud run services delete frontend --region=$REGION

gcloud artifacts repositories delete app-registry --location=$REGION

gcloud sql instances delete $INSTANCE_NAME

gcloud projects delete $PROJECT_ID
```

---

**Tiempo estimado**: 15-20 minutos
**Costo estimado**: ~$10-20/mes (db-f1-micro es muy barato)
