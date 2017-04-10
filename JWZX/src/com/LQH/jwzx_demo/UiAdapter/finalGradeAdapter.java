

package com.LQH.jwzx_demo.UiAdapter;

import java.util.List;

import com.LQH.jwzx_demo.bean.finalGrades;
import com.zhy.zhy_slidemenu_demo02.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class finalGradeAdapter extends BaseAdapter {

	private List<finalGrades> ftable;
	private View view;
	private Context mContext;
	private ViewHolder viewHolder;

	public finalGradeAdapter(Context mContext, List<finalGrades> ftable) {
		this.ftable = ftable;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return ftable.size();
	}

	@Override
	public Object getItem(int position) {
		return ftable.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			view = LayoutInflater.from(mContext).inflate(
					R.layout.final_grade_item, null);
			viewHolder = new ViewHolder();

			viewHolder.name = (TextView) view.findViewById(R.id.name);
			viewHolder.test_time = (TextView) view
					.findViewById(R.id.test_time);
			viewHolder.curriculum = (TextView) view
					.findViewById(R.id.curriculum);
			viewHolder.ctrriculum_num = (TextView) view
					.findViewById(R.id.ctrriculum_num);
			viewHolder.arithmetic = (TextView) view
					.findViewById(R.id.arithmetic);
			viewHolder.usually_resuit = (TextView) view
					.findViewById(R.id.usually_resuit);
			viewHolder.pratice_resuit = (TextView) view
					.findViewById(R.id.pratice_resuit);
			viewHolder.theory_result = (TextView) view
					.findViewById(R.id.theory_result);
			viewHolder.total_score = (TextView) view
					.findViewById(R.id.total_score);

			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.name.setText("姓名:        " + ftable.get(position).getName());
		viewHolder.test_time.setText("考试时间:        "
				+ ftable.get(position).getTest_time());
		viewHolder.curriculum.setText("课程名标识:        "
				+ ftable.get(position).getCurriculum());
		viewHolder.ctrriculum_num.setText("课程号:             "
				+ ftable.get(position).getCtrriculum_num());
		viewHolder.arithmetic.setText("算法名称:       "
				+ ftable.get(position).getArithmetic());
		viewHolder.usually_resuit.setText("平时成绩:       "
				+ ftable.get(position).getUsually_resuit());
		viewHolder.pratice_resuit.setText("实践成绩：        "
				+ ftable.get(position).getPratice_resuit());
		viewHolder.theory_result.setText("理论成绩：        "
				+ ftable.get(position).getTheory_result());
		viewHolder.total_score.setText("总成绩：        "
				+ ftable.get(position).getTotal_score());

		return view;
	}

	class ViewHolder {

		public TextView name;
		public TextView test_time;
		public TextView curriculum;
		public TextView ctrriculum_num;
		public TextView arithmetic;
		public TextView usually_resuit;
		public TextView pratice_resuit;
		public TextView theory_result;
		public TextView total_score;

	}

}


