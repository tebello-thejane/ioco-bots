FROM openjdk:14
EXPOSE 8080
ARG JAR_FILE=robot_apocalypse/target/*.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
