package aii.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	 * Inserts an optiune in the database, starting from a Disciplina.
	 *
	 * @param optiune the optiune
	 * @return true, if successful
	 */
	public boolean insertOptiune(Disciplina optiune, String cnpStudent, int anStudiu)
	{
		try {
			//Prepare SQL insert
			String sqlStatement="INSERT INTO "+Constants.CONTRACT_TABLE + ""+
					" ( cnp_student , an_studiu , cod_disciplina)" +
					" VALUES (" +
					"\'" + cnpStudent  +"\', " +
					"\'" + anStudiu    +"\', " +
					"\'" + optiune.cod +"\' );";
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
