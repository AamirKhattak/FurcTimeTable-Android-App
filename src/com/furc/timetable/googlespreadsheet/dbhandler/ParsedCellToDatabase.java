package com.furc.timetable.googlespreadsheet.dbhandler;

import android.content.Context;
import android.util.Log;

import com.furc.timetable.googlespreadsheet.GoogleSpreadSheet_Parser;
import com.google.gdata.data.spreadsheet.Cell;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.util.ServiceException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParsedCellToDatabase {

	public CellFeed cellFeed = new CellFeed();
	public Context context;
	public DatabaseHandler dbHandler;
	public int tCols;
	public int tRows;

	public ArrayList<String> parsedCellsData;
	private String tag ="ParsedCellToDatabase";
	
	public ParsedCellToDatabase(Context context) throws IOException, ServiceException {
		Log.i(tag, "constructer");
		this.cellFeed = new GoogleSpreadSheet_Parser().getCellFeed();
		this.tCols = this.cellFeed.getColCount();
		this.tRows = this.cellFeed.getRowCount();
		this.context = context;
		this.dbHandler = new DatabaseHandler(context);
		
		parsedCellsData = new ArrayList<String>();
	}
	


public void writingParsedCellInDatabase() {
		String period = null;
		int day = 0;
		// delete old database
		this.dbHandler.deleteDB();
		Iterator iterator = this.cellFeed.getEntries().iterator();
		while( iterator.hasNext()){
			
			CellEntry localCellEntry = (CellEntry) iterator.next();
			int j = localCellEntry.getCell().getRow();
			int k = localCellEntry.getCell().getCol();

			String cellValue = localCellEntry.getCell().getValue();
			if ((j < 5) || (j == 11) || (j == 17) || (j == 23) || (j == 29)
					|| (k == 1) || (k == 12)) {

				if (k == 1) {
					if (cellValue.toLowerCase().contains("830")) {
						day++;
						period = cellValue;
					} else if (cellValue.toLowerCase().contains("955")) {
						period = cellValue;
					} else if (cellValue.toLowerCase().contains("1120")) {
						period = cellValue;
					} else if (cellValue.toLowerCase().contains("1245")) {
						period = cellValue;
					} else if (cellValue.toLowerCase().contains("1410")) {
						period = cellValue;
					}
				}// end-if

			}// end-if
			else {
				String room = getRoom(k);
				String[] arrayOfString = cellValue.split("\\n");
				Log.i(tag, "arraylength : "+arrayOfString.length);
				if (arrayOfString.length > 2) {
					String subject = arrayOfString[0];
					String classBatch = arrayOfString[1];
					String teacher = arrayOfString[2];
					this.dbHandler.writeToDatabase(day, period, room, subject,
							classBatch, teacher);
				}//end-if
			}//end-else
		}//end-for
	}


	private String getRoom(int paramInt) {
		switch (paramInt) {
		case 12:
		default:
			return null;
		case 2:
			return "Room 1";
		case 3:
			return "Room 2";
		case 4:
			return "Room 5";
		case 5:
			return "Room 10";
		case 6:
			return "Room 11";
		case 7:
			return "Room 13";
		case 8:
			return "Room 14";
		case 9:
			return "Room 16";
		case 10:
			return "Room 17";
		case 11:
			return "Room 19";
		case 13:
			return "IT Lab A";
		case 14:
			return "IT Lab B";
		case 15:
			return "IT Lab C";
		case 16:
			return "IT Lab D";
		case 17:
			return "TE Lab 1";
		case 18:
			return "TE Lab 2";
		case 19:
			return "TE Lab 3";
		case 20:
			return "TE Lab 4";
		case 21:
			return "TE Lab 5";
		case 22:
			return "TE Lab 6";
		}

	}
}
	
	/*	public void PopulateDatabase() throws IOException, ServiceException{
	Log.i(tag, "populate db");
	// get ArrayList 
	// 1. remove data cells not needed e.g. days, etc.
	// 2. parse cellData
	
	Log.i(tag, "creating object of gss_parser");
	GoogleSpreadSheet_Parser gss_parser = new GoogleSpreadSheet_Parser();
			
	Log.i(tag, "get ArrayList(cellFeeds)");
	parsedCellsData = gss_parser.getParsedRawCells();
	
	Log.i(tag, "gss_parser.getParsedRawCells().size() : "+ gss_parser.getParsedRawCells().size());
	int size = parsedCellsData.size();
	Log.i(tag, "parsedCellsData.size() : "+ size);
	for( int i=0; i<size; i++){
		System.out.println(parsedCellsData.get(i));
		Log.i(tag, parsedCellsData.get(i));
	}*/
