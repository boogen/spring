FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

COPY gradlew ./
COPY gradle ./gradle
COPY settings.gradle.kts build.gradle.kts ./

RUN chmod +x gradlew

RUN ./gradlew --no-daemon dependencies || true

COPY . .
RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/build/libs/ ./libs/

RUN cp ./libs/*-SNAPSHOT.jar app.jar && rm -rf ./libs

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -jar app.jar"]
