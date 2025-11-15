FROM eclipse-temurin:21-jdk

# Copia el jar generado por Maven al contenedor
COPY ./target/FUTBOL-1.jar /app.jar

# Puerto que usa tu aplicación (según tu EXPOSE)
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app.jar"]