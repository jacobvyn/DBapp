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
import training.viewGroup.ModalWindows.InputErrorModalWindow;

public class AddWinOKButtonListener implements ActionListener {
	AddModalWindow parentsWindow;
	String birth_Day;

	public AddWinOKButtonListener(AddModalWindow window) {
		this.parentsWindow = window;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		JSONObject addJObject = collectInfo();

		ServletsCommunication.makeQueryByURL(ServletsCommunication.ADD_URL, addJObject);

		parentsWindow.getFace().repaint();
		parentsWindow.getAddDialog().setVisible(false);

	}

	private JSONObject collectInfo() {
		ArrayList<JTextField> textFieldsList = parentsWindow.getTextFieldsList();
		ArrayList<String> columnsNames = parentsWindow.getColumnsNames();
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
						new InputErrorModalWindow(parentsWindow.getAddDialog());
						addJObject.put(columnsNames.get(i).toUpperCase(), birth_Day);
					}

				} else
					addJObject.put(columnsNames.get(i).toUpperCase(), textFieldsList.get(i).getText());

			}
		} catch (

		JSONException e)

		{
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

}
