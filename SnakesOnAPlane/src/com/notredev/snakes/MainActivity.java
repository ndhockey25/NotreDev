package com.notredev.snakes;

import com.amazon.device.gamecontroller.GameController;
import com.amazon.device.gamecontroller.GameController.DeviceNotFoundException;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class MainActivity extends Activity {
	
	public InputManager inputManager = InputManager.Instance();
    // View for this activity
    private SnakeSurfaceView mSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mSurfaceView = new SnakeSurfaceView(this);
        setContentView(mSurfaceView);

        // Initialize with context so GameController can invoke system services
        GameController.init(this);
    }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return this.inputManager.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return this.inputManager.onKeyUp(keyCode, event) || super.onKeyUp(keyCode, event);
	}
	
	@Override
	public boolean onGenericMotionEvent(MotionEvent event) {
		return this.inputManager.onGenericMotionEvent(event) || super.onGenericMotionEvent(event);
	}

    @Override
    protected void onPause() {
        super.onPause();
        mSurfaceView.onPauseMySurfaceView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSurfaceView.onResumeMySurfaceView();
    }

}
