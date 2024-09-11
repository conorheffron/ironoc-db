FROM gradle:8.5-jdk21-alpine

COPY . /home/gradle
RUN gradle build

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "gradle bootRun --args='--spring.profiles.active=h2'" ]
