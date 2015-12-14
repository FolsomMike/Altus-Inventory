/*******************************************************************************
* Title: Display.java
* Author: Hunter Schoonover
* Date: 12/11/15
*
* Purpose:
*
* This 
* 
*/

//------------------------------------------------------------------------------

package view;

//------------------------------------------------------------------------------

import command.Command;
import command.CommandHandler;
import java.util.Map;


public abstract class Display implements CommandHandler
{
    
    //Required functions for customer actions
    protected abstract void displayCustomers();
    protected abstract void displayAddCustomer();
    
    private final CommandHandler view;
    
    //--------------------------------------------------------------------------
    // DisplayBareBones::DisplayBareBones (constructor)
    //

    public Display(CommandHandler pView)
    {
        
        view = pView;

    }//end of DisplayBareBones::DisplayBareBones (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // Display::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //
    
    public void init() 
    {
        
    }//end of Display::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // Display::handleCommand
    //
    // Performs different actions depending on pCommand.
    //
    
    @Override
    public void handleCommand(Map<String, String> pCommand) 
    {
        
        switch (pCommand.get("action")) {
            
            //customer actions
            case "display customers":
                displayCustomers();
                break;
                
            case "display add customer":
                displayAddCustomer();
                break;
                
        }
        
    }//end of Display::handleCommand
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // Display::sendCommandToController
    //
    // Sends pCommand to the controller through view.
    //
    
    protected void sendCommandToController(Map<String, String> pCommand) 
    {
        
        //address pCommand to controller
        Command.addressToController(pCommand);
        
        view.handleCommand(pCommand);
        
    }//end of Display::sendCommandToController
    //--------------------------------------------------------------------------
    
}//end of class Display
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------