package training.viewGroup;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

import training.viewGroup.listeners.FaceWinAddButtonListener;
import training.viewGroup.listeners.FaceWinChangeButtonListener;
import training.viewGroup.listeners.FaceWinDeLButtonListener;

public class FaceOfApp extends JFrame {
	private JButton add;
	private JButton change;
	private JButton del;
	private JButton exit;
	private JLabel countOfRecords;

	private int selectedUserId;
	private int selecteRow;

	private MyTableModel tableModel;
	private JTable table;
	


	
	public FaceOfApp() {
		super("My first internet-application");
		this.setSize(500, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		makeTable();
		makeButtons();
		setSelectedUserIdAndRow();
		this.setVisible(true);
		this.pack();
	}

	private void makeTable() {

		tableModel = new MyTableModel();
		tableModel.fillTheTable();
		
		table = new JTable(tableModel);

		JPanel paneltable = new JPanel();
		paneltable.setLayout(new FlowLayout());

		JScrollPane scrolPane = new JScrollPane(table);
		scrolPane.setPreferredSize(new Dimension(400, 400));
		paneltable.add(scrolPane);

		this.add(paneltable, BorderLayout.CENTER);

	}

	private void makeButtons() {
		add = new JButton("Add");
		change = new JButton("Change");
		del = new JButton("Delete");
		exit = new JButton("Exit");
		countOfRecords = new JLabel();

		change.addActionListener(new FaceWinChangeButtonListener(this));
		add.addActionListener(new FaceWinAddButtonListener(this));
		del.addActionListener(new FaceWinDeLButtonListener(this));
	

		countOfRecords.setText("Records : " +tableModel.getRowCount());
		
		JPanel panelButtons = new JPanel();
		panelButtons.setLayout(new FlowLayout());

		panelButtons.add(add);
		panelButtons.add(change);
		panelButtons.add(del);
		panelButtons.add(exit);
		panelButtons.add(countOfRecords);
		this.add(panelButtons, BorderLayout.SOUTH);

		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});
	}

	public JTable getTable() {
		return table;
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
				selecteRow = selectedRows[0];
				Object user_id = tableModel.getValueAt(selecteRow, 0);
				selectedUserId = Integer.valueOf((String) user_id);

			}
		});
	}

	public MyTableModel getTableModel() {
		return tableModel;
	}

	public String[] getSelectedPerson() {
		String[] selectedPers = tableModel.getRow(selecteRow);
		return selectedPers;
	}

	public int getSelecteRow() {
		return selecteRow;
	}
	
	@Override
	public void repaint() {
		tableModel.refreshDataList();
		countOfRecords.setText("Records : " +tableModel.getRowCount());
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
