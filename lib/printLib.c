#include <stdio.h>


int extern print_int(int* val){
	printf("%d\n", *val);
	return 0;
}

int extern print_float(float* val){
	printf("%f\n", *val);
	return 0;
}


void extern print_bool(int* val){
	if(*val == 0){
		printf("%s\n","true" );
	}else{
		printf("%s\n", "false");
	}
}

