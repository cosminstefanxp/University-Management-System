/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Vector;

/**
 * The Class DatabaseConnection.
 */
public class DatabaseConnection {
	
	/** The connection. */
	private static Connection connection;
	
	/** The statement. */
	private static Statement statement;
	
	/** If the connection is open. */
	private static boolean open = false;

	/**
	 * Opens a new connection to the database.
	 *
	 * @throws SQLException the sQL exception
	 */
	public static void openConnection() throws SQLException {
		if(open)
			return;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		connection = DriverManager.getConnection(Constants.DATABASE_CONNECTION,
				Constants.DATABASE_USER, Constants.DATABASE_PASSWORD);
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		open = true;
	}

	/**
	 * Closes the current connection to the database.
	 *
	 * @throws SQLException the sQL exception
	 */
	public static void closeConnection() throws SQLException {
		if(!open)
			return;
		
		statement.close();
		open = false;
	}

	/**
	 * Gets the full data from a single table. Equivalent to 'SELECT * FROM table;'
	 *
	 * @param table the table
	 * @return the full table
	 * @throws SQLException the sQL exception
	 */
	public static ResultSet getFullTable(String table) throws SQLException 
	{
		openConnection();
		
		String expression = "SELECT * FROM " + table;
		ResultSet result = statement.executeQuery(expression);
		System.out.println("S-a apelat expresia SQL '"+expression+"'");
		
		return result;
	}
	
	/**
	 * Gets data from one table, with a where clause. Equivalent to 'SELECT * FROM tables WHERE where'.
	 *
	 * @param tables the tables
	 * @param where the where
	 * @return the restricted table
	 * @throws SQLException the sQL exception
	 */
	public static ResultSet getRestrictedTable(String tables, String where) throws SQLException
	{
		openConnection();
		
		String expression = "SELECT * FROM " + tables + " WHERE " + where;
		System.out.println("S-a apelat expresia SQL '"+expression+"'");
		ResultSet result = statement.executeQuery(expression);
		
		
		return result;
	}
	
	/**
	 * Gets given fields from given tables, using the given where clause and adding the given extra parameters.
	 * Equivalent to: SELECT fields FROM table [WHERE where [extra]]
	 *
	 * @param fields the fields to extract
	 * @param tables the tables to query
	 * @param where the where clause
	 * @param extra the extra parameters, such as ORDER BY, GROUP BY, etc
	 * @return the result
	 */
	public static ResultSet getCustom(String fields, String tables, String where, String extra) throws SQLException
	{
		openConnection();
		
		String expression = "SELECT "+fields+" FROM " + tables;
		if(where!=null)
			expression+= " WHERE " + where;
		if(extra!=null)
			expression+=" "+extra;
		expression+=";";
		
		System.out.println("S-a apelat expresia SQL '"+expression+"'");
		ResultSet result = statement.executeQuery(expression);
		
		return result;		
	}
	
	/**
	 * Performs a custom query and returns a result set with the results.
	 *
	 * @param sqlQuery the sql query
	 * @return the result set
	 * @throws SQLException the sQL exception
	 */
	public static ResultSet customQuery(String sqlQuery) throws SQLException
	{
		openConnection();
		
		System.out.println("S-a apelat expresia SQL '"+sqlQuery+"'");
		ResultSet result = statement.executeQuery(sqlQuery);
		
		return result;
	}
	
	public static float getSingleValueResult(String sqlQuery) throws SQLException
	{
		openConnection();
		
		System.out.println("S-a apelat expresia SQL '"+sqlQuery+"'");
		ResultSet result = statement.executeQuery(sqlQuery);
		
		result.next();
		System.out.println("Rezultat: "+result.getFloat(1));
		return result.getFloat(1);
	}
	
	/**
	 * Performs a custom query execution.
	 *
	 * @param sqlQuery the sql query
	 * @return the result set
	 * @throws SQLException the sQL exception
	 */
	public static void customExecute(String sqlQuery) throws SQLException
	{
		openConnection();
		
		System.out.println("S-a apelat expresia SQL '"+sqlQuery+"'");
		statement.execute(sqlQuery);
	}
	
