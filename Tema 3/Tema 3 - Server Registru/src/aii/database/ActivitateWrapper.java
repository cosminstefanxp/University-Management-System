/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import aii.Activitate;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

/**
 * The Class that wraps the access to database for Activitate Objects.
 */
public class ActivitateWrapper extends ObjectWrapper<Activitate> {

	public ActivitateWrapper() {
		super(Activitate.class, Constants.ACTIVITATE_FIELD_MATCH, Constants.ACTIVITATE_TABLE_PK_COUNT);
	}

	/**
	 * Gets all the activitate that match a certain where clause.
	 *
	 * @param whereClause the where clause
	 * @return the activitati
	 */
	public ArrayList<Activitate> getActivitati(String whereClause)
	{
		ArrayList<Activitate> activitati = null;
		try {
			activitati=this.getObjects(Constants.ACTIVITATE_TABLE, whereClause);
		} catch (SQLException e) {
			e.printStackTrace();
			//JOptionPane.showMessageDialog(null,"A fost intampinata o eroare in momentul " +"accesului la baza de date!");
			return null;
		} catch (Exception e) {
			//JOptionPane.showMessageDialog(null,"A fost intampinata o eroare in momentul " +"constructiei dinamice a obiectelor din baza de date:"+e.getMessage());
			e.printStackTrace();
			return null;
		}
		
		return activitati;
	}
	
	/**
	 * Gets the activitati joined with other tables.
	 *
	 * @param fields the fields
	 * @param tables the tables
	 * @param whereClause the where clause
	 * @return the activitati joined
	 */
	public ArrayList<Activitate> getActivitatiJoined(String fields, String tables, String whereClause)
	{
		ArrayList<Activitate> activitati = null;
		try {
			activitati = this.getObjects(fields, tables, whereClause, null);
		} catch (SQLException e) {
			e.printStackTrace();
			// JOptionPane.showMessageDialog(null,"A fost intampinata o eroare in momentul " +"accesului la baza de date!");
			return null;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"A fost intampinata o eroare in momentul " +
					"constructiei dinamice a obiectelor din baza de date:"+e.getMessage());
			e.printStackTrace();
			return null;
		}
		
		return activitati;
	}
	
	/**
	 * Gets the activitate that has a given cod.
	 *
	 * @param cod the cod
	 * @return the activitate
	 */
	public Activitate getActivitate(String cod)
	{
		List<Activitate> activitati=this.getActivitati("cod=\'"+cod+"\'");
		
		if(activitati==null || activitati.size()!=1)
			return null;
		
		return activitati.get(0);
	}
	
	/**
	 * Inserts an activitate in the database.
	 *
	 * @param activitate the activitate
	 * @return true, if successful
	 */
	public boolean insertActivitate(Activitate activitate)
	{
		try {
			this.insertObject(Constants.ACTIVITATE_TABLE, activitate);
		} catch (MySQLIntegrityConstraintViolationException e){
			e.printStackTrace();
			//JOptionPane.showMessageDialog(null,"Aveti campuri invalide: "+e.getMessage());	
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			//JOptionPane.showMessageDialog(null,"A fost intampinata o eroare in momentul " +"accesului la baza de date!");
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
	 * Deletes an activitate from the database.
	 *
	 * @param activitate the activitate
	 * @return true, if successful
	 */
	public boolean deleteActivitate(Activitate activitate)
	{
		try {
			this.deleteObject(Constants.ACTIVITATE_TABLE, activitate);
		} catch (SQLException e) {
			e.printStackTrace();
			//JOptionPane.showMessageDialog(null,"A fost intampinata o eroare in momentul " +	"accesului la baza de date!");
			return false;
		} catch (Exception e) {
//			JOptionPane.showMessageDialog(null,"A fost intampinata o eroare in momentul " +
//					"constructiei dinamice a obiectelor din baza de date:"+e.getMessage());
			e.printStackTrace();
			return false;
		} 
		
		return true;
	}
	
	/**
	 * Updates an activitate in the database.
	 *
	 * @param activitateVeche the activitate veche
	 * @param activitateNoua the activitate noua
	 * @return true, if successful
	 */
	public boolean updateActivitate(Activitate activitateVeche, Activitate activitateNoua)
	{
		try {
			this.updateObject(Constants.ACTIVITATE_TABLE, activitateVeche, activitateNoua);
		} catch (MySQLIntegrityConstraintViolationException e){
			e.printStackTrace();
//			JOptionPane.showMessageDialog(null,"Aveti campuri invalide: "+e.getMessage());	
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
//			JOptionPane.showMessageDialog(null,"A fost intampinata o eroare in momentul " +
//					"accesului la baza de date!");
			return false;
		} catch (Exception e) {
//			JOptionPane.showMessageDialog(null,"A fost intampinata o eroare in momentul " +
//					"constructiei dinamice a obiectelor din baza de date:"+e.getMessage());
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * Updates an activitate in the database.
	 *
	 * @param activitateVeche the activitate veche
	 * @param activitateNoua the activitate noua
	 * @return true, if successful
	 */
	public boolean updateActivitate(String whereClause, Activitate activitateNoua)
	{
		try {
			this.updateObject(Constants.ACTIVITATE_TABLE, whereClause, activitateNoua);
		} catch (MySQLIntegrityConstraintViolationException e){
			e.printStackTrace();
//			JOptionPane.showMessageDialog(null,"Aveti campuri invalide: "+e.getMessage());	
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
//			JOptionPane.showMessageDialog(null,"A fost intampinata o eroare in momentul " +
//					"accesului la baza de date!");
			return false;
		} catch (Exception e) {
//			JOptionPane.showMessageDialog(null,"A fost intampinata o eroare in momentul " +
//					"constructiei dinamice a obiectelor din baza de date:"+e.getMessage());
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
