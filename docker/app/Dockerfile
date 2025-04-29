FROM maven:3.9.4-amazoncorretto-21-debian-bookworm

WORKDIR /app

ARG APP_ENVIRONMENT
ENV APP_ENVIRONMENT=${APP_ENVIRONMENT}
ENV MAVEN_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8010"

COPY pom.xml ./

RUN mvn dependency:go-offline

COPY ./ /app/

RUN cp "/app/src/main/resources/application-${APP_ENVIRONMENT}.properties" /app/src/main/resources/application.properties

EXPOSE 8080 8010

CMD [ "mvn", "-X", "clean", "spring-boot:run" ]