	/**
	 * Custom query array.
	 *
	 * @param sqlQuery the sql query
	 * @return the vector
	 * @throws SQLException the sQL exception
	 */
	public static Vector<Object[]> customQueryArray(String sqlQuery) throws SQLException
	{
		ResultSet entries=DatabaseConnection.customQuery(sqlQuery);
		Vector<Object[]> vector=new Vector<Object[]>();
		
		int columnCount=entries.getMetaData().getColumnCount();
		System.out.println("S-a obtinut un tabel cu "+columnCount+" coloane.");
		
		//Cycle through the rows
		while(entries.next())
		{
			//Prepare a new Row
			Object[] objects=new Object[columnCount];
			
			for(int i=0;i<columnCount;i++)
				objects[i]=entries.getObject(i+1);
			
			vector.add(objects);
			System.out.println("Rand nou obtinut: "+Arrays.toString(objects));
		}
		
		
		
		return vector;
	}
	
	
	/**
	 * Adds a new entity in the database table, given the fieldNames and the field Values.
	 *
	 * @param table the table
	 * @param fieldNames the field names
	 * @param values the values
	 * @throws SQLException the sQL exception
	 */
	public static void addEntity(String table, String[] fieldNames, String[] values)
			throws SQLException {

		openConnection();
		
		String expression = "INSERT INTO " + table + " (";
		
		//Insert field names
		for (int col = 0; col < fieldNames.length; col++)
			expression += fieldNames[col] + ",";
		expression = expression.substring(0, expression.length() - 1);
		
		//Insert field values
		expression += ") VALUES (";
		for (String value : values)
		{
			if(value.equals("false"))
				value="0";
			if(value.equals("true"))
				value="1";
			
			expression += "\'" + value + "\',";
		}
		expression = expression.substring(0, expression.length() - 1);
		expression += ")";
		
		System.out.println("S-a apelat expresia SQL \'"+expression+"\'");
		statement.execute(expression);
	}
	
	/**
	 * Delete entities that match a given where close.
	 *
	 * @param table the table
	 * @param whereClause the where clause
	 * @throws SQLException the sQL exception
	 */
	public static void deleteEntities(String table, String whereClause)
			throws SQLException {
		
		openConnection();
		
		String expression = "DELETE FROM " + table + " WHERE "+whereClause;
		
		System.out.println("S-a apelat expresia SQL \'"+expression+"\'");
		statement.execute(expression);
	}
	
	/**
	 * Delete entities that match a given where close.
	 *
	 * @param table the table
	 * @param whereClause the where clause
	 * @return the number of successfully deleted entities
	 * @throws SQLException the sQL exception
	 */
	public static int deleteEntitiesCount(String table, String whereClause)
			throws SQLException {
		
		openConnection();
		
		String expression = "DELETE FROM " + table + " WHERE "+whereClause;
		
		System.out.println("S-a apelat expresia SQL \'"+expression+"\'");
		statement.execute(expression);
		
		return statement.getUpdateCount();
		
	}
	
	/**
	 * Updates entities in the table, using the setClause for setting values and whereClause to restrict rows.
	 *
	 * @param table the table
	 * @param setClause the set clause
	 * @param whereClause the where clause
	 * @throws SQLException the sQL exception
	 */
	public static void updateEntities(String table, String setClause, String whereClause) throws SQLException
	{
		openConnection();
		
		String expression = "UPDATE " + table + " SET "+setClause+" WHERE "+whereClause;
		
		System.out.println("S-a apelat expresia SQL \'"+expression+"\'");
		statement.execute(expression);		
		
	}
	
	/**
	 * Updates entities in the table, using the setClause for setting values and whereClause to restrict rows.
	 *
	 * @param table the table
	 * @param setClause the set clause
	 * @param whereClause the where clause
	 * @return the count of succesfully modified entries
	 * @throws SQLException the sQL exception
	 */
	public static int updateEntitiesCount(String table, String setClause, String whereClause) throws SQLException
	{
		openConnection();
		
		String expression = "UPDATE " + table + " SET "+setClause+" WHERE "+whereClause;
		
		System.out.println("S-a apelat expresia SQL \'"+expression+"\'");
		statement.execute(expression);	
	
		return statement.getUpdateCount();
	}
}
