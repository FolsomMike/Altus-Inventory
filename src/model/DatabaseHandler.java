/*******************************************************************************
* Title: DatabaseHandler.java
* Author: Hunter Schoonover
* Date: 01/03/16
*
* Purpose:
*
* This class handles action pertaining to the database. Essentially, it is a
* translator of the Altus Inventory program to the database package used; it
* takes objects and variables specific to this program and inputs them into the
* database.
* 
* It handles different types of records, such as Customers, Batches,
* Receivements, etc.
*
* It handles different actions for different entities of the program, currently:
*   add customer
*   delete customer
*   get customers
*   update customer
*
*/

//------------------------------------------------------------------------------

package model;

import command.Command;
import command.CommandHandler;
import shared.Table;
import shared.Descriptor;
import model.database.DatabaseEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.SwingUtilities;
import model.database.DatabaseError;
import model.database.MySQLDatabase;
import shared.Record;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class DatabaseHandler
//

public class DatabaseHandler implements CommandHandler
{
    
    private final List<String> handledCommands = new ArrayList<>();
    
    private final MySQLDatabase db = new MySQLDatabase();
    
    private boolean waitingForFixedConnection = false;
    
    //class to hold names of tables in the database
    private class TableName {
        public static final String batches = "BATCHES";
        public static final String batchesDescriptors = "BATCHES_DESCRIPTORS";
        public static final String customers = "CUSTOMERS";
        public static final String customersDescriptors = "CUSTOMERS_DESCRIPTORS";
        public static final String movements = "MOVEMENTS";
        public static final String movementsDescriptors = "MOVEMENTS_DESCRIPTORS";
        public static final String racks = "RACKS";
        public static final String racksDescriptors = "RACKS_DESCRIPTORS";
        public static final String receivements = "RECEIVEMENTS";
        public static final String receivementsDescriptors = "RECEIVEMENTS_DESCRIPTORS";
    }

    //--------------------------------------------------------------------------
    // DatabaseHandler::DatabaseHandler (constructor)
    //

    public DatabaseHandler()
    {

    }//end of DatabaseHandler::DatabaseHandler (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // DatabaseHandler::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    public void init()
    {
        
        //initialize the database
        db.init();
        
        handledCommands.add(Command.RECEIVE_BATCH);
        handledCommands.add(Command.GET_RECIEVEMENT_AND_BATCH_DESCRIPTORS);
        handledCommands.add(Command.ADD_CUSTOMER);
        handledCommands.add(Command.DELETE_CUSTOMER);
        handledCommands.add(Command.EDIT_CUSTOMER);
        handledCommands.add(Command.GET_CUSTOMERS);
        handledCommands.add(Command.ADD_RACK);
        handledCommands.add(Command.DELETE_RACK);
        handledCommands.add(Command.EDIT_RACK);
        handledCommands.add(Command.GET_RACKS);

    }// end of DatabaseHandler::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::handleCommand
    //
    // Performs different actions depending on pCommand.
    //

    @Override
    public void handleCommand(Command pCommand)
    {
        
        if (pCommand==null) { return; }
        
        try {
        
            switch (pCommand.getMessage()) {
                
                //all commands added here need to be added to init()
                
                case Command.RECEIVE_BATCH:
                    receiveBatch((Table)pCommand.get(Command.RECEIVEMENT),
                                    (Table)pCommand.get(Command.BATCH),
                                    (String)pCommand.get(Command.RECORD_KEY));
                    break;
                
                case Command.GET_RECIEVEMENT_AND_BATCH_DESCRIPTORS:
                    getReceivementAndBatchDescriptors();
                    break;
                
                case Command.ADD_CUSTOMER:
                    addCustomer((Table)pCommand.get(Command.TABLE),
                                (String)pCommand.get(Command.RECORD_KEY));
                    break;
                    
                case Command.DELETE_CUSTOMER:
                    deleteCustomer((String)pCommand.get(Command.SKOONIE_KEY));
                    break;
                    
                case Command.EDIT_CUSTOMER:
                    editCustomer((Table)pCommand.get(Command.TABLE),
                                    (String)pCommand.get(Command.RECORD_KEY));
                    break;
                    
                case Command.GET_CUSTOMERS:
                    getCustomers();
                    break;
                    
                case Command.ADD_RACK:
                    addRack((Table)pCommand.get(Command.TABLE),
                                (String)pCommand.get(Command.RECORD_KEY));
                    break;
                    
                case Command.DELETE_RACK:
                    deleteRack((String)pCommand.get(Command.SKOONIE_KEY));
                    break;
                    
                case Command.EDIT_RACK:
                    editRack((Table)pCommand.get(Command.TABLE),
                                    (String)pCommand.get(Command.RECORD_KEY));
                    break;
                    
                case Command.GET_RACKS:
                    getRacks();
                    break;

            }
            
        }
        catch (DatabaseError error) { handleDatabaseError(error); }

    }//end of DatabaseHandler::handleCommand
    //--------------------------------------------------------------------------  
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::addCustomer
    //
    // Adds the customer in pCustomers associated with pCustomerKey to the
    // database.
    //

