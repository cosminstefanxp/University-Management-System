package aii.gui.panels;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;
import aii.Activitate;
import aii.Constants;
import aii.NotaCatalog;
import aii.Utilizator;
import aii.arhiva.Arhiva;
import aii.gui.tools.ObjectTableModel;
import aii.rad.RegistruActivitatiDidactice;


@SuppressWarnings("serial")
public class AdminCatalogPanel extends MainPanelAbstract implements ListSelectionListener, ActionListener {
	
	private JTable table;
	private ArrayList<NotaCatalog> objects;
	private ArrayList<Activitate> activitati;
	private ArrayList<Utilizator> utilizatori;
	private ObjectTableModel<NotaCatalog> mainTableModel;
	private ObjectTableModel<Activitate> activitatiTableModel;
	private ObjectTableModel<Utilizator> utilizatoriTableModel;

	private JButton btnSalveaza;
	private JButton btnSterge;
	private JButton btnAdauga;
	private JPanel panelEditInfo;
	private JTable tableActivitate;
	private JScrollPane scrollPaneStudent;
	private JTable tableStudent;
	private JPanel panelInfo;
	private JComboBox comboBoxNota;
	private JSpinner spinnerAn;
	private JSpinner spinnerZi;
	private JSpinner spinnerLuna;

