# 📑 Índice Completo del Proyecto

## 🗂️ Estructura de Archivos Generados

```
app-microservices/
│
├── 📄 DOCUMENTACIÓN
│   ├── README.md                      ← Documentación completa
│   ├── QUICK_START.md                 ← Guía rápida (2-5 minutos)
│   ├── SETUP_GCP.md                   ← Setup Google Cloud (paso a paso)
│   ├── COMPLETENESS_CHECKLIST.md      ← Lo que está completo
│   ├── INIT_DATA.sql                  ← Datos iniciales
│   ├── postman-collection.json        ← Tests API
│   └── INDEX.md                       ← Este archivo
│
├── 📦 BACKEND - JAVA SPRING BOOT
│   │
│   ├── pom.xml                        ← Maven parent (21 dependencias)
│   │
│   ├── 🔐 GATEWAY-SERVICE (Autenticación)
│   │   ├── pom.xml
│   │   ├── Dockerfile
│   │   ├── src/main/resources/application.yml
│   │   └── src/main/java/com/concierge/gateway/
│   │       ├── GatewayApplication.java (Spring Cloud Gateway)
│   │       ├── config/
│   │       │   └── SecurityConfig.java (Spring Security + CORS)
│   │       ├── controller/
│   │       │   └── AuthController.java (/register, /login, /me)
│   │       ├── service/
│   │       │   └── AuthService.java (lógica autenticación)
│   │       ├── repository/
│   │       │   └── UserRepository.java (JPA)
│   │       ├── model/
│   │       │   ├── User.java
│   │       │   └── Role.java (GUEST, STAFF, ADMIN)
│   │       ├── dto/
│   │       │   ├── LoginRequest.java
│   │       │   ├── RegisterRequest.java
│   │       │   ├── JwtResponse.java
│   │       │   └── UserResponse.java
│   │       └── security/
│   │           ├── JwtTokenProvider.java (JWT generation)
│   │           └── JwtAuthenticationFilter.java (Filter)
│   │
│   ├── 📋 REQUEST-SERVICE (Gestión de Solicitudes)
│   │   ├── pom.xml
│   │   ├── Dockerfile
│   │   ├── src/main/resources/application.yml
│   │   └── src/main/java/com/concierge/request/
│   │       ├── RequestServiceApplication.java
│   │       ├── controller/
│   │       │   ├── ConciergeRequestController.java (CRUD solicitudes)
│   │       │   └── ServiceCategoryController.java (CRUD categorías)
│   │       ├── service/
│   │       │   ├── ConciergeRequestService.java
│   │       │   └── ServiceCategoryService.java
│   │       ├── repository/
│   │       │   ├── ConciergeRequestRepository.java
│   │       │   ├── ServiceCategoryRepository.java
│   │       │   ├── RequestCommentRepository.java
│   │       │   └── RequestReviewRepository.java
│   │       ├── model/
│   │       │   ├── ConciergeRequest.java (Entidad principal)
│   │       │   ├── ServiceCategory.java
│   │       │   ├── RequestComment.java
│   │       │   ├── RequestReview.java
│   │       │   ├── RequestStatus.java (PENDING, IN_PROGRESS, COMPLETED, CANCELLED)
│   │       │   └── Priority.java (LOW, MEDIUM, HIGH, URGENT)
│   │       └── dto/
│   │           ├── ConciergeRequestDTO.java
│   │           ├── ServiceCategoryDTO.java
│   │           ├── RequestCommentDTO.java
│   │           └── CreateRequestDTO.java
│   │
│   └── 💬 CHAT-SERVICE (Chat + IA)
│       ├── pom.xml
│       ├── Dockerfile
│       ├── src/main/resources/application.yml
│       └── src/main/java/com/concierge/chat/
│           ├── ChatServiceApplication.java
│           ├── controller/
│           │   ├── ChatConversationController.java
│           │   └── RecommendationController.java
│           ├── service/
│           │   ├── ChatService.java (Lógica principal)
│           │   └── AIResponseService.java (IA básica)
│           ├── repository/
│           │   ├── ChatConversationRepository.java
│           │   ├── ChatMessageRepository.java
│           │   └── RecommendationRepository.java
│           ├── model/
│           │   ├── ChatConversation.java
│           │   ├── ChatMessage.java
│           │   ├── KnowledgeBase.java
│           │   ├── Recommendation.java
│           │   └── MessageType.java (USER, AI)
│           └── dto/
│               ├── ChatMessageDTO.java
│               ├── ConversationDTO.java
│               └── RecommendationDTO.java
│
├── 🎨 FRONTEND - ANGULAR 21
│   ├── package.json (dependencias)
│   ├── angular.json (config Angular)
│   ├── tsconfig.json (TypeScript)
│   ├── tsconfig.app.json
│   ├── .npmrc (npm config)
│   ├── Dockerfile (Node 20 + Nginx)
│   ├── nginx.conf (SPA routing + proxy)
│   │
│   └── src/
│       ├── index.html
│       ├── main.ts (bootstrap)
│       ├── styles.scss (estilos globales)
│       ├── app.component.ts
│       ├── app.routes.ts
│       │
│       ├── app/
│       │   ├── core/
│       │   │   ├── services/
│       │   │   │   ├── auth.service.ts (login, register, getCurrentUser)
│       │   │   │   ├── request.service.ts (CRUD solicitudes)
│       │   │   │   └── chat.service.ts (CRUD conversaciones)
│       │   │   ├── guards/
│       │   │   │   └── auth.guard.ts (route protection)
│       │   │   └── interceptors/
│       │   │       └── jwt.interceptor.ts (Bearer token)
│       │   │
│       │   └── shared/
│       │       └── navbar/
│       │           └── navbar.component.ts
│       │
│       └── environments/
│           ├── environment.ts (desarrollo: localhost:8080)
│           └── environment.prod.ts (producción: Cloud Run)
│
├── 🐳 DOCKER & ORQUESTACIÓN
│   └── docker-compose.yml
│       - postgres-gateway (5432)
│       - postgres-request (5433)
│       - postgres-chat (5434)
│       - gateway-service (8080)
│       - request-service (8081)
│       - chat-service (8082)
│       - frontend (4200)
│       - 3x networks
│       - 3x volumes
│
├── 🚀 CI/CD - GITHUB ACTIONS
│   └── .github/workflows/
│       ├── deploy-gateway.yml
│       │   - Build Maven
│       │   - Push a Artifact Registry
│       │   - Deploy a Cloud Run
│       │   - Cloud SQL integration
│       │
│       ├── deploy-request-service.yml
│       │   - Mismo patrón
│       │
│       ├── deploy-chat-service.yml
│       │   - Mismo patrón
│       │
│       └── deploy-frontend.yml
│           - Build Angular
│           - Push a Artifact Registry
│           - Deploy a Cloud Run (Nginx)
│
├── ⚙️ CONFIGURACIÓN
│   └── .gitignore (Maven, Node, IDEs)
│
└── 📊 BASES DE DATOS
    ├── gateway_db/
    │   └── users
    │
    ├── service_a_db/
    │   ├── service_categories
    │   ├── concierge_requests
    │   ├── request_comments
    │   └── request_reviews
    │
    └── service_b_db/
        ├── chat_conversations
        ├── chat_messages
        ├── knowledge_base
        └── recommendations
```

