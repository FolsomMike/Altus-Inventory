/******************************************************************************
* Title: Commands.java
* Author: Hunter Schoonover
* Date: 12/08/15
*
* Purpose:
*
* This class contains the commands that are sent to the CommandHandler. Using
* variables for commands makes changing a command easier.
* 
* It also contains some helper functions to help with creating a command or 
* determining a command's type.
*
*/

//-----------------------------------------------------------------------------

package command;

//------------------------------------------------------------------------------

//------------------------------------------------------------------------------
// class Commands
//

public class Commands {
    
    public final static String controllerCommandId    = "controller|";
    public final static String viewCommandId          = "view|";
    
    //--------------------------------------------------------------------------
    // Commands::createControllerCommand
    //
    // Creates and returns a controller command by adding the contorller command
    // id to the beginning of pCommand.
    //

    public static String createControllerCommand(String pCommand)
    {
        
        //if pCommand is null or empty then just return an empty string
        if (pCommand == null || pCommand.isEmpty()) { return ""; }
        
        //if pCommand is already a controller commmand, then just give it back
        if (isControllerCommand(pCommand)) { return pCommand; }
        
        return controllerCommandId + pCommand;

    }//end of Commands::createControllerCommand
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // Commands::createViewCommand
    //
    // Creates and returns a view command by adding the view command id to the
    // beginning of pCommand.
    //

    public static String createViewCommand(String pCommand)
    {
        
        //if pCommand is null or empty then just return an empty string
        if (pCommand == null || pCommand.isEmpty()) { return ""; }
        
        //if pCommand is already a view commmand, then just give it back
        if (isViewCommand(pCommand)) { return pCommand; }
        
        return viewCommandId + pCommand;

    }//end of Commands::createViewCommand
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // Commands::isControllerCommand
    //
    // Determines whether or not the passed in command is a controller command.
    //
    // Returns true if it is; false if not.
    //

    public static boolean isControllerCommand(String pCommand)
    {
        
        if (pCommand == null || pCommand.isEmpty()) { return false; }
        
        return pCommand.startsWith(controllerCommandId);

    }//end of Commands::isControllerCommand
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // Commands::isViewCommand
    //
    // Determines whether or not the passed in command is a view command.
    //
    // Returns true if it is; false if not.
    //

    public static boolean isViewCommand(String pCommand)
    {
        
        if (pCommand == null || pCommand.isEmpty()) { return false; }
        
        return pCommand.startsWith(viewCommandId);

    }//end of Commands::isViewCommand
    //--------------------------------------------------------------------------
    
    
}//end of class Commands
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------