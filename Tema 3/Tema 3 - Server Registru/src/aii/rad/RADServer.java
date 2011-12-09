/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii.rad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import com.sun.org.apache.xpath.internal.operations.Bool;

import aii.Activitate;
import aii.Examen;
import aii.Orar;
import aii.OrarComplet;
import aii.Utilizator;
import aii.Utilizator.Tip;
import aii.arhiva.Arhiva;
import aii.database.ActivitateWrapper;
import aii.database.Constants;
import aii.database.DatabaseConnection;
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

	private static PrintWriter toArhiva = null;
	private static BufferedReader fromArhiva = null;
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
		if(structure.header.equalsIgnoreCase("solicitare_orar"))
			return providerOrarStudent(message);
		if(structure.header.equalsIgnoreCase("solicitare_calendar_examene"))
			return providerExameneStudent(message);
		if(structure.header.equalsIgnoreCase("cadru_pentru_disciplina"))
			return queryCadruPentruDisciplina(message);		
		
		return null;
	}
	
	/**
	 * Sent Message to arhiva.
	 *
	 * @param message the message
	 * @return the string
	 */
	private String messageToArhiva(String message)
	{
		
		try {
				toArhiva.println(message);
				String fromServer = fromArhiva.readLine();
				return fromServer;
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: localhost.");
			System.exit(-1);
		}
		return null;
	}
	
	/**
	 * Conectare la componenta arhiva.
	 */
	private void conectareArhiva()
	{
		if(toArhiva!=null && fromArhiva!=null)
			return;
		
		Socket socket = null;
		
		int port = Arhiva.SERVER_PORT;
		String adresa = Arhiva.SERVER_ADDRESS;
		
		debug("Ne conectam la Arhiva.");
		try {
			socket = new Socket(adresa, port);
			toArhiva = new PrintWriter(socket.getOutputStream(), true);
			fromArhiva = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: localhost.");
			System.exit(-1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: localhost.");
			System.exit(-1);
		}
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

	/**
	 * Obtine orar al unui student.
	 *
	 * @param message the message
	 * @return the string
	 */
	private String providerOrarStudent(String message)
	{
		//Spargere mesaj in componente
		String[] msgFields=MessageParser.splitMessage(message);
		if(msgFields.length!=3)
		{
			debug("Format incorect mesaj: "+message);
			return "error#format_mesaj";
		}
		
		//Realizam fiecare operatie
		Integer semestru=Integer.parseInt(msgFields[2]);
		String cnp=msgFields[1];
		
		String response;
		response="raspuns_"+msgFields[0];	//header de raspuns

		//Realizare operatii
		ArrayList<OrarComplet> orarComplet;
		try {
			orarComplet=obtineOrarComplet(cnp, semestru);
		} catch (Exception e) {
			return "eroare#"+e.getMessage();
		}		
		//Raspuns
		response+=MessageParser.DELIMITER.toString()+orarComplet.size();
		for(OrarComplet orar: orarComplet)
			response+=MessageParser.DELIMITER.toString()+MessageParser.getObjectRepresentation(OrarComplet.class, orar, MessageConstants.STRUCTURE_ORAR);	
		
		return response;
	}

	/**
	 * Obtine planificare examene pentru un student.
	 *
	 * @param message the message
	 * @return the string
	 */
	private String providerExameneStudent(String message)
	{
		//Spargere mesaj in componente
		String[] msgFields=MessageParser.splitMessage(message);
		if(msgFields.length!=2)
		{
			debug("Format incorect mesaj: "+message);
			return "error#format_mesaj";
		}
		
		//Realizam fiecare operatie
		String cnp=msgFields[1];
		
		String response;
		response="raspuns_"+msgFields[0];	//header de raspuns

		//Realizare operatii
		ArrayList<Examen> examene;
		try {
			examene = obtineProgramareExamene(cnp);
		} catch (Exception e) {
			return "eroare#"+e.getMessage();
		}
		//Raspuns
		response+=MessageParser.DELIMITER.toString()+examene.size();
		response+=MessageParser.getObjectsRepresentation(Examen.class, examene, MessageConstants.STRUCTURE_EXAMEN);	
		
		return response;
	}

	/**
	 * Verifica daca un cadru preda cursul pentru o disciplina.
	 *	
	 * Format: cadru_pentru_disciplina#cnp_cadru#numar_discipline#cod_disciplina_1#..#cod_desciplina_n
	 *
	 * @param message the message
	 * @return the string
	 */
	private String queryCadruPentruDisciplina(String message)
	{
		//Spargere mesaj in componente
		String[] msgFields=MessageParser.splitMessage(message);
		if(msgFields.length<4)
		{
			debug("Format incorect mesaj: "+message);
			return "error#format_mesaj";
		}
		

		int cod;
		String cnp=msgFields[1];
		int n=Integer.parseInt(msgFields[2]);
		String response="response_"+msgFields[0]+MessageParser.DELIMITER+msgFields[1];
		
		//Realizam fiecare operatie
		for(int i=3;i<n+3;i++)
		{
			debug("Verificam disciplina "+msgFields[i]);
			cod=Integer.parseInt(msgFields[i]);
			boolean res=cadruPentruDisciplina(cod, cnp);
			
			response+=MessageParser.DELIMITER+Boolean.toString(res);
			
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

	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#cadruPentruDisciplina(int, java.lang.String)
	 */
	@Override
	public boolean cadruPentruDisciplina(int codDisciplina, String cnpCadruDidactic) {
		//Pregatim un query in care numaram cate rezultate avem
		String sqlQuery="SELECT count(*) " +
				"FROM "+ Constants.ACTIVITATE_TABLE+" a " +
				"WHERE a.cnp_cadru_didactic=\'"+cnpCadruDidactic+"\' AND a.tip=\'"+Activitate.TipActivitate.Curs+"\' AND a.cod_disciplina=\'"+codDisciplina+"\';";
		
		//Daca nu avem nici un rezultat, inseamna ca acest cadru didactic nu preda la disciplina respectiva cursul
		try {
			if((int)DatabaseConnection.getSingleValueResult(sqlQuery)==0)
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		
		return true;
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

	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#obtineOrarComplet()
	 */
	@Override
	public ArrayList<OrarComplet> obtineOrarComplet() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#obtineOrarComplet(java.lang.String, java.lang.String, int)
	 */
	@Override
	public ArrayList<OrarComplet> obtineOrarComplet(String cnpStudent, int semestru) throws Exception{
		
		//Obtinut dinamic grupa si verificat cnp-ul
		Utilizator user=utilizatorDAO.getUtilizator(cnpStudent);
		if(user==null || user.tip!=Tip.STUDENT)
			throw new Exception("CNP-ul dat nu este al unui student valid!");
		String grupa=user.titlu_grupa;
		if(!user.isContractCompletat())
			throw new Exception("Studentul nu si-a completat contractul de studii.");
		
		//Pregatim anul de studiu, din grupa
		if(grupa==null || grupa.isEmpty())
		{
			System.err.println("Studentul "+cnpStudent+" nu e inregistrat la nici o grupa.");
			throw new Exception("Studentul "+cnpStudent+" nu e inregistrat la nici o grupa.");
		}
		if(grupa.equals("licentiat"))
		{
			System.err.println("Studentul "+cnpStudent+" este licentiat.");
			throw new Exception("Studentul "+cnpStudent+" este licentiat.");
		}
		int anStudiu = grupa.charAt(1) - '0';

		//Pregatim disciplinele urmate prin conectare si comunicare cu Arhiva
		ArrayList<Integer> discipline=new ArrayList<Integer>();
		this.conectareArhiva();
		String raspuns=this.messageToArhiva("solicitare_discipline_urmate#"+cnpStudent+"#"+anStudiu+"#"+semestru);
		String[] fields=MessageParser.splitMessage(raspuns);
		for(int i=1;i<fields.length;i++)
			discipline.add(Integer.parseInt(fields[i]));
		debug("Obtinut disciplinele urmate: "+discipline);
		
		//Obtinem si returnam orarul pentru respectivul student
		return orarCompletDAO.getOrareParticularizat(grupa, discipline);
	}

	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#obtineProgramareExamene(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<Examen> obtineProgramareExamene(String cnpStudent) throws Exception{
		//Obtinut dinamic grupa si verificat cnp-ul
		Utilizator user=utilizatorDAO.getUtilizator(cnpStudent);
		if(user==null || user.tip!=Tip.STUDENT)
			throw new Exception("CNP-ul dat nu este al unui student valid!");
		String grupa=user.titlu_grupa;
		
		//Pregatim anul de studiu, din grupa
		if(grupa==null || grupa.isEmpty())
		{
			System.err.println("Studentul "+cnpStudent+" nu e inregistrat la nici o grupa.");
			throw new Exception("Studentul "+cnpStudent+" nu e inregistrat la nici o grupa.");
		}
		if(grupa.equals("licentiat"))
		{
			System.err.println("Studentul "+cnpStudent+" este licentiat.");
			throw new Exception("Studentul "+cnpStudent+" este licentiat.");
		}
		int anStudiu = grupa.charAt(1) - '0';
		
		
		//Obtinere semestru curent
		int semestru;
		Calendar calendar=new GregorianCalendar();
		if(calendar.get(Calendar.MONTH)>=Calendar.JULY ||
				calendar.get(Calendar.MONTH)<=Calendar.FEBRUARY)
			semestru=1;
		else
			semestru=2;

		//Pregatim disciplinele urmate prin conectare si comunicare cu Arhiva
		ArrayList<Integer> discipline=new ArrayList<Integer>();
		this.conectareArhiva();
		String raspuns=this.messageToArhiva("solicitare_discipline_urmate#"+cnpStudent+"#"+anStudiu+"#"+semestru);
		String[] fields=MessageParser.splitMessage(raspuns);
		for(int i=1;i<fields.length;i++)
			discipline.add(Integer.parseInt(fields[i]));
		debug("Obtinut disciplinele urmate: "+discipline);

		
		//TODO: Obtinere nume cadru didactic 
		
		//Obtinem examenele
		return examenDAO.getExameneParticularizat(discipline, grupa);

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
