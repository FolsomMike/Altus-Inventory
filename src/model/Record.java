/*******************************************************************************
* Title: Record.java
* Author: Hunter Schoonover
* Date: 11/21/15
*
* Purpose:
*
* This class stores information about a record in a database table.
* 
* Currently stores:
*       Skoonie Key, Id
* 
* Children classes can extend this to store more values, depending on the 
* record type (Customer, Batch, etc.).
*
*/

//------------------------------------------------------------------------------

package model;

//------------------------------------------------------------------------------

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class Record
//

public class Record 
{
    
    private String skoonieKey;
    public String getSkoonieKey() { return skoonieKey; }
    public void setSkoonieKey(String pKey) { skoonieKey = pKey; }
    
    private String id;
    public String getId() { return id; }
    public void setId(String pId) { id = pId; }
    
    //--------------------------------------------------------------------------
    // Record::Record (constructor)
    //

    public Record(String pSkoonieKey, String pId)
    {
        
        skoonieKey  = pSkoonieKey;
        id          = pId;

    }//end of Record::Record (constructor)
    //--------------------------------------------------------------------------
    
}//end of class Record
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
