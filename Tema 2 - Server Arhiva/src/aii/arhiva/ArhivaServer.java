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
import aii.database.DisciplinaWrapper;
import aii.database.NotaCatalogWrapper;
import aii.database.OptiuneContractWrapper;

/**
 * The Class ArhivaServer.
 */
public class ArhivaServer implements Arhiva {

	static DisciplinaWrapper disciplinaDAO=new DisciplinaWrapper();
	static NotaCatalogWrapper notaCatalogDAO=new NotaCatalogWrapper();
	static OptiuneContractWrapper optiuneContractDAO=new OptiuneContractWrapper();
	
	/* (non-Javadoc)
	 * @see aii.arhiva.Arhiva#adaugareDisciplina(java.lang.String, java.util.ArrayList)
	 */
	@Override
	public int adaugareDisciplina(String CNPCadruDidactic, ArrayList<Disciplina> discipline)
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
	public int editareDisciplina(String CNPCadruDidactic, ArrayList<Disciplina> discipline)
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
	public int stergereDisciplina(String CNPCadruDidactic, ArrayList<Disciplina> discipline)
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
	public ArrayList<Integer> obtineNota(String CNPStudent, ArrayList<Integer> codDisciplina)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}



	/* (non-Javadoc)
	 * @see aii.arhiva.Arhiva#obtineSituatieScolara(java.lang.String)
	 */
	@Override
	public SituatieScolara obtineSituatieScolara(String CNPStudent) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}



	/* (non-Javadoc)
	 * @see aii.arhiva.Arhiva#stabilesteNota(java.lang.String, int, java.lang.String, int, java.util.Calendar)
	 */
	@Override
	public boolean stabilesteNota(String CNPCadruDidactic, int codDisciplina, String CNPStudent,
			int nota, Calendar data) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
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
