package com.roi.audio2video.fragment;

import android.Manifest;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore.Video.Media;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.roi.audio2video.R;
import com.roi.audio2video.VideoPlayerSystemActivity;
import com.roi.audio2video.adapter.VideoListAdapter;
import com.roi.audio2video.base.BaseFragment;
import com.roi.audio2video.bean.VideoItem;
import com.roi.audio2video.db.SimpleQueryHandler;
import com.roi.audio2video.utils.LogUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;


public class VideoListFragment extends BaseFragment {

	private ListView listview;
	private VideoListAdapter adapter;
	private SimpleQueryHandler queryHandler;

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_video_list, null);
		listview = (ListView) view.findViewById(R.id.listview);
		return view;
	}

	@Override
	protected void initListener() {
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {

				Cursor cursor = (Cursor) adapter.getItem(position);
				ArrayList<VideoItem> videoList = cursorToList(cursor);
				Bundle bundle = new Bundle();
				bundle.putInt("currentPosition", position);
				bundle.putSerializable("videoList", videoList);
//				enterActivity(VideoPlayerActivity.class, bundle);
				enterActivity(VideoPlayerSystemActivity.class, bundle);


			}
		});
	}

	@Override
	protected void initData(){

		// 动态获取sd卡读权限
		new RxPermissions(getActivity()).request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
				.subscribe(new Consumer<Boolean>() {
					@Override
					public void accept(Boolean aBoolean) throws Exception {
						if (aBoolean) {
							//所有权限都开启aBoolean才为true，否则为false
							adapter = new VideoListAdapter(getActivity(), null);
							listview.setAdapter(adapter);

							queryHandler = new SimpleQueryHandler(getActivity().getContentResolver());
							String[] projection = {Media._ID,Media.SIZE,Media.DURATION,Media.TITLE,Media.DATA};
                           //		Cursor cursor = getActivity().getContentResolver().query(Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
							queryHandler.startQuery(0, adapter, Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
						} else {
						}
					}
				});

	}

	@Override
	protected void processClick(View view) {
		
	}

	/**
	 * 将cursor中的数据解析出并放入集合
	 * @param cursor
	 * @return
	 */
	private ArrayList<VideoItem> cursorToList(Cursor cursor){
		cursor.moveToPosition(-1);//将cursor移动到最初位置，否则获取到的数据很可能不全

		ArrayList<VideoItem> list = new ArrayList<VideoItem>();

		//遍历cursor的所有结果集，然后将每一条结果集转成VideoItem对象放入集合当中
		while(cursor.moveToNext()){
			list.add(VideoItem.fromCursor(cursor));
		}
		return list;
	}

}
