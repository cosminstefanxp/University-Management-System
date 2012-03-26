/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii;

import java.io.Serializable;
import java.sql.SQLException;

import aii.database.DatabaseConnection;

/**
 * The Class SituatieScolara.
 */
public class SituatieScolara implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4952273979956930996L;

	/** The medie generala. */
	public float medieGenerala=0;
	
	/** The medie aritmetica. */
	public float medieAritmetica=0;
	
	/** The puncte credit. */
	public int puncteCredit;
	
	/** The restante. */
	public int restante;
	
	/** The medie semestru1. Necompletat daca este un query despre toti anii.*/
	public float medieSemestru1;
	
	/** The medie semestru2. Necompletat daca este un query despre toti anii.*/
	public float medieSemestru2;
	
	//****************** METHODS ********************/

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SituatieScolara [medieAritmetica=" + medieAritmetica + ", medieGenerala="
				+ medieGenerala + ", medieSemestru1=" + medieSemestru1 + ", medieSemestru2="
				+ medieSemestru2 + ", puncteCredit=" + puncteCredit + ", restante=" + restante
				+ "]";
	}

	//****************** GETTERS / SETTERS ********************/
	/**
	 * Gets the medie generala.
	 *
	 * @return the medie generala
	 */
	public float getMedieGenerala() {
		return medieGenerala;
	}

	/**
	 * Sets the medie generala.
	 *
	 * @param medieGenerala the new medie generala
	 */
	public void setMedieGenerala(float medieGenerala) {
		this.medieGenerala = medieGenerala;
	}

	/**
	 * Gets the medie aritmetica.
	 *
	 * @return the medie aritmetica
	 */
	public float getMedieAritmetica() {
		return medieAritmetica;
	}

	/**
	 * Sets the medie aritmetica.
	 *
	 * @param medieAritmetica the new medie aritmetica
	 */
	public void setMedieAritmetica(float medieAritmetica) {
		this.medieAritmetica = medieAritmetica;
	}

	/**
	 * Gets the puncte credit.
	 *
	 * @return the puncte credit
	 */
	public int getPuncteCredit() {
		return puncteCredit;
	}

	/**
	 * Sets the puncte credit.
	 *
	 * @param puncteCredit the new puncte credit
	 */
	public void setPuncteCredit(int puncteCredit) {
		this.puncteCredit = puncteCredit;
	}

	/**
	 * Gets the restante.
	 *
	 * @return the restante
	 */
	public int getRestante() {
		return restante;
	}

	/**
	 * Sets the restante.
	 *
	 * @param restante the new restante
	 */
	public void setRestante(int restante) {
		this.restante = restante;
	}


	public float getMedieSemestru1() {
		return medieSemestru1;
	}


	public void setMedieSemestru1(float medieSemestru1) {
		this.medieSemestru1 = medieSemestru1;
	}


	public float getMedieSemestru2() {
		return medieSemestru2;
	}


	public void setMedieSemestru2(float medieSemestru2) {
		this.medieSemestru2 = medieSemestru2;
	}
	
	
	public static SituatieScolara obtineSituatieScolara(String CNPStudent, int anStudiu) {
		SituatieScolara situatie=new SituatieScolara();
		
		//Prepare Queries
		//Media aritmetica
		String unprocessedQueryAritmetica="SELECT AVG(IFNULL(nota,0)) " +
				"FROM optiuni_contract o " +
				"INNER JOIN disciplina d " +
				"	ON d.cod=o.cod_disciplina " +
				"LEFT JOIN catalog c " +
				"	ON o.cod_disciplina=c.cod_disciplina AND c.cnp_student=\'%s\'" +
				"WHERE o.cnp_student=\'%s\'";
		
		//Suma ponderata
		String unprocessedQueryGenerala="SELECT SUM(IFNULL(nota,0)*d.puncte_credit) " +
				"FROM optiuni_contract o " +
				"INNER JOIN disciplina d " +
				"	ON d.cod=o.cod_disciplina " +
				"LEFT JOIN catalog c " +
				"	ON o.cod_disciplina=c.cod_disciplina AND c.cnp_student=\'%s\'" +
				"WHERE o.cnp_student=\'%s\'";		
		
		//Numar total de credite
		String unprocessedQueryCreditTotal="SELECT sum(d1.puncte_credit)" +
				" FROM optiuni_contract o, disciplina d1" +
				" WHERE o.cod_disciplina=d1.cod" +
				"	AND o.cnp_student='%s'";
		
		//Numar obtinut
		String unprocessedQueryCreditObtinut="SELECT sum(d1.puncte_credit)" +
				" FROM optiuni_contract o, disciplina d1" +
				" WHERE o.cod_disciplina=d1.cod" +
				"	AND o.cnp_student='%s'" +
				"	AND (SELECT IFNULL(max(nota),0) FROM catalog c" +
				"		WHERE cnp_student='%s'" +
				"			AND cod_disciplina=o.cod_disciplina) >= 5";
		
		//Numar restante
		String unprocessedQueryRestante="SELECT count(*)" +
				" FROM optiuni_contract o, disciplina d1" +
				" WHERE o.cod_disciplina=d1.cod" +
				"		AND o.cnp_student='%s'" +
				"		AND (SELECT IFNULL(max(nota),0) FROM catalog c" +
				"			WHERE cnp_student='%s'" +
				"			AND cod_disciplina=o.cod_disciplina) < 5";
		
		
		if(anStudiu!=0)	//nu e pe toti anii
		{
			unprocessedQueryAritmetica+=" AND o.an_studiu=\'"+anStudiu+"\'";
			unprocessedQueryCreditTotal+=" AND o.an_studiu=\'"+anStudiu+"\'";
			unprocessedQueryCreditObtinut+=" AND o.an_studiu=\'"+anStudiu+"\'";
			unprocessedQueryGenerala+=" AND o.an_studiu=\'"+anStudiu+"\'";
			unprocessedQueryRestante+=" AND o.an_studiu=\'"+anStudiu+"\'";
		}

		try{
			//Situatia anuala - aritmetica
			String query=String.format(unprocessedQueryAritmetica,CNPStudent,CNPStudent);
			situatie.setMedieAritmetica(DatabaseConnection.getSingleValueResult(query));
			if(anStudiu!=0)
			{		
				//Situatia semestrul 1
				situatie.setMedieSemestru1(DatabaseConnection.getSingleValueResult(query+" AND d.semestru=\'1\'"));
				//Situatia semestrul 2
				situatie.setMedieSemestru2(DatabaseConnection.getSingleValueResult(query+" AND d.semestru=\'2\'"));
			}
			//Situatia anuala - ponderata
			query=String.format(unprocessedQueryGenerala,CNPStudent,CNPStudent);
			String query2=String.format(unprocessedQueryCreditTotal,CNPStudent);
			float suma1=DatabaseConnection.getSingleValueResult(query);
			float suma2=DatabaseConnection.getSingleValueResult(query2);
			if(suma2!=0)
				situatie.setMedieGenerala(suma1/suma2);
			else
				situatie.setMedieGenerala(0);
			//Puncte credit obtinute
			query=String.format(unprocessedQueryCreditObtinut,CNPStudent,CNPStudent);
			situatie.setPuncteCredit((int) DatabaseConnection.getSingleValueResult(query));
			//Restante
			query=String.format(unprocessedQueryRestante,CNPStudent,CNPStudent);
			situatie.setRestante((int) DatabaseConnection.getSingleValueResult(query));
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		
		
		return situatie;
	}
	
}
