FROM alpine/java:21-jdk

# for CI
ADD *.war app.war
# for local
#COPY build/libs/*.war app.war

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=h2 -jar /app.war" ]
