/*******************************************************************************
* Title: BatchController.java
* Author: Hunter Schoonover
* Date: 12/08/15
*
* Purpose:
*
* This class handles commands pertaining to batches. 
*
* Currently handles batch actions:
*   receive batch
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
// class BatchActionHandler
//

public class BatchActionHandler implements CommandListener
{
    
    private final MySQLDatabase db;
    
    private final ConfigFile attrsConfigFile;
    
    //Command keys -- keys to look for when handling a command
    private final List<String> batchKeys = new ArrayList<>();
    private final List<String> receivementKeys = new ArrayList<>();
    
    //Table names -- back quotes so that they can be easily put in cmd strings
    private final String batchesTable = "`BATCHES`";
    private final String receivementsTable = "`RECEIVEMENTS`";

    //--------------------------------------------------------------------------
    // BatchActionHandler::BatchActionHandler (constructor)
    //

    public BatchActionHandler(MySQLDatabase pDatabase, ConfigFile pAttrsFile)
    {

        db = pDatabase;
        attrsConfigFile = pAttrsFile;

    }//end of BatchActionHandler::BatchActionHandler (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // BatchActionHandler::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    public void init()
    {
        
        //register this as a controller listener
        CommandHandler.registerControllerListener(this);
        
        //setup the batch keys
        setupBatchKeys();
        
        //setup the receivement keys
        setupReceivementKeys();

    }// end of BatchActionHandler::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // BatchActionHandler::commandPerformed
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
                && !pCommand.contains("record-type=batch")) { return; }
        
        Map<String, String> command = Command.extractKeyValuePairs(pCommand);
        
        switch (command.get("action")) {
            case "receive":
                receiveBatch(command);
                break;
                
            case "delete": //DEBUG HSS -- for testing purposes only
                deleteBatch(command);
                break;
                
            case "update":
                updateBatch(command);
                break;
        }

    }//end of BatchActionHandler::commandPerformed
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // BatchActionHandler::deleteBatch
    //
    // Deletes a batch and the receivement associated with that batch using the
    // information in pCommand.
    //
    // //DEBUG HSS// -- testing purposes only
    //

    private void deleteBatch(Map<String, String> pCommand)
    {
        
        //delete the batch
        Record batchRecord = new Record();
        getValues(batchRecord, pCommand, batchKeys);
        db.deleteRecord(batchRecord, batchesTable);
        
        //delete the receivement associated with the batch
        ArrayList<Record> receivementRecords = db.getRecords(receivementsTable);
        String batchKey = batchRecord.getValue("skoonie_key");
        for (Record receivement : receivementRecords) {
            if (receivement.getValue("batch_key").equals(batchKey)) {
                db.deleteRecord(receivement, receivementsTable);
                break;
            }
        }
       
        
    }//end of BatchActionHandler::deleteBatch
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // BatchActionHandler::getValues
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

    }//end of BatchActionHandler::getValues
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // BatchActionHandler::receiveBatch
    //
    // Receives a batch using the information in pCommand.
    //
    // Two records are created and inserted into the database:
    //      one for a receivement
    //      one for a new batch
    //

    private void receiveBatch(Map<String, String> pCommand)
    {
        
        //record for the batch
        Record batchRecord = new Record();
        getValues(batchRecord, pCommand, batchKeys);
       
        //record for the receivement
        Record receiveRecord = new Record();
        getValues(receiveRecord, pCommand, receivementKeys);

        //insert the batch into the database and store the skoonie key
        int skoonieKey = db.insertRecord(batchRecord, batchesTable);

        //add the batch skoonie key to the receivement
        receiveRecord.addColumn("batch_key", Integer.toString(skoonieKey));

        //insert the receivement into the database
        db.insertRecord(receiveRecord, receivementsTable);

    }//end of BatchActionHandler::receiveBatch
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // BatchActionHandler::setupBatchKeys
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
        batchKeys.add("skoonie_key");
        //id or batch_id can be used as key -- value will be stored using "id"
        batchKeys.add("id/batch_id");
        batchKeys.add("quantity");
        batchKeys.add("storage_location_key");
        
        //optional keys that are grabbed from the attributes file
        //WIP HSS//--add code to do this man

    }//end of BatchActionHandler::setupBatchKeys
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // BatchActionHandler::setupReceivementKeys
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
        receivementKeys.add("skoonie_key");
        //id or receivement_id can be used as key -- value will 
        //be stored into a record using "id" as the column name
        receivementKeys.add("id/receivement_id");
        receivementKeys.add("date");
        receivementKeys.add("quantity");
        receivementKeys.add("batch_key");
        receivementKeys.add("storage_location_key");
        
        //optional keys that are grabbed from the attributes file
        //WIP HSS//--add code to do this man

    }//end of BatchActionHandler::setupReceivementKeys
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // BatchActionHandler::updateBatch
    //
    // Updates a batch using the information inside pCommand.
    //

    private void updateBatch(Map<String, String> pCommand)
    {
        
        //record for the batch
        Record batchRecord = new Record();
        getValues(batchRecord, pCommand, batchKeys);
        db.updateRecord(batchRecord, batchesTable);
        
    }//end of BatchActionHandler::updateBatch
    //--------------------------------------------------------------------------
    
}//end of class BatchActionHandler
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------