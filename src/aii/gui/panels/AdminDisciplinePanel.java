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
import aii.Disciplina;
import aii.Utilizator;
import aii.Utilizator.Finantare;
import aii.Utilizator.Tip;
import aii.database.Constants;
import aii.database.DisciplinaWrapper;
import aii.gui.tools.ObjectTableModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;


@SuppressWarnings("serial")
public class AdminDisciplinePanel extends MainPanelAbstract implements ListSelectionListener, ActionListener {
	
	private Utilizator utilizator;
	private JTable table;
	private ArrayList<Disciplina> objects;
	private ObjectTableModel<Disciplina> tableModel;
	private DisciplinaWrapper disciplinaDAO=new DisciplinaWrapper();
	
	private JLabel statusLbl;
	private JTextField textFieldCodDisciplina;
	private JButton btnSalveaza;
	private JButton btnSterge;
	private JButton btnAdauga;
	private JPanel panelEditInfo;
	private JTextField textFieldDenumire;

	/**
	 * Create the panel.
	 */
	public AdminDisciplinePanel(Utilizator utilizator, JLabel statusLbl) {
		this.utilizator=utilizator;
		this.statusLbl=statusLbl;
		this.statusLbl.setText("Administrare discipline. Completeaza campurile pentru a crea o noua disciplina sau selecteaza un rand pentru a il modifica.");
		
		//Get the objects		
		try {
			objects=disciplinaDAO.getObjects(Constants.DISCIPLINA_TABLE,"cod=cod");
			tableModel=new ObjectTableModel<Disciplina>(Disciplina.class,
					objects,
					Constants.ADMIN_DISCIPLINA_COLUMN_FIELD_MATCH[1],
					Constants.ADMIN_DISCIPLINA_COLUMN_FIELD_MATCH[0]);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		setLayout(new MigLayout("", "[799.00px:1022.00px]", "[132.00px:370.00px][245.00px:243.00px]"));
		
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
		panelEditInfo.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Setari Disciplina", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panelEditInfo.setBounds(7, -4, 822, 242);
		panelEdit.add(panelEditInfo);
		panelEditInfo.setLayout(null);
		
		JPanel panelEditMainInfo = new JPanel();
		panelEditMainInfo.setBounds(12, 24, 786, 167);
		panelEditInfo.add(panelEditMainInfo);
		panelEditMainInfo.setLayout(null);
		
		textFieldCodDisciplina = new JTextField((String) null);
		textFieldCodDisciplina.setColumns(10);
		textFieldCodDisciplina.setBounds(144, 0, 260, 28);
		panelEditMainInfo.add(textFieldCodDisciplina);
		
		JLabel lblCodDisciplina = new JLabel("Cod Disciplina: *");
		lblCodDisciplina.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCodDisciplina.setBounds(12, 6, 114, 15);
		panelEditMainInfo.add(lblCodDisciplina);
		
		JLabel lblDenumire = new JLabel("Denumire: *");
		lblDenumire.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDenumire.setBounds(29, 49, 97, 15);
		panelEditMainInfo.add(lblDenumire);
		
		btnSalveaza = new JButton("Salveaza");
		btnSalveaza.addActionListener(this);
		btnSalveaza.setBounds(681, 203, 117, 25);
		panelEditInfo.add(btnSalveaza);
		
		btnSterge = new JButton("Sterge");
		btnSterge.addActionListener(this);
		btnSterge.setBounds(841, 60, 117, 25);
		panelEdit.add(btnSterge);
		
		btnAdauga = new JButton("Adauga");
		btnAdauga.addActionListener(this);
		btnAdauga.setBounds(841, 23, 117, 25);
		panelEdit.add(btnAdauga);
		
		
		//Some default values
		textFieldCodDisciplina.setText(null);
		
		textFieldDenumire = new JTextField((String) null);
		textFieldDenumire.setColumns(10);
		textFieldDenumire.setBounds(144, 43, 260, 28);
		panelEditMainInfo.add(textFieldDenumire);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(Disciplina.TipDisciplina.values()));
		comboBox.setBounds(144, 83, 260, 28);
		panelEditMainInfo.add(comboBox);
		
		JLabel lblTipDisciplina = new JLabel("Tip Disciplina: *");
		lblTipDisciplina.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTipDisciplina.setBounds(18, 90, 108, 15);
		panelEditMainInfo.add(lblTipDisciplina);
		
		JSpinner spinnerNrOre = new JSpinner();
		spinnerNrOre.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinnerNrOre.setBounds(542, 83, 48, 28);
		panelEditMainInfo.add(spinnerNrOre);
		
		JLabel lblNumarOre = new JLabel("Numar Ore: *");
		lblNumarOre.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNumarOre.setBounds(416, 89, 108, 15);
		panelEditMainInfo.add(lblNumarOre);
		
		JSpinner spinnerPctCredit = new JSpinner();
		spinnerPctCredit.setModel(new SpinnerNumberModel(1, 1, 5, 1));
		spinnerPctCredit.setBounds(731, 83, 55, 28);
		panelEditMainInfo.add(spinnerPctCredit);
		
		JLabel lblPuncteCredit = new JLabel("Puncte Credit: *");
		lblPuncteCredit.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPuncteCredit.setBounds(602, 89, 111, 15);
		panelEditMainInfo.add(lblPuncteCredit);
		
		JLabel lblFormaStudiu = new JLabel("Examinare: *");
		lblFormaStudiu.setHorizontalAlignment(SwingConstants.TRAILING);
		lblFormaStudiu.setBounds(416, 7, 108, 15);
		panelEditMainInfo.add(lblFormaStudiu);
		
		JComboBox comboBoxExaminare = new JComboBox();
		comboBoxExaminare.setModel(new DefaultComboBoxModel(Disciplina.Examinare.values()));
		comboBoxExaminare.setBounds(542, 0, 244, 28);
		panelEditMainInfo.add(comboBoxExaminare);
		
		JLabel lblAnStudiu = new JLabel("An Studiu: *");
		lblAnStudiu.setHorizontalAlignment(SwingConstants.TRAILING);
		lblAnStudiu.setBounds(416, 44, 108, 15);
		panelEditMainInfo.add(lblAnStudiu);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(1, 1, 5, 1));
		spinner.setBounds(542, 43, 48, 28);
		panelEditMainInfo.add(spinner);
		
