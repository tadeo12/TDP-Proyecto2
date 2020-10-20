package GUI;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Clase que modela el temporizador de la GUI. Se encarga de contabilizar el
 * tiempo transcurrido y reflejarlo en los JLabels de los digitos.
 * 
 * @author Tadeo Villafaña
 *
 */
public class Temporizador {
	private Timer timer;
	private TimerTask timerTask;
	private int tiempo; // en segundos

	/**
	 * Arreglo de Strings que almacena los nombres de los archivos de las imagenes
	 * utilizadas para mostrar el tiempo. dichos archivos se deben almacenar en
	 * .../Imagenes/cronometro/
	 */
	private String imagenes[] = { "cero.png", "uno.png", "dos.png", "tres.png", "cuatro.png", "cinco.png", "seis.png",
			"siete.png", "ocho.png", "nueve.png" };

	/**
	 * Constructor del Temporizador al que se le pasa por parametro los JLabels de
	 * los digitos del tiempo a mostrar. Tener en cuenta que no se redimensiona las
	 * imagenes al tamaño del JLabel por lo que se recomienda que los JLabels
	 * parametrizados tengan exactamente el mismo tamaño que las imagenes utilizadas
	 * 
	 * @param h2 JLabel que mostrará el digito mas significativo de la cantidad de
	 *           horas transcurridas
	 * @param h1 JLabel que mostrará el digito menos significativo de la cantidad de
	 *           horas transcurridas
	 * @param m2 JLabel que mostrará el digito mas significativo de la cantidad de
	 *           minutos transcurridos
	 * @param m1 JLabel que mostrará el digito menos significativo de la cantidad de
	 *           minutos transcurridos
	 * @param s2 JLabel que mostrará el digito mas significativo de la cantidad de
	 *           segundos transcurridos
	 * @param s1 JLabel que mostrará el digito menos significativo de la cantidad de
	 *           segundos transcurridos
	 */
	public Temporizador(JLabel h2, JLabel h1, JLabel m2, JLabel m1, JLabel s2, JLabel s1) {
		timer = new Timer();
		tiempo = 0;
		timerTask = new TimerTask() {
			@Override
			public void run() {
				tiempo++;
				if (tiempo % 60 == 0) {
					h1.setIcon(primerDigHora());
					h1.repaint();
					h2.setIcon(segundoDigHora());
					h2.repaint();
					m1.setIcon(primerDigMin());
					m1.repaint();
					m2.setIcon(segundoDigMin());
					m2.repaint();

				}
				s1.setIcon(primerDigSeg());
				s1.repaint();
				s2.setIcon(segundoDigSeg());
				s2.repaint();

			}
		};
	}

	/**
	 * Método utilizado para detener el temporizador
	 */
	public void detener() {
		timer.cancel();
	}

	/**
	 * Método utilizado para iniciar el temporizador
	 */
	public void runTimer() {
		timer.schedule(timerTask, 0, 1000);
		// el tercer parametro es la cantidad de milisegundos que debe ocurrir para que
		// aumente en uno el tiempo (1000 milisegundos= 1 segundo)
	}

	/**
	 * Método que crea y retorna un ImageIcon correspondiente al valor parametrizado
	 * 
	 * @param i Valor del que se quiere obtener una imagen
	 * @return ImageIcon del valor parametrizado
	 */
	public ImageIcon img(int i) {
		return new ImageIcon(this.getClass().getResource("/Imagenes/cronometro/" + imagenes[i]));
	}

	/**
	 * Método que crea y retorna un ImageIcon que debe mostrarse en el primer digito
	 * de los segundos
	 * 
	 * @return ImageIcon correspondiente al primer digito de los segundos
	 */
	public ImageIcon primerDigSeg() {
		int digito = getSegundos() % 10;
		return new ImageIcon(this.getClass().getResource("/Imagenes/cronometro/" + imagenes[digito]));
	}

	/**
	 * Método que crea y retorna un ImageIcon que debe mostrarse en el segundo
	 * digito de los segundos
	 * 
	 * @return ImageIcon correspondiente al segundo digito de los segundos
	 */
	public ImageIcon segundoDigSeg() {
		int digito = getSegundos() / 10;
		return new ImageIcon(this.getClass().getResource("/Imagenes/cronometro/" + imagenes[digito]));
	}

	/**
	 * Método que crea y retorna un ImageIcon que debe mostrarse en el primer digito
	 * de los minutos
	 * 
	 * @return ImageIcon correspondiente al primer digito de los minutos
	 */
	public ImageIcon primerDigMin() {
		int digito = getMinutos() % 10;
		return new ImageIcon(this.getClass().getResource("/Imagenes/cronometro/" + imagenes[digito]));
	}

	/**
	 * Método que crea y retorna un ImageIcon que debe mostrarse en el segundo
	 * digito de los minutos
	 * 
	 * @return ImageIcon correspondiente al segundo digito de los minutos
	 */
	public ImageIcon segundoDigMin() {
		int digito = getMinutos() / 10;
		return new ImageIcon(this.getClass().getResource("/Imagenes/cronometro/" + imagenes[digito]));
	}

	/**
	 * Método que crea y retorna un ImageIcon que debe mostrarse en el primer digito
	 * de las horas
	 * 
	 * @return ImageIcon correspondiente al primer digito de las horas
	 */
	public ImageIcon primerDigHora() {
		int digito = getHoras() % 10;
		return new ImageIcon(this.getClass().getResource("/Imagenes/cronometro/" + imagenes[digito]));
	}

	/**
	 * Método que crea y retorna un ImageIcon que debe mostrarse en el segundo
	 * digito de las horas
	 * 
	 * @return ImageIcon correspondiente al segundo digito de las horas
	 */
	public ImageIcon segundoDigHora() {
		int digito = getHoras() / 10;
		return new ImageIcon(this.getClass().getResource("/Imagenes/cronometro/" + imagenes[digito]));
	}

	/**
	 * Método que retorna la cantidad de segundos transcurridos a mostrar
	 * 
	 * @return Cantidad de segundos (sin contar los que se contabilizan como
	 *         minutos/horas)
	 */
	public int getSegundos() {
		return tiempo % 60;
	}

	/**
	 * Método que retorna la cantidad de minutos transcurridos a mostrar
	 * 
	 * @return Cantidad de minutos (sin contar los que se contabilizan como horas)
	 */
	public int getMinutos() {
		return (tiempo % 3600) / 60;
	}

	/**
	 * Método que retorna la cantidad de horas transcurridas a mostrar
	 * 
	 * @return Cantidad de horas
	 */
	public int getHoras() {
		return tiempo / 3600;
	}
}