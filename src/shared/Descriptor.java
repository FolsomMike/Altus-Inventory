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
*   Values Table Name
*
*/

//------------------------------------------------------------------------------

package shared;

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
    
    private String valuesTable;
    public String getValuesTable() { return valuesTable; }
    public void setValuesTable(String pTable) { valuesTable = pTable; }
    
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
        
        //DEBUG HSS//duplicatesAllowed = allowed;

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
        
        //DEBUG HSS//removable = bool;

    }//end of DatabaseEntry::setRemovable
    //--------------------------------------------------------------------------
    
}//end of class Descriptor
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
