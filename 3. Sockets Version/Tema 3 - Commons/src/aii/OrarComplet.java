/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii;

import java.io.Serializable;

import aii.Activitate.TipActivitate;

/**
 * The Class OrarComplet that contains all the information that has been joined
 * from an Orar and an associated Activitate, Disciplina and Utilizator.
 */
public class OrarComplet extends Orar implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2992742507361598140L;

	/** The cod disciplina. */
	public int codDisciplina;
	
	/** The cnp cadru didactic. */
	public String cnpCadruDidactic;
	
	/** The nume cadru didactic. */
	public String numeCadruDidactic;

	/** The tip. */
	public TipActivitate tip;

	// ****************** METHODS ********************/
	/* (non-Javadoc)
	 * @see aii.Orar#toString()
	 */
	@Override
	public String toString() {
		return "OrarComplet [cnpCadruDidactic=" + cnpCadruDidactic + ", codDisciplina="
				+ codDisciplina + ", numeCadruDidactic=" + numeCadruDidactic + ", tip=" + tip + ", durata="
				+ durata + ", frecventa=" + frecventa + ", grupa=" + grupa + ", idActivitate="
				+ idActivitate + ", ora=" + ora + ", sala=" + sala + ", zi=" + zi + "]";
	}



	// ****************** GETTERS / SETTERS ********************/
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
	 * @param codDisciplina
	 *            the new cod disciplina
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
	 * @param cnpCadruDidactic
	 *            the new cnp cadru didactic
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
	 * @param tip
	 *            the new tip
	 */
	public void setTip(TipActivitate tip) {
		this.tip = tip;
	}


	/**
	 * Gets the nume cadru didactic.
	 *
	 * @return the nume cadru didactic
	 */
	public String getNumeCadruDidactic() {
		return numeCadruDidactic;
	}



	/**
	 * Sets the nume cadru didactic.
	 *
	 * @param numeCadruDidactic the new nume cadru didactic
	 */
	public void setNumeCadruDidactic(String numeCadruDidactic) {
		this.numeCadruDidactic = numeCadruDidactic;
	}
}
