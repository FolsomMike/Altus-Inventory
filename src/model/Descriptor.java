/*******************************************************************************
* Title: Descriptor.java
* Author: Hunter Schoonover
* Date: 12/14/15
*
* Purpose:
*
* This class stores information about a descriptor.
* 
* Currently stores:
*   Skoonie Key
*   Name
*   For Table
*   Duplicates Allowed
*   Removable
*
*/

//------------------------------------------------------------------------------

package model;

//------------------------------------------------------------------------------

import java.util.HashMap;
import java.util.Map;


//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class Descriptor
//

public class Descriptor 
{
    
    private String skoonieKey;
    public String getSkoonieKey() { return skoonieKey; }
    public void setSkoonieKey(String pKey) { skoonieKey = pKey; }
    
    private String name;
    public String getName() { return name; }
    public void setName(String pName) { name = pName; }
    
    private String forTable;
    public String getForTable() { return forTable; }
    public void setForTable(String pForTable) { forTable = pForTable; }
    
    private boolean duplicatesAllowed;
    public boolean getDuplicatesAllowed() { return duplicatesAllowed; }
    public void setDuplicatesAllowed(boolean pBool) {duplicatesAllowed = pBool;}
    
    private boolean removable;
    public boolean getRemovable() { return removable; }
    public void setRemovable(boolean pBool) { removable = pBool; }
    
    //key=skoonie key; value=value
    private final Map<String, String> values = new HashMap<>();
    public Map<String, String> getValues() { return values; }
    public void storeValue(String pKey, String pValue) { 
        values.put(pKey, pValue);
    }
    
    //--------------------------------------------------------------------------
    // Descriptor::Descriptor (constructor)
    //

    public Descriptor()
    {

    }//end of Descriptor::Descriptor (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseEntry::setDuplicatesAllowed
    //
    // Sets duplicatesAllowed depending on what is inside the string.
    //

    public void setDuplicatesAllowed(String pAllowed) 
    {
        
        boolean allowed = false;
        if (pAllowed.equals("true")) { allowed = true; }
        
        duplicatesAllowed = allowed;

    }//end of DatabaseEntry::setDuplicatesAllowed
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseEntry::setRemovable
    //
    // Sets removable depending on what is inside the string.
    //

    public void setRemovable(String pRemovable) 
    {
        
        boolean bool = false;
        if (pRemovable.equals("true")) { bool = true; }
        
        removable = bool;

    }//end of DatabaseEntry::setRemovable
    //--------------------------------------------------------------------------
    
}//end of class Descriptor
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
