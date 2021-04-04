# BeeHRM

## Introduction

This is an effort to create a Human Resource Management solution that shall be suitable for the Malaysian workplace environment but keeping the HR parameters settings as configurable as possible.

## About this repository

This repository is an Eclipse project, so you need an Eclipse IDE to clone the project.  

## Database

Database configuration parameters are declared in the dbconnection.properties file.  This project support any database.  I have created a sample connections for H2, PostgreSQL and MySQL/MariaDB in the dbconnection.properties.copy file.

H2 Database.

```
driver.default=org.h2.Driver
dialect.default=org.hibernate.dialect.H2Dialect
user.default=sa
password.default=
url.default=jdbc:h2:file:~/h2db/beehrm
```

PostgreSQL Database

```
driver.default=org.postgresql.Driver
dialect.default=org.hibernate.dialect.PostgreSQLDialect
user.default=postgres
password.default=sbahrin1234
url.default=jdbc:postgresql://localhost:5432/mydb
```

MySQL/MariaDB Database.

```
driver.default=com.mysql.cj.jdbc.Driver
dialect.default=org.hibernate.dialect.MySQL5InnoDBDialect
user.default=root
password.default=sbahrin1234
url.default=jdbc:mysql://localhost:3307/beehrm
```

## Setting the database schema

This project is using the Hibernate JPA libraries, therefore, the database tables schema will be created automatically.  If you are using database other than H2, you only need to create the empty database first.  

Then you need to run a program InitializeSetupDatabase.java by right click on the java file and select Run As -> Java Application.  This program shall also initialize the database tables with preliminary data.

## Running the application

Your Eclipse IDE need to have the Tomcat Server configured.  You can run this application by right click on the project, and select Run As -> Run On Server.

## About Me

Hello! My name is Shamsul Bahrin and I am a Software Developer.
