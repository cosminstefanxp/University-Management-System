/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */

package aii.arhiva;

import java.rmi.RemoteException;
import java.util.ArrayList;

import aii.Disciplina;
import aii.NotaCatalog;
import aii.SituatieScolara;
import aii.protocol.MessageStructure;

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
public interface Arhiva {
	
	/** The Constant PUBLISH_NAME. */
	public static final String SERVER_ADDRESS="localhost";
	
	/** The Constant SERVER_PORT. */
	public static final Integer SERVER_PORT=10234;
	
	/**
	 * Processes a message and returns the string (message) to be sent to the client.
	 *
	 * @param message the message
	 * @param structure the structure
	 * @return the string
	 */
	public String processMessage(String message, MessageStructure structure);

	/**
	 * Adaugare disciplina in planul de învăţământ.
	 *
	 * @param discipline the discipline
	 * @return the int
	 * @throws RemoteException the remote exception
	 */
	public int adaugareDisciplina(ArrayList<Disciplina> discipline)  ;

	/**
	 * Editare disciplina in planul de învăţământ.
	 *
	 * @param discipline the discipline
	 * @return the int
	 * @  the remote exception
	 */
	public int editareDisciplina(ArrayList<Disciplina> discipline)  ;

	/**
	 * Stergere disciplina din planul de învăţământ.
	 *
	 * @param coduriDiscipline the coduri discipline
	 * @return the int
	 */
	public int stergereDisciplina(ArrayList<String> coduriDiscipline)  ;
	
	/**
	 * Adaugare disciplina in planul de învăţământ.
	 *
	 * @param disciplina the disciplina
	 * @return the int
	 * @  the remote exception
	 */
	public boolean adaugareDisciplina(Disciplina disciplina)  ;

	/**
	 * Editare disciplina in planul de învăţământ.
	 *
	 * @param disciplina the disciplina
	 * @return the int
	 * @  the remote exception
	 */
	public boolean editareDisciplina(Disciplina disciplina)  ;
	
	
	/**
	 * Obtine disciplinele din planul de invatamant.
	 *
	 * @return the array list
	 * @  the remote exception
	 */
	public ArrayList<Disciplina> obtineDiscipline()  ;

	/**
	 * Stergere disciplina din planul de învăţământ.
	 *
	 * @param codDisciplina the cod disciplina
	 * @return the int
	 */
	public boolean stergereDisciplina(String codDisciplina)  ;

	/**
	 * Specifica nota obţinută de un student la un examen.
	 *
	 * @param CNPCadruDidactic the cNP cadru didactic
	 * @param note the note
	 * @return 1.0 - daca e nota noua si a reusit; 2.0 - daca e nota veche dar asta e mai mare si operatia a reusit; 
	 * -1.0 - daca nu e materia cadrului respectiv; 0.0 - daca nu a reusit din alt motiv
	 * @  the remote exception
	 */
	public ArrayList<Float> stabilesteNota(String CNPCadruDidactic, ArrayList<NotaCatalog> note)  ;
	
	/**
	 * Stergere nota obţinută de un student la un examen.
	 *
	 * @param nota the nota
	 * @return true, if successful
	 * @  the remote exception
	 */
	public boolean stergereNota(NotaCatalog nota)  ;
	
	/**
	 * Obtine notele din tot catalogul (la toate materiile).
	 *
	 * @return the array list
	 * @  the remote exception
	 */
	public ArrayList<NotaCatalog> obtineNote()  ;

	/**
	 * Obţine nota pentru una sau mai multe discipline.
	 *
	 * @param CNPStudent the CNP student, used for checking permissions
	 * @param codDisciplina the cod disciplina
	 * @return the array list
	 * @  the remote exception
	 */
	public ArrayList<Float> obtineNoteStudent(String CNPStudent, ArrayList<Integer> codDisciplina);

	/**
	 * Obţine situaţia şcolară pentru anul universitar dat. Daca anStudiu este 0, se genereaza o medie pe toti anii.
	 *
	 * @param cnpStudent the cnp student
	 * @param anStudiu the an studiu
	 * @return the situatie scolara
	 * @  the remote exception
	 */
	public SituatieScolara obtineSituatieScolara(String cnpStudent, int anStudiu)  ;
	
	/**
	 * Obtine disciplinele urmate de un student intr-un an, conform contractului de studii al acestuia.
	 *
	 * @param cnpStudent the cnp student
	 * @param anStudiu the an studiu
	 * @return the array list
	 * @  the remote exception
	 */
	public ArrayList<Integer> obtineDisciplineUrmate(String cnpStudent, int anStudiu)  ; 
	
	/**
	 * Obtine disciplinele urmate de un student oricand, conform contractului de studii al acestuia.
	 *
	 * @param cnpStudent the cnp student
	 * @param anStudiu the an studiu
	 * @return the array list
	 * @  the remote exception
	 */
	public ArrayList<Integer> obtineDisciplineUrmate(String cnpStudent)  ; 

	/**
	 * Obtine discipline urmate de un student, conform contractului de studii al acestuia. Se obtin doar disciplinele
	 * dintr-unul dintre semestre.
	 *
	 * @param cnpStudent the cnp student
	 * @param anStudiu the an studiu
	 * @param semestru the semestru
	 * @return the array list
	 * @  the remote exception
	 */
	public ArrayList<Integer> obtineDisciplineUrmate(String cnpStudent, int anStudiu, int semestru)  ;
}
