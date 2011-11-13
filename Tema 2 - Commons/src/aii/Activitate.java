/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii;

import java.io.Serializable;

/**
 * The Class Activitate.
 */
public class Activitate implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3457341944836460198L;

	/**
	 * The Enum TipActivitate.
	 */
	public enum TipActivitate {
			/** The Curs. */
			Curs, 
			 /** The Seminar. */
			 Seminar, 
			 /** The Laborator. */
			 Laborator, 
			 /** The Proiect. */
			 Proiect};
 
	/** The id. */
	public int id;
	
	/** The cod disciplina. */
	public int codDisciplina;
	
	/** The cnp cadru didactic. */
	public String cnpCadruDidactic;
	
	/** The tip. */
	public TipActivitate tip;

	//****************** METHODS ********************/
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Activitate [cnpCadruDidactic=" + cnpCadruDidactic + ", codDisciplina="
				+ codDisciplina + ", id=" + id + ", tip=" + tip + "]";
	}

	//****************** GETTERS / SETTERS ********************/
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
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
	 * Gets the cnp cadru didactic.
	 *
	 * @return the cnp cadru didactic
	 */
	public String getCnpCadruDidactic() {
		return cnpCadruDidactic;
	}

	/**
	 * Sets the cnp cadru didactic.
	 *
	 * @param cnpCadruDidactic the new cnp cadru didactic
	 */
	public void setCnpCadruDidactic(String cnpCadruDidactic) {
		this.cnpCadruDidactic = cnpCadruDidactic;
	}

	/**
	 * Gets the tip.
	 *
	 * @return the tip
	 */
	public TipActivitate getTip() {
		return tip;
	}

	/**
	 * Sets the tip.
	 *
	 * @param tip the new tip
	 */
	public void setTip(TipActivitate tip) {
		this.tip = tip;
	}
	

}
