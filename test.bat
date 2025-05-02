@echo off
echo Compiling Video Game Collection Application...

REM Compile the application
javac videogameCollection/*.java
javac VideoGameCollectionTest.java

echo Running Video Game Collection Tests...
java VideoGameCollectionTest

echo Done!
pause
