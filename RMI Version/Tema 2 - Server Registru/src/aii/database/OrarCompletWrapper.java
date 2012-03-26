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

import aii.OrarComplet;

/**
 * The Class that wraps the access to database for 'OrarComplet' Objects.
 */
public class OrarCompletWrapper extends ObjectWrapper<OrarComplet> {

	public OrarCompletWrapper() {
		super(OrarComplet.class, Constants.ORAR_COMPLET_FIELD_MATCH, Constants.ORAR_TABLE_PK_COUNT);
	}

	/**
	 * Gets the orare joined with other databases.
	 *
	 * @param whereClause the where clause
	 * @return the orare joined
	 */
	public ArrayList<OrarComplet> getOrareJoined(String fields, String from, String where)
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
	 * @param grupa the grupa
	 * @param discipline disciplinele pe care le urmeaza studentul si pentru care vrem sa aflam orarul.
	 * @return the orare
	 */
	public ArrayList<OrarComplet> getOrareParticularizat(String grupa, ArrayList<Integer> discipline)
	{
		String fields="o.zi, o.ora, o.sala, o.grupa, o.frecventa, o.durata, o.id_activitate, a.tip, a.cod_disciplina, u.cnp, concat(u.nume,concat(' ',u.prenume)) nume";
		String from=Constants.ORAR_TABLE+" o, "+Constants.ACTIVITATE_TABLE+" a, "+Constants.USER_TABLE+" u";
		String extra="GROUP BY o.grupa ORDER BY o.zi";
		String where="o.grupa=\'"+grupa+"\'" +
				" AND o.id_activitate=a.id" +
				" AND u.cnp=a.cnp_cadru_didactic" +
				" AND a.cod_disciplina IN (";
		//adaugam clauza IN
		for(Integer codDisciplina : discipline)
			where+="\'"+codDisciplina+"\', ";
		
		where=where.substring(0,where.length()-2);	//eliminam  ultima virgula
		where+=" )";
			
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
