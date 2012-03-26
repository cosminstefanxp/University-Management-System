package aii.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import aii.Disciplina;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

/**
 * The Class that wraps the access to database for Disciplina Objects.
 */
public class DisciplinaWrapper extends ObjectWrapper<Disciplina> {

	public DisciplinaWrapper() {
		super(Disciplina.class, Constants.DISCIPLINA_FIELD_MATCH, Constants.DISCIPLINA_TABLE_PK_COUNT);
	}

	/**
	 * Gets the disciplina that match a certain where clause.
	 *
	 * @param whereClause the where clause
	 * @return the discipline
	 */
	public ArrayList<Disciplina> getDiscipline(String whereClause)
	{
		ArrayList<Disciplina> discipline = null;
		try {
			discipline=this.getObjects(Constants.DISCIPLINA_TABLE, whereClause);
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
		
		return discipline;
	}
	
	/**
	 * Gets the disciplina that has a given cod.
	 *
	 * @param cod the cod
	 * @return the disciplina
	 */
	public Disciplina getDisciplina(String cod)
	{
		List<Disciplina> discipline=this.getDiscipline("cod=\'"+cod+"\'");
		
		if(discipline==null || discipline.size()!=1)
			return null;
		
		return discipline.get(0);
	}
	
	/**
	 * Inserts a disciplina in the database.
	 *
	 * @param disciplina the disciplina
	 * @return true, if successful
	 */
	public boolean insertDisciplina(Disciplina disciplina)
	{
		try {
			this.insertObject(Constants.DISCIPLINA_TABLE, disciplina);
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
	 * Deletes a disciplina from the database.
	 *
	 * @param disciplina the disciplina
	 * @return true, if successful
	 */
	public boolean deleteDisciplina(Disciplina disciplina)
	{
		try {
			this.deleteObject(Constants.DISCIPLINA_TABLE, disciplina);
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
	 * Updates a disciplina in the database.
	 *
	 * @param disciplinaVeche the disciplina veche
	 * @param disciplinaNoua the disciplina noua
	 * @return true, if successful
	 */
	public boolean updateDisciplina(Disciplina disciplinaVeche, Disciplina disciplinaNoua)
	{
		try {
			this.updateObject(Constants.DISCIPLINA_TABLE, disciplinaVeche, disciplinaNoua);
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
