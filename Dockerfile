# Use a imagem base com OpenJDK 21
FROM openjdk:21-jdk-slim

# Defina as variÃ¡veis de ambiente MAVEN_HOME e M2_HOME
ENV MAVEN_VERSION=3.9.1 \
    MAVEN_HOME=/opt/maven \
    M2_HOME=/opt/maven \
    FLYWAY_VERSION=9.16.0 \
    FLYWAY_HOME=/opt/flyway

# Instale o Maven, Flyway CLI e outras dependÃªncias necessÃ¡rias
RUN apt-get update && \
    apt-get install -y wget libfreetype6 libfontconfig1 && \
    # Instalar Maven
    wget -O apache-maven-$MAVEN_VERSION-bin.tar.gz https://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz && \
    tar -xzf apache-maven-$MAVEN_VERSION-bin.tar.gz -C /opt && \
    mv /opt/apache-maven-$MAVEN_VERSION /opt/maven && \
    ln -s /opt/maven/bin/mvn /usr/bin/mvn && \
    rm apache-maven-$MAVEN_VERSION-bin.tar.gz && \
    # Instalar Flyway CLI
    wget -O flyway-commandline-$FLYWAY_VERSION-linux-x64.tar.gz https://repo1.maven.org/maven2/org/flywaydb/flyway-commandline/$FLYWAY_VERSION/flyway-commandline-$FLYWAY_VERSION-linux-x64.tar.gz && \
    tar -xzf flyway-commandline-$FLYWAY_VERSION-linux-x64.tar.gz -C /opt && \
    mv /opt/flyway-$FLYWAY_VERSION $FLYWAY_HOME && \
    ln -s $FLYWAY_HOME/flyway /usr/local/bin/flyway && \
    rm flyway-commandline-$FLYWAY_VERSION-linux-x64.tar.gz

# Defina o diretÃ³rio de trabalho
WORKDIR /app

# Copie o arquivo pom.xml e o diretÃ³rio src para o contÃªiner
COPY pom.xml .
COPY src ./src

# Execute o Maven para construir a aplicaÃ§Ã£o
RUN mvn clean install -U -DskipTests

# Copie o arquivo WAR gerado para o diretÃ³rio apropriado
COPY target/fixyou-app-0.0.1-SNAPSHOT.war /app/fixyou-app-0.0.1-SNAPSHOT.war

# Copie o diretÃ³rio env para o contÃªiner
COPY env /app/env

# Defina o arquivo de configuraÃ§Ã£o das variÃ¡veis de ambiente
ENV APPLICATION_ENVIRONMENT=local
ENV ENV_FILE=docker-NBQFC-3S0L1M3
ENV ENV_PATH=/app/env

# Adiciona variÃ¡veis de ambiente do Keycloak
ENV KEYCLOAK_URL=http://keycloak:8080
ENV KEYCLOAK_REALM=fixyourealm

# Exponha as portas para o aplicativo e para a depuraÃ§Ã£o
EXPOSE 8080
EXPOSE 8081
EXPOSE 8082
EXPOSE 8083
EXPOSE 5005

# Comando para iniciar a aplicaÃ§Ã£o Spring Boot
CMD ["java", "-jar", "/app/fixyou-app-0.0.1-SNAPSHOT.war"]

