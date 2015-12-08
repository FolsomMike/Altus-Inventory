/******************************************************************************
* Title: Commands.java
* Author: Hunter Schoonover
* Date: 12/08/15
*
* Purpose:
*
* This class contains helper functions and variables for creating commands for
* the CommandHandler.
*
*/

//-----------------------------------------------------------------------------

package command;

//------------------------------------------------------------------------------

//------------------------------------------------------------------------------
// class Command
//

public class Command {
    
    public final static String controllerCommandId    = "controller|";
    public final static String viewCommandId          = "view|";
    
    //batch action Ids
    public final static String batchActionId    = controllerCommandId + "batch|";
    public final static String batchDeleteId    = batchActionId + "delete|";
    public final static String batchMoveId      = batchActionId + "move|";
    
    //--------------------------------------------------------------------------
    // Command::createControllerCommand
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

    }//end of Command::createControllerCommand
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // Command::createViewCommand
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

    }//end of Command::createViewCommand
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // Command::isControllerCommand
    //
    // Determines whether or not the passed in command is a controller command.
    //
    // Returns true if it is; false if not.
    //

    public static boolean isControllerCommand(String pCommand)
    {
        
        if (pCommand == null || pCommand.isEmpty()) { return false; }
        
        return pCommand.startsWith(controllerCommandId);

    }//end of Command::isControllerCommand
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // Command::isViewCommand
    //
    // Determines whether or not the passed in command is a view command.
    //
    // Returns true if it is; false if not.
    //

    public static boolean isViewCommand(String pCommand)
    {
        
        if (pCommand == null || pCommand.isEmpty()) { return false; }
        
        return pCommand.startsWith(viewCommandId);

    }//end of Command::isViewCommand
    //--------------------------------------------------------------------------
    
    
}//end of class Command
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------