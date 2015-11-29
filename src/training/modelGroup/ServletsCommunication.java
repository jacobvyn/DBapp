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

	public static void makeQueryByURL(String url, JSONObject jObject) {
		try {
			
			
			
			String query = makeQueryFromObject(jObject);
			String totalQuery = url +"?"+ query ;
			URL serverURL = new URL(totalQuery);
	
			HttpURLConnection connect = (HttpURLConnection) serverURL.openConnection();
			
			System.out.println("App: Next query was send : " + totalQuery);
			System.out.println("ServletsCommunication - Answer form server : " +connect.getResponseCode() + " " +connect.getResponseMessage());
			
			
			/*
			//connect.setDoOutput(true);
			//connect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			//connect.setRequestProperty("charset", "UTF-8");
			
			try (OutputStream out = connect.getOutputStream()){
				out.write(query.getBytes("UTF-8"));
				out.flush();
			}
			*/
			
			
			
			/*
			String answer= null;
			String line= null;
			try (BufferedReader read = new BufferedReader(new InputStreamReader(connect.getInputStream()))){
				
				while ((line = read.readLine())!=null){
					answer += line;
				}
				
			}
			
			
			
			System.out.println("App: Next query was send : " + totalQuery);
			System.out.println("App: received from servlet : " +answer );
			*/

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
			
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			StringBuilder jString = new StringBuilder();
			String c;

			
			while ((c = br.readLine()) != null) {
				jString.append(c);
			}
			br.close();

			JSONArray jsonArr = new JSONArray(jString.toString());
			return jsonArr;

		} catch (MalformedURLException e) {
			System.out.println("Something bad with URL");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException ");
			e.printStackTrace();
		} catch (JSONException e) {
			System.out.println("Exception by creating JsonArray, source string is bad (ServletsCommunication.getDataFromDB)");
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
/// переделать метод что бы провер€л наличие пустых параметров----------------------- или объект приходит со всеми пол€ми?
	private static String makeQueryFromObject(JSONObject jObject) {
		StringBuilder query = new StringBuilder();
		String[] names = JSONObject.getNames(jObject);
		System.out.println("List of keys in json object");
		for (String s: names) System.out.print( s +" ");
		System.out.println();
		System.out.println();

		try {
			for (String key : names) {
				String value = String.valueOf(jObject.get(key));
				//if (!value.isEmpty()) {
					query.append(key);
					query.append("=");
					query.append(value);
					query.append("&");
				//}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		query = query.deleteCharAt(query.length() - 1);

		return query.toString();

	}
	


/*
	public static void main(String[] args) {
	  
	  JSONObject jObject = new JSONObject();
	  try { 
		  jObject.put("firstName","Jacob");
		  jObject.put("lastName","Vin");
		  jObject.put("job", "Engineer");
		  jObject.put("comment", "29");
		  jObject.put("birthDay", "12/12/1212");
	  
	   ServletsCommunication.makeQueryByURL(ServletsCommunication.ADD_URL, jObject);
	  
	  } catch (JSONException e) { 
	  e.printStackTrace(); }
	  
	  }
	*/
	
	
	/*
	
	public static void main(String[] args) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("firstName", "Jacob");
			int postDataLength = obj.length();

			System.out.println("sent from here :" + obj);

			URL serverURL = new URL(ServletsCommunication.ADD_URL);
			HttpURLConnection connect = (HttpURLConnection) serverURL.openConnection();

			connect.setDoOutput(true);
			connect.setRequestMethod("POST");
			connect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connect.setRequestProperty("charset", "UTF_8");
			connect.setRequestProperty("Content-Length", Integer.toString(postDataLength));

			OutputStream out =connect.getOutputStream();

			out.write(obj.toString().getBytes());
			out.close();

			System.out.println("received from servlet : " + ServletsCommunication.getStringfromServlet(ADD_URL));

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	*/
	
	

	/*
	 * 
	 * public static void main(String[] args) { JSONObject obj = new
	 * JSONObject(); try { obj.put("NAME", "Jacob"); int postDataLength =
	 * obj.length();
	 * 
	 * System.out.println("sent from here :" + obj);
	 * 
	 * URL serverURL = new URL(ServletsCommunication.ADD_URL); HttpURLConnection
	 * connect = (HttpURLConnection) serverURL.openConnection();
	 * 
	 * connect.setDoOutput(true); connect.setRequestMethod("POST");
	 * connect.setRequestProperty("Content-Type", "application/json");
	 * //"application/x-www-form-urlencoded"
	 * connect.setRequestProperty("charset", "UTF-8");
	 * connect.setRequestProperty("Content-Length",
	 * Integer.toString(postDataLength));
	 * 
	 * OutputStream out =connect.getOutputStream(); InputStream response =
	 * connect.getInputStream();
	 * 
	 * 
	 * 
	 * out.write(obj.toString().getBytes("UTF-8")); out.flush(); out.close();
	 * response.close();
	 * 
	 * System.out.println("App received from servlet : " +
	 * ServletsCommunication.getStringfromServlet(ADD_URL+"?name1=value1"));
	 * 
	 * } catch (JSONException e) { // 
	 * e.printStackTrace(); } catch (MalformedURLException e) { //  * Auto-generated catch block e.printStackTrace(); } catch
	 * (ProtocolException e) { // 
	 * e.printStackTrace(); } catch (IOException e) { // 	 * catch block e.printStackTrace(); }
	 * 
	 * }
	 */
}
