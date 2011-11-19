/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii.arhiva;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
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
import aii.rad.RegistruActivitatiDidactice;

/**
 * The Class ArhivaServer.
 */
public class ArhivaServer implements Arhiva{

	/** The disciplina dao. */
	static DisciplinaWrapper disciplinaDAO=new DisciplinaWrapper();
	
	/** The nota catalog dao. */
	static NotaCatalogWrapper notaCatalogDAO=new NotaCatalogWrapper();
	
	/** The optiune contract dao. */
	static OptiuneContractWrapper optiuneContractDAO=new OptiuneContractWrapper();
	
	/** The rad service. */
	private RegistruActivitatiDidactice radService=null;
	
	/**
	 * Instantiates a new arhiva server.
	 */
	public ArhivaServer() {
		super();
	}
	
	/**
	 * Pregateste rad service.
	 */
	private void pregatesteRADService()
	{
		System.out.println("Initializam serviciul RegistruActivitatiDidactice");
		try {
			// Prepare the registry to query
			Registry registry = LocateRegistry.getRegistry("localhost");

			// Get the stubs for the remote service
			radService = (RegistruActivitatiDidactice) registry
					.lookup(RegistruActivitatiDidactice.PUBLISH_NAME);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Eroare la conectarea la componentele remote");
		}
	}
	
	/* (non-Javadoc)
	 * @see aii.arhiva.Arhiva#adaugareDisciplina(java.lang.String, java.util.ArrayList)
	 */
	@Override
	public int adaugareDisciplina(ArrayList<Disciplina> discipline)
			throws RemoteException {
		
		int count=0;
		boolean success;
		//introducem fiecare disciplina in baza de date si numaram cate insertii au avut succes
		for(Disciplina disciplina : discipline)
		{
			success=disciplinaDAO.insertDisciplina(disciplina);
			if(success)
				count++;
		}
		return count;
	}



	/* (non-Javadoc)
	 * @see aii.arhiva.Arhiva#editareDisciplina(java.lang.String, java.util.ArrayList)
	 */
	@Override
	public int editareDisciplina(ArrayList<Disciplina> discipline)
			throws RemoteException {
		
		int count=0;
		boolean success;
		//editam fiecare disciplina in baza de date si numaram cate editari au avut succes
		for(Disciplina disciplina : discipline)
		{
			success=disciplinaDAO.updateDisciplina("cod=\'"+disciplina.cod+"\'", disciplina);
			if(success)
				count++;
		}
		return count;
	}
	
	/* (non-Javadoc)
	 * @see aii.arhiva.Arhiva#stergereDisciplina(java.lang.String, java.util.ArrayList)
	 */
	@Override
	public int stergereDisciplina(ArrayList<Disciplina> discipline)
			throws RemoteException {
		
		int count=0;
		boolean success;
		//stergem fiecare disciplina din baza de date si numaram cate stergeri au avut succes
		for(Disciplina disciplina : discipline)
		{
			success=disciplinaDAO.deleteDisciplina(disciplina);
			if(success)
				count++;
		}
		return count;
	}


	/* (non-Javadoc)
	 * @see aii.arhiva.Arhiva#obtineNota(java.lang.String, java.util.ArrayList)
	 */
	@Override
	public ArrayList<Float> obtineNoteStudent(String CNPStudent, ArrayList<Integer> codDisciplina)
			throws RemoteException {
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
		
		//Construim un array-list cerut
		ArrayList<Float> noteList=new ArrayList<Float>();
		for(int i=0;i<codDisciplina.size();i++)
		{
			if(!note.containsKey(codDisciplina.get(i)))
				noteList.add(-1.0f);
			else
				noteList.add(note.get(codDisciplina.get(i)));
		}
		System.out.println("Obtinute notele: "+noteList);
		
		return noteList;
	}



