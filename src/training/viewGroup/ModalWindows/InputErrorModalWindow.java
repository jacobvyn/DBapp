package training.viewGroup.ModalWindows;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class InputErrorModalWindow {
	JDialog parent;
	JDialog errorWindow;
	JButton ok;

	public InputErrorModalWindow(JDialog parent) {
		this.parent = parent;
		errorWindow = new JDialog(parent, "Input Error", true);
		errorWindow.setSize(350, 120);
		errorWindow.setLayout(new BorderLayout());

		ok = new JButton("OK");

		errorWindow.add(new JLabel("You have made a mistake while inputing a \"date\" "), BorderLayout.NORTH);
		errorWindow.add(new JLabel("This field will be set as \"1970-01-01\"."), BorderLayout.CENTER);
		errorWindow.add(ok, BorderLayout.SOUTH);

		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				errorWindow.setVisible(false);
			}
		});

		errorWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		errorWindow.setVisible(true);
		errorWindow.pack();
	}

}
