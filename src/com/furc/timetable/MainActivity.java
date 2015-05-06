package com.furc.timetable;

import java.util.ArrayList;
import java.util.List;

import com.furc.timetable.PreferencesKeys.PreferencesKeys;
import com.furc.timetable.googlespreadsheet.dbhandler.DatabaseHandler;
import com.furc.timetable.googlespreadsheet.dbhandler.TimeTableAttribSetterGetter;
import com.furc.timetable.gui.homescreen.CustomListViewAdapter;
import com.furc.timetable.menu.ClassSettings_Activity;
import com.furc.timetable.menu.ClassTTSearch;
import com.furc.timetable.menu.TimetableUpdate;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private String tag = "Mainactivity";

	private int CLASS_SETTING_REQUESTCODE = 1;

	String searchedClass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ListView list = (ListView) findViewById(R.id.timetable_listView_main);

		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		String className = preferences.getString(PreferencesKeys.CLASS_NAME,
				"BCSE4A");

		DatabaseHandler dbHandler = new DatabaseHandler(this);

		String querry = "select * from timetable where classBatch = \""
				+ className + "\"";
		
		List<TimeTableAttribSetterGetter> ttAttribList = new ArrayList<TimeTableAttribSetterGetter>();
		ttAttribList = dbHandler.readFromDatabase(querry);

		CustomListViewAdapter adapter = new CustomListViewAdapter(this,
				ttAttribList);
		list.setAdapter(adapter);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub

		ListView list = (ListView) findViewById(R.id.timetable_listView_main);

		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		String className = preferences.getString(PreferencesKeys.CLASS_NAME,
				"BCSE2A");


		DatabaseHandler dbHandler = new DatabaseHandler(this);

		String querry = "select * from timetable where classBatch = \""
				+ className + "\"";

		List<TimeTableAttribSetterGetter> ttAttribList = new ArrayList<TimeTableAttribSetterGetter>();
		ttAttribList = dbHandler.readFromDatabase(querry);

		CustomListViewAdapter adapter = new CustomListViewAdapter(this,
				ttAttribList);
		list.setAdapter(adapter);

		super.onStart();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Handle item selection
		switch (item.getItemId()) {

		case R.id.timetable_update: {
			Log.i(tag, "update button click");
			final Context context = this;
			
			ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo wifi = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			NetworkInfo gprs = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

			Toast.makeText(context, "Timetable Update Started",
					Toast.LENGTH_SHORT).show();
			Runnable runnable = new Runnable() {

				@Override
				public void run() {
					android.os.Process
							.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
					Log.i(tag, "thread");
					TimetableUpdate ttUpdate = new TimetableUpdate();
					ttUpdate.update(context);

				}
			};
			Thread thread = new Thread(runnable);
			thread.start();
			if (!thread.isAlive()) {
				Toast.makeText(context, "Timetable Updated", Toast.LENGTH_SHORT)
						.show();
			}
				return true;
		}// end-case-timetable-update

		case R.id.set_default_class_menu: {
			// Log.i(tag, "in updates class");
			Intent intent = new Intent();
			intent.setClass(this, ClassSettings_Activity.class);
			// Log.i(tag, "starting new activity");
			// startActivity( intent);
			startActivityForResult(intent, CLASS_SETTING_REQUESTCODE);
			return true;
		}

		case R.id.search_by_class_menu: {
			Log.i(tag, "search_by_class_menu");
			Intent intent = new Intent();
			intent.setClass(this, ClassTTSearch.class);
			Log.i(tag, "starting new activity");
			startActivity(intent);
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CLASS_SETTING_REQUESTCODE) {
			if (resultCode == RESULT_OK) {

				Bundle bundle = data.getExtras();
				String className = bundle.getString(PreferencesKeys.CLASS_NAME);

				SharedPreferences preferences = getPreferences(MODE_PRIVATE);
				SharedPreferences.Editor editor = preferences.edit();
				editor.putString(PreferencesKeys.CLASS_NAME, className);
				editor.commit();

				Log.i(tag, "150: " + className);

			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

}
