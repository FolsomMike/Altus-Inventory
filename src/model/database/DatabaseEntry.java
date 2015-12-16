/*******************************************************************************
* Title: DatabaseEntry.java
* Author: Hunter Schoonover
* Date: 12/14/15
*
* Purpose:
*
* This class is a wrapper for the columns and values of a database entry.
*
*/

//------------------------------------------------------------------------------

package model.database;

//------------------------------------------------------------------------------

import java.util.HashMap;
import java.util.Map;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class DatabaseEntry
//

public class DatabaseEntry 
{
    
    //key=column; value=column value
    private final Map<String, String> columns = new HashMap<>();
    public Map<String, String> getColumns() { return columns; }
    
    //--------------------------------------------------------------------------
    // DatabaseEntry::DatabaseEntry (constructor)
    //

    public DatabaseEntry()
    {

    }//end of DatabaseEntry::DatabaseEntry (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseEntry::getValue
    //
    // Returns the value for pColumn, or an empty string if pColumn cannot be
    // found or pValue is null.
    //

    public String getValue(String pColumn) 
    {
        
        String value = columns.get(pColumn);
        return value!=null ? value : "";

    }//end of DatabaseEntry::getValue
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseEntry::storeColumn
    //
    // Stores pColumn and pValue. If pColumn has already been added, then the
    // value previously stored with it is replaced by pValue.
    //

    public void storeColumn(String pColumn, String pValue) 
    {
        
        columns.put(pColumn, pValue);

    }//end of DatabaseEntry::storeColumn
    //--------------------------------------------------------------------------
    
}//end of class DatabaseEntry
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
