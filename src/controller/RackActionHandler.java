/*******************************************************************************
* Title: RackActionHandler.java
* Author: Hunter Schoonover
* Date: 12/09/15
*
* Purpose:
*
* This class listens for controller commands sent to the CommandHandler that 
* contain rack actions.
*
* Currently handles actions:
*   add rack
*   delete rack
*   update rack
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
// class RackActionHandler
//

public class RackActionHandler implements CommandListener
{
    
    private final MySQLDatabase db;
    
    int skoonieKeyIndex     = 3;
    
    //Record attributes --  record attributes are the keys to search for in a
    //                      command array for a specific record type
    private final String[] rackAttributes = { 
                                                "id",
                                                "name"
                                            };
    
    //Table names -- back quotes so that they can be easily put in cmd strings
    private final String racksTable = "`RACKS`";

    //--------------------------------------------------------------------------
    // RackActionHandler::RackActionHandler (constructor)
    //

    public RackActionHandler(MySQLDatabase pDatabase)
    {

        db = pDatabase;

    }//end of RackActionHandler::RackActionHandler (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // RackActionHandler::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    public void init()
    {
        
        //register this as a controller listener
        CommandHandler.registerControllerListener(this);
        
        //register this as an error listener
        CommandHandler.registerErrorListener(this);

    }// end of RackActionHandler::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RackActionHandler::commandPerformed
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
        
        if (Command.isRackAddCommand(pCommand)) {
            addRack(cmdArray);
        }
        else if (Command.isRackDeleteCommand(pCommand)) {
            deleteRecord(cmdArray, racksTable);
        }
        else if (Command.isRackUpdateCommand(pCommand)) {
            updateRecord(cmdArray, rackAttributes, racksTable);
        }

    }//end of RackActionHandler::commandPerformed
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RackActionHandler::deleteRecord
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

    }//end of RackActionHandler::deleteRecord
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RackActionHandler::extractAttributes
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

    }//end of RackActionHandler::extractAttributes
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RackActionHandler::addRack
    //
    // Adds a new rack using the information in pCommand.
    //

    private void addRack(String[] pCommand)
    {
        
        //record for the rack
        Record rackRecord = new Record();
        extractAttributes(rackRecord, pCommand, rackAttributes);
        
        //before we go any farther, verify the add
        if(!verifyAdd(rackRecord.getAttr("id"), rackRecord.getAttr("name"))) 
        { return; }

        //insert the batch into the database and store the skoonie key
        db.insertRecord(rackRecord, racksTable);

    }//end of RackActionHandler::addRack
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RackActionHandler::updateRecord
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

    }//end of RackActionHandler::updateRecord
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RackActionHandler::verifyAdd
    //
    // Verifies that a customer should be added to the database.
    //
    // If pRackId or pRackName already exists in the database or are empty, then
    // the customer should not be added.
    //
    // Returns true if rack should be added; false if not.
    //

    private boolean verifyAdd(String pRackId, String pRackName)
    {
        
        boolean shouldAdd = true;
        
        if (pRackId.isEmpty() || pRackName.isEmpty()
            || db.checkForValue(pRackId, racksTable, "id")
            || db.checkForValue(pRackName, racksTable, "name"))
        {
            shouldAdd = false;
        }
        
        return shouldAdd;

    }//end of RackActionHandler::verifyAdd
    //--------------------------------------------------------------------------
    
}//end of class RackActionHandler
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------