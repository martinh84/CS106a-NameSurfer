/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.util.*;
import java.util.*;

public class NameSurferEntry implements NameSurferConstants {

/* Constructor: NameSurferEntry(line) */
/**
 * Creates a new NameSurferEntry from a data line as it appears
 * in the data file.  Each line begins with the name, which is
 * followed by integers giving the rank of that name for each
 * decade.
 */
	public NameSurferEntry(String line) {
		
		/* To select the name from the String line */
		int endOfName = line.indexOf(' ');
		name = line.substring(0, endOfName);
		
		/* To get the ranks from the remaining String*/
		String numberList = line.substring(endOfName + 1);
		String[] nums = numberList.split("\\s");
		for(int i = 0; i < NDECADES; i++) {
			rank[i] = Integer.parseInt(nums[i]);
		}
	}

/**
 * Returns the name associated with this entry.
 */
	public String getName() {
		return name;
	}

/**
 * Returns the rank associated with an entry for a particular
 * decade.  The decade value is an integer indicating how many
 * decades have passed since the first year in the database,
 * which is given by the constant START_DECADE.  If a name does
 * not appear in a decade, the rank value is 0.
 */
	public int getRank(int decade) {
		return rank[decade];
	}

/**
 * Returns a string that makes it easy to see the value of a
 * NameSurferEntry.
 */
	public String toString() {
		String toString = name + "[ ";
		for(int i = 0; i < NDECADES; i++) {
			toString += rank[i] + " ";
		}
		return toString + "]";
	}
	
	/* Private Instance Variables */
	private String name;
	private int[] rank = new int[NDECADES];
}
