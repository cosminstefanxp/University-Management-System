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
	 * @param cnpSefCatedra the cnp sef catedra, used for checking permissions
	 * @param activitate the activitate
	 * @return true, if successful
	 * @throws RemoteException the remote exception
	 */
	public boolean stabilesteActivitatePredare(String cnpSefCatedra, Activitate activitate) throws RemoteException;

	/**
	 * Stabileste formatie de studiu.
	 *
	 * @param cnpSecretar the cnp secretar, used for checking permissions
	 * @param CNPStudent the CNP student
	 * @param grupa the grupa
	 * @return the int
	 * @throws RemoteException the remote exception
	 */
	public int stabilesteFormatieDeStudiu(String cnpSecretar, ArrayList<String> CNPStudent, int grupa)
			throws RemoteException;

	/**
	 * Adaugare activitate didactica in orar.
	 *
	 * @param cnpSecretar the cnp secretar, used for checking permissions
	 * @param activitatiDidactice the activitati didactice
	 * @return the int
	 * @throws RemoteException the remote exception
	 */
	public int adaugareActivitateOrar(String cnpSecretar, ArrayList<Orar> activitatiDidactice)
			throws RemoteException;

	/**
	 * Editare activitate didactica in orar.
	 *
	 * @param cnpSecretar the cnp secretar, used for checking permissions
	 * @param activitatiDidactice the activitati didactice
	 * @return the int
	 * @throws RemoteException the remote exception
	 */
	public int editareActivitateOrar(String cnpSecretar, ArrayList<Orar> activitatiDidactice)
			throws RemoteException;

	/**
	 * Stergere activitate didactica din orar.
	 *
	 * @param cnpSecretar the cnp secretar, used for checking permissions
	 * @param activitatiDidactice the activitati didactice
	 * @return the int
	 * @throws RemoteException the remote exception
	 */
	public int stergereActivitateOrar(String cnpSecretar, ArrayList<Orar> activitatiDidactice)
			throws RemoteException;

	/**
	 * Adaugare examen in calendarul de examene.
	 *
	 * @param cnpSecretar the cnp secretar, used for checking permissions
	 * @param examene the examene
	 * @return the int
	 * @throws RemoteException the remote exception
	 */
	public int adaugareExamen(String cnpSecretar, ArrayList<Examen> examene) throws RemoteException;

	/**
	 * Editare examen in calendarul de examene.
	 *
	 * @param cnpSecretar the cnp secretar, used for checking permissions
	 * @param examene the examene
	 * @return the int
	 * @throws RemoteException the remote exception
	 */
	public int editareExamen(String cnpSecretar, ArrayList<Examen> examene) throws RemoteException;

	/**
	 * Stergere examen din calendarul de examene.
	 *
	 * @param cnpSecretar the cnp secretar, used for checking permissions
	 * @param examene the examene
	 * @return the int
	 * @throws RemoteException the remote exception
	 */
	public int stergereExamen(String cnpSecretar, ArrayList<Examen> examene) throws RemoteException;

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
	 */
	public boolean autentificaUtilizator(String cnp, Utilizator.Tip permisiuni) throws RemoteException;
}
