package training.viewGroup.listeners;

import java.awt.event.ActionEvent;


import java.awt.event.ActionListener;

import training.modelGroup.MyDBDriver;
import training.modelGroup.ServletsCommunication;
import training.viewGroup.FaceOfApp;


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
		System.out.println("was received by DEL: "+ServletsCommunication.getStringfromServlet(ServletsCommunication.DELETE_URL));
		

	}

}