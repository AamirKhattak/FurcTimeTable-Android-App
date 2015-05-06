package com.furc.timetable.googlespreadsheet;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.Worksheet;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class GoogleSpreadSheet_Parser {
	private String tag = "GoogleSpreadSheet_Parser";
	// will contain raw cells data of parse Excel file

	/*ArrayList<String> parsedRawCells = new ArrayList<String>();
	private void setParsedRawCells(String cellData) {
		parsedRawCells.add(cellData);
	}

	public ArrayList<String> getParsedRawCells() throws IOException,
			ServiceException {
		this.parseSpreadSheet();
		return parsedRawCells;
	}*/

	private CellFeed cellFeed;
	
	public CellFeed getCellFeed() throws IOException, ServiceException{
		this.parseSpreadSheet();
		return this.cellFeed; 
	} 
	
	private void setCellFeed(CellFeed cellFeed){
		this.cellFeed = cellFeed;
	}
	
	public GoogleSpreadSheet_Parser() {
		Log.i(tag, "constructor ");
	}

	private void parseSpreadSheet() throws IOException, ServiceException {
		String USERNAME = "EMAIL";
		String PASSWORD = "PASSWORD";

		Log.i(tag, "service ");
		SpreadsheetService service = new SpreadsheetService(
				"MySpreadsheetIntegration");
		// spreadsheet api version
		service.setProtocolVersion(SpreadsheetService.Versions.V3);

		Log.i(tag, " verification ");
		// verifying credentials
		service.setUserCredentials(USERNAME, PASSWORD);

		// Define the URL to request. This should never change.
		URL SPREADSHEET_FEED_URL = new URL(
				"https://spreadsheets.google.com/feeds/spreadsheets/private/full");

		// Make a request to the API and get all spreadsheets.
		SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL,
				SpreadsheetFeed.class);
		List<SpreadsheetEntry> spreadsheets = feed.getEntries();

		Log.i(tag, "List<SpreadsheetEntry> spreadsheets = feed.getEntries();");

		if (spreadsheets.size() == 0) {
			// TODO: There were no spreadsheets, act accordingly.
			System.out.println("there are no spreadsheets in your drive");
			Log.i(tag, "there are not spreadsheets in your drive");
		} else {
			// Iterate through all of the spreadsheets returned
			for (SpreadsheetEntry spreadsheet : spreadsheets) {
				// Print the title of this spreadsheet to the screen
				System.out.println(spreadsheet.getTitle().getPlainText());
				Log.i(tag, spreadsheet.getTitle().getPlainText());
			}
		}

		// TODO: Choose a spreadsheet more intelligently based on your
		// app's needs.
		SpreadsheetEntry spreadsheet = spreadsheets.get(0);
		System.out.println(spreadsheet.getTitle().getPlainText());

		// Make a request to the API to fetch information about all
		// worksheets in the spreadsheet.
		List<WorksheetEntry> worksheets = spreadsheet.getWorksheets();

		// Iterate through each worksheet in the spreadsheet.
	/*	for (WorksheetEntry worksheet : worksheets) {
			// Get the worksheet's title, row count, and column count.
			String title = worksheet.getTitle().getPlainText();
			int rowCount = worksheet.getRowCount();
			int colCount = worksheet.getColCount();

			// Print the fetched information to the screen for this worksheet.
			System.out.println("\t" + title + "- rows:" + rowCount + " cols: "
					+ colCount);
		}*/

		// worksheets.get(0)
		URL cellFeedUrl = worksheets.get(0).getCellFeedUrl();
		CellFeed cellFeed = service.getFeed(cellFeedUrl, CellFeed.class);
		this.setCellFeed(cellFeed);
/*
		Log.i(tag, "above parsing loop");
		// Iterate through each cell, printing its value.
		for (CellEntry cell : cellFeed.getEntries()) {
			// writing to ArrayList<string> parsedRawCells
			System.out.println("writing to arrayList");
			Log.i(tag, "writing to arrayList");
			this.setParsedRawCells(cell.getCell().getValue());
		}*/
		/*
		 * // Print the cell's address in A1 notation
		 * System.out.print(cell.getTitle().getPlainText() + "\t"); // Print the
		 * cell's address in R1C1 notation
		 * System.out.print(cell.getId().substring(
		 * cell.getId().lastIndexOf('/') + 1) + "\t"); // Print the cell's
		 * formula or text value System.out.print("in value: " +
		 * cell.getCell().getInputValue() + "\t"); // Print the cell's
		 * calculated value if the cell's value is // numeric // Prints empty
		 * string if cell's value is not numeric
		 * System.out.print("numeric value:" + cell.getCell().getNumericValue()
		 * + "\t"); // Print the cell's displayed value (useful if the cell has
		 * a // formula) System.out.println("value: " +
		 * cell.getCell().getValue() + "\t");
		 */
		System.out.println("---END---");
	}// end-main
}// end-class
