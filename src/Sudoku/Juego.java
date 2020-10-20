package Sudoku;

import java.io.*;
import java.util.LinkedList;
import java.util.Random;

/**
 * Clase que modela las partidas del juego.
 * 
 * @author Tadeo Villafaña
 *
 */
public class Juego {

	// ATRIBUTOS -----------------------------------------
	private static int reveladasXdefec = 25;
	private static String archivoSolXdefec = "sudoku.txt";

	/**
	 * Instancia del tablero que almacena las celdas de la partida
	 */
	private Tablero tablero;

	/**
	 * Atributo lista de celdas que incumplen reglas. Esto es aquellas que tengan un
	 * valor igual a otra celda de la misma fila, columna o cuadrante
	 */
	private LinkedList<Celda> incumplenReglas;

	// CONSTRUCTORES -------------------------------------

	/**
	 * Constructor sin parametros, utiliza el archivo por defecto y la cantidad de
	 * celdas reveladas por defecto
	 * 
	 * @throws InvalidFileFormat Cuando el archivo de texto de la solución no tiene
	 *                           el formato esperado o no es una solución valida
	 */
	public Juego() throws InvalidFileFormat {
		this(archivoSolXdefec, reveladasXdefec);
	}

	/**
	 * Constructor en el que se parametriza la cantidad de celdas pero no el archivo
	 * desde donde se leera la solución
	 * 
	 * @param reveladas Cantidad de celdas reveladas desde el inicio
	 * @throws InvalidFileFormat Cuando el archivo de texto de la solución no tiene
	 *                           el formato esperado o no es una solución valida
	 */
	public Juego(int reveladas) throws InvalidFileFormat {
		this(archivoSolXdefec, reveladas);
	}

	/**
	 * Constructor en el que se parametriza la ruta del archivo desde el que se
	 * extraera los valores iniciales. La cantidad de celdas reveladas será por
	 * defecto
	 * 
	 * @param archivo Ruta del archivo de texto con la solución
	 * @throws InvalidFileFormat Cuando el archivo de texto de la solución no tiene
	 *                           el formato esperado o no es una solución valida
	 */
	public Juego(String archivo) throws InvalidFileFormat {
		this(archivo, reveladasXdefec);
	}

