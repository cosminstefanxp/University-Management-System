/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii.gui.panels;
import java.rmi.RemoteException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import aii.SituatieScolara;
import aii.Utilizator;
import aii.arhiva.Arhiva;
import aii.rad.RegistruActivitatiDidactice;



@SuppressWarnings("serial")
public class ViewSituatiePanel extends MainPanelAbstract {
	

	private JTextField textFields2;
	private JTextField textFieldAnualAritm;
	private JTextField textFieldCredit;
	private JTextField textFieldAnualPonderat;
	private JLabel lblAnualPonderat;
	private JTextField textFields1;
	private JLabel lblRestante;
	private JTextField textFieldRestante;

	/**
	 * Create the panel.
	 */
	public ViewSituatiePanel(Arhiva arhivaService, RegistruActivitatiDidactice radService,
			Utilizator utilizator, JLabel statusLabel) {
		//Initialize the MainPanelAbstract object
		super(arhivaService, radService, utilizator, statusLabel);
		
		setLayout(null);
		
		JLabel lblSemestrul2 = new JLabel("Semestrul 2");
		lblSemestrul2.setBounds(57, 78, 84, 15);
		add(lblSemestrul2);
		
		JLabel lblAnual = new JLabel("Anual");
		lblAnual.setBounds(101, 116, 40, 15);
		add(lblAnual);
		
		JLabel lblCredite = new JLabel("Credite");
		lblCredite.setBounds(89, 154, 52, 15);
		add(lblCredite);
		
		textFieldCredit = new JTextField();
		textFieldCredit.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldCredit.setEnabled(false);
		textFieldCredit.setColumns(10);
		textFieldCredit.setBounds(159, 149, 84, 26);
		add(textFieldCredit);
		
		textFieldAnualPonderat = new JTextField();
		textFieldAnualPonderat.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldAnualPonderat.setEnabled(false);
		textFieldAnualPonderat.setColumns(10);
		textFieldAnualPonderat.setBounds(159, 187, 84, 26);
		add(textFieldAnualPonderat);
		this.statusLbl.setText("Vizualizare situatie scolara an de studiu curent. In semestrul curent, mediile poate fi zero, in cazul in care nu au fost introduse toate notele in catalog");
		
		lblAnualPonderat = new JLabel("Anual ponderat");
		lblAnualPonderat.setBounds(37, 192, 110, 15);
		add(lblAnualPonderat);
		
		textFields2 = new JTextField();
		textFields2.setBounds(159, 73, 84, 26);
		add(textFields2);
		textFields2.setEnabled(false);
		textFields2.setHorizontalAlignment(SwingConstants.CENTER);
		textFields2.setColumns(10);
		
		textFieldAnualAritm = new JTextField();
		textFieldAnualAritm.setBounds(159, 111, 84, 26);
		add(textFieldAnualAritm);
		textFieldAnualAritm.setEnabled(false);
		textFieldAnualAritm.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldAnualAritm.setColumns(10);
		
		JLabel lblSemestrul = new JLabel("Semestrul 1");
		lblSemestrul.setBounds(63, 40, 84, 15);
		add(lblSemestrul);
		
		textFields1 = new JTextField();
		textFields1.setHorizontalAlignment(SwingConstants.CENTER);
		textFields1.setEnabled(false);
		textFields1.setColumns(10);
		textFields1.setBounds(159, 35, 84, 26);
		add(textFields1);
		
		lblRestante = new JLabel("Restante");
		lblRestante.setBounds(76, 230, 65, 15);
		add(lblRestante);
		
		textFieldRestante = new JTextField();
		textFieldRestante.setText("0.0");
		textFieldRestante.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldRestante.setEnabled(false);
		textFieldRestante.setColumns(10);
		textFieldRestante.setBounds(159, 225, 84, 26);
		add(textFieldRestante);
		
		//Prepare study year
		if(utilizator.titlu_grupa==null || utilizator.titlu_grupa.isEmpty())
		{
			JOptionPane.showMessageDialog(null,"Nu sunteti inregistrat la nici o grupa!");
			return;
		}
		
		if(utilizator.titlu_grupa.equals("licentiat") || utilizator.titlu_grupa.equals("licent"))
		{
			JOptionPane.showMessageDialog(null,"Sunteti licentiat");
			return;	
		}
		int studyYear = utilizator.titlu_grupa.charAt(1) - '0';
		System.out.println("Studentul este in anul "+studyYear);
		
		//Obtine campuri
		SituatieScolara situatie;
		try {
			situatie = arhivaService.obtineSituatieScolara(utilizator.CNP, studyYear);
		} catch (RemoteException e) {
			e.printStackTrace();
			return;
		}
		
		textFieldAnualAritm.setText(Float.toString(situatie.getMedieAritmetica()));
		textFieldAnualPonderat.setText(Float.toString(situatie.getMedieGenerala()));
		textFields1.setText(Float.toString(situatie.getMedieSemestru1()));
		textFields2.setText(Float.toString(situatie.getMedieSemestru2()));
		textFieldCredit.setText(Integer.toString(situatie.getPuncteCredit()));
		textFieldRestante.setText(Integer.toString(situatie.getRestante()));
		

			
		
		
		
	}
}
