/*******************************************************************************
* Title:  TransferMaterialFrame.java
* Author: Hunter Schoonover
* Date: 07/26/15
*
* Purpose:
*
* This class is the Transfer Material window.
*
*/

//------------------------------------------------------------------------------

package view;

//------------------------------------------------------------------------------

import javax.swing.BoxLayout;
import javax.swing.JPanel;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class  TransferMaterialFrame
//

public class  TransferMaterialFrame extends ActionFrame
{

    //--------------------------------------------------------------------------
    //  TransferMaterialFrame:: TransferMaterialFrame (constructor)
    //

    public  TransferMaterialFrame(MainView pMainView)
    {

        super("Transfer Material", "TransferMaterialFrame", pMainView);

    }//end of  TransferMaterialFrame:: TransferMaterialFrame (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TransferMaterialFrame::createGui
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
        
    }// end of TransferMaterialFrame::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TransferMaterialFrame::createRow1
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
        
    }// end of TransferMaterialFrame::createRow1
    //--------------------------------------------------------------------------

}//end of class  TransferMaterialFrame
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