		JLabel lblSemestru = new JLabel("Semestru: *");
		lblSemestru.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSemestru.setBounds(602, 44, 111, 15);
		panelEditMainInfo.add(lblSemestru);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(1, 1, 2, 1));
		spinner_1.setBounds(731, 43, 55, 28);
		panelEditMainInfo.add(spinner_1);
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
			textFieldCodDisciplina.setText(null);
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
		Utilizator object=null;//=objects.get(row);
		textFieldCodDisciplina.setText(object.CNP);
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
			//if(!utilizatorDAO.deleteUtilizator(objects.get(table.getSelectedRow())))
				//return;
			
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
			if(textFieldCodDisciplina.getText().isEmpty() ||
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
			object.CNP=textFieldCodDisciplina.getText();
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
				//if(!utilizatorDAO.insertUtilizator(object))
					//return;
			
				statusLbl.setText("S-a creat un utilizator nou.");
				//Update JTable
//				objects.add(object);
				tableModel.setObjects(objects);
				tableModel.fireTableDataChanged();
				table.getSelectionModel().setSelectionInterval(0, objects.size()-1);
			}
			else //If it's an old entry
			{
				System.out.println("Utilizator existent -> modificat in " + object);
			//	if(!utilizatorDAO.UpdateUtilizator(objects.get(table.getSelectedRow()), object))
				//	return;
				
				statusLbl.setText("Utilizatorul "+utilizator.CNP+" a fost actualizat.");
				//Update JTable
//				objects.set(table.getSelectedRow(), object);
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
