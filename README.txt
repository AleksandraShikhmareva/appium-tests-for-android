---------DESCRIPTION------------

This is an example of automated tests project for android using Appium + TestNG based on POM Pattern
The free mobile application Snapchat was used to demonstrate the development of tests.
Used logback instead of log4j to avoid using versions with unsafety vulnerabilities
Tests will run appium-server with appuim installed on your computer

------HOW TO START TESTS--------

Preconditions:
- Have appuim is installed
- Android SDK configured on your system
- Put capabilities for your device

Steps:

1) Import maven project to IDE
2) Add configuration for TestNG framework: Test kind - Suite, Suite - <PATH to file tests.xml>
3) Compile the project
4) Connect device to computer
5) Run tests

