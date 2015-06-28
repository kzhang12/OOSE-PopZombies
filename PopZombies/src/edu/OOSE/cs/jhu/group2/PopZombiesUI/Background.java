package edu.OOSE.cs.jhu.group2.PopZombiesUI;

/**
 * Background class that incorporates a scrolling background.
 * 
 * @author Connie Chang
 * @author Elaine Chao 
 *
 */
public class Background {
	
	/**
	 * The horizontal and vertical coordinates for the top left corner of the background
	 */
	private int bgZ;
	private float bgTheta;
	
	/**
	 * The amount the background scrolls by
	 */
	//private int speedTheta;
	
	/**Constant representing 180 degrees.*/
	private final int DEGREE180 = 180;
	
	/**
	 * Background constructor
	 * @param theta the horizontal coordinate
	 * @param z the vertical coordinate
	 */
	public Background(int theta, int z) {
		this.bgTheta = theta;
		this.bgZ = z;
		//this.speedTheta = 10;
	}
	
	/**
	 * Move the background a certain angle.
	 * @param angle to move
	 */
	public void move(float angle) {
		this.bgTheta -= angle;
		
		if (this.bgTheta > this.DEGREE180) {
			this.bgTheta = this.bgTheta - 2 * this.DEGREE180;
		}
		else if (this.bgTheta < -1 * this.DEGREE180) {
			this.bgTheta = this.bgTheta + 2 * this.DEGREE180;
		}
		//System.out.println(this.bgTheta);
	}

	/**
	 * Gets the horizontal coordinate of the background
	 * @return the horizontal position
	 */
	public float getbgTheta() {
		return bgTheta;
	}

	/**
	 * Sets the horizontal position of the background
	 * @param bgTheta
	 */
	public void setbgTheta(int bgTheta) {
		this.bgTheta = bgTheta;
	}

	/**
	 * gets the vertical position of the background
	 * @return the vertical position
	 */
	public int getbgZ() {
		return bgZ;
	}

	/**
	 * Set the vertical position of the background
	 * @param bgZ the vertical position 
	 */
	public void setbgZ(int bgZ) {
		this.bgZ = bgZ;
		System.out.println("what the....");
	}
}
