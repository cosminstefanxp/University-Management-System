/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii.protocol;

import aii.protocol.MessageStructure.Type;

/**
 * The Class MessageConstants.
 */
public class MessageConstants {
	
	public static MessageStructure[] structure = {
		new MessageStructure("terminare_comunicatie", 
				Type.EXIT),
					
		new MessageStructure("raspuns_stabilire_plan_de_invatamant", 
				Type.CLIENT),
		new MessageStructure("raspuns_repartizare_cadre_didactice", 
				Type.CLIENT),
				
		new MessageStructure("solicitare_note", 
				Type.ARHIVA),
		new MessageStructure("solicitare_situatie_scolara", 
				Type.ARHIVA),				
		new MessageStructure("stabilire_plan_de_invatamant", 
				Type.ARHIVA),
		new MessageStructure("stabilire_nota", 
				Type.ARHIVA),
				
		new MessageStructure("repartizare_cadre_didactice", 
				Type.RAD),
		new MessageStructure("solicitare_orar", 
				Type.RAD),
		new MessageStructure("solicitare_calendar_examene", 
				Type.RAD),
		new MessageStructure("repartizare_cadre_didactice", 
				Type.RAD),
		new MessageStructure("stabilirea_formatie_de_studiu", 
				Type.RAD),
		new MessageStructure("stabilire_calendar_examene", 
				Type.RAD),
		
		new MessageStructure("schimbare_grupa", 
				Type.JURNAL),
		new MessageStructure("contestare_nota", 
				Type.JURNAL),
		new MessageStructure("rezultat_solicitari", 
				Type.JURNAL),
		new MessageStructure("cereri_in_asteptare_cadru_didactic", 
				Type.JURNAL),
		new MessageStructure("rezolvare_cereri_cadru_didactic", 
				Type.JURNAL),
		new MessageStructure("cereri_in_asteptare_secretar", 
				Type.JURNAL),
		new MessageStructure("rezolvare_cereri_secretar", 
				Type.JURNAL)
		};
	
	public static final String[] STRUCTURE_DISCIPLINA={"cod", "denumire", "tip", "nrOre",  "pctCredit",     "examinare", "anStudiu",  "semestru", "grup"};
	public static final String[] STRUCTURE_CATALOG={ "cnpStudent",  "codDisciplina",  "data", "nota"};
	
	public static final String[] STRUCTURE_ACTIVITATE={"cnpCadruDidactic", "codDisciplina", "tip"};
	public static final String[] STRUCTURE_ORAR={"idActivitate",  "grupa",  "zi", "ora", "sala", "durata", "frecventa"};
	public static final String[] STRUCTURE_EXAMEN={"codDisciplina",  "grupa", "data", "ora", "sala"};
	public static final String[] STRUCTURE_USER={"CNP", "parola", "tip", "nume", "prenume", "email", "adresa", "titlu_grupa", "finantare", "contractCompletat"};
	public static final String[] STRUCTURE_SITUATIE_SCOLARA={"medieGenerala", "medieAritmetica", "puncteCredit", "restante", "medieSemestru1", "medieSemestru2"}; 
}
