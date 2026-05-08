# Conserjería Virtual con IA Generativa

Aplicación de microservicios completa para gestión de solicitudes de conserjería con asistente virtual basado en IA generativa.

## 🏗️ Arquitectura

```
Frontend (Angular 17.3.0)
    ↓
API Gateway (Spring Boot 3.1.12)
    ├── Request Service (Spring Boot 3.1.12 + PostgreSQL)
    └── Chat Service (Spring Boot 3.1.12 + PostgreSQL)
```

## 🎯 Microservicios

### 1. Gateway Service (Puerto 8080)
- Autenticación con JWT
- Gestión de usuarios (registro, login)
- Enrutamiento a microservicios
- CORS configurado

**Endpoints:**
- `POST /api/auth/register` - Registrar usuario
- `POST /api/auth/login` - Iniciar sesión
- `GET /api/auth/me` - Obtener usuario autenticado
- `GET /api/auth/health` - Health check

### 2. Request Service (Puerto 8081)
- Gestión de solicitudes de conserjería
- Categorías de servicios
- Comentarios en solicitudes
- Revisiones y calificaciones

**Endpoints:**
- `GET /api/service-a/categories` - Listar categorías
- `POST /api/service-a/categories` - Crear categoría
- `GET /api/service-a/requests` - Listar solicitudes
- `POST /api/service-a/requests` - Crear solicitud

### 3. Chat Service (Puerto 8082)
- Conversaciones con IA
- Mensajes de usuario e IA
- Base de conocimiento
- Recomendaciones inteligentes

**Endpoints:**
- `GET /api/service-b/conversations` - Listar conversaciones
- `POST /api/service-b/conversations` - Crear conversación
- `POST /api/service-b/conversations/{id}/messages` - Enviar mensaje

## 📋 Prerequisitos

- Java 17 o superior
- Maven 3.8+
- Docker & Docker Compose
- Node.js 20+

## 🚀 Ejecución Local

Sigue los pasos en `RUN_ME_FIRST.md`

## 🐳 Docker Compose

El archivo `docker-compose.yml` incluye:
- 3 instancias de PostgreSQL
- 3 microservicios Spring Boot
- 1 frontend Angular con Nginx
- Red compartida para comunicación

## 🔐 Credenciales de Prueba

**Email:** test@example.com
**Password:** password123

## 📚 Bases de Datos

### Gateway DB (Puerto 5432)
POSTGRES_DB: gateway_db

### Request Service DB (Puerto 5433)
POSTGRES_DB: service_a_db

### Chat Service DB (Puerto 5434)
POSTGRES_DB: service_b_db

## 🛠️ Estructura del Proyecto

```
.
├── gateway-service/
│   ├── src/main/java/
│   ├── pom.xml
│   └── Dockerfile
├── request-service/
│   ├── src/main/java/
│   ├── pom.xml
│   └── Dockerfile
├── chat-service/
│   ├── src/main/java/
│   ├── pom.xml
│   └── Dockerfile
├── frontend/
│   ├── src/
│   ├── package.json
│   └── Dockerfile
├── pom.xml
└── docker-compose.yml
```

## 🔧 Configuración

**Backend:**
- PORT: 8080/8081/8082
- SPRING_DATASOURCE_URL
- JWT_SECRET
- CORS_ALLOWED_ORIGIN

**Frontend:**
- API_BASE_URL: http://localhost:8080/api

## ⚠️ Problemas Comunes

### "relation does not exist"
```bash
docker-compose down -v
docker-compose up -d
```

### "DependencyResolutionException"
```bash
mvn clean package -DskipTests -q
```

### Ver Logs
```bash
docker-compose logs -f [servicio]
```

## 🔐 Seguridad

- ✅ JWT para autenticación
- ✅ CORS configurado
- ✅ Contraseñas hasheadas
- ✅ Variables de entorno para secretos

## 📦 Tecnologías

**Backend:**
- Spring Boot 3.1.12
- Spring Cloud Gateway
- Spring Security
- PostgreSQL 15
- JWT
- Maven
- Java 17

**Frontend:**
- Angular 17.3.0
- RxJS 7.8.1
- TypeScript 5.2
- Nginx

**DevOps:**
- Docker & Docker Compose
- Cloud SQL (producción)

## 📖 Para Más Información

Consulta RUN_ME_FIRST.md para instrucciones paso a paso.
