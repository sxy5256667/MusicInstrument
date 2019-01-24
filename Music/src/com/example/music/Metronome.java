package com.example.music;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class Metronome extends Activity{
	//状态变量
	private boolean play = false;
	private boolean record = false;
	//按键声音库
	private SoundPool soundPool;
	private HashMap<Integer, Integer> soundMap =new HashMap<Integer, Integer>();
	//下拉菜单列表
	private List<String> list = new ArrayList<String>();  
    private Spinner mySpinner;    
    private ArrayAdapter<String> adapter;
	//录音器
	private MediaRecorder recorder = null;
	//播放器
	private MediaPlayer player = null; 
	//文件存储地址
	private String FileName = null;
	private int beats=60;
	private int beat=4;
	private int beatcount=beat;
	//start
	private boolean isStart=false;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);  //设置窗口无标题和全屏
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        //显示钢琴界面
	        setContentView(R.layout.metro);   
	        
	        final ImageView tick=(ImageView) findViewById(R.id.tick);
	        //按钮动画
	        final ScaleAnimation click=new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
	        click.setFillAfter(true);
	        final ScaleAnimation release=new ScaleAnimation(1.1f, 1.0f, 1.1f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
	        release.setFillAfter(true);
	        final Button o = (Button) findViewById(R.id.o);
	        o.startAnimation(click);
	        o.startAnimation(release);
	        //指针动画
	        final Animation tickAnim=AnimationUtils.loadAnimation(this, R.anim.shake);	        	        
	        //目录地址初始化
	        FileName = Environment.getExternalStorageDirectory().getAbsolutePath();
	        //文件初始化
	        File destdir= new File(FileName + "/MusicManager/recorder/temp");        
	        if(!destdir.exists()){
	        	destdir.mkdirs();
	        }
	        //文件地址初始化
	        FileName += "/MusicManager/recorder/audiorecordtest.3gp";  	        
	        //下拉列表初始化	        
	        list.add("2");
	        list.add("3");
	        list.add("4");
	        list.add("8");
	        list.add("16");  
	        mySpinner = (Spinner)findViewById(R.id.beat);
	        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
	        mySpinner.setAdapter(adapter);
	        //创建声音池
	        soundPool = new SoundPool(4, AudioManager.STREAM_SYSTEM, 5);

	        soundMap.put(1, soundPool.load(this, R.raw.j1, 1)); 
	        soundMap.put(2, soundPool.load(this, R.raw.j2, 1));
	        soundMap.put(3, soundPool.load(this, R.raw.white3, 1));
	        //指针音效
	        tickAnim.setAnimationListener(new Animation.AnimationListener() {
	            @Override
	            public void onAnimationStart(Animation animation) {}
	            @Override
	            public void onAnimationEnd(Animation animation) {}
	            @Override
	            public void onAnimationRepeat(Animation animation) {
	            	if(beatcount==beat){
	            		soundPool.play(soundMap.get(1),1,1,0,0,1);
	            		beatcount=1;
	            	}
	            	else{
	            		soundPool.play(soundMap.get(2),1,1,0,0,1);
	            		beatcount++;
	            	}
	            }
	        });	 		
	        mySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){    
	            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {    
	                // TODO Auto-generated method stub    
	                /* 将所选mySpinner 的值带入myTextView 中*/    
	                switch(arg2){
	                case 0:
	                	beat=2;
	                	break;
	                case 1:
	                	beat=3;
	                	break;
	                case 2:
	                	beat=4;
	                	break;
	                case 3:
	                	beat=8;
	                	break;
	                case 4:
	                	beat=16;
	                	break;
	                }
	                /* 将mySpinner 显示*/    
	                arg0.setVisibility(View.VISIBLE);   
	                beatcount=beat;
	            }    
	            public void onNothingSelected(AdapterView<?> arg0) {}    
	        });
	        //为变量绑定实体按钮
	        final Button rtnm = (Button) findViewById(R.id.rtnm);
	        final Button stopbtn = (Button) findViewById(R.id.stop);
	        final Button rcdbtn = (Button) findViewById(R.id.record);
	        final Button playbtn = (Button) findViewById(R.id.play);   
	        final Button beatsbtn = (Button) findViewById(R.id.beats);  
	        final Button add1 = (Button) findViewById(R.id.add1);
	        final Button add5 = (Button) findViewById(R.id.add5);
	        final Button sub1 = (Button) findViewById(R.id.sub1);
	        final Button sub5 = (Button) findViewById(R.id.sub5);
	        final Button start = (Button) findViewById(R.id.start);
	        // 为'+1'按钮绑定事件监听器
	        add1.setOnTouchListener(new View.OnTouchListener() {
	        	public boolean onTouch(View view, MotionEvent motionEvent) {
	        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
	        			beats+=1;
	        			
	        		}
	        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {   
	        			beatsbtn.setText(Integer.toString(beats));
	        			tickAnim.setDuration(60*1000/beats);
	                }
	        		return false;
	        	}
	        }); 
	        // 为'+5'按钮绑定事件监听器
	        add5.setOnTouchListener(new View.OnTouchListener() {
	        	public boolean onTouch(View view, MotionEvent motionEvent) {
	        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
	        			beats+=5;
	        			
	        		}
	        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {   
	        			beatsbtn.setText(Integer.toString(beats));
	        			tickAnim.setDuration(60*1000/beats);
	                }
	        		return false;
	        	}
	        }); 
	        // 为'-1'按钮绑定事件监听器
	        sub1.setOnTouchListener(new View.OnTouchListener() {
	        	public boolean onTouch(View view, MotionEvent motionEvent) {
	        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
	        			beats-=1;
	        			
	        		}
	        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {   
	        			beatsbtn.setText(Integer.toString(beats));
	        			tickAnim.setDuration(60*1000/beats);
	                }
	        		return false;
	        	}
	        }); 
	        // 为'-5'按钮绑定事件监听器
	        sub5.setOnTouchListener(new View.OnTouchListener() {
	        	public boolean onTouch(View view, MotionEvent motionEvent) {
	        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
	        			beats-=5;
	        			
	        		}
	        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {   
	        			beatsbtn.setText(Integer.toString(beats));
	        			tickAnim.setDuration(60*1000/beats);
	                }
	        		return false;
	        	}
	        }); 
	        // 为'start'按钮绑定事件监听器
	        start.setOnTouchListener(new View.OnTouchListener() {
	        	public boolean onTouch(View view, MotionEvent motionEvent) {
	        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
	        			start.startAnimation(click);	        			
	        		}
	        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {    
	        			if(isStart){
	        				tick.clearAnimation();
	        				isStart=false;
	        				start.setText("start");
	        			}
	        			else{
	        				tick.startAnimation(tickAnim);
	        				isStart=true;
	        				start.setText("stop");
	        			}	        			
	        			start.startAnimation(release);
	                }
	        		return false;
	        	}
	        });
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
}
