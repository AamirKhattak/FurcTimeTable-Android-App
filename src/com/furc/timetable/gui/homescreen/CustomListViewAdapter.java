package com.furc.timetable.gui.homescreen;

import java.util.List;
import com.furc.timetable.googlespreadsheet.dbhandler.TimeTableAttribSetterGetter;

import com.furc.timetable.R;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListViewAdapter extends
		ArrayAdapter<TimeTableAttribSetterGetter> {
	Context context;

	public CustomListViewAdapter(Context context,
			List<TimeTableAttribSetterGetter> ttAttribList) {

		super(context, R.layout.timetable_contents_listview, ttAttribList);
		this.context = context;
	}

	// ViewGroup is also known as layout manager, parent or root of view
	// elements
	@Override
	public View getView(int position, View view, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		TimeTableAttribSetterGetter ttAttribSetterGetter = (TimeTableAttribSetterGetter) getItem(position);

		
		// View rowView
		view = inflater.inflate(R.layout.timetable_contents_listview, parent,
				false);

		ViewHolder viewHolder;
		viewHolder = new ViewHolder();
		viewHolder.txtDay = ((TextView) view.findViewById(R.id.txtDay));
		viewHolder.txtSubj = ((TextView) view.findViewById(R.id.txtSubject));
		viewHolder.txtTime = ((TextView) view.findViewById(R.id.txtTime));
		viewHolder.txtRoom = ((TextView) view.findViewById(R.id.txtRoom));
		viewHolder.txtTeacher = ((TextView) view.findViewById(R.id.txtTeacher));

		view.setTag(viewHolder);

		String dayOfWeek = null;
		int color= 0;
		int i = Integer.parseInt(ttAttribSetterGetter.getDayOfWeek());
		
		switch (i) {
		case 1:
			dayOfWeek = "Mon";
			color = Color.GREEN;
			break;
		case 2:
			dayOfWeek = "Tue";
			color = Color.CYAN;
			break;
		case 3:
			dayOfWeek = "Wed";
			color = Color.BLUE;
			break;
		case 4:
			dayOfWeek = "Thu";
			color = Color.RED;
			break;
		case 5:
			dayOfWeek = "Fri";
			color = Color.MAGENTA;
			break;
		}
		Log.i("customAdapter", "dayOfWeek: " + dayOfWeek);
		// viewHolder.txtDay.setText("(: TEMP :)");
		viewHolder.txtDay.setText(dayOfWeek);
		viewHolder.txtDay.setTextColor( color);
		viewHolder.txtSubj.setText(ttAttribSetterGetter.getSubject());
		viewHolder.txtSubj.setTextColor( Color.parseColor("#CCE5FF"));
		viewHolder.txtRoom.setText(ttAttribSetterGetter.getRoom());
		viewHolder.txtTeacher.setText(ttAttribSetterGetter.getTeacher());
		viewHolder.txtTime.setText(ttAttribSetterGetter.getPeriod());
		viewHolder = (ViewHolder) view.getTag();
		return view;
	}// end-function

	private class ViewHolder {
		TextView txtDay;
		TextView txtRoom;
		TextView txtSubj;
		TextView txtTeacher;
		TextView txtTime;

		private ViewHolder() {
		}
	}
}
