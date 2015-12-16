/*******************************************************************************
* Title: Record.java
* Author: Hunter Schoonover
* Date: 12/15/15
*
* Purpose:
*
* This class stores information about a record.
*
*/

//------------------------------------------------------------------------------

package shared;

//------------------------------------------------------------------------------

import java.util.HashMap;
import java.util.Map;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class Records
//
// This class is used to wrap information about a record.
//

public class Record 
{
    
    private String skoonieKey;
    public String getSkoonieKey() { return skoonieKey; }
    public void setSkoonieKey(String pKey) { skoonieKey = pKey; }
    
    //key=descriptor skoonie key; value=value
    private final Map<String, String> values = new HashMap<>();
    
    public String getValue(String pKey) { return values.get(pKey); }
    
    public void addValue(String pKey, String pValue) {values.put(pKey, pValue);}
    
    //--------------------------------------------------------------------------
    // Records::Records (constructor)
    //

    public Record()
    {
        
    }//end of Records::Records (constructor)
    //--------------------------------------------------------------------------
    
}//end of class Records
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------