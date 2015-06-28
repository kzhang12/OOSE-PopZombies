package edu.OOSE.cs.jhu.group2.PopZombiesModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import edu.OOSE.cs.jhu.group2.PopZombiesUI.GameView;

/**
 * Model for implementing the logic of Pop Zombies. 
 * On each level, player tries to eliminate all zombies with popcorn.
 * 
 * @author Connie Chang
 * @author Elaine Chao
 * @author Stephanie Chew
 * @author Ted Staley
 * @author Kevin Zhang
 */
public class PopZombieModel {
    
    /**Current state of game.*/
    private GameState state;
    
    /** State of game between levels */
    private levelUpState levelupstate;
    
    /** State of game during game play */
    private gameGoingState gamegoingstate;
    
    /** State of game when paused */
    private pauseState pausestate;

    /**
     * Speed that refreshes the frame in seconds.
     */
    private final int FRAME_RATE;
    /**
     * Time lag between each parachute launch, in seconds.
     */
    private final double DELAY_BETWEEN_PARACHUTES;
    /**
     * Number of kernels each parachute supplies.
     */
    private final int KERNELS_PER_PARACHUTE;
    /**
     * Time lag in between each level before the next level begins, in seconds.
     */
    private final double TIME_BETWEEN_LEVELS;
    /**
     * Initial number of kernels per parachute is 25
     */
    final int KERNELS_PER_MICROWAVE_BATCH = 25; //microwave 25 kernels at a time    
    /**
     * Height of the user's screen.
     */
    private final int GAME_HEIGHT; //this should match the screen height
    /**
     * Depth of the room.
     */
    private final int GAME_DEPTH;
    /**The horizon height.*/
    private final int HORIZON;
    /**
     * Constant to assist image scaling.
     */
    private final int POPCORN_SIZE;
    /**
     * Gives zombie's depth in the screen.
     */
    private final int ZOMBIE_INITIAL_DEPTH;
    /**
     * Zombie's attack range.
     */
    private final int ZOMBIE_FINAL_DEPTH;
    /**
     * Assist zombie image scaling.
     */
    private final double ZOMBIE_INITIAL_SCALE;
    /** 
     * Size of the zombie image.
     */
    private final int ZOMBIE_SIZE;
    /**
     * Scale's zombie attack range.
     */
    private final double ZOMBIE_FINAL_SCALE;
    /**
     * Zombie's beginning Y.
     */
    private final int ZOMBIE_START_Y;
    /**
     * Countdown to the next parachute launch.
     */
    private int parachuteDelay;
    /**
     * Countdown til the next level begins.
     */
    private int nextLevelDelay;
    /**
     * Number of zombies in room limit.
     */
    private int zombiesInRoomCap;
    /**
     * Number of zombies left in level that need to be killed.
     */
    private int totalZombiesRemainingInLevel;
    /**
     * Keeps track of whether the game is currently in between levels or not.
     */
    private boolean betweenLevels;
    /**
     * Keeps track of whether or not a parachute has been sent out.
     */
    private boolean activeParachute;
    /**
     * Parachute that the player taps on to collect ammo.
     */
    private Parachute parachute;
    /**
     * Player that user uses to interact with game.
     */
    private Player player;
    /**
     * List of popcorn kernels player has sent out on the screen.
     */
    private List<Popcorn> popcorns;
    /**
     * List of zombies currently in the room.
     */
    private List<Zombie> zombies;
    /**
     * Current level the player is on.
     */
    private int level;
    /**
     * Player's current point score.
     */
    private int currentScore;
    
    /**
     * Player's Maximum Score.
     */
    private int maxScore;
    
    /**
     * Player is being attacked.
     */
    private boolean beingAttacked;

    /**
     * List of model listeners to interact between UI and model.
     */
    private List<PopZombieModelListener> listeners;
    
    /**Conversion factor between pixel to degrees.*/
    private float PIXEL_TO_DEGREES;
    
    /**Timer to constantly call gameLoop.*/
    private Timer timer;
    
    /**The time that the last gameLoop call started.*/
    private float previousTime;
    
