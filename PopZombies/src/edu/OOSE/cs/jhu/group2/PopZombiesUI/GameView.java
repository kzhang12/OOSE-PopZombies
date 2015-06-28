package edu.OOSE.cs.jhu.group2.PopZombiesUI;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import edu.OOSE.cs.jhu.group2.PopZombiesModel.Parachute;
import edu.OOSE.cs.jhu.group2.PopZombiesModel.PopZombieModel;
import edu.OOSE.cs.jhu.group2.PopZombiesModel.PopZombieModelListener;
import edu.OOSE.cs.jhu.group2.PopZombiesModel.Popcorn;
import edu.OOSE.cs.jhu.group2.PopZombiesModel.Position;
import edu.OOSE.cs.jhu.group2.PopZombiesModel.Zombie;
import edu.OOSE.cs.jhu.group2.popzombies.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * GameView class that is responsible for rendering the view of the game.
 * 
 * @author Connie Chang
 * @author Ted Staley
 * @author Elaine Chao 
 *
 */
public class GameView extends ImageView {
	/** 
	 * Paint object that will create the view
	 */
	private Paint paint = new Paint();
	/**
	 * Model used to control the logic of the game
	 */
	private PopZombieModel model;
	/**
	 * Game background
	 */
	private Background bg1, bg2;
	
	/** 
	 * Game backgrounds bitmap
	 */
	private Bitmap background, background2;
	
	/**
	 * Image of the popcorn
	 */
	private Bitmap popcornImageOriginal;
	
	/**
	 * Image of the microwave
	 */
	private Bitmap microwaveOriginal;
	
	/**
	 * Image of the popcorn in HUD
	 */
	private Bitmap HUDPopcorn;

	/**
	 * Image of the health in HUD
	 */
	private Bitmap HUDHealth;

	/**
	 * Image of parachute
	 */
	private Bitmap parachuteOriginal;
	
	/**
	 * Image of pause screen
	 */
	private Bitmap pausedOriginal;
	
	/**
	 * Image of the gameover screen
	 */
	private Bitmap gameover;
	/**
	 * Image that will flash when the level is over
	 */
	private Bitmap levelover;
	/**
	 * Picture of the zombie
	 */
	private Bitmap zombieImageOriginal;
	/**
	 * Signals if the game is over yet
	 */
	private Boolean endgame;
	/**
	 * Signals if the level is over yet
	 */
	private Boolean endlevel;
	
	/**
	 * Checks if device is vibrating
	 */
	private Boolean vibrating;
	
	/**
	 * Checks if game is paused
	 */
	private Boolean paused;
	
	/**
	 * Checks if game is in virtual reality mode
	 */
	private Boolean virtual;
	
	/**
	 * Signals if the player is under sttack
	 */
	private boolean attacking;
	/**
	 * Keeps track of the zombie's position and the screen to image ratio for the zombie
	 */
	private float popTheta, popZ, popR, zR, zombieAspectRatio;
	/**
	 * Keeps track of the zombie's depth
	 */
	private int zTheta, zZ;
	
	/**
	 * Keeps track of where the parachute is
	 */
	private float parachuteX, parachuteZ;
	
	/**
	 * Animations used to move the zombies
	 */
	private Movie movie, movie1;
	InputStream is = null, is1 = null;
	/**
	 * Tracks the length of the animation
	 */
	private long moviestart;
	
	/**The width of the screen.*/
	private int screenwidth;
	
	private float mapDegrees = 0;
	
	/**Conversion factor between pixel to degrees.*/
	private final float PIXEL_TO_DEGREES;
	
	/**
	 * Size of the parachute image
	 */
	private final int PARACHUTE_SIZE = 200;
	
	private Vibrator v;
	
	/**
	 * Array of zombie images
	 */
	private ArrayList<Integer> zombieImages;
	/**
	 * Current Zombie Image
	 */
	private Bitmap currentFrame;
	/**
	 * Tracks current frame of walking animation
	 */
	private int frameCount;

	/**
	 * Constructor for GameView object
	 * @param context to provide information about the android environment
	 */
	public GameView(Context context) {
		super(context);
		DisplayMetrics metrics = this.getResources().getDisplayMetrics(); //get dimensions of the screen
		this.screenwidth = metrics.widthPixels;
		//int height = metrics.heightPixels;
		setupZombieImages();
		this.PIXEL_TO_DEGREES = this.screenwidth/180;
	}
	
