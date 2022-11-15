FROM openjdk:8
EXPOSE 9090
ADD /target/employee-app-v1.jar employee-app-v1.jar
CMD [ "java", "-jar", "/employee-app-v1.jar" ]