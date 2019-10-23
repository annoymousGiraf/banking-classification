#!/usr/bin/env bash

docker-compose run build

docker-compose build release

docker run banking-classification:latest "sample1.csv" "ACC555666" "20/10/2018 12:00:00" "20/10/2019 12:00:00"