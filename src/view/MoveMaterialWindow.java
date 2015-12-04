/*******************************************************************************
* Title:  MoveMaterialWindow.java
* Author: Hunter Schoonover
* Date: 07/26/15
*
* Purpose:
*
* This class is the Move Material window.
* 
* It presents the user with input fields to specify the quantity of the material
* and what rack to move it to.
* 
* Currently, it has input fields for:
*       Rack
*
*/

//------------------------------------------------------------------------------

package view;

//------------------------------------------------------------------------------

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import model.Batch;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class MoveMaterialWindow
//

public class  MoveMaterialWindow extends AltusJDialog
{
    
    static private final String actionId = "MoveMaterialWindow";
    static public String getActionId() { return actionId; }
    
    private final Batch batch;

    //--------------------------------------------------------------------------
    // MoveMaterialWindow::MoveMaterialWindow (constructor)
    //

    public MoveMaterialWindow(Batch pBatch, MainFrame pMainFrame, 
                                MainView pMainView)
    {

        super("Move Material", pMainFrame, pMainView);
        
        batch = pBatch;

    }// end of MoveMaterialWindow::MoveMaterialWindow (constructor)
    //-------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MoveMaterialWindow::createGui
    //
    // Creates and adds the GUI to the mainPanel.
    //
    
    @Override
    protected void createGui() 
    {
        
        setMainPanelLayout(BoxLayout.Y_AXIS);
        
        //add the Rack row
        addToMainPanel(createRow(new JPanel[] {
            createRackPanel(batch)
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Cancel/Confirm panel
        addToMainPanel(createCancelConfirmPanel("Move", "Move the material."));
        
    }// end of MoveMaterialWindow::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MoveMaterialWindow::confirm
    //
    // Confirms that the user wants to move a material using the inputs.
    //

    @Override
    public void confirm()
    {
        
        //check user input for errors
        if (!checkUserInput()) { return; }
        
        //get the user input
        getUserInput();
        
        //update the batch in the database
        getDatabase().updateBatch(batch);
        
        //tell the MainFrame to reload its data from the database since we 
        //changed some stuff there
        getMainFrame().retrieveBatchesFromDatabase();
        
        //dispose of the window and its resources
        dispose();

    }// end of MoveMaterialWindow::confirm
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MoveMaterialWindow::checkUserInput
    //
    // Checks the user input for errors.
    //
    // Returns true if no errors; false if there are.
    //

    private boolean checkUserInput()
    {
        
        //Check Rack input
        if (!checkRackInput()) { 
            displayError("Please select a rack.");
            return false;
        }
        
        //we made it here, so there were no errors
        return true;

    }// end of MoveMaterialWindow::checkUserInput
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MoveMaterialWindow::getUserInput
    //
    // Gets the user input from the text fields and stores it in the Batch.
    //

    private void getUserInput()
    {
        
        batch.setRackKey(getRackInput());

    }// end of MoveMaterialWindow::getUserInput
    //--------------------------------------------------------------------------

}// end of class MoveMaterialWindow
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
