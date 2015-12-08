/*******************************************************************************
* Title: BatchController.java
* Author: Hunter Schoonover
* Date: 12/08/15
*
* Purpose:
*
* This class listens for controller commands sent to the CommandHandler that 
* contain batch actions.
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

import command.CommandHandler;
import command.CommandListener;
import command.Command;
import model.MySQLDatabase;
import model.Record;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class BatchActionHandler
//

public class BatchActionHandler implements CommandListener
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
    private final String[] batchAttributes =    { 
                                                    "customer_key",
                                                    "id",
                                                    "quantity",
                                                    "rack_key", 
                                                    "total_length"
                                                };
    
    private final String[] movementAttributes =  {};
    
    private final String[] receivementAttributes =  {
                                                        "rack_key",
                                                        "truck_key",
                                                        "truck_company_key", 
                                                        "truck_driver_key"
                                                    };
    
    //Table names -- back quotes so that they can be easily put in cmd strings
    private final String batchesTable = "`BATCHES`";
    private final String movementsTable = "`MOVEMENTS`";
    private final String receivementsTable = "`RECEIVEMENTS`";

    //--------------------------------------------------------------------------
    // BatchActionHandler::BatchActionHandler (constructor)
    //

    public BatchActionHandler(MySQLDatabase pDatabase)
    {

        db = pDatabase;

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
        
        if (!pCommand.equals(Command.batchActionId)) { return; }
        
        String[] command = pCommand.split("\\|");
        
        switch(command[actionIndex]) {
            case "delete":  deleteRecord(command, batchesTable); break;
            case "move":    moveBatch(command); break;
            case "receive": receiveBatch(command); break;
            case "update":  updateRecord(command, batchesTable); break;
        }

    }//end of BatchActionHandler::commandPerformed
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // BatchActionHandler::deleteRecord
    //
    // Deletes the Record associated with the Skoonie Key found in pCommand
    // from pTable.
    //

    private void deleteRecord(String[] pCommand, String pTable)
    {
        
        //extract the skoonie key from pCommand
        Record r = new Record();
        r.setSkoonieKey(pCommand[skoonieKeyIndex]);
        
        db.deleteRecord(r, pTable);

    }//end of BatchActionHandler::deleteRecord
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // BatchActionHandler::extractAttributes
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

    }//end of BatchActionHandler::extractAttributes
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // BatchActionHandler::moveBatch
    //
    // Moves a batch using the information in pCommand.
    //
    // A record to document the movement is created and inserted into the 
    // movements table.
    //

    private void moveBatch(String[] pCommand)
    {
        
        //extract values from pCommand
        String batchKey     = pCommand[3];
        String moveId       = pCommand[4];
        String date         = pCommand[5];
        String toRackKey    = pCommand[6];
        String fromRackKey  = db.getRecord(batchKey, batchesTable)
                                                        .getAttr("rack_key");
        
        //verify the move
        if (!verifyMove(toRackKey, fromRackKey)) { return; }
       
        //document the movement
        Record moveRecord = new Record();
        moveRecord.addAttr("batch_key",      batchKey);
        moveRecord.addAttr("id",             moveId);
        moveRecord.addAttr("date",           date);
        moveRecord.addAttr("from_rack_key",  fromRackKey);
        moveRecord.addAttr("to_rack_key",    toRackKey);
        extractAttributes(moveRecord, pCommand, movementAttributes);
        db.insertRecord(moveRecord, movementsTable);
       
        //update the batch with the new rack
        Record batchRecord = new Record(batchKey);
        batchRecord.addAttr("rack_key", toRackKey);
        extractAttributes(batchRecord, pCommand, batchAttributes);
        db.updateRecord(batchRecord, batchesTable);

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

    private void receiveBatch(String[] pCommand)
    {
        
        //extract values from pCommand
        String receiveId    = pCommand[3];
        String receiveDate  = pCommand[4];
        
        //record for the batch
        Record batchRecord = new Record();
        extractAttributes(batchRecord, pCommand, batchAttributes);
        
        //before we go any farther, verify the receivement
        if(!verifyReceivement(receiveId, batchRecord.getAttr("id"))) { return; }
       
        //record for the receivement
        Record receiveRecord = new Record();
        receiveRecord.addAttr("id", receiveId);
        receiveRecord.addAttr("date", receiveDate);
        extractAttributes(receiveRecord, pCommand, receivementAttributes);

        //insert the batch into the database and store the skoonie key
        int skoonieKey = db.insertRecord(batchRecord, batchesTable);

        //add the batch skoonie key to the receivement
        receiveRecord.addAttr("batch_key", Integer.toString(skoonieKey));

        //insert the receivement into the database
        db.insertRecord(receiveRecord, receivementsTable);

    }//end of BatchActionHandler::receiveBatch
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // BatchActionHandler::updateRecord
    //
    // Updates the Record in pTable using the Skoonie Key and key-value pairs
    // found in pCommand.
    //

    private void updateRecord(String[] pCommand, String pTable)
    {
        
        //extract the skoonie key from pCommand
        Record r = new Record();
        r.setSkoonieKey(pCommand[skoonieKeyIndex]);
        
        for (int i=skKeyAttrsIndex; i<pCommand.length; i++) {
            String[] pairs = pCommand[i].split(":");
            r.addAttr(pairs[0], pairs[1]);
        }
        
        db.updateRecord(r, pTable);

    }//end of BatchActionHandler::updateRecord
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // BatchActionHandler::verifyMove
    //
    // Verifies the move of a batch from pFromRackKey to pToRackKey.
    //
    // If the keys are the same or if the pFromRackKey is empty, then the move
    // should not be made. 
    //
    // Returns true if move should be made; false if not.
    //

    private boolean verifyMove(String pFromRackKey, String pToRackKey)
    {
        
        boolean shouldMove = true;
        
        if (pFromRackKey.isEmpty() || pFromRackKey.equals(pToRackKey)) {
            shouldMove = false;
        }
        
        return shouldMove;

    }//end of BatchActionHandler::verifyMove
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // BatchActionHandler::verifyReceivement
    //
    // Verifies the receivement of a batch.
    //
    // If pReceiveId or pBatchId already exist in the database or are empty,
    // then the receivement should not be made.
    //
    // Returns true if receivement should be made; false if not.
    //

    private boolean verifyReceivement(String pReceiveId, String pBatchId)
    {
        
        boolean shouldReceive = true;
        
        if (pReceiveId.isEmpty() || pBatchId.isEmpty() 
            || db.checkForValue(pBatchId, batchesTable, "id")
            || db.checkForValue(pReceiveId, receivementsTable, "id"))
        {
            shouldReceive = false;
        }
        
        return shouldReceive;

    }//end of BatchActionHandler::verifyReceivement
    //--------------------------------------------------------------------------
    
}//end of class BatchActionHandler
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------