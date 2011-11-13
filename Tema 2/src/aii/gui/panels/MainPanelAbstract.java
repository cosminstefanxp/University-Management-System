package aii.gui.panels;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

/**
 * The Class MainPanelAbstract that is an abstract class extending JPanel and which is used 
 * for panels that are used Main Panels in the lower section of the application Frames.
 */
@SuppressWarnings("serial")
public abstract class MainPanelAbstract extends JPanel {

	/**
	 * Create the panel.
	 */
	public MainPanelAbstract() {
		this.setBorder(new LineBorder(UIManager.getColor("ToolBar.borderColor")));
		this.setLayout(null);

	}

}
