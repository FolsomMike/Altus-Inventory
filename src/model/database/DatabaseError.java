/*******************************************************************************
* Title: DatabaseError.java
* Author: Hunter Schoonover
* Date: 01/01/16
*
* Purpose:
*
* This class is an Exception used specifically for the database.
* 
* It also contains standard error messages to be used upon construction.
*
*/

//------------------------------------------------------------------------------

package model.database;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class DatabaseError
//

public class DatabaseError extends Exception
{
    
    public static final String ADD_COLUMN_ERROR = "ADD_COLUMN_ERROR";
    
    public static final String CREATE_TABLE_ERROR = "CREATE_TABLE_ERROR";
    
    public static final String CONNECTION_ERROR = "CONNECTION_ERROR";
    
    public static final String DELETE_ENTRY_ERROR = "DELETE_ENTRY_ERROR";
    
    public static final String DROP_COLUMN_ERROR = "DROP_COLUMN_ERROR";
    
    public static final String DROP_TABLE_ERROR = "DROP_TABLE_ERROR";
    
    public static final String QUERY_ERROR = "QUERY_ERROR";
    
    public static final String TRUNCATE_TABLE_ERROR = "TRUNCATE_TABLE_ERROR";
    
    public static final String UPDATE_ENTRY_ERROR = "UPDATE_ENTRY_ERROR";
    
    //--------------------------------------------------------------------------
    // DatabaseError::DatabaseError (constructor)
    //

    public DatabaseError(String pMessage)
    {
        
        super(pMessage);

    }// end of DatabaseError::DatabaseError (constructor)
    //--------------------------------------------------------------------------
    
}//end of class DatabaseError
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
