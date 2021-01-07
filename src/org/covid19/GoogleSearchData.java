package org.covid19;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class GoogleSearchData extends SearchData
{
	private String[][] lines;

	/**
	 * Main Contributors:
	 * 
	 * @author Daniel Lee
	 * 
	 * @Purpose: The Source and result data of Google Search Engine
	 */
	public GoogleSearchData()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public static GoogleSearchData parse(String fileName) throws DataException
	{
		try
		{
			FileInputStream in = new FileInputStream(fileName);
			GoogleSearchData data = parse(in, false);
			return data;
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new DataException(e.getMessage());
		}
	}
	public static GoogleSearchData parse(URL url) throws DataException {
		try
		{
			InputStream in = url.openStream();
			GoogleSearchData data = parse(in, false);
			return data;
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new DataException(e.getMessage());
		}
	}
	public static GoogleSearchData parse(InputStream in, boolean console) throws DataException
	{
		GoogleSearchData data = new GoogleSearchData();
		List<GoogleDataRow> rowList = new ArrayList<>();
		try
		{
			Scanner scanner = new Scanner(in);
			if (console)
				System.out.print("Input Google engine source data (type q to quit): ");
			while (scanner.hasNextLine())
			{
				String line = scanner.nextLine();
				if (console) {
					if (line.equals("q"))
						break;
					else
						System.out.print("Input Google engine source data (type q to quit): ");
				}
				GoogleDataRow row = new GoogleDataRow(line);
				rowList.add(row);
			}
			scanner.close();
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new DataException(e.getMessage());
		}
		data.setRows(rowList.toArray(new GoogleDataRow[0]));
		return data;

	}

	public static GoogleSearchData parse(String[] files) throws DataException
	{
		GoogleSearchData data = new GoogleSearchData();
		List<List<String>> fileList = new ArrayList<>();
		int linesCnt = 0;
		try
		{
			for (int i = 0; i < files.length; i++)
			{
				List<String> dataList = new ArrayList<>();
				BufferedReader f = new BufferedReader(new FileReader(files[i]));
				String line = f.readLine();
				while (line != null)
				{
					dataList.add(line);
					line = f.readLine();
				}
				f.close();
				if (dataList.size() > linesCnt)
					linesCnt = dataList.size();
				fileList.add(dataList);
			}
			data.lines = new String[files.length][linesCnt];
			for (int i = 0; i < fileList.size(); i++)
			{
				List<String> dataList = fileList.get(i);
				for (int j = 0; j < dataList.size(); j++)
				{
					data.lines[i][j] = dataList.get(j);
				}
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DataException(e.getMessage());
		}
		return data;
	}

	public SearchData search(String key)
	{
		GoogleSearchData resultData = new GoogleSearchData();
		List<GoogleDataRow> rowList = new ArrayList<>();
		GoogleDataRow[] rows = (GoogleDataRow[])getRows();
		for (int i = 0; i < rows.length; i++)
		{
			GoogleDataRow row = rows[i];
			String line = row.getRow();
			if (line.indexOf(key) != -1)
				rowList.add(row);
		}
		resultData.setRows(rowList.toArray(new GoogleDataRow[0]));
		return resultData;
	}

	public GoogleSearchData googleSearch(String key)
	{
		GoogleSearchData resultData = new GoogleSearchData();
		List<GoogleDataRow> rowList = new ArrayList<>();
		for (int i = 0; i < lines.length; i++) {
			for (int j = 0; j < lines[i].length; j++) {
				String line = lines[i][j];
				if (line.indexOf(key) != -1) {
					GoogleDataRow row = new GoogleDataRow(line);
					rowList.add(row);
				}
			}
		}
		resultData.setRows(rowList.toArray(new GoogleDataRow[0]));
		return resultData;
	}
}
