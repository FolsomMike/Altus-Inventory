/*******************************************************************************
* Title: Rack.java
* Author: Hunter Schoonover
* Date: 11/20/15
*
* Purpose:
*
* This class stores information about a rack:
*       Name
*
*/

//------------------------------------------------------------------------------

package model;

//------------------------------------------------------------------------------

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class Rack
//

public class Rack 
{
    
    private String name;
    public String getName() { return name; }
    public void setName(String pName) { name = pName; }
    
    //--------------------------------------------------------------------------
    // Rack::Rack (constructor)
    //

    public Rack(String pName)
    {
        
        name = pName;

    }//end of Rack::Rack (constructor)
    //--------------------------------------------------------------------------
    
}//end of class Rack
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------