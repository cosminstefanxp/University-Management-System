package aii.gui.panels;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import net.miginfocom.swing.MigLayout;
import aii.Orar;
import aii.Utilizator;
import aii.database.Constants;
import aii.database.OrarWrapper;
import aii.gui.tools.ObjectTableModel;


@SuppressWarnings("serial")
public class ViewOrarPanel extends MainPanelAbstract {
	
	@SuppressWarnings("unused")
	private Utilizator utilizator;
	private JTable table;
	private ArrayList<Orar> objects;
	private ObjectTableModel<Orar> mainTableModel;
	private OrarWrapper orareDAO=new OrarWrapper();
	
	
	private JLabel statusLbl;
	private JLabel lblOrarulTauEste;

	/**
	 * Create the panel.
	 */
	public ViewOrarPanel(Utilizator utilizator, JLabel statusLbl) {
		this.utilizator=utilizator;
		this.statusLbl=statusLbl;
		
		//Prepare study year
		if(utilizator.titlu_grupa==null || utilizator.titlu_grupa.isEmpty())
		{
			JOptionPane.showMessageDialog(null,"Nu sunteti inregistrat la nici o grupa!");
			return;
		}
		int studyYear = utilizator.titlu_grupa.charAt(1) - '0';
		System.out.println("Studentul este in anul "+studyYear);
		
		//Prepare semestru
		Calendar calendar=new GregorianCalendar();
		int semestru;
		Calendar inceputS1=new GregorianCalendar(); inceputS1.set(calendar.get(Calendar.YEAR), Calendar.OCTOBER, 1);
		Calendar finalS1=new GregorianCalendar(); finalS1.set(calendar.get(Calendar.YEAR)+1, Calendar.MARCH, 1);
		if(calendar.after(inceputS1) && calendar.before(finalS1) )
		{
			this.statusLbl.setText("Se vizualizeaza orarul pentru primul semestru.");
			semestru=1;
		}
		else
		{
			this.statusLbl.setText("Se vizualizeaza orarul pentru al doilea semestru.");
			semestru=2;			
		}
		
		
		//Get the objects and prepare the table models		
		try {
			//Table model for "Orare"
			orareDAO.setNameMatch(Constants.ORAR_STUDENT_FIELD_MATCH);
			objects=orareDAO.getOrareParticularizat(utilizator.CNP, studyYear, utilizator.titlu_grupa, semestru);
			mainTableModel=new ObjectTableModel<Orar>(Orar.class,
					objects,
					Constants.VIEW_ORAR_STUDENT_COLUMN_FIELD_MATCH[1],
					Constants.VIEW_ORAR_STUDENT_COLUMN_FIELD_MATCH[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//GUI
		setLayout(new MigLayout("", "[799.00px:1022.00px]", "[][132.00px:541.00px][]"));
		
		lblOrarulTauEste = new JLabel("Orarul tau este:");
		add(lblOrarulTauEste, "cell 0 0");
		
		JScrollPane scrollPaneTable = new JScrollPane();
		add(scrollPaneTable, "cell 0 1,grow");
		
		JLabel lblDisclaimer = new JLabel("* Se afiseaza doar orarul pentru grupa ta si materiile alese de tine.");
		add(lblDisclaimer, "cell 0 2");
		
		table = new JTable(mainTableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(true);
		table.setEnabled(false);
		scrollPaneTable.setViewportView(table);
		
	}

	
}
