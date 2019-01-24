package com.example.music;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class BackMusic {
	private static BackMusic backMusic = null;  
    private static float LeftVolume;  
    private static float RightVolume;  
    private static Context context;  
    private static MediaPlayer BackPlayer;  
    private static boolean IsPaused;  
    private static int CurrentID;  
  
    private BackMusic(Context mcontext) {  
        BackMusic.context = mcontext;  
        initData();  
    }  
  
    public static BackMusic setContext(Context context) {  
        if (backMusic == null) {  
            backMusic = new BackMusic(context);  
        }  
        return backMusic;  
    }  
  
    // ��ʼ��һЩ����  
    private static void initData() {  
        LeftVolume = 0.5f;  
        RightVolume = 0.5f;  
        BackPlayer = null;  
        IsPaused = false;  
        CurrentID = 0;  
    }  
    
    //����ID���ű������� 
    public static void playBackMusic(int ID, boolean isLoop) {  
        if (CurrentID == 0) {  
            // ���ǵ�һ�β��ű���������
            BackPlayer = MediaPlayer.create(context, ID);  
            CurrentID = ID;  
        } 
  
        if (BackPlayer == null) {  
            Log.e("BackMusic", "playBackgroundMusic: background media player is null");  
        } else {  
            // �����������ڲ��Ż��ѽ��жϣ���ֹͣ  
            BackPlayer.stop();  
            BackPlayer.setLooping(isLoop);  
            try {  
                BackPlayer.prepare();  
                BackPlayer.seekTo(0);  
                BackPlayer.start();  
                BackMusic.IsPaused = false;  
            } catch (Exception e) {  
                Log.e("BackMusic", "playBackgroundMusic: error state");  
            }  
        }  
    }  
  

    //ֹͣ���ű�������
    public void stopBackMusic() {  
        if (BackPlayer != null) {  
            BackPlayer.stop();  
            BackMusic.IsPaused = false;  
        }  
    }  

    //��ͣ���ű�������  
    public static void pauseBackMusic() {  
        if (BackPlayer != null && BackPlayer.isPlaying()) {  
            BackPlayer.pause();  
            BackMusic.IsPaused = true;  
        }  
    }  
  

    //�������ű������� 
    public static void resumeBackMusic() {  
        if (BackPlayer != null && BackMusic.IsPaused) {  
            BackPlayer.start();  
            BackMusic.IsPaused = false;  
        }  
    }  
  
    //���²��ű������� 
    public static void rewindBackMusic() {  
        if (BackPlayer != null) {  
            BackPlayer.stop();  
            try {  
                BackPlayer.prepare();  
                BackPlayer.seekTo(0);  
                BackPlayer.start();  
                BackMusic.IsPaused = false;  
            } catch (Exception e) {  
                Log.e("BackMusic", "rewindBackgroundMusic: error state");  
            }  
        }  
    }  
  
    //�жϱ��������Ƿ����ڲ��� 
    public boolean isMusicPlaying() {  
        boolean rtn = false;  
        if (BackPlayer == null) {  
            rtn = false;  
        } else {  
            rtn = BackPlayer.isPlaying();  
        }  
        return rtn;  
    }  
  
    //�����������֣����ͷ���Դ 
    public static void end() {  
        if (BackPlayer != null) {  
            BackPlayer.release();  
        }  
        BackMusic.initData();
    }  
  
    //�õ��������ֵ����� 
    public float getMusicVolume() {  
        if (BackMusic.BackPlayer != null) {  
            return (BackMusic.LeftVolume + BackMusic.RightVolume) / 2;  
        } else {  
            return 0.0f;  
        }  
    }  
  
    //���ñ������ֵ����� 
    public void setMusicVolume(float volume) {  
    	BackMusic.LeftVolume = BackMusic.RightVolume = volume;  
        if (BackMusic.BackPlayer != null) {  
        	BackMusic.BackPlayer.setVolume(BackMusic.LeftVolume,  
        			BackMusic.RightVolume);  
        }  
    }  
    
}
