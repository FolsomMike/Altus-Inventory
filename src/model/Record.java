/*******************************************************************************
* Title: Record.java
* Author: Hunter Schoonover
* Date: 11/21/15
*
* Purpose:
*
* This class stores information about a record intended for a database table.
* 
* Generic information needed for every single record in the program:
*       Skoonie Key
* 
* All other attributes can be stored and retrieved using the addAttr() and
* getAttr() functions. The keys for the attributes need to match the column
* names in the database.
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
    
    private final Map<String, String> attributes = new HashMap<>();
    public Map<String, String> getAttrs() { return attributes; }
    public void addAttr(String pKey, String pAttribute) 
    { attributes.put(pKey, pAttribute); }
    public String getAttr(String pKey) { return attributes.get(pKey); }
    
    //--------------------------------------------------------------------------
    // Record::Record (constructor)
    //

    public Record()
    {

    }//end of Record::Record (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // Record::Record (constructor)
    //

    public Record(String pSkoonieKey)
    {
        
        skoonieKey  = pSkoonieKey;

    }//end of Record::Record (constructor)
    //--------------------------------------------------------------------------
    
}//end of class Record
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
