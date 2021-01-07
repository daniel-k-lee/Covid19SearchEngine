package org.covid19;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Main Contributors:
 * 
 * @author Daniel Lee
 * 
 *         Purpose: This class contains a list of COVID-19 data row. It serves
 *         as a search source data, as well as search result data.
 */
public class Covid19SearchData extends SearchData {

	public Covid19SearchData() {
		super();
	}

	public Covid19SearchData(List<Covid19DataRow> rowList) {
		super(rowList.toArray(new Covid19DataRow[0]));
	}

	// This method parses the csv file's data and produces Covid19SearchData
	public static Covid19SearchData parse(String csvFileName) throws DataException {
		List<Covid19DataRow> rowList = new ArrayList<>();
		Scanner scnr = null;
		try {
			File dataSrc = new File(csvFileName);
			scnr = new Scanner(dataSrc);
			while (scnr.hasNextLine()) {
				String line = null;
				try {
					line = scnr.nextLine();
					// sample line: 1231, General, false, 09/30/20, 92020, El Cajon
					Scanner lineScanner = new Scanner(line).useDelimiter("\s*,\s*");
					int caseCnt = lineScanner.nextInt();
					String type = lineScanner.next();
					boolean newCase = "true".equalsIgnoreCase(lineScanner.next());
					Date date = null;
					String dateStr = lineScanner.next();
					SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
					date = formatter.parse(dateStr);
					String zip = lineScanner.next();
					String city = lineScanner.next();
					lineScanner.close();
					Covid19DataRow row = new Covid19DataRow(caseCnt, type, newCase, date, zip, city);
					rowList.add(row);
				} catch (ParseException e) {
					System.out.println("This line has data problem, skipped: " + line);
					e.printStackTrace();
				}
			}
			Covid19SearchData data = new Covid19SearchData(rowList);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataException(csvFileName + " not available", e);
		} finally {
			if (scnr != null)
				scnr.close();
		}
	}

	public SearchData searchZipCode(String zipCode, Date date) {
		Covid19SearchData resultData = new Covid19SearchData();
		List<Covid19DataRow> rowList = new ArrayList<>();
		Covid19DataRow[] rows = (Covid19DataRow[]) getRows();
		for (int i = 0; i < rows.length; i++) {
			Covid19DataRow row = rows[i];
			if (zipCode.equals(row.getZipcode())) {
				if (date == null) {
					rowList.add(row);
				} else if (date.compareTo(row.getDate()) == 0) {
					rowList.add(row);
				}
			}
		}
		resultData.setRows(rowList.toArray(new Covid19DataRow[0]));
		return resultData;
	}

	public SearchData searchCity(String city, Date date) {
		Covid19SearchData resultData = new Covid19SearchData();
		List<Covid19DataRow> rowList = new ArrayList<>();
		Covid19DataRow[] rows = (Covid19DataRow[]) getRows();
		for (int i = 0; i < rows.length; i++) {
			Covid19DataRow row = rows[i];
			if (city.equals(row.getCity())) {
				if (date == null) {
					rowList.add(row);
				} else if (date.compareTo(row.getDate()) == 0) {
					rowList.add(row);
				}
			}
		}
		resultData.setRows(rowList.toArray(new Covid19DataRow[0]));
		return resultData;
	}

	Covid19DataRow findDataRow(String zipCode, Date date, String type) {
		List<Covid19DataRow> rowList = new ArrayList<>();
		Covid19DataRow[] rows = (Covid19DataRow[]) getRows();
		for (int i = 0; i < rows.length; i++) {
			Covid19DataRow row = rows[i];
			if (zipCode.equals(row.getZipcode())) {
				if (date == null || date.compareTo(row.getDate()) == 0) {
					if (type.equals(row.getType()))
						rowList.add(row);
				}
			}
		}
		if (rowList.size() == 1)
			return rowList.get(0);
		else if (rowList.size() == 0)
			return null;
		else {
			System.out.println("Found " + rowList.size() + " rows of data, returning the 1st row.");
			return rowList.get(0);
		}
	}

}