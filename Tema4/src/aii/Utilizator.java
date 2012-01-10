package aii;

/**
 * The class the contains all the information regarding an user.
 */
public class Utilizator {
	public enum Tip {
		ADMIN, SUPER_ADMIN, SECRETAR, CADRU_DIDACTIC, SEF_CATEDRA, STUDENT, ANY
	};


	public enum Finantare {
		Buget, Taxa
	};

	public enum Taxa {
		Achitata, Neachitata
	}
	
	public String CNP;
	public String parola;
	public Tip tip;
	public String nume;
	public String prenume;
	public String email;
	public String adresa;
	public String titlu_grupa;
	public Finantare finantare;
	public boolean contractCompletat;
	public Taxa taxa;
	
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
		utilizator.taxa=taxa;
		
		return utilizator;
	}
	
	@Override
	public String toString() {
		return "Utilizator [CNP=" + CNP + ", adresa=" + adresa + ", contractCompletat="
				+ contractCompletat + ", email=" + email + ", finantare=" + finantare + ", nume="
				+ nume + ", parola=" + parola + ", prenume=" + prenume + ", tip=" + tip
				+ ", titlu_grupa=" + titlu_grupa + "]";
	}


	
}
