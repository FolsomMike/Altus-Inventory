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

package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class BatchHandler
//

public class BatchHandler extends RecordHandler
{
    
    //Command keys -- keys to look for when handling a command
    private final List<String> batchKeys = new ArrayList<>();
    
    private final List<String> movementKeys = new ArrayList<>();
    
    private final List<String> receivementKeys = new ArrayList<>();
    
    //Table names
    private final String batchesTable = "BATCHES";
    private final String receivementsTable = "RECEIVEMENTS";
    private final String movementsTable = "MOVEMENTS";

    //--------------------------------------------------------------------------
    // BatchHandler::BatchHandler (constructor)
    //

    public BatchHandler(MySQLDatabase pDatabase)
    {

        super(pDatabase);

    }//end of BatchHandler::BatchHandler (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // BatchHandler::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    @Override
    public void init()
    {
        
        super.init();

    }// end of BatchHandler::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // BatchHandler::deleteBatch
    //
    // Deletes a batch and the receivement associated with that batch using the
    // information in pCommand.
    //
    // //DEBUG HSS// -- testing purposes only
    //

    public void deleteBatch(Map<String, String> pCommand)
    {
        
        getDatabase().connectToDatabase();
        
        //delete the batch
        Record batchRecord = new Record();
        batchRecord.setSkoonieKey(pCommand.get("skoonie_key"));
        getDatabase().deleteRecord(batchRecord, batchesTable);
        
        //delete the receivement associated with the batch
        ArrayList<Record> receivementRecords 
                        = getDatabase().getRecords(receivementsTable);
        String batchKey = batchRecord.getSkoonieKey();
        for (Record receivement : receivementRecords) {
            if (receivement.getValue("batch_key").equals(batchKey)) {
                getDatabase().deleteRecord(receivement, receivementsTable);
                break;
            }
        }
       
        getDatabase().closeDatabaseConnection();
        
    }//end of BatchHandler::deleteBatch
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // BatchHandler::moveBatch
    //
    // Moves a batch using the information in pCommand.
    //

    public void moveBatch(Map<String, String> pCommand)
    {
        
        getDatabase().connectToDatabase();
        
        //update the batch in the database
        Record batchRecord = new Record();
        batchRecord.setSkoonieKey(pCommand.get("skoonie_key"));
        getValues(batchRecord, pCommand, batchKeys);
        getDatabase().updateRecord(batchRecord, batchesTable);
       
        //document the movement
        Record moveRecord = new Record();
        getValues(moveRecord, pCommand, movementKeys);
        moveRecord.addColumn("batch_key", batchRecord.getSkoonieKey());
        getDatabase().insertRecord(moveRecord, movementsTable);
        
        getDatabase().closeDatabaseConnection();

    }//end of BatchHandler::moveBatch
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // BatchHandler::receiveBatch
    //
    // Receives a batch using the information in pCommand.
    //
    // Two records are created and inserted into the database:
    //      one for a receivement
    //      one for a new batch
    //

    public void receiveBatch(Map<String, String> pCommand)
    {
        
        getDatabase().connectToDatabase();
        
        //record for the batch
        Record batchRecord = new Record();
        getValues(batchRecord, pCommand, batchKeys);
       
        //record for the receivement
        Record receiveRecord = new Record();
        getValues(receiveRecord, pCommand, receivementKeys);

        //insert the batch into the database and store the skoonie key
        int skoonieKey = getDatabase().insertRecord(batchRecord, batchesTable);

        //add the batch skoonie key to the receivement
        receiveRecord.addColumn("batch_key", Integer.toString(skoonieKey));

        //insert the receivement into the database
        getDatabase().insertRecord(receiveRecord, receivementsTable);
        
        getDatabase().closeDatabaseConnection();

    }//end of BatchHandler::receiveBatch
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // BatchHandler::updateBatch
    //
    // Updates a batch using the information in pCommand.
    //

    public void updateBatch(Map<String, String> pCommand)
    {
        
        updateRecord(pCommand, batchKeys, batchesTable);

    }//end of BatchHandler::updateBatch
    //--------------------------------------------------------------------------
    
}//end of class BatchHandler
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------