package test;

import java.util.List;

import training.modelGroup.Person;
import training.modelGroup.ServletsCommunication;

public class Test {
	public static void main(String[] args) {
		makeTest1();
	}

	private static void makeTest1() {
		List<Person> list = ServletsCommunication.getDataFromDbNEW(ServletsCommunication.GET_DATA_URL);
		for (Person person : list) {
			System.out.println(person);
		}
	}

}
