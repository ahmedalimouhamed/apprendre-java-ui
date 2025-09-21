package java_swing_examples;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JeuMorpion extends JFrame implements ActionListener{
	
	private JButton[][] buttons = new JButton[3][3];
	private boolean tourX = true;
	private int coups = 0;
	
	public JeuMorpion() {
		setTitle("Jeu du morpion");
		setSize(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JPanel grille = new JPanel();
		grille.setLayout(new GridLayout(3,3,5,5));
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				buttons[i][j] = new JButton("");
				buttons[i][j].setFont(new Font("Arial", Font.BOLD, 40));
				buttons[i][j].addActionListener(this);
				grille.add(buttons[i][j]);
			}
		}
		
		add(grille, BorderLayout.CENTER);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton buttonClique = (JButton) e.getSource();
		
		if(!buttonClique.getText().equals("")) {
			return;
		}
		
		if(tourX) {
			buttonClique.setText("X");
			buttonClique.setForeground(Color.BLUE);
		}else {
			buttonClique.setText("O");
			buttonClique.setForeground(Color.RED);
		}
		
		coups++;
		
		if(verifierVictoire()) {
			String gagnant = tourX ? "X" : "O";
			JOptionPane.showMessageDialog(this, "Le joueur "+gagnant+" a gagné!");
			reinitialiserJeu();
		}else if(coups == 9) {
			JOptionPane.showMessageDialog(this, "Match null!");
			reinitialiserJeu();
		}else {
			tourX = !tourX;
		}
	}
	
	private boolean verifierVictoire() {
		String symbole = tourX ? "X" : "O";
		
		for(int i = 0; i < 3; i++) {
			if(
					buttons[i][0].getText().equals(symbole) && 
					buttons[i][1].getText().equals(symbole) && 
					buttons[i][2].getText().equals(symbole)
			) {
				return true;
			}
		}
		
		for(int j = 0; j < 3; j++) {
			if(
					buttons[0][j].getText().equals(symbole) &&
					buttons[1][j].getText().equals(symbole) &&
					buttons[2][j].getText().equals(symbole)
			) {
				return true;
			}
		}
		
		if(
				buttons[0][0].getText().equals(symbole) &&
				buttons[1][1].getText().equals(symbole) &&
				buttons[2][2].getText().equals(symbole)
		) {
			return true;
		}
		
		if(
				buttons[0][2].getText().equals(symbole) &&
				buttons[1][1].getText().equals(symbole) &&
				buttons[2][0].getText().equals(symbole)
		) {
			return true;
		}
		
		return false;		
	}
	
	private void reinitialiserJeu() {
		for(int i = 0; i<3; i++) {
			for(int j = 0; j < 3; j++) {
				buttons[i][j].setText("");
			}
		}
		
		tourX = true;
		coups = 0;
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(JeuMorpion::new);
	}


}