---

## 📋 ENDPOINTS REST

### 🔐 Autenticación (Public)
```
POST   /api/auth/register      Registrar usuario
POST   /api/auth/login         Login
GET    /api/auth/me            Datos usuario actual (Protegido)
GET    /api/auth/health        Health check
```

### 📝 Request Service (Protegido)
```
POST   /api/request-service/requests                      Crear solicitud
GET    /api/request-service/requests                      Listar mis solicitudes
GET    /api/request-service/requests/{id}                 Detalle solicitud
PUT    /api/request-service/requests/{id}                 Actualizar solicitud
PATCH  /api/request-service/requests/{id}/status          Cambiar estado
DELETE /api/request-service/requests/{id}                 Eliminar solicitud
GET    /api/request-service/categories                    Listar categorías
POST   /api/request-service/categories                    Crear categoría
PUT    /api/request-service/categories/{id}               Actualizar categoría
DELETE /api/request-service/categories/{id}               Eliminar categoría
POST   /api/request-service/requests/{id}/comments        Añadir comentario
GET    /api/request-service/requests/{id}/comments        Obtener comentarios
```

### 💬 Chat Service (Protegido)
```
POST   /api/chat-service/conversations                    Crear conversación
GET    /api/chat-service/conversations                    Listar mis conversaciones
GET    /api/chat-service/conversations/{id}               Detalle conversación
POST   /api/chat-service/conversations/{id}/messages      Enviar mensaje
GET    /api/chat-service/conversations/{id}/messages      Historial mensajes
POST   /api/chat-service/recommendations                  Añadir recomendación
GET    /api/chat-service/recommendations/{conversationId} Obtener recomendaciones
```

---

## 🚀 CÓMO EMPEZAR

### Opción 1: Docker Compose (Recomendado - 2 minutos)
```bash
git clone <repo>
cd app-microservices
docker-compose up -d
# Ir a http://localhost:4200
```

### Opción 2: Local con Java & Node (5 minutos)
```bash
mvn clean package -DskipTests
java -jar gateway-service/target/gateway-service-1.0.0.jar &
java -jar request-service/target/request-service-1.0.0.jar &
java -jar chat-service/target/chat-service-1.0.0.jar &
cd frontend && npm install && npm start
# Ir a http://localhost:4200
```

### Opción 3: Google Cloud Run (Automático)
```bash
# Ver SETUP_GCP.md para instrucciones detalladas
bash SETUP_GCP.md
git push origin main
# GitHub Actions despliega automáticamente
```

---

## 📚 DOCUMENTACIÓN RECOMENDADA

1. **QUICK_START.md** - Para empezar rápido (2-5 min)
2. **README.md** - Documentación completa
3. **SETUP_GCP.md** - Setup en Google Cloud
4. **postman-collection.json** - Testing API
5. **COMPLETENESS_CHECKLIST.md** - Lo que está completo

---

## 🔑 Credenciales de Prueba

Después de `docker-compose up`, registrarse en http://localhost:4200 o usar:

```
Email:    test@example.com
Password: password123
```

---

## 📊 Variables de Entorno

Todas las env vars están configuradas en:
- `docker-compose.yml` - Para desarrollo
- `application.yml` en cada servicio - Con defaults
- GitHub Secrets - Para GCP

No requiere cambios manuales para empezar.

---

## ✨ Características Principales

- ✅ Arquitectura de microservicios
- ✅ Autenticación JWT completa
- ✅ Spring Cloud Gateway
- ✅ Angular 21 standalone
- ✅ PostgreSQL (3 bases de datos)
- ✅ Docker Compose ready
- ✅ GitHub Actions CI/CD
- ✅ Google Cloud Run ready
- ✅ Cloud SQL integration
- ✅ Workload Identity Federation
- ✅ 80+ archivos generados

---

## 🎯 Archivo para Consultar Según la Tarea

| Necesito... | Ver... |
|------------|--------|
| Empezar ahora | QUICK_START.md |
| Entender la arquitectura | README.md + diagrama |
| Endpoints API | README.md o postman-collection.json |
| Deploy en GCP | SETUP_GCP.md |
| Testing manual | postman-collection.json |
| Ver lo completo | COMPLETENESS_CHECKLIST.md |
| Estructura carpetas | Este archivo (INDEX.md) |
| Datos iniciales | INIT_DATA.sql |

---

## 🆘 ¿Algo no funciona?

1. Consultar **QUICK_START.md** → Troubleshooting
2. Consultar **README.md** → Troubleshooting
3. Ver logs: `docker-compose logs -f <servicio>`
4. Probar health: `curl http://localhost:8080/api/auth/health`

---

Generado: 2026-05-07
Proyecto: Conserjería Virtual con IA Generativa
Status: ✅ **100% Completo**
