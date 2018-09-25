package com.roi.audio2video.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class BaseActivity extends AppCompatActivity implements OnClickListener
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initListener();
		initData();
	}
	
	protected abstract void initView();
	protected abstract void initListener();
	protected abstract void initData();
	protected abstract void processClick(View view);

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		processClick(v);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return super.dispatchTouchEvent(ev);
	}
}
