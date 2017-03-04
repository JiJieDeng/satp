package com.buildce.phototacker;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ImageSender extends Thread{
	
	private Socket s;
	private byte[] image;
	
	public ImageSender(byte[] image){
		this.image = image;
	}
	
	public void run(){
		while(true){
			try {
				s = new Socket("192.168.23.1",6666);
				BufferedOutputStream bus = new BufferedOutputStream(s.getOutputStream());
				bus.write(image);
				bus.flush();
				bus.close();
				s.close();
				bus = null;
				s = null;
				break;
			} catch (UnknownHostException e) {
			} catch (IOException e) {
			}
		}
	}

}
