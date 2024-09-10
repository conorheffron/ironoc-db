FROM eclipse-temurin:21-jdk

VOLUME /tmp
# for CD
ADD *.war app.war
RUN sh -c 'touch /app.war'

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=h2 -jar /app.war" ]
