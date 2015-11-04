package training.viewGroup.listeners;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import training.modelGroup.MyDBDriver;
import training.viewGroup.ModalWindows.ChangeModalWindow;

public class ChangeWinOkButtonListener implements ActionListener {
	ChangeModalWindow window;

	public ChangeWinOkButtonListener(ChangeModalWindow window) {
		this.window = window;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String firstName = "FIRSTNAME";
		String lastName = "LASTNAME";
		String birthDay = "BIRTH_DAY";
		String job = "JOB";
		String comment = "COMMENT";
		
		//String [] fields = {firstName, lastName, birthDay, job, comment};
		
		ArrayList<String> fieldsList = new ArrayList<String>();
		fieldsList.add(firstName);
		fieldsList.add(lastName);
		
		
	
		String firstNameVal = window.getNameTextField().getText();
		String lastNameVal = window.getLastNameTextField().getText();
		String birthDayVal = window.getBirthDayTextField().getText();
		String jobVal =window.getJobTextField().getText();
		String commentVal = window.getCommentTextField().getText();
		 
		 if (lastNameVal.length()==0) lastName="";
		
		 if (jobVal.length()==0) job="";
		 if (commentVal.length()==0) comment="";
		
		
		//String [] values ={firstName, lastName, birthDay,job, comment};
		
		 ArrayList<String> valuesList = new ArrayList<String>();
		 valuesList.add(firstNameVal);
		 valuesList.add(lastNameVal);
		 if (birthDayVal.length()!=0){
			 valuesList.add(birthDayVal);
			 fieldsList.add(birthDay);
		 }
		 valuesList.add(jobVal);
		 valuesList.add(commentVal);
		 
		 fieldsList.add(job);
			fieldsList.add(comment);
		
		if (birthDayVal.length()==0){
			fieldsList.remove(birthDay);
			valuesList.remove(birthDayVal);
			
		}
		
		/*
		//------------
				for (int i = 0; i < fieldsList.size(); i++) {
					System.out.println( fieldsList.get(i));
					
				}
				
				
		//-------------
		*/
		
		int user_id = window.getFace().getSelectedUserId();
		
		MyDBDriver md = new MyDBDriver();
		md.updateRecord(fieldsList, valuesList, user_id);
		md.releaseResources();
		
		window.getFace().getTableModel().refreshDataList();
		window.getFace().repaint();
		window.getChangeDialog().setVisible(false);

	}

}
