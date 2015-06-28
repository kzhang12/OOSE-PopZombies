package edu.OOSE.cs.jhu.group2.PopZombiesModel;

/**
 * Interface for model listener to interact between UI and model.
 * 
 * @author Stephanie 
 * @author Connie Chang
 * @author Elaine Chao
 * @author Ted Staley
 * @author Kevin Zhang
 */

public interface PopZombieModelListener {
    /**
     * Player has defeated all zombies in current level and is
     * ready to continue onto next level. 
     */
    public void levelEnded();
    
    /**
     * Next level is ready to be set up and started.
     */
    public void levelStarted();
    
    /**
     * Indicates that the player has run out of health
     * and game ended.
     */
    public void gameEnded();
    /**
     * Sends error message to user that user is out of ammo
     * @param message to send to user
     */
    public void outOfAmmo(String s);
    
    /**
     * Indicates that game is paused
     */
    public void gamePaused();
    
    /**
     * Indicates to UI that game has been unpaused
     */
    public void gameUnpaused();
    
    /**Indicates to UI to draw.*/
    public void draw();
    
}
