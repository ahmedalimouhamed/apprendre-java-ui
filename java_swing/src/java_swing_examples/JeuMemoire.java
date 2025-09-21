package java_swing_examples;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JeuMemoire extends JFrame {
	private JButton[] cartes;
	private int[] valeurs;
	private int premiereCarte = -1;
	private int secondeCarte = 0;
	private int pairesTrouvees = 0;
	private Timer timer;
	
	public JeuMemoire() {
		setTitle("Jeu de mémoire");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		valeurs = new int[16];
		for(int i = 0; i < 8; i++) {
			valeurs[i * 2] = i + 1;
			valeurs[i * 2 + 1] = i + 1;
		}
		
		melangerCartes();
		
		cartes = new JButton[16];
		for (int i = 0; i < 16; i++) {
			cartes[i] = new JButton();
			cartes[i].setFont(new Font("Arial", Font.BOLD, 24));
			cartes[i].setText("?");
			cartes[i].setName(String.valueOf(i));
			cartes[i].addMouseListener(new CarteClickListener());
		}
		
		timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				retournerCartes();
			}
			
		});
		timer.setRepeats(false);
		JPanel grille = new JPanel(new GridLayout(4, 4, 5, 5));
		grille.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		for(JButton carte : cartes) {
			grille.add(carte);
		}
		
		JPanel panelButtons = new JPanel();
		JButton btnNouvellePartie = new JButton("Nouvelle partie");
		btnNouvellePartie.addActionListener(e -> nouvellePartie());
		panelButtons.add(btnNouvellePartie);
		
		setLayout(new BorderLayout());
		add(grille, BorderLayout.CENTER);
		add(panelButtons, BorderLayout.SOUTH);
		
		setVisible(true);
	}
	
	private void melangerCartes() {
		List<Integer> liste = Arrays.asList(
			1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8
		);
		Collections.shuffle(liste);
		for(int i=0; i<16; i++) {
			valeurs[i] = liste.get(i);
		}
	}
	
	private void nouvellePartie() {
		melangerCartes();
		for(JButton carte : cartes) {
			carte.setText("?");
			carte.setEnabled(true);
			carte.setBackground(null);
		}
		premiereCarte = -1;
		secondeCarte = -1;
		pairesTrouvees = 0;
	}
	
	private class CarteClickListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			JButton carteCliquee = (JButton) e.getSource();
			int index = Integer.parseInt(carteCliquee.getName());
			
			if(!carteCliquee.isEnabled() || carteCliquee.getText().equals(String.valueOf(valeurs[index]))) {
				return;
			}
			
			if(premiereCarte == -1) {
				premiereCarte = index;
				carteCliquee.setText(String.valueOf(valeurs[index]));
				carteCliquee.setBackground(Color.LIGHT_GRAY);
			}else if(secondeCarte == -1 && index != premiereCarte) {
				secondeCarte = index;
				carteCliquee.setText(String.valueOf(valeurs[index]));
				carteCliquee.setBackground(Color.LIGHT_GRAY);
				timer.start();
			}
		}
	}
	
	private void retournerCartes() {
		if(valeurs[premiereCarte] == valeurs[secondeCarte]) {
			cartes[premiereCarte].setEnabled(false);
			cartes[secondeCarte].setEnabled(false);
			cartes[premiereCarte].setBackground(Color.GREEN);
			cartes[secondeCarte].setBackground(Color.GREEN);
			pairesTrouvees++;
			
			if(pairesTrouvees == 8) {
				JOptionPane.showMessageDialog(this, "Félicitation! vous avez gagné!");
			}
		}else {
			cartes[premiereCarte].setText("?");
			cartes[secondeCarte].setText("?");
			cartes[premiereCarte].setBackground(null);
			cartes[secondeCarte].setBackground(null);
		}
		
		premiereCarte = -1;
		secondeCarte = -1;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(JeuMemoire::new);
	}
}
