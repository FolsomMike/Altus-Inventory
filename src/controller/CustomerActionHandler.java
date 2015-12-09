/*******************************************************************************
* Title: CustomerActionHandler.java
* Author: Hunter Schoonover
* Date: 12/09/15
*
* Purpose:
*
* This class listens for controller commands sent to the CommandHandler that 
* contain customer actions.
*
* Currently handles actions:
*   add customer
*   delete customer
*   update customer
*
*/

//------------------------------------------------------------------------------

package controller;

import command.CommandHandler;
import command.CommandListener;
import command.Command;
import model.MySQLDatabase;
import model.Record;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class CustomerActionHandler
//

public class CustomerActionHandler implements CommandListener
{
    
    private final MySQLDatabase db;
    
    //command array index variables
    int controllerIndex     = 0;
    int recordTypeIndex     = 1;
    int actionIndex         = 2;
    int skoonieKeyIndex     = 3;
    //where the key-value pairs start when Skoonie Key is NOT there
    int noSkKeyAttrsIndex   = 3;
    //where the key-value pairs start when Skoonie Key is there
    int skKeyAttrsIndex     = 4;
    
    //Record attributes --  record attributes are the keys to search for in a
    //                      command array for a specific record type
    private final String[] customerAttributes =    { 
                                                    "id",
                                                    "name",
                                                    "address_line_1",
                                                    "address_line_2",
                                                    "city",
                                                    "state",
                                                    "zip_code"
                                                    };
    
    //Table names -- back quotes so that they can be easily put in cmd strings
    private final String customersTable = "`CUSTOMERS`";

    //--------------------------------------------------------------------------
    // CustomerActionHandler::CustomerActionHandler (constructor)
    //

    public CustomerActionHandler(MySQLDatabase pDatabase)
    {

        db = pDatabase;

    }//end of CustomerActionHandler::CustomerActionHandler (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // CustomerActionHandler::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    public void init()
    {
        
        //register this as a controller listener
        CommandHandler.registerControllerListener(this);
        
        //register this as an error listener
        CommandHandler.registerErrorListener(this);

    }// end of CustomerActionHandler::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomerActionHandler::commandPerformed
    //
    // Performs different actions depending on pCommand.
    //
    // The function will do nothing if pCommand was not intended for controller
    // or if it is not a batch action command.
    //
    // Called by the CommandHandler everytime a controller command is performed.
    //

    @Override
    public void commandPerformed(String pCommand)
    {
        
        String[] cmdArray = pCommand.split("\\|");
        
        if (Command.isCustomerAddCommand(pCommand)) {
            addCustomer(cmdArray);
        }
        else if (Command.isCustomerDeleteCommand(pCommand)) {
            deleteRecord(cmdArray, customersTable);
        }
        else if (Command.isCustomerUpdateCommand(pCommand)) {
            updateRecord(cmdArray, customerAttributes, customersTable);
        }

    }//end of CustomerActionHandler::commandPerformed
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomerActionHandler::deleteRecord
    //
    // Deletes the Record associated with the Skoonie Key found in pCommand
    // from pTable.
    //
    // //DEBUG HSS// -- for testing purposes only. If this becomes a program
    //                  feature in the future, then a check needs to be
    //                  performed to see if a record can be deleted.
    //

    private void deleteRecord(String[] pCommand, String pTable)
    {
        
        //extract the skoonie key from pCommand
        Record r = new Record();
        r.setSkoonieKey(pCommand[skoonieKeyIndex]);
        
        db.deleteRecord(r, pTable);

    }//end of CustomerActionHandler::deleteRecord
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomerActionHandler::extractAttributes
    //
    // Extracts pAttributes from pCommand and puts them in pRec.
    //

    private void extractAttributes(Record pRec, String[] pCommand, 
                                            String[] pAttributes)
    {
        
        //return if there are no attributes to check for
        if (pAttributes.length <= 0) { return; }
        
        int attrsIndex = -1;
        for (int i=0; i<pCommand.length; i++) {
            if (pCommand[i].equals("begin attributes")) { attrsIndex = ++i; }
        }
        
        //return if there are no attributes in pCommand
        if (attrsIndex==-1) { return; }
        
        for (int i=attrsIndex; i<pCommand.length; i++) {
            
            //get key-value pair -- key index is 0; value index is 1
            String[] pair = pCommand[i].split(":");
            
            //check to see if key matches an attribute, store pair if it does
            for (String attr : pAttributes) {
                if (pair[0].equals(attr)) { 
                    pRec.addAttr(pair[0], pair[1]);
                    break;
                }
            }
            
        }

    }//end of CustomerActionHandler::extractAttributes
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomerActionHandler::addCustomer
    //
    // Adds a new customer using the information in pCommand.
    //

    private void addCustomer(String[] pCommand)
    {
        
        //record for the customer
        Record cusRecord = new Record();
        extractAttributes(cusRecord, pCommand, customerAttributes);
        
        //before we go any farther, verify the add
        if(!verifyAdd(cusRecord.getAttr("id"), cusRecord.getAttr("name"))) 
        { return; }

        //insert the batch into the database and store the skoonie key
        db.insertRecord(cusRecord, customersTable);

    }//end of CustomerActionHandler::addCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomerActionHandler::updateRecord
    //
    // Updates the Record in pTable associated with the Skoonie Key found in 
    // pCommand in pTable using the Skoonie Key and key-value pairs
    //

    private void updateRecord(String[] pCommand, String[] pAttributes,
                                String pTable)
    {
        
        Record r = new Record();
        r.setSkoonieKey(pCommand[skoonieKeyIndex]);
        extractAttributes(r, pCommand, pAttributes);
        
        db.updateRecord(r, pTable);

    }//end of CustomerActionHandler::updateRecord
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomerActionHandler::verifyAdd
    //
    // Verifies that a customer should be added to the database.
    //
    // If pCustomerId or pCustomerName already exists in the database or are
    // empty, then the customer should not be added.
    //
    // Returns true if customer should be added; false if not.
    //

    private boolean verifyAdd(String pCustomerId, String pCustomerName)
    {
        
        boolean shouldAdd = true;
        
        if (pCustomerId.isEmpty() || pCustomerName.isEmpty()
            || db.checkForValue(pCustomerId, customersTable, "id")
            || db.checkForValue(pCustomerName, customersTable, "name"))
        {
            shouldAdd = false;
        }
        
        return shouldAdd;

    }//end of CustomerActionHandler::verifyAdd
    //--------------------------------------------------------------------------
    
}//end of class CustomerActionHandler
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------