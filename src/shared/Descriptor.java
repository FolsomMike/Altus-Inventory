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
*   Required
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
    
    //Examples: Id, Name, Quantity
    private String name;
    public String getName() { return name; }
    public void setName(String pName) { name = pName; }
    
    //required means that the input field cannot be empty
    private boolean required;
    public boolean getRequired() { return required; }
    public void setRequired(boolean pRequired) { required = pRequired; }
    
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
