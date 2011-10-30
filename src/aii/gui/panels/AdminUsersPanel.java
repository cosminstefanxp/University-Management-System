package aii.gui.panels;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;
import aii.Utilizator;
import aii.Utilizator.Tip;
import aii.database.Constants;
import aii.database.UtilizatorWrapper;
import aii.gui.tools.ObjectTableModel;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;


@SuppressWarnings("serial")
public class AdminUsersPanel extends MainPanelAbstract implements ListSelectionListener, ActionListener {
	private JTable table;
	private ArrayList<Utilizator> objects;
	private ObjectTableModel<Utilizator> tableModel;
	private JTextField textFieldNume;
	private JTextField textFieldPrenume;
	private JTextField textFieldEmail;
	private JTextField textFieldAdresa;
	private JTextField textFieldCNP;
	private JPasswordField passwordField;
	private JTextField textFieldTitluGrupa;
	private JComboBox comboBoxTipUtilizator;
	private JComboBox comboBoxFinantare;
	private JLabel lblTitluGrupa;
	private JLabel lblFormaFinantare;
	private JButton btnSalveaza;
	private JButton btnSterge;
	private JButton btnAdauga;
	private JPanel panelEditInfo;

	/**
	 * Create the panel.
	 */
	public AdminUsersPanel() {
		//Get the objects
		UtilizatorWrapper utilizatorDAO=new UtilizatorWrapper();
		
		try {
			objects=utilizatorDAO.getObjects(Constants.USER_TABLE,"cnp=cnp");
			tableModel=new ObjectTableModel<Utilizator>(Utilizator.class,
					objects,
					Constants.ADMIN_USER_COLUMN_FIELD_MATCH[1],
					Constants.ADMIN_USER_COLUMN_FIELD_MATCH[0]);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		setLayout(new MigLayout("", "[799.00px:1022.00px]", "[201.00px:315px][301.00px:313px]"));
		
		JScrollPane scrollPaneTable = new JScrollPane();
		add(scrollPaneTable, "cell 0 0,grow");
		
		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(true);
		table.getSelectionModel().addListSelectionListener(this);
		scrollPaneTable.setViewportView(table);
		
		JPanel panelEdit = new JPanel();
		add(panelEdit, "cell 0 1,grow");
		panelEdit.setLayout(null);
		
		panelEditInfo = new JPanel();
		panelEditInfo.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Setari Utilizator", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panelEditInfo.setBounds(7, -4, 744, 309);
		panelEdit.add(panelEditInfo);
		panelEditInfo.setLayout(null);
		
		JPanel panelEditMainInfo = new JPanel();
		panelEditMainInfo.setBounds(12, 24, 400, 273);
		panelEditInfo.add(panelEditMainInfo);
		panelEditMainInfo.setLayout(null);
		
		textFieldNume = new JTextField((String) null);
		textFieldNume.setColumns(10);
		textFieldNume.setBounds(115, 90, 260, 28);
		panelEditMainInfo.add(textFieldNume);
		
		JLabel label = new JLabel("Nume:");
		label.setHorizontalAlignment(SwingConstants.TRAILING);
		label.setBounds(0, 100, 97, 15);
		panelEditMainInfo.add(label);
		
		textFieldPrenume = new JTextField((String) null);
		textFieldPrenume.setColumns(10);
		textFieldPrenume.setBounds(115, 135, 260, 28);
		panelEditMainInfo.add(textFieldPrenume);
		
		JLabel label_1 = new JLabel("Prenume:");
		label_1.setHorizontalAlignment(SwingConstants.TRAILING);
		label_1.setBounds(0, 144, 97, 15);
		panelEditMainInfo.add(label_1);
		
		textFieldEmail = new JTextField((String) null);
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(115, 180, 260, 28);
		panelEditMainInfo.add(textFieldEmail);
		
		JLabel label_2 = new JLabel("Email:");
		label_2.setHorizontalAlignment(SwingConstants.TRAILING);
		label_2.setBounds(0, 188, 97, 15);
		panelEditMainInfo.add(label_2);
		
		textFieldAdresa = new JTextField((String) null);
		textFieldAdresa.setColumns(10);
		textFieldAdresa.setBounds(115, 225, 260, 28);
		panelEditMainInfo.add(textFieldAdresa);
		
		JLabel label_3 = new JLabel("Adresa:");
		label_3.setHorizontalAlignment(SwingConstants.TRAILING);
		label_3.setBounds(0, 232, 97, 15);
		panelEditMainInfo.add(label_3);
		
		textFieldCNP = new JTextField((String) null);
		textFieldCNP.setColumns(10);
		textFieldCNP.setBounds(115, 0, 260, 28);
		panelEditMainInfo.add(textFieldCNP);
		
		JLabel label_4 = new JLabel("CNP:");
		label_4.setHorizontalAlignment(SwingConstants.TRAILING);
		label_4.setBounds(0, 12, 97, 15);
		panelEditMainInfo.add(label_4);
		
		passwordField = new JPasswordField((String) null);
		passwordField.setBounds(115, 45, 260, 28);
		panelEditMainInfo.add(passwordField);
		
		JLabel label_5 = new JLabel("Parola:");
		label_5.setHorizontalAlignment(SwingConstants.TRAILING);
		label_5.setBounds(0, 56, 97, 15);
		panelEditMainInfo.add(label_5);
		
		JPanel panelEditSideInfo = new JPanel();
		panelEditSideInfo.setBounds(473, 24, 260, 202);
		panelEditInfo.add(panelEditSideInfo);
		panelEditSideInfo.setLayout(null);
		
		comboBoxFinantare = new JComboBox();
		comboBoxFinantare.setBounds(0, 168, 260, 34);
		panelEditSideInfo.add(comboBoxFinantare);
		comboBoxFinantare.setModel(new DefaultComboBoxModel(Utilizator.Finantare.values()));
		comboBoxFinantare.setEditable(true);
		
		lblFormaFinantare = new JLabel("Forma Finantare:");
		lblFormaFinantare.setBounds(69, 141, 122, 15);
		panelEditSideInfo.add(lblFormaFinantare);
		lblFormaFinantare.setHorizontalAlignment(SwingConstants.CENTER);
		
		textFieldTitluGrupa = new JTextField((String) null);
		textFieldTitluGrupa.setBounds(0, 100, 260, 28);
		panelEditSideInfo.add(textFieldTitluGrupa);
		textFieldTitluGrupa.setColumns(10);
		
		lblTitluGrupa = new JLabel("Grupa:");
		lblTitluGrupa.setBounds(82, 73, 97, 15);
		panelEditSideInfo.add(lblTitluGrupa);
		lblTitluGrupa.setHorizontalAlignment(SwingConstants.CENTER);
		
		comboBoxTipUtilizator = new JComboBox();
		comboBoxTipUtilizator.setBounds(0, 27, 260, 34);
		panelEditSideInfo.add(comboBoxTipUtilizator);
		comboBoxTipUtilizator.setModel(new DefaultComboBoxModel(Utilizator.Tip.values()));
		comboBoxTipUtilizator.setEditable(true);
		
		JLabel label_6 = new JLabel("Tip Utilizator:");
		label_6.setBounds(82, 0, 97, 15);
		panelEditSideInfo.add(label_6);
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		
		comboBoxFinantare.setVisible(false);
		lblFormaFinantare.setVisible(false);
		lblTitluGrupa.setVisible(false);
		textFieldTitluGrupa.setVisible(false);
		comboBoxTipUtilizator.setSelectedItem(null);
		
		btnSalveaza = new JButton("Salveaza");
		btnSalveaza.setBounds(616, 272, 117, 25);
		panelEditInfo.add(btnSalveaza);
		
		btnSterge = new JButton("Sterge");
		btnSterge.setBounds(788, 65, 117, 25);
		panelEdit.add(btnSterge);
		
		btnAdauga = new JButton("Adauga");
		btnAdauga.addActionListener(this);
		btnAdauga.setBounds(788, 16, 117, 25);
		panelEdit.add(btnAdauga);
	}

	/* Eveniment declansat la schimbarea selectiei
	 * (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged(ListSelectionEvent event) {
		if (event.getValueIsAdjusting())
            return;
        
		int row = table.getSelectedRow();
		System.out.println("Selectie modificata pe randul "+row);
		
		if(row==-1)
		{
			textFieldCNP.setText(null);
			textFieldNume.setText(null);
			textFieldPrenume.setText(null);
			textFieldAdresa.setText(null);
			textFieldEmail.setText(null);
			passwordField.setText(null);
			comboBoxTipUtilizator.setSelectedItem(null);
			comboBoxFinantare.setSelectedItem(null);
			textFieldTitluGrupa.setText(null);
			
			comboBoxFinantare.setVisible(false);
			lblFormaFinantare.setVisible(false);
			lblTitluGrupa.setVisible(false);
			textFieldTitluGrupa.setVisible(false);
			
			return;
		}
		
		//Always Visible fields
		Utilizator object=objects.get(row);
		textFieldCNP.setText(object.CNP);
		textFieldNume.setText(object.nume);
		textFieldPrenume.setText(object.prenume);
		textFieldAdresa.setText(object.adresa);
		textFieldEmail.setText(object.email);
		passwordField.setText(object.parola);
		comboBoxTipUtilizator.setSelectedItem(object.tip);
		comboBoxFinantare.setSelectedItem(null);
		textFieldTitluGrupa.setText(null);
		
		//Per-type fields
		if(object.tip==Tip.STUDENT)
		{
			comboBoxFinantare.setSelectedItem(object.finantare);
			comboBoxFinantare.setVisible(true);
			lblFormaFinantare.setVisible(true);
			
			textFieldTitluGrupa.setText(object.titlu_grupa);
			lblTitluGrupa.setText("Grupa");
			lblTitluGrupa.setVisible(true);
			textFieldTitluGrupa.setVisible(true);
		}
		else if(object.tip==Tip.CADRU_DIDACTIC || object.tip==Tip.SEF_CATEDRA)
		{
			lblTitluGrupa.setText("Titlu");
			lblTitluGrupa.setVisible(true);
			textFieldTitluGrupa.setVisible(true);
			
			comboBoxFinantare.setVisible(false);
			lblFormaFinantare.setVisible(false);
		}
		else
		{
			comboBoxFinantare.setVisible(false);
			lblFormaFinantare.setVisible(false);
			lblTitluGrupa.setVisible(false);
			textFieldTitluGrupa.setVisible(false);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		//Adding new entities
		if(source==btnAdauga)
		{
			System.out.println("Adding new User");
			table.getSelectionModel().clearSelection();
		}
		
	}
	
	
}
