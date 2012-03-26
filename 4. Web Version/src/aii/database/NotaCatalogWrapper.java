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
	public ArrayList<NotaCatalog> getNoteCatalog(String whereClause)
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
	 * Gets the note catalog joined with student and class name.
	 *
	 * @param fields the fields
	 * @param from the from
	 * @param whereClause the where clause
	 * @return the note catalog joined
	 */
	public ArrayList<NotaCatalog> getNoteCatalogJoined(String fields, String from, String whereClause)
	{
		ArrayList<NotaCatalog> note = null;
		try {
			note=this.getObjects(fields, from, whereClause, null);
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
	 * Gets the maximum nota for a student and a class.
	 *
	 * @param codDisciplina the cod disciplina
	 * @param cnpStudent the cnp student
	 * @return the nota
	 */
	public NotaCatalog getNotaCatalog(int codDisciplina, String cnpStudent)
	{
		List<NotaCatalog> note=this.getNoteCatalog("cod_disciplina=\'"+codDisciplina+"\' AND cnp_student=\'"+cnpStudent+"\'");
		
		if(note==null || note.size()==0)
			return null;
		
		//Cautam nota maxima
		NotaCatalog maxim=note.get(0);
		for(NotaCatalog nota : note)
			if(nota.nota>maxim.nota)
				maxim=nota;
		
		return maxim;
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
			//JOptionPane.showMessageDialog(null,"Aveti campuri invalide: "+e.getMessage());	
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			//JOptionPane.showMessageDialog(null,"A fost intampinata o eroare in momentul " +
			//		"accesului la baza de date!");
			return false;
		} catch (Exception e) {
			//JOptionPane.showMessageDialog(null,"A fost intampinata o eroare in momentul " +
			//		"constructiei dinamice a obiectelor din baza de date:"+e.getMessage());
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
			JOptionPane.showMessageDialog(null,"Aveti deja introdusa o nota pentru acelasi student, la aceasta materie, in aceeasi data.");	
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
	 * Cadru pentru disciplina.
	 *
	 * @param codDisciplina the cod disciplina
	 * @param cnpCadruDidactic the cnp cadru didactic
	 * @return true, if successful
	 */
	public boolean cadruPentruDisciplina(int codDisciplina, String cnpCadruDidactic) {
		//Pregatim un query in care numaram cate rezultate avem
		String sqlQuery="SELECT count(*) " +
				"FROM "+ Constants.ACTIVITATE_TABLE+" a " +
				"WHERE a.cnp_cadru_didactic=\'"+cnpCadruDidactic+"\' AND a.tip=\'"+Activitate.TipActivitate.Curs+"\' AND a.cod_disciplina=\'"+codDisciplina+"\';";
		
		//Daca nu avem nici un rezultat, inseamna ca acest cadru didactic nu preda la disciplina respectiva cursul
		try {
			if((int)DatabaseConnection.getSingleValueResult(sqlQuery)==0)
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		
		return true;
	}
}
