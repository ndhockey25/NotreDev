/**
 * Amazon Fire TV Development Resources
 *
 * Copyright 2004-2014 Amazon.com, Inc. or its affiliates.  All Rights Reserved.
 
 * These materials are licensed as "Program Materials" under the Program Materials 
 * License Agreement (the "License") of the Amazon Mobile App Distribution program, 
 * which is available at https://developer.amazon.com/sdk/pml.html.  See the License 
 * for the specific language governing permissions and limitations under the License.
 *
 * These materials are distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */

package com.sample.amazon.samplegame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.amazon.device.gamecontroller.GameController;
import com.amazon.device.gamecontroller.GameController.DeviceNotFoundException;
import com.amazon.device.gamecontroller.GameController.PlayerNumberNotFoundException;

/**
 * This is a sample game which will be a part of the SDK. The game shows a
 * colored dot for each of the input game controllers connected. Using the
 * connected input game controllers navigation buttons the dots can be moved on
 * the screen<br>
 * 
 * This game can support till 4 players
 * 
 */
public class GameActivity extends Activity {

    /* This would be used for logging purpose */
    public static final String TAG = GameActivity.class.getName();

    // View for this activity
    private MySurfaceView mySurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mySurfaceView = new MySurfaceView(this);
        setContentView(mySurfaceView);

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
        mySurfaceView.onPauseMySurfaceView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mySurfaceView.onResumeMySurfaceView();
    }

    /**
     * Implements drawing at 60fps from a separate thread
     * 
     */
    public class MySurfaceView extends SurfaceView implements Runnable {

        private volatile boolean running = false;

        private Thread thread = null;
        private SurfaceHolder surfaceHolder;
        private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        /*
         * Constructor
         */
        public MySurfaceView(Context context) {
            super(context);
            surfaceHolder = getHolder();
        }

        /*
         * Start the thread and mark it with running flag
         */
        public void onResumeMySurfaceView() {
            running = true;
            thread = new Thread(this);
            thread.start();
        }

        /*
         * Pause the thread
         */
        public void onPauseMySurfaceView() {
            boolean retry = true;
            running = false;
            while (retry) {
                try {
                    thread.join();
                    retry = false;
                }
                catch (InterruptedException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }

        /*
         * This is the core drawing thread that demonstrates the user of
         * GameController(non-Javadoc)
         * 
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {

            // Store an X,Y out-of-boundary defaults for each of the 4 players
            int x[] = new int[] {
                    -1, -1, -1, -1
            };
            int y[] = new int[] {
                    -1, -1, -1, -1
            };

            // Define a color for each player
            int colors[] = new int[] {
                    Color.RED, Color.GREEN, Color.YELLOW, Color.MAGENTA
            };

            while (running) {

                if (surfaceHolder.getSurface().isValid()) {

                    Canvas canvas = surfaceHolder.lockCanvas();
                    canvas.drawColor(Color.BLACK);

                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(8);
                    int width = canvas.getWidth();
                    int height = canvas.getHeight();

                    // Iterate through all players
                    for (int n = 0; n < GameController.MAX_PLAYERS; n++) {

                        GameController gameController = null;
                        try {
                            gameController =
                                    GameController.getControllerByPlayer(n + 1);
                        }
                        catch (PlayerNumberNotFoundException e) {
                        }

                        // If the cursor went out of the screen boundaries
                        if ((x[n] < 0) | (y[n] < 0) | (x[n] > width)
                                | (y[n] > height)) {
                            // Reset cursors proportionally on a horizontally
                            // centered line
                            x[n] =
                                    width / (GameController.MAX_PLAYERS + 1)
                                            * (n + 1);
                            y[n] = height / 2;
                        }

                        if (gameController != null) {

                            // Move the cursor proportionally to the stick angle
                            float deltaX =
                                    gameController
                                            .getAxisValue(GameController.AXIS_STICK_LEFT_X);
                            float deltaY =
                                    gameController
                                            .getAxisValue(GameController.AXIS_STICK_LEFT_Y);
                            if ((deltaX * deltaX + deltaY * deltaY) > GameController.DEAD_ZONE
                                    * GameController.DEAD_ZONE) {
                                // stick angle is greater than the center dead
                                // zone
                                x[n] += Math.round(deltaX * 10);
                                y[n] += Math.round(deltaY * 10);
                            }

                            // Move the cursor function of the DPAD direction
                            x[n] +=
                                    gameController
                                            .isButtonPressed(GameController.BUTTON_DPAD_RIGHT) ? +5
                                            : 0;
                            x[n] +=
                                    gameController
                                            .isButtonPressed(GameController.BUTTON_DPAD_LEFT) ? -5
                                            : 0;
                            y[n] +=
                                    gameController
                                            .isButtonPressed(GameController.BUTTON_DPAD_DOWN) ? +5
                                            : 0;
                            y[n] +=
                                    gameController
                                            .isButtonPressed(GameController.BUTTON_DPAD_UP) ? -5
                                            : 0;

                            // Draw a spot for that player
                            paint.setColor(colors[n]);
                            canvas.drawPoint(x[n], y[n], paint);

                            // Draw a circle for each down transition of button
                            // A on a gamepad or on the Dpad center on a remote
                            if (gameController
                                    .wasButtonPressed(GameController.BUTTON_A)
                                    | gameController
                                            .wasButtonPressed(GameController.BUTTON_DPAD_CENTER)) {
                                canvas.drawCircle(x[n], y[n], 20, paint);
                            }
                        }
                    }

                    // Now pass over to the GPU for rendering.
                    GameController.startFrame();
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }

                // Wait the equivalent of one frame at 60fps
                try {
                    Thread.sleep(17);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
