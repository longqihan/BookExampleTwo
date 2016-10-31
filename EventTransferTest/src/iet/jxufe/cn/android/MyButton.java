package iet.jxufe.cn.android;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;
public class MyButton extends Button {
	public MyButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public boolean onTouchEvent(MotionEvent event) {
		System.out.println("MyButton中触摸事件触发了！");
		return false;
	}
}
