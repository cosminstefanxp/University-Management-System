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
			expression += "\'" + value + "\',";
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
	
	/*public static void modifica_inregistrari_baza_de_date(String tabela, ArrayList<String> valori)
			throws SQLExcep3nixtion {
		deschide_conexiune_baza_de_date();
		String expresie = "UPDATE " + tabela + " SET ";
		for (String[] structura_tabela : Constants.STRUCTURA_TABELA)
			if (tabela.equals(structura_tabela[0])) {
				int coloana = 1;
				for (String valoare : valori)
					expresie += structura_tabela[coloana++] + "=\'" + valoare + "\', ";
			}
		expresie = expresie.substring(0, expresie.length() - 2);
		expresie += " WHERE ";
		for (String[] structura_tabela : Constants.STRUCTURA_TABELA)
			if (tabela.equals(structura_tabela[0]))
				expresie += structura_tabela[1] + "=\'" + valori.get(0) + "\'";
		System.out.println(expresie);
		interogare.execute(expresie);
		inchide_conexiune_baza_de_date();
	}
	*/
	
	// public static Object[][] obtine_continut_baza_de_date (String tabela)
	// throws SQLException
	// {
	// openConnection();
	// String expresie = "SELECT * FROM "+tabela;
	// ResultSet rezultat = statement.executeQuery(expresie);
	// rezultat.last();
	// int numar_randuri = rezultat.getRow();
	// int numar_coloane = -1;
	// for (String[] structura_tabela:Constants.STRUCTURA_TABELA)
	// if (tabela.equals(structura_tabela[0]))
	// numar_coloane = structura_tabela.length - 1;
	// if (numar_coloane == -1)
	// return null;
	// Object[][] continut_baza_de_date = new
	// Object[numar_randuri][numar_coloane];
	// rezultat.beforeFirst();
	// int rand = 0;
	// while (rezultat.next())
	// {
	// for (int coloana = 0; coloana < numar_coloane; coloana++)
	// continut_baza_de_date[rand][coloana] = rezultat.getString(coloana+1);
	// rand++;
	// }
	// closeConnection();
	// return continut_baza_de_date;
	// }
	//

	//
	// public static void modifica_inregistrari_baza_de_date (String tabela,
	// ArrayList<String> valori) throws SQLException
	// {
	// openConnection();
	// String expresie = "UPDATE "+tabela+" SET ";
	// for (String[] structura_tabela:Constants.STRUCTURA_TABELA)
	// if (tabela.equals(structura_tabela[0]))
	// {
	// int coloana = 1;
	// for (String valoare:valori)
	// expresie += structura_tabela[coloana++]+"=\'"+valoare+"\', ";
	// }
	// expresie = expresie.substring(0,expresie.length()-2);
	// expresie += " WHERE ";
	// for (String[] structura_tabela:Constants.STRUCTURA_TABELA)
	// if (tabela.equals(structura_tabela[0]))
	// expresie += structura_tabela[1]+"=\'"+valori.get(0)+"\'";
	// System.out.println (expresie);
	// statement.execute(expresie);
	// closeConnection();
	// }

	// public static void sterge_inregistrari_baza_de_date (String tabela,
	// ArrayList<String> valori) throws SQLException
	// {
	// deschide_conexiune_baza_de_date();
	// String expresie = "DELETE FROM "+tabela+" WHERE ";
	// for (String[] structura_tabela:Constants.STRUCTURA_TABELA)
	// if (tabela.equals(structura_tabela[0]))
	// expresie += structura_tabela[1]+"=\'"+valori.get(0)+"\'";
	// System.out.println (expresie);
	// statement.execute(expresie);
	// inchide_conexiune_baza_de_date();
	// }
	//
	// public static Object[][] apeleaza_functie (String[] coloane, String
	// tabela, String conditie, String parametru) throws SQLException
	// {
	// deschide_conexiune_baza_de_date();
	//
	// String expresie = "SELECT ";
	// for (int contor = 0; contor < coloane.length -1; contor++)
	// expresie+=coloane[contor]+", ";
	// expresie = expresie.substring (0, expresie.length() - 2);
	//
	// expresie += " FROM "+tabela;
	// expresie += " WHERE "+conditie;
	//
	// System.out.println (expresie);
	//
	// PreparedStatement interogare_parametrizata =
	// connection.prepareStatement(expresie,ResultSet.TYPE_SCROLL_SENSITIVE,
	// ResultSet.CONCUR_UPDATABLE);
	// interogare_parametrizata.setString(1,parametru);
	// ResultSet rezultat = interogare_parametrizata.executeQuery();
	// rezultat.last();
	// int numar_randuri = rezultat.getRow();
	// int numar_coloane = coloane.length;
	//
	// Object[][] continut_baza_de_date = new
	// Object[numar_randuri][numar_coloane];
	// rezultat.beforeFirst();
	// int rand = 0;
	// while (rezultat.next())
	// {
	// for (int coloana = 0; coloana < numar_coloane - 1; coloana++)
	// continut_baza_de_date[rand][coloana] = rezultat.getString(coloana+1);
	//
	// CallableStatement interogare_functie =
	// connection.prepareCall("{? = call "+coloane[coloane.length - 1]+" (?)}");
	// interogare_functie.registerOutParameter(1, java.sql.Types.VARCHAR);
	// interogare_functie.setString(2,rezultat.getString(numar_coloane - 1));
	// interogare_functie.execute();
	// String valoare = interogare_functie.getString(1);
	// interogare_functie.close();
	// continut_baza_de_date[rand][numar_coloane - 1] = valoare;
	//
	// rand++;
	// }
	// inchide_conexiune_baza_de_date();
	// return continut_baza_de_date;
	// }
	//
	// public static Object[][] statistici (String[] coloane, String tabela,
	// String conditie) throws SQLException
	// {
	// deschide_conexiune_baza_de_date();
	//
	// String expresie = "SELECT ";
	// for (int contor = 0; contor < coloane.length; contor++)
	// expresie+=coloane[contor]+", ";
	// expresie = expresie.substring (0, expresie.length() - 2);
	//
	// expresie += " FROM "+tabela;
	// expresie += " WHERE "+conditie;
	//
	// expresie += " GROUP BY ";
	// for (int contor = 0; contor < coloane.length - 1; contor++)
	// expresie+=coloane[contor]+", ";
	// expresie = expresie.substring (0, expresie.length() - 2);
	//
	// expresie += " ORDER BY ";
	// expresie += coloane[coloane.length - 1]+" DESC";
	//
	// System.out.println (expresie);
	//
	// ResultSet rezultat = statement.executeQuery(expresie);
	// rezultat.last();
	// int numar_randuri = rezultat.getRow();
	// int numar_coloane = coloane.length;
	//
	// Object[][] continut_baza_de_date = new
	// Object[numar_randuri][numar_coloane];
	// rezultat.beforeFirst();
	// int rand = 0;
	// while (rezultat.next())
	// {
	// for (int coloana = 0; coloana < numar_coloane; coloana++)
	// continut_baza_de_date[rand][coloana] = rezultat.getString(coloana+1);
	// rand++;
	// }
	// inchide_conexiune_baza_de_date();
	// return continut_baza_de_date;
	// }
}