	/**
	 * Create the panel.
	 */
	public AdminCatalogPanel(Arhiva arhivaService, RegistruActivitatiDidactice radService,
			Utilizator utilizator, JLabel statusLabel) {
		//Initialize the MainPanelAbstract object
		super(arhivaService, radService, utilizator, statusLabel);
		
		this.statusLbl.setText("Administrare note catalog. Selecteaza valorile dorite pentru a crea o noua intrare in catalog sau selecteaza un rand pentru a il modifica.");
		
		//Get the objects and prepare the table models		
		try {
			//Table model for "Note"
			objects=arhivaService.obtineNote();
			mainTableModel=new ObjectTableModel<NotaCatalog>(NotaCatalog.class,
					objects,
					Constants.ADMIN_CATALOG_COLUMN_FIELD_MATCH[1],
					Constants.ADMIN_CATALOG_COLUMN_FIELD_MATCH[0]);
			
			//Table model for "Activitate"
			activitati=radService.obtinereActivitatiPredareCursCadru(utilizator.CNP);
			activitatiTableModel=new ObjectTableModel<Activitate>(Activitate.class,
					activitati, 
					new String[] {"Cod disciplina", "Nume Disciplina"},
					new String[] {"codDisciplina", "denumireDisciplina"});
			//Table model for "Utilizator"
			utilizatori=radService.obtineUtilizatori("tip=\'STUDENT\'");
			utilizatoriTableModel=new ObjectTableModel<Utilizator>(Utilizator.class,
					utilizatori,
					new String[] {"CNP","Nume","Prenume"},
					new String[] {"CNP","nume","prenume"});
			
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
		
		tableActivitate = new JTable(activitatiTableModel);
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
		
		spinnerZi = new JSpinner();
		spinnerZi.setModel(new SpinnerNumberModel(1, 1, 31, 1));
		spinnerZi.setBounds(0, 0, 46, 33);
		panelInfo.add(spinnerZi);
		
		JLabel label_1 = new JLabel("/");
		label_1.setBounds(51, 6, 6, 18);
		panelInfo.add(label_1);
		
		spinnerLuna = new JSpinner();
		spinnerLuna.setModel(new SpinnerNumberModel(1, 1, 12, 1));
		spinnerLuna.setBounds(62, 0, 46, 33);
		panelInfo.add(spinnerLuna);
		
		JLabel label_2 = new JLabel("/");
		label_2.setBounds(112, 6, 6, 18);
		panelInfo.add(label_2);
		
		spinnerAn = new JSpinner();
		spinnerAn.setModel(new SpinnerNumberModel(2011, 2000, 2012, 1));
		spinnerAn.setBounds(123, 0, 64, 33);
		spinnerAn.setEditor(new JSpinner.NumberEditor(spinnerAn, "####"));
		panelInfo.add(spinnerAn);
		
		JLabel lblNota = new JLabel("Nota:");
		lblNota.setBounds(0, 45, 122, 15);
		panelInfo.add(lblNota);
		
		comboBoxNota = new JComboBox();
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
			spinnerAn.setValue(2011);
			spinnerLuna.setValue(1);
			spinnerZi.setValue(1);
			comboBoxNota.setSelectedItem(10);
			btnSalveaza.setEnabled(true);
			
			statusLbl.setText("Seteaza activitatea, studentul a carui nota sa adauga, data si nota si apasa 'Salveaza' pentru a crea o noua intrare in catalog.");
			return;
		}
		
		//Always Visible fields
		NotaCatalog object=objects.get(table.getSelectedRow());
		
		//Finding the associated 'Activitate'
		int indexActivitate=-1;
		for(int i=0;i<activitati.size();i++)
			if(activitati.get(i).codDisciplina==object.codDisciplina)
			{
				indexActivitate=i;
				break;
			}
		if(indexActivitate==-1)
		{
			JOptionPane.showMessageDialog(null, "Nu aveti dreptul sa editati aceasta intrare. Nu sunteti titular de curs al disciplinei.");
			table.getSelectionModel().clearSelection();
			return;
		}
		tableActivitate.getSelectionModel().setSelectionInterval(0, indexActivitate);
		
		//Finding the associated 'Utilizator'
		int indexStudent=-1;
		for(int i=0;i<utilizatori.size();i++)
			if(utilizatori.get(i).CNP.equals(object.cnpStudent))
			{
				indexStudent=i;
				break;
			}
		if(indexStudent==-1)
		{
			JOptionPane.showMessageDialog(null, "Nu aveti dreptul sa editati aceasta intrare. Lipseste studentul asociat.");
			table.getSelectionModel().clearSelection();
			return;
		}
		tableStudent.getSelectionModel().setSelectionInterval(0, indexStudent);

		//Set the info
		Calendar calendar=new GregorianCalendar();
		calendar.setTime(object.data);
		spinnerAn.setValue(calendar.get(Calendar.YEAR));
		spinnerLuna.setValue(calendar.get(Calendar.MONTH)+1);
		spinnerZi.setValue(calendar.get(Calendar.DAY_OF_MONTH));
		comboBoxNota.setSelectedIndex(object.nota-1);
		btnSalveaza.setEnabled(false);

		statusLbl.setText("Nu se pot realiza modificari asupra notelor deja introduse.");
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
			System.out.println("Adaugam o noua nota in catalog");
			table.getSelectionModel().clearSelection();
			tableStudent.getSelectionModel().clearSelection();
			tableActivitate.getSelectionModel().clearSelection();
			
			statusLbl.setText("Seteaza activitatea, studentul a carui nota sa adauga, data si nota si apasa 'Salveaza' pentru a crea o noua intrare in catalog.");
		} else	
		if(source==btnSterge)
		{
			System.out.println("Stergem nota");
			
			if(table.getSelectedRow()==-1)
				return;
			
			//Delete the nota
			try {
				if(!arhivaService.stergereNota(objects.get(table.getSelectedRow())))
					return;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			statusLbl.setText("Nota "+objects.get(table.getSelectedRow()).nota+" a fost stearsa.");
			
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
			NotaCatalog object=new NotaCatalog();
			object.cnpStudent=utilizatori.get(tableStudent.getSelectedRow()).CNP;
			object.codDisciplina=activitati.get(tableActivitate.getSelectedRow()).codDisciplina;
			object.nota=Integer.parseInt(comboBoxNota.getSelectedItem().toString());
			GregorianCalendar calendar=new GregorianCalendar();
			calendar.set(Calendar.YEAR, (Integer) spinnerAn.getValue());
			calendar.set(Calendar.MONTH, (Integer) spinnerLuna.getValue()-1);
			calendar.set(Calendar.DAY_OF_MONTH, (Integer) spinnerZi.getValue());
			object.data=new java.sql.Date(calendar.getTime().getTime());
			
			//If it's a new entry
			if(table.getSelectedRow()==-1)
			{
				//Insert the new entry
				System.out.println("Nota noua: "+object);
				try {
					if(!arhivaService.stabilesteNota(utilizator.CNP,object))
						return;
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				statusLbl.setText("S-a creat o intrare noua in catalog.");
				
				objects.add(object);
				mainTableModel.setObjects(objects);
				mainTableModel.fireTableDataChanged();
				table.getSelectionModel().setSelectionInterval(0, objects.size()-1);
			}
			else //If it's an old entry
			{
				JOptionPane.showMessageDialog(null, "Nu se pot modifica note deja introduse!");
				table.getSelectionModel().clearSelection();
			}
				
		}
		
	}
}
