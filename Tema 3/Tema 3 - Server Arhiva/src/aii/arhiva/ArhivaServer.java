/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii.arhiva;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import aii.Disciplina;
import aii.NotaCatalog;
import aii.SituatieScolara;
import aii.database.Constants;
import aii.database.DatabaseConnection;
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
		if(structure.header.equalsIgnoreCase("solicitare_note"))
			return providerNoteStudent(message);		
		if(structure.header.equalsIgnoreCase("solicitare_situatie_scolara"))
			return providerSituatieScolaraStudent(message);	
		
		
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

	/**
	 * Obtine notele unui student.
	 *
	 * @param message the message
	 * @return the string
	 */
	private String providerNoteStudent(String message)
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
		String cnp=msgFields[1];
		String response;
		ArrayList<Integer> coduri=new ArrayList<Integer>();
		response="raspuns_"+msgFields[0]+"#"+n;	//header de raspuns
		for(int i=1;i<=n;i++)
		{
			int cod=Integer.parseInt(msgFields[i+2]);
			debug("Obtinem nota "+i+" pentru "+cod);
			coduri.add(cod);
		}
		//Realizare operatii
		ArrayList<Float> note=obtineNoteStudent(cnp, coduri);
		
		//Raspuns
		for(Float nota : note)
			response+=MessageParser.DELIMITER.toString()+String.format("%.2f", nota);	
		
		return response;
	}
	
	/**
	 * Obtine situatia scolara a unui student.
	 *
	 * @param message the message
	 * @return the string
	 */
	private String providerSituatieScolaraStudent(String message)
	{
		//Spargere mesaj in componente
		String[] msgFields=MessageParser.splitMessage(message);
		if(msgFields.length!=3)
		{
			debug("Format incorect mesaj: "+message);
			return "error#format_mesaj";
		}
		
		//Realizam fiecare operatie
		Integer an=Integer.parseInt(msgFields[2]);
		String cnp=msgFields[1];
		String response;
		response="raspuns_"+msgFields[0];	//header de raspuns

		//Realizare operatii
		SituatieScolara situatie=obtineSituatieScolara(cnp, an);
		
		//Raspuns
		response+=MessageParser.DELIMITER.toString()+MessageParser.getObjectRepresentation(SituatieScolara.class, situatie, MessageConstants.STRUCTURE_SITUATIE_SCOLARA);	
		
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
	
	/* (non-Javadoc)
	 * @see aii.arhiva.Arhiva#obtineDisciplineUrmate(java.lang.String)
	 */
	public ArrayList<Integer> obtineDisciplineUrmate(String cnpStudent) {
		/* Pregatire date despre disciplina. */
		Vector<Object[]> results=null;
		String query = "SELECT cod_disciplina " + 
			" FROM " + Constants.CONTRACT_TABLE + " c " +
			" WHERE c.cnp_student=\'" + cnpStudent + "\';";
		try {
			DatabaseConnection.openConnection();
			results=DatabaseConnection.customQueryArray(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		//Construire lista discipline
		ArrayList<Integer> discipline=new ArrayList<Integer>();
		for(int i=0;i<results.size();i++)
		{
			discipline.add((Integer) results.get(i)[0]);
		}
		debug("Obtinute disciplinele din contractul de studiu: "+discipline);
		
		return discipline;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see aii.arhiva.Arhiva#obtineDisciplineUrmate(java.lang.String, int)
	 */
	@Override
	public ArrayList<Integer> obtineDisciplineUrmate(String cnpStudent, int anStudiu) {
		/* Pregatire date despre disciplina. */
		Vector<Object[]> results=null;
		String query = "SELECT cod_disciplina " + 
			" FROM " + Constants.CONTRACT_TABLE + " c " +
			" WHERE c.cnp_student=\'" + cnpStudent + "\'" + 
			" 	AND c.an_studiu=\'" + anStudiu + "\'";

		try {
			DatabaseConnection.openConnection();
			results=DatabaseConnection.customQueryArray(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		//Construire lista discipline
		ArrayList<Integer> discipline=new ArrayList<Integer>();
		for(int i=0;i<results.size();i++)
		{
			discipline.add((Integer) results.get(i)[0]);
		}
		debug("Obtinute disciplinele din contractul de studiu: "+discipline);
		
		return discipline;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see aii.arhiva.Arhiva#obtineDisciplineUrmate(java.lang.String, int, int)
	 */
	@Override
	public ArrayList<Integer> obtineDisciplineUrmate(String cnpStudent, int anStudiu, int semestru) {
		/* Pregatire date despre disciplina. */
		Vector<Object[]> results=null;
		String query = "SELECT cod_disciplina " + 
			" FROM " + Constants.CONTRACT_TABLE + " c, " + Constants.DISCIPLINA_TABLE+" d" +
			" WHERE c.cnp_student=\'" + cnpStudent + "\'" + 
			" 	AND c.an_studiu=\'" + anStudiu + "\'" +
			"	AND d.semestru=\'" + semestru + "\'" +
			"	AND d.cod=c.cod_disciplina";	

		try {
			DatabaseConnection.openConnection();
			results=DatabaseConnection.customQueryArray(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		//Construire lista discipline
		ArrayList<Integer> discipline=new ArrayList<Integer>();
		for(int i=0;i<results.size();i++)
		{
			discipline.add((Integer) results.get(i)[0]);
		}
		debug("Obtinute disciplinele din contractul de studiu pentru "+cnpStudent+": "+discipline);
		
		return discipline;
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
		Vector<Object[]> results=null;
		//Pregatim query-ul
		String from=Constants.CATALOG_TABLE;
		String where="cnp_student=\'"+CNPStudent+"\'" +
				" AND cod_disciplina IN (";
		for(Integer cod : codDisciplina)
			where+="\'"+cod+"\', ";
		where=where.substring(0,where.length()-2);	//stergem virgula
		where+=")";
		
		String query="SELECT nota, cod_disciplina FROM "+from+" WHERE "+where;
		
		//Executam query-ul
		try {
			DatabaseConnection.openConnection();
			results=DatabaseConnection.customQueryArray(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		//Construim un hash-table rezultat
		HashMap<Integer, Float> note=new HashMap<Integer, Float>();
		for(int i=0;i<results.size();i++)
		{
			float value=(Long) results.get(i)[0];
			Integer cod=(Integer) results.get(i)[1];
			
			note.put(cod,value);
		}
		
		//Obtinem disciplinele urmate
		ArrayList<Integer> disciplineUrmate=this.obtineDisciplineUrmate(CNPStudent);
		
		//Construim un array-list cerut
		ArrayList<Float> noteList=new ArrayList<Float>();
		for(int i=0;i<codDisciplina.size();i++)
		{
			if(!disciplineUrmate.contains(codDisciplina.get(i)))
				noteList.add(-2.0f);
			else if(!note.containsKey(codDisciplina.get(i)))
				noteList.add(-1.0f);
			else
				noteList.add(note.get(codDisciplina.get(i)));
		}
		debug("Obtinute notele: "+noteList);
		
		return noteList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see aii.arhiva.Arhiva#obtineSituatieScolara(java.lang.String, int)
	 */
	@Override
	public SituatieScolara obtineSituatieScolara(String CNPStudent, int anStudiu) {
		SituatieScolara situatie=new SituatieScolara();
		
		//Prepare Queries
		//Media aritmetica
		String unprocessedQueryAritmetica="SELECT AVG(IFNULL(nota,0)) " +
				"FROM optiuni_contract o " +
				"INNER JOIN disciplina d " +
				"	ON d.cod=o.cod_disciplina " +
				"LEFT JOIN catalog c " +
				"	ON o.cod_disciplina=c.cod_disciplina AND c.cnp_student=\'%s\'" +
				"WHERE o.cnp_student=\'%s\'";
		
		//Suma ponderata
		String unprocessedQueryGenerala="SELECT SUM(IFNULL(nota,0)*d.puncte_credit) " +
				"FROM optiuni_contract o " +
				"INNER JOIN disciplina d " +
				"	ON d.cod=o.cod_disciplina " +
				"LEFT JOIN catalog c " +
				"	ON o.cod_disciplina=c.cod_disciplina AND c.cnp_student=\'%s\'" +
				"WHERE o.cnp_student=\'%s\'";		
		
		//Numar total de credite
		String unprocessedQueryCreditTotal="SELECT sum(d1.puncte_credit)" +
				" FROM optiuni_contract o, disciplina d1" +
				" WHERE o.cod_disciplina=d1.cod" +
				"	AND o.cnp_student='%s'";
		
		//Numar obtinut
		String unprocessedQueryCreditObtinut="SELECT sum(d1.puncte_credit)" +
				" FROM optiuni_contract o, disciplina d1" +
				" WHERE o.cod_disciplina=d1.cod" +
				"	AND o.cnp_student='%s'" +
				"	AND (SELECT IFNULL(max(nota),0) FROM catalog c" +
				"		WHERE cnp_student='%s'" +
				"			AND cod_disciplina=o.cod_disciplina) >= 5";
		
		//Numar restante
		String unprocessedQueryRestante="SELECT count(*)" +
				" FROM optiuni_contract o, disciplina d1" +
				" WHERE o.cod_disciplina=d1.cod" +
				"		AND o.cnp_student='%s'" +
				"		AND (SELECT IFNULL(max(nota),0) FROM catalog c" +
				"			WHERE cnp_student='%s'" +
				"			AND cod_disciplina=o.cod_disciplina) < 5";
		
		
		if(anStudiu!=0)	//nu e pe toti anii
		{
			unprocessedQueryAritmetica+=" AND o.an_studiu=\'"+anStudiu+"\'";
			unprocessedQueryCreditTotal+=" AND o.an_studiu=\'"+anStudiu+"\'";
			unprocessedQueryCreditObtinut+=" AND o.an_studiu=\'"+anStudiu+"\'";
			unprocessedQueryGenerala+=" AND o.an_studiu=\'"+anStudiu+"\'";
			unprocessedQueryGenerala+=" AND o.an_studiu=\'"+anStudiu+"\'";
		}

		try{
			//Situatia anuala - aritmetica
			String query=String.format(unprocessedQueryAritmetica,CNPStudent,CNPStudent);
			situatie.setMedieAritmetica(DatabaseConnection.getSingleValueResult(query));
			if(anStudiu!=0)
			{		
				//Situatia semestrul 1
				situatie.setMedieSemestru1(DatabaseConnection.getSingleValueResult(query+" AND d.semestru=\'1\'"));
				//Situatia semestrul 2
				situatie.setMedieSemestru1(DatabaseConnection.getSingleValueResult(query+" AND d.semestru=\'2\'"));
			}
			//Situatia anuala - ponderata
			query=String.format(unprocessedQueryGenerala,CNPStudent,CNPStudent);
			String query2=String.format(unprocessedQueryCreditTotal,CNPStudent);
			float suma1=DatabaseConnection.getSingleValueResult(query);
			float suma2=DatabaseConnection.getSingleValueResult(query2);
			situatie.setMedieGenerala(suma1/suma2);
			//Puncte credit obtinute
			query=String.format(unprocessedQueryCreditTotal,CNPStudent);
			situatie.setPuncteCredit((int) DatabaseConnection.getSingleValueResult(query));
			//Restante
			query=String.format(unprocessedQueryRestante,CNPStudent,CNPStudent);
			situatie.setRestante((int) DatabaseConnection.getSingleValueResult(query));
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		
		
		return situatie;
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
