package training.viewGroup;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import training.modelGroup.*;
import training.viewGroup.ModalWindows.AddModalWindow;

public class MyTableModelNew extends AbstractTableModel {
	private List<String> columnsNames;
	private List<String[]> dataTable;
	private List<Person> list;
	// for date class formating
	public static final String DATE_PATTERN = "yyyy-mm-dd";
	public static final SimpleDateFormat FORMATTER = new SimpleDateFormat(DATE_PATTERN);

	public MyTableModelNew() {
		init();
	}

	private void init() {
		DataFromDBNew data = new DataFromDBNew();
		columnsNames = data.getColumnsNames();
		list = data.getList();
		sortListOfPersons();
		dataTable = new ArrayList<>();
		populateTable();

	}

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public int getColumnCount() {
		return columnsNames.size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		if (columnIndex == 0)
			return "ID";
		else
			return AddModalWindow.makeNice(columnsNames.get(columnIndex));
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Person person = list.get(rowIndex);
		Object value = asList(person).get(columnIndex);
		return value;

	}

	public String[] getRow(int rowIndex) {
		Person person = list.get(rowIndex);
		String[] row = asArray(person);
		return row;

	}

	public void refreshDataList() {
		dataTable.clear();
		init();
	}

	public void addData(String[] row) {
		dataTable.add(row);
	}

	public void populateTable() {
		for (Person person : list) {
			String[] row = asArray(person);
			addData(row);
		}
	}

	public List<String> getColumnsNames() {
		return columnsNames;
	}

	public List<Person> getList() {
		return list;
	}

	public String[] asArray(Person person) {
		String[] array = new String[columnsNames.size()];
		for (int i = 0; i < columnsNames.size(); i++) {
			Object value = getValueByPropertyName(columnsNames.get(i), person);
			array[i] = String.valueOf(value);
		}
		return array;

	}

	public List<Object> asList(Person person) {
		List<Object> list = new ArrayList<>();
		for (String propertyName : columnsNames) {
			Object value = getValueByPropertyName(propertyName, person);
			list.add(value);
		}
		return list;

	}

	private static Object getValueByPropertyName(String propertyName, Person person) {

		Method[] methods = person.getClass().getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().equalsIgnoreCase("get" + propertyName)) {
				method.setAccessible(true);
				try {
					Object result = method.invoke(person);
					if (result instanceof Date) {
						return formateDate(result);
					}
					return result;
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
		return null;
	}

	private static String formateDate(Object result) {
		String formatted_date = FORMATTER.format((Date) result);
		return formatted_date;
	}

	private void sortListOfPersons() {

		list.sort(new Comparator<Person>() {

			@Override
			public int compare(Person p1, Person p2) {
				return ((Integer) p1.getId()).compareTo(p2.getId());
			}
		});

	}

}
