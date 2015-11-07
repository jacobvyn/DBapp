package training.viewGroup.listeners;

import java.awt.event.ActionEvent;


import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
*/
import training.modelGroup.MyDBDriver;
import training.viewGroup.ModalWindows.*;

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

		MyDBDriver md = new MyDBDriver();
		
		if (checkDateFormat(birthDay, "/")  || checkDateFormat(birthDay, "-") ) {
			md.addRecord(firstName, lastName, birthDay, job, comment);
		} 
	/*	else if (birthDay.length() == 0) {
			md.addRecord(firstName, lastName, job, comment);
		}*/
		else {
			md.addRecord(firstName, lastName, job, comment);
			new InputErrorModalWindow(parentsWindow.getAddDialog());
		}

		md.releaseResources();

		parentsWindow.getFace().getTableModel().refreshDataList();
		parentsWindow.getFace().repaint();
		parentsWindow.getAddDialog().setVisible(false);

	}
/*
	public static boolean checkDateFormat(String date, String separator) {

		if (date.matches("([0-9]{2})"+separator +"([0-9]{2})"+separator +"([0-9]{4})") ) {
			return true;
		}
		return false;
	} 
	*/
	 public static boolean checkDateFormat(String value, String separator) {
		 
		 String format="dd" +separator +"mm" +separator +"yyyy";
		 
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
