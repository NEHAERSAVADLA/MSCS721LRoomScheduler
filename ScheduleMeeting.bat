::author: Neha Ersavadla

@echo off

::calling addRooms.bat
call addRooms.bat

::Declaring variable
 set /a "x = 1"
::Scheduling Meetings
:while1
    if %x% leq 15 (
	echo 4
    echo room%x%	
	echo 2018-05-%x%
	echo 09:%x%
	echo 2018-05-%x%
	echo %x%:00
	echo subject%x%
	set /a "x = x + 1"	
	goto :while1
    )
::List of scheduled rooms
set /a "x = 1"
:while2
    if %x% leq 3 (
	echo 3
	echo room%x%
	
	set /a "x = x + 1"	
		goto :while2
    )
	endlocal
	