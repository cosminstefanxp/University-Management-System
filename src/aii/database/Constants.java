package aii.database;


public class Constants {
	final public static String 		DATABASE_CONNECTION	= "jdbc:mysql://localhost/Grupa342C4_StefanDobrinCosmin";
	final public static String		DATABASE_USER		= "root";
	final public static String 		DATABASE_PASSWORD	= "root";
	
	//Utilizator
	final public static String		USER_TABLE			= "utilizatori";
	final public static String[][]	USER_FIELD_MATCH	= {
															{ "cnp", "parola", "tip", "nume", "prenume", "email", "adresa", "titlu_grupa", "finantare"},	//campuri obiect
															{ "CNP", "parola", "tip", "nume", "prenume", "email", "adresa", "titlu_grupa", "finantare"}		//campuri baza de date
														};
	final public static int			USER_TABLE_PK_COUNT	= 1;		//doar cnp face parte din cheie
	
	
	final public static String		DISCIPLINA_TABLE			= "disciplina";
	final public static String[][]	DISCIPLINA_FIELD_MATCH		= {
																{ "cod", "denumire", "tip", "nr_ore", "puncte_credit", "examinare", "an_studiu", "semestru"},	//campuri baza de date
																{ "cod", "denumire", "tip", "nrOre",  "pctCredit",     "examinare", "anStudiu",  "semestru"}	//campuri obiect
															};
	final public static int			DISCIPLINA_TABLE_PK_COUNT	= 1;	//doar cod face parte din cheie
	
	final public static String		ACTIVITATE_TABLE			= "activitate";
	final public static String[][]	ACTIVITATE_FIELD_MATCH		= {
																{ "id", "cod_disciplina", "cnp_cadru_didactic", "tip"},	//campuri baza de date
																{ "id", "codDisciplina", "cnpCadruDidactic", "tip"}		//campuri obiect
															};
	final public static int			ACTIVITATE_TABLE_PK_COUNT	= 1;	//doar cod face parte din cheie
	
	
	
	final public static String[][]	ADMIN_USER_COLUMN_FIELD_MATCH ={
															{ "tip",      "CNP", "nume", "prenume", "email", "adresa", "titlu_grupa", "finantare"},			//campuri obiect
															{ "Tip Cont", "CNP", "Nume", "Prenume", "Email", "Adresa", "Titlu/Grupa", "Forma Finantare"}	//nume coloane
														};
	final public static String[][]	ADMIN_DISCIPLINA_COLUMN_FIELD_MATCH		= {
															{ "cod", "denumire", "tip", "nrOre", "pctCredit", "examinare", "anStudiu", "semestru"},	//campuri obiect
															{ "Cod Disciplina", "Denumire", "Tip", "Numar Ore", "Puncte Credit", "Examinare", "An Studiu", "Semestru"}	//nume coloane
														};

}
