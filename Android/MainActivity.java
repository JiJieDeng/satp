package com.buildce.phototacker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private Button b;
	public static TextView t;
	private SurfaceView sv;
	private SurfaceHolder surfaceholder;
	private WakeLock m_wklk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PowerManager pm = (PowerManager)getSystemService(POWER_SERVICE);
        m_wklk = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "cn");
        m_wklk.acquire(); //设置保持唤醒
        
        b = (Button)findViewById(R.id.button1);
        t = (TextView)findViewById(R.id.t);
        sv = (SurfaceView)findViewById(R.id.surfaceView1);
        
        t.setText("");
        
        surfaceholder = sv.getHolder();
        surfaceholder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceholder.addCallback(new SurfaceHolder.Callback(){
        	
			@SuppressLint("NewApi") @Override
			public void surfaceCreated(SurfaceHolder arg0) {
				//画布准备好后开启拍照线程
				t.setText("准备拍照");
				new MainLoop().start();
				new ImageSocket(surfaceholder).start();
			}
	        
	        @Override
			public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2,int arg3) {}

			@Override
			public void surfaceDestroyed(SurfaceHolder arg0){}
        	
        });
    }

	@Override
	public void onBackPressed() {
		ImageSocket.camera.release();
		ImageSocket.camera = null;
		System.exit(0);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		m_wklk.release(); //解除保持唤醒
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		m_wklk.release();//解除保持唤醒
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		m_wklk.acquire(); //设置保持唤醒
	}
    
}
