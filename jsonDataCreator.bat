::author: Neha Ersavadla

:: Giving input using json files

@ECHO OFF

FOR /F "tokens=*" %%i IN (samplefile.json) DO @ECHO %%i
::Setting variables 
 set /a x =1
 set /a z=11
 set /a y=13
 set /a m=4
 set /a minutes=12
 set /a hours =12
 
 
 ::echo [
 :: Creating 1000 rooms  and scheduling meetings
:while1
    if %x% leq 1000 (

	 
	set /a "x = x + 1"
    set /a y = y+1	
	set /a z=z+1
	set /a minutes+=1
	set /a hours+=1	
	if %y% ==30 set y=10
	set /a m=m+1
	if %m% ==12 set m=1	
	if %z% ==30 set z=10
	if %minutes% ==60 set minutes=10
if %hours% ==24 set hours=10	
call :sub >1000roomsScheduling
     :sub
	
	goto :while1
	
    )
	
	endlocal )