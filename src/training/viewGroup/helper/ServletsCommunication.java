package training.viewGroup.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class ServletsCommunication {
	
	public static final String ADD_URL = "http://localhost:8080/DBServlet/dbAdd";
	public static final String CHANGE_URL = "http://localhost:8080/DBServlet/dbChange";
	public static final String DELETE_URL = "http://localhost:8080/DBServlet/dbDelete";
	public static final String GET_DATA_URL = "http://localhost:8080/DBServlet/dbGetData";

	public static void makeQueryByURL(String url, JSONObject jObject) {
		try {
			URL serverURL = new URL(url);
			HttpURLConnection connect = (HttpURLConnection) serverURL.openConnection();
			connect.setDoOutput(true);
			connect.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

			OutputStreamWriter out = new OutputStreamWriter(connect.getOutputStream());

			out.write(jObject.toString());
			out.close();

		} catch (MalformedURLException e) {
			System.out.println("Bad url  (AddWinOKButtonListener)");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Exception by opening Connection (AddWinOKButtonListener)");
			e.printStackTrace();
		}
	}

	public static JSONArray getDataFromDB(String url) {

		try {
			URL serverUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) serverUrl.openConnection();
			connection.setDoInput(true);
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String jString = "";
			String c;

			while ((c = br.readLine()) != null) {
				jString += c;
			}
			br.close();

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

	// checking method

	public static String getStringfromServlet(String url) {
		String jString = "";
		try {
			URL serverUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) serverUrl.openConnection();
			connection.setDoInput(true);
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String c;

			while ((c = br.readLine()) != null) {
				jString += c;
			}
			br.close();

			return jString;

		} catch (MalformedURLException e) {
			System.out.println("Something bad with URL");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException ");
			e.printStackTrace();
		}
		return null;

	}

}
