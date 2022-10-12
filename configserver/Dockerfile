FROM openjdk:8-jdk-alpine
COPY apiEncryptionkey.jks apiEncryptionkey.jks
COPY UnlimitedJCEPolicyJDK8/* /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/
COPY terget/configserver-0.0.1-SNAPSHOT.jar configserver-0.0.1-SNAPSHOT.jar
ENTRYPOINT [ "java","-Djava.security.egd=file:/dev/./urandom","-jar","configserver-0.0.1-SNAPSHOT.jar" ]
