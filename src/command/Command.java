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
    public final static String EXIT_PROGRAM = "EXIT_PROGRAM";
    
    public final static String DB_SUCCESS = "DB_SUCCESS";
    public final static String DB_FAILURE = "DB_FAILURE";
    public final static String DB_CONNECTION_ERROR = "DB_CONNECTION_ERROR";
    public final static String DB_CONNECTION_FIXED = "DB_CONNECTION_FIXED";
    
    public final static String ADD_CUSTOMER = "ADD_CUSTOMER";
    public final static String ADD_RACK = "ADD_RACK";
    public final static String BATCH = "BATCH";
    public final static String BATCH_DESCRIPTORS = "BATCH_DESCRIPTORS";
    public final static String CUSTOMER = "CUSTOMER";
    public final static String CUSTOMER_DESCRIPTORS = "CUSTOMER_DESCRIPTORS";
    public final static String CUSTOMERS = "CUSTOMERS";
    public final static String DELETE_CUSTOMER = "DELETE_CUSTOMER";
    public final static String DELETE_RACK = "DELETE_RACK";
    public final static String EDIT_CUSTOMER = "EDIT_CUSTOMER";
    public final static String EDIT_RACK = "EDIT_RACK";
    public final static String GET_CUSTOMERS = "GET_CUSTOMERS";
    public final static String GET_MOVEMENT_DESCRIPTORS = "GET_MOVEMENT_DESCRIPTORS";
    public final static String GET_RACKS = "GET_RACKS";
    public final static String GET_RECEIVEMENT_DESCRIPTORS = "GET_RECIEVEMENT_DESCRIPTORS";
    public final static String GET_TRANSFER_DESCRIPTORS = "GET_TRANSFER_DESCRIPTORS";
    public final static String MOVE_BATCH = "MOVE_BATCH";
    public final static String MOVEMENT = "MOVEMENT";
    public final static String MOVEMENT_DESCRIPTORS = "MOVEMENT_DESCRIPTORS";
    public final static String RACK = "RACK";
    public final static String RACK_DESCRIPTORS = "RACK_DESCRIPTORS";
    public final static String RACKS = "RACKS";
    public final static String RECEIVE_BATCH = "RECEIVE_BATCH";
    public final static String RECEIVEMENT = "RECEIVEMENT";
    public final static String RECIEVEMENT_AND_BATCH_DESCRIPTORS = "RECIEVEMENT_AND_BATCH_DESCRIPTORS";
    public final static String RECIEVEMENT_DESCRIPTORS = "RECIEVEMENT_DESCRIPTORS";
    public final static String SKOONIE_KEY = "SKOONIE_KEY";
    public final static String TRANSFER = "TRANSFER";
    public final static String TRANSFER_BATCH = "TRANSFER_BATCH";
    public final static String TRANSFER_DESCRIPTORS = "TRANSFER_DESCRIPTORS";
    
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