package com.example.music;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MusicList extends Activity {
	//乐谱目录
	private static final String ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/MusicManager/exerList";
	//目录项存储
	private ArrayList<HashMap<String,String>> names = new ArrayList<HashMap<String,String>>(); 
	//目录项信息存储
	private HashMap<String, String> map;
	//文件名暂存器
	private String filename = new String();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //设置窗口无标题和全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //显示音乐列表界面
        setContentView(R.layout.music_list);
        //按钮动画
        final ScaleAnimation click=new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        click.setFillAfter(true);
        final ScaleAnimation release=new ScaleAnimation(1.1f, 1.0f, 1.1f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        release.setFillAfter(true);
        final Button o = (Button) findViewById(R.id.o);
        o.startAnimation(click);
        o.startAnimation(release);
        //返回按钮初始化
        final Button ListReturn = (Button) findViewById(R.id.rtnl);
        //乐曲目录初始化
        File file = new File(ROOT_PATH);
        //如果无当前目录，则创建目录
        if(!file.exists()){
        	file.mkdirs();
        }
        //列表读取并存储
        final File[] files = file.listFiles();
        //读取"ExrciseList"文件夹中所有文件的文件名
        for (File f : files){  
        	map= new HashMap<String, String>();
        	filename=f.getName().substring(0, f.getName().indexOf("."));
        	map.put("MusicName", filename);
        	names.add(map);  
        }  
        //列表显示初始化
        ListView list = (ListView) findViewById(R.id.musicList);
        //将文件名显示在ListView中
        SimpleAdapter listAdapter =new SimpleAdapter(this,names,R.layout.music_item , new String[]{"MusicName"}, new int[]{R.id.item_name});
        list.setAdapter(listAdapter);
        //为列表绑定事件监听器
        list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                  Intent intent = new Intent();
                  intent.setClass(MusicList.this, Exercise.class);
                  BackMusic.pauseBackMusic();
                  //信息传输
                  Bundle bundle = new Bundle();
                  bundle.putString("path", files[arg2].getPath());    
                  //跳转
                  intent.putExtras(bundle);
                  startActivity(intent);
            }
        });
        // 为'返回'按钮绑定事件监听器
        ListReturn.setOnTouchListener(new View.OnTouchListener() {
        	public boolean onTouch(View view, MotionEvent motionEvent) {
        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        			ListReturn.startAnimation(click);
        		}
        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {   
        			ListReturn.startAnimation(release);
        			finish();
                }
        		return false;
        	}
        }); 
	}
}

