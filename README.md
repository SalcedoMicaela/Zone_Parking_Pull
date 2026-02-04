# 🅿️ Zone Parking - Sistema Distribuido de Estacionamiento

Sistema distribuido de gestión de estacionamientos desarrollado en **Java Spring Boot** con arquitectura de microservicios, despliegue en **Kubernetes** y comunicación asíncrona vía **RabbitMQ**.

---

## 📋 Descripción del Proyecto

**Zone Parking** es una solución completa para la gestión de zonas de estacionamiento, clientes y notificaciones. El proyecto implementa una arquitectura de microservicios escalable y resiliente, con orquestación en Kubernetes y manejo de eventos mediante colas de mensajes.

### Componentes Principales

El sistema está compuesto por **dos microservicios principales** (laboratorios):

1. **P2_lab1: Zone Core** - Microservicio de gestión de zonas de estacionamiento
   - Spring Boot 3.5.7
   - Java 21
   - Puerto: 8080
   - Base de datos: PostgreSQL (db_parkin_zone)

2. **P2_lab2: MS Clientes** - Microservicio de gestión de clientes
   - Spring Boot 4.0.0
   - Java 21
   - Puerto: 8081
   - Base de datos: PostgreSQL (db_parkin_users)

### Arquitectura General

```
┌─────────────────────────────────────────────────────────┐
│                   Kubernetes Cluster                     │
├─────────────────────────────────────────────────────────┤
│                                                           │
│  ┌──────────────────┐         ┌────────────────────┐   │
│  │   MS Clientes    │         │    Zone Core       │   │
│  │   (Puerto 8081)  │         │   (Puerto 8080)    │   │
│  └────────┬─────────┘         └────────┬───────────┘   │
│           │                            │                │
│  ┌────────▼────────────────────────────▼────────────┐  │
│  │         PostgreSQL Database Server                │  │
│  │  ┌──────────────┬──────────────┬───────────────┐ │  │
│  │  │ db_parkin_   │ db_parkin_   │ db_parkin_    │ │  │
│  │  │   users      │   zone       │   tickets     │ │  │
│  │  └──────────────┴──────────────┴───────────────┘ │  │
│  └───────────────────────────────────────────────────┘  │
│           │                                              │
│  ┌────────▼──────────────────────────────────────────┐  │
│  │              RabbitMQ Message Broker               │  │
│  │         (Comunicación asíncrona entre servicios)   │  │
│  └───────────────────────────────────────────────────┘  │
│                                                           │
│  ┌───────────────────────────────────────────────────┐  │
│  │         Ingress Nginx (API Gateway)                │  │
│  │  parkin.local/clientes                             │  │
│  │  parkin.local/zona                                 │  │
│  │  parkin.local/tickets                              │  │
│  │  parkin.local/notificaciones                        │  │
│  └───────────────────────────────────────────────────┘  │
│                                                           │
└─────────────────────────────────────────────────────────┘
```

---

## 🏗️ Estructura del Proyecto

```
Zone_Parking_Pull/
│
├── P2_lab1/                                  # Microservicio Zone Core
│   └── zone_core/
│       ├── src/main/java/ec/edu/espe/zone_core/zone_core/
│       │   ├── ZoneCoreApplication.java     # Punto de entrada
│       │   ├── controller/                  # Controladores REST
│       │   ├── services/                    # Lógica de negocio
│       │   ├── repositories/                # Acceso a datos (JPA)
│       │   ├── model/                       # Entidades JPA
│       │   ├── dto/                         # Objetos de transferencia
│       │   ├── messaging/                   # Comunicación con RabbitMQ
│       │   └── utils/                       # Utilidades
│       ├── src/main/resources/
│       │   ├── application.yaml             # Configuración
│       │   ├── static/                      # Recursos estáticos
│       │   └── templates/                   # Plantillas HTML
│       ├── Dockerfile                       # Imagen Docker
│       ├── docker-compose.yml               # Orquestación local
│       ├── pom.xml                          # Dependencias Maven
│       └── target/                          # Compilados
│
├── P2_lab2/                                  # Microservicio MS Clientes
│   └── ms-clientes/
│       ├── src/main/java/ec/edu/espe/ms_clientes/
│       │   ├── MsClientesApplication.java   # Punto de entrada
│       │   ├── controlador/                 # Controladores REST
│       │   ├── servicio/                    # Lógica de negocio
│       │   ├── repositorio/                 # Acceso a datos (JPA)
│       │   ├── model/                       # Entidades JPA
│       │   ├── dto/                         # Objetos de transferencia
│       │   ├── utils/                       # Utilidades
│       │   └── probar.java                  # Clase de prueba
│       ├── src/main/resources/
│       │   ├── application.yaml             # Configuración
│       │   ├── static/                      # Recursos estáticos
│       │   └── templates/                   # Plantillas HTML
│       ├── Dockerfile                       # Imagen Docker
│       ├── docker-compose.yaml              # Orquestación local
│       ├── pom.xml                          # Dependencias Maven
│       └── target/                          # Compilados
│
└── Despliegue kubernates/                    # Configuración Kubernetes
    ├── 01-infraestructura.yaml              # PostgreSQL + RabbitMQ
    ├── 02-servicios.yaml                    # Deployments de microservicios
    └── 4ingress.yml                         # Ingress y enrutamiento
```

