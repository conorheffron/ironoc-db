FROM gradle:8.14.0-jdk24-alpine

COPY . /home/gradle

RUN apk update && apk upgrade --no-cache
RUN apk add gcompat
ENV LD_PRELOAD=/lib/libgcompat.so.0

RUN gradle build

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "gradle bootRun --args='--spring.profiles.active=h2'" ]
