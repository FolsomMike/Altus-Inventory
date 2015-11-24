/*******************************************************************************
* Title:  TransferMaterialWindow.java
* Author: Hunter Schoonover
* Date: 07/26/15
*
* Purpose:
*
* This class is the Transfer Material window.
*
* It presents the user with input fields to specify the quantity of the material
* and what customer to transfer it to.
* 
* Currently, it has input fields for:
*       Quantity, Customer
*/

//------------------------------------------------------------------------------

package view;

//------------------------------------------------------------------------------

import javax.swing.BoxLayout;
import javax.swing.JPanel;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class TransferMaterialWindow
//

public class TransferMaterialWindow extends ActionFrame
{

    //--------------------------------------------------------------------------
    // TransferMaterialWindow::TransferMaterialWindow (constructor)
    //

    public  TransferMaterialWindow(MainView pMainView)
    {

        super("Transfer Material", "TransferMaterialFrame", pMainView);

    }//end of TransferMaterialWindow::TransferMaterialWindow (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TransferMaterialWindow::createGui
    //
    // Creates and adds the GUI to the mainPanel.
    //
    
    @Override
    protected void createGui() 
    {
        
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        //vertical spacer
        mainPanel.add(createVerticalSpacer(20));
        
        //add Row 1
        mainPanel.add(createRow1());
        
        //vertical spacer
        mainPanel.add(createVerticalSpacer(30));
        
        //add the Cancel/Confirm panel
        mainPanel.add(createCancelConfirmPanel("Transfer", 
                                                "Transfer the material."));
        
    }// end of TransferMaterialWindow::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TransferMaterialWindow::createRow1
    //
    // Creates and returns a JPanel containing the Quantity and Customer input 
    // panels.
    //
    
    private JPanel createRow1() {
        
        String tip = "How many pieces of material would you like to transfer?";
        JPanel input1 = createQuantityInputPanel(tip);
        
        JPanel input2 
                    = createInputPanel("Customer", 
                        "What customer is the material being transferred to?", 
                        TEXT_FIELD_WIDTH_ONE_THIRD, textFieldHeight);

        return createRow(new JPanel[]{input1, input2});
        
    }// end of TransferMaterialWindow::createRow1
    //--------------------------------------------------------------------------

}//end of class TransferMaterialWindow
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
