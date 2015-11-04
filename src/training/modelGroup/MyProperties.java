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
		prop.load(new FileInputStream(new File("info.txt")));
		
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
