package com.example.music;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;

public class Muqin extends Activity implements OnTouchListener{
	 //状态变量
	private boolean play = false;
	private boolean record = false;
	//按键声音库
	private SoundPool soundPool;
	private HashMap<Integer, Integer> soundMap =new HashMap<Integer, Integer>();
	//录音器
	private MediaRecorder recorder = null;
	//播放器
	private MediaPlayer player = null; 
	//文件存储地址
	private String FileName = null;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //设置窗口无标题和全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //显示钢琴界面
        setContentView(R.layout.muqin);   
        //按钮动画
        final ScaleAnimation click=new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        click.setFillAfter(true);
        final ScaleAnimation release=new ScaleAnimation(1.1f, 1.0f, 1.1f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        release.setFillAfter(true);
        final Button o = (Button) findViewById(R.id.o);
        o.startAnimation(click);
        o.startAnimation(release);
        //目录地址初始化
        FileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        //文件初始化
        File destdir= new File(FileName + "/MusicManager/recorder/temp");        
        if(!destdir.exists()){
        	destdir.mkdirs();
        }
        //文件地址初始化
        FileName += "/MusicManager/recorder/audiorecordtest.3gp";  
        //创建声音池
        soundPool = new SoundPool(25, AudioManager.STREAM_SYSTEM, 5);
        //1-14号声音为白色琴键的声音
        soundMap.put(1, soundPool.load(this, R.raw.m1, 1)); 
        soundMap.put(2, soundPool.load(this, R.raw.m2, 1)); 
        soundMap.put(3, soundPool.load(this, R.raw.m3, 1)); 
        soundMap.put(4, soundPool.load(this, R.raw.m4, 1)); 
        soundMap.put(5, soundPool.load(this, R.raw.m5, 1)); 
        soundMap.put(6, soundPool.load(this, R.raw.m6, 1)); 
        soundMap.put(7, soundPool.load(this, R.raw.m7, 1)); 
        soundMap.put(8, soundPool.load(this, R.raw.m8, 1)); 
        soundMap.put(9, soundPool.load(this, R.raw.m9, 1)); 
        soundMap.put(10, soundPool.load(this, R.raw.m10, 1)); 
        soundMap.put(11, soundPool.load(this, R.raw.m11, 1)); 
        soundMap.put(12, soundPool.load(this, R.raw.m12, 1)); 
        soundMap.put(13, soundPool.load(this, R.raw.m13, 1)); 
        soundMap.put(14, soundPool.load(this, R.raw.m14, 1));
        soundMap.put(15, soundPool.load(this, R.raw.m15, 1)); 
        //为变量绑定实体按钮
        final Button rtnm = (Button) findViewById(R.id.rtnm);
        final Button stopbtn = (Button) findViewById(R.id.stop);
        final Button rcdbtn = (Button) findViewById(R.id.record);
        final Button playbtn = (Button) findViewById(R.id.play);
        //木琴
        Button Button_mu1 = (Button) findViewById(R.id.mu1);
        Button Button_mu2 = (Button) findViewById(R.id.mu2);
        Button Button_mu3 = (Button) findViewById(R.id.mu3);
        Button Button_mu4 = (Button) findViewById(R.id.mu4);
        Button Button_mu5 = (Button) findViewById(R.id.mu5);
        Button Button_mu6 = (Button) findViewById(R.id.mu6);
        Button Button_mu7 = (Button) findViewById(R.id.mu7);
        Button Button_mu8 = (Button) findViewById(R.id.mu8);
        Button Button_mu9 = (Button) findViewById(R.id.mu9);
        Button Button_mu10 = (Button) findViewById(R.id.mu10);
        Button Button_mu11 = (Button) findViewById(R.id.mu11);
        Button Button_mu12 = (Button) findViewById(R.id.mu12);
        Button Button_mu13 = (Button) findViewById(R.id.mu13);
        Button Button_mu14 = (Button) findViewById(R.id.mu14);  
        Button Button_mu15 = (Button) findViewById(R.id.mu15); 
        //为琴键绑定声音
        Button_mu1.setOnTouchListener(this);
        Button_mu2.setOnTouchListener(this);
        Button_mu3.setOnTouchListener(this);
        Button_mu4.setOnTouchListener(this);
        Button_mu5.setOnTouchListener(this);
        Button_mu6.setOnTouchListener(this);
        Button_mu7.setOnTouchListener(this);
        Button_mu8.setOnTouchListener(this);
        Button_mu9.setOnTouchListener(this);
        Button_mu10.setOnTouchListener(this);
        Button_mu11.setOnTouchListener(this);
        Button_mu12.setOnTouchListener(this);
        Button_mu13.setOnTouchListener(this);
        Button_mu14.setOnTouchListener(this);
        Button_mu15.setOnTouchListener(this);
        // 为'返回'按钮绑定事件监听器
        rtnm.setOnTouchListener(new View.OnTouchListener() {
        	public boolean onTouch(View view, MotionEvent motionEvent) {
        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        			rtnm.startAnimation(click);
        		}
        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {   
        			rtnm.startAnimation(release);
        			BackMusic.resumeBackMusic();
        			finish();
                }
        		return false;
        	}
        }); 
		// 为'play'按钮绑定事件监听器
        playbtn.setOnTouchListener(new View.OnTouchListener() {
        	public boolean onTouch(View view, MotionEvent motionEvent) {
        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        			playbtn.startAnimation(click);
        		}
        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {   
        			playbtn.startAnimation(release);
        			//如果当前没有播放，则开始播放
    	            if(play==false){	
    					player = new MediaPlayer();  
    		            try{  
    		                player.setDataSource(FileName);  
    		                player.prepare();  
    		                player.start();  
    		                play = true;
    		            }catch(IOException e){  
    		            }  
    		            //监听音乐播放的状态，若播放结束，释放资源的同时将按钮置为开始
    		    		player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
    		                @Override
    		                public void onCompletion(MediaPlayer mediaPlayer) {
    							player.release();
    							player = null;
    							play = false;
    						}
    		            });
    				}
                }
        		return false;
        	}
        });
        // 为'record'按钮绑定事件监听器
        rcdbtn.setOnTouchListener(new View.OnTouchListener() {
        	public boolean onTouch(View view, MotionEvent motionEvent) {
        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        			rcdbtn.startAnimation(click);
        		}
        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {   
        			rcdbtn.startAnimation(release);
        			//如果当前没有录制，则开始录制
    				if(record==false){
    					recorder= new MediaRecorder(); 
    					recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
    					recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
    					recorder.setOutputFile(FileName);
    					recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    					try {  
    						recorder.prepare();  
    				    } catch (IOException e) {  
    				    }  
    				    recorder.start();							
    					record=true;
    				}
    				//如果正在录制，则停止录制，释放文件
    				else{
    					recorder.stop();  
    				    recorder.release();  
    				    recorder = null;  
    					record=false;
    				}
                }
        		return false;
        	}
        }); 
		// 为'stop'按钮绑定事件监听器
        stopbtn.setOnTouchListener(new View.OnTouchListener() {
        	public boolean onTouch(View view, MotionEvent motionEvent) {
        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        			stopbtn.startAnimation(click);
        		}
        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {   
        			stopbtn.startAnimation(release);
        			// 根据当前状态进行处理		
    				//如果正在录制且正在播放，则停止录制，释放文件，并停止播放
    				if(record==true&&play==true){
    					recorder.stop();  
    				    recorder.release();  
    				    recorder = null;  
    					record=false;
    					player.release();
    					player = null;
    					play = false;
    				}
    				//如果正在播放，则停止播放，释放文件
    				else if(play==true){
    					player.release();
    					player = null;
    					play = false;
    				}
    				//如果正在录制，则停止录制，释放文件
    				else if(record==true){
    					recorder.stop();  
    				    recorder.release();  
    				    recorder = null;  
    					record=false;
    				}
                }
        		return false;
        	}
        }); 
	}
    // 钢琴键事件监听器
	@Override
	public boolean onTouch(View v, MotionEvent motionEvent) {
		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
			switch(v.getId()){
			case R.id.mu1:
				soundPool.play(soundMap.get(1),1,1,0,0,1);break;
			case R.id.mu2:
				soundPool.play(soundMap.get(2),1,1,0,0,1);break;
			case R.id.mu3:
				soundPool.play(soundMap.get(3),1,1,0,0,1);break;
			case R.id.mu4:
				soundPool.play(soundMap.get(4),1,1,0,0,1);break;
			case R.id.mu5:
				soundPool.play(soundMap.get(5),1,1,0,0,1);break;
			case R.id.mu6:
				soundPool.play(soundMap.get(6),1,1,0,0,1);break;
			case R.id.mu7:
				soundPool.play(soundMap.get(7),1,1,0,0,1);break;
			case R.id.mu8:
				soundPool.play(soundMap.get(8),1,1,0,0,1);break;
			case R.id.mu9:
				soundPool.play(soundMap.get(9),1,1,0,0,1);break;
			case R.id.mu10:
				soundPool.play(soundMap.get(10),1,1,0,0,1);break;
			case R.id.mu11:
				soundPool.play(soundMap.get(11),1,1,0,0,1);break;
			case R.id.mu12:
				soundPool.play(soundMap.get(12),1,1,0,0,1);break;
			case R.id.mu13:
				soundPool.play(soundMap.get(13),1,1,0,0,1);break;
			case R.id.mu14:
				soundPool.play(soundMap.get(14),1,1,0,0,1);break;
			case R.id.mu15:
				soundPool.play(soundMap.get(15),1,1,0,0,1);break;
			}
		}
		return false;
	}
}
