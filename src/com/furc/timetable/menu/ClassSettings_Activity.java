package com.furc.timetable.menu;

import java.util.ArrayList;

import com.furc.timetable.R;
import com.furc.timetable.PreferencesKeys.PreferencesKeys;
import com.furc.timetable.googlespreadsheet.dbhandler.DatabaseHandler;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ClassSettings_Activity extends ListActivity {

	DatabaseHandler handler = new DatabaseHandler(this);

	ArrayList<String> classBatches;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		handler.readFromDatabase("select distinct classBatch from timetable");
		classBatches = new ArrayList<String>();
		//handler.getClassBatchList().remove(handler.getClassBatchList().size());
		classBatches = handler.getClassBatchList();
		setListAdapter(new ArrayAdapter<String>(this,
				R.layout.set_default_class_activity, classBatches));

		ListView list = getListView();
		list.setTextFilterEnabled(true);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				String className = ((TextView) view).getText().toString();

				Intent intent = new Intent();
				intent.putExtra(PreferencesKeys.CLASS_NAME, className);
				setResult(RESULT_OK, intent);
				finish();

			}
		});
	}

	/*
	 * @Override protected void onCreate(Bundle savedInstanceState) { // TODO
	 * Auto-generated method stub super.onCreate(savedInstanceState);
	 * 
	 * setContentView(R.layout.menu_class_settings);
	 * 
	 * final TextView className = (TextView)
	 * findViewById(R.id.classname_classSettings_menu); Button submit = (Button)
	 * findViewById(R.id.submit_classSettings_menu);
	 * submit.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) {
	 * 
	 * String classNameTxt = className.getText().toString(); //Log.i("prefe",
	 * classNameTxt);
	 * 
	 * Intent intent = new Intent(); intent.putExtra(PreferencesKeys.CLASS_NAME,
	 * classNameTxt); setResult(RESULT_OK, intent);
	 * 
	 * 
	 * finish(); } });
	 */

}
