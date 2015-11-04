package training.viewGroup.ModalWindows;

import java.awt.FlowLayout;
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
	JLabel msg;
	JLabel msg2;
	public InputErrorModalWindow(JDialog parent) {
		this.parent = parent;
		errorWindow = new JDialog(parent, "Input Error", true);
		errorWindow.setSize(400, 120);
		errorWindow.setLayout(new FlowLayout());
		
		msg = new JLabel("You have made a mistake while inputing a \"date\". ");
		msg2 = new JLabel("The record will be put to the databse without field \"date\"            ");
		ok = new JButton("OK");
		errorWindow.add(msg);
		errorWindow.add(msg2);
		errorWindow.add(ok);
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
