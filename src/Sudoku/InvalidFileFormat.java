package Sudoku;

/**
 * Clase que modela excepciones causadas por intentar leer un archivo de la
 * solución con formato invalido
 * 
 * @author Tadeo Villafaña
 *
 */
public class InvalidFileFormat extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la excepción
	 * 
	 * @param msj Mensaje a mostrar cuando la excepcion sea lanzada
	 */
	public InvalidFileFormat(String msj) {
		super(msj);
	}

}
