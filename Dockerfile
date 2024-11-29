FROM gradle:8.11.1-jdk23-alpine

COPY . /home/gradle
RUN export LD_BIND_NOW=1
RUN gradle build

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "gradle bootRun --args='--spring.profiles.active=h2'" ]
