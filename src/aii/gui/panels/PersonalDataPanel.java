package aii.gui.panels;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class PersonalDataPanel extends MainPanelAbstract {
	private JTextField textFieldNume;
	private JTextField textFieldPrenume;
	private JTextField textFieldEmail;
	private JTextField textFieldAdresa;
	private JTextField textFieldCNP;
	private JPasswordField passwordField;

	/**
	 * Create the panel.
	 */
	public PersonalDataPanel() {
		
		JLabel lblAiciPotiEdita = new JLabel("Aici poti edita datele tale personale:");
		lblAiciPotiEdita.setBounds(12, 25, 260, 15);
		add(lblAiciPotiEdita);
		
		JPanel panelFields = new JPanel();
		panelFields.setBounds(12, 52, 400, 273);
		add(panelFields);
		panelFields.setLayout(null);
		
		textFieldNume = new JTextField();
		textFieldNume.setBounds(115, 90, 260, 28);
		panelFields.add(textFieldNume);
		textFieldNume.setColumns(10);
		
		JLabel lblNume = new JLabel("Nume:");
		lblNume.setBounds(0, 100, 97, 15);
		panelFields.add(lblNume);
		lblNume.setHorizontalAlignment(SwingConstants.TRAILING);
		
		textFieldPrenume = new JTextField();
		textFieldPrenume.setBounds(115, 135, 260, 28);
		panelFields.add(textFieldPrenume);
		textFieldPrenume.setColumns(10);
		
		JLabel lblPrenume = new JLabel("Prenume:");
		lblPrenume.setBounds(0, 144, 97, 15);
		panelFields.add(lblPrenume);
		lblPrenume.setHorizontalAlignment(SwingConstants.TRAILING);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(115, 180, 260, 28);
		panelFields.add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(0, 188, 97, 15);
		panelFields.add(lblEmail);
		lblEmail.setHorizontalAlignment(SwingConstants.TRAILING);
		
		textFieldAdresa = new JTextField();
		textFieldAdresa.setBounds(115, 225, 260, 28);
		panelFields.add(textFieldAdresa);
		textFieldAdresa.setColumns(10);
		
		JLabel lblAdresa = new JLabel("Adresa:");
		lblAdresa.setBounds(0, 232, 97, 15);
		panelFields.add(lblAdresa);
		lblAdresa.setHorizontalAlignment(SwingConstants.TRAILING);
		
		textFieldCNP = new JTextField();
		textFieldCNP.setBounds(115, 0, 260, 28);
		panelFields.add(textFieldCNP);
		textFieldCNP.setColumns(10);
		
		JLabel lblCnp = new JLabel("CNP:");
		lblCnp.setBounds(0, 12, 97, 15);
		panelFields.add(lblCnp);
		lblCnp.setHorizontalAlignment(SwingConstants.TRAILING);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(115, 45, 260, 28);
		panelFields.add(passwordField);
		
		JLabel lblParola = new JLabel("Parola:");
		lblParola.setBounds(0, 56, 97, 15);
		panelFields.add(lblParola);
		lblParola.setHorizontalAlignment(SwingConstants.TRAILING);
		
		JButton btnSave = new JButton("Salveaza");
		btnSave.setBounds(266, 330, 117, 25);
		add(btnSave);
	}
}
