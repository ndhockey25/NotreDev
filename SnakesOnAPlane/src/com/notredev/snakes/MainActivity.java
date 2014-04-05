package com.notredev.snakes;

import com.amazon.device.gamecontroller.GameController;
import com.amazon.device.gamecontroller.GameController.DeviceNotFoundException;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class MainActivity extends Activity {
	
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

    /*
     * Forward key down events to GameController so it can manage
     * state(non-Javadoc)
     * 
     * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean handled = false;
        try {
            handled = GameController.onKeyDown(keyCode, event);
        }
        catch (DeviceNotFoundException e) {
        }
        return handled || super.onKeyDown(keyCode, event);
    }

    /*
     * Forward key up events to GameController so it can manage
     * state(non-Javadoc)
     * 
     * @see android.app.Activity#onKeyUp(int, android.view.KeyEvent)
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        boolean handled = false;
        try {
            handled = GameController.onKeyUp(keyCode, event);
        }
        catch (DeviceNotFoundException e) {
        }
        return handled || super.onKeyUp(keyCode, event);
    }

    /*
     * Forward motion events to GameController so it can manage
     * state(non-Javadoc)
     * 
     * @see android.app.Activity#onGenericMotionEvent(android.view.MotionEvent)
     */
    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        boolean handled = false;
        try {
            handled = GameController.onGenericMotionEvent(event);
        }
        catch (DeviceNotFoundException e) {
        }
        return handled || super.onGenericMotionEvent(event);
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
