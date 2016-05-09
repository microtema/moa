# Message Oriented Architecture
Design/Implement a simple message broker that follows the Message Oriented Architecture to handle various functionality.

# Tasks
## Producer/Consumer
   Send and consume simple Message (Header-Properties, XML-Body as Payload)
## Broker
   We will use Wildfly 10.0.0 as Message Broker (http://wildfly.org/)
## Choreography
   Play the Message choreography with Camel Apache Router (http://camel.apache.org/message-router.html)
## We perform Test-Pyramid guidelines
* **Unit-Test with Junit-Framework** 
    (http://junit.org/junit4/)
* **Integration-Test with Arquillian-Framework** 
    (http://arquillian.org/)
* **Spock Framework** 
    (http://spockframework.github.io/spock/docs/1.0/index.html)

### REST API
Message

@GET        /rest/message Return all Messages order by Title
@POST       /rest/message/ @BODY{Message} will create new Message
@PUT        /rest/message/ @BODY{Message} will update existing Message
@DELETE     /rest/message/{id} will delete Message by given id



Building
-------------------

Ensure you have JDK 8 (or newer) installed

> java -version

Ensure you have MS SQL Server installed with DataBase "moa"

> jdbc:sqlserver://localhost:1433;DatabaseName=moa

Ensure you have Wildfly (10.0.0) Server installed

> http://wildfly.org/

Ensure you have activate messaging-activemq extension in standalone.xml config file

> <extension module="org.wildfly.extension.messaging-activemq"/>

If you already have Maven 3.2.3 (or newer) installed you can use it directly

Ensure you have install dependencies in local repository:

> de.seven.fate:model-builder
  (https://github.com/sevenfate/model-builder/tree/develop)
> de.seven.fate:model-converter
  (https://github.com/sevenfate/model-converter/tree/develop)

> mvn install

Ensure you have created input folder with read/write permission

> C:/data/input

After building war file and deployed in Wildfly standalone Server you can

Copy & Paste src/site/messages.xml into C:/data/input and watch logs.
Messages will be read and process and write to DataBase

### Development

* **mvn clean test**  ( run JUnit test)
* **mvn clean verify**  ( run JUnit and IT test together)
* **mvn clean install**  ( compile, verify, build and install to maven local repository)

We'll be building a simple but realistic Message Oriented Architecture, a basic version of the real time Messages orchestrated by Message Broker as Choreography and REST API for client.

We'll provide:

* A ORM of Messages
* A form to import Messages via Apache Camel Route
* A Topic JMS with MDB
* JPA Persistence Layer to persist messages int DB (MS SQL)
* Junit Test and IT
* CDD and TDD

It'll also have a few neat features:

* **Live loading:** Messages will be imported from FTP/Remote-Folder with Camel Route.
* **Data converting, validating and persisting:** Convert DTO to Entity
* **REST API:** CRUD action for Client.

### Running a server

In order to start this tutorial, we're going to require a running Wildfly server (standalone.xml). This will serve purely as an API endpoint which we'll use for importing *.xml files and serve through REST endpoint.
