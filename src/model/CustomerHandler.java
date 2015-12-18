/*******************************************************************************
* Title: CustomerActionHandler.java
* Author: Hunter Schoonover
* Date: 12/10/15
*
* Purpose:
*
* This class handles commands pertaining to customers
*
* Currently handles actions:
*   add customer
*   get customers
*   update customer
*
*/

//------------------------------------------------------------------------------

package model;

import shared.Table;
import shared.Descriptor;
import model.database.DatabaseEntry;
import model.database.MySQLDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import shared.Record;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class CustomerHandler
//

public class CustomerHandler extends RecordHandler
{
    
    //Command keys -- keys to look for when handling a command
    private final List<String> customerKeys = new ArrayList<>();
    
    //Table names
    private final String customersTable = "CUSTOMERS";
    
    private final DescriptorHandler descriptorHandler;

    //--------------------------------------------------------------------------
    // CustomerHandler::CustomerHandler (constructor)
    //

    public CustomerHandler(MySQLDatabase pDatabase)
    {

       super(pDatabase);
       
       descriptorHandler = new DescriptorHandler(pDatabase, customersTable);

    }//end of CustomerHandler::CustomerHandler (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // CustomerHandler::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    @Override
    public void init()
    {
        
        super.init();
        
        descriptorHandler.init();

    }// end of CustomerHandler::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomerHandler::addCustomer
    //
    // Adds the customer in pCustomers associated with pCustomerKey to the
    // database.
    //

    public void addCustomer(Table pCustomers, String pCustomerKey)
    {
        
        getDatabase().connectToDatabase();
        
        //get the proper record from pCustomers
        Record record = pCustomers.getRecord(pCustomerKey);
        
        //add the customer to the database
        DatabaseEntry entry = new DatabaseEntry();
        for (Descriptor d : pCustomers.getDescriptors()) {
            String descKey = d.getSkoonieKey();
            
             //store the value for the descriptor
            String value = record.getValue(descKey);
            if (value != null) { entry.storeColumn(descKey, value); }
        }
        
        getDatabase().insertEntry(entry, customersTable);
        
        getDatabase().closeDatabaseConnection();

    }//end of CustomerHandler::addCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomerHandler::deleteCustomer
    //
    // Deletes the customer associated with pSkoonieKey from the database.
    //

    public void deleteCustomer(String pSkoonieKey)
    {
        
        //WIP HSS// -- perform check to see if he can be deleted
        
        deleteRecord(customersTable, pSkoonieKey);

    }//end of CustomerHandler::deleteCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomerHandler::getCustomers
    //
    // Gets and returns the customers from the database.
    //
    // //WIP HSS// -- should actually perform check to see if the main column in
    //                  the descriptor values table is a descriptor that needs to
    //                  be read from
    //

    public Table getCustomers()
    {
        
        getDatabase().connectToDatabase();
        
        Table customers = new Table();
        
        //this list will contain the skoonie keys of all
        //the descriptors that need to be retrieved from
        //the database
        List<String> descKeys = new ArrayList<>();
        
        //get the descriptor keys for this table from the database
        for (String columnName : getDatabase().getColumnNames(customersTable)) {
            if (!columnName.equals("skoonie_key")) { descKeys.add(columnName); }
        }
        
        //get and store the descriptors for the table
        customers.setDescriptors(descriptorHandler.getDescriptors(descKeys));
        
        //get the customer data from the database
        List<DatabaseEntry> entries = getDatabase().getEntries(customersTable);
        
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
                else if (customers.getDescriptor(name) != null) {
                    r.addValue(name, value);
                }
            
            }
            
            //store the record
            customers.addRecord(r);
            
        }
        
        getDatabase().closeDatabaseConnection();
        
        return customers;

    }//end of CustomerHandler::getCustomers
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomerHandler::updateCustomer
    //
    // Updates the customer in pCustomers associated with pCustomerKey to the
    // database.
    //

    public void updateCustomer(Table pCustomers, String pCustomerKey)
    {
        
        getDatabase().connectToDatabase();
        
        //get the proper record from pCustomers
        Record record = pCustomers.getRecord(pCustomerKey);
        
        //add the customer to the database
        DatabaseEntry entry = new DatabaseEntry();
        entry.storeColumn("skoonie_key", record.getSkoonieKey());
        for (Descriptor d : pCustomers.getDescriptors()) {
            String descKey = d.getSkoonieKey();
            
             //store the value for the descriptor
            String value = record.getValue(descKey);
            if (value != null) { entry.storeColumn(descKey, value); }
        }
        
        getDatabase().updateEntry(entry, customersTable);
        
        getDatabase().closeDatabaseConnection();

    }//end of CustomerHandler::updateCustomer
    //--------------------------------------------------------------------------
    
}//end of class CustomerHandler
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------