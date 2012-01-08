package aii;



/**
 * The Class Orar.
 */
public class Orar {
	public enum Frecventa { Saptamanal, Pare, Impare};
	public enum Ziua {Luni, Marti, Miercuri, Joi, Vineri};
	
	public Ziua zi;
	public int ora;
	public String sala;
	public int durata;
	public int idActivitate;
	public Frecventa frecventa;
	public String grupa;
	
	//Optional, pentru orarul unui student
	public String denumireDisciplina=null;
	public Activitate.TipActivitate tipActivitate=null;
	
	@Override
	public String toString() {
		return "Orar [denumireDisciplina=" + denumireDisciplina + ", durata=" + durata
				+ ", frecventa=" + frecventa + ", grupa=" + grupa + ", idActivitate="
				+ idActivitate + ", ora=" + ora + ", sala=" + sala + ", tipActivitate="
				+ tipActivitate + ", zi=" + zi + "]";
	}
	

}


//SELECT o.zi, o.ora, o.sala, d.denumire, o.grupa, o.frecventa, o.durata, a.tip
//FROM optiuni_contract c, disciplina d, orar o, activitate a
//WHERE c.cnp_student='666' 
//	AND c.an_studiu='4'
//	AND o.grupa='342C4'
//	AND d.semestru='1'
//	AND o.id_activitate=a.id
//	AND a.cod_disciplina=c.cod_disciplina
//	AND c.cod_disciplina=d.cod
//GROUP BY o.grupa, d.denumire
//ORDER BY o.zi
//	