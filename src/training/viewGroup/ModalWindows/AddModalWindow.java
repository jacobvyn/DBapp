package training.viewGroup.ModalWindows;

import java.awt.GridBagConstraints;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import training.viewGroup.FaceOfApp;
import training.viewGroup.listeners.AddWinOKButtonListener;
import training.viewGroup.listeners.MyCaretListener;

public class AddModalWindow {
	FaceOfApp face;

	JDialog addDialog;

	JLabel nameLabel;
	JLabel lastNameLabel;
	JLabel birthDayLabel;
	JLabel jobLabel;
	JLabel commentLabel;
	JLabel status;

	JButton okButton;
	JButton cancelButton;

	JTextField nameTextField;
	JTextField lastNameTextField;
	JTextField birthDayTextField;
	JTextField jobTextField;
	JTextField commentTextField;

	public AddModalWindow(FaceOfApp face) {
		this.face = face;
		addDialog = new JDialog(face, "Geben Sie bitte eine Persone ein!", true);
		addDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addDialog.setLayout(new GridBagLayout());
		addDialog.setSize(450, 250);

		makeLabelsButtonsAndTextFields();
		addDialog.setVisible(true);
		addDialog.pack();

	}

	private void makeLabelsButtonsAndTextFields() {
		nameLabel = new JLabel("Name");
		lastNameLabel = new JLabel("Lastname");
		birthDayLabel = new JLabel("Birth Day");
		jobLabel = new JLabel("Job");
		commentLabel = new JLabel("Comment");

		okButton = new JButton("Ok");
		cancelButton = new JButton("Cancel");

		nameTextField = new JTextField("This Field is required", 25);
		lastNameTextField = new JTextField(10);
		birthDayTextField = new JTextField(10);
		jobTextField = new JTextField(10);
		commentTextField = new JTextField(20);
		status = new JLabel("Status : clear");

		addDialog.add(nameLabel, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
		addDialog.add(nameTextField, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));

		addDialog.add(lastNameLabel, new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
		addDialog.add(lastNameTextField, new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));

		addDialog.add(birthDayLabel, new GridBagConstraints(0, 3, 1, 1, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
		addDialog.add(birthDayTextField, new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));

		addDialog.add(jobLabel, new GridBagConstraints(0, 4, 1, 1, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
		addDialog.add(jobTextField, new GridBagConstraints(1, 4, 1, 1, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));

		addDialog.add(commentLabel, new GridBagConstraints(0, 5, 1, 1, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
		addDialog.add(commentTextField, new GridBagConstraints(1, 5, 1, 1, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));

		addDialog.add(okButton, new GridBagConstraints(0, 6, 1, 1, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
		addDialog.add(cancelButton, new GridBagConstraints(1, 6, 1, 1, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));

		addDialog.add(status, new GridBagConstraints(0, 7, 0, 0, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addDialog.setVisible(false);
			}
		});

		okButton.addActionListener(new AddWinOKButtonListener(this));

		
		birthDayTextField.addCaretListener(new MyCaretListener(birthDayTextField, status));
		

	}

	public JTextField getNameTextField() {
		return nameTextField;
	}

	public JTextField getLastNameTextField() {
		return lastNameTextField;
	}

	public JTextField getBirthDayTextField() {
		return birthDayTextField;
	}

	public JTextField getJobTextField() {
		return jobTextField;
	}

	public JTextField getCommentTextField() {
		return commentTextField;
	}

	public JDialog getAddDialog() {
		return addDialog;
	}

	public FaceOfApp getFace() {
		return face;
	}


}
