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

    }// end of MySQLDatabase::MySQLDatabase (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    public void init()
    {
        
        registerJDBCDriver();

    }// end of MySQLDatabase::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::checkForValue
    //
    // Checks for pValue in pTable under pColumn.
    //

    public boolean checkForValue(String pValue, String pTable, String pColumn)
    {

        String cmd = "SELECT COUNT(*) FROM " +pTable+ " WHERE " +pColumn+ "=?";
        
        int count = 0;
        
        PreparedStatement stmt = createPreparedStatement(cmd);
        
        //perform query
        try { 
            stmt.setString(1, pValue);
            
            ResultSet set = performQuery(stmt);
            
            //extract the count from the ResultSet
            while (set.next()) { count = set.getInt(1); }
            
            //clean up environment
            closeResultSet(set);
            
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 134"); }
        
        //clean up environment
        closePreparedStatement(stmt);
        closeDatabaseConnection();
        
        return (count > 0);
        
    }// end of MySQLDatabase::checkForValue
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
        
    }// end of MySQLDatabase::closeDatabaseConnection
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

    }// end of MySQLDatabase::closeResultSet
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

    }// end of MySQLDatabase::closePreparedStatement
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
        
    }// end of MySQLDatabase::connectToDatabase
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

    }// end of MySQLDatabase::createPreparedStatement
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::deleteCustomer
    //
    // Deletes pCustomer from the CUSTOMERS table in the database.
    //

    public void deleteCustomer(Customer pCustomer)
    {
        
        String cmd = "DELETE FROM `CUSTOMERS` WHERE `skoonie_key`=?";
        
        PreparedStatement stmt = createPreparedStatement(cmd);
        
        try {
            stmt.setString(1, pCustomer.getSkoonieKey());
            
            //execute the statement
            stmt.execute();
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 237"); }
        
        //clean up environment
        closePreparedStatement(stmt);
        closeDatabaseConnection();

    }// end of MySQLDatabase::deleteCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::deleteRack
    //
    // Deletes pRack from the RACKS table in the database.
    //

    public void deleteRack(Rack pRack)
    {
        
        String cmd = "DELETE FROM `RACKS` WHERE `skoonie_key`=?";
        
        PreparedStatement stmt = createPreparedStatement(cmd);
        
        try {
            stmt.setString(1, pRack.getSkoonieKey());
            
            //execute the statement
            stmt.execute();
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 265"); }
        
        //clean up environment
        closePreparedStatement(stmt);
        closeDatabaseConnection();

    }// end of MySQLDatabase::deleteRack
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::deleteTruckCompany
    //
    // Deletes pCompany from the TRUCK COMPANIES table in the database.
    //

    public void deleteTruckCompany(TruckCompany pCompany)
    {
        
        String cmd = "DELETE FROM `TRUCK_COMPANIES` WHERE `skoonie_key`=?";
        
        PreparedStatement stmt = createPreparedStatement(cmd);
        
        try {
            stmt.setString(1, pCompany.getSkoonieKey());
            
            //execute the statement
            stmt.execute();
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 332"); }
        
        //clean up environment
        closePreparedStatement(stmt);
        closeDatabaseConnection();

    }// end of MySQLDatabase::deleteTruckCompany
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::deleteTruckDriver
    //
    // Deletes pTruckDriver from the TRUCK_DRIVERS table in the database.
    //

    public void deleteTruckDriver(TruckDriver pTruckDriver)
    {
        
        String cmd = "DELETE FROM `TRUCK_DRIVERS` WHERE `skoonie_key`=?";
        
        PreparedStatement stmt = createPreparedStatement(cmd);
        
        try {
            stmt.setString(1, pTruckDriver.getSkoonieKey());
            
            //execute the statement
            stmt.execute();
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 360"); }
        
        //clean up environment
        closePreparedStatement(stmt);
        closeDatabaseConnection();

    }// end of MySQLDatabase::deleteTruckDriver
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::deleteTruck
    //
    // Deletes pTruck from the TRUCKS table in the database.
    //

    public void deleteTruck(Truck pTruck)
    {
        
        String cmd = "DELETE FROM `TRUCKS` WHERE `skoonie_key`=?";
        
        PreparedStatement stmt = createPreparedStatement(cmd);
        
        try {
            stmt.setString(1, pTruck.getSkoonieKey());
            
            //execute the statement
            stmt.execute();
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 360"); }
        
        //clean up environment
        closePreparedStatement(stmt);
        closeDatabaseConnection();

    }// end of MySQLDatabase::deleteTruck
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
                String sKey     = set.getString("skoonie_key");
                String id       = set.getString("id");
                String date     = set.getString("date");
                String quantity = set.getString("quantity");
                String length   = set.getString("total_length");
                String cKey     = set.getString("customer_key");
                String rKey     = set.getString("rack_key");
                
                //store the batch information
                batches.add(new Batch(sKey, id, date, quantity, length, cKey,
                                        rKey));
            }
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 251"); }
        
        //clean up environment
        closeResultSet(set);
        closePreparedStatement(stmt);
        closeDatabaseConnection();
        
        return batches;

    }// end of MySQLDatabase::getBatches
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::getCustomer
    //
    // Gets and returns the customer associated with pSkoonieKey from the 
    // CUSTOMERS table in a Customer object.
    //

    public Customer getCustomer(String pSkoonieKey)
    {
        
        Customer c = null;

        String cmd = "SELECT * FROM `CUSTOMERS` WHERE `skoonie_key` = ?";
        PreparedStatement stmt = createPreparedStatement(cmd);
        
        //perform query
        try {
            stmt.setString(1, pSkoonieKey);
            
            ResultSet set = performQuery(stmt);
            
            //extract the data from the ResultSet
            while (set.next()) { 
                String sKey     = set.getString("skoonie_key");
                String id       = set.getString("id");
                String name     = set.getString("name");
                String line1    = set.getString("address_line_1");
                String line2    = set.getString("address_line_2");
                String city     = set.getString("city");
                String state    = set.getString("state");
                String zip      = set.getString("zip_code");
                
                //store the customer information
                c = new Customer(sKey, id, name, line1, line2, 
                                    city, state, zip);
            }
            
            //clean up environment
            closeResultSet(set);
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 306"); }
        
        //clean up environment
        closePreparedStatement(stmt);
        closeDatabaseConnection();
        
        return c;

    }// end of MySQLDatabase::getCustomer
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

        String cmd = "SELECT * FROM `CUSTOMERS` ORDER BY `name` ASC";
        PreparedStatement stmt = createPreparedStatement(cmd);
        ResultSet set = performQuery(stmt);
        
        //extract the data from the ResultSet
        try {
            while (set.next()) { 
                String sKey     = set.getString("skoonie_key");
                String id       = set.getString("id");
                String name     = set.getString("name");
                String line1    = set.getString("address_line_1");
                String line2    = set.getString("address_line_2");
                String city     = set.getString("city");
                String state    = set.getString("state");
                String zip      = set.getString("zip_code");
                
                //store the customer information
                customers.add(new Customer(sKey, id, name, line1, line2, 
                                                city, state, zip));
            }
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 351"); }
        
        //clean up environment
        closeResultSet(set);
        closePreparedStatement(stmt);
        closeDatabaseConnection();
        
        return customers;

    }// end of MySQLDatabase::getCustomers
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::getRack
    //
    // Gets and returns the rack associated with pSkoonieKey from the RACKS 
    // table in a Rack object.
    //

    public Rack getRack(String pSkoonieKey)
    {
        
        Rack r = null;

        String cmd = "SELECT * FROM `RACKS` WHERE `skoonie_key` = ?";
        PreparedStatement stmt = createPreparedStatement(cmd);
        ResultSet set;
        
        //perform query
        try {
            stmt.setString(1, pSkoonieKey);
            set = performQuery(stmt);
            
            //extract the data from the ResultSet
            while (set.next()) { 
                String sKey     = set.getString("skoonie_key");
                String id       = set.getString("id");
                String name     = set.getString("name");
                
                //store the rack information
                r = new Rack(sKey, id, name);
            }
            
            //clean up environment
            closeResultSet(set);
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 434"); }
        
        //clean up environment
        closePreparedStatement(stmt);
        closeDatabaseConnection();
        
        return r;

    }// end of MySQLDatabase::getRack
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::getRacks
    //
    // Gets and returns all of the racks in the RACKS table and stores all of 
    // the data pertaining to a rack in a Rack object. All of the Rack objects 
    // are returned in an array list.
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
                String sKey     = set.getString("skoonie_key");
                String id       = set.getString("id");
                String name     = set.getString("name");
                
                //store the rack information
                racks.add(new Rack(sKey, id, name));
            }
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 387"); }
        
        //clean up environment
        closeResultSet(set);
        closePreparedStatement(stmt);
        closeDatabaseConnection();
        
        return racks;

    }// end of MySQLDatabase::getRacks
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::getTruckCompanies
    //
    // Gets and returns all of the truck companeis in the TRUCK COMPANIES table
    // and stores all of the data pertaining to a truck company in a 
    // TruckCompany object. All of the TruckCompany objects are returned in an
    // array list.
    //

    public ArrayList<TruckCompany> getTruckCompanies()
    {
        
        ArrayList<TruckCompany> companies = new ArrayList();

        String cmd = "SELECT * FROM `TRUCK_COMPANIES` ORDER BY `name` ASC";
        PreparedStatement stmt = createPreparedStatement(cmd);
        ResultSet set = performQuery(stmt);
        
        //extract the data from the ResultSet
        try {
            while (set.next()) { 
                String sKey     = set.getString("skoonie_key");
                String id       = set.getString("id");
                String name     = set.getString("name");
                
                //store the truck company information
                companies.add(new TruckCompany(sKey, id, name));
            }
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 569"); }
        
        //clean up environment
        closeResultSet(set);
        closePreparedStatement(stmt);
        closeDatabaseConnection();
        
        return companies;

    }// end of MySQLDatabase::getTruckCompanies
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::getTruckDrivers
    //
    // Gets and returns all of the truck drivers in the TRUCKDRIVERS table and 
    // stores all of the data pertaining to a truck driver in a TruckDriver
    // object. All of the TruckDriver objects are returned in an array list.
    //

    public ArrayList<TruckDriver> getTruckDrivers()
    {
        
        ArrayList<TruckDriver> drivers = new ArrayList();

        String cmd = "SELECT * FROM `TRUCK_DRIVERS` ORDER BY `name` ASC";
        PreparedStatement stmt = createPreparedStatement(cmd);
        ResultSet set = performQuery(stmt);
        
        //extract the data from the ResultSet
        try {
            while (set.next()) { 
                String sKey     = set.getString("skoonie_key");
                String id       = set.getString("id");
                String name     = set.getString("name");
                String comp_key = set.getString("truck_company_key");
                
                //store the driver information
                drivers.add(new TruckDriver(sKey, id, name, comp_key));
            }
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 666"); }
        
        //clean up environment
        closeResultSet(set);
        closePreparedStatement(stmt);
        closeDatabaseConnection();
        
        return drivers;

    }// end of MySQLDatabase::getTruckDrivers
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::getTrucks
    //
    // Gets and returns all of the trucks in the TRUCKS table and stores all of
    // the data pertaining to a truck company in a Truck object. All of the 
    // Truck objects are returned in an array list.
    //

    public ArrayList<Truck> getTrucks()
    {
        
        ArrayList<Truck> trucks = new ArrayList();

        String cmd = "SELECT * FROM `TRUCKS` ORDER BY `name` ASC";
        PreparedStatement stmt = createPreparedStatement(cmd);
        ResultSet set = performQuery(stmt);
        
        //extract the data from the ResultSet
        try {
            while (set.next()) { 
                String sKey     = set.getString("skoonie_key");
                String id       = set.getString("id");
                String name     = set.getString("name");
                String comp_key = set.getString("truck_company_key");
                
                //store the truck information
                trucks.add(new Truck(sKey, id, name, comp_key));
            }
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 638"); }
        
        //clean up environment
        closeResultSet(set);
        closePreparedStatement(stmt);
        closeDatabaseConnection();
        
        return trucks;

    }// end of MySQLDatabase::getTrucks
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::insertBatch
    //
    // Inserts pBatch into the database.
    //

    public void insertBatch(Batch pBatch)
    {
        
        String cmd = "INSERT INTO `BATCHES` ("
                        + "`id`,"
                        + "`date`,"
                        + "`quantity`,"
                        + "`total_length`,"
                        + "`customer_key`,"
                        + "`rack_key`) "
                        + "VALUES ("
                        + "?,"  //placeholder 1     Id
                        + "?,"  //placeholder 2     Date
                        + "?,"  //placeholder 3     Quantity
                        + "?,"  //placeholder 4     Total Length
                        + "?,"  //placeholder 5     Customer Key
                        + "?"   //placeholder 6     Rack Key
                        + ")";
        
        PreparedStatement stmt = createPreparedStatement(cmd);
        
        try {
            stmt.setString(1, pBatch.getId());
            stmt.setString(2, pBatch.getDate());
            stmt.setString(3, pBatch.getQuantity());
            stmt.setString(4, pBatch.getTotalLength());
            stmt.setString(5, pBatch.getCustomerKey());
            stmt.setString(6, pBatch.getRackKey());
            
            //execute the statement
            stmt.execute();
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 578"); }
        
        //clean up environment
        closePreparedStatement(stmt);
        closeDatabaseConnection();
                
    }// end of MySQLDatabase::insertBatch
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::insertCustomer
    //
    // Inserts pCustomer into the database.
    //

    public void insertCustomer(Customer pCustomer)
    {
        
        String cmd = "INSERT INTO `CUSTOMERS` ("
                        + "`id`,`name`,"
                        + "`address_line_1`,`address_line_2`,"
                        + "`city`,`state`,`zip_code`) "
                        + "VALUES ("
                        + "?,"  //placeholder 1     Id
                        + "?,"  //placeholder 2     Name
                        + "?,"  //placeholder 3     Address Line 1
                        + "?,"  //placeholder 4     Address Line 2
                        + "?,"  //placeholder 5     City
                        + "?,"  //placeholder 6     State
                        + "?)"; //placeholder 7     Zip Code
        
        PreparedStatement stmt = createPreparedStatement(cmd);
        
        try {
            stmt.setString(1, pCustomer.getId());
            stmt.setString(2, pCustomer.getName());
            stmt.setString(3, pCustomer.getAddressLine1());
            stmt.setString(4, pCustomer.getAddressLine2());
            stmt.setString(5, pCustomer.getCity());
            stmt.setString(6, pCustomer.getState());
            stmt.setString(7, pCustomer.getZipCode());
            
            //execute the statement
            stmt.execute();
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 437"); }
        
        //clean up environment
        closePreparedStatement(stmt);
        closeDatabaseConnection();
                
    }// end of MySQLDatabase::insertCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::insertRack
    //
    // Inserts pRack into the database.
    //

    public void insertRack(Rack pRack)
    {
        
        String cmd = "INSERT INTO `RACKS` ("
                        + "`id`,`name`) "
                        + "VALUES ("
                        + "?,"  //placeholder 1     Id
                        + "?)"; //placeholder 2     Name
        
        PreparedStatement stmt = createPreparedStatement(cmd);
        
        try {
            stmt.setString(1, pRack.getId());
            stmt.setString(2, pRack.getName());
            
            //execute the statement
            stmt.execute();
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 510"); }
        
        //clean up environment
        closePreparedStatement(stmt);
        closeDatabaseConnection();
                
    }// end of MySQLDatabase::insertRack
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::insertTruck
    //
    // Inserts pTruck into the database.
    //

    public void insertTruck(Truck pTruck)
    {
        
        String cmd = "INSERT INTO `TRUCKS` ("
                        + "`id`,`name`,`truck_company_key`) "
                        + "VALUES ("
                        + "?,"  //placeholder 1     Id
                        + "?,"  //placeholder 2     Name
                        + "?)"; //placeholder 3     Truck Company Key
        
        PreparedStatement stmt = createPreparedStatement(cmd);
        
        try {
            stmt.setString(1, pTruck.getId());
            stmt.setString(2, pTruck.getName());
            stmt.setString(3, pTruck.getTruckCompanyKey());
            
            //execute the statement
            stmt.execute();
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 801"); }
        
        //clean up environment
        closePreparedStatement(stmt);
        closeDatabaseConnection();
                
    }// end of MySQLDatabase::insertTruck
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::insertTruckCompany
    //
    // Inserts pCompany into the database.
    //

    public void insertTruckCompany(TruckCompany pCompany)
    {
        
        String cmd = "INSERT INTO `TRUCK_COMPANIES` ("
                        + "`id`,`name`) "
                        + "VALUES ("
                        + "?,"  //placeholder 1     Id
                        + "?)"; //placeholder 2     Name
        
        PreparedStatement stmt = createPreparedStatement(cmd);
        
        try {
            stmt.setString(1, pCompany.getId());
            stmt.setString(2, pCompany.getName());
            
            //execute the statement
            stmt.execute();
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 758"); }
        
        //clean up environment
        closePreparedStatement(stmt);
        closeDatabaseConnection();
                
    }// end of MySQLDatabase::insertTruckCompany
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::insertTruckDriver
    //
    // Inserts pTruckDriver into the database.
    //

    public void insertTruckDriver(TruckDriver pTruckDriver)
    {
        
        String cmd = "INSERT INTO `TRUCK_DRIVERS` ("
                        + "`id`,`name`,`truck_company_key`) "
                        + "VALUES ("
                        + "?,"  //placeholder 1     Id
                        + "?,"  //placeholder 2     Name
                        + "?)"; //placeholder 3     Truck Company Key
        
        PreparedStatement stmt = createPreparedStatement(cmd);
        
        try {
            stmt.setString(1, pTruckDriver.getId());
            stmt.setString(2, pTruckDriver.getName());
            stmt.setString(3, pTruckDriver.getTruckCompanyKey());
            
            //execute the statement
            stmt.execute();
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 938"); }
        
        //clean up environment
        closePreparedStatement(stmt);
        closeDatabaseConnection();
                
    }// end of MySQLDatabase::insertTruckDriver
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::logSevere
    //
    // Logs pMessage with level SEVERE using the Java logger.
    //

    private void logSevere(String pMessage)
    {

        Logger.getLogger(getClass().getName()).log(Level.SEVERE, pMessage);

    }// end of MySQLDatabase::logSevere
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

    }// end of MySQLDatabase::logStackTrace
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
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 718"); }
        
        return set;

    }// end of MySQLDatabase::performQuery
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
            logSevere(e.getMessage() + " - Error: 477"); 
        }

    }// end of MySQLDatabase::registerJDBCDriver
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    // MySQLDatabase::updateBatch
    //
    // Updates pBatch in the database.
    //

    public void updateBatch(Batch pBatch)
    {
        
        String cmd = "UPDATE `BATCHES` SET "
                        + "`id`=?,"                 //placeholder 1     id
                        + "`date`=?,"               //placeholder 2     date
                        + "`quantity`=?,"           //placeholder 3     quantity
                        + "`total_length`=?,"       //placeholder 4     total length
                        + "`customer_key`=?,"       //placeholder 5     customer key
                        + "`rack_key`=?"            //placeholder 6     rack key
                        + "WHERE `skoonie_key`=?";  //placeholder 7     skoonie key
        
        PreparedStatement stmt = createPreparedStatement(cmd);
        
        try {
            stmt.setString(1, pBatch.getId());
            stmt.setString(2, pBatch.getDate());
            stmt.setString(3, pBatch.getQuantity());
            stmt.setString(4, pBatch.getTotalLength());
            stmt.setString(5, pBatch.getCustomerKey());
            stmt.setString(6, pBatch.getRackKey());
            stmt.setString(7, pBatch.getSkoonieKey());
            
            //execute the statement
            stmt.execute();
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 676"); }
        
        //clean up environment
        closePreparedStatement(stmt);
        closeDatabaseConnection();
                
    }// end of MySQLDatabase::updateBatch
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::updateCustomer
    //
    // Updates pCustomer in the database.
    //

    public void updateCustomer(Customer pCustomer)
    {
        
        String cmd = "UPDATE `CUSTOMERS` SET "
                        + "`id`=?,"                 //placeholder 1
                        + "`name`=?,"               //placeholder 2
                        + "`address_line_1`=?,"     //placeholder 3
                        + "`address_line_2`=?,"     //placeholder 4
                        + "`city`=?,"               //placeholder 5
                        + "`state`=?,"              //placeholder 6
                        + "`zip_code`=? "           //placeholder 7
                        + "WHERE `skoonie_key`=?";  //placeholder 8
        
        PreparedStatement stmt = createPreparedStatement(cmd);
        
        try {
            stmt.setString(1, pCustomer.getId());
            stmt.setString(2, pCustomer.getName());
            stmt.setString(3, pCustomer.getAddressLine1());
            stmt.setString(4, pCustomer.getAddressLine2());
            stmt.setString(5, pCustomer.getCity());
            stmt.setString(6, pCustomer.getState());
            stmt.setString(7, pCustomer.getZipCode());
            stmt.setString(8, pCustomer.getSkoonieKey());
            
            //execute the statement
            stmt.execute();
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 517"); }
        
        //clean up environment
        closePreparedStatement(stmt);
        closeDatabaseConnection();
                
    }// end of MySQLDatabase::updateCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::updateRack
    //
    // Updates pRack in the database.
    //

    public void updateRack(Rack pRack)
    {
        
        String cmd = "UPDATE `RACKS` SET "
                        + "`id`=?,"                 //placeholder 1     Id
                        + "`name`=?"                //placeholder 2     Name
                        + "WHERE `skoonie_key`=?";  //placeholder 3     Skoonie Key
        
        PreparedStatement stmt = createPreparedStatement(cmd);
        
        try {
            stmt.setString(1, pRack.getId());
            stmt.setString(2, pRack.getName());
            stmt.setString(3, pRack.getSkoonieKey());
            
            //execute the statement
            stmt.execute();
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 662"); }
        
        //clean up environment
        closePreparedStatement(stmt);
        closeDatabaseConnection();
                
    }// end of MySQLDatabase::updateRack
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::updateTruckCompany
    //
    // Updates pTruckCompany in the database.
    //

    public void updateTruckCompany(TruckCompany pCompany)
    {
        
        String cmd = "UPDATE `TRUCK_COMPANIES` SET "
                        + "`id`=?,"                 //placeholder 1
                        + "`name`=?"                //placeholder 2
                        + "WHERE `skoonie_key`=?";  //placeholder 3
        
        PreparedStatement stmt = createPreparedStatement(cmd);
        
        try {
            stmt.setString(1, pCompany.getId());
            stmt.setString(2, pCompany.getName());
            stmt.setString(3, pCompany.getSkoonieKey());
            
            //execute the statement
            stmt.execute();
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 982"); }
        
        //clean up environment
        closePreparedStatement(stmt);
        closeDatabaseConnection();
                
    }// end of MySQLDatabase::updateTruckCompany
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::updateTruck
    //
    // Updates pTruckDriver in the database.
    //

    public void updateTruckDriver(TruckDriver pTruckDriver)
    {
        
        String cmd = "UPDATE `TRUCK_DRIVERS` SET "
                        + "`id`=?,"                 //placeholder 1
                        + "`name`=?,"               //placeholder 2
                        + "`truck_company_key`=?"   //placeholder 3
                        + "WHERE `skoonie_key`=?";  //placeholder 4
        
        PreparedStatement stmt = createPreparedStatement(cmd);
        
        try {
            stmt.setString(1, pTruckDriver.getId());
            stmt.setString(2, pTruckDriver.getName());
            stmt.setString(3, pTruckDriver.getTruckCompanyKey());
            stmt.setString(4, pTruckDriver.getSkoonieKey());
            
            //execute the statement
            stmt.execute();
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 1197"); }
        
        //clean up environment
        closePreparedStatement(stmt);
        closeDatabaseConnection();
                
    }// end of MySQLDatabase::updateTruckDriver
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::updateTruck
    //
    // Updates pTruck in the database.
    //

    public void updateTruck(Truck pTruck)
    {
        
        String cmd = "UPDATE `TRUCKS` SET "
                        + "`id`=?,"                 //placeholder 1
                        + "`name`=?,"               //placeholder 2
                        + "`truck_company_key`=?"   //placeholder 3
                        + "WHERE `skoonie_key`=?";  //placeholder 4
        
        PreparedStatement stmt = createPreparedStatement(cmd);
        
        try {
            stmt.setString(1, pTruck.getId());
            stmt.setString(2, pTruck.getName());
            stmt.setString(3, pTruck.getTruckCompanyKey());
            stmt.setString(4, pTruck.getSkoonieKey());
            
            //execute the statement
            stmt.execute();
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 1093"); }
        
        //clean up environment
        closePreparedStatement(stmt);
        closeDatabaseConnection();
                
    }// end of MySQLDatabase::updateTruck
    //--------------------------------------------------------------------------
    
}// end of class MySQLDatabase
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
