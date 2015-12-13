/*******************************************************************************
* Title: BatchController.java
* Author: Hunter Schoonover
* Date: 12/08/15
*
* Purpose:
*
* This class contains general functions and performs general operations
* necessary to listen for record actions.
* 
* No record action commands are handled in this class. It is intended to be 
* extended by children that handle actions for specific records, such as 
* RecordActionHandler or CustomerActionHandler. Actions should be handled in
* those children classes.
*
*/

//------------------------------------------------------------------------------

package controller;

import command.CommandHandler;
import command.CommandListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.MySQLDatabase;
import model.Record;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class RecordActionHandler
//

public abstract class RecordActionHandler implements CommandListener
{
    
    @Override
    public abstract void commandPerformed(String pCommand);
    
    private final MySQLDatabase db;
    public final MySQLDatabase getDatabase() { return db; }
    
    //Command keys -- keys to look for when handling a command
    private final List<String> batchKeys = new ArrayList<>();
    public final List<String> getBatchKeys() { return batchKeys; }
    
    private final List<String> customerKeys = new ArrayList<>();
    public final List<String> getCustomerKeys() { return customerKeys; }
    
    private final List<String> movementKeys = new ArrayList<>();
    public final List<String> getMovementKeys() { return movementKeys; }
    
    private final List<String> receivementKeys = new ArrayList<>();
    public final List<String> getReceivementKeys() { return receivementKeys; }
    
    //Table names -- back quotes so that they can be easily put in cmd strings
    private final String batchesTable = "`BATCHES`";
    public final String getBatchesTableName() { return batchesTable; }
    
    private final String customersTable = "`CUSTOMERS`";
    public final String getCustomersTableName() { return customersTable; }
    
    private final String movementsTable = "`MOVEMENTS`";
    public final String getMovementsTableName() { return movementsTable; }
    
    private final String receivementsTable = "`RECEIVEMENTS`";
    public final String getReceivementsTableName() { return receivementsTable; }

    //--------------------------------------------------------------------------
    // RecordActionHandler::RecordActionHandler (constructor)
    //

    public RecordActionHandler(MySQLDatabase pDatabase)
    {

        db = pDatabase;

    }//end of RecordActionHandler::RecordActionHandler (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // RecordActionHandler::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    public void init()
    {
        
        //register this as a controller listener
        CommandHandler.registerControllerListener(this);
        
        //setup the batch keys
        setupBatchKeys();
        
        //setup the customer keys
        setupCustomerKeys();
        
        //setup the movement keys
        setupMovementKeys();
        
        //setup the receivement keys
        setupReceivementKeys();

    }// end of RecordActionHandler::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordActionHandler::deleteRecord
    //
    // Deletes the record assoicated with the skoonie key found in pCommand from
    // pTable.
    //
    // //DEBUG HSS// -- testing purposes only
    //

    protected void deleteRecord(Map<String, String> pCommand, String pTable)
    {
        
        db.connectToDatabase();
        
        //delete the record
        Record record = new Record();
        record.setSkoonieKey(pCommand.get("skoonie_key"));
        db.deleteRecord(record, pTable);
       
        db.closeDatabaseConnection();
        
    }//end of RecordActionHandler::deleteRecord
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordActionHandler::getValues
    //
    // The value of every key in pKeyValuePairs that matches one of the keys in 
    // pKeys is added to pRec, using the key as the column.
    //
    // If a string in pKeys contains two keys for one value, then the first key
    // is used as the column.
    //

    protected void getValues(Record pRec, Map<String, String> pKeyValuePairs, 
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

    }//end of RecordActionHandler::getValues
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordActionHandler::setupBatchKeys
    //
    // Sets up the batch keys list by adding some keys that are hard coded
    // into the program to the list and then adding the optional attribute keys
    // grabbed from the config file to that same list.
    //
    // NOTE:    The keys list is a list of keys that need to be extracted from a
    //          command string when dealing with a batch.
    //          Try to make sure that the keys and column names in the batches
    //          table match. If there is a special case where the key cannot be
    //          the same as a column name, prepend the column name followed by a
    //          slash to the key: "column_name/key".
    //

    private void setupBatchKeys()
    {
        
        //keys hard coded into the program
        batchKeys.add("id/batch_id"); //"id" used as column name
        batchKeys.add("quantity");
        batchKeys.add("storage_location_key");
        
        //optional keys that are grabbed from the attributes file
        //WIP HSS//--add code to do this man

    }//end of RecordActionHandler::setupBatchKeys
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordActionHandler::setupCustomerKeys
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

    }//end of RecordActionHandler::setupCustomerKeys
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordActionHandler::setupMovementKeys
    //
    // Sets up the movement keys list by adding some keys that are hard coded
    // into the program to the list and then adding the optional attribute keys
    // grabbed from the config file to that same list.
    //
    // NOTE:    The keys list is a list of keys that need to be extracted from a
    //          command string when dealing with a movement.
    //          Try to make sure that the keys and column names in the batches
    //          table match. If there is a special case where the key cannot be
    //          the same as a column name, prepend the column name followed by a
    //          slash to the key: "column_name/key".
    //

    private void setupMovementKeys()
    {
        
        //keys hard coded into the program
        movementKeys.add("id/move_id"); //"id" used as column name
        movementKeys.add("date");
        movementKeys.add("storage_location_key");
        
        //optional keys that are grabbed from the attributes file
        //WIP HSS//--add code to do this man

    }//end of RecordActionHandler::setupMovementKeys
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordActionHandler::setupReceivementKeys
    //
    // Sets up the receivement keys list by adding some keys that are hard coded
    // into the program to the list and then adding the optional attribute keys
    // grabbed from the config file to that same list.
    //
    // NOTE:    The keys list is a list of keys that need to be extracted from a
    //          command string when dealing with a receivement.
    //          Try to make sure that the keys and column names in the batches
    //          table match. If there is a special case where the key cannot be
    //          the same as a column name, prepend the column name followed by a
    //          slash to the key: "column_name/key".
    //

    private void setupReceivementKeys()
    {
        
        //keys hard coded into the program
        receivementKeys.add("id/receivement_id"); //"id" used as column name
        receivementKeys.add("date");
        receivementKeys.add("quantity");
        receivementKeys.add("batch_key");
        receivementKeys.add("storage_location_key");
        
        //optional keys that are grabbed from the attributes file
        //WIP HSS//--add code to do this man

    }//end of RecordActionHandler::setupReceivementKeys
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordActionHandler::updateRecord
    //
    // Updates the record in pTable associated with the skoonie key found in 
    // pCommand using information extracted from pCommand using pKeys.

    protected void updateRecord(Map<String, String> pCommand, 
                                List<String> pKeys, String pTable)
    {
        
        db.connectToDatabase();
        
        //update the record
        Record record = new Record();
        record.setSkoonieKey(pCommand.get("skoonie_key"));
        getValues(record, pCommand, pKeys);
        db.updateRecord(record, pTable);
        
        db.closeDatabaseConnection();
        
    }//end of RecordActionHandler::updateRecord
    //--------------------------------------------------------------------------
    
}//end of class RecordActionHandler
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------