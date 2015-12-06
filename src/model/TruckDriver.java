/*******************************************************************************
* Title: TruckDriver.java
* Author: Hunter Schoonover
* Date: 12/06/15
*
* Purpose:
*
* This class stores information about a truck driver:
*       Skoonie Key, Id, Name, Truck Company Key
*/

//------------------------------------------------------------------------------

package model;

//------------------------------------------------------------------------------

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class TruckDriver
//

public class TruckDriver extends Record
{
    
    private String name;
    public String getName() { return name; }
    public void setName(String pName) { name = pName; }
    
    private String truckCompanyKey;
    public String getTruckCompanyKey() { return truckCompanyKey; }
    public void setTruckCompanyKey(String pKey) { truckCompanyKey = pKey; }
    
    //--------------------------------------------------------------------------
    // TruckDriver::TruckDriver (constructor)
    //

    public TruckDriver(String pSkoonieKey, String pId, String pName, 
                    String pTruckCompanyKey)
    {
        
        super(pSkoonieKey, pId);
        
        name                = pName;
        truckCompanyKey     = pTruckCompanyKey;

    }//end of TruckDriver::TruckDriver (constructor)
    //--------------------------------------------------------------------------
    
}//end of class TruckDriver
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
