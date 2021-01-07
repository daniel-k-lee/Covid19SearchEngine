package org.covid19;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Main Contributors:
 * 
 * @author Daniel Lee
 * 
 *         Purpose: This class contains detailed COVID-19 data fields. It can be
 *         populated by the backend database, or can be populated with mockup
 *         data for testing purposes.
 */
public class Covid19DataRow implements DataRow
{
	private int caseCount;
	private String type;
	private boolean newCase;
	private Date date;
	private String zipcode;
	private String city;

	public Covid19DataRow(int caseCnt, String type, boolean newCase, Date date, String zip, String city)
	{
		this.caseCount = caseCnt;
		this.type = type;
		this.newCase = newCase;
		this.date = date;
		this.zipcode = zip;
		this.city = city;
	}

	public int getCaseCount()
	{
		return caseCount;
	}

	public void setCaseCount(int caseCount)
	{
		this.caseCount = caseCount;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public boolean isNewCase()
	{
		return newCase;
	}

	public void setNewCase(boolean newCase)
	{
		this.newCase = newCase;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public String getZipcode()
	{
		return zipcode;
	}

	public void setZipcode(String zipcode)
	{
		this.zipcode = zipcode;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public Covid19DataRow copy() {
		Covid19DataRow copy = new Covid19DataRow(caseCount, type, newCase, (Date)date.clone(), zipcode, city);
		return copy;
	}
	public void write(PrintWriter writer) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
		String dateStr = formatter.format(date);
		//1231, General, false, 09/30/20, 92020, El Cajon
		writer.print(caseCount);
		writer.print(",");
		writer.print(type);
		writer.print(",");
		writer.print(newCase);
		writer.print(",");
		writer.print(dateStr);
		writer.print(",");
		writer.print(zipcode);
		writer.print(",");
		writer.println(city);
	}
	public void display()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
		String dateStr = formatter.format(date);
		String msg = dateStr + " " + caseCount + " " + type + " " + city + " " + zipcode;
		System.out.println(msg);
	}
}