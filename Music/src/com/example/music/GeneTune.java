package com.example.music;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class GeneTune extends Activity {
		//��Ŀ¼����
		private static final String ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
		//�洢�ļ�����  
	    private String filename;  
	    //�洢�ļ�·��  
	    private String path = ROOT_PATH;
	    //�б���
	    private List<Map<String, Object>> list;
	    //����Ϣ���
	    private HashMap<String, Object> map;
	    //�ļ����ļ��б�
	    private File file;
	    private File[] files; 
	    //�������״��  
	    private String tunes = "";
		
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);  //���ô����ޱ����ȫ��
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        //��ʾlist����
	        setContentView(R.layout.in_list);
	        //��ť����
	        final ScaleAnimation click=new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
	        click.setFillAfter(true);
	        final ScaleAnimation release=new ScaleAnimation(1.1f, 1.0f, 1.1f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
	        release.setFillAfter(true);
	        final Button o = (Button) findViewById(R.id.o);
	        o.startAnimation(click);
	        o.startAnimation(release);  
	        //���ذ�ť��ʼ��
	        final Button rtnin = (Button) findViewById(R.id.rtnin);
	        //�б��ʼ��
	        final ListView listview = (ListView) findViewById(R.id.inList);        
	        listview.setAdapter(showFileDir(path));
	        //�б����¼�����
	        listview.setOnItemClickListener(new OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
	            	//�����ǰĿ¼Ϊ��Ŀ¼
	            	if(ROOT_PATH.equals(path)){
	            		//���ѡ����ΪĿ¼������ʾĿ¼���ļ�
	            		if(files[arg2].isDirectory()){
	            			path=files[arg2].getPath();
	                  	  	listview.setAdapter(showFileDir(path));                	 
	                    }
	            		//���ѡ����Ϊ�ļ������ж��ļ������Ƿ�Ϊ��������
	                    else if(files[arg2].isFile()){
	                    	String name=files[arg2].getName();
	                    	String type=name.substring(name.lastIndexOf(".") + 1, name.length()).toLowerCase();
	                    	//�����wav���ͣ����뵽��Ӧ�ļ���
	                    	if(type.equals("wav")){
	                    		WaveFileReader reader = new WaveFileReader(files[arg2].getPath()); 
	                    	    if(reader.isSuccess()){ 
	                    	      int[] data = reader.getData()[0]; //��ȡ��һ���� 
	                    	      Tunesplit tune=new Tunesplit(data);
	                    	      tunes=tune.split();
	                    	      copyFile(name);
	                    	    } 
	                    	    else{ 
	                    	    	new AlertDialog.Builder(GeneTune.this)  
		                            .setTitle("Error")
		                            .setMessage("It's not a normal wave file!")
		                            .setNegativeButton("ȷ��", new DialogInterface.OnClickListener() {  
		                                @Override  
		                                public void onClick(DialogInterface dialog, int which) {}  
		                            }).show(); 
	                    	   }	                    		
	                    	}
	                    	//������ǣ��򵯳���ʾ��
	                    	else{
	                    		new AlertDialog.Builder(GeneTune.this)  
	                            .setTitle("Error")
	                            .setMessage("Please choose the correct type of file")
	                            .setNegativeButton("ȷ��", new DialogInterface.OnClickListener() {  
	                                @Override  
	                                public void onClick(DialogInterface dialog, int which) {}  
	                            }).show();
	                    	}                	  
	                    }
	            	}
	            	//�����ǰ���Ǹ�Ŀ¼������ӷ��ظ�Ŀ¼�������ϼ��˵�����
	            	else {
	            		//���ظ�Ŀ¼
	            		if(arg2==0){
	                  	 path=ROOT_PATH;
	                  	  listview.setAdapter(showFileDir(path));
	                    }
	            		//�����ϼ��˵�
	            		else if(arg2==1){
	                  	  path=file.getParent();
	                  	  listview.setAdapter(showFileDir(path));   
	                    }
	            		//���ѡ����ΪĿ¼������ʾĿ¼���ļ�
	            		else if(files[arg2-2].isDirectory()){
	            			path=files[arg2-2].getPath();
	                  	  	listview.setAdapter(showFileDir(path));                	 
	                    }
	            		//���ѡ����Ϊ�ļ������ж��ļ������Ƿ�Ϊ��������
	                    else if(files[arg2-2].isFile()){
	                    	String name=files[arg2-2].getName();
	                    	String type=name.substring(name.lastIndexOf(".") + 1, name.length()).toLowerCase();
	                    	//�����tune���ͣ����뵽��Ӧ�ļ���
	                    	if(type.equals("wav")){
	                    		WaveFileReader reader = new WaveFileReader(files[arg2-2].getPath()); 
	                    	    if(reader.isSuccess()){ 
	                    	      int[] data = reader.getData()[0]; //��ȡ��һ���� 
	                    	      Tunesplit tune=new Tunesplit(data);
	                    	      tunes=tune.split();
	                    	      copyFile(name);
	                    	    } 
	                    	    else{ 
	                    	    	new AlertDialog.Builder(GeneTune.this)  
		                            .setTitle("Error")
		                            .setMessage("It's not a normal wave file!")
		                            .setNegativeButton("ȷ��", new DialogInterface.OnClickListener() {  
		                                @Override  
		                                public void onClick(DialogInterface dialog, int which) {}  
		                            }).show(); 
	                    	   }	                    		
	                    	}
	                    	//������ǣ��򵯳���ʾ��
	                    	else{
	                    		new AlertDialog.Builder(GeneTune.this)  
	                            .setTitle("Error")
	                            .setMessage("Please choose the correct type of file")
	                            .setNegativeButton("ȷ��", new DialogInterface.OnClickListener() {  
	                                @Override  
	                                public void onClick(DialogInterface dialog, int which) {}  
	                            }).show();
	                    	}               	  
	                    }
	            	} 
	            }
	        });
	        // Ϊ'����'��ť���¼�������
	        rtnin.setOnTouchListener(new View.OnTouchListener() {
	        	public boolean onTouch(View view, MotionEvent motionEvent) {
	        		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
	        			rtnin.startAnimation(click);
	        		}
	        		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {   
	        			rtnin.startAnimation(release);
	        			finish();
	                }
	        		return false;
	        	}
	        }); 
		}	
		
		private SimpleAdapter showFileDir(String path){  
			 list = new ArrayList<Map<String, Object>>();
		     file = new File(path);
		     files = file.listFiles();
			//�����ǰĿ¼���Ǹ�Ŀ¼  
	        if (!ROOT_PATH.equals(path)){  
	        	//���ظ�Ŀ¼
	        	map = new HashMap<String, Object>();
	        	map.put("img", R.drawable.icon);
	        	map.put("filename", "/���ظ�Ŀ¼");
	        	list.add(map);
	            //������һ��
	        	map = new HashMap<String, Object>();
	        	map.put("img", R.drawable.icon);
	        	map.put("filename", "/������һ��");
	        	list.add(map);
	        }  
	        //��������ļ�  
	        for (File f : files){  
	        	map= new HashMap<String, Object>();	        	
	        		filename=f.getName();
	        		if(f.isDirectory()){
	        			map.put("filename", filename);
	        			map.put("img", R.drawable.folder);
	        		}
	        		else if(f.isFile()){
	        			map.put("filename", filename);
	        			map.put("img", R.drawable.file);
	        		}
	        		list.add(map); 	        	
	        }         
	        SimpleAdapter listAdapter =new SimpleAdapter(this,list,R.layout.in_item,new String[] { "img", "filename"},new int[] { R.id.in_img,R.id.file_name });
	        return listAdapter;
	    } 
		
		public void copyFile(String name) {//���ļ����뵽��Ӧ�ļ���
			final String newPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/MusicManager/exerList/"+name;
			File newfile = new File(newPath);
			if(newfile.exists()){
				 new AlertDialog.Builder(GeneTune.this)  
	             .setTitle("Exist")
	             .setMessage("�ļ��Ѵ��ڣ��Ƿ񸲸�")
	             .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {  
	                 @Override  
	                 public void onClick(DialogInterface dialog, int which) {
	                	 return;
	                 }})
	             .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						try {
					    	   File file = new File(newPath);
					           if (!file.exists()) {
					            file.createNewFile();
					           }
					           RandomAccessFile raf = new RandomAccessFile(file, "rw");
					           raf.seek(file.length());
					           raf.write(tunes.getBytes());
					           raf.close();
					    	   //����ɹ���ʾ��
					           new AlertDialog.Builder(GeneTune.this)  
					           .setTitle("Succeed")
					           .setMessage("���ɳɹ�")
					           .setNegativeButton("ȷ��", new DialogInterface.OnClickListener() {  
					               @Override  
					               public void onClick(DialogInterface dialog, int which) {}  
					           }).show();
					       }   
					       catch (Exception e) { 
					    	   //�����������ʧ�ܣ������������˱�����һ�㲻��ִ��
					    	   new AlertDialog.Builder(GeneTune.this)  
				               .setTitle("Failed")
				               .setMessage("����ʧ��")
				               .setNegativeButton("ȷ��", new DialogInterface.OnClickListener() {  
				                   @Override  
				                   public void onClick(DialogInterface dialog, int which) {}  
				               }).show();	  
					       }
						
					}
	            	 
	             }).show();
			}
			else{
				try {
			    	   File file = new File(newPath);
			           if (!file.exists()) {
			            file.createNewFile();
			           }
			           RandomAccessFile raf = new RandomAccessFile(file, "rw");
			           raf.seek(file.length());
			           raf.write(tunes.getBytes());
			           raf.close();
			    	   //����ɹ���ʾ��
			           new AlertDialog.Builder(GeneTune.this)  
			           .setTitle("Succeed")
			           .setMessage("���ɳɹ�")
			           .setNegativeButton("ȷ��", new DialogInterface.OnClickListener() {  
			               @Override  
			               public void onClick(DialogInterface dialog, int which) {}  
			           }).show();
			       }   
			       catch (Exception e) { 
			    	   //�����������ʧ�ܣ������������˱�����һ�㲻��ִ��
			    	   new AlertDialog.Builder(GeneTune.this)  
		               .setTitle("Failed")
		               .setMessage("����ʧ��")
		               .setNegativeButton("ȷ��", new DialogInterface.OnClickListener() {  
		                   @Override  
		                   public void onClick(DialogInterface dialog, int which) {}  
		               }).show();	  
			       }  
			}
		        
			} 
		 
}

