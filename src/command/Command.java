/******************************************************************************
* Title: Commands.java
* Author: Hunter Schoonover
* Date: 12/08/15
*
* Purpose:
*
* This class contains helper functions and variables for commands.
*/

//-----------------------------------------------------------------------------

package command;

//------------------------------------------------------------------------------

import java.util.Map;


//------------------------------------------------------------------------------
// class Command
//

public class Command {
    
    private final static String controllerId  = "controller";
    private final static String viewId  = "view";
    
    //--------------------------------------------------------------------------
    // Command::addressToController
    //
    // Addresses the passed in command to the controller.
    //

    public static void addressToController(Map<String, String> pCommand)
    {
        
        //if pCommand is null then return
        if (pCommand == null) { return; }
        
        //if pCommand is already addressed to controller, then do nothing
        if (isAddressedToController(pCommand)) { return; }
        
        //set the source to controller
        pCommand.put("src", controllerId);

    }//end of Command::addressToController
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // Command::addressToView
    //
    // Addresses the passed in command to the view.
    //

    public static void addressToView(Map<String, String> pCommand)
    {
        
        //if pCommand is null then return
        if (pCommand == null) { return; }
        
        //if pCommand is already addressed to view, then do nothing
        if (isAddressedToView(pCommand)) { return; }
        
        //set the source to view
        pCommand.put("src", viewId);

    }//end of Command::addressToController
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // Command::isAddressedToController
    //
    // Determines whether or not the passed in command is addressed to the
    // controller.
    //
    // Returns true if it is; false if not.
    //

    public static boolean isAddressedToController(Map<String, String> pCommand)
    {
        
        //return false if pCommand is null or empty
        if (pCommand == null || pCommand.isEmpty()) { return false; }
        
        return pCommand.containsKey(controllerId);

    }//end of Command::isAddressedToController
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // Command::isAddressedToView
    //
    // Determines whether or not the passed in command is addressed to the view.
    //
    // Returns true if it is; false if not.
    //

    public static boolean isAddressedToView(Map<String, String> pCommand)
    {
        
        //return false if pCommand is null or empty
        if (pCommand == null || pCommand.isEmpty()) { return false; }
        
        return pCommand.containsKey(viewId);

    }//end of Command::isAddressedToView
    //--------------------------------------------------------------------------
    
}//end of class Command
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------