package training.viewGroup.ModalWindows;

import java.awt.GridBagConstraints;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import training.viewGroup.FaceOfApp;
import training.viewGroup.listeners.ModalWindowsButtonListener;
import training.viewGroup.listeners.MyCaretListener;

public class AddModalWindow {
	private FaceOfApp face;
	private JDialog addDialog;

	private ArrayList<JLabel> labelsList;
	private ArrayList<JTextField> textFieldsList;
	private List<String> columnsNames;

	private JLabel status;
	private JButton okButton;
	private JButton cancelButton;

	public AddModalWindow(FaceOfApp face) {
		this.face = face;
		columnsNames = face.getTableModel().getColumnsNames();

		addDialog = new JDialog(face, "Input information about the person", true);
		addDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addDialog.setLayout(new GridBagLayout());
		addDialog.setSize(450, 250);

		createLabelsAndTextFields();
		createButtons();

		addDialog.setVisible(true);
		addDialog.pack();

	}

	private void createLabelsAndTextFields() {
		labelsList = new ArrayList<>();
		textFieldsList = new ArrayList<>();

		int columnsCount = columnsNames.size();

		for (int i = 1; i < columnsCount; i++) {
			String name = makeNice(columnsNames.get(i));
			labelsList.add(new JLabel(name));
			if (i == 1) {
				textFieldsList.add(new JTextField("Required", 15));
			} else {
				textFieldsList.add(new JTextField(15));
			}
		}

		for (int i = 0; i < columnsCount - 1; i++) {

			addDialog.add(labelsList.get(i), new GridBagConstraints(0, i + 1, 1, 1, 1, 1, GridBagConstraints.NORTH,
					GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));

			addDialog.add(textFieldsList.get(i), new GridBagConstraints(1, i + 1, 1, 1, 1, 1, GridBagConstraints.NORTH,
					GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
		}

	}

	private void createButtons() {
		int lastPosition = columnsNames.size();
		okButton = new JButton("Ok");
		cancelButton = new JButton("Cancel");
		status = new JLabel("Status : clear");

		addDialog.add(okButton, new GridBagConstraints(0, lastPosition + 2, 1, 1, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
		addDialog.add(cancelButton, new GridBagConstraints(1, lastPosition + 2, 1, 1, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));

		addDialog.add(status, new GridBagConstraints(0, lastPosition + 3, 0, 0, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));

		ModalWindowsButtonListener listener = new ModalWindowsButtonListener(this, null);
		cancelButton.setActionCommand("cancelAdd");
		cancelButton.addActionListener(listener);

		okButton.setActionCommand("okAdd");
		okButton.addActionListener(listener);

		textFieldsList.get(2).addCaretListener(new MyCaretListener(textFieldsList.get(2), status));

	}

	public static String makeNice(String str) {
		String newStr = str.toUpperCase().charAt(0) + str.toLowerCase().substring(1, str.length());
		return newStr.replace('_', ' ');
	}

	public JDialog getAddDialog() {
		return addDialog;
	}

	public FaceOfApp getFace() {
		return face;
	}

	public ArrayList<JTextField> getTextFieldsList() {
		return textFieldsList;
	}

	public List<String> getColumnsNames() {
		return columnsNames;
	}

}
