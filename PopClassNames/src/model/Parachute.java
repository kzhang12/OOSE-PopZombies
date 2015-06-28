package model;

/**Parachute class that provides the player with unpopped kernels.
 * 
 * @author Connie Chang
 * @author Kevin Zhang
 * @author Stephanie Chew
 *
 */
public class Parachute extends Character {
    
    /**The weight of the parachute.*/
    private int weight;
    
    /**Number of kernels in this Parachute.*/
    private int supplyNum;
    
    @Override
    public void update() {
        //TODO physics things
    }
    
    /**Determines if Parachute is going right or left for this step.
     * 
     * @return True for right, false for left
     */
    public boolean goRight() {
        return false;
    }
    
}
