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
*/

//------------------------------------------------------------------------------

package model;

import model.database.MySQLDatabase;
import java.util.List;
import java.util.Map;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class RecordHandler
//

public class RecordHandler
{
    
    private final MySQLDatabase db;
    public final MySQLDatabase getDatabase() { return db; }

    //--------------------------------------------------------------------------
    // RecordHandler::RecordHandler (constructor)
    //

    public RecordHandler(MySQLDatabase pDatabase)
    {

        db = pDatabase;

    }//end of RecordHandler::RecordHandler (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // RecordHandler::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    public void init()
    {

    }// end of RecordHandler::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordHandler::deleteRecord
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
        Table record = new Table();
        record.setSkoonieKey(pCommand.get("skoonie_key"));
        db.deleteRecord(record, pTable);
       
        db.closeDatabaseConnection();
        
    }//end of RecordHandler::deleteRecord
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordHandler::getValues
    //
    // The value of every key in pKeyValuePairs that matches one of the keys in 
    // pKeys is added to pRec, using the key as the column.
    //
    // If a string in pKeys contains two keys for one value, then the first key
    // is used as the column.
    //

    protected void getValues(Table pRec, Map<String, String> pKeyValuePairs, 
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

    }//end of RecordHandler::getValues
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordHandler::updateRecord
    //
    // Updates the record in pTable associated with the skoonie key found in 
    // pCommand using information extracted from pCommand using pKeys.

    protected void updateRecord(Map<String, String> pCommand, 
                                List<String> pKeys, String pTable)
    {
        
        db.connectToDatabase();
        
        //update the record
        Table record = new Table();
        record.setSkoonieKey(pCommand.get("skoonie_key"));
        getValues(record, pCommand, pKeys);
        db.updateRecord(record, pTable);
        
        db.closeDatabaseConnection();
        
    }//end of RecordHandler::updateRecord
    //--------------------------------------------------------------------------
    
}//end of class RecordHandler
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------