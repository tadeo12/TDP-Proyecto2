package GUI;

import javax.swing.ImageIcon;

/**
 * Clase que modela conjuntos de imagenes que pueden tener las celdas del
 * Sudoku. Para funcionar requiere que los archivos de imagen utilizados se
 * encuentren en la carpeta "Imagenes" del proyecto
 * 
 * @author Tadeo Villafaña
 *
 */
public class Imagenes {

	private String imagenes[];

	/**
	 * Constructor de la colección de imagenes, requiere que se parametrice todos
	 * los nombres de los archivos de imagenes png a mostrar
	 * 
	 * @param uno    Nombre de la imagen de valor uno
	 * @param dos    Nombre de la imagen de valor dos
	 * @param tres   Nombre de la imagen de valor tres
	 * @param cuatro Nombre de la imagen de valor cuatro
	 * @param cinco  Nombre de la imagen de valor cinco
	 * @param seis   Nombre de la imagen de valor seis
	 * @param siete  Nombre de la imagen de valor siete
	 * @param ocho   Nombre de la imagen de valor ocho
	 * @param nueve  Nombre de la imagen de valor nueve
	 */
	public Imagenes(String uno, String dos, String tres, String cuatro, String cinco, String seis, String siete,
			String ocho, String nueve) {
		imagenes = new String[9];

		imagenes[0] = uno;
		imagenes[1] = dos;
		imagenes[2] = tres;
		imagenes[3] = cuatro;
		imagenes[4] = cinco;
		imagenes[5] = seis;
		imagenes[6] = siete;
		imagenes[7] = ocho;
		imagenes[8] = nueve;
	}

	/**
	 * Constructor de la coleccion de imagenes sin parametros. Se utiliza las
	 * imagenes por defecto. esto es las que se encuentren como x.png donde x es el
	 * nombre del numero escrito en minuscula
	 */
	public Imagenes() {
		this("uno.png", "dos.png", "tres.png", "cuatro.png", "cinco.png", "seis.png", "siete.png", "ocho.png",
				"nueve.png");
	}

	/**
	 * Método que crea y retorna una instancia de ImageIcon de la imagen
	 * correspondiente al valor parametrizado
	 * 
	 * @param i Valor del que se desea obtener una imagen.
	 * @return ImageIcon correspondiente al valor parametrizado. Si el valor no es
	 *         valido retorna null.
	 */
	public ImageIcon getImagen(int i) {
		if (i > 0 && i < 10)
			return new ImageIcon(this.getClass().getResource("/Imagenes/" + imagenes[i - 1]));
		else
			return null;
	}

	/**
	 * Setter del nombre de la imagen que se espera que corresponda al valor i
	 * 
	 * @param i    Valor al que se le setea la imagen
	 * @param ruta Nombre del archivo(debe encontrase en la carpeta Imagenes y si es
	 *             una ruta ignorar el prefijo .../Imagenes/)
	 */
	public void setImagen(int i, String ruta) {
		if (i > 0 && i < 10)
			imagenes[i - 1] = ruta;
	}
}
