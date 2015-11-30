package training.viewGroup.listeners;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import training.modelGroup.MyDBDriver;
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

		JSONObject jObject = collectData();

		if (!(jObject.length() == 0)) {

			System.out.println("Next fields were changed");
			String[] names = JSONObject.getNames(jObject);
			for (String s : names) {
				System.out.print(s + ", ");
			}
			
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

	private JSONObject collectData() {

		String firstName = "FIRSTNAME";
		String lastName = "LASTNAME";
		String birthDay = "BIRTH_DAY";
		String job = "JOB";
		String comment = "COMMENT";

		String firstNameVal = parentsWindow.getNameTextField().getText();
		String lastNameVal = parentsWindow.getLastNameTextField().getText();
		String birthDayVal = parentsWindow.getBirthDayTextField().getText();
		String jobVal = parentsWindow.getJobTextField().getText();
		String commentVal = parentsWindow.getCommentTextField().getText();

		JSONObject jObject = new JSONObject();
		try {
			if (!firstNameVal.equals(parentsWindow.getPrevName())) {
				jObject.put(firstName, firstNameVal);
			}

			if (!lastNameVal.equals(parentsWindow.getPrevLastname())) {
				jObject.put(lastName, lastNameVal);
			}

			if (!jobVal.equals(parentsWindow.getPrevJob())) {
				jObject.put(job, jobVal);
			}

			if (!commentVal.equals(parentsWindow.getPrevComment())) {
				jObject.put(comment, commentVal);
			}

			if (birthDayVal.length() != 0
					&& (AddWinOKButtonListener.checkDateFormat(birthDayVal, "/")
							|| AddWinOKButtonListener.checkDateFormat(birthDayVal, "-"))
					&& !birthDayVal.equals(parentsWindow.getPrevBday())) {
				jObject.put(birthDay, birthDayVal);
			} else {
				jObject.put(birthDay, "1970-01-01");
				new InputErrorModalWindow(parentsWindow.getChangeDialog());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jObject;

	}

}
