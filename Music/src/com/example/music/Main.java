package com.example.music;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;

public class Main extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //设置窗口无标题和全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //显示主界面
        setContentView(R.layout.main);
        //背景音乐
        BackMusic.setContext(Main.this);
        BackMusic.playBackMusic(R.raw.backmusic,true);
        //按钮动画
        final ScaleAnimation click=new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        click.setFillAfter(true);
        final ScaleAnimation release=new ScaleAnimation(1.1f, 1.0f, 1.1f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        release.setFillAfter(true);
        final Button o = (Button) findViewById(R.id.o);
        o.startAnimation(click);
        o.startAnimation(release);
        // 获取应用程序中的'自由模式'按钮
		final Button freebtn = (Button) findViewById(R.id.freebtn);
		// 为'自由模式'按钮绑定事件监听器
		freebtn.setOnTouchListener(new View.OnTouchListener() {
        	public boolean onTouch(View view, MotionEvent motionEvent) {
        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {        			
        			freebtn.setBackgroundResource(R.drawable.main_freeplay_back);
        		}
        		if(motionEvent.getAction() == MotionEvent.ACTION_UP){
        			freebtn.setBackgroundResource(R.drawable.main_freeplay);
        			// 创建需要启动的Activity对应的Intent
    				Intent intent = new Intent(Main.this,InsChosen.class);
    				// 启动intent对应的Activity
    				startActivity(intent);
        		}
        		return false;
        		}
        });
		// 获取应用程序中的'练习模式'按钮
		final Button exbtn = (Button) findViewById(R.id.exerbtn);
		// 为'练习模式'按钮绑定事件监听器
		exbtn.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View view, MotionEvent motionEvent) {
				if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
					exbtn.setBackgroundResource(R.drawable.main_exercise_back);
				}
				if(motionEvent.getAction() == MotionEvent.ACTION_UP){
					exbtn.setBackgroundResource(R.drawable.main_exercise);
					// 创建需要启动的Activity对应的Intent
					Intent intent = new Intent(Main.this,MusicList.class);
					// 启动intent对应的Activity
					startActivity(intent);
				}
				return false;
		    }
		});
		// 获取应用程序中的'导入曲谱'按钮
		final Button loadbtn = (Button) findViewById(R.id.loadbtn);
		// 为'导入曲谱'按钮绑定事件监听器
		loadbtn.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View view, MotionEvent motionEvent) {
				if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
					loadbtn.startAnimation(click);
				}
				if(motionEvent.getAction() == MotionEvent.ACTION_UP){
					loadbtn.startAnimation(release);
					// 创建需要启动的Activity对应的Intent
					Intent intent = new Intent(Main.this,ImportTune.class);
					// 启动intent对应的Activity
					startActivity(intent);
				}
				return false;
			}
		});
		// 获取应用程序中的'导入曲谱'按钮
				final Button probtn = (Button) findViewById(R.id.probtn);
				// 为'导入曲谱'按钮绑定事件监听器
				probtn.setOnTouchListener(new View.OnTouchListener() {
					public boolean onTouch(View view, MotionEvent motionEvent) {
						if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
							probtn.startAnimation(click);
						}
						if(motionEvent.getAction() == MotionEvent.ACTION_UP){
							probtn.startAnimation(release);
							// 创建需要启动的Activity对应的Intent
							Intent intent = new Intent(Main.this,GeneTune.class);
							// 启动intent对应的Activity
							startActivity(intent);
						}
						return false;
					}
				});
		// 获取应用程序中的'EXIT'按钮
		final Button exit = (Button) findViewById(R.id.exit);
		// 为'EXIT'按钮绑定事件监听器
		exit.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View view, MotionEvent motionEvent) {
				if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
					exit.startAnimation(click);
				}
				if(motionEvent.getAction() == MotionEvent.ACTION_UP){
					exit.startAnimation(release);
					// 结束
					BackMusic.end();
					finish();
				}
				return false;
			}
		});
	}
}
