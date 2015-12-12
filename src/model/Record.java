/*******************************************************************************
* Title: Record.java
* Author: Hunter Schoonover
* Date: 11/21/15
*
* Purpose:
*
* This class stores information about a record intended for a database table.
* 
* Currently stores:
*   columns and values in a Map
*
*/

//------------------------------------------------------------------------------

package model;

//------------------------------------------------------------------------------

import java.util.HashMap;
import java.util.Map;


//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class Record
//

public class Record 
{
    
    private String skoonieKey;
    public String getSkoonieKey() { return skoonieKey; }
    public void setSkoonieKey(String pKey) { skoonieKey = pKey; }
    
    //key=column; value=column value
    private final Map<String, String> columns = new HashMap<>();
    public Map<String, String> getColumns() { return columns; }
    public void addColumn(String pColumn, String pValue) 
    { columns.put(pColumn, pValue); }
    public String getValue(String pColumn) { 
        String attr = columns.get(pColumn);
        return attr!=null ? attr : "";
    }
    
    //--------------------------------------------------------------------------
    // Record::Record (constructor)
    //

    public Record()
    {

    }//end of Record::Record (constructor)
    //--------------------------------------------------------------------------
    
}//end of class Record
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
