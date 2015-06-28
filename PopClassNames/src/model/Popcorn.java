package model;

/**Popcorn class representing thrown popcorn.
 * 
 * @author Connie Chang
 * @author Kevin Zhang
 *
 */
public class Popcorn extends Character {

    /**The horizontal speed of the popcorn.*/
    private int speedX;
    
    /**The vertical speed of the popcorn.*/
    private int speedY;
    
    /**Constructor for popcorn class.*/
    public Popcorn() {
        this.setHealth(0);
    }
    
    /**Gets the horizontal speed.
     * 
     * @return The horizontal speed
     */
    public int getSpeedX() {
        return this.speedX;
    }

    /**Sets the horizontal speed.
     * 
     * @param sX The new horizontal speed
     */
    public void setSpeedX(int sX) {
        this.speedX = sX;
    }

    /**Gets the vertical speed.
     * 
     * @return The current vertical speed.
     */
    public int getSpeedY() {
        return this.speedY;
    }

    /**Sets the vertical speed.
     * 
     * @param sY The new vertical speed
     */
    public void setSpeedY(int sY) {
        this.speedY = sY;
    }

    @Override
    public void update() {

    }
    
    /**Hits zombie to give damage.*/
    public void hitZombie() {
        
    }
}
