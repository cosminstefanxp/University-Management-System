package aii.gui.panels;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
import aii.database.Constants;
import aii.database.UtilizatorWrapper;
import aii.gui.tools.FixedSizeDocument;
import aii.gui.tools.ObjectTableModel;


@SuppressWarnings("serial")
public class AdminGrupePanel extends MainPanelAbstract implements ListSelectionListener, ActionListener {
	
	@SuppressWarnings("unused")
	private Utilizator utilizator;
	private JTable table;
	private ArrayList<Utilizator> objects;
	private ObjectTableModel<Utilizator> tableModel;
	private UtilizatorWrapper utilizatorDAO=new UtilizatorWrapper();
	
	private JLabel statusLbl;
	
	private JTextField textFieldNume;
	private JTextField textFieldGrupa;
	private JLabel lblTitluGrupa;
	private JButton btnSalveaza;
	private JPanel panelEditInfo;

	/**
	 * Create the panel.
	 */
	public AdminGrupePanel(Utilizator utilizator, JLabel statusLbl) {
		this.utilizator=utilizator;
		this.statusLbl=statusLbl;
		this.statusLbl.setText("Administrare grupe utilizatori. Completeaza campurile pentru a edita grupa unui student si apasa 'Salveaza' pentru a face permanente setarile.");
		
		//Get the objects		
		try {
			objects=utilizatorDAO.getObjects(Constants.USER_TABLE,"tip=\'STUDENT\'");
			tableModel=new ObjectTableModel<Utilizator>(Utilizator.class,
					objects,
					new String[] {"CNP","Nume", "Prenume", "Grupa"},
					new String[] {"CNP","nume", "prenume", "titlu_grupa"});
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		setLayout(new MigLayout("", "[467.00px:752.00px]", "[112.00px:298.00px][144.00px:204.00px]"));
		
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
		panelEditInfo.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Setari Grupa", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panelEditInfo.setBounds(7, -4, 744, 145);
		panelEdit.add(panelEditInfo);
		panelEditInfo.setLayout(null);
		
		JPanel panelEditMainInfo = new JPanel();
		panelEditMainInfo.setBounds(12, 24, 421, 107);
		panelEditInfo.add(panelEditMainInfo);
		panelEditMainInfo.setLayout(null);
		
		textFieldNume = new JTextField((String) null);
		textFieldNume.setEditable(false);
		textFieldNume.setColumns(10);
		textFieldNume.setBounds(149, 12, 260, 28);
		panelEditMainInfo.add(textFieldNume);
		
		JLabel lblNumeComplet = new JLabel("Nume Complet: *");
		lblNumeComplet.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNumeComplet.setBounds(14, 18, 117, 15);
		panelEditMainInfo.add(lblNumeComplet);
		
		btnSalveaza = new JButton("Salveaza");
		btnSalveaza.addActionListener(this);
		btnSalveaza.setBounds(615, 106, 117, 25);
		panelEditInfo.add(btnSalveaza);
		textFieldNume.setText(null);
		
		lblTitluGrupa = new JLabel("Grupa: *");
		lblTitluGrupa.setBounds(73, 77, 58, 15);
		panelEditMainInfo.add(lblTitluGrupa);
		lblTitluGrupa.setHorizontalAlignment(SwingConstants.CENTER);
		
		textFieldGrupa = new JTextField((String) null);
		textFieldGrupa.setBounds(149, 71, 260, 28);
		panelEditMainInfo.add(textFieldGrupa);
		textFieldGrupa.setColumns(10);
		textFieldGrupa.setText(null);
		textFieldGrupa.setDocument(new FixedSizeDocument(Constants.FIELD_SIZE_GRUPA));

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
			textFieldNume.setText(null);
			textFieldGrupa.setText(null);
			
			return;
		}
		
		//Always Visible fields
		Utilizator object=objects.get(row);
		textFieldNume.setText(object.nume+" "+object.prenume);
		textFieldGrupa.setText(object.titlu_grupa);

		statusLbl.setText("Modfica datele utilizatorului "+object.CNP+" si apasa 'Salveaza' pentru a face permanente modificarile.");		
	}

	/* Evenimente declansate la click pe cele 3 butoane sau la schimbarea selectiei tipului
	 *  (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		if(source==btnSalveaza)
		{
			System.out.println("Salvam informatiile in DB");
			
			//Check fields
			if(textFieldGrupa.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(null, "Va rugam sa completati toate campurile obligatorii","Incomplet",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(table.getSelectedRow()==-1)
			{
				JOptionPane.showMessageDialog(null, "Va rugam sa selectati un student din lista caruia sa ii editati grupa.","Incomplet",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			Utilizator object=objects.get(table.getSelectedRow());
				
			object.titlu_grupa=textFieldGrupa.getText().toUpperCase();
			if(!utilizatorDAO.UpdateUtilizator(object, object))
				return;
				
			statusLbl.setText("Utilizatorul "+object.CNP+" a fost actualizat.");
				
			//Update JTable
			int curSelected=table.getSelectedRow();
			objects.set(table.getSelectedRow(), object);
			tableModel.setObjects(objects);
			tableModel.fireTableDataChanged();	
			table.getSelectionModel().setSelectionInterval(0, curSelected);
		}
				
		
	}
}
