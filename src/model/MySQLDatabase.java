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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
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
    
    //Table names -- back quotes so that they can be easily put in cmd strings
    private final String batchesTable = "`BATCHES`";
    private final String customersTable = "`CUSTOMERS`";
    private final String racksTable = "`RACKS`";
    private final String truckCompaniesTable = "`TRUCK_COMPANIES`";
    private final String truckDriversTable = "`TRUCK_DRIVERS`";
    private final String trucksTable = "`TRUCKS`";
    
    
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
        
        try { 
            stmt = connection.prepareStatement(pCommand, 
                                            Statement.RETURN_GENERATED_KEYS);
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 266"); }
        
        return stmt;

    }// end of MySQLDatabase::createPreparedStatement
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::deleteBatch
    //
    // Deletes pRec from the batches table in the database.
    //

    public void deleteBatch(Record pRec)
    {
        
        deleteRecord(pRec, batchesTable);

    }// end of MySQLDatabase::deleteBatch
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::deleteCustomer
    //
    // Deletes pRec from the customers table in the database.
    //

    public void deleteCustomer(Record pRec)
    {
        
        deleteRecord(pRec, customersTable);

    }// end of MySQLDatabase::deleteCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::deleteRack
    //
    // Deletes pRec from the racks table in the database.
    //

    public void deleteRack(Record pRec)
    {
        
        deleteRecord(pRec, racksTable);

    }// end of MySQLDatabase::deleteRack
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::deleteRecord
    //
    // Deletes pRec from pTable.
    //

    public void deleteRecord(Record pRec, String pTable)
    {
        
        //create the command string
        String cmd = "DELETE FROM " + pTable + " WHERE `skoonie_key`=?";
        
        PreparedStatement stmt = createPreparedStatement(cmd);
        
        try {
            stmt.setString(1, pRec.getSkoonieKey());
            
            //execute the statement
            stmt.execute();
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 338"); }
        
        //clean up environment
        closePreparedStatement(stmt);
        closeDatabaseConnection();
                
    }// end of MySQLDatabase::deleteRecord
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::deleteTruckCompany
    //
    // Deletes pRec from the truck companies table in the database.
    //

    public void deleteTruckCompany(Record pRec)
    {
        
        deleteRecord(pRec, truckCompaniesTable);

    }// end of MySQLDatabase::deleteTruckCompany
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::deleteTruckDriver
    //
    // Deletes pRec from the truck drivers table in the database.
    //

    public void deleteTruckDriver(Record pRec)
    {
        
        deleteRecord(pRec, truckDriversTable);

    }// end of MySQLDatabase::deleteTruckDriver
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::deleteTruck
    //
    // Deletes pRec from the trucks table in the database.
    //

    public void deleteTruck(Record pRec)
    {
        
        deleteRecord(pRec, trucksTable);

    }// end of MySQLDatabase::deleteTruck
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::getBatches
    //
    // Gets and returns all of the records in the batches table.
    //

    public ArrayList<Record> getBatches()
    {
        
        return getRecords(batchesTable);

    }// end of MySQLDatabase::getBatches
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::getCustomers
    //
    // Gets and returns all of the records in the customers table.
    //

    public ArrayList<Record> getCustomers()
    {
        
        return getRecords(customersTable);

    }// end of MySQLDatabase::getCustomers
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::getRacks
    //
    // Gets and returns all of the records in the racks table.
    //

    public ArrayList<Record> getRacks()
    {
        
        return getRecords(racksTable);

    }// end of MySQLDatabase::getRacks
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::getRecords
    //
    // Gets and returns all of the records in pTable.
    //

    public ArrayList<Record> getRecords(String pTable)
    {
        
        ArrayList<Record> recs = new ArrayList();

        String cmd = "SELECT * FROM " + pTable;
        PreparedStatement stmt = createPreparedStatement(cmd);
        ResultSet set = performQuery(stmt);
        
        //extract the data from the ResultSet
        try {
            ResultSetMetaData d = set.getMetaData();
            while (set.next()) { 
                
                Record r = new Record(set.getString("skoonie_key"));
                
                //put attributes in record
                //start count at 2 since Skoonie Key is at 1
                for (int i=2; i<d.getColumnCount(); i++) {
                    String key = d.getColumnName(i);
                    r.addAttr(key, set.getString(key));
                }
                
                //store the record
                recs.add(r);
            }
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error:635"); }
        
        //clean up environment
        closeResultSet(set);
        closePreparedStatement(stmt);
        closeDatabaseConnection();
        
        return recs;

    }// end of MySQLDatabase::getRecords
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::getTruckCompanies
    //
    // Gets and returns all of the records in the truck companies table.
    //

    public ArrayList<Record> getTruckCompanies()
    {
        
        return getRecords(truckCompaniesTable);

    }// end of MySQLDatabase::getTruckCompanies
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::getTruckDrivers
    //
    // Gets and returns all of the records in the truck drivers table.
    //

    public ArrayList<Record> getTruckDrivers()
    {
        
        return getRecords(truckDriversTable);

    }// end of MySQLDatabase::getTruckDrivers
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::getTrucks
    //
    // Gets and returns all of the records in the trucks table.
    //

    public ArrayList<Record> getTrucks()
    {
        
        return getRecords(trucksTable);

    }// end of MySQLDatabase::getTrucks
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::insertBatch
    //
    // Inserts pRec into the batches table.
    //

    public void insertBatch(Record pRec)
    {
        
        insertRecord(pRec, batchesTable);
                
    }// end of MySQLDatabase::insertBatch
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::insertCustomer
    //
    // Inserts pRec into the customers table.
    //

    public void insertCustomer(Record pRec)
    {
        
        insertRecord(pRec, customersTable);
                
    }// end of MySQLDatabase::insertCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::insertRack
    //
    // Inserts pRec into the racks table.
    //

    public void insertRack(Record pRec)
    {
        
        insertRecord(pRec, racksTable);
                
    }// end of MySQLDatabase::insertRack
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::insertRecord
    //
    // Inserts pRec into pTable.
    //

    public int insertRecord(Record pRec, String pTable)
    {
        
        int skoonieKey = -1;
        
        String cmd = "INSERT INTO " + pTable + " (";
        
        //get the attributes of pRec and put them in a set
        Set<Map.Entry<String, String>> attrs = pRec.getAttrs().entrySet();
        
        //use the keys in the Record attributes as the column names
        int c=0;
        for (Map.Entry<String, String> a : attrs) {
            //add a comma to separate this column from the last one
            if(c!=0) { cmd += ","; }
            
            //add the key to the command string
            cmd += "`" + a.getKey() + "`";
            
            //count number of times we've looped through
            ++c;
        }
        
        //put some more SQL into the command string
        cmd += ") VALUES (";
        
        //use the length of attrs to determine how many placeholders to put
        for (int i=0; i<attrs.size(); i++) {
            //add a comma to separate this placeholder from the last one
            if(i!=0) { cmd += ","; }
            
            //add a placeholder to the command string
            cmd += "?";
        }
        
        //finish up the command string
        cmd += ")";
        
        PreparedStatement stmt = createPreparedStatement(cmd);
        
        try {
            //for every attribute, put the value into the proper placeholder
            int place = 1;
            for (Map.Entry<String, String> a : attrs) {
                stmt.setString(place++, a.getValue());
            }
            
            //execute the statement
            stmt.execute();
            
            ResultSet set = stmt.getGeneratedKeys();

            if (set.next()) { skoonieKey = set.getInt(1); }
            
            //clean up environment
            closeResultSet(set);
        }
        catch (SQLException e) { 
            //DEBUG HSS//
            System.out.println(e.getMessage());
            logSevere(e.getMessage() + " - Error: 667"); }
        
        //clean up environment
        closePreparedStatement(stmt);
        closeDatabaseConnection();
        
        return skoonieKey;
                
    }// end of MySQLDatabase::insertRecord
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::insertTruckCompany
    //
    // Inserts pRec into the truck companies table.
    //

    public void insertTruckCompany(Record pRec)
    {
        
        insertRecord(pRec, truckCompaniesTable);
                
    }// end of MySQLDatabase::insertTruckCompany
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::insertTruckDriver
    //
    // Inserts pRec into the truck drivers table.
    //

    public void insertTruckDriver(Record pRec)
    {
        
        insertRecord(pRec, truckDriversTable);
                
    }// end of MySQLDatabase::insertTruckDriver
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::insertTruck
    //
    // Inserts pRec into the trucks table.
    //

    public void insertTruck(Record pRec)
    {
        
        insertRecord(pRec, trucksTable);
                
    }// end of MySQLDatabase::insertTruck
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
    // Updates pRec in the batches table.
    //

    public void updateBatch(Record pRec)
    {
        
        updateRecord(pRec, batchesTable);
                
    }// end of MySQLDatabase::updateBatch
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::updateCustomer
    //
    // Updates pRec in the customers table.
    //

    public void updateCustomer(Record pRec)
    {
        
        updateRecord(pRec, customersTable);
                
    }// end of MySQLDatabase::updateCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::updateRack
    //
    // Updates pRec in the racks table.
    //

    public void updateRack(Record pRec)
    {
        
        updateRecord(pRec, racksTable);
                
    }// end of MySQLDatabase::updateRack
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::updateRecord
    //
    // Updates pRec in pTable.
    //

    public void updateRecord(Record pRec, String pTable)
    {
        
        //start the command string
        String cmd = "UPDATE " + pTable + " SET ";
        
        //get the attributes of pRec and put them in a set
        Set<Map.Entry<String, String>> attrs = pRec.getAttrs().entrySet();
        
        //use the keys in the Record attributes as the column names
        int c=0;
        for (Map.Entry<String, String> a : attrs) {
            //add a comma to separate this column from the last one
            if(c!=0) { cmd += ","; }
            
            //add the key and a placeholder to the command string
            cmd += "`" + a.getKey() + "`" + "=?";
            
            //count number of times we've looped through
            ++c;
        }
        
        //finish up the command string
        cmd += "WHERE `skoonie_key`=?";
        
        PreparedStatement stmt = createPreparedStatement(cmd);
        
        try {
            //for every attribute, put the value into the proper placeholder
            int place = 1;
            for (Map.Entry<String, String> a : attrs) {
                stmt.setString(place++, a.getValue());
            }
            
            //set the Skoonie Key
            stmt.setString(place, pRec.getSkoonieKey());
            
            //execute the statement
            stmt.execute();
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 1308"); }
        
        //clean up environment
        closePreparedStatement(stmt);
        closeDatabaseConnection();
                
    }// end of MySQLDatabase::updateRecord
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::updateTruckCompany
    //
    // Updates pRec in the truck companies table.
    //

    public void updateTruckCompany(Record pRec)
    {
        
        updateRecord(pRec, truckCompaniesTable);
                
    }// end of MySQLDatabase::updateTruckCompany
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::updateTruckDriver
    //
    // Updates pRec in the truck drivers table.
    //

    public void updateTruckDriver(Record pRec)
    {
        
        updateRecord(pRec, truckDriversTable);
                
    }// end of MySQLDatabase::updateTruckDriver
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::updateTruck
    //
    // Updates pRec in the trucks table.
    //

    public void updateTruck(Record pRec)
    {
        
        updateRecord(pRec, trucksTable);
                
    }// end of MySQLDatabase::updateTruck
    //--------------------------------------------------------------------------
    
}// end of class MySQLDatabase
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
