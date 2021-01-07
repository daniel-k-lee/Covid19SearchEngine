package org.covid19;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Main Contributors:
 * 
 * @author Daniel Lee
 * 
 *         Purpose: This is a test class for this project.
 * 
 *         Sources:
 * 
 *         https://www.javatpoint.com/java-simpledateformat
 */
public class SearchEngineTest
{
	public static void main(String[] args) throws Exception
	{
		SearchEngineTest test = new SearchEngineTest();
		//test.arrayTest();
		//test.classTest();
		//test.polymorphismTest();
		test.ioTest();
	}

	public void arrayTest() throws Exception
	{
		// 1 dimensional array
		GoogleSearchData data1 = GoogleSearchData.parse("GoogleDataset.txt");
		SearchEngine engine = new GoogleSearchEngine();
		engine.setData(data1);
		SearchData resultRows1 = engine.search("El Cajon");
		System.out.println("1 dimensional array usage: Search City El Cajon");
		DataRow[] rows = resultRows1.getRows();
		for (int i = 0; i < rows.length; i++)
			rows[i].display();
		// 2 dimensional array
		String[] files = { "Covid19Dataset.csv", "GoogleDataSet.txt" };
		GoogleSearchData data2 = GoogleSearchData.parse(files);
		GoogleSearchEngine googleEngine = new GoogleSearchEngine();
		googleEngine.setGoogleData(data2);
		GoogleSearchData googleResult = googleEngine.googleSearch("El Cajon");
		System.out.println("\n2 dimensional array usage:  Google Specialized Search El Cajon");
		googleResult.display();
	}

	public void classTest() throws Exception {
		Covid19SearchData data = Covid19SearchData.parse("Covid19Dataset.csv");
		DataRow[] rows = data.getRows();
		System.out.println("Covid19 source data");
		for (int i = 0; i < rows.length; i++)
			rows[i].display();
		System.out.println(" ");
		Covid19SearchEngine engine = new Covid19SearchEngine();
		engine.setData(data);
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
		String dateStr = "09/30/20";
		Date date = format.parse(dateStr);
		String zip = "92020";
		String type = "General";
		Covid19DataRow row = engine.findRow(zip, date, type);
		System.out.println("Find row: zip=" + zip + ", date="+dateStr+", type="+type);
		row.display();
		System.out.println("Set the result caseCount=2000, and find it from the source data again.");
		row.setCaseCount(2000);
		row = engine.findRow(zip, date, type);
		row.display();
		System.out.println("Do a deep copy of the result row, set the deep copy caseCount=3000, and find it from the source data one more time.");
		Covid19DataRow deepCopy = row.copy();
		deepCopy.setCaseCount(3000);
		row = engine.findRow(zip, date, type);
		row.display();
	}
	
	public void polymorphismTest() throws Exception
	{ 
		SearchEngine[] engines = new SearchEngine[2];
		Covid19SearchData data = Covid19SearchData.parse("Covid19Dataset.csv");
		engines[0] = new Covid19SearchEngine();
		engines[0].setData(data);
		SearchData resultRows = engines[0].search("El Cajon");
		System.out.println("Covid19SearchEngine search city El Cajon");
		resultRows.display();
		GoogleSearchData data1 = GoogleSearchData.parse("GoogleDataset.txt");
		engines[1] = new GoogleSearchEngine();
		engines[1].setData(data1);
		SearchData resultRows1 = engines[1].search("El Cajon");
		System.out.println("\nGoogleSearchEngine search city El Cajon");
		resultRows1.display();
	}
	
	public void ioTest() throws Exception {
		//use Scanner to read integer, String from a file.
		Covid19SearchData data = Covid19SearchData.parse("Covid19Dataset.csv");
		Covid19SearchEngine cEngine = new Covid19SearchEngine();
		cEngine.setData(data);
		Covid19SearchData resultRows = (Covid19SearchData)cEngine.search("El Cajon");
		//use PrintWriter to write integer, String to a file
		resultRows.write("Covid19SearchResult.csv");
		
		//use InputStream to read data from a file
		GoogleSearchData data1 = GoogleSearchData.parse("GoogleDataset.txt");
		GoogleSearchEngine gEngine = new GoogleSearchEngine();
		gEngine.setData(data1);
		GoogleSearchData resultRows1 = (GoogleSearchData)gEngine.search("El Cajon");
		resultRows1.write("GoogleSearchResult.txt");
		//use InputStream to read data from Console
		GoogleSearchData data2 = GoogleSearchData.parse(System.in, true);
		gEngine.setData(data2);
		GoogleSearchData resultRows2 = (GoogleSearchData)gEngine.search("Java");
		resultRows2.write("GoogleSearchResult2.txt");
		//use InputStream to read data from an URL
		URL url = new URL("https://docs.oracle.com/javase/7/docs/api/java/net/URL.html");
		GoogleSearchData data3 = GoogleSearchData.parse(url);
		gEngine.setData(data3);
		GoogleSearchData resultRows3 = (GoogleSearchData)gEngine.search("Java");
		resultRows3.write("GoogleSearchResult3.txt");
	}
}
