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
 * The class the contains all the information regarding a 'disciplina'.
 */
public class Disciplina implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2381064280396274605L;

	/**
	 * The Enum TipDisciplina.
	 */
	public enum TipDisciplina {
		/** The Obligatoriu. */
		Obligatoriu,
		/** The Optional. */
		Optional,
		/** The Facultativ. */
		Facultativ
	};
	
	/**
	 * The Enum Examinare.
	 */
	public enum Examinare {
		/** The Examen. */
		Examen,
		/** The Colocviu. */
		Colocviu
	};
	
	/** The cod disciplina. */
	public int cod;
	
	/** The denumire. */
	public String denumire;
	
	/** The tip. */
	public TipDisciplina tip;
	
	/** The nr ore. */
	public int nrOre;
	
	/** The pct credit. */
	public int pctCredit;
	
	/** The examinare. */
	public Examinare examinare;
	
	/** The an studiu. */
	public int anStudiu;
	
	/** The semestru. */
	public int semestru;
	
	/** The grup. */
	public int grup;

	
	//****************** METHODS ********************/
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Disciplina [anStudiu=" + anStudiu + ", cod=" + cod + ", denumire=" + denumire
				+ ", examinare=" + examinare + ", grup=" + grup + ", nrOre=" + nrOre
				+ ", pctCredit=" + pctCredit + ", semestru=" + semestru + ", tip=" + tip + "]";
	}

	//****************** GETTERS / SETTERS ********************/
	/**
	 * Gets the cod.
	 *
	 * @return the cod
	 */
	public int getCod() {
		return cod;
	}

	/**
	 * Sets the cod.
	 *
	 * @param cod the new cod
	 */
	public void setCod(int cod) {
		this.cod = cod;
	}

	/**
	 * Gets the denumire.
	 *
	 * @return the denumire
	 */
	public String getDenumire() {
		return denumire;
	}

	/**
	 * Sets the denumire.
	 *
	 * @param denumire the new denumire
	 */
	public void setDenumire(String denumire) {
		this.denumire = denumire;
	}

	/**
	 * Gets the tip.
	 *
	 * @return the tip
	 */
	public TipDisciplina getTip() {
		return tip;
	}

	/**
	 * Sets the tip.
	 *
	 * @param tip the new tip
	 */
	public void setTip(TipDisciplina tip) {
		this.tip = tip;
	}

	/**
	 * Gets the nr ore.
	 *
	 * @return the nr ore
	 */
	public int getNrOre() {
		return nrOre;
	}

	/**
	 * Sets the nr ore.
	 *
	 * @param nrOre the new nr ore
	 */
	public void setNrOre(int nrOre) {
		this.nrOre = nrOre;
	}

	/**
	 * Gets the pct credit.
	 *
	 * @return the pct credit
	 */
	public int getPctCredit() {
		return pctCredit;
	}

	/**
	 * Sets the pct credit.
	 *
	 * @param pctCredit the new pct credit
	 */
	public void setPctCredit(int pctCredit) {
		this.pctCredit = pctCredit;
	}

	/**
	 * Gets the examinare.
	 *
	 * @return the examinare
	 */
	public Examinare getExaminare() {
		return examinare;
	}

	/**
	 * Sets the examinare.
	 *
	 * @param examinare the new examinare
	 */
	public void setExaminare(Examinare examinare) {
		this.examinare = examinare;
	}

	/**
	 * Gets the an studiu.
	 *
	 * @return the an studiu
	 */
	public int getAnStudiu() {
		return anStudiu;
	}

	/**
	 * Sets the an studiu.
	 *
	 * @param anStudiu the new an studiu
	 */
	public void setAnStudiu(int anStudiu) {
		this.anStudiu = anStudiu;
	}

	/**
	 * Gets the semestru.
	 *
	 * @return the semestru
	 */
	public int getSemestru() {
		return semestru;
	}

	/**
	 * Sets the semestru.
	 *
	 * @param semestru the new semestru
	 */
	public void setSemestru(int semestru) {
		this.semestru = semestru;
	}

	/**
	 * Gets the grup.
	 *
	 * @return the grup
	 */
	public int getGrup() {
		return grup;
	}

	/**
	 * Sets the grup.
	 *
	 * @param grup the new grup
	 */
	public void setGrup(int grup) {
		this.grup = grup;
	}
	
	
}
