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
    
    private String skoonieKey = "";
    public String getSkoonieKey() { return skoonieKey; }
    public void setSkoonieKey(String pKey) { skoonieKey = pKey; }
    
    //Examples: Id, Name, Quantity
    private String name = "";
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
    
}//end of class Descriptor
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
