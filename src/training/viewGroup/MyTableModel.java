package training.viewGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import training.modelGroup.MyDBDriver;

public class MyTableModel extends AbstractTableModel {

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
		switch (columnIndex) {
		case 0:
			return "#ID";
		case 1:
			return "Name";
		case 2:
			return "Lastname";
		case 3:
			return "Birthday";
		case 4:
			return "Job";
		case 5:
			return "Comment";
		}
		return "";
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
		//addDataToTable();
		fillTheTable();
	}


	public JSONArray getDataFromDB() {

		try {
			URL url = new URL("http://localhost:8080/DBServlet/dbUpdate");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String jString = "";
			String c;

			while ((c = rd.readLine()) != null) {
				jString += c;
			}

			JSONArray jsonArr = new JSONArray(jString);

			return jsonArr;

		} catch (MalformedURLException e) {
			System.out.println("Something bad with URL");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException ");
			e.printStackTrace();
		} catch (JSONException e) {
			System.out.println("Exception by creating JsonArray, source string is bad");
			e.printStackTrace();
		}
		return null;

	}
	
	
	public void fillTheTable () {
		JSONArray jArray=getDataFromDB();
		if (jArray !=null){
			try {
				for (int i = 0; i < jArray.length(); i++) {
				
					JSONObject object = jArray.getJSONObject(i);
					String[] row = {
							""+ object.getInt("USER_ID"),
							object.getString("FIRSTNAME"),
							object.getString("LASTNAME"),
							object.getString("BIRTH_DAY"),
							object.getString("JOB"),
							object.getString("COMMENT") };
					addDate(row);
				
				}
			} catch (JSONException e) {
				System.out.println("Exception by creating JsonObject");
				e.printStackTrace();
		}
		} else {
			System.out.println( "Somethig wrong with creating of JsonArray");
		}
		
	}
	

	// =============================================================== бпелеммши лернд!!
	public void addDataToTable() {
		MyDBDriver mcDrive = new MyDBDriver();
		ResultSet rs = mcDrive.getResultSet();

		try {
			while (rs.next()) {
				String id = rs.getString("user_id");
				String name = rs.getString("FIRSTNAME");
				String lastname = rs.getString("LASTNAME");

				DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

				String birth_day = null;
				if (rs.getDate("birth_day") != null) {
					birth_day = df.format(rs.getDate("birth_day"));
				}

				String job = rs.getString("JOB");
				String comment = rs.getString("COMMENT");

				String[] row = { id, name, lastname, birth_day, job, comment };
				addDate(row);

			}
		} catch (SQLException e) {
			System.out.println("Exceptions in MyTableModel.addDate");
			e.printStackTrace();
		} finally {
			mcDrive.releaseResources();

		}
	}

}
