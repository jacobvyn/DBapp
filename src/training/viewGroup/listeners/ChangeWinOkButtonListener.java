package training.viewGroup.listeners;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JTextField;

import org.json.JSONException;
import org.json.JSONObject;

import training.modelGroup.ServletsCommunication;
import training.viewGroup.ModalWindows.ChangeModalWindow;
import training.viewGroup.ModalWindows.InputErrorModalWindow;

public class ChangeWinOkButtonListener implements ActionListener {
	ChangeModalWindow parentsWindow;

	public ChangeWinOkButtonListener(ChangeModalWindow window) {
		this.parentsWindow = window;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JSONObject jObject = collectData_new();

		if (!(jObject.length() == 0)) {

			System.out.println("Next fields were changed");
			String[] names = JSONObject.getNames(jObject);
			for (String s : names) {
				System.out.print(s + ", ");
			}
			System.out.println();

			int user_id = parentsWindow.getFace().getSelectedUserId();

			try {
				jObject.put("user_id", String.valueOf(user_id));
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			ServletsCommunication.makeQueryByURL(ServletsCommunication.CHANGE_URL, jObject);
			parentsWindow.getFace().repaint();

		}

		parentsWindow.getChangeDialog().setVisible(false);

	}

	private ArrayList<String> getNewValues() {
		ArrayList<JTextField> textFieldsList = parentsWindow.getTextFieldsList();
		ArrayList<String> newValues = new ArrayList<>();
		for (JTextField field : textFieldsList) {
			newValues.add(field.getText());
		}
		return newValues;
	}

	private JSONObject collectData_new() {
		ArrayList<String> prevValues = parentsWindow.getPrevValuesOfFields();
		ArrayList<String> newValues = getNewValues();
		ArrayList<String> columnsNames = parentsWindow.getColumnsNames();
		columnsNames.remove(0); // delete cell "user id"

		JSONObject jObject = new JSONObject();
		try {
			for (int i = 0; i < newValues.size(); i++) {
				if (i == 2 && (newValues.get(i).isEmpty()
						|| !AddWinOKButtonListener.checkDateFormat(newValues.get(i), "-"))) {
					jObject.put(columnsNames.get(i), "1970-01-01");
					new InputErrorModalWindow(parentsWindow.getChangeDialog());

				} else if (!prevValues.get(i).equalsIgnoreCase(newValues.get(i))) {
					jObject.put(columnsNames.get(i), newValues.get(i));
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jObject;
	}

}
