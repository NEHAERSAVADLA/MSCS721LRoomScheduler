::author: Neha Ersavadla

@echo off

call samplefile.json

::Importing the data
echo 6
for /F "tokens=*" %%i in (samplefile.json) do 
echo %%i

:: Scheduling the added room
echo 3
echo room5
echo 2018-05-08
echo 10:43
echo 2018-05-08
echo 11:23
echo subject
echo Meeting

:: Exporting the room details
echo 7
echo /json