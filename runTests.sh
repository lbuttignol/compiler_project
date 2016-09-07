#!/bin/bash

#
#                           ******************                           
#*****************************     test     ****************************************
#**                         ******************                                    **
#**                                                                               **
#**Descripcion : Script que corre los casos de prueba definidos en la carpeta     **
#**              tests. cada test tiene su propio main.                          **
#**                                                                               **
#***********************************************************************************   
echo "*********************************************************************************** "
echo "**                                                                               ** " 
echo "**                            Running test cases . . .                           ** "                
echo "**                                                                               ** "
echo "**                                                                               ** "
echo "*********************************************************************************** "
echo "     " 
echo "     " 

comp="dist/Compiler.jar"        #nombre del jar.. 
main="src/Compiler/Main.java"   #noombre clase main

echo "//////////////////////////// SUCCESSFUL TESTS //////////////////////////////////////" 
files=`ls test/successful/*.ctds`


for file in $files ; do 
	echo "---------------- Test $file ... -------------------" 
        java -classpath $comp $main $file 
	echo "---------------------------------------------------" 
        echo "  "
        echo "  "
done

echo "////////////////////////////// FAILED TESTS ////////////////////////////////////////" 

files=`ls test/failed/*.ctds`

for file in $files ; do 
	echo "---------------- Test $file ... -------------------" 
        java -classpath $comp $main $file  
	echo "---------------------------------------------------" 
        echo "  "
        echo "  "
done

exit 0

