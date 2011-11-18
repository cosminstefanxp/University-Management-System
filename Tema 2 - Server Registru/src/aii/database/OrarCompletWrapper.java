package aii.database;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import aii.OrarComplet;

/**
 * The Class that wraps the access to database for 'OrarComplet' Objects.
 */
public class OrarCompletWrapper extends ObjectWrapper<OrarComplet> {

	public OrarCompletWrapper() {
		super(OrarComplet.class, Constants.ORAR_FIELD_MATCH_FULL, Constants.ORAR_TABLE_PK_COUNT);
	}

	/**
	 * Gets the orare joined with other databases.
	 *
	 * @param whereClause the where clause
	 * @return the orare joined
	 */
	public ArrayList<OrarComplet> getOrare(String fields, String from, String where)
	{
		ArrayList<OrarComplet> orare = null;
		try {
			orare=this.getObjects(fields, from, where, null);
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
	 * Gets all the orar entries that are relevant for a given user.
	 *
	 * @param cnpStudent the cnp student
	 * @param anStudiu the an studiu
	 * @param grupa the grupa
	 * @param semestru the semestru
	 * @return the orare
	 */
	public ArrayList<OrarComplet> getOrareParticularizat(String cnpStudent, int anStudiu, String grupa, int semestru)
	{
		String fields="o.zi, o.ora, o.sala, o.grupa, o.frecventa, o.durata, o.id_activitate, a.tip, a.cod_disciplina, d.denumire, u.cnp, concat(u.nume,concat(' ',u.prenume)) nume";
		String from=Constants.CONTRACT_TABLE+" c, "+Constants.DISCIPLINA_TABLE+" d, "+Constants.ORAR_TABLE+" o, "+Constants.ACTIVITATE_TABLE+" a, "+Constants.USER_TABLE+" u";
		String where="c.cnp_student=\'"+cnpStudent+"\'" +
				" AND c.an_studiu=\'"+anStudiu+"\'" +
				" AND o.grupa=\'"+grupa+"\'" +
				" AND d.semestru=\'"+semestru+"\'" +
				" AND o.id_activitate=a.id" +
				" AND a.cod_disciplina=c.cod_disciplina" +
				" AND c.cod_disciplina=d.cod" +
				" AND u.cnp=a.cnp_cadru_didactic";
		String extra="GROUP BY o.grupa, d.denumire ORDER BY o.zi";
			
		ArrayList<OrarComplet> orare = null;
		try {
			orare=this.getObjects(fields, from, where, extra);
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
	
}
