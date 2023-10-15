FROM ubuntu:latest AS build

WORKDIR /app

RUN apt-get update -y 
RUN apt-get install openjdk-17-jdk -y

COPY . .

EXPOSE 8080

RUN ./mvnw clean install

COPY --from=build /app/target/todolist-1.0.0.jar ./app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]

