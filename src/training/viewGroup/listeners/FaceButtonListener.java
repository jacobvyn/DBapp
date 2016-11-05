package training.viewGroup.listeners;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import org.json.JSONException;
import org.json.JSONObject;

import training.modelGroup.ServletsCommunication;
import training.viewGroup.FaceOfApp;
import training.viewGroup.ModalWindows.*;

public class FaceButtonListener implements ActionListener {

	private static final String ADD_BUTTON = "Add";
	private static final String CHANGE_BUTTON = "Change";
	private static final String DELETE_BUTTON = "Delete";
	private static final String EXIT_BUTTON = "Exit";

	FaceOfApp face;

	public FaceButtonListener(FaceOfApp face) {
		this.face = face;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		switch (command) {
		case ADD_BUTTON:
			new AddModalWindow(face);
			break;

		case CHANGE_BUTTON:
			new ChangeModalWindow(face);
			break;

		case DELETE_BUTTON:
			deleteLogic(e);
			break;

		case EXIT_BUTTON:
			System.exit(0);
			break;

		default:
			break;
		}

	}

	private void deleteLogic(ActionEvent e) {
		JSONObject jObject = new JSONObject();

		try {
			jObject.put("toDelete", face.getSelectedUserId());
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		ServletsCommunication.makeQueryByURL(ServletsCommunication.DELETE_URL, jObject);
		face.repaint();
		face.setSelectedUserIdAndRow();
		System.out.println("ID #" + (face.getSelectedUserId()) + " is deleted");
	}
}
