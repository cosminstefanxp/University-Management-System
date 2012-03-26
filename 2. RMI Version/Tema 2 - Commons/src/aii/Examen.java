/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii;

import java.io.Serializable;
import java.sql.Date;

/**
 * The Class Examen.
 */
public class Examen implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4353015622988696836L;

	/** The data. */
	public Date data;
	
	/** The ora. */
	public int ora;
	
	/** The cod disciplina. */
	public int codDisciplina;
	
	/** The sala. */
	public String sala;
	
	/** The grupa. */
	public String grupa;
	
	//****************** METHODS ********************/
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Examen [codDisciplina=" + codDisciplina + ", data=" + data + ", grupa=" + grupa
				+ ", ora=" + ora + ", sala=" + sala + "]";
	}

	//****************** GETTERS / SETTERS ********************/
	/**
	 * Gets the data of the exam.
	 *
	 * @return the data
	 */
	public Date getData() {
		return data;
	}

	/**
	 * Sets the data of the exam.
	 *
	 * @param data the new data
	 */
	public void setData(Date data) {
		this.data = data;
	}

	/**
	 * Gets the ora.
	 *
	 * @return the ora
	 */
	public int getOra() {
		return ora;
	}

	/**
	 * Sets the ora.
	 *
	 * @param ora the new ora
	 */
	public void setOra(int ora) {
		this.ora = ora;
	}

	/**
	 * Gets the cod disciplina.
	 *
	 * @return the cod disciplina
	 */
	public int getCodDisciplina() {
		return codDisciplina;
	}

	/**
	 * Sets the cod disciplina.
	 *
	 * @param codDisciplina the new cod disciplina
	 */
	public void setCodDisciplina(int codDisciplina) {
		this.codDisciplina = codDisciplina;
	}

	/**
	 * Gets the sala.
	 *
	 * @return the sala
	 */
	public String getSala() {
		return sala;
	}

	/**
	 * Sets the sala.
	 *
	 * @param sala the new sala
	 */
	public void setSala(String sala) {
		this.sala = sala;
	}

	/**
	 * Gets the grupa.
	 *
	 * @return the grupa
	 */
	public String getGrupa() {
		return grupa;
	}

	/**
	 * Sets the grupa.
	 *
	 * @param grupa the new grupa
	 */
	public void setGrupa(String grupa) {
		this.grupa = grupa;
	}

	
}
