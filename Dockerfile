FROM eclipse-temurin:17-jdk as build
WORKDIR /workspace/app

# Copy gradle files
COPY gradle gradle
COPY build.gradle settings.gradle gradlew ./

# Download dependencies
RUN ./gradlew dependencies

# Copy source code
COPY src src

# Build the application
RUN ./gradlew build -x test

FROM eclipse-temurin:17-jre
VOLUME /tmp
COPY --from=build /workspace/app/build/libs/*.jar /app/app.jar

# Run as non-root user for security
RUN addgroup --system --gid 1001 appgroup && \
    adduser --system --uid 1001 --gid 1001 appuser
USER appuser

ENTRYPOINT ["java", "-jar", "/app/app.jar"]