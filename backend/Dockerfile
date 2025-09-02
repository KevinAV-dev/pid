# Usa una imagen base de Java 21
FROM openjdk:21-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR de tu aplicación al contenedor
# Reemplaza 'nombre-de-tu-app.jar' con el nombre real de tu JAR
COPY target/formulario-0.0.1-SNAPSHOT.jar /app/app.jar

# Expone el puerto en el que escucha tu aplicación (por defecto 8080)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]