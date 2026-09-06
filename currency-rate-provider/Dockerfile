# Build stage: compiles the code and packages the jar. Never runs the app.
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -B dependency:go-offline
COPY src ./src
RUN mvn -q -B package -DskipTests

# Runtime stage: the release artifact. Only ever runs the jar built above,
# never rebuilds it — all environment-specific behaviour comes from config
# injected at `docker run` time (SPRING_PROFILES_ACTIVE, ZOOKEEPER_CONNECT_STRING, ...).
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/currency-rate-provider-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
