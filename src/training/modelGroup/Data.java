package training.modelGroup;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import training.modelGroup.ServletsCommunication.METHOD;

public class Data {

	private List<Person> list;
	private List<String> columnsNames;

	public Data() {
		list = ServletsCommunication.sendRequest(null, METHOD.GET);
		setColumnsNames();
	}

	private void setColumnsNames() {
		Person pers = list.get(0);
		Field[] fields = pers.getClass().getDeclaredFields();
		columnsNames = new ArrayList<>();
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
