package training.viewGroup.listeners;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JTextField;
import org.json.JSONException;
import org.json.JSONObject;
import training.modelGroup.Person;
import training.modelGroup.ServletsCommunication;
import training.viewGroup.FaceOfApp;
import training.viewGroup.MyTableModelNew;
import training.viewGroup.ModalWindows.DialogWindow;
import training.viewGroup.ModalWindows.InputErrorModalWindow;

public class DialogButtonListener implements ActionListener {

	private static final String OK_ADD = "okAdd";
	private static final String OK_CHANGE = "okChange";
	private static final String CANCEL = "cancel";

	private DialogWindow dialog;
	private String birth_Day;

	public DialogButtonListener(DialogWindow add) {
		this.dialog = add;
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

		case CANCEL:
			dialog.getDialog().setVisible(false);
			break;

		default:
			break;
		}
	}

	private void addButton_Logic() {
		JSONObject addJObject = collectInfo();
		
		Person person = collectInfoNEW();
		
		ServletsCommunication.makeQueryByURL(ServletsCommunication.ADD_URL, addJObject);
		repaintAndHide(dialog.getFace(), dialog.getDialog());
	}

	private void changeButton_Logic() {
		Person person = collectInfoNEW();
		

		int user_id = dialog.getFace().getSelectedUserId();
		person.setId(user_id);
		System.out.println(person.toString());

		//ServletsCommunication.makeQueryByURL(ServletsCommunication.CHANGE_URL, person);
		//ServletsCommunicationNEW.sendRequest(ServletsCommunicationNEW.CHANGE_URL, person);

		repaintAndHide(dialog.getFace(), dialog.getDialog());
	}

	// to make better or del ???????
	private JSONObject collectData_new() {

		List<String> newValues = getNewValues();
		List<String> columnsNames = dialog.getColumnsNames();
		// columnsNames.remove(0); // delete cell "user id"

		JSONObject jObject = new JSONObject();
		try {
			for (int i = 0; i < newValues.size(); i++) {
				if (i == 2 && (newValues.get(i).isEmpty() || !DialogButtonListener.checkDateFormat(newValues.get(i)))) {
					jObject.put(columnsNames.get(i), "1970-01-01");
					new InputErrorModalWindow(dialog.getDialog());
				} else {
					jObject.put(columnsNames.get(i), newValues.get(i));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jObject;
	}

	// ??????
	private JSONObject collectInfo() {
		ArrayList<JTextField> textFieldsList = dialog.getTextFieldsList();
		List<String> columnsNames = dialog.getColumnsNames();
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
						new InputErrorModalWindow(dialog.getDialog());
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

	// ????
	private ArrayList<String> getNewValues() {
		ArrayList<JTextField> textFieldsList = dialog.getTextFieldsList();
		ArrayList<String> newValues = new ArrayList<>();
		for (JTextField field : textFieldsList) {
			newValues.add(field.getText());
		}
		return newValues;
	}

	// new!!
	private Person collectInfoNEW() {
		ArrayList<JTextField> textFieldsList = dialog.getTextFieldsList();
		Map<String, Object> personAsMap = fromFieldsToMap(textFieldsList);

		Person person = new Person();
		Method[] methods = person.getClass().getDeclaredMethods();
		ArrayList<Method> setters = getOnlySetters(methods);
		fromListToPerson(person, setters, personAsMap);

		return person;

	}

	private ArrayList<Method> getOnlySetters(Method[] methods) {
		ArrayList<Method> setters = new ArrayList<>();
		for (Method method : methods) {
			String methodsName = method.getName();
			if (methodsName.contains("set") && !methodsName.contains("Id")) {
				setters.add(method);
			}
		}
		return setters;
	}

	private void fromListToPerson(Person person, ArrayList<Method> setters, Map<String, Object> map) {
		List<String> columnsNames = dialog.getColumnsNames();

		for (int i = 0; i < setters.size(); i++) {
			String methodsName = setters.get(i).getName();

			for (int j = 0; j < columnsNames.size(); j++) {
				String propertyName = columnsNames.get(j);

				if (methodsName.equalsIgnoreCase("set" + propertyName)) {
					Object value = map.get(propertyName);
					invokeMethod(person, setters.get(i), value);
					break;
				}
			}
		}

	}

	private Map<String, Object> fromFieldsToMap(ArrayList<JTextField> list) {
		List<String> columnsNames = dialog.getColumnsNames();
		Map<String, Object> map = new HashMap<>();

		for (JTextField field : list) {
			String fieldName = field.getName().toLowerCase();

			for (String propertyKey : columnsNames) {
				if (fieldName.equalsIgnoreCase(propertyKey)) {
					Object value = field.getText();
					if (propertyKey.toLowerCase().contains("day")) {
						parseDate(propertyKey, value, map);
					} else {
						map.put(propertyKey, value);
					}
					break;
				}
			}
		}
		return map;
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

	@SuppressWarnings("deprecation")
	private void parseDate(String key, Object value, Map<String, Object> list) {

		Date date = parseDate((String) value);
		if (date == null) {
			list.put(key, new Date(1970, 0, 1));
		} else {
			list.put(key, date);
		}

	}

	private Date parseDate(String toParse) {
		Date date = null;
		try {
			date = MyTableModelNew.FORMATTER.parse(toParse);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	private void repaintAndHide(FaceOfApp face, JDialog dialog) {
		face.repaint();
		dialog.setVisible(false);
	}

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
}