package org.covid19;

import java.util.Date;

/**
 * Main Contributors
 * 
 * @author Daniel Lee
 * 
 *         Purpose: This class contains COVID-19 specific search engine methods.
 */
public class Covid19SearchEngine extends SearchEngine
{
	// the private variables are to be used later
	private Date date;
	private String zipCode;
	private String city;

	/**
	 * Purpose: Search the data and return the result data
	 * 
	 * @param area (could be the zipcode or city name)
	 * @return search result
	 */
	public SearchData search(String area) throws DataException
	{
		return search(area, null);
	}

	// https://mkyong.com/java/java-how-to-check-if-a-string-is-numeric/
	public static boolean isNumeric(final String str)
	{

		// null or empty
		if (str == null || str.length() == 0)
		{
			return false;
		}

		for (char c : str.toCharArray())
		{
			if (!Character.isDigit(c))
			{
				return false;
			}
		}

		return true;

	}

	/**
	 * Purpose: Search the data and return the result data
	 * 
	 * @param area (could be the zipcode or city name)
	 * @param date of the data
	 * @return search result
	 */
	public SearchData search(String area, Date date) throws DataException
	{
		if (data == null)
		{
			throw new DataException("No source data defined!");
		}
		Covid19SearchData result = null;
		if (isNumeric(area))
		{
			zipCode = area;
			result = (Covid19SearchData) ((Covid19SearchData) data).searchZipCode(zipCode, date);
		} else
		{
			city = area;
			result = (Covid19SearchData) ((Covid19SearchData) data).searchCity(city, date);
		}
		return result;
	}
	
	public Covid19DataRow findRow(String zip, Date date, String type) {
		return ((Covid19SearchData)data).findDataRow(zip, date, type);
	}
}
