package edu.OOSE.cs.jhu.group2.PopZombiesUI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import edu.OOSE.cs.jhu.group2.popzombies.R;

/**
 * Main menu that will provide user options to start game or read instructions
 * 
 * @author Connie Chang
 * @author Elaine Chao
 * @author Stephanie Chew
 * @author Ted Staley
 * @author Kevin Zhang
 *
 */
public class MainActivity extends Activity {
    
    //private PopZombieModel model;
	/**
	 * Allows music to be played while user is selecting option
	 */
	private MediaPlayer music;

	/**
	 * Manager for calling sensor information
	 */
	private SensorManager manager;

	/**
	 * Calls in sensor information.
	 */
	private Sensor sensor; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        music = MediaPlayer.create(MainActivity.this, R.raw.poketcg);
        music.setVolume((float)0.3, (float)0.3);
        music.setLooping(true);
        music.start();
        //this.model = new PopZombieModel();
        //final Button playButton = (Button) findViewByID(R.id.button1);
        
        this.manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        
        // Start up android sensor
     //   this.sensor = this.manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
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
    	music.stop();

    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	music.start();

    }
        
    /**
     * Allows for user to start playing the game in virtual reality mode
     * @param view to change to start of game
     */
    public void playVirtual(View view) {
    	//Do something in response to button
    	Intent intent = new Intent(this, GameActivity.class);
    	//intent.putExtra("model", this.model);
    	//EditText editText = (EditText) findViewById(R.id.edit_message);
    	//String message = editText.getText().toString();
    	//intent.putExtra(EXTRA_MESSAGE, message);
    	intent.putExtra("isvirtual", true);
    	startActivity(intent);
    	finish();
    }
    
    public void play(View view) {
    	//Do something in response to button
    	Intent intent = new Intent(this, GameActivity.class);
    	intent.putExtra("isvirtual", false);
    	startActivity(intent);
    	finish();
    }
    
    /**
     * Allows user to go to view instructions
     * @param view of the instructions
     */
    public void instructions(View view) {
    	Intent intent = new Intent(this, InstructionsActivity.class);
    	//Intent intent = new Intent(this, SensorActivity.class);
    	startActivity(intent);
    	finish();
    }
}