package com.furc.timetable.menu;

import java.io.IOException;

import android.content.Context;

import com.furc.timetable.googlespreadsheet.dbhandler.ParsedCellToDatabase;
import com.google.gdata.util.ServiceException;

public class TimetableUpdate {

	public void update(Context context) {
		ParsedCellToDatabase pp;
		try {
			pp = new ParsedCellToDatabase(context);
			pp.writingParsedCellInDatabase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
