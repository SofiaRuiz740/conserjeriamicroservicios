# Guía de Despliegue en Google Cloud Run

Esta guía te ayuda a desplegar la aplicación en Google Cloud Run.

## 📋 Prerequisitos

- Cuenta de Google Cloud
- Google Cloud SDK instalado
- Docker instalado
- GitHub token (para crear secrets)

## 🔧 Paso 1: Configurar Proyecto en Google Cloud

### 1.1 Crear proyecto
```bash
gcloud projects create concierge-app --name="Concierge App"
gcloud config set project concierge-app
```

### 1.2 Activar APIs necesarias
```bash
gcloud services enable \
  run.googleapis.com \
  artifactregistry.googleapis.com \
  sqladmin.googleapis.com \
  iamcredentials.googleapis.com \
  cloudbuild.googleapis.com \
  iam.googleapis.com
```

## 🗄️ Paso 2: Crear Cloud SQL

### 2.1 Crear instancia PostgreSQL
```bash
gcloud sql instances create concierge-db \
  --database-version=POSTGRES_15 \
  --tier=db-f1-micro \
  --region=us-central1 \
  --storage-type=PD_SSD \
  --storage-size=10GB
```

### 2.2 Crear bases de datos
```bash
gcloud sql databases create gateway_db --instance=concierge-db
gcloud sql databases create service_a_db --instance=concierge-db
gcloud sql databases create service_b_db --instance=concierge-db
```

### 2.3 Crear usuario de base de datos
```bash
gcloud sql users create postgres \
  --instance=concierge-db \
  --password=[TU_CONTRASEÑA]
```

## 📦 Paso 3: Crear Artifact Registry

```bash
gcloud artifacts repositories create concierge-repo \
  --repository-format=docker \
  --location=us-central1
```

## 🔐 Paso 4: Configurar GitHub Actions

### 4.1 Crear service account
```bash
gcloud iam service-accounts create github-actions \
  --display-name="GitHub Actions"
```

### 4.2 Dar permisos
```bash
gcloud projects add-iam-policy-binding concierge-app \
  --member=serviceAccount:github-actions@concierge-app.iam.gserviceaccount.com \
  --role=roles/run.admin

gcloud projects add-iam-policy-binding concierge-app \
  --member=serviceAccount:github-actions@concierge-app.iam.gserviceaccount.com \
  --role=roles/artifactregistry.writer

gcloud projects add-iam-policy-binding concierge-app \
  --member=serviceAccount:github-actions@concierge-app.iam.gserviceaccount.com \
  --role=roles/iam.serviceAccountUser

gcloud projects add-iam-policy-binding concierge-app \
  --member=serviceAccount:github-actions@concierge-app.iam.gserviceaccount.com \
  --role=roles/cloudsql.client
```

### 4.3 Configurar Workload Identity Federation
```bash
gcloud iam workload-identity-pools create "github-pool" \
  --project="concierge-app" \
  --location="global" \
  --display-name="GitHub Actions"

gcloud iam workload-identity-providers create-oidc "github-provider" \
  --project="concierge-app" \
  --location="global" \
  --display-name="GitHub Provider" \
  --attribute-mapping="google.subject=assertion.sub,attribute.actor=assertion.actor,attribute.repository=assertion.repository" \
  --issuer-uri="https://token.actions.githubusercontent.com"

gcloud iam service-accounts add-iam-policy-binding \
  github-actions@concierge-app.iam.gserviceaccount.com \
  --role roles/iam.workloadIdentityUser \
  --member "principalSet://iam.googleapis.com/projects/PROJECT_NUMBER/locations/global/workloadIdentityPools/github-pool/attribute.repository/TU_USUARIO/concierge-app"
```

## 🚀 Paso 5: Desplegar Servicios Manualmente

### 5.1 Build local
```bash
mvn clean package -DskipTests -q
```

### 5.2 Push a Artifact Registry
```bash
docker tag gateway-service:1.0.0 us-central1-docker.pkg.dev/concierge-app/concierge-repo/gateway-service
docker push us-central1-docker.pkg.dev/concierge-app/concierge-repo/gateway-service
```

### 5.3 Desplegar a Cloud Run
```bash
gcloud run deploy gateway-service \
  --image us-central1-docker.pkg.dev/concierge-app/concierge-repo/gateway-service \
  --platform managed \
  --region us-central1 \
  --allow-unauthenticated \
  --add-cloudsql-instances concierge-app:us-central1:concierge-db \
  --set-env-vars SPRING_DATASOURCE_URL=jdbc:postgresql://cloudsql/gateway_db?cloudSqlInstance=concierge-app:us-central1:concierge-db&socketFactory=com.google.cloud.sql.postgres.SocketFactory
```

## 📝 Variables de Entorno para Cloud Run

```
SPRING_DATASOURCE_URL=jdbc:postgresql://cloudsql/gateway_db?cloudSqlInstance=concierge-app:us-central1:concierge-db&socketFactory=com.google.cloud.sql.postgres.SocketFactory
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=[TU_CONTRASEÑA]
JWT_SECRET=[CLAVE_SECRETA_MINIMO_32_CARACTERES]
REQUEST_SERVICE_URL=https://request-service-HASH.run.app
CHAT_SERVICE_URL=https://chat-service-HASH.run.app
CORS_ALLOWED_ORIGIN=https://frontend-HASH.run.app
```

## 📊 Ver Logs

```bash
gcloud run services logs read gateway-service --region us-central1 --limit=100
```

## 🔍 Verificar Despliegue

```bash
gcloud run services list --region us-central1
gcloud sql instances describe concierge-db
```

## ❌ Solucionar Problemas

### Database Connection Error
- Verificar Workload Identity binding
- Verificar Cloud SQL socket factory dependency

### CORS Error
- Actualizar CORS_ALLOWED_ORIGIN
- Reindesplegar servicio

### Out of Memory
- Aumentar memoria en Cloud Run
- Optimizar aplicación

## 💰 Estimado de Costos

- Cloud SQL: ~$25/mes (db-f1-micro)
- Cloud Run: ~$1-10/mes (bajo tráfico)
- Artifact Registry: ~$0.10/GB
- Total estimado: $30-40/mes

## 🔐 Mejores Prácticas

1. Usar secretos en Secret Manager, no variables de entorno
2. Habilitar Cloud SQL Auth Proxy
3. Configurar HTTPS
4. Usar Cloud CDN para frontend
5. Monitorear con Cloud Monitoring
