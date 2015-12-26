package training.viewGroup;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.table.AbstractTableModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import training.modelGroup.ServletsCommunication;

public class MyTableModel extends AbstractTableModel {
	JSONArray jArray;
	JSONObject columnsNames;

	private int columnCount = 6;
	ArrayList<String[]> dataList;

	public MyTableModel() {
		dataList = new ArrayList<String[]>();
		for (int i = 0; i < dataList.size(); i++) {
			dataList.add(new String[getColumnCount()]);
		}
	}

	@Override
	public int getRowCount() {
		return dataList.size();
	}

	@Override
	public int getColumnCount() {
		return columnCount;
	}

	@Override
	public String getColumnName(int columnIndex) {

		try {
			columnIndex++;
			return columnsNames.getString(String.valueOf(columnIndex));
		} catch (JSONException e) {
			System.out.println("Exception by getting columns names (MyTableModel)");
		}

		return null;

	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String[] row = dataList.get(rowIndex);
		return row[columnIndex];
	}

	public String[] getRow(int rowIndex) {
		String[] row = dataList.get(rowIndex);
		return row;

	}

	public void addDate(String[] row) {
		String[] rowTable = new String[getColumnCount()];
		rowTable = row;
		dataList.add(rowTable);
	}

	public void refreshDataList() {
		dataList.clear();
		fillTheTable();
	}

	public void fillTheTable() {
		TreeMap<Integer, ArrayList<String>> resultTreeMap = jsonArrayToTreeMap();
		Set<Entry<Integer, ArrayList<String>>> entries = resultTreeMap.entrySet();

		for (Entry<Integer, ArrayList<String>> entry : entries) {
			ArrayList<String> row = entry.getValue();
			addDate(row.toArray(new String[row.size()]));
		}

	}

	/*
	 * public void fillTheTable111() { try { if (jArray != null) {
	 * 
	 * for (int i = 0; i < jArray.length(); i++) {
	 * 
	 * JSONObject record = jArray.getJSONObject(i); ArrayList<String> row = new
	 * ArrayList<>(); String cellsContent;
	 * 
	 * for (int j = 1; j <= columnsNames.length(); j++) { cellsContent =
	 * columnsNames.getString(String.valueOf(j));
	 * row.add(record.getString(cellsContent)); } addDate(row.toArray(new
	 * String[row.size()])); } } else { System.out.println(
	 * "Something wrong with creating of JsonArray (MyTableModel.fillTheTable)"
	 * ); } } catch (JSONException e) { System.out.println(
	 * "Exception by creating JsonObject"); e.printStackTrace(); }
	 * 
	 * }
	 * 
	 */

	private TreeMap<Integer, ArrayList<String>> jsonArrayToTreeMap() {
		jArray = ServletsCommunication.getDataFromDB(ServletsCommunication.GET_DATA_URL);

		// sort jArray seperate as method
		TreeMap<Integer, ArrayList<String>> resultTreeMap = new TreeMap<Integer, ArrayList<String>>();

		if (jArray != null) {
			try {
				// get the object with tables' names and delete it from jArray
				columnsNames = jArray.getJSONObject(jArray.length() - 1);
				jArray.remove(jArray.length() - 1);

				for (int i = 0; i < jArray.length(); i++) {

					JSONObject record = jArray.getJSONObject(i);
					ArrayList<String> row = new ArrayList<String>();
					String cellsContent;

					for (int j = 1; j <= columnsNames.length(); j++) {
						cellsContent = columnsNames.getString(String.valueOf(j));
						row.add(record.getString(cellsContent));
					}

					resultTreeMap.put(record.getInt("user_id"), row);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("The result received from server is empty");
		}
		return resultTreeMap;

	}

}
