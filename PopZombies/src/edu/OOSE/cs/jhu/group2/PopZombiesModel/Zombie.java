package edu.OOSE.cs.jhu.group2.PopZombiesModel;

/**Zombie class that represents the enemy.
 * 
 * @author Connie Chang
 * @author Stephanie Chew
 * @author Kevin Zhang
 * @author Elaine Chao
 * @author Ted Staley
 *
 */
public class Zombie extends Entity {

	/**The distance from Player when Zombies start attacking.*/
    final int ATTACK_RANGE = 50;
    /**The amount of damage taken when hit with Popcorn.*/
    final int DAMAGE_PER_POPCORN = 25;

    /**The max health of this Zombie.*/
    private int totalHealth;
    /**The Zombie's health during the game.*/
    private int currentHealth;
    
    /**Speed in millisec in R direction.*/
    private float speedR;
    
    /**Zombie constructor that sets up 
     * location and health.
     * 
     * @param p Starting position of Zombie.
     * @param totalHealth The max health of Zombie.
     */
    public Zombie(Position p, int totalHealth) {
        super(p);
        this.speedR = 0.05f;
        this.totalHealth = totalHealth;
        this.currentHealth = this.totalHealth;
    }
    
    /**Returns the max health.
     * 
     * @return The max health
     */
    public int getTotalHealth() {
        return this.totalHealth;
    }
    
    /**Returns the current health.
     * 
     * @return The current health
     */
    public int getCurrentHealth() {
        return this.currentHealth;
    }
    
    /**Increases the current health by the given integer,
     * 
     * @param health The amount of health to increase by
     */
    public void increaseHealth(int health) {
        this.currentHealth += health;
        if (this.currentHealth > this.totalHealth) {
            this.currentHealth = this.totalHealth;
        }
    }
    
    /**Decreases health by given integer.
     * 
     */
    public void decreaseHealth() {
        this.currentHealth -= this.DAMAGE_PER_POPCORN;
        if (this.currentHealth < 0) {
            this.currentHealth = 0;
        }
    }
    
    /**Updates the Position of the Zombie.
     * 
     */
    public void update(float deltaTime){
        if (!this.isAttacking()) {
            this.getPosition().setR((float) (this.getPosition().getR() - deltaTime*this.speedR));
        }
    }
    
    /**Returns whether the Zombie is attacking the Player.
     * 
     * @return True if attacking, otherwise false
     */
    public boolean isAttacking() {
        return this.getPosition().getR() <= this.ATTACK_RANGE;
    }

}