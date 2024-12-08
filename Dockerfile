FROM maven:3.9.9-eclipse-temurin-23 AS build

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
COPY lombok.config .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:23-jdk AS run
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
