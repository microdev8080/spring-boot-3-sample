FROM openjdk:17-alpine

ADD ./target/customer-core-0.0.1-SNAPSHOT.jar /app/

ENTRYPOINT ["java", "-Xmx200m", "-jar", "/app/customer-core-0.0.1-SNAPSHOT.jar"]
EXPOSE 8083