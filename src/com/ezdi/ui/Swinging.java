package com.ezdi.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.text.*;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;

import com.ezdi.coding.clinic.lucene.*;

/**
 * TextInputDemo.java is a 1.4 application that uses
 * these additional files:
 *   SpringUtilities.java
 *   ICD-10 is CUI here
 *   ...
 */
public class Swinging extends JPanel implements ActionListener, FocusListener {
	LuceneSearch luc = new LuceneSearch();
	LuceneIndexing lucindx = new LuceneIndexing();
	JTextField primaryConcept, secondaryConcept;
	JFormattedTextField medicalConcept;
	JSpinner stateSpinner;
	boolean CUI = false;
	Font regularFont, italicFont;
	JLabel addressDisplay;
	final static int GAP = 10;

	public Swinging() throws CorruptIndexException, ParseException, IOException {
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		JPanel leftHalf = new JPanel() {
			//Don't allow us to stretch vertically.
			public Dimension getMaximumSize() {
				Dimension pref = getPreferredSize();
				return new Dimension(100, pref.height);
			}

		};
		leftHalf.setLayout(new BoxLayout(leftHalf,BoxLayout.PAGE_AXIS));
		leftHalf.add(createEntryFields());
		leftHalf.add(createButtons());

		add(leftHalf);
		add(createAddressDisplay());
	}

	protected JComponent createButtons() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));

		JButton button = new JButton("Search");
		button.addActionListener(this);
		panel.add(button);

		button = new JButton("Clear");
		button.addActionListener(this);
		button.setActionCommand("clear");
		panel.add(button);

		//Match the SpringLayout's gap, subtracting 5 to make
		//up for the default gap FlowLayout provides.
		panel.setBorder(BorderFactory.createEmptyBorder(0, 0,GAP-5, GAP-5));
		return panel;
	}

	/**
	 * Called when the user clicks the button or presses
	 * Enter in a text field.
	 */
	public void actionPerformed(ActionEvent e) {
		if ("clear".equals(e.getActionCommand())) {
			CUI = false;
			primaryConcept.setText("");
			secondaryConcept.setText("");

			//We can't just setText on the formatted text
			//field, since its value will remain set.
			medicalConcept.setValue(null);
		} else {
			CUI = true;
		}
		try {
			updateDisplays();
		} catch (ParseException | IOException e1) {
			e1.printStackTrace();
		}
	}

	protected void updateDisplays() throws CorruptIndexException, ParseException, IOException {
		addressDisplay.setText(formatAddress());
		if (CUI) {
			addressDisplay.setFont(regularFont);
		} else {
			addressDisplay.setFont(italicFont);
		}
	}

	protected JComponent createAddressDisplay() throws CorruptIndexException, ParseException, IOException {
		JPanel panel = new JPanel(new BorderLayout());
		addressDisplay = new JLabel();
//		addressDisplay.setPreferredSize(new Dimension);
		addressDisplay.setSize(WIDTH, HEIGHT);
		addressDisplay.setHorizontalAlignment(JLabel.CENTER);
		regularFont = addressDisplay.getFont().deriveFont(Font.LAYOUT_LEFT_TO_RIGHT,12.0f).deriveFont(Font.BOLD);
		italicFont = regularFont.deriveFont(Font.ITALIC);
		updateDisplays();

		//Lay out the panel.
		panel.setBorder(BorderFactory.createEmptyBorder(
				GAP/2, //top
				0,     //left
				GAP/2, //bottom
				0));   //right
		panel.add(new JSeparator(JSeparator.VERTICAL),BorderLayout.LINE_START);
		panel.add(addressDisplay, BorderLayout.NORTH);
		panel.scrollRectToVisible(getBounds());
		panel.setPreferredSize(new Dimension(300,2000));
		JScrollPane scroll = new JScrollPane(panel, 
				   ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,  
				   ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS); 
		
		scroll.setPreferredSize(new Dimension(800, 500));
		return scroll;
	}

	protected String formatAddress() throws CorruptIndexException, ParseException, IOException {
		if (!CUI) return "Results displayed here";

		ArrayList<String> results= new ArrayList<>();
		String primary = primaryConcept.getText();
		String secondary = secondaryConcept.getText();

		
		String cui = (String)stateSpinner.getValue();
		String medical = medicalConcept.getText();
		String empty = "";

		if ((primary == null) || empty.equals(primary)) {
			primary = null;
		}
		if ((secondary == null) || empty.equals(secondary)&&(primary!=null)) {
			secondary = null;
		}

		if((primary != null)&&(secondary != null)){
			results = luc.displayResults(primary, secondary);
		}
		else if(secondary==null){
			results = luc.displayResults(primary, secondary);
		}

		//		if ((cui == null) || empty.equals(cui)) {
		//			cui = "<em>(no cui specified)</em>";
		//		} else {
		//			int abbrevIndex = cui.indexOf('(') + 1;
		//			cui = cui.substring(abbrevIndex,
		//					abbrevIndex + 2);
		//		}
		//		if ((medical == null) || empty.equals(medical)) {
		//			medical = "";
		//		}

		StringBuffer sb = new StringBuffer();
		sb.append("<html><p align=left>");
		if(!results.isEmpty()){
			for(String s : results)
			{
				sb.append(s);
				sb.append("<br>");
				sb.append("<br>");
			}
		}
		else if(primary == null&&secondary != null){
			sb.append("<em>(no primary concept specified)</em>");
			sb.append(" ");
//			sb.append(cui); //should format
//			sb.append(" ");
//			sb.append(medical);
		}
		else{
			sb.append("<em>(no primary concept specified)</em>");
			sb.append("<br>");
			sb.append("<em>(no secondary concept specified)</em>");
//			sb.append(" ");
//			sb.append(cui); //should format
//			sb.append(" ");
//			sb.append(medical);
		}
		sb.append("</p></html>");

		return sb.toString();
	}

