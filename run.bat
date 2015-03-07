echo off

SET mainClass=si.gto76.funphototime.FunPhotoTime
SET appName=fun-photo-time
SET scriptName=%~n0

echo %scriptName%: COMPILING...
mkdir target\main
dir /s /B *.java > sources.txt
javac -d target\main @sources.txt
del sources.txt
cd target

echo %scriptName%: PACKAGING IN JAR...
jar cvfe %appName%.jar %mainClass% -C ..\src\main\ resources -C main .

echo %scriptName%: EXECUTING JAR...
java -jar %appName%.jar

cd ..