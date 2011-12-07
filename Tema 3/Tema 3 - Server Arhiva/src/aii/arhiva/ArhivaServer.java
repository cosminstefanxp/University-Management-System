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
import aii.protocol.MessageConstants;
import aii.protocol.MessageParser;
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
		if (ArhivaConnectionThread.DEBUG)
			System.out.println("[DEBUG][Thread " + Thread.currentThread().getId() + "] " + string);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see aii.arhiva.Arhiva#processMessage(java.lang.String,
	 * aii.protocol.MessageStructure)
	 */
	public String processMessage(String message, MessageStructure structure) {
		
		//Facem procesarea header-ului mesajului si trimitem mesajul la componenta corespunzatoare
		if(structure.header.equalsIgnoreCase("stabilire_plan_de_invatamant"))
			return managementDisciplina(message);
		
		
		
		return null;
	}
	
	/**
	 * Realizeaza taskurile de management asupre unei/mai multor discipline.
	 *
	 * @param message the message
	 * @return the string
	 */
	private String managementDisciplina(String message)
	{
		//Spargere mesaj in componente
		String[] msgFields=MessageParser.splitMessage(message);
		if(msgFields.length<4)
		{
			debug("Format incorect mesaj: "+message);
			return "error#format_mesaj";
		}
		
		//Realizam fiecare operatie
		Integer n=Integer.parseInt(msgFields[1]);
		String response;
		response="raspuns_"+msgFields[0]+"#"+n;	//header de raspuns
		for(int i=1;i<=n;i++)
		{
			String operatie=msgFields[2*i];
			boolean res=true;
			debug("Analizam operatia "+(2*i)+":"+operatie);
			
			//Realizare operatii
			if(operatie.equals("stergere"))
				res=disciplinaDAO.deleteDisciplina("cod=\'"+msgFields[2*i+1]+"\'");
			else 
			{
				Disciplina disciplina=MessageParser.parseObject(Disciplina.class, msgFields[2*i+1], MessageConstants.STRUCTURE_DISCIPLINA);
				if(operatie.equals("adaugare"))
					res=disciplinaDAO.insertDisciplina(disciplina);
				else if(operatie.equals("editare"))
					res=disciplinaDAO.updateDisciplina("cod=\'"+disciplina.cod+"\'",disciplina);
				else 
					res=false;
			}
			//Raspuns
			response+=MessageParser.DELIMITER.toString()+Boolean.toString(res);
		}
		
		
		return response;
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


	/* (non-Javadoc)
	 * @see aii.arhiva.Arhiva#stabilesteNota(java.lang.String, aii.NotaCatalog)
	 */
	@Override
	public boolean stabilesteNota(String cnpCadruDidactic, NotaCatalog nota) {
//		//Verificam daca acest cadru preda la materia respectiva cursul
//		if(radService==null)
//			pregatesteRADService();
//		if(radService.cadruPentruDisciplina(nota.getCodDisciplina(), cnpCadruDidactic)==false)
//			return false;
//		
//		//Verificam daca exista deja o nota pentru acel elev la aceasta materie
//		NotaCatalog notaExistentaMaxima=notaCatalogDAO.getNotaCatalog(nota.codDisciplina, nota.cnpStudent);
//		
//		//Daca nu exista nota, o inseram
//		if(notaExistentaMaxima==null)
//			return notaCatalogDAO.insertNotaCatalog(nota);
//		//Daca exista dar era mai mica sau era restanta, o actualizam
//		else if(notaExistentaMaxima.nota<5 || notaExistentaMaxima.nota<=nota.nota)
//			return notaCatalogDAO.updateNotaCatalog(notaExistentaMaxima, nota);

		//Altfel nu facem nimic
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
