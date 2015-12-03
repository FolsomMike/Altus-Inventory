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
import model.Batch;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class TransferMaterialWindow
//

public class TransferMaterialWindow extends AltusJDialog
{
    
    static private final String actionId = "TransferMaterialWindow";
    static public String getActionId() { return actionId; }
    
    private final Batch batch;

    //--------------------------------------------------------------------------
    // TransferMaterialWindow::TransferMaterialWindow (constructor)
    //

    public TransferMaterialWindow(Batch pBatch, MainFrame pMainFrame, 
                                    MainView pMainView)
    {

        super("Transfer Material", pMainFrame, pMainView);
        
        batch = pBatch;

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
            createCustomerPanel(batch)
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Cancel/Confirm panel
        addToMainPanel(createCancelConfirmPanel("Transfer", 
                                                    "Transfer the material."));
        
    }// end of TransferMaterialWindow::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TransferMaterialWindow::confirm
    //
    // Confirms that the user wants to transfer a material using the inputs.
    //

    @Override
    public void confirm()
    {
        
        //get the user input
        getUserInput();
        
        //update the batch in the database
        getDatabase().updateBatch(batch);
        
        //tell the MainFrame to reload its data from the database since we 
        //changed some stuff there
        getMainFrame().retrieveBatchesFromDatabase();
        
        //dispose of the window and its resources
        dispose();

    }// end of TransferMaterialWindow::confirm
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TransferMaterialWindow::getUserInput
    //
    // Gets the user input from the text fields and stores it in the Batch.
    //

    private void getUserInput()
    {
        
        batch.setCustomerKey(getCustomerInput());

    }// end of TransferMaterialWindow::getUserInput
    //--------------------------------------------------------------------------

}//end of class TransferMaterialWindow
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
