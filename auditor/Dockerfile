FROM openjdk:17-alpine

ADD ./target/auditor-0.0.1-SNAPSHOT.jar /app/

ENTRYPOINT ["java", "-Xmx200m", "-jar", "/app/auditor-0.0.1-SNAPSHOT.jar"]
EXPOSE 9095