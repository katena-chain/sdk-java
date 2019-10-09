#!/usr/bin/env bash

main () {
  echo "Clean project"
  mvn clean

  echo "Compile project"
  mvn compile

  echo "Build project JAR"
  mvn package
}

main