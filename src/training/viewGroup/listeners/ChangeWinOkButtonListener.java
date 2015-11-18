package training.viewGroup.listeners;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import training.modelGroup.MyDBDriver;

import training.viewGroup.ModalWindows.ChangeModalWindow;
import training.viewGroup.ModalWindows.InputErrorModalWindow;

public class ChangeWinOkButtonListener implements ActionListener {
	ChangeModalWindow parentsWindow;

	public ChangeWinOkButtonListener(ChangeModalWindow window) {
		this.parentsWindow = window;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

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

		ArrayList<String> fieldsList = new ArrayList<String>();
		ArrayList<String> valuesList = new ArrayList<String>();

		if (!firstNameVal.equals(parentsWindow.getPrevName())) {
			fieldsList.add(firstName);
			valuesList.add(firstNameVal);
		}

		if (!lastNameVal.equals(parentsWindow.getPrevLastname())) {
			fieldsList.add(lastName);
			valuesList.add(lastNameVal);
		}

		if (!jobVal.equals(parentsWindow.getPrevJob())) {
			fieldsList.add(job);
			valuesList.add(jobVal);
		}

		if (!commentVal.equals(parentsWindow.getPrevComment())) {
			fieldsList.add(comment);
			valuesList.add(commentVal);
		}

		if (birthDayVal.length() != 0
				&& (AddWinOKButtonListener.checkDateFormat(birthDayVal, "/")
						|| AddWinOKButtonListener.checkDateFormat(birthDayVal, "-"))
				&& !birthDayVal.equals(parentsWindow.getPrevBday())) {
			valuesList.add(birthDayVal);
			fieldsList.add(birthDay);
		}

		if (!fieldsList.isEmpty()) {
			System.out.println(birthDayVal);
			System.out.println("Next fields were changed");
			for (String s : fieldsList) {
				System.out.println(s);
			}

			int user_id = parentsWindow.getFace().getSelectedUserId();

			MyDBDriver mcDrive = new MyDBDriver();
			mcDrive.updateRecord(fieldsList, valuesList, user_id);
			mcDrive.releaseResources();

			parentsWindow.getFace().repaint();

		}

		if (birthDayVal.length() != 0 && !AddWinOKButtonListener.checkDateFormat(birthDayVal, "/")
				&& !AddWinOKButtonListener.checkDateFormat(birthDayVal, "-")) {
			new InputErrorModalWindow(parentsWindow.getChangeDialog());
		}

		parentsWindow.getChangeDialog().setVisible(false);

	}

}
