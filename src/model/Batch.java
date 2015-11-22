/*******************************************************************************
* Title: Batch.java
* Author: Hunter Schoonover
* Date: 11/20/15
*
* Purpose:
*
* This class stores information about a batch:
*       Skoonie Key, Id, Date Created, Quantity, Total Length, Customer Key
* 
*
*/

//------------------------------------------------------------------------------

package model;

//------------------------------------------------------------------------------

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class Batch
//

public class Batch extends Record
{
    
    private String customerKey;
    public String getCustomerKey() { return customerKey; }
    public void setCustomerKey(String pKey) { customerKey = pKey; }
    
    private String dateCreated;
    public String getDateCreated() { return dateCreated; }
    public void setDateCreated(String pDate) { dateCreated = pDate; }
    
    private String quantity;
    public String getQuantity() { return quantity; }
    public void setQuantity(String pQuantity) { quantity = pQuantity; }
    
    private String totalLength;
    public String getTotalLength() { return totalLength; }
    public void setTotalLength(String pLength) { totalLength = pLength; }
    
    //--------------------------------------------------------------------------
    // Batch::Batch (constructor)
    //

    public Batch(String pSkoonieKey, String pId, String pDate, String pQuantity, 
                    String pLength, String pCustomerKey)
    {
        
        super(pSkoonieKey, pId);
        
        dateCreated     = pDate;
        quantity        = pQuantity;
        totalLength     = pLength;
        customerKey     = pCustomerKey;

    }//end of Batch::Batch (constructor)
    //--------------------------------------------------------------------------
    
}//end of class Batch
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
