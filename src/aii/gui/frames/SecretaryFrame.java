package aii.gui.frames;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;
import aii.Utilizator;
import aii.gui.panels.AdminGrupePanel;
import aii.gui.panels.AdminOrarePanel;
import aii.gui.panels.PersonalDataPanel;

/**
 * The Administrator frame that contains all the features that the admin uses.
 */
@SuppressWarnings("serial")
public class SecretaryFrame extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JPanel mainPanel;
	private JButton btnSetariPersonale;
	private JButton btnAdminGrupe;
	private Utilizator utilizator;
	public JLabel statusLbl;
	private JButton btnAdminOrar;
	private JButton btnProgramareExamene;


	/**
	 * Create the frame.
	 */
	public SecretaryFrame(Utilizator utilizator) {
		this.utilizator=utilizator;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1052, 800);
		
		/******* MENUs ********/
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnAdministrare = new JMenu("Administrare");
		menuBar.add(mnAdministrare);
		
		JMenuItem mntmGrupe = new JMenuItem("Grupe");
		mntmGrupe.addActionListener(this);
		mnAdministrare.add(mntmGrupe);
		
		JMenuItem mntmOrar = new JMenuItem("Orar");
		mntmOrar.addActionListener(this);
		mnAdministrare.add(mntmOrar);
		
		JMenuItem mntmProgramareExamene = new JMenuItem("Programare examene");
		mntmProgramareExamene.addActionListener(this);
		mnAdministrare.add(mntmProgramareExamene);
		
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
		
		JLabel lblBineAiVenit = new JLabel("Bine ati venit in consola pentru secretar a universitatii!");
		lblBineAiVenit.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblBineAiVenit, "cell 0 1,alignx center,aligny top");

		statusLbl = new JLabel("Autentificat...");
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
		
		btnAdminGrupe = new JButton("Administrare grupe");
		btnAdminGrupe.setBounds(25, 88, 198, 25);
		btnAdminGrupe.addActionListener(this);
		mainPanel.add(btnAdminGrupe);
		
		btnAdminOrar = new JButton("Administrare orar");
		btnAdminOrar.setBounds(25, 125, 198, 25);
		btnAdminOrar.addActionListener(this);
		mainPanel.add(btnAdminOrar);
		
		btnProgramareExamene = new JButton("Programare examene");
		btnProgramareExamene.setBounds(25, 162, 198, 25);
		btnProgramareExamene.addActionListener(this);
		mainPanel.add(btnProgramareExamene);
		
		JLabel lblFolositiMeniulSau = new JLabel("Folositi meniul sau unul dintre butoanele de mai jos pentru a interactiona cu sistemul:");
		lblFolositiMeniulSau.setBounds(25, 23, 613, 15);
		mainPanel.add(lblFolositiMeniulSau);
		
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
        	else if(sourceItem.getText().toLowerCase().equals("orar"))
        	{
        		System.out.println("Meniu: Administrare Orar");
        		
        		contentPane.remove(mainPanel);
        		mainPanel=new AdminOrarePanel(utilizator, statusLbl);
        		
        		contentPane.add(mainPanel, "cell 0 2,grow");
        		contentPane.revalidate();        		
        	}
        	else if(sourceItem.getText().toLowerCase().equals("grupe"))
        	{
        		System.out.println("Meniu: Administrare Grupe");
        		
        		contentPane.remove(mainPanel);
        		mainPanel=new AdminGrupePanel(utilizator, statusLbl);
        		
        		contentPane.add(mainPanel, "cell 0 2,grow");
        		contentPane.revalidate();        		
        	}
        	else if(sourceItem.getText().toLowerCase().equals("programare examene"))
        	{
        		System.out.println("Meniu: Programare Examene");
        		
        		contentPane.remove(mainPanel);
        		//mainPanel=new AdminUsersPanel(utilizator, statusLbl);
        		
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
        	else if(source==this.btnAdminGrupe)
        	{
        		System.out.println("Buton: Administrare Grupe");
        		
        		contentPane.remove(mainPanel);
        		mainPanel=new AdminGrupePanel(utilizator, statusLbl);
        		
        		contentPane.add(mainPanel, "cell 0 2,grow");
        		contentPane.revalidate();        		
        	}
        	else if(source==this.btnAdminOrar)
        	{
        		System.out.println("Buton: Administrare Orar");
        		
        		contentPane.remove(mainPanel);
        		mainPanel=new AdminOrarePanel(utilizator, statusLbl);
        		
        		contentPane.add(mainPanel, "cell 0 2,grow");
        		contentPane.revalidate();        		
        	}
        	else if(source==this.btnProgramareExamene)
        	{
        		System.out.println("Buton: Programare Examen");
        		
        		contentPane.remove(mainPanel);
        		//mainPanel=new AdminUsersPanel(utilizator, statusLbl);
        		
        		contentPane.add(mainPanel, "cell 0 2,grow");
        		contentPane.revalidate();        		
        	}
        }
		
	}
}
