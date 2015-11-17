package training.viewGroup.listeners;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import training.modelGroup.MyDBDriver;
import training.viewGroup.FaceOfApp;
import training.viewGroup.helper.ServletsCommunication;

public class FaceWinDeLButtonListener implements ActionListener {

	FaceOfApp face;

	public FaceWinDeLButtonListener(FaceOfApp face) {
		this.face = face;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MyDBDriver drive = new MyDBDriver();
		drive.deleteRecord(face.getSelectedUserId());
	
		face.repaint();
		
		drive.releaseResources();
		System.out.println("ID #" +(face.getSelectedUserId()) +" is deleted");
		face.setSelectedUserIdAndRow();
		/////// to delete!!!
		System.out.println(ServletsCommunication.getStringfromServlet(ServletsCommunication.CHANGE_URL));
		

	}

}