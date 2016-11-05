package training.modelGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ServletsCommunication {

	public static final String HOST_AND_PORT = "http://localhost:8080";
	public static final String ADD_URL = HOST_AND_PORT + "/DBServlet/dbAdd";
	public static final String CHANGE_URL = HOST_AND_PORT + "/DBServlet/dbChange";
	public static final String DELETE_URL = HOST_AND_PORT + "/DBServlet/dbDelete";
	public static final String GET_DATA_URL = HOST_AND_PORT + "/DBServlet/dbGetData";

	public static void makeQueryByURL(String httpVerb, JSONObject jObject) {
		try {
			String parameters = makeQueryFromObject(jObject);
			String totalQuery = httpVerb + "?" + parameters;
			URL serverURL = new URL(totalQuery);

			HttpURLConnection connect = (HttpURLConnection) serverURL.openConnection();
			
			System.out.println("[ServletsCommunication] App: Next query was send : " + totalQuery);
			int responseCode=connect.getResponseCode();
			String responseCodeMessage =connect.getResponseMessage();
			System.out.println("[ServletsCommunication]  Answer from server : " + responseCode + " " + responseCodeMessage);

		} catch (MalformedURLException e) {
			System.out.println("Bad url  (ServletsCommunication.makeQueryByURL)");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Exception by opening Connection (ServletsCommunication.makeQueryByURL)");
			e.printStackTrace();
		}
	}

	public static JSONArray getDataFromDB(String url) {

		try {
			URL serverUrl = new URL(url);

			HttpURLConnection connection = (HttpURLConnection) serverUrl.openConnection();

			connection.setDoInput(true);
			System.out.println("[ServletsCommunication] Getting data... Response code is "
					+ connection.getResponseCode() + " " + connection.getResponseMessage());

			InputStreamReader isr = new InputStreamReader(connection.getInputStream());

			BufferedReader br = new BufferedReader(isr);

			StringBuilder jString = new StringBuilder();
			String c;

			while ((c = br.readLine()) != null) {
				jString.append(c);
			}
			br.close();

			JSONArray jsonArr = new JSONArray(jString.toString());
			System.out.println("[ServletsCommunication] Data is loaded succsesfully!");
			System.out.println("-------");
			return jsonArr;

		} catch (MalformedURLException e) {
			System.out.println("Something bad with URL");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException. Failed by creating bufferde reader");
			e.getCause();
			e.getLocalizedMessage();
			e.getMessage();
			e.printStackTrace();
		} catch (JSONException e) {
			System.out.println(
					"Exception by creating JsonArray, source string is bad (ServletsCommunication.getDataFromDB)");
			e.printStackTrace();
		}
		return null;

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

	public static void printData(String url) {

		URL serverUrl;
		try {
			serverUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) serverUrl.openConnection();
			System.out.println("Connection is opend!");
			connection.setDoInput(true);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
