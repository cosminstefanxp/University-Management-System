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
import java.util.Calendar;

import aii.Disciplina;
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
	 * Specifica nota obţinută de un student la un examen.
	 *
	 * @param CNPCadruDidactic the cNP cadru didactic
	 * @param codDisciplina the cod disciplina
	 * @param CNPStudent the cNP student
	 * @param nota the nota
	 * @param data the data
	 * @return true, if successful
	 * @throws RemoteException the remote exception
	 */
	public boolean stabilesteNota(int CNPCadruDidactic, int codDisciplina, int CNPStudent,
			int nota, Calendar data) throws RemoteException;

	/**
	 * Obţine nota pentru una sau mai multe discipline.
	 *
	 * @param CNPStudent the CNP student
	 * @param codDisciplina the cod disciplina
	 * @return the array list
	 * @throws RemoteException the remote exception
	 */
	public ArrayList<Integer> obtineNota(int CNPStudent, ArrayList<Integer> codDisciplina)
			throws RemoteException;

	/**
	 * Obţine situaţia şcolară pentru anul universitar curent.
	 *
	 * @param CNPStudent the CNP student
	 * @return the situatie scolara
	 * @throws RemoteException the remote exception
	 */
	public SituatieScolara obtineSituatieScolara(int CNPStudent) throws RemoteException;

}
