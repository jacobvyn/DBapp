package training.modelGroup;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DataFromDBNew {

	private List<Person> list;
	private List<String> columnsNames;

	public DataFromDBNew() {
		list = ServletsCommunicationNEW.getData(ServletsCommunication.GET_DATA_URL);
		setColumnsNames();
	}

	private void setColumnsNames() {
		Person pers = list.get(0);
		Field[] fields = pers.getClass().getDeclaredFields();
		columnsNames = new ArrayList<>();
		//prevent including id field
		for (Field field : fields) {
			columnsNames.add(field.getName());
		}
	}

	public List<String> getColumnsNames() {
		return columnsNames;
	}

	public List<Person> getList() {
		return list;
	}

}
