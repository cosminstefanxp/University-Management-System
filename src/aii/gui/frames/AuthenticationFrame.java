package aii.gui.frames;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import aii.Utilizator;
import aii.database.UtilizatorWrapper;

/**
 * The Class Authentication that implements the window where the user can authentify.
 */
public class AuthenticationFrame implements ActionListener{

	/** The main frame of the application. */
	private JFrame mainFrame;
	private JTextField cnpUtilizatorField;
	private JPasswordField parolaUserField;
	private UtilizatorWrapper utilizatorDAO;
	
	/**
	 * Create the application.
	 */
	public AuthenticationFrame() {
		initialize();
		this.mainFrame.setVisible(true);
		
		utilizatorDAO=new UtilizatorWrapper();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setResizable(false);
		mainFrame.setBounds(100, 100, 663, 375);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null);
		
		JLabel lblUniversitateaPolitehnicaBucurestibine = new JLabel("Universitatea Politehnica Bucuresti");
		lblUniversitateaPolitehnicaBucurestibine.setBounds(0, 0, 647, 29);
		lblUniversitateaPolitehnicaBucurestibine.setHorizontalAlignment(SwingConstants.CENTER);
		lblUniversitateaPolitehnicaBucurestibine.setFont(new Font("Dialog", Font.BOLD, 25));
		mainFrame.getContentPane().add(lblUniversitateaPolitehnicaBucurestibine);
		
		JLabel lblBineAtiVenit = new JLabel("Bine ati venit! Va rugam autentificativa pentru a continua:");
		lblBineAtiVenit.setBounds(117, 44, 412, 15);
		mainFrame.getContentPane().add(lblBineAtiVenit);
		
		JPanel authenticationPanel = new JPanel();
		authenticationPanel.setBounds(35, 71, 572, 226);
		authenticationPanel.setBorder(new TitledBorder(null, "Autentificare", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		authenticationPanel.setLayout(null);
		mainFrame.getContentPane().add(authenticationPanel);
		
		cnpUtilizatorField = new JTextField();
		cnpUtilizatorField.setBounds(204, 56, 283, 29);
		authenticationPanel.add(cnpUtilizatorField);
		cnpUtilizatorField.setColumns(10);
		
		JLabel lblCnpUtilizator = new JLabel("CNP utilizator:");
		lblCnpUtilizator.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCnpUtilizator.setLabelFor(cnpUtilizatorField);
		lblCnpUtilizator.setBounds(55, 56, 131, 17);
		authenticationPanel.add(lblCnpUtilizator);
		
		JLabel lblParola = new JLabel("Parola:");
		lblParola.setHorizontalAlignment(SwingConstants.TRAILING);
		lblParola.setBounds(55, 129, 131, 17);
		authenticationPanel.add(lblParola);
		
		parolaUserField = new JPasswordField();
		parolaUserField.setBounds(204, 129, 283, 29);
		authenticationPanel.add(parolaUserField);
		
		
		
		JButton btnAutentificare = new JButton("Autentificare");
		btnAutentificare.addActionListener(this);		
		btnAutentificare.setBounds(340, 177, 147, 29);
		authenticationPanel.add(btnAutentificare);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Realizam autentificarea...");
		
		//Get the user
		Utilizator utilizator=utilizatorDAO.getUtilizator(cnpUtilizatorField.getText());
		
		if(utilizator==null)
		{
			JOptionPane.showMessageDialog(mainFrame, "Nu s-a gasit in baza de date un utilizator " +
					"cu CNP-ul introdus. Va rugam incercati din nou!");
			return ;
		}
		
		//Authentify the user
		if(!utilizator.parola.equals(new String(parolaUserField.getPassword())))
		{
			JOptionPane.showMessageDialog(mainFrame, "Combinatia cnp/parola introdusa nu este corecta. " +
				"Va rugam incercati din nou!");
		}
		else
		{
			System.out.println("Utilizator "+utilizator+" autentificat.");
			switch(utilizator.tip)
			{
			case ADMIN: 
			case SUPER_ADMIN:
				mainFrame.dispose();
				mainFrame=new AdminFrame(utilizator);
				mainFrame.setVisible(true);
				break;
			case CADRU_DIDACTIC:
			case SEF_CATEDRA:
				mainFrame.dispose();
				mainFrame=new TeacherFrame(utilizator);
				mainFrame.setVisible(true);
				break;
			}
		}
	}
}
