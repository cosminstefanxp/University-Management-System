/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class Orar.
 */
public class Orar implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2644333137364237607L;

	/**
	 * The Enum Frecventa.
	 */
	public enum Frecventa { 
		 /** The Saptamanal. */
		 Saptamanal, 
		 /** The Pare. */
		 Pare, 
		 /** The Impare. */
		 Impare};
	
	/**
	 * The Enum Ziua.
	 */
	public enum Ziua {
		/** The Luni. */
		Luni, 
		 /** The Marti. */
		 Marti, 
		 /** The Miercuri. */
		 Miercuri, 
		 /** The Joi. */
		 Joi, 
		 /** The Vineri. */
		 Vineri};
	
	/** The zi. */
	public Ziua zi;
	
	/** The ora. */
	public int ora;
	
	/** The sala. */
	public String sala;
	
	/** The durata. */
	public int durata;
	
	/** The id activitate. */
	public int idActivitate;
	
	/** The frecventa. */
	public Frecventa frecventa;
	
	/** The grupa. */
	public String grupa;
	
	//****************** METHODS ********************/
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Orar [durata=" + durata + ", frecventa=" + frecventa + ", grupa=" + grupa
				+ ", idActivitate=" + idActivitate + ", ora=" + ora + ", sala=" + sala + ", zi="
				+ zi + "]";
	}

	//****************** GETTERS / SETTERS ********************/
	/**
	 * Gets the zi.
	 *
	 * @return the zi
	 */
	public Ziua getZi() {
		return zi;
	}
	
	/**
	 * Sets the zi.
	 *
	 * @param zi the new zi
	 */
	public void setZi(Ziua zi) {
		this.zi = zi;
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
	 * Gets the durata.
	 *
	 * @return the durata
	 */
	public int getDurata() {
		return durata;
	}
	
	/**
	 * Sets the durata.
	 *
	 * @param durata the new durata
	 */
	public void setDurata(int durata) {
		this.durata = durata;
	}
	
	/**
	 * Gets the id activitate.
	 *
	 * @return the id activitate
	 */
	public int getIdActivitate() {
		return idActivitate;
	}
	
	/**
	 * Sets the id activitate.
	 *
	 * @param idActivitate the new id activitate
	 */
	public void setIdActivitate(int idActivitate) {
		this.idActivitate = idActivitate;
	}
	
	/**
	 * Gets the frecventa.
	 *
	 * @return the frecventa
	 */
	public Frecventa getFrecventa() {
		return frecventa;
	}
	
	/**
	 * Sets the frecventa.
	 *
	 * @param frecventa the new frecventa
	 */
	public void setFrecventa(Frecventa frecventa) {
		this.frecventa = frecventa;
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

