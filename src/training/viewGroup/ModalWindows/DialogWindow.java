package training.viewGroup.ModalWindows;

import java.awt.Component;
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

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
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
	// 1
	private ArrayList<Component> textFieldsList;
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

		setTitleByMode(mode);
		populateTextFields(mode);

		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dialog.setSize(450, 250);
		dialog.setVisible(true);
		dialog.pack();

	}

	private void setTitleByMode(MODE mode) {
		switch (mode) {

		case ADD:
			dialog.setTitle("New person");
			okButton.setActionCommand("okAdd");
			break;

		case CHANGE:
			dialog.setTitle("What information  would you like to change?");
			okButton.setActionCommand("okChange");
			break;

		default:
			break;
		}

	}

	private void populateTextFields(MODE mode) {
		switch (mode) {
		case ADD:
			// 2
			for (Component component : textFieldsList) {

				if (component instanceof JTextField) {
					JTextField field = (JTextField) component;
					if (field.getName().contains("First")) {
						field.setText("Required");
					}
					/*
					 * if (field.getName().contains("day")) {
					 * field.setText(MyTableModel.DEFAULT_DATE); }
					 */
				}
			}

			break;
		case CHANGE:
			personTochange = face.getSelectedPerson();
			for (int i = 0; i < textFieldsList.size(); i++) {
				Component component = textFieldsList.get(i);
				if (component instanceof JTextField) {
					JTextField field = (JTextField) component;
					field.setText(personTochange[i + 1]);
				} else if (component instanceof JDatePickerImpl) {
					JDatePickerImpl picker = (JDatePickerImpl) component;
					String date = personTochange[i + 1];
					if (!date.equals(MyTableModel.DEFAULT_DATE)) {
						int year = getInt(date, "y");
						int mounth = getInt(date, "m");
						int day = getInt(date, "d");
						picker.getModel().setDate(year, mounth, day);
						System.out.println(picker.getModel().getValue());
					}
				}

			}
			break;

		default:
			break;
		}

	}

	private int getInt(String date, String what) {
		String str = null;
		switch (what) {
		case "y":
			str = (String) date.subSequence(0, 4);
			break;
		case "m":
			str = (String) date.subSequence(5, 7);
			break;
		case "d":
			str = (String) date.subSequence(8, 10);
			break;

		default:
			break;
		}

		int value = Integer.valueOf(str);
		return value;

	}

	private void createLabelsAndTextFields() {

		int fieldsAmount = columnsNames.size();

		// start form index 1 to prevent creating of jtexField of ID
		for (int i = 1; i < fieldsAmount; i++) {
			String name = DialogWindow.makeNice(columnsNames.get(i));
			labelsList.add(new JLabel(name));
			if (name.contains("day")) {
				JDatePickerImpl picker = createPicker();
				picker.setName(name);
				textFieldsList.add(picker);
			} else {
				JTextField field = new JTextField(15);
				field.setName(name);
				textFieldsList.add(field);
			}

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
		status = new JLabel("Hint : the date format should be " + MyTableModel.DATE_PATTERN);

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

	public ArrayList<Component> getTextFieldsList() {
		return textFieldsList;
	}

	public List<String> getColumnsNames() {
		return columnsNames;
	}

	private JDatePickerImpl createPicker() {
		UtilDateModel mod = new UtilDateModel();
		mod.setDate(1970, 0, 1);
		mod.setSelected(true);
		JDatePanelImpl panel = new JDatePanelImpl(mod);
		JDatePickerImpl picker = new JDatePickerImpl(panel, new DateLabelFormatter());
		return picker;
	}
}
