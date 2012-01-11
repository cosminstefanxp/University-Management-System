package aii;

import java.sql.Date;


// TODO: Auto-generated Javadoc
/**
 * The Class Mesaj.
 */
public class Mesaj {
	
	public Mesaj() {
		super();
		citit=false;
		data=new Date(new java.util.Date().getTime());
	}

	/** The id. */
	public int id;
	
	/** The cnp sursa. */
	public String cnpSursa;
	
	/** The cnp destinatie. */
	public String cnpDestinatie;
	
	/** The nume destinatie. */
	public String numeSursa;
	
	/** The subiect. */
	public String subiect;
	
	/** The mesaj. */
	public String mesaj;
	
	/** The data. */
	public Date data;
	
	/** The citit. */
	public boolean citit;
}
