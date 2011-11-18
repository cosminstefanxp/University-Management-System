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

import javax.swing.JOptionPane;

import aii.Disciplina;
import aii.NotaCatalog;
import aii.SituatieScolara;
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
	private RegistruActivitatiDidactice radService;
	
	/**
	 * Instantiates a new arhiva server.
	 */
	public ArhivaServer() {
		super();
		try {
			// Prepare the registry to query
			Registry registry = LocateRegistry.getRegistry("localhost");

			// Get the stubs for the remote service
			radService = (RegistruActivitatiDidactice) registry
					.lookup(RegistruActivitatiDidactice.PUBLISH_NAME);

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Eroare la conectarea la componentele remote");
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
	public ArrayList<Integer> obtineNoteStudent(String CNPStudent, ArrayList<Integer> codDisciplina)
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
		if(radService.cadruPentruDisciplina(nota.codDisciplina, cnpCadruDidactic)==false)
			return false;
		
		//TODO: Verificam daca exista deja o nota pentru acel elev la aceasta materie
		
		return notaCatalogDAO.insertNotaCatalog(nota);		
	}



	/* (non-Javadoc)
	 * @see aii.arhiva.Arhiva#stergereNota(aii.NotaCatalog)
	 */
	@Override
	public boolean stergereNota(NotaCatalog nota) throws RemoteException {
		return notaCatalogDAO.deleteNotaCatalog(nota);
	}








	
}
