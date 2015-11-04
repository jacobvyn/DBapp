package training.viewGroup.listeners;

import java.awt.event.ActionEvent;


import java.awt.event.ActionListener;
/*
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
*/
import training.modelGroup.MyDBDriver;
import training.viewGroup.ModalWindows.*;

public class AddWinOKButtonListener implements ActionListener {
	AddModalWindow window;

	public AddWinOKButtonListener(AddModalWindow window) {
		this.window = window;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		String firstName = window.getNameTextField().getText();
		String lastName = window.getLastNameTextField().getText();
		String birthDay = window.getBirthDayTextField().getText();
		
		String job = window.getJobTextField().getText();
		String comment = window.getCommentTextField().getText();

		MyDBDriver md = new MyDBDriver();
		
		if (checkDateFormat(birthDay, "/")  || checkDateFormat(birthDay, "-") ) {
			md.addRecord(firstName, lastName, birthDay, job, comment);
		} 
		else if (birthDay.length() == 0) {
			md.addRecord(firstName, lastName, job, comment);
		}
		else {
			md.addRecord(firstName, lastName, job, comment);
			new InputErrorModalWindow(window.getAddDialog());
		}

		md.releaseResources();

		window.getFace().getTableModel().refreshDataList();
		window.getFace().repaint();
		window.getAddDialog().setVisible(false);

	}

	public static boolean checkDateFormat(String date, String sign) {

		if (date.matches("([0-9]{2})"+sign +"([0-9]{2})"+sign +"([0-9]{4})") ) {
			return true;
		}
		return false;
	}

}
