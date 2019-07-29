## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)

## General info
This project is test automation framework for REST-API and GUI automation testing.
All required test tasks (create user, update all user, delete user) are stored in
RestTest class(package: test/java/rest). Also I have spare time to setup basis for
GUI auto tests. GUI tests are stored in test/java/ui package (there is only one login
test case just as a proof concept). GUI test cases can be run on chrome or firefox.
Pleasse update your credentials if it is necessery in main/resources/properties.yml 
file to obtain valid session token.

## Technologies
Machine on which framework is made:
* Windows10 64bit
* InteliJ IDEA
* java version: 8
* maven versoin: 3.6.0

Project is created with libraries:
* testng version: 6.14.3
* selenium-java version: 3.141.59
* selenium-chrome-driver version: 3.141.59
* hamcrest-all version: 1.3
* rest-assured version: 4.0.0
* gson version: 2.8.5
* snakeyaml version: 1.21

## Setup
To run this project, open it as maven project run test from testeng.xml file
(package/test/java/resources) or steps to run project from command line:
```
$ mvn clean compile
$ mvn clean install
```

Note: If you have difficulties load libraries:
* Check if mvn auto import is allowed
* Still have problem in InteliJ - click on File/Invalidate Caches/Restart and
rerun maven commands fom above.
* Final solution is to run mvn clean install -U to force update from external libraries
