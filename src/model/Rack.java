/*******************************************************************************
* Title: Rack.java
* Author: Hunter Schoonover
* Date: 11/29/15
*
* Purpose:
*
* This class stores information about a rack:
*       Skoonie Key, Id, Name
*
*/

//------------------------------------------------------------------------------

package model;

//------------------------------------------------------------------------------

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class Rack
//

public class Rack extends Record
{
    
    private String name;
    public String getName() { return name; }
    public void setName(String pName) { name = pName; }
    
    //--------------------------------------------------------------------------
    // Rack::Rack (constructor)
    //

    public Rack(String pSkoonieKey, String pId, String pName)
    {
        
        super(pSkoonieKey, pId);
        
        name = pName;

    }//end of Rack::Rack (constructor)
    //--------------------------------------------------------------------------
    
}//end of class Rack
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
