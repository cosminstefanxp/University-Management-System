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
import aii.Utilizator.Tip;
import aii.database.ActivitateWrapper;
import aii.database.ExamenWrapper;
import aii.database.OrarCompletWrapper;
import aii.database.OrarWrapper;
import aii.database.UtilizatorWrapper;
import aii.protocol.MessageConstants;
import aii.protocol.MessageParser;
import aii.protocol.MessageStructure;

/**
 * The Class ArhivaServer.
 */
public class RADServer implements RegistruActivitatiDidactice {

	/** The utilizator dao. */
	private static UtilizatorWrapper utilizatorDAO=new UtilizatorWrapper();
	
	/** The orar dao. */
	private static OrarWrapper orarDAO=new OrarWrapper();
	
	/** The orar complet dao. */
	private static OrarCompletWrapper orarCompletDAO=new OrarCompletWrapper();
	
	/** The activitate dao. */
	private static ActivitateWrapper activitateDAO=new ActivitateWrapper();
	
	/** The examen dao. */
	private static ExamenWrapper examenDAO=new ExamenWrapper();

	/**
	 * Debug message printer.
	 * 
	 * @param string
	 *            the string
	 */
	private static void debug(String string) {
		if (RADConnectionThread.DEBUG)
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
		if(structure.header.equalsIgnoreCase("repartizare_cadre_didactice"))
			return managementActivitate(message);
		
		
		
		return null;
	}
	
	/**
	 * Realizeaza taskurile de management asupre unei/mai multor activitati.
	 *
	 * @param message the message
	 * @return the string
	 */
	private String managementActivitate(String message)
	{
		//Spargere mesaj in componente
		String[] msgFields=MessageParser.splitMessage(message);
		if(msgFields.length<3)
		{
			debug("Format incorect mesaj: "+message);
			return "error#format_mesaj";
		}
		
		//Realizam fiecare operatie
		Integer n=Integer.parseInt(msgFields[1]);
		String response;
		response="raspuns_"+msgFields[0]+"#"+n;	//header de raspuns
		for(int i=2;i<n+2;i++)
		{
			boolean res=true;
			debug("Analizam operatia "+msgFields[i]);
			
			//Realizare operatii
			Activitate activitate=MessageParser.parseObject(Activitate.class, msgFields[i], MessageConstants.STRUCTURE_ACTIVITATE);
			res=activitateDAO.insertActivitate(activitate);

			//Raspuns
			response+=MessageParser.DELIMITER.toString()+Boolean.toString(res);
		}
		
		return response;
	}

	@Override
	public boolean adaugareActivitateOrar(Orar orar) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean adaugareExamen(Examen examen) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Utilizator autentificaUtilizator(String CNP, String parola) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean autentificaUtilizator(String cnp, Tip permisiuni) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cadruPentruDisciplina(int codDisciplina, String cnpCadruDidactic) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editareActivitateOrar(Orar orarNou, Orar orarVechi) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#editareActivitatePredare(aii.Activitate)
	 */
	@Override
	public boolean editareActivitatePredare(Activitate activitate){
		return activitateDAO.updateActivitate("id=\'"+activitate.id+"\'",activitate);
	}

	@Override
	public ArrayList<Examen> obtineExamene() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<String> obtineGrupeStudenti() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<OrarComplet> obtineOrarComplet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<OrarComplet> obtineOrarComplet(String CNPStudent, String grupa, int semestru) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Examen> obtineProgramareExamene(String CNPStudent, String grupa) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Utilizator> obtineUtilizatori(String whereClause) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Activitate> obtinereActivitatePredare() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Activitate> obtinereActivitatiPredareCursCadru(String cnpCadruDidactic) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#stabilesteActivitatePredare(java.lang.String, aii.Activitate)
	 */
	@Override
	public boolean stabilesteActivitatePredare(Activitate activitate) {
		return activitateDAO.insertActivitate(activitate);
		
	}

	@Override
	public int stabilesteFormatieDeStudiu(ArrayList<String> CNPStudent, String grupa) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean stergereActivitateOrar(Orar orar) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#stergereActivitatePredare(aii.Activitate)
	 */
	@Override
	public boolean stergereActivitatePredare(Activitate activitate) {
		return activitateDAO.deleteActivitate(activitate);
	}

	@Override
	public boolean stergereExamen(Examen examen) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editareExamen(Examen examenNou, Examen examenVechi) {
		// TODO Auto-generated method stub
		return false;
	}


}
