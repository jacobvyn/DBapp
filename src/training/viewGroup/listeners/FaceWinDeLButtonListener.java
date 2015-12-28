package training.viewGroup.listeners;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import org.json.JSONException;
import org.json.JSONObject;
import training.modelGroup.ServletsCommunication;
import training.viewGroup.FaceOfApp;

public class FaceWinDeLButtonListener implements ActionListener {

	FaceOfApp face;

	public FaceWinDeLButtonListener(FaceOfApp face) {
		this.face = face;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JSONObject jObject = new JSONObject();

		try {
			jObject.put("toDelete", face.getSelectedUserId());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ServletsCommunication.makeQueryByURL(ServletsCommunication.DELETE_URL, jObject);

		face.repaint();
		face.setSelectedUserIdAndRow();

		System.out.println("ID #" + (face.getSelectedUserId()) + " is deleted");

	}

}