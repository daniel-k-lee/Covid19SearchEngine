package org.covid19;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;

public class SearchEngineGui extends Frame implements ActionListener, WindowListener, ItemListener {
	static final String COVID19_ENGINE = "Covid19SearchEngine";
	static final String GOOGLE_ENGINE = "GoogleSearchEngine";
	static final String COVID19_DATAFILE = "Covid19Dataset.csv";
	static final String GOOGLE_DATAFILE = "GoogleDataset.txt";
	private Label title;
	private Panel center;
	private Choice engineChoice;
	private Choice dataFile;
	private TextField keyField;
	private Button searchButton;
	private Panel result;
	private SearchData resultData;

	// Constructor to setup GUI components and event handlers
	public SearchEngineGui() {
		setLayout(new BorderLayout());
		
		Panel tPanel = new Panel();
		tPanel.setLayout(new FlowLayout());
		title = new Label("Search Engine GUI");
		title.setFont(new Font("Courier", Font.BOLD, 14));
		tPanel.add(title);
		add(tPanel, BorderLayout.NORTH);
		center = new Panel();
		add(center, BorderLayout.CENTER);
		center.setLayout(new GridLayout(4, 1));
		
		Panel enginePanel = new Panel();
		center.add(enginePanel);
		enginePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		Label engine = new Label("Search Engine: ");
		enginePanel.add(engine);
		engineChoice = new Choice();
		engineChoice.addItem(COVID19_ENGINE);
		engineChoice.addItem(GOOGLE_ENGINE);
		enginePanel.add(engineChoice);
		engineChoice.addItemListener(this);

		Panel dataPanel = new Panel();
		center.add(dataPanel);
		dataPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		Label data = new Label("Search Engine Data: ");
		dataPanel.add(data);
		dataFile = new Choice();
		dataFile.addItem(COVID19_DATAFILE);
		dataFile.addItem(GOOGLE_DATAFILE);
		dataPanel.add(dataFile);
		dataFile.addItemListener(this);

		Panel searchPanel = new Panel();
		center.add(searchPanel);
		searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		Label key = new Label("Search Key: ");
		searchPanel.add(key);
		keyField = new TextField();
		keyField.setColumns(20);
		searchPanel.add(keyField);
		//keyField.addActionListener(this);

		searchButton = new Button("Search");
		center.add(searchButton);
		searchButton.addActionListener(this);

		result = new Panel();
		add(result, BorderLayout.SOUTH);

		addWindowListener(this);

		setTitle("Search Engine GUI"); // "super" Frame sets title
		setSize(600, 300); // "super" Frame sets initial size
		setVisible(true); // "super" Frame shows
	}

	private void fillCovid19Result() {
		result.removeAll();
		Covid19DataRow[] rows = (Covid19DataRow[])resultData.getRows();
		result.setLayout(new GridLayout(rows.length+1, 5));

		Font subTitleFont = new Font("Courier", Font.BOLD,12);
		Label label = new Label("CaseCount", Label.CENTER);
		label.setFont(subTitleFont);
		result.add(label);

		label = new Label("Type", Label.CENTER);
		label.setFont(subTitleFont);
		result.add(label);

		label = new Label("Date", Label.CENTER);
		label.setFont(subTitleFont);
		result.add(label);
		label = new Label("Zip", Label.CENTER);
		label.setFont(subTitleFont);
		result.add(label);
		label = new Label("City", Label.CENTER);
		label.setFont(subTitleFont);
		result.add(label);


		for (int i = 0; i < rows.length; i++) {
			label = new Label(String.valueOf(rows[i].getCaseCount()), Label.CENTER);
			result.add(label);
			label = new Label(rows[i].getType(), Label.CENTER);
			result.add(label);
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
			label = new Label(formatter.format(rows[i].getDate()), Label.CENTER);
			result.add(label);
			label = new Label(rows[i].getZipcode(), Label.CENTER);
			result.add(label);
			label = new Label(rows[i].getCity(), Label.CENTER);
			result.add(label);
		}
		setVisible(true);
	}

	private void fillGoogleResult() {
		result.removeAll();
		GoogleDataRow[] rows = (GoogleDataRow[])resultData.getRows();
		result.setLayout(new GridLayout(rows.length, 1));
		Label label;

		for (int i = 0; i < rows.length; i++) {
			label = new Label(rows[i].getRow(), Label.LEFT);
			result.add(label);
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
	
	public void itemStateChanged(ItemEvent ie) {
		if (engineChoice.getSelectedItem().equals(COVID19_ENGINE)) {
			if (dataFile.getSelectedItem().equals(COVID19_DATAFILE)) {
				engineChoice.setBackground(Color.white);
				dataFile.setBackground(Color.white);
				searchButton.setEnabled(true);
			} else {
				engineChoice.setBackground(Color.red);
				dataFile.setBackground(Color.red);
				searchButton.setEnabled(false);
			}
		} else {
			engineChoice.setBackground(Color.white);
			dataFile.setBackground(Color.white);
			searchButton.setEnabled(true);
		}
	}
}