---

## 🛠️ Tecnologías Utilizadas

### Backend
- **Java 21** - Lenguaje de programación
- **Spring Boot 3.5.7 & 4.0.0** - Framework web y MVC
- **Spring Data JPA** - ORM para acceso a datos
- **Spring AMQP** - Integración con RabbitMQ
- **Lombok** - Reducción de código boilerplate
- **Jackson** - Serialización JSON

### Base de Datos
- **PostgreSQL 16** - Base de datos relacional
- **4 esquemas**: db_parkin_zone, db_parkin_users, db_parkin_tickets, db_notifications

### Messaging
- **RabbitMQ 3.12** - Broker de mensajes para comunicación asíncrona

### Contenedorización y Orquestación
- **Docker** - Empaquetamiento de aplicaciones
- **Kubernetes** - Orquestación de contenedores
- **Nginx Ingress** - API Gateway y enrutamiento

### Herramientas
- **Maven** - Gestión de dependencias y compilación
- **Git** - Control de versiones

---

## 📦 Dependencias Principales

### Zone Core (P2_lab1)
```xml
- spring-boot-starter-data-jpa
- spring-boot-starter-web
- spring-boot-starter-amqp
- postgresql (driver JDBC)
- lombok
- jackson-databind
```

### MS Clientes (P2_lab2)
```xml
- spring-boot-starter-data-jpa
- spring-boot-starter-webmvc
- spring-boot-starter-validation
- postgresql (driver JDBC)
- lombok
```

---

## 🚀 Guía de Instalación y Ejecución

### Requisitos Previos
- Java 21 JDK
- Maven 3.8+
- Docker 20.10+
- Docker Compose 1.29+
- Kubernetes 1.24+ (con kubectl)
- Helm (opcional)

### 1️⃣ Instalación Local con Docker Compose

#### Opción A: Zone Core

```bash
cd P2_lab1/zone_core

# Compilar el proyecto
mvn clean package

# Construir imagen Docker
docker build -t zone_core:latest .

# Ejecutar con Docker Compose
docker-compose up -d
```

**Acceso:**
- Aplicación: http://localhost:8080
- PostgreSQL: localhost:5432 (usuario: parkin, contraseña: qwerty123)

#### Opción B: MS Clientes

```bash
cd P2_lab2/ms-clientes

# Compilar el proyecto
mvn clean package

# Construir imagen Docker
docker build -t ms-clientes:latest .

# Ejecutar con Docker Compose
docker-compose up -d
```

**Acceso:**
- Aplicación: http://localhost:8081
- PostgreSQL: localhost:5432 (usuario: parkin, contraseña: qwerty123)

### 2️⃣ Compilación Local sin Docker

#### Zone Core
```bash
cd P2_lab1/zone_core
mvn clean install
mvn spring-boot:run
```
Puerto: http://localhost:8080

#### MS Clientes
```bash
cd P2_lab2/ms-clientes
mvn clean install
mvn spring-boot:run
```
Puerto: http://localhost:8081

### 3️⃣ Despliegue en Kubernetes

#### Configurar Cluster
```bash
# Asegurarse de tener kubectl configurado
kubectl cluster-info

# Crear namespace (opcional)
kubectl create namespace parkin
```

#### Aplicar Configuraciones Kubernetes

```bash
cd Despliegue\ kubernates/

# 1. Desplegar infraestructura (PostgreSQL + RabbitMQ)
kubectl apply -f 01-infraestructura.yaml

# 2. Desplegar servicios (Microservicios)
kubectl apply -f 02-servicios.yaml

# 3. Configurar Ingress
kubectl apply -f 4ingress.yml
```

