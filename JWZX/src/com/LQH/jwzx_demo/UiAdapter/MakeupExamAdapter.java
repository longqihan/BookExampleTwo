package com.LQH.jwzx_demo.UiAdapter;

import java.util.List;

import com.LQH.jwzx_demo.bean.Nextexam;
import com.zhy.zhy_slidemenu_demo02.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MakeupExamAdapter extends BaseAdapter {

	private List<Nextexam> mktable;
	private View view;
	private Context mContext;
	private ViewHolder viewHolder;

	public MakeupExamAdapter(Context mContext, List<Nextexam> mktable) {
		this.mktable = mktable;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return mktable.size();
	}

	@Override
	public Object getItem(int position) {
		return mktable.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			view = LayoutInflater.from(mContext).inflate(
					R.layout.makeup_exam_item, null);
			viewHolder = new ViewHolder();

			viewHolder.name = (TextView) view.findViewById(R.id.name);
			viewHolder.test_time = (TextView) view.findViewById(R.id.test_time);
			viewHolder.curriculum = (TextView) view
					.findViewById(R.id.curriculum);
			viewHolder.ctrriculum_num = (TextView) view
					.findViewById(R.id.ctrriculum_num);
			viewHolder.XQ = (TextView) view.findViewById(R.id.xq);
			viewHolder.XY = (TextView) view.findViewById(R.id.xy);
			viewHolder.BJ = (TextView) view.findViewById(R.id.bj);
			viewHolder.KCLX = (TextView) view.findViewById(R.id.KCLX);
			viewHolder.JSH = (TextView) view.findViewById(R.id.JSH);
			viewHolder.GLDW = (TextView) view.findViewById(R.id.GLDW);
			viewHolder.KSFS = (TextView) view.findViewById(R.id.KSFS);

			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.name
				.setText("校区:        " + mktable.get(position).getName());
		viewHolder.test_time.setText("学院:        "
				+ mktable.get(position).getTest_time());
		viewHolder.curriculum.setText("班级:        "
				+ mktable.get(position).getCurriculum());
		viewHolder.ctrriculum_num.setText("姓名:             "
				+ mktable.get(position).getCtrriculum_num());
		viewHolder.XQ.setText("课程号:       " + mktable.get(position).getXQ());
		viewHolder.XY.setText("课程名称:       " + mktable.get(position).getXY());
		viewHolder.BJ.setText("课程类型：        " + mktable.get(position).getBJ());
		viewHolder.KCLX.setText("课程管理单位：        "
				+ mktable.get(position).getKCLX());
		viewHolder.JSH.setText("教室号：        " + mktable.get(position).getJSH());
		viewHolder.GLDW.setText("考试时间：        "
				+ mktable.get(position).getGLDW());
		viewHolder.KSFS.setText("考试方式：        "
				+ mktable.get(position).getKSFS());

		return view;
	}

	class ViewHolder {

		public TextView name;
		public TextView test_time;
		public TextView curriculum;
		public TextView ctrriculum_num;
		public TextView XQ, XY, BJ, KCLX, JSH, GLDW, KSFS;

	}

}
