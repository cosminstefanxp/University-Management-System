/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii.rad;

import java.util.ArrayList;
import java.util.Vector;

import aii.Activitate;
import aii.Examen;
import aii.Orar;
import aii.OrarComplet;
import aii.Utilizator;
import aii.protocol.MessageStructure;

/**
 * RegistruActivităţiDidactice gestionează informaţiile despre calendarul
 * activităţilor didactice de pe parcursul unui an universitar. Acesta va
 * permite şefului de catedră să stabilească repartizarea unui cadru didactic
 * pentru un tip de activitate didactică aferente unei discipline de învăţământ,
 * secretarului stabilirea formaţiilor de studiu, specificarea orarului şi a
 * programării examenelor iar studentului afişarea informaţiilor cu privire la
 * activităţile didactice la care trebuie să participe.
 * 
 * 
 * @author Stefan-Dobrin Cosmin
 */
public interface RegistruActivitatiDidactice{

	/** The Constant PUBLISH_NAME. */
	public static final String SERVER_ADDRESS="localhost";
	
	/** The Constant SERVER_PORT. */
	public static final Integer SERVER_PORT=10235;
	
	/**
	 * Processes a message and returns the string (message) to be sent to the client.
	 *
	 * @param message the message
	 * @param structure the structure
	 * @return the string
	 */
	public String processMessage(String message, MessageStructure structure);
	
	/**
	 * Stabileste repartizare cadru didactic la o disciplina pentru un tip de activitate didactica.
	 *
	 * @param activitate the activitate
	 * @return true, if successful
	 * @  the remote exception
	 */
	public boolean stabilesteActivitatePredare(Activitate activitate)  ;
	
	/**
	 * Sterge o repartizare cadru didactic la o disciplina pentru un tip de activitate didactica.
	 *
	 * @param activitate the activitate
	 * @return true, if successful
	 * @  the remote exception
	 */
	public boolean stergereActivitatePredare(Activitate activitate)  ;
	
	/**
	 * Editeaza o repartizare cadru didactic la o disciplina pentru un tip de activitate didactica.
	 *
	 * @param activitate the activitate
	 * @return true, if successful
	 * @  the remote exception
	 */
	public boolean editareActivitatePredare(Activitate activitate)  ;
	
	/**
	 * Obtine repartizare cadru didactic la o disciplina pentru un tip de activitate didactica.
	 *
	 * @return true, if successful
	 * @  the remote exception
	 */
	public ArrayList<Activitate> obtinereActivitatePredare()  ;
	
	/**
	 * Obtinere activitatile de predare de tip curs pentru un cadru didactic dat.
	 *
	 * @param cnpCadruDidactic the cnp cadru didactic
	 * @return the array list
	 * @  the remote exception
	 */
	public ArrayList<Activitate> obtinereActivitatiPredareCursCadru(String cnpCadruDidactic)  ;

	/**
	 * Stabileste formatie de studiu.
	 *
	 * @param CNPStudent the CNP student
	 * @param grupa the grupa
	 * @return the int
	 * @  the remote exception
	 */
	public int stabilesteFormatieDeStudiu(ArrayList<String> CNPStudent, String grupa)
			 ;

	/**
	 * Adaugare activitate didactica in orar.
	 *
	 * @param orar the orar
	 * @return true, if successful
	 * @  the remote exception
	 */
	public boolean adaugareActivitateOrar(Orar orar)
			 ;

	/**
	 * Editare activitate didactica in orar.
	 *
	 * @param orarNou the orar nou
	 * @param orarVechi the orar vechi, utilizat pentru a gasi vechea activitate orar si a o edita
	 * @return true, if successful
	 * @  the remote exception
	 */
	public boolean editareActivitateOrar(Orar orarNou, Orar orarVechi)
			 ;

	/**
	 * Stergere activitate didactica din orar.
	 *
	 * @param orar the orar
	 * @return true, if successful
	 * @  the remote exception
	 */
	public boolean stergereActivitateOrar(Orar orar)
			 ;

	/**
	 * Adaugare examen in calendarul de examene.
	 *
	 * @param examen the examen
	 * @return the int
	 * @  the remote exception
	 */
	public boolean adaugareExamen(Examen examen)  ;

	/**
	 * Editare examen in calendarul de examene.
	 *
	 * @param examenNou the examen nou
	 * @param examenVechi the examen vechi, pentru a putea identifica in mod unic examenul pe care il actualizam
	 * @return the int
	 * @  the remote exception
	 */
	public boolean editareExamen(Examen examenNou, Examen examenVechi)  ;

	/**
	 * Stergere examen din calendarul de examene.
	 *
	 * @param examen the examen
	 * @return the int
	 * @  the remote exception
	 */
	public boolean stergereExamen(Examen examen)  ;
	
	/**
	 * Obtine toate examenele din baza de date. Pentru a se folosi in interfata de secretar.
	 *
	 * @return the array list
	 * @  the remote exception
	 */
	public ArrayList<Examen> obtineExamene()  ;
	
	/**
	 * Obţine orarul complet al activităţilor didactice pentru semestrul în curs.
	 *
	 * @param CNPStudent the CNP student, used for checking permissions
	 * @param grupa the grupa
	 * @param semestru the semestru
	 * @return the array list of OrarComplet entities
	 * @  the remote exception
	 */
	public ArrayList<OrarComplet> obtineOrarComplet(String CNPStudent, int semestru) throws Exception ;
	
	/**
	 * Obtine orarul complet al tuturor activitatile. De folosit in interfata de secretar.
	 *
	 * @return the array list
	 * @  the remote exception
	 */
	public ArrayList<OrarComplet> obtineOrarComplet()  ;

	/**
	 * Obţine calendarul examenelor pentru sesiunea în curs. 
	 *
	 * @param CNPStudent the CNP student
	 * @param grupa the grupa
	 * @return the array list
	 * @  the remote exception
	 */
	public ArrayList<Examen> obtineProgramareExamene(String CNPStudent) throws Exception ;

	
	/**
	 * Autentifica un utilizator, intorcandu-l daca autentificarea a avut loc cu succes.
	 *
	 * @param CNP the cNP
	 * @param parola the parola
	 * @return the utilizator, daca autentificarea a fost cu succes, sau null daca nu a fost cu succes.
	 * @  the remote exception
	 */
	public Utilizator autentificaUtilizator(String CNP, String parola)  ;
	
	/**
	 * Autentifica un utilizator, verificand daca acesta are permisiunile cerute.
	 *
	 * @param cnp the cnp
	 * @param permisiuni the permisiuni
	 * @return true, if successful
	 * @  the remote exception
	 */
	public boolean autentificaUtilizator(String cnp, Utilizator.Tip permisiuni)  ;
	
	/**
	 * Obtine utilizatorii din baza de date, dandu'se o clauza de selectie.
	 *
	 * @param whereClause the where clause
	 * @return the array list
	 * @  the remote exception
	 */
	public ArrayList<Utilizator> obtineUtilizatori(String whereClause)  ;
	
	/**
	 * Verifica daca un cadru didactic preda cursul la o disciplina. 
	 *
	 * @param codDisciplina the cod disciplina
	 * @param cnpCadruDidactic the cnp cadru didactic
	 * @return true, if successful
	 * @  the remote exception
	 */
	public boolean cadruPentruDisciplina(int codDisciplina, String cnpCadruDidactic)  ;
	
	/**
	 * Obtine toate grupele de studenti care exista.
	 *
	 * @return the vector
	 */
	public Vector<String> obtineGrupeStudenti()  ;
	
}
