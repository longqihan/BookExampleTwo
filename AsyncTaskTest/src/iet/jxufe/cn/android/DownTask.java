package iet.jxufe.cn.android;

import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DownTask extends AsyncTask<Integer, Integer, String> {
	private TextView tv;
	private ProgressBar pb;
	public DownTask(TextView tv,ProgressBar pb){
		this.tv=tv;
		this.pb=pb;
	}
	public DownTask(){
		//提供一个无参的构造方法
	}
	protected String doInBackground(Integer... param) {	
		for(int i=0;i<=100;i++){			
			publishProgress(i);
			try{
				Thread.sleep(param[0]);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "下载完毕";
	}
	protected void onPreExecute() {		
		super.onPreExecute();
	}
	protected void onPostExecute(String result) {		
		//执行结束后，相关界面组件属性的设置
		tv.setText(result);
		tv.setTextColor(Color.RED);
		tv.setTextSize(20);
		pb.setVisibility(View.INVISIBLE);
		super.onPostExecute(result);
	}
	protected void onProgressUpdate(Integer... param) {		
		//更改界面组件的属性
		tv.setText("当前完成任务的"+param[0]+"%");
		pb.setProgress(param[0]);
		tv.setVisibility(View.VISIBLE);
		pb.setVisibility(View.VISIBLE);
		super.onProgressUpdate(param);
	}
}
