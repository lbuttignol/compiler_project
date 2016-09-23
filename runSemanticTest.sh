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

comp="dist/Compiler.jar" #nombre del jar.. 


echo "//////////////////////////// SUCCESSFUL TESTS //////////////////////////////////////" 
files=`ls test/semantic/successful/*.ctds`


for file in $files ; do 
        echo "---------------- Test $file ... -------------------" 
        java -jar $comp $file 
        echo "---------------------------------------------------" 
        echo "  "
        echo "  "
done

echo "////////////////////////////// FAILED TESTS ////////////////////////////////////////" 

files=`ls test/semantic/failed/*.ctds`

for file in $files ; do 
        echo "---------------- Test $file ... -------------------" 
        java -jar $comp $file  
        echo "---------------------------------------------------" 
        echo "  "
        echo "  "
done

exit 0
