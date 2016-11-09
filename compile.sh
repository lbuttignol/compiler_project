#!/bin/bash

comp="dist/Compiler.jar" # Jar name 
printlib="lib/printLib.c" # lib  name
srcfile="$1"	
execut="pepito" # runeable file
if [ -z "$2" ]; then
	java -jar $comp $srcfile
	gcc $printlib "${srcfile%.ctds}.s" -o $execut
	#echo "${srcfile%.ctds}.s"
	./$execut
else
	java -jar $comp $srcfile $2  
	gcc $printlib "${srcfile%.ctds}.s" -o $execut
	./$execut 

fi
exit 0
