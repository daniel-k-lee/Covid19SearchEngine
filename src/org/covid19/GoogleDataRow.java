package org.covid19;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;

/**
 * Main Contributors:
 * 
 * @author Daniel Lee
 * 
 * @Purpose: Google Search Data Row for Google Search Engine
 */
public class GoogleDataRow implements DataRow
{
	private String line;

	public GoogleDataRow(String line) {
		this.line = line;
	}
	@Override
	public void display()
	{
		// TODO Auto-generated method stub
		System.out.println(line);
	}

	public void write(PrintWriter writer) {
		writer.println(line);
	}
	
	public String getRow()
	{
		return line;
	}

	public void setRow(String line)
	{
		this.line = line;
	}

}
