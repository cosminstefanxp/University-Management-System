package aii.gui.panels;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import aii.Utilizator;
import aii.arhiva.Arhiva;
import aii.rad.RegistruActivitatiDidactice;

/**
 * The Class MainPanelAbstract that is an abstract class extending JPanel and
 * which is used for panels that are used Main Panels in the lower section of
 * the application Frames.
 */
@SuppressWarnings("serial")
public abstract class MainPanelAbstract extends JPanel {

	protected Arhiva arhivaService;
	protected RegistruActivitatiDidactice radService;
	protected Utilizator utilizator;
	protected JLabel statusLbl;

	/**
	 * Create the panel.
	 */
	public MainPanelAbstract(Arhiva arhivaService, RegistruActivitatiDidactice radService,
			Utilizator utilizator, JLabel statusLabel) {
		this.setBorder(new LineBorder(UIManager.getColor("ToolBar.borderColor")));
		this.setLayout(null);
		this.arhivaService = arhivaService;
		this.radService = radService;
		this.utilizator = utilizator;
		this.statusLbl = statusLabel;

	}

}
