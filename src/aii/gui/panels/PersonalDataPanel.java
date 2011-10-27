package aii.gui.panels;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class PersonalDataPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public PersonalDataPanel() {
		this.setBorder(new LineBorder(UIManager.getColor("ToolBar.borderColor")));
		this.setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(137, 111, 117, 25);
		add(btnNewButton);
	}
}
