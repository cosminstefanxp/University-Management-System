package aii;

import java.sql.Date;

public class NotaCatalog {
	public String cnpStudent;
	public int codDisciplina;
	public Date data;
	public int nota;
	
	//Optional
	public String numeStudent;
	public String denumireDisciplina;
	
	@Override
	public String toString() {
		return "NotaCatalog [cnpStudent=" + cnpStudent + ", codDisciplina=" + codDisciplina
				+ ", data=" + data + ", denumireDisciplina=" + denumireDisciplina + ", nota="
				+ nota + ", numeStudent=" + numeStudent + "]";
	}
	

}
