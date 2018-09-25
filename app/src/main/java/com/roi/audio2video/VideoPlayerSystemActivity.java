package com.roi.audio2video;

import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.roi.audio2video.base.BaseActivity;
import com.roi.audio2video.bean.VideoItem;

import java.util.ArrayList;

/**
 * Created by changquan on 2018/7/23.
 * 使用系统自带的播放器
 */
public class VideoPlayerSystemActivity extends BaseActivity{

    private VideoView videoView;
    private ArrayList<VideoItem> videoList;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_video_player_sys);
        videoView = findViewById(R.id.video);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
       int currentPosition = getIntent().getExtras().getInt("currentPosition");
       videoList = (ArrayList<VideoItem>) getIntent().getExtras().getSerializable("videoList");
       playVideo(currentPosition);

    }

    @Override
    protected void processClick(View view) {

    }


    /**
     * 播放
     * @author changquan
     * @param currentPosition 当前播放文件的角标
     * @retrun
     */
    private void playVideo(int currentPosition) {
        if(videoList==null || videoList.size()==0){
            finish();
            return;
        }

        VideoItem videoItem = videoList.get(currentPosition);
        videoView.setVideoURI(Uri.parse(videoItem.getPath()));
        videoView.setMediaController(new MediaController(this));//显示控制栏
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.start();//开始播放视频
            }
        });
    }
}
