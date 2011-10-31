package aii.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import aii.Utilizator;

/**
 * The Class that wraps the access to database for Utilizator Objects.
 */
public class UtilizatorWrapper extends ObjectWrapper<Utilizator> {

	public UtilizatorWrapper() {
		super(Utilizator.class, Constants.USER_FIELD_MATCH, Constants.USER_TABLE_PK_COUNT);
	}

	/**
	 * Gets the utilizatori that match a certain where clause.
	 *
	 * @param whereClause the where clause
	 * @return the utilizatori
	 */
	public ArrayList<Utilizator> getUtilizatori(String whereClause)
	{
		ArrayList<Utilizator> utilizatori = null;
		try {
			utilizatori=this.getObjects(Constants.USER_TABLE, whereClause);
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
		List<Utilizator> utilizatori=this.getUtilizatori("cnp=\'"+cnp+"\'");
		
		if(utilizatori==null || utilizatori.size()!=1)
			return null;
		
		return utilizatori.get(0);
	}
	
	/**
	 * Inserts a new utilizator in the database
	 *
	 * @param utilizator the utilizator
	 * @return true, if successful
	 */
	public boolean insertUtilizator(Utilizator utilizator)
	{
		try {
			this.insertObject(Constants.USER_TABLE, utilizator);
		} catch (MySQLIntegrityConstraintViolationException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Aveti campuri invalide: "+e.getMessage());	
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"A fost intampinata o eroare in momentul " +
					"accesului la baza de date!");
			return false;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"A fost intampinata o eroare in momentul " +
					"constructiei dinamice a obiectelor din baza de date:"+e.getMessage());
			e.printStackTrace();
			return false;
		} 
		
		return true;
	}
	
	
	/**
	 * Deletes an utilizator from the database
	 *
	 * @param utilizator the utilizator
	 * @return true, if successful
	 */
	public boolean deleteUtilizator(Utilizator utilizator)
	{
		try {
			this.deleteObject(Constants.USER_TABLE, utilizator);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"A fost intampinata o eroare in momentul " +
					"accesului la baza de date!");
			return false;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"A fost intampinata o eroare in momentul " +
					"constructiei dinamice a obiectelor din baza de date:"+e.getMessage());
			e.printStackTrace();
			return false;
		} 
		
		return true;
	}
	
	/**
	 * Updates an utilizator in the database
	 *
	 * @param utilizator the utilizator
	 * @return true, if successful
	 */
	public boolean UpdateUtilizator(Utilizator utilizatorVechi, Utilizator utilizatorNou)
	{
		try {
			this.updateObject(Constants.USER_TABLE, utilizatorVechi, utilizatorNou);
		} catch (MySQLIntegrityConstraintViolationException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Aveti campuri invalide: "+e.getMessage());	
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"A fost intampinata o eroare in momentul " +
					"accesului la baza de date!");
			return false;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"A fost intampinata o eroare in momentul " +
					"constructiei dinamice a obiectelor din baza de date:"+e.getMessage());
			e.printStackTrace();
			return false;
		} 
		
		return true;
	}
}
