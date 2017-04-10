package com.LQH.jwzx_demo.UiAdapter;

import java.util.List;

import com.LQH.jwzx_demo.bean.Scheduletable;
import com.zhy.zhy_slidemenu_demo02.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ScheduleAdapter extends BaseAdapter {

	private List<Scheduletable> stable;
	private View view;
	private Context mContext;
	private ViewHolder viewHolder;

	public ScheduleAdapter(Context mContext, List<Scheduletable> stable) {
		this.stable = stable;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return stable.size();
	}

	@Override
	public Object getItem(int position) {
		return stable.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			view = LayoutInflater.from(mContext).inflate(
					R.layout.schedule_item, null);
			viewHolder = new ViewHolder();
			viewHolder.tableNum = (TextView) view.findViewById(R.id.date);
			viewHolder.monday = (TextView) view.findViewById(R.id.monday);
			viewHolder.tuesday = (TextView) view.findViewById(R.id.twoday);
			viewHolder.wendnesday = (TextView) view.findViewById(R.id.threeday);
			viewHolder.thursday = (TextView) view.findViewById(R.id.foureday);
			viewHolder.friday = (TextView) view.findViewById(R.id.fiveday);
			viewHolder.saturday = (TextView) view.findViewById(R.id.staeday);
			viewHolder.sunday = (TextView) view.findViewById(R.id.sunday);

			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.tableNum.setText(stable.get(position).getTableNum());
		viewHolder.monday.setText(stable.get(position).getMonday());
		viewHolder.tuesday.setText(stable.get(position).getTuesday());
		viewHolder.thursday.setText(stable.get(position).getThursday());
		viewHolder.friday.setText(stable.get(position).getFriday());
		viewHolder.wendnesday.setText(stable.get(position).getWendnesday());
		viewHolder.saturday.setText(stable.get(position).getSaturday());
		viewHolder.sunday.setText(stable.get(position).getSunday());

		return view;
	}

	class ViewHolder {
		public TextView sunday;
		public TextView saturday;
		public TextView friday;
		public TextView thursday;
		public TextView wendnesday;
		public TextView tuesday;
		public TextView monday;
		public TextView tableNum;

	}

	

}
