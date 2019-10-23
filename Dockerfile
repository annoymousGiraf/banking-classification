FROM openjdk:8

RUN mkdir -p /root/app

WORKDIR /root/app

COPY build/libs/* /root/app

COPY samples/* /root/app

ENTRYPOINT ["java","-jar","banking-classification-1.0.jar"]