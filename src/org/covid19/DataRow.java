package org.covid19;

import java.io.PrintWriter;

/**
 * Main Contributors:
 * @author Daniel Lee
 * Purpose: This is an interface for the data.
 * The detailed data will be defined in its child class.
 */
public interface DataRow
{

	public void write(PrintWriter writer);
	public void display();

}
