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
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;
import aii.Disciplina;
import aii.Utilizator;
import aii.Disciplina.Examinare;
import aii.Disciplina.TipDisciplina;
import aii.database.Constants;
import aii.database.DisciplinaWrapper;
import aii.gui.tools.ObjectTableModel;


@SuppressWarnings("serial")
public class AdminDisciplinePanel extends MainPanelAbstract implements ListSelectionListener, ActionListener {
	
	@SuppressWarnings("unused")
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
	private JSpinner spinnerAnStudiu;
	private JSpinner spinnerSemestru;
	private JComboBox comboBoxExaminare;
	private JSpinner spinnerPctCredit;
	private JSpinner spinnerNrOre;
	private JComboBox comboBoxTip;

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
		
		textFieldDenumire = new JTextField((String) null);
		textFieldDenumire.setColumns(10);
		textFieldDenumire.setBounds(144, 43, 260, 28);
		panelEditMainInfo.add(textFieldDenumire);
		
		comboBoxTip = new JComboBox();
		comboBoxTip.setModel(new DefaultComboBoxModel(Disciplina.TipDisciplina.values()));
		comboBoxTip.setBounds(144, 83, 260, 28);
		panelEditMainInfo.add(comboBoxTip);
		
		JLabel lblTipDisciplina = new JLabel("Tip Disciplina: *");
		lblTipDisciplina.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTipDisciplina.setBounds(18, 90, 108, 15);
		panelEditMainInfo.add(lblTipDisciplina);
		
		spinnerNrOre = new JSpinner();
		spinnerNrOre.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinnerNrOre.setBounds(542, 83, 48, 28);
		panelEditMainInfo.add(spinnerNrOre);
		
		JLabel lblNumarOre = new JLabel("Numar Ore: *");
		lblNumarOre.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNumarOre.setBounds(416, 89, 108, 15);
		panelEditMainInfo.add(lblNumarOre);
		
		spinnerPctCredit = new JSpinner();
		spinnerPctCredit.setModel(new SpinnerNumberModel(1, 1, 6, 1));
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
		
		comboBoxExaminare = new JComboBox();
		comboBoxExaminare.setModel(new DefaultComboBoxModel(Disciplina.Examinare.values()));
		comboBoxExaminare.setBounds(542, 0, 244, 28);
		panelEditMainInfo.add(comboBoxExaminare);
		
		JLabel lblAnStudiu = new JLabel("An Studiu: *");
		lblAnStudiu.setHorizontalAlignment(SwingConstants.TRAILING);
		lblAnStudiu.setBounds(416, 44, 108, 15);
		panelEditMainInfo.add(lblAnStudiu);
		
		spinnerAnStudiu = new JSpinner();
		spinnerAnStudiu.setModel(new SpinnerNumberModel(1, 1, 5, 1));
		spinnerAnStudiu.setBounds(542, 43, 48, 28);
		panelEditMainInfo.add(spinnerAnStudiu);
		
		JLabel lblSemestru = new JLabel("Semestru: *");
		lblSemestru.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSemestru.setBounds(602, 44, 111, 15);
		panelEditMainInfo.add(lblSemestru);
		
		spinnerSemestru = new JSpinner();
		spinnerSemestru.setModel(new SpinnerNumberModel(1, 1, 2, 1));
		spinnerSemestru.setBounds(731, 43, 55, 28);
		panelEditMainInfo.add(spinnerSemestru);
		
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
		textFieldCodDisciplina.setEnabled(false);	//if we create a new entity, we let the PK to be auto incremented
		textFieldCodDisciplina.setText("Auto-Generated");
		
		
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
		
		//Nothing selected / deselection
		if(row==-1)
		{
			textFieldCodDisciplina.setText(null);
			textFieldDenumire.setText(null);
			comboBoxTip.setSelectedItem(TipDisciplina.Obligatoriu);
			comboBoxExaminare.setSelectedItem(Examinare.Examen);
			spinnerAnStudiu.setValue(1);
			spinnerNrOre.setValue(1);
			spinnerPctCredit.setValue(1);
			spinnerSemestru.setValue(1);
			
			textFieldCodDisciplina.setEnabled(false);	//if we create a new entity, we let the PK to be auto incremented
			textFieldCodDisciplina.setText("Auto-Generated");
			
			return;
		}
		
