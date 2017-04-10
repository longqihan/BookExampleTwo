package com.LQH.jwzx_demo.utils;

import android.app.Application;

public class MyAppliction extends Application {

	private String SessionId;
	private String Loginstate="1234";
	private String photonum;

	

	@Override
	public void onCreate() {
		// TODO 自动生成的方法存根
		super.onCreate();
	}

	public String getLoginstate() {
		return Loginstate;
	}

	public void setLoginstate(String Loginstate) {
		this.Loginstate = Loginstate;
	}

	public String getSessionId() {
		return SessionId;
	}

	public void setSessionId(String SessionId) {
		this.SessionId = SessionId;
	}
	public String getPhotonum() {
		return photonum;
	}

	public void setPhotonum(String photonum) {
		this.photonum = photonum;
	}

}
