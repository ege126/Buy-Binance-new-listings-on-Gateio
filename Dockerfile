FROM openjdk:17
RUN mkdir /app
COPY ./src/main/java/ /app
WORKDIR /app
CMD java StrategyExecution