#### Verificar Despliegue
```bash
# Listar pods
kubectl get pods

# Ver logs de un servicio
kubectl logs -f deployment/ms-clientes
kubectl logs -f deployment/zone-core

# Acceder a servicios
kubectl port-forward svc/ms-clientes-svc 8081:80
kubectl port-forward svc/postgres-svc 5432:5432
kubectl port-forward svc/rabbitmq-svc 5672:5672
```

#### Configurar /etc/hosts (Linux/Mac) o C:\Windows\System32\drivers\etc\hosts (Windows)
```
127.0.0.1 parkin.local
```

#### Acceder a la Aplicación
```
http://parkin.local/clientes
http://parkin.local/zona
http://parkin.local/tickets
http://parkin.local/notificaciones
```

---

## 📊 Configuración de Aplicaciones

### Zone Core (application.yaml - P2_lab1)

```yaml
spring:
  datasource:
    url: jdbc:postgresql://postgres:5432/db_parkin_zone
    username: parkin
    password: qwerty123
  jpa:
    hibernate:
      ddl-auto: update
  rabbitmq:
    host: rabbitmq
    port: 5672
    username: admin
    password: admin

server:
  port: 8080
```

### MS Clientes (application.yaml - P2_lab2)

```yaml
spring:
  application:
    name: clientes
  datasource:
    url: jdbc:postgresql://postgres-users:5432/db_parkin_users
    username: parkin
    password: qwerty123
  jpa:
    hibernate:
      ddl-auto: update

server:
  port: 8081

logging:
  level:
    com.parkin.users: DEBUG
```

---

## 🔌 Endpoints API

### MS Clientes (Puerto 8081)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/clientes` | Listar todos los clientes |
| GET | `/clientes/{id}` | Obtener cliente por ID |
| POST | `/clientes` | Crear nuevo cliente |
| PUT | `/clientes/{id}` | Actualizar cliente |
| DELETE | `/clientes/{id}` | Eliminar cliente |

### Zone Core (Puerto 8080)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/zona` | Listar todas las zonas |
| GET | `/zona/{id}` | Obtener zona por ID |
| POST | `/zona` | Crear nueva zona |
| PUT | `/zona/{id}` | Actualizar zona |
| DELETE | `/zona/{id}` | Eliminar zona |

---

## 🔄 Comunicación entre Microservicios

Los microservicios se comunican de manera **asíncrona** mediante **RabbitMQ**:

1. **Zone Core** publica eventos sobre cambios en zonas de estacionamiento
2. **MS Clientes** se suscribe a estos eventos para actualizar su estado
3. **Notificaciones** se envían automáticamente a través de colas de mensajes

### Ejemplo de Integración RabbitMQ

**Zone Core** envía mensajes:
```java
@Service
public class ZoneService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public void publishZoneEvent(ZoneEvent event) {
        rabbitTemplate.convertAndSend("zone-exchange", "zone.created", event);
    }
}
```

**MS Clientes** escucha eventos:
```java
@Service
public class ClienteListener {
    @RabbitListener(queues = "cliente-queue")
    public void handleZoneEvent(ZoneEvent event) {
        // Procesar evento
    }
}
```

---

## 🗄️ Estructura de Bases de Datos

### PostgreSQL - Esquemas Creados

1. **db_parkin_zone** - Gestión de zonas de estacionamiento
   - Tabla: zones
   - Tabla: zona_estados

2. **db_parkin_users** - Gestión de clientes
   - Tabla: clientes
   - Tabla: usuarios

3. **db_parkin_tickets** - Gestión de tickets de estacionamiento
   - Tabla: tickets
   - Tabla: tipos_ticket

4. **db_notifications** - Sistema de notificaciones
   - Tabla: notificaciones
   - Tabla: estado_notificaciones

**Credentials:**
- Usuario: `parkin`
- Contraseña: `qwerty123`

---

## 🐳 Docker Compose

Cada microservicio incluye su propio `docker-compose.yml`:

```yaml
version: '3.8'
services:
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/db_parkin_zone
      SPRING_DATASOURCE_USERNAME: parkin
      SPRING_DATASOURCE_PASSWORD: qwerty123
    depends_on:
      - postgres
      - rabbitmq

  postgres:
    image: postgres:16-alpine
    environment:
      POSTGRES_USER: parkin
      POSTGRES_PASSWORD: qwerty123
    ports:
      - "5432:5432"

  rabbitmq:
    image: rabbitmq:3-management
    ports:
      - "5672:5672"
      - "15672:15672"
```

---

## 📈 Monitoreo y Logs

### Ver Logs en Kubernetes

