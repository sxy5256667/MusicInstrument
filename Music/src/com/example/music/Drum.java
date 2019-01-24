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
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;

public class Drum extends Activity{
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
	        //显示架子鼓界面
	        setContentView(R.layout.drum);
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
	        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
	        //1-10号声音为白色琴键的声音
	        soundMap.put(1, soundPool.load(this, R.raw.d1, 1)); 
	        soundMap.put(2, soundPool.load(this, R.raw.d2, 1)); 
	        soundMap.put(3, soundPool.load(this, R.raw.d3, 1)); 
	        soundMap.put(4, soundPool.load(this, R.raw.d4, 1)); 
	        soundMap.put(5, soundPool.load(this, R.raw.d5, 1)); 
	        soundMap.put(6, soundPool.load(this, R.raw.d6, 1)); 
	        soundMap.put(7, soundPool.load(this, R.raw.l1, 1)); 
	        soundMap.put(8, soundPool.load(this, R.raw.l2, 1)); 
	        soundMap.put(9, soundPool.load(this, R.raw.l3, 1)); 
	        soundMap.put(10, soundPool.load(this, R.raw.l4, 1)); 	        
	        //为变量绑定实体按钮
	        final Button rtnd = (Button) findViewById(R.id.rtnd);
	        final Button stopbtn = (Button) findViewById(R.id.stop);
	        final Button rcdbtn = (Button) findViewById(R.id.record);
	        final Button playbtn = (Button) findViewById(R.id.play);
	        //鼓
	        final Button Button_gu1 = (Button) findViewById(R.id.gu1);
	        final Button Button_gu2 = (Button) findViewById(R.id.gu2);
	        final Button Button_gu3 = (Button) findViewById(R.id.gu3);
	        final Button Button_gu4 = (Button) findViewById(R.id.gu4);
	        final Button Button_gu5 = (Button) findViewById(R.id.gu5);
	        final Button Button_gu6 = (Button) findViewById(R.id.gu6);
	        //檫
	        final Button Button_cha1 = (Button) findViewById(R.id.cha1);
	        final Button Button_cha2 = (Button) findViewById(R.id.cha2);
	        final Button Button_cha3 = (Button) findViewById(R.id.cha3);
	        final Button Button_cha4 = (Button) findViewById(R.id.cha4);
	        //为鼓绑定声音
	        Button_gu1.setOnTouchListener(new View.OnTouchListener() {
	        	public boolean onTouch(View view, MotionEvent motionEvent) {
	        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
	        			soundPool.play(soundMap.get(5),1,1,0,0,1);
	        			Button_gu1.setBackgroundResource(R.drawable.d1_back);
	        		}
	        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
	        			Button_gu1.setBackgroundResource(R.drawable.d1);
	                }
	        		return false;
	        	}
	        });
	        Button_gu2.setOnTouchListener(new View.OnTouchListener() {
	        	public boolean onTouch(View view, MotionEvent motionEvent) {
	        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
	        			soundPool.play(soundMap.get(4),1,1,0,0,1);
	        			Button_gu2.setBackgroundResource(R.drawable.d2_back);
	        		}
	        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
	        			Button_gu2.setBackgroundResource(R.drawable.d2);
	                }
	        		return false;
	        	}
	        });
	        Button_gu3.setOnTouchListener(new View.OnTouchListener() {
	        	public boolean onTouch(View view, MotionEvent motionEvent) {
	        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
	        			soundPool.play(soundMap.get(3),1,1,0,0,1);
	        			Button_gu3.setBackgroundResource(R.drawable.d3_back);
	        		}
	        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
	        			Button_gu3.setBackgroundResource(R.drawable.d3);
	                }
	        		return false;
	        	}
	        });
	        Button_gu4.setOnTouchListener(new View.OnTouchListener() {
	        	public boolean onTouch(View view, MotionEvent motionEvent) {
	        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
	        			soundPool.play(soundMap.get(2),1,1,0,0,1);
	        			Button_gu4.setBackgroundResource(R.drawable.d4_back);
	        		}
	        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
	        			Button_gu4.setBackgroundResource(R.drawable.d4);
	                }
	        		return false;
	        	}
	        });
	        Button_gu5.setOnTouchListener(new View.OnTouchListener() {
	        	public boolean onTouch(View view, MotionEvent motionEvent) {
	        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
	        			soundPool.play(soundMap.get(6),1,1,0,0,1);
	        			Button_gu5.setBackgroundResource(R.drawable.d5_back);
	        		}
	        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
	        			Button_gu5.setBackgroundResource(R.drawable.d5);
	                }
	        		return false;
	        	}
	        });
	        Button_gu6.setOnTouchListener(new View.OnTouchListener() {
	        	public boolean onTouch(View view, MotionEvent motionEvent) {
	        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
	        			soundPool.play(soundMap.get(1),1,1,0,0,1);
	        			Button_gu6.setBackgroundResource(R.drawable.d6_back);
	        		}
	        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
	        			Button_gu6.setBackgroundResource(R.drawable.d6);
	                }
	        		return false;
	        	}
	        });
	        //为檫绑定声音
	        Button_cha1.setOnTouchListener(new View.OnTouchListener() {
	        	public boolean onTouch(View view, MotionEvent motionEvent) {
	        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
	        			soundPool.play(soundMap.get(10),1,1,0,0,1);
	        			Button_cha1.setBackgroundResource(R.drawable.l1_back);
	        		}
	        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
	        			Button_cha1.setBackgroundResource(R.drawable.l1);
	                }
	        		return false;
	        	}
	        });
	        Button_cha2.setOnTouchListener(new View.OnTouchListener() {
	        	public boolean onTouch(View view, MotionEvent motionEvent) {
	        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
	        			soundPool.play(soundMap.get(9),1,1,0,0,1);
	        			Button_cha2.setBackgroundResource(R.drawable.l2_back);
	        		}
	        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
	        			Button_cha2.setBackgroundResource(R.drawable.l2);
	                }
	        		return false;
	        	}
	        });
	        Button_cha3.setOnTouchListener(new View.OnTouchListener() {
	        	public boolean onTouch(View view, MotionEvent motionEvent) {
	        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
	        			soundPool.play(soundMap.get(8),1,1,0,0,1);
	        			Button_cha3.setBackgroundResource(R.drawable.l3_back);
	        		}
	        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
	        			Button_cha3.setBackgroundResource(R.drawable.l3);
	                }
	        		return false;
	        	}
	        });
	        Button_cha4.setOnTouchListener(new View.OnTouchListener() {
	        	public boolean onTouch(View view, MotionEvent motionEvent) {
	        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
	        			soundPool.play(soundMap.get(7),1,1,0,0,1);
	        			Button_cha4.setBackgroundResource(R.drawable.l4_back);
	        		}
	        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
	        			Button_cha4.setBackgroundResource(R.drawable.l4);
	                }
	        		return false;
	        	}
	        });
	        // 为'返回'按钮绑定事件监听器
	        rtnd.setOnTouchListener(new View.OnTouchListener() {
	        	public boolean onTouch(View view, MotionEvent motionEvent) {
	        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
	        			rtnd.startAnimation(click);
	        		}
	        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {   
	        			rtnd.startAnimation(release);
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
