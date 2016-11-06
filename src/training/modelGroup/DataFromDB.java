package training.modelGroup;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataFromDB {
	private TreeMap<Integer, ArrayList<String>> resultTreeMap;
	private JSONObject columnsNames;
	private JSONArray jArray;
	private List<Person> listNew;
	private ArrayList<String> columnsNamesNEW;

	public DataFromDB() {
		init();
	}

	public DataFromDB(String does_not_matter) {
		initNew();
	}

	private void init() {
		jArray = ServletsCommunication.getDataFromDB(ServletsCommunication.GET_DATA_URL);
		setColumnsNames();
		jsonArrayToTreeMap();
	}

	private void initNew() {
		listNew = ServletsCommunication.getDataFromDbNEW(ServletsCommunication.GET_DATA_URL);
		setColumnsNamesNEW(listNew.get(0));
	}

	private void jsonArrayToTreeMap() {

		resultTreeMap = new TreeMap<Integer, ArrayList<String>>();

		if (jArray != null) {
			try {
				for (int i = 0; i < jArray.length(); i++) {

					JSONObject record = jArray.getJSONObject(i);
					ArrayList<String> row = new ArrayList<String>();
					String cellsContent;

					for (int j = 0; j < columnsNames.length(); j++) {

						cellsContent = columnsNames.getString(String.valueOf(j));
						row.add(record.getString(cellsContent));
					}

					resultTreeMap.put(record.getInt("id"), row);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}

	public TreeMap<Integer, ArrayList<String>> getResultTreeMap() {
		return resultTreeMap;
	}

	private void setColumnsNames() {
		// get the object with tables' names and delete it from jArray
		try {

			columnsNames = jArray.getJSONObject(jArray.length() - 1);
			jArray.remove(jArray.length() - 1);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void setColumnsNamesNEW(Person pers) {
		Field[] fields = pers.getClass().getDeclaredFields();
		columnsNamesNEW = new ArrayList<>();

		for (Field field : fields) {
			columnsNamesNEW.add(field.getName());
		}
	}

	public JSONObject getColumnsNames() {
		return columnsNames;
	}

	public ArrayList<String> getColumnsNamesNEW() {
		return columnsNamesNEW;
	}

	public List<Person> getListNew() {
		return listNew;
	}

}
