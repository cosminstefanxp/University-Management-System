/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii;

/**
 * The Class SituatieScolara.
 */
public class SituatieScolara {

	/** The medie generala. */
	public float medieGenerala;
	
	/** The medie aritmetica. */
	public float medieAritmetica;
	
	/** The puncte credit. */
	public float puncteCredit;
	
	/** The restante. */
	public float restante;
	
	//****************** METHODS ********************/

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SituatieScolara [medieAritmetica=" + medieAritmetica + ", medieGenerala="
				+ medieGenerala + ", puncteCredit=" + puncteCredit + ", restante=" + restante + "]";
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
	public float getPuncteCredit() {
		return puncteCredit;
	}

	/**
	 * Sets the puncte credit.
	 *
	 * @param puncteCredit the new puncte credit
	 */
	public void setPuncteCredit(float puncteCredit) {
		this.puncteCredit = puncteCredit;
	}

	/**
	 * Gets the restante.
	 *
	 * @return the restante
	 */
	public float getRestante() {
		return restante;
	}

	/**
	 * Sets the restante.
	 *
	 * @param restante the new restante
	 */
	public void setRestante(float restante) {
		this.restante = restante;
	}
}
