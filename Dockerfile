#BUILD
FROM maven:3-jdk-11 as builder
RUN mkdir -p /build
WORKDIR /build
COPY pom.xml /build
RUN mvn -B dependency:resolve dependency:resolve-plugins
COPY src /build/src
RUN mvn package

#RUN
FROM "openjdk:11-jdk"
MAINTAINER Marcos Mele
COPY --from=builder /build/target/*.jar batalha_medieval.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=prd", "-jar", "/batalha_medieval.jar"]