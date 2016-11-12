package training.modelGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ServletsCommunication {
	public static enum METHOD {
		GET, PUT, POST, DELETE;
	}

	private static final String HOST_AND_PORT = "http://localhost:8080";
	public static final String CHANGE_URL = HOST_AND_PORT + "/DBServlet/dbChange";
	public static final String GET_URL = HOST_AND_PORT + "/DBServlet/dbGetData";

	public static List<Person> sendRequest(Person person, METHOD method) {

		switch (method) {
		case GET:
			HttpURLConnection connect = openConnection(GET_URL, method);
			List<Person> list = sendRequestGET(connect);
			printServersAnswer(connect);
			return list;

		case PUT:
		case POST:
		case DELETE:
			HttpURLConnection conn = openConnection(CHANGE_URL, method);
			sendRequestXXX(conn, person);
			printServersAnswer(conn);
			return null;

		default:
			return null;
		}

	}

	// ===================================================
	private static List<Person> sendRequestGET(HttpURLConnection conn) {

		String jString = readData(conn);
		List<Person> list = fromJsonStringToList(jString);
		return list;

	}

	private static void sendRequestXXX(HttpURLConnection connection, Person person) {
		OutputStream outputStream = null;
		try {
			outputStream = connection.getOutputStream();
			log("output stream created");
			String jsonString = toJson(person);
			log("Sending message ... " + jsonString);
			outputStream.write(jsonString.getBytes());

		} catch (IOException e) {
			log("IOException raised");
			e.printStackTrace();
		} finally {
			try {
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	// =================================

	private static HttpURLConnection openConnection(String url, METHOD method) {
		URL serverUrl;
		HttpURLConnection connection = null;

		try {
			serverUrl = new URL(url);
			connection = (HttpURLConnection) serverUrl.openConnection();
			connection.setRequestMethod(method.toString());
			connection.setDoOutput(true);
			// printServersAnswer(connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	private static String readData(HttpURLConnection connection) {
		StringBuilder jString = null;
		try {
			// log("goin to create reader");
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			// log("reader is created");
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

	private static List<Person> fromJsonStringToList(String jString) {
		JSONArray jArray = null;
		try {
			jArray = new JSONArray(jString.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return createListFromJson(jArray);
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
			System.out.println("=======>>>>>> [ServletsCommunication] Getting data... " + connection.getResponseCode()
					+ " " + connection.getResponseMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String toJson(Person person) {
		Gson gson = new GsonBuilder().create();
		return gson.toJson(person);
	}

	private static void log(String string) {
		System.out.println("=======>>>>>> " + string);
	}

}
