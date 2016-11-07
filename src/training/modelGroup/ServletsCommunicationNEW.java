package training.modelGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ServletsCommunicationNEW {

	public static final String HOST_AND_PORT = "http://localhost:8080";
	public static final String ADD_URL = HOST_AND_PORT + "/DBServlet/dbAdd";
	public static final String CHANGE_URL = HOST_AND_PORT + "/DBServlet/dbChange";
	public static final String DELETE_URL = HOST_AND_PORT + "/DBServlet/dbDelete";
	public static final String GET_DATA_URL = HOST_AND_PORT + "/DBServlet/dbGetData";

	public static void sendRequest(String httpVerb, JSONObject jObject) {
		try {
			String parameters = makeQueryFromObject(jObject);
			String totalQuery = httpVerb + "?" + parameters;
			URL serverURL = new URL(totalQuery);

			HttpURLConnection connect = (HttpURLConnection) serverURL.openConnection();

			System.out.println("[ServletsCommunication] App: Next query was send : " + totalQuery);
			printServersAnswer(connect);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<Person> getData(String url) {

		// create url, open connection and print servers answer
		HttpURLConnection connection = openConnection(url);

		// read data from input stream and return result string
		String jString = readData(connection);

		// create json array and convert to the list
		List<Person> list = fromJsonStringToList(jString);

		return list;

	}

	// =================================

	private static HttpURLConnection openConnection(String url) {

		URL serverUrl;
		HttpURLConnection connection = null;

		try {
			serverUrl = new URL(url);
			connection = (HttpURLConnection) serverUrl.openConnection();
			connection.setDoInput(true);
			printServersAnswer(connection);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return connection;
	}

	private static String readData(HttpURLConnection connection) {
		StringBuilder jString = null;
		try {

			InputStream inputStream = connection.getInputStream();
			InputStreamReader isr = new InputStreamReader(inputStream);
			BufferedReader br = new BufferedReader(isr);
			jString = new StringBuilder();
			String c;

			while ((c = br.readLine()) != null) {
				jString.append(c);
			}
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return jString.toString();
	}

	private static String makeQueryFromObject(JSONObject jObject) {
		StringBuilder query = new StringBuilder();
		String[] names = JSONObject.getNames(jObject);
		try {
			for (String key : names) {
				String value = String.valueOf(jObject.get(key));
				query.append(key);
				query.append("=");
				query.append(value);
				query.append("&");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		query = query.deleteCharAt(query.length() - 1);

		return query.toString();
	}

	private static List<Person> fromJsonStringToList(String jString) {
		JSONArray jArray = null;
		try {
			jArray = new JSONArray(jString.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (jArray == null) {
			System.out.println("jArray is null");
			return null;
		} else {
			return createListFromJson(jArray);
		}

	}

	private static List<Person> createListFromJson(JSONArray jArray) {
		List<Person> list = new ArrayList<>();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-mm-dd").create();
		for (int i = 0; i < jArray.length(); i++) {
			JSONObject record;
			try {
				record = jArray.getJSONObject(i);
				Person pers = gson.fromJson(record.toString(), Person.class);
				list.add(pers);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	private static void printServersAnswer(HttpURLConnection connection) {
		try {
			System.out.println("[ServletsCommunication] Getting data... " + connection.getResponseCode() + " "
					+ connection.getResponseMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
