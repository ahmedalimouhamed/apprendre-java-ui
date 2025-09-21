package java_swing_examples;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class EditeurTexte extends JFrame implements ActionListener{
	
	private JTextArea zoneTexte;
	private JFileChooser selecteurFichier;
	
	public EditeurTexte() {
		setTitle("Editeur de texte simple");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		zoneTexte = new JTextArea();
		zoneTexte.setFont(new Font("Monospaced", Font.PLAIN, 14));
		JScrollPane scrollPane = new JScrollPane(zoneTexte);
		
		JMenuBar barreMenu = new JMenuBar();
		
		JMenu menuFichier = new JMenu("Fichier");
		JMenuItem itemNouveau = new JMenuItem("Nouveau");
		JMenuItem itemOuvrir = new JMenuItem("Ouvrir");
		JMenuItem itemEnregistrer = new JMenuItem("Enregistrer");
		JMenuItem itemQuitter = new JMenuItem("Quitter");
		
		itemNouveau.addActionListener(this);
		itemOuvrir.addActionListener(this);
		itemEnregistrer.addActionListener(this);
		itemQuitter.addActionListener(this);
		
		menuFichier.add(itemNouveau);
		menuFichier.add(itemOuvrir);
		menuFichier.add(itemEnregistrer);
		menuFichier.addSeparator();
		menuFichier.add(itemQuitter);
		
		JMenu menuEdition = new JMenu("Edition");
		JMenuItem itemCouper = new JMenuItem("Couper");
		JMenuItem itemCopier = new JMenuItem("Copier");
		JMenuItem itemColler = new JMenuItem("Coller");
		
		itemCouper.addActionListener(this);
		itemCopier.addActionListener(this);
		itemColler.addActionListener(this);
		
		menuEdition.add(itemCouper);
		menuEdition.add(itemCopier);
		menuEdition.add(itemColler);
		
		barreMenu.add(menuFichier);
		barreMenu.add(menuEdition);
		
		setJMenuBar(barreMenu);
		
		JToolBar barreOutils = new JToolBar();
		JButton btnNouveau = new JButton("Nouveau");
		JButton btnOuvrir = new JButton("Ouvrir");
		JButton btnEnregistrer = new JButton("Enregistrer");
		
		btnNouveau.addActionListener(this);
		btnOuvrir.addActionListener(this);
		btnEnregistrer.addActionListener(this);
		
		barreOutils.add(btnNouveau);
		barreOutils.add(btnOuvrir);
		barreOutils.add(btnEnregistrer);
		
		selecteurFichier = new JFileChooser();
		
		setLayout(new BorderLayout());
		add(barreOutils, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String commande = e.getActionCommand();
		
		switch(commande) {
			case "Nouveau":
				zoneTexte.setText("");
				break;
				
			case "Ouvrir":
				ouvrirFichier();
				break;
				
			case "Enregistrer":
				enregistrerFichier();
				break;
				
			case "Quitter":
				System.exit(0);
				break;
				
			case "Couper":
				zoneTexte.cut();
				break;
				
			case "Copier":
				zoneTexte.copy();
				break;
				
			case "Coller":
				zoneTexte.paste();
				break;
		}
	}
	
	private void ouvrirFichier() {
		int retour = selecteurFichier.showOpenDialog(this);
		if(retour == JFileChooser.APPROVE_OPTION) {
			File fichier = selecteurFichier.getSelectedFile();
			try(BufferedReader reader = new BufferedReader(new FileReader(fichier))){
				zoneTexte.read(reader,  null);
			}catch(IOException e) {
				JOptionPane.showMessageDialog(this, "Erreur lors de la lecture du ficier: "+e.getMessage());
			}
		}
	}
	
	private void enregistrerFichier() {
		int retour = selecteurFichier.showSaveDialog(this);
		if(retour == JFileChooser.APPROVE_OPTION) {
			File fichier = selecteurFichier.getSelectedFile();
			try(BufferedWriter writer = new BufferedWriter(new FileWriter(fichier))) {
				zoneTexte.write(writer);
			}catch(IOException e) {
				JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement du fichier" + e.getMessage());
			}
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(EditeurTexte::new);
	}
}