	/**
	 * Overloaded GameView object constructor
	 * @param context of the android environment
	 * @param attrs attributes of the environment
	 */
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		DisplayMetrics metrics = this.getResources().getDisplayMetrics(); //get dimensions of the screen
		this.screenwidth = metrics.widthPixels;
		//int height = metrics.heightPixels;
		setupZombieImages();
		this.PIXEL_TO_DEGREES = this.screenwidth/180;
	}
	
	/**
	 * Set up the map according to the accelerometer and compass.
	 * @param azimuthInDegrees degrees taken in as user input
	 */
	public void setMap(float azimuthInDegrees) {
		mapDegrees = azimuthInDegrees;
	}
	
	/**
	 * Get first background.
	 * @return first background
	 */
	public Background getBG1() {
		return this.bg1;
	}
	
	/**
	 * Get second background.
	 * @return second background
	 */
	public Background getBG2() {
		return this.bg2;
	}
	
	/**
	 * Initializes model to follow responses to change in game logic
	 * @param m the model used to keep track of logic of the game 
	 */
	public void setModel(PopZombieModel m) {
		this.model = m;
		this.model.setScreenSize(this.PIXEL_TO_DEGREES);
		
		this.endlevel = false;
		this.endgame = false;
		this.attacking = false;
		this.vibrating = false;
		this.paused = false;
		v = (Vibrator) super.getContext().getSystemService(Context.VIBRATOR_SERVICE);
		
		this.bg1 = new Background(0, 0);
		this.bg2 = new Background(180, 0);
	}
	
	/**
	 * Add all the zombie images to animate zombies
	 */
	public void setupZombieImages(){
		zombieImages = new ArrayList<Integer>();
		frameCount = 0;
		
		//I tried to make a for-loop. I really did.
		zombieImages.add(R.drawable.frame1);
		//zombieImages.add(BitmapFactory.decodeResource(getResources(), R.drawable.frame2));
		zombieImages.add(R.drawable.frame3);
		//zombieImages.add(BitmapFactory.decodeResource(getResources(), R.drawable.frame4));
		zombieImages.add(R.drawable.frame5);
		//zombieImages.add(BitmapFactory.decodeResource(getResources(), R.drawable.frame6));
		zombieImages.add(R.drawable.frame7);
		//zombieImages.add(BitmapFactory.decodeResource(getResources(), R.drawable.frame8));
		zombieImages.add(R.drawable.frame9);
		//zombieImages.add(BitmapFactory.decodeResource(getResources(), R.drawable.frame10));
		zombieImages.add(R.drawable.frame11);
		//zombieImages.add(BitmapFactory.decodeResource(getResources(), R.drawable.frame12));
		zombieImages.add(R.drawable.frame13);
		//zombieImages.add(BitmapFactory.decodeResource(getResources(), R.drawable.frame14));
		zombieImages.add(R.drawable.frame15);
		//zombieImages.add(BitmapFactory.decodeResource(getResources(), R.drawable.frame16));
		zombieImages.add(R.drawable.frame17);
		//zombieImages.add(BitmapFactory.decodeResource(getResources(), R.drawable.frame18));
		zombieImages.add(R.drawable.frame19);
		//zombieImages.add(BitmapFactory.decodeResource(getResources(), R.drawable.frame20));
		zombieImages.add(R.drawable.frame21);
		//zombieImages.add(BitmapFactory.decodeResource(getResources(), R.drawable.frame22));
		zombieImages.add(R.drawable.frame23);
		//zombieImages.add(BitmapFactory.decodeResource(getResources(), R.drawable.frame24));
		//*/
	}

	
	/**
	 * Flash a response when the user provides input in the screen 
	 * @param e the event to indicate user input
	 * @return true if the user tapped the screen
	 */
	@Override
	public boolean onTouchEvent(MotionEvent e) {
	    // MotionEvent reports input details from the touch screen
	    // and other input controls. In this case, you are only
	    // interested in events where the touch position changed.

		if (this.model.stateAllowTouch()) {
		
			int microwavebegx = (this.screenwidth / 2) - (this.microwaveOriginal.getWidth() / 2);
			int microwaveendx = (this.screenwidth / 2) + (this.microwaveOriginal.getWidth() / 2);
			int microwavebegy = this.getHeight() - this.microwaveOriginal.getHeight();
		
			if (e.getAction() == MotionEvent.ACTION_UP) {
	        	float x = e.getX();
	    	    float y = e.getY();
	    	    
                if (!virtual) {
	    	    	
	    	    	DisplayMetrics metrics = this.getResources().getDisplayMetrics(); //get dimensions of the screen
		    		int width = metrics.widthPixels;
		    	    int moveRightRange = this.getWidth() - 100;
		    	    int moveLeftRange = 100;
	    	    	
		    	    if (x > moveRightRange) {
		    	    	//bg1.updateRight();
		    	    	//bg2.updateRight();
		    	    	//this.model.screenMovesRight();
			    	    this.mapDegrees += 10;
		    	    	bg1.move(10);
		    	    	bg2.move(10);
		    	    	this.model.playerMoves(10);
		    	    	return true;
		    	    } else if (x < moveLeftRange) {
		    	    //	bg1.updateLeft();
		    	    //	bg2.updateLeft();
		    	    //	this.model.screenMovesLeft();
			    	    this.mapDegrees -= 10;
		    	    	bg1.move(-10);
		    	    	bg2.move(-10);
		    	    	this.model.playerMoves(-10);
		    	    	return true;
		    	    } 
	    	    }
	    	    
	    	    if (x >= microwavebegx && x <= microwaveendx && y >= microwavebegy) {
	    	    	this.model.microwavePopcorn();
	    	    } 
	    	    
	    	    
	    	    
			    else if  (x >= this.parachuteX && x <= this.parachuteX + PARACHUTE_SIZE && 
    	    		y >= this.parachuteZ && y <= this.parachuteZ + PARACHUTE_SIZE) {
	    	    	this.model.collectParachute();
	    	    }  
	    	    else {
	    	    	this.model.throwPopcorn(new Position(x*(1/this.PIXEL_TO_DEGREES), 0, y));
	    	    }

			}
		}
	    return true;
	}
	
	/**
	 * Indicates when the game is over and paint has to render the game over image
	 */
	public void drawGameOver() {
		this.endgame = true;
	}
	
	/**
	 * Indicates when the level is over and paint has to render the level over image
	 */
	public void endLevel() {
		this.endlevel = true;
	}
	
	/**
	 * Indicates when the level is starting and paint has to render the beginning of the level image
	 */
	public void startLevel() {
		this.endlevel = false;
	}
	
	/**
	 * Indicates when game is paused
	 */
	public void pauseGame() {
		this.paused = true;
	}
	
	/**
	 * Indicates when game has been unpaused
	 */
	public void unpauseGame() {
		this.paused = false;
	}
	
	/**
	 * Indicates whether the game will be played with the accelerometer or on touch
	 * @param b whether the game is virtual or regular
	 */
	public void isVirtual(Boolean b) {
		this.virtual = b;
	}

	/**
	 * Converts degrees to pixels
	 * @param degree to be converted to pixels
	 * @return number of pixels that corresponds to the degree
	 */
	private float degreeToPixel(float degree) {
		return degree*this.PIXEL_TO_DEGREES;
	}
	
	@SuppressLint("DrawAllocation")
	@Override
	public void onDraw(Canvas canvas) {
		
		if (this.model != null) {
			drawBackground(canvas);
			drawZombies(canvas);
			drawParachute(canvas);
	    	drawPopcorn(canvas);
			drawGameOver(canvas);
			drawText(canvas);
			drawHUD(canvas);
			drawAttacked(canvas);
			drawMiniMap(canvas);
			drawLevelUp(canvas);
			drawMicrowave(canvas);
			drawPause(canvas);
		}
	}

	/**
	 * Draw the pause button.
	 * @param canvas the canvas that it draws on
	 */
    private void drawPause(Canvas canvas) {
        if  (this.paused) {
        	if (this.pausedOriginal == null) {
        		this.pausedOriginal = BitmapFactory.decodeResource(getResources(), R.drawable.pause);
        	}	
        	Bitmap pauseimage = Bitmap.createScaledBitmap(this.pausedOriginal, this.getWidth(), this.getHeight(), false);	
        	canvas.drawBitmap(pauseimage, 0, 0, null);
        }
    }

	/**
	 * Draw the microwave.
	 * @param canvas the canvas that it draws on
	 */
    private void drawMicrowave(Canvas canvas) {
        if (this.microwaveOriginal == null) {
        	this.microwaveOriginal = BitmapFactory.decodeResource(getResources(), R.drawable.microwave);
        }
        
        int mheight = this.microwaveOriginal.getHeight();
        int mwidth = this.microwaveOriginal.getWidth();	
        canvas.drawBitmap(this.microwaveOriginal, (this.getWidth() - mwidth) / 2, this.getHeight() - mheight, null);
    }

	/**
	 * Draw the level up text.
	 * @param canvas the canvas that it draws on
	 */
    private void drawLevelUp(Canvas canvas) {
        if (this.endlevel) {
        	Paint p = new Paint(); 
        	p.setColor(Color.WHITE); 
        	p.setTextSize(60); 
        	p.setTextAlign(Align.CENTER);
        	canvas.drawText("Level " + this.model.getCurrentLevel(),this.screenwidth / 2, 100, p);
        }
    }

	/**
	 * Draw the mini-map.
	 * @param canvas the canvas that it draws on
	 */
    private void drawMiniMap(Canvas canvas) {
        Paint mapFront = new Paint();
        mapFront.setColor(Color.argb(200, 115, 250, 100));
        Paint mapBack = new Paint();
        mapBack.setColor(Color.argb(100, 0, 255, 0));
        canvas.drawCircle(100, 100, 100, mapBack);
        RectF mapOval = new RectF(0, 0, 200, 200);
        canvas.drawArc(mapOval, this.mapDegrees+90, 180, true, mapFront);
    }

	/**
	 * Draw the screen and vibrate when being attacked.
	 * @param canvas the canvas that it draws on
	 */
    private void drawAttacked(Canvas canvas) {
        if (this.model.beingAttacked()) {
            if (!this.endgame) {
        		long[] pattern = {0, 100, 1000};
                v.vibrate(pattern, 0);
                this.vibrating = true;
            }
            canvas.drawARGB(100, 255, 0, 0);
        } else if (this.vibrating && !this.attacking) {
            v.cancel();
            this.vibrating = false;
        }
    }

	/**
	 * Draw the HUD.
	 * @param canvas the canvas that it draws on
	 */
    private void drawHUD(Canvas canvas) {
        Paint healthBarFront = new Paint();
        healthBarFront.setColor(Color.argb(200, 255, 0, 0));
        Paint healthBarBack = new Paint();
        healthBarBack.setColor(Color.argb(200, 255, 255, 0));
        Paint ammoBarFront = new Paint();
        ammoBarFront.setColor(Color.argb(200, 0, 0, 255));
        Paint ammoBarBack = new Paint();
        ammoBarBack.setColor(Color.argb(200, 0, 255, 255));
        RectF healthOval = new RectF(-200, this.getHeight() - 200, 200, this.getHeight() + 200);
        canvas.drawArc(healthOval, 270, 90, true, healthBarBack);
        if(this.model.getPlayerCurrentHealth()<=0){
        	canvas.drawArc(healthOval, 270, 0, true, healthBarFront);
        }else{
        	canvas.drawArc(healthOval, 270 + 90*(1000 - this.model.getPlayerCurrentHealth())/1000, 90, true, healthBarFront);
        }
        
        RectF ammoOval = new RectF(this.getWidth() - 200, this.getHeight() - 200, this.getWidth() + 200, this.getHeight() + 200);
        canvas.drawArc(ammoOval, 180, 90, true, ammoBarBack);
        canvas.drawArc(ammoOval, 180, 90*this.model.getPlayerAmmo()/50, true, ammoBarFront);
        
        if (this.HUDHealth == null) {
        	this.HUDHealth= BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        }
        
        this.HUDHealth = Bitmap.createScaledBitmap(this.HUDHealth, 100, 100, false);
        int hheight = this.HUDHealth.getHeight();
        canvas.drawBitmap(this.HUDHealth, 0, this.getHeight() - hheight, null);

        if (this.HUDPopcorn == null) {
        	this.HUDPopcorn= BitmapFactory.decodeResource(getResources(), R.drawable.popcorn);
        }
        
        this.HUDPopcorn = Bitmap.createScaledBitmap(this.HUDPopcorn, 100, 100, false);
        int pheight = this.HUDPopcorn.getHeight();
        int pwidth = this.HUDPopcorn.getWidth();	
        canvas.drawBitmap(this.HUDPopcorn, (this.getWidth() - pwidth), this.getHeight() - pheight, null);
    }

	/**
	 * Draw the text attributes.
	 * @param canvas the canvas that it draws on
	 */
    private void drawText(Canvas canvas) {
        Paint paint = new Paint(); 
        paint.setColor(Color.WHITE); 
        paint.setTextSize(30); 
        canvas.drawText("Score: " + this.model.getCurrentScore(), this.getWidth()/2 - 10, 50, paint);
        canvas.drawText("Ammo: " + this.model.getPlayerAmmo(), this.getWidth()/2 + 250, this.getHeight() - 20, paint);
        canvas.drawText("Supply: " + this.model.getPlayerSupply(), this.getWidth()/2 + 250, this.getHeight() - 50, paint);
        canvas.drawText("Your Health: " + this.model.getPlayerTotalHealth(), 250, this.getHeight() - 20, paint);
        canvas.drawText("Level " + this.model.getCurrentLevel(), 250, this.getHeight() - 50, paint);
    }

	/**
	 * Draw the game over text.
	 * @param canvas the canvas that it draws on
	 */
    private void drawGameOver(Canvas canvas) {
        if (this.endgame) {
        	
        	Paint p = new Paint(); 
        	p.setColor(Color.WHITE); 
        	p.setTextSize(60); 
        	p.setTextAlign(Align.CENTER);
        	canvas.drawText("GAME OVER",this.screenwidth / 2, 100, p);

        	v.cancel();
        }
    }

	/**
	 * Draw the pause popcorn.
	 * @param canvas the canvas that it draws on
	 */
    private void drawPopcorn(Canvas canvas) {
        Iterator<Popcorn> j = this.model.getCopyOfPopcorns().iterator();
        while(j.hasNext()) {
        	Popcorn pop = j.next();
        	Position pos = pop.getPosition();
        	popTheta = pos.getTheta();
        	popZ = pos.getZ();
        	popR = pos.getR();

        	if (this.popcornImageOriginal == null) {
        		this.popcornImageOriginal = BitmapFactory.decodeResource(getResources(), R.drawable.popcorn);
        	}
        	
        	int height = (int)(this.model.getPopcornSize()*(1.0-((double)popR/400.0)));
        	int width = (int)(this.model.getPopcornSize()*(1.0-((double)popR/400.0)));
        	
        	if (height <= 0 || width <= 0) {
        		height = 1;
        		width = 1;
        	}
        	
        	Bitmap popcornImage = Bitmap.createScaledBitmap(this.popcornImageOriginal, height, width, false);
        	height = popcornImage.getHeight();
        	width = popcornImage.getWidth();	
        	canvas.drawBitmap(popcornImage, this.degreeToPixel(popTheta)-(width/2), popZ-(height/2), null);
        	
        }
    }

	/**
	 * Draw the parachute.
	 * @param canvas the canvas that it draws on
	 */
    private void drawParachute(Canvas canvas) {
        //if there is an active parachute, draw it
        if (this.model.getActiveParachute()) {
        	
        	Parachute par = this.model.getParachute();
        	
        	if (this.parachuteOriginal == null) {
        		this.parachuteOriginal = BitmapFactory.decodeResource(getResources(), R.drawable.parachute);
        	}
        	
        	Position parpos = par.getPosition();
        	this.parachuteX = degreeToPixel(parpos.getTheta());
        	this.parachuteZ = parpos.getZ();
        	
        	Bitmap parImage = Bitmap.createScaledBitmap(this.parachuteOriginal, PARACHUTE_SIZE, PARACHUTE_SIZE, false);

        	canvas.drawBitmap(parImage, this.parachuteX, this.parachuteZ, null);
        	
        }
    }

	/**
	 * Draw the zombies.
	 * @param canvas the canvas that it draws on
	 */
    private void drawZombies(Canvas canvas) {
        //initialize zombie image if not already
        if (this.zombieImageOriginal == null) {
        	this.zombieImageOriginal = BitmapFactory.decodeResource(getResources(), R.drawable.zombie);
        	zombieAspectRatio = this.zombieImageOriginal.getHeight()/this.zombieImageOriginal.getWidth();
        }
        
        Paint zombieMap = new Paint();
        zombieMap.setColor(Color.argb(200, 255, 255, 0));
        //draw zombies
        
        frameCount++;
        if(frameCount>=zombieImages.size()){
        	frameCount = 0;
        }
        if(currentFrame!=null){
        	currentFrame.recycle();
        }
        
        currentFrame = BitmapFactory.decodeResource(getResources(), zombieImages.get(frameCount));
        
        Iterator<Zombie> i = this.model.getCopyOfZombies().iterator();
        while(i.hasNext()) {
        	Zombie z = i.next();
        	Position zpos = z.getPosition();
        	zTheta = (int) zpos.getTheta();
        	zZ = (int) zpos.getZ();
        	zR = zpos.getR();
        	float zx = (float) (zR/5*Math.cos(zTheta));
        	float zy = (float) (zR/5*Math.sin(zTheta));
        	//TODO
        	System.out.println("x coordinate: " + zx + " y coordinate: " + zy);
        	canvas.drawCircle(100 + zx, 100 + zy, 10, zombieMap);
        	
        	double percentTotalTravelCompleted = (this.model.getZombieInitialDepth()-zR)/(this.model.getZombieInitialDepth() - this.model.getZombieFinalDepth());
        	double scalingFactor = ((this.model.getZombieFinalScale()-this.model.getZombieInitialScale())*percentTotalTravelCompleted)+this.model.getZombieInitialScale();
        	
        	int zheight = (int) (this.model.getZombieSize()*scalingFactor);
        	int zwidth = (int) (int) (this.model.getZombieSize()*scalingFactor);
        	
        	Bitmap zImage = Bitmap.createScaledBitmap(this.currentFrame, zwidth, zheight, false);
        	zheight = zImage.getHeight();
        	zwidth = zImage.getWidth();	
        	canvas.drawBitmap(zImage, this.degreeToPixel(zTheta), zZ, null);
        	
        		//draw the health bar background
        		Paint healthbgPaint = new Paint();
        		healthbgPaint.setColor(Color.RED);
        		int startx = (int)this.degreeToPixel(zTheta) + (zwidth/4);
        		canvas.drawRect(startx, zZ-30, startx+(zwidth/2), zZ-15, healthbgPaint);
        	
        	
        		//draw the health bar HP
        		Paint healthPaint = new Paint();
        		healthPaint.setColor(Color.GREEN);
        		canvas.drawRect(startx, zZ-30, startx+(int)(((double)z.getCurrentHealth()/(double)z.getTotalHealth())*((double)zwidth/2.0)), zZ-15, healthPaint);
        	
        }
    }

	/**
	 * Draw the background images.
	 * @param canvas the canvas that it draws on
	 */
    private void drawBackground(Canvas canvas) {
        if (this.background == null) {
        	this.background = BitmapFactory.decodeResource(getResources(), R.drawable.iter2game);
        }	
        Bitmap backgroundimage = Bitmap.createScaledBitmap(this.background, this.getWidth(), this.getHeight(), false);	
        canvas.drawBitmap(backgroundimage, this.degreeToPixel(bg1.getbgTheta()),  bg1.getbgZ(), null);
        
        if (this.background2 == null) {
        	this.background2 = BitmapFactory.decodeResource(getResources(), R.drawable.bg2);
        }
        
        Bitmap backgroundimage2 = Bitmap.createScaledBitmap(this.background2, this.getWidth(), this.getHeight(), false);
        canvas.drawBitmap(backgroundimage2, this.degreeToPixel(bg2.getbgTheta()), bg2.getbgZ(), null);
    }
	
    /**
     * Initiates drawing the canvas.
     */
    public void startDraw() {
	    this.postInvalidate();
	}
}
