package aii.database;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The Class ObjectWrapper.
 *
 * @param <T> the generic type
 */
public class ObjectWrapper<T> {
	
	/** The class type. */
	Class<T> classType;
	
	/** A 2 dimensional array containing matchings between the names of the fields in the database and the
	 *  names of the fields in the objects. */
	String[][] nameMatch;
	
		
	/**
	 * Instantiates a new object wrapper.
	 *
	 * @param type the type
	 * @param nameMatch a 2 dimensional array containing matchings between the names of the fields in the database and the
	 *  		names of the fields in the objects.
	 */
	public ObjectWrapper(Class<T> type, String[][] nameMatch) {
		super();
		this.classType = type;
		this.nameMatch = nameMatch;
	}


	/**
	 * Creates a list of objects, by querying the database and then building Java objects from the database fields
	 *
	 * @param table the database table containing the data
	 * @param where the where clause that is used to restrict the number of fields
	 * @return the list of objects
	 * @throws SQLException the sQL exception
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws SecurityException the security exception
	 * @throws NoSuchFieldException the no such field exception
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<T> createObjects(String table, String where) throws SQLException,
			InstantiationException, IllegalAccessException, SecurityException, NoSuchFieldException 
		{
		ResultSet entries=null;
		
		//Get the result set from the database
		DatabaseConnection.openConnection();
		entries=DatabaseConnection.getRestrictedTable(table, where);			
		
		//Parse the result set entries, create the objects and insert them in the list
		ArrayList<T> objects=new ArrayList<T>();
		while (entries.next()) 
		{
			//Create a new instance of the class, that will be populated with information
			T instance=(T) classType.newInstance();
			
			//For every field in the nameMatch[0] array of database field names, we put the value in the matching object field
			for(int i=0; i<nameMatch[0].length;i++)
			{
				//Prepare the field of the object which we are now filling
				Field field=classType.getDeclaredField(nameMatch[1][i]);
				
				//Prepare the value of the database column
				String columnName = nameMatch[0][i];
				
				if(field.getType() == java.sql.Date.class)
					field.set(instance, entries.getDate(columnName));
				else
					if(field.getType() == int.class)
						field.setInt(instance, entries.getInt(columnName));
					else
						if(field.getType().isEnum())
						{
							String enumValue=(String) entries.getObject(columnName);
							if(enumValue==null)
								continue;
							field.set(instance, Enum.valueOf((Class<Enum>) field.getType(), enumValue));	
						}
						else
							if(field.getType() == java.sql.Date.class)
								field.set(instance, entries.getTime(columnName));
							else
								field.set(instance, entries.getObject(columnName));
			}
			
			//Finally add the new instance to the list
			objects.add(instance);
		}
		
		System.out.println("S-au obtinut obiectele:" + objects.toString());
		return objects;
	}
	
}
