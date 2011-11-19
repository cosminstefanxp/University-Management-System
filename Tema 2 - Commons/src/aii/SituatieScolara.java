/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii;

import java.io.Serializable;

/**
 * The Class SituatieScolara.
 */
public class SituatieScolara implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4952273979956930996L;

	/** The medie generala. */
	public float medieGenerala;
	
	/** The medie aritmetica. */
	public float medieAritmetica;
	
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
}
