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
*   delete customer
*   update customer
* 
*   add customer descriptor
*   delete customer descriptor
*
*/

//------------------------------------------------------------------------------

package model;

import model.database.DatabaseEntry;
import model.database.MySQLDatabase;
import command.Command;
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

    public void addCustomer(Table pCustomer)
    {
        
        getDatabase().connectToDatabase();
        
        //add the customer to the database
        DatabaseEntry entry = new DatabaseEntry();
        entry.storeColumn("skoonie_key", pCustomer.getSkoonieKey());
        for (Descriptor d : pCustomer.getDescriptors()) {
            d.getName();
        }
        getValues(customerRecord, pCommand, customerKeys);
        getDatabase().insertRecord(customerRecord, customersTable);
        
        getDatabase().closeDatabaseConnection();

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

    public Table getCustomers()
    {
        
        Table customers = new Table();
        
        getDatabase().connectToDatabase();
        
        customers.setSkoonieKeys(getDatabase().getSkoonieKeys(customersTable));
        
        customers.setDescriptors(descriptorHandler.getDescriptors());
        
        //get the customers and sticks them in pCommand
        pCommand.put("customers", getDatabase().getRecords(customersTable));
        
        getDatabase().closeDatabaseConnection();

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