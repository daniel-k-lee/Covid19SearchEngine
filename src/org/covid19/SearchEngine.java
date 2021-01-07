package org.covid19;

/**
 * Main Contributors:
 * 
 * @author Daniel Lee
 * 
 *         Purpose: This class contains the source search data. It has an
 *         abstract search method
 */
public abstract class SearchEngine
{
	protected SearchData data;

	/**
	 * Purpose: Search data based on the search criteria (searchString), and return
	 * search result data.
	 * 
	 * @param searchString is a search filter which could contain different meaning
	 *                     in different child classes.
	 * @return Search result data based on the filter is returned.
	 */
	public abstract SearchData search(String searchString) throws DataException;

	public SearchData getData()
	{
		return data;
	}

	public void setData(SearchData data)
	{
		this.data = data;
	}
}