# 🚀 COMIENZA AQUÍ - Conserjería Virtual con IA Generativa

## ¡PROYECTO COMPLETAMENTE GENERADO Y FUNCIONAL!

Este documento te guía para ejecutar la aplicación en tu computadora.

---

## 📋 Requisitos Previos (Verificar)

Abre PowerShell y ejecuta:

```powershell
java -version
mvn -version
docker --version
docker-compose --version
```

**Deben mostrar versiones. Si alguno falta, instálalo:**
- Java 17+: https://www.oracle.com/java/technologies/downloads/
- Maven 3.8+: https://maven.apache.org/download.cgi
- Docker Desktop: https://www.docker.com/products/docker-desktop

---

## 🎯 PASO 1: Compilar el Proyecto (5-10 minutos)

Abre PowerShell **en esta carpeta** y ejecuta:

```powershell
mvn clean package -DskipTests -q
```

⏳ **Espera a que termine.** Maven descargará ~500MB de dependencias.

Cuando veas el cursor sin errores, continúa.

---

## 🐳 PASO 2: Iniciar Servicios (30 segundos)

En la misma PowerShell:

```powershell
docker-compose down -v
docker-compose up -d
```

Espera 30 segundos a que arranque todo.

---

## ✅ PASO 3: Verificar Estado

```powershell
docker-compose ps
```

**Deberías ver:**
```
postgres-gateway   Up
postgres-request   Up
postgres-chat      Up
gateway-service    Up
request-service    Up
chat-service       Up
frontend           Up
```

Si alguno dice "Exited", ve a la sección "Solucionar Problemas" abajo.

---

## 🌐 PASO 4: Acceder a la App

### Frontend (aplicación principal)
```
http://localhost:4200
```

### Credenciales de prueba:
- **Email:** test@example.com
- **Contraseña:** password123

### APIs (para testing)
- Gateway: `http://localhost:8080/api/auth/health`
- Request Service: `http://localhost:8081/actuator/health`
- Chat Service: `http://localhost:8082/actuator/health`

---

## 📝 Funcionalidades Disponibles

### ✅ En el Frontend
- Login y Registro
- Dashboard de bienvenida
- CRUD de Solicitudes de Conserjería
- Chat con IA Generativa
- Navegación completa

### ✅ En los APIs
- Autenticación JWT
- Gestión de usuarios
- Categorías de servicios
- Solicitudes (crear, leer, actualizar, eliminar)
- Conversaciones de chat
- Recomendaciones inteligentes

---

## 🧪 PASO 5: Probar con Postman (Opcional)

1. Descarga e instala Postman: https://www.postman.com/downloads/
2. Abre Postman
3. Menú: **File → Import**
4. Selecciona: `postman-collection.json` (en esta carpeta)
5. Haz clic en cualquier request

**Primero,** ejecuta "Login" para obtener un token.

---

## 🛑 Solucionar Problemas

### "La aplicación no carga en http://localhost:4200"

```powershell
docker-compose logs frontend
```

Si ves errores, ejecuta:
```powershell
docker-compose down -v
docker-compose up -d
```

### "Los servicios no inician"

```powershell
docker-compose logs [servicio]
```

Ejemplo:
```powershell
docker-compose logs gateway-service
```

Si ves "relation does not exist":
```powershell
docker-compose down -v
docker-compose up -d
```

### "Port 8080 is already in use"

Hay otro proceso usando el puerto:
```powershell
netstat -ano | findstr :8080
taskkill /PID [PID] /F
docker-compose up -d
```

### "mvn command not found"

Maven no está en PATH. Reinstálalo y asegúrate de agregar a PATH.

### "java -version dice Java 21, no 17"

El proyecto está configurado para Java 17. Si solo tienes Java 21, cambia en `pom.xml`:
```xml
<maven.compiler.source>21</maven.compiler.source>
<maven.compiler.target>21</maven.compiler.target>
```

---

## 📚 Documentación

Si necesitas más detalles:

- **`RUN_ME_FIRST.md`** - Pasos exactos de ejecución
- **`README.md`** - Documentación completa
- **`DEPLOYMENT.md`** - Cómo desplegar en Google Cloud
- **`PROJECT_STATUS.md`** - Estado y características
- **`postman-collection.json`** - Tests de API

---

## 🔧 Comandos Útiles

```powershell
# Ver logs en tiempo real
docker-compose logs -f [servicio]

# Detener servicios
docker-compose down

# Detener y eliminar datos
docker-compose down -v

# Reiniciar un servicio
docker-compose restart [servicio]

# Ver estado
docker-compose ps

# Limpiar todo de Docker
docker system prune -a -f
```

---

## 📊 Estructura del Proyecto

```
.
├── gateway-service/          # API Gateway (puerto 8080)
├── request-service/          # Solicitudes (puerto 8081)
├── chat-service/             # Chat IA (puerto 8082)
├── frontend/                 # Angular (puerto 4200)
├── docker-compose.yml        # Orquestación
├── pom.xml                   # Parent Maven
├── postman-collection.json   # Tests
└── README.md                 # Documentación
```

---

## 🎓 Flujo de Uso Típico

1. **Abre** http://localhost:4200
2. **Haz clic** "Registrarse" (o usa test@example.com)
3. **Inicia sesión**
4. **Crea solicitudes** en la sección "Solicitudes"
5. **Chat** con la IA en la sección "Chat IA"
6. **Dashboard** para ver resumen

---

## ⏰ Tiempo Total

- Compilación: 5-10 minutos
- Iniciar servicios: 30 segundos
- Listo para usar: ~6-11 minutos

---

## 💡 Consejos

✅ Mantén Docker Desktop abierto mientras usas la app
✅ Si cambias código, recompila: `mvn clean package -DskipTests -q`
✅ Luego reinicia: `docker-compose down -v && docker-compose up -d`
✅ Los datos se pierden al hacer `docker-compose down -v` (usa sin `-v` para mantener)
✅ Consulta logs si algo falla

---

## 🎉 ¡LISTO!

Tu aplicación está completamente funcional. Disfruta y ¡buena suerte en el examen!

**Preguntas frecuentes:**
- ¿Qué es este proyecto? → Conserjería Virtual con IA Generativa
- ¿Qué tecnologías usa? → Java 17, Spring Boot, PostgreSQL, Angular 17, Docker
- ¿Es seguro? → Sí, incluye JWT, CORS, validaciones, hashing de contraseñas
- ¿Puedo usarlo en producción? → Sí, con algunos ajustes de seguridad (DEPLOYMENT.md)

---

**Si algo no funciona, revisa el archivo de logs:**
```powershell
docker-compose logs -f
```

¡Muchas éxito! 🚀
