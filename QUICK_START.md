# 🚀 Guía Rápida - Quick Start

## 📦 Opción 1: Docker Compose (RECOMENDADO - 2 minutos)

```bash
# 1. Clonar y entrar
git clone <repo-url>
cd app-microservices

# 2. Iniciar todo con Docker
docker-compose up -d

# 3. Esperar ~30 segundos

# 4. Acceder
Frontend:     http://localhost:4200
Gateway API:  http://localhost:8080
Health Check: http://localhost:8080/api/auth/health

# 5. Probar
curl -X GET http://localhost:8080/api/auth/health
```

## 🖥️ Opción 2: Local con Java & Node (5 minutos)

```bash
# 1. Requisitos
java -version        # Debe ser Java 21+
mvn -version         # Maven 3.9+
node --version       # Node 20+
npm --version

# 2. PostgreSQL (asegurarse que corre)
pg_isready -h localhost

# 3. Crear DBs
createdb gateway_db
createdb service_a_db
createdb service_b_db

# 4. Compilar backend
mvn clean package -DskipTests

# 5. Iniciar servicios en 3 terminales diferentes
# Terminal 1: Gateway
java -Dserver.port=8080 -jar gateway-service/target/gateway-service-1.0.0.jar

# Terminal 2: Request Service
java -Dserver.port=8081 -jar request-service/target/request-service-1.0.0.jar

# Terminal 3: Chat Service
java -Dserver.port=8082 -jar chat-service/target/chat-service-1.0.0.jar

# 6. Frontend (Terminal 4)
cd frontend
npm install
npm start

# Acceder: http://localhost:4200
```

## 🧪 Testing API con Postman

1. **Importar colección**
   - Abrir Postman
   - Import → postman-collection.json

2. **Registrarse**
   ```
   POST /api/auth/register
   {
     "email": "test@example.com",
     "fullName": "Test User",
     "password": "password123"
   }
   ```

3. **Login**
   ```
   POST /api/auth/login
   {
     "email": "test@example.com",
     "password": "password123"
   }
   ```

4. **Copiar token** de respuesta y usarlo en header:
   ```
   Authorization: Bearer <TOKEN>
   ```

## 🔗 URLs Locales

| Servicio | URL | Puerto |
|----------|-----|--------|
| Frontend | http://localhost:4200 | 4200 |
| Gateway | http://localhost:8080 | 8080 |
| Request Service | http://localhost:8081 | 8081 |
| Chat Service | http://localhost:8082 | 8082 |
| PostgreSQL Gateway | localhost:5432 | 5432 |
| PostgreSQL Request | localhost:5433 | 5433 |
| PostgreSQL Chat | localhost:5434 | 5434 |

## 🐳 Docker Compose Comandos Útiles

```bash
# Ver logs
docker-compose logs -f gateway-service
docker-compose logs -f request-service
docker-compose logs -f chat-service
docker-compose logs -f frontend

# Parar servicios
docker-compose down

# Limpiar todo
docker-compose down -v

# Reiniciar
docker-compose restart

# Construir imágenes sin caché
docker-compose build --no-cache

# Ver estado
docker-compose ps
```

## 🔧 Variables de Entorno Locales

```bash
# .env file (opcional)
PORT=8080
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/gateway_db
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres
JWT_SECRET=mySecretKeyForDevelopmentPurposesMustBeAtLeast32CharsLongForHS256
REQUEST_SERVICE_URL=http://localhost:8081
CHAT_SERVICE_URL=http://localhost:8082
CORS_ALLOWED_ORIGIN=http://localhost:4200
```

## 📊 Health Checks

```bash
# Gateway
curl http://localhost:8080/api/auth/health

# Request Service
curl http://localhost:8081/api/request-service/health

# Chat Service
curl http://localhost:8082/api/chat-service/health

# Frontend
curl http://localhost:4200/health
```

## 🔐 Credenciales de Prueba

```
Email:    test@example.com
Password: password123
```

O registrar nuevas desde el frontend.

## 🚀 Google Cloud Deployment (Rápido)

```bash
# 1. Ejecutar setup
bash SETUP_GCP.md

# 2. Configurar GitHub Secrets

# 3. Push a main
git push origin main

# GitHub Actions se encarga del resto (~10 minutos)
```

## 🛠️ Troubleshooting Rápido

| Problema | Solución |
|----------|----------|
| "Connection refused" | Asegurar que Docker está corriendo |
| "Database already exists" | `docker-compose down -v` |
| "Port already in use" | Cambiar puerto en docker-compose.yml |
| "Cannot GET /api/auth/health" | Esperar más tiempo (30s) para que se inicie |
| "CORS error" | Verificar CORS_ALLOWED_ORIGIN en variables env |
| "401 Unauthorized" | Token expirado o inválido, hacer login de nuevo |

## 📚 Estructura de Carpetas (Rápida)

```
app-microservices/
├── gateway-service/       → Autenticación y enrutamiento
├── request-service/       → Gestión de solicitudes
├── chat-service/          → Chat con IA
├── frontend/              → Angular UI
├── docker-compose.yml     → Orquestación
├── README.md              → Documentación completa
├── SETUP_GCP.md           → Instrucciones GCP
├── postman-collection.json→ Tests API
└── pom.xml                → Maven parent
```

## 🎯 Próximos Pasos

1. ✅ Clonar repo
2. ✅ `docker-compose up -d`
3. ✅ Ir a http://localhost:4200
4. ✅ Registrarse
5. ✅ Crear solicitudes y chats
6. ✅ Probar endpoints en Postman
7. ✅ Configurar GCP cuando esté listo
8. ✅ Push a GitHub
9. ✅ Automático deployment a Cloud Run ✨

---

**¿Necesitas más ayuda?** Ver `README.md` para documentación completa.
