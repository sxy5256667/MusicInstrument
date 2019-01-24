package com.example.music;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import java.util.Timer;
import java.util.TimerTask;

public class ExerPicture extends View{
	// ��¼����λͼ��ʵ�ʸ߶�
	final int BACK_HEIGHT = 580;
	private int count = 60;
	//����
	public String tune = "ABCDEFGHIJKLMN 0123456789";
    //�����ݴ�
	private char[] tone = new char[6];
	//��ͼ�ݴ�
	private Bitmap bitmap1 ;
	private Bitmap bitmap2 ;
	private Bitmap bitmap3 ;
	private Bitmap bitmap4 ;
	private Bitmap bitmap5 ;
	private Bitmap bitmap6 ;
	//�����ݴ�
	private int[] startY = new int[6];
	private int[] X = {0,0,0,0,0,0};
	//���׼�����
	private int i = 0;
	private boolean end = false;
	//�Ƿ�����
	private boolean[] click = {false,false,false,false,false,false}; 
	//���߷���ͼƬ
	private Bitmap exerect = BitmapFactory.decodeResource(this.getResources(),R.drawable.exerect);
	private Bitmap endrect = BitmapFactory.decodeResource(this.getResources(),R.drawable.exerect);
	// ͼƬ����
	final int WIDTH = 62;
	final int HEIGHT = 220;
	public Handler handler;
	
	public ExerPicture(final Activity context,String Tunes){
		super(context);		
		tune = Tunes;
		//���䷽���ʼ��
		bitmap1 = Bitmap.createBitmap(exerect, 0, 0, WIDTH, HEIGHT);
		bitmap2 = Bitmap.createBitmap(exerect, 0, 0, WIDTH, HEIGHT);
		bitmap3 = Bitmap.createBitmap(exerect, 0, 0, WIDTH, HEIGHT);
		bitmap4 = Bitmap.createBitmap(exerect, 0, 0, WIDTH, HEIGHT);
		bitmap5 = Bitmap.createBitmap(exerect, 0, 0, WIDTH, HEIGHT);
		bitmap6 = Bitmap.createBitmap(exerect, 0, 0, WIDTH, HEIGHT);
		//�������
		handler = new Handler(){
			public void handleMessage(Message msg){
				//��������
				if (count!=0){
					for(int j=0;j<6;j++){
						if(startY[j]>0){
							startY[j] -= 10;							
						}
					}					
					count--;
				}
				//����װ��
				else if(i<tune.length()){	
					//��ȡ��һ����
					tone[i%6]=tune.charAt(i);
					//����Y
					startY[i%6]=BACK_HEIGHT-HEIGHT;
					//���õ���ж�
					click[i%6]=false;
					//��������λ��
					if(tone[i%6]>='A'&&tone[i%6]<='Z')
						X[i%6]=(tone[i%6]-'A')*WIDTH;
					else if(tone[i%6]>='0'&&tone[i%6]<='9'){
						switch(tone[i%6]){
						case '0':
							X[i%6]=41;
							break;
						case '1':
							X[i%6]=41+WIDTH;
							break;
						case '2':
							X[i%6]=41+WIDTH*2;
							break;
						case '3':
							X[i%6]=41+WIDTH*4;
							break;
						case '4':
							X[i%6]=41+WIDTH*5;
							break;
						case '5':
							X[i%6]=41+WIDTH*7;
							break;
						case '6':
							X[i%6]=41+WIDTH*8;
							break;
						case '7':
							X[i%6]=41+WIDTH*9;
							break;
						case '8':
							X[i%6]=41+WIDTH*11;
							break;
						case '9':
							X[i%6]=41+WIDTH*12;
							break;
						default:
							X[i%6]=0;
							break;
						}
					}
					i++;
					count = 5;
				}
				else if(i==tune.length()){
					if(end==false){
					count=40;
					end=true;
					}
					else{
						//BackMusic.resumeBackMusic();
						context.finish();					
					}
					
				}
				invalidate();
				}
		};

		//��������������
		new Timer().schedule(new TimerTask(){
			@Override
			public void run(){
				handler.sendEmptyMessage(0x123);
			}
		}, 0, 50);
	}		
	
	//����װ��
	public Bitmap getMap(char t, int y){
		Bitmap temp;
		//�׼�
		if(t>='A'&&t<='Z'){
			temp = Bitmap.createBitmap(exerect, 0, y, WIDTH, HEIGHT);
		}
		//�ڼ�
		else if(t>='0'&&t<='9'){
			temp = Bitmap.createBitmap(exerect, 0, y, WIDTH-20, HEIGHT);
		}
		//����
		else{
			temp = Bitmap.createBitmap(exerect, 0, 0, WIDTH, HEIGHT);
		}		
		return temp;
	}
	
	//���ƺ���
	@Override
	public void onDraw(Canvas canvas)
	{
		//���������ͼƬ����
		bitmap1 = getMap(tone[0],startY[0]);
		bitmap2 = getMap(tone[1],startY[1]);
		bitmap3 = getMap(tone[2],startY[2]);
		bitmap4 = getMap(tone[3],startY[3]);
		bitmap5 = getMap(tone[4],startY[4]);
		bitmap6 = getMap(tone[5],startY[5]);
		// ������λͼ
		canvas.drawBitmap(bitmap1, X[0], 0, null);
		canvas.drawBitmap(bitmap2, X[1], 0, null);
		canvas.drawBitmap(bitmap3, X[2], 0, null);
		canvas.drawBitmap(bitmap4, X[3], 0, null);
		canvas.drawBitmap(bitmap5, X[4], 0, null);		
		canvas.drawBitmap(bitmap6, X[5], 0, null);
	}
	
	//�ض���������߶Ȼ�ȡ
	public int getY(char tun){
		//��ǰ����װ��ķ���Ϊi%6���������װ���ӦΪ(i+1)%6�ſ�,�ú����Զ���ȡ��µ�һ��δ�������
		for(int j=i+1;j<=i+6;j++){
			if(tone[j%6]==tun&&click[j%6]==false){
				click[j%6]=true;
				return startY[j%6];				
			}
		}
		return 0;
	}
}