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
*   Order Number
*   Required
*
*/

//------------------------------------------------------------------------------

package shared;

//------------------------------------------------------------------------------

import java.util.HashMap;
import java.util.Map;

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
    
    //order number is the number that decides what order the descriptors go in
    private String orderNumber;
    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String pNumber) { orderNumber = pNumber; }
    
    //required means that the input field cannot be empty
    private boolean required;
    public boolean getRequired() { return required; }
    public void setRequired(boolean pRequired) { required = pRequired; }
    
    //if a descriptor uses preset values, it means that the descriptor can only
    //use predefined values
    private boolean usesPresetValues;
    public boolean getUsesPresetValues() { return usesPresetValues; }
    public void setUsesPresetValues(boolean pBool) { usesPresetValues = pBool; }
    
    //this map will only contain values if usesPresetValues is true
    //key=skoonie key of entry; value=display name for that entry
    private final Map<String, String> presetValues = new HashMap<>();
    public void addPresetValue(String pKey, String pDisplayName) 
        { presetValues.put(pKey, pDisplayName); }
    public Map<String, String> getPresetValues() { return presetValues; }
    
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