    private void addCustomer(Table pCustomers, String pCustomerKey)
        throws DatabaseError
    {
        
        addRecord(TableName.customers, pCustomers, pCustomerKey, false);
        
        //get the customers from the database again now that things have
        //changed there
        getCustomers();

    }//end of DatabaseHandler::addCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::addCustomerDescriptor
    //
    // Adds pDescriptor to the customers table.
    //

    private void addCustomerDescriptor(Descriptor pDescriptor)
        throws DatabaseError
    {
        
        addDescriptor(TableName.customers, pDescriptor);

    }//end of DatabaseHandler::addCustomerDescriptor
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::addDescriptor
    //
    // Adds the record in pTable associated with pRecordKey to the database.
    //

    private void addDescriptor(String pTableName, Descriptor pDescriptor)
        throws DatabaseError
    {
       
        //wip hss// -- needs to do this properly
        
        /*db.connectToDatabase();
        
        //entry for the descriptor
        DatabaseEntry descriptorEntry = new DatabaseEntry();
        
        //store the Name
        descriptorEntry.storeColumn("name", pDescriptor.getName());
        
        //store Required -- in the database, true=1, and false=0
        String required = pDescriptor.getRequired() ? "1" : "0";
        descriptorEntry.storeColumn("required", required);

        //store Order Number
        descriptorEntry.storeColumn("order_number", 
                                        pDescriptor.getOrderNumber());
            
        //add the descriptor to the database and get the skoonie key generated
        int intKey = db.insertEntry(descriptorEntry, TableName.descriptors);
        String key = Integer.toString(intKey);
        
        //add the descriptor key to the table as a column
        db.addColumn(pTableName, "`" + key + "` VARCHAR(2000) NULL");
        
        db.disconnectFromDatabase();*/

    }//end of DatabaseHandler::addDescriptor
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::addRack
    //
    // Adds the rack in pRacks associated with pRackKey to the database.
    //

    private void addRack(Table pRacks, String pRackKey)
        throws DatabaseError
    {
        
        addRecord(TableName.racks, pRacks, pRackKey, false);
        
        //get the racks from the database again now that things have
        //changed there
        getRacks();

    }//end of DatabaseHandler::addRack
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::addRecord
    //
    // Adds the record in pTable associated with pRecordKey to the database and
    // returns the key generated when this is done.
    //

    private int addRecord(String pTableName, Table pTable, String pRecordKey,
                            boolean pDisconnectWhenDone)
        throws DatabaseError
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
        
        int generatedKey = db.insertEntry(entry, pTableName);
        
        //only disconnect if directed to do so -- allows for this to
        //be used in a series of database operations
        if (pDisconnectWhenDone) { db.disconnectFromDatabase(); }
        
        return generatedKey;

    }//end of DatabaseHandler::addRecord
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::editCustomer
    //
    // Updates the customer in pTable associated with pCustomerKey in the 
    // database.
    //

    private void editCustomer(Table pTable, String pCustomerKey)
        throws DatabaseError
    {
        
        editRecord(TableName.customers, pTable, pCustomerKey, false);
        
        //get the customers from the database again now that things have
        //changed there
        getCustomers();
        
    }//end of DatabaseHandler::editCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::editCustomer
    //
    // Updates the rack in pTable associated with pRackKey in the 
    // database.
    //

    private void editRack(Table pTable, String pCustomerKey)
        throws DatabaseError
    {
        
        editRecord(TableName.racks, pTable, pCustomerKey, false);
        
        //get the racks from the database again now that things have
        //changed there
        getRacks();
        
    }//end of DatabaseHandler::editRack
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::editRecord
    //
    // Updates the record in pTable associated with pKey to the table in the
    // database with pTableName.

    private void editRecord(String pTableName, Table pTable,  String pKey,
                            boolean pDisconnectWhenDone)
        throws DatabaseError
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
        
        //only disconnect if directed to do so -- allows for this to
        //be used in a series of database operations
        if (pDisconnectWhenDone) { db.disconnectFromDatabase(); }
        
    }//end of DatabaseHandler::editRecord
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::checkDatabaseConnection
    //
    // Checks the database connection. If the connection was previously bad but
    // is now good, a command is performed saying that the connection is good
    // now. If the connection is bad, a command saying that it's bad is 
    // performed.
    //

    public void checkDatabaseConnection()
    {
        
        try { 
            db.connectToDatabase();
            db.disconnectFromDatabase();
            
            //we made it to here so inform everybody that the
            //connection is fixed if it was broken before
            if(waitingForFixedConnection) {
                waitingForFixedConnection = false;
                Command c = new Command(Command.DB_CONNECTION_FIXED);
                performCommandInMainThread(c);
            }
        } 
        catch (DatabaseError e) { handleConnectionError(); }

    }//end of DatabaseHandler::checkDatabaseConnection
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::deleteCustomer
    //
    // Deletes the customer associated with pSkoonieKey from the database.
    //

    private void deleteCustomer(String pSkoonieKey)
        throws DatabaseError
    {
        
        //WIP HSS// -- perform check to see if he can be deleted
        
        deleteRecord(TableName.customers, pSkoonieKey, false);
        
        //get the customers from the database again now that things have
        //changed there
        getCustomers();

    }//end of DatabaseHandler::deleteCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::deleteCustomerDescriptor
    //
    // Deletes pDescriptor as if it were assoicated with the customers table.
    //

    private void deleteCustomerDescriptor(Descriptor pDescriptor)
        throws DatabaseError
    {
        
        deleteDescriptor(TableName.customers, pDescriptor);

    }//end of DatabaseHandler::deleteCustomerDescriptor
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::deleteDescriptor
    //
    // Removes pDescriptor from the descriptors table and from pTable.
    //

    private void deleteDescriptor(String pTable, Descriptor pDescriptor)
        throws DatabaseError
    {
        
        //WIP HSS// -- needs to do this properly
        
        /*db.connectToDatabase();
        
        String key = pDescriptor.getSkoonieKey();
        
        //drop the column from the customers table
        db.dropColumn(TableName.customers, key);
        
        //delete the descriptor entry from the descriptors table
        db.deleteEntry(TableName.descriptors, key);
        
        db.disconnectFromDatabase();*/

    }//end of DatabaseHandler::deleteDescriptor
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::deleteRack
    //
    // Deletes the rack associated with pSkoonieKey from the database.
    //

    private void deleteRack(String pSkoonieKey)
        throws DatabaseError
    {
        
        //WIP HSS// -- perform check to see if he can be deleted
        
        deleteRecord(TableName.racks, pSkoonieKey, false);
        
        //get the rack from the database again now that things have
        //changed there
        getRacks();

    }//end of DatabaseHandler::deleteRack
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::deleteRecord
    //
    // Deletes the entry associated with pSkoonieKey from pTableName.
    //

    private void deleteRecord(String pTableName, String pSkoonieKey,
                                boolean pDisconnectWhenDone)
        throws DatabaseError
    {
        
        db.connectToDatabase();
        
        //WIP HSS// -- perform check to see if record key exists anywhere else
        //              Maybe pass in a list of tables to check in?
        
        db.deleteEntry(pTableName, pSkoonieKey);

        //only disconnect if directed to do so -- allows for this to
        //be used in a series of database operations
        if (pDisconnectWhenDone) { db.disconnectFromDatabase(); }
        
    }//end of DatabaseHandler::deleteRecord
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::getCustomers
    //
    // Gets all of the customers from the database and stick them into a command
    // to be performed in the main thread.
    //

    private void getCustomers()
        throws DatabaseError
    {
        
        Table customers = getTable(TableName.customers, 
                                    TableName.customersDescriptors);
        
        Command c = new Command(Command.CUSTOMERS);
        c.put(Command.TABLE, customers);
        
        performCommandInMainThread(c);

    }//end of DatabaseHandler::getCustomers
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::getCustomerDescriptors
    //
    // Gets and returns the descriptors used for customers from the database.
    //

    private List<Descriptor> getCustomerDescriptors()
        throws DatabaseError
    {
        
        return getDescriptors(TableName.customers, true);

    }//end of DatabaseHandler::getCustomerDescriptors
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::getDescriptors
    //
    // Gets and returns all of the descriptors from pDescriptorsTable.
    //
    // NOTE: Only disconnects from database if pCloseConnection is true.
    //

    private List<Descriptor> getDescriptors(String pDescriptorsTableName, 
                                            boolean pCloseConnection)
        throws DatabaseError
    {
        
        db.connectToDatabase();
        
        List<Descriptor> descriptors = new ArrayList<>();
        
        //retrieve the entries from the database and put them into descriptors
        for (DatabaseEntry e : db.getEntries(pDescriptorsTableName)) {
            
            Descriptor d = new Descriptor();
            d.setSkoonieKey(e.getValue("skoonie_key"));
            d.setName(e.getValue("name"));
            d.setOrderNumber(e.getValue("order_number"));
            d.setRequired(e.getValue("required").equals("1"));
            d.setUsesPresetValues(e.getValue("uses_preset_values").equals("1"));
            
            //only add the preset values to the descriptor if necessary and the
            //value retrieved the from the "preset_values" column splits
            //properly
            String[] v;
            if (d.getUsesPresetValues()
                && (v=e.getValue("preset_values").split("\\.")).length == 2) 
            {
                
                //after the split:
                //  v[0] = the table to get the values from
                //  v[1] = the column that has the display name of each value
                String table = v[0];
                String displayColumn = v[1];
                
                //for every preset value retrieved, store its key and the
                //display name to be used with that value
                for (DatabaseEntry preset : db.getEntries(table)) {
                    String key = preset.getValue("skoonie_key");
                    String display = preset.getValue(displayColumn);
                    d.addPresetValue(key, display);
                }
                
            }
            
            descriptors.add(d);
            
        }
        
        if (pCloseConnection) { db.disconnectFromDatabase();}
        
        return descriptors;

    }//end of DatabaseHandler::getDescriptors
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::getRacks
    //
    // Gets all of the racks from the database and sticks them into a command
    // to be performed in the main thread.
    //

    private void getRacks()
        throws DatabaseError
    {
        
        Table racks = getTable(TableName.racks, TableName.racksDescriptors);
        
        Command c = new Command(Command.RACKS);
        c.put(Command.TABLE, racks);
        
        performCommandInMainThread(c);

    }//end of DatabaseHandler::getRacks
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::getReceivementAndBatchDescriptors
    //
    // Gets all of the receivement descriptors and the batch descriptors from 
    // the database and sticks them into a command  to be performed in the main 
    // thread.
    //

    private void getReceivementAndBatchDescriptors()
        throws DatabaseError
    {
        
        db.connectToDatabase();
        
        Command c = new Command(Command.RECIEVEMENT_AND_BATCH_DESCRIPTORS);
       
        //get and store the receivement descriptors in the command
        c.put(Command.RECIEVEMENT_DESCRIPTORS, 
                 getDescriptors(TableName.receivementsDescriptors, false));

        //get and store the batch descriptors in the command
        c.put(Command.BATCH_DESCRIPTORS, 
                 getDescriptors(TableName.batchesDescriptors, false));
        
        db.disconnectFromDatabase();

        performCommandInMainThread(c);

    }//end of DatabaseHandler::getReceivementAndBatchDescriptors
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::getTable
    //
    // Gets the entries and descriptors for the table in the database with 
    // pTableName and puts them into a Table object.
    //

    private Table getTable(String pTableName, String pDescriptorsTableName)
        throws DatabaseError
    {
        
        db.connectToDatabase();
        
        Table table = new Table();
        
        //get and store the descriptors for the table
        table.setDescriptors(getDescriptors(pDescriptorsTableName, false));
        
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
        
        db.disconnectFromDatabase();
        
        return table;

    }//end of DatabaseHandler::getTable
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::handleConnectionError
    //
    // Sets the waiting for fixed connection boolean to true and performs a
    // command telling everybody that the database can't be connected to.
    //

    public void handleConnectionError()
    {
        
        waitingForFixedConnection = true;
        
        performCommandInMainThread(new Command(Command.DB_CONNECTION_ERROR));

    }//end of DatabaseHandler::handleConnectionError
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::handleDatabaseError
    //
    // Takes different actions depending on the message of pError.
    //

    private void handleDatabaseError(DatabaseError pError)
    {
        
        if (pError==null) { return; }
        
        if (pError.getMessage().equals(DatabaseError.CONNECTION_ERROR)
                || !db.checkConnection()) {
            handleConnectionError();
        }
        else { performCommandInMainThread(new Command(Command.DB_FAILURE)); }

    }//end of DatabaseHandler::handleDatabaseError
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::handlesCommand
    //
    // Returns true if the command is handled by this class; false if not.
    //

    public boolean handlesCommand(Command pCommand)
    {
        
        String msg = pCommand.getMessage();
        
        for (String cmd : handledCommands) {
            if (msg.equals(cmd)) { return true; }
        }
        
        return false;

    }//end of DatabaseHandler::handlesCommand
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::receiveBatch
    //
    // //WIP HSS// -- receive batch
    //

    private void receiveBatch(Table pReceivements, Table pBatches, 
                                String pRecordKey)
        throws DatabaseError
    {
        
        int batchKey = addRecord(TableName.batches, pBatches, pRecordKey,
                                                                        false);
        
        //extract the data from the receivement record into a DatabaseEntry object
        Record record = pReceivements.getRecord(pRecordKey);
        DatabaseEntry recEntry = new DatabaseEntry();
        for (Descriptor d : pReceivements.getDescriptors()) {
            String descKey = d.getSkoonieKey();
            
            //store the value for the descriptor
            String value = record.getValue(descKey);
            if (value != null) { recEntry.storeColumn(descKey, value); }
        }
        
        //store the batch key in the receivement entry
        recEntry.storeColumn("batch_key", Integer.toString(batchKey));
        
        //inert the receivement entry into the database
        db.insertEntry(recEntry, TableName.receivements);

        db.disconnectFromDatabase();
        
        //WIP HSS// -- get batches again now that stuff has changed there

    }//end of DatabaseHandler::addCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::performCommandInMainThread
    //
    // Performs pCommand in the main thread.
    // 
    // NOTE:    All of this is done in the main thread, so pCommand should not
    //          be used after it is given to this function.
    //

    private void performCommandInMainThread(Command pCommand)
    {
        
        SwingUtilities.invokeLater(() -> { pCommand.perform(); });

    }//end of DatabaseHandler::performCommandInMainThread
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::updateCustomerDescriptor
    //
    // Updates pDescriptor in the customers table.
    //

    private void updateCustomerDescriptor(Descriptor pDescriptor)
        throws DatabaseError
    {
        
        updateDescriptor(TableName.customers, pDescriptor);
        
    }//end of DatabaseHandler::updateCustomerDescriptor
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::updateDescriptor
    //
    // Updates pDescriptor in pTableName.
    //

    private void updateDescriptor(String pTableName, Descriptor pDescriptor)
        throws DatabaseError
    {
        
        //WIP HSS// -- needs to be done properly
        
        /*db.connectToDatabase();
        
        //entry for the descriptor
        DatabaseEntry descriptorEntry = new DatabaseEntry();
        
        //store the skoonie key
        descriptorEntry.storeColumn("skoonie_key", pDescriptor.getSkoonieKey());
        
        //store the Name
        descriptorEntry.storeColumn("name", pDescriptor.getName());
        
        //store Required -- in the database, true=1, and false=0
        String required = pDescriptor.getRequired() ? "1" : "0";
        descriptorEntry.storeColumn("required", required);

        //store Order Number
        descriptorEntry.storeColumn("order_number", 
                                        pDescriptor.getOrderNumber());
            
        //update the descriptor in the database
        db.updateEntry(descriptorEntry, TableName.descriptors);
        
        db.disconnectFromDatabase();*/

    }//end of DatabaseHandler::updateDescriptor
    //--------------------------------------------------------------------------
    
}//end of class DatabaseHandler
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------