/*******************************************************************************
* Title: CustomersFrame.java
* Author: Hunter Schoonover
* Date: 11/20/15
*
* Purpose:
*
* This class is the Customers window. It retrieves all of the customers from the
* database and displays their names in a table.
*
*/

//------------------------------------------------------------------------------

package view;

//------------------------------------------------------------------------------

import javax.swing.BoxLayout;
import skooniecomponents.frame.SkoonieFrame;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class  CustomersFrame
//

public class  CustomersFrame extends SkoonieFrame
{

    //--------------------------------------------------------------------------
    // CustomersFrame::CustomersFrame (constructor)
    //

    public  CustomersFrame(MainView pMainView)
    {

        super("Customers", "CustomersFrame", pMainView, pMainView);
        
        //don't maximize
        maximize = false;
        
        //don't all resize
        resizable = false;
        
        //center the frame
        center = true;

    }//end of CustomersFrame::CustomersFrame (constructor)
    //-------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersFrame::createGui
    //
    // Creates and adds the GUI to the mainPanel.
    //
    
    @Override
    protected void createGui() 
    {
        
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        //vertical spacer
        mainPanel.add(createVerticalSpacer(20));
        
    }// end of CustomersFrame::createGui
    //--------------------------------------------------------------------------

}//end of class  CustomersFrame
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
