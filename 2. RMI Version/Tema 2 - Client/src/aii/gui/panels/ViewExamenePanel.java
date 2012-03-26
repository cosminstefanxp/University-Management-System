/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii.gui.panels;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import net.miginfocom.swing.MigLayout;
import aii.Constants;
import aii.Examen;
import aii.Utilizator;
import aii.arhiva.Arhiva;
import aii.gui.tools.ObjectTableModel;
import aii.rad.RegistruActivitatiDidactice;


@SuppressWarnings("serial")
public class ViewExamenePanel extends MainPanelAbstract {
	
	private JTable table;
	private ArrayList<Examen> objects;
	private ObjectTableModel<Examen> mainTableModel=null;

	private JLabel lblSesiuneaTaEste;

	/**
	 * Create the panel.
	 */
	public ViewExamenePanel(Arhiva arhivaService, RegistruActivitatiDidactice radService,
			Utilizator utilizator, JLabel statusLabel) {
		//Initialize the MainPanelAbstract object
		super(arhivaService, radService, utilizator, statusLabel);
		
		this.statusLbl.setText("Vizualizeaza programul examenelor tale pentru perioada urmatoare.");
		
		//Prepare study year
		if(utilizator.titlu_grupa==null || utilizator.titlu_grupa.isEmpty())
		{
			JOptionPane.showMessageDialog(null,"Nu sunteti inregistrat la nici o grupa!");
			return;
		}
		if(utilizator.titlu_grupa.equals("licentiat"))
		{
			JOptionPane.showMessageDialog(null,"Sunteti licentiat!");
			return;
		}
		int studyYear = utilizator.titlu_grupa.charAt(1) - '0';
		System.out.println("Studentul este in anul "+studyYear);
		
		//Get the objects and prepare the table models		
		try {
			//Table model for "Examene"
			objects=radService.obtineProgramareExamene(utilizator.CNP, utilizator.titlu_grupa);
			mainTableModel=new ObjectTableModel<Examen>(Examen.class,
					objects,
					Constants.VIEW_EXAMEN_STUDENT_COLUMN_FIELD_MATCH[1],
					Constants.VIEW_EXAMEN_STUDENT_COLUMN_FIELD_MATCH[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//GUI
		setLayout(new MigLayout("", "[799.00px:1022.00px]", "[][132.00px:546.00px][]"));
		
		lblSesiuneaTaEste = new JLabel("Programul examenelor tale este:");
		add(lblSesiuneaTaEste, "cell 0 0");
		
		JScrollPane scrollPaneTable = new JScrollPane();
		add(scrollPaneTable, "cell 0 1,grow");
		
		JLabel lblDisclaimer = new JLabel("* Se afiseaza doar programarile de examen pentru grupa ta.");
		add(lblDisclaimer, "cell 0 2");
		
		table = new JTable(mainTableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(true);
		table.setEnabled(false);
		scrollPaneTable.setViewportView(table);
		
	}

	
}
