package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;

/**clase que modela el mensaje de victoria mendiante un JDialog 
 *  
 * @author Tadeo Villafaña
 *
 */
public class MensajeVictoria extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();

	/**
	 * Se crea un JDialog que indica cuando el jugador gano y en que tiempo lo hiso,
	 * además se da la opcion de volver a jugar una partida o cerrar el juego
	 * 
	 * @param horas cantidad de horas que tardó en solucionar el sudoku
	 * @param minutos cantidad de minutos que tardó en solucionar el sudoku
	 * @param segundos cantidad de segundos que tardó en solucionar el sudoku
	 */
	public MensajeVictoria(int horas, int minutos, int segundos) {
		getContentPane().setBackground(new Color(102, 205, 170));
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(102,204,153));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setResizable(false);
		{
			JLabel lblNewLabel = new JLabel("Felicitaciones");
			lblNewLabel.setForeground(new Color(0, 0, 0));
			lblNewLabel.setBorder(null);
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setFont(new Font("Monotype Corsiva", Font.BOLD, 76));
			lblNewLabel.setBounds(10, 0, 414, 134);
			contentPanel.add(lblNewLabel);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Ha ganado en :");
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);
			lblNewLabel_1.setBounds(10, 107, 414, 27);
			contentPanel.add(lblNewLabel_1);
		}
		{
			JPanel panel = new JPanel();
			panel.setBackground(new Color(0, 51, 255));
			panel.setBounds(10, 128, 414, 76);
			contentPanel.add(panel);
			panel.setLayout(new GridLayout(1, 3, 0, 0));
			{
				JLabel etiquetaHoras = new JLabel("<html><body> " + horas + " <br> Horas </body></html>");
				etiquetaHoras.setForeground(new Color(255, 204, 255));
				etiquetaHoras.setHorizontalTextPosition(SwingConstants.CENTER);
				etiquetaHoras.setHorizontalAlignment(SwingConstants.CENTER);
				etiquetaHoras.setFont(new Font("Monotype Corsiva", Font.PLAIN, 26));
				panel.add(etiquetaHoras);
			}
			{
				JLabel etiquetaMinutos = new JLabel("<html><body> " + minutos + " <br> Minutos </body></html>");
				etiquetaMinutos.setForeground(new Color(255, 204, 255));
				etiquetaMinutos.setHorizontalTextPosition(SwingConstants.CENTER);
				etiquetaMinutos.setHorizontalAlignment(SwingConstants.CENTER);
				etiquetaMinutos.setFont(new Font("Monotype Corsiva", Font.PLAIN, 26));
				panel.add(etiquetaMinutos);
			}
			{
				JLabel etiquetaSegundos = new JLabel("<html><body> " + segundos + " <br> Segundos </body></html>");
				etiquetaSegundos.setForeground(new Color(255, 204, 255));
				etiquetaSegundos.setHorizontalTextPosition(SwingConstants.CENTER);
				etiquetaSegundos.setHorizontalAlignment(SwingConstants.CENTER);
				etiquetaSegundos.setFont(new Font("Monotype Corsiva", Font.PLAIN, 26));
				panel.add(etiquetaSegundos);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(0, 51, 255));
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Volver a jugar");
				okButton.setBackground(new Color(102,204,153));
				buttonPane.add(okButton);
				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						InterfazMenu frame = new InterfazMenu();
						frame.setVisible(true);
						dispose();
					}

				});
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cerrar");
				cancelButton.setBackground(new Color(102,204,153));
				cancelButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}

				});
				buttonPane.add(cancelButton);
			}
		}
	}

}
