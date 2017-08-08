/*******************************************************************************
* Title: Batch.java
* Author: Hunter Schoonover
* Date: 11/20/15
*
* Purpose:
*
* This class stores information about a batch:
*       Id, Date Created, Quantity, Total Length, Customer Id, Rack //WIP HSS// -- store more stuff man
* 
* NOTES:
*       The id will always be the first parameter in the constructor. After 
*       that, the parameters should be organized alphabetically according to
*       the column name in the database.
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
    
    private String customerId;
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String pId) { customerId = pId; }
    
    private String dateCreated;
    public String getDateCreated() { return dateCreated; }
    public void setDateCreated(String pDate) { dateCreated = pDate; }
    
    private String rack;
    public String getRack() { return rack; }
    public void setRack(String pRack) { rack = pRack; }
    
    private String quantity;
    public String getQuantity() { return quantity; }
    public void setQuantity(String pQuantity) { quantity = pQuantity; }
    
    private String totalLength;
    public String getTotalLength() { return totalLength; }
    public void setTotalLength(String pLength) { totalLength = pLength; }
    
    //--------------------------------------------------------------------------
    // Batch::Batch (constructor)
    //

    public Batch(String pId, String pCustomerId, String pDate, String pQuantity,
                    String pRack, String pLength)
    {
        
        id              = pId;
        customerId      = pCustomerId;
        dateCreated     = pDate;
        quantity        = pQuantity;
        rack            = pRack;
        totalLength     = pLength;

    }//end of Batch::Batch (constructor)
    //--------------------------------------------------------------------------
    
}//end of class Batch
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
