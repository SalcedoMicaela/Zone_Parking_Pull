# 1️⃣ Usamos Java 21 (igual a tu versión)
FROM eclipse-temurin:21-jdk-alpine

# 2️⃣ Carpeta de trabajo dentro del contenedor
WORKDIR /app

# 3️⃣ Copiar el jar de tu aplicación al contenedor
COPY target/*.jar app.jar

# 4️⃣ Puerto que expone tu app
EXPOSE 8080

# 5️⃣ Comando para ejecutar la app
ENTRYPOINT ["java","-jar","app.jar"]
