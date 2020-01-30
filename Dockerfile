FROM maven:3.6.3-jdk-8

# Copy the source code
RUN mkdir /usr/src/json-contact/
COPY pom.xml /usr/src/json-contact/pom.xml
COPY src /usr/src/json-contact/src

COPY json-contact.sh /usr/src/json-contact/

WORKDIR /usr/src/json-contact
RUN mvn compile

# Start a terminal
ENTRYPOINT bash
