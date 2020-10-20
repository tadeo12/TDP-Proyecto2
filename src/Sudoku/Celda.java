package Sudoku;

/**
 * Clase que modela las celdas del sudoku
 * 
 * @author Tadeo Villafaña
 *
 */
public class Celda {
	private boolean editable;
	private int valor;
	private int fila;
	private int columna;
	private int cuadrante;

	/**
	 * Constructor de la celda inicializa sus atributos con los parametros
	 * 
	 * @param fila         Entero que representa la fila en la que se encuentra la
	 *                     celda
	 * @param columna      Entero que representa la columna en la que se encuentra
	 *                     la celda
	 * @param cuadrante    Entero que representa el cuadrante en el que se encuentra
	 *                     la celda
	 * @param valorInicial valor en el que se inicializa la celda
	 */
	public Celda(int fila, int columna, int cuadrante, int valorInicial) {
		this.fila = fila;
		this.columna = columna;
		this.cuadrante = cuadrante;
		valor = valorInicial;
		editable = true;
	}

	/**
	 * Getter del valor de la celda
	 * 
	 * @return Valor de la celda
	 */
	public int getValor() {
		return valor;
	}

	/**
	 * Getter de la fila de la celda
	 * 
	 * @return Numero de fila en la que se encuentra
	 */
	public int getFila() {
		return fila;
	}

	/**
	 * Getter de la columna de la celda
	 * 
	 * @return Numero de columna en la que se encuentra
	 */
	public int getColumna() {
		return columna;
	}

	/**
	 * Getter del cuadrante de la celda
	 * 
	 * @return Numero de cuadrante en el que se encuentra
	 */
	public int getCuadrante() {
		return cuadrante;
	}

	/**
	 * Consulta si la celda es editable
	 * 
	 * @return Verdadero si a la celda se le puede cambiar el el valor. Falso en
	 *         caso contrario
	 */
	public boolean esEditable() {
		return editable;
	}

	/**
	 * Setter del valor de la celda. Si la celda no es editable o si el valor es invalido no se modifica.
	 * @param i Valor nuevo de la celda.
	 */
	public void setValor(int i) {
		if (editable && i > 0 && i < 10)
			valor = i;
	}

	/**
	 * Método que desabilita la opción de modificar el valor de la celda
	 */
	public void desabilitar() {
		editable = false;
	}
}
