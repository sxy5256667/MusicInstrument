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
  
    // 初始化一些数据  
    private static void initData() {  
        LeftVolume = 0.5f;  
        RightVolume = 0.5f;  
        BackPlayer = null;  
        IsPaused = false;  
        CurrentID = 0;  
    }  
    
    //根据ID播放背景音乐 
    public static void playBackMusic(int ID, boolean isLoop) {  
        if (CurrentID == 0) {  
            // 这是第一次播放本背景音乐
            BackPlayer = MediaPlayer.create(context, ID);  
            CurrentID = ID;  
        } 
  
        if (BackPlayer == null) {  
            Log.e("BackMusic", "playBackgroundMusic: background media player is null");  
        } else {  
            // 若果音乐正在播放或已近中断，则停止  
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
  

    //停止播放背景音乐
    public void stopBackMusic() {  
        if (BackPlayer != null) {  
            BackPlayer.stop();  
            BackMusic.IsPaused = false;  
        }  
    }  

    //暂停播放背景音乐  
    public static void pauseBackMusic() {  
        if (BackPlayer != null && BackPlayer.isPlaying()) {  
            BackPlayer.pause();  
            BackMusic.IsPaused = true;  
        }  
    }  
  

    //继续播放背景音乐 
    public static void resumeBackMusic() {  
        if (BackPlayer != null && BackMusic.IsPaused) {  
            BackPlayer.start();  
            BackMusic.IsPaused = false;  
        }  
    }  
  
    //重新播放背景音乐 
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
  
    //判断背景音乐是否正在播放 
    public boolean isMusicPlaying() {  
        boolean rtn = false;  
        if (BackPlayer == null) {  
            rtn = false;  
        } else {  
            rtn = BackPlayer.isPlaying();  
        }  
        return rtn;  
    }  
  
    //结束背景音乐，并释放资源 
    public static void end() {  
        if (BackPlayer != null) {  
            BackPlayer.release();  
        }  
        BackMusic.initData();
    }  
  
    //得到背景音乐的音量 
    public float getMusicVolume() {  
        if (BackMusic.BackPlayer != null) {  
            return (BackMusic.LeftVolume + BackMusic.RightVolume) / 2;  
        } else {  
            return 0.0f;  
        }  
    }  
  
    //设置背景音乐的音量 
    public void setMusicVolume(float volume) {  
    	BackMusic.LeftVolume = BackMusic.RightVolume = volume;  
        if (BackMusic.BackPlayer != null) {  
        	BackMusic.BackPlayer.setVolume(BackMusic.LeftVolume,  
        			BackMusic.RightVolume);  
        }  
    }  
    
}
