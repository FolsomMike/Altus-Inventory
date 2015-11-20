/*******************************************************************************
* Title: Batch.java
* Author: Hunter Schoonover
* Date: 11/20/15
*
* Purpose:
*
* This class stores information about a batch:
*       Id, Date Created, Quantity, Total Length, Customer Id //WIP HSS// -- store more stuff man
*
*/

//------------------------------------------------------------------------------

package model;

//------------------------------------------------------------------------------

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class Batch
//

public class Batch 
{
    
    private String id;
    public String getId() { return id; }
    public void setId(String pId) { id = pId; }
    
    private String dateCreated;
    public String getDateCreated() { return dateCreated; }
    public void setDateCreated(String pDate) { dateCreated = pDate; }
    
    private String quantity;
    public String getQuantity() { return quantity; }
    public void setQuantity(String pQuantity) { quantity = pQuantity; }
    
    private String totalLength;
    public String getTotalLength() { return totalLength; }
    public void setTotalLength(String pLength) { totalLength = pLength; }
    
    private String customerId;
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String pId) { customerId = pId; }
    
    //--------------------------------------------------------------------------
    // Batch::Batch (constructor)
    //

    public Batch(String pId, String pDate, String pQuantity, String pLength,
                        String pCustomerId)
    {
        
        id              = pId;
        dateCreated     = pDate;
        quantity        = pQuantity;
        totalLength     = pLength;
        customerId      = pCustomerId;

    }//end of Batch::Batch (constructor)
    //--------------------------------------------------------------------------
    
}//end of class Batch
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
