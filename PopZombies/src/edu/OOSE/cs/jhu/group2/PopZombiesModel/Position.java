package edu.OOSE.cs.jhu.group2.PopZombiesModel;

/**Position class that represents the Entities location.
 * 
 * @author Connie Chang
 * @author Elaine Chao
 * @author Stephanie Chew
 * @author Ted Stanley
 * @author Kevin Zhang
 *
 */
public class Position {

	/**The horizontal position on the plane of the screen.*/
	private float theta;
	/**The depth on the entity (how far from player it is).*/
	private float r;
	/**The height on the plane of the screen*/
	private float z;
	
	/**Constructor that takes three coordinates for Position.
	 * 
	 * @param theta horizontal position on screen
	 * @param r depth of the position
	 * @param z Z (height) position
	 */
	public Position(float t, float r, float z) {
		this.theta = t;
		this.r = r;
		this.z = z;
	}
	
	/**Returns the horizontal position.
	 * 
	 * @return The theta position
	 */
	public float getTheta() {
		return this.theta;
	}
	
	/**Returns the depth.
	 * 
	 * @return The depth
	 */
	public float getR() {
		return this.r;
	}
	
	/**Returns the height position.
	 * 
	 * @return The height
	 */
	public float getZ() {
		return this.z;
	}
	
	/**Sets the theta position.
	 * 
	 * @param theta The new theta position
	 */
	public void setTheta(float t) {
		this.theta = t;
	}
	
	/**Sets the depth.
	 * 
	 * @param r The new R position
	 */
	public void setR(float r) {
		this.r = r;
	}
	
	/**Sets the height.
	 * 
	 * @param z The new height
	 */
	public void setZ(float z) {
		this.z = z;
	}
	
}