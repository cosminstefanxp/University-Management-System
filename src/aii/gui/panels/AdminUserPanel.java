package aii.gui.panels;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import net.miginfocom.swing.MigLayout;
import aii.Utilizator;
import aii.database.Constants;
import aii.database.UtilizatorWrapper;
import aii.gui.tools.ObjectTableModel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import java.awt.BorderLayout;
import java.awt.GridLayout;


@SuppressWarnings("serial")
public class AdminUserPanel extends MainPanelAbstract {
	private JTable table;
	private ArrayList<Utilizator> objects;
	private ObjectTableModel<Utilizator> tableModel;

	/**
	 * Create the panel.
	 */
	public AdminUserPanel() {
		//Get the objects
		UtilizatorWrapper utilizatorDAO=new UtilizatorWrapper();
		
		try {
			objects=utilizatorDAO.createObjects(Constants.USER_TABLE,"cnp=cnp");
			tableModel=new ObjectTableModel<Utilizator>(Utilizator.class,
					objects,
					Constants.ADMIN_USER_COLUMN_FIELD_MATCH[1],
					Constants.ADMIN_USER_COLUMN_FIELD_MATCH[0]);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane scrollPaneTable = new JScrollPane();
		add(scrollPaneTable);
		
		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(true);
		scrollPaneTable.setViewportView(table);
		
		JPanel panel = new JPanel();
		add(panel);
		

	}
}
