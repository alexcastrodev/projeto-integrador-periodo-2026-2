FROM maven:3.9-eclipse-temurin-21
WORKDIR /app
COPY pom.xml .
RUN mvn -q -B dependency:go-offline
COPY src src
EXPOSE 8080
CMD ["mvn", "-q", "-B", "jetty:run"]
