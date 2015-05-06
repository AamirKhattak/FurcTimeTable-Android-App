package com.furc.timetable.googlespreadsheet.dbhandler;

public class TimeTableAttribSetterGetter {
	
	private String ClassBatch;
	private String DayOfWeek;
	private String Period;
	private String Room;
	private String Subject;
	private String Teacher;

	public String getClassBatch() {
		return this.ClassBatch;
	}

	public String getDayOfWeek() {
		return this.DayOfWeek;
	}

	public String getPeriod() {
		return this.Period;
	}

	public String getRoom() {
		return this.Room;
	}

	public String getSubject() {
		return this.Subject;
	}

	public String getTeacher() {
		return this.Teacher;
	}

	public void setClassBatch(String classBatch) {
		this.ClassBatch = classBatch;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.DayOfWeek = dayOfWeek;
	}

	public void setPeriod(String period) {
		this.Period = period;
	}

	public void setRoom(String room) {
		this.Room = room;
	}

	public void setSubject(String subject) {
		this.Subject = subject;
	}

	public void setTeacher(String teacher) {
		this.Teacher = teacher;
	}

}
