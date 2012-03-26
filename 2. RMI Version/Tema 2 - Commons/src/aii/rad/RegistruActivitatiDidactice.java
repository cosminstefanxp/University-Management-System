/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii.rad;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;

import aii.Activitate;
import aii.Examen;
import aii.Orar;
import aii.OrarComplet;
import aii.Utilizator;

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
public interface RegistruActivitatiDidactice extends Remote {

	/** The Constant PUBLISH_NAME. */
	public static final String PUBLISH_NAME = "RADServiciu";
	
	/**
	 * Stabileste repartizare cadru didactic la o disciplina pentru un tip de activitate didactica.
	 *
	 * @param activitate the activitate
	 * @return true, if successful
	 * @throws RemoteException the remote exception
	 */
	public boolean stabilesteActivitatePredare(Activitate activitate) throws RemoteException;
	
	/**
	 * Sterge o repartizare cadru didactic la o disciplina pentru un tip de activitate didactica.
	 *
	 * @param activitate the activitate
	 * @return true, if successful
	 * @throws RemoteException the remote exception
	 */
	public boolean stergereActivitatePredare(Activitate activitate) throws RemoteException;
	
	/**
	 * Editeaza o repartizare cadru didactic la o disciplina pentru un tip de activitate didactica.
	 *
	 * @param activitate the activitate
	 * @return true, if successful
	 * @throws RemoteException the remote exception
	 */
	public boolean editareActivitatePredare(Activitate activitate) throws RemoteException;
	
	/**
	 * Obtine repartizare cadru didactic la o disciplina pentru un tip de activitate didactica.
	 *
	 * @return true, if successful
	 * @throws RemoteException the remote exception
	 */
	public ArrayList<Activitate> obtinereActivitatePredare() throws RemoteException;
	
	/**
	 * Obtinere activitatile de predare de tip curs pentru un cadru didactic dat.
	 *
	 * @param cnpCadruDidactic the cnp cadru didactic
	 * @return the array list
	 * @throws RemoteException the remote exception
	 */
	public ArrayList<Activitate> obtinereActivitatiPredareCursCadru(String cnpCadruDidactic) throws RemoteException;

	/**
	 * Stabileste formatie de studiu.
	 *
	 * @param CNPStudent the CNP student
	 * @param grupa the grupa
	 * @return the int
	 * @throws RemoteException the remote exception
	 */
	public int stabilesteFormatieDeStudiu(ArrayList<String> CNPStudent, String grupa)
			throws RemoteException;

	/**
	 * Adaugare activitate didactica in orar.
	 *
	 * @param orar the orar
	 * @return true, if successful
	 * @throws RemoteException the remote exception
	 */
	public boolean adaugareActivitateOrar(Orar orar)
			throws RemoteException;

	/**
	 * Editare activitate didactica in orar.
	 *
	 * @param orarNou the orar nou
	 * @param orarVechi the orar vechi, utilizat pentru a gasi vechea activitate orar si a o edita
	 * @return true, if successful
	 * @throws RemoteException the remote exception
	 */
	public boolean editareActivitateOrar(Orar orarNou, Orar orarVechi)
			throws RemoteException;

	/**
	 * Stergere activitate didactica din orar.
	 *
	 * @param orar the orar
	 * @return true, if successful
	 * @throws RemoteException the remote exception
	 */
	public boolean stergereActivitateOrar(Orar orar)
			throws RemoteException;

	/**
	 * Adaugare examen in calendarul de examene.
	 *
	 * @param examen the examen
	 * @return the int
	 * @throws RemoteException the remote exception
	 */
	public boolean adaugareExamen(Examen examen) throws RemoteException;

	/**
	 * Editare examen in calendarul de examene.
	 *
	 * @param examenNou the examen nou
	 * @param examenVechi the examen vechi, pentru a putea identifica in mod unic examenul pe care il actualizam
	 * @return the int
	 * @throws RemoteException the remote exception
	 */
	public boolean editareExamen(Examen examenNou, Examen examenVechi) throws RemoteException;

	/**
	 * Stergere examen din calendarul de examene.
	 *
	 * @param examen the examen
	 * @return the int
	 * @throws RemoteException the remote exception
	 */
	public boolean stergereExamen(Examen examen) throws RemoteException;
	
	/**
	 * Obtine toate examenele din baza de date. Pentru a se folosi in interfata de secretar.
	 *
	 * @return the array list
	 * @throws RemoteException the remote exception
	 */
	public ArrayList<Examen> obtineExamene() throws RemoteException;
	
	/**
	 * Obţine orarul complet al activităţilor didactice pentru semestrul în curs.
	 *
	 * @param CNPStudent the CNP student, used for checking permissions
	 * @param grupa the grupa
	 * @param semestru the semestru
	 * @return the array list of OrarComplet entities
	 * @throws RemoteException the remote exception
	 */
	public ArrayList<OrarComplet> obtineOrarComplet(String CNPStudent, String grupa, int semestru) throws RemoteException;
	
	/**
	 * Obtine orarul complet al tuturor activitatile. De folosit in interfata de secretar.
	 *
	 * @return the array list
	 * @throws RemoteException the remote exception
	 */
	public ArrayList<OrarComplet> obtineOrarComplet() throws RemoteException;

	/**
	 * Obţine calendarul examenelor pentru sesiunea în curs. Se completeaza si campul denumireDisciplina din obiectele Examen.
	 *
	 * @param CNPStudent the CNP student
	 * @param grupa the grupa
	 * @return the array list
	 * @throws RemoteException the remote exception
	 */
	public ArrayList<Examen> obtineProgramareExamene(String CNPStudent, String grupa) throws RemoteException;

	
	/**
	 * Autentifica un utilizator, intorcandu-l daca autentificarea a avut loc cu succes.
	 *
	 * @param CNP the cNP
	 * @param parola the parola
	 * @return the utilizator, daca autentificarea a fost cu succes, sau null daca nu a fost cu succes.
	 * @throws RemoteException the remote exception
	 */
	public Utilizator autentificaUtilizator(String CNP, String parola) throws RemoteException;
	
	/**
	 * Autentifica un utilizator, verificand daca acesta are permisiunile cerute.
	 *
	 * @param cnp the cnp
	 * @param permisiuni the permisiuni
	 * @return true, if successful
	 * @throws RemoteException the remote exception
	 */
	public boolean autentificaUtilizator(String cnp, Utilizator.Tip permisiuni) throws RemoteException;
	
	/**
	 * Obtine utilizatorii din baza de date, dandu'se o clauza de selectie.
	 *
	 * @param whereClause the where clause
	 * @return the array list
	 * @throws RemoteException the remote exception
	 */
	public ArrayList<Utilizator> obtineUtilizatori(String whereClause) throws RemoteException;
	
	/**
	 * Verifica daca un cadru didactic preda cursul la o disciplina. 
	 *
	 * @param codDisciplina the cod disciplina
	 * @param cnpCadruDidactic the cnp cadru didactic
	 * @return true, if successful
	 * @throws RemoteException the remote exception
	 */
	public boolean cadruPentruDisciplina(int codDisciplina, String cnpCadruDidactic) throws RemoteException;
	
	/**
	 * Obtine toate grupele de studenti care exista.
	 *
	 * @return the vector
	 */
	public Vector<String> obtineGrupeStudenti() throws RemoteException;
	
}
