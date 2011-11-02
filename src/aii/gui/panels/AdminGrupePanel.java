package aii.gui.panels;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;
import aii.Utilizator;
import aii.Utilizator.Finantare;
import aii.Utilizator.Tip;
import aii.database.Constants;
import aii.database.UtilizatorWrapper;
import aii.gui.tools.ObjectTableModel;


@SuppressWarnings("serial")
public class AdminGrupePanel extends MainPanelAbstract implements ListSelectionListener, ActionListener {
	
	private Utilizator utilizator;
	private JTable table;
	private ArrayList<Utilizator> objects;
	private ObjectTableModel<Utilizator> tableModel;
	private UtilizatorWrapper utilizatorDAO=new UtilizatorWrapper();
	
	private JLabel statusLbl;
	
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
	public AdminGrupePanel(Utilizator utilizator, JLabel statusLbl) {
		this.utilizator=utilizator;
		this.statusLbl=statusLbl;
		this.statusLbl.setText("Administrare utilizatori ca "+utilizator.tip+". Completeaza campurile pentru a crea un nou utilizator sau selecteaza un rand pentru a il modifica.");
		
		//Get the objects		
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
		setLayout(new MigLayout("", "[799.00px:1022.00px]", "[132.00px:306.00px][301.00px:313px]"));
		
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
		
		JLabel label = new JLabel("Nume: *");
		label.setHorizontalAlignment(SwingConstants.TRAILING);
		label.setBounds(0, 100, 97, 15);
		panelEditMainInfo.add(label);
		
		textFieldPrenume = new JTextField((String) null);
		textFieldPrenume.setColumns(10);
		textFieldPrenume.setBounds(115, 135, 260, 28);
		panelEditMainInfo.add(textFieldPrenume);
		
		JLabel label_1 = new JLabel("Prenume: *");
		label_1.setHorizontalAlignment(SwingConstants.TRAILING);
		label_1.setBounds(0, 144, 97, 15);
		panelEditMainInfo.add(label_1);
		
		textFieldEmail = new JTextField((String) null);
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(115, 180, 260, 28);
		panelEditMainInfo.add(textFieldEmail);
		
		JLabel label_2 = new JLabel("Email: *");
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
		
		JLabel label_4 = new JLabel("CNP: *");
		label_4.setHorizontalAlignment(SwingConstants.TRAILING);
		label_4.setBounds(0, 12, 97, 15);
		panelEditMainInfo.add(label_4);
		
		passwordField = new JPasswordField((String) null);
		passwordField.setBounds(115, 45, 260, 28);
		panelEditMainInfo.add(passwordField);
		
		JLabel label_5 = new JLabel("Parola: *");
		label_5.setHorizontalAlignment(SwingConstants.TRAILING);
		label_5.setBounds(0, 56, 97, 15);
		panelEditMainInfo.add(label_5);
		
		JPanel panelEditSideInfo = new JPanel();
		panelEditSideInfo.setBounds(465, 24, 268, 215);
		panelEditInfo.add(panelEditSideInfo);
		panelEditSideInfo.setLayout(null);
		
		comboBoxFinantare = new JComboBox();
		comboBoxFinantare.setBounds(0, 168, 260, 35);
		panelEditSideInfo.add(comboBoxFinantare);
		comboBoxFinantare.setModel(new DefaultComboBoxModel(Utilizator.Finantare.values()));
		
		lblFormaFinantare = new JLabel("Forma Finantare:");
		lblFormaFinantare.setBounds(69, 141, 122, 15);
		panelEditSideInfo.add(lblFormaFinantare);
		lblFormaFinantare.setHorizontalAlignment(SwingConstants.CENTER);
		
		textFieldTitluGrupa = new JTextField((String) null);
		textFieldTitluGrupa.setBounds(0, 100, 260, 28);
		panelEditSideInfo.add(textFieldTitluGrupa);
		textFieldTitluGrupa.setColumns(10);
		
		lblTitluGrupa = new JLabel("Grupa:");
		lblTitluGrupa.setBounds(64, 73, 132, 15);
		panelEditSideInfo.add(lblTitluGrupa);
		lblTitluGrupa.setHorizontalAlignment(SwingConstants.CENTER);
		
		comboBoxTipUtilizator = new JComboBox();
		comboBoxTipUtilizator.addActionListener(this);
		comboBoxTipUtilizator.setBounds(0, 27, 260, 34);
		panelEditSideInfo.add(comboBoxTipUtilizator);
		comboBoxTipUtilizator.setModel(new DefaultComboBoxModel(Utilizator.Tip.values()));
		
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
		btnSalveaza.addActionListener(this);
		btnSalveaza.setBounds(616, 272, 117, 25);
		panelEditInfo.add(btnSalveaza);
		
		btnSterge = new JButton("Sterge");
		btnSterge.addActionListener(this);
		btnSterge.setBounds(788, 65, 117, 25);
		panelEdit.add(btnSterge);
		
		btnAdauga = new JButton("Adauga");
		btnAdauga.addActionListener(this);
		btnAdauga.setBounds(788, 16, 117, 25);
		panelEdit.add(btnAdauga);
		
		
		//Some default values
		textFieldCNP.setText(null);
		textFieldNume.setText(null);
		textFieldPrenume.setText(null);
		textFieldAdresa.setText(null);
		textFieldEmail.setText(null);
		passwordField.setText(null);
		comboBoxTipUtilizator.setSelectedItem(Tip.SECRETAR);
		comboBoxFinantare.setSelectedItem(null);
		textFieldTitluGrupa.setText(null);
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
			comboBoxTipUtilizator.setSelectedItem(Tip.SECRETAR);
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

		statusLbl.setText("Modfica datele utilizatorului "+object.CNP+" si apasa 'Salveaza' pentru a face permanente modificarile.");
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
			lblTitluGrupa.setText("Titulatura");
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
		
		//Extra-case for super admin
		if(object.tip==Tip.SUPER_ADMIN && utilizator.tip!=Tip.SUPER_ADMIN)
		{
			btnSalveaza.setEnabled(false);
			statusLbl.setText("Conturile de super-admin nu pot fi editate.");
		}
		else
			btnSalveaza.setEnabled(true);
		
	}

