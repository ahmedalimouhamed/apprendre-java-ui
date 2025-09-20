package java_swing_examples;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class CalculateurSimple extends JFrame implements ActionListener {
	
	private JTextField display;
	private double premierNombre, secondNombre, resultat;
	private String operation;
	
	public CalculateurSimple() {
		setTitle("Calculateur simple");
		setSize(300, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		display = new JTextField();
		display.setFont(new Font("Arial", Font.BOLD, 24));
		display.setHorizontalAlignment(JTextField.RIGHT);
		display.setEditable(false);
		
		String[] buttons = {
				"7", "8", "9", "/",
				"4", "5", "6", "*",
				"1", "2", "3", "-",
				"0", ".", "=", "+",
				"c"
		};
		
		JPanel panelButtons = new JPanel();
		panelButtons.setLayout(new GridLayout(5,4,5,5));
		
		for(String texte : buttons) {
			JButton button = new JButton(texte); 
			button.setFont(new Font("Arial", Font.BOLD, 18));
			button.addActionListener(this);
			panelButtons.add(button);
		}
		
		setLayout(new BorderLayout(5, 5));
		add(display, BorderLayout.NORTH);
		add(panelButtons, BorderLayout.CENTER);
		
		setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String commande = e.getActionCommand();
		
		if(commande.charAt(0) >= '0' && commande.charAt(0) <= '9' || commande.equals(".")) {
			display.setText(display.getText() + commande);
		}else if (commande.equals("c")) {
			display.setText("");
		}else if(commande.equals("=")) {
			secondNombre = Double.parseDouble(display.getText());
			calculer();
			display.setText(String.valueOf(resultat));
		}else {
			premierNombre = Double.parseDouble(display.getText());
			operation = commande;
			display.setText("");
		}
		
	}
	
	private void calculer() {
		switch(operation) {
			case"+" : resultat = premierNombre + secondNombre ; break;
			case"-" : resultat = premierNombre - secondNombre ; break;
			case"*" : resultat = premierNombre * secondNombre ; break;
			case"/" : resultat = premierNombre / secondNombre ; break;
		}
	}
	

	public static void main(String[] args) {
		SwingUtilities.invokeLater(CalculateurSimple::new);
	}

}
