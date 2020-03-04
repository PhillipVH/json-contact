#!/bin/bash
cp "$1/*" src/main/resources/contacts/
mvn clean -q
mvn test -q
mvn jacoco:report
