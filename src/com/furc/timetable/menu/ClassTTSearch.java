package com.furc.timetable.menu;

import java.util.ArrayList;
import java.util.List;

import com.furc.timetable.R;
import com.furc.timetable.PreferencesKeys.PreferencesKeys;
import com.furc.timetable.googlespreadsheet.dbhandler.DatabaseHandler;
import com.furc.timetable.googlespreadsheet.dbhandler.TimeTableAttribSetterGetter;
import com.furc.timetable.gui.homescreen.CustomListViewAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ClassTTSearch extends Activity {

	private String tag = "ClassTTSearch";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_by_class_menu);

		Button searchBtn = (Button) findViewById(R.id.searchBtn_searchByClass_activity);
		final Context context = this;
		searchBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				TextView searchBox = (TextView) findViewById(R.id.searchBox_searchByClass_activity);
				String className = searchBox.getText().toString();
          
				//String querry = "select * from timetable where classBatch = \"BCSE 8\"";
				String querry = "select * from timetable where classBatch = \""
						+ className + "\"";
				
				ListView list = (ListView) findViewById(R.id.listView_searchByClass);
				DatabaseHandler dbHandler = new DatabaseHandler(context);
				
				List<TimeTableAttribSetterGetter> ttAttribList = new ArrayList<TimeTableAttribSetterGetter>();
				ttAttribList = dbHandler.readFromDatabase(querry);
				Log.i(tag, ""+ttAttribList.size());
				CustomListViewAdapter adapter = new CustomListViewAdapter(
						context, ttAttribList);
				Log.i(tag, "60"+adapter.getCount());
				list.setAdapter(adapter);

			}
		});
	//	super.onCreate(savedInstanceState);
	}

}
