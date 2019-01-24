package com.example.music;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.util.HashMap;
import org.apache.http.util.EncodingUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Exercise extends Activity implements OnTouchListener {
	//分数
	public static int point=0;
	//按键声音库
	private SoundPool soundPool;
	private HashMap<Integer, Integer> soundMap =new HashMap<Integer, Integer>();
	//乐谱文件地址
	private String TuneFile = null;
	TextView Pcnt;
	ImageView good;
	ExerPicture draw;
	ScaleAnimation scale;
	int temp;
		
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //设置窗口无标题和全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //显示练习界面
        setContentView(R.layout.exercise); 
        point=0;
        //"good"图片初始化
        good= (ImageView) findViewById(R.id.good);
        good.setVisibility(View.INVISIBLE);
        scale=new ScaleAnimation(0.0f, 1.5f, 0.0f, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(300);
        //接收乐谱文件地址
        Bundle bundle = this.getIntent().getExtras();
        TuneFile = bundle.getString("path");
        String lyric="";
        //读取乐谱
        try {
			lyric=readFile(TuneFile);
		} 
        catch (IOException e) {
			new AlertDialog.Builder(Exercise.this)  
            .setTitle("Error")
            .setMessage("Can not read the tune file!")
            .setNegativeButton("确定", new DialogInterface.OnClickListener() {  
                @Override
                public void onClick(DialogInterface dialog, int which) {
                	finish();
                }  
            }).show();
		}
        //显示下落方块
        LinearLayout root = (LinearLayout) findViewById(R.id.drawer);
        draw = new ExerPicture(this,lyric);
        root.addView(draw);
        //按钮动画
        final ScaleAnimation click=new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        click.setFillAfter(true);
        final ScaleAnimation release=new ScaleAnimation(1.1f, 1.0f, 1.1f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        release.setFillAfter(true);
        final Button o = (Button) findViewById(R.id.o);
        o.startAnimation(click);
        o.startAnimation(release);
        //创建声音池
        soundPool = new SoundPool(25, AudioManager.STREAM_SYSTEM, 5);
        //1-14号声音为白色琴键的声音
        soundMap.put(1, soundPool.load(this, R.raw.white1, 1)); 
        soundMap.put(2, soundPool.load(this, R.raw.white2, 1)); 
        soundMap.put(3, soundPool.load(this, R.raw.white3, 1)); 
        soundMap.put(4, soundPool.load(this, R.raw.white4, 1)); 
        soundMap.put(5, soundPool.load(this, R.raw.white5, 1)); 
        soundMap.put(6, soundPool.load(this, R.raw.white6, 1)); 
        soundMap.put(7, soundPool.load(this, R.raw.white7, 1)); 
        soundMap.put(8, soundPool.load(this, R.raw.white8, 1)); 
        soundMap.put(9, soundPool.load(this, R.raw.white9, 1)); 
        soundMap.put(10, soundPool.load(this, R.raw.white10, 1)); 
        soundMap.put(11, soundPool.load(this, R.raw.white11, 1)); 
        soundMap.put(12, soundPool.load(this, R.raw.white12, 1)); 
        soundMap.put(13, soundPool.load(this, R.raw.white13, 1)); 
        soundMap.put(14, soundPool.load(this, R.raw.white14, 1));         
        //15-24号声音为黑色琴键的声音
        soundMap.put(15, soundPool.load(this, R.raw.black1, 1)); 
        soundMap.put(16, soundPool.load(this, R.raw.black2, 1)); 
        soundMap.put(17, soundPool.load(this, R.raw.black3, 1)); 
        soundMap.put(18, soundPool.load(this, R.raw.black4, 1)); 
        soundMap.put(19, soundPool.load(this, R.raw.black5, 1)); 
        soundMap.put(20, soundPool.load(this, R.raw.black6, 1)); 
        soundMap.put(21, soundPool.load(this, R.raw.black7, 1));
        soundMap.put(22, soundPool.load(this, R.raw.black8, 1));
        soundMap.put(23, soundPool.load(this, R.raw.black9, 1)); 
        soundMap.put(24, soundPool.load(this, R.raw.black10, 1));
        //为变量绑定实体按钮
        final Button rtne = (Button) findViewById(R.id.rtne);
        Pcnt = (TextView) findViewById(R.id.point);
        //白键
        Button Button_white1 = (Button) findViewById(R.id.white1);
        Button Button_white2 = (Button) findViewById(R.id.white2);
        Button Button_white3 = (Button) findViewById(R.id.white3);
        Button Button_white4 = (Button) findViewById(R.id.white4);
        Button Button_white5 = (Button) findViewById(R.id.white5);
        Button Button_white6 = (Button) findViewById(R.id.white6);
        Button Button_white7 = (Button) findViewById(R.id.white7);
        Button Button_white8 = (Button) findViewById(R.id.white8);
        Button Button_white9 = (Button) findViewById(R.id.white9);
        Button Button_white10 = (Button) findViewById(R.id.white10);
        Button Button_white11 = (Button) findViewById(R.id.white11);
        Button Button_white12 = (Button) findViewById(R.id.white12);
        Button Button_white13 = (Button) findViewById(R.id.white13);
        Button Button_white14 = (Button) findViewById(R.id.white14);        
        //黑键
        Button Button_black1 = (Button) findViewById(R.id.black1);
        Button Button_black2 = (Button) findViewById(R.id.black2);
        Button Button_black3 = (Button) findViewById(R.id.black3);
        Button Button_black4 = (Button) findViewById(R.id.black4);
        Button Button_black5 = (Button) findViewById(R.id.black5);
        Button Button_black6 = (Button) findViewById(R.id.black6);
        Button Button_black7 = (Button) findViewById(R.id.black7);
        Button Button_black8 = (Button) findViewById(R.id.black8);
        Button Button_black9 = (Button) findViewById(R.id.black9);
        Button Button_black10 = (Button) findViewById(R.id.black10);
        //为白色琴键绑定声音
        Button_white1.setOnTouchListener(this);
        Button_white2.setOnTouchListener(this);
        Button_white3.setOnTouchListener(this);
        Button_white4.setOnTouchListener(this);
        Button_white5.setOnTouchListener(this);
        Button_white6.setOnTouchListener(this);
        Button_white7.setOnTouchListener(this);
        Button_white8.setOnTouchListener(this);
        Button_white9.setOnTouchListener(this);
        Button_white10.setOnTouchListener(this);
        Button_white11.setOnTouchListener(this);
        Button_white12.setOnTouchListener(this);
        Button_white13.setOnTouchListener(this);
        Button_white14.setOnTouchListener(this);
        //为黑色琴键绑定声音
        Button_black1.setOnTouchListener(this);
        Button_black2.setOnTouchListener(this);      
        Button_black3.setOnTouchListener(this);     
        Button_black4.setOnTouchListener(this);
        Button_black5.setOnTouchListener(this);     
        Button_black6.setOnTouchListener(this);     
        Button_black7.setOnTouchListener(this);      
        Button_black8.setOnTouchListener(this);      
        Button_black9.setOnTouchListener(this);
        Button_black10.setOnTouchListener(this);     
        // 为'返回'按钮绑定事件监听器
        rtne.setOnTouchListener(new View.OnTouchListener() {
        	public boolean onTouch(View view, MotionEvent motionEvent) {
        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        			rtne.startAnimation(click);
        		}
        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {   
        			rtne.startAnimation(release);
        			BackMusic.resumeBackMusic();
        			finish();
                }
        		return false;
        	}
        }); 
	}

    //读取文件内容
    public String readFile(String fileName) throws IOException {    
    	String res = "";      	
        File file = new File(fileName);      
        FileInputStream fis = new FileInputStream(file);      
        int length = fis.available();     
         byte [] buffer = new byte[length];   
         fis.read(buffer);         
         res = EncodingUtils.getString(buffer, "UTF-8");    
         fis.close();       
         return res;    
    }
    
    //分数记录
    public String PointCount(int t){
		if(t>=130&&t<=140){
			point+=3;
			good.setImageResource(R.drawable.excelent);
			good.setVisibility(View.VISIBLE);
			good.startAnimation(scale);
			good.setVisibility(View.INVISIBLE);
		}
		else if(t>=150&&t<=160){
			point+=2;
			good.setImageResource(R.drawable.good);
			good.setVisibility(View.VISIBLE);
			good.startAnimation(scale);
			good.setVisibility(View.INVISIBLE);
		}
		else if(t>=170&&t<=180){
			point+=1;
			good.setImageResource(R.drawable.okey);
			good.setVisibility(View.VISIBLE);
			good.startAnimation(scale);
			good.setVisibility(View.INVISIBLE);
		}
		return Integer.toString(point);
    }
    
    	// 钢琴键事件监听器
 		@Override
 		public boolean onTouch(View v, MotionEvent motionEvent) {
 			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
 				switch(v.getId()){
 				case R.id.white1:
 					soundPool.play(soundMap.get(1),1,1,0,0,1);
 					temp=draw.getY('A');
        			Pcnt.setText(PointCount(temp));
        			break;
 				case R.id.white2:
 					soundPool.play(soundMap.get(2),1,1,0,0,1);
 					temp=draw.getY('B');
        			Pcnt.setText(PointCount(temp));
        			break;
 				case R.id.white3:
 					soundPool.play(soundMap.get(3),1,1,0,0,1);
 					temp=draw.getY('C');
        			Pcnt.setText(PointCount(temp));
        			break;
 				case R.id.white4:
 					soundPool.play(soundMap.get(4),1,1,0,0,1);
 					temp=draw.getY('D');
        			Pcnt.setText(PointCount(temp));
        			break;
 				case R.id.white5:
 					soundPool.play(soundMap.get(5),1,1,0,0,1);
 					temp=draw.getY('E');
        			Pcnt.setText(PointCount(temp));
        			break;
 				case R.id.white6:
 					soundPool.play(soundMap.get(6),1,1,0,0,1);
 					temp=draw.getY('F');
        			Pcnt.setText(PointCount(temp));
        			break;
 				case R.id.white7:
 					soundPool.play(soundMap.get(7),1,1,0,0,1);
 					temp=draw.getY('G');
        			Pcnt.setText(PointCount(temp));
        			break;
 				case R.id.white8:
 					soundPool.play(soundMap.get(8),1,1,0,0,1);
 					temp=draw.getY('H');
        			Pcnt.setText(PointCount(temp));
        			break;
 				case R.id.white9:
 					soundPool.play(soundMap.get(9),1,1,0,0,1);
 					temp=draw.getY('I');
        			Pcnt.setText(PointCount(temp));
        			break;
 				case R.id.white10:
 					soundPool.play(soundMap.get(10),1,1,0,0,1);
 					temp=draw.getY('J');
        			Pcnt.setText(PointCount(temp));
        			break;
 				case R.id.white11:
 					soundPool.play(soundMap.get(11),1,1,0,0,1);
 					temp=draw.getY('K');
        			Pcnt.setText(PointCount(temp));
        			break;
 				case R.id.white12:
 					soundPool.play(soundMap.get(12),1,1,0,0,1);
 					temp=draw.getY('L');
        			Pcnt.setText(PointCount(temp));
        			break;
 				case R.id.white13:
 					soundPool.play(soundMap.get(13),1,1,0,0,1);
 					temp=draw.getY('M');
        			Pcnt.setText(PointCount(temp));
        			break;
 				case R.id.white14:
 					soundPool.play(soundMap.get(14),1,1,0,0,1);
 					temp=draw.getY('N');
        			Pcnt.setText(PointCount(temp));
        			break;
 				case R.id.black1:
 					soundPool.play(soundMap.get(15),1,1,0,0,1);
 					temp=draw.getY('0');
        			Pcnt.setText(PointCount(temp));
        			break;
 				case R.id.black2:
 					soundPool.play(soundMap.get(16),1,1,0,0,1);
 					temp=draw.getY('1');
        			Pcnt.setText(PointCount(temp));
        			break;
 				case R.id.black3:
 					soundPool.play(soundMap.get(17),1,1,0,0,1);
 					temp=draw.getY('2');
        			Pcnt.setText(PointCount(temp));
        			break;
 				case R.id.black4:
 					soundPool.play(soundMap.get(18),1,1,0,0,1);
 					temp=draw.getY('3');
        			Pcnt.setText(PointCount(temp));
        			break;
 				case R.id.black5:
 					soundPool.play(soundMap.get(19),1,1,0,0,1);
 					temp=draw.getY('4');
        			Pcnt.setText(PointCount(temp));
        			break;
 				case R.id.black6:
 					soundPool.play(soundMap.get(20),1,1,0,0,1);
 					temp=draw.getY('5');
        			Pcnt.setText(PointCount(temp));
        			break;
 				case R.id.black7:
 					soundPool.play(soundMap.get(21),1,1,0,0,1);
 					temp=draw.getY('6');
        			Pcnt.setText(PointCount(temp));
        			break;
 				case R.id.black8:
 					soundPool.play(soundMap.get(22),1,1,0,0,1);
 					temp=draw.getY('7');
        			Pcnt.setText(PointCount(temp));
        			break;
 				case R.id.black9:
 					soundPool.play(soundMap.get(23),1,1,0,0,1);
 					temp=draw.getY('8');
        			Pcnt.setText(PointCount(temp));
        			break;
 				case R.id.black10:
 					soundPool.play(soundMap.get(24),1,1,0,0,1);
 					temp=draw.getY('9');
        			Pcnt.setText(PointCount(temp));
        			break;
        		default: break;
 				}
 			}
 			return false;
 			
 		}
    
}


 

