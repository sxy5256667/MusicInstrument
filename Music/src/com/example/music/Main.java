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
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //���ô����ޱ����ȫ��
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //��ʾ������
        setContentView(R.layout.main);
        //��������
        BackMusic.setContext(Main.this);
        BackMusic.playBackMusic(R.raw.backmusic,true);
        //��ť����
        final ScaleAnimation click=new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        click.setFillAfter(true);
        final ScaleAnimation release=new ScaleAnimation(1.1f, 1.0f, 1.1f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        release.setFillAfter(true);
        final Button o = (Button) findViewById(R.id.o);
        o.startAnimation(click);
        o.startAnimation(release);
        // ��ȡӦ�ó����е�'����ģʽ'��ť
		final Button freebtn = (Button) findViewById(R.id.freebtn);
		// Ϊ'����ģʽ'��ť���¼�������
		freebtn.setOnTouchListener(new View.OnTouchListener() {
        	public boolean onTouch(View view, MotionEvent motionEvent) {
        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {        			
        			freebtn.setBackgroundResource(R.drawable.main_freeplay_back);
        		}
        		if(motionEvent.getAction() == MotionEvent.ACTION_UP){
        			freebtn.setBackgroundResource(R.drawable.main_freeplay);
        			// ������Ҫ������Activity��Ӧ��Intent
    				Intent intent = new Intent(Main.this,InsChosen.class);
    				// ����intent��Ӧ��Activity
    				startActivity(intent);
        		}
        		return false;
        		}
        });
		// ��ȡӦ�ó����е�'��ϰģʽ'��ť
		final Button exbtn = (Button) findViewById(R.id.exerbtn);
		// Ϊ'��ϰģʽ'��ť���¼�������
		exbtn.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View view, MotionEvent motionEvent) {
				if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
					exbtn.setBackgroundResource(R.drawable.main_exercise_back);
				}
				if(motionEvent.getAction() == MotionEvent.ACTION_UP){
					exbtn.setBackgroundResource(R.drawable.main_exercise);
					// ������Ҫ������Activity��Ӧ��Intent
					Intent intent = new Intent(Main.this,MusicList.class);
					// ����intent��Ӧ��Activity
					startActivity(intent);
				}
				return false;
		    }
		});
		// ��ȡӦ�ó����е�'��������'��ť
		final Button loadbtn = (Button) findViewById(R.id.loadbtn);
		// Ϊ'��������'��ť���¼�������
		loadbtn.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View view, MotionEvent motionEvent) {
				if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
					loadbtn.startAnimation(click);
				}
				if(motionEvent.getAction() == MotionEvent.ACTION_UP){
					loadbtn.startAnimation(release);
					// ������Ҫ������Activity��Ӧ��Intent
					Intent intent = new Intent(Main.this,ImportTune.class);
					// ����intent��Ӧ��Activity
					startActivity(intent);
				}
				return false;
			}
		});
		// ��ȡӦ�ó����е�'��������'��ť
				final Button probtn = (Button) findViewById(R.id.probtn);
				// Ϊ'��������'��ť���¼�������
				probtn.setOnTouchListener(new View.OnTouchListener() {
					public boolean onTouch(View view, MotionEvent motionEvent) {
						if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
							probtn.startAnimation(click);
						}
						if(motionEvent.getAction() == MotionEvent.ACTION_UP){
							probtn.startAnimation(release);
							// ������Ҫ������Activity��Ӧ��Intent
							Intent intent = new Intent(Main.this,GeneTune.class);
							// ����intent��Ӧ��Activity
							startActivity(intent);
						}
						return false;
					}
				});
		// ��ȡӦ�ó����е�'EXIT'��ť
		final Button exit = (Button) findViewById(R.id.exit);
		// Ϊ'EXIT'��ť���¼�������
		exit.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View view, MotionEvent motionEvent) {
				if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
					exit.startAnimation(click);
				}
				if(motionEvent.getAction() == MotionEvent.ACTION_UP){
					exit.startAnimation(release);
					// ����
					BackMusic.end();
					finish();
				}
				return false;
			}
		});
	}
}
