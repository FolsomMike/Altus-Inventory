/*******************************************************************************
* Title: CustomerActionHandler.java
* Author: Hunter Schoonover
* Date: 12/10/15
*
* Purpose:
*
* This class handles commands pertaining to customers
*
* Currently handles actions:
*   add customer
*
*/

//------------------------------------------------------------------------------

package controller;

import command.CommandListener;
import command.Command;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.ConfigFile;
import model.MySQLDatabase;
import model.Record;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class CustomerActionHandler
//

public class CustomerActionHandler extends RecordActionHandler
{

    //--------------------------------------------------------------------------
    // CustomerActionHandler::CustomerActionHandler (constructor)
    //

    public CustomerActionHandler(MySQLDatabase pDatabase, ConfigFile pAttrsFile)
    {

       super(pDatabase, pAttrsFile);

    }//end of CustomerActionHandler::CustomerActionHandler (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // CustomerActionHandler::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    @Override
    public void init()
    {
        
        super.init();

    }// end of CustomerActionHandler::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomerActionHandler::commandPerformed
    //
    // Performs different actions depending on pCommand.
    //
    // The function will do nothing if pCommand was not intended for controller
    // or if it is not a customer action command.
    //
    // Called by the CommandHandler everytime a controller command is performed.
    //

    @Override
    public void commandPerformed(String pCommand)
    {
        
        //return if this is not a controller command or does not pertain to a
        //batch
        if(!Command.isControllerCommand(pCommand) 
                && !pCommand.contains("record-type=customer")) { return; }
        
        Map<String, String> command = Command.extractKeyValuePairs(pCommand);
        
        switch (command.get("action")) {
            case "add":
                addCustomer(command);
                break;
        }

    }//end of CustomerActionHandler::commandPerformed
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomerActionHandler::addCustomer
    //
    // Adds a customer using the information in pCommand.
    //

    private void addCustomer(Map<String, String> pCommand)
    {
        
        getDatabase().connectToDatabase();
        
        //add the customer to the database
        Record customerRecord = new Record();
        getValues(customerRecord, pCommand, getCustomerKeys());
        getDatabase().insertRecord(customerRecord, getCustomersTableName());
        
        getDatabase().closeDatabaseConnection();

    }//end of CustomerActionHandler::addCustomer
    //--------------------------------------------------------------------------
    
}//end of class CustomerActionHandler
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------