package edu.OOSE.cs.jhu.group2.PopZombiesModel;

import android.graphics.BitmapFactory;
import edu.OOSE.cs.jhu.group2.popzombies.R;

/**Popcorn class representing thrown popcorn.
 * 
 * @author Connie Chang
 * @author Elaine Chao
 * @author Stephanie Chew
 * @author Ted Stanley
 * @author Kevin Zhang
 *
 */
public class Popcorn extends Entity {
    
    /**Speed per millisec in R direction.*/
    private float speedR;

	/**Constructor for Popcorn class.*/
    public Popcorn(Position p) {
        super(p);
        this.speedR = 1;
    }
    
    /**Updates the position of the Popcorn.*/
    public void update(float deltaTime) {
    	this.getPosition().setR(this.getPosition().getR() + deltaTime*this.speedR);
    }
    
}
