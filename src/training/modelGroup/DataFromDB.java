package training.modelGroup;

import java.util.ArrayList;

import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataFromDB {
	private TreeMap<Integer, ArrayList<String>> resultTreeMap;
	private int columnCount;
	private JSONObject columnsNames;
	private JSONArray jArray;

	public DataFromDB() {
		initialize();
	}

	private void initialize() {
		jArray = ServletsCommunication.getDataFromDB(ServletsCommunication.GET_DATA_URL);
		//System.out.println("[DataFromDB] Received : "+jArray);
		setColumnsNames();
		jsonArrayToTreeMap();

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

	public int getColumnCount() {
		columnCount = jArray.length();
		return columnCount;
	}

	public void setColumnsNames() {
		// get the object with tables' names and delete it from jArray
		try {
			
			columnsNames = jArray.getJSONObject(jArray.length() - 1);
			jArray.remove(jArray.length() - 1);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public JSONObject getColumnsNames() {
		return columnsNames;
	}

}
