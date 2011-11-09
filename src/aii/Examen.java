package aii;

import java.sql.Date;


/**
 * The Class Examen.
 */
public class Examen {

	public Date data;
	public int ora;
	public int codDisciplina;
	public String sala;
	public String grupa;
	
	//Optional
	public String denumireDisciplina;
	
	@Override
	public String toString() {
		return "Examen [codDisciplina=" + codDisciplina + ", data=" + data + ", grupa=" + grupa
				+ ", ora=" + ora + ", sala=" + sala + "]";
	}
}
