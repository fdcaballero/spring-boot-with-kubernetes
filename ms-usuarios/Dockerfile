FROM openjdk:17.0.2-jdk

WORKDIR /app/ms-usuarios

COPY  ./pom.xml /app
COPY ./ms-usuarios .

RUN ./mvnw clean package -DskipTests
EXPOSE 8081

ENTRYPOINT ["java", "-jar" , "./target/ms-usuarios-0.0.1-SNAPSHOT.jar"]

