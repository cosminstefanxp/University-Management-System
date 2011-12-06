/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii.protocol;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import aii.Disciplina;
import aii.Disciplina.Examinare;
import aii.Disciplina.TipDisciplina;

/**
 * The Class MessageParser.
 */
public class MessageParser{

	/** The Constant delimiter. */
	public final static Character DELIMITER='#';
	
	/** The Constant fieldDelimiter. */
	public final static Character FIELD_DELIMITER='~';
	
	/** The Constant structures. */
	public final static List<MessageStructure> structures=Arrays.asList(MessageConstants.structure);
	
	/**
	 * Gets the message structure corresponding to a given message.
	 *
	 * @param message the message
	 * @return the message structure
	 */
	public static MessageStructure getMessageStructure(String message)
	{
		String messageHeader;
		int firstDelim=message.indexOf(DELIMITER);
		if(firstDelim>=0)
			messageHeader=message.substring(0,firstDelim);
		else
			messageHeader=message;
		
		for(MessageStructure struct : structures)
		{
			if(struct.header.equals(messageHeader))
				return struct;
		}
		
		return null;
	}
	
	/**
	 * Gets the object representation.
	 *
	 * @param <T> the generic type
	 * @param classType the class type
	 * @param object the object
	 * @param fieldMatch the field match
	 * @return the object representation
	 */
	public static <T> String getObjectRepresentation(Class<T> classType, T object, String[] fieldMatch)
	{
		String representation="";
		
		try {
			
			// For every field in the nameMatch[0] array of object field names,
			// we write it in the string
			for (int i = 0; i < fieldMatch.length; i++) {
				// Prepare the field of the object which we are now getting info from
				Field field = classType.getDeclaredField(fieldMatch[i]);

				// Get the field value
				Object value = field.get(object);

				//Add it to the representation
				if (value != null)
					representation += value.toString()+FIELD_DELIMITER;
				else
					representation += FIELD_DELIMITER;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return "ERROR";
		}
		
		//Remove the last delimiter
		representation=representation.substring(0,representation.length()-1);
		
		return representation;	
	}
	
	/**
	 * Gets the representation for multiple objects.
	 *
	 * @param <T> the generic type
	 * @param classType the class type
	 * @param objects the objects
	 * @param fieldMatch the field match
	 * @return the objects representation
	 */
	public static <T> String getObjectsRepresentation(Class<T> classType, List<T> objects, String[] fieldMatch)
	{
		String representation="";
		
		for(T object:objects)
			representation+=getObjectRepresentation(classType, object, fieldMatch)+DELIMITER;
		
		return representation;
	}
	
	/**
	 * Parses the object.
	 *
	 * @param <T> the generic type
	 * @param classType the class type
	 * @param representation the representation
	 * @param fieldMatch the field match
	 * @return the object
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static <T> T parseObject(Class<T> classType, String representation, String[] fieldMatch)
	{
		T instance;
		
		//Break the representation
		String[] fields=representation.split(FIELD_DELIMITER.toString());

		try {
			//Create a new instance of the class, that will be populated with information
			instance=(T) classType.newInstance();
			
			//For every field in the fieldMatch array of object field names, we get the value from the representation
			for (int i = 0; i < fieldMatch.length; i++) {
				// Prepare the field of the object which we are now filling
				Field field = classType.getDeclaredField(fieldMatch[i]);

				if (field.getType() == java.sql.Date.class)
					field.set(instance, Date.parse(fields[i]));
				
				else if (field.getType() == int.class)
					field.setInt(instance, Integer.parseInt(fields[i]));
				
				else if (field.getType() == double.class)
					field.setDouble(instance, Double.parseDouble(fields[i]));
				
				else if (field.getType() == float.class)
					field.setFloat(instance, Float.parseFloat(fields[i]));

				
				else if (field.getType().isEnum()) {
					String enumValue = (String) fields[i];
					if (enumValue == null || enumValue.equalsIgnoreCase("null"))
						continue;
					field.set(instance, Enum.valueOf((Class<Enum>) field.getType(), enumValue));
				} else
					field.set(instance, fields[i]);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		
		return instance;
	}
	
	/**
	 * Parses multiple objects from a string representation. The number of objects should be count.
	 *
	 * @param <T> the generic type
	 * @param classType the class type
	 * @param representation the string representation
	 * @param fieldMatch the field match
	 * @param count the count
	 * @return the array list
	 */
	public static <T> ArrayList<T> parseObjects(Class<T> classType, String representation, String[] fieldMatch, int count)
	{
		//Split the representation
		String[] objectsRepresentation=representation.split(DELIMITER.toString());
		
		//Get the objects
		ArrayList<T> objects=new ArrayList<T>();
		assert(count==objectsRepresentation.length);
		for(int i=0;i<count;i++)
			objects.add(parseObject(classType, objectsRepresentation[i], fieldMatch));
		
		return objects;
	}
	
	public static void parseTest()
	{
		Disciplina disciplina=new Disciplina();
		disciplina.anStudiu=2;
		disciplina.cod=12;
		disciplina.denumire="dasd";
		disciplina.examinare=Examinare.Examen;
		disciplina.grup=3;
		disciplina.nrOre=2;
		disciplina.pctCredit=4;
		disciplina.semestru=1;
		disciplina.tip=TipDisciplina.Facultativ;
		
		String s=MessageParser.getObjectRepresentation(Disciplina.class, disciplina, MessageConstants.STRUCTURE_DISCIPLINA);
		System.out.println(s);
		Disciplina parsed=MessageParser.parseObject(Disciplina.class, s, MessageConstants.STRUCTURE_DISCIPLINA);
		System.out.println(parsed.toString());
		ArrayList<Disciplina> parsedL=MessageParser.parseObjects(Disciplina.class, s+MessageParser.DELIMITER.toString()+s, MessageConstants.STRUCTURE_DISCIPLINA, 2);
		System.out.println(parsedL.toString());
		System.out.println(MessageParser.getObjectsRepresentation(Disciplina.class, parsedL, MessageConstants.STRUCTURE_DISCIPLINA));
		System.out.println("##Test COMPLETE!!!");
	}
	
}
