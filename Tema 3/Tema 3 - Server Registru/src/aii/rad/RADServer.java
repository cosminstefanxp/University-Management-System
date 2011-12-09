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
import aii.Disciplina;
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
		if(structure.header.equalsIgnoreCase("stabilire_formatie_de_studiu"))
			return managementFormatieStudiu(message);
		if(structure.header.equalsIgnoreCase("stabilire_orar"))
			return managementOrar(message);
		if(structure.header.equalsIgnoreCase("stabilire_calendar_examene"))
			return managementExamene(message);
		//TODO - sa verifice cat a editat la examene
				
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
	 * Realizeaza taskurile de management asupre unei/mai multor examene.
	 *
	 * @param message the message
	 * @return the string
	 */
	private String managementExamene(String message)
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
		for(int i=1;i<=n;i++)
		{
			String operatie=msgFields[2*i];
			boolean res=true;
			debug("Analizam operatia "+(2*i)+":"+operatie);
			
			//Realizare operatii
			if(operatie.equals("stergere"))
			{
				String[] delFields=msgFields[2*i+1].split(MessageParser.FIELD_DELIMITER.toString());
				if(delFields.length!=2)
				{
					debug("Format incorect stergere: "+message);
					return "error#format_mesaj_stergere";
				}
				res=examenDAO.deleteExamen("cod_disciplina=\'"+delFields[0]+"\' AND grupa=\'"+delFields[1]+"\'");
			}
			else 
			{
				Examen examen=MessageParser.parseObject(Examen.class, msgFields[2*i+1], MessageConstants.STRUCTURE_EXAMEN);
				if(operatie.equals("adaugare"))
					res=examenDAO.insertExamen(examen);
				else if(operatie.equals("editare"))
					res=examenDAO.updateExamen(examen, examen);
					//Sectiunea de update va folosi doar campurile din cheia primara, deci e ok daca la obiect vechi dam
					//noul obiect, pentru ca va face cautarea doar dupa cod_disciplina si grupa
				else 
					res=false;
			}
			//Raspuns
			response+=MessageParser.DELIMITER.toString()+Boolean.toString(res);
		}
		
		
		return response;
	}
	
	/**
	 * Realizeaza taskurile de management asupre unei/mai multor intrari in orar.
	 *
	 * @param message the message
	 * @return the string
	 */
	private String managementOrar(String message)
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
			debug("Analizam intrare in orar "+msgFields[i]);
			
			//Realizare operatii
			Orar orar=MessageParser.parseObject(Orar.class, msgFields[i], MessageConstants.STRUCTURE_ORAR_SIMPLU);
			res=this.adaugareActivitateOrar(orar);

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
	
	/**
	 * Management pentru grupe studenti.
	 *
	 * @param message the message
	 * @return the string
	 */
	private String managementFormatieStudiu(String message)
	{
		//Spargere mesaj in componente
		String[] msgFields=MessageParser.splitMessage(message);
		if(msgFields.length<4)
		{
			debug("Format incorect mesaj: "+message);
			return "error#format_mesaj";
		}
		
		//Realizam fiecare operatie
		Integer n=Integer.parseInt(msgFields[2]);
		String grupa=msgFields[1];
		String response;
		response="raspuns_"+msgFields[0];	//header de raspuns
		//Obtinem CNP-urile
		ArrayList<String> cnps=new ArrayList<String>();
		for(int i=3;i<n+3;i++)
		{
			cnps.add(msgFields[i]);
		}
		
		//Realizare operatie
		debug("Schimbam formatia de studiu in "+grupa + " pentru "+cnps);
		response+=MessageParser.DELIMITER.toString() + stabilesteFormatieDeStudiu(cnps, grupa);
		
		return response;
	}
	
	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#adaugareActivitateOrar(aii.Orar)
	 */
	@Override
	public boolean adaugareActivitateOrar(Orar orar) {
		return orarDAO.insertOrar(orar);
	}

	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#adaugareExamen(aii.Examen)
	 */
	@Override
	public boolean adaugareExamen(Examen examen){
		return examenDAO.insertExamen(examen);
	}

	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#editareExamen(aii.Examen, aii.Examen)
	 */
	@Override
	public boolean editareExamen(Examen examenNou, Examen examenVechi){
		return examenDAO.updateExamen(examenVechi, examenNou);
	}

	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#stergereExamen(aii.Examen)
	 */
	@Override
	public boolean stergereExamen(Examen examen){
		return examenDAO.deleteExamen(examen);
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

	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#stabilesteFormatieDeStudiu(java.util.ArrayList, java.lang.String)
	 */
	@Override
	public int stabilesteFormatieDeStudiu(ArrayList<String> CNPStudent, String grupa) {
		//Pregatim o expresie SQL care sa actualizeze grupele studentilor
		String whereClause="cnp IN (";
		for(String cnp : CNPStudent)
		{
			whereClause+="\'"+cnp+"\', ";
		}
		whereClause=whereClause.substring(0,whereClause.length()-2);	//eliminam ultima virgula
		whereClause+=") AND tip=\'STUDENT\'";
		
		String setClause="titlu_grupa=\'"+grupa+"\'";
		
		try {
			return DatabaseConnection.updateEntitiesCount(Constants.USER_TABLE, setClause, whereClause);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
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

}
