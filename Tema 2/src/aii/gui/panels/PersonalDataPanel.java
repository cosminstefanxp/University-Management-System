package aii.gui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import aii.Utilizator;
import aii.Utilizator.Finantare;
import aii.Utilizator.Tip;
import aii.database.Constants;
import aii.database.UtilizatorWrapper;
import aii.gui.tools.FixedSizeDocument;

@SuppressWarnings("serial")
public class PersonalDataPanel extends MainPanelAbstract implements ActionListener {
	
	private JTextField textFieldNume;
	private JTextField textFieldPrenume;
	private JTextField textFieldEmail;
	private JTextField textFieldAdresa;
	private JTextField textFieldCNP;
	private JPasswordField passwordField;

	private JLabel statusLbl;
	
	/** The associated user. */
	private Utilizator utilizator;
	
	private UtilizatorWrapper utilizatorDAO=new UtilizatorWrapper();
	private JTextField textFieldTitluGrupa;
	private JComboBox comboBoxFinantare;
	
	/**
	 * Create the panel.
	 */
	public PersonalDataPanel(Utilizator utilizator, JLabel statusLbl) {
		
		this.utilizator=utilizator;
		this.statusLbl=statusLbl;
		
		this.statusLbl.setText("Modifica datele tale personale.");
		
		JLabel lblAiciPotiEdita = new JLabel("Aici poti edita datele tale personale:");
		lblAiciPotiEdita.setBounds(12, 25, 260, 15);
		add(lblAiciPotiEdita);
		
		JPanel panelFields = new JPanel();
		panelFields.setBounds(12, 52, 400, 273);
		add(panelFields);
		panelFields.setLayout(null);
		
		textFieldNume = new JTextField(utilizator.nume);
		textFieldNume.setBounds(115, 90, 260, 28);
		panelFields.add(textFieldNume);
		textFieldNume.setColumns(10);
		textFieldNume.setDocument(new FixedSizeDocument(Constants.FIELD_SIZE_NUME));
		textFieldNume.setText(utilizator.nume);
		
		JLabel lblNume = new JLabel("Nume: *");
		lblNume.setBounds(0, 100, 97, 15);
		panelFields.add(lblNume);
		lblNume.setHorizontalAlignment(SwingConstants.TRAILING);
		
		textFieldPrenume = new JTextField(utilizator.prenume);
		textFieldPrenume.setBounds(115, 135, 260, 28);
		panelFields.add(textFieldPrenume);
		textFieldPrenume.setColumns(10);
		textFieldPrenume.setDocument(new FixedSizeDocument(Constants.FIELD_SIZE_PRENUME));
		textFieldPrenume.setText(utilizator.prenume);
		
		JLabel lblPrenume = new JLabel("Prenume: *");
		lblPrenume.setBounds(0, 144, 97, 15);
		panelFields.add(lblPrenume);
		lblPrenume.setHorizontalAlignment(SwingConstants.TRAILING);
		
		textFieldEmail = new JTextField(utilizator.email);
		textFieldEmail.setBounds(115, 180, 260, 28);
		panelFields.add(textFieldEmail);
		textFieldEmail.setColumns(10);
		textFieldEmail.setDocument(new FixedSizeDocument(Constants.FIELD_SIZE_EMAIL));
		textFieldEmail.setText(utilizator.email);
		
		JLabel lblEmail = new JLabel("Email: *");
		lblEmail.setBounds(0, 188, 97, 15);
		panelFields.add(lblEmail);
		lblEmail.setHorizontalAlignment(SwingConstants.TRAILING);
		
		textFieldAdresa = new JTextField(utilizator.adresa);
		textFieldAdresa.setBounds(115, 225, 260, 28);
		panelFields.add(textFieldAdresa);
		textFieldAdresa.setColumns(10);
		textFieldAdresa.setDocument(new FixedSizeDocument(Constants.FIELD_SIZE_ADRESA));
		textFieldAdresa.setText(utilizator.adresa);
		
		JLabel lblAdresa = new JLabel("Adresa:");
		lblAdresa.setBounds(0, 232, 97, 15);
		panelFields.add(lblAdresa);
		lblAdresa.setHorizontalAlignment(SwingConstants.TRAILING);
		
		textFieldCNP = new JTextField(utilizator.CNP);
		textFieldCNP.setBounds(115, 0, 260, 28);
		panelFields.add(textFieldCNP);
		textFieldCNP.setColumns(10);
		textFieldCNP.setDocument(new FixedSizeDocument(Constants.FIELD_SIZE_CNP));
		textFieldCNP.setText(utilizator.CNP);
		
		JLabel lblCnp = new JLabel("CNP: *");
		lblCnp.setBounds(0, 12, 97, 15);
		panelFields.add(lblCnp);
		lblCnp.setHorizontalAlignment(SwingConstants.TRAILING);
		
		passwordField = new JPasswordField(this.utilizator.parola);
		passwordField.setBounds(115, 45, 260, 28);
		panelFields.add(passwordField);
		passwordField.setDocument(new FixedSizeDocument(Constants.FIELD_SIZE_PAROLA));
		passwordField.setText(utilizator.parola);
		
		JLabel lblParola = new JLabel("Parola: *");
		lblParola.setBounds(0, 56, 97, 15);
		panelFields.add(lblParola);
		lblParola.setHorizontalAlignment(SwingConstants.TRAILING);
		
		JButton btnSave = new JButton("Salveaza");
		btnSave.addActionListener(this);
		btnSave.setBounds(266, 330, 117, 25);
		add(btnSave);
		
		JPanel panelSideInfo = new JPanel();
		panelSideInfo.setBorder(null);
		panelSideInfo.setBounds(464, 52, 270, 226);
		add(panelSideInfo);
		panelSideInfo.setLayout(null);
		
		JLabel label = new JLabel("Tip Utilizator:");
		label.setBounds(87, 20, 97, 15);
		panelSideInfo.add(label);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		JComboBox comboBoxTip = new JComboBox();
		comboBoxTip.setModel(new DefaultComboBoxModel(new String[] {utilizator.tip.toString()}));
		comboBoxTip.setEnabled(false);
		comboBoxTip.setBounds(5, 47, 260, 34);
		panelSideInfo.add(comboBoxTip);
		
		if(utilizator.tip==Tip.STUDENT)
		{
			JLabel lblFormaFinantare = new JLabel("Forma Finantare:");
			lblFormaFinantare.setBounds(87, 160, 122, 15);
			panelSideInfo.add(lblFormaFinantare);
			lblFormaFinantare.setHorizontalAlignment(SwingConstants.CENTER);
			
			comboBoxFinantare = new JComboBox();
			comboBoxFinantare.setBounds(5, 187, 260, 34);
			comboBoxFinantare.setModel(new DefaultComboBoxModel(Utilizator.Finantare.values()));
			comboBoxFinantare.setSelectedItem(utilizator.finantare);
			comboBoxFinantare.setEnabled(false);
			panelSideInfo.add(comboBoxFinantare);
			
			JLabel lblTitluGrupa = new JLabel("Grupa:");
			lblTitluGrupa.setBounds(87, 93, 97, 15);
			panelSideInfo.add(lblTitluGrupa);
			lblTitluGrupa.setHorizontalAlignment(SwingConstants.CENTER);
			
			textFieldTitluGrupa = new JTextField(utilizator.titlu_grupa);
			textFieldTitluGrupa.setBounds(5, 120, 260, 28);
			textFieldTitluGrupa.setEnabled(false);
			panelSideInfo.add(textFieldTitluGrupa);
			textFieldTitluGrupa.setColumns(10);	
			textFieldTitluGrupa.setDocument(new FixedSizeDocument(Constants.FIELD_SIZE_GRUPA));
			textFieldTitluGrupa.setText(utilizator.titlu_grupa);
		}
		
		if(utilizator.tip==Tip.CADRU_DIDACTIC || utilizator.tip==Tip.SEF_CATEDRA)
		{
			JLabel lblTitluGrupa = new JLabel("Titlu:");
			lblTitluGrupa.setBounds(87, 93, 97, 15);
			panelSideInfo.add(lblTitluGrupa);
			lblTitluGrupa.setHorizontalAlignment(SwingConstants.CENTER);
			
			textFieldTitluGrupa = new JTextField(utilizator.titlu_grupa);
			textFieldTitluGrupa.setBounds(5, 120, 260, 28);
			panelSideInfo.add(textFieldTitluGrupa);
			textFieldTitluGrupa.setColumns(10);
			textFieldTitluGrupa.setDocument(new FixedSizeDocument(Constants.FIELD_SIZE_TITLU));
			textFieldTitluGrupa.setText(utilizator.titlu_grupa);
		}
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		//Check fields
		if(textFieldCNP.getText().isEmpty() ||
				textFieldNume.getText().isEmpty() ||
				textFieldPrenume.getText().isEmpty() ||
				passwordField.getPassword().length==0 ||
				textFieldEmail.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(null, "Va rugam sa completati toate campurile obligatorii","Incomplet",JOptionPane.ERROR_MESSAGE);
			return;
		}
		//Copy the old user
		Utilizator utilizatorCopy=utilizator.clone();
		
		//Create the new user
		utilizator.CNP=textFieldCNP.getText();
		utilizator.nume=textFieldNume.getText();
		utilizator.prenume=textFieldPrenume.getText();
		utilizator.parola=new String(passwordField.getPassword());
		utilizator.email=textFieldEmail.getText();
		utilizator.adresa=textFieldAdresa.getText();
		if(textFieldTitluGrupa!=null)
			utilizator.titlu_grupa=textFieldTitluGrupa.getText();
		if(comboBoxFinantare!=null)
			utilizator.finantare=(Finantare) comboBoxFinantare.getSelectedItem();
		
		System.out.println("Utilizator existent -> modificat in " + utilizator);
		if(!utilizatorDAO.UpdateUtilizator(utilizatorCopy,utilizator))
			return;
		
		statusLbl.setText("Utilizatorul "+utilizator.CNP+" a fost actualizat.");	
	}
	
}
