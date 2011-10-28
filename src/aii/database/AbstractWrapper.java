package aii.database;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AbstractWrapper<T> {
	
	/**
	 * Creates the objects.
	 *
	 * @param table the table
	 * @param where the where
	 * @return the list
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public List<T> createObjects(Class classType, String[][] nameMatch, String table, String where) throws SQLException,
			InstantiationException, IllegalAccessException, SecurityException, NoSuchFieldException 
		{
		ResultSet entries=null;
		
		//Get the result set from the database
		try {
			DatabaseConnection.openConnection();
			entries=DatabaseConnection.getRestrictedTable(table, where);			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Parse the result set entries, create the objects and insert them in the list
		List<T> objects=new ArrayList<T>();
		while (entries.next()) 
		{
			T instance=(T) classType.newInstance();
			
			//For every field in the nameMatch[0] array, we put the value in the matching object field
			for(int i=0; i<nameMatch[0].length;i++)
			{
				String fieldName = nameMatch[0][i];
				
				//Object value=entries.getObject(fieldName);
				Object value=entries.getObject(i+1);	//it is important for the fields to be in the same order as in the object
				
				Field field=classType.getDeclaredField(nameMatch[1][i]);
				field.set(instance, value);
			}
			
			objects.add(instance);
		}
		
		System.out.println(objects.toString());
		return objects;
	}
}
