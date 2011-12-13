/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii.protocol;

import aii.protocol.MessageStructure.Sender;
import aii.protocol.MessageStructure.Type;

/**
 * The Class MessageConstants.
 */
public class MessageConstants {
	
	public static MessageStructure[] structure = {
		new MessageStructure("terminare_comunicatie", 
				Type.EXIT, Sender.ANY),
					
		new MessageStructure("raspuns_stabilire_plan_de_invatamant", 
				Type.CLIENT,Sender.SERVER),
		new MessageStructure("raspuns_repartizare_cadre_didactice", 
				Type.CLIENT,Sender.SERVER),
				
		new MessageStructure("solicitare_note", 
				Type.ARHIVA,Sender.STUDENT),
		new MessageStructure("solicitare_situatie_scolara", 
				Type.ARHIVA,Sender.STUDENT),				
		new MessageStructure("stabilire_plan_de_invatamant", 
				Type.ARHIVA,Sender.SEF_CATEDRA),
		new MessageStructure("stabilire_nota", 
				Type.ARHIVA,Sender.CADRU_DIDACTIC),
		new MessageStructure("solicitare_discipline_urmate", 
				Type.ARHIVA,Sender.ANY),
				
		new MessageStructure("repartizare_cadre_didactice", 
				Type.RAD, Sender.SEF_CATEDRA),
		new MessageStructure("solicitare_orar", 
				Type.RAD, Sender.STUDENT),
		new MessageStructure("solicitare_calendar_examene", 
				Type.RAD, Sender.STUDENT),
		new MessageStructure("stabilire_formatie_de_studiu", 
				Type.RAD, Sender.SECRETAR),
		new MessageStructure("stabilire_calendar_examene", 
				Type.RAD, Sender.SECRETAR),
		new MessageStructure("stabilire_orar", 
				Type.RAD, Sender.SECRETAR),
		new MessageStructure("cadru_pentru_disciplina", 
				Type.RAD, Sender.ANY),
				
		new MessageStructure("schimbare_grupa", 
				Type.JURNAL, Sender.STUDENT),
		new MessageStructure("contestare_nota", 
				Type.JURNAL, Sender.STUDENT),
		new MessageStructure("rezultat_solicitari", 
				Type.JURNAL, Sender.STUDENT),
		new MessageStructure("cereri_in_asteptare_cadru_didactic", 
				Type.JURNAL, Sender.CADRU_DIDACTIC),
		new MessageStructure("rezolvare_cereri_cadru_didactic", 
				Type.JURNAL, Sender.CADRU_DIDACTIC),
		new MessageStructure("cereri_in_asteptare_secretar", 
				Type.JURNAL, Sender.SECRETAR),
		new MessageStructure("rezolvare_cereri_secretar", 
				Type.JURNAL, Sender.SECRETAR)
		};
	
	public static final String[] STRUCTURE_DISCIPLINA={"cod", "denumire", "tip", "nrOre",  "pctCredit", "examinare", "anStudiu",  "semestru", "grup"};
	public static final String[] STRUCTURE_CATALOG={ "codDisciplina", "cnpStudent", "nota", "data"};
	
	public static final String[] STRUCTURE_ACTIVITATE={"cnpCadruDidactic", "codDisciplina", "tip"};
	public static final String[] STRUCTURE_ORAR={"idActivitate", "grupa",  "zi", "ora", "sala", "durata", "codDisciplina", "numeCadruDidactic", "tip", "frecventa"  };
	public static final String[] STRUCTURE_ORAR_SIMPLU={"zi", "ora", "sala", "durata", "idActivitate", "grupa", "frecventa"  };
	public static final String[] STRUCTURE_EXAMEN={"data", "ora", "sala", "codDisciplina",  "grupa"};
	public static final String[] STRUCTURE_USER={"CNP", "parola", "tip", "nume", "prenume", "email", "adresa", "titlu_grupa", "finantare", "contractCompletat"};
	public static final String[] STRUCTURE_SITUATIE_SCOLARA={"medieGenerala", "medieAritmetica", "puncteCredit", "restante", "medieSemestru1", "medieSemestru2"}; 
}
