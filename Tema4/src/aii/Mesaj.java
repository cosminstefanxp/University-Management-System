package aii;

import java.util.Date;


// TODO: Auto-generated Javadoc
/**
 * The Class Mesaj.
 */
public class Mesaj {
	
	public Mesaj() {
		super();
		citit=false;
		data=new Date();
	}

	/** The id. */
	public int id;
	
	/** The cnp sursa. */
	public String cnpSursa;
	
	/** The cnp destinatie. */
	public String cnpDestinatie;
	
	/** The subiect. */
	public String subiect;
	
	/** The mesaj. */
	public String mesaj;
	
	/** The data. */
	public Date data;
	
	/** The citit. */
	public boolean citit;
}
