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
	 //״̬����
	private boolean play = false;
	private boolean record = false;
	//����������
	private SoundPool soundPool;
	private HashMap<Integer, Integer> soundMap =new HashMap<Integer, Integer>();
	//¼����
	private MediaRecorder recorder = null;
	//������
	private MediaPlayer player = null; 
	//�ļ��洢��ַ
	private String FileName = null;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //���ô����ޱ����ȫ��
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //��ʾ���ٽ���
        setContentView(R.layout.muqin);   
        //��ť����
        final ScaleAnimation click=new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        click.setFillAfter(true);
        final ScaleAnimation release=new ScaleAnimation(1.1f, 1.0f, 1.1f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        release.setFillAfter(true);
        final Button o = (Button) findViewById(R.id.o);
        o.startAnimation(click);
        o.startAnimation(release);
        //Ŀ¼��ַ��ʼ��
        FileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        //�ļ���ʼ��
        File destdir= new File(FileName + "/MusicManager/recorder/temp");        
        if(!destdir.exists()){
        	destdir.mkdirs();
        }
        //�ļ���ַ��ʼ��
        FileName += "/MusicManager/recorder/audiorecordtest.3gp";  
        //����������
        soundPool = new SoundPool(25, AudioManager.STREAM_SYSTEM, 5);
        //1-14������Ϊ��ɫ�ټ�������
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
        //Ϊ������ʵ�尴ť
        final Button rtnm = (Button) findViewById(R.id.rtnm);
        final Button stopbtn = (Button) findViewById(R.id.stop);
        final Button rcdbtn = (Button) findViewById(R.id.record);
        final Button playbtn = (Button) findViewById(R.id.play);
        //ľ��
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
        //Ϊ�ټ�������
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
        // Ϊ'����'��ť���¼�������
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
		// Ϊ'play'��ť���¼�������
        playbtn.setOnTouchListener(new View.OnTouchListener() {
        	public boolean onTouch(View view, MotionEvent motionEvent) {
        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        			playbtn.startAnimation(click);
        		}
        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {   
        			playbtn.startAnimation(release);
        			//�����ǰû�в��ţ���ʼ����
    	            if(play==false){	
    					player = new MediaPlayer();  
    		            try{  
    		                player.setDataSource(FileName);  
    		                player.prepare();  
    		                player.start();  
    		                play = true;
    		            }catch(IOException e){  
    		            }  
    		            //�������ֲ��ŵ�״̬�������Ž������ͷ���Դ��ͬʱ����ť��Ϊ��ʼ
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
        // Ϊ'record'��ť���¼�������
        rcdbtn.setOnTouchListener(new View.OnTouchListener() {
        	public boolean onTouch(View view, MotionEvent motionEvent) {
        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        			rcdbtn.startAnimation(click);
        		}
        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {   
        			rcdbtn.startAnimation(release);
        			//�����ǰû��¼�ƣ���ʼ¼��
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
    				//�������¼�ƣ���ֹͣ¼�ƣ��ͷ��ļ�
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
		// Ϊ'stop'��ť���¼�������
        stopbtn.setOnTouchListener(new View.OnTouchListener() {
        	public boolean onTouch(View view, MotionEvent motionEvent) {
        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        			stopbtn.startAnimation(click);
        		}
        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {   
        			stopbtn.startAnimation(release);
        			// ���ݵ�ǰ״̬���д���		
    				//�������¼�������ڲ��ţ���ֹͣ¼�ƣ��ͷ��ļ�����ֹͣ����
    				if(record==true&&play==true){
    					recorder.stop();  
    				    recorder.release();  
    				    recorder = null;  
    					record=false;
    					player.release();
    					player = null;
    					play = false;
    				}
    				//������ڲ��ţ���ֹͣ���ţ��ͷ��ļ�
    				else if(play==true){
    					player.release();
    					player = null;
    					play = false;
    				}
    				//�������¼�ƣ���ֹͣ¼�ƣ��ͷ��ļ�
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
    // ���ټ��¼�������
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
