/*******************************************************************************
* Title: CommandHandler.java
* Author: Hunter Schoonover
* Date: 12/13/15
*
* Purpose:
*
* This interface is the Command in a Command-Model-View-Controller architecture.
* 
* It is how the MainController and MainView communicate.
*
*/

//------------------------------------------------------------------------------

package command;

//------------------------------------------------------------------------------

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// interface CommandHandler
//

public interface CommandHandler {
    
    public void handleCommand(Command pCommand);

}//end of interface CommandHandler
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------