package java_swing_examples;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;

public class ConvertisseurTemperature extends JFrame implements ActionListener, ChangeListener{

	private JTextField txtCelsius, txtFahrenheit;
	private JSlider slider;
	private JComboBox<String> comboUnites;
	private boolean enCoursDeMiseAJour = false;
	
	public ConvertisseurTemperature() {
		setTitle("Convertisseur de température");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JLabel lblCelsius = new JLabel("Celsius:");
		JLabel lblFahrenheit = new JLabel("FahrenHeit:");
		JLabel lblSlider = new JLabel("Réglage:");
		
		txtCelsius = new JTextField(10);
		txtFahrenheit = new JTextField(10);
		txtCelsius.addActionListener(this);
		txtFahrenheit.addActionListener(this);
		
		slider = new JSlider(JSlider.HORIZONTAL, -100, 200, 0);
		slider.setMajorTickSpacing(50);
		slider.setMinorTickSpacing(10);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.addChangeListener(this);
		
		String[] unites = {"Celsius -> Fahrenheit", "Fahrenheit -> Celsius"};
		comboUnites = new JComboBox<>(unites);
		comboUnites.addActionListener(this);
		
		JButton btnConvertir = new JButton("Convertir");
		btnConvertir.addActionListener(this);
		
		JButton btnEffacer = new JButton("Effacer");
		btnEffacer.addActionListener(e -> {
			txtCelsius.setText("");
			txtFahrenheit.setText("");
			slider.setValue(0);
		});
		
		JPanel panelPrincipal = new JPanel(new GridLayout(5, 2, 10, 10));
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		panelPrincipal.add(lblCelsius);
		panelPrincipal.add(txtCelsius);
		panelPrincipal.add(lblFahrenheit);
		panelPrincipal.add(txtFahrenheit);
		panelPrincipal.add(lblSlider);
		panelPrincipal.add(slider);
		panelPrincipal.add(new JLabel("Convertion:"));
		panelPrincipal.add(comboUnites);
		panelPrincipal.add(btnConvertir);
		panelPrincipal.add(btnEffacer);
		
		add(panelPrincipal);
		setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == txtCelsius || e.getSource() == txtFahrenheit) {
			convertir();
		}else if(e.getSource() instanceof JComboBox){
			convertir();
		}
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if(!enCoursDeMiseAJour && e.getSource() == slider) {
			enCoursDeMiseAJour = true;
			
			int valeur = slider.getValue();
			
			if(comboUnites.getSelectedIndex() == 0) {
				txtCelsius.setText(String.valueOf(valeur));
				double fahrenheit = (valeur * 9 / 5.0) + 32;
				txtFahrenheit.setText(new DecimalFormat("#.##").format(fahrenheit));
			}else {
				txtFahrenheit.setText(String.valueOf(valeur));
				double celsius = (valeur - 32) * 5 / 9.0;
				txtCelsius.setText(new DecimalFormat("#.##").format(celsius));
			}
			
			enCoursDeMiseAJour = false;
			
		}
	}
	
	private void convertir() {
		try {
			if(comboUnites.getSelectedIndex() == 0) {
				double celsius = Double.parseDouble(txtCelsius.getText());
				double fahrenheit = (celsius * 9 / 5.0) + 32;
				txtFahrenheit.setText(new DecimalFormat("#.##").format(fahrenheit));
				slider.setValue((int) celsius);
			} else {
				double fahrenheit = Double.parseDouble(txtFahrenheit.getText());
				double celsius = (fahrenheit - 32) * 5 / 9.0;
				txtCelsius.setText(new DecimalFormat("#.##").format(celsius));
				slider.setValue((int) fahrenheit);
			}
		}catch(NumberFormatException ex) {
			JOptionPane.showMessageDialog(this,  "Veuillez entrer une valeur numérique valide");
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(ConvertisseurTemperature::new);
	}

}
