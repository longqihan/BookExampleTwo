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
		//�ṩһ���޲εĹ��췽��
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
		return "�������";
	}
	protected void onPreExecute() {		
		super.onPreExecute();
	}
	protected void onPostExecute(String result) {		
		//ִ�н�������ؽ���������Ե�����
		tv.setText(result);
		tv.setTextColor(Color.RED);
		tv.setTextSize(20);
		pb.setVisibility(View.INVISIBLE);
		super.onPostExecute(result);
	}
	protected void onProgressUpdate(Integer... param) {		
		//���Ľ������������
		tv.setText("��ǰ��������"+param[0]+"%");
		pb.setProgress(param[0]);
		tv.setVisibility(View.VISIBLE);
		pb.setVisibility(View.VISIBLE);
		super.onProgressUpdate(param);
	}
}