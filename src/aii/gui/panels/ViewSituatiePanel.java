package aii.gui.panels;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import aii.SituatieAn;
import aii.Utilizator;
import aii.database.DatabaseConnection;



@SuppressWarnings("serial")
public class ViewSituatiePanel extends MainPanelAbstract {
	
	@SuppressWarnings("unused")
	private Utilizator utilizator;
	
	private JLabel statusLbl;
	private JTextField textField11;
	private JTextField textField12;
	private JTextField textField10;
	private JTextField textField21;
	private JTextField textField22;
	private JTextField textField20;
	private JTextField textField31;
	private JTextField textField32;
	private JTextField textField30;
	private JTextField textField41;
	private JTextField textField42;
	private JTextField textField40;

	/**
	 * Create the panel.
	 */
	public ViewSituatiePanel(Utilizator utilizator, JLabel statusLbl) {
		this.utilizator=utilizator;
		this.statusLbl=statusLbl;
		setLayout(null);
		
		JLabel lblSemestrul1 = new JLabel("Semestrul 1");
		lblSemestrul1.setBounds(63, 8, 84, 15);
		add(lblSemestrul1);
		
		JLabel lblSemestrul2 = new JLabel("Semestrul 2");
		lblSemestrul2.setBounds(159, 8, 84, 15);
		add(lblSemestrul2);
		
		JLabel lblAnual = new JLabel("Anual");
		lblAnual.setBounds(273, 8, 40, 15);
		add(lblAnual);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(8, 35, 331, 26);
		add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblAnul1 = new JLabel("Anul 1:");
		lblAnul1.setBounds(0, 5, 48, 15);
		panel_1.add(lblAnul1);
		
		textField11 = new JTextField();
		textField11.setEnabled(false);
		textField11.setHorizontalAlignment(SwingConstants.CENTER);
		textField11.setBounds(55, 0, 84, 26);
		panel_1.add(textField11);
		textField11.setColumns(10);
		
		textField12 = new JTextField();
		textField12.setEnabled(false);
		textField12.setHorizontalAlignment(SwingConstants.CENTER);
		textField12.setBounds(151, 0, 84, 26);
		panel_1.add(textField12);
		textField12.setColumns(10);
		
		textField10 = new JTextField();
		textField10.setEnabled(false);
		textField10.setHorizontalAlignment(SwingConstants.CENTER);
		textField10.setBounds(247, 0, 84, 26);
		panel_1.add(textField10);
		textField10.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(8, 77, 331, 26);
		add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblAnul2 = new JLabel("Anul 2:");
		lblAnul2.setBounds(0, 5, 48, 15);
		panel_2.add(lblAnul2);
		
		textField21 = new JTextField();
		textField21.setEnabled(false);
		textField21.setHorizontalAlignment(SwingConstants.CENTER);
		textField21.setBounds(55, 0, 84, 26);
		panel_2.add(textField21);
		textField21.setColumns(10);
		
		textField22 = new JTextField();
		textField22.setEnabled(false);
		textField22.setHorizontalAlignment(SwingConstants.CENTER);
		textField22.setBounds(151, 0, 84, 26);
		panel_2.add(textField22);
		textField22.setColumns(10);
		
		textField20 = new JTextField();
		textField20.setEnabled(false);
		textField20.setHorizontalAlignment(SwingConstants.CENTER);
		textField20.setBounds(247, 0, 84, 26);
		panel_2.add(textField20);
		textField20.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(8, 115, 331, 26);
		add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblAnul3 = new JLabel("Anul 3:");
		lblAnul3.setBounds(0, 5, 48, 15);
		panel_3.add(lblAnul3);
		
		textField31 = new JTextField();
		textField31.setEnabled(false);
		textField31.setHorizontalAlignment(SwingConstants.CENTER);
		textField31.setBounds(55, 0, 84, 26);
		panel_3.add(textField31);
		textField31.setColumns(10);
		
		textField32 = new JTextField();
		textField32.setEnabled(false);
		textField32.setHorizontalAlignment(SwingConstants.CENTER);
		textField32.setBounds(151, 0, 84, 26);
		panel_3.add(textField32);
		textField32.setColumns(10);
		
		textField30 = new JTextField();
		textField30.setEnabled(false);
		textField30.setHorizontalAlignment(SwingConstants.CENTER);
		textField30.setBounds(247, 0, 84, 26);
		panel_3.add(textField30);
		textField30.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(8, 153, 331, 26);
		add(panel_4);
		panel_4.setLayout(null);
		
		JLabel lblAnul4 = new JLabel("Anul 4:");
		lblAnul4.setBounds(0, 5, 48, 15);
		panel_4.add(lblAnul4);
		
		textField41 = new JTextField();
		textField41.setEnabled(false);
		textField41.setHorizontalAlignment(SwingConstants.CENTER);
		textField41.setBounds(55, 0, 84, 26);
		panel_4.add(textField41);
		textField41.setColumns(10);
		
		textField42 = new JTextField();
		textField42.setEnabled(false);
		textField42.setHorizontalAlignment(SwingConstants.CENTER);
		textField42.setBounds(151, 0, 84, 26);
		panel_4.add(textField42);
		textField42.setColumns(10);
		
		textField40 = new JTextField();
		textField40.setEnabled(false);
		textField40.setHorizontalAlignment(SwingConstants.CENTER);
		textField40.setBounds(247, 0, 84, 26);
		panel_4.add(textField40);
		textField40.setColumns(10);
		this.statusLbl.setText("Vizualizare situatie scolara ani de studiu. inclusiv. In semestrul curent, media poate fi zero, in cazul in care nu au fost introduse toate notele in catalog");
		
		//Get the objects and prepare the table models
		String unprocessedQuery="SELECT AVG(IFNULL(nota,0)) " +
				"FROM optiuni_contract o " +
				"INNER JOIN disciplina d " +
				"	ON d.cod=o.cod_disciplina " +
				"LEFT JOIN catalog c " +
				"	ON o.cod_disciplina=c.cod_disciplina " +
				"WHERE d.an_studiu=\'%d\' " +
				"	AND o.cnp_student=\'%s\'";
		
		//Prepare study year
		if(utilizator.titlu_grupa==null || utilizator.titlu_grupa.isEmpty())
		{
			JOptionPane.showMessageDialog(null,"Nu sunteti inregistrat la nici o grupa!");
			return;
		}
		int studyYear = utilizator.titlu_grupa.charAt(1) - '0';
		System.out.println("Studentul este in anul "+studyYear);
			
		try {
			//For each semester, get the requested values
			int i;
			for(i=1;i<=studyYear;i++)
			{
				String query=String.format(unprocessedQuery, i, utilizator.CNP);
				SituatieAn situatie=new SituatieAn();
				//Situatia anuala
				situatie.an=DatabaseConnection.getSingleValueResult(query);
				//Situatia semestrul 1
				situatie.sem1=DatabaseConnection.getSingleValueResult(query+" AND d.semestru=\'1\'");
				//Situatia semestrul 2
				situatie.sem2=DatabaseConnection.getSingleValueResult(query+" AND d.semestru=\'2\'");
				
				//Use the data
				if(i==1) {
					textField10.setText(Float.toString(situatie.an)); textField11.setText(Float.toString(situatie.sem1)); textField12.setText(Float.toString(situatie.sem2));
				}
				
				if(i==2) {
					textField20.setText(Float.toString(situatie.an)); textField21.setText(Float.toString(situatie.sem1)); textField22.setText(Float.toString(situatie.sem2));
				}
				
				if(i==3) {
					textField30.setText(Float.toString(situatie.an)); textField31.setText(Float.toString(situatie.sem1)); textField32.setText(Float.toString(situatie.sem2));
				}
				
				if(i==4) {
					textField40.setText(Float.toString(situatie.an)); textField41.setText(Float.toString(situatie.sem1)); textField42.setText(Float.toString(situatie.sem2));
				}
			}
			for(;i<=4;i++)
			{
				if(i==2) panel_2.setVisible(false);
				if(i==3) panel_3.setVisible(false);
				if(i==4) panel_4.setVisible(false);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
