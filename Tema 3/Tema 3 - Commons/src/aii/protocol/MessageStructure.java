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
		ARHIVA,
		
		/** The CLIENT. */
		CLIENT
	}
	
	/**
	 * The Enum Sender.
	 */
	public enum Sender {
		STUDENT,
		CADRU_DIDACTIC,
		SEF_CATEDRA,
		SECRETAR,
		ANY,
		SERVER
	}
	/** The header. */
	public String header;
	
	
	/** The type. */
	public Type type;
	
	/** The sender. */
	public Sender sender;


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
	public MessageStructure(String header, Type type, Sender sender) {
		super();
		this.header = header;
		this.type = type;
		this.sender = sender;
	}



}
