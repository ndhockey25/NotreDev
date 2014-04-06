package com.notredev.snakes;

import java.util.Random;

import com.amazon.device.gamecontroller.GameController;
import com.amazon.device.gamecontroller.GameController.PlayerNumberNotFoundException;
import com.firebase.client.Firebase;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Implements drawing at 60fps from a separate thread
 * 
 */
public class SnakeSurfaceView extends SurfaceView implements Runnable {

	private static final String TAG = "IM SICK AND TIRED OF THESE MUTHA FUCKIN SNAKES ON THIS MUTHA FUCKIN PLANES";
    private volatile boolean running = false;

    private Thread thread = null;
    private SurfaceHolder surfaceHolder;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Firebase mFirebaseRoot;
        
    // Store an X,Y out-of-boundary defaults for each of the 4 players
    int x[] = new int[] {-1, -1, -1, -1};
    int y[] = new int[] {-1, -1, -1, -1};
    int canvasWidth = 1920;
    int canvasHeight = 1080;

    // Define a color for each player
    int colors[] = new int[] {Color.RED, Color.GREEN, Color.YELLOW, Color.MAGENTA};
    
    
    /*
     * Constructor
     */
    public SnakeSurfaceView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        // Create a reference to a Firebase location
        mFirebaseRoot = new Firebase("https://radiant-fire-9333.firebaseio.com/SnakesOnAPlane");
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
    
    public void update(){
        //Update the actors
    	for(Actor actor : GameBoard.Instance().listOfActors)
    	{
    		actor.update();
    	}
    	
        //Update the gameboard
    	GameBoard.Instance().updateOccupants();
    	
        // Iterate through all players
        for (int n = 0; n < GameController.MAX_PLAYERS; n++) {
            GameController gameController = null;
            try {
                gameController = GameController.getControllerByPlayer(n + 1);
            }
            catch (PlayerNumberNotFoundException e) {
            }

            // If the cursor went out of the screen boundaries
            if ((x[n] < 0) | (y[n] < 0) | (x[n] > canvasWidth) | (y[n] > canvasHeight)) {
                // Reset cursors proportionally on a horizontally centered line
                x[n] = canvasWidth / (GameController.MAX_PLAYERS + 1) * (n + 1);
                y[n] = canvasHeight / 2;
            }

            if (gameController != null) {
                // Move the cursor proportionally to the stick angle
                float deltaX = gameController.getAxisValue(GameController.AXIS_STICK_LEFT_X);
                float deltaY = gameController.getAxisValue(GameController.AXIS_STICK_LEFT_Y);
                
                // stick angle is greater than the center dead zone
                if ((deltaX * deltaX + deltaY * deltaY) > GameController.DEAD_ZONE * GameController.DEAD_ZONE) {
                    x[n] += Math.round(deltaX * 10);
                    y[n] += Math.round(deltaY * 10);
                }

                // Move the cursor function of the DPAD direction
                x[n] += gameController
                                .isButtonPressed(GameController.BUTTON_DPAD_RIGHT) ? +5
                                : 0;
                x[n] += gameController
                                .isButtonPressed(GameController.BUTTON_DPAD_LEFT) ? -5
                                : 0;
                y[n] += gameController
                                .isButtonPressed(GameController.BUTTON_DPAD_DOWN) ? +5
                                : 0;
                y[n] += gameController
                                .isButtonPressed(GameController.BUTTON_DPAD_UP) ? -5
                                : 0;
                final String xVal = Integer.toString(x[n]);
                final String yVal = Integer.toString(y[n]);
                //mFirebaseRoot.child(xVal + ":" + yVal).setValue(true);
            }
        }
    }
    
    public void draw()
    {
    	if (surfaceHolder.getSurface().isValid()) 
    	{
	        Canvas canvas = surfaceHolder.lockCanvas();
	        canvas.drawColor(Color.WHITE);
	        canvasWidth = canvas.getWidth();
	        canvasHeight = canvas.getHeight();
	        
	        paint.setStyle(Paint.Style.STROKE);
	        paint.setStrokeWidth(15);
	        
	        paint.setColor(Color.BLACK);
	        int xPos = 0;
	        int yPos = 0;
	        int gridWidth = canvasWidth/GameBoard.Instance().MAX_COLUMNS;
	        int gridHeight = canvasHeight/GameBoard.Instance().MAX_ROWS;
	        for(int i=0; i<GameBoard.Instance().gameBoardCells.length; i++)
	        {
	        	int posX = (i % GameBoard.Instance().MAX_COLUMNS) * gridWidth + gridWidth/2;
	        	int posY = ((int)i/GameBoard.Instance().MAX_COLUMNS) * gridHeight + gridHeight/2;
	        	if(GameBoard.Instance().gameBoardCells[i].getActors().size() > 0)
	        	{
	        		Actor actor = GameBoard.Instance().gameBoardCells[i].getActors().getFirst();
		        	switch(actor.getType())
		        	{
		        	case SNAKE:
		        		Snake snake = (Snake)actor;
		        		switch(snake.getPlayerNumber())
		        		{
		        		case 0:
		        			paint.setColor(Color.GREEN);
		        			break;
		        		case 1:
		        			paint.setColor(Color.BLUE);
		        			break;
		        		case 2:
		        			paint.setColor(Color.YELLOW);
		        			break;
		        		case 3:
		        			paint.setColor(Color.MAGENTA);
		        			break;
		        		case 4:
		        			paint.setColor(Color.CYAN);
		        			break;
		        		}
		        		break;
		        	case OBSTACLE:
		        		paint.setColor(Color.GRAY);
		        		break;
		        	case BULLET:
		        		paint.setColor(Color.BLACK);
		        		break;
		        	case FOOD:
		        		paint.setColor(Color.RED);
		        		break;
		        	default:
		        		paint.setColor(Color.WHITE);
		        		break;
		        	}
		        	canvas.drawPoint(posX, posY, paint);
	        	}
	        	else
	        	{
	        		paint.setColor(Color.BLACK);
	        	}
	        }
	        
	        paint.setStyle(Paint.Style.STROKE);
	        paint.setStrokeWidth(8);
	        
	        
	        for (int n = 0; n < GameController.MAX_PLAYERS; n++) {
	
		        // Draw a spot for that player
		        paint.setColor(colors[n]);
		        canvas.drawPoint(x[n], y[n], paint);
		        canvas.drawCircle(x[n], y[n], 20, paint);
	        }
	        
	        // Now pass over to the GPU for rendering.
	        GameController.startFrame();
	        surfaceHolder.unlockCanvasAndPost(canvas);
	    }
    }

    /*
     * This runs
     */
    @Override
    public void run() {
    	Random r = new Random();
    	for(int i=0; i< GameController.MAX_PLAYERS;i++)
    	{
    		GameBoard.Instance().getListOfActors().add(new PlayerSnake(GameBoard.Instance().getCell(i*5, i*5), i));
    	}
    	
    	for(int i=0; i< 20;i++)
    	{
	    	GameBoard.Instance().getListOfActors().add(new Food(GameBoard.Instance().getCell(r.nextInt(GameBoard.Instance().MAX_ROWS), r.nextInt(GameBoard.Instance().MAX_COLUMNS))));
    	}
    	
        while(running){
        	//TODO: menu logic goes around the below 2 call
        	update();
        	draw();
        	
            // Wait the equivalent of one frame at 60fps
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
