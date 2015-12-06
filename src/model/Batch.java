/*******************************************************************************
* Title: Batch.java
* Author: Hunter Schoonover
* Date: 11/20/15
*
* Purpose:
*
* This class stores information about a batch:
*       Skoonie Key, Id, Date, Quantity, Total Length, Customer Key, Rack Key,
*       Truck Company Key, Truck Key, Truck Driver Key, Comments
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
    
    private String date;
    public String getDate() { return date; }
    public void setDate(String pDate) { date = pDate; }
    
    private String quantity;
    public String getQuantity() { return quantity; }
    public void setQuantity(String pQuantity) { quantity = pQuantity; }
    
    private String totalLength;
    public String getTotalLength() { return totalLength; }
    public void setTotalLength(String pLength) { totalLength = pLength; }
    
    private String customerKey;
    public String getCustomerKey() { return customerKey; }
    public void setCustomerKey(String pKey) { customerKey = pKey; }
    
    private String rackKey;
    public String getRackKey() { return rackKey; }
    public void setRackKey(String pKey) { rackKey = pKey; }
    
    private String truckCompanyKey;
    public String getTruckCompanyKey() { return truckCompanyKey; }
    public void setTruckCompanyKey(String pKey) { truckCompanyKey = pKey; }
    
    private String truckKey;
    public String getTruckKey() { return truckKey; }
    public void setTruckKey(String pKey) { truckKey = pKey; }
    
    private String truckDriverKey;
    public String getTruckDriverKey() { return truckDriverKey; }
    public void setTruckDriverKey(String pKey) { truckDriverKey = pKey; }
    
    private String comments;
    public String getComments() { return comments; }
    public void setComments(String pComments) { comments = pComments; }
    
    //--------------------------------------------------------------------------
    // Batch::Batch (constructor)
    //

    public Batch(String pSkoonieKey, String pId, String pDate, String pQuantity, 
                    String pLength, String pCustomerKey, String pRackKey,
                    String pTruckCompanyKey, String pTruckKey,
                    String pTruckDriverKey, String pComments)
    {
        
        super(pSkoonieKey, pId);
        
        date            = pDate;
        quantity        = pQuantity;
        totalLength     = pLength;
        customerKey     = pCustomerKey;
        rackKey         = pRackKey;
        truckCompanyKey = pTruckCompanyKey;
        truckKey        = pTruckKey;
        truckDriverKey  = pTruckDriverKey;
        comments        = pComments;

    }//end of Batch::Batch (constructor)
    //--------------------------------------------------------------------------
    
}//end of class Batch
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
