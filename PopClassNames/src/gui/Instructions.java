package gui;
import java.util.List;

/**Instructions menu to teach user how to play Pop Zombies.
 * 
 * @author Connie Chang
 * @author Kevin Zhang
 * @author Stephanie Chew
 *
 */
public class Instructions implements Menu {
    
    /**User has option to go back to main menu, adjust the sound,
     * and view high scores.
     */
    private List<String> optionsList;
    
    /** Constructor for instructions menu.*/
    public Instructions() {
    }

    @Override
    public void select() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<String> getOptionsList() {
        // TODO Auto-generated method stub
        return null;
    }
    
    /** Get the instructions for the game.
     * 
     * @return instructions for how to play the game
     */
    public String getInstructions() {
        return null;
    }

}
