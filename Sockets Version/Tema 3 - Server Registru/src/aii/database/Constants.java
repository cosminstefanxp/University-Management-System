/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
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
	
		
	/*******ACTIVITATE********/
	final public static String		ACTIVITATE_TABLE			= "activitate";
	final public static String[][]	ACTIVITATE_FIELD_MATCH		= {
																{ "id", "cod_disciplina", "cnp_cadru_didactic", "tip"},	//campuri baza de date
																{ "id", "codDisciplina", "cnpCadruDidactic", "tip"}		//campuri obiect
															};
	final public static String[][]	ACTIVITATE_FIELD_MATCH_FULL		= {
																{ "id", "cod_disciplina", "cnp_cadru_didactic", "tip", "nume"},							//campuri baza de date
																{ "id", "codDisciplina", "cnpCadruDidactic", "tip",  "numeCadruDidactic"}		//campuri obiect
															};
	final public static int			ACTIVITATE_TABLE_PK_COUNT	= 1;	//doar id face parte din cheie
	
	/*******ORAR********/
	final public static String		ORAR_TABLE			= "orar";
	final public static String[][]	ORAR_FIELD_MATCH		= {
																{ "id_activitate", "grupa", "zi", "ora", "sala", "durata", "frecventa"},	//campuri baza de date
																{ "idActivitate",  "grupa",  "zi", "ora", "sala", "durata", "frecventa"}		//campuri obiect
															};
	final public static String[][]	ORAR_COMPLET_FIELD_MATCH	= {
															{ "id_activitate", "grupa", "zi", "ora", "sala", "durata", "frecventa", "cod_disciplina", "tip", "nume", "cnp"},							//campuri baza de date
															{ "idActivitate",  "grupa",  "zi", "ora", "sala", "durata", "frecventa", "codDisciplina", "tip", "numeCadruDidactic", "cnpCadruDidactic"}	//campuri obiect
														};
	final public static int			ORAR_TABLE_PK_COUNT	= 2;	//atat 'id_activitate' cat si 'grupa' fac parte din cheie	
	
	/*******EXAMEN********/
	final public static String		EXAMEN_TABLE			= "examen";
	final public static String[][]	EXAMEN_FIELD_MATCH		= {
																{ "cod_disciplina", "grupa", "data", "ora", "sala"},	//campuri baza de date
																{ "codDisciplina",  "grupa", "data", "ora", "sala"}		//campuri obiect
															};

	final public static int			EXAMEN_TABLE_PK_COUNT	= 2;	//atat 'cod_disciplina' cat si 'grupa'  fac parte din cheie
		

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
