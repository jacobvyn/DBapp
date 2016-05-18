package training.viewGroup;

import java.util.ArrayList;

import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.table.AbstractTableModel;

import org.json.JSONException;
import org.json.JSONObject;

import training.modelGroup.DataFromDB;
import training.viewGroup.ModalWindows.AddModalWindow;

public class MyTableModel extends AbstractTableModel {

	private ArrayList<String> columnsNames;
		private ArrayList<String[]> dataList;
	private TreeMap<Integer, ArrayList<String>> resultTreeMap;

	private int columnCount;// = 6;

	public MyTableModel() {
		initialize();
	}

	private void initialize() {
		DataFromDB data = new DataFromDB();
		resultTreeMap = data.getResultTreeMap();
		columnsNames = jsonToArrayList(data.getColumnsNames());
		columnCount = columnsNames.size();
		dataList = new ArrayList<String[]>();
		fillTheTable();

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
		if (columnIndex==0) return "ID";
		else 
		return AddModalWindow.makeNice(columnsNames.get(columnIndex));

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
		initialize();
	}

	public void fillTheTable() {
		Set<Entry<Integer, ArrayList<String>>> entries = resultTreeMap.entrySet();

		for (Entry<Integer, ArrayList<String>> entry : entries) {
			ArrayList<String> row = entry.getValue();
			addDate(row.toArray(new String[row.size()]));
		}

	}

	private ArrayList<String> jsonToArrayList(JSONObject ob) {
		ArrayList<String> columnNames = new ArrayList<>();
		// i was 1
		for (int i = 0; i < ob.length(); i++) {
			try {
				columnNames.add(ob.getString(String.valueOf(i)));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return columnNames;
	}
	
	public ArrayList<String> getColumnsNames() {
		return columnsNames;
	}

}
