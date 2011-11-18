/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii.rad;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import aii.Activitate;
import aii.Examen;
import aii.Orar;
import aii.OrarComplet;
import aii.Utilizator;
import aii.Utilizator.Tip;
import aii.database.ActivitateWrapper;
import aii.database.Constants;
import aii.database.DatabaseConnection;
import aii.database.ExamenWrapper;
import aii.database.OrarCompletWrapper;
import aii.database.OrarWrapper;
import aii.database.UtilizatorWrapper;

/**
 * The Class RegistruActivitatiDidacticeServer.
 */
public class RegistruActivitatiDidacticeServer implements RegistruActivitatiDidactice {

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
	

	

	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#obtineOrarComplet(java.lang.String)
	 */
	@Override
	public ArrayList<OrarComplet> obtineOrarComplet(String CNPStudent) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}



	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#obtineProgramareExamene(java.lang.String)
	 */
	@Override
	public ArrayList<Examen> obtineProgramareExamene(String CNPStudent) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}



	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#stabilesteActivitatePredare(java.lang.String, aii.Activitate)
	 */
	@Override
	public boolean stabilesteActivitatePredare(Activitate activitate)
			throws RemoteException {
		return activitateDAO.insertActivitate(activitate);
		
	}



	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#stabilesteFormatieDeStudiu(java.lang.String, java.util.ArrayList, int)
	 */
	@Override
	public int stabilesteFormatieDeStudiu(ArrayList<String> CNPStudent,
			String grupa) throws RemoteException {
		
		//Pregatim o expresie SQL care sa actualizeze grupele studentilor
		String whereClause="cnp IN (";
		for(String cnp : CNPStudent)
		{
			whereClause+="\'"+cnp+"\', ";
		}
		whereClause=whereClause.substring(0,whereClause.length()-2);	//eliminam ultima virgula
		whereClause+=")";
		
		String setClause="titlu_grupa=\'"+grupa+"\'";
		
		try {
			return DatabaseConnection.updateEntitiesCount(Constants.USER_TABLE, setClause, whereClause);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}







	
	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#autentificaUtilizator(java.lang.String, java.lang.String)
	 */
	@Override
	public Utilizator autentificaUtilizator(String CNP, String parola) throws RemoteException {
		Utilizator utilizator;

		//obtine utilizatorul. daca a fost intampinata o eroare, intoarce null
		utilizator=utilizatorDAO.getUtilizator(CNP);
		if(utilizator==null || !utilizator.parola.equals(parola))
			return null;
		
		return utilizator;
	}


	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#autentificaUtilizator(java.lang.String, aii.Utilizator.Tip)
	 */
	@Override
	public boolean autentificaUtilizator(String CNP, Tip permisiuni) throws RemoteException {
		
		Utilizator utilizator;
		//obtine utilizatorul. daca a fost intampinata o eroare, intoarce null
		utilizator=utilizatorDAO.getUtilizator(CNP);
		if(utilizator==null || utilizator.tip!=permisiuni)
			return false;
		return true;
	}


	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// if the security manager hasn't been initialized, initialize it
		if (System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());

		try {
			// initialize a new object
			RegistruActivitatiDidactice serviciu = new RegistruActivitatiDidacticeServer();
			// create a delegate for that created object
			RegistruActivitatiDidactice delegat = (RegistruActivitatiDidactice) UnicastRemoteObject
					.exportObject(serviciu, 0);
			// register the new delegate to the LocalRegistry
			Registry registry = LocateRegistry.getRegistry();
			registry.rebind(PUBLISH_NAME, delegat);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		System.out.println("Initializare completa a serverului pentru " + PUBLISH_NAME);
	}



	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#editareActivitatePredare(aii.Activitate)
	 */
	@Override
	public boolean editareActivitatePredare(Activitate activitate) throws RemoteException {
		return activitateDAO.updateActivitate("id=\'"+activitate.id+"\'",activitate);
	}



	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#obtinereActivitatePredare()
	 */
	@Override
	public ArrayList<Activitate> obtinereActivitatePredare() throws RemoteException {
		return activitateDAO.getActivitati("id=id");	//toate activitatile
	}



	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#stergereActivitatePredare(aii.Activitate)
	 */
	@Override
	public boolean stergereActivitatePredare(Activitate activitate) throws RemoteException {
		return activitateDAO.deleteActivitate(activitate);
	}



	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#obtineUtilizatori(java.lang.String)
	 */
	@Override
	public ArrayList<Utilizator> obtineUtilizatori(String whereClause) throws RemoteException {
		return utilizatorDAO.getUtilizatori(whereClause);
	}



	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#cadruPentruDisciplina(int, java.lang.String)
	 */
	@Override
	public boolean cadruPentruDisciplina(int codDisciplina, String cnpCadruDidactic)
			throws RemoteException {

		//Pregatim un query in care numaram cate rezultate avem
		String sqlQuery="SELECT count(*) " +
				"FROM "+ Constants.ACTIVITATE_TABLE+" a, "+Constants.DISCIPLINA_TABLE+" d " +
				"WHERE a.cnp_cadru_didactic=\'"+cnpCadruDidactic+"\' AND a.tip=\'"+Activitate.TipActivitate.Curs+"\' AND d.cod=a.cod_disciplina AND d.cod=\'"+codDisciplina+"\';";
		
		//Daca nu avem nici un rezultat, inseamna ca acest cadru didactic nu preda la disciplina respectiva cursul
		try {
			if((int)DatabaseConnection.getSingleValueResult(sqlQuery)==0)
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return true;
	}



	/**
	 * Obtinere activitati predare curs cadru.
	 *
	 * @param cnpCadruDidactic the cnp cadru didactic
	 * @return the array list
	 * @throws RemoteException the remote exception
	 */
	@Override
	public ArrayList<Activitate> obtinereActivitatiPredareCursCadru(String cnpCadruDidactic)
			throws RemoteException {
		//Setam un nameMatch diferit pentru a adauga campul optional de denumire in activitate
		activitateDAO.setNameMatch(Constants.ACTIVITATE_FIELD_MATCH_SHORT);
		//Obtinem activitatile de predare la care cadrul didactic este titular (inclusiv numele disciplinei)
		ArrayList<Activitate> activitati=activitateDAO.getActivitatiJoined("a.id, a.cod_disciplina, d.denumire",
				Constants.ACTIVITATE_TABLE+" a, "+Constants.DISCIPLINA_TABLE+" d",
				"a.cnp_cadru_didactic='"+cnpCadruDidactic+"\' AND a.tip=\'"+Activitate.TipActivitate.Curs+"\' AND d.cod=a.cod_disciplina");
		//Revenim la nameMatch-ul normal
		activitateDAO.setNameMatch(Constants.ACTIVITATE_FIELD_MATCH);
		
		return activitati;
	}


	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#adaugareActivitateOrar(aii.Orar)
	 */
	@Override
	public boolean adaugareActivitateOrar(Orar orar) throws RemoteException {
		return orarDAO.insertOrar(orar);
		
	}


	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#editareActivitateOrar(aii.Orar, aii.Orar)
	 */
	@Override
	public boolean editareActivitateOrar(Orar orarNou, Orar orarVechi) throws RemoteException {
		return orarDAO.updateOrar(orarVechi, orarNou);
	}


	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#stergereActivitateOrar(aii.Orar)
	 */
	@Override
	public boolean stergereActivitateOrar(Orar orar) throws RemoteException {
		return orarDAO.deleteOrar(orar);
	}


	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#obtineOrarComplet()
	 */
	@Override
	public ArrayList<OrarComplet> obtineOrarComplet() throws RemoteException {
		
		ArrayList<OrarComplet> orare=orarCompletDAO.getOrare("o.zi, o.ora, o.sala, o.grupa, o.frecventa, o.durata, o.id_activitate, a.tip, a.cod_disciplina, d.denumire, u.cnp, concat(u.nume,concat(' ',u.prenume)) nume",
				Constants.DISCIPLINA_TABLE+" d, "+Constants.ORAR_TABLE+" o, "+Constants.ACTIVITATE_TABLE+" a, "+Constants.USER_TABLE+" u",
				"o.id_activitate=a.id AND a.cod_disciplina=d.cod AND u.cnp=a.cnp_cadru_didactic");
		
		return orare;
	}


	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#obtineGrupeStudenti()
	 */
	@Override
	public Vector<String> obtineGrupeStudenti() throws RemoteException{
		/* Pregatire date despre grupa. */
		Vector<Object[]> results=null;
		String query="SELECT DISTINCT titlu_grupa FROM "+Constants.USER_TABLE+" WHERE tip=\'"+Utilizator.Tip.STUDENT.toString()+"\'";
		
		try {
			DatabaseConnection.openConnection();
			results=DatabaseConnection.customQueryArray(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		//Construire grupe
		Vector<String> grupe=new Vector<String>(results.size());
		for(int i=0;i<results.size();i++)
		{
			String value=(String) results.get(i)[0];
			if(!value.isEmpty())	//Skip empty values
				grupe.add(value);
		}
		System.out.println("Obtinute grupele: "+grupe);
		
		return grupe;
	}



	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#adaugareExamen(aii.Examen)
	 */
	@Override
	public boolean adaugareExamen(Examen examen) throws RemoteException {
		return examenDAO.insertExamen(examen);
	}

	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#editareExamen(aii.Examen, aii.Examen)
	 */
	@Override
	public boolean editareExamen(Examen examenNou, Examen examenVechi) throws RemoteException {
		return examenDAO.updateExamen(examenVechi, examenNou);
	}

	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#stergereExamen(aii.Examen)
	 */
	@Override
	public boolean stergereExamen(Examen examen) throws RemoteException {
		return examenDAO.deleteExamen(examen);
	}

	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#obtineExamene()
	 */
	@Override
	public ArrayList<Examen> obtineExamene() throws RemoteException {
		return examenDAO.getExamene("true");
	}

}
