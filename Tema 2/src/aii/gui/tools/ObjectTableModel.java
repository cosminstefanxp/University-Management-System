/**
 * 
 */
package aii.gui.tools;

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
public class ObjectTableModel<T> extends AbstractTableModel {

	/** The objects. */
	private ArrayList<T> objects;

	/** The column names. */
	private String[] columnNames;
	
	/** The fields. */
	private Field[] fields;

	/**
	 * Instantiates a new object table model.
	 *
	 * @param classType the class type
	 * @param objects the objects
	 * @param columnNames the column names
	 * @param fieldNames the field names
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 */
	public ObjectTableModel(Class<T> classType, ArrayList<T> objects, String[] columnNames, String[] fieldNames) throws SecurityException, NoSuchFieldException {
		super();
		this.objects = objects;
		this.columnNames = columnNames;
		fields=new Field[fieldNames.length];
		
		//Build the object fields
		for(int i=0;i<fieldNames.length;i++)
			fields[i]=classType.getDeclaredField(fieldNames[i]);
		
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return objects.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		T object=objects.get(rowIndex);
		
		try {
			return fields[columnIndex].get(object);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getColumnName(int index) {
		return columnNames[index];
	}

	public void setObjects(ArrayList<T> objects) {
		this.objects = objects;
	}

}
