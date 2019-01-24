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
	//����Ŀ¼
	private static final String ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/MusicManager/exerList";
	//Ŀ¼��洢
	private ArrayList<HashMap<String,String>> names = new ArrayList<HashMap<String,String>>(); 
	//Ŀ¼����Ϣ�洢
	private HashMap<String, String> map;
	//�ļ����ݴ���
	private String filename = new String();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //���ô����ޱ����ȫ��
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //��ʾ�����б����
        setContentView(R.layout.music_list);
        //��ť����
        final ScaleAnimation click=new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        click.setFillAfter(true);
        final ScaleAnimation release=new ScaleAnimation(1.1f, 1.0f, 1.1f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        release.setFillAfter(true);
        final Button o = (Button) findViewById(R.id.o);
        o.startAnimation(click);
        o.startAnimation(release);
        //���ذ�ť��ʼ��
        final Button ListReturn = (Button) findViewById(R.id.rtnl);
        //����Ŀ¼��ʼ��
        File file = new File(ROOT_PATH);
        //����޵�ǰĿ¼���򴴽�Ŀ¼
        if(!file.exists()){
        	file.mkdirs();
        }
        //�б��ȡ���洢
        final File[] files = file.listFiles();
        //��ȡ"ExrciseList"�ļ����������ļ����ļ���
        for (File f : files){  
        	map= new HashMap<String, String>();
        	filename=f.getName().substring(0, f.getName().indexOf("."));
        	map.put("MusicName", filename);
        	names.add(map);  
        }  
        //�б���ʾ��ʼ��
        ListView list = (ListView) findViewById(R.id.musicList);
        //���ļ�����ʾ��ListView��
        SimpleAdapter listAdapter =new SimpleAdapter(this,names,R.layout.music_item , new String[]{"MusicName"}, new int[]{R.id.item_name});
        list.setAdapter(listAdapter);
        //Ϊ�б���¼�������
        list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                  Intent intent = new Intent();
                  intent.setClass(MusicList.this, Exercise.class);
                  BackMusic.pauseBackMusic();
                  //��Ϣ����
                  Bundle bundle = new Bundle();
                  bundle.putString("path", files[arg2].getPath());    
                  //��ת
                  intent.putExtras(bundle);
                  startActivity(intent);
            }
        });
        // Ϊ'����'��ť���¼�������
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

