package edu.OOSE.cs.jhu.group2.PopZombiesModel;

/**Player class that represents the user.
 * 
 * @author Connie Chang
 * @author Kevin Zhang
 *
 */
public class Player {
    
	/**The amount of health a player gains by eating a piece of popcorn.*/
    private final int HEALTH_PER_POPCORN = 2; //Player gains 2HP (of 100) when eating one popcorn
    /**The amount of damage a player takes when a zombie attacks them.*/
    private final int DAMAGE_FROM_ZOMBIE = 1;
    /**The maximum amount of kernels that can be microwaved at one time.*/
    private final int KERNELS_PER_MICROWAVE_BATCH = 25; //microwave 25 kernels at a time

    /**The number of unpopped popcorn that the player possesses.*/
    private int supply;
    /**The number of popped popcorn available for the player to throw.*/
    private int ammo;
    /**The player's current health.*/
    private int currentHealth;
    /**The player's starting health.*/
    private int totalHealth;

    
    /**Constructor for the Player class.*/
    public Player(int totalHealth) {
        // why would a player ever be created with less than full health?
        this.supply = 10;
        this.ammo = 50; //TODO change back to 0
        this.totalHealth = totalHealth;
        this.currentHealth = this.totalHealth;
    }
    
    /**Checks if the player still has ammo.
     * 
     * @return whether or not the player still has ammo
     */
    public boolean hasAmmo(){
        return this.ammo>0;
    }
    
    /**Checks if the player still has popcorn in the supply.
     * 
     * @return whether or not there is popcorn in the supply
     */
    public boolean hasSupply(){
        return this.supply>0;
    }
    
    /**Updates the amount of ammo the player has.
     * 
     * @param change: The amount to be changed
     */
    private void changeAmmo(int change){
        this.ammo += change; //pass negative values to decrease ammo
        if(this.ammo<0){
            this.ammo = 0;
        }
    }
    
    /**Updates the amount of popcorn in the supply.
     * 
     * @param change: The amount to be changed
     */
    private void changeSupply(int change){
        this.supply += change; //pass negative values to decrease supply
        if(this.supply < 0){
            this.supply = 0;
        }
    }
    
    /**Gets the number of popped popcorn.
     * 
     * @return The number of popped popcorn
     */
    public int getAmmo(){
        return this.ammo;
    }
    
    /**Gets the number of unpopped popcorn in the supply.
     * 
     * @return the number of the supply
     */
    public int getSupply(){
        return this.supply;
    }
    
    /**Gets the player's total health.
     * 
     * @return total health
     */
    public int getTotalHealth() {
        return this.totalHealth;
    }
    
    /**Gets the player's current health.
     * 
     * @return current health
     */
    public int getCurrentHealth() {
        return this.currentHealth;
    }
    
    /**Increases the player's current health.
     * 
     * @param health: amount to increase health
     */
    private void increaseHealth(int health) {
        this.currentHealth += health;
        if (this.currentHealth > this.totalHealth) {
            this.currentHealth = this.totalHealth;
        }
    }
    
    /**Decreases the player's current health.
     * 
     * @param damage: amount to decrease health
     */
    private void decreaseHealth(int damage) {
        this.currentHealth -= damage;
        if (this.currentHealth < 0) {
            this.currentHealth = 0;
        }
    }
    
    /**Player eats one piece of popcorn.*/
    public void eatSupply(){
        //change method name? Misleading to say eat 'supply', but decrement 'ammo'
        if (this.hasAmmo()) {
            this.changeAmmo(-1);
            this.increaseHealth(this.HEALTH_PER_POPCORN);
        }
    }
    
    /**Player takes damage from zombie*/
    public void hurt() {
        this.decreaseHealth(this.DAMAGE_FROM_ZOMBIE);
    }
    
    /**Checks if player is still alive.
     * 
     * @return whether or not the player is still alive
     */
    public boolean isAlive() {
        return this.currentHealth > 0;
    }
    
    /**Player throws one piece of popcorn.*/
    public boolean throwPopcorn(){
        //check to make sure ammo is remaining
        //create a new piece of popcorn at depth zero and at the (x,y) of the player's tap
        //decrease player's ammo supply by one
        //add the piece to this.popcorns so that it will be used in the game
        if (this.hasAmmo()) {
            this.changeAmmo(-1);
            return true;
        }
        return false;
    }
    
    /**Takes popcorn from the supply and mvoes it into ammo*/
    public void microwavePopcorn(){
        //we probably want a delay system here, so microwaving is not instantaneous
    	if (this.hasSupply()) {
            if (this.getSupply() < this.KERNELS_PER_MICROWAVE_BATCH) {
                this.changeAmmo(this.getSupply());
                this.changeSupply(this.getSupply() * -1);
            } else {
                this.changeAmmo(this.KERNELS_PER_MICROWAVE_BATCH);
                this.changeSupply(this.KERNELS_PER_MICROWAVE_BATCH * -1);
            }
        }
    }
    
    /**Adds the amount of kernels to the supply
     * 
     * @param kernels: amount of kernels in the parachute
     */
    public void takeParachute(int kernels) {
        this.changeSupply(kernels);
    }

}
