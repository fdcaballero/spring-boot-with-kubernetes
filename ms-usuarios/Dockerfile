FROM openjdk:17.0.2

WORKDIR /app/ms-usuarios

COPY  ./pom.xml /app
COPY ./ms-usuarios/.mvn ./.mvn
COPY ./ms-usuarios/mvnw ./
COPY ./ms-usuarios/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
COPY ./ms-usuarios/src ./src
RUN ./mvnw clean package -DskipTests
EXPOSE 8081

ENTRYPOINT ["java", "-jar" , "./target/ms-usuarios-0.0.1-SNAPSHOT.jar"]

