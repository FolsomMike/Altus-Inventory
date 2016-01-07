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
    
    //messages and keys used throuhout the program for commands
    public final static String SUCCESS = "SUCCESS";
    public final static String FAILURE = "FAILURE";
    public final static String DB_CONNECTION_ERROR = "DB_CONNECTION_ERROR";
    
    public final static String RECORD_KEY = "RECORD_KEY";
    public final static String SKOONIE_KEY = "SKOONIE_KEY";
    
    public final static String TABLE = "TABLE";
    
    public final static String CUSTOMER = "CUSTOMER";
    public final static String CUSTOMERS = "CUSTOMERS";
    public final static String ADD_CUSTOMER = "ADD_CUSTOMER";
    public final static String DELETE_CUSTOMER = "DELETE_CUSTOMER";
    public final static String EDIT_CUSTOMER = "EDIT_CUSTOMER";
    public final static String GET_CUSTOMERS = "GET_CUSTOMERS";
    
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
    
    //--------------------------------------------------------------------------
    // Command::copy
    //
    // Copies all of the data in the current instance of Command into a new
    // Command object. That new object is returned.
    //

    public Command copy()
    {
        
        Command c = new Command(message);
        for (Map.Entry<String, Object> e : map.entrySet()) {
            c.put(e.getKey(), e.getValue());
        }
        
        return c;

    }//end of Command::copy
    //--------------------------------------------------------------------------
    
}//end of class Command
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------