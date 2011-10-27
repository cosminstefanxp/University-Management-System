package aii.gui.frames;

import java.awt.EventQueue;
import java.awt.Font;

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

/**
 * The Administrator frame that contains all the features that the admin uses.
 */
@SuppressWarnings("serial")
public class AdminFrame extends JFrame {

	/** The content pane. */
	private JPanel contentPane;

	/**
	 * Launch the application.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminFrame frame = new AdminFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AdminFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 895, 563);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnAdministrare = new JMenu("Administrare");
		menuBar.add(mnAdministrare);
		
		JMenuItem mntmAdminUtilizatori = new JMenuItem("Administrare Utilizatori");
		mnAdministrare.add(mntmAdminUtilizatori);
		
		JMenuItem mntmSuperAdmin = new JMenuItem("Super Administrator");
		mnAdministrare.add(mntmSuperAdmin);
		
		JMenu mnSetari = new JMenu("Setari");
		menuBar.add(mnSetari);
		
		JMenuItem mntmDatePersonale = new JMenuItem("Date Personale");
		mnSetari.add(mntmDatePersonale);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[851.00px]", "[30px][32.00px][412.00px]"));
		
		JLabel label = new JLabel("Universitatea Politehnica Bucuresti");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Dialog", Font.BOLD, 25));
		contentPane.add(label, "cell 0 0,alignx center,aligny top");
		
		JLabel lblBineAiVenit = new JLabel("Bine ati venit in consola de adminstrare a universitatii!");
		lblBineAiVenit.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblBineAiVenit, "cell 0 1,alignx center,aligny top");
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(UIManager.getColor("ToolBar.borderColor")));
		contentPane.add(panel, "cell 0 2,grow");
		panel.setLayout(null);
		
		JButton btnEditeazaSetariPersonale = new JButton("Setari personale");
		btnEditeazaSetariPersonale.setBounds(25, 50, 198, 25);
		panel.add(btnEditeazaSetariPersonale);
		
		JButton btnAdministrareUtilizatori = new JButton("Administrare utilizatori");
		btnAdministrareUtilizatori.setBounds(25, 88, 198, 25);
		panel.add(btnAdministrareUtilizatori);
		
		JLabel lblFolositiMeniulSau = new JLabel("Folositi meniul sau unul dintre butoanele de mai jos pentru a interactiona cu sistemul:");
		lblFolositiMeniulSau.setBounds(25, 23, 613, 15);
		panel.add(lblFolositiMeniulSau);
	}
}
