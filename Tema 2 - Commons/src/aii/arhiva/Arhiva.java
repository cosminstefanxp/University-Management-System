/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */

package aii.arhiva;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import aii.Disciplina;
import aii.NotaCatalog;
import aii.SituatieScolara;

/**
 * Arhiva gestionează informaţiile referitoare la disciplinele de învăţământ cât
 * şi la notele obţinute de studenţi la examene. Sunt implementate metode care
 * permit specificarea unui plan de învăţământ de către un şef de catedră,
 * stabilirea notei pentru o disciplină pentru un student (de către un cadru
 * didactic), dar şi obţinerea ei (de către un student), cât şi determinarea
 * situaţiei şcolare prin calculul mediei generale şi al numărului de puncte
 * credit.
 * 
 * @author Stefan-Dobrin Cosmin
 */
public interface Arhiva extends Remote {
	
	/** The Constant PUBLISH_NAME. */
	public static final String PUBLISH_NAME="ServiciuArhiva";

	/**
	 * Adaugare disciplina in planul de învăţământ.
	 *
	 * @param discipline the discipline
	 * @return the int
	 * @throws RemoteException the remote exception
	 */
	public int adaugareDisciplina(ArrayList<Disciplina> discipline) throws RemoteException;

	/**
	 * Editare disciplina in planul de învăţământ.
	 *
	 * @param discipline the discipline
	 * @return the int
	 * @throws RemoteException the remote exception
	 */
	public int editareDisciplina(ArrayList<Disciplina> discipline) throws RemoteException;

	/**
	 * Stergere disciplina din planul de învăţământ.
	 *
	 * @param discipline the discipline
	 * @return the int
	 * @throws RemoteException the remote exception
	 */
	public int stergereDisciplina(ArrayList<Disciplina> discipline) throws RemoteException;
	
	/**
	 * Adaugare disciplina in planul de învăţământ.
	 *
	 * @param disciplina the disciplina
	 * @return the int
	 * @throws RemoteException the remote exception
	 */
	public boolean adaugareDisciplina(Disciplina disciplina) throws RemoteException;

	/**
	 * Editare disciplina in planul de învăţământ.
	 *
	 * @param discipline the discipline
	 * @return the int
	 * @throws RemoteException the remote exception
	 */
	public boolean editareDisciplina(Disciplina disciplina) throws RemoteException;
	
	
	/**
	 * Obtine disciplinele din planul de invatamant.
	 *
	 * @return the array list
	 * @throws RemoteException the remote exception
	 */
	public ArrayList<Disciplina> obtineDiscipline() throws RemoteException;

	/**
	 * Stergere disciplina din planul de învăţământ.
	 *
	 * @param discipline the discipline
	 * @return the int
	 * @throws RemoteException the remote exception
	 */
	public boolean stergereDisciplina(Disciplina disciplina) throws RemoteException;

	/**
	 * Specifica nota obţinută de un student la un examen.
	 *
	 * @param CNPCadruDidactic the cNP cadru didactic
	 * @param nota the nota
	 * @return true, if successful
	 * @throws RemoteException the remote exception
	 */
	public boolean stabilesteNota(String CNPCadruDidactic, NotaCatalog nota) throws RemoteException;
	
	/**
	 * Stergere nota obţinută de un student la un examen.
	 *
	 * @param nota the nota
	 * @return true, if successful
	 * @throws RemoteException the remote exception
	 */
	public boolean stergereNota(NotaCatalog nota) throws RemoteException;
	
	/**
	 * Obtine notele din tot catalogul (la toate materiile).
	 *
	 * @return the array list
	 */
	public ArrayList<NotaCatalog> obtineNote() throws RemoteException;

	/**
	 * Obţine nota pentru una sau mai multe discipline.
	 *
	 * @param CNPStudent the CNP student, used for checking permissions
	 * @param codDisciplina the cod disciplina
	 * @return the array list
	 * @throws RemoteException the remote exception
	 */
	public ArrayList<Float> obtineNoteStudent(String CNPStudent, ArrayList<Integer> codDisciplina)
			throws RemoteException;

	/**
	 * Obţine situaţia şcolară pentru anul universitar dat. Daca anStudiu este 0, se genereaza o medie pe toti anii.
	 *
	 * @param CNPStudent the CNP student, used for checking permissions
	 * @param anStudiu the an studiu
	 * @return the situatie scolara
	 * @throws RemoteException the remote exception
	 */
	public SituatieScolara obtineSituatieScolara(String CNPStudent, int anStudiu) throws RemoteException;
	


}
