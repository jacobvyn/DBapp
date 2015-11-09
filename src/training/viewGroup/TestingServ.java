package training.viewGroup;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;



public class TestingServ {
	
	public static void main(String[] args) {
		try {
			URL url = new URL("http://localhost:8080/DBServlet/dbUpdate");
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			//BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			BufferedInputStream bufferedStream = new BufferedInputStream(connection.getInputStream());
			
			String jString="";
			int c;
		
			
			while ((c=bufferedStream.read())!=0) {
				jString+=(char)c;
			}
			
			System.out.println(jString);
		
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	


	}
	
	public static ArrayList<String[]> listFromJsonResultList( JsonArray jArray) {
		
		ArrayList<String []> list = new ArrayList<String[]>();
		

		for (int i =0; i<jArray.size(); i++) {
			JsonObject object = jArray.get(i).getAsJsonObject();
			String [] row ={
					object.get("USER_ID").toString(),
					object.get("FIRSTNAME").toString(),
					object.get("LASTNAME").toString(),
					object.get("BIRTH_DAY").toString(),
					object.get("JOB").toString(),
					object.get("COMMENT").toString()
			};
			list.add(row);
		}
		return list;
	}
	
}
