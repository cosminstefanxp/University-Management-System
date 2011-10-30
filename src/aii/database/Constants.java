package aii.database;


public class Constants {
	final public static String 		DATABASE_CONNECTION	= "jdbc:mysql://localhost/Grupa342C4_StefanDobrinCosmin";
	final public static String		DATABASE_USER		= "root";
	final public static String 		DATABASE_PASSWORD	= "root";
	
	final public static String		USER_TABLE			= "utilizatori";
	final public static String[][]	USER_FIELD_MATCH	= {
															{ "cnp", "parola", "tip", "nume", "prenume", "email", "adresa", "titlu_grupa", "finantare"},	//campuri obiect
															{ "CNP", "parola", "tip", "nume", "prenume", "email", "adresa", "titlu_grupa", "finantare"}		//campuri baza de date
														};
	
	final public static String[][]	ADMIN_USER_COLUMN_FIELD_MATCH ={
															{ "tip",      "CNP", "nume", "prenume", "email", "adresa", "titlu_grupa", "finantare"},			//campuri obiect
															{ "Tip Cont", "CNP", "Nume", "Prenume", "Email", "Adresa", "Titlu/Grupa", "Forma Finantare"}	//nume coloane
														}; 
		
	
}