	/* (non-Javadoc)
	 * @see aii.arhiva.Arhiva#obtineSituatieScolara(java.lang.String)
	 */
	@Override
	public SituatieScolara obtineSituatieScolara(String CNPStudent, int anStudiu) throws RemoteException {
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
	 * @see aii.arhiva.Arhiva#adaugareDisciplina(aii.Disciplina)
	 */
	@Override
	public boolean adaugareDisciplina(Disciplina disciplina) throws RemoteException {
		return disciplinaDAO.insertDisciplina(disciplina);
	}



	/* (non-Javadoc)
	 * @see aii.arhiva.Arhiva#editareDisciplina(aii.Disciplina)
	 */
	@Override
	public boolean editareDisciplina(Disciplina disciplina) throws RemoteException {
		return disciplinaDAO.updateDisciplina("cod=\'"+disciplina.cod+"\'", disciplina);
	}



	/* (non-Javadoc)
	 * @see aii.arhiva.Arhiva#stergereDisciplina(aii.Disciplina)
	 */
	@Override
	public boolean stergereDisciplina(Disciplina disciplina) throws RemoteException {
		return disciplinaDAO.deleteDisciplina(disciplina);
	}


	/* (non-Javadoc)
	 * @see aii.arhiva.Arhiva#obtineDiscipline()
	 */
	@Override
	public ArrayList<Disciplina> obtineDiscipline() throws RemoteException {
		return disciplinaDAO.getDiscipline("cod=cod");	//toate disciplinele
	}

	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		//if the security manager hasn't been initialized, initialize it
		if (System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());

		try {
			//initialize a new object
			Arhiva serviciu = new ArhivaServer();
			//create a delegate for that created object
			Arhiva delegat = (Arhiva) UnicastRemoteObject.exportObject(serviciu, 0);
			//register the new delegate to the LocalRegistry
			Registry registry = LocateRegistry.getRegistry();
			registry.rebind(PUBLISH_NAME, delegat);
		} catch (Exception e) {
			e.printStackTrace();
			return ;
		}
		
		//Pregatim serviciul RegistruActivitatiDidactice
		
		
		System.out.println("Initializare completa a serverului pentru "+PUBLISH_NAME);
	}



	/* (non-Javadoc)
	 * @see aii.arhiva.Arhiva#obtineNote()
	 */
	@Override
	public ArrayList<NotaCatalog> obtineNote() throws RemoteException {
		return notaCatalogDAO.getNoteCatalog("true");	//toate notele din catalog
	}



	/* (non-Javadoc)
	 * @see aii.arhiva.Arhiva#stabilesteNota(java.lang.String, aii.NotaCatalog)
	 */
	@Override
	public boolean stabilesteNota(String cnpCadruDidactic, NotaCatalog nota) throws RemoteException {
		//Verificam daca acest cadru preda la materia respectiva cursul
		if(radService==null)
			pregatesteRADService();
		if(radService.cadruPentruDisciplina(nota.getCodDisciplina(), cnpCadruDidactic)==false)
			return false;
		
		//Verificam daca exista deja o nota pentru acel elev la aceasta materie
		NotaCatalog notaExistentaMaxima=notaCatalogDAO.getNotaCatalog(nota.codDisciplina, nota.cnpStudent);
		
		//Daca nu exista nota, o inseram
		if(notaExistentaMaxima==null)
			return notaCatalogDAO.insertNotaCatalog(nota);
		//Daca exista dar era mai mica sau era restanta, o actualizam
		else if(notaExistentaMaxima.nota<5 || notaExistentaMaxima.nota<=nota.nota)
			return notaCatalogDAO.updateNotaCatalog(notaExistentaMaxima, nota);

		//Altfel nu facem nimic
		return false;
			
	}



	/* (non-Javadoc)
	 * @see aii.arhiva.Arhiva#stergereNota(aii.NotaCatalog)
	 */
	@Override
	public boolean stergereNota(NotaCatalog nota) throws RemoteException {
		return notaCatalogDAO.deleteNotaCatalog(nota);
	}

	/* (non-Javadoc)
	 * @see aii.arhiva.Arhiva#obtineDisciplineUrmate(java.lang.String, int)
	 */
	@Override
	public ArrayList<Integer> obtineDisciplineUrmate(String cnpStudent, int anStudiu)
			throws RemoteException {
		
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
		System.out.println("Obtinute disciplinele din contractul de studiu: "+discipline);
		
		return discipline;
	}

	/* (non-Javadoc)
	 * @see aii.arhiva.Arhiva#obtineDisciplineUrmate(java.lang.String, int, int)
	 */
	@Override
	public ArrayList<Integer> obtineDisciplineUrmate(String cnpStudent, int anStudiu, int semestru)
			throws RemoteException {
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
		System.out.println("Obtinute disciplinele din contractul de studiu: "+discipline);
		
		return discipline;
	}








	
}
