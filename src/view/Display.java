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
import java.util.List;
import shared.Record;


public abstract class Display implements CommandHandler
{
    
    //Required functions for customer actions
    protected abstract void displayAddCustomerFrame();
    protected abstract void displayCustomers(List<Record> pRecords);
    protected abstract void displayCustomersFrame();
    
    //--------------------------------------------------------------------------
    // DisplayBareBones::DisplayBareBones (constructor)
    //

    public Display()
    {

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
    public void handleCommand(Command pCommand) 
    {
        
        switch (pCommand.getMessage()) {
            
            //customer actions
            case "display add customer frame":
                displayAddCustomerFrame();
                break;
                
            case "display customers frame":
                displayCustomersFrame();
                break;
                
            case "display customers":
                displayCustomers((List<Record>)pCommand.get("customers"));
                break;
                
        }
        
    }//end of Display::handleCommand
    //--------------------------------------------------------------------------
    
}//end of class Display
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------