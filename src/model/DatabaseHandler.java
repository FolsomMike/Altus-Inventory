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
        public static final String transfers = "TRANSFERS";
        public static final String transfersDescriptors = "TRANSFERS_DESCRIPTORS";
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
        handledCommands.add(Command.MOVE_BATCH);
        handledCommands.add(Command.TRANSFER_BATCH);
        handledCommands.add(Command.GET_RECEIVEMENT_DESCRIPTORS);
        handledCommands.add(Command.GET_MOVEMENT_DESCRIPTORS);
        handledCommands.add(Command.GET_TRANSFER_DESCRIPTORS);
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
                    receiveBatch(pCommand);
                    break;
                
                case Command.GET_RECEIVEMENT_DESCRIPTORS:
                    getReceivementDescriptors();
                    break;
                    
                case Command.MOVE_BATCH:
                    moveBatch(pCommand);
                    break;
                
                case Command.GET_MOVEMENT_DESCRIPTORS:
                    getMovementDescriptors();
                    break;
                    
                case Command.TRANSFER_BATCH:
                    transferBatch(pCommand);
                    break;
                
                case Command.GET_TRANSFER_DESCRIPTORS:
                    getTransferDescriptors();
                    break;
                
                case Command.ADD_CUSTOMER:
                    addCustomer(pCommand);
                    break;
                    
                case Command.DELETE_CUSTOMER:
                    deleteCustomer((String)pCommand.get(Command.SKOONIE_KEY));
                    break;
                    
                case Command.EDIT_CUSTOMER:
                    editCustomer(pCommand);
                    break;
                    
                case Command.GET_CUSTOMERS:
                    getCustomers();
                    break;
                    
                case Command.ADD_RACK:
                    addRack(pCommand);
                    break;
                    
                case Command.DELETE_RACK:
                    deleteRack((String)pCommand.get(Command.SKOONIE_KEY));
                    break;
                    
                case Command.EDIT_RACK:
                    editRack(pCommand);
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
    // Adds a customer by extracting and using objects that it knows are in
    // pCommand.
    //

    private void addCustomer(Command pCommand)
        throws DatabaseError
    {
        
        Record customer = (Record)pCommand.get(Command.CUSTOMER);
        
        List<?> descriptors
                        = (List<?>)pCommand.get(Command.CUSTOMER_DESCRIPTORS);
        
        addRecord(TableName.customers, customer, descriptors, false);
        
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
    // Adds a racks by extracting and using objects that are known to be in
    // pCommand.
    //

    private void addRack(Command pCommand)
        throws DatabaseError
    {
        
        Record rack = (Record)pCommand.get(Command.RACK);
        
        List<?> descriptors
                        = (List<?>)pCommand.get(Command.RACK_DESCRIPTORS);
        
        addRecord(TableName.racks, rack, descriptors, false);
        
        //get the racks from the database again now that things have
        //changed there
        getRacks();

    }//end of DatabaseHandler::addRack
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::addRecord
    //
    // Addds pRecord to pTableName in the database using pDescriptors.
    // 
    // This function will only disconnect from the database after it's done if 
    // pDisconnectWhenDone is true.
    //

    private int addRecord(String pTableName, Record pRecord, 
                            List<?> pDescriptors, 
                            boolean pDisconnectWhenDone)
        throws DatabaseError
    {
        
        db.connectToDatabase();
        
        //add the customer to the database
        DatabaseEntry entry = new DatabaseEntry();
        for (Object o : pDescriptors) {
            
            Descriptor d = (Descriptor)o;
            
            String descKey = d.getSkoonieKey();
            
            //store the value for the descriptor
            String value = pRecord.getValue(descKey);
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
    // Updates a customer by extracting and using objects that are known to be
    // in pCommand.
    //

    private void editCustomer(Command pCommand)
        throws DatabaseError
    {
        
        Record customer = (Record)pCommand.get(Command.CUSTOMER);
        
        List<?> descriptors
                        = (List<?>)pCommand.get(Command.CUSTOMER_DESCRIPTORS);
        
        editRecord(TableName.customers, customer, descriptors, false);
        
        //get the customers from the database again now that things have
        //changed there
        getCustomers();
        
    }//end of DatabaseHandler::editCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::editRack
    //
    // Updates a rack by extracting and using objects that are known to be in 
    // pCommand.
    //

    private void editRack(Command pCommand)
        throws DatabaseError
    {
        
        Record rack = (Record)pCommand.get(Command.RACK);
        
        List<?> descriptors
                        = (List<?>)pCommand.get(Command.RACK_DESCRIPTORS);
        
        editRecord(TableName.racks, rack, descriptors, false);
        
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

    private void editRecord(String pTableName, Record pRecord, 
                            List<?> pDescriptors, 
                            boolean pDisconnectWhenDone)
        throws DatabaseError
    {
        
        db.connectToDatabase();
        
        //update the customer in the database
        DatabaseEntry entry = new DatabaseEntry();
        entry.storeColumn("skoonie_key", pRecord.getSkoonieKey());
        for (Object o : pDescriptors) {
            
            Descriptor d = (Descriptor)o;
            
            String descKey = d.getSkoonieKey();
            
            //store the value for the descriptor
            String value = pRecord.getValue(descKey);
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
    // Gets all of the customers from the database and sticks them into a
    // command to be performed in the main thread.
    //

    private void getCustomers()
        throws DatabaseError
    {
        
        db.connectToDatabase();
        
        //get all of the entries from the customers table
        List<DatabaseEntry> entries = db.getEntries(TableName.customers);
        
        //get all of the descriptors for the customers table
        List<Descriptor> descriptors 
                        = getDescriptors(TableName.customersDescriptors, true);
        
        //list to hold all of the customer records
        List<Record> records = new ArrayList<>();
        
        //extract all of the descriptor values from the entries and put them
        //into the custmer records
        extractDescriptorValuesFromEntries(records, descriptors, entries);
        
        Command c = new Command(Command.CUSTOMERS);
        
        c.put(Command.CUSTOMERS, records);
        c.put(Command.CUSTOMER_DESCRIPTORS, descriptors);
        
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
    // DatabaseHandler::getMovementDescriptors
    //
    // Gets all of the movement descriptors from the database and sticks them
    // into a command  to be performed in the main thread.
    //

    private void getMovementDescriptors()
        throws DatabaseError
    {
        
        db.connectToDatabase();
        
        Command c = new Command(Command.MOVEMENT_DESCRIPTORS);
       
        //get and store the movement descriptors in the command
        c.put(Command.MOVEMENT_DESCRIPTORS, 
                 getDescriptors(TableName.movementsDescriptors, false));
        
        db.disconnectFromDatabase();

        performCommandInMainThread(c);

    }//end of DatabaseHandler::getMovementDescriptors
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
        
        db.connectToDatabase();
        
        //get all of the entries from the racks table
        List<DatabaseEntry> entries = db.getEntries(TableName.racks);
        
        //get all of the descriptors for the racks table
        List<Descriptor> descriptors 
                        = getDescriptors(TableName.racksDescriptors, true);
        
        //list to hold all of the rack records
        List<Record> records = new ArrayList<>();
        
        //extract all of the descriptor values from the entries and put them
        //into the rack records
        extractDescriptorValuesFromEntries(records, descriptors, entries);
        
        Command c = new Command(Command.RACKS);
        
        c.put(Command.RACKS, records);
        c.put(Command.RACK_DESCRIPTORS, descriptors);
        
        performCommandInMainThread(c);
        
    }//end of DatabaseHandler::getRacks
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::getReceivementDescriptors
    //
    // //WIP HSS// -- describe function
    //
    // Gets all of the receivement descriptors and the batch descriptors from 
    // the database and sticks them into a command  to be performed in the main 
    // thread.
    //

    private void getReceivementDescriptors()
        throws DatabaseError
    {
        
        db.connectToDatabase();
        
        Command c = new Command(Command.RECIEVEMENT_DESCRIPTORS);
        
        List<Descriptor> descriptors 
                        = getDescriptors(TableName.batchesDescriptors, false);
        
        //column "id" in the receivements table is not
        //a descriptor. We know it will always exist
        Descriptor receivementId = new Descriptor();
        receivementId.setSkoonieKey("receivement id");
        receivementId.setName("Receivement Id");
        receivementId.setRequired(true);
        receivementId.setUsesPresetValues(false);
        descriptors.add(receivementId);
        
        //column "date" in the receivements table is not
        //a descriptor. We know it will always exist
        Descriptor receivementDate = new Descriptor();
        receivementDate.setSkoonieKey("date");
        receivementDate.setName("Date");
        receivementDate.setRequired(true);
        receivementDate.setUsesPresetValues(false);
        descriptors.add(receivementDate);
       
        //get and store the receivement descriptors in the command
        c.put(Command.RECIEVEMENT_DESCRIPTORS, descriptors);
        
        db.disconnectFromDatabase();

        performCommandInMainThread(c);

    }//end of DatabaseHandler::getReceivementDescriptors
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::getTransferDescriptors
    //
    // Gets all of the transfer descriptors from the database and sticks them
    // into a command  to be performed in the main thread.
    //

    private void getTransferDescriptors()
        throws DatabaseError
    {
        
        db.connectToDatabase();
        
        Command c = new Command(Command.TRANSFER_DESCRIPTORS);
       
        //get and store the movement descriptors in the command
        c.put(Command.TRANSFER_DESCRIPTORS, 
                 getDescriptors(TableName.transfersDescriptors, false));
        
        db.disconnectFromDatabase();

        performCommandInMainThread(c);

    }//end of DatabaseHandler::getTransferDescriptors
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::extractDescriptorValuesFromEntries
    //
    // For all of the descriptors in pDescriptors that match columns in 
    // pEntries, the values of those descriptors are extracted from pEntries and
    // put into pRecords. This also handles skoonie keys.
    //

    private void extractDescriptorValuesFromEntries(List<Record> pRecords,
                                                List<Descriptor> pDescriptors, 
                                                List<DatabaseEntry> pEntries)
        throws DatabaseError
    {
        
        db.connectToDatabase();
        
        //iterate through through the entries
        for (DatabaseEntry e : pEntries) {
            
            Record r = new Record();
            
            //iterate through the columns and column values
            for (Map.Entry<String, String> c : e.getColumns().entrySet()) {
                
                //key=column name; value=column value
                String name = c.getKey();
                String value = c.getValue();
                
                //if the column is the skoonie_key,
                //store it and continue to next one
                if(name.equals("skoonie_key")) { 
                    r.setSkoonieKey(value);
                    continue;
                }
                
                //if the column name matches a descriptor key, store the column
                //value in the record, using the skoonie key of the descriptor
                //it belongs to as the key
                for (Descriptor d : pDescriptors) {
                    if (name.equals(d.getSkoonieKey())) { 
                        r.addValue(name, value); 
                    }
                }
            
            }
            
            //store the record
            pRecords.add(r);
            
        }

    }//end of DatabaseHandler::extractDescriptorValuesFromEntries
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
    // DatabaseHandler::moveBatch
    //
    // Moves a batch by extracting and using objects that it knows are in
    // pCommand.
    //

    private void moveBatch(Command pCommand)
        throws DatabaseError
    {
        
        //get the movement record and movement descriptors
        Record movement = (Record)pCommand.get(Command.MOVEMENT);
        List<?> movementDescriptors = (List<?>)pCommand
                                            .get(Command.MOVEMENT_DESCRIPTORS);
        
        //get the batch record and batch descriptors
        Record batch = (Record)pCommand.get(Command.BATCH);
        
        //create database entries for the batch and receivement
        DatabaseEntry moveEntry = new DatabaseEntry();
        DatabaseEntry batchEntry = new DatabaseEntry();
        
        //add the batch key to the move and batch entries
        moveEntry.storeColumn("batch_key", batch.getSkoonieKey());
        batchEntry.storeColumn("skoonie_key", batch.getSkoonieKey());
        
        //extract data from the movement
        for (Object o : movementDescriptors) {
            Descriptor d = (Descriptor)o;
            
            String key = d.getSkoonieKey();
            String name = d.getName();
            String value = movement.getValue(key);
            
            switch (name) {
                
                //Descriptor is the movement To Rack
                case "To Rack":
                    batchEntry.storeColumn("4", value);
                   
                default:
                    moveEntry.storeColumn(key, value);
                    break;
                    
            }
            
        }
        
        db.connectToDatabase();
        
        //update the batch in the database
        db.updateEntry(batchEntry, TableName.batches);
        
        //inert the movement entry into the database
        db.insertEntry(moveEntry, TableName.movements);

        db.disconnectFromDatabase();

    }//end of DatabaseHandler::moveBatch
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::receiveBatch
    //
    // Receives a batch by extracting and using objects that it knows are in
    // pCommand.
    //

    private void receiveBatch(Command pCommand)
        throws DatabaseError
    {
        
        //get the receivement record and receivement descriptors
        Record receivement = (Record)pCommand.get(Command.RECEIVEMENT);
        List<?> descriptors = (List<?>)pCommand
                                        .get(Command.RECIEVEMENT_DESCRIPTORS);
        
        //create database entries for the batch and receivement
        DatabaseEntry recEntry = new DatabaseEntry();
        DatabaseEntry batchEntry = new DatabaseEntry();
        
        //extract data from the descriptors
        for (Object o : descriptors) {
            Descriptor d = (Descriptor)o;
            
            String key = d.getSkoonieKey();
            String name = d.getName();
            String value = receivement.getValue(key);
            
            switch (name) {
                
                //Descriptor is the receivement id
                case "Receivement Id":
                    recEntry.storeColumn("id", value);
                    break;
                    
                //Descriptor is the date
                case "Date":
                    recEntry.storeColumn(key, value);
                    break;
                   
                //Descriptor is just an ordinary batch descriptor
                default:
                    recEntry.storeColumn(key, value);
                    batchEntry.storeColumn(key, value);
                    break;
                    
            }
            
        }
        
        db.connectToDatabase();
        
        //insert the batch into database and get the generated key
        int batchKey = db.insertEntry(batchEntry, TableName.batches);
        
        //store the batch key in the receivement entry
        recEntry.storeColumn("batch_key", Integer.toString(batchKey));
        
        //inert the receivement entry into the database
        db.insertEntry(recEntry, TableName.receivements);

        db.disconnectFromDatabase();

    }//end of DatabaseHandler::receiveBatch
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseHandler::transferBatch
    //
    // Transfers a batch by extracting and using objects that it knows are in
    // pCommand.
    //

    private void transferBatch(Command pCommand)
        throws DatabaseError
    {
        
        //get the transfer record and transfer descriptors
        Record transfer = (Record)pCommand.get(Command.TRANSFER);
        List<?> transferDescriptors = (List<?>)pCommand
                                            .get(Command.TRANSFER_DESCRIPTORS);
        
        //get the batch record
        Record batch = (Record)pCommand.get(Command.BATCH);
        
        //create database entries for the batch and transfer
        DatabaseEntry transferEntry = new DatabaseEntry();
        DatabaseEntry batchEntry = new DatabaseEntry();
        
        //add the batch key to the transfer and batch entries
        transferEntry.storeColumn("batch_key", batch.getSkoonieKey());
        batchEntry.storeColumn("skoonie_key", batch.getSkoonieKey());
        
        //extract data from the movement
        for (Object o : transferDescriptors) {
            Descriptor d = (Descriptor)o;
            
            String key = d.getSkoonieKey();
            String name = d.getName();
            String value = transfer.getValue(key);
            
            switch (name) {
                
                //Descriptor is the transfer's To Customer
                case "To Customer":
                    //WIP HSS// -- this uses hardcoded descriptor key -- very bad
                    batchEntry.storeColumn("3", value); 
                   
                default:
                    transferEntry.storeColumn(key, value);
                    break;
                    
            }
            
        }
        
        db.connectToDatabase();
        
        //update the batch in the database
        db.updateEntry(batchEntry, TableName.batches);
        
        //inert the transfer entry into the database
        db.insertEntry(transferEntry, TableName.transfers);

        db.disconnectFromDatabase();

    }//end of DatabaseHandler::transferBatch
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