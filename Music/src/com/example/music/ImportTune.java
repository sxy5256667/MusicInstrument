package com.example.music;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
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

public class ImportTune extends Activity {	
	//根目录定义
	private static final String ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
	//存储文件名称  
    private String filename;  
    //存储文件路径  
    private String path = ROOT_PATH;
    //列表存放
    private List<Map<String, Object>> list;
    //项信息存放
    private HashMap<String, Object> map;
    //文件及文件列表
    private File file;
    private File[] files; 
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //设置窗口无标题和全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //显示list界面
        setContentView(R.layout.in_list);
        //按钮动画
        final ScaleAnimation click=new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        click.setFillAfter(true);
        final ScaleAnimation release=new ScaleAnimation(1.1f, 1.0f, 1.1f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        release.setFillAfter(true);
        final Button o = (Button) findViewById(R.id.o);
        o.startAnimation(click);
        o.startAnimation(release);  
        //返回按钮初始化
        final Button rtnin = (Button) findViewById(R.id.rtnin);
        //列表初始化
        final ListView listview = (ListView) findViewById(R.id.inList);        
        listview.setAdapter(showFileDir(path));
        //列表点击事件监听
        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	//如果当前目录为根目录
            	if(ROOT_PATH.equals(path)){
            		//如果选中项为目录，则显示目录中文件
            		if(files[arg2].isDirectory()){
            			path=files[arg2].getPath();
                  	  	listview.setAdapter(showFileDir(path));                	 
                    }
            		//如果选中项为文件，则判断文件类型是否为所需类型
                    else if(files[arg2].isFile()){
                    	String name=files[arg2].getName();
                    	String type=name.substring(name.lastIndexOf(".") + 1, name.length()).toLowerCase();
                    	//如果是tune类型，则导入到对应文件夹
                    	if(type.equals("tune")){
                    		copyFile(files[arg2].getPath(),files[arg2].getName());
                    	}
                    	//如果不是，则弹出提示窗
                    	else{
                    		new AlertDialog.Builder(ImportTune.this)  
                            .setTitle("Error")
                            .setMessage("Please choose the correct type of file")
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {  
                                @Override  
                                public void onClick(DialogInterface dialog, int which) {}  
                            }).show();
                    	}                	  
                    }
            	}
            	//如果当前不是根目录，则添加返回根目录及返回上级菜单两项
            	else {
            		//返回根目录
            		if(arg2==0){
                  	 path=ROOT_PATH;
                  	  listview.setAdapter(showFileDir(path));
                    }
            		//返回上级菜单
            		else if(arg2==1){
                  	  path=file.getParent();
                  	  listview.setAdapter(showFileDir(path));   
                    }
            		//如果选中项为目录，则显示目录中文件
            		else if(files[arg2-2].isDirectory()){
            			path=files[arg2-2].getPath();
                  	  	listview.setAdapter(showFileDir(path));                	 
                    }
            		//如果选中项为文件，则判断文件类型是否为所需类型
                    else if(files[arg2-2].isFile()){
                    	String name=files[arg2-2].getName();
                    	String type=name.substring(name.lastIndexOf(".") + 1, name.length()).toLowerCase();
                    	//如果是tune类型，则导入到对应文件夹
                    	if(type.equals("tune")){
                    		copyFile(files[arg2-2].getPath(),files[arg2-2].getName());
                    	}
                    	//如果不是，则弹出提示窗
                    	else{
                    		new AlertDialog.Builder(ImportTune.this)  
                            .setTitle("Error")
                            .setMessage("Please choose the correct type of file")
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {  
                                @Override  
                                public void onClick(DialogInterface dialog, int which) {}  
                            }).show();
                    	}               	  
                    }
            	} 
            }
        });
        // 为'返回'按钮绑定事件监听器
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
		//如果当前目录不是根目录  
        if (!ROOT_PATH.equals(path)){  
        	//返回根目录
        	map = new HashMap<String, Object>();
        	map.put("img", R.drawable.icon);
        	map.put("filename", "/返回根目录");
        	list.add(map);
            //返回上一级
        	map = new HashMap<String, Object>();
        	map.put("img", R.drawable.icon);
        	map.put("filename", "/返回上一级");
        	list.add(map);
        }  
        //添加所有文件  
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
	
	public void copyFile(final String sourcePath, String name) {//将文件导入到对应文件夹
		final String newPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/MusicManager/exerList/"+name;
		final File oldfile = new File(sourcePath);  
 	    File newfile = new File(newPath);  
 	    if(newfile.exists()){
 	    	 new AlertDialog.Builder(ImportTune.this)  
             .setTitle("Exist")
             .setMessage("文件已存在，是否覆盖")
             .setNegativeButton("取消", new DialogInterface.OnClickListener() {  
                 @Override  
                 public void onClick(DialogInterface dialog, int which) {
                	 return;
                 }})
             .setPositiveButton("确定", new DialogInterface.OnClickListener() {  
                 @Override  
                 public void onClick(DialogInterface dialog, int which) {
                	 try {
             			//源文件存在时，防止误操作
             	           if (oldfile.exists()) { 
             	        	   int byteread = 0;
             	        	   //读入原文件
             	               InputStream inStream = new FileInputStream(sourcePath);    
             	               FileOutputStream fs = new FileOutputStream(newPath);   
             	               //采用缓存方式分批读入并转写源文件到目标文件
             	               byte[] buffer = new byte[1444];   
             	               while ( (byteread = inStream.read(buffer)) != -1) {
             	                   fs.write(buffer, 0, byteread);   
             	               }   
             	               inStream.close();   
             	               fs.close(); 
             	           }
             	           new AlertDialog.Builder(ImportTune.this)  
                            .setTitle("Succeed")
                            .setMessage("导入文件成功")
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {  
                                @Override  
                                public void onClick(DialogInterface dialog, int which) {}  
                            }).show();
             	       }   
             	       catch (Exception e) { 
             	    	   //如果出错则导入失败，由于上面做了保护，一般不会执行
             	    	   new AlertDialog.Builder(ImportTune.this)  
                            .setTitle("Failed")
                            .setMessage("导入文件失败")
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {  
                                @Override  
                                public void onClick(DialogInterface dialog, int which) {}  
                            }).show();	  
             	       }   
                 }  
             }).show();	
 	    }
 	    else{
 	    	 try {
      			//源文件存在时，防止误操作
      	           if (oldfile.exists()) { 
      	        	   int byteread = 0;
      	        	   //读入原文件
      	               InputStream inStream = new FileInputStream(sourcePath);    
      	               FileOutputStream fs = new FileOutputStream(newPath);   
      	               //采用缓存方式分批读入并转写源文件到目标文件
      	               byte[] buffer = new byte[1444];   
      	               while ( (byteread = inStream.read(buffer)) != -1) {
      	                   fs.write(buffer, 0, byteread);   
      	               }   
      	               inStream.close();   
      	               fs.close(); 
      	           }
      	           new AlertDialog.Builder(ImportTune.this)  
                     .setTitle("Succeed")
                     .setMessage("导入文件成功")
                     .setNegativeButton("确定", new DialogInterface.OnClickListener() {  
                         @Override  
                         public void onClick(DialogInterface dialog, int which) {}  
                     }).show();
      	       }   
      	       catch (Exception e) { 
      	    	   //如果出错则导入失败，由于上面做了保护，一般不会执行
      	    	   new AlertDialog.Builder(ImportTune.this)  
                     .setTitle("Failed")
                     .setMessage("导入文件失败")
                     .setNegativeButton("确定", new DialogInterface.OnClickListener() {  
                         @Override  
                         public void onClick(DialogInterface dialog, int which) {}  
                     }).show();	  
      	       }   
 	    }
		
	  
	   } 
	 
}