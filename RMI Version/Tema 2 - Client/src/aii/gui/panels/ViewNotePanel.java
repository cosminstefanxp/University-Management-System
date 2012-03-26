/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii.gui.panels;
import java.awt.Color;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;
import aii.Disciplina;
import aii.Utilizator;
import aii.arhiva.Arhiva;
import aii.gui.tools.ObjectTableModel;
import aii.rad.RegistruActivitatiDidactice;


@SuppressWarnings("serial")
public class ViewNotePanel extends MainPanelAbstract implements ListSelectionListener {
	private ArrayList<Disciplina> discipline;
	private ObjectTableModel<Disciplina> disciplineTableModel;
	private JPanel panelEditInfo;
	private JTable tableDiscipline;
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public ViewNotePanel(Arhiva arhivaService, RegistruActivitatiDidactice radService,
			Utilizator utilizator, JLabel statusLabel) {
		//Initialize the MainPanelAbstract object
		super(arhivaService, radService, utilizator, statusLabel);
		
		this.statusLbl.setText("Vizualizare note. Selecteaza o disciplina pentru a ii vizualiza nota.");
		
		//Get the objects and prepare the table models		
		try {
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
		setLayout(new MigLayout("", "[799.00px:1022.00px]", "[140.00px:325.00px]"));
		
		JPanel panelEdit = new JPanel();
		add(panelEdit, "cell 0 0,grow");
		panelEdit.setLayout(null);
		
		panelEditInfo = new JPanel();
		panelEditInfo.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Setari Examene", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panelEditInfo.setBounds(12, 16, 756, 316);
		panelEdit.add(panelEditInfo);
		panelEditInfo.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Disciplina:");
		lblNewLabel.setBounds(11, 29, 273, 15);
		panelEditInfo.add(lblNewLabel);
		
		JScrollPane scrollPaneDiscipline = new JScrollPane();
		scrollPaneDiscipline.setBounds(11, 52, 368, 245);
		panelEditInfo.add(scrollPaneDiscipline);
		
		tableDiscipline = new JTable(disciplineTableModel);
		tableDiscipline.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableDiscipline.setFillsViewportHeight(true);
		tableDiscipline.getSelectionModel().addListSelectionListener(this);
		scrollPaneDiscipline.setViewportView(tableDiscipline);
		
		textField = new JTextField("Lipsa nota");
		textField.setEnabled(false);
		textField.setBounds(452, 51, 191, 33);
		panelEditInfo.add(textField);
		textField.setColumns(10);
		
	    
	    JLabel lblNota = new JLabel("Nota:");
	    lblNota.setBounds(452, 29, 273, 15);
	    panelEditInfo.add(lblNota);
		
	
	}

	/* Eveniment declansat la schimbarea selectiei
	 * (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged(ListSelectionEvent event) {
		if (event.getValueIsAdjusting())
            return;
        
		int row = tableDiscipline.getSelectedRow();
		System.out.println("Selectie modificata pe randul "+row);
		
		//Nothing selected / deselection
		if(row==-1)
		{
			System.out.println("Deselectie detectata.");
			textField.setText("Lipsa valoare!");
			return;
		}
		
		//Proof of concept for ArrayList
		ArrayList<Integer> codDisciplina=new ArrayList<Integer>();
		codDisciplina.add(discipline.get(tableDiscipline.getSelectedRow()).cod);
		
		ArrayList<Float> note;
		try {
			note=arhivaService.obtineNoteStudent(utilizator.CNP, codDisciplina);
		} catch (RemoteException e) {
			e.printStackTrace();
			return;
		}
		if(note.get(0)!=-1.0f)
			textField.setText(note.get(0).toString());
		else
			textField.setText("Lipsa nota");
		
	}
	
	
}
