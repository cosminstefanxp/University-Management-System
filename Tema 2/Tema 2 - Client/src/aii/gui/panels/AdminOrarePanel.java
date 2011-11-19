/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii.gui.panels;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
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
import aii.Constants;
import aii.Orar;
import aii.OrarComplet;
import aii.Utilizator;
import aii.Orar.Frecventa;
import aii.Orar.Ziua;
import aii.arhiva.Arhiva;
import aii.gui.tools.FixedSizeDocument;
import aii.gui.tools.ObjectTableModel;
import aii.rad.RegistruActivitatiDidactice;


@SuppressWarnings("serial")
public class AdminOrarePanel extends MainPanelAbstract implements ListSelectionListener, ActionListener {
	
	private JTable table;
	private ArrayList<OrarComplet> objects;
	private ArrayList<Activitate> activitati;
	private ObjectTableModel<OrarComplet> mainTableModel;
	private ObjectTableModel<Activitate> activitatiTableModel;

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
	public AdminOrarePanel(Arhiva arhivaService, RegistruActivitatiDidactice radService,
			Utilizator utilizator, JLabel statusLabel) {
		//Initialize the MainPanelAbstract object
		super(arhivaService, radService, utilizator, statusLabel);
		
		this.statusLbl.setText("Administrare activitati de predare. Selecteaza valorile dorite pentru a crea o noua disciplina sau selecteaza un rand pentru a il modifica.");
		
		//Get the objects and prepare the table models		
		try {
			//Table model for "Orare"
			objects=radService.obtineOrarComplet();
			mainTableModel=new ObjectTableModel<OrarComplet>(OrarComplet.class,
					objects,
					Constants.ADMIN_ORAR_COLUMN_FIELD_MATCH[1],
					Constants.ADMIN_ORAR_COLUMN_FIELD_MATCH[0]);
			//Table model for "Activitate"
			activitati=radService.obtinereActivitatePredare();
			activitatiTableModel=new ObjectTableModel<Activitate>(Activitate.class,
					activitati,
					new String[] {"ID","Tip","Disciplina", "Cadru Didactic"},
					new String[] {"id","tip","codDisciplina", "cnpCadruDidactic"});			
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
		Vector<String> grupe=null;
		try {
			grupe = radService.obtineGrupeStudenti();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
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
			try {
				if(!radService.stergereActivitateOrar(objects.get(table.getSelectedRow())))
					return;
			} catch (RemoteException e) {
				e.printStackTrace();
				return;
			}
			
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
			OrarComplet object=new OrarComplet();
			object.frecventa=(Frecventa) comboBoxFrecventa.getSelectedItem();
			object.durata=(Integer) spinnerDurata.getValue();
			object.grupa=(String) comboBoxGrupa.getSelectedItem();
			object.sala=textFieldSala.getText();
			object.idActivitate=activitati.get(tableActivitati.getSelectedRow()).id;
			object.zi=(Ziua) comboBoxZiua.getSelectedItem();
			object.ora=(Integer)spinnerOra.getValue();
			object.tip=activitati.get(tableActivitati.getSelectedRow()).tip;
			
			//Tweak to help the check
			OrarComplet backup=null;
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
				try {
					if(!radService.adaugareActivitateOrar(object))
						return;
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			
				statusLbl.setText("S-a creat o noua intrare in orar.");
				
				objects.add(object);
				mainTableModel.setObjects(objects);
				mainTableModel.fireTableDataChanged();
				table.getSelectionModel().setSelectionInterval(0, objects.size()-1);
			}
			else //If it's an old entry
			{
				
				System.out.println("Orar existent -> modificat in " + object);
				try {
					if(!radService.editareActivitateOrar(object,backup))
					{
						objects.set(table.getSelectedRow(), backup);
						return;
					}
				} catch (RemoteException e) {
					e.printStackTrace();
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
