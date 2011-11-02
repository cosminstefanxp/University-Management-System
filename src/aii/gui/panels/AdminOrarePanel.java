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
import aii.Activitate;
import aii.Disciplina;
import aii.Orar;
import aii.Utilizator;
import aii.Activitate.TipActivitate;
import aii.Disciplina.TipDisciplina;
import aii.Orar.Frecventa;
import aii.Orar.Ziua;
import aii.database.ActivitateWrapper;
import aii.database.Constants;
import aii.database.OrarWrapper;
import aii.database.UtilizatorWrapper;
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
	private JTable tableDisciplina;
	private JComboBox comboBoxFrecventa;
	private JTextField textFieldSala;
	private JComboBox comboBoxZiua;
	private JSpinner spinnerOra;
	private JComboBox comboBoxGrupa;

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
			objects=orareDAO.getObjects(Constants.ORAR_TABLE,"zi=zi");
			mainTableModel=new ObjectTableModel<Orar>(Orar.class,
					objects,
					Constants.ADMIN_ORAR_COLUMN_FIELD_MATCH[1],
					Constants.ADMIN_ORAR_COLUMN_FIELD_MATCH[0]);
			//Table model for "Activitate"
			activitati=activitatiDAO.getObjects(Constants.ACTIVITATE_TABLE,"id=id");
			activitatiTableModel=new ObjectTableModel<Activitate>(Activitate.class,
					activitati,
					new String[] {"ID","Cod Disciplina", "CNP Cadru Didactic"},
					new String[] {"id","codDisciplina", "cnpCadruDidactic"});			
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
		panelEditInfo.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Setari Orar", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panelEditInfo.setBounds(7, -4, 756, 308);
		panelEdit.add(panelEditInfo);
		panelEditInfo.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Activitate de predare");
		lblNewLabel.setBounds(11, 29, 273, 15);
		panelEditInfo.add(lblNewLabel);
		
		JScrollPane scrollPaneActivitate = new JScrollPane();
		scrollPaneActivitate.setBounds(11, 52, 368, 245);
		panelEditInfo.add(scrollPaneActivitate);
		
		tableDisciplina = new JTable(activitatiTableModel);
		tableDisciplina.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableDisciplina.setFillsViewportHeight(true);
		scrollPaneActivitate.setViewportView(tableDisciplina);
		
		comboBoxFrecventa = new JComboBox();
		comboBoxFrecventa.setBounds(532, 204, 187, 33);
		comboBoxFrecventa.setModel(new DefaultComboBoxModel(Frecventa.values()));
		panelEditInfo.add(comboBoxFrecventa);
		
		btnSalveaza = new JButton("Salveaza");
		btnSalveaza.setBounds(621, 271, 98, 25);
		panelEditInfo.add(btnSalveaza);
		
		JLabel lblNewLabel_1 = new JLabel("Sala: *");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1.setBounds(468, 81, 46, 15);
		panelEditInfo.add(lblNewLabel_1);
		
		JLabel lblZiua = new JLabel("Ziua: *");
		lblZiua.setHorizontalAlignment(SwingConstants.TRAILING);
		lblZiua.setBounds(467, 123, 47, 15);
		panelEditInfo.add(lblZiua);
		
		JLabel lblOra = new JLabel("Ora: *");
		lblOra.setHorizontalAlignment(SwingConstants.TRAILING);
		lblOra.setBounds(468, 167, 46, 15);
		panelEditInfo.add(lblOra);
		
		JLabel lblDurata = new JLabel("Durata: *");
		lblDurata.setBounds(601, 167, 64, 15);
		panelEditInfo.add(lblDurata);
		
		JLabel lblGrupa = new JLabel("Grupa: *");
		lblGrupa.setHorizontalAlignment(SwingConstants.TRAILING);
		lblGrupa.setBounds(456, 38, 58, 15);
		panelEditInfo.add(lblGrupa);
		
		textFieldSala = new JTextField();
		textFieldSala.setBounds(532, 72, 187, 33);
		panelEditInfo.add(textFieldSala);
		textFieldSala.setColumns(10);
		
		comboBoxZiua = new JComboBox();
		comboBoxZiua.setModel(new DefaultComboBoxModel(Ziua.values()));
		comboBoxZiua.setBounds(532, 114, 187, 33);
		panelEditInfo.add(comboBoxZiua);
		
		spinnerOra = new JSpinner();
		spinnerOra.setModel(new SpinnerNumberModel(8, 7, 22, 1));
		spinnerOra.setBounds(532, 159, 46, 33);
		panelEditInfo.add(spinnerOra);
		
		JSpinner spinnerDurata = new JSpinner();
		spinnerDurata.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		spinnerDurata.setBounds(673, 159, 46, 33);
		panelEditInfo.add(spinnerDurata);
		
		comboBoxGrupa = new JComboBox();
		comboBoxGrupa.setBounds(532, 29, 187, 33);
		panelEditInfo.add(comboBoxGrupa);
		
		JLabel lblFrecventa = new JLabel("Frecventa: *");
		lblFrecventa.setHorizontalAlignment(SwingConstants.TRAILING);
		lblFrecventa.setBounds(410, 211, 104, 18);
		panelEditInfo.add(lblFrecventa);
		btnSalveaza.addActionListener(this);
		
		btnSterge = new JButton("Sterge");
		btnSterge.addActionListener(this);
		btnSterge.setBounds(805, 49, 117, 25);
		panelEdit.add(btnSterge);
		
		btnAdauga = new JButton("Adauga");
		btnAdauga.addActionListener(this);
		btnAdauga.setBounds(805, 12, 117, 25);
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
			tableCadruDidactic.getSelectionModel().clearSelection();
			tableDisciplina.getSelectionModel().clearSelection();
			comboBoxFrecventa.setSelectedItem(TipDisciplina.Obligatoriu);
			
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
		tableDisciplina.getSelectionModel().setSelectionInterval(0, indexDisciplina);
		
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
		tableCadruDidactic.getSelectionModel().setSelectionInterval(0, indexCadruDidactic);

		//Set the "Tip"
		comboBoxFrecventa.setSelectedItem(object.tip);

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
			tableCadruDidactic.getSelectionModel().clearSelection();
			tableDisciplina.getSelectionModel().clearSelection();
			
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
			if(tableCadruDidactic.getSelectedRow()==-1 ||
					tableDisciplina.getSelectedRow()==-1)
			{
				JOptionPane.showMessageDialog(null, "Va rugam sa completati toate campurile obligatorii","Incomplet",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//Create the new object
			Activitate object=new Activitate();
			object.cnpCadruDidactic=utilizatori.get(tableCadruDidactic.getSelectedRow()).CNP;
			object.codDisciplina=discipline.get(tableDisciplina.getSelectedRow()).cod;
			object.tip=(TipActivitate) comboBoxFrecventa.getSelectedItem();
			
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
