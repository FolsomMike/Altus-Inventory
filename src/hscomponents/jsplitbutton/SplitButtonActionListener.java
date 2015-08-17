/*******************************************************************************
* Title: SplitButtonActionListener.java
* Author: Hunter Schoonover
* Date: 08/14/15
*
* Purpose:
*
* The listener interface for receiving action events.
* 
* The class that is interested in processing an action event implements this 
* interface, and the object created with that class is registered with a 
* component, using the component's addSplitButtonActionListenermethod. When the
* action event occurs, that object's buttonClicked or splitButtonClicked
* method is invoked.
* 
* The source code used was largely based on the Google SplitButtonActionListner.
*
*/

//------------------------------------------------------------------------------

package hscomponents.jsplitbutton;

//------------------------------------------------------------------------------

import java.awt.event.ActionEvent;
import java.util.EventListener;

public interface SplitButtonActionListener extends EventListener {

    /**
     * Invoked when the button part is clicked.
     */
    public void buttonClicked(ActionEvent e);

    /**
     * Invoked when split part is clicked.
     */
    public void splitButtonClicked(ActionEvent e);

}