		//Always Visible fields
		Disciplina object=objects.get(table.getSelectedRow());
		textFieldCodDisciplina.setText(Integer.toString(object.cod));
		textFieldDenumire.setText(object.denumire);
		comboBoxTip.setSelectedItem(object.tip);
		comboBoxExaminare.setSelectedItem(object.examinare);
		spinnerAnStudiu.setValue(object.anStudiu);
		spinnerNrOre.setValue(object.nrOre);
		spinnerPctCredit.setValue(object.pctCredit);
		spinnerSemestru.setValue(object.semestru);
		
		textFieldCodDisciplina.setEnabled(true);

		statusLbl.setText("Modfica datele disciplinei "+object.denumire+" si apasa 'Salveaza' pentru a face permanente modificarile.");
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
			System.out.println("Adding new Disciplina");
			table.getSelectionModel().clearSelection();
			statusLbl.setText("Completeaza campurile pentru a crea o noua disciplina si apasa 'Salveaza'");
		} else	
		if(source==btnSterge)
		{
			System.out.println("Stergem disciplina");
			
			if(table.getSelectedRow()==-1)
				return;
			
			//Delete the disciplina
			if(!disciplinaDAO.deleteDisciplina(objects.get(table.getSelectedRow())))
				return;
			
			statusLbl.setText("Disciplina "+objects.get(table.getSelectedRow()).denumire+" a fost stearsa.");
			
			//Update JTable
			objects.remove(table.getSelectedRow());
			tableModel.setObjects(objects);
			tableModel.fireTableDataChanged();
			
		} else
		if(source==btnSalveaza)
		{
			System.out.println("Salvam informatiile in DB");
			
			//Check fields
			if(textFieldDenumire.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(null, "Va rugam sa completati toate campurile obligatorii","Incomplet",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(textFieldCodDisciplina.getText().isEmpty() && 
					table.getSelectedRow()!=-1)	//if there's something selected
			{
				JOptionPane.showMessageDialog(null, "Va rugam sa completati toate campurile obligatorii","Incomplet",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//Create the new object
			Disciplina object=new Disciplina();
			object.denumire=textFieldDenumire.getText();
			object.tip=(TipDisciplina) comboBoxTip.getSelectedItem();
			object.examinare=(Examinare) comboBoxExaminare.getSelectedItem();
			object.anStudiu=(Integer) spinnerAnStudiu.getValue();
			object.nrOre=(Integer) spinnerNrOre.getValue();
			object.pctCredit=(Integer) spinnerPctCredit.getValue();
			object.semestru=(Integer) spinnerSemestru.getValue();
			
			//If it's a new entry
			if(table.getSelectedRow()==-1)
			{
				object.cod=0;
				
				//Insert the new user
				System.out.println("Disciplina noua: "+object);
				if(!disciplinaDAO.insertDisciplina(object))
					return;
			
				statusLbl.setText("S-a creat o disciplina noua.");
				
				//Update JTable - need new pull from database, as a new id was generated
				try {
					objects=disciplinaDAO.getObjects(Constants.DISCIPLINA_TABLE,"cod=cod");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				tableModel.setObjects(objects);
				tableModel.fireTableDataChanged();
				table.getSelectionModel().setSelectionInterval(0, objects.size()-1);
			}
			else //If it's an old entry
			{
				object.cod=Integer.parseInt(textFieldCodDisciplina.getText());
				
				System.out.println("Disciplina existenta -> modificata in " + object);
				if(!disciplinaDAO.updateDisciplina(objects.get(table.getSelectedRow()), object))
					return;
				
				statusLbl.setText("Disciplina "+object.denumire+" a fost actualizata.");
				
				//Update JTable
				int curSelected=table.getSelectedRow();
				objects.set(table.getSelectedRow(), object);
				tableModel.setObjects(objects);
				tableModel.fireTableDataChanged();	
				table.getSelectionModel().setSelectionInterval(0, curSelected);
			}
				
		}
		
	}
}
