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

import aii.Mesaj;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

// TODO: Auto-generated Javadoc
/**
 * The Class that wraps the access to database for Mesaj Objects.
 */
public class MesajWrapper extends ObjectWrapper<Mesaj> {

	/**
	 * Instantiates a new Mesaj wrapper.
	 */
	public MesajWrapper() {
		super(Mesaj.class, Constants.MESAJ_FIELD_MATCH, Constants.MESAJ_TABLE_PK_COUNT);
	}

	/**
	 * Gets the Mesaj that match a certain where clause.
	 *
	 * @param whereClause the where clause
	 * @return the mesaje
	 */
	public ArrayList<Mesaj> getMesaje(String whereClause)
	{
		ArrayList<Mesaj> mesaje = null;
		try {
			mesaje=this.getObjects(Constants.MESAJ_TABLE, whereClause);
		} catch (SQLException e) {
			e.printStackTrace();
			//JOptionPane.showMessageDialog(null,"A fost intampinata o eroare in momentul " + "accesului la baza de date!");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return mesaje;
	}
	
	/**
	 * Gets the Mesaj that has a given cod.
	 *
	 * @param cod the cod
	 * @return the Mesaj
	 */
	public Mesaj getMesaj(int id)
	{
		List<Mesaj> mesaje=this.getMesaje("id=\'"+id+"\'");
		
		if(mesaje==null || mesaje.size()!=1)
			return null;
		
		return mesaje.get(0);
	}
	
	/**
	 * Inserts a Mesaj in the database.
	 *
	 * @param Mesaj the Mesaj
	 * @return true, if successful
	 */
	public boolean insertMesaj(Mesaj mesaj)
	{
		try {
			this.insertObject(Constants.MESAJ_TABLE, mesaj);
		} catch (MySQLIntegrityConstraintViolationException e){
			e.printStackTrace();
			//JOptionPane.showMessageDialog(null,"Aveti campuri invalide: "+e.getMessage());	
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
	 * Deletes a mesaj from the database.
	 *
	 * @param whereClause the where clause
	 * @return true, if successful
	 */
	public boolean deleteMesaj(String whereClause)
	{
		try {
			this.deleteObject(Constants.MESAJ_TABLE, whereClause);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
		
		return true;
	}
	

	/**
	 * Updates a mesaj in the database.
	 *
	 * @param whereClause the where clause
	 * @param disciplinaNoua the mesaj noua
	 * @return true, if successful
	 */
	public boolean updateMesaj(String whereClause, Mesaj mesajNou)
	{
		try {
			this.updateObject(Constants.MESAJ_TABLE, whereClause, mesajNou);
		} catch (MySQLIntegrityConstraintViolationException e){
			e.printStackTrace();
			//JOptionPane.showMessageDialog(null,"Aveti campuri invalide: "+e.getMessage());	
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			//JOptionPane.showMessageDialog(null,"A fost intampinata o eroare in momentul " +"accesului la baza de date!");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

}
