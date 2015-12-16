/*******************************************************************************
* Title: MainModel.java
* Author: Hunter Schoonover
* Date: 12/14/15
*
* Purpose:
*
* //WIP HSS// -- describe this!!!!
* 
*/

//------------------------------------------------------------------------------

package model;

import shared.Record;
import model.database.MySQLDatabase;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class MainModel
//

public class MainModel
{
    
    private final MySQLDatabase db = new MySQLDatabase();
    
    private final BatchHandler batchHandler = new BatchHandler(db);
    
    private final CustomerHandler customerHandler = new CustomerHandler(db);

    //--------------------------------------------------------------------------
    // MainModel::MainModel (constructor)
    //

    public MainModel()
    {

    }//end of MainModel::MainModel (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainModel::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    public void init()
    {
        
        //initialize database
        db.init();
        
        //initialize the batch handler
        batchHandler.init();
        
        //initialize the customer handler
        customerHandler.init();

    }// end of MainModel::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainModel::addCustomer
    //
    // Adds pCustomer to the database.
    //

    public void addCustomer(Record pCustomer)
    {

        //WIP HSS// -- add the customer

    }//end of MainModel::addCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainModel::getCustomers
    //
    // Gets and returns all of the customers in the database
    //

    public List<Record> getCustomers()
    {

        return customerHandler.getCustomers();

    }//end of MainModel::getCustomers
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainModel::emptyDatabase
    //
    // Deletes all of the data in the database.
    //
    // //DEBUG HSS// -- for testing purposes only
    //

    public void emptyDatabase()
    {

        db.connectToDatabase();
        
        db.emptyTable("BATCHES");
        
        db.emptyTable("CUSTOMERS");
        
        db.emptyTable("DESCRIPTORS");
        
        db.emptyTable("MOVEMENTS");
        
        db.emptyTable("RECEIVEMENTS");
        
        db.closeDatabaseConnection();

    }//end of MainModel::emptyDatabase
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainModel::logSevere
    //
    // Logs pMessage with level SEVERE using the Java logger.
    //

    void logSevere(String pMessage)
    {

        Logger.getLogger(getClass().getName()).log(Level.SEVERE, pMessage);

    }//end of MainModel::logSevere
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainModel::logStackTrace
    //
    // Logs stack trace info for exception pE with pMessage at level SEVERE 
    // using the Java logger.
    //

    void logStackTrace(String pMessage, Exception pE)
    {

        Logger.getLogger(getClass().getName()).log(Level.SEVERE, pMessage, pE);

    }//end of MainModel::logStackTrace
    //--------------------------------------------------------------------------
    
}//end of class MainModel
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------