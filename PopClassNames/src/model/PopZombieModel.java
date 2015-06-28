package model;

import java.util.List;

/**Model for implementing the logic of Pop Zombies.
 * On each level, player tries to eliminate all zombies
 * with popcorn.
 * 
 * @author Connie Chang
 * @author Kevin Zhang
 * @author Stephanie Chew
 *
 */
public class PopZombieModel {
    
    /**The level number.*/
    private int levelNum;
    
    /**The player that the user interacts with.*/
    private Player player;
    
    /**The microwave of the game.*/
    private Microwave microwave;
    
    /**List of zombies left to kill.*/
    private List<Zombie> zombieList;
    
    /**Player's current score count.*/
    private int currentScore;
    
    /** Constructor for model.*/
    public PopZombieModel() {
    }
    
    /**The zombie dies and disappears.*/
    public void removeZombie() {
        
    }
    
    /** Checks to see if high score makes the list.
     * Prompts user for name if it is a new high score
     * @param newScore the score to check
     */
    public void checkHighScore(int newScore) {
        
    }
    
    /**Pauses the game.*/
    public void pause() {
        
    }
    
    /**Adds a Zombie to the zombieList.*/
    public void createZombie() {
        
    }
    
    /**Updates the positions of all characters.*/
    public void update() {
        
    }
    
    /**Drops a parachute in the game.*/
    public void createParachute() {
        
    }
    
    /**Removes the parachute from the game.*/
    public void removeParachute() {
        
    }
    
    /**Player gets the parachute on screen.*/
    public void retrieveParachute() {
        
    }
    
    /**Player checks to see if the round is correctly ended.
     * @return true if the player should continue to the next level*/
    public boolean endLevel() {
        return false;
    }
    
    /**End the game and save the high score.
     * @return true if the game is over*/
    public boolean endRound() {
        return false;
    }
    
    /**Update the score when the user ends the level.*/
    private void scoreCount() {

    }
}
