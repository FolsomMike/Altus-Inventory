/*******************************************************************************
* Title: Record.java
* Author: Hunter Schoonover
* Date: 12/15/15
*
* Purpose:
*
* This class stores information about a table.
*
*/

//------------------------------------------------------------------------------

package shared;

//------------------------------------------------------------------------------

import java.util.HashMap;
import java.util.Map;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class Record
//
// This class is used to wrap information about a record.
//

public class Record 
{
    
    private String skoonieKey;
    public String getSkoonieKey() { return skoonieKey; }
    public void setSkoonieKey(String pKey) { skoonieKey = pKey; }
    
    private final Map<Descriptor, String> descriptors = new HashMap<>();
    public Map<Descriptor, String> getDescriptors() { return descriptors; }
    
    //--------------------------------------------------------------------------
    // Record::Record (constructor)
    //

    public Record()
    {
        
    }//end of Record::Record (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // Record::getValueByDescriptorName
    //
    // Returns the value of associated with the descriptor that contains the
    // passed in name.
    //

    public String getValueByDescriptorName(String pName)
    {
        
        for (Map.Entry<Descriptor, String> d : descriptors.entrySet()) {
            if (d.getKey().getName().equals(pName)) { return d.getValue(); }
        }
        
        return null;
        
    }//end of Record::getValueByDescriptorName
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // Record::storeDescriptor
    //

    public void storeDescriptor(Descriptor pDescriptor, String pValue)
    {
        
        descriptors.put(pDescriptor, pValue);
        
    }//end of Record::storeDescriptor
    //--------------------------------------------------------------------------
    
}//end of class Record
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------