#include <stdio.h>


int print_int(int val){
	printf("%d\n", val);
	return 0;
}

int print_float(float val){
	printf("%f\n", val);
	return 0;
}


int print_bool(int val){
	if(val == 0){
		printf("%s\n","true" );
	}else{
		printf("%s\n", "false");
	}
}

