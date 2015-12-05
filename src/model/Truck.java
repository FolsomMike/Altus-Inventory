/*******************************************************************************
* Title: Truck.java
* Author: Hunter Schoonover
* Date: 12/05/15
*
* Purpose:
*
* This class stores information about a truck company:
*       Skoonie Key, ID, Name, Truck Company Key
*/

//------------------------------------------------------------------------------

package model;

//------------------------------------------------------------------------------

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class Truck
//

public class Truck extends Record
{
    
    private String name;
    public String getName() { return name; }
    public void setName(String pName) { name = pName; }
    
    private String truckCompanyKey;
    public String getTruckCompanyKey() { return truckCompanyKey; }
    public void setTruckCompanyKey(String pKey) { truckCompanyKey = pKey; }
    
    //--------------------------------------------------------------------------
    // Truck::Truck (constructor)
    //

    public Truck(String pSkoonieKey, String pId, String pName, 
                    String pTruckCompanyKey)
    {
        
        super(pSkoonieKey, pId);
        
        name                = pName;
        truckCompanyKey     = pTruckCompanyKey;

    }//end of Truck::Truck (constructor)
    //--------------------------------------------------------------------------
    
}//end of class Truck
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
