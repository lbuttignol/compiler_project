#!/bin/bash

comp="dist/Compiler.jar" # Jar name 
printlib="lib/printLib.c" # lib  name
srcfile="$1"	
execut="pepito" # runeable file

java -jar $comp $srcfile
gcc $printlib "${srcfile%.ctds}.s" -o $execut
clear 
./$execut
exit 0