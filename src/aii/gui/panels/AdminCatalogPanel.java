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
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;
import aii.Activitate;
import aii.Disciplina;
import aii.Utilizator;
import aii.Activitate.TipActivitate;
import aii.Disciplina.TipDisciplina;
import aii.database.ActivitateWrapper;
import aii.database.Constants;
import aii.database.DisciplinaWrapper;
import aii.database.UtilizatorWrapper;
import aii.gui.tools.ObjectTableModel;
import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;


@SuppressWarnings("serial")
public class AdminCatalogPanel extends MainPanelAbstract implements ListSelectionListener, ActionListener {
	
	@SuppressWarnings("unused")
	private Utilizator utilizator;
	private JTable table;
	private ArrayList<Activitate> objects;
	private ArrayList<Disciplina> discipline;
	private ArrayList<Utilizator> utilizatori;
	private ObjectTableModel<Activitate> mainTableModel;
	private ObjectTableModel<Disciplina> disciplineTableModel;
	private ObjectTableModel<Utilizator> utilizatoriTableModel;
	private ActivitateWrapper activitatiDAO=new ActivitateWrapper();
	private DisciplinaWrapper disciplineDAO=new DisciplinaWrapper();
	private UtilizatorWrapper utilizatoriDAO=new UtilizatorWrapper();
	
	private JLabel statusLbl;
	private JButton btnSalveaza;
	private JButton btnSterge;
	private JButton btnAdauga;
	private JPanel panelEditInfo;
	private JTable tableActivitate;
	private JScrollPane scrollPaneStudent;
	private JTable tableStudent;
	private JPanel panelInfo;

	/**
	 * Create the panel.
	 */
	public AdminCatalogPanel(Utilizator utilizator, JLabel statusLbl) {
		this.utilizator=utilizator;
		this.statusLbl=statusLbl;
		this.statusLbl.setText("Administrare activitati de predare. Selecteaza valorile dorite pentru a crea o noua disciplina sau selecteaza un rand pentru a il modifica.");
		
		//Get the objects and prepare the table models		
		try {
			//Table model for "Activitate"
			objects=activitatiDAO.getObjects(Constants.ACTIVITATE_TABLE,"id=id");
			mainTableModel=new ObjectTableModel<Activitate>(Activitate.class,
					objects,
					Constants.ADMIN_ACTIVITATE_COLUMN_FIELD_MATCH[1],
					Constants.ADMIN_ACTIVITATE_COLUMN_FIELD_MATCH[0]);
			//Table model for "Disciplina"
			discipline=disciplineDAO.getObjects(Constants.DISCIPLINA_TABLE,"cod=cod");
			disciplineTableModel=new ObjectTableModel<Disciplina>(Disciplina.class,
					discipline,
					new String[] {"Cod","Denumire"},
					new String[] {"cod","denumire"});
			//Table model for "Utilizator"
			utilizatori=utilizatoriDAO.getObjects(Constants.USER_TABLE,"tip=\'CADRU_DIDACTIC\' OR tip=\'SEF_CATEDRA\'");
			utilizatoriTableModel=new ObjectTableModel<Utilizator>(Utilizator.class,
					utilizatori,
					new String[] {"CNP","Nume","Prenume"},
					new String[] {"CNP","nume","prenume"});
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//GUI
		setLayout(new MigLayout("", "[799.00px:1022.00px]", "[132.00px:280.00px][140.00px:325.00px]"));
		
		JScrollPane scrollPaneTable = new JScrollPane();
		add(scrollPaneTable, "cell 0 0,grow");
		
		table = new JTable(mainTableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(true);
		table.getSelectionModel().addListSelectionListener(this);
		scrollPaneTable.setViewportView(table);
		
		JPanel panelEdit = new JPanel();
		add(panelEdit, "cell 0 1,grow");
		panelEdit.setLayout(null);
		
		panelEditInfo = new JPanel();
		panelEditInfo.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Setari Disciplina", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panelEditInfo.setBounds(7, -4, 822, 308);
		panelEdit.add(panelEditInfo);
		panelEditInfo.setLayout(new MigLayout("", "[279.00px,grow][29.00][268.00px,grow][27.00][190.00,grow]", "[20px:20px][60.00px:218.00px,grow][30px:30px]"));
		
		JLabel lblActivitate = new JLabel("Activitate predare:");
		panelEditInfo.add(lblActivitate, "cell 0 0,growx,aligny center");
		
		JLabel lblStudent = new JLabel("Student:");
		panelEditInfo.add(lblStudent, "cell 2 0");
		
		JLabel lblData = new JLabel("Data (zi/luna/an):");
		panelEditInfo.add(lblData, "cell 4 0");
		
		JScrollPane scrollPaneActivitate = new JScrollPane();
		panelEditInfo.add(scrollPaneActivitate, "cell 0 1,grow");
		
		tableActivitate = new JTable(disciplineTableModel);
		tableActivitate.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableActivitate.setFillsViewportHeight(true);
		scrollPaneActivitate.setViewportView(tableActivitate);
		
		scrollPaneStudent = new JScrollPane();
		panelEditInfo.add(scrollPaneStudent, "cell 2 1,grow");
		
		tableStudent = new JTable(utilizatoriTableModel);
		tableStudent.setFillsViewportHeight(true);
		tableStudent.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaneStudent.setViewportView(tableStudent);
		
		panelInfo = new JPanel();
		panelEditInfo.add(panelInfo, "cell 4 1,grow");
		panelInfo.setLayout(null);
		
		JSpinner spinnerZi = new JSpinner();
		spinnerZi.setModel(new SpinnerNumberModel(1, 1, 31, 1));
		spinnerZi.setBounds(0, 0, 46, 33);
		panelInfo.add(spinnerZi);
		
		JLabel label_1 = new JLabel("/");
		label_1.setBounds(51, 6, 6, 18);
		panelInfo.add(label_1);
		
		JSpinner spinnerLuna = new JSpinner();
		spinnerLuna.setModel(new SpinnerNumberModel(1, 1, 12, 1));
		spinnerLuna.setBounds(62, 0, 46, 33);
		panelInfo.add(spinnerLuna);
		
		JLabel label_2 = new JLabel("/");
		label_2.setBounds(112, 6, 6, 18);
		panelInfo.add(label_2);
		
		JSpinner spinnerAn = new JSpinner();
		spinnerAn.setModel(new SpinnerNumberModel(2011, 2000, 2012, 1));
		spinnerAn.setBounds(123, 0, 64, 33);
		spinnerAn.setEditor(new JSpinner.NumberEditor(spinnerAn, "####"));
		panelInfo.add(spinnerAn);
		
		JLabel lblNota = new JLabel("Nota:");
		lblNota.setBounds(0, 45, 122, 15);
		panelInfo.add(lblNota);
		
		JComboBox comboBoxNota = new JComboBox();
		comboBoxNota.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		comboBoxNota.setBounds(0, 72, 187, 33);
		panelInfo.add(comboBoxNota);
		
		btnSalveaza = new JButton("Salveaza");
		panelEditInfo.add(btnSalveaza, "cell 4 2,alignx right,aligny bottom");
		btnSalveaza.addActionListener(this);
		
		btnSterge = new JButton("Sterge");
		btnSterge.addActionListener(this);
		btnSterge.setBounds(841, 60, 117, 25);
		panelEdit.add(btnSterge);
		
		btnAdauga = new JButton("Adauga");
		btnAdauga.addActionListener(this);
		btnAdauga.setBounds(841, 23, 117, 25);
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
		
		//Nothing selected / deselection
		if(row==-1)
		{
			System.out.println("Deselectie detectata.");
			tableStudent.getSelectionModel().clearSelection();
			tableActivitate.getSelectionModel().clearSelection();
			comboBoxTipActivitate.setSelectedItem(TipDisciplina.Obligatoriu);
			
			statusLbl.setText("Seteaza disciplina, cadrul didactic asociat si tipul activitatii si apasa 'Salveaza' pentru a crea o noua activitate didactica.");
			return;
		}
		
		//Always Visible fields
		Activitate object=objects.get(table.getSelectedRow());
		
		//Finding the associated 'Disciplina'
		int indexDisciplina=-1;
		for(int i=0;i<discipline.size();i++)
			if(discipline.get(i).cod==object.codDisciplina)
			{
				indexDisciplina=i;
				break;
			}
		if(indexDisciplina==-1)
		{
			JOptionPane.showMessageDialog(null, "Lipseste disciplina asociata.");
			return;
		}
		tableActivitate.getSelectionModel().setSelectionInterval(0, indexDisciplina);
		
		//Finding the associated 'Utilizator'
		int indexCadruDidactic=-1;
		for(int i=0;i<utilizatori.size();i++)
			if(utilizatori.get(i).CNP.equals(object.cnpCadruDidactic))
			{
				indexCadruDidactic=i;
				break;
			}
		if(indexCadruDidactic==-1)
		{
			JOptionPane.showMessageDialog(null, "Lipseste cadrul didactic asociat.");
			return;
		}
		tableStudent.getSelectionModel().setSelectionInterval(0, indexCadruDidactic);

		//Set the "Tip"
		comboBoxTipActivitate.setSelectedItem(object.tip);

		statusLbl.setText("Seteaza disciplina, cadrul didactic asociat si tipul activitatii si apasa 'Salveaza' pentru a face permanente modificarile.");
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
			System.out.println("Adaugam o noua activitate");
			table.getSelectionModel().clearSelection();
			tableStudent.getSelectionModel().clearSelection();
			tableActivitate.getSelectionModel().clearSelection();
			
			statusLbl.setText("Seteaza disciplina, cadrul didactic asociat si tipul activitatii si apasa 'Salveaza' pentru a crea o noua activitate didactica.");
		} else	
		if(source==btnSterge)
		{
			System.out.println("Stergem activitatea");
			
			if(table.getSelectedRow()==-1)
				return;
			
			//Delete the disciplina
			if(!activitatiDAO.deleteActivitate(objects.get(table.getSelectedRow())))
				return;
			
			statusLbl.setText("Activitatea "+objects.get(table.getSelectedRow()).id+" a fost stearsa.");
			
			//Update JTable
			objects.remove(table.getSelectedRow());
			mainTableModel.setObjects(objects);
			mainTableModel.fireTableDataChanged();
			
		} else
		if(source==btnSalveaza)
		{
			System.out.println("Salvam informatiile in DB");
			
			//Check fields
			if(tableStudent.getSelectedRow()==-1 ||
					tableActivitate.getSelectedRow()==-1)
			{
				JOptionPane.showMessageDialog(null, "Va rugam sa completati toate campurile obligatorii","Incomplet",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//Create the new object
			Activitate object=new Activitate();
			object.cnpCadruDidactic=utilizatori.get(tableStudent.getSelectedRow()).CNP;
			object.codDisciplina=discipline.get(tableActivitate.getSelectedRow()).cod;
			object.tip=(TipActivitate) comboBoxTipActivitate.getSelectedItem();
			
			//If it's a new entry
			if(table.getSelectedRow()==-1)
			{
				object.id=0;
				
				//Insert the new user
				System.out.println("Activitate noua: "+object);
				if(!activitatiDAO.insertActivitate(object))
					return;
			
				statusLbl.setText("S-a creat o activitate noua.");
				
				//Update JTable - need new pull from database, as a new id was generated
				try {
					objects=activitatiDAO.getObjects(Constants.ACTIVITATE_TABLE,"id=id");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				mainTableModel.setObjects(objects);
				mainTableModel.fireTableDataChanged();
				table.getSelectionModel().setSelectionInterval(0, objects.size()-1);
			}
			else //If it's an old entry
			{
				object.id=objects.get(table.getSelectedRow()).id;
				
				System.out.println("Activitate existenta -> modificata in " + object);
				if(!activitatiDAO.updateActivitate(objects.get(table.getSelectedRow()), object))
					return;
				
				statusLbl.setText("Activitatea "+object.id+" a fost actualizata.");
				
				//Update JTable
				int curSelected=table.getSelectedRow();
				objects.set(table.getSelectedRow(), object);
				mainTableModel.setObjects(objects);
				mainTableModel.fireTableDataChanged();	
				table.getSelectionModel().setSelectionInterval(0, curSelected);
			}
				
		}
		
	}
}
