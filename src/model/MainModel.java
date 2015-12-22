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

import java.util.List;
import shared.Table;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.Descriptor;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class MainModel
//

public class MainModel
{
    
    private final RecordHandler recordHandler = new RecordHandler();

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

    }// end of MainModel::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainModel::addCustomer
    //
    // Adds the customer in pCustomers associated with pCustomerKey to the
    // database.
    //

    public void addCustomer(Table pCustomers, String pCustomerKey)
    {

        recordHandler.addCustomer(pCustomers, pCustomerKey);

    }//end of MainModel::addCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainModel::addCustomerDescriptor
    //
    // Adds the descriptor to the customers table.
    //

    public void addCustomerDescriptor(Descriptor pDescriptor)
    {

        recordHandler.addCustomerDescriptor(pDescriptor);

    }//end of MainModel::addCustomerDescriptor
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainModel::deleteCustomer
    //
    // Deletes the customer associated with pSkoonieKey from the database.
    //

    public void deleteCustomer(String pSkoonieKey)
    {

        recordHandler.deleteCustomer(pSkoonieKey);

    }//end of MainModel::deleteCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainModel::getCustomers
    //
    // Gets and returns all of the customers in the database
    //

    public Table getCustomers()
    {

        return recordHandler.getCustomers();

    }//end of MainModel::getCustomers
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainModel::getCustomerDescriptors
    //
    // Gets and returns all of the descriptors used for customers.
    //

    public List<Descriptor> getCustomerDescriptors()
    {

        return recordHandler.getCustomerDescriptors();

    }//end of MainModel::getCustomerDescriptors
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
    
    //--------------------------------------------------------------------------
    // MainModel::updateCustomer
    //
    // Updates the customer in pCustomers associated with pCustomerKey to the
    // database.
    //

    public void updateCustomer(Table pCustomers, String pCustomerKey)
    {

        recordHandler.updateCustomer(pCustomers, pCustomerKey);

    }//end of MainModel::updateCustomer
    //--------------------------------------------------------------------------
    
}//end of class MainModel
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------