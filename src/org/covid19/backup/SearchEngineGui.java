package org.covid19.backup;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;

import org.covid19.Covid19DataRow;
import org.covid19.Covid19SearchData;
import org.covid19.Covid19SearchEngine;
import org.covid19.DataException;
import org.covid19.GoogleDataRow;
import org.covid19.GoogleSearchData;
import org.covid19.GoogleSearchEngine;
import org.covid19.SearchData;
import org.covid19.SearchEngine;

public class SearchEngineGui extends Frame implements ActionListener, WindowListener {
	static final String COVID19_ENGINE = "Covid19SearchEngine";
	static final String GOOGLE_ENGINE = "GoogleSearchEngine";
	static final String COVID19_DATAFILE = "Covid19Dataset.csv";
	static final String GOOGLE_DATAFILE = "GoogleDataset.txt";
	private Choice engineChoice;
	private Choice dataFile;
	private TextField keyField;
	private Button search;
	private Panel result;
	private SearchData resultData;

	// Constructor to setup GUI components and event handlers
	public SearchEngineGui() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		Label engine = new Label("Search Engine: ");
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		add(engine, c);

		engineChoice = new Choice();
		engineChoice.addItem(COVID19_ENGINE);
		engineChoice.addItem(GOOGLE_ENGINE);
		c.gridx = 1;
		c.gridy = 0;
		add(engineChoice, c);

		Label data = new Label("Search Engine Data: ");
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		add(data, c);

		dataFile = new Choice();
		dataFile.addItem(COVID19_DATAFILE);
		dataFile.addItem(GOOGLE_DATAFILE);
		c.gridx = 1;
		c.gridy = 1;
		add(dataFile, c);

		Label key = new Label("Search Key: ");
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 2;
		add(key, c);

		keyField = new TextField();
		c.gridx = 1;
		c.gridy = 2;
		add(keyField, c);
		keyField.addActionListener(this);

		search = new Button("Search");
		c.gridx=0;
		c.gridy=3;
		c.gridwidth=2;
		c.insets = new Insets(0,150,0,150);
		add(search, c);
		search.addActionListener(this);
		
		key = new Label("Search Result: ");
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 4;
		c.insets = new Insets(0,0,0,0);
		add(key, c);

		result = new Panel();
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth=2;
		add(result, c);

		addWindowListener(this);

		setTitle("Search Engine GUI"); // "super" Frame sets title
		setSize(600, 300); // "super" Frame sets initial size
		setVisible(true); // "super" Frame shows
	}

	private void fillCovid19Result() {
		result.removeAll();
		result.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
	
		if (engineChoice.getSelectedItem().equals(COVID19_ENGINE) && dataFile.getSelectedItem().equals(GOOGLE_DATAFILE)) {
			Label label = new Label("Wrong data file: " + GOOGLE_DATAFILE, Label.CENTER);
			c.gridx = 0;
			c.gridy = 0;
			result.add(label, c);
			setVisible(true);
			return;
		}
		Label label = new Label("CaseCount", Label.CENTER);
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		result.add(label, c);

		label = new Label("Type", Label.CENTER);
		c.gridx = 1;
		c.gridy = 0;
		result.add(label, c);

		label = new Label("Date", Label.CENTER);
		c.gridx = 2;
		c.gridy = 0;
		result.add(label, c);
		label = new Label("Zip", Label.CENTER);
		c.gridx = 3;
		c.gridy = 0;
		result.add(label, c);
		label = new Label("City", Label.CENTER);
		c.gridx = 4;
		c.gridy = 0;
		result.add(label, c);

		Covid19DataRow[] rows = (Covid19DataRow[])resultData.getRows();
		for (int i = 0; i < rows.length; i++) {
			label = new Label(String.valueOf(rows[i].getCaseCount()), Label.CENTER);
			c.gridx = 0;
			c.gridy = i+1;
			result.add(label, c);
			label = new Label(rows[i].getType(), Label.CENTER);
			c.gridx = 1;
			c.gridy = i+1;
			result.add(label, c);
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
			label = new Label(formatter.format(rows[i].getDate()), Label.CENTER);
			c.gridx = 2;
			c.gridy = i+1;
			result.add(label, c);
			label = new Label(rows[i].getZipcode(), Label.CENTER);
			c.gridx = 3;
			c.gridy = i+1;
			result.add(label, c);
			label = new Label(rows[i].getCity(), Label.CENTER);
			c.gridx = 4;
			c.gridy = i+1;
			result.add(label, c);
		}
		setVisible(true);
	}

	private void fillGoogleResult() {
		result.removeAll();
		result.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		Label label;

		GoogleDataRow[] rows = (GoogleDataRow[])resultData.getRows();
		for (int i = 0; i < rows.length; i++) {
			label = new Label(rows[i].getRow(), Label.LEFT);
			c.gridx = 0;
			c.gridy = i;
			result.add(label, c);
		}
		setVisible(true);
	}
	// The entry main() method
	public static void main(String[] args) {
		new SearchEngineGui(); // Let the constructor do the job
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SearchEngine engine = null;
		try {
			if (engineChoice.getSelectedItem().equals(COVID19_ENGINE)) {
				if (dataFile.getSelectedItem().equals(COVID19_DATAFILE)) {
					Covid19SearchData data = Covid19SearchData.parse(COVID19_DATAFILE);
					engine = new Covid19SearchEngine();
					engine.setData(data);
				}
			} else {
				GoogleSearchData data = GoogleSearchData.parse(dataFile.getSelectedItem());
				engine = new GoogleSearchEngine();
				engine.setData(data);	
			}
			//System.out.println(engineChoice.getSelectedItem() + " search " + keyField.getText());
			if (engine != null)
				resultData = engine.search(keyField.getText());
			if (engineChoice.getSelectedItem().equals(COVID19_ENGINE))
				fillCovid19Result();
			else
				fillGoogleResult();
		} catch (DataException e1) {
			e1.printStackTrace();
		}
	}
	
	public void windowActivated(WindowEvent e) {}  
	public void windowClosed(WindowEvent e) {}  
	public void windowClosing(WindowEvent e) {  
	    dispose();  
	}  
	public void windowDeactivated(WindowEvent e) {}  
	public void windowDeiconified(WindowEvent e) {}  
	public void windowIconified(WindowEvent e) {}  
	public void windowOpened(WindowEvent arg0) {}  
}
