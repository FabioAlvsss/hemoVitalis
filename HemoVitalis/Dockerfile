FROM ibm-semeru-runtimes:open-17-jdk
VOLUME /tmp
EXPOSE 8080
ADD ./target/HemoVitalis-0.0.1-SNAPSHOT.jar hemovitalis.jar
ENTRYPOINT ["java","-jar","/hemovitalis.jar"]