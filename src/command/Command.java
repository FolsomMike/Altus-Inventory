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

import java.util.HashMap;
import java.util.Map;


//------------------------------------------------------------------------------
// class Command
//

public class Command {
    
    private final static String controllerCommandId  = "target=controller listeners|";
    private final static String viewCommandId  = "target=view listeners|";
    
    //--------------------------------------------------------------------------
    // Command::createControllerCommand
    //
    // Creates and returns a controller command by adding the controller command
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
    // Command::extractKeyValuePairs
    //
    // Extracts all of the key value pairs from pCommand and returns them in a
    // Map.
    //

    public static Map<String, String> extractKeyValuePairs(String pCommand)
    {
        
        Map<String, String> map = new HashMap<>();
        
        if (pCommand == null || pCommand.isEmpty()) { return map; }
        
        //pairs array will contain key-value pairs
        String[] pairs = pCommand.split("\\|");
        
        for (String pair : pairs) {
            //keyat index 0; value at index 1
            String[] keyValue = pair.split("=");
            map.put(keyValue[0], keyValue[1]);
        }
        
        return map;

    }//end of Command::extractKeyValuePairs
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
    
}//end of class Command
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------