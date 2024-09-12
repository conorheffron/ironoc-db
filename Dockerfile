FROM gradle:8.10.1-jdk21-alpine

COPY . /home/gradle
RUN gradle build

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "gradle bootRun --args='--spring.profiles.active=h2'" ]
