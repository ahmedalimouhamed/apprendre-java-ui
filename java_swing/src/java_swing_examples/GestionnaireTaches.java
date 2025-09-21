package java_swing_examples;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GestionnaireTaches extends JFrame implements ActionListener, ListSelectionListener{
	
	private DefaultListModel<String> modeleListe;
	private JList<String> listeTaches;
	private JTextField champTache;
	private JButton btnAjouter, btnSupprimer, btnModifier;
	private ArrayList<String> taches;
	
	public GestionnaireTaches() {
		setTitle("Gestionnaire de taches");
		setSize(500, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		taches = new ArrayList<>();
		modeleListe = new DefaultListModel<>();
		listeTaches = new JList<>(modeleListe);
		listeTaches.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listeTaches.addListSelectionListener(this);
		
		champTache = new JTextField(20);
		btnAjouter = new JButton("Ajouter");
		btnSupprimer = new JButton("Supprimer");
		btnModifier = new JButton("Modifier");
		
		btnAjouter.addActionListener(this);
		btnSupprimer.addActionListener(this);
		btnModifier.addActionListener(this);
		btnSupprimer.setEnabled(false);
		btnModifier.setEnabled(false);
		
		JPanel panelHaut = new JPanel();
		panelHaut.add(new JLabel("Nouvelle tâche:"));
		panelHaut.add(champTache);
		panelHaut.add(btnAjouter);
		
		JPanel panelBas = new JPanel();
		panelBas.add(btnSupprimer);
		panelBas.add(btnModifier);
		
		JScrollPane scrollPane = new JScrollPane(listeTaches);
		
		setLayout(new BorderLayout(10, 10));
		add(panelHaut, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		add(panelBas, BorderLayout.SOUTH);
		
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnAjouter) {
			String nouvelleTache = champTache.getText().trim();
			
			if(!nouvelleTache.isEmpty()) {
				taches.add(nouvelleTache);
				modeleListe.addElement(nouvelleTache);
				champTache.setText("");
			}
		}else if(e.getSource() == btnSupprimer) {
			int index = listeTaches.getSelectedIndex();
			if(index != -1) {
				taches.remove(index);
				modeleListe.remove(index);
			}
		}else if(e.getSource() == btnModifier) {
			int index = listeTaches.getSelectedIndex();
			
			if(index != -1) {
				String modification = JOptionPane.showInputDialog(this, "Modifier la tâche : ", taches.get(index));
				if(modification != null && !modification.trim().isEmpty()) {
					taches.set(index, modification);
					modeleListe.set(index, modification);
				}
			}
		}
		
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		boolean selection = !listeTaches.isSelectionEmpty();
		btnSupprimer.setEnabled(selection);
		btnModifier.setEnabled(selection);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(GestionnaireTaches::new);
	}


}
