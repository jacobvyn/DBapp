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
import training.viewGroup.listeners.DialogButtonListener;
import training.viewGroup.listeners.MyCaretListener;

public class ChangeModalWindow {
	private FaceOfApp face;
	private JDialog changeDialog;

	private JButton okButton;
	private JButton cancelButton;
	private JLabel status;

	private ArrayList<JLabel> labelsList;
	private ArrayList<JTextField> textFieldsList;
	private List<String> columnsNames;

	/// **** previous values to check changes
	private ArrayList<String> prevValuesOfFields;
	// +
	private String[] selectedPersonTochange;

	public ChangeModalWindow(FaceOfApp face) {
		this.face = face;
		columnsNames = face.getTableModel().getColumnsNames();
		// +
		selectedPersonTochange = face.getSelectedPerson();

		changeDialog = new JDialog(face, "What information  would you like to change?", true);
		changeDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		changeDialog.setLayout(new GridBagLayout());
		changeDialog.setSize(450, 250);

		
		
		createLabelsAndTextFields();
		createButtons();
		setPreviousValues();

		changeDialog.setVisible(true);
		changeDialog.pack();
	}

	private void setPreviousValues() {
		prevValuesOfFields = new ArrayList<>();
		for (String s : selectedPersonTochange) {
			prevValuesOfFields.add(s);
		}
		prevValuesOfFields.remove(0);

	}

	private void createLabelsAndTextFields() {
		int fieldsAmount = columnsNames.size();

		for (int i = 1; i < fieldsAmount; i++) {
			String name = DialogWindow.makeNice(columnsNames.get(i));
			labelsList.add(new JLabel(name));
			textFieldsList.add(new JTextField(selectedPersonTochange[i], 15));
		}

		for (int i = 0; i < fieldsAmount - 1; i++) {
			changeDialog.add(labelsList.get(i), new GridBagConstraints(0, i + 1, 1, 1, 1, 1, GridBagConstraints.NORTH,
					GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
			changeDialog.add(textFieldsList.get(i), new GridBagConstraints(1, i + 1, 1, 1, 1, 1,
					GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
		}

	}

	private void createButtons() {
		int lastPosition = columnsNames.size();
		okButton = new JButton("Ok");
		cancelButton = new JButton("Cancel");
		status = new JLabel("Status : clear");

		changeDialog.add(okButton, new GridBagConstraints(0, lastPosition + 2, 1, 1, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
		changeDialog.add(cancelButton, new GridBagConstraints(1, lastPosition + 2, 1, 1, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
		changeDialog.add(status, new GridBagConstraints(0, lastPosition + 3, 0, 0, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));

		/*
		DialogButtonListener listener = new DialogButtonListener(null, this);
		cancelButton.setActionCommand("cancelChange");
		cancelButton.addActionListener(listener);

		okButton.setActionCommand("okChange");
		okButton.addActionListener(listener);
*/
		
		textFieldsList.get(2).addCaretListener(new MyCaretListener(textFieldsList.get(2), status));
	}

	public JDialog getChangeDialog() {
		return changeDialog;
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

	public ArrayList<String> getPrevValuesOfFields() {
		return prevValuesOfFields;
	}

}
