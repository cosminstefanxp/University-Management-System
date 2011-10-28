package aii;

/**
 * The clas the contains all the information regarding an user.
 */
public class Utilizator {
	public enum Tip {
		ADMIN, SUPER_ADMIN, SECRETAR, CADRU_DIDACTIC, SEF_CATEDRA, STUDENT
	};

	public enum Finantare {
		Buget, Taxa
	};

	public String CNP;
	public String parola;
	public Tip tip;
	public String nume;
	public String prenume;
	public String email;
	public String adresa;
	public String titlu_grupa;
	public Finantare finantare;
	
	public Utilizator() {
		super();
	}

	@Override
	public String toString() {
		return "Utilizator [CNP=" + CNP + ", adresa=" + adresa + ", email=" + email
				+ ", finantare=" + finantare + ", nume=" + nume + ", parola=" + parola
				+ ", prenume=" + prenume + ", tip=" + tip + ", titlu_grupa=" + titlu_grupa + "]";
	}

	
}
