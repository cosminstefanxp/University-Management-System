/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii.jurnal;

import java.util.ArrayList;

import aii.jurnal.Cerere.Stare;
import aii.jurnal.Cerere.TipCerere;
import aii.protocol.MessageParser;
import aii.protocol.MessageStructure;

public class JurnalServer implements Jurnal {
	
	/** The cereri. */
	public ArrayList<Cerere> cereri;
	
	/**
	 * Instantiates a new jurnal server.
	 */
	public JurnalServer() {
		super();
		cereri=new ArrayList<Cerere>();
	}

	/**
	 * Debug message printer.
	 * 
	 * @param string the string
	 */
	private static void debug(String string) {
		if (JurnalConnectionThread.DEBUG)
			System.out.println("[DEBUG][Thread " + Thread.currentThread().getId() + "] " + string);
	}

	/**
	 * Process message.
	 *
	 * @param message the message
	 * @param structure the structure
	 * @return the string
	 */
	public String processMessage(String message, MessageStructure structure) {
		
		//Facem procesarea header-ului mesajului si trimitem mesajul la componenta corespunzatoare
		if(structure.header.equalsIgnoreCase("schimbare_grupa"))
			return cerereSchimbareGrupa(message);
		if(structure.header.equalsIgnoreCase("rezultat_solicitari"))
			return rezultatSolicitari(message);
		if(structure.header.equalsIgnoreCase("contestare_nota"))
			return cerereContestareNota(message);
		if(structure.header.equalsIgnoreCase("cereri_in_asteptare_cadru_didactic"))
			return solicitariCadruDidactic(message);
		if(structure.header.equalsIgnoreCase("rezolvare_cereri_cadru_didactic"))
			return rezolvareCereriCadruDidactic(message);
		if(structure.header.equalsIgnoreCase("cereri_in_asteptare_secretar"))
			return solicitariSecretar(message);
		if(structure.header.equalsIgnoreCase("rezolvare_cereri_secretar"))
			return rezolvareCereriSecretar(message);
		
		return null;
	}
	
	/**
	 * Cerere schimbare grupa.
	 *
	 * @param message the message
	 * @return the string
	 */
	public String cerereSchimbareGrupa(String message)
	{
		//Spargere mesaj in componente
		String[] msgFields=MessageParser.splitMessage(message);
		if(msgFields.length!=3)
		{
			debug("Format incorect mesaj: "+message);
			return "error#format_mesaj";
		}
		
		//Realizam fiecare operatie
		String cnp=msgFields[1];
		String grupa=msgFields[2];
		
		String response;
		response="raspuns_"+msgFields[0];	//header de raspuns

		//Realizare operatie
		Cerere cerere=new Cerere(cnp, grupa, TipCerere.SCHIMBARE_GRUPA);
		for(Cerere oldCerere : cereri)
			if(oldCerere.cnpStudent.equals(cnp) && oldCerere.tip.equals(TipCerere.SCHIMBARE_GRUPA))
			{
				response+=MessageParser.DELIMITER+"eroare"+MessageParser.DELIMITER+"Deja exista o asemenea cerere"+MessageParser.DELIMITER+oldCerere.id;
				System.err.println("Cerere de schimbare grupa duplicata de la "+cnp+" - "+cerere);
				return response;
			}
		debug("Cerere noua schimbare grupa salvata: "+cerere);
		cereri.add(cerere);
		
		//Raspuns
		response+=MessageParser.DELIMITER.toString()+cerere.id+MessageParser.DELIMITER+grupa;
		
		return response;
	}
	
