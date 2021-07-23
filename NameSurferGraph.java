/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {

	/**
	* Creates a new NameSurferGraph object that displays the data.
	*/
	public NameSurferGraph() {
		addComponentListener(this);
		update();	
	}
	
	/**
	* Clears the list of name surfer entries stored inside this class.
	*/
	public void clear() {
		listOfNames.clear();
	}
		
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(NameSurferEntry entry) {
		listOfNames.add(entry);
		}
		
	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the list of entries. Update is also called whenever
	* the size of the canvas changes.
	*/
	public void update() {
		removeAll();
		createGrid();
		addNamesData();
	}
	
	/**
	 * Creates the grid on which the data will be displayed.
	 */
	private void createGrid() {
		/* Top and bottom margin lines for the grid*/ 
		add(new GLine(0, GRAPH_MARGIN_SIZE, getWidth(),GRAPH_MARGIN_SIZE));
		add(new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE));
		
		/* For the years to be displayed on GLabels*/
		int decade = START_DECADE;
		/* Int for equally spaced vertical lines*/
		int equalDiv = getWidth() / NDECADES;
		int decPosition = 0;
		for(int i = 0; i < NDECADES; i++) {
			add(new GLine(decPosition, 0, decPosition, getHeight()));
			GLabel dec = new GLabel(Integer.toString(decade), decPosition + 2, getHeight() - (GRAPH_MARGIN_SIZE / 2));
			dec.setFont("Helvetica-bold-12");
			add(dec);
			decade += 10;
			decPosition += equalDiv;
		}
	}
	
	/**
	 * Cycles through the list of names to send to create the display for each
	 */
	private void addNamesData() {
		for(int i = 0; i < listOfNames.size(); ++i) {
			addToGraph(listOfNames.get(i), i);
		}
	}
	
	/**
	 * Adds each name and respective rank data to the graph for display. GPoints are created to place each names rank for
	 * each decade.  Lines are then created joining up the GPoints, using their location.  GLabels are then added to
	 * show the user the name and rank for each decade - additional coding was introduced to eliminate when increasing
	 * GPoints and GLines would cut through the GLabels, obscuring the text.
	 * 
	 * For each entry, a different color would be provided (up to a maximum of 5, before gray would be used).
	 * @param entry To provide the name and rank selection by the user to be displayed
	 * @param orderNumber Would count the volume of names selected and provide a color depending on the number 
	 */
	private void addToGraph(NameSurferEntry entry, int orderNumber) {
		/* to enable alterations to positioning for better user experience*/
		double xadjustment = 3;
		double yadjustment = 2;
				
		for(int i = 0; i < NDECADES; i++) {
			double rank = entry.getRank(i);
			double y = ((getHeight() - (2 * GRAPH_MARGIN_SIZE))/MAX_RANK) * yPoint(rank) + GRAPH_MARGIN_SIZE;
			if(rank == 1000) {
				y = getHeight() - GRAPH_MARGIN_SIZE;
			}
			GPoint rPoint = new GPoint(((getWidth() / NDECADES) * i), y);
			
			rankScores.add(i, rPoint);			
		}
		/* Using the GPoint locations, the lines are joined up, stopping short of going past the last decade. Color is selected too */
		for(int i = 0; i < NDECADES - 1; i++) {
			GLine line = new GLine(rankScores.get(i).getX(),rankScores.get(i).getY(),rankScores.get(i + 1).getX(), rankScores.get(i + 1).getY());
			line.setColor(lineColor(orderNumber));
			add(line);
		}
		
		/* Creates the labels based on the position of the GPoint, with small adjustments, depending on the ranking to increase visibility*/
		for(int i = 0; i < NDECADES; ++i) {
			yadjustment = 0;
			String rank = qualifyLabel(entry.getRank(i));
			String name = entry.getName();
			
			/* To avoid out of range issue, the last i in the above for loop is treated differently*/
			if(i == NDECADES -1) {
				GLabel graphLabel = new GLabel(capitalise(name) + " " + rank, rankScores.get(i).getX() + xadjustment ,rankScores.get(i).getY() + yadjustment);
				graphLabel.setFont("Helvetica-bold-14");
				graphLabel.setColor(lineColor(orderNumber));
				add(graphLabel);
				break;
			}
			/* To adjust the GLabel positioning when the line graph increases, for user experience*/
			if (entry.getRank(i) > entry.getRank(i + 1)) yadjustment = 20;			
			GLabel graphLabel = new GLabel(capitalise(name) + " " + rank, rankScores.get(i).getX() + xadjustment ,rankScores.get(i).getY() + yadjustment);
			graphLabel.setFont("Helvetica-bold-14");
			graphLabel.setColor(lineColor(orderNumber));
			add(graphLabel);
			}
	}
	
	/**
	 * Changes the graph display to show "*" instead of 0 if the name hasn't ranked that decade
	 * @param rank Provides an int of the rank of that decade
	 * @return Returns "*" if the rank is 0, or the rank if between 1- 1000
	 */
	private String qualifyLabel(int rank) {
		if(rank == 0) {
			return "*";
		} else {
			return "" + rank;
		}
	}
	
	/**
	 * To display capitalised name instead of Uppercase
	 * @param name Provides the String name in Uppercase to the method
	 * @return Returns a capitalised version of the name
	 */
	private String capitalise(String name) {
		return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
	}
	
	/**
	 * Returns rank for plotting Y coordinate, if rank is 0, the Y plot is changed to 1000 
	 * (so it is displayed at the bottom of the screen
	 * @param rank Provides the rank for the name in the given decade
	 * @return rank as either 1000 of actual rank
	 */	
	private double yPoint(double rank) {
		if(rank == 0) {
			double graphScore = 1000;
			return graphScore;
		} else {
			return rank;
		}
	}
	
	/**
	 * Enables the graph to show the first 5 name entries in different colors.  The GPoint, GLabel and GLine are all changed
	 * @param orderNumber Provides a different int for each name selected by the user 
	 * @return Returns a color for the GPoint, GLabel and GLine, depending on the number of names selected by the user 
	 */
	private Color lineColor(int orderNumber) {
		switch(orderNumber) {
		case 0: return Color.black;
		case 1: return Color.blue;
		case 2: return Color.magenta;
		case 3: return Color.red;
		case 4: return Color.cyan;
		default: return Color.gray;
		}
	}
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
	
		
	/* Instance Variables*/
	private ArrayList<NameSurferEntry> listOfNames = new ArrayList<NameSurferEntry>();
	private ArrayList<GPoint> rankScores = new ArrayList<GPoint>();
	
}
