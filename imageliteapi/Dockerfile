# Etapa de build usando Maven
FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app

# Copia o arquivo de configuração do Maven
COPY pom.xml ./

# Baixa as dependências do Maven
RUN mvn dependency:go-offline

# Copia o código-fonte para o contêiner
COPY src ./src

# Compila o código e gera o pacote (.jar)
RUN mvn clean package -DskipTests

# Etapa final usando OpenJDK
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copia o arquivo .jar gerado na etapa de build
COPY --from=builder /app/target/*.jar ./application.jar

# Expor a porta que a aplicação irá rodar
EXPOSE 8080

# Definir variáveis de ambiente
ENV POSTGRES_HOST=localhost

# Executa a aplicação
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=production", "application.jar"]
