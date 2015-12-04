/*******************************************************************************
* Title: TruckCompany.java
* Author: Hunter Schoonover
* Date: 12/04/15
*
* Purpose:
*
* This class stores information about a truck company:
*       Skoonie Key, ID, Name
*/

//------------------------------------------------------------------------------

package model;

//------------------------------------------------------------------------------

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class TruckCompany
//

public class TruckCompany extends Record
{
    
    private String name;
    public String getName() { return name; }
    public void setName(String pName) { name = pName; }
    
    //--------------------------------------------------------------------------
    // TruckCompany::TruckCompany (constructor)
    //

    public TruckCompany(String pSkoonieKey, String pId, String pName)
    {
        
        super(pSkoonieKey, pId);
        
        name            = pName;

    }//end of TruckCompany::TruckCompany (constructor)
    //--------------------------------------------------------------------------
    
}//end of class TruckCompany
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
