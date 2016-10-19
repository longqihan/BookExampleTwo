package com.example.fragmemt;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment2 extends Fragment implements View.OnClickListener{
	
	private IEventListener mEventListener;
	/*private selectInterface mSelectInterface;
	
	
	public interface selectInterface{
		public void onSelectInterface(String select);

	}
	
    public void onAttach(Activity activity) {  
        super.onAttach(activity);  
      
        try {  
            mSelectInterface = (selectInterface) activity;  
        } catch (Exception e) {  
            throw new ClassCastException(activity.toString() + "must implement OnArticleSelectedListener");  
        }  
    }  */
	
	
	//接口构造回调函数
	public interface IEventListener {
        public void customDialogEvent(int selectID);
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view = inflater.inflate(R.layout.fragment2, container,false);
		/*ListView listView = (ListView) getView().findViewById(R.id.tv2);*/
		if (getArguments() != null) { 
		        String mParam1 = getArguments().getString("param");  
		        TextView tv = (TextView)view.findViewById(R.id.tv);  
		        tv.setText(mParam1);  
		}
		 
		    view.findViewById(R.id.img1).setOnClickListener(this);
	        view.findViewById(R.id.img2).setOnClickListener(this);
	        view.findViewById(R.id.img3).setOnClickListener(this);
	        view.findViewById(R.id.img4).setOnClickListener(this);
		
		return view;
	}
    public static Fragment2 newInstance(String text) {  
        Fragment2 fragment = new Fragment2();  
        Bundle args = new Bundle();  
        args.putString("param", text);  
        fragment.setArguments(args);  
        return fragment;  
    }  
    
    
	
     //回调函数
    public void setResultListener(IEventListener listener){
        mEventListener = listener;
    }

    
    
    
    @Override
    public void onClick(View view) {
    	
    	 
    	Fragment1 fragment1 = (Fragment1) getActivity().getFragmentManager().findFragmentByTag("f1");
        int id = view.getId();
        switch (id){
            case R.id.img1:{
            	
            	 fragment1.updateText("蓝猫被选中了");
                mEventListener.customDialogEvent(R.drawable.e);
               
               /* mSelectInterface.onSelectInterface("蓝猫被选中了");*/
               
            }
            break;
            case R.id.img2:{
            	
            	fragment1.updateText("黑猫被选中了");
                mEventListener.customDialogEvent(R.drawable.r);
               
            }
            break;
            case R.id.img3:{
            	fragment1.updateText("绿猫被选中了");
                mEventListener.customDialogEvent(R.drawable.w);
            }
            break;
            case R.id.img4:{
            	fragment1.updateText("剑猫被选中了");
                mEventListener.customDialogEvent(R.drawable.t);
            }
            break;
        }
      //退出当前fragment
        getFragmentManager().popBackStack();
    }
    
 
   
	
}
