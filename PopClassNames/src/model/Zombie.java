package model;

/**Zombie class that represents the enemy.
 * 
 * @author Connie Chang
 * @author Kevin Zhang
 *
 */
public class Zombie extends Character {
    
    /**How fast the Zombie is walking.*/
    private int speed;
    
    /**How many hits required to kill Zombie.*/
    private int strength;
    
    /**Constructor for the Zombie class.
     * @param level The level of the Zombie
     * */
    public Zombie(int level) {
        
    }

    /**Returns the current speed.
     * 
     * @return The speed
     */
    public int getSpeed() {
        return this.speed;
    }

    @Override
    public void update() {
        
    }
    
    /**Returns whether the Zombie is hurting the player, determined by position.
     * 
     * @return Whether Zombie is hurting
     */
    public boolean isAttacking() {
        return false;
    }
    
    /**Determines if Parachute is going right or left for this step.
     * 
     * @return True for right, false for left
     */
    public boolean goRight() {
        return false;
    }

}
