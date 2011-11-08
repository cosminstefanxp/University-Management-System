package aii;

import java.sql.Date;

public class NotaCatalog {
	public String cnpStudent;
	public int codDisciplina;
	public Date data;
	public int nota;
	
	@Override
	public String toString() {
		return "NotaCatalog [cnpStudent=" + cnpStudent + ", codDisciplina=" + codDisciplina
				+ ", data=" + data + ", nota=" + nota + "]";
	}
}
