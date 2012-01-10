/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii.database;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import aii.Disciplina;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

/**
 * The Class that wraps the access to database for 'Optiune' Objects.
 */
public class OptiuneContractWrapper extends ObjectWrapper<Disciplina> {

	public OptiuneContractWrapper() {
		super(Disciplina.class, Constants.DISCIPLINA_FIELD_MATCH, Constants.DISCIPLINA_TABLE_PK_COUNT);
	}

	
	/**
	 * Gets all the discipline that have been chosen by the student in his contract.
	 *
	 * @param cnpStudent the cnp student
	 * @param anStudiu the an studiu
	 * @return the orare
	 */
	public ArrayList<Disciplina> getOptiuni(String cnpStudent, int anStudiu)
	{
		ArrayList<Disciplina> discipline=new ArrayList<Disciplina>();
		String fields = "b.*";
		String from = Constants.CONTRACT_TABLE+" a, "+ Constants.DISCIPLINA_TABLE+" b ";
		String whereClause =" a.cod_disciplina=b.cod AND a.an_studiu=\'"+anStudiu+"\' AND cnp_student=\'"+cnpStudent+"\' ";
		try {
			discipline=this.getObjects(fields, from, whereClause, null);
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
	 * Inserts an optiune in the database, starting from a Disciplina.
	 *
	 * @param optiune the optiune
	 * @return true, if successful
	 */
	public boolean insertOptiune(String cod, String cnpStudent, int anStudiu)
	{
		try {
			//Prepare SQL insert
			String sqlStatement="INSERT INTO "+Constants.CONTRACT_TABLE + ""+
					" ( cnp_student , an_studiu , cod_disciplina)" +
					" VALUES (" +
					"\'" + cnpStudent  +"\', " +
					"\'" + anStudiu    +"\', " +
					"\'" + cod +"\' );";
			DatabaseConnection.customExecute(sqlStatement);
		} catch (MySQLIntegrityConstraintViolationException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Aveti deja o intrare in baza de date pentru acelasi CNP, acelasi an de studiu si aceeasi optiune.");	
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"A fost intampinata o eroare in momentul " +
					"accesului la baza de date!");
			return false;
		}
		
		return true;
	}
}
