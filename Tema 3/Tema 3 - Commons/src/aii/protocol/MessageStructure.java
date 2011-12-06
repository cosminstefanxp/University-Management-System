/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii.protocol;


/**
 * The Class MessageStructure.
 */
public class MessageStructure {

	/**
	 * The Enum Type.
	 */
	public enum Type {
		
		/** The EXIT message type. */
		EXIT,
		
		/** The JURNAL. */
		JURNAL,
		
		/** The RAD. */
		RAD,
		
		/** The ARHIVA. */
		ARHIVA
	}
	/** The header. */
	public String header;
	
	
	/** The type. */
	public Type type;


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MessageStructure [header=" + header + ", type=" + type + "]";
	}


	/**
	 * Instantiates a new message structure.
	 *
	 * @param header the header
	 * @param type the type
	 */
	public MessageStructure(String header, Type type) {
		super();
		this.header = header;
		this.type = type;
	}



}
