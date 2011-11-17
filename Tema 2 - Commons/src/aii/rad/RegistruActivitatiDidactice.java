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
	 * Stabileste formatie de studiu.
	 *
	 * @param CNPStudent the CNP student
	 * @param grupa the grupa
	 * @return the int
	 * @throws RemoteException the remote exception
	 */
	public int stabilesteFormatieDeStudiu(ArrayList<String> CNPStudent, int grupa)
			throws RemoteException;

	/**
	 * Adaugare activitate didactica in orar.
	 *
	 * @param activitatiDidactice the activitati didactice
	 * @return the int
	 * @throws RemoteException the remote exception
	 */
	public int adaugareActivitateOrar(ArrayList<Orar> activitatiDidactice)
			throws RemoteException;

	/**
	 * Editare activitate didactica in orar.
	 *
	 * @param activitatiDidactice the activitati didactice
	 * @return the int
	 * @throws RemoteException the remote exception
	 */
	public int editareActivitateOrar(ArrayList<Orar> activitatiDidactice)
			throws RemoteException;

	/**
	 * Stergere activitate didactica din orar.
	 *
	 * @param activitatiDidactice the activitati didactice
	 * @return the int
	 * @throws RemoteException the remote exception
	 */
	public int stergereActivitateOrar(ArrayList<Orar> activitatiDidactice)
			throws RemoteException;

	/**
	 * Adaugare examen in calendarul de examene.
	 *
	 * @param examene the examene
	 * @return the int
	 * @throws RemoteException the remote exception
	 */
	public int adaugareExamen(ArrayList<Examen> examene) throws RemoteException;

	/**
	 * Editare examen in calendarul de examene.
	 *
	 * @param examene the examene
	 * @return the int
	 * @throws RemoteException the remote exception
	 */
	public int editareExamen(ArrayList<Examen> examene) throws RemoteException;

	/**
	 * Stergere examen din calendarul de examene.
	 *
	 * @param examene the examene
	 * @return the int
	 * @throws RemoteException the remote exception
	 */
	public int stergereExamen(ArrayList<Examen> examene) throws RemoteException;

	/**
	 * Obţine orarul complet al activităţilor didactice pentru semestrul în curs.
	 *
	 * @param CNPStudent the CNP student, used for checking permissions
	 * @return the array list of OrarComplet entities
	 * @throws RemoteException the remote exception
	 */
	public ArrayList<OrarComplet> obtineOrarComplet(String CNPStudent) throws RemoteException;

	/**
	 * Obţine calendarul examenelor pentru sesiunea în curs.
	 *
	 * @param CNPStudent the CNP student
	 * @return the array list
	 * @throws RemoteException the remote exception
	 */
	public ArrayList<Examen> obtineProgramareExamene(String CNPStudent) throws RemoteException;

	
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
}