//	A convenience method for creating a MaskFormatter.
//	protected MaskFormatter createFormatter(String s) {
//		MaskFormatter formatter = null;
//		try {
//			formatter = new MaskFormatter(s);
//		} catch (java.text.ParseException exc) {
//			System.err.println("formatter is bad: " + exc.getMessage());
//			System.exit(-1);
//		}
//		return formatter;
//	}

	/**
	 * Called when one of the fields gets the focus so that
	 * we can select the focused field.
	 */
	public void focusGained(FocusEvent e) {
		Component c = e.getComponent();
		if (c instanceof JFormattedTextField) {
			selectItLater(c);
		} else if (c instanceof JTextField) {
			((JTextField)c).selectAll();
		}
	}

	//Workaround for formatted text field focus side effects.
	protected void selectItLater(Component c) {
		if (c instanceof JFormattedTextField) {
			final JFormattedTextField ftf = (JFormattedTextField)c;
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					ftf.selectAll();
				}
			});
		}
	}

	//Needed for FocusListener interface.
	public void focusLost(FocusEvent e) { } //ignore

	protected JComponent createEntryFields() throws IOException {
		JPanel panel = new JPanel(new SpringLayout());

		String[] labelStrings = {
				"Primary Concept: ",
				"Secondary Concept ",
				"CUI ",
				"Medical concept"
		};

		JLabel[] labels = new JLabel[labelStrings.length];
		JComponent[] fields = new JComponent[labelStrings.length];
		int fieldNum = 0;

		//Create the text field and set it up.
		primaryConcept = new JTextField();
		primaryConcept.setColumns(10);
		fields[fieldNum++] = primaryConcept;

		secondaryConcept = new JTextField();
		secondaryConcept.setColumns(10);
		fields[fieldNum++] = secondaryConcept;

		String[] stateStrings = getStateStrings();
		stateSpinner = new JSpinner(new SpinnerListModel(stateStrings));
		fields[fieldNum++] = stateSpinner;

		medicalConcept = new JFormattedTextField();
//				createFormatter("#####"));
		fields[fieldNum++] = medicalConcept;

		//Associate label/field pairs, add everything,
		//and lay it out.
		for (int i = 0; i < labelStrings.length; i++) {
			labels[i] = new JLabel(labelStrings[i],
					JLabel.TRAILING);
			labels[i].setLabelFor(fields[i]);
			panel.add(labels[i]);
			panel.add(fields[i]);

			//Add listeners to each field.
			JTextField tf = null;
			if (fields[i] instanceof JSpinner) {
				tf = getTextField((JSpinner)fields[i]);
			} else {
				tf = (JTextField)fields[i];
			}
			tf.addActionListener(this);
			tf.addFocusListener(this);
		}
		SpringUtilities.makeCompactGrid(panel,
				labelStrings.length, 2,
				GAP*5, GAP*5, //init x,y
				GAP*5, GAP/2);//xpad, ypad
		return panel;
	}

	public String[] getStateStrings() throws IOException {
		HashSet<String> maps = lucindx.readFlatFile();
		String[] stateStrings = maps.toArray( new String[0] );
		 
//		String[] stateStrings = {
//				"Hello", 
//				"Test"
//		};
		return stateStrings;
	}

	public JFormattedTextField getTextField(JSpinner spinner) {
		JComponent editor = spinner.getEditor();
		if (editor instanceof JSpinner.DefaultEditor) {
			return ((JSpinner.DefaultEditor)editor).getTextField() ;
		} else {
			System.err.println("Unexpected editor type: "
					+ spinner.getEditor().getClass()
					+ " isn't a descendant of DefaultEditor");
			return null;
		}
	}

	/**
	 * Create the GUI and show it.  For thread safety,
	 * this method should be invoked from the
	 * event-dispatching thread.
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws CorruptIndexException 
	 */
	private static void createAndShowGUI() throws CorruptIndexException, ParseException, IOException {
		//Make sure we have nice window decorations.
		JFrame.setDefaultLookAndFeelDecorated(true);

		//Create and set up the window.
		JFrame frame = new JFrame("Coding Clinic");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Create and set up the content pane.
		JComponent newContentPane = new Swinging();
		newContentPane.setOpaque(true); //content panes must be opaque
		frame.setContentPane(newContentPane);

		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					createAndShowGUI();
				} catch (ParseException | IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
}