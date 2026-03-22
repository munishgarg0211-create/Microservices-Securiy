# Build Stage: Standard JDK for compilation
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Runtime Stage: Distroless JRE for maximum security
# Distroless contains only the OS shim and the JRE. No shell, no package manager.
FROM gcr.io/distroless/java21-debian11
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Run as nonroot user (UID 65532) defined in the distroless image
USER nonroot

# Expose port
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
