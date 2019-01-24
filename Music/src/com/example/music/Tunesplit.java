package com.example.music;
public class Tunesplit {
	  private int[] data = null; 
	  public String tune = "";
	  private double TH=0;
	  
	  public Tunesplit(int[] data) { 
	    this.data = data; 
	  } 
	  
	  public String split() { 
		  int size=data.length;
		  int pick=512;
		  int width=256;
		  int prei=0;
		  InitialTH(width);
		  double temp=0;		  
		  for(int i=width*10;i<size;i=i+pick){
			  refreshTH(i,width);
			  if(data[i]<10||i<temp+width*20)
				  continue;		  	  
			  if(isTune(i,width)){			 
				  temp=i;
				  //System.out.println();
				  //System.out.println("start="+i);
				  if((prei-temp)>11000){
					  for(int j=0;j<(prei-temp)/11000;j++){
						  tune=tune+' ';
					  }
				  }
				  recog(data,i);
				  prei=i;
			  }	
		  }
	 	  return tune;
	  }   
	  
	  protected void recog(int[] rsc,int start) { 
		  int size=256;
		  int pick=20;
		  int large=0;
		  int largex=0;
		  Complex[] fftx = new Complex[size];
		  for (int i = 0; i < size; i++) {
		      fftx[i] = new Complex(i, 0);		      
		      fftx[i] = new Complex(rsc[i*pick+start], 0);
		      //System.out.print(" "+(i*pick+start));
		  }
		  Complex[] result=FFT.fft(fftx);
		  for (int i = 1; i < size/2; i++) {
			  if(result[i].re()>0&&result[i].re()>large){
				  large=(int) result[i].re();
				  largex=i;
			  }
			  else if(result[i].re()<0&&(-result[i].re())>large){
				  large=(int) -result[i].re();
				  largex=i;
			  }
		  }
	 	  int fx=(int)44100*largex/pick/size;
	 	  tune=tune+tuneOrgnize(fx);	 	  
	  } 
	  //具体音高字符输出
	  protected char tuneOrgnize(int x){
		  if(x<169)		  return ' ';
		  else if(x<179)  return 'A';
		  else if(x<190)  return '0';
		  else if(x<201)  return 'B';
		  else if(x<213)  return '1';
		  else if(x<226)  return 'C';
		  else if(x<240)  return '2';
		  else if(x<253)  return 'D';
		  else if(x<269)  return 'E';
		  else if(x<285)  return '3';
		  else if(x<302)  return 'F';
		  else if(x<320)  return '4';
		  else if(x<339)  return 'G';
		  else if(x<359)  return 'H';
		  else if(x<380)  return '5';
		  else if(x<403)  return 'I';
		  else if(x<428)  return '6';
		  else if(x<480)  return 'J';
		  else if(x<508)  return '7';
		  else if(x<538)  return 'L';
		  else if(x<570)  return '8';
		  else if(x<604)  return 'M';
		  else if(x<679)  return '9';
		  else if(x<718)  return 'N';
		  else return ' ';
	  }
	  //判断是否为起始点
	  protected boolean isTune(int start,int width){
		  for(int i=start;i<start+width*5;i=i+width){
			  if(Ncal(i,width,2)<TH)
				  return false;
		  }
		  return true;
	  }
	  //初始化初始门限
	  protected void InitialTH(int width){
		  for(int i=0;i<10;i++){
			  TH=TH+Ecal(i*width,width);
		  }
		  TH=TH/10*1.2;  
	  }
	  //更新门限
	  protected void refreshTH(int start,int width){
		  TH=0.9*TH+0.1*1.2*Ncal(start,width,10);  
	  }
	  //N点短时能零积平均值计算，start为起点，width为帧宽，重叠半帧
	  protected double Ncal(int start,int width,int n){
		  double N=0;
		  for(int i=start;i>start-(n-1)*width;i=i-width/2){
			  N=N+EZcal(i,width);
		  }
		  N=N/n;
		  return N;
	  }
	  //短时能零积计算，start为起点，width为帧宽
	  protected double EZcal(int start,int width){
		  double E=0,EZ=0;
		  int Z;
		  E=Ecal(start,width);
		  Z=Zcal(start,width);
		  EZ=E*Z;
		  return EZ;
	  }
	  //短时能量计算，start为起点，width为帧宽
	  protected double Ecal(int start,int width){
		  double E=0;
		  for(int i=start;i<(start+width);i++){
				  E=E+data[i]*data[i];
		  }
		  return E;
	  }
	  //过零率计算，start为起点，width为帧宽
	  protected int Zcal(int start,int width){
		  int Z=0;
		  for(int i=start+1;i<(start+width);i++){
			  if(data[i-1]>0&&data[i]<0)
				  Z++;
			  else if(data[i-1]<0&&data[i]>0)
				  Z++;
		  }
		  return Z;
	  }

}
