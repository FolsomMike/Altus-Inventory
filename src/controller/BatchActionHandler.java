/*******************************************************************************
* Title: BatchActionHandler.java
* Author: Hunter Schoonover
* Date: 12/08/15
*
* Purpose:
*
* This class handles commands pertaining to batches. 
*
* Currently handles actions:
*   delete batch
*   move batch
*   receive batch
*   update batch
*
*/

//------------------------------------------------------------------------------

package controller;

import command.Command;
import java.util.ArrayList;
import java.util.Map;
import model.ConfigFile;
import model.MySQLDatabase;
import model.Record;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class BatchActionHandler
//

public class BatchActionHandler extends RecordActionHandler
{

    //--------------------------------------------------------------------------
    // BatchActionHandler::BatchActionHandler (constructor)
    //

    public BatchActionHandler(MySQLDatabase pDatabase, ConfigFile pAttrsFile)
    {

        super(pDatabase, pAttrsFile);

    }//end of BatchActionHandler::BatchActionHandler (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // BatchActionHandler::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    @Override
    public void init()
    {
        
        super.init();

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
                
            case "move":
            moveBatch(command);
                break;
                
            case "update":
                updateRecord(command, getBatchKeys(), getBatchesTableName());
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
        
        getDatabase().connectToDatabase();
        
        //delete the batch
        Record batchRecord = new Record();
        batchRecord.setSkoonieKey(pCommand.get("skoonie_key"));
        getDatabase().deleteRecord(batchRecord, getBatchesTableName());
        
        //delete the receivement associated with the batch
        ArrayList<Record> receivementRecords 
                        = getDatabase().getRecords(getReceivementsTableName());
        String batchKey = batchRecord.getSkoonieKey();
        for (Record receivement : receivementRecords) {
            if (receivement.getValue("batch_key").equals(batchKey)) {
                getDatabase().deleteRecord(receivement, 
                                            getReceivementsTableName());
                break;
            }
        }
       
        getDatabase().closeDatabaseConnection();
        
    }//end of BatchActionHandler::deleteBatch
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // BatchActionHandler::moveBatch
    //
    // Moves a batch using the information in pCommand.
    //

    private void moveBatch(Map<String, String> pCommand)
    {
        
        getDatabase().connectToDatabase();
        
        //update the batch in the database
        Record batchRecord = new Record();
        batchRecord.setSkoonieKey(pCommand.get("skoonie_key"));
        getValues(batchRecord, pCommand, getBatchKeys());
        getDatabase().updateRecord(batchRecord, getBatchesTableName());
       
        //document the movement
        Record moveRecord = new Record();
        getValues(moveRecord, pCommand, getMovementKeys());
        moveRecord.addColumn("batch_key", batchRecord.getSkoonieKey());
        getDatabase().insertRecord(moveRecord, getMovementsTableName());
        
        getDatabase().closeDatabaseConnection();

    }//end of BatchActionHandler::moveBatch
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
        
        getDatabase().connectToDatabase();
        
        //record for the batch
        Record batchRecord = new Record();
        getValues(batchRecord, pCommand, getBatchKeys());
       
        //record for the receivement
        Record receiveRecord = new Record();
        getValues(receiveRecord, pCommand, getReceivementKeys());

        //insert the batch into the database and store the skoonie key
        int skoonieKey = getDatabase().insertRecord(batchRecord, 
                                                        getBatchesTableName());

        //add the batch skoonie key to the receivement
        receiveRecord.addColumn("batch_key", Integer.toString(skoonieKey));

        //insert the receivement into the database
        getDatabase().insertRecord(receiveRecord, getReceivementsTableName());
        
        getDatabase().closeDatabaseConnection();

    }//end of BatchActionHandler::receiveBatch
    //--------------------------------------------------------------------------
    
}//end of class BatchActionHandler
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------