package com.ezdi.ui;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.TextArea;

import javax.swing.JTextField;

import java.awt.Insets;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

public class LuceneUI {

	private JFrame frame;
	private JTextField textField_file;
	private JButton btnProcess;
	private JTextField textField_separatorSymbol;
	private JLabel lblFileName;
	private JLabel lblSeparatorSymbol;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LuceneUI window = new LuceneUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LuceneUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 550, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{533, 0};
		gridBagLayout.rowHeights = new int[]{36, 252, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		frame.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {20, 435, 0, 20, 20};
		gbl_panel.rowHeights = new int[] {30, 0, 0, 50, 0, 0, 10, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE, 0.0, 0.0};
		panel.setLayout(gbl_panel);
		
		textField_file = new JTextField();
		GridBagConstraints gbc_textField_file = new GridBagConstraints();
		gbc_textField_file.weightx = 1.0;
		gbc_textField_file.insets = new Insets(0, 0, 5, 5);
		gbc_textField_file.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_file.gridx = 1;
		gbc_textField_file.gridy = 1;
		panel.add(textField_file, gbc_textField_file);
		textField_file.setColumns(10);
		
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		JButton btnBrowse = new JButton("Search");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser.showOpenDialog(frame);
				textField_file.setText(fileChooser.getSelectedFile().getAbsolutePath());
			}
		});
		GridBagConstraints gbc_btnBrowse = new GridBagConstraints();
		gbc_btnBrowse.insets = new Insets(0, 0, 5, 5);
		gbc_btnBrowse.gridx = 2;
		gbc_btnBrowse.gridy = 1;
		panel.add(btnBrowse, gbc_btnBrowse);
		
		btnProcess = new JButton("Test");
		btnProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fileName = textField_file.getText().trim();
				String separator = textField_separatorSymbol.getText();
				
				// call rdf conversion process
				if(separator == null || separator.length() == 0)
				{
					JOptionPane.showMessageDialog(frame, "Separator symbol not set ...", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		lblFileName = new JLabel("Primary Concept");
		GridBagConstraints gbc_lblFileName = new GridBagConstraints();
		gbc_lblFileName.anchor = GridBagConstraints.WEST;
		gbc_lblFileName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFileName.gridx = 0;
		gbc_lblFileName.gridy = 1;
		panel.add(lblFileName, gbc_lblFileName);
		
		lblSeparatorSymbol = new JLabel("Secondary Concept");
		GridBagConstraints gbc_lblSeparatorSymbol = new GridBagConstraints();
		gbc_lblSeparatorSymbol.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeparatorSymbol.anchor = GridBagConstraints.WEST;
		gbc_lblSeparatorSymbol.gridx = 0;
		gbc_lblSeparatorSymbol.gridy = 2;
		panel.add(lblSeparatorSymbol, gbc_lblSeparatorSymbol);

		textField_separatorSymbol = new JTextField();
		GridBagConstraints gbc_textField_separatorSymbol = new GridBagConstraints();
		gbc_textField_separatorSymbol.insets = new Insets(0, 0, 5, 5);
		gbc_textField_separatorSymbol.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_separatorSymbol.gridx = 1;
		gbc_textField_separatorSymbol.gridy = 2;
		panel.add(textField_separatorSymbol, gbc_textField_separatorSymbol);
		textField_separatorSymbol.setColumns(10);
		GridBagConstraints gbc_btnProcess = new GridBagConstraints();
		gbc_btnProcess.insets = new Insets(0, 0, 5, 5);
		gbc_btnProcess.gridx = 0;
		gbc_btnProcess.gridy = 3;
		panel.add(btnProcess, gbc_btnProcess);
		
		lblSeparatorSymbol = new JLabel("jdnfkd.snfConcept");
		GridBagConstraints gbc_lblSeparatorSymbol1 = new GridBagConstraints();
		gbc_lblSeparatorSymbol1.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeparatorSymbol1.anchor = GridBagConstraints.WEST;
		gbc_lblSeparatorSymbol1.gridx = 0;
		gbc_lblSeparatorSymbol1.gridy = 3;
		panel.add(lblSeparatorSymbol, gbc_lblSeparatorSymbol1);
		
		
		textField_separatorSymbol = new JTextField();
		GridBagConstraints gbc_textField_separatorSymbol1 = new GridBagConstraints();
		gbc_textField_separatorSymbol1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_separatorSymbol1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_separatorSymbol1.gridx = 2;
		gbc_textField_separatorSymbol1.gridy = 2;
		panel.add(textField_separatorSymbol, gbc_textField_separatorSymbol);
		textField_separatorSymbol.setColumns(10);
		GridBagConstraints gbc_btnProcess1 = new GridBagConstraints();
		gbc_btnProcess1.insets = new Insets(0, 0, 5, 5);
		gbc_btnProcess1.gridx = 1;
		gbc_btnProcess1.gridy = 3;
		panel.add(btnProcess, gbc_btnProcess1);
	}
}