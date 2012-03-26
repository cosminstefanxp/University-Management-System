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
import java.util.Vector;

import javax.swing.JOptionPane;

import aii.Disciplina;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

// TODO: Auto-generated Javadoc
/**
 * The Class that wraps the access to database for Disciplina Objects.
 */
public class DisciplinaWrapper extends ObjectWrapper<Disciplina> {

	/**
	 * Instantiates a new disciplina wrapper.
	 */
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
			//JOptionPane.showMessageDialog(null,"A fost intampinata o eroare in momentul " + "accesului la baza de date!");
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
			//JOptionPane.showMessageDialog(null,"A fost intampinata o eroare in momentul " + "accesului la baza de date!");
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
	 * @param whereClause the where clause
	 * @return true, if successful
	 */
	public boolean deleteDisciplina(String whereClause)
	{
		try {
			this.deleteObject(Constants.DISCIPLINA_TABLE, whereClause);
		} catch (SQLException e) {
			e.printStackTrace();
			//JOptionPane.showMessageDialog(null,"A fost intampinata o eroare in momentul " + "accesului la baza de date!");
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
			//JOptionPane.showMessageDialog(null,"Aveti campuri invalide: "+e.getMessage());	
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			//JOptionPane.showMessageDialog(null,"A fost intampinata o eroare in momentul " + 	"accesului la baza de date!");
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
	 * @param whereClause the where clause
	 * @param disciplinaNoua the disciplina noua
	 * @return true, if successful
	 */
	public boolean updateDisciplina(String whereClause, Disciplina disciplinaNoua)
	{
		try {
			this.updateObject(Constants.DISCIPLINA_TABLE, whereClause, disciplinaNoua);
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
	 * Obtine discipline urmate.
	 *
	 * @param cnpStudent the cnp student
	 * @return the array list
	 */
	public ArrayList<Integer> obtineDisciplineUrmate(String cnpStudent) {
		/* Pregatire date despre disciplina. */
		Vector<Object[]> results=null;
		String query = "SELECT cod_disciplina " + 
			" FROM " + Constants.CONTRACT_TABLE + " c " +
			" WHERE c.cnp_student=\'" + cnpStudent + "\';";
		try {
			DatabaseConnection.openConnection();
			results=DatabaseConnection.customQueryArray(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		//Construire lista discipline
		ArrayList<Integer> discipline=new ArrayList<Integer>();
		for(int i=0;i<results.size();i++)
		{
			discipline.add((Integer) results.get(i)[0]);
		}
		System.out.println("Obtinute disciplinele din contractul de studiu: "+discipline);
		
		return discipline;
	}

	/**
	 * Obtine disciplinele urmate de un student intr-un anumit an.
	 *
	 * @param cnpStudent the cnp student
	 * @param anStudiu the an studiu
	 * @return the array list
	 */
	public ArrayList<Integer> obtineDisciplineUrmate(String cnpStudent, int anStudiu) {
		/* Pregatire date despre disciplina. */
		Vector<Object[]> results=null;
		String query = "SELECT cod_disciplina " + 
			" FROM " + Constants.CONTRACT_TABLE + " c " +
			" WHERE c.cnp_student=\'" + cnpStudent + "\'" + 
			" 	AND c.an_studiu=\'" + anStudiu + "\'";

		try {
			DatabaseConnection.openConnection();
			results=DatabaseConnection.customQueryArray(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		//Construire lista discipline
		ArrayList<Integer> discipline=new ArrayList<Integer>();
		for(int i=0;i<results.size();i++)
		{
			discipline.add((Integer) results.get(i)[0]);
		}
		System.out.println("Obtinute disciplinele din contractul de studiu: "+discipline);
		
		return discipline;
	}


	/**
	 * Obtine discipline urmate.
	 *
	 * @param cnpStudent the cnp student
	 * @param anStudiu the an studiu
	 * @param semestru the semestru
	 * @return the array list
	 */
	public ArrayList<Integer> obtineDisciplineUrmate(String cnpStudent, int anStudiu, int semestru) {
		/* Pregatire date despre disciplina. */
		Vector<Object[]> results=null;
		String query = "SELECT cod_disciplina " + 
			" FROM " + Constants.CONTRACT_TABLE + " c, " + Constants.DISCIPLINA_TABLE+" d" +
			" WHERE c.cnp_student=\'" + cnpStudent + "\'" + 
			" 	AND c.an_studiu=\'" + anStudiu + "\'" +
			"	AND d.semestru=\'" + semestru + "\'" +
			"	AND d.cod=c.cod_disciplina";	

		try {
			DatabaseConnection.openConnection();
			results=DatabaseConnection.customQueryArray(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		//Construire lista discipline
		ArrayList<Integer> discipline=new ArrayList<Integer>();
		for(int i=0;i<results.size();i++)
		{
			discipline.add((Integer) results.get(i)[0]);
		}
		System.out.println("Obtinute disciplinele din contractul de studiu pentru "+cnpStudent+": "+discipline);
		
		return discipline;
	}
}
