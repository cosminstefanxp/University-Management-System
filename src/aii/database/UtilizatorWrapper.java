package aii.database;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import aii.Utilizator;

/**
 * The Class UserWrapper.;
 */
public class UtilizatorWrapper {

	public static Utilizator getUtilizator(String cnp)
	{
		ObjectWrapper<Utilizator> wrapper=new ObjectWrapper<Utilizator>(Utilizator.class, Constants.USER_FIELD_MATCH);
		List<Utilizator> utilizatori = null;
		try {
			utilizatori=wrapper.createObjects(Constants.USER_TABLE, "cnp='"+cnp+"'");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		utilizatori.size();
		
		return null;
	}
}
