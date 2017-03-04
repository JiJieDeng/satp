package com.buildce.phototacker;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.hardware.Camera;
import android.view.SurfaceHolder;

public class ImageSocket extends Thread{
	
	private SurfaceHolder sh;
	public static Camera camera;
	public static boolean takePhoto = false;
	
	public ImageSocket(SurfaceHolder sh){
		this.sh = sh;
	}
	
	@SuppressLint("NewApi") 
	public void run(){
		camera = Camera.open(0);
		try {
			camera.setPreviewDisplay(sh);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(true){
			if(takePhoto){
				ImageSocket.takePhoto = false;
				camera.startPreview();
				//camera.autoFocus(null);
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				camera.takePicture(null, null,new MyPictureCallback());
				//camera.stopPreview();
			}else{
				try {
					sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	class MyPictureCallback implements Camera.PictureCallback{

		@Override
		public void onPictureTaken(byte[] arg0, Camera arg1) {
			//MainActivity.t.setText("PhotoTaking");
			MainLoop.imageData = arg0;
			MainLoop.newImage = true;
		}
	}
}