```bash
# Logs de MS Clientes
kubectl logs -f deployment/ms-clientes

# Logs de Zone Core
kubectl logs -f deployment/zone-core

# Logs de PostgreSQL
kubectl logs -f deployment/postgres-db

# Logs de RabbitMQ
kubectl logs -f deployment/rabbitmq

# Con selector de etiqueta
kubectl logs -l app=ms-clientes --all-containers=true -f
```

### Acceder a RabbitMQ Management (en local)
```
http://localhost:15672
Usuario: admin
Contraseña: admin
```

### Acceder a PostgreSQL

```bash
# Usando psql dentro del contenedor
kubectl exec -it deployment/postgres-db -- psql -U parkin

# Listar bases de datos
\l

# Conectar a una BD específica
\c db_parkin_zone

# Listar tablas
\dt
```

---

## ✅ Testing

### Ejecutar Tests Unitarios

```bash
# Zone Core
cd P2_lab1/zone_core
mvn test

# MS Clientes
cd P2_lab2/ms-clientes
mvn test
```

### Prueba de Endpoints (curl)

```bash
# MS Clientes - Listar
curl http://localhost:8081/clientes

# MS Clientes - Crear
curl -X POST http://localhost:8081/clientes \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Juan","email":"juan@example.com"}'

# Zone Core - Listar
curl http://localhost:8080/zona

# Zone Core - Crear
curl -X POST http://localhost:8080/zona \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Zona A","capacidad":50}'
```

---

## 🔐 Seguridad

### Credenciales por Defecto

| Servicio | Usuario | Contraseña |
|----------|---------|-----------|
| PostgreSQL | parkin | qwerty123 |
| RabbitMQ | admin | admin |

⚠️ **IMPORTANTE:** Cambiar credenciales en producción antes del despliegue.

---

## 🐛 Troubleshooting

### Problema: Conexión rechazada a PostgreSQL
```bash
# Verificar si PostgreSQL está corriendo
kubectl get pods | grep postgres

# Ver logs de PostgreSQL
kubectl logs -f deployment/postgres-db

# Verificar conectividad
kubectl run -it --rm debug --image=postgres:16-alpine --restart=Never -- \
  psql -h postgres-svc -U parkin -d postgres
```

### Problema: RabbitMQ no conecta
```bash
# Verificar servicio
kubectl get svc rabbitmq-svc

# Ver logs
kubectl logs -f deployment/rabbitmq

# Verificar puerto
kubectl port-forward svc/rabbitmq-svc 5672:5672
```

### Problema: Microservicios no inician
```bash
# Ver eventos del cluster
kubectl describe pod <pod-name>

# Ver logs completos
kubectl logs <pod-name> --previous

# Recrear deployment
kubectl rollout restart deployment/ms-clientes
```

### Problema: Variables de entorno no se aplican
```bash
# Editar deployment
kubectl edit deployment ms-clientes

# Restart para aplicar cambios
kubectl rollout restart deployment/ms-clientes
```

---

## 📝 Variables de Entorno (Kubernetes)

Se pueden override en el manifiesto `02-servicios.yaml`:

```yaml
env:
  - name: SPRING_DATASOURCE_URL
    value: "jdbc:postgresql://postgres-svc:5432/db_parkin_users"
  - name: SPRING_DATASOURCE_USERNAME
    value: "parkin"
  - name: SPRING_DATASOURCE_PASSWORD
    value: "qwerty123"
  - name: SPRING_RABBITMQ_HOST
    value: "rabbitmq-svc"
  - name: SPRING_RABBITMQ_PORT
    value: "5672"
```

---

## 🔗 Enrutamiento Ingress

```yaml
# Rutas configuradas en 4ingress.yml
parkin.local/clientes      → ms-clientes-svc:8081
parkin.local/zona          → zone-core-svc:8080
parkin.local/tickets       → ticket-svc (futuro)
parkin.local/notificaciones → notification-svc (futuro)
```

---

## 📚 Recursos Adicionales

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [RabbitMQ Documentation](https://www.rabbitmq.com/documentation.html)
- [Kubernetes Documentation](https://kubernetes.io/docs/)
- [Docker Documentation](https://docs.docker.com/)

---

## 👨‍💻 Autores

Proyecto desarrollado como parte del curso de **Sistemas Distribuidos** - 7mo Semestre ESPE
**Integrantes del grupo** 
- Alejandro Andra 
- Jairo Bonilla 
- Axel Herrera
- Micaela Salcedo
---

## 📄 Licencia

Este proyecto no tiene licencia específica. Uso restringido para propósitos educativos.

---

## 📞 Soporte

Para reportar problemas o sugerencias, crear un issue en el repositorio Git.

---

**Última actualización:** Febrero 2026
**Estado:** Activo en desarrollo
