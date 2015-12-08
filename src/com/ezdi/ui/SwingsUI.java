package com.ezdi.ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class SwingsUI {
 
    public static void main(String[] args) {
    	 final int GAP = 10;
//        final JFrame frame = new JFrame("JTextField Demo");
// 
//        JLabel lblFName = new JLabel("First Name:");
//        JTextField tfFName = new JTextField(20);
//        lblFName.setLabelFor(tfFName);
// 
//        JLabel lblLName = new JLabel("Last Name:");
//        JTextField tfLName = new JTextField(20);
//        lblLName.setLabelFor(tfLName);
// 
//        JLabel lblResult = new JLabel("Results");
//        JTextArea resultField = new JTextArea(100,100);
//        resultField.setSize(100, 100);
//        JPanel panel = new JPanel();
//        panel.setLayout(new SpringLayout());
// 
//        panel.add(lblFName);
//        panel.add(tfFName);
//        panel.add(lblLName);
//        panel.add(tfLName);
//        panel.add(lblResult);
//        panel.add(resultField);
// 
//        SpringUtilities.makeCompactGrid(panel,
//                2, 2,  //rows, cols
//                6, 6,  //initX, initY
//                6, 6); //xPad, yPad
// 
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(300, 100);
//        frame.getContentPane().add(panel);
//        frame.setVisible(true);
    	
    	
    	
        // create the middle panel components
        
        JLabel lblFName = new JLabel("First Name:");
      JTextField tfFName = new JTextField(20);
      lblFName.setLabelFor(tfFName);

      JLabel lblLName = new JLabel("Last Name:");
      JTextField tfLName = new JTextField(20);
      lblLName.setLabelFor(tfLName);

        
//        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );

        JPanel middlePanel = new JPanel ();
//        middlePanel.setBorder ( (Border) new TitledBorder ( new EtchedBorder (), "Display Area" ) );
        JTextArea display = new JTextArea ( 10, 10 );
        display.setEditable ( false ); // set textArea non-editable
        JScrollPane scroll = new JScrollPane ( display );

        //Add Textarea in to middle panel
        middlePanel.add(lblFName);
        middlePanel.add(tfFName);
        middlePanel.add(lblLName);
        middlePanel.add(tfLName);
        middlePanel.add ( display );

        SpringUtilities.makeCompactGrid(middlePanel,
                5, 2,
                GAP, GAP, //init x,y
                GAP, GAP/2);
        
        // My code
        JFrame frame = new JFrame ();
        frame.add ( middlePanel );
        frame.pack ();
        frame.setLocationRelativeTo ( null );
        frame.setVisible ( true );
    }
}