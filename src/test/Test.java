package test;

import java.awt.Point;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import training.modelGroup.Person;
import training.modelGroup.ServletsCommunication;
import training.viewGroup.MyTableModelNew;

public class Test {
	public static void main(String[] args) {
		/// makeTest1();
		// makeTest2();
		// makeTest3();
		//makeTest4();
		Point point = new Point(1, 2);
		System.out.println("first point " +point.toString());
		maketest5(point);
		System.out.println("second point " +point.toString());
	}

	private static void maketest5(Point point) {
		point.y=point.y+2;
	}

	private static void makeTest4() {
		MyTableModelNew table = new MyTableModelNew();
		List<String> list = table.getColumnsNames();
		for (String string : list) {
			System.out.println(string);
		}
	}

	private static void makeTest3() {
		MyTableModelNew table = new MyTableModelNew();

		List<Person> list = table.getList();

		list.sort(new Comparator<Person>() {

			@Override
			public int compare(Person p1, Person p2) {
				return ((Integer) p1.getId()).compareTo(p2.getId());
			}
		});

		for (Person person : list) {
			//System.out.println(person.toMyString());
		}

	}

	private static void makeTest2() {

		MyTableModelNew table = new MyTableModelNew();

		Person person = table.getList().get(1);
		Date date = person.getBirthDay();

		System.out.println(date);

		String pattern = "yyyy-mm-dd";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);

		String source = formatter.format(date);
		System.out.println(source);
	}

	private static void makeTest1() {
		List<Person> list = ServletsCommunication.getDataFromDbNEW(ServletsCommunication.GET_DATA_URL);
		for (Person person : list) {
			System.out.println(person);
		}
	}

}
