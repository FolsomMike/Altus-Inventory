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
*
*/

//------------------------------------------------------------------------------

package controller;

import java.util.Map;
import model.MySQLDatabase;
import model.Record;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class CustomerActionHandler
//

public class CustomerActionHandler extends RecordActionHandler
{

    //--------------------------------------------------------------------------
    // CustomerActionHandler::CustomerActionHandler (constructor)
    //

    public CustomerActionHandler(MySQLDatabase pDatabase)
    {

       super(pDatabase);

    }//end of CustomerActionHandler::CustomerActionHandler (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // CustomerActionHandler::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    @Override
    public void init()
    {
        
        super.init();

    }// end of CustomerActionHandler::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomerActionHandler::addCustomer
    //
    // Adds a customer using the information in pCommand.
    //

    public void addCustomer(Map<String, String> pCommand)
    {
        
        getDatabase().connectToDatabase();
        
        //add the customer to the database
        Record customerRecord = new Record();
        getValues(customerRecord, pCommand, getCustomerKeys());
        getDatabase().insertRecord(customerRecord, getCustomersTableName());
        
        getDatabase().closeDatabaseConnection();

    }//end of CustomerActionHandler::addCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomerActionHandler::deleteCustomer
    //
    // Deletes a customer using the information in pCommand.
    //

    public void deleteCustomer(Map<String, String> pCommand)
    {
        
        deleteRecord(pCommand, getCustomersTableName());

    }//end of CustomerActionHandler::deleteCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomerActionHandler::updateCustomer
    //
    // Updates a customer using the information in pCommand.
    //

    public void updateCustomer(Map<String, String> pCommand)
    {
        
        updateRecord(pCommand, getCustomerKeys(), getCustomersTableName());

    }//end of CustomerActionHandler::updateCustomer
    //--------------------------------------------------------------------------
    
}//end of class CustomerActionHandler
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------