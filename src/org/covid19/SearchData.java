package org.covid19;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Main Contributors:
 * 
 * @author Daniel Lee
 * 
 * @Purpose: This class is the base class for all search data.
 */
public class SearchData
{
	private DataRow[] rows;

	public SearchData() {}
	
	public SearchData(DataRow[] rows) {
		this.rows = rows;
	}
	public DataRow[] getRows()
	{
		return rows;
	}

	public void setRows(DataRow[] rows)
	{
		this.rows = rows;
	}

	public void write(String fileName) throws DataException {
		try
		{
			File dataFile = new File(fileName);
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(dataFile)));
		    DataRow[] rows = getRows();
			for (int i = 0; i < rows.length; i++)
			{
				rows[i].write(out);
			}
			out.close();
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new DataException(e.getMessage());
		}
	}
	
	public void display()
	{
		for (int i = 0; i < rows.length; i++)
		{
			rows[i].display();
		}
	}
}