package com.buildce.phototacker;

import java.io.BufferedOutputStream;
import java.net.Socket;

import android.view.SurfaceHolder;

public class MainLoop extends Thread{
	
	public static boolean newImage = false;
	public static byte[] imageData;
	
	public void run(){
		Socket s;
		while(true){
			try{
				s = new Socket("192.168.23.1",6666);
				s.setKeepAlive(true);
				break;
			}catch(Exception e){
				try{
					sleep(1000);
				}catch(Exception e1){
					;
				}
				continue;
			}
		}
		try{
			BufferedOutputStream bos;
			bos = new BufferedOutputStream(s.getOutputStream());
			
			while(true){
				ImageSocket.takePhoto = true;
				while(!newImage){
					sleep(100);
				}
				newImage = false;
				bos.write(getByte(imageData.length));
				bos.write(imageData);
				bos.flush();
			}
		}catch(Exception e){
			;
		}
	}
	private byte[] getByte(int l){
		byte[] ll = new byte[4];
		ll[0] = new Integer(l&0x000000ff).byteValue();
		ll[1] = new Integer((l&0x0000ff00)>>8).byteValue();
		ll[2] = new Integer((l&0x00ff0000)>>16).byteValue();
		ll[3] = new Integer((l&0xff000000)>>24).byteValue();
		return ll;
	}
}
