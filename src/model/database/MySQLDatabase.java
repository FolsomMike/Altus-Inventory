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

package model.database;

//------------------------------------------------------------------------------

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
    // MySQLDatabase::addColumn
    //
    // Adds pColumn to the end of pTable.
    //

    public void addColumn(String pTable, String pColumn)
    {
        
        //create the sql command string
        String cmd = "ALTER TABLE `" + pTable + "` ADD " + pColumn;
        
        PreparedStatement stmt = createPreparedStatement(cmd);
        
        //execute the statement
        try { stmt.execute(); }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 125"); }
        
        //clean up environment
        closePreparedStatement(stmt);
                
    }// end of MySQLDatabase::addColumn
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::checkForValue
    //
    // Checks for pValue in pTable under pColumn.
    //

    public boolean checkForValue(String pValue, String pTable, String pColumn)
    {

        String cmd = "SELECT COUNT(*) FROM `" + pTable
                            + "` WHERE `" + pColumn + "`=?";
        
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
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 162"); }
        
        //clean up environment
        closePreparedStatement(stmt);
        
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

    public void closeDatabaseConnection()
    {
        
        try { 
            //return if the connection is not open
            if (connection == null || connection.isClosed()) { return; }
            
            connection.close();
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 192"); }
        
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
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 207"); }

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
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 222"); }

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

    public boolean connectToDatabase()
    {
        
        boolean success = true;
        
        try { 
            //return true if we are already connected
            if(connection != null && !connection.isClosed() 
                    && connection.isValid(10)) { return success; }
            
            connection = DriverManager.getConnection(url, username, password);
            
        }
        catch (SQLException e) {
            success = false;
            logSevere(e.getMessage() + " - Error: 255"); 
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
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 283"); }
        
        return stmt;

    }// end of MySQLDatabase::createPreparedStatement
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::createTable
    //
    // Creates pTable containing pColumns in the database.
    //

    public void createTable(String pTable, String[] pColumns)
    {
        
        //start the sql command string
        String cmd = "CREATE TABLE `" + pTable + "` (";
        
        //put the columns into the command
        for (int i=0; i<pColumns.length; i++) { 
            //add a comma to separate this column from the last one
            if(i!=0) { cmd += ","; }
            cmd += pColumns[i];
        }
        
        //finish up the command string
        cmd += ")";
        
        PreparedStatement stmt = createPreparedStatement(cmd);
        
        //execute the statement
        try { stmt.execute(); }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 316"); }
        
        //clean up environment
        closePreparedStatement(stmt);
                
    }// end of MySQLDatabase::createTable
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::deleteEntry
    //
    // Deletes the entry associated with pSkoonieKey from pTable.
    //

    public void deleteEntry(String pTable, String pSkoonieKey)
    {
        
        //create the command string
        String cmd = "DELETE FROM `" + pTable + "` WHERE `skoonie_key`=?";
        
        PreparedStatement stmt = createPreparedStatement(cmd);
        
        try {
            stmt.setString(1, pSkoonieKey);
            
            //execute the statement
            stmt.execute();
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 344"); }
        
        //clean up environment
        closePreparedStatement(stmt);
                
    }// end of MySQLDatabase::deleteEntry
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::dropColumn
    //
    // Drops pColumn from pTable.
    //

    public void dropColumn(String pTable, String pColumn)
    {
        
        //create the sql command string
        String cmd = "ALTER TABLE `" + pTable + "` DROP `" + pColumn + "`";
        
        PreparedStatement stmt = createPreparedStatement(cmd);
        
        //execute the statement
        try { stmt.execute(); }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 368"); }
        
        //clean up environment
        closePreparedStatement(stmt);
                
    }// end of MySQLDatabase::dropColumn
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::dropTable
    //
    // Drops pTable from the database.
    //

    public void dropTable(String pTable)
    {
        
        //create the sql command string
        String cmd = "DROP TABLE `" + pTable + "`";
        
        PreparedStatement stmt = createPreparedStatement(cmd);
        
        //execute the statement
        try { stmt.execute(); }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 392"); }
        
        //clean up environment
        closePreparedStatement(stmt);
                
    }// end of MySQLDatabase::dropTable
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::emptyTable
    //
    // Deletes all of the data in pTable from the database.
    //

    public void emptyTable(String pTable)
    {
        
        String cmd = "TRUNCATE `" + pTable + "`";
        PreparedStatement stmt = createPreparedStatement(cmd);
        
        //execute the statement
        try { stmt.execute(); }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 416"); }
        
        //clean up environment
        closePreparedStatement(stmt);

    }// end of MySQLDatabase::emptyTable
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::getEntry
    //
    // Gets and returns the entry in pTable associated with pSkoonieKey.
    //

    public DatabaseEntry getEntry(String pTable, String pSkoonieKey)
    {

        String cmd = "SELECT * FROM `" + pTable + "`"
                            + " WHERE `skoonie_key`=" + pSkoonieKey;
        PreparedStatement stmt = createPreparedStatement(cmd);
        ResultSet set = performQuery(stmt);
        
        //extract the data from the ResultSet
        DatabaseEntry entry = new DatabaseEntry();
        try {
            ResultSetMetaData d = set.getMetaData();
            while (set.next()) { 
                
                //store all of the columns in the database entry
                for (int i=2; i<d.getColumnCount(); i++) {
                    String key = d.getColumnName(i);
                    entry.storeColumn(key, set.getString(key));
                }
                
            }
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 450"); }
        
        //clean up environment
        closeResultSet(set);
        closePreparedStatement(stmt);
        
        return entry;

    }// end of MySQLDatabase::getEntry
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::getEntries
    //
    // Gets and returns all of the entries in pTable.
    //

    public List<DatabaseEntry> getEntries(String pTable)
    {
        
        List<DatabaseEntry> entries = new ArrayList();

        String cmd = "SELECT * FROM `" + pTable + "`";
        PreparedStatement stmt = createPreparedStatement(cmd);
        ResultSet set = performQuery(stmt);
        
        //extract the data from the ResultSet
        try {
            ResultSetMetaData d = set.getMetaData();
            while (set.next()) { 
                
                //get the columns and values of the entry
                DatabaseEntry entry = new DatabaseEntry();
                for (int i=1; i<=d.getColumnCount(); i++) {
                    String columnName = d.getColumnName(i);
                    entry.storeColumn(columnName, set.getString(columnName));
                }
                
                //store the entry
                entries.add(entry);
            }
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 493"); }
        
        //clean up environment
        closeResultSet(set);
        closePreparedStatement(stmt);
        
        return entries;

    }// end of MySQLDatabase::getEntries
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::getEntries
    //
    // Gets and returns all of the entries whose skoonie keys are those in pKeys
    // from pTable.
    //

    public List<DatabaseEntry> getEntries(String pTable, List<String> pKeys)
    {
        
        List<DatabaseEntry> entries = new ArrayList();

        //start the sql command string
        String cmd = "SELECT * FROM `" + pTable + "` WHERE ";
        
        //add the key condidtions to the command string
        for (int i=0; i<pKeys.size(); i++) {
            if (i>0) { cmd += " OR "; }
            cmd += "`skoonie_key`=" + pKeys.get(i);
        }
        
        PreparedStatement stmt = createPreparedStatement(cmd);
        ResultSet set = performQuery(stmt);
        
        //extract the data from the ResultSet
        try {
            ResultSetMetaData d = set.getMetaData();
            while (set.next()) { 
                
                //get the columns and values of the entry
                DatabaseEntry entry = new DatabaseEntry();
                for (int i=1; i<=d.getColumnCount(); i++) {
                    String columnName = d.getColumnName(i);
                    entry.storeColumn(columnName, set.getString(columnName));
                }
                
                //store the entry
                entries.add(entry);
            }
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 493"); }
        
        //clean up environment
        closeResultSet(set);
        closePreparedStatement(stmt);
        
        return entries;

    }// end of MySQLDatabase::getEntries
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::getSkoonieKeys
    //
    // Gets and returns all of the skoonie keys of the entries in pTable.
    //

    public List<String> getSkoonieKeys(String pTable)
    {
        
        List<String> keys = new ArrayList<>();

        String cmd = "SELECT `skoonie_key` FROM `" + pTable + "`";
        PreparedStatement stmt = createPreparedStatement(cmd);
        ResultSet set = performQuery(stmt);
        
        //extract the data from the ResultSet
        try { while (set.next()) { keys.add(set.getString("skoonie_key")); } }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 521"); }
        
        //clean up environment
        closeResultSet(set);
        closePreparedStatement(stmt);
        
        return keys;

    }// end of MySQLDatabase::getSkoonieKeys
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::insertEntry
    //
    // Inserts pEntry into pTable and returns the skoonie key assigned to that
    // entry inside the database.
    //

    public int insertEntry(DatabaseEntry pEntry, String pTable)
    {
        
        int skoonieKey = -1;
        
        Set<Map.Entry<String, String>> columns = pEntry.getColumns().entrySet();
        
        String columnNames = "";
        String columnValuePlaceholders = "";
        int numberOfColumns = 0;
        for (Map.Entry<String, String> column : columns) {
            if (numberOfColumns>0) { 
                //add a comma to separate this column name and from the last one
                columnNames += ",";
                //add a comma to separate this placeholder form the last one
                columnValuePlaceholders += ",";
            }
            
            //add the column name
            columnNames += "`" + column.getKey() + "`";
            
            //add a value placeholder
            columnValuePlaceholders += "?";
            
            //number of columns has increased by one
            ++numberOfColumns;
        }
        
        //construct the sql command string
        String cmd = "INSERT INTO `" + pTable + "` (" + columnNames 
                        + ") VALUES (" + columnValuePlaceholders + ")";
        
        PreparedStatement stmt = createPreparedStatement(cmd);
        
        try {
            //for every column, put the value into the proper placeholder
            int place = 1;
            for (Map.Entry<String, String> column : columns) {
                stmt.setString(place++, column.getValue());
            }
            
            //execute the statement
            stmt.execute();
            
            //get the skoonie key given to the entry
            ResultSet set = stmt.getGeneratedKeys();
            if (set.next()) { skoonieKey = set.getInt(1); }
            
            //clean up environment
            closeResultSet(set);
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 562"); }
        
        //clean up environment
        closePreparedStatement(stmt);
        
        return skoonieKey;
                
    }// end of MySQLDatabase::insertEntry
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
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 633"); }
        
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
            logSevere(e.getMessage() + " - Error: 652"); 
        }

    }// end of MySQLDatabase::registerJDBCDriver
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MySQLDatabase::updateEntry
    //
    // Updates pEntry in pTable.
    //

    public void updateEntry(DatabaseEntry pEntry, String pTable)
    {
        
        //start the command string
        String cmd = "UPDATE `" + pTable + "` SET ";
        
        //get the columns of pEntry and put them in a set
        Set<Map.Entry<String, String>> columns = pEntry.getColumns().entrySet();
        
        int numberOfColumns = 0;
        for (Map.Entry<String, String> column : columns) {
            //add a comma to separate this column from the last one
            if(numberOfColumns>0) { cmd += ","; }
            
            //add the column name and a placeholder for the value
            cmd += "`" + column.getKey() + "`" + "=?";
            
            //number of columns has increased by one
            ++numberOfColumns;
        }
        
        //finish up the command string
        cmd += "WHERE `skoonie_key`=?";
        
        PreparedStatement stmt = createPreparedStatement(cmd);
        
        try {
            //for every column, put the value into the proper placeholder
            int place = 1;
            for (Map.Entry<String, String> column : columns) {
                stmt.setString(place++, column.getValue());
            }
            
            //set the Skoonie Key
            stmt.setString(place, pEntry.getValue("skoonie_key"));
            
            //execute the statement
            stmt.execute();
        }
        catch (SQLException e) { logSevere(e.getMessage() + " - Error: 692"); }
        
        //clean up environment
        closePreparedStatement(stmt);
                
    }// end of MySQLDatabase::updateEntry
    //--------------------------------------------------------------------------
        
}// end of class MySQLDatabase
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
