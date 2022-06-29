FROM openjdk:8-jre-alpine

EXPOSE 8080

COPY ./target/githubaction_test-1.0.1.war /usr/app/
WORKDIR /usr/app

ENTRYPOINT ["java", "-jar", "githubaction_test-1.0.1.jar"]