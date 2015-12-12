/******************************************************************************
* Title: CommandListener.java
* Author: Hunter Schoonover
* Date: 12/08/15
*
* Purpose:
*
* This Interface provides methods that the CommandHandler uses to notify
* listeners that a command has been performed.
*
*/

//-----------------------------------------------------------------------------

package command;

public interface CommandListener {
    
    public void commandPerformed(String pCommand);

}
