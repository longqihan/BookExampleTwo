package com.LQH.jwzx_demo.bean;

public class Scheduletable {

	private String tableNum;
	private String monday;
	private String tuesday;
	private String wendnesday;
	private String thursday;
	private String friday;
	private String saturday;
	private String sunday;

	public Scheduletable(String tableNum, String monday, String tuesday,
			String wendnesday, String thursday, String friday, String saturday,
			String sunday) {
		this.setTableNum(tableNum);
		this.setMonday(monday);
		this.setTuesday(tuesday);
		this.setWendnesday(wendnesday);
		this.setThursday(thursday);
		this.setFriday(friday);
		this.setSaturday(saturday);
		this.setSunday(sunday);

	}

	public String getSunday() {
		return sunday;
	}

	public void setSunday(String sunday) {
		this.sunday = sunday;
	}

	public String getSaturday() {
		return saturday;
	}

	public void setSaturday(String saturday) {
		this.saturday = saturday;
	}

	public String getFriday() {
		return friday;
	}

	public void setFriday(String friday) {
		this.friday = friday;
	}

	public String getThursday() {
		return thursday;
	}

	public void setThursday(String thursday) {
		this.thursday = thursday;
	}

	public String getWendnesday() {
		return wendnesday;
	}

	public void setWendnesday(String wendnesday) {
		this.wendnesday = wendnesday;
	}

	public String getTuesday() {
		return tuesday;
	}

	public void setTuesday(String tuesday) {
		this.tuesday = tuesday;
	}

	public String getMonday() {
		return monday;
	}

	public void setMonday(String monday) {
		this.monday = monday;
	}

	public String getTableNum() {
		return tableNum;
	}

	public void setTableNum(String tableNum) {
		this.tableNum = tableNum;
	}

}
