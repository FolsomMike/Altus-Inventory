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
*       Customer
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

public class TransferMaterialWindow extends AltusJDialog
{
    
    static private final String actionId = "TransferMaterialWindow";
    static public String getActionId() { return actionId; }

    //--------------------------------------------------------------------------
    // TransferMaterialWindow::TransferMaterialWindow (constructor)
    //

    public TransferMaterialWindow(MainFrame pMainFrame, MainView pMainView)
    {

        super("Transfer Material", pMainFrame, pMainView);

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
        
        setMainPanelLayout(BoxLayout.Y_AXIS);
        
        //add the Quantity and Customer row
        addToMainPanel(createRow(new JPanel[] {
            createInputPanel("Customer", "", "What customer is the material "
                                    + "being transferred to?", 130)
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Cancel/Confirm panel
        addToMainPanel(createCancelConfirmPanel("Transfer", 
                                                    "Transfer the material."));
        
    }// end of TransferMaterialWindow::createGui
    //--------------------------------------------------------------------------

}//end of class TransferMaterialWindow
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
