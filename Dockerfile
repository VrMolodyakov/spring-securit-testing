FROM openjdk:11
COPY build/libs/finance-0.0.1-SNAPSHOT.jar finance-react-app.jar
CMD ["java","-jar","finance-react-app.jar"]