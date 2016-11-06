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

import training.viewGroup.listeners.FaceButtonListener;

public class FaceOfApp extends JFrame {
	private JButton add;
	private JButton change;
	private JButton del;
	private JButton exit;
	private JLabel countOfRecords;

	private int selectedUserId;
	private int selectedRow;

	//========================================= 1
	private MyTableModelNew tableModel;
	private JTable table;

	public FaceOfApp() {
		super("My first internet application");
		this.setSize(600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		makeTable();
		crateButtons();
		setSelectedUserIdAndRow();
		this.setVisible(true);
		this.pack();
	}

	private void makeTable() {
		//========================================= 2
		tableModel = new MyTableModelNew();

		table = new JTable(tableModel);

		JPanel paneltable = new JPanel();
		paneltable.setLayout(new FlowLayout());

		JScrollPane scrolPane = new JScrollPane(table);
		scrolPane.setPreferredSize(new Dimension(600, 500));
		paneltable.add(scrolPane);

		this.add(paneltable, BorderLayout.CENTER);

	}

	private void crateButtons() {
		add = new JButton("Add");
		change = new JButton("Change");
		del = new JButton("Delete");
		exit = new JButton("Exit");
		countOfRecords = new JLabel();

		FaceButtonListener listener = new FaceButtonListener(this);
		change.addActionListener(listener);
		add.addActionListener(listener);
		del.addActionListener(listener);
		exit.addActionListener(listener);

		countOfRecords.setText("Records : " + tableModel.getRowCount());

		JPanel panelButtons = new JPanel();
		panelButtons.setLayout(new FlowLayout());

		panelButtons.add(add);
		panelButtons.add(change);
		panelButtons.add(del);
		panelButtons.add(exit);
		panelButtons.add(countOfRecords);
		this.add(panelButtons, BorderLayout.SOUTH);

	}

	public int getSelectedUserId() {
		return selectedUserId;
	}

	public void setSelectedUserIdAndRow() {

		ListSelectionModel selModel = table.getSelectionModel();
		selModel.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int[] selectedRows = table.getSelectedRows();
				selectedRow = selectedRows[0];
				Object user_id = tableModel.getValueAt(selectedRow, 0);
				//selectedUserId = Integer.valueOf((String) user_id);
				selectedUserId = (int)  user_id;

			}
		});
	}

	// =========================================3
	public MyTableModelNew getTableModel() {
		return tableModel;
	}

	public String[] getSelectedPerson() {
		String[] selectedPers = tableModel.getRow(selectedRow);
		return selectedPers;
	}

	public int getSelecteRow() {
		return selectedRow;
	}

	@Override
	public void repaint() {
		tableModel.refreshDataList();
		countOfRecords.setText("Records : " + tableModel.getRowCount());
		super.repaint();
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
