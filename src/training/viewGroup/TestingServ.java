package training.viewGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;

public class TestingServ {

	public static void main(String[] args) {
		try {
			URL url = new URL("http://localhost:8080/DBServlet/dbUpdate");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			// BufferedInputStream bufferedStream = new
			// BufferedInputStream(connection.getInputStream());

			String jString = "";
			String c;

			while ((c = rd.readLine()) != null) {
				jString += c;
			}
			
			JSONArray arr = new JSONArray(jString);
			System.out.println(arr);
	

		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (JSONException e) {
			System.out.println("Exception by creating JsonArray, string is bad");
			e.printStackTrace();
		}

	}

	/*
	private TreeMap<Integer, ArrayList<String>> jsonArrayToTreeMap() {
		jArray = ServletsCommunication.getDataFromDB(ServletsCommunication.GET_DATA_URL);

		columnCount = jArray.length();

		// sort jArray seperate as method TreeMap<Integer, ArrayList<String>>
		resultTreeMap = new TreeMap<Integer, ArrayList<String>>();

		if (jArray != null) {
			try {
				// get the object with tables' names an delete it from jArray
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
*/


}
