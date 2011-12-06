/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii.protocol;

import aii.protocol.MessageStructure.Type;

/**
 * The Class MessageConstants.
 */
public class MessageConstants {
	
	public static MessageStructure[] structure = {
		new MessageStructure("terminare_comunicatie", 
				Type.EXIT),
		new MessageStructure("solicitare_note", 
				Type.ARHIVA),
		new MessageStructure("stabilire_plan_de_invatamant", 
				Type.ARHIVA)
		

		};
	
	public static final String[] STRUCTURE_DISCIPLINA={"cod", "denumire", "tip", "nrOre",  "pctCredit",     "examinare", "anStudiu",  "semestru", "grup"};
}
