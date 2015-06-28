package model;

/**General Character class with health and position.
 * 
 * @author Connie Chang
 * @author Kevin Zhang
 *
 */
public class Character {
    
    /**The current health of the character.*/
    private int health;
    
    /**The X position of the character.*/
    private int posX;
    
    /**The Y position of the character.*/
    private int posY;

    /**Gets the health.
     * 
     * @return The health
     */
    public int getHealth() {
        return this.health;
    }

    /**Sets the health.
     * 
     * @param h The new health
     */
    public void setHealth(int h) {
        this.health = h;
    }

    /**Gets the X position.
     * 
     * @return The X position
     */
    public int getPosX() {
        return this.posX;
    }

    /**Sets the X position.
     * 
     * @param x The new X 
     */
    public void setPosX(int x) {
        this.posX = x;
    }

    /**Gets the Y position.
     * 
     * @return The Y position
     */
    public int getPosY() {
        return this.posY;
    }

    /**Sets the Y position.
     * 
     * @param y The new Y
     */
    public void setPosY(int y) {
        this.posY = y;
    }
    
    /**Updates position and health.*/
    void update() {
        
    }
}
