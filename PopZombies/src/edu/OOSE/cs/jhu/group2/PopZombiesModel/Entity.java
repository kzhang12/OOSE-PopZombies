package edu.OOSE.cs.jhu.group2.PopZombiesModel;

/**Entity class that represents all on-screen objects.
 * 
 * @author Connie Chang
 * @author Elaine Chao
 * @author Stephanie Chew
 * @author Ted Stanley
 * @author Kevin Zhang
 *
 */
public abstract class Entity {
    
	/**Constant representing 180 degrees.*/
	private final int DEGREE180 = 180;
	
	/**The Position of the Entity on screen.*/
    protected Position position;
    
    /**How fast entity moves when screen moves.*/
    protected float speedTheta;
    
    /**Constructor that sets up the position.*/
    public Entity(Position p) {
        this.position = p;
        this.speedTheta = 10;
    }
    
    /**Returns the Position.
     * 
     * @return The position
     */
    public Position getPosition(){
        return this.position;
    }
    
    /**
     * Moves the entity.
     * @param angle the entity takes in
     */
    public void move(float angle) {
    	this.getPosition().setTheta(this.getPosition().getTheta() - angle);
    	
    	if (this.getPosition().getTheta() < -1 * this.DEGREE180) {
    		this.getPosition().setTheta(this.getPosition().getTheta() + 2 * this.DEGREE180);
    	}
    	else if (this.getPosition().getTheta() > this.DEGREE180) {
    		this.getPosition().setTheta(this.getPosition().getTheta() - 2 * this.DEGREE180);
    	}
    }
    
    /**
     * Updates the entity.
     * @param deltaTime elapsed time
     */
    public abstract void update(float deltaTime);
}
