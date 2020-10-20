package Sudoku;

/**
 * Clase que modela excepciones causadas por intentar leer un archivo de la
 * soluci�n con formato invalido
 * 
 * @author Tadeo Villafa�a
 *
 */
public class InvalidFileFormat extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la excepci�n
	 * 
	 * @param msj Mensaje a mostrar cuando la excepcion sea lanzada
	 */
	public InvalidFileFormat(String msj) {
		super(msj);
	}

}
