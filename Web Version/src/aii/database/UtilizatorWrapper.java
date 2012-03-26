package aii.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import aii.Utilizator;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

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
	 * Gets the utilizatori joined with other tables.
	 *
	 * @param fields the fields
	 * @param tables the tables
	 * @param whereClause the where clause
	 * @return the utilizatori joined
	 */
	public ArrayList<Utilizator> getUtilizatoriJoined(String fields, String tables, String whereClause)
	{
		ArrayList<Utilizator> utilizatori = null;
		try {
			utilizatori = this.getObjects(fields, tables, whereClause, null);
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
	 * Gets the utilizatori promovati.
	 *
	 * @return the utilizatori promovati
	 */
	public ArrayList<Utilizator> getUtilizatoriPromovati()
	{
		String fields="u.*, o.an_studiu";
		String from="catalog c " +
				" INNER JOIN disciplina d" +
				"	ON d.cod=c.cod_disciplina" +
				" RIGHT JOIN optiuni_contract o" +
				"	ON c.cod_disciplina=o.cod_disciplina AND o.cnp_student=c.cnp_student" +
				" RIGHT JOIN utilizatori u" +
				"	ON u.cnp=o.cnp_student";
		String where="c.nota >= 5";
		String extra="GROUP BY o.an_studiu, u.nume, u.prenume" +
				" HAVING sum(IFNULL(d.puncte_credit,0)) > (SELECT sum(d1.puncte_credit)" +
				"										FROM optiuni_contract o1, disciplina d1" +
				"										WHERE o1.cod_disciplina=d1.cod" +
				"											AND o1.an_studiu = o.an_studiu" +
				"											AND o1.cnp_student = u.cnp" +
				"										GROUP BY d1.an_studiu) / 2";
		ArrayList<Utilizator> utilizatori = null;
		try {
			utilizatori=this.getObjects(fields, from, where, extra);
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
			JOptionPane.showMessageDialog(null,"Aveti deja o intrare in baza de date pentru acelasi cnp.");	
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
			JOptionPane.showMessageDialog(null,"Aveti deja o intrare in baza de date pentru acelasi cnp.");	
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
