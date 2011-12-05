/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii;

import java.io.Serializable;

/**
 * The class the contains all the information regarding an user.
 */
public class Utilizator implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4886387913878950579L;

	/**
	 * The Enum Tip.
	 */
	public enum Tip {
		/** The ADMIN. */
		ADMIN, 
		/** The SUPER Admin. */
		SUPER_ADMIN, 
		/** The SECRETAR. */
		SECRETAR, 
		/** The CADRU Didactic. */
		CADRU_DIDACTIC, 
		/** The SEF Catedra. */
		SEF_CATEDRA, 
		/** The STUDENT. */
		STUDENT
	};


	/**
	 * The Enum Finantare.
	 */
	public enum Finantare {
		/** The Buget. */
		Buget, 
		/** The Taxa. */
		Taxa
	};

	/** The CNP. */
	public String CNP;
	
	/** The parola. */
	public String parola;
	
	/** The tip. */
	public Tip tip;
	
	/** The nume. */
	public String nume;
	
	/** The prenume. */
	public String prenume;
	
	/** The email. */
	public String email;
	
	/** The adresa. */
	public String adresa;
	
	/** The titlu_grupa. */
	public String titlu_grupa;
	
	/** The finantare. */
	public Finantare finantare;
	
	/** The contract completat. */
	public boolean contractCompletat;
	
	//****************** METHODS ********************/
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Utilizator clone() {
		Utilizator utilizator=new Utilizator();
		
		utilizator.CNP=CNP;
		utilizator.parola=parola;
		utilizator.tip=tip;
		utilizator.nume=nume;
		utilizator.prenume=prenume;
		utilizator.email=email;
		utilizator.adresa=adresa;
		utilizator.titlu_grupa=titlu_grupa;
		utilizator.finantare=finantare;
		utilizator.contractCompletat=contractCompletat;
		
		return utilizator;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Utilizator [CNP=" + CNP + ", adresa=" + adresa + ", contractCompletat="
				+ contractCompletat + ", email=" + email + ", finantare=" + finantare + ", nume="
				+ nume + ", parola=" + parola + ", prenume=" + prenume + ", tip=" + tip
				+ ", titlu_grupa=" + titlu_grupa + "]";
	}

	
	
	//****************** GETTERS / SETTERS ********************/




	/**
	 * Gets the CNP.
	 *
	 * @return the cNP
	 */
	public String getCNP() {
		return CNP;
	}

	/**
	 * Sets the CNP.
	 *
	 * @param cNP the new cNP
	 */
	public void setCNP(String cNP) {
		CNP = cNP;
	}

	/**
	 * Gets the parola.
	 *
	 * @return the parola
	 */
	public String getParola() {
		return parola;
	}

	/**
	 * Sets the parola.
	 *
	 * @param parola the new parola
	 */
	public void setParola(String parola) {
		this.parola = parola;
	}

	/**
	 * Gets the tip.
	 *
	 * @return the tip
	 */
	public Tip getTip() {
		return tip;
	}

	/**
	 * Sets the tip.
	 *
	 * @param tip the new tip
	 */
	public void setTip(Tip tip) {
		this.tip = tip;
	}

	/**
	 * Gets the nume.
	 *
	 * @return the nume
	 */
	public String getNume() {
		return nume;
	}

	/**
	 * Sets the nume.
	 *
	 * @param nume the new nume
	 */
	public void setNume(String nume) {
		this.nume = nume;
	}

	/**
	 * Gets the prenume.
	 *
	 * @return the prenume
	 */
	public String getPrenume() {
		return prenume;
	}

	/**
	 * Sets the prenume.
	 *
	 * @param prenume the new prenume
	 */
	public void setPrenume(String prenume) {
		this.prenume = prenume;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the adresa.
	 *
	 * @return the adresa
	 */
	public String getAdresa() {
		return adresa;
	}

	/**
	 * Sets the adresa.
	 *
	 * @param adresa the new adresa
	 */
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	/**
	 * Gets the titlu_grupa.
	 *
	 * @return the titlu_grupa
	 */
	public String getTitlu_grupa() {
		return titlu_grupa;
	}

	/**
	 * Sets the titlu_grupa.
	 *
	 * @param titluGrupa the new titlu_grupa
	 */
	public void setTitlu_grupa(String titluGrupa) {
		titlu_grupa = titluGrupa;
	}

	/**
	 * Gets the finantare.
	 *
	 * @return the finantare
	 */
	public Finantare getFinantare() {
		return finantare;
	}

	/**
	 * Sets the finantare.
	 *
	 * @param finantare the new finantare
	 */
	public void setFinantare(Finantare finantare) {
		this.finantare = finantare;
	}

	/**
	 * Checks if is contract completat.
	 *
	 * @return true, if is contract completat
	 */
	public boolean isContractCompletat() {
		return contractCompletat;
	}

	/**
	 * Sets the contract completat.
	 *
	 * @param contractCompletat the new contract completat
	 */
	public void setContractCompletat(boolean contractCompletat) {
		this.contractCompletat = contractCompletat;
	}
	
}
