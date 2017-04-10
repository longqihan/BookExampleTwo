package com.LQH.jwzx_demo.UiAdapter;

import java.util.List;

import com.LQH.jwzx_demo.bean.AllGrades;
import com.zhy.zhy_slidemenu_demo02.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AllGradeAdapter extends BaseAdapter {

	private List<AllGrades> atable;
	private View view;
	private Context mContext;
	private ViewHolder viewHolder;

	public AllGradeAdapter(Context mContext, List<AllGrades> atable) {
		this.atable = atable;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return atable.size();
	}

	@Override
	public Object getItem(int position) {
		return atable.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			view = LayoutInflater.from(mContext).inflate(
					R.layout.all_grade_item, null);
			viewHolder = new ViewHolder();
			viewHolder.KCH = (TextView) view.findViewById(R.id.KCH);
			viewHolder.KCM = (TextView) view.findViewById(R.id.KCM);
			viewHolder.SDXF = (TextView) view.findViewById(R.id.SDXF);
			viewHolder.KCCJ = (TextView) view.findViewById(R.id.KCCJ);
			viewHolder.BKCJ = (TextView) view.findViewById(R.id.BKCJ);
			viewHolder.BZF = (TextView) view.findViewById(R.id.BZF);

			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.KCH.setText("课程号:        "+atable.get(position).getKCH());
		viewHolder.KCM.setText("课程名:        "+atable.get(position).getKCM());
		viewHolder.SDXF.setText("学分:             "+atable.get(position).getSDXF());
		viewHolder.KCCJ.setText("考试成绩:       "+atable.get(position).getKCCJ());
		viewHolder.BKCJ.setText("补考成绩:       "+atable.get(position).getBKCJ());
		viewHolder.BZF.setText("标准分：        "+atable.get(position).getBZF());

		return view;
	}

	class ViewHolder {

		public TextView BZF;
		public TextView BKCJ;
		public TextView KCCJ;
		public TextView SDXF;
		public TextView KCM;
		public TextView KCH;

	}

}