	/**
	 * Cerere contestare nota.
	 *
	 * @param message the message
	 * @return the string
	 */
	public String cerereContestareNota(String message)
	{
		//Spargere mesaj in componente
		String[] msgFields=MessageParser.splitMessage(message);
		if(msgFields.length!=4)
		{
			debug("Format incorect mesaj: "+message);
			return "error#format_mesaj";
		}
		
		//Realizam fiecare operatie
		String cnpStudent=msgFields[1];
		String cnpProfesor=msgFields[2];
		String codDisciplina=msgFields[3];
		
		String response;
		response="raspuns_"+msgFields[0];	//header de raspuns

		//Realizare operatie
		Cerere cerere=new Cerere(cnpStudent, cnpProfesor, codDisciplina, TipCerere.CONTESTARE_NOTA);
		for(Cerere oldCerere : cereri)
			if(oldCerere.cnpStudent.equals(cnpStudent) && 
					oldCerere.tip.equals(TipCerere.CONTESTARE_NOTA) &&
					oldCerere.field1.equals(cnpProfesor) && 
					oldCerere.field2.equals(codDisciplina))
			{
				response+=MessageParser.DELIMITER+"eroare"+MessageParser.DELIMITER+"Deja exista o asemenea cerere"+MessageParser.DELIMITER+oldCerere.id;
				System.err.println("Cerere de contestare duplicata de la "+cnpStudent+" - "+cerere);
				return response;
			}
		debug("Cerere noua contestare_nota salvata: "+cerere);
		cereri.add(cerere);
		
		//Raspuns
		response+=MessageParser.DELIMITER.toString()+cerere.id+
					MessageParser.DELIMITER+cnpStudent+
					MessageParser.DELIMITER+cnpProfesor+
					MessageParser.DELIMITER+codDisciplina;
		
		return response;
	}
	
	/**
	 * Rezultat solicitari.
	 *
	 * @param message the message
	 * @return the string
	 */
	public String rezultatSolicitari(String message)
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

		//Obtinere cereri pentru student
		ArrayList<Cerere> forUser=new ArrayList<Cerere>();
		ArrayList<Cerere> deSters=new ArrayList<Cerere>();
		for(Cerere cerere : cereri)
			if(cerere.cnpStudent.equals(cnp))
			{
				forUser.add(cerere);
				if(!cerere.stare.equals(Stare.Neanalizata))
					deSters.add(cerere);
			}
		
		//Eliminare cereri
		debug("Eliminam cererile, fiind rezolvate: "+deSters);
		cereri.removeAll(deSters);
		
		//Pregatire mesaj
		response+=MessageParser.DELIMITER.toString()+forUser.size();
		for(Cerere cerere : forUser)
			response+=MessageParser.DELIMITER.toString()+cerere.id+MessageParser.FIELD_DELIMITER+cerere.stare;
			
