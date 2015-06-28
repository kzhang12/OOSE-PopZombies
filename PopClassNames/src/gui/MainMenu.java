package gui;
import java.util.List;

/**MainMenu class that includes all the options for the player.
 * 
 * @author Connie Chang
 * @author Kevin Zhang
 * @author Stephanie Chew
 *
 */
public class MainMenu implements Menu {
    /** Options list contains Play, High Score, General Options, and
     * Instructions.*/
    private List<String> optionsList;
    
    /** Constructor for main menu. */
    public MainMenu() {
    }
    
    @Override
    public List<String> getOptionsList() {
        return this.optionsList;
    }

    @Override
    public void select() {
        // TODO Auto-generated method stub
        
    }
}
