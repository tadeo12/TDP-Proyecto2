package GUI;

import java.awt.EventQueue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

/**
 * JFrame inicial, menú en el que se selecciona la dificultad y se inicia el
 * juego
 * 
 * @author Tadeo Villafaña
 */
public class InterfazMenu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel PanelPrincipal;

	/**
	 * Se inicia la aplicacion con un frame de InterfazMenu
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					InterfazMenu frame = new InterfazMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Se crea el frame del menú
	 */
	public InterfazMenu() {
		setResizable(false);
	}{
		setBackground(new Color(255, 0, 0));
		setTitle("Sudoku (by Tadeo Villafaña)");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		PanelPrincipal = new JPanel();
		PanelPrincipal.setBackground(new Color(102, 153, 255));
		PanelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(PanelPrincipal);
		PanelPrincipal.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel Titulo = new JLabel("SUDOKU");
		Titulo.setForeground(new Color(0, 0, 102));
		Titulo.setHorizontalAlignment(SwingConstants.CENTER);
		Titulo.setFont(new Font("Source Serif Pro", Font.BOLD, 60));
		PanelPrincipal.add(Titulo);

		JPanel panelDificultad = new JPanel();
		panelDificultad.setBackground(new Color(102, 204, 153));
		PanelPrincipal.add(panelDificultad);
		panelDificultad.setLayout(null);

		JLabel labelDificultad = new JLabel("Dificultad");
		labelDificultad.setBounds(0, 0, 195, 83);
		labelDificultad.setFont(new Font("Monotype Corsiva", Font.PLAIN, 28));
		labelDificultad.setHorizontalAlignment(SwingConstants.CENTER);
		panelDificultad.add(labelDificultad);

		String[] dificultades = { "Facil", "Normal", "Dificil" };
		JComboBox<String> seleccionarDificultad = new JComboBox<String>();
		seleccionarDificultad.setBounds(202, 33, 212, 22);
		for (int i = 0; i < dificultades.length; i++) {
			seleccionarDificultad.addItem(dificultades[i]);
		}
		panelDificultad.add(seleccionarDificultad);

		JButton botonJugar = new JButton("JUGAR");
		botonJugar.setBackground(new Color(135, 206, 235));
		botonJugar.setForeground(new Color(0, 0, 102));
		botonJugar.setFont(new Font("Source Serif Pro", Font.BOLD, 28));
		botonJugar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// se crea una instancia de la InterfazJuego, parametrizando la dificultad
				// deseada y se cierra la interfaz menu
				String dificultad = (String) seleccionarDificultad.getSelectedItem();
				InterfazJuego frame = new InterfazJuego(dificultad);
				frame.setVisible(true);
				dispose();

			}
		});
		PanelPrincipal.add(botonJugar);
	}

}
