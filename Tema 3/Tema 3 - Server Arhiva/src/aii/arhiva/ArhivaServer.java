/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii.arhiva;

import java.util.ArrayList;

import aii.Disciplina;
import aii.NotaCatalog;
import aii.SituatieScolara;
import aii.database.DisciplinaWrapper;
import aii.database.NotaCatalogWrapper;
import aii.database.OptiuneContractWrapper;
import aii.protocol.MessageStructure;

/**
 * The Class ArhivaServer.
 */
public class ArhivaServer implements Arhiva {

	/** The disciplina dao. */
	static DisciplinaWrapper disciplinaDAO = new DisciplinaWrapper();

	/** The nota catalog dao. */
	static NotaCatalogWrapper notaCatalogDAO = new NotaCatalogWrapper();

	/** The optiune contract dao. */
	static OptiuneContractWrapper optiuneContractDAO = new OptiuneContractWrapper();

	/**
	 * Debug message printer.
	 * 
	 * @param string
	 *            the string
	 */
	private static void debug(String string) {
		if (ConnectionThread.DEBUG)
			System.out.println("[DEBUG][Thread " + Thread.currentThread().getId() + "] " + string);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see aii.arhiva.Arhiva#processMessage(java.lang.String,
	 * aii.protocol.MessageStructure)
	 */
	public String processMessage(String message, MessageStructure structure) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see aii.arhiva.Arhiva#adaugareDisciplina(java.lang.String,
	 * java.util.ArrayList)
	 */
	@Override
	public int adaugareDisciplina(ArrayList<Disciplina> discipline) {

		int count = 0;
		boolean success;
		// introducem fiecare disciplina in baza de date si numaram cate
		// insertii au avut succes
		for (Disciplina disciplina : discipline) {
			success = disciplinaDAO.insertDisciplina(disciplina);
			if (success)
				count++;
		}
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see aii.arhiva.Arhiva#editareDisciplina(java.lang.String,
	 * java.util.ArrayList)
	 */
	@Override
	public int editareDisciplina(ArrayList<Disciplina> discipline) {

		int count = 0;
		boolean success;
		// editam fiecare disciplina in baza de date si numaram cate editari au
		// avut succes
		for (Disciplina disciplina : discipline) {
			success = disciplinaDAO.updateDisciplina("cod=\'" + disciplina.cod + "\'", disciplina);
			if (success)
				count++;
		}
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see aii.arhiva.Arhiva#stergereDisciplina(java.lang.String,
	 * java.util.ArrayList)
	 */
	@Override
	public int stergereDisciplina(ArrayList<String> coduriDiscipline) {

		int count = 0;
		boolean success;
		// stergem fiecare disciplina din baza de date si numaram cate stergeri
		// au avut succes
		for (String cod : coduriDiscipline) {
			success = disciplinaDAO.deleteDisciplina("cod=\'"+cod+"\'");
			if (success)
				count++;
		}
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see aii.arhiva.Arhiva#adaugareDisciplina(aii.Disciplina)
	 */
	@Override
	public boolean adaugareDisciplina(Disciplina disciplina) {
		return disciplinaDAO.insertDisciplina(disciplina);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see aii.arhiva.Arhiva#editareDisciplina(aii.Disciplina)
	 */
	@Override
	public boolean editareDisciplina(Disciplina disciplina) {
		return disciplinaDAO.updateDisciplina("cod=\'" + disciplina.cod + "\'", disciplina);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see aii.arhiva.Arhiva#stergereDisciplina(aii.Disciplina)
	 */
	@Override
	public boolean stergereDisciplina(String codDisciplina) {
		return disciplinaDAO.deleteDisciplina("cod=\'"+codDisciplina+"\'");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see aii.arhiva.Arhiva#obtineDiscipline()
	 */
	@Override
	public ArrayList<Disciplina> obtineDiscipline() {
		return disciplinaDAO.getDiscipline("cod=cod"); // toate disciplinele
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see aii.arhiva.Arhiva#obtineDisciplineUrmate(java.lang.String, int)
	 */
	@Override
	public ArrayList<Integer> obtineDisciplineUrmate(String cnpStudent, int anStudiu) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see aii.arhiva.Arhiva#obtineDisciplineUrmate(java.lang.String, int, int)
	 */
	@Override
	public ArrayList<Integer> obtineDisciplineUrmate(String cnpStudent, int anStudiu, int semestru) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see aii.arhiva.Arhiva#obtineNote()
	 */
	@Override
	public ArrayList<NotaCatalog> obtineNote() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see aii.arhiva.Arhiva#obtineNoteStudent(java.lang.String,
	 * java.util.ArrayList)
	 */
	@Override
	public ArrayList<Float> obtineNoteStudent(String CNPStudent, ArrayList<Integer> codDisciplina) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see aii.arhiva.Arhiva#obtineSituatieScolara(java.lang.String, int)
	 */
	@Override
	public SituatieScolara obtineSituatieScolara(String cnpStudent, int anStudiu) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see aii.arhiva.Arhiva#stabilesteNota(java.lang.String, aii.NotaCatalog)
	 */
	@Override
	public boolean stabilesteNota(String CNPCadruDidactic, NotaCatalog nota) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see aii.arhiva.Arhiva#stergereNota(aii.NotaCatalog)
	 */
	@Override
	public boolean stergereNota(NotaCatalog nota) {
		// TODO Auto-generated method stub
		return false;
	}

}
