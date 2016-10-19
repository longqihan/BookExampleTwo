package com.example.listviewmore;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	  ListView lv=(ListView) this.findViewById(R.id.lv);
		
		lv.setAdapter(new MyAdapter());
		
	}

	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 50;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view;
			if(convertView==null){
				//´òÆøÍ²
				view=View.inflate(getApplicationContext(), R.layout.item, null);
			}else {
				view=convertView;
			}
			
			return view;
		}
		
	}
	
	
	
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

}
