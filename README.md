# TDP-Proyecto2
Algunas desiciones de diseño que tome en el proyecto:
 	 con respecto a la logica del juego:
   	 	-Consideré usar una lista  de celdas (incumplenReglas) para identificar las celdas que estan con un valor
   	 	que se repita en la misma fila/columna/cuadrante. A la lista tiene acceso la gui mediante un getter y de 
   		 esta forma la GUI decide como mostrarlas (y si se desea). Esta decision me simplifica el control de si se 
    		gano la partida ya que dicho control consta solo de verificar que no haya celdas en la lista y que todas 
    		las celdas tengan valor (distinto a cero).
    		Tambien se utiliza una lista para retornar las celdas que estaban repetidas y ya no se encuentran erroneas 
    		al modificar el valor de una celda (se retorna en el metodo Modificar del juego), esto fue una eleccion que hice
		para no recorrer todo el tablero desde la GUI o tener que utilizar listas auxiliares para recordar cuales estaban mal.
    		-Para identificar a que cuadrante pertenecen las celdas utilizo un arreglo de listas de celdas en la clase 
    		tablero en el que en cada lista se guardan las 9 celdas de un determinado cuadrante. El indice de dicho 
   		cuadrante en el arreglo esta dado por:
      		 0 0 0 1 1 1 2 2 2
     		 0 0 0 1 1 1 2 2 2
     		 0 0 0 1 1 1 2 2 2
     		 3 3 3 4 4 4 5 5 5
      		 3 3 3 4 4 4 5 5 5
     		 3 3 3 4 4 4 5 5 5
      		 6 6 6 7 7 7 8 8 8
     		 6 6 6 7 7 7 8 8 8
      		 6 6 6 7 7 7 8 8 8
   		 al iniciar el tablero se realiza cuadrante por cuadrante para setear el indice de el cuadrante a la celda.
   		 -El control de si la solución del archivo es valida se realiza de forma totalmente independiente a si una vez
    		jugando se consulta si se gano el juego.
		-use multiples constructores del juego por que no sabia como se utilizaria la interfaz y de esta forma al agregar
		posible futura funcionalidad (como inicializar el tablero con un archivo solucion aleatorio) se pueda realizar sin 
		modificar el juego
 	con respecto a la GUI
  		  - Consideré que el manejo del temporizador es algo de lo que se encarga la interfaz (y no el Juego) por que
    		preferia no agregar mas dependecias entre la GUI y el juego y que sea una funcionalidad de la que se encarge
     		una unica clase. Pero soy consciente de que hubiera sido mas correcto que el tiempo transcurrido se le
		pida al juego para facilitar el agregado de posibles futuras versiones del juego (que se pida por ejemplo, que solo
		gana si se consigue solucionar el sudoku en un tiempo determinado)
		 - Puede que en ciertas ocasiones se de que una partida en dificil sea mas facil que una en facil o normal, esto se 
		da por que en ningun momento se controla que haya una solucion unica. (la dificultad no es estrictamente 
		proporcional a la cantidad de celdas reveladas). Sin embargo la mayoria de la veces sí que se va a respetar la dificultad
		(se busco una cantidad de celdas adecuada para que esto ocurra)
    
