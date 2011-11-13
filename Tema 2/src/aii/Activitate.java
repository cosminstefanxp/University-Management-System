package aii;

/**
 * The Class Activitate.
 */
public class Activitate {
	public enum TipActivitate {Curs, Seminar, Laborator, Proiect};
 
	public int id;
	public int codDisciplina;
	public String cnpCadruDidactic;
	public TipActivitate tip;
	
	//Optional
	public String denumireDisciplina;
	public String numeCadruDidactic;
	
	@Override
	public String toString() {
		return "Activitate [cnpCadruDidactic=" + cnpCadruDidactic + ", codDisciplina="
				+ codDisciplina + ", denumireDisciplina=" + denumireDisciplina + ", id=" + id
				+ ", numeCadruDidactic=" + numeCadruDidactic + ", tip=" + tip + "]";
	}

	

}
