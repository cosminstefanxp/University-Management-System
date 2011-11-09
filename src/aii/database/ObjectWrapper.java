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
	private Class<T> classType;
	
	/** A 2 dimensional array containing matchings between the names of the fields in the database and the
	 *  names of the fields in the objects. */
	private String[][] nameMatch;
	
	/** The private key count is the number of the fields the form the private key of the object. The fields should
	 * be on the first privateKeyCount positions in the nameMatch array. */
	private int privateKeyCount;
	
	
		
	/**
	 * Instantiates a new object wrapper.
	 *
	 * @param type the type
	 * @param nameMatch a 2 dimensional array containing matchings between the names of the fields in the database and the
	 * names of the fields in the objects.
	 * @param privateKeyCount The private key count is the number of the fields the form the private key of the object. The fields should
	 * be on the first privateKeyCount positions in the nameMatch array.
	 */
	public ObjectWrapper(Class<T> type, String[][] nameMatch, int privateKeyCount) {
		super();
		this.classType = type;
		this.nameMatch = nameMatch;
		this.privateKeyCount=privateKeyCount;
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
	public ArrayList<T> getObjects(String table, String where) throws SQLException,
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
							if(enumValue==null || enumValue.equalsIgnoreCase("null"))
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
	
	/**
	 * Insert a new object in the given table.
	 *
	 * @param table the table
	 * @param object the object
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws SecurityException the security exception
	 * @throws NoSuchFieldException the no such field exception
	 * @throws SQLException the sQL exception
	 */
	public void insertObject(String table, T object) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException, SQLException
	{
		String[] values=new String[nameMatch[0].length];
		
		//For every field in the nameMatch[0] array of database field names, we put the value in the matching object field in the values array
		for(int i=0; i<nameMatch[0].length;i++)
		{
			//Prepare the field of the object which we are now getting info from
			Field field=classType.getDeclaredField(nameMatch[1][i]);
			
			Object value=field.get(object);
			if(value!=null)
				values[i]=value.toString();
			else
				values[i]="NULL";
		}
		
		//Run the SQL insertion query
		System.out.println("Inseram un nou obiect in baza de date: "+values);
		DatabaseConnection.openConnection();
		DatabaseConnection.addEntity(table, nameMatch[0], values);
	}
	
	/**
	 * Delete an object from the given table
	 *
	 * @param table the table
	 * @param object the object
	 * @throws SecurityException the security exception
	 * @throws NoSuchFieldException the no such field exception
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws SQLException the sQL exception
	 */
	public void deleteObject(String table, T object) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, SQLException
	{
		String whereClause="";
		
		//For every field in the private key, we put the value in the where clause
		for(int i=0;i<this.privateKeyCount;i++)
		{
			//Prepare the field of the object which we are now getting info from
			Field field=classType.getDeclaredField(nameMatch[1][i]);
			
			whereClause+=nameMatch[0][i]+"=\'"+field.get(object)+"\' AND ";				
		}
		
		//Clean the last ,
		whereClause=whereClause.substring(0,whereClause.length()-4);

		//Run the SQL deletion query
		System.out.println("Stergem un obiect din baza de date: "+whereClause);
		DatabaseConnection.openConnection();
		DatabaseConnection.deleteEntities(table, whereClause);	
	}
	
	/**
	 * Updates an object from the given table. Object selection is based on private key.
	 *
	 * @param table the table
	 * @param oldObject the old object
	 * @param newObject the new object
	 * @throws SecurityException the security exception
	 * @throws NoSuchFieldException the no such field exception
	 * @throws SQLException the sQL exception
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException the illegal access exception
	 */
	public void updateObject(String table, T oldObject, T newObject) throws SecurityException, NoSuchFieldException, SQLException, IllegalArgumentException, IllegalAccessException
	{
		String whereClause="";
		String setClause="";
		
		//For every field in the private key, we put the value in the where clause
		for(int i=0;i<this.privateKeyCount;i++)
		{
			//Prepare the field of the object which we are now getting info from
			Field field=classType.getDeclaredField(nameMatch[1][i]);
			
			whereClause+=nameMatch[0][i]+"=\'"+field.get(oldObject)+"\' AND ";				
		}
		
		//Clean the last ,
		whereClause=whereClause.substring(0,whereClause.length()-4);
		
		//For every field in the object, we put the value in the set clause
		for(int i=0;i<nameMatch[0].length;i++)
		{
			//Prepare the field of the object which we are now getting info from
			Field field=classType.getDeclaredField(nameMatch[1][i]);
			
			if(field.getType() == boolean.class)
			{
				if(field.getBoolean(newObject))
					setClause+=nameMatch[0][i]+"=\'1\', ";
				else
					setClause+=nameMatch[0][i]+"=\'0\', ";
			}
			else
				setClause+=nameMatch[0][i]+"=\'"+field.get(newObject)+"\', ";				
		}
		
		//Clean the last ,
		setClause=setClause.substring(0,setClause.length()-2);

		//Run the SQL deletion query
		System.out.println("Actualizam un obiect din baza de date: "+whereClause);
		DatabaseConnection.openConnection();
		DatabaseConnection.updateEntities(table, setClause, whereClause);	
	}
	
	
}
