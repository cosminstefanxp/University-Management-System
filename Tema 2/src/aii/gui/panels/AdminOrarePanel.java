package aii.gui.panels;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
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
import aii.Activitate;
import aii.Orar;
import aii.Utilizator;
import aii.Orar.Frecventa;
import aii.Orar.Ziua;
import aii.database.ActivitateWrapper;
import aii.database.Constants;
import aii.database.DatabaseConnection;
import aii.database.OrarWrapper;
import aii.gui.tools.FixedSizeDocument;
import aii.gui.tools.ObjectTableModel;


@SuppressWarnings("serial")
public class AdminOrarePanel extends MainPanelAbstract implements ListSelectionListener, ActionListener {
	
	@SuppressWarnings("unused")
	private Utilizator utilizator;
	private JTable table;
	private ArrayList<Orar> objects;
	private ArrayList<Activitate> activitati;
	private ObjectTableModel<Orar> mainTableModel;
	private ObjectTableModel<Activitate> activitatiTableModel;
	private OrarWrapper orareDAO=new OrarWrapper();
	private ActivitateWrapper activitatiDAO=new ActivitateWrapper();
	
	
	private JLabel statusLbl;
	private JButton btnSalveaza;
	private JButton btnSterge;
	private JButton btnAdauga;
	private JPanel panelEditInfo;
	private JTable tableActivitati;
	private JComboBox comboBoxFrecventa;
	private JTextField textFieldSala;
	private JComboBox comboBoxZiua;
	private JSpinner spinnerOra;
	private JComboBox comboBoxGrupa;
	private JSpinner spinnerDurata;
	private JLabel lblNuSe;

