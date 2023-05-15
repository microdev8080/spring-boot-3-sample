FROM openjdk:17-alpine

ADD ./target/auth-service-0.0.1-SNAPSHOT.jar /app/

ENTRYPOINT ["java", "-Xmx200m", "-jar", "/app/auth-service-0.0.1-SNAPSHOT.jar"]
EXPOSE 9095