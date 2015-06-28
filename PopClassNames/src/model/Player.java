package model;

/**Player class that represents the user.
 * 
 * @author Connie Chang
 * @author Kevin Zhang
 *
 */
public class Player extends Character {
  
    /**The degree out of 360 that the player is facing.*/
    private int direction;
    
    /**The number of unpopped popcorn that the player possesses.*/
    private int supply;
    
    /**The number of popped popcorn available for the player to throw.*/
    private int ammo;

    /**Constructor for the Player class.*/
    public Player() {
        
    }
    
    /**Gets the direction of the player.
     * 
     * @return The direction
     */
    public int getDirection() {
        return this.direction;
    }

    /**Sets the direction.
     * 
     * @param d The new direction
     */
    public void setDirection(int d) {
        this.direction = d;
    }

    /**Gets the number of unpopped popcorn in the supply.
     * 
     * @return the number of the supply
     */
    public int getSupply() {
        return this.supply;
    }

    /**Sets the number of unpopped popcorn in the supply.
     * 
     * @param s The new number of supply
     */
    public void setSupply(int s) {
        this.supply = s;
    }

    /**Gets the number of popped popcorn.
     * 
     * @return The number of popped popcorn
     */
    public int getAmmo() {
        return this.ammo;
    }

    /**Sets the number of popped popcorn.
     * 
     * @param a The new number of popped popcorn
     */
    public void setAmmo(int a) {
        this.ammo = a;
    }
    
    /**Player throws one piece of popcorn.*/
    public void throwPopcorn() {
        
    }
    
    /**Player eats one piece of popcorn.*/
    public void eatPopcorn() {
        
    }
    
    @Override
    public void update() {
        
    }

}
