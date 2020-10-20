package GUI;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import Sudoku.*;

import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 * JFrame principal, se encarga de inicializar el juego en ventana.
 * 
 * @author Tadeo Villafaña
 *
 */
public class InterfazJuego extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel panelPrincipal;

	private Juego juego;
	private Imagenes imagenesCeldas;

	private Celda celdaApretada;
	private JLabel etiquetaCeldaApretada;

	private JLabel[][] tableroEtiquetas;

	private boolean mostrandoErrores;

	private Temporizador temp;

	/**
	 * Constructor de la interfaz del juego, crea todas las componentes graficas
	 * necesarias para el desarrollo del mismo. La dificultad parametrizada indica
	 * la cantidad de celdas que se inicializaran mostradas, tener en cuenta que
	 * esta es la dificultad promedio de los tableros iniciales aleatorios y no es
	 * proporcional a la cantidad de celdas reveladas ya que no se verifica que haya
	 * solucion unica para cada sudoku
	 *
	 * @param dificultad String que describe la dificultad elegida para el juego
	 */
	public InterfazJuego(String dificultad) {
		setTitle("Sudoku (" + dificultad + ")");
		setResizable(false);

		try {

			if (dificultad.charAt(0) == 'F')
				juego = new Juego(45);
			else if (dificultad.charAt(0) == 'N')
				juego = new Juego(35);
			else
				juego = new Juego(26);

			imagenesCeldas = new Imagenes();

			Tablero t = juego.getTablero();

			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			setBounds(100, 100, 857, 700);
			panelPrincipal = new JPanel();
			panelPrincipal.setLayout(null);
			panelPrincipal.setBackground(new Color(82, 113, 255));
			setContentPane(panelPrincipal);

			JPanel panelTablero = new JPanel();
			panelTablero.setBounds(10, 24, 592, 626);
			panelPrincipal.add(panelTablero);
			panelTablero.setLayout(new GridLayout(9, 9, 0, 0));

			tableroEtiquetas = new JLabel[9][9];

			for (int i = 0; i < 9; i++)
				for (int j = 0; j < 9; j++) {
					// inicializacion JLabels de las celdas
					Celda c = t.getCelda(i, j);

					ImageIcon grafico = c.getValor() == 0 ? null : imagenesCeldas.getImagen(c.getValor());
					// si el valor de c es 0 no tendrá imagen que mostrar(se inicia vacía)

					JLabel label = new JLabel();
					tableroEtiquetas[i][j] = label; // almaceno las etuitas para posterior uso
					label.setOpaque(true);
					label.setBorder(new LineBorder(Color.BLACK));
					setearFondoPorDefecto(c, label);

					if (!c.esEditable())
						label.setEnabled(false);

					panelTablero.add(label);

					label.addComponentListener(new ComponentAdapter() {
						@Override
						public void componentResized(ComponentEvent e) {
							if (grafico != null) {
								redimensionar(label, grafico);
								label.setIcon(grafico);
							}
						}
					});

					label.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							// el evento que se ejecuta al clickear las celdas consiste en guardar la celda
							// apretada y mostrar graficamente que esta seleccionada, no se modifica el
							// valor de la celda(eso lo realiza el teclado)
							if (grafico != null)
								redimensionar(label, grafico);
							if (celdaApretada != null) {
								etiquetaCeldaApretada.setBorder(new LineBorder(Color.BLACK));
								// se deja de seleccionar la anterior
							}
							etiquetaCeldaApretada = label;
							etiquetaCeldaApretada.setBorder(new LineBorder(Color.GREEN));
							celdaApretada = c;
						}
					});

				}

			JPanel panelTeclado = new JPanel();
			panelTeclado.setBounds(612, 24, 219, 230);
			panelTeclado.setBorder(new LineBorder(Color.BLACK));
			panelPrincipal.add(panelTeclado);
			panelTeclado.setLayout(new GridLayout(3, 3, 0, 0));

			JPanel PanelTemporizador = new JPanel();
			PanelTemporizador.setLayout(null);
			PanelTemporizador.setBorder(new LineBorder(new Color(5, 101, 98)));
			PanelTemporizador.setBackground(new Color(126, 96, 169));
			PanelTemporizador.setBounds(609, 270, 229, 77);
			panelPrincipal.add(PanelTemporizador);

			JLabel primerDigitoHora = new JLabel("");
			JLabel segundoDigitoHora = new JLabel("");
			JLabel primerDigitoMinuto = new JLabel("");
			JLabel segundoDigitoMinuto = new JLabel("");
			JLabel primerDigitoSegundo = new JLabel("");
			JLabel segundoDigitoSegundo = new JLabel("");

			// las JLabels del temporizador son enviadas por parametro al constructor del
			// mismo, InterfazJuego le delega la responsabilidad de mostrar
			// graficamente las imagenes de los digitos del tiempo transcurrido y que el
			// tiempo avance correctamente
			temp = new Temporizador(primerDigitoHora, segundoDigitoHora, primerDigitoMinuto, segundoDigitoMinuto,
					primerDigitoSegundo, segundoDigitoSegundo);

			primerDigitoHora.setBounds(3, 36, 32, 32);
			PanelTemporizador.add(primerDigitoHora);
			primerDigitoHora.setIcon(temp.img(0));

			segundoDigitoHora.setBounds(37, 36, 32, 32);
			PanelTemporizador.add(segundoDigitoHora);
			segundoDigitoHora.setIcon(temp.img(0));

			primerDigitoMinuto.setBounds(78, 36, 32, 32);
			PanelTemporizador.add(primerDigitoMinuto);
			primerDigitoMinuto.setIcon(temp.img(0));

			segundoDigitoMinuto.setBounds(112, 36, 32, 32);
			PanelTemporizador.add(segundoDigitoMinuto);
			segundoDigitoMinuto.setIcon(temp.img(0));

			primerDigitoSegundo.setBounds(152, 36, 32, 32);
			PanelTemporizador.add(primerDigitoSegundo);
			primerDigitoSegundo.setIcon(temp.img(0));

			segundoDigitoSegundo.setBounds(186, 36, 32, 32);
			PanelTemporizador.add(segundoDigitoSegundo);
			segundoDigitoSegundo.setIcon(temp.img(0));

			temp.runTimer(); // se inicia el tiempo

			JLabel etiquetaTiempo = new JLabel("Tiempo:");
			etiquetaTiempo.setFont(new Font("Monotype Corsiva", Font.PLAIN, 27));
			etiquetaTiempo.setHorizontalAlignment(SwingConstants.CENTER);
			etiquetaTiempo.setBounds(10, 11, 219, 21);
			PanelTemporizador.add(etiquetaTiempo);

			ActionListener oyenteTeclado = new OyenteTeclado();

			for (int i = 0; i < 9; i++) {
				JButton boton = new JButton();
				ImageIcon grafico = imagenesCeldas.getImagen(i + 1);
				boton.addComponentListener(new ComponentAdapter() {
					@Override
					public void componentResized(ComponentEvent e) {
						redimensionarBoton(boton, grafico);
						boton.setIcon(grafico);
					}
				});
				boton.addActionListener(oyenteTeclado);
				boton.setActionCommand("" + (i + 1));
				panelTeclado.add(boton);
			}

			mostrandoErrores = false; // por defecto no se muestran las celdas con valores repetidos.

			JLabel etiquetaSolucionInvalida = new JLabel("<html><body> Solución <br> Invalida </body></html>");
			etiquetaSolucionInvalida.setBorder(new LineBorder(new Color(178, 34, 34), 8, true));
			etiquetaSolucionInvalida.setBackground(new Color(0, 255, 255));
			etiquetaSolucionInvalida.setHorizontalAlignment(SwingConstants.CENTER);
			etiquetaSolucionInvalida.setVisible(false);
			etiquetaSolucionInvalida.setFont(new Font("Monotype Corsiva", Font.BOLD, 29));
			etiquetaSolucionInvalida.setForeground(Color.RED);
			etiquetaSolucionInvalida.setBounds(612, 424, 219, 177);
			panelPrincipal.add(etiquetaSolucionInvalida);

			JButton botonValidar = new JButton("VALIDAR");
			botonValidar.setForeground(Color.BLUE);
			botonValidar.setFont(new Font("Source Serif Pro", Font.BOLD, 26));
			botonValidar.setBounds(612, 358, 219, 55);
			botonValidar.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// se verifica si gano, si la solucion es correcta se abrirá un JDialog y se
					// cerrará el frame del juego. Caso contrario, se mostrará un JLabel de solucion
					// invalida
					if (juego.gano()) {
						temp.detener();
						MensajeVictoria dialog = new MensajeVictoria(temp.getHoras(), temp.getMinutos(),
								temp.getSegundos());
						dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
						dialog.setVisible(true);
						dispose();
					} else
						etiquetaSolucionInvalida.setVisible(true);

				}

			});
			panelPrincipal.add(botonValidar);

			JRadioButton opcionMostrarErrores = new JRadioButton("Mostrar Erroneos");
			opcionMostrarErrores.setFont(new Font("Monotype Corsiva", Font.PLAIN, 30));
			opcionMostrarErrores.setBounds(608, 625, 223, 23);
			panelPrincipal.add(opcionMostrarErrores);
			opcionMostrarErrores.setBackground(new Color(82, 113, 255));
			opcionMostrarErrores.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (mostrandoErrores) {
						noMostrarErrores();
						mostrandoErrores = false;
					} else {
						mostrarErrores();
						mostrandoErrores = true;
					}

				}
			});

		} catch (InvalidFileFormat e) {
			e.printStackTrace();
		}
	}

	/**
	 * Clase privada que modela los oyentes del teclado
	 * 
	 * @author Tadeo Villafaña
	 *
	 */
	private class OyenteTeclado implements ActionListener {

		/**
		 * La acción realizada al oprimir un boton del teclado es la de modificar, si es
		 * posible, con el valor correspondiente la celda oprimida previamente. El nuevo
		 * valor de la celda es obtenido mediante el ActionCommand del evento. Luego de
		 * modificar el valor de la celda se actualiza, si es necesario, las celdas
		 * marcadas como erroneas.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			int valorNuevo = e.getActionCommand().charAt(0) - '0';
			LinkedList<Celda> yaNoErroneas;
			if (celdaApretada != null && celdaApretada.esEditable()) {
				yaNoErroneas = juego.modificar(valorNuevo, celdaApretada.getFila(), celdaApretada.getColumna());
				// la lista yaNoErroneas es utilizada para saber cuales celdas se deberian dejar
				// de marcar como erroneas
				ImageIcon im = imagenesCeldas.getImagen(celdaApretada.getValor());
				redimensionar(etiquetaCeldaApretada, im);
				etiquetaCeldaApretada.setIcon(im);
				if (mostrandoErrores) {
					for (Celda c : yaNoErroneas) {
						JLabel label = tableroEtiquetas[c.getFila()][c.getColumna()];
						setearFondoPorDefecto(c, label);
					}
					for (Celda c : juego.listaErroneas()) {
						JLabel label = tableroEtiquetas[c.getFila()][c.getColumna()];
						label.setBackground(new Color(247, 66, 66));
					}
				}
			}
		}

	}

	/**
	 * Método auxiliar que permite desmarcar las celdas erroneas
	 */
	private void noMostrarErrores() {
		for (Celda c : juego.listaErroneas()) {
			JLabel label = tableroEtiquetas[c.getFila()][c.getColumna()];
			setearFondoPorDefecto(c, label);
		}
	}

	/**
	 * Método auxiliar que marca las celdas erroneas
	 */
	private void mostrarErrores() {
		for (Celda c : juego.listaErroneas()) {
			JLabel label = tableroEtiquetas[c.getFila()][c.getColumna()];
			label.setBackground(new Color(247, 66, 66));
		}
	}

	/**
	 * Se setea por defecto el fondo de la JLabel parametrizada. Se
	 * utilizan dos colores distintos dependiendo de a que cuadrante pertenezca con
	 * el objetivo de facilitar la visualizacion de los limites de cada cuadrante
	 * 
	 * @param c Celda, se parametriza para facilitar la verificacion de a que cuadrante pertenece
	 * @param label JLabel correspondiente a la celda a parametrizar
	 */
	private void setearFondoPorDefecto(Celda c, JLabel label) {
		Tablero t = juego.getTablero();
		if (t.getCuadrante(0).contains(c) || t.getCuadrante(2).contains(c) || t.getCuadrante(4).contains(c)
				|| t.getCuadrante(6).contains(c) || t.getCuadrante(8).contains(c))
			label.setBackground(new Color(126, 96, 169));
		else
			label.setBackground(new Color(126, 159, 180));
	}

	/**Método para mostrar graficamente la imagen de los botones con un tamaño acorde
	 * 
	 * @param botonTeclado botón a redimensionar
	 * @param grafico ImageIcon a mostrar en el botón
	 */
	private void redimensionarBoton(JButton botonTeclado, ImageIcon grafico) {
		Image image = grafico.getImage();
		if (image != null) {
			Image newimg = image.getScaledInstance(botonTeclado.getWidth(), botonTeclado.getHeight(),
					java.awt.Image.SCALE_SMOOTH);
			grafico.setImage(newimg);
			botonTeclado.repaint();
		}
	}

	/**Método para mostrar graficamente la imagen de las celdas con un tamaño acorde
	 * 
	 * @param label JLabel a redimensionar
	 * @param grafico ImageIcon a mostrar en el JLabel
	 */
	private void redimensionar(JLabel label, ImageIcon grafico) {
		Image image = grafico.getImage();
		if (image != null) {
			Image newimg = image.getScaledInstance(label.getWidth(), label.getHeight(), java.awt.Image.SCALE_SMOOTH);
			grafico.setImage(newimg);
			label.repaint();
		}
	}
}
