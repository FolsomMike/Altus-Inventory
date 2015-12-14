/******************************************************************************
* Title: Command.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//------------------------------------------------------------------------------
// class Command
//

public class Command {
    
    private final static List<CommandHandler> commandHandlers = new ArrayList<>();
    
    private String message;
    public String getMessage() { return message; }
    public void setMessage(String pMessage) { message = pMessage; }
    
    private final Map<String, Object> map = new HashMap<>();
    public Object get(String pKey) { return map.get(pKey); }
    public void put(String pKey, Object pValue) { map.put(pKey, pValue); }
    
    //--------------------------------------------------------------------------
    // Command::registerHandler (static)
    //
    // Registers pHandler as a command handler to be called every time a command
    // is performed.
    //

    public static void registerHandler(CommandHandler pHandler)
    {
        
        //return false if pHandler is null
        if (pHandler == null) { return; }
        
        commandHandlers.add(pHandler);

    }//end of Command::registerHandler (static)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // Command::Command (constructor)
    //

    public Command(String pMessage)
    {
        
        message = pMessage;

    }//end of Command::Command (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // Command::perform
    //
    // Performs the command by sending it to all of the handlers registered with
    // the Command class.
    //

    public void perform()
    {
        
        //return if there are no registered handlers
        if (commandHandlers.isEmpty()) { return; }
        
        for (CommandHandler h : commandHandlers) { h.handleCommand(this); }

    }//end of Command::perform
    //--------------------------------------------------------------------------
    
}//end of class Command
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------