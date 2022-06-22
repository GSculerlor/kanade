FROM openjdk:11
EXPOSE 5002:5002
WORKDIR /
COPY kanade.jar kanade.jar
CMD java -jar kanade.jar