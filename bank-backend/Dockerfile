FROM eclipse-temurin:21-jdk AS build-backend
WORKDIR /app
COPY pom.xml .
COPY mvnw .
COPY .mvn/ .mvn
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build-backend /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
