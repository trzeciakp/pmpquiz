PMPQuiz
=======
Requirements
----------

1. Android SDK with build tools (19.0.3) and platform tools installed
2. XML with quiz questions

Build instruction
----------
Firstly place quiz_raw_all.xml in app/src/main/res/raw/
Project uses Gradle build tool. It is not necessary to install it, gradle wrapper is included.
To build project navigate to its directory and run:
gradlew.bat build (or ./gradlew build if you work on unix system)


Import into IntelliJ
----------
1. Clone project
2. Choose File -> Import project
3. Navigate to the project directory
4. Dialog is shown. Choose gradle 
5. On the next screen check 'Use default gradle wrapper' and click ok.


Import into Eclipse
----------
??? use Intellij :p