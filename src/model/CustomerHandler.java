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
*/

//------------------------------------------------------------------------------

package model;

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

    //--------------------------------------------------------------------------
    // CustomerHandler::CustomerHandler (constructor)
    //

    public CustomerHandler(MySQLDatabase pDatabase)
    {

       super(pDatabase);

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

    }// end of CustomerHandler::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomerHandler::addCustomer
    //
    // Adds a customer using the information in pCommand.
    //

    public void addCustomer(Map<String, String> pCommand)
    {
        
        getDatabase().connectToDatabase();
        
        //add the customer to the database
        Record customerRecord = new Record();
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