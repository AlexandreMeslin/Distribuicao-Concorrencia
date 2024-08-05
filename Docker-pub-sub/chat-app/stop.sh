#!/bin/bash

sudo docker ps -a

sudo docker stop chat-app_consumer-service_1
sudo docker stop chat-app_producer-service_1
sudo docker stop chat-app_kafka_1
sudo docker stop chat-app_zookeeper_1

sudo docker rm chat-app_consumer-service_1
sudo docker rm chat-app_producer-service_1
sudo docker rm chat-app_kafka_1
sudo docker rm chat-app_zookeeper_1

sudo docker ps -a
