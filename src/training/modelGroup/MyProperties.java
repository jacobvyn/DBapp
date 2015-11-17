package training.modelGroup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class MyProperties {
	private String login;
	private String password;
	
	public MyProperties() throws FileNotFoundException, IOException {
		Properties prop = new Properties();
		FileInputStream in = new FileInputStream(new File("D:\\Education\\Java eclipse\\DBServlet\\info.txt"));
		prop.load(in);
		in.close();
		
		login = prop.getProperty("login");
		password = prop.getProperty("pass");
	}
	
	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

}