    /**
     * Constructor for model -- also sets up the level.
     * 
     * @param int the height of the device's screen
     */
    public PopZombieModel(int screenHeight) {
        this.GAME_HEIGHT = screenHeight;
        this.KERNELS_PER_PARACHUTE = 40;
        this.parachute = new Parachute(new Position(-100,0,0), KERNELS_PER_PARACHUTE);
        this.player = new Player(1000);
        this.popcorns = new ArrayList<Popcorn>(); //popcorn that is being thrown (in mid-air)
        this.zombies = new ArrayList<Zombie>(); //zombies active in the game
        this.listeners = new ArrayList<PopZombieModelListener>(); //list of model listeners
        this.level = 1;
        this.currentScore = 0;
        this.totalZombiesRemainingInLevel = 1;
        this.activeParachute = false;
        this.betweenLevels = true;
        this.zombiesInRoomCap =  1;
        this.parachuteDelay = 0;
        this.nextLevelDelay = 0;
        this.HORIZON = Math.round(this.GAME_HEIGHT*((float)1/3));
        this.ZOMBIE_SIZE = 500;
        this.GAME_DEPTH = 5000;
        this.POPCORN_SIZE = 150;
        this.TIME_BETWEEN_LEVELS = 3000; //milliseconds
        this.DELAY_BETWEEN_PARACHUTES = 6000; //milliseconds
        this.FRAME_RATE = 30;
        this.ZOMBIE_INITIAL_DEPTH = 500;
        this.ZOMBIE_FINAL_DEPTH = 50;
        this.ZOMBIE_INITIAL_SCALE = 0.20;
        this.ZOMBIE_FINAL_SCALE = 1.0;
        this.ZOMBIE_START_Y = 100;
        this.beingAttacked = false;
        this.previousTime = System.nanoTime() / 1000000;
        
        this.setupLevel();
        
        this.state = new gameGoingState();
        this.levelupstate = new levelUpState();
        this.gamegoingstate = new gameGoingState();
        this.pausestate = new pauseState();
        
        this.timer = new Timer(true);
        this.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run(){
                float currentTime = System.nanoTime() / 1000000;
                state.gameLoop(currentTime - previousTime);
                previousTime = currentTime;
            }
        }, 0, 1000/FRAME_RATE);
    }
    
    /**
     * Sets the size of the device's screen.
     * @param p to scale the screen size and convert from pixels to degrees
     */
    public void setScreenSize(float p) {
        this.PIXEL_TO_DEGREES = p;
    }
    
    /**
     * Checks to see if a zombie is near enough to attack a player.
     * @return true if the player is being attacked
     */
    public boolean beingAttacked() {
        return this.beingAttacked;
    }
    
    /**
     * Generates a random integer within the bounds of the screen.
     * @return integer that will be used for positioning
     */
    private int getRandomThetaInRoom() {
        Random rand = new Random();
        return rand.nextInt(360) + 1;
    }

    /**
     * Sets up the level by increasing the number of zombies
     * according to the level difficulty.
     * @param level the player is currently on
     */
    private void setupLevel() {
        // only setup each level one time
        if(!betweenLevels) {
            return;
        } else {
            betweenLevels = false;
        }

        // increase max zombies that can exist at once
        zombiesInRoomCap = 1 + this.level;

        // increase number of zombies for the level
        totalZombiesRemainingInLevel = this.level*3 + 1;

        // setup zombies to be introduced based on current level
        for(int i = 0; i<zombiesInRoomCap; i++) {
            addZombieToGame();
        }
    }
    
    /**
     * Get copy of the zombie list
     * @return a copy of this.zombies
     */
    public List<Zombie> getCopyOfZombies() {
        List<Zombie> zombiesCopy = new ArrayList<Zombie>();
        for(Zombie z : this.zombies) {
            zombiesCopy.add(z);
        }
        Collections.reverse(zombiesCopy); //so the older zombies get drawn last
        return zombiesCopy;
    }
    
    /**
     * Get copy of the popcorn list
     * @return a copy of this.popcorns
     */
    public List<Popcorn> getCopyOfPopcorns() {
        List<Popcorn> popcornCopy = new ArrayList<Popcorn>();
        for(Popcorn p : this.popcorns) {
            popcornCopy.add(p);
        }
        return popcornCopy;
    }
    
    /**
     * Checks to see if player has defeated all the zombies in the level.
     * @return true if the level is over
     */
    public boolean checkEndLevel() {
        if (this.zombies.size() == 0 && this.player.getCurrentHealth() > 0) {
            return true;
        }
        return false;
    }
    
    /**
     * Updates all the listeners that the level has ended and advance game onto next level.
     */
    private void endLevel() {
        this.advanceLevel();
        
        for(PopZombieModelListener l : this.listeners) {
            l.levelEnded();
        }
    }
    
    /**
     * Sets up the new level, and starts it.
     */
    private void startLevel() {
        setupLevel();
        for(PopZombieModelListener l : this.listeners) {
            l.levelStarted();
        }
    }

    /** 
     * Updates location of parachute downwards, zombies closer, and popcorn further.
     * @param deltaTime The amount of time changed since this method was last called
     */
    private void moveEntities(float deltaTime) {
        // move parachute, if active
        if (this.activeParachute) {
            this.parachute.update(deltaTime);
        }

        // move all zombies
        for (Zombie z : this.zombies) {
            z.update(deltaTime);
        }
    
        // move all popcorns that are being thrown
        for (Popcorn p : this.popcorns) {
            p.update(deltaTime);
        }
    }
    
    /**
     * Create a zombie to add to the game.
     * @return z the zombie that was created
     */
    public Zombie addZombieToGame() {
        Zombie z = new Zombie(new Position(
                this.getRandomThetaInRoom(), ZOMBIE_INITIAL_DEPTH, this.HORIZON), 50
                );
        this.zombies.add(z);
        this.totalZombiesRemainingInLevel -= 1;
        return z;
    }

    /**
     * Adds new listener to current list of listeners.
     * @param l the listener that interacts between model and ui
     */
    public void addListener(GameView v, PopZombieModelListener l) {
        this.listeners.add(l);
    }
    
    /**
     * Get the initial depth of the zombie
     * @return ZOMBIE_INITIAL_DEPTH
     */
    public int getZombieInitialDepth(){
        return ZOMBIE_INITIAL_DEPTH;
    }
    
    /**
     * Get the final depth of the zombie
     * @return ZOMIBIE_FINAL_DEPTH
     */
    public int getZombieFinalDepth(){
        return ZOMBIE_FINAL_DEPTH;
    }
    
    /**
     * Get the initial scaling factor for the zombie image
     * @return ZOMBIE_INITIAL_SCALE
     */
    public double getZombieInitialScale(){
        return ZOMBIE_INITIAL_SCALE;
    }
    
    /**
     * Get the final scaling factor of the zombie image
     * @return ZOMBIE_FINAL_SCALE
     */
    public double getZombieFinalScale(){
        return ZOMBIE_FINAL_SCALE;
    }
    
    /**
     * Launches a parachute to float down the screen.
     */
    private void sendParachute() {
        this.activeParachute = true;
        // set parachute's position randomly in the room, at y=0 TODO
        this.parachute.getPosition().setTheta(this.getRandomThetaInRoom());
        // later this should be changed to (height of parachute)*-1
        this.parachute.getPosition().setZ(0);
    }
    
    /**
     * Gets parachute's status.
     * @return activeParachute true if the parachute is launched 
     */
    public boolean getActiveParachute() {
        return this.activeParachute;
    }
    
    /**
     * Get the popcorns that have been launched.
     * @return popcorns the list of popcorns player launched
     */
    public List<Popcorn> getPopcorns() {
        return this.popcorns;
    }
    
    /**
     * Get the zombies on the screen.
     * @return zombies the list of zombies on the screen
     */
    public List<Zombie> getZombies() {
        return this.zombies;
    }

    /**
     * Check if popcorn makes contact with the zombie.
     */
    public void collisionCheck() {
        float zombieTheta, zombieR, zombieZ;
        float popTheta, popR, popZ;

        for (int i = 0; i < this.popcorns.size(); i++) {
            for (Zombie z : this.zombies) {
            /*
             * run through all popcorns
             * a collision should be detected if
             *     1) the popcorn is at a similar depth to the zombie
             *     2) the popcorn's x and y are with some rectangle that
             *        confines the zombie (size of zombie image, probably)
             */
                //Checks that popcorn position is close to x-y position of zombie
                zombieTheta = degreeToPixel(z.getPosition().getTheta());
                zombieR = z.getPosition().getR();
                zombieZ = z.getPosition().getZ();
                popTheta = degreeToPixel(this.popcorns.get(i).getPosition().getTheta());
                popR = this.popcorns.get(i).getPosition().getR();
                popZ = this.popcorns.get(i).getPosition().getZ();
            /*    if (popTheta < (zombieTheta - ZOMBIE_SIZE/zombieZ) || popTheta > (zombieTheta + ZOMBIE_SIZE/zombieZ)) {
                    continue;
                }

                if (popZ < (zombieZ - ZOMBIE_SIZE/zombieZ) || popZ > (zombieZ + ZOMBIE_SIZE/zombieZ)) {
                    continue;
                }
*/
                double percentTotalTravelCompleted = (this.getZombieInitialDepth()-zombieR)/(this.getZombieInitialDepth() - this.getZombieFinalDepth());
                double scalingFactor = ((this.getZombieFinalScale()-this.getZombieInitialScale())*percentTotalTravelCompleted)+this.getZombieInitialScale();
                
                int zheight = (int) (ZOMBIE_SIZE*scalingFactor);
                int zwidth = (int) (int) (ZOMBIE_SIZE*scalingFactor);
                
                if (popTheta < zombieTheta || popTheta > (zombieTheta + zwidth)) {
                    continue;
                }
                
                if (popZ > (zombieZ + zheight) || popZ < zombieZ) {
                    continue;
                }
                
                // if popcorn position is greater than the zombie position, we have a hit

                if (popR >= zombieR) {
                    // if popcorn hits zombie,
                    // remove the piece of popcorn and decrease the zombie's health
                    this.popcorns.remove(this.popcorns.get(i));
                    z.decreaseHealth();
                    i--;
                    break;
                }

            }
        }
    }

    /**
     * Converts the device's degrees to pixels.
     * @param degree the current degree orientation of the device
     * @return the pixel on the image the device should be at
     */
    private float degreeToPixel(float degree) {
        return degree*this.PIXEL_TO_DEGREES;
    }
    
    /**
     * Kills zombies that are out of health by removing them from the list of zombies.
     */
    private void killZombie() {
        
        Iterator<Zombie> i = this.zombies.iterator();
        while(i.hasNext()) {
            Zombie z = i.next();
            if (z.getCurrentHealth() == 0) {
                i.remove();
                // Player gets 5 points for every zombie killed
                this.currentScore += 5;
                this.totalZombiesRemainingInLevel -= 1;
            }
        }
        
    }

    /**
     * Get the number of zombies current out in the level.
     * @return zombies the list of zombies player sees on screen
     */
    public int getNumZombiesInLevel() {
        return zombies.size();
    }

    /**
     * Get the player's current score.
     * @return the number of points player currently has
     */
    public int getCurrentScore() {
        return this.currentScore;
    }

    /**
     * Gets the current level the game is on
     * @return the current level
     */
    public int getCurrentLevel() {
        return this.level;
    }
    
    /**
     * Get the height of the screen.
     * @return GAME_HEIGHT the height of the user's screen
     */
    public int getGameHeight() {
        return this.GAME_HEIGHT;
    }
    
    
    /**
     * Get the depth of the screen.
     * @return GAME_DEPTH the height of the user's screen
     */
    public int getGameDepth() {
        return this.GAME_DEPTH;
    }

    
    /**
     * Get the default size of the popcorn.
     * @return POPCORN_SIZE the default size
     */
    public int getPopcornSize() {
        return this.POPCORN_SIZE;
    }
    
    /**
     * Get the default size of the zombie (used for scaling).
     * @return ZOMBIE_SIZE the default size
     */
    public int getZombieSize() {
        return this.ZOMBIE_SIZE;
    }
    
    /**
     * Retrieves the parachute used to send ammo.
     */
    public Parachute getParachute() {
        return this.parachute;
    }
    
    /**
     * Advances player onto the next level and increases level by 1.
     */
    private void advanceLevel(){
        this.level++;
    }

    /**
     * Ends game when player is out of health.
     */
    private void gameOver() {
        this.popcorns.clear();
        
        for (PopZombieModelListener l : this.listeners) {
            l.gameEnded();
        }
    }

    /**
     * Player throws popcorn so creates a popcorn to appear on the screen
     * and takes popcorn away from player's supply.
     * @param P the position of the popcorn thrown at
     * @return
     */
    public Popcorn throwPopcorn(Position P){
        //check to make sure ammo is remaining
        //create a new piece of popcorn at depth zero and at the (x,y) of the player's tap
        //decrease player's ammo supply by one
        //add the piece to this.popcorns so that it will be used in the game
        Popcorn pop = new Popcorn(P);
        if (this.player.throwPopcorn()) {
            this.popcorns.add(pop);
        } else {
            for (PopZombieModelListener l : this.listeners) {
                l.outOfAmmo("Out of ammo!");
            }
        }
        return pop;
    }
    
    
    /**
     * Removes popcorn from the list that didn't hit any zombies and have gone too far
     */
    public void removeTooFarPopcorn() {
        Iterator<Popcorn> i = this.popcorns.iterator();
        while(i.hasNext()) {
            Popcorn p = i.next();
            if (p.getPosition().getR() > GAME_DEPTH) {
                i.remove();
            } else if (this.POPCORN_SIZE/(p.getPosition().getR() + 1) <= 0) {
            
                i.remove();
            } 
        }
    }
    
    /**
     * Microwave popcorn to convert it from kernel to popcorn.
     */
    public void microwavePopcorn(){
        this.player.microwavePopcorn();
    }
    
    /**
     * Gets the amount of ammo at player's disposal
     * @return the ammount of ammo
     */
    public int getPlayerAmmo() {
        return this.player.getAmmo();
    }
    
    /**
     * Gets the ammount of kernels in the player's supply
     * @return the size of the supply
     */
    public int getPlayerSupply() {
        return this.player.getSupply();
    }

    public int getPlayerCurrentHealth() {
        return this.player.getCurrentHealth();
    }
    
    /**
     * Gets the player's total health
     * @return the player's total health
     */
    public int getPlayerTotalHealth() {
        return this.player.getCurrentHealth();
    }
    
    /**
     * Player tapped on parachute and collects it to add kernels to ammo.
     */
    public void collectParachute() {
        if (this.activeParachute) {
            //move the parachute off-screen
            //parachute.getPosition().setTheta(GAME_WIDTH);
            parachute.getPosition().setZ(GAME_HEIGHT);
            parachute.getPosition().setR(GAME_DEPTH);
 
            //reset the parachute delay
            this.parachuteDelay = 0;
        
            //increase supply
            this.player.takeParachute(this.KERNELS_PER_PARACHUTE);
            this.activeParachute = false;
        }
    }
    
    /**
     * Notifies the listeners that the game is currently paused.
     */
    public void pause() {
        this.state = this.pausestate;
    	for (PopZombieModelListener l: this.listeners) {
            l.gamePaused();
        }
    }
    
    /**
     * Notifies the listeners that the game has unpaused.
     */
    public void unpause() {
        this.state = this.gamegoingstate;
        for (PopZombieModelListener l: this.listeners) {
            l.gameUnpaused();
        }
    }
    
    /**
     * Returns the current state of the game.
     * @return GameState of the game
     */
    public GameState getState() {
        return this.state;
    }

    /**
     * Returns if the current state responds to touch or not.
     * @return true if the state responds to touch
     */
    public boolean stateAllowTouch() {
        return this.state.allowTouch();
    }

    /**
     * Moves the player, zombies, and popcorns
     * @param angle the angle to move the entities
     */
    public void playerMoves(float angle) {
        if (this.activeParachute) {
            this.parachute.move(angle);
        }

        //move all zombies
        for (Zombie z : this.zombies) {
            z.move(angle);
        }
            
        //move all popcorns that are being thrown
        for (Popcorn p : this.popcorns) {
            p.move(angle);
        }
    }
    
    /**
     * LevelUpState class that moves the model onto the next level
     *
     */
    private class levelUpState implements GameState {

    	/**
    	 * The time lapse between levels.
    	 */
        private double countdown;
        
        /**
         * Moves the player onto the next level.
         */
        private levelUpState() {
            countdown = PopZombieModel.this.TIME_BETWEEN_LEVELS; //milliseconds
        }
        
        @Override
        public void gameLoop(float deltaTime) {
            countdown = countdown - deltaTime;
            if (countdown < 0) {
                PopZombieModel.this.state = PopZombieModel.this.gamegoingstate;
                PopZombieModel.this.startLevel();
                countdown = PopZombieModel.this.TIME_BETWEEN_LEVELS;
                return;
            }
        }

        @Override
        public boolean allowTouch() {
            return false;
        }
        
    }
    
    /**
     * GameState class where the game is currently running and playing.
     *
     */
    private class gameGoingState implements GameState {
        
    	/**
    	 * Model used to communicate with the PopZombieModel.
    	 */
        PopZombieModel model = PopZombieModel.this;
        
        /**
         * Checks to see if the game satisfies the necessary conditions to finish the level.
         */
        private void checkEndLevel() {
            if (model.checkEndLevel()) {
                model.betweenLevels = true;
                model.endLevel();
                model.popcorns = new ArrayList<Popcorn>();
                model.state = PopZombieModel.this.levelupstate;
            }
        }
        
        /**
         * Checks to see if a zombie is close enough to attack.
         */
        private void zombieAttack() {
            int attackingZombies = 0;
            for (int i = 0; i < model.zombies.size(); i++) {
                if (model.zombies.get(i).isAttacking()) {
                    model.beingAttacked = true;
                    attackingZombies++;
                    model.player.hurt();
                    //for(PopZombieModelListener l : model.listeners) {
                    //    l.zombieAttacking();
                    //}
                    
                }
            }
            
            if (attackingZombies == 0) {
                model.beingAttacked = false;
                //for(PopZombieModelListener l : model.listeners) {
                //    model.beingAttacked = false;
                //    l.zombieNotAttacking();
                //}
            }
        }
        
        /**
         * Adds a new zombie to the game.
         */
        private void addZombies() {
            if(model.zombies.size() < zombiesInRoomCap) {
                if(totalZombiesRemainingInLevel > 0) {
                    model.addZombieToGame();
                }
            }
        }
        
        /**
         * Sends out a parachute when the time has elapsed.
         * @param deltaTime the amount of time between the delay
         */
        private void makeParachute(float deltaTime) {
            
            model.parachuteDelay += deltaTime;
            if(parachuteDelay > DELAY_BETWEEN_PARACHUTES) {
                parachuteDelay = 0;
                model.sendParachute();
            }
        }

        @Override
        public void gameLoop(float deltaTime) {
            
            // add zombie to room if necessary
            this.addZombies();

            // create parachute if delay between parachutes has ended
            this.makeParachute(deltaTime);

            // update location of parachute, zombies, and popcorn
            model.moveEntities(deltaTime);
            //removes popcorns that didn't hit any zombies but are far away
            model.removeTooFarPopcorn();
            // check for collisions (popcorn & zombie)
            model.collisionCheck();        
            // check to see which zombies are still alive and kill the ones that are not
            model.killZombie();
            
            // check if zombies are within attacking range and attack
            this.zombieAttack();
            
            // check if player has missed parachute and make it inactive if missed
            if (model.activeParachute) {           
                if (model.parachute.getPosition().getZ() > model.GAME_HEIGHT) {
                    //this.activeParachute = false;
                    model.parachuteDelay = 0;
                }
            }
            
            // check to see if player is still alive
            if (!model.player.isAlive()) {
                model.gameOver();
            }

            // check for level complete
            this.checkEndLevel();
            
            for (PopZombieModelListener l: model.listeners) {
                l.draw();
            }
            
        }

        @Override
        public boolean allowTouch() {
            return true;
        }
    }
    
    /**
     * Halts all game processes when the game is paused.
     *
     */
    private class pauseState implements GameState {
    	
        @Override
        public void gameLoop(float deltaTime) {
        }

        @Override
        public boolean allowTouch() {
            return false;
        }
        
    }
}
