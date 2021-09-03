package com.example.myapplication;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class TwoActivity extends Activity {
    private VideoView videoView;
    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //全屏
        play_mp4();

    }
    private void play_mp4() {
        String videoUrl1 = "android.resource://" + getPackageName() + "/" + R.raw.yuanshen2;
        Uri uri = Uri.parse( videoUrl1 );
        videoView = (VideoView) findViewById(R.id.videoView);

        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(uri);
        videoView.start();
    }
}
