/*******************************************************************************
* Title: Table.java
* Author: Hunter Schoonover
* Date: 12/15/15
*
* Purpose:
*
* This class stores information about a table.
*
*/

//------------------------------------------------------------------------------

package model;

//------------------------------------------------------------------------------

import java.util.ArrayList;
import java.util.List;


//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class Table
//

public class Table 
{
    
    private List<String> skoonieKeys = new ArrayList<>();
    public List<String> getSkoonieKey() { return skoonieKeys; }
    public void setSkoonieKeys(List<String> pKeys) { skoonieKeys = pKeys; }
    public void storeSkoonieKey(String pKey) { skoonieKeys.add(pKey); }
    
    private List<Descriptor> descriptors = new ArrayList<>();
    public List<Descriptor> getDescriptors() { return descriptors; }
    public void setDescriptors(List<Descriptor> pDesc) { descriptors = pDesc; }
    public void storeDescriptor(Descriptor pDescriptor)
    { descriptors.add(pDescriptor); }
    
    //--------------------------------------------------------------------------
    // Table::Table (constructor)
    //

    public Table()
    {
        
    }//end of Table::Table (constructor)
    //--------------------------------------------------------------------------
    
}//end of class Table
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
