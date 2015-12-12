/*******************************************************************************
* Title: Display.java
* Author: Hunter Schoonover
* Date: 12/11/15
*
* Purpose:
*
* This interface provides methods that all displays need to function as 
* displays.
* 
*/

//------------------------------------------------------------------------------

package view;

//------------------------------------------------------------------------------

public interface Display
{
    
    public void init();
    
    //Function relating to customers
    public void displayCustomers();
    public void displayAddCustomer();
    
}