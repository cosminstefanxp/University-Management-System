package aii.gui;

import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

/**
 * The Class ObjectTableModel.
 *
 * @param <T> the generic type
 * @author cosmin
 */
@SuppressWarnings("serial")
public class ObjectTableGenerator<T> extends AbstractTableModel {

	/** The objects. */
	private ArrayList<T> objects;

	/** The column names. */
	private String[] columnNames;
	
	/** The fields. */
	private Field[] fields;
	
	/** The primary key count. */
	private int[] idFields;


	/**
	 * Instantiates a new object table generator.
	 *
	 * @param classType the class type
	 * @param objects the objects
	 * @param columnNames the column names
	 * @param fieldNames the field names
	 * @param idFields the fields which will be used for the generation of the id
	 * @throws SecurityException the security exception
	 * @throws NoSuchFieldException the no such field exception
	 */
	public ObjectTableGenerator(Class<T> classType, ArrayList<T> objects, String[] columnNames, String[] fieldNames, int[] idFields) throws SecurityException, NoSuchFieldException {
		this.objects = objects;
		this.columnNames = columnNames;
		this.idFields=idFields;
		fields=new Field[fieldNames.length];
		
		//Build the object fields
		for(int i=0;i<fieldNames.length;i++)
			fields[i]=classType.getDeclaredField(fieldNames[i]);
		
	}


	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return columnNames.length;
	}


	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return objects.size();
	}


	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		T object=objects.get(rowIndex);
		
		try {
			return fields[columnIndex].get(object);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	public String getColumnName(int index) {
		return columnNames[index];
	}

	/**
	 * Sets the objects.
	 *
	 * @param objects the new objects
	 */
	public void setObjects(ArrayList<T> objects) {
		this.objects = objects;
	}
	
	/**
	 * Gets the HTML table representation. Without the table tag
	 *
	 * @return the hTML table representation
	 */
	public String getHTMLTableRepresentation(String editLink, String deleteLink)
	{
		StringBuilder result=new StringBuilder();
		//Build the header first
		result.append("\t<tr>");
		for(int i=0;i<getColumnCount();i++)
			result.append("\t\t<th>"+getColumnName(i)+"</th>");
		//Admin column
		if(editLink!=null || deleteLink!=null)
			result.append("\t\t<th>Admin</th>");
		result.append("</tr>\n");

		//Add the data
		for(int i=0; i<getRowCount();i++)
		{
			result.append("\t<tr>");
			
			//Data columns
			for (int j = 0; j < getColumnCount(); j++)
			{
				if(getValueAt(i, j)==null)
					result.append("\t\t<td> - </td>");	
				else
					result.append("\t\t<td>" + getValueAt(i, j) + "</td>");
			}
			//Admin columns
			if(editLink!=null || deleteLink!=null)
			{
				result.append("\t\t<td>");
				String id=buildIdentificationString(i);
				if(editLink!=null)
					result.append("<a href='"+editLink+"?"+id+"'>Edit</a> ");
				if(deleteLink!=null)
					result.append("<a href='"+deleteLink+"?"+id+"'>Delete</a>");
				result.append("</td>");
				
			}
			result.append("</tr>");
		}
		
		return result.toString();
	}

	/**
	 * Builds the identification string, considering the primary key, for a given row.
	 *
	 * @param row the row
	 * @return the string
	 */
	private String buildIdentificationString(int row)
	{
		String result="";
		for(int i=0;i<idFields.length;i++)
			result+=getColumnName(idFields[i])+"="+getValueAt(row, idFields[i])+"&";
		return result.substring(0,result.length()-1);
	}
}
