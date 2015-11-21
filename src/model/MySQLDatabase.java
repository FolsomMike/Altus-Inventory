/*******************************************************************************
* Title: MySQLDatabase.java
* Author: Hunter Schoonover
* Date: 11/18/15
*
* Purpose:
*
* This class is used to connect to and send and retrieve data to and from the 
* MySQL database containing all of the data for the program.
* 
* NOTES:
*   Since opening a connection can be an expensive process for the server, it is
*   best to minimize the number of times you close and reopen the connection. 
*   This problem should not be solved by keeping a connection open for all of 
*   eternity; this uses valuable resources and can negatively affect the 
*   application's overall performance. Instead, if you have a series of commands
*   that you are sending to the database one after another, then open the 
*   connection once by calling connectToDatabase() and close it by calling 
*   closeDatabaseConnection() after all of the commands have been sent. A long 
*   wait time between command calls should be handled by opening and closing the
*   connection between each series of calls.
* 
* In order to use this class, the latest version of Connector/J must be added to
* to the project. In order to add Connector/J to your project in NetBeans, 
* follow these steps:
* 1. Download the latest version of Connector/J from 
*       http://dev.mysql.com/downloads/connector/j/
* 
* 2. After downloading, unzip the folder to a general location that is not
*       specific to your project; the driver can be used for future projects.
* 
* 3. Copy the jar file from the unzipped folder to your Netbeans project folder.
*       The jar file will have look similar to 
*       mysql-connector-java-5.1.37-bin.jar but will vary depending on the
*       latest version.
* 
* 4. Launch Netbeans and expand your project in the Projects pane to the left.
*
* 5. Right-click on Libraries and click "Add Jar/Folder".
* 
* 6. Select the jar file that you just copied into your project folder.
* 
* 7. Before using the driver, you have to register it: 
*       Class.forName("com.mysql.jdbc.Driver");
* 
*       For more information on why you have to register the driver:
*           http://www.xyzws.com/Javafaq/what-does-classforname-method-do/17
*
* Open Source Policy:
*
* This source code is Public Domain and free to any interested party.  Any
* person, company, or organization may do with it as they please.
*
*/

//------------------------------------------------------------------------------

package model;

//------------------------------------------------------------------------------

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class MySQLDatabase
//

public class MySQLDatabase 
{
    
    private final String url = "jdbc:mysql://108.167.140.102:3306/"
                                    + "hunter98_altus-inventory";
    private final String username = "hunter98_altus";
    private final String password = "shyanismineagain98";
    
    private Connection connection;
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::MySQLDatabase (constructor)
    //

    public MySQLDatabase()
    {

    }//end of MySQLDatabase::MySQLDatabase (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    public void init()
    {
        
        registerJDBCDriver();

    }//end of MySQLDatabase::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::closeDatabaseConnection
    //
    // Closes the connection to the database.
    //
    // This should be called after sending commands to the database to improve 
    // code performance and free up resources.
    // Read the NOTES at the top of the file for more information on when to
    // connect and when to close the connection.
    //

