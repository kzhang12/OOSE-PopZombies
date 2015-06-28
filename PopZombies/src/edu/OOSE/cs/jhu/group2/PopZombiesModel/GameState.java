package edu.OOSE.cs.jhu.group2.PopZombiesModel;

/**
 * GameState interface for each state of the game.
 * 
 * @author Connie Chang
 * @author Ted Staley
 * @author Elaine Chao
 * @author Kevin Zhang
 * @author Stephanie Chew
 *
 */
public interface GameState {
	
	/**
	 * Main game loop to update activity.
	 * @param deltaTime elapsed time
	 */
	public void gameLoop(float deltaTime);
	
	/**
	 * Determines whether user plays virtual or regular game.
	 * @return whether to use accelerometer or on touch
	 */
	public boolean allowTouch();
	
}
