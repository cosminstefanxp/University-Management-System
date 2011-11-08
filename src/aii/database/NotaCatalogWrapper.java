package aii.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import aii.Disciplina;
import aii.NotaCatalog;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

/**
 * The Class that wraps the access to database for NotaCatalog Objects.
 */
public class NotaCatalogWrapper extends ObjectWrapper<NotaCatalog> {

	public NotaCatalogWrapper() {
		super(NotaCatalog.class, Constants.CATALOG_FIELD_MATCH, Constants.CATALOG_TABLE_PK_COUNT);
	}

	/**
	 * Gets the nota that match a certain where clause.
	 *
	 * @param whereClause the where clause
	 * @return the note
	 */
	public ArrayList<NotaCatalog> getDiscipline(String whereClause)
	{
		ArrayList<NotaCatalog> note = null;
		try {
			note=this.getObjects(Constants.CATALOG_TABLE, whereClause);
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
		
		return note;
	}
	
	/**
	 * Gets the nota that has a given cod.
	 *
	 * @param codDisciplina the cod disciplina
	 * @param cnpStudent the cnp student
	 * @return the nota
	 */
	public NotaCatalog getNotaCatalog(int codDisciplina, String cnpStudent)
	{
		List<NotaCatalog> note=this.getDiscipline("codDisciplina=\'"+codDisciplina+"\' AND cnpStudent=\'"+cnpStudent);
		
		if(note==null || note.size()!=1)
			return null;
		
		return note.get(0);
	}
	
	/**
	 * Inserts a nota in the database.
	 *
	 * @param nota the nota
	 * @return true, if successful
	 */
	public boolean insertNotaCatalog(NotaCatalog nota)
	{
		try {
			this.insertObject(Constants.CATALOG_TABLE, nota);
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
	 * Deletes a nota from the database.
	 *
	 * @param nota the nota
	 * @return true, if successful
	 */
	public boolean deleteNotaCatalog(NotaCatalog nota)
	{
		try {
			this.deleteObject(Constants.CATALOG_TABLE, nota);
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
	 * Updates a nota in the database.
	 *
	 * @param notaVeche the nota veche
	 * @param notaNoua the nota noua
	 * @return true, if successful
	 */
	public boolean updateNotaCatalog(NotaCatalog notaVeche, NotaCatalog notaNoua)
	{
		try {
			this.updateObject(Constants.CATALOG_TABLE, notaVeche, notaNoua);
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
