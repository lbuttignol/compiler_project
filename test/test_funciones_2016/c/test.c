/* Contiene los casos de test para el compilador C-TDS (version C)
 * Necesita el archivo libtestCTDS.c
 * */

int get_int();
void print_int(int x);
void print_string(int s);

// retorna el factorial de v
int factorial (int v){
  int limit;
  limit = 15;
  if ( v > limit) { return -1;}
  { 
	int c, fact;
	c = 0;
	fact = 1;
    while (c<v){
       c = c+1;
       fact = fact*c;
    }
    return fact;
  }
}

// retorna el factorial de v
int factorialFor (int v){
  int limit;
  limit = 15;
  if ( v > limit) { return -1;}
  { 
	int c, fact;
	c = 0;
	fact = 1;
    while (c<v){
       c = c+1;
       fact = fact*c;
    }
    return fact;
  }
} 


// Calcula el factorial de 0 a 14 y retorna el factorial de v
int factorialArray (int v){
  int limit;
  limit = 15;
  
  { int i;
    int arr_fact[15];
    i=0;
    while (i<limit){
       int c, fact;
       c = 0;
       fact = 1;
       while (c<i){
          c = c+1;
          fact = fact*c;
       }
       arr_fact[i]=fact;  
       i=i+1;
    }
    if ( v > limit-1) return -1; 
    else return arr_fact[v];
  }
}

// retorna el n esimo primo
int nthprime (int n) {
    int i;
    int divs;
    i = 0;
    divs = 2;
    n = n +1;
    while ( n > 0) {
      int divides;
      divides = 0;
      i = i + 1;
      while ( ! divides && divs < i) {
       if ( i % divs == 0)
         { divides = 1; }
       else
         { divs = divs + 1; }
      }
      divs = 2;
      if ( ! divides)
        { n = n - 1; }
    }
    return i;
}

// calcula los primeros 100 primos y retorna el n esimo
int nthprimeArray (int n) {

  int j, arr[100];
    j = 0;
    
  while (j<100){
    arr[j]= nthprime(j); 
    j=j+1;
  } 
  return arr[n-1];
}

// retorna la representacion de d en binario
int int2bin(int d){
   int acum, aux, i;
   acum=0;
   i=0;

   while (1)
      if(d>1){               
        aux = d % 2;   // toma el ultimo digito
        { int j;
          j=0;   
                            
          while (1){    // ubica el digito en la posicion que le corresponde
             if (j<i){
               aux = aux * 10;  
               j=j+1;
               continue;  
             } 
             else { break; }
          }
         }     			

          acum=acum +aux;  // resultado parcial 
          i=i+1;           
          d=d/2;           // toma el resto del numero decimal
          continue;  
       }
       else { break; }

       { int j;
         j=0;
                            
         while (1){         // ubica el ultimo digito en la posicion que corresponde
            if (j<i){
              d = d * 10;
              j=j+1;
              continue;  
            } 
            else { break; }
         }   	
	   }
	return acum + d;  // resultaod final y retorna
}

// retorna el maximo comun divisor de a y b,
int gcd (int a, int b) {
	int i = 1;
	int result = i;
	while ( i < (a+b)) {
		if (((a % i) == 0) &&  ((b%i) ==0 ))
			{ result = i; }
		i = i + 1;
	}
	return result;
}

/* Calcula el valor del real m elevado a la n*/
int potencia(int m, int n){
	int i;
	int acum;
	acum=1;
	i=1;
	while (1){
		if(i<n || i==n){
			acum=acum * m;
			i=i+1;
		}
		else { break;}
	} 
	return(acum);  
}

// invoca varias funciones
void test(){
    int aux;
    aux = 2; 
    print_int(gcd(factorial(3),factorial(4)));
    print_int(nthprimeArray(gcd(factorial(3),factorial(4))));
    
    aux = potencia(aux,nthprimeArray(gcd(factorial(3),factorial(4))));
    print_int(aux);
}
// invoca test
void test1(){
    int aux;
    aux = 2; 
    test();
	print_int(aux);
}

// funcion main 
int main (){
       
        int x, i; 
	    init_input(1); 

// test factorial entero    
        print_string(2);    
        x=get_int(); // lee la cantidad de veces que ejecutara la funcion  
        i = 0;        
        while (i<x){              
            int aux;         
            aux=get_int(); // lee los datos para invocar a la funcion
            aux = factorial(aux);
            print_int(aux);             
            i++;
	    }
		print_string(1);
		
// test factorial array entero    
        print_string(4);    
        x=get_int(); // lee la cantidad de veces que ejecutara la funcion  
        i = 0;        
        while (i<x){              
            int aux;         
            aux=get_int(); // lee los datos para invocar a la funcion
            aux = factorialArray(aux);
            print_int(aux);             
            i++;
	    }
		print_string(1);

// test nthprime entero    
        print_string(5);    
        x=get_int(); // lee la cantidad de veces que ejecutara la funcion  
        i = 0;        
        while (i<x){              
            int aux;         
            aux=get_int(); // lee los datos para invocar a la funcion
            aux = nthprime(aux);
            print_int(aux);             
            i++;
	    }
		print_string(1);

// test nthprime array entero    
        print_string(6);    
        x=get_int(); // lee la cantidad de veces que ejecutara la funcion  
        i = 0;        
        while (i<x){              
            int aux;         
            aux=get_int(); // lee los datos para invocar a la funcion
            aux = nthprimeArray(aux);
            print_int(aux);             
            i++;
	    }
		print_string(1);

// test int2bin entero    
        print_string(7);    
        x=get_int(); // lee la cantidad de veces que ejecutara la funcion  
        i = 0;        
        while (i<x){              
            int aux;         
            aux=get_int(); // lee los datos para invocar a la funcion
            aux = int2bin(aux);
            print_int(aux);             
            i++;
	    }
		print_string(1);

// test gcd entero    
        print_string(8);    
        x=get_int(); // lee la cantidad de veces que ejecutara la funcion  
        i = 0;        
        while (i<x){              
            int aux;// lee los datos para invocar a la funcion
            aux = gcd(get_int(),get_int());
            print_int(aux);             
            i++;
	    }
		print_string(1);

// test test    
        print_string(9);    
        test();
		print_string(1);


// test test1    
        print_string(10);    
        test1();
		print_string(1);



        close_input();
        return 1;   
}
