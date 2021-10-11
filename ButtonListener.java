

import java.awt.event.*;
/**
 * Button Listener checks for button presses
 */
public class ButtonListener extends MouseAdapter implements ActionListener
{
    private Display master;
    
    /**
     * Basic contructor for the buttonlistener
     * 
     * @param  Display mast  Sets the display this listener will listen to
     */
    public ButtonListener (Display mast) {
        this.master = mast;
    }
    
    /**
     * Method that is called when an action is performed in the display set
     * 
     * @param  ActionEvent e  This is the action that is performed to be passed into the display
     */
    public void actionPerformed (ActionEvent e) {
        Object src = e.getSource();
        master.handleButtonPress(src);
    }
}