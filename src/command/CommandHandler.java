/*******************************************************************************
* Title: CommandHandler.java
* Author: Hunter Schoonover
* Date: 12/06/15
*
* Purpose:
*
* This class is the Command in a Command-Model-View-Controller architecture.
* 
* It is how the MainController and MainView communicate.
*
*/

//------------------------------------------------------------------------------

package command;

//------------------------------------------------------------------------------

import java.util.ArrayList;
import java.util.List;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class CommandHandler
//

public class CommandHandler {
    
    //the view and controller listeners in one list
    private final static List<CommandListener> 
                listeners = new ArrayList<>();
    
    //view listeners
    private final static List<CommandListener> 
                viewListeners = new ArrayList<>();
    
    //controller listeners
    private final static List<CommandListener> 
                controllerListeners = new ArrayList<>();  
    
    //--------------------------------------------------------------------------
    // CommandHandler::performCommand
    //
    // Performs pCommand by notifying listeners.
    //
    // Only controllerListeners will be notified of controller commands; only
    // viewListeners notified of view commands.
    //
    // Command that can be distinguished are sent to all listeners.
    //

    public static void performCommand(String pCommand)
    {
        
        if (pCommand == null || pCommand.isEmpty()) { return; }
        
        if (Command.isControllerCommand(pCommand)) { 
            notifyListeners(controllerListeners, pCommand);
        }
        else if (Command.isViewCommand(pCommand)) { 
            notifyListeners(viewListeners, pCommand);
        }
        else { notifyListeners(listeners, pCommand); }

    }//end of CommandHandler::performCommand
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CommandHandler::performControllerCommand
    //
    // Performs pCommand as a controller command.
    //
    // If pCommand is not a controller commmand, then it is turned into one.
    //

    public static void performControllerCommand(String pCommand)
    {
        
        if (pCommand == null || pCommand.isEmpty()) { return; }
        
        if (!Command.isControllerCommand(pCommand)) { 
            pCommand = Command.createControllerCommand(pCommand);
        }
        
        performCommand(pCommand);
        
    }//end of CommandHandler::performControllerCommand
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CommandHandler::performViewCommand
    //
    // Performs pCommand as a view command.
    //
    // If pCommand is not a view commmand, then it is turned into one.
    //

    public static void performViewCommand(String pCommand)
    {
        
        if (pCommand == null || pCommand.isEmpty()) { return; }
        
        if (!Command.isViewCommand(pCommand)) { 
            pCommand = Command.createViewCommand(pCommand);
        }
        
        performCommand(pCommand);
        
    }//end of CommandHandler::performViewCommand
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CommandHandler::notifyListeners
    //
    // Notifies all of the listeners in pListeners that pCommand has been
    // performed.
    //

    public static void notifyListeners( List<CommandListener> pListeners,
                                        String pCommand)
    {
        
        if (pListeners == null || pListeners.isEmpty()) { return; }
        
        for (CommandListener l : pListeners) {
            if (l == null) { pListeners.remove(l); continue; }           
            l.commandPerformed(pCommand);
        }

    }//end of CommandHandler::notifyListeners
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // CommandHandler::registerControllerListener
    //
    // Registers pListener as a controller listener, meaning that every time a
    // command intended for the controller is performed, the listener will be 
    // notified that the command was performed.
    //

    public static void registerControllerListener(CommandListener pListener)
    {
        
        if (pListener == null) { return; }
        
        controllerListeners.add(pListener);
        
        listeners.add(pListener);

    }//end of CommandHandler::registerControllerListener
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CommandHandler::registerViewListener
    //
    // Registers pListener as a view listener, meaning that every time a
    // command intended for the view is performed, the listener will be notified
    // that the command was performed.
    //

    public static void registerViewListener(CommandListener pListener)
    {
        
        if (pListener == null) { return; }
        
        viewListeners.add(pListener);
        
        listeners.add(pListener);

    }//end of CommandHandler::registerViewListener
    //--------------------------------------------------------------------------

}//end of class CommandHandler
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------