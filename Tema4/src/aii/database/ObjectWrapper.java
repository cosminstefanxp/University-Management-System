/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii.database;

import java.lang.reflect.Field;
import java.sql.Date;
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
	
	
		
	public void setNameMatch(String[][] nameMatch) {
		this.nameMatch = nameMatch;
	}

	/**
	 * Instantiates a new object wrapper.
	 *
	 * @param type the type
	 * @param nameMatch a 2 dimensional array containing matchings between the names of the fields in the database and the
	 * names of the fields in the objects.
	 * @param privateKeyCount The primary key count is the number of the fields the form the primary key of the object. The fields should
	 * be on the first privateKeyCount positions in the nameMatch array.
	 */
	public ObjectWrapper(Class<T> type, String[][] nameMatch, int privateKeyCount) {
		super();
		this.classType = type;
		this.nameMatch = nameMatch;
		this.privateKeyCount=privateKeyCount;
	}
	
	/**
	 * Parses the result set entries, create the objects and insert them in the list
	 *
	 * @param entries the result set entries
	 * @return the array list with the constructed objects
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws SQLException the sQL exception
	 * @throws InstantiationException the instantiation exception
	 * @throws SecurityException the security exception
	 * @throws NoSuchFieldException the no such field exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList<T> parseResultSet(ResultSet entries) throws IllegalArgumentException, IllegalAccessException, SQLException, InstantiationException, SecurityException, NoSuchFieldException
	{
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
				Field field;
				try{
					field=classType.getDeclaredField(nameMatch[1][i]);
				}
				//If the field was not found, try to get it from the super class
				catch(NoSuchFieldException ex)
				{
					if(classType.getSuperclass()==null)
						throw ex;
					field=classType.getSuperclass().getDeclaredField(nameMatch[1][i]);
				}
				
				//Prepare the value of the database column
				String columnName = nameMatch[0][i];
				
				if(field.getType() == java.sql.Date.class)
					field.set(instance, entries.getDate(columnName));
				if(field.getType() == java.util.Date.class)
				{
					java.sql.Date sqlDate=entries.getDate(columnName);
					field.set(instance, new java.util.Date(sqlDate.getTime()));
				}
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
								field.set(instance, entries.getObject(columnName));
			}
			
			//Finally add the new instance to the list
			objects.add(instance);
		}
		
		return objects;
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
	public ArrayList<T> getObjects(String table, String where) throws SQLException,
			InstantiationException, IllegalAccessException, SecurityException, NoSuchFieldException 
		{
		ResultSet entries=null;
		
		//Get the result set from the database
		DatabaseConnection.openConnection();
		entries=DatabaseConnection.getRestrictedTable(table, where);			
		
		//Parse the results
		ArrayList<T> objects=parseResultSet(entries);
		
		System.out.println("S-au obtinut obiectele:" + objects.toString());
		return objects;
	}
	
	/**
	 * Gets given fields from given tables and creates the associated objects, using the given where clause and adding the given extra parameters.
	 * Equivalent to: SELECT fields FROM table [WHERE where [extra]]
	 *
	 * @param fields the fields to get from table
	 * @param tables the tables to query
	 * @param where the where clause - can be null
	 * @param extra the extra clauses - can be null
	 * @return the objects that are build using data from the database
	 * @throws SQLException the sQL exception
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws SecurityException the security exception
	 * @throws NoSuchFieldException the no such field exception
	 */
	public ArrayList<T> getObjects(String fields, String tables, String where, String extra) throws SQLException,
			InstantiationException, IllegalAccessException, SecurityException, NoSuchFieldException {
		ResultSet entries = null;

		// Get the result set from the database
		DatabaseConnection.openConnection();
		entries = DatabaseConnection.getCustom(fields, tables, where, extra);

		// Parse the results
		ArrayList<T> objects = parseResultSet(entries);

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
			
			if(field.getType()==java.util.Date.class)
			{
				java.util.Date date=(java.util.Date) value;
				java.sql.Date sqlDate=new java.sql.Date(date.getTime());
				values[i]=sqlDate.toString();
			}
			else
			{
				if(value!=null)
					values[i]=value.toString();
				else
					values[i]="NULL";
			}
		}
		
		//Run the SQL insertion query
		System.out.println("Inseram un nou obiect in baza de date: "+values);
		DatabaseConnection.openConnection();
		DatabaseConnection.addEntity(table, nameMatch[0], values);
	}
	
	/**
	 * Delete an object from the given table. Object selection is based on primary key.
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
		
		//For every field in the primary key, we put the value in the where clause
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
	 * Delete an object from the given table. The selection of the objects to delete is done using the provided where clause.
	 *
	 * @param table the table
	 * @param whereClause the where clause
	 * @throws SecurityException the security exception
	 * @throws NoSuchFieldException the no such field exception
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws SQLException the sQL exception
	 */
	public void deleteObject(String table, String whereClause) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, SQLException
	{
		//Run the SQL deletion query
		System.out.println("Stergem un obiect din baza de date: "+whereClause);
		DatabaseConnection.openConnection();
		int count=DatabaseConnection.deleteEntitiesCount(table, whereClause);
		if(count==0)
			throw new SQLException("CUSTOM: No entities were deleted!");
	}
	
	/**
	 * Updates an object from the given table. Object selection is based on primary key.
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
		
		//For every field in the primary key, we put the value in the where clause
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
			else if(field.getType() == java.util.Date.class)
			{
				java.util.Date date=(java.util.Date) field.get(newObject);
				java.sql.Date sqlDate= new Date(date.getTime());
				setClause+=nameMatch[0][i]+"=\'"+sqlDate+"\', ";
			}
			else
				setClause+=nameMatch[0][i]+"=\'"+field.get(newObject)+"\', ";				
		}
		
		//Clean the last ,
		setClause=setClause.substring(0,setClause.length()-2);

		//Run the SQL deletion query
		System.out.println("Actualizam un obiect din baza de date: "+whereClause);
		DatabaseConnection.openConnection();
		int count=DatabaseConnection.updateEntitiesCount(table, setClause, whereClause);
		if(count==0)
			throw new SQLException("CUSTOM: No entities were updated!");
	}
	
	/**
	 * Updates an object from the given table. The selection of the objects to update is done using the provided where clause.
	 *
	 * @param table the table
	 * @param whereClause the where clause
	 * @param newObject the new object
	 * @throws SecurityException the security exception
	 * @throws NoSuchFieldException the no such field exception
	 * @throws SQLException the sQL exception
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException the illegal access exception
	 */
	public void updateObject(String table, String whereClause, T newObject) throws SecurityException, NoSuchFieldException, SQLException, IllegalArgumentException, IllegalAccessException
	{
		String setClause="";
		
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
		int count=DatabaseConnection.updateEntitiesCount(table, setClause, whereClause);
		if(count==0)
			throw new SQLException("CUSTOM: No entities were updated!");
	}
	
	
}
