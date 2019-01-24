package com.example.music;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;

public class InsChosen extends Activity {
	Intent intent;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //设置窗口无标题和全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //显示乐器选择界面
        setContentView(R.layout.ins_chosen);
        //按钮动画
        final ScaleAnimation click=new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        click.setFillAfter(true);
        final ScaleAnimation release=new ScaleAnimation(1.1f, 1.0f, 1.1f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        release.setFillAfter(true);
        final Button o = (Button) findViewById(R.id.o);
        o.startAnimation(click);
        o.startAnimation(release);        
       // 获取应用程序中乐器按钮
        final Button piano = (Button) findViewById(R.id.piano);
        final Button drum = (Button) findViewById(R.id.drum);
        final Button wood = (Button) findViewById(R.id.wood);
        final Button keyboard = (Button) findViewById(R.id.keyboard);
        final Button metro = (Button) findViewById(R.id.metro);
        final Button rtn = (Button) findViewById(R.id.rtn);
        // 为'钢琴'按钮绑定事件监听器
        piano.setOnTouchListener(new View.OnTouchListener() {
        	public boolean onTouch(View view, MotionEvent motionEvent) {
        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        			piano.startAnimation(click);
        		}
        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {   
        			piano.startAnimation(release);        	
        			BackMusic.pauseBackMusic();
    				// 启动intent对应的Activity
        			intent = new Intent(InsChosen.this,Piano.class);
    				startActivity(intent);    				
                }
        		return false;
        	}
        }); 
        // 为'架子鼓'按钮绑定事件监听器
        drum.setOnTouchListener(new View.OnTouchListener() {
        	public boolean onTouch(View view, MotionEvent motionEvent) {
        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        			drum.startAnimation(click);
        		}
        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {  
        			drum.startAnimation(release);
        			BackMusic.pauseBackMusic();
    				// 启动intent对应的Activity
        			intent = new Intent(InsChosen.this,Drum.class);
    				startActivity(intent);
                }
        		return false;
        	}
        }); 
        // 为'木琴'按钮绑定事件监听器
        wood.setOnTouchListener(new View.OnTouchListener() {
        	public boolean onTouch(View view, MotionEvent motionEvent) {
        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        			wood.startAnimation(click);
        		}
        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {   
        			wood.startAnimation(release);
        			BackMusic.pauseBackMusic();
    				// 启动intent对应的Activity

        			intent = new Intent(InsChosen.this,Muqin.class);
    				startActivity(intent);
                }
        		return false;
        	}
        }); 
        // 为'电子琴'按钮绑定事件监听器
        keyboard.setOnTouchListener(new View.OnTouchListener() {
        	public boolean onTouch(View view, MotionEvent motionEvent) {
        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        			keyboard.startAnimation(click);
        		}
        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {   
        			keyboard.startAnimation(release);
        			BackMusic.pauseBackMusic();
    				// 启动intent对应的Activity
        			intent = new Intent(InsChosen.this,KeyBoard.class);
    				startActivity(intent);
                }
        		return false;
        	}
        }); 
        // 为'吉他'按钮绑定事件监听器
        metro.setOnTouchListener(new View.OnTouchListener() {
        	public boolean onTouch(View view, MotionEvent motionEvent) {
        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        			metro.startAnimation(click);
        		}
        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {    
        			metro.startAnimation(release);
        			BackMusic.pauseBackMusic();
    				// 启动intent对应的Activity
        			intent = new Intent(InsChosen.this,Metronome.class);
    				startActivity(intent);
                }
        		return false;
        	}
        }); 
        // 为'返回'按钮绑定事件监听器
        rtn.setOnTouchListener(new View.OnTouchListener() {
        	public boolean onTouch(View view, MotionEvent motionEvent) {
        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        			rtn.startAnimation(click);
        		}
        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {   
        			rtn.startAnimation(release);
    				// 启动intent对应的Activity
        			finish();
                }
        		return false;
        	}
        }); 
	}
}
