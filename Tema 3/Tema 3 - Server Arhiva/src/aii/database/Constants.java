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
	
	/*******DISCIPLINA********/
	final public static String		DISCIPLINA_TABLE			= "disciplina";
	final public static String[][]	DISCIPLINA_FIELD_MATCH		= {
																{ "cod", "denumire", "tip", "nr_ore", "puncte_credit", "examinare", "an_studiu", "semestru", "grup"},	//campuri baza de date
																{ "cod", "denumire", "tip", "nrOre",  "pctCredit",     "examinare", "anStudiu",  "semestru", "grup"}	//campuri obiect
															};
	final public static int			DISCIPLINA_TABLE_PK_COUNT	= 1;	//doar cod face parte din cheie
	
	
	/*******NOTA CATALOG********/
	final public static String		CATALOG_TABLE			= "catalog";
	final public static String[][]	CATALOG_FIELD_MATCH		= {
																{ "cnp_student", "cod_disciplina", "data", "nota"},		//campuri baza de date
																{ "cnpStudent",  "codDisciplina",  "data", "nota"}		//campuri obiect
															};
	final public static int			CATALOG_TABLE_PK_COUNT	= 2;	//atat 'cod_disciplina' cat si 'grupa' fac parte din cheie	

	/*******OPTIUNI CONTRACT********/
	final public static String		CONTRACT_TABLE			= "optiuni_contract";
	
	
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
