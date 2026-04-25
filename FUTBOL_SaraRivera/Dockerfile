FROM eclipse-temurin:21-jre-jammy

COPY ./target/FUTBOL-1.jar /app.jar

EXPOSE 8217

ENTRYPOINT ["java", "-jar", "/app.jar", "--server.port=8217"]