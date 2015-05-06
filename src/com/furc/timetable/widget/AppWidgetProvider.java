package com.furc.timetable.widget;

import java.util.ArrayList;
import java.util.List;

import com.furc.timetable.R;
import com.furc.timetable.googlespreadsheet.dbhandler.DatabaseHandler;
import com.furc.timetable.googlespreadsheet.dbhandler.TimeTableAttribSetterGetter;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.ListView;
import android.widget.RemoteViews;

public class AppWidgetProvider  extends android.appwidget.AppWidgetProvider{

	DatabaseHandler dbHandler;
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		final int N = appWidgetIds.length;
		dbHandler = new DatabaseHandler(context);
		String className = "BCSE 8";
		List<TimeTableAttribSetterGetter> ttList = new ArrayList<TimeTableAttribSetterGetter>();
		ttList = dbHandler.readFromDatabase("select * from timetable where classBatch = \""+className+"\"");
		
		
		
		String text = "";
		/*for( int i =0; i< ttList.size();i++){
			text = text+ttList.get(i).getSubject()+"\n";
		}*/
		
		for( int i=0; i<N; i++){
			
			int currentWidgetID = appWidgetIds[i];
			
			RemoteViews remoteView = new RemoteViews( context.getPackageName(), R.layout.widget_layout);
			
			remoteView.setTextViewText(R.id.widget_tt_for_class_txtView, text);
			
			
			
			Intent intent = new Intent( context, AppWidgetProvider.class);
			
			intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
			appWidgetManager.updateAppWidget(currentWidgetID, remoteView);
			
		}
	}
	

}
