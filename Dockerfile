# syntax=docker/dockerfile:1

# --- Build Stage ---
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copy Maven wrapper and config first for better caching
COPY --link pom.xml mvnw ./
COPY --link .mvn .mvn/

# Make sure Maven wrapper is executable and download dependencies with retry
RUN chmod +x mvnw && ./mvnw dependency:go-offline -Dmaven.wagon.http.retryHandler.count=3

# Copy source code
COPY --link src ./src/

# Build the application with offline mode
RUN ./mvnw package -DskipTests -o || ./mvnw package -DskipTests

# --- Runtime Stage ---
FROM eclipse-temurin:21-jre
WORKDIR /app

# Create a non-root user and group
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser

# Copy built jar from build stage
COPY --from=build /app/target/*.jar /app/app.jar

# Set permissions
RUN chown appuser:appgroup /app/app.jar

USER appuser

# JVM flags for container resource awareness
ENV JAVA_OPTS="-XX:MaxRAMPercentage=80.0 -XX:+UseContainerSupport"

# Expose port for InnovaTube service
EXPOSE 8091

# Use exec form for proper signal handling
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]
