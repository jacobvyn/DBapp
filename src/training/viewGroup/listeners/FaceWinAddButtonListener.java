package training.viewGroup.listeners;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import training.viewGroup.FaceOfApp;
import training.viewGroup.ModalWindows.*;

public class FaceWinAddButtonListener implements ActionListener {

	FaceOfApp face;

	public FaceWinAddButtonListener(FaceOfApp face) {
		this.face = face;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new AddModalWindow(face);

	}

}
