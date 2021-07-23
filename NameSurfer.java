/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;


public class NameSurfer extends Program implements NameSurferConstants {

/**
 * This method has the responsibility for reading in the data base
 * and initializing the interactors at the bottom of the window.
 */
	public void init() {
				
		data = new NameSurferDataBase(NAMES_DATA_FILE);
		graph = new NameSurferGraph();
		add(graph);
		
		/* Textfield to take user input for the data */
	    tf = new JTextField(20);
	    tf.setActionCommand("Graph");
	    tf.addActionListener(this);
	    add(new JLabel("Name"), SOUTH);
	    add(tf, SOUTH);
	    
	    /* Textfield to take user input for the data */
	    add(new JButton("Graph"), SOUTH);
	    /* Button to clear the display */
	    clear = new JButton("Clear");
	    add(clear, SOUTH);
	    
	    addActionListeners();
	}

/**
 * This is responsible for detecting when the buttons are clicked or the user presses enter after entering a name in the textfield
 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("Graph")) {
			String name = tf.getText().toUpperCase();
			NameSurferEntry input = data.findEntry(name);
			if(input != null) {
				graph.addEntry(input);
				graph.update();		
			}
		} 
		if (e.getSource() == clear) {
			graph.clear();
			graph.update();
		}
	}
	
	/* Private Instance Variables */
	private JTextField tf;
	private JButton clear;
	private NameSurferDataBase data;
	private NameSurferGraph graph;
	}
