# TDP-Proyecto2
Algunas desiciones de diseño que tome en el proyecto:
  con respecto a la logica del juego:
    -Considere usar una lista  de celdas (incumplenReglas) para identificar las celdas que estan con un valor
    que se repita en la misma fila/columna/cuadrante. A la lista tiene acceso la gui mediante un getter y de 
    esta forma la GUI decide como mostrarlas (y si se desea). Esta decision me simplifica el control de si se 
    gano la partida ya que dicho control consta solo de verificar que no haya celdas en la lista y que todas 
    las celdas tengan valor (distinto a cero).
    Tambien se utiliza una lista para retornar las celdas que estaban repetidas y ya no se encuentran erroneas 
    al modificar el valor de una celda (se retorna en el metodo Modificar del juego)
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
    -El control de si la solucion del archivo es valida se realiza de forma totalmente independiente a si una vez
    jugando se consulta si se gano el juego.
  con respecto al manejo de la GUI
    - Considere que el manejo del temporizador es algo de lo que se encarga la interfaz (y no el Juego) por que
    preferia agregar mas dependecias entre la GUI y el juego
    
