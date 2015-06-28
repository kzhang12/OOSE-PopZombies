package edu.OOSE.cs.jhu.group2.PopZombiesUI;

import edu.OOSE.cs.jhu.group2.PopZombiesModel.PopZombieModel;
import edu.OOSE.cs.jhu.group2.PopZombiesModel.PopZombieModelListener;
import edu.OOSE.cs.jhu.group2.popzombies.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Generates the view for the game
 * 
 * @author Connie Chang
 * @author Ted Staley
 * @author Elaine Chao
 * @author Stephanie Chew
 * @author Kevin Zhang
 *
 */
public class GameActivity extends Activity {
    /**
     * Model to keep track of game logic.
     */
    private PopZombieModel model;
    /**
     * Generates the game view.
     */
    private GameView view;
    /**
     * Plays music on menus and during gameplay.
     */
    private MediaPlayer music;
    
    /**Retrieves information from sensors.*/
    private SensorManager mSensorManager;
    
    /**Retrieves information from the accelerometer.*/
    private Sensor mAccelerometer;
    
    /**Retrieves information from the compass.*/
    private Sensor mMagnetometer;
    
    /**Listens for when the compass changes direction.*/
    private SensorEventListener compassListener;

    private Boolean virtual;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_screen);

        this.music = MediaPlayer.create(GameActivity.this, R.raw.hansend);
        this.music.setVolume(1, 1);
        this.music.setLooping(true);
        this.music.start();
        
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        
        this.view = (GameView) findViewById(R.id.background);
        this.model = new PopZombieModel(metrics.heightPixels);
        this.view.setModel(this.model);

        Bundle e = getIntent().getExtras();
        virtual = false;
        if (e != null) {
            virtual = e.getBoolean("isvirtual");
            System.out.println(virtual);
        }
        
        this.view.isVirtual(virtual);
        
        if (virtual) {
	        this.mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	        this.mAccelerometer = this.mSensorManager.getDefaultSensor(
	                Sensor.TYPE_ACCELEROMETER);
	        this.mMagnetometer = this.mSensorManager.getDefaultSensor(
	                Sensor.TYPE_MAGNETIC_FIELD);
	        
	        this.compassListener = new SensorEventListener() {
	            
	            private final int numTHREE = 3;
	            private final int numNINE = 9;
	            private final int num360 = 360;
	
	            private float[] mLastAccelerometer = new float[numTHREE];
	            private float[] mLastMagnetometer = new float[numTHREE];
	            private boolean mLastAccelerometerSet = false;
	            private boolean mLastMagnetometerSet = false;
	            private float[] mR = new float[numNINE];
	            private float[] outR = new float[numNINE];
	            private float[] mOrientation = new float[numTHREE];
	            private float mCurrentDegree = 0f;
	
	            @Override
	            public void onSensorChanged(SensorEvent event) {
	                if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
	                    System.arraycopy(event.values, 0, 
	                            mLastAccelerometer, 0, event.values.length);
	                    mLastAccelerometerSet = true;
	                } else if (event.sensor.getType() 
	                        == Sensor.TYPE_MAGNETIC_FIELD) {
	                    System.arraycopy(event.values, 0, 
	                            mLastMagnetometer, 0, event.values.length);
	                    mLastMagnetometerSet = true;
	                }
	                    
	                if (mLastAccelerometerSet && mLastMagnetometerSet) {
	                    SensorManager.getRotationMatrix(mR, null, 
	                            mLastAccelerometer, mLastMagnetometer);
	                    SensorManager.remapCoordinateSystem(mR, 
	                            SensorManager.AXIS_X, 
	                            SensorManager.AXIS_Z, outR);
	                    SensorManager.getOrientation(outR, mOrientation);
	                    float azimuthInRadians = mOrientation[0];
	                    float azimuthInDegrees = (float) 
	                            (Math.toDegrees(azimuthInRadians) + num360) 
	                            % num360;
	                    float delta = azimuthInDegrees - mCurrentDegree;
	                    if (Math.abs(delta) > numTHREE) {
	                        if (GameActivity.this.model.stateAllowTouch()) {
	                            GameActivity.this.model.playerMoves(delta);
	                            GameActivity.this.view.getBG1().move(delta);
	                            GameActivity.this.view.getBG2().move(delta);
	                            mCurrentDegree = azimuthInDegrees;
	                        }
	                    }
	                    GameActivity.this.view.setMap(azimuthInDegrees);
	                }
            	
	            }
	            @Override
	            public void onAccuracyChanged(Sensor sensor, int accuracy) {
	                // TODO Auto-generated method stub
	            }

	        };
        }
        
        this.model.addListener(this.view, new PopZombieModelListener() {

            @Override
            public void levelEnded() {
                view.endLevel();
            }

            @Override
            public void gameEnded() {
                view.drawGameOver();
            }

            @Override
            public void levelStarted() {
                view.startLevel();
            }
            
            @Override
            public void outOfAmmo(String s) {
                //TODO print out given string
            }
            
            @Override
            public void draw() {
                view.startDraw();
            }

			@Override
			public void gamePaused() {
				view.pauseGame();
			}

			@Override
			public void gameUnpaused() {
				view.unpauseGame();
			}
            
        });
        //this.model.setupLevel();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onPause() {
        super.onPause();
        this.music.stop();
        
        if (virtual) {
	        this.mSensorManager.unregisterListener(
	                this.compassListener, this.mAccelerometer);
	        this.mSensorManager.unregisterListener(
	                this.compassListener, this.mMagnetometer);
        } 
	   this.pause(this.view);
   
    }
    
    @Override
    public void onResume() {
        super.onResume();
        this.music.start();
        if (virtual) {
	        this.mSensorManager.registerListener(
	                this.compassListener, this.mAccelerometer, 
	                SensorManager.SENSOR_DELAY_GAME);
	        this.mSensorManager.registerListener(
	                this.compassListener, this.mMagnetometer, 
	                SensorManager.SENSOR_DELAY_GAME);
        }
    }
    
    /**
     * Allows user to go back to main menu.
     * @param v to change
     */
    public void backToMenu(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    
    /**
     * Allows user to go to the pause menu.
     * @param v to change
     */
    public void pause(View v) {
        //Intent intent = new Intent(this, PauseActivity.class);
        //startActivity(intent);
        this.model.pause();
        Button unpause = (Button) findViewById(R.id.button1);
        Button backToMenu = (Button) findViewById(R.id.button2);
        unpause.setVisibility(View.VISIBLE);
        backToMenu.setVisibility(View.VISIBLE);
        Button pause = (Button) findViewById(R.id.button3);
        pause.setVisibility(View.INVISIBLE);
    }
    
    /**Unpauses the game.
     * 
     * @param v The view where the method was called
     */
    public void unpause(View v) {
        this.model.unpause();
        Button unpause = (Button) findViewById(R.id.button1);
        Button backToMenu = (Button) findViewById(R.id.button2);
        unpause.setVisibility(View.INVISIBLE);
        backToMenu.setVisibility(View.INVISIBLE);
        Button pause = (Button) findViewById(R.id.button3);
        pause.setVisibility(View.VISIBLE);
    }
}
