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
import aii.database.OptiuneContractWrapper;
import aii.database.UtilizatorWrapper;
import aii.gui.tools.ObjectTableModel;
import javax.swing.JTabbedPane;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;


@SuppressWarnings("serial")
public class AdminContractStudiiPanel extends MainPanelAbstract implements ListSelectionListener, ActionListener {
	
	@SuppressWarnings("unused")
	private Utilizator utilizator;
	private JTable tableObligatorii;
	private ArrayList<Disciplina> disciplineObligatorii;
	private ArrayList<ArrayList<Disciplina>> disciplineOptionale;
	private ArrayList<Disciplina> disciplineFacultative;
	private ObjectTableModel<Disciplina> obligatoriiTableModel;
	private ArrayList<ObjectTableModel<Disciplina>> optionaleTableModel;
	private ObjectTableModel<Disciplina> facultativeTableModel;
	private OptiuneContractWrapper optiuniDAO=new OptiuneContractWrapper();
	private DisciplinaWrapper disciplineDAO=new DisciplinaWrapper();
	
	private JLabel statusLbl;
	private JButton btnSalveaza;
	private JTable tableOptional;
	private JScrollPane scrollPaneFacultativ;
	private JTable tableFacultativ;
	private JLabel lbl1;
	private JLabel lbl2;
	
	private void prepareData(Utilizator utilizator)
	{
		//Get the objects and prepare the table models		
		try {
			//Table model for "Activitate"
			disciplineObligatorii=activitatiDAO.getObjects(Constants.ACTIVITATE_TABLE,"id=id");
			obligatoriiTableModel=new ObjectTableModel<Activitate>(Activitate.class,
					disciplineObligatorii,
					Constants.ADMIN_ACTIVITATE_COLUMN_FIELD_MATCH[1],
					Constants.ADMIN_ACTIVITATE_COLUMN_FIELD_MATCH[0]);
			//Table model for "Disciplina"
			discipline=disciplineDAO.getObjects(Constants.DISCIPLINA_TABLE,"cod=cod");
			optionaleTableModel=new ObjectTableModel<Disciplina>(Disciplina.class,
					discipline,
					new String[] {"Cod","Denumire"},
					new String[] {"cod","denumire"});
			//Table model for "Utilizator"
			disciplineOptionale=utilizatoriDAO.getObjects(Constants.USER_TABLE,"tip=\'CADRU_DIDACTIC\' OR tip=\'SEF_CATEDRA\'");
			facultativeTableModel=new ObjectTableModel<Utilizator>(Utilizator.class,
					disciplineOptionale,
					new String[] {"CNP","Nume","Prenume"},
					new String[] {"CNP","nume","prenume"});
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the panel.
	 */
	public AdminContractStudiiPanel(Utilizator utilizator, JLabel statusLbl) {
		this.utilizator=utilizator;
		this.statusLbl=statusLbl;
		this.statusLbl.setText("Completare contract studii. Alege materiile pe care doresti sa le urmezi in acest an de studiu.");
		
		
		
		//GUI
		setLayout(new MigLayout("", "[638.00px:1022.00px,grow]", "[][][469.00,grow][51.00px]"));
		
		lbl1 = new JLabel("Acesta este contractul tau de studii pentru anul scolar curent.");
		add(lbl1, "cell 0 0");
		
		lbl2 = new JLabel("Te rugam selecteaza materiile optionale (cate una pe fiecare grup) si facultative pe care le doresti si apasa Salveaza:");
		add(lbl2, "cell 0 1");
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, "cell 0 2,grow");
		
		JScrollPane scrollPaneObligatorii = new JScrollPane();
		tabbedPane.addTab("Obligatorii", null, scrollPaneObligatorii, null);
		
		tableObligatorii = new JTable(obligatoriiTableModel);
		tableObligatorii.setEnabled(false);
		tableObligatorii.setRowSelectionAllowed(false);
		tableObligatorii.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableObligatorii.setFillsViewportHeight(true);
		tableObligatorii.getSelectionModel().addListSelectionListener(this);
		scrollPaneObligatorii.setViewportView(tableObligatorii);
		
		JScrollPane scrollPaneOptional = new JScrollPane();
		tabbedPane.addTab("Optional 1", null, scrollPaneOptional, null);
		
		tableOptional = new JTable(optionaleTableModel);
		tableOptional.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableOptional.setFillsViewportHeight(true);
		scrollPaneOptional.setViewportView(tableOptional);
		
		scrollPaneFacultativ = new JScrollPane();
		tabbedPane.addTab("Facultativ", null, scrollPaneFacultativ, null);
		tabbedPane.setEnabledAt(2, true);
		
		tableFacultativ = new JTable(facultativeTableModel);
		tableFacultativ.setToolTipText("Foloseste CTRL+Click pentru a selecta mai multe randuri.");
		tableFacultativ.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tableFacultativ.setFillsViewportHeight(true);
		scrollPaneFacultativ.setViewportView(tableFacultativ);
		
		JPanel panelEdit = new JPanel();
		add(panelEdit, "cell 0 3,grow");
		panelEdit.setLayout(new BorderLayout(0, 0));
		
		JLabel lblOdataCe = new JLabel("* Odata ce ai salvat optiunile tale, contractul de studii nu mai poate fi modificat!");
		lblOdataCe.setFont(new Font("Dialog", Font.ITALIC, 12));
		panelEdit.add(lblOdataCe, BorderLayout.WEST);
		
		btnSalveaza = new JButton("Salveaza");
		lblOdataCe.setLabelFor(btnSalveaza);
		panelEdit.add(btnSalveaza, BorderLayout.EAST);
		btnSalveaza.addActionListener(this);
	}

	/* Eveniment declansat la schimbarea selectiei
	 * (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged(ListSelectionEvent event) {
		if (event.getValueIsAdjusting())
            return;
        
		int row = tableObligatorii.getSelectedRow();
		System.out.println("Selectie modificata pe randul "+row);
		
		//Nothing selected / deselection
		if(row==-1)
		{
			System.out.println("Deselectie detectata.");
			tableCadruDidactic.getSelectionModel().clearSelection();
			tableDisciplina.getSelectionModel().clearSelection();
			comboBoxTipActivitate.setSelectedItem(TipDisciplina.Obligatoriu);
			
			statusLbl.setText("Seteaza disciplina, cadrul didactic asociat si tipul activitatii si apasa 'Salveaza' pentru a crea o noua activitate didactica.");
			return;
		}
		
		//Always Visible fields
		Activitate object=disciplineObligatorii.get(tableObligatorii.getSelectedRow());
		
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
		for(int i=0;i<disciplineOptionale.size();i++)
			if(disciplineOptionale.get(i).CNP.equals(object.cnpCadruDidactic))
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
		
	}
}
