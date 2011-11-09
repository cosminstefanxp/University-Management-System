package aii.gui.panels;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import net.miginfocom.swing.MigLayout;
import aii.Examen;
import aii.Utilizator;
import aii.database.Constants;
import aii.database.ExamenWrapper;
import aii.gui.tools.ObjectTableModel;


@SuppressWarnings("serial")
public class ViewExamenePanel extends MainPanelAbstract {
	
	@SuppressWarnings("unused")
	private Utilizator utilizator;
	private JTable table;
	private ArrayList<Examen> objects;
	private ObjectTableModel<Examen> mainTableModel=null;
	private ExamenWrapper exameneDAO=new ExamenWrapper();
	
	
	private JLabel statusLbl;
	private JLabel lblSesiuneaTaEste;

	/**
	 * Create the panel.
	 */
	public ViewExamenePanel(Utilizator utilizator, JLabel statusLbl) {
		this.utilizator=utilizator;
		this.statusLbl=statusLbl;
		this.statusLbl.setText("Vizualizeaza programul examenelor tale pentru perioada urmatoare.");
		
		//Prepare study year
		if(utilizator.titlu_grupa==null || utilizator.titlu_grupa.isEmpty())
		{
			JOptionPane.showMessageDialog(null,"Nu sunteti inregistrat la nici o grupa!");
			return;
		}
		int studyYear = utilizator.titlu_grupa.charAt(1) - '0';
		System.out.println("Studentul este in anul "+studyYear);
		
		//Get the objects and prepare the table models		
		try {
			//Table model for "Examene"
			exameneDAO.setNameMatch(Constants.EXAMEN_STUDENT_FIELD_MATCH);
			objects=exameneDAO.getExameneParticularizat(utilizator.titlu_grupa);
			mainTableModel=new ObjectTableModel<Examen>(Examen.class,
					objects,
					Constants.VIEW_EXAMEN_STUDENT_COLUMN_FIELD_MATCH[1],
					Constants.VIEW_EXAMEN_STUDENT_COLUMN_FIELD_MATCH[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//GUI
		setLayout(new MigLayout("", "[799.00px:1022.00px]", "[][132.00px:605.00px]"));
		
		lblSesiuneaTaEste = new JLabel("Programul examenelor tale este:");
		add(lblSesiuneaTaEste, "cell 0 0");
		
		JScrollPane scrollPaneTable = new JScrollPane();
		add(scrollPaneTable, "cell 0 1,grow");
		
		table = new JTable(mainTableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(true);
		scrollPaneTable.setViewportView(table);
		
	}

	
}
