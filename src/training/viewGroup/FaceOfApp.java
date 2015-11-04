package training.viewGroup;

import java.awt.Dimension;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
//import javax.swing.JLabel;
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

	private int selectedUserId;
	private int selecteRow;

	

	private MyTableModel tableModel;
	private JTable table;

	public FaceOfApp() {
		super("My first internet-application");
		this.setSize(700, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());

		makeTable();
		makeButtons();
		setSelectedUserIdAndRow();
		// changeStatus();
		this.setVisible(true);
		this.pack();
	}

	private void makeTable() {

		tableModel = new MyTableModel();
		tableModel.addDataToTable();
		table = new JTable(tableModel);
		JScrollPane scrolPane = new JScrollPane(table);
		scrolPane.setPreferredSize(new Dimension(400, 400));

		this.add(scrolPane, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.BOTH,
				new Insets(3, 3, 3, 3), 100, 0));

	}

	private void makeButtons() {
		add = new JButton("Add");
		change = new JButton("Change");
		del = new JButton("Delete");
		exit = new JButton("Exit");

		change.addActionListener(new FaceWinChangeButtonListener(this));
		add.addActionListener(new FaceWinAddButtonListener(this));
		del.addActionListener(new FaceWinDeLButtonListener(this));

		this.add(add, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH,
				new Insets(3, 3, 3, 3), 0, 0));
		this.add(change, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH,
				new Insets(3, 3, 3, 3), 0, 0));
		this.add(del, new GridBagConstraints(2, 1, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH,
				new Insets(3, 3, 3, 3), 0, 0));
		this.add(exit, new GridBagConstraints(3, 1, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH,
				new Insets(3, 3, 3, 3), 0, 0));

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
					selecteRow=selectedRows[0];
					Object user_id = tableModel.getValueAt(selecteRow, 0);
					selectedUserId = Integer.valueOf((String) user_id);
				
			}
		});
	}

	public MyTableModel getTableModel() {
		return tableModel;
	}

	public String [] getSelectedPerson() {
		String [] selectedPers = tableModel.getRow(selecteRow);
		return selectedPers;
	}
	
	public int getSelecteRow() {
		return selecteRow;
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
