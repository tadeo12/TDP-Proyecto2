 package Sudoku;

import java.util.LinkedList;

/**
 * Clase que modela un tablero de Sudoku
 * 
 * @author Tadeo Villafaña
 *
 */
public class Tablero {
	private Celda celdas[][];
	private LinkedList<Celda> cuadrantes[];

	@SuppressWarnings("unchecked")
	/**
	 * Constructor del tablero. En el mismo se inicializa todas las celdas con valor
	 * cero y la fila,columna y cuadrante correspondiente
	 */
	public Tablero() {
		cuadrantes = new LinkedList[9];
		for (int i = 0; i < 9; i++) {
			cuadrantes[i] = new LinkedList<Celda>();
		}
		celdas = new Celda[9][9];
		inicializarTablero();
	}

	/**
	 * Inicializa todas las celdas del tablero.
	 */
	private void inicializarTablero() {
		// se crea las celdas y se agregan a los cuadrantes en el orden:
		// 0 1 2
		// 3 4 5
		// 6 7 8
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				inicializarCuadrante(i * 3 + j, i, j);
			}
	}

	/**
	 * Método auxiliar que se utiliza para inicializar un cuadrante
	 * 
	 * @param numCuadrante valor entre 0 y 8 (inclusive) que identifica el cuadrante
	 *                     que se inicializa
	 * @param i            Valor que identifica las filas del cuadrante. Valor entre
	 *                     0 y 2 (inclusive)
	 * @param j            Valor que identifica las columnas del cuadrante. Valor
	 *                     entre 0 y 2 (inclusive)
	 */
	private void inicializarCuadrante(int numCuadrante, int i, int j) {
		for (int fila = i * 3; fila < i * 3 + 3; fila++)
			for (int col = j * 3; col < j * 3 + 3; col++) {
				celdas[fila][col] = new Celda(fila, col, numCuadrante, 0);
				cuadrantes[numCuadrante].add(celdas[fila][col]);
			}

	}

	/**
	 * Getter que a partir del numero de fila y columna devuelve la celda
	 * correspondiente
	 * 
	 * @param fila Entero que representa la fila de la celda pedida
	 * @param col  Entero que representa la columna de la celda pedida
	 * @return Celda pedida. si la fila o la columna parametrizada es invalida (no
	 *         esta entre 0 y 8) entonces retorna null
	 */
	public Celda getCelda(int fila, int col) {
		if (fila < 9 && fila > -1 && col < 9 && col > -1) {
			return celdas[fila][col];
		} else
			return null;
	}

	/**
	 * Getter del cuadrante que se encuentra en la posición parametrizada segun el
	 * orden de izquierda a derecha y de arriba a abajo. El cuadrante en si no es
	 * mas que una lista de las nueve celdas que contiene.
	 * 
	 * @param c Entero entre 0 y 8 (inclusive) que representa el cuadrante
	 * @return Lista de las celdas que se encuentran en el cuadrante parametrizado.
	 *         retorna null si el valor del cuadrante es invalido (menor a 0 o mayor
	 *         a 8)
	 */
	public LinkedList<Celda> getCuadrante(int c) {
		if (c > -1 && c < 9)
			return cuadrantes[c];
		else
			return null;
	}

	/**
	 * Retorna un String en el que se muestra todos los valores de las celdas del
	 * tablero
	 */
	@Override
	public String toString() {
		String toReturn = "";
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++)
				toReturn += " " + celdas[i][j].getValor();
			toReturn += "\n";
		}
		return toReturn;
	}
}
