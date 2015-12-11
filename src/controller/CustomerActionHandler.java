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

import command.CommandHandler;
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

public class CustomerActionHandler implements CommandListener
{
    
    private final MySQLDatabase db;
    
    private final ConfigFile attrsConfigFile;
    
    //Command keys -- keys to look for when handling a command
    private final List<String> customerKeys = new ArrayList<>();
    
    //Table names -- back quotes so that they can be easily put in cmd strings
    private final String customersTable = "`CUSTOMERS`";

    //--------------------------------------------------------------------------
    // CustomerActionHandler::CustomerActionHandler (constructor)
    //

    public CustomerActionHandler(MySQLDatabase pDatabase, ConfigFile pAttrsFile)
    {

        db = pDatabase;
        attrsConfigFile = pAttrsFile;

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
        
        //setup the batch keys
        setupCustomerKeys();

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
        
        db.connectToDatabase();
        
        //add the customer to the database
        Record customerRecord = new Record();
        getValues(customerRecord, pCommand, customerKeys);
        db.insertRecord(customerRecord, customersTable);
        
        db.closeDatabaseConnection();

    }//end of CustomerActionHandler::addCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomerActionHandler::getValues
    //
    // The value of every key in pKeyValuePairs that matches one of the keys in 
    // pKeys is added to pRec, using the key as the column.
    //
    // If a string in pKeys contains two keys for one value, then the first key
    // is used as the column.
    //

    private void getValues(Record pRec, Map<String, String> pKeyValuePairs, 
                            List<String> pKeys)
    {
        
        //return if there are no keys to check for
        if (pKeys.isEmpty() || pKeyValuePairs.isEmpty()) { return; }
        
        for (String keys : pKeys) {
            
            //since multiple keys can relate to one value, split up keys into
            //an array and look for each key in the array
            String[] allKeys = keys.split("/");
            for (String key : allKeys) {
                
                //if one of the keys is found, then add the value retrieved
                //using that key to pRec, using the first key in allKeys as the
                //column
                String value;
                if((value=pKeyValuePairs.get(key)) != null) { 
                    pRec.addColumn(allKeys[0], value);
                    break; 
                }
                
            }

        }

    }//end of CustomerActionHandler::getValues
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomerActionHandler::setupCustomerKeys
    //
    // Sets up the customer keys list by adding some keys that are hard coded
    // into the program to the list and then adding the optional attribute keys
    // grabbed from the config file to that same list.
    //
    // NOTE:    The keys list is a list of keys that need to be extracted from a
    //          command string when dealing with a customer.
    //          Try to make sure that the keys and column names in the customers
    //          table match. If there is a special case where the key cannot be
    //          the same as a column name, prepend the column name followed by a
    //          slash to the key: "column_name/key".
    //

    private void setupCustomerKeys()
    {
        
        //keys hard coded into the program
        customerKeys.add("id");
        customerKeys.add("name");
        
        //optional keys that are grabbed from the attributes file
        //WIP HSS//--add code to do this man

    }//end of CustomerActionHandler::setupCustomerKeys
    //--------------------------------------------------------------------------
    
}//end of class CustomerActionHandler
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------