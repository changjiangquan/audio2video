package com.roi.audio2video.fragment;

import android.Manifest;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.roi.audio2video.AudioPlayerActivity;
import com.roi.audio2video.R;
import com.roi.audio2video.adapter.AudioListAdapter;
import com.roi.audio2video.base.BaseFragment;
import com.roi.audio2video.bean.AudioItem;
import com.roi.audio2video.db.SimpleQueryHandler;
import com.roi.audio2video.utils.LogUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;


public class AudioListFragment extends BaseFragment {

	private ListView listview;
	private AudioListAdapter adapter;
	private SimpleQueryHandler queryHandler;

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
							Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_audio_list, null);
		listview = (ListView) view.findViewById(R.id.listview);
		return view;
	}

	@Override
	protected void initListener() {
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				Cursor cursor = (Cursor) adapter.getItem(position);
				Bundle bundle = new Bundle();
				bundle.putInt("currentPosition", position);
				bundle.putSerializable("audioList", cursorToList(cursor));
				enterActivity(AudioPlayerActivity.class, bundle);
			}
		});
	}

	@Override
	protected void initData() {
		// 动态获取sd卡读权限
		new RxPermissions(getActivity()).request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
				.subscribe(new Consumer<Boolean>() {
					@Override
					public void accept(Boolean aBoolean) throws Exception {
						if (aBoolean) {
							//所有权限都开启aBoolean才为true，否则为false
							LogUtil.e("rrrrrrrr","权限开启!");
							adapter = new AudioListAdapter(getActivity(), null);
							listview.setAdapter(adapter);

							queryHandler = new SimpleQueryHandler(getActivity().getContentResolver());

							String[] projection = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME
									, MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.DURATION};
							queryHandler.startQuery(0, adapter, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
						} else {
							LogUtil.e("rrrrrrrr","获取权限失败!");
						}
					}
				});
	}

	/**
	 * 将cursor中 的数据取出来放入一个集合
	 * @param cursor
	 * @return
	 */
	private ArrayList<AudioItem> cursorToList(Cursor cursor)
	{
		ArrayList<AudioItem> list = new ArrayList<AudioItem>();
		cursor.moveToPosition(-1);
		while(cursor.moveToNext()){
			list.add(AudioItem.fromCursor(cursor));
		}
		return list;
	}

	@Override
	protected void processClick(View view) {

	}

}
