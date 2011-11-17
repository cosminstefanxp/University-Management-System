package aii.gui.frames;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;
import aii.Utilizator;
import aii.Utilizator.Tip;
import aii.arhiva.Arhiva;
import aii.gui.panels.AdminActivitatiPanel;
import aii.gui.panels.AdminCatalogPanel;
import aii.gui.panels.AdminDisciplinePanel;
import aii.gui.panels.PersonalDataPanel;
import aii.rad.RegistruActivitatiDidactice;

/**
 * The Administrator frame that contains all the features that the admin uses.
 */
@SuppressWarnings("serial")
public class TeacherFrame extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JPanel mainPanel;
	private JButton btnSetariPersonale;
	private JButton btnCatalog;
	private Utilizator utilizator;
	public JLabel statusLbl;
	private JButton btnOrar;
	private JButton btnDiscipline;
	private Arhiva arhivaService;
	private RegistruActivitatiDidactice radService;


	/**
	 * Create the frame.
	 */
	public TeacherFrame(Arhiva arhivaService, RegistruActivitatiDidactice radService, Utilizator utilizator) {
		this.utilizator=utilizator;
		this.arhivaService=arhivaService;
		this.radService=radService;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1052, 800);
		
		/******* MENUs ********/
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnAdministrare = new JMenu("Administrare");
		menuBar.add(mnAdministrare);
		
		JMenuItem mntmCatalog = new JMenuItem("Catalog");
		mntmCatalog.addActionListener(this);
		mnAdministrare.add(mntmCatalog);
		
		JSeparator separator = new JSeparator();
		mnAdministrare.add(separator);

		JCheckBoxMenuItem chckbxmntmSefCatedra = new JCheckBoxMenuItem("Sef de Catedra");
		chckbxmntmSefCatedra.setEnabled(false);
		mnAdministrare.add(chckbxmntmSefCatedra);
		
		JMenuItem mntmDiscipline = new JMenuItem("Discipline");
		mntmDiscipline.addActionListener(this);
		mntmDiscipline.setEnabled(false);
		mnAdministrare.add(mntmDiscipline);
		
		JMenuItem mntmOrare = new JMenuItem("Activitati de predare");
		mntmOrare.addActionListener(this);
		mntmOrare.setEnabled(false);
		mnAdministrare.add(mntmOrare);
		
		JMenu mnSetari = new JMenu("Setari");
		menuBar.add(mnSetari);
		
		JMenuItem mntmSetariPersonale = new JMenuItem("Setari Personale");
		mntmSetariPersonale.addActionListener(this);
		mnSetari.add(mntmSetariPersonale);
		
		/******* FULL CONTENT PANEL ********/
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[1020.00px]", "[30.00px][17.00px][640.00px][40.00]"));
		
		JLabel label = new JLabel("Universitatea Politehnica Bucuresti");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Dialog", Font.BOLD, 25));
		contentPane.add(label, "cell 0 0,alignx center,aligny top");
		
		JLabel lblBineAiVenit = new JLabel("Bine ati venit in consola cadrelor didactice ale universitatii!");
		lblBineAiVenit.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblBineAiVenit, "cell 0 1,alignx center,aligny top");

		statusLbl = new JLabel("Autentificat ca "+utilizator.tip);
		statusLbl.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 11));
		contentPane.add(statusLbl, "cell 0 3");
		
		/******* MAIN PANEL ********/
		mainPanel = new JPanel();
		mainPanel.setBorder(new LineBorder(UIManager.getColor("ToolBar.borderColor")));
		mainPanel.setLayout(null);
		contentPane.add(mainPanel, "cell 0 2,grow");
		
		btnSetariPersonale = new JButton("Setari personale");
		btnSetariPersonale.setBounds(25, 50, 198, 25);
		btnSetariPersonale.addActionListener(this);
		mainPanel.add(btnSetariPersonale);
		
		btnCatalog = new JButton("Catalog");
		btnCatalog.setBounds(25, 88, 198, 25);
		btnCatalog.addActionListener(this);
		mainPanel.add(btnCatalog);
		
		JLabel lblFolositiMeniulSau = new JLabel("Folositi meniul sau unul dintre butoanele de mai jos pentru a interactiona cu sistemul:");
		lblFolositiMeniulSau.setBounds(25, 23, 613, 15);
		mainPanel.add(lblFolositiMeniulSau);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(25, 125, 198, 2);
		mainPanel.add(separator_1);
		
		btnDiscipline = new JButton("Administrare Discipline");
		btnDiscipline.addActionListener(this);
		btnDiscipline.setEnabled(false);
		btnDiscipline.setBounds(25, 166, 198, 25);
		mainPanel.add(btnDiscipline);
		
		btnOrar = new JButton("Activitati de predare");
		btnOrar.addActionListener(this);
		btnOrar.setEnabled(false);
		btnOrar.setBounds(25, 203, 198, 25);
		mainPanel.add(btnOrar);
		
		JCheckBox chckbxSefDeCatedra = new JCheckBox("Sef de catedra");
		chckbxSefDeCatedra.setEnabled(false);
		chckbxSefDeCatedra.setBounds(25, 135, 129, 23);
		mainPanel.add(chckbxSefDeCatedra);
		
		//Other initializations
		if(utilizator.tip==Tip.SEF_CATEDRA)
		{
			//Buttons
			chckbxSefDeCatedra.setSelected(true);
			btnDiscipline.setEnabled(true);
			btnOrar.setEnabled(true);
			
			//Menu
			chckbxmntmSefCatedra.setSelected(true);
			mntmDiscipline.setEnabled(true);
			mntmOrare.setEnabled(true);
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
        if (source instanceof JMenu)
            System.out.println (((JMenu)source).getText());
        
        // Check menu item events
        if (source instanceof JMenuItem)
        {
        	//Date personale
        	JMenuItem sourceItem=(JMenuItem)source;
        	if(sourceItem.getText().toLowerCase().equals("setari personale"))
        	{
        		System.out.println("Menu: Setari personale");
        		
        		contentPane.remove(mainPanel);
        		mainPanel=new PersonalDataPanel(utilizator, statusLbl);
        		
        		contentPane.add(mainPanel, "cell 0 2,grow");
        		contentPane.revalidate();        		
        	}
        	else if(sourceItem.getText().toLowerCase().equals("catalog"))
        	{
        		System.out.println("Meniu: Administrare Catalog");
        		
        		contentPane.remove(mainPanel);
        		mainPanel=new AdminCatalogPanel(utilizator, statusLbl);
        		
        		contentPane.add(mainPanel, "cell 0 2,grow");
        		contentPane.revalidate();        		
        	}
        	else if(sourceItem.getText().toLowerCase().equals("discipline"))
        	{
        		System.out.println("Meniu: Administrare Discipline");
        		
        		contentPane.remove(mainPanel);
        		mainPanel=new AdminDisciplinePanel(arhivaService, radService, utilizator, statusLbl);
        		
        		contentPane.add(mainPanel, "cell 0 2,grow");
        		contentPane.revalidate();        		
        	}
        	else if(sourceItem.getText().toLowerCase().equals("activitati de predare"))
        	{
        		System.out.println("Meniu: Administrare Orare");
        		
        		contentPane.remove(mainPanel);
        		mainPanel=new AdminActivitatiPanel(arhivaService, radService, utilizator, statusLbl);
        		
        		contentPane.add(mainPanel, "cell 0 2,grow");
        		contentPane.revalidate();        		
        	}
        
        }
        
        // Check buttons
        if(source instanceof JButton)
        {
        	if(source==this.btnSetariPersonale)
        	{
        		System.out.println("Buton: Setari personale");
        		
        		contentPane.remove(mainPanel);
        		mainPanel=new PersonalDataPanel(utilizator, statusLbl);
        		
        		contentPane.add(mainPanel, "cell 0 2,grow");
        		contentPane.revalidate();        		
        	}
        	else if(source==this.btnCatalog)
        	{
        		System.out.println("Buton: Catalog");
        		
        		contentPane.remove(mainPanel);
        		mainPanel=new AdminCatalogPanel(utilizator, statusLbl);
        		
        		contentPane.add(mainPanel, "cell 0 2,grow");
        		contentPane.revalidate();        		
        	}
        	else if(source==this.btnOrar)
        	{
        		System.out.println("Buton: Orar");
        		
        		contentPane.remove(mainPanel);
        		mainPanel=new AdminActivitatiPanel(arhivaService, radService, utilizator, statusLbl);
        		
        		contentPane.add(mainPanel, "cell 0 2,grow");
        		contentPane.revalidate();        		
        	}
        	else if(source==this.btnDiscipline)
        	{
        		System.out.println("Buton: Discipline");
        		
        		contentPane.remove(mainPanel);
        		mainPanel=new AdminDisciplinePanel(arhivaService, radService, utilizator, statusLbl);
        		
        		contentPane.add(mainPanel, "cell 0 2,grow");
        		contentPane.revalidate();        		
        	}
        }
		
	}
}
