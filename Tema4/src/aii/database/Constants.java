package aii.database;



public class Constants {
	final public static String 		DATABASE_CONNECTION	= "jdbc:mysql://localhost/Grupa342C4_StefanDobrinCosmin";
	final public static String		DATABASE_USER		= "root";
	final public static String 		DATABASE_PASSWORD	= "root";
	
	/*******UTILIZATOR********/
	final public static String		USER_TABLE			= "utilizatori";
	final public static String[][]	USER_FIELD_MATCH	= {
															{ "cnp", "parola", "tip", "nume", "prenume", "email", "adresa", "titlu_grupa", "finantare", "contract_completat", "taxa"},	//campuri obiect
															{ "CNP", "parola", "tip", "nume", "prenume", "email", "adresa", "titlu_grupa", "finantare", "contractCompletat", "taxa"}		//campuri baza de date
														};
	final public static String[][]	USER_FIELD_MATCH_SHORT	= {
															{ "cnp", "nume", "prenume"},	//campuri obiect
															{ "CNP", "nume", "prenume"}		//campuri baza de date
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
	final public static String[][]	ACTIVITATE_FIELD_MATCH_SHORT= {
																{ "id", "cod_disciplina", "denumire"},	//campuri baza de date
																{ "id", "codDisciplina", "denumireDisciplina"}		//campuri obiect
															};
	
	final public static String[][]	ACTIVITATE_FIELD_MATCH_MEDIUM= {
											{ "id", "cod_disciplina", "denumire", "nume"},	//campuri baza de date
											{ "id", "codDisciplina", "denumireDisciplina", "numeCadruDidactic"}		//campuri obiect
										};		
	final public static String[][]	ACTIVITATE_FIELD_MATCH_FULL		= {
																{ "id", "cod_disciplina", "cnp_cadru_didactic", "tip", "denumire", "nume"},							//campuri baza de date
																{ "id", "codDisciplina", "cnpCadruDidactic", "tip", "denumireDisciplina", "numeCadruDidactic"}		//campuri obiect
															};
	final public static int			ACTIVITATE_TABLE_PK_COUNT	= 1;	//doar id face parte din cheie
	
	/*******ORAR********/
	final public static String		ORAR_TABLE			= "orar";
	final public static String[][]	ORAR_FIELD_MATCH		= {
																{ "id_activitate", "grupa", "zi", "ora", "sala", "durata", "frecventa"},	//campuri baza de date
																{ "idActivitate",  "grupa",  "zi", "ora", "sala", "durata", "frecventa"}		//campuri obiect
															};
	final public static String[][]	ORAR_FIELD_MATCH_FULL	= {
															{ "id_activitate", "grupa", "zi", "ora", "sala", "durata", "frecventa", "denumire", "tip"},					//campuri baza de date
															{ "idActivitate",  "grupa",  "zi", "ora", "sala", "durata", "frecventa", "denumireDisciplina", "tipActivitate"}	//campuri obiect
														};
	final public static String[][]	ORAR_STUDENT_FIELD_MATCH = {
																{ "grupa", "zi", "ora", "sala", "durata", "frecventa", "denumire", "tip"},						//campuri baza de date
																{ "grupa", "zi", "ora", "sala", "durata", "frecventa", "denumireDisciplina", "tipActivitate"}	//campuri obiect
															};
	final public static String[][]	ORAR_COMPLET_FIELD_MATCH	= {
															{ "id_activitate", "grupa", "zi", "ora", "sala", "durata", "frecventa", "cod_disciplina", "tip", "nume", "cnp", "denumire"},							//campuri baza de date
															{ "idActivitate",  "grupa",  "zi", "ora", "sala", "durata", "frecventa", "codDisciplina", "tip", "numeCadruDidactic", "cnpCadruDidactic", "denumireDisciplina"}	//campuri obiect
														};
	final public static int			ORAR_TABLE_PK_COUNT	= 2;	//atat 'id_activitate' cat si 'grupa' fac parte din cheie	
	
	/*******EXAMEN********/
	final public static String		EXAMEN_TABLE			= "examen";
	final public static String[][]	EXAMEN_FIELD_MATCH		= {
																{ "cod_disciplina", "grupa", "data", "ora", "sala"},	//campuri baza de date
																{ "codDisciplina",  "grupa", "data", "ora", "sala"}		//campuri obiect
															};
	final public static String[][]	EXAMEN_FIELD_MATCH_FULL		= {
																{ "cod_disciplina", "grupa", "data", "ora", "sala", "denumire"},			//campuri baza de date
																{ "codDisciplina",  "grupa", "data", "ora", "sala", "denumireDisciplina"}	//campuri obiect
															};
	
	final public static String[][]	EXAMEN_STUDENT_FIELD_MATCH		= {
																{ "denumire", "data", "ora", "sala"},				//campuri baza de date
																{ "denumireDisciplina", "data", "ora", "sala"}		//campuri obiect
															};
	final public static int			EXAMEN_TABLE_PK_COUNT	= 3;	//atat 'cod_disciplina' cat si 'grupa' si 'data' fac parte din cheie
	
	/*******NOTA CATALOG********/
	final public static String		CATALOG_TABLE			= "catalog";
	final public static String[][]	CATALOG_FIELD_MATCH		= {
																{ "cnp_student", "cod_disciplina", "data", "nota"},		//campuri baza de date
																{ "cnpStudent",  "codDisciplina",  "data", "nota"}		//campuri obiect
															};
	final public static String[][]	CATALOG_FIELD_MATCH_FULL= {
																{ "cnp_student", "cod_disciplina", "data", "nota", "nume", "denumire"},		//campuri baza de date
																{ "cnpStudent",  "codDisciplina",  "data", "nota", "numeStudent", "denumireDisciplina"}		//campuri obiect
															};	
	final public static int			CATALOG_TABLE_PK_COUNT	= 2;	//atat 'cod_disciplina' cat si 'cnp student' fac parte din cheie	

	/*******OPTIUNI CONTRACT********/
	final public static String		CONTRACT_TABLE			= "optiuni_contract";
	
	/*******TABELE********/
	final public static String[][]	ADMIN_USER_COLUMN_FIELD_MATCH ={
															{ "tip",      "CNP", "nume", "prenume", "email", "adresa", "titlu_grupa", "finantare", "taxa" },			//campuri obiect
															{ "Tip Cont", "CNP", "Nume", "Prenume", "Email", "Adresa", "Titlu/Grupa", "Finantare", "Taxa"}	//nume coloane
														};
	final public static String[][]	ADMIN_STUDENT_COLUMN_FIELD_MATCH ={
															{ "CNP", "nume", "prenume", "titlu_grupa", "finantare", "taxa" },			//campuri obiect
															{ "CNP", "Nume", "Prenume", "Grupa", "Finantare", "Taxa"}					//nume coloane
														};
	final public static String[][]	ADMIN_DISCIPLINA_COLUMN_FIELD_MATCH		= {
															{ "cod", "denumire", "tip", "nrOre", "pctCredit", "examinare", "anStudiu", "semestru", "grup"},	//campuri obiect
															{ "Cod Disciplina", "Denumire", "Tip", "Numar Ore", "Puncte Credit", "Examinare", "An Studiu", "Semestru", "Grup"}	//nume coloane
														};
	final public static String[][]	ADMIN_ACTIVITATE_COLUMN_FIELD_MATCH 	= {
															{ "id", "codDisciplina", "denumireDisciplina" , "cnpCadruDidactic", "numeCadruDidactic",  "tip"},		//campuri obiect
															{ "ID", "Cod Disciplina", "Denumire", "CNP Cadru Didactic", "Nume Cadru", "Tip Activitate"}			//nume coloane
														};

	final public static String[][]	ADMIN_EXAMEN_COLUMN_FIELD_MATCH 	= {
															{ "grupa", "codDisciplina", "denumireDisciplina", "data", "ora", "sala"},	//campuri obiect
															{ "Grupa", "Cod Disciplina", "Denumire", "Data", "Ora", "Sala"}		//nume coloane
														};
	final public static String[][]	ADMIN_CATALOG_COLUMN_FIELD_MATCH 	= {
															{ "codDisciplina", "denumireDisciplina" , "cnpStudent", "numeStudent", "data", "nota"},		//campuri obiect
															{ "Cod Disciplina", "Denumire", "CNP Student", "Nume", "Data", "Nota"}		//nume coloane
														};
	final public static String[][]	VIEW_CONTRACT_DISCIPLINA_COLUMN_FIELD_MATCH		= {
															{ "cod", "denumire", "tip", "nrOre", "pctCredit", "examinare"},	//campuri obiect
															{ "Cod Disciplina", "Denumire", "Tip", "Numar Ore", "Puncte Credit", "Examinare"}	//nume coloane
														};
	final public static String[][]	ADMIN_ORAR_COLUMN_FIELD_MATCH 	= {
															{ "grupa", "idActivitate", "tip", "denumireDisciplina", "numeCadruDidactic", "zi", "ora", "sala", "durata",   "frecventa"},	//campuri obiect
															{ "Grupa", "ID Activitate", "Tip", "Disciplina", "Cadru Didactic", "Zi", "Ora", "Sala", "Durata", "Frecventa"}	//nume coloane
														};	
	final public static String[][]	VIEW_ORAR_STUDENT_COLUMN_FIELD_MATCH = {
															{ "zi", "ora", "sala", "denumireDisciplina", "numeCadruDidactic", "durata", "frecventa",  "tip", "grupa", "idActivitate"},	//campuri obiect
															{ "Zi", "Ora", "Sala", "Denumire Disciplina", "Cadru Didactic", "Durata (h)", "Frecventa",  "Tip", "Grupa", "ID"}	//nume coloane
														};	
	final public static String[][]	VIEW_EXAMEN_STUDENT_COLUMN_FIELD_MATCH		= {
															{ "denumireDisciplina", "data", "ora", "sala"},		//campuri obiect
															{ "Disciplina", "Data", "Ora", "Sala"}		//nume coloane
														};
	final public static String[][]	VIEW_SITUATIE_SCOLARA_COLUMN_FIELD_MATCH		= {
													{ "medieSemestru1", "medieSemestru2", "medieAritmetica", "medieGenerala", "puncteCredit", "restante"},		//campuri obiect
													{ "Semestrul 1", "Semestrul 2", "Medie Aritmetica", "Medie Generala", "Puncte Credit", "Restante"}		//nume coloane
												};
	

	
}
