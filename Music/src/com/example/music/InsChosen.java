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
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //���ô����ޱ����ȫ��
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //��ʾ����ѡ�����
        setContentView(R.layout.ins_chosen);
        //��ť����
        final ScaleAnimation click=new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        click.setFillAfter(true);
        final ScaleAnimation release=new ScaleAnimation(1.1f, 1.0f, 1.1f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        release.setFillAfter(true);
        final Button o = (Button) findViewById(R.id.o);
        o.startAnimation(click);
        o.startAnimation(release);        
       // ��ȡӦ�ó�����������ť
        final Button piano = (Button) findViewById(R.id.piano);
        final Button drum = (Button) findViewById(R.id.drum);
        final Button wood = (Button) findViewById(R.id.wood);
        final Button keyboard = (Button) findViewById(R.id.keyboard);
        final Button metro = (Button) findViewById(R.id.metro);
        final Button rtn = (Button) findViewById(R.id.rtn);
        // Ϊ'����'��ť���¼�������
        piano.setOnTouchListener(new View.OnTouchListener() {
        	public boolean onTouch(View view, MotionEvent motionEvent) {
        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        			piano.startAnimation(click);
        		}
        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {   
        			piano.startAnimation(release);        	
        			BackMusic.pauseBackMusic();
    				// ����intent��Ӧ��Activity
        			intent = new Intent(InsChosen.this,Piano.class);
    				startActivity(intent);    				
                }
        		return false;
        	}
        }); 
        // Ϊ'���ӹ�'��ť���¼�������
        drum.setOnTouchListener(new View.OnTouchListener() {
        	public boolean onTouch(View view, MotionEvent motionEvent) {
        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        			drum.startAnimation(click);
        		}
        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {  
        			drum.startAnimation(release);
        			BackMusic.pauseBackMusic();
    				// ����intent��Ӧ��Activity
        			intent = new Intent(InsChosen.this,Drum.class);
    				startActivity(intent);
                }
        		return false;
        	}
        }); 
        // Ϊ'ľ��'��ť���¼�������
        wood.setOnTouchListener(new View.OnTouchListener() {
        	public boolean onTouch(View view, MotionEvent motionEvent) {
        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        			wood.startAnimation(click);
        		}
        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {   
        			wood.startAnimation(release);
        			BackMusic.pauseBackMusic();
    				// ����intent��Ӧ��Activity

        			intent = new Intent(InsChosen.this,Muqin.class);
    				startActivity(intent);
                }
        		return false;
        	}
        }); 
        // Ϊ'������'��ť���¼�������
        keyboard.setOnTouchListener(new View.OnTouchListener() {
        	public boolean onTouch(View view, MotionEvent motionEvent) {
        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        			keyboard.startAnimation(click);
        		}
        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {   
        			keyboard.startAnimation(release);
        			BackMusic.pauseBackMusic();
    				// ����intent��Ӧ��Activity
        			intent = new Intent(InsChosen.this,KeyBoard.class);
    				startActivity(intent);
                }
        		return false;
        	}
        }); 
        // Ϊ'����'��ť���¼�������
        metro.setOnTouchListener(new View.OnTouchListener() {
        	public boolean onTouch(View view, MotionEvent motionEvent) {
        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        			metro.startAnimation(click);
        		}
        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {    
        			metro.startAnimation(release);
        			BackMusic.pauseBackMusic();
    				// ����intent��Ӧ��Activity
        			intent = new Intent(InsChosen.this,Metronome.class);
    				startActivity(intent);
                }
        		return false;
        	}
        }); 
        // Ϊ'����'��ť���¼�������
        rtn.setOnTouchListener(new View.OnTouchListener() {
        	public boolean onTouch(View view, MotionEvent motionEvent) {
        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        			rtn.startAnimation(click);
        		}
        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {   
        			rtn.startAnimation(release);
    				// ����intent��Ӧ��Activity
        			finish();
                }
        		return false;
        	}
        }); 
	}
}
