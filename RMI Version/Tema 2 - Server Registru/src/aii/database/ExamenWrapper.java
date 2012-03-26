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

import aii.Examen;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

/**
 * The Class that wraps the access to database for 'Examen' Objects.
 */
public class ExamenWrapper extends ObjectWrapper<Examen> {

	public ExamenWrapper() {
		super(Examen.class, Constants.EXAMEN_FIELD_MATCH, Constants.EXAMEN_TABLE_PK_COUNT);
	}

	/**
	 * Gets all the examen that match a certain where clause.
	 *
	 * @param whereClause the where clause
	 * @return the examene
	 */
	public ArrayList<Examen> getExamene(String whereClause)
	{
		ArrayList<Examen> examene = null;
		try {
			examene=this.getObjects(Constants.EXAMEN_TABLE, whereClause);
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
		
		return examene;
	}
	
	public ArrayList<Examen> getExameneJoined(String fields, String from, String whereClause)
	{
		ArrayList<Examen> examene = null;
		try {
			examene=this.getObjects(fields, from, whereClause, null);
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
		
		return examene;
	}
	
	/**
	 * Gets all the examen entries that are relevant for a given user.
	 *
	 * @param discipline disciplinele pentru care se doreste afisarea examenelor
	 * @param grupa the grupa
	 * @return the examene
	 */
	public ArrayList<Examen> getExameneParticularizat(ArrayList<Integer> discipline, String grupa)
	{
		String fields="*";
		String tables=Constants.EXAMEN_TABLE+" e";
		String extra="ORDER BY data, ora";
		String where="e.grupa=\'"+grupa+"\'" +
		" AND e.cod_disciplina IN (";
		
		//adaugam clauza IN
		for(Integer codDisciplina : discipline)
			where+="\'"+codDisciplina+"\', ";
		
		where=where.substring(0,where.length()-2);	//eliminam  ultima virgula
		where+=" )";		
		
		ArrayList<Examen> examene = null;
		try {
			examene=this.getObjects(fields, tables, where, extra);
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
		
		return examene;
	}
	
	/**
	 * Gets the examen that has a given 'zi','sala','ora'.
	 *
	 * @param cod the cod
	 * @return the examen
	 */
	public Examen getExamen(String zi, int ora, String sala)
	{
		List<Examen> examene=this.getExamene("zi=\'"+zi+"\' AND ora=\'"+ora+"\' AND sala=\'"+sala+"\'");
		
		if(examene==null || examene.size()!=1)
			return null;
		
		return examene.get(0);
	}
	
	/**
	 * Inserts an examen in the database.
	 *
	 * @param examen the examen
	 * @return true, if successful
	 */
	public boolean insertExamen(Examen examen)
	{
		try {
			this.insertObject(Constants.EXAMEN_TABLE, examen);
		} catch (MySQLIntegrityConstraintViolationException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Aveti deja o intrare in baza de date pentru aceeasi grupa si pentru aceeasi disciplina in aceeasi data.");	
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
	 * Deletes an examen from the database.
	 *
	 * @param examen the examen
	 * @return true, if successful
	 */
	public boolean deleteExamen(Examen examen)
	{
		try {
			this.deleteObject(Constants.EXAMEN_TABLE, examen);
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
	 * Updates an examen in the database.
	 *
	 * @param examenVechi the examen veche
	 * @param examenNou the examen noua
	 * @return true, if successful
	 */
	public boolean updateExamen(Examen examenVechi, Examen examenNou)
	{
		try {
			this.updateObject(Constants.EXAMEN_TABLE, examenVechi, examenNou);
		} catch (MySQLIntegrityConstraintViolationException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Aveti deja o intrare in baza de date pentru aceeasi grupa si pentru aceeasi disciplina in aceeasi data.");		
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
