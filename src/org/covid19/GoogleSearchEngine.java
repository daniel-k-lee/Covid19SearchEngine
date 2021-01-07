package org.covid19;

/**
 * Main Contributors:
 * 
 * @author Daniel Lee
 * 
 * @Purpose: Mock Google Search Engine
 */
public class GoogleSearchEngine extends SearchEngine
{
	private GoogleSearchData googleData;

	public void setGoogleData(GoogleSearchData gData)
	{
		googleData = gData;
	}

	public GoogleSearchData googleSearch(String key) throws DataException
	{
		if (googleData == null)
		{
			throw new DataException("No source data defined!");
		}
		GoogleSearchData result = null;
		result = googleData.googleSearch(key);
		return result;
	}

	@Override
	public SearchData search(String key) throws DataException
	{
		if (data == null)
		{
			throw new DataException("No source data defined!");
		}
		GoogleSearchData result = null;
		result = (GoogleSearchData) ((GoogleSearchData) data).search(key);
		return result;
	}

}
