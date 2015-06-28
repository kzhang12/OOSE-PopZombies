package gui;
import java.util.List;
import java.util.Map;

/**HighScoreMenu with high score info and displays them.
 * 
 * @author Connie Chang
 * @author Kevin Zhang
 * @author Stephanie Chew
 *
 */
public class HighScoreMenu implements Menu {
    
    /**A list of names associated with high scores.*/
    private Map<String, Integer> highScores;
    
    /** List of menu options including return back to main menu.*/
    private List<String> optionsList;

    /** Constructor for high score menu.*/
    public HighScoreMenu() {
        
    }
    
    /** Get the names and associated high scores for the game.
     * @return scores the names mapped to number scores*/
    public Map<String, Integer> getHighScores() {
        return this.highScores;
    }

    /** Get the options for the high score menu.
     */
    @Override
    public void select() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<String> getOptionsList() {
        // TODO Auto-generated method stub
        return this.optionsList;
    }
    
    /**Adds new high score into the high score menu.
     * 
     * @param name of user who has the high score
     * @param score of the game
     */
    public void addScores(String name, int score) {
    }

}