    private void closeDatabaseConnection()
    {
        
        try { 
            //return if the connection is not open
            if (connection == null || connection.isClosed()) { return; }
            
            connection.close();
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 129"); }
        
    }//end of MySQLDatabase::closeDatabaseConnection
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::closeResultSet
    //
    // Closes the passed in ResultSet to free up resources.
    //

    private void closeResultSet(ResultSet pSet)
    {
        
        try { if (pSet != null) { pSet.close(); } }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 144"); }

    }//end of MySQLDatabase::closeResultSet
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::closePreparedStatement
    //
    // Closes the passed in PreparedStatement to free up resources.
    //

    private void closePreparedStatement(PreparedStatement pStatement)
    {
        
        try { if (pStatement != null) { pStatement.close(); } }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 161"); }

    }//end of MySQLDatabase::closePreparedStatement
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::connectToDatabase
    //
    // Establishes a connection with the database if one is not already
    // established.
    //
    // Returns true upon success; false upon failure.
    //
    // This should be called before sending any commands to the database. 
    // Read the NOTES at the top of the file for more information on when to
    // connect and when to close the connection.
    //

    private boolean connectToDatabase()
    {
        
        boolean success = true;
        
        try { 
            //return true if we are already connected
            if(connection != null && !connection.isClosed()) { return success; }
            
            connection = DriverManager.getConnection(url, username, password);
        }
        catch (SQLException e) { 
            success = false;
            logSevere(e.getMessage() + " - Error: 191"); 
        }
        
        return success;
        
    }//end of MySQLDatabase::connectToDatabase
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::createPreparedStatement
    //
    // Creates a PreparedStatment using the passed in SQL command string.
    // Returns PreparedStatment upon success; null upon failure.
    //

    private PreparedStatement createPreparedStatement(String pCommand)
    {
        
        PreparedStatement stmt = null;
        
        //ensure that we are connected to the database -- return null if a 
        //conection can't be established
        if (!connectToDatabase()) { return stmt; }
        
        try { stmt = connection.prepareStatement(pCommand); }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 216"); }
        
        return stmt;

    }//end of MySQLDatabase::createPreparedStatement
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::getBatches
    //
    // Gets and returns all of the batches in the BATCHES table and stores all
    // of the data pertaining to a batch in a Batch object. All of the Batch 
    // objects are returned in an array list.
    //

    public ArrayList<Batch> getBatches()
    {
        
        ArrayList<Batch> batches = new ArrayList();

        String cmd = "SELECT * FROM `BATCHES` ORDER BY `id` ASC"; //WIP HSS// -- order by should be specified by user
        PreparedStatement stmt = createPreparedStatement(cmd);
        ResultSet set = performQuery(stmt);
        
        //extract the data from the ResultSet
        try {
            while (set.next()) { 
                String id       = set.getString("id");
                String cusId    = set.getString("customer_id");
                String date     = set.getString("date_created");
                String quantity = set.getString("quantity");
                String rack     = set.getString("rack");
                String length   = set.getString("total_length");
                
                //store the batch information
                batches.add(new Batch(id, cusId, date, quantity, rack, length));
            }
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 254"); }
        
        //clean up environment
        closeResultSet(set);
        closePreparedStatement(stmt);
        closeDatabaseConnection();
        
        return batches;

    }//end of MySQLDatabase::getBatches
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::getCustomers
    //
    // Gets and returns all of the customers in the CUSTOMERS table and stores
    // all of the data pertaining to a customer in a Customer object. All of 
    // the Customer objects are returned in an array list.
    //

    public ArrayList<Customer> getCustomers()
    {
        
        ArrayList<Customer> customers = new ArrayList();

        String cmd = "SELECT * FROM `CUSTOMERS` ORDER BY `display_name` ASC";
        PreparedStatement stmt = createPreparedStatement(cmd);
        ResultSet set = performQuery(stmt);
        
        //extract the data from the ResultSet
        try {
            while (set.next()) { 
                String id       = set.getString("id");
                String name     = set.getString("display_name");
                String line1    = set.getString("address_line_1");
                String line2    = set.getString("address_line_2");
                String city     = set.getString("city");
                String state    = set.getString("state");
                String zip      = set.getString("zip_code");
                
                //store the customer information
                customers.add(new Customer(id, name, line1, line2, 
                                                city, state, zip));
            }
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 298"); }
        
        //clean up environment
        closeResultSet(set);
        closePreparedStatement(stmt);
        closeDatabaseConnection();
        
        return customers;

    }//end of MySQLDatabase::getCustomers
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::getBatches
    //
    // Gets and returns all of the batches in the BATCHES table and stores all
    // of the data pertaining to a batch in a Batch object. All of the Batch 
    // objects are returned in an array list.
    //

    public ArrayList<Rack> getRacks()
    {
        
        ArrayList<Rack> racks = new ArrayList();

        String cmd = "SELECT * FROM `RACKS` ORDER BY `name` ASC";
        PreparedStatement stmt = createPreparedStatement(cmd);
        ResultSet set = performQuery(stmt);
        
        //extract the data from the ResultSet
        try {
            while (set.next()) { 
                String name = set.getString("name");
                
                //store the rack information
                racks.add(new Rack(name));
            }
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 336"); }
        
        //clean up environment
        closeResultSet(set);
        closePreparedStatement(stmt);
        closeDatabaseConnection();
        
        return racks;

    }//end of MySQLDatabase::getRacks
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::logSevere
    //
    // Logs pMessage with level SEVERE using the Java logger.
    //

    private void logSevere(String pMessage)
    {

        Logger.getLogger(getClass().getName()).log(Level.SEVERE, pMessage);

    }//end of MySQLDatabase::logSevere
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MySQLDatabase::logStackTrace
    //
    // Logs stack trace info for exception pE with pMessage at level SEVERE 
    // using the Java logger.
    //

    private void logStackTrace(String pMessage, Exception pE)
    {

        Logger.getLogger(getClass().getName()).log(Level.SEVERE, pMessage, pE);

    }//end of MySQLDatabase::logStackTrace
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::performQuery
    //
    // Performs a query on the database using the passed in PreparedStatment. 
    // Returns ResultSet upon success; null upon failure.
    //
    // NOTE:    To improve code performance and free up resources, the ResultSet 
    //          returned should be closed after use by using the 
    //          closeResultSet() helper function provided by this class.
    //

    private ResultSet performQuery(PreparedStatement pStatement)
    {
        
        ResultSet set = null;
        
        //ensure that we are connected to the database -- return null if a 
        //conection can't be established
        if (!connectToDatabase()) { return set; }
        
        try { set = pStatement.executeQuery(); }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 399"); }
        
        return set;

    }//end of MySQLDatabase::performQuery
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::registerJDBCDriver
    //
    // Loads and registers the JDBC driver.
    //

    private void registerJDBCDriver()
    {
        
        //Register JDBC driver
        try { Class.forName("com.mysql.jdbc.Driver"); }
        catch (ClassNotFoundException e) { 
            logSevere(e.getMessage() + " - Error: 418"); 
        }

    }//end of MySQLDatabase::registerJDBCDriver
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::updateCustomer
    //
    // Updates the customer associated with pId by extracting the data in
    // pCustomer.
    //

    public void updateCustomer(String pId, Customer pCustomer)
    {
        
        String cmd = "UPDATE `CUSTOMERS` SET "
                        + "`id`=?,"             //placeholder 1
                        + "`display_name`=?,"   //placeholder 2
                        + "`address_line_1`=?," //placeholder 3
                        + "`address_line_2`=?," //placeholder 4
                        + "`city`=?,"           //placeholder 5
                        + "`state`=?,"          //placeholder 6
                        + "`zip_code`=? "       //placeholder 7
                        + "WHERE `id`=?";       //placeholder 8
        
        PreparedStatement stmt = createPreparedStatement(cmd);
        
        try {
            stmt.setString(1, pCustomer.getId());
            stmt.setString(2, pCustomer.getDisplayName());
            stmt.setString(3, pCustomer.getAddressLine1());
            stmt.setString(4, pCustomer.getAddressLine2());
            stmt.setString(5, pCustomer.getCity());
            stmt.setString(6, pCustomer.getState());
            stmt.setString(7, pCustomer.getZipCode());
            stmt.setString(8, pId);
            
            //execute the statement
            stmt.execute();
        }
        catch (SQLException e) {logSevere(e.getMessage() + " - Error: 458");}
        
        //clean up environment
        closePreparedStatement(stmt);
        closeDatabaseConnection();
                
    }//end of MySQLDatabase::updateCustomer
    //--------------------------------------------------------------------------
    
}//end of class MySQLDatabase
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
