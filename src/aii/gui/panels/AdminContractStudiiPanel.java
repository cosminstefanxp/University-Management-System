package aii.gui.panels;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import net.miginfocom.swing.MigLayout;
import aii.Disciplina;
import aii.Utilizator;
import aii.database.Constants;
import aii.database.DisciplinaWrapper;
import aii.database.OptiuneContractWrapper;
import aii.database.UtilizatorWrapper;
import aii.gui.tools.ObjectTableModel;


@SuppressWarnings("serial")
public class AdminContractStudiiPanel extends MainPanelAbstract implements ActionListener {
	
	private Utilizator utilizator;
	private JTable tableObligatorii;
	private ArrayList<Disciplina> discipline;
	private ArrayList<Disciplina> disciplineObligatorii;
	private Hashtable<Integer, ArrayList<Disciplina>> disciplineOptionale;
	private ArrayList<Disciplina> disciplineFacultative;
	
	private ObjectTableModel<Disciplina> obligatoriiTableModel;
	private ArrayList<ObjectTableModel<Disciplina>> optionaleTableModel;
	private ObjectTableModel<Disciplina> facultativeTableModel;
	
	private OptiuneContractWrapper optiuniDAO=new OptiuneContractWrapper();
	private DisciplinaWrapper disciplineDAO=new DisciplinaWrapper();
	
	private JLabel statusLbl;
	private JButton btnSalveaza;
	private JTable tableOptional[];
	private JScrollPane scrollPaneFacultativ;
	private JTable tableFacultativ;
	private JLabel lbl1;
	private JScrollPane scrollPaneOptional;
	private int studyYear;
	private ArrayList<Integer> keysHashtable;
	
	private void prepareData(Utilizator utilizator)
	{
		if(utilizator.titlu_grupa==null || utilizator.titlu_grupa.isEmpty())
		{
			JOptionPane.showMessageDialog(null,"Nu sunteti inregistrat la nici o grupa!");
			optionaleTableModel=new ArrayList<ObjectTableModel<Disciplina>>();	//init in case the preparation fails
			return;
		}
		studyYear = utilizator.titlu_grupa.charAt(1) - '0';
		System.out.println("Studentul este in anul "+studyYear);
		
		//Get the objects		
		try {
			if(!utilizator.contractCompletat)
				//Get all the discipline
				discipline=disciplineDAO.getObjects(Constants.DISCIPLINA_TABLE,"an_studiu=\'"+studyYear+"\'");
			else
				discipline=optiuniDAO.getOptiuni(utilizator.CNP, studyYear);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Prepare arrays
		disciplineObligatorii=new ArrayList<Disciplina>();
		disciplineFacultative=new ArrayList<Disciplina>();
		disciplineOptionale=new Hashtable<Integer, ArrayList<Disciplina>>(10);
		
		//Iterate through the entries and put the in the proper container
		for(Disciplina disciplina : discipline)
		{
			if(disciplina.tip.equals(Disciplina.TipDisciplina.Obligatoriu))
				disciplineObligatorii.add(disciplina);
			else if(disciplina.tip.equals(Disciplina.TipDisciplina.Facultativ))
				disciplineFacultative.add(disciplina);
			else
			{
				if(disciplineOptionale.get(disciplina.grup)==null)
					disciplineOptionale.put(disciplina.grup, new ArrayList<Disciplina>());
				disciplineOptionale.get(disciplina.grup).add(disciplina);
			}
		}
		
		//Create table models
		try {
			obligatoriiTableModel=new ObjectTableModel<Disciplina>(Disciplina.class,
					disciplineObligatorii,
					Constants.VIEW_CONTRACT_DISCIPLINA_COLUMN_FIELD_MATCH[1],
					Constants.VIEW_CONTRACT_DISCIPLINA_COLUMN_FIELD_MATCH[0]);
			
			facultativeTableModel=new ObjectTableModel<Disciplina>(Disciplina.class,
					disciplineFacultative,
					Constants.VIEW_CONTRACT_DISCIPLINA_COLUMN_FIELD_MATCH[1],
					Constants.VIEW_CONTRACT_DISCIPLINA_COLUMN_FIELD_MATCH[0]);
			
			optionaleTableModel=new ArrayList<ObjectTableModel<Disciplina>>();
			keysHashtable=new ArrayList<Integer>();
			for(Integer key : disciplineOptionale.keySet())
			{
				keysHashtable.add(key);
				optionaleTableModel.add(new ObjectTableModel<Disciplina>(Disciplina.class,
						disciplineOptionale.get(key),
						Constants.VIEW_CONTRACT_DISCIPLINA_COLUMN_FIELD_MATCH[1],
						Constants.VIEW_CONTRACT_DISCIPLINA_COLUMN_FIELD_MATCH[0]));
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the panel.
	 */
	public AdminContractStudiiPanel(Utilizator utilizator, JLabel statusLbl) {
		this.utilizator=utilizator;
		this.statusLbl=statusLbl;
		if(this.utilizator.contractCompletat)
			this.statusLbl.setText("Contractul tau de studii a fost completat si nu mai poate fi modificat!");
		else
			this.statusLbl.setText("Completare contract studii. Te rugam selecteaza materiile optionale (cate una pe fiecare grup) si facultative pe care le doresti si apasa Salveaza.");
		
		//GUI
		setLayout(new MigLayout("", "[638.00px:1022.00px,grow]", "[][469.00,grow][51.00px]"));
		
		lbl1 = new JLabel("Acesta este contractul tau de studii pentru anul scolar curent:");
		if(this.utilizator.contractCompletat)
			lbl1.setText("Acesta este contractul tau de studii completat pentru anul scolar curent:");
		add(lbl1, "cell 0 0");
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, "cell 0 1,grow");
		
		JScrollPane scrollPaneObligatorii = new JScrollPane();
		tabbedPane.addTab("Obligatorii", null, scrollPaneObligatorii, null);
		
		prepareData(utilizator);
		
		tableObligatorii = new JTable(obligatoriiTableModel);
		tableObligatorii.setEnabled(false);
		tableObligatorii.setRowSelectionAllowed(false);
		tableObligatorii.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		if(!utilizator.contractCompletat)
			tableObligatorii.getSelectionModel().setSelectionInterval(0, disciplineObligatorii.size()-1);
		tableObligatorii.setFillsViewportHeight(true);
		scrollPaneObligatorii.setViewportView(tableObligatorii);
		
		//Insert panes for optionale
		tableOptional=new JTable[optionaleTableModel.size()];
		
		for(int i=0;i<optionaleTableModel.size();i++)
		{
			scrollPaneOptional = new JScrollPane();
			tabbedPane.addTab("Optional "+i+1, null, scrollPaneOptional, null);
			
			tableOptional[i] = new JTable(optionaleTableModel.get(i));
			tableOptional[i].setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableOptional[i].setFillsViewportHeight(true);
			if(utilizator.contractCompletat)
			{
				tableOptional[i].setEnabled(false);
			}
			scrollPaneOptional.setViewportView(tableOptional[i]);
		}
		
		scrollPaneFacultativ = new JScrollPane();
		tabbedPane.addTab("Facultativ", null, scrollPaneFacultativ, null);
		
		tableFacultativ = new JTable(facultativeTableModel);
		tableFacultativ.setToolTipText("Foloseste CTRL+Click pentru a selecta mai multe randuri.");
		tableFacultativ.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tableFacultativ.setFillsViewportHeight(true);
		if(utilizator.contractCompletat)
			tableFacultativ.setEnabled(false);
		scrollPaneFacultativ.setViewportView(tableFacultativ);
		
		JPanel panelEdit = new JPanel();
		add(panelEdit, "cell 0 2,grow");
		panelEdit.setLayout(new BorderLayout(0, 0));
		
		JLabel lblOdataCe = new JLabel("* Odata ce ai salvat optiunile tale, contractul de studii nu mai poate fi modificat!");
		lblOdataCe.setFont(new Font("Dialog", Font.ITALIC, 12));
		panelEdit.add(lblOdataCe, BorderLayout.WEST);
		
		btnSalveaza = new JButton("Salveaza");
		lblOdataCe.setLabelFor(btnSalveaza);
		panelEdit.add(btnSalveaza, BorderLayout.EAST);
		if(utilizator.contractCompletat)
			btnSalveaza.setEnabled(false);
		else
			btnSalveaza.addActionListener(this);
	}

	/* Evenimente declansate la click pe cele butonul de salvare sau la schimbarea selectiei tipului
	 *  (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		if(source.equals(btnSalveaza))
		{
			//Verificare selectii optionale
			for(int i=0;i<tableOptional.length;i++)
				if(tableOptional[i].getSelectedRowCount()!=1)
				{
					JOptionPane.showMessageDialog(null, "Trebuie aleasa cel putin o materie optionala pentru fiecare grup.", "Incomplet", JOptionPane.ERROR_MESSAGE);
					return;
				}
			//Introducem disciplinele obligatorii
			for(Disciplina disciplina : disciplineObligatorii)
				optiuniDAO.insertOptiune(disciplina, utilizator.CNP, studyYear);
			
			//Introducem disciplinele optionale
			for(int i=0;i<tableOptional.length;i++)
			{
				int indexSelectat=tableOptional[i].getSelectedRow();
				optiuniDAO.insertOptiune(disciplineOptionale.get(keysHashtable.get(i)).get(indexSelectat), utilizator.CNP, studyYear);
			}
				
			//Introducem disciplinele facultative
			for(int i=0;i<tableFacultativ.getSelectedRowCount();i++)
				optiuniDAO.insertOptiune(disciplineFacultative.get(tableFacultativ.getSelectedRows()[i]), utilizator.CNP, studyYear);
			
			//Salvam utilizatorul
			UtilizatorWrapper utilizatorWrapper=new UtilizatorWrapper();
			Utilizator utilizatorVechi=utilizator.clone();
			utilizator.contractCompletat=true;
			utilizatorWrapper.UpdateUtilizator(utilizatorVechi, utilizator);
			
			//Blocam optiunile
			btnSalveaza.setEnabled(false);
			for(int i=0;i<tableOptional.length;i++)
				tableOptional[i].setEnabled(false);
			tableFacultativ.setEnabled(false);
			this.statusLbl.setText("Optiunile tale au fost salvate!");
		}
		
	}
}
