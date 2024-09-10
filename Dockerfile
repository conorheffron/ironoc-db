FROM eclipse-temurin:21-jdk

VOLUME /tmp
# for CI
ADD *.war app.war
# for local
#ADD build/libs/*.war app.war
RUN sh -c 'touch /app.war'

EXPOSE 8080
#MySQL
#ENTRYPOINT [ "sh", "-c", "java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app.war" ]
#H2
ENTRYPOINT [ "sh", "-c", "java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=h2 -jar /app.war" ]
