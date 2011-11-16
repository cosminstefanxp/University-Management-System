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
import java.util.ArrayList;

import javax.imageio.IIOException;

import aii.Activitate;
import aii.Examen;
import aii.Orar;
import aii.OrarComplet;
import aii.Utilizator;
import aii.Utilizator.Tip;
import aii.database.ActivitateWrapper;
import aii.database.ExamenWrapper;
import aii.database.OrarWrapper;
import aii.database.UtilizatorWrapper;

/**
 * The Class RegistruActivitatiDidacticeServer.
 */
public class RegistruActivitatiDidacticeServer implements RegistruActivitatiDidactice {

	private static UtilizatorWrapper utilizatorDAO=new UtilizatorWrapper();
	private static OrarWrapper orarDAO=new OrarWrapper();
	private static ActivitateWrapper activitateDAO=new ActivitateWrapper();
	private static ExamenWrapper examenDAO=new ExamenWrapper();
	


	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#adaugareActivitateOrar(java.lang.String, java.util.ArrayList)
	 */
	@Override
	public int adaugareActivitateOrar(String cnpSecretar, ArrayList<Orar> activitatiDidactice)
			throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}



	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#adaugareExamen(java.lang.String, java.util.ArrayList)
	 */
	@Override
	public int adaugareExamen(String cnpSecretar, ArrayList<Examen> examene) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}



	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#editareActivitateOrar(java.lang.String, java.util.ArrayList)
	 */
	@Override
	public int editareActivitateOrar(String cnpSecretar, ArrayList<Orar> activitatiDidactice)
			throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}



	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#editareExamen(java.lang.String, java.util.ArrayList)
	 */
	@Override
	public int editareExamen(String cnpSecretar, ArrayList<Examen> examene) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}



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
	public boolean stabilesteActivitatePredare(String cnpSefCatedra, Activitate activitate)
			throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}



	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#stabilesteFormatieDeStudiu(java.lang.String, java.util.ArrayList, int)
	 */
	@Override
	public int stabilesteFormatieDeStudiu(String cnpSecretar, ArrayList<String> CNPStudent,
			int grupa) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}



	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#stergereActivitateOrar(java.lang.String, java.util.ArrayList)
	 */
	@Override
	public int stergereActivitateOrar(String cnpSecretar, ArrayList<Orar> activitatiDidactice)
			throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}



	/* (non-Javadoc)
	 * @see aii.rad.RegistruActivitatiDidactice#stergereExamen(java.lang.String, java.util.ArrayList)
	 */
	@Override
	public int stergereExamen(String cnpSecretar, ArrayList<Examen> examene) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
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





}
