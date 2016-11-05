package training.viewGroup.listeners;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTextField;

import org.json.JSONException;
import org.json.JSONObject;

import training.modelGroup.ServletsCommunication;
import training.viewGroup.ModalWindows.AddModalWindow;
import training.viewGroup.ModalWindows.ChangeModalWindow;
import training.viewGroup.ModalWindows.InputErrorModalWindow;

public class ModalWindowsButtonListener implements ActionListener {

	private final String OK_ADD = "okAdd";
	private final String OK_CHANGE = "okChange";
	private final String CANCEL_ADD = "cancelAdd";
	private final String CANCEL_CHANGE = "cancelChange";

	AddModalWindow addWindow;
	ChangeModalWindow changeWindow;
	String birth_Day;

	public ModalWindowsButtonListener(AddModalWindow add, ChangeModalWindow change) {
		this.addWindow = add;
		this.changeWindow = change;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();

		switch (command) {
		case OK_ADD:
			addButtonLogic();
			break;

		case OK_CHANGE:
			changeButtonLogic();
			break;

		case CANCEL_ADD:
			addWindow.getAddDialog().setVisible(false);
			break;

		case CANCEL_CHANGE:
			changeWindow.getChangeDialog().setVisible(false);
			break;
			
		default:
			break;
		}
	}

	private void addButtonLogic() {
		JSONObject addJObject = collectInfo();
		ServletsCommunication.makeQueryByURL(ServletsCommunication.ADD_URL, addJObject);

		addWindow.getFace().repaint();
		addWindow.getAddDialog().setVisible(false);
	}

	private JSONObject collectInfo() {
		ArrayList<JTextField> textFieldsList = addWindow.getTextFieldsList();
		ArrayList<String> columnsNames = addWindow.getColumnsNames();
		columnsNames.remove(0);

		JSONObject addJObject = new JSONObject();
		try {
			for (int i = 0; i < textFieldsList.size(); i++) {
				if (i == 2) {
					birth_Day = textFieldsList.get(i).getText();
					if (birth_Day.isEmpty()) {
						birth_Day = "1970-01-01";
						addJObject.put(columnsNames.get(i).toUpperCase(), birth_Day);
					} else if (!birth_Day.isEmpty() && !checkDateFormat(birth_Day, "-")) {
						birth_Day = "1970-01-01";
						new InputErrorModalWindow(addWindow.getAddDialog());
						addJObject.put(columnsNames.get(i).toUpperCase(), birth_Day);
					} else if (!birth_Day.isEmpty() && checkDateFormat(birth_Day, "-")) {
						addJObject.put(columnsNames.get(i).toUpperCase(), birth_Day);
					}
				} else
					addJObject.put(columnsNames.get(i).toUpperCase(), textFieldsList.get(i).getText());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(addJObject);
		return addJObject;

	}

	public static boolean checkDateFormat(String value, String separator) {
		String format = "yyyy" + separator + "mm" + separator + "dd";

		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(value);
			if (!value.equals(sdf.format(date))) {
				date = null;
			}
		} catch (ParseException ex) {
			// ex.printStackTrace();
		}
		return date != null;
	}

	private void changeButtonLogic() {

		JSONObject jObject = collectData_new();

		if (!(jObject.length() == 0)) {

			System.out.println("Next fields were changed");
			String[] names = JSONObject.getNames(jObject);
			for (String s : names) {
				System.out.print(s + ", ");
			}
			System.out.println();
			int user_id = changeWindow.getFace().getSelectedUserId();

			try {
				jObject.put("user_id", String.valueOf(user_id));
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			ServletsCommunication.makeQueryByURL(ServletsCommunication.CHANGE_URL, jObject);
			changeWindow.getFace().repaint();
		}
		changeWindow.getChangeDialog().setVisible(false);

	}

	private JSONObject collectData_new() {
		ArrayList<String> prevValues = changeWindow.getPrevValuesOfFields();
		ArrayList<String> newValues = getNewValues();
		ArrayList<String> columnsNames = changeWindow.getColumnsNames();
		columnsNames.remove(0); // delete cell "user id"

		JSONObject jObject = new JSONObject();
		try {
			for (int i = 0; i < newValues.size(); i++) {
				if (i == 2 && (newValues.get(i).isEmpty()
						|| !ModalWindowsButtonListener.checkDateFormat(newValues.get(i), "-"))) {
					jObject.put(columnsNames.get(i), "1970-01-01");
					new InputErrorModalWindow(changeWindow.getChangeDialog());
				} else if (!prevValues.get(i).equalsIgnoreCase(newValues.get(i))) {
					jObject.put(columnsNames.get(i), newValues.get(i));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jObject;
	}

	private ArrayList<String> getNewValues() {
		ArrayList<JTextField> textFieldsList = changeWindow.getTextFieldsList();
		ArrayList<String> newValues = new ArrayList<>();
		for (JTextField field : textFieldsList) {
			newValues.add(field.getText());
		}
		return newValues;
	}

}