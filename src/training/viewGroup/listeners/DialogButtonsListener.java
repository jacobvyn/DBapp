package training.viewGroup.listeners;

import java.awt.Component;
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
import javax.swing.JTextField;

import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import training.modelGroup.Person;
import training.modelGroup.ServletsCommunication;
import training.modelGroup.ServletsCommunication.METHOD;
import training.viewGroup.MyTableModel;
import training.viewGroup.ModalWindows.DialogWindow;

public class DialogButtonsListener implements ActionListener {

	private static final String OK_ADD = "okAdd";
	private static final String OK_CHANGE = "okChange";
	private static final String CANCEL = "cancel";

	private DialogWindow dialog;

	public DialogButtonsListener(DialogWindow add) {
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

	/// ---------------------------------------
	private void addButton_Logic() {
		Person person = collectInformation();
		ServletsCommunication.sendRequest(person, METHOD.PUT);
		repaintAndHide();
	}

	private void changeButton_Logic() {
		Person person = collectInformation();
		int user_id = dialog.getFace().getSelectedUserId();
		person.setId(user_id);

		// check did something change?
		Person oldPers = dialog.getFace().getTableModel().getPersonByID(user_id);

		if (!person.equals(oldPers)) {
			ServletsCommunication.sendRequest(person, METHOD.POST);
			dialog.getFace().repaint();
		}
		dialog.getDialog().setVisible(false);

	}
	/// ---------------------------------------

	private Person collectInformation() {
		ArrayList<Component> textFieldsList = dialog.getTextFieldsList();
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

	private Map<String, Object> fromFieldsToMap(ArrayList<Component> textFieldslist) {
		List<String> columnsNames = dialog.getColumnsNames();
		Map<String, Object> map = new HashMap<>();

		for (Component field : textFieldslist) {
			Object value = null;

			if (field instanceof JDatePickerImpl) {
				Date date = (Date) ((JDatePickerImpl) field).getModel().getValue();
				value = dateToString(date);

			} else if (field instanceof JTextField) {
				value = ((JTextField) field).getText();
			}

			String fieldName = field.getName().toLowerCase();

			for (String propertyKey : columnsNames) {
				if (fieldName.equalsIgnoreCase(propertyKey)) {

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

	private Object dateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(MyTableModel.DATE_PATTERN);
		return sdf.format(date);
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
			date = MyTableModel.FORMATTER.parse(toParse);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	private void repaintAndHide() {
		dialog.getFace().repaint();
		dialog.getDialog().setVisible(false);
	}

	public static boolean checkDateFormat(String value) {

		SimpleDateFormat sdf = new SimpleDateFormat(MyTableModel.DATE_PATTERN);
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