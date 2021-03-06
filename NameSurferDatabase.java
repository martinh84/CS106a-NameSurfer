/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

import java.io.*;
import java.util.*;
import acm.util.*;

public class NameSurferDataBase implements NameSurferConstants {
	
/* Constructor: NameSurferDataBase(filename) */
/**
 * Creates a new NameSurferDataBase and initializes it using the
 * data in the specified file.  The constructor throws an error
 * exception if the requested file does not exist or if an error
 * occurs as the file is being read.
 */
	public NameSurferDataBase(String filename) {
		try {
			rd = new BufferedReader(new FileReader(NAMES_DATA_FILE));
			while(true) {
				String line = rd.readLine();
				if(line == null) break;
				line = line.toUpperCase();
				NameSurferEntry input = new NameSurferEntry(line);
				data.put(input.getName(), input);
			}
			rd.close();
		}catch (IOException ex) {
			throw new ErrorException(ex);
		}
	}
	
/**
 * Returns the NameSurferEntry associated with this name, if one
 * exists.  If the name does not appear in the database, this
 * method returns null.
 */
	public NameSurferEntry findEntry(String name) {
		return data.get(name);
	}
	
	/* Private Instance Variables */
	private BufferedReader rd;
	private HashMap<String, NameSurferEntry> data = new HashMap<String, NameSurferEntry>();
}
