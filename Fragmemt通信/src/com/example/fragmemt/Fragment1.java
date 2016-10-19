package com.example.fragmemt;



import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;

public class Fragment1 extends Fragment {
	
	 
	
	private TextView tv2;




	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		
		View view = inflater.inflate(R.layout.fragment1, container, false);
		Button btn = (Button) view.findViewById(R.id.btn_fragment);
	    tv2 = (TextView)view.findViewById(R.id.tv2);
	 	
		
	      /*  String mParam2 = getArguments().getString("param1");  
	        TextView tv2 = (TextView)view.findViewById(R.id.tv2);  
	        tv2.setText(mParam2);  
	        */
	    
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Fragment2 fragment2 = Fragment2.newInstance("传来的图片");
				
				Toast.makeText(getActivity(), "请选中图片，点击！", 0).show();
				
				
				
				fragment2.setResultListener(new Fragment2.IEventListener() {
                   @Override
                    public void customDialogEvent(int selectID) {
                        ImageView imageView = (ImageView)getView().findViewById(R.id.img_result);
                        
                        imageView.setImageResource(selectID);
                    }
                });
				
				
				 
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				//ft.replace(R.id.main_layout, fragment2);
				
			    ft.add(R.id.main_layout, fragment2,"f2");

				ft.addToBackStack(null);
				
				ft.commit();
				 
				}
			
		});
		return view;
	}
	
	
	
	
	public void updateText(String content){
		tv2.setText(content);
		
		}
	/* public void onTitleSelect(String title) {  
	        FragmentManager manager = getFragmentManager();  
	        Fragment1 fragment1 = (Fragment1)manager.findFragmentById(R.id.tv2);  
	        fragment1.setText(title);  
	    }  
	
	   public void setText(String text) {  
	        mTv.setText(text);  
	    }  
	  */
	/*public static Fragment1 newInstance1(String text) {  
        Fragment1 fragment = new Fragment1();  
        Bundle args = new Bundle();  
        args.putString("param1", text);  
        fragment.setArguments(args);  
        return fragment;  
}
*/
}