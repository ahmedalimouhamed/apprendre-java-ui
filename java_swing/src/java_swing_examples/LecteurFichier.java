package java_swing_examples;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;

public class LecteurFichier extends JFrame implements ActionListener, ListSelectionListener{

	private JList<String> listeFichiers;
	private DefaultListModel<String> modeleListe;
	private JTextArea zoneContenu;
	private JLabel lblRepertoire;
	private File repertoireCourant;
	
	public LecteurFichier() {
		setTitle("Lecteur de fichiers");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		modeleListe = new DefaultListModel<>();
		listeFichiers = new JList<>(modeleListe);
		listeFichiers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listeFichiers.addListSelectionListener(this);
		
		zoneContenu = new JTextArea();
		zoneContenu.setEditable(false);
		zoneContenu.setFont(new Font("Monospaced", Font.PLAIN, 12));
		
		lblRepertoire = new JLabel("Répertoire: ");
		lblRepertoire.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		JButton btnOuvrir = new JButton("Ouvrir");
		JButton btnParent = new JButton("Dossier parent");
		JButton btnRacine = new JButton("Racine");
		
		btnOuvrir.addActionListener(this);
		btnParent.addActionListener(this);
		btnRacine.addActionListener(this);
		
		JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelButtons.add(btnOuvrir);
		panelButtons.add(btnParent);
		panelButtons.add(btnRacine);
		
		JSplitPane splitPane = new JSplitPane(
						JSplitPane.HORIZONTAL_SPLIT, 
						new JScrollPane(listeFichiers), 
						new JScrollPane(zoneContenu)
					);
		splitPane.setDividerLocation(250);
		
		setLayout(new BorderLayout());
		add(lblRepertoire, BorderLayout.NORTH);
		add(panelButtons, BorderLayout.SOUTH);
		add(splitPane, BorderLayout.CENTER);
		
		chargerRepertoire(File.listRoots()[0]);
		setVisible(true);
	}
	
	private void chargerRepertoire(File repertoire) {
		repertoireCourant = repertoire;
		lblRepertoire.setText("Répértoire: " + repertoire.getAbsolutePath());
		modeleListe.clear();
		
		if(repertoire.getParentFile() != null) {
			modeleListe.addElement("[..]");
		}
		
		File[] fichiers = repertoire.listFiles();
		if(fichiers != null) {
			Arrays.sort(fichiers, Comparator.comparing(File::isDirectory).reversed().thenComparing(File::getName));
			
			for(File fichier : fichiers) {
				if(fichier.isDirectory()) {
					modeleListe.addElement("["+fichier.getName()+"]");
				}else {
					modeleListe.addElement(fichier.getName());
				}
			}
		}
	}
	
	private void chargerContenuFichier(File fichier) {
	    try {
	        // Option 1 : lire tout en une seule fois
	        String contenu = new String(Files.readAllBytes(Paths.get(fichier.getAbsolutePath())),
	                                    StandardCharsets.UTF_8);
	        zoneContenu.setText(contenu);
	    } catch(IOException e) {
	        zoneContenu.setText("Erreur de lecture: " + e.getMessage());
	    }
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(!e.getValueIsAdjusting()) {
			String selection = listeFichiers.getSelectedValue();
			if(selection != null) {
				if(selection.startsWith("[") && selection.endsWith("]")) {
					String nomDossier = selection.substring(1, selection.length()-1);
					if(nomDossier.equals("..")) {
						chargerRepertoire(repertoireCourant.getParentFile());
					}else {
						chargerRepertoire(new File(repertoireCourant, nomDossier));
					}
				}else {
					chargerContenuFichier(new File(repertoireCourant, selection));
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String commande = e.getActionCommand();
		
		switch(commande) {
			case "Ouvrir":
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int resultat = fileChooser.showOpenDialog(this);
				if(resultat == JFileChooser.APPROVE_OPTION) {
					File selection = fileChooser.getSelectedFile();
					if(selection.isDirectory()) {
						chargerRepertoire(selection);
					}else {
						chargerRepertoire(selection.getParentFile());
						listeFichiers.setSelectedValue(selection.getName(), true);
					}
				}
				break;
				
			case "Dossier parent":
				if(repertoireCourant.getParentFile() != null) {
					chargerRepertoire(repertoireCourant.getParentFile());
				}
				break;
				
			case "Racine":
				chargerRepertoire(File.listRoots()[0]);
				break;
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(LecteurFichier::new);
	}
}
