package gui;

import java.util.List;

/**SoundMenu class for when player wants to adjust the sound.
 * Options list includes going back to the main menu, viewing
 * the instructions, viewing the high scores, and adjusting
 * sound options.
 * 
 * @author Connie Chang
 * @author Kevin Zhang
 * @author Stephanie Chew
 *
 */

public class SoundMenu implements Menu {
    
    /**Options list includes going back to the main menu, viewing
 * the instructions, viewing the high scores, and adjusting
 * sound options.*/
    private List<String> optionsList;
    
    /**Constructor for sound menu.*/
    public SoundMenu() {

    }
    
    /** Changes the loudness of the music.
     * 
     * @param loudness on a scale of 0-10 where 0 is the
     * softest and 10 is the loudest
     */
    public void changeVolume(int loudness) {
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
}
