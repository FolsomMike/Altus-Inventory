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
*   get customers
*
*/

//------------------------------------------------------------------------------

package model;

import shared.Record;
import shared.Descriptor;
import model.database.DatabaseEntry;
import model.database.MySQLDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    // Adds a customer using the information in pCustomer.
    //

    public void addCustomer(Record pCustomer)
    {
        //DEBUG HSS//
        /*getDatabase().connectToDatabase();
        
        //add the customer to the database
        DatabaseEntry entry = new DatabaseEntry();
        entry.storeColumn("skoonie_key", pCustomer.getSkoonieKey());
        for (Descriptor d : pCustomer.getDescriptors()) {
            d.getName();
        }
        getValues(customerRecord, pCommand, customerKeys);
        getDatabase().insertRecord(customerRecord, customersTable);
        
        getDatabase().closeDatabaseConnection();*/

    }//end of CustomerHandler::addCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomerHandler::deleteCustomer
    //
    // Deletes a customer using the information in pCommand.
    //

    public void deleteCustomer(Map<String, String> pCommand)
    {
        
        deleteRecord(pCommand, customersTable);

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

    public List<Record> getCustomers()
    {
        
        List<Record> customers = new ArrayList<>();
        
        getDatabase().connectToDatabase();
        
        //get the customer entries and all descriptors from the database
        List<DatabaseEntry> entries = getDatabase().getEntries(customersTable);
        Map<String, Descriptor> descriptors = descriptorHandler.getDescriptors();
        
        //extract data from entries and descriptors
        for (DatabaseEntry e : entries) {
            
            Record r = new Record();
            
            //iterate through the columns and column values
            for (Map.Entry<String, String> c : e.getColumns().entrySet()) {
                
                //key=column name; value=column value
                String name = c.getKey();
                String value = c.getValue();
                
                //if the column is the skoonie_key, 
                //then store it and continue to the
                //next one
                if(name.equals("skoonie_key")) { 
                    r.setSkoonieKey(value);
                    continue;
                }
                
                //get the Descriptor for the record
                Descriptor descriptor = descriptors.get(c.getKey());
                
                //if the Descriptor is not null store it and the value
                if(descriptor!= null) { r.storeDescriptor(descriptor, value); }
            
            }
            
            //store the record
            customers.add(r);
            
        }
        
        getDatabase().closeDatabaseConnection();
        
        return customers;

    }//end of CustomerHandler::getCustomers
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomerHandler::updateCustomer
    //
    // Updates a customer using the information in pCommand.
    //

    public void updateCustomer(Map<String, String> pCommand)
    {
        
        updateRecord(pCommand, customerKeys, customersTable);

    }//end of CustomerHandler::updateCustomer
    //--------------------------------------------------------------------------
    
}//end of class CustomerHandler
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------