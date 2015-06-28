package gui;

import java.util.List;

/**Interface for the menus.
 * 
 * @author Connie Chang
 * @author Kevin Zhang
 * @author Stephanie Chew
 *
 */
public interface Menu {

    /**The user selects an option.*/
    public void select();
    
    /** Get the list of menu options that user can choose from.
     * @return optionsList the list of options
     */
    public List<String> getOptionsList();
    
   
}
