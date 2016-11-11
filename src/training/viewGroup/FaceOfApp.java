package training.viewGroup;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import training.viewGroup.listeners.FaceButtonsListener;

public class FaceOfApp extends JFrame {
	private JButton add;
	private JButton change;
	private JButton del;
	private JButton exit;
	private JLabel countOfRecords;

	private int selectedUserId;
	private int selectedRow;

	private MyTableModel tableModel;
	private JTable table;

	private void createTable() {
		tableModel = new MyTableModel();
		table = new JTable(tableModel);

		JPanel paneltable = new JPanel();
		paneltable.setLayout(new FlowLayout());

		JScrollPane scrolPane = new JScrollPane(table);
		scrolPane.setPreferredSize(new Dimension(550, 500));
		paneltable.add(scrolPane);

		this.add(paneltable, BorderLayout.CENTER);
	}

	public FaceOfApp() {
		super("My first internet application");
		this.setSize(600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		createTable();
		createButtons();
		setSelectedUserIdAndRow();
		this.setVisible(true);
		this.pack();
		System.out.println("-------------- Aplication is created---------------------");
	}

	private void createButtons() {
		add = new JButton("Add");
		change = new JButton("Change");
		del = new JButton("Delete");
		exit = new JButton("Exit");
		countOfRecords = new JLabel();

		FaceButtonsListener listener = new FaceButtonsListener(this);
		change.addActionListener(listener);
		add.addActionListener(listener);
		del.addActionListener(listener);
		exit.addActionListener(listener);

		countOfRecords.setText("Records : " + tableModel.getRowCount());

		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		buttons.add(add);
		buttons.add(change);
		buttons.add(del);
		buttons.add(exit);
		buttons.add(countOfRecords);
		this.add(buttons, BorderLayout.SOUTH);

	}

	public void setSelectedUserIdAndRow() {

		ListSelectionModel selectedModel = table.getSelectionModel();
		selectedModel.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int[] selectedRows = table.getSelectedRows();
				selectedRow = selectedRows[0];
				Object id = tableModel.getValueAt(selectedRow, 0);
				// selectedUserId = Integer.valueOf((String)id);
				selectedUserId = (int) id;
			}
		});
	}

	public MyTableModel getTableModel() {
		return tableModel;
	}

	public String[] getSelectedPerson() {
		String[] selectedPers = tableModel.getRow(selectedRow);
		return selectedPers;
	}

	@Override
	public void repaint() {
		tableModel.refreshDataList();
		countOfRecords.setText("Records : " + tableModel.getRowCount());
		super.repaint();
	}

	public int getSelectedUserId() {
		return selectedUserId;
	}

	// ===========================================================================================================================
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new FaceOfApp();

			}
		});
	}

}
