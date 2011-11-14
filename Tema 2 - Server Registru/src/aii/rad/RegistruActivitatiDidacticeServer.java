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

import aii.Activitate;
import aii.Examen;
import aii.Orar;
import aii.OrarComplet;

/**
 * The Class RegistruActivitatiDidacticeServer.
 */
public class RegistruActivitatiDidacticeServer implements RegistruActivitatiDidactice {

	/** The Constant PUBLISH_NAME. */
	public static final String PUBLISH_NAME = "RADServiciu";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * aii.rad.RegistruActivitatiDidactice#adaugareActivitateOrar(java.util.
	 * ArrayList)
	 */
	@Override
	public int adaugareActivitateOrar(ArrayList<Orar> activitatiDidactice) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * aii.rad.RegistruActivitatiDidactice#adaugareExamen(java.util.ArrayList)
	 */
	@Override
	public int adaugareExamen(ArrayList<Examen> examene) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * aii.rad.RegistruActivitatiDidactice#editareActivitateOrar(java.util.ArrayList
	 * )
	 */
	@Override
	public int editareActivitateOrar(ArrayList<Orar> activitatiDidactice) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * aii.rad.RegistruActivitatiDidactice#editareExamen(java.util.ArrayList)
	 */
	@Override
	public int editareExamen(ArrayList<Examen> examene) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see aii.rad.RegistruActivitatiDidactice#obtineOrarComplet(int)
	 */
	@Override
	public ArrayList<OrarComplet> obtineOrarComplet(int CNPStudent) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see aii.rad.RegistruActivitatiDidactice#obtineProgramareExamene(int)
	 */
	@Override
	public ArrayList<Examen> obtineProgramareExamene(int CNPStudent) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeaii.rad.RegistruActivitatiDidactice#stabilesteActivitatePredare(aii.
	 * Activitate)
	 */
	@Override
	public boolean stabilesteActivitatePredare(Activitate activitate) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * aii.rad.RegistruActivitatiDidactice#stabilesteFormatieDeStudiu(java.util
	 * .ArrayList, int)
	 */
	@Override
	public int stabilesteFormatieDeStudiu(ArrayList<String> CNPStudent, int grupa)
			throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * aii.rad.RegistruActivitatiDidactice#stergereActivitateOrar(java.util.
	 * ArrayList)
	 */
	@Override
	public int stergereActivitateOrar(ArrayList<Orar> activitatiDidactice) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * aii.rad.RegistruActivitatiDidactice#stergereExamen(java.util.ArrayList)
	 */
	@Override
	public int stergereExamen(ArrayList<Examen> examene) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
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