	/**
	 * Constructor en el que se parametriza tanto el archivo de la solución como la
	 * cantidad de celdas a revelar inicialmente
	 * 
	 * @param archivoSolucion Ruta del archivo de texto con la solución
	 * @param reveladas       Cantidad de celdas reveladas desde el inicio
	 * @throws InvalidFileFormat Cuando el archivo de texto de la solución no tiene
	 *                           el formato esperado o no es una solución valida
	 */
	public Juego(String archivoSolucion, int reveladas) throws InvalidFileFormat {
		tablero = new Tablero();
		int matriz[][] = new int[9][9];
		try {

			String linea;
			
			InputStream in = Juego.class.getClassLoader().getResourceAsStream(archivoSolucion);
            InputStreamReader inr = new InputStreamReader(in);
            BufferedReader br =  new BufferedReader(inr);
            
			for (int i = 0; i < 9; i++) {
				linea = br.readLine();
				if (linea == null) {
					br.close();
					throw new InvalidFileFormat("Error, el archivo de la solucion tiene menos de 9 filas");
				}
				if (linea.length() < 17) {
					br.close();
					throw new InvalidFileFormat(
							"Error, el archivo de la solucion tiene filas con menos de 9 elementos");
				}
				for (int j = 0; j < 9; j++) {
					// se lee el archivo y se guarda en la matriz
					char caracterLeido = linea.charAt(j * 2);
					// al multiblicar por dos se saltea los espacios entre las columnas
					matriz[i][j] = Character.getNumericValue(caracterLeido);
				}
			}
			br.close();

			if (!solucionValida(matriz)) // se delega a un metodo auxiliar el chequeo de si es correcta la solucion
				throw new InvalidFileFormat("Error, la solucion del archivo no es valida");

			int celdasReveladas[];
			if (reveladas < 81 && reveladas > 0)
				celdasReveladas = new int[reveladas];
			else
				celdasReveladas = new int[reveladasXdefec];
			// si la cantidad de celdas a revelar no es valida, se revela la cantidad por
			// defecto (atributo constante)

			celdasAleatorias(celdasReveladas);

			int numCelda, fila, col;
			for (int i = 0; i < celdasReveladas.length; i++) {
				numCelda = celdasReveladas[i];
				// se obtiene la fila y la columna de la celda correspondiente al numero
				// aleatorio
				fila = numCelda / 9;
				col = numCelda % 9;
				Celda celda = tablero.getCelda(fila, col);
				celda.setValor(matriz[fila][col]);
				celda.desabilitar();
			}

			incumplenReglas = new LinkedList<Celda>();
			// En principio, no hay celdas erroneas (ya se chequeó la solucion)

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// METODOS ------------------------------------------

	/**
	 * Getter del Tablero que se mantiene como atributo del juego. este metodo es
	 * necesario para que los clientes de Juego puedan ver los valores de las celdas
	 * y cuales son editables
	 * 
	 * @return tablero Instancia del tablero en la que se desarrolla el juego
	 */
	public Tablero getTablero() {
		return tablero;
	}

	/**
	 * Método auxiliar que llena el arreglo parametrizado de valores aleatorios
	 * entre 0 y 80 para que se pueda inicializar posteriormente el tablero con las
	 * celdas correspondientes parametrizadas
	 * 
	 * @param arreglo Arreglo de enteros vacio en el que se guardara los valores, se
	 *                asume que tiene una longitud menor a 81
	 */
	private void celdasAleatorias(int[] arreglo) {
		Random r = new Random();
		int cantidad = arreglo.length;
		for (int i = 0; i < cantidad; i++) {
			arreglo[i] = r.nextInt(81);
			for (int j = 0; j < i; j++) {
				if (arreglo[i] == arreglo[j])
					i--;
			}
		}
	}

	/**
	 * Método que verifica si es valida la solución del archivo. Esto es, que no se
	 * repitan valores de celdas en las filas, columnas y cuadrantes. se debe llamar
	 * luego de guardar dicha solucion en la matriz parametrizada.
	 * 
	 * @param matriz Matriz en la que se almacena los valores de la solución
	 * @return Verdadero si la solución es valida. Falso caso contrario
	 */
	private boolean solucionValida(int matriz[][]) {
		return filasValidas(matriz) && columnasValidas(matriz) && cuadrantesValidos(matriz);
	}

	/**
	 * Método que verifica que en cada cuadrante de la solución parametrizada no
	 * haya valores repetidos o invalidos
	 * 
	 * @param matriz Matriz donde se almacena la solución
	 * @return Verdadero si son validos. Falso caso contrario
	 */
	private boolean cuadrantesValidos(int[][] matriz) {
		boolean sonValidos = true;
		for (int i = 0; i < 3 && sonValidos; i++)
			for (int j = 0; j < 3 && sonValidos; j++)
				sonValidos = cuadranteValido(matriz, i, j);
		return sonValidos;
	}

	/**
	 * Método que verifica que un cuadrante sea valido. Es decir, que no se repita
	 * ningún valor y los valores sean validos
	 * 
	 * @param matriz Matriz donde se almacena la solución
	 * @param i      valor que indica en que filas se encuentra el cuadrante. debe
	 *               ser 0, 1 o 2
	 * @param j      valor que indica en que columnas se encuentra el cuadrante.
	 *               debe ser 0, 1 o 2
	 * @return Verdadero si el cuadrante es valido.Falso caso contrario
	 */
	private boolean cuadranteValido(int[][] matriz, int i, int j) {
		boolean esValido = true;
		int valor;
		LinkedList<Integer> yaEncontrados = new LinkedList<Integer>();
		for (int fila = i * 3; fila < i * 3 + 3 && esValido; fila++)
			for (int col = j * 3; col < j * 3 + 3 && esValido; col++) {
				valor = matriz[fila][col];
				if (valor > 9 || valor < 1 || yaEncontrados.contains(valor))
					esValido = false;
				else
					yaEncontrados.add(valor);
			}
		return esValido;
	}

	/**
	 * Método que verifica que en cada columna de la solución parametrizada no haya
	 * valores repetidos o invalidos
	 * 
	 * @param matriz Matriz donde se almacena la solución
	 * @return Verdadero si son validas. Falso caso contrario
	 */
	private boolean columnasValidas(int[][] matriz) {
		boolean sonValidas = true;
		LinkedList<Integer> yaEncontrados;
		Integer valor;
		for (int j = 0; j < 9 && sonValidas; j++) {
			yaEncontrados = new LinkedList<Integer>();
			for (int i = 0; i < 9 && sonValidas; i++) {
				valor = matriz[i][j];
				if (valor > 9 || valor < 1 || yaEncontrados.contains(valor))
					sonValidas = false;
				else
					yaEncontrados.add(valor);
			}
		}
		return sonValidas;
	}

	/**
	 * Método que verifica que en cada fila de la solución parametrizada no haya
	 * valores repetidos o invalidos
	 * 
	 * @param matriz Matriz donde se almacena la solución
	 * @return Verdadero si son validas. Falso caso contrario
	 */
	private boolean filasValidas(int[][] matriz) {
		boolean sonValidas = true;
		LinkedList<Integer> yaEncontrados;
		Integer valor;
		for (int i = 0; i < 9 && sonValidas; i++) {
			yaEncontrados = new LinkedList<Integer>();
			for (int j = 0; j < 9 && sonValidas; j++) {
				valor = matriz[i][j];
				if (valor > 9 || valor < 1 || yaEncontrados.contains(valor))
					sonValidas = false;
				else
					yaEncontrados.add(valor);
			}
		}
		return sonValidas;
	}

	/**
	 * Método que permite modificar el valor de la celda correspondiente a la fila y
	 * columna parametrizada cuando el valor es valido. Tambien se encarga de
	 * actualizar la lista de celdas incumpleReglas.
	 * 
	 * @param valor Nuevo valor de la celda a modificar
	 * @param fila  Fila de la celda a modificar
	 * @param col   Columna de la celda a modificar
	 * @return Lista de celdas que antes eran erroneas pero al introducir el nuevo
	 *         valor dejaron de serlo
	 */
	public LinkedList<Celda> modificar(int valor, int fila, int col) {
		LinkedList<Celda> yaNoErroneas = new LinkedList<Celda>();
		if ((valor < 10 && valor > 0) && (fila > -1 && fila < 9) && (col > -1 && col < 9)) {
			Celda c = tablero.getCelda(fila, col);
			if (c.esEditable()) {
				int valorAnterior = c.getValor();
				c.setValor(valor);
				if (valorAnterior != valor) {
					actualizarColumna(c, valorAnterior, yaNoErroneas);
					actualizarFila(c, valorAnterior, yaNoErroneas);
					actualizarCuadrante(c, valorAnterior, yaNoErroneas);
				}
				if (incumplenReglas.contains(c) && !sigueEstandoMal(c)) {
					incumplenReglas.remove(c);
					yaNoErroneas.add(c);
				}

			}
		}
		return yaNoErroneas;
	}

	/**
	 * Método auxiliar que actualiza el cuadrante de la celda modificada
	 * (parametrizada) verificando si hay nuevas celdas erroneas y/o celdas erroneas
	 * que ahora dejaron de estarlo
	 * 
	 * @param c             Celda que fue modificada
	 * @param valorAnterior Valor que contenia la celda parametrizada antes de
	 *                      modificarla
	 * @param yaNoErroneas  Lista en la que se almacenará las celdas que dejaron de
	 *                      estar mal
	 */
	private void actualizarCuadrante(Celda c, int valorAnterior, LinkedList<Celda> yaNoErroneas) {
		LinkedList<Celda> cuadrante = tablero.getCuadrante(c.getCuadrante());
		int valor = c.getValor();
		for (Celda otra : cuadrante) {
			if (otra != c) {
				if (otra.getValor() == valorAnterior)
					if (!sigueEstandoMal(otra)) {
						incumplenReglas.remove(otra);
						yaNoErroneas.add(otra);
					}
				if (otra.getValor() == valor) {
					if (!incumplenReglas.contains(otra))
						incumplenReglas.add(otra);
					if (!incumplenReglas.contains(c))
						incumplenReglas.add(c);
				}
			}
		}

	}

	/**
	 * Método auxiliar que actualiza la columna de la celda modificada
	 * (parametrizada) verificando si hay nuevas celdas erroneas y/o celdas erroneas
	 * que ahora dejaron de estarlo
	 * 
	 * @param c             Celda que fue modificada
	 * @param valorAnterior Valor que contenia la celda parametrizada antes de
	 *                      modificarla
	 * @param yaNoErroneas  Lista en la que se almacenará las celdas que dejaron de
	 *                      estar mal
	 */
	private void actualizarColumna(Celda c, int valorAnterior, LinkedList<Celda> yaNoErroneas) {
		Celda otra;
		int col = c.getColumna();
		int valor = c.getValor();
		for (int i = 0; i < 9; i++) {
			otra = tablero.getCelda(i, col);
			if (otra != c) {
				if (otra.getValor() == valorAnterior)
					if (!sigueEstandoMal(otra)) {
						incumplenReglas.remove(otra);
						yaNoErroneas.add(otra);
					}
				if (otra.getValor() == valor) {
					if (!incumplenReglas.contains(otra))
						incumplenReglas.add(otra);
					if (!incumplenReglas.contains(c))
						incumplenReglas.add(c);
				}
			}
		}
	}

	/**
	 * Método auxiliar que actualiza la fila de la celda modificada (parametrizada)
	 * verificando si hay nuevas celdas erroneas y/o celdas erroneas que ahora
	 * dejaron de estarlo
	 * 
	 * @param c             Celda que fue modificada
	 * @param valorAnterior Valor que contenia la celda parametrizada antes de
	 *                      modificarla
	 * @param yaNoErroneas  Lista en la que se almacenará las celdas que dejaron de
	 *                      estar mal
	 */
	private void actualizarFila(Celda c, int valorAnterior, LinkedList<Celda> yaNoErroneas) {
		Celda otra;
		int fila = c.getFila();
		int valor = c.getValor();
		for (int j = 0; j < 9; j++) {
			otra = tablero.getCelda(fila, j);
			if (otra != c) {
				if (otra.getValor() == valorAnterior)
					if (!sigueEstandoMal(otra)) {
						incumplenReglas.remove(otra);
						yaNoErroneas.add(otra);
					}
				if (otra.getValor() == valor) {
					if (!incumplenReglas.contains(otra))
						incumplenReglas.add(otra);
					if (!incumplenReglas.contains(c))
						incumplenReglas.add(c);
				}
			}
		}
	}

	/**
	 * Método que verifica si sigue estando mal la celda parametrizada. Esto es, si
	 * hay otra celda del mismo cuadrante/fila/columna que comparta el valor de la
	 * misma.
	 * 
	 * @param celda Celda a verificar si sigue estando con un valor repetido en su
	 *              fila columna o cuadrante
	 * @return Verdadero si existe otra celda en la misma fila/columna/cuadrante que
	 *         comparte valor con la parametrizada. Falso en caso contrario
	 */
	private boolean sigueEstandoMal(Celda celda) {
		return seRepiteEnFila(celda) || seRepiteEnColumna(celda) || seRepiteEnCuadrante(celda);
	}

	/**
	 * Método auxiliar que verifica si hay alguna celda que tenga el mismo valor que
	 * la celda parametrizada y se encuentre en el mismo cuadrante
	 * 
	 * @param celda Celda a chequear
	 * @return Verdadero si se repite el valor de la celda en su cuadrante. Falso en
	 *         caso contrario
	 */
	private boolean seRepiteEnCuadrante(Celda celda) {
		LinkedList<Celda> cuadrante = tablero.getCuadrante(celda.getCuadrante());
		boolean encontre = false;
		int valor = celda.getValor();
		for (Celda otra : cuadrante) {
			if (otra != celda)
				if (otra.getValor() == valor)
					encontre = true;
		}
		return encontre;
	}

	/**
	 * Método auxiliar que verifica si hay alguna celda que tenga el mismo valor que
	 * la celda parametrizada y se encuentre en la misma columna
	 * 
	 * @param celda Celda a chequear
	 * @return Verdadero si se repite el valor de la celda en su columna. Falso en
	 *         caso contrario
	 */
	private boolean seRepiteEnColumna(Celda celda) {
		int col = celda.getColumna();
		int valor = celda.getValor();
		boolean encontre = false;
		Celda otra;
		for (int i = 0; i < 9 && !encontre; i++) {
			otra = tablero.getCelda(i, col);
			if (otra != celda)
				encontre = otra.getValor() == valor;
		}
		return encontre;
	}

	/**
	 * Método auxiliar que verifica si hay alguna celda que tenga el mismo valor que
	 * la celda parametrizada y se encuentre en la misma fila
	 * 
	 * @param celda Celda a chequear
	 * @return Verdadero si se repite el valor de la celda en su fila. Falso en caso
	 *         contrario
	 */
	private boolean seRepiteEnFila(Celda celda) {
		int fila = celda.getFila();
		int valor = celda.getValor();
		boolean encontre = false;
		Celda otra;
		for (int j = 0; j < 9 && !encontre; j++) {
			otra = tablero.getCelda(fila, j);
			if (otra != celda)
				encontre = otra.getValor() == valor;
		}
		return encontre;
	}

	/**
	 * Método que verifica si en el estado actual de la partida se considera que
	 * ganó
	 * 
	 * @return Verdadero si en el tablero se encuentra una solucion valida. Falso en
	 *         caso contrario
	 */
	public boolean gano() {
		// si se verifica que no haya celdas con valores repetidos (en la lista) y todas
		// las celdas se inicializaron con algun valor entonces se gano el juego.
		return incumplenReglas.isEmpty() && todasConValor();
	}

	/**
	 * Método auxiliar que verifica que todas las celdas tienen un valor valido
	 * (distinto a cero)
	 * 
	 * @return Verdadero si todos los valores son distintos a cero. Falso si alguna
	 *         celda tiene valor cero
	 */
	private boolean todasConValor() {
		boolean encontreCero = false;
		for (int i = 0; i < 9 && !encontreCero; i++)
			for (int j = 0; j < 9 && !encontreCero; j++)
				encontreCero = tablero.getCelda(i, j).getValor() == 0;
		return !encontreCero;
	}

	/**
	 * Getter de la lista de celdas con valores repetidos
	 * 
	 * @return Lista de celdas que incumplen alguna regla (se repite su valor en la
	 *         fila/columna/cuadrante)
	 */
	public LinkedList<Celda> listaErroneas() {
		return incumplenReglas;
	}

	/**
	 * toString devuelve un String con los valores actuales del tablero en forma de
	 * matriz (una linea por cada fila y columnas separadas por espacios)
	 */
	@Override
	public String toString() {
		return tablero.toString();
	}
}
