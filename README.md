# MyJots-JPA
JPA library for the MyJots services.

This JPA library is adopt a standalone architecture to be employed in a Java application without JavaEE techonology support.
This project is organized as Maven project and is initially developed with NetBeans 8 IDE.

There is a Main class to demonstrate that is a standalone project with JPA support.

## JPA Classes

JPA classes has been generated with NetBeans 8 'Entities from database tables" wizard setting this options:

- Generated NamedQuery annotations
- JAXB annotations
- Association fetch: Default (LAZY)
- Collection type: `java.util.List`
- Use columnname as relationship

## Underlying Database
Persistence unit is configured to connect in RESOURCE_LOCAL transactionmode to a MySQL database.
Database script to regenerate MySQL database schema is in `/MyJots-JPA/datbase-scripts/database-script.sql`.
Other database related statement for database initialization are in the same directory.

## Dependencies

- **Hibernate** for JPA implementation
- **JUnit** for tests framwork
- **MySQL connector** as database driver

## Produced Maven Artifact 

    <groupId>org.jolab</groupId>
    <artifactId>MyJots-JPA</artifactId>
    <version>0.1-SNAPSHOT</version>
    <packaging>jar</packaging>
    <name>MyJots-JPA</name>

# Design architecture

A brief introduction to the design main solutions.

## Controller
Controller classes has the role to interact direclty with JPA classes to read and modify data content.
Each database tabla has its Entity in the `org.jolab.myjots.jpa.model` and its controller in `org.jolab.myjots.jpa.controller.standalone`
Controller is intended to be tha old Data Access Object.

Each Dao has a constructor that need an EntityManager instance to execute its JPA operation and realize programmatically necessary transaction support.
In a JavaEE environment this could be managed from container.
