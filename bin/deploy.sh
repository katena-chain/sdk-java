#!/usr/bin/env bash

main () {
  echo "Clean project"
  mvn clean

  echo "Compile project"
  mvn compile

  echo "Build project JAR"
  mvn package

  echo "Verify project signature"
  mvn verify

  echo "Install to local maven repo"
  mvn install

  echo "Deploy to Sonatype"
  mvn deploy
}

main