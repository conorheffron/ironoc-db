FROM eclipse-temurin:21-jdk

VOLUME /tmp
ADD build/libs/*.war app.war
RUN sh -c 'touch /app.war'

ENTRYPOINT [ "sh", "-c", "java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app.war" ]