		return response;
	}
	
	/**
	 * Solicitari cadru didactic.
	 *
	 * @param message the message
	 * @return the string
	 */
	public String solicitariCadruDidactic(String message)
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

		//Obtinere cereri pentru cadru didactic
		ArrayList<Cerere> forUser=new ArrayList<Cerere>();
		for(Cerere cerere : cereri)
			if(cerere.tip.equals(TipCerere.CONTESTARE_NOTA) && cerere.field1.equals(cnp) && cerere.stare.equals(Stare.Neanalizata))
				forUser.add(cerere);
		
		//Pregatire mesaj
		response+=MessageParser.DELIMITER.toString()+forUser.size();
		for(Cerere cerere : forUser)
			response+=MessageParser.DELIMITER.toString() + cerere.id +	//id cerere
				MessageParser.FIELD_DELIMITER + cerere.cnpStudent + 	//cnp student
				MessageParser.FIELD_DELIMITER + cerere.field2;			//cod disciplina
		
		return response;
	}
	
	/**
	 * Rezolvare cereri cadru didactic.
	 *
	 * @param message the message
	 * @return the string
	 */
	public String rezolvareCereriCadruDidactic(String message)
	{
		
		//Spargere mesaj in componente
		String[] msgFields=MessageParser.splitMessage(message);
		if(msgFields.length<3)
		{
			debug("Format incorect mesaj: "+message);
			return "error#format_mesaj";
		}
		
		//Realizam fiecare operatie
		int n=Integer.parseInt(msgFields[1]);
		String response;
		response="raspuns_"+msgFields[0];	//header de raspuns

		//Analiza raspunsuri
		for(int i=2;i<n+2;i++)
		{
			String[] pieces=msgFields[i].split(MessageParser.FIELD_DELIMITER.toString());
			if(pieces.length!=2)
			{
				System.err.println("Mesaj incorect formatat: "+msgFields[i]);
				return "eroare#Format incorect in cadrul rezolvarii pentru cererea "+(i-2);
			}
			int id=Integer.parseInt(pieces[0]);
			int rezultat=Integer.parseInt(pieces[1]);
			for(Cerere cerere : cereri)
				if(cerere.id==id)
				{
					cerere.stare= rezultat==1 ? Stare.Acceptata : rezultat==-1 ? Stare.Respinsa : Stare.Neanalizata;
					debug("Rezolvat cererea: "+cerere);
					if(cerere.stare.equals(Stare.Neanalizata))
							System.err.println("Valoare ilegala pentru cerere. Trecem la status Neanalizat");
				}
		}
		
		return response+"#success";
	}
	
	/**
	 * Solicitari secretar.
	 *
	 * @param message the message
	 * @return the string
	 */
	public String solicitariSecretar(String message)
	{
		//Spargere mesaj in componente
		String[] msgFields=MessageParser.splitMessage(message);
		if(msgFields.length!=2)
		{
			debug("Format incorect mesaj: "+message);
			return "error#format_mesaj";
		}
		
		//Realizam fiecare operatie
		String response;
		response="raspuns_"+msgFields[0];	//header de raspuns

		//Obtinere cereri pentru secretar
		ArrayList<Cerere> forUser=new ArrayList<Cerere>();
		for(Cerere cerere : cereri)
			if(cerere.tip.equals(TipCerere.SCHIMBARE_GRUPA) && cerere.stare.equals(Stare.Neanalizata))
				forUser.add(cerere);
		
		//Pregatire mesaj
		response+=MessageParser.DELIMITER.toString()+forUser.size();
		for(Cerere cerere : forUser)
			response+=MessageParser.DELIMITER.toString() + cerere.id +	//id cerere
				MessageParser.FIELD_DELIMITER + cerere.cnpStudent + 	//cnp student
				MessageParser.FIELD_DELIMITER + cerere.field1;			//grupa noua
		
		return response;
	}
	
	/**
	 * Rezolvare cereri secretar.
	 *
	 * @param message the message
	 * @return the string
	 */
	public String rezolvareCereriSecretar(String message)
	{
		
		//Spargere mesaj in componente
		String[] msgFields=MessageParser.splitMessage(message);
		if(msgFields.length<3)
		{
			debug("Format incorect mesaj: "+message);
			return "error#format_mesaj";
		}
		
		//Realizam fiecare operatie
		int n=Integer.parseInt(msgFields[1]);
		String response;
		response="raspuns_"+msgFields[0];	//header de raspuns

		//Analiza raspunsuri
		for(int i=2;i<n+2;i++)
		{
			String[] pieces=msgFields[i].split(MessageParser.FIELD_DELIMITER.toString());
			if(pieces.length!=2)
			{
				System.err.println("Mesaj incorect formatat: "+msgFields[i]);
				return "eroare#Format incorect in cadrul rezolvarii pentru cererea "+(i-2);
			}
			int id=Integer.parseInt(pieces[0]);
			int rezultat=Integer.parseInt(pieces[1]);
			for(Cerere cerere : cereri)
				if(cerere.id==id)
				{
					cerere.stare= rezultat==1 ? Stare.Acceptata : rezultat==-1 ? Stare.Respinsa : Stare.Neanalizata;
					debug("Rezolvat cererea: "+cerere);
					if(cerere.stare.equals(Stare.Neanalizata))
							System.err.println("Valoare ilegala pentru cerere. Trecem la status Neanalizat");
				}
		}
		
		return response+"#success";
	}
	
}
