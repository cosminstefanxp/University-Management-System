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

import aii.Orar;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

/**
 * The Class that wraps the access to database for 'Orar' Objects.
 */
public class OrarWrapper extends ObjectWrapper<Orar> {

	public OrarWrapper() {
		super(Orar.class, Constants.ORAR_FIELD_MATCH, Constants.ORAR_TABLE_PK_COUNT);
	}

	/**
	 * Gets all the orar that match a certain where clause.
	 *
	 * @param whereClause the where clause
	 * @return the orare
	 */
	public ArrayList<Orar> getOrare(String whereClause)
	{
		ArrayList<Orar> orare = null;
		try {
			orare=this.getObjects(Constants.ORAR_TABLE, whereClause);
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
		
		return orare;
	}
	
	/**
	 * Gets the orar that has a given 'zi','sala','ora'.
	 *
	 * @param cod the cod
	 * @return the orar
	 */
	public Orar getOrar(String zi, int ora, String sala)
	{
		List<Orar> orare=this.getOrare("zi=\'"+zi+"\' AND ora=\'"+ora+"\' AND sala=\'"+sala+"\'");
		
		if(orare==null || orare.size()!=1)
			return null;
		
		return orare.get(0);
	}
	
	/**
	 * Inserts an orar in the database.
	 *
	 * @param orar the orar
	 * @return true, if successful
	 */
	public boolean insertOrar(Orar orar)
	{
		try {
			this.insertObject(Constants.ORAR_TABLE, orar);
		} catch (MySQLIntegrityConstraintViolationException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Aveti deja o intrare in baza de date pentru aceeasi grupa si pentru aceeasi activitate.");	
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
	 * Deletes an orar from the database.
	 *
	 * @param orar the orar
	 * @return true, if successful
	 */
	public boolean deleteOrar(Orar orar)
	{
		try {
			this.deleteObject(Constants.ORAR_TABLE, orar);
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
	 * Updates an orar in the database.
	 *
	 * @param orarVechi the orar veche
	 * @param orarNou the orar noua
	 * @return true, if successful
	 */
	public boolean updateOrar(Orar orarVechi, Orar orarNou)
	{
		try {
			this.updateObject(Constants.ORAR_TABLE, orarVechi, orarNou);
		} catch (MySQLIntegrityConstraintViolationException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Aveti deja o intrare in baza de date pentru aceeasi grupa si pentru aceeasi activitate.");		
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
