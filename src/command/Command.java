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
    
    public final static String controllerCommandId  = "controller|";
    public final static String errorCommandId       = "error|";
    public final static String viewCommandId        = "view|";
    
    //batch action Ids
    public final static String batchActionId    = controllerCommandId + "batch|";
    public final static String batchDeleteId    = batchActionId + "delete|";
    public final static String batchMoveId      = batchActionId + "move|";
    public final static String batchReceiveId   = batchActionId + "receive|";
    public final static String batchUpdateId    = batchActionId + "update|";
    
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
    // Command::createErrorCommand
    //
    // Creates and returns an error command by adding the error command id to 
    // the beginning of pCommand.
    //

    public static String createErrorCommand(String pCommand)
    {
        
        //if pCommand is null or empty then just return an empty string
        if (pCommand == null || pCommand.isEmpty()) { return ""; }
        
        //if pCommand is already an error commmand, then just give it back
        if (isErrorCommand(pCommand)) { return pCommand; }
        
        return errorCommandId + pCommand;

    }//end of Command::createErrorCommand
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
    // Command::isBatchDeleteCommand
    //
    // Determines whether or not the passed in command is a batch delete
    // command.
    //
    // Returns true if it is; false if not.
    //

    public static boolean isBatchDeleteCommand(String pCommand)
    {
        
        if (pCommand == null || pCommand.isEmpty()) { return false; }
        
        return pCommand.startsWith(batchDeleteId);

    }//end of Command::isBatchDeleteCommand
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // Command::isBatchMoveCommand
    //
    // Determines whether or not the passed in command is a batch move command.
    //
    // Returns true if it is; false if not.
    //

    public static boolean isBatchMoveCommand(String pCommand)
    {
        
        if (pCommand == null || pCommand.isEmpty()) { return false; }
        
        return pCommand.startsWith(batchMoveId);

    }//end of Command::isBatchMoveCommand
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // Command::isBatchReceiveCommand
    //
    // Determines whether or not the passed in command is a batch recieve
    // command.
    //
    // Returns true if it is; false if not.
    //

    public static boolean isBatchReceiveCommand(String pCommand)
    {
        
        if (pCommand == null || pCommand.isEmpty()) { return false; }
        
        return pCommand.startsWith(batchReceiveId);

    }//end of Command::isBatchReceiveCommand
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // Command::isBatchUpdateCommand
    //
    // Determines whether or not the passed in command is a batch update
    // command.
    //
    // Returns true if it is; false if not.
    //

    public static boolean isBatchUpdateCommand(String pCommand)
    {
        
        if (pCommand == null || pCommand.isEmpty()) { return false; }
        
        return pCommand.startsWith(batchUpdateId);

    }//end of Command::isBatchUpdateCommand
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
    // Command::isErrorCommand
    //
    // Determines whether or not the passed in command is an error command.
    //
    // Returns true if it is; false if not.
    //

    public static boolean isErrorCommand(String pCommand)
    {
        
        if (pCommand == null || pCommand.isEmpty()) { return false; }
        
        return pCommand.startsWith(errorCommandId);

    }//end of Command::isErrorCommand
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