# 🔧 Fixes - Problemas Resueltos

## ❌ Error 1: "package-lock.json: not found"

### Causa
El Dockerfile estaba intentando copiar `package-lock.json` que no existía.

### ✅ Solución
Ya está arreglado en `frontend/Dockerfile`:
- Cambié `npm ci` por `npm install`
- Removí la copia de `package-lock.json`

### Para ejecutar ahora
```bash
docker-compose up -d
```

---

## ❌ Error 2: "Could not find '@angular-devkit/build-angular:dev-server'"

### Causa
Angular DevKit no estaba instalado porque los paquetes npm no se instalaron correctamente.

### ✅ Soluciones aplicadas
1. Actualicé `frontend/angular.json` con configuración completa
2. Actualicé `frontend/package.json` con versiones correctas
3. Mejoré `frontend/Dockerfile` para mejor manejo de builds

### Para ejecutar ahora
```bash
docker-compose up -d
```

---

## 📋 Cambios Realizados

### `docker-compose.yml`
```yaml
# Antes
context: ./frontend
dockerfile: Dockerfile

# Después
context: .
dockerfile: frontend/Dockerfile
```

### `frontend/Dockerfile`
```dockerfile
# Cambios:
# 1. npm ci → npm install --legacy-peer-deps
# 2. Removida copia de package-lock.json
# 3. Contexto correcto
# 4. Health check añadido
```

### `frontend/package.json`
```json
// Versiones actualizadas a 17.3.0 (más estables)
// Agregado script de postinstall
// Agregado --host 0.0.0.0 al start
```

### `frontend/angular.json`
```json
// Configuración completa para build y dev
// Paths correctos
// Budgets configurados
// Development y production configurations
```

---

## 🚀 Ahora SÍ debería funcionar

### Opción 1: Docker Compose (Recomendado)
```bash
cd C:\Users\MI PC\Desktop\Programacion2\preparcial2
docker-compose down -v  # Limpiar si corrió antes
docker-compose up -d
```

Espera 60 segundos y ve a: **http://localhost:4200**

### Opción 2: Local (si tienes Node 20+ instalado)
```bash
cd frontend
npm install
npm start
```

Luego en otra terminal:
```bash
mvn clean package -DskipTests
java -jar gateway-service/target/gateway-service-1.0.0.jar &
java -jar request-service/target/request-service-1.0.0.jar &
java -jar chat-service/target/chat-service-1.0.0.jar &
```

---

## ✅ Verificar que funciona

```bash
# Verificar que todos los servicios están corriendo
docker-compose ps

# Ver logs
docker-compose logs -f frontend

# Health check
curl http://localhost:8080/api/auth/health

# Frontend
curl http://localhost:4200/health
```

---

## 🛠️ Si aún falla

### "Docker build still fails"
```bash
# Limpiar todo
docker-compose down -v
docker system prune -a

# Volver a intentar
docker-compose up -d
```

### "npm install falla"
```bash
# Dentro del contenedor
docker-compose exec frontend npm install --legacy-peer-deps

# O recrear
docker-compose down
docker-compose up -d --build
```

### "Angular ng no encontrado"
```bash
# El contenedor debería instalarlo automáticamente
# Si no, ejecutar manualmente
docker-compose exec frontend npm install

# Ver si está instalado
docker-compose exec frontend npx ng version
```

---

## ❌ Error 3: "Maven build failed in Docker"

### Causa
Alpine Linux es muy minimal y faltaban dependencias para compilar con Maven. El error fue:
```
exit code: 1 - MojoFailureException
```

### ✅ Solución
Cambié la imagen base en los Dockerfiles de backend:
- **Antes**: `FROM eclipse-temurin:21-jdk-alpine AS builder` + `apk add maven`
- **Después**: `FROM maven:3.9-eclipse-temurin-21-alpine AS builder`

La imagen oficial de Maven ya incluye Java 21 y Maven 3.9, evitando problemas.

### Archivos actualizados
- `gateway-service/Dockerfile`
- `request-service/Dockerfile`
- `chat-service/Dockerfile`

---

## ❌ Error 4: "DependencyResolutionException en Maven"

### Causa
Maven no podía resolver dependencias cuando se copiaban solo carpetas específicas. El proyecto multi-módulo necesitaba toda la estructura.

### ✅ Solución
Cambié todos los Dockerfiles para copiar **todo el proyecto** en lugar de partes:
```dockerfile
# Antes - Copiaba solo módulos
COPY pom.xml .
COPY gateway-service ./gateway-service

# Después - Copia todo el proyecto
COPY . .
```

Ahora Maven tiene acceso a todos los pom.xml y puede resolver dependencias correctamente.

### Cambios adicionales
- Agregué `-q` al comando mvn (quiet mode, menos output)
- Agregué health checks a todos los servicios Java
- Mejor manejo de errores

---

## 📝 Resumen de cambios

| Archivo | Cambio | Estado |
|---------|--------|--------|
| docker-compose.yml | Contexto correcto | ✅ |
| frontend/Dockerfile | npm install, sin package-lock | ✅ |
| frontend/angular.json | Config completa | ✅ |
| frontend/package.json | Versiones actualizadas | ✅ |
| frontend/.npmrc | legacy-peer-deps | ✅ |
| gateway-service/Dockerfile | Maven official image | ✅ |
| request-service/Dockerfile | Maven official image | ✅ |
| chat-service/Dockerfile | Maven official image | ✅ |

---

## 🚀 Ahora debería funcionar sin errores

Ejecuta:
```bash
# Limpiar builds anteriores
docker-compose down -v
docker system prune -a

# Build y ejecutar
docker-compose up -d --build
```

Espera **3-5 minutos** (primera compilación de Maven es lenta)

Ve a http://localhost:4200