	/* Evenimente declansate la click pe cele 3 butoane sau la schimbarea selectiei tipului
	 *  (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		//Adding new entities
		if(source==btnAdauga)
		{
			System.out.println("Adding new User");
			table.getSelectionModel().clearSelection();
			statusLbl.setText("Completeaza campurile pentru a crea un nou utilizator si apasa 'Salveaza'");
		} else	
		if(source==btnSterge)
		{
			System.out.println("Stergem utilizatorul");
			
			if(table.getSelectedRow()==-1)
				return;
			
			//Delete the user
			if(!utilizatorDAO.deleteUtilizator(objects.get(table.getSelectedRow())))
				return;
			
			statusLbl.setText("Utilizatorul "+utilizator.CNP+" a fost sters.");
			
			//Update JTable
			objects.remove(table.getSelectedRow());
			tableModel.setObjects(objects);
			tableModel.fireTableDataChanged();
			
		} else
		if(source==btnSalveaza)
		{
			System.out.println("Salvam informatiile in DB");
			
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
			
			//Create the new user
			Utilizator object=new Utilizator();
			object.CNP=textFieldCNP.getText();
			object.nume=textFieldNume.getText();
			object.prenume=textFieldPrenume.getText();
			object.parola=new String(passwordField.getPassword());
			object.email=textFieldEmail.getText();
			object.adresa=textFieldAdresa.getText();
			object.titlu_grupa=textFieldTitluGrupa.getText();
			object.tip=(Tip) comboBoxTipUtilizator.getSelectedItem();
			object.finantare=(Finantare) comboBoxFinantare.getSelectedItem();
			
			//If it's a new entry
			if(table.getSelectedRow()==-1)
			{
				//Insert the new user
				System.out.println("Utilizator nou: "+object);
				if(!utilizatorDAO.insertUtilizator(object))
					return;
			
				statusLbl.setText("S-a creat un utilizator nou.");
				//Update JTable
				objects.add(object);
				tableModel.setObjects(objects);
				tableModel.fireTableDataChanged();
				table.getSelectionModel().setSelectionInterval(0, objects.size()-1);
			}
			else //If it's an old entry
			{
				System.out.println("Utilizator existent -> modificat in " + object);
				if(!utilizatorDAO.UpdateUtilizator(objects.get(table.getSelectedRow()), object))
					return;
				
				statusLbl.setText("Utilizatorul "+utilizator.CNP+" a fost actualizat.");
				//Update JTable
				objects.set(table.getSelectedRow(), object);
				tableModel.setObjects(objects);
				tableModel.fireTableDataChanged();	
			}
				
		}
		else if(source==comboBoxTipUtilizator)
		{
			System.out.println("Schimbat selectie tip.");
			//If unauthorized
			if(utilizator.tip!=Tip.SUPER_ADMIN && (comboBoxTipUtilizator.getSelectedItem()==Tip.ADMIN || comboBoxTipUtilizator.getSelectedItem()==Tip.SUPER_ADMIN))
			{
				//If existing user
				if(table.getSelectedRow()!=-1 && comboBoxTipUtilizator.getSelectedItem()!=objects.get(table.getSelectedRow()).tip)
				{
					JOptionPane.showMessageDialog(null,"Nu aveti dreptul de a crea administratori!","Neautorizat",JOptionPane.ERROR_MESSAGE);
					comboBoxTipUtilizator.setSelectedItem(objects.get(table.getSelectedRow()).tip);
				}
				//If new user
				if(table.getSelectedRow()==-1)
				{
					JOptionPane.showMessageDialog(null,"Nu aveti dreptul de a crea administratori!","Neautorizat",JOptionPane.ERROR_MESSAGE);
					comboBoxTipUtilizator.setSelectedItem(Tip.STUDENT);
				}
			}
			//Per-type fields
			if(comboBoxTipUtilizator.getSelectedItem()==Tip.STUDENT)
			{
				comboBoxFinantare.setSelectedItem(Finantare.Buget);
				comboBoxFinantare.setVisible(true);
				lblFormaFinantare.setVisible(true);
				
				textFieldTitluGrupa.setText(null);
				lblTitluGrupa.setText("Grupa");
				lblTitluGrupa.setVisible(true);
				textFieldTitluGrupa.setVisible(true);
			}
			else if(comboBoxTipUtilizator.getSelectedItem()==Tip.CADRU_DIDACTIC || comboBoxTipUtilizator.getSelectedItem()==Tip.SEF_CATEDRA)
			{
				lblTitluGrupa.setText("Titulatura");
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
		
	}
}
