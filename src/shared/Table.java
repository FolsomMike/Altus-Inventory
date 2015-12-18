/*******************************************************************************
* Title: Records.java
* Author: Hunter Schoonover
* Date: 12/15/15
*
* Purpose:
*
* This class is used to store a set of records and the descriptors that go along
* with them. It provides some helper function for accessing this data.
*
*/

//------------------------------------------------------------------------------

package shared;

//------------------------------------------------------------------------------

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class Table
//

public class Table 
{
    
    private String skoonieKey;
    public String getSkoonieKey() { return skoonieKey; }
    public void setSkoonieKey(String pKey) { skoonieKey = pKey; }
    
    //descriptors
    
    private List<Descriptor> descriptorsList;
    
    //key=descriptor skoonie key; value=descriptor
    private final Map<String, Descriptor> descriptorsMap = new HashMap<>();
    
    public List<Descriptor> getDescriptors() { return descriptorsList; }
    
    public Descriptor getDescriptor(String pKey) 
    { return descriptorsMap.get(pKey); }
    
    //end of descriptors
    
    
    //records
    
    //key=descriptor skoonie key; value=record
    private final List<Record> records = new ArrayList<>();
    
    public List<Record> getRecords() { return records; }
    
    public void addRecord(Record pRecord) { records.add(pRecord); }
            
    //end of records
    
    
    //--------------------------------------------------------------------------
    // Table::Table (constructor)
    //

    public Table()
    {
        
    }//end of Table::Table (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // Table::getDescriptorKeyByName
    //
    // Returns the skoonie key of the descriptor who has the same name as pName.
    // Returns null if none of the descriptors have pName.
    //
    // NOTE:    If there are multiple descriptors that match the constraint, 
    //          then the skoonie key of the one found first is returned.
    //

    public String getDescriptorKeyByName(String pName)
    {
        
        String key = null;
        
        for (Descriptor d : descriptorsList) {
            if(d.getName().equals(pName)) { key = d.getSkoonieKey(); }
        }
            
        return key;
        
    }//end of Table::getDescriptorKeyByName
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // Table::getRecord
    //
    // Returns the Record whose skoonie key is pSkoonieKey.
    //

    public Record getRecord(String pSkoonieKey)
    {
        
        Record record = null;
        
        if (pSkoonieKey==null || records.isEmpty()) { return record; }
        
        for (Record r : records) {
            if(r.getSkoonieKey().equals(pSkoonieKey)) { record = r; break; }
        }
            
        return record;
        
    }//end of Table::getRecord
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // Table::setDescriptors
    //
    // Sets the descriptors list to pDescriptors and sets up the descriptors
    // map.
    //

    public void setDescriptors(List<Descriptor> pDescriptors)
    {
        
        descriptorsList = pDescriptors;
        
        descriptorsMap.clear();
        
        for (Descriptor d : descriptorsList) {
            descriptorsMap.put(d.getSkoonieKey(), d);
        }
        
    }//end of Table::setDescriptors
    //--------------------------------------------------------------------------
    
}//end of class Table
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------