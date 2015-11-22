package training.viewGroup.listeners;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import training.modelGroup.ServletsCommunication;
import training.viewGroup.ModalWindows.AddModalWindow;
import training.viewGroup.ModalWindows.InputErrorModalWindow;


public class AddWinOKButtonListener implements ActionListener {
	AddModalWindow parentsWindow;

	public AddWinOKButtonListener(AddModalWindow window) {
		this.parentsWindow = window;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		String firstName = parentsWindow.getNameTextField().getText();
		String lastName = parentsWindow.getLastNameTextField().getText();
		String birthDay = parentsWindow.getBirthDayTextField().getText();
		String job = parentsWindow.getJobTextField().getText();
		String comment = parentsWindow.getCommentTextField().getText();

		JSONObject addJObject = new JSONObject();

		try {
			addJObject.put("firstName", firstName);
			addJObject.put("lastName", lastName);
			addJObject.put("birth_Day", birthDay);
			addJObject.put("job", job);
			addJObject.put("comment", comment);

		} catch (JSONException e) {
			System.out.println("Something wrong by creating addQuery string (AddWinOKButtonListener)");
			e.printStackTrace();
		}

		if (!birthDay.isEmpty() && ( !checkDateFormat(birthDay, "/") || !checkDateFormat(birthDay, "-"))  ) {
			addJObject.remove("birth_Day");
			new InputErrorModalWindow(parentsWindow.getAddDialog());
		}
		
		ServletsCommunication.makeQueryByURL(ServletsCommunication.ADD_URL, addJObject);

		parentsWindow.getFace().repaint();
		parentsWindow.getAddDialog().setVisible(false);

	}

	public static boolean checkDateFormat(String value, String separator) {

		String format = "dd" + separator + "mm" + separator + "yyyy";

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
