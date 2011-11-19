/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii;


public class Constants {
	
	/*******TABELE********/
	final public static String[][]	ADMIN_USER_COLUMN_FIELD_MATCH ={
															{ "tip",      "CNP", "nume", "prenume", "email", "adresa", "titlu_grupa", "finantare", "contractCompletat"},			//campuri obiect
															{ "Tip Cont", "CNP", "Nume", "Prenume", "Email", "Adresa", "Titlu/Grupa", "Forma Finantare", "contract"}	//nume coloane
														};
	final public static String[][]	ADMIN_DISCIPLINA_COLUMN_FIELD_MATCH		= {
															{ "cod", "denumire", "tip", "nrOre", "pctCredit", "examinare", "anStudiu", "semestru", "grup"},	//campuri obiect
															{ "Cod Disciplina", "Denumire", "Tip", "Numar Ore", "Puncte Credit", "Examinare", "An Studiu", "Semestru", "Grup"}	//nume coloane
														};
	final public static String[][]	ADMIN_ACTIVITATE_COLUMN_FIELD_MATCH 	= {
															{ "id", "codDisciplina",  "cnpCadruDidactic",   "tip"},		//campuri obiect
															{ "ID", "Cod Disciplina",  "CNP Cadru Didactic",  "Tip Activitate"}			//nume coloane
														};
	final public static String[][]	ADMIN_ORAR_COLUMN_FIELD_MATCH 	= {
															{ "grupa", "idActivitate", "tip", "codDisciplina", "zi", "ora", "sala", "durata",   "frecventa"},	//campuri obiect
															{ "Grupa", "ID Activitate", "Tip", "Disciplina", "Zi", "Ora", "Sala", "Durata", "Frecventa"}	//nume coloane
														};
	final public static String[][]	ADMIN_EXAMEN_COLUMN_FIELD_MATCH 	= {
															{ "grupa", "codDisciplina", "data", "ora", "sala"},	//campuri obiect
															{ "Grupa", "Cod Disciplina", "Data", "Ora", "Sala"}		//nume coloane
														};
	final public static String[][]	ADMIN_CATALOG_COLUMN_FIELD_MATCH 	= {
															{ "codDisciplina", "cnpStudent", "data", "nota"},		//campuri obiect
															{ "Cod Disciplina", "CNP Student", "Data", "Nota"}		//nume coloane
														};
	final public static String[][]	VIEW_ORAR_STUDENT_COLUMN_FIELD_MATCH = {
															{ "zi", "ora", "sala", "codDisciplina", "numeCadruDidactic","durata", "frecventa",  "tip", "grupa"},	//campuri obiect
															{ "Zi", "Ora", "Sala", "Cod Disciplina", "Nume Cadru", "Durata (h)", "Frecventa",  "Tip", "Grupa"}	//nume coloane
														};	
	final public static String[][]	VIEW_EXAMEN_STUDENT_COLUMN_FIELD_MATCH		= {
															{ "codDisciplina", "data", "ora", "sala"},		//campuri obiect
															{ "Cod Disciplina", "Data", "Ora", "Sala"}		//nume coloane
														};
	
	
	/*******FIELD SIZES*********/
	final public static int FIELD_SIZE_GRUPA=6;
	final public static int FIELD_SIZE_TITLU=12;
	final public static int FIELD_SIZE_SALA=10;
	final public static int FIELD_SIZE_NUME=45;
	final public static int FIELD_SIZE_PRENUME=45;
	final public static int FIELD_SIZE_PAROLA=25;
	final public static int FIELD_SIZE_CNP=13;
	final public static int FIELD_SIZE_EMAIL=45;
	final public static int FIELD_SIZE_ADRESA=120;
	final public static int FIELD_SIZE_DISCIPLINA_DENUMIRE=45;
	
	
}
