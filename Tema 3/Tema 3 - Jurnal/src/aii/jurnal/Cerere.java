/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii.jurnal;

/**
 * The Class Cerere.
 */
public class Cerere {

	/**
	 * The Enum TipCerere.
	 */
	public enum TipCerere {
		
		/** The CONTESTARE NOTA. */
		CONTESTARE_NOTA,
		
		/** The SCHIMBARE GRUPA. */
		SCHIMBARE_GRUPA
	};
	
	/**
	 * The Enum Stare.
	 */
	public enum Stare {
		/** The Neanalizata. */
		Neanalizata,
		/** The Acceptata. */
		Acceptata,
		/** The Respinsa. */
		Respinsa
	};
	
	/** The last id that has been given to a request. */
	public static int lastID=0;	
	
	/** The id. */
	public int id;
	
	/** The cnp student. */
	public String cnpStudent;
	
	/** The field1. */
	public String field1;
	
	/** The field2. */
	public String field2;
	
	/** The tip. */
	public TipCerere tip;
	
	/** The stare. */
	public Stare stare;

	/**
	 * Instantiates a new cerere.
	 *
	 * @param cnpStudent the cnp student
	 * @param field1 the field1
	 * @param field2 the field2
	 * @param tip the tip
	 */
	public Cerere(String cnpStudent, String field1, String field2, TipCerere tip) {
		super();
		this.id=lastID++;
		this.cnpStudent = cnpStudent;
		this.field1 = field1;
		this.field2 = field2;
		this.tip=tip;
		this.stare=Stare.Neanalizata;
	}

	/**
	 * Instantiates a new cerere.
	 *
	 * @param cnpStudent the cnp student
	 * @param field1 the field1
	 * @param tip the tip
	 */
	public Cerere(String cnpStudent, String field1, TipCerere tip) {
		super();
		this.id=lastID++;
		this.cnpStudent = cnpStudent;
		this.field1 = field1;
		this.tip=tip;
		this.stare=Stare.Neanalizata;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cerere other = (Cerere) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Cerere [cnpStudent=" + cnpStudent + ", field1=" + field1 + ", field2=" + field2 + ", id="
				+ id + ", stare=" + stare + ", tip=" + tip + "]";
	}
	
	
}