	/**
	 * Create the panel.
	 */
	public AdminOrarePanel(Utilizator utilizator, JLabel statusLbl) {
		this.utilizator=utilizator;
		this.statusLbl=statusLbl;
		this.statusLbl.setText("Administrare activitati de predare. Selecteaza valorile dorite pentru a crea o noua disciplina sau selecteaza un rand pentru a il modifica.");
		
		//Get the objects and prepare the table models		
		try {
			//Table model for "Orare"
			orareDAO.setNameMatch(Constants.ORAR_FIELD_MATCH_FULL);
			objects=orareDAO.getOrareJoined("o.zi, o.ora, o.sala, d.denumire, o.grupa, o.frecventa, o.durata, o.id_activitate, a.tip",
					Constants.DISCIPLINA_TABLE+" d, "+Constants.ORAR_TABLE+" o, "+Constants.ACTIVITATE_TABLE+" a",
					"o.id_activitate=a.id AND a.cod_disciplina=d.cod");
			orareDAO.setNameMatch(Constants.ORAR_FIELD_MATCH);
			mainTableModel=new ObjectTableModel<Orar>(Orar.class,
					objects,
					Constants.ADMIN_ORAR_COLUMN_FIELD_MATCH[1],
					Constants.ADMIN_ORAR_COLUMN_FIELD_MATCH[0]);
			//Table model for "Activitate"
			activitatiDAO.setNameMatch(Constants.ACTIVITATE_FIELD_MATCH_FULL);
			activitati=activitatiDAO.getActivitatiJoined("a.id, a.cod_disciplina, a.cnp_cadru_didactic, a.tip, d.denumire, concat(u.nume,concat(\" \",u.prenume)) nume", 
					Constants.ACTIVITATE_TABLE+" a, "+Constants.DISCIPLINA_TABLE+" d, "+Constants.USER_TABLE+" u",
					"a.cod_disciplina=d.cod AND u.cnp=a.cnp_cadru_didactic");
			activitatiTableModel=new ObjectTableModel<Activitate>(Activitate.class,
					activitati,
					new String[] {"ID","Tip","Denumire Disciplina", "Cadru Didactic"},
					new String[] {"id","tip","denumireDisciplina", "numeCadruDidactic"});			
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
		panelEditInfo.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Setari Orar", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panelEditInfo.setBounds(7, -4, 845, 308);
		panelEdit.add(panelEditInfo);
		panelEditInfo.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Activitate de predare");
		lblNewLabel.setBounds(11, 29, 273, 15);
		panelEditInfo.add(lblNewLabel);
		
		JScrollPane scrollPaneActivitate = new JScrollPane();
		scrollPaneActivitate.setBounds(11, 52, 428, 245);
		panelEditInfo.add(scrollPaneActivitate);
		
		tableActivitati = new JTable(activitatiTableModel);
		tableActivitati.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableActivitati.setFillsViewportHeight(true);
		scrollPaneActivitate.setViewportView(tableActivitati);
		
		comboBoxFrecventa = new JComboBox();
		comboBoxFrecventa.setBounds(641, 204, 187, 33);
		comboBoxFrecventa.setModel(new DefaultComboBoxModel(Frecventa.values()));
		panelEditInfo.add(comboBoxFrecventa);
		
		btnSalveaza = new JButton("Salveaza");
		btnSalveaza.setBounds(730, 276, 98, 25);
		panelEditInfo.add(btnSalveaza);
		
		JLabel lblNewLabel_1 = new JLabel("Sala: *");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1.setBounds(577, 81, 46, 15);
		panelEditInfo.add(lblNewLabel_1);
		
		JLabel lblZiua = new JLabel("Ziua: *");
		lblZiua.setHorizontalAlignment(SwingConstants.TRAILING);
		lblZiua.setBounds(576, 123, 47, 15);
		panelEditInfo.add(lblZiua);
		
		JLabel lblOra = new JLabel("Ora: *");
		lblOra.setHorizontalAlignment(SwingConstants.TRAILING);
		lblOra.setBounds(577, 167, 46, 15);
		panelEditInfo.add(lblOra);
		
		JLabel lblDurata = new JLabel("Durata: *");
		lblDurata.setBounds(713, 167, 64, 15);
		panelEditInfo.add(lblDurata);
		
		JLabel lblGrupa = new JLabel("Grupa: *");
		lblGrupa.setHorizontalAlignment(SwingConstants.TRAILING);
		lblGrupa.setBounds(565, 38, 58, 15);
		panelEditInfo.add(lblGrupa);
		
		textFieldSala = new JTextField();
		textFieldSala.setBounds(641, 72, 187, 33);
		panelEditInfo.add(textFieldSala);
		textFieldSala.setColumns(10);
		textFieldSala.setDocument(new FixedSizeDocument(Constants.FIELD_SIZE_SALA));
		
		comboBoxZiua = new JComboBox();
		comboBoxZiua.setModel(new DefaultComboBoxModel(Ziua.values()));
		comboBoxZiua.setBounds(641, 114, 187, 33);
		panelEditInfo.add(comboBoxZiua);
		
		spinnerOra = new JSpinner();
		spinnerOra.setModel(new SpinnerNumberModel(8, 7, 22, 1));
		spinnerOra.setBounds(641, 159, 46, 33);
		panelEditInfo.add(spinnerOra);
		
		spinnerDurata = new JSpinner();
		spinnerDurata.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		spinnerDurata.setBounds(782, 159, 46, 33);
		panelEditInfo.add(spinnerDurata);
		
		comboBoxGrupa = new JComboBox();
		comboBoxGrupa.setBounds(641, 29, 187, 33);
		panelEditInfo.add(comboBoxGrupa);
		
		JLabel lblFrecventa = new JLabel("Frecventa: *");
		lblFrecventa.setHorizontalAlignment(SwingConstants.TRAILING);
		lblFrecventa.setBounds(519, 211, 104, 18);
		panelEditInfo.add(lblFrecventa);
		btnSalveaza.addActionListener(this);
		
		btnSterge = new JButton("Sterge");
		btnSterge.addActionListener(this);
		btnSterge.setBounds(864, 49, 117, 25);
		panelEdit.add(btnSterge);
		
		btnAdauga = new JButton("Adauga");
		btnAdauga.addActionListener(this);
		btnAdauga.setBounds(864, 12, 117, 25);
		panelEdit.add(btnAdauga);
		
		/* Pregatire date despre grupa. */
		Vector<Object[]> results=null;
		String query="SELECT DISTINCT titlu_grupa FROM "+Constants.USER_TABLE+" WHERE tip=\'"+Utilizator.Tip.STUDENT.toString()+"\'";
		
		try {
			DatabaseConnection.openConnection();
			results=DatabaseConnection.customQueryArray(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
		//Construire grupe
		Vector<String> grupe=new Vector<String>(results.size());
		for(int i=0;i<results.size();i++)
		{
			String value=(String) results.get(i)[0];
			if(!value.isEmpty())	//Skip empty values
				grupe.add(value);
		}
		System.out.println("Obtinute grupele: "+grupe);
		
		comboBoxGrupa.setModel(new DefaultComboBoxModel(grupe));
		
		lblNuSe = new JLabel("* Nu se pot desfasura mai multe clase in aceeasi sala");
		lblNuSe.setBounds(447, 249, 378, 15);
		panelEditInfo.add(lblNuSe);
		
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
			tableActivitati.getSelectionModel().clearSelection();
			textFieldSala.setText(null);
			comboBoxGrupa.setSelectedIndex(0);
			comboBoxZiua.setSelectedIndex(0);
			comboBoxFrecventa.setSelectedItem(Frecventa.Saptamanal);
			spinnerOra.setValue(8);
			spinnerDurata.setValue(1);			
			
			statusLbl.setText("Seteaza campurile corespunzatori si apasa 'Salveaza' pentru a crea o noua intrare in orar.");
			return;
		}
		
		//Always Visible fields
		Orar object=objects.get(table.getSelectedRow());
		
		//Finding the associated 'Activitate'
		int indexActivitate=-1;
		for(int i=0;i<activitati.size();i++)
			if(activitati.get(i).id==object.idActivitate)
			{
				indexActivitate=i;
				break;
			}
		if(indexActivitate==-1)
		{
			JOptionPane.showMessageDialog(null, "Lipseste activitatea asociata.");
			return;
		}
		tableActivitati.getSelectionModel().setSelectionInterval(0, indexActivitate);
		
		//Set the "Tip"
		textFieldSala.setText(object.sala.toUpperCase());
		comboBoxGrupa.setSelectedItem(object.grupa);
		comboBoxZiua.setSelectedItem(object.zi);
		comboBoxFrecventa.setSelectedItem(object.frecventa);
		spinnerOra.setValue(object.ora);
		spinnerDurata.setValue(object.durata);		

		statusLbl.setText("Modifica campurile dorite si apasa 'Salveaza' pentru a face permanente modificarile.");
	}
	
	/**
	 * Checks if is a given time and place slot is busy.
	 *
	 * @param object the object
	 * @return true, if is busy
	 */
	private boolean isBusy(Orar object)
	{
		for(Orar orarFixat:objects)
		{
			if(orarFixat!=null && orarFixat.sala.equalsIgnoreCase(object.sala) && orarFixat.zi.equals(object.zi))
				if(orarFixat.idActivitate!=object.idActivitate)
					if(orarFixat.frecventa==Frecventa.Saptamanal || object.frecventa==Frecventa.Saptamanal || object.frecventa==orarFixat.frecventa)
						if((orarFixat.ora <= object.ora && (orarFixat.ora + orarFixat.durata) > object.ora) ||
								(orarFixat.ora < (object.ora+object.durata) && (orarFixat.ora + orarFixat.durata) >= (object.ora+object.durata)))
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
			System.out.println("Adaugam o noua intrare in orar");
			table.getSelectionModel().clearSelection();
			tableActivitati.getSelectionModel().clearSelection();
			
			statusLbl.setText("Seteaza campurile dorite si apasa 'Salveaza' pentru a crea o noua intrare in orar.");
		} else	
		if(source==btnSterge)
		{
			System.out.println("Stergem intrarea din orar");
			
			if(table.getSelectedRow()==-1)
				return;
			
			//Delete the orar
			if(!orareDAO.deleteOrar(objects.get(table.getSelectedRow())))
				return;
			
			statusLbl.setText("Orarul pentru "+objects.get(table.getSelectedRow()).grupa+"la activitate "+objects.get(table.getSelectedRow()).idActivitate+" a fost sters.");
			
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
			if(tableActivitati.getSelectedRow()==-1 ||
					textFieldSala.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(null, "Va rugam sa completati toate campurile si sa alegeti o activitate.","Incomplet",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//Create the new object
			Orar object=new Orar();
			object.frecventa=(Frecventa) comboBoxFrecventa.getSelectedItem();
			object.durata=(Integer) spinnerDurata.getValue();
			object.grupa=(String) comboBoxGrupa.getSelectedItem();
			object.sala=textFieldSala.getText();
			object.idActivitate=activitati.get(tableActivitati.getSelectedRow()).id;
			object.zi=(Ziua) comboBoxZiua.getSelectedItem();
			object.ora=(Integer)spinnerOra.getValue();
			object.tipActivitate=activitati.get(tableActivitati.getSelectedRow()).tip;
			object.denumireDisciplina=activitati.get(tableActivitati.getSelectedRow()).denumireDisciplina;
			
			//Tweak to help the check
			Orar backup=null;
			if(table.getSelectedRow()!=-1)
			{
				backup=objects.get(table.getSelectedRow());
				objects.set(table.getSelectedRow(),null);
			}
			
			//Check if the place is free
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
				//Insert the new orar
				System.out.println("Intrare noua in orar: "+object);
				if(!orareDAO.insertOrar(object))
					return;
			
				statusLbl.setText("S-a creat o noua intrare in orar.");
				
				objects.add(object);
				mainTableModel.setObjects(objects);
				mainTableModel.fireTableDataChanged();
				table.getSelectionModel().setSelectionInterval(0, objects.size()-1);
			}
			else //If it's an old entry
			{
				
				System.out.println("Orar existent -> modificat in " + object);
				if(!orareDAO.updateOrar(backup, object))
				{
					objects.set(table.getSelectedRow(), backup);
					return;
				}
				
				statusLbl.setText("Orarul pentru grupa "+object.grupa +" la activitatea "+object.idActivitate+" a fost actualizat.");
				
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
