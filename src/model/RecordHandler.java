/*******************************************************************************
* Title: RecordHandler.java
* Author: Hunter Schoonover
* Date: 12/19/15
*
* Purpose:
*
* This class handles action pertaining to different types of records, such as
* Customers, Batches, Receivements, etc.
*
* Currently handles actions:
*   add customer
*   delete customer
*   get customers
*   update customer
*
*/

//------------------------------------------------------------------------------

package model;

import shared.Table;
import shared.Descriptor;
import model.database.DatabaseEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.database.MySQLDatabase;
import shared.Record;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class RecordHandler
//

public class RecordHandler
{
    
    private final MySQLDatabase db = new MySQLDatabase();
    
    //class to hold names of tables in the database
    private class TableName {
        public static final String customers = "CUSTOMERS";
        public static final String descriptors = "DESCRIPTORS";
    }

    //--------------------------------------------------------------------------
    // RecordHandler::RecordHandler (constructor)
    //

    public RecordHandler()
    {

    }//end of RecordHandler::RecordHandler (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // RecordHandler::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    public void init()
    {
        
        //initialize the database
        db.init();

    }// end of RecordHandler::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordHandler::addCustomer
    //
    // Adds the customer in pCustomers associated with pCustomerKey to the
    // database.
    //

    public void addCustomer(Table pCustomers, String pCustomerKey)
    {
        
        addRecord(TableName.customers, pCustomers, pCustomerKey);

    }//end of RecordHandler::addCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordHandler::addRecord
    //
    // Adds the record in pTable associated with pRecordKey to the database.
    //

    public void addRecord(String pTableName, Table pTable, String pRecordKey)
    {
        
        db.connectToDatabase();
        
        //get the proper record from pTable
        Record record = pTable.getRecord(pRecordKey);
        
        //add the customer to the database
        DatabaseEntry entry = new DatabaseEntry();
        for (Descriptor d : pTable.getDescriptors()) {
            String descKey = d.getSkoonieKey();
            
             //store the value for the descriptor
            String value = record.getValue(descKey);
            if (value != null) { entry.storeColumn(descKey, value); }
        }
        
        db.insertEntry(entry, pTableName);
        
        db.closeDatabaseConnection();

    }//end of RecordHandler::addRecord
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordHandler::deleteCustomer
    //
    // Deletes the customer associated with pSkoonieKey from the database.
    //

    public void deleteCustomer(String pSkoonieKey)
    {
        
        //WIP HSS// -- perform check to see if he can be deleted
        
        deleteRecord(TableName.customers, pSkoonieKey);

    }//end of RecordHandler::deleteCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordHandler::deleteRecord
    //
    // Deletes the entry associated with pSkoonieKey from pTableName.
    //

    public void deleteRecord(String pTableName, String pSkoonieKey)
    {
        
        db.connectToDatabase();
        
        //WIP HSS// -- perform check to see if record key exists anywhere else
        //              Maybe pass in a list of tables to check in?
        
        db.deleteEntry(pTableName, pSkoonieKey);
       
        db.closeDatabaseConnection();
        
    }//end of RecordHandler::deleteRecord
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordHandler::getCustomers
    //
    // Gets and returns all of the customers from the database.
    //

    public Table getCustomers()
    {
        
        return getTable(TableName.customers);

    }//end of RecordHandler::getCustomers
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordHandler::getCustomersDescriptors
    //
    // Gets and returns the descriptors used for customers from the database.
    //

    public List<Descriptor> getCustomersDescriptors()
    {
        
        return getDescriptors(TableName.customers, true);

    }//end of RecordHandler::getCustomers
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordHandler::getDescriptors
    //
    // Gets and returns all of the descriptors with skoonie keys matching pKeys
    // from the descriptors table.
    //
    // NOTE: Only disconnects from database if pCloseConnection is true.
    //

    public List<Descriptor> getDescriptors(String pTableName, 
                                            boolean pCloseConnection)
    {
        
        db.connectToDatabase();
        
        //this list will contain the skoonie keys of all
        //the descriptors that need to be retrieved from
        //the database
        List<String> descKeys = new ArrayList<>();
        
        //get the descriptor keys for pTableName from the database
        for (String columnName : db.getColumnNames(pTableName)) {
            if (!columnName.equals("skoonie_key")) { descKeys.add(columnName); }
        }
        
        List<Descriptor> descriptors = new ArrayList<>();
        
        List<DatabaseEntry> entries = db.getEntries(TableName.descriptors,
                                                                    descKeys);
        for (DatabaseEntry e : entries) {
            
            Descriptor d = new Descriptor();
            d.setSkoonieKey(e.getValue("skoonie_key"));
            d.setName(e.getValue("name"));
            d.setRequired(e.getValue("required").equals("1"));
            
            descriptors.add(d);
        }
        
        if (pCloseConnection) { db.closeDatabaseConnection(); }
        
        return descriptors;

    }//end of RecordHandler::getDescriptors
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordHandler::getTable
    //
    // Gets the entries and descriptors for the table in the database with 
    // pTableName and puts them into a Table object.
    //

    public Table getTable(String pTableName)
    {
        
        db.connectToDatabase();
        
        Table table = new Table();
        
        //get and store the descriptors for the table
        table.setDescriptors(getDescriptors(pTableName, false));
        
        //get the table entries from the database
        List<DatabaseEntry> entries = db.getEntries(pTableName);
        
        //iterate through through the entries
        for (DatabaseEntry e : entries) {
            
            Record r = new Record();
            
            //iterate through the columns and column values
            for (Map.Entry<String, String> c : e.getColumns().entrySet()) {
                
                //key=column name; value=column value
                String name = c.getKey();
                String value = c.getValue();
                
                //if the column is the skoonie_key,
                //store it and continue to next one
                if(name.equals("skoonie_key")) { r.setSkoonieKey(value); }
                
                //if the column name matches a descriptor key, store the column
                //value in the record, using the skoonie key of the descriptor
                //it belongs to as the key
                else if (table.getDescriptor(name) != null) {
                    r.addValue(name, value);
                }
            
            }
            
            //store the record
            table.addRecord(r);
            
        }
        
        db.closeDatabaseConnection();
        
        return table;

    }//end of RecordHandler::getTable
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordHandler::updateCustomer
    //
    // Updates the customer in pTable associated with pCustomerKey in the 
    // database.
    //

    public void updateCustomer(Table pTable, String pCustomerKey)
    {
        
        updateRecord(TableName.customers, pTable, pCustomerKey);
        
    }//end of RecordHandler::updateCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordHandler::updateRecord
    //
    // Updates the record in pTable associated with pKey to the table in the
    // database with pTableName.

    public void updateRecord(String pTableName, Table pTable,  String pKey)
    {
        
        db.connectToDatabase();
        
        //get the proper record from pTable
        Record record = pTable.getRecord(pKey);
        
        //add the customer to the database
        DatabaseEntry entry = new DatabaseEntry();
        entry.storeColumn("skoonie_key", record.getSkoonieKey());
        for (Descriptor d : pTable.getDescriptors()) {
            String descKey = d.getSkoonieKey();
            
             //store the value for the descriptor
            String value = record.getValue(descKey);
            if (value != null) { entry.storeColumn(descKey, value); }
        }
        
        db.updateEntry(entry, pTableName);
        
        db.closeDatabaseConnection();
        
    }//end of RecordHandler::updateRecord
    //--------------------------------------------------------------------------
    
}//end of class RecordHandler
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------