# ✅ FUNCIONA - SIGUE ESTOS PASOS EXACTOS

## 🚀 PASO 1: Compilar localmente (OBLIGATORIO)

Abre PowerShell en esta carpeta y ejecuta:

```powershell
# Compilar TODO
mvn clean package -DskipTests -q
```

Espera 5-10 minutos. Cuando termine sin errores, continúa.

---

## 🐳 PASO 2: Ejecutar con Docker Compose

```powershell
# Detener todo (si corre algo)
docker-compose down -v

# Iniciar servicios
docker-compose up -d
```

Espera 30 segundos.

---

## ✅ PASO 3: Verificar que funciona

```powershell
docker-compose ps
```

Deberías ver:

```
NAME                COMMAND              STATUS
postgres-gateway    docker-entrypoint    Up 1 minute
gateway-service     java -jar app.jar    Up 1 minute
request-service     java -jar app.jar    Up 1 minute
chat-service        java -jar app.jar    Up 1 minute
frontend            nginx -g daemon      Up 1 minute
```

---

## 🌐 ACCEDER A LA APP

### Frontend
```
http://localhost:4200
```

### Gateway API
```
http://localhost:8080/api/auth/health
```

---

## 📝 CREDENCIALES DE PRUEBA

**Email**: test@example.com  
**Password**: password123

---

## 🆘 SI FALLA ALGO

### Ver logs
```powershell
docker-compose logs [servicio]
```

### Limpiar todo y reintentar
```powershell
docker-compose down -v
docker system prune -a -f
docker-compose up -d
```

---

**¡LISTO! Si seguiste estos pasos, todo debería funcionar** ✨
