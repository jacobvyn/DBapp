package test;

import java.util.List;

import training.modelGroup.Person;
import training.modelGroup.ServletsCommunication;
import training.viewGroup.MyTableModelNew;

public class Test {
	public static void main(String[] args) {
		///makeTest1();
		makeTest2();
	}

	private static void makeTest2() {
		
		MyTableModelNew table = new MyTableModelNew();
		
		Person person = table.getList().get(5);
		String [] arr = table.asArray(person);
		
		List<Object> list = table.asList(person);
		for (String string : arr) {
			System.out.print(string+" ");
		}
		System.out.println(list);
		
	}

	private static void makeTest1() {
		List<Person> list = ServletsCommunication.getDataFromDbNEW(ServletsCommunication.GET_DATA_URL);
		for (Person person : list) {
			System.out.println(person);
		}
	}

}
