package aii.gui.panels;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

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
import aii.Constants;
import aii.Disciplina;
import aii.Examen;
import aii.Utilizator;
import aii.arhiva.Arhiva;
import aii.gui.tools.FixedSizeDocument;
import aii.gui.tools.ObjectTableModel;
import aii.rad.RegistruActivitatiDidactice;


@SuppressWarnings("serial")
public class AdminExamenePanel extends MainPanelAbstract implements ListSelectionListener, ActionListener {
	
	private JTable table;
	private ArrayList<Examen> objects;
	private ArrayList<Disciplina> discipline;
	private ObjectTableModel<Examen> mainTableModel;
	private ObjectTableModel<Disciplina> disciplineTableModel;

	private JButton btnSalveaza;
	private JButton btnSterge;
	private JButton btnAdauga;
	private JPanel panelEditInfo;
	private JTable tableDiscipline;
	private JTextField textFieldSala;
	private JSpinner spinnerOra;
	private JComboBox comboBoxGrupa;
	private JSpinner spinnerZi;
	private JSpinner spinnerLuna;
	private JSpinner spinnerAn;
	private JLabel lblExamenulLa;
	private JLabel lblDisciplinaSePoate;

	/**
	 * Create the panel.
	 */
	public AdminExamenePanel(Arhiva arhivaService, RegistruActivitatiDidactice radService,
			Utilizator utilizator, JLabel statusLabel) {
		//Initialize the MainPanelAbstract object
		super(arhivaService, radService, utilizator, statusLabel);
		
		this.statusLbl.setText("Administrare activitati de predare. Selecteaza valorile dorite pentru a crea o noua disciplina sau selecteaza un rand pentru a il modifica.");
		
		//Get the objects and prepare the table models		
		try {
			//Table model for "Examen"
			objects=radService.obtineExamene();
			mainTableModel=new ObjectTableModel<Examen>(Examen.class,
					objects,
					Constants.ADMIN_EXAMEN_COLUMN_FIELD_MATCH[1],
					Constants.ADMIN_EXAMEN_COLUMN_FIELD_MATCH[0]);
			//Table model for "Discipline"
			discipline=arhivaService.obtineDiscipline();
			disciplineTableModel=new ObjectTableModel<Disciplina>(Disciplina.class,
					discipline,
					new String[] {"Cod","Denumire"},
					new String[] {"cod","denumire"});			
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
		panelEditInfo.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Setari Examene", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panelEditInfo.setBounds(7, -4, 756, 316);
		panelEdit.add(panelEditInfo);
		panelEditInfo.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Disciplina");
		lblNewLabel.setBounds(11, 29, 273, 15);
		panelEditInfo.add(lblNewLabel);
		
		JScrollPane scrollPaneDiscipline = new JScrollPane();
		scrollPaneDiscipline.setBounds(11, 52, 368, 245);
		panelEditInfo.add(scrollPaneDiscipline);
		
		tableDiscipline = new JTable(disciplineTableModel);
		tableDiscipline.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableDiscipline.setFillsViewportHeight(true);
		scrollPaneDiscipline.setViewportView(tableDiscipline);
		
		btnSalveaza = new JButton("Salveaza");
		btnSalveaza.setBounds(621, 279, 98, 25);
		panelEditInfo.add(btnSalveaza);
		
		JLabel lblNewLabel_1 = new JLabel("Sala: *");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1.setBounds(468, 81, 46, 15);
		panelEditInfo.add(lblNewLabel_1);
		
		JLabel lblZiua = new JLabel("Zi/Luna/An: *");
		lblZiua.setHorizontalAlignment(SwingConstants.TRAILING);
		lblZiua.setBounds(424, 123, 90, 18);
		panelEditInfo.add(lblZiua);
		
		JLabel lblOra = new JLabel("Ora: *");
		lblOra.setHorizontalAlignment(SwingConstants.TRAILING);
		lblOra.setBounds(468, 167, 46, 15);
		panelEditInfo.add(lblOra);
		
		JLabel lblGrupa = new JLabel("Grupa: *");
		lblGrupa.setHorizontalAlignment(SwingConstants.TRAILING);
		lblGrupa.setBounds(456, 38, 58, 15);
		panelEditInfo.add(lblGrupa);
		
		textFieldSala = new JTextField();
		textFieldSala.setBounds(532, 72, 187, 33);
		panelEditInfo.add(textFieldSala);
		textFieldSala.setColumns(10);
		textFieldSala.setDocument(new FixedSizeDocument(Constants.FIELD_SIZE_SALA));
		
		spinnerOra = new JSpinner();
		spinnerOra.setModel(new SpinnerNumberModel(8, 7, 22, 1));
		spinnerOra.setBounds(532, 159, 187, 33);
		panelEditInfo.add(spinnerOra);
		
		comboBoxGrupa = new JComboBox();
		comboBoxGrupa.setBounds(532, 29, 187, 33);
		panelEditInfo.add(comboBoxGrupa);
		btnSalveaza.addActionListener(this);
		
		spinnerZi = new JSpinner();
		spinnerZi.setModel(new SpinnerNumberModel(1, 1, 31, 1));
		spinnerZi.setBounds(532, 117, 46, 33);
		panelEditInfo.add(spinnerZi);
		
		spinnerLuna = new JSpinner();
		spinnerLuna.setModel(new SpinnerNumberModel(1, 1, 12, 1));
		spinnerLuna.setBounds(594, 117, 46, 33);
		panelEditInfo.add(spinnerLuna);
		
		spinnerAn = new JSpinner();
		spinnerAn.setModel(new SpinnerNumberModel(2011, 2000, 2014, 1));
		spinnerAn.setBounds(655, 117, 64, 33);
		panelEditInfo.add(spinnerAn);
	    spinnerAn.setEditor(new JSpinner.NumberEditor(spinnerAn, "####"));

		
		JLabel label = new JLabel("/");
		label.setBounds(583, 123, 6, 18);
		panelEditInfo.add(label);
		
		JLabel label_1 = new JLabel("/");
		label_1.setBounds(644, 123, 6, 18);
		panelEditInfo.add(label_1);
		
		btnSterge = new JButton("Sterge");
		btnSterge.addActionListener(this);
		btnSterge.setBounds(805, 49, 117, 25);
		panelEdit.add(btnSterge);
		
		btnAdauga = new JButton("Adauga");
		btnAdauga.addActionListener(this);
		btnAdauga.setBounds(805, 12, 117, 25);
		panelEdit.add(btnAdauga);
		
		/* Pregatire date despre grupa. */
		Vector<String> grupe=null;
		try {
			grupe = radService.obtineGrupeStudenti();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		comboBoxGrupa.setModel(new DefaultComboBoxModel(grupe));
		
		JLabel lblDurataAlocata = new JLabel("* Durata alocata unui examen este 3 ore");
		lblDurataAlocata.setBounds(433, 252, 286, 15);
		panelEditInfo.add(lblDurataAlocata);
		
		lblExamenulLa = new JLabel("* Examenul mai multor grupe la aceeasi ");
		lblExamenulLa.setBounds(437, 214, 282, 15);
		panelEditInfo.add(lblExamenulLa);
		
		lblDisciplinaSePoate = new JLabel("disciplina se poate suprapune");
		lblDisciplinaSePoate.setBounds(504, 231, 215, 15);
		panelEditInfo.add(lblDisciplinaSePoate);
		
		
		
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
			tableDiscipline.getSelectionModel().clearSelection();
			textFieldSala.setText(null);
			comboBoxGrupa.setSelectedIndex(0);
			spinnerAn.setValue(2011);
			spinnerLuna.setValue(11);
			spinnerZi.setValue(1);
			spinnerOra.setValue(8);
		
			statusLbl.setText("Seteaza campurile corespunzatori si apasa 'Salveaza' pentru a crea o noua programare pentru examen.");
			return;
		}
		
		//Always Visible fields
		Examen object=objects.get(table.getSelectedRow());
		
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
		tableDiscipline.getSelectionModel().setSelectionInterval(0, indexDisciplina);
		
		//Set the "Values"
		textFieldSala.setText(object.sala);
		comboBoxGrupa.setSelectedItem(object.grupa);
		Calendar calendar=new GregorianCalendar();
		calendar.setTime(object.data);
		spinnerAn.setValue(calendar.get(Calendar.YEAR));
		spinnerLuna.setValue(calendar.get(Calendar.MONTH)+1);
		spinnerZi.setValue(calendar.get(Calendar.DAY_OF_MONTH));
		spinnerOra.setValue(object.ora);
	
		statusLbl.setText("Modifica campurile dorite si apasa 'Salveaza' pentru a face permanente modificarile.");
	}
	
	/**
	 * Checks if is a given time and place slot is busy.
	 *
	 * @param object the object
	 * @return true, if is busy
	 */
	@SuppressWarnings("deprecation")
	private boolean isBusy(Examen object)
	{
		for(Examen examenFixat:objects)
		{
			if(examenFixat!=null && examenFixat.sala.equalsIgnoreCase(object.sala))	//if it's the same exam room
				if(examenFixat.codDisciplina != object.codDisciplina)				//if it's not the same class
					if(examenFixat.data.getDay()==object.data.getDay() && 			//id it's the same day
							examenFixat.data.getMonth()==object.data.getMonth() &&
								examenFixat.data.getYear()==object.data.getYear())
						if((examenFixat.ora <= object.ora && (examenFixat.ora + 3) > object.ora) ||
								(examenFixat.ora < (object.ora+3) && (examenFixat.ora + 3) >= (object.ora+3)))	//if the hours overlap
							return true;
				
		}
		return false;		
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
			System.out.println("Adaugam o noua intrare in programarea examenelor");
			table.getSelectionModel().clearSelection();
			tableDiscipline.getSelectionModel().clearSelection();
			
			statusLbl.setText("Seteaza campurile dorite si apasa 'Salveaza' pentru a crea o noua programare de examene.");
		} else	
		if(source==btnSterge)
		{
			System.out.println("Stergem programarea examenului.");
			
			if(table.getSelectedRow()==-1)
				return;
			
			//Delete the orar
			try {
				if(!radService.stergereExamen(objects.get(table.getSelectedRow())))
					return;
			} catch (RemoteException e) {
				e.printStackTrace();
				return;
			}
			
			statusLbl.setText("Examenul pentru "+objects.get(table.getSelectedRow()).grupa+" la disciplina "+objects.get(table.getSelectedRow()).codDisciplina+" a fost sters.");
			
			//Update JTable
			objects.remove(table.getSelectedRow());
			table.getSelectionModel().clearSelection();
			mainTableModel.setObjects(objects);
			mainTableModel.fireTableDataChanged();
			
		} else
		if(source==btnSalveaza)
		{
			System.out.println("Salvam informatiile in DB");
			
			//Check fields
			if(tableDiscipline.getSelectedRow()==-1 ||
					textFieldSala.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(null, "Va rugam sa completati toate campurile si sa alegeti o disciplina.","Incomplet",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//Create the new object
			Examen object=new Examen();
			object.grupa=comboBoxGrupa.getSelectedItem().toString().toUpperCase();
			object.sala=textFieldSala.getText().toUpperCase();
			object.codDisciplina=discipline.get(tableDiscipline.getSelectedRow()).cod;
			GregorianCalendar calendar=new GregorianCalendar();
			calendar.set(Calendar.YEAR, (Integer) spinnerAn.getValue());
			calendar.set(Calendar.MONTH, (Integer) spinnerLuna.getValue()-1);
			calendar.set(Calendar.DAY_OF_MONTH, (Integer) spinnerZi.getValue());
			object.data=new java.sql.Date(calendar.getTime().getTime());
			object.ora=(Integer)spinnerOra.getValue();
			
			
			//Tweak to help the check
			Examen backup=null;
			if(table.getSelectedRow()!=-1)
			{
				backup=objects.get(table.getSelectedRow());
				objects.set(table.getSelectedRow(),null);
			}
			
			//Check if the exam room is free
			if(isBusy(object))
			{
				JOptionPane.showMessageDialog(null, "Sala selectata este ocupata in intervalul selectat.","Ocupat",JOptionPane.ERROR_MESSAGE);
				if(table.getSelectedRow()!=-1)
					objects.set(table.getSelectedRow(), backup);
				return;
			}
			
			
			//If it's a new entry
			if(table.getSelectedRow()==-1)
			{
				//Insert the new examen
				System.out.println("Intrare noua in tabela de examene: "+object);
				try {
					if(!radService.adaugareExamen(object))
						return;
				} catch (RemoteException e) {
					e.printStackTrace();
					return;
				}
			
				statusLbl.setText("S-a creat o noua programare de examen.");
				
				objects.add(object);
				mainTableModel.setObjects(objects);
				mainTableModel.fireTableDataChanged();
				table.getSelectionModel().setSelectionInterval(0, objects.size()-1);
			}
			else //If it's an old entry
			{
				
				System.out.println("Examen existent -> modificat in " + object);
				try {
					if(!radService.editareExamen(object, backup))
					{
						objects.set(table.getSelectedRow(), backup);	//restore previous exam
						return;
					}
				} catch (RemoteException e) {
					e.printStackTrace();
					return;
				}
				
				statusLbl.setText("Examenul pentru grupa "+object.grupa +" la disciplina "+object.codDisciplina+" a fost actualizat.");
				
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
