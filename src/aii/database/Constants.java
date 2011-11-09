package aii.database;


public class Constants {
	final public static String 		DATABASE_CONNECTION	= "jdbc:mysql://localhost/Grupa342C4_StefanDobrinCosmin";
	final public static String		DATABASE_USER		= "root";
	final public static String 		DATABASE_PASSWORD	= "root";
	
	/*******UTILIZATOR********/
	final public static String		USER_TABLE			= "utilizatori";
	final public static String[][]	USER_FIELD_MATCH	= {
															{ "cnp", "parola", "tip", "nume", "prenume", "email", "adresa", "titlu_grupa", "finantare", "contract_completat"},	//campuri obiect
															{ "CNP", "parola", "tip", "nume", "prenume", "email", "adresa", "titlu_grupa", "finantare", "contractCompletat"}		//campuri baza de date
														};
	final public static int			USER_TABLE_PK_COUNT	= 1;		//doar cnp face parte din cheie
	
	
	/*******DISCIPLINA********/
	final public static String		DISCIPLINA_TABLE			= "disciplina";
	final public static String[][]	DISCIPLINA_FIELD_MATCH		= {
																{ "cod", "denumire", "tip", "nr_ore", "puncte_credit", "examinare", "an_studiu", "semestru", "grup"},	//campuri baza de date
																{ "cod", "denumire", "tip", "nrOre",  "pctCredit",     "examinare", "anStudiu",  "semestru", "grup"}	//campuri obiect
															};
	final public static int			DISCIPLINA_TABLE_PK_COUNT	= 1;	//doar cod face parte din cheie
	
	/*******ACTIVITATE********/
	final public static String		ACTIVITATE_TABLE			= "activitate";
	final public static String[][]	ACTIVITATE_FIELD_MATCH		= {
																{ "id", "cod_disciplina", "cnp_cadru_didactic", "tip"},	//campuri baza de date
																{ "id", "codDisciplina", "cnpCadruDidactic", "tip"}		//campuri obiect
															};
	final public static int			ACTIVITATE_TABLE_PK_COUNT	= 1;	//doar id face parte din cheie
	
	/*******ORAR********/
	final public static String		ORAR_TABLE			= "orar";
	final public static String[][]	ORAR_FIELD_MATCH		= {
																{ "id_activitate", "grupa", "zi", "ora", "sala", "durata", "frecventa"},	//campuri baza de date
																{ "idActivitate",  "grupa",  "zi", "ora", "sala", "durata", "frecventa"}		//campuri obiect
															};
	final public static int			ORAR_TABLE_PK_COUNT	= 2;	//atat 'id_activitate' cat si 'grupa' fac parte din cheie	
	
	/*******EXAMEN********/
	final public static String		EXAMEN_TABLE			= "examen";
	final public static String[][]	EXAMEN_FIELD_MATCH		= {
																{ "cod_disciplina", "grupa", "data", "ora", "sala"},	//campuri baza de date
																{ "codDisciplina",  "grupa", "data", "ora", "sala"}		//campuri obiect
															};
	final public static int			EXAMEN_TABLE_PK_COUNT	= 3;	//atat 'cod_disciplina' cat si 'grupa' si 'data' fac parte din cheie
	
	/*******NOTA CATALOG********/
	final public static String		CATALOG_TABLE			= "catalog";
	final public static String[][]	CATALOG_FIELD_MATCH		= {
																{ "cnp_student", "cod_disciplina", "data", "nota"},		//campuri baza de date
																{ "cnpStudent",  "codDisciplina",  "data", "nota"}		//campuri obiect
															};
	final public static int			CATALOG_TABLE_PK_COUNT	= 3;	//atat 'cod_disciplina' cat si 'grupa' si 'data' fac parte din cheie	

	/*******OPTIUNI CONTRACT********/
	final public static String		CONTRACT_TABLE			= "optiuni_contract";
	
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
															{ "id", "codDisciplina" , "cnpCadruDidactic",   "tip"},				//campuri obiect
															{ "ID", "Cod Disciplina", "CNP Cadru Didactic", "Tip Activitate"}	//nume coloane
														};
	final public static String[][]	ADMIN_ORAR_COLUMN_FIELD_MATCH 	= {
															{ "grupa", "idActivitate", "zi", "ora", "sala", "durata",   "frecventa"},	//campuri obiect
															{ "Grupa", "ID Activitate", "Zi", "Ora", "Sala", "Durata", "Frecventa"}	//nume coloane
														};
	final public static String[][]	ADMIN_EXAMEN_COLUMN_FIELD_MATCH 	= {
															{ "grupa", "codDisciplina",  "data", "ora", "sala"},	//campuri obiect
															{ "Grupa", "Cod Disciplina", "Data", "Ora", "Sala"}		//nume coloane
														};
	final public static String[][]	ADMIN_CATALOG_COLUMN_FIELD_MATCH 	= {
															{ "codDisciplina", "cnpStudent", "data", "nota"},		//campuri obiect
															{ "Cod Disciplina", "CNP Student", "Data", "Nota"}		//nume coloane
														};
	final public static String[][]	VIEW_CONTRACT_DISCIPLINA_COLUMN_FIELD_MATCH		= {
															{ "cod", "denumire", "tip", "nrOre", "pctCredit", "examinare"},	//campuri obiect
															{ "Cod Disciplina", "Denumire", "Tip", "Numar Ore", "Puncte Credit", "Examinare"}	//nume coloane
														};
	
	
	/*******FIELD SIZES*********/
	final public static int FIELD_SIZE_GRUPA=6;
	final public static int FIELD_SIZE_SALA=10;
	final public static int FIELD_SIZE_NUME=45;
	final public static int FIELD_SIZE_PRENUME=45;
	final public static int FIELD_SIZE_PAROLA=25;
	final public static int FIELD_SIZE_CNP=13;
	final public static int FIELD_SIZE_EMAIL=45;
	final public static int FIELD_SIZE_ADRESA=120;
	final public static int FIELD_SIZE_DISCIPLINA_DENUMIRE=45;
	
	
}
