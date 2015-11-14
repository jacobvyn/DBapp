package training.viewGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
	

		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (JSONException e) {
			System.out.println("Exception by creating JsonArray, string is bad");
			e.printStackTrace();
		}

	}



}
