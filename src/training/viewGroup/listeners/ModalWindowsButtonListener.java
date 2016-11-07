package training.viewGroup.listeners;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JTextField;

import org.json.JSONException;
import org.json.JSONObject;

import training.modelGroup.Person;
import training.modelGroup.ServletsCommunication;
import training.viewGroup.MyTableModelNew;
import training.viewGroup.ModalWindows.AddModalWindow;
import training.viewGroup.ModalWindows.ChangeModalWindow;
import training.viewGroup.ModalWindows.InputErrorModalWindow;

public class ModalWindowsButtonListener implements ActionListener {

	private static final String OK_ADD = "okAdd";
	private static final String OK_CHANGE = "okChange";
	private static final String CANCEL_ADD = "cancelAdd";
	private static final String CANCEL_CHANGE = "cancelChange";

	private AddModalWindow addModalWindow;
	private ChangeModalWindow changeModalWindow;
	private String birth_Day;

	public ModalWindowsButtonListener(AddModalWindow add, ChangeModalWindow change) {
		this.addModalWindow = add;
		this.changeModalWindow = change;

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();

		switch (command) {
		case OK_ADD:
			addButton_Logic();
			break;

		case OK_CHANGE:
			changeButton_Logic();
			break;

		case CANCEL_ADD:
			addModalWindow.getAddDialog().setVisible(false);
			break;

		case CANCEL_CHANGE:
			changeModalWindow.getChangeDialog().setVisible(false);
			break;

		default:
			break;
		}
	}

	private void addButton_Logic() {
		JSONObject addJObject = collectInfo();
		/*
		System.out.println("=================");
		Person person = collectInfoNEW();
		System.out.println(person);
		System.out.println("=================");
		*/
		ServletsCommunication.makeQueryByURL(ServletsCommunication.ADD_URL, addJObject);

		addModalWindow.getFace().repaint();
		addModalWindow.getAddDialog().setVisible(false);
	}

	private void changeButton_Logic() {

		JSONObject jObject = collectData_new();

		if (!(jObject.length() == 0)) {

			System.out.println("Next fields were changed");
			String[] names = JSONObject.getNames(jObject);
			for (String s : names) {
				System.out.print(s + ", ");
			}
			System.out.println();
			int user_id = changeModalWindow.getFace().getSelectedUserId();

			try {
				jObject.put("user_id", String.valueOf(user_id));
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			ServletsCommunication.makeQueryByURL(ServletsCommunication.CHANGE_URL, jObject);
			changeModalWindow.getFace().repaint();
		}
		changeModalWindow.getChangeDialog().setVisible(false);
	}

	// to make better or del
	private JSONObject collectData_new() {

		List<String> newValues = getNewValues();
		List<String> columnsNames = changeModalWindow.getColumnsNames();
		columnsNames.remove(0); // delete cell "user id"

		JSONObject jObject = new JSONObject();
		try {
			for (int i = 0; i < newValues.size(); i++) {
				if (i == 2 && (newValues.get(i).isEmpty()
						|| !ModalWindowsButtonListener.checkDateFormat(newValues.get(i)))) {
					jObject.put(columnsNames.get(i), "1970-01-01");
					new InputErrorModalWindow(changeModalWindow.getChangeDialog());
				} else {
					jObject.put(columnsNames.get(i), newValues.get(i));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jObject;
	}

	// to make better or del
	private JSONObject collectInfo() {
		ArrayList<JTextField> textFieldsList = addModalWindow.getTextFieldsList();
		List<String> columnsNames = addModalWindow.getColumnsNames();
		columnsNames.remove(0);

		JSONObject addJObject = new JSONObject();
		try {
			for (int i = 0; i < textFieldsList.size(); i++) {
				if (i == 2) {
					birth_Day = textFieldsList.get(i).getText();
					if (birth_Day.isEmpty()) {
						birth_Day = "1970-01-01";
						addJObject.put(columnsNames.get(i).toUpperCase(), birth_Day);
					} else if (!birth_Day.isEmpty() && !checkDateFormat(birth_Day)) {
						birth_Day = "1970-01-01";
						new InputErrorModalWindow(addModalWindow.getAddDialog());
						addJObject.put(columnsNames.get(i).toUpperCase(), birth_Day);
					} else if (!birth_Day.isEmpty() && checkDateFormat(birth_Day)) {
						addJObject.put(columnsNames.get(i).toUpperCase(), birth_Day);
					}
				} else
					addJObject.put(columnsNames.get(i).toUpperCase(), textFieldsList.get(i).getText());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println(addJObject);
		return addJObject;

	}

	// to make better or del
	private ArrayList<String> getNewValues() {
		ArrayList<JTextField> textFieldsList = changeModalWindow.getTextFieldsList();
		ArrayList<String> newValues = new ArrayList<>();
		for (JTextField field : textFieldsList) {
			newValues.add(field.getText());
		}
		return newValues;
	}

	// to make better or del
	public static boolean checkDateFormat(String value) {

		SimpleDateFormat sdf = new SimpleDateFormat(MyTableModelNew.DATE_PATTERN);
		Date date = null;

		try {
			date = sdf.parse(value);
			if (!value.equals(sdf.format(date))) {
				date = null;
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return date != null;
	}

	private Person collectInfoNEW() {
		ArrayList<JTextField> textFieldsList = addModalWindow.getTextFieldsList();
		List<Object> personAsList = collectDataFromJTextFields(textFieldsList);

		Person person = new Person();
		Method[] methods = person.getClass().getDeclaredMethods();
		ArrayList<Method> setters = getOnlySetters(methods);
		fromListToPerson(person, setters, personAsList);

		return null;

	}

	private ArrayList<Method> getOnlySetters(Method[] methods) {
		ArrayList<Method> setters = new ArrayList<>();
		for (Method method : methods) {
			String methodsName = method.getName();
			if (methodsName.contains("set") && !methodsName.contains("id")) {
				setters.add(method);
			}
		}
		return setters;
	}

	private void fromListToPerson(Person person, ArrayList<Method> setters, List<Object> personAsList) {
		List<String> columnsNames = addModalWindow.getColumnsNames();
		columnsNames.remove(0); // remove id column name

		for (int i = 0; i < setters.size(); i++) {
			String methodsName = setters.get(i).getName();
			String propertyName = columnsNames.get(i);
			if (methodsName.contains(propertyName)) {
				invokeMethod(person, setters.get(i), personAsList.get(i));
			}
		}

	}

	private void invokeMethod(Person person, Method method, Object object) {
		method.setAccessible(true);
		try {
			if (object instanceof String) {
				String value = (String) object;
				method.invoke(person, value);
			} else if (object instanceof Date) {
				Date date = (Date) object;
				method.invoke(person, date);
			}

		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

	}

	private List<Object> collectDataFromJTextFields(ArrayList<JTextField> textFieldsList) {
		List<Object> list = new ArrayList<>();
		for (JTextField field : textFieldsList) {
			String fieldsName = field.getName();
			if (fieldsName.contains("day")) {
				parseDate(field.getText(), list);
			} else {
				list.add(field.getText());
			}
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	private void parseDate(String toParse, List<Object> list) {

		Date date = parseDate(toParse);
		if (date == null) {
			list.add(new Date(1970, 0, 1));
		} else {
			list.add(date);
		}

	}

	private Date parseDate(String toParse) {
		Date date = null;
		try {
			date = MyTableModelNew.FORMATTER.parse(toParse);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
}