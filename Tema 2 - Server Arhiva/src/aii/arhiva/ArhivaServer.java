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
import java.util.ArrayList;
import java.util.Calendar;

import aii.Disciplina;
import aii.SituatieScolara;

/**
 * The Class ArhivaServer.
 */
public class ArhivaServer implements Arhiva {

	public static final String PUBLISH_NAME="ServiciuArhiva";
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see aii.arhiva.Arhiva#adaugareDisciplina(java.util.ArrayList)
	 */
	@Override
	public int adaugareDisciplina(ArrayList<Disciplina> discipline) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see aii.arhiva.Arhiva#editareDisciplina(java.util.ArrayList)
	 */
	@Override
	public int editareDisciplina(ArrayList<Disciplina> discipline) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see aii.arhiva.Arhiva#obtineNota(int, java.util.ArrayList)
	 */
	@Override
	public ArrayList<Integer> obtineNota(int CNPStudent, ArrayList<Integer> codDisciplina)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see aii.arhiva.Arhiva#obtineSituatieScolara(int)
	 */
	@Override
	public SituatieScolara obtineSituatieScolara(int CNPStudent) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see aii.arhiva.Arhiva#stabilesteNota(int, int, int, int,
	 * java.util.Calendar)
	 */
	@Override
	public boolean stabilesteNota(int CNPCadruDidactic, int codDisciplina, int CNPStudent,
			int nota, Calendar data) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see aii.arhiva.Arhiva#stergereDisciplina(java.util.ArrayList)
	 */
	@Override
	public int stergereDisciplina(ArrayList<Disciplina> discipline) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
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
		
		System.out.println("Initializare completa a serverului pentru "+PUBLISH_NAME);
	}
}
