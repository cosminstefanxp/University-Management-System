/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
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
import aii.arhiva.Arhiva;
import aii.gui.panels.ViewExamenePanel;
import aii.gui.panels.ViewNotePanel;
import aii.gui.panels.ViewOrarPanel;
import aii.gui.panels.ViewSituatiePanel;
import aii.rad.RegistruActivitatiDidactice;

/**
 * The Administrator frame that contains all the features that the admin uses.
 */
@SuppressWarnings("serial")
public class StudentFrame extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JPanel mainPanel;
	private Utilizator utilizator;
	public JLabel statusLbl;
	private JButton btnOrar;
	private JButton btnSituatieScolara;
	private JButton btnProgramareExamene;
	private Arhiva arhivaService;
	private RegistruActivitatiDidactice radService;
	private JButton btnVizualizareNote;


	/**
	 * Create the frame.
	 */
	public StudentFrame(Arhiva arhivaService, RegistruActivitatiDidactice radService, Utilizator utilizator) {
		this.utilizator=utilizator;
		this.arhivaService=arhivaService;
		this.radService=radService;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1052, 800);
		
		/******* MENUs ********/
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnVizualizare = new JMenu("Vizualizare");
		menuBar.add(mnVizualizare);
		
		JMenuItem mntmOrar = new JMenuItem("Orar");
		mntmOrar.addActionListener(this);
		mnVizualizare.add(mntmOrar);
		
		JMenuItem mntmSituatieScoalara = new JMenuItem("Situatie Scolara");
		mntmSituatieScoalara.addActionListener(this);
		mnVizualizare.add(mntmSituatieScoalara);
		
		JMenuItem mntmProgramareExamene = new JMenuItem("Programare Examene");
		mntmProgramareExamene.addActionListener(this);
		mnVizualizare.add(mntmProgramareExamene);
		
		/******* FULL CONTENT PANEL ********/
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[1020.00px]", "[30.00px][17.00px][640.00px][40.00]"));
		
		JLabel label = new JLabel("Universitatea Politehnica Bucuresti");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Dialog", Font.BOLD, 25));
		contentPane.add(label, "cell 0 0,alignx center,aligny top");
		
		JLabel lblBineAiVenit = new JLabel("Bine ati venit in consola pentru student a universitatii!");
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
		
		btnOrar = new JButton("Vizualizare Orar");
		btnOrar.setBounds(25, 125, 198, 25);
		btnOrar.addActionListener(this);
		mainPanel.add(btnOrar);
		
		btnSituatieScolara = new JButton("Situatie Scolara");
		btnSituatieScolara.setBounds(25, 88, 198, 25);
		btnSituatieScolara.addActionListener(this);
		mainPanel.add(btnSituatieScolara);
		
		btnProgramareExamene = new JButton("Programare Examene");
		btnProgramareExamene.setBounds(25, 51, 198, 25);
		btnProgramareExamene.addActionListener(this);
		mainPanel.add(btnProgramareExamene);
		
		JLabel lblFolositiMeniulSau = new JLabel("Folositi meniul sau unul dintre butoanele de mai jos pentru a interactiona cu sistemul:");
		lblFolositiMeniulSau.setBounds(25, 23, 613, 15);
		mainPanel.add(lblFolositiMeniulSau);
		
		btnVizualizareNote = new JButton("Vizualizare Note");
		btnVizualizareNote.setBounds(25, 162, 198, 25);
		btnVizualizareNote.addActionListener(this);
		mainPanel.add(btnVizualizareNote);
		

		
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
        	if(sourceItem.getText().toLowerCase().equals("orar"))
        	{
        		System.out.println("Meniu: Vizualizare Orar");
        		
        		contentPane.remove(mainPanel);
        		mainPanel=new ViewOrarPanel(arhivaService, radService, utilizator, statusLbl);
        		
        		contentPane.add(mainPanel, "cell 0 2,grow");
        		contentPane.revalidate();        		
        	}
        	else if(sourceItem.getText().toLowerCase().equals("situatie scolara"))
        	{
        		System.out.println("Meniu: Vizualizare situatie scolara");
        		
        		contentPane.remove(mainPanel);
        		mainPanel=new ViewSituatiePanel(arhivaService, radService, utilizator, statusLbl);
        		
        		contentPane.add(mainPanel, "cell 0 2,grow");
        		contentPane.revalidate();        		
        	}
        	else if(sourceItem.getText().toLowerCase().equals("programare examene"))
        	{
        		System.out.println("Meniu: Vizualizare Programare Examene");
        		
        		contentPane.remove(mainPanel);
        		mainPanel=new ViewExamenePanel(arhivaService, radService, utilizator, statusLbl);
        		
        		contentPane.add(mainPanel, "cell 0 2,grow");
        		contentPane.revalidate();        		
        	}
       
        }
        
        // Check buttons
        if(source instanceof JButton)
        {
        	if(source==this.btnOrar)
        	{
        		System.out.println("Buton: Vizualizare Orar");
        		
        		contentPane.remove(mainPanel);
        		mainPanel=new ViewOrarPanel(arhivaService, radService, utilizator, statusLbl);
        		
        		contentPane.add(mainPanel, "cell 0 2,grow");
        		contentPane.revalidate();        		
        	}
        	else if(source==this.btnSituatieScolara)
        	{
        		System.out.println("Buton: Vizualizare Situatie Scolara");
        		
        		contentPane.remove(mainPanel);
        		mainPanel=new ViewSituatiePanel(arhivaService, radService, utilizator, statusLbl);
        		
        		contentPane.add(mainPanel, "cell 0 2,grow");
        		contentPane.revalidate();        		
        	}
        	else if(source==this.btnProgramareExamene)
        	{
        		System.out.println("Buton: Vizualizare Programare Examen");
        		
        		contentPane.remove(mainPanel);
        		mainPanel=new ViewExamenePanel(arhivaService, radService, utilizator, statusLbl);
        		
        		contentPane.add(mainPanel, "cell 0 2,grow");
        		contentPane.revalidate();        		
        	}
        	else if(source==this.btnVizualizareNote)
        	{
        		System.out.println("Buton: Vizualizare Note");
        		
        		contentPane.remove(mainPanel);
        		mainPanel=new ViewNotePanel(arhivaService, radService, utilizator, statusLbl);
        		
        		contentPane.add(mainPanel, "cell 0 2,grow");
        		contentPane.revalidate();        		
        	}
        }
		
	}
}
