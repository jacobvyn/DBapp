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
import training.viewGroup.MyTableModel;
import training.viewGroup.listeners.DialogButtonsListener;

public class DialogWindow {

	public static enum MODE {
		ADD, CHANGE;
	}

	private FaceOfApp face;
	private JDialog dialog;

	private ArrayList<JLabel> labelsList;
	private ArrayList<JTextField> textFieldsList;
	private List<String> columnsNames;

	private JLabel status;
	private JButton okButton;
	private JButton cancelButton;
	private String[] personTochange;

	public DialogWindow(FaceOfApp face, MODE mode) {
		this.face = face;
		columnsNames = face.getTableModel().getColumnsNames();

		init(mode);
	}

	private void init(MODE mode) {
		
		dialog = new JDialog(face, true);
		dialog.setLayout(new GridBagLayout());

		labelsList = new ArrayList<>();
		textFieldsList = new ArrayList<>();

		createLabelsAndTextFields();
		createButtons();

		switch (mode) {

		case ADD:
			dialog.setTitle("New person");
			populateTextFields(mode);
			okButton.setActionCommand("okAdd");
			break;

		case CHANGE:
			dialog.setTitle("What information  would you like to change?");
			populateTextFields(mode);
			okButton.setActionCommand("okChange");
			break;

		default:
			break;
		}

		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dialog.setSize(450, 250);
		dialog.setVisible(true);
		dialog.pack();

	}

	private void populateTextFields(MODE mode) {
		switch (mode) {
		case ADD:

			for (JTextField field : textFieldsList) {
				if (field.getName().contains("First")) {
					field.setText("Required");
				}
				if (field.getName().contains("day")) {
					field.setText(MyTableModel.DEFAULT_DATE);
				}
			}

			break;
		case CHANGE:
			personTochange = face.getSelectedPerson();
			for (int i = 0; i < textFieldsList.size(); i++) {
				textFieldsList.get(i).setText(personTochange[i + 1]);
			}
			break;

		default:
			break;
		}
	}

	private void createLabelsAndTextFields() {

		int fieldsAmount = columnsNames.size();

		// start form index 1 to prevent creating of jtexField of ID
		for (int i = 1; i < fieldsAmount; i++) {
			String name = DialogWindow.makeNice(columnsNames.get(i));
			labelsList.add(new JLabel(name));
			JTextField field = new JTextField(15);
			field.setName(name);
			textFieldsList.add(field);
		}

		// putt all to the layout
		for (int i = 0; i < fieldsAmount - 1; i++) {
			dialog.add(labelsList.get(i), new GridBagConstraints(0, i + 1, 1, 1, 1, 1, GridBagConstraints.NORTH,
					GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
			dialog.add(textFieldsList.get(i), new GridBagConstraints(1, i + 1, 1, 1, 1, 1, GridBagConstraints.NORTH,
					GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
		}
	}

	private void createButtons() {
		int lastPosition = columnsNames.size();
		okButton = new JButton("Ok");
		cancelButton = new JButton("Cancel");
		status = new JLabel("Hint : the date format should be \"yyyy-mm-dd\"");

		dialog.add(okButton, new GridBagConstraints(0, lastPosition + 2, 1, 1, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
		dialog.add(cancelButton, new GridBagConstraints(1, lastPosition + 2, 1, 1, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));

		dialog.add(status, new GridBagConstraints(0, lastPosition + 3, 0, 0, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));

		DialogButtonsListener listener = new DialogButtonsListener(this);
		cancelButton.setActionCommand("cancel");
		cancelButton.addActionListener(listener);
		okButton.addActionListener(listener);

	}

	public static String makeNice(String str) {
		String newStr = str.toUpperCase().charAt(0) + str.toLowerCase().substring(1, str.length());
		return newStr.replace('_', ' ');
	}

	public JDialog getDialog() {
		return dialog;
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
