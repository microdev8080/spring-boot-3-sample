FROM openjdk:17-alpine

ADD ./target/api-gateway-0.0.1-SNAPSHOT.jar /app/

ENTRYPOINT ["java", "-Xmx200m", "-jar", "/app/api-gateway-0.0.1-SNAPSHOT.jar"]
EXPOSE 9099