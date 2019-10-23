#!/usr/bin/env bash

cd "$(dirname "$0")"
args=("$@")


if [[ ${#args[@]} != 3 ]]
then
    echo 'usage: <account_id> <start_date> <end_date>'
    exit 1
fi

java -jar "app/banking-classification-1.0.jar" "app/sample1.csv" "$1" "$2" "$3"
