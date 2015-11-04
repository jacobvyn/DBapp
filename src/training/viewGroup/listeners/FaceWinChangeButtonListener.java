package training.viewGroup.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import training.viewGroup.FaceOfApp;
import training.viewGroup.ModalWindows.ChangeModalWindow;

public class FaceWinChangeButtonListener implements ActionListener {

	FaceOfApp face;

	public FaceWinChangeButtonListener(FaceOfApp face) {
		this.face = face;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new ChangeModalWindow(face);

	}

}