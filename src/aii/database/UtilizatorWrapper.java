package aii.database;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import aii.Utilizator;

/**
 * The Class that wraps the access to database for Utilizator Objects.
 */
public class UtilizatorWrapper extends ObjectWrapper<Utilizator> {

	public UtilizatorWrapper() {
		super(Utilizator.class, Constants.USER_FIELD_MATCH);
	}

	/**
	 * Gets the utilizatori that match a certain where clause.
	 *
	 * @param whereClause the where clause
	 * @return the utilizatori
	 */
	public List<Utilizator> getUtilizatori(String whereClause)
	{
		List<Utilizator> utilizatori = null;
		try {
			utilizatori=this.createObjects(Constants.USER_TABLE, whereClause);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"A fost intampinata o eroare in momentul " +
					"accesului la baza de date!");
			return null;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"A fost intampinata o eroare in momentul " +
					"constructiei dinamice a obiectelor din baza de date:"+e.getMessage());
			e.printStackTrace();
			return null;
		} 
		
		return utilizatori;
	}
	
	/**
	 * Gets the utilizator that has a given cnp.
	 *
	 * @param cnp the cnp
	 * @return the utilizator
	 */
	public Utilizator getUtilizator(String cnp)
	{
		List<Utilizator> utilizatori=this.getUtilizatori("cnp="+cnp);
		
		if(utilizatori==null || utilizatori.size()!=1)
			return null;
		
		return utilizatori.get(0);
	}
}
