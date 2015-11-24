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
*       Quantity, Rack
*
*/

//------------------------------------------------------------------------------

package view;

//------------------------------------------------------------------------------

import javax.swing.BoxLayout;
import javax.swing.JPanel;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class MoveMaterialWindow
//

public class  MoveMaterialWindow extends AltusJDialog
{
    
    static private final String actionId = "MoveMaterialWindow";
    static public String getActionId() { return actionId; }

    //--------------------------------------------------------------------------
    // MoveMaterialWindow::MoveMaterialWindow (constructor)
    //

    public  MoveMaterialWindow(MainFrame pMainFrame, MainView pMainView)
    {

        super("Move Material", pMainFrame, pMainView);

    }//end of  MoveMaterialWindow::MoveMaterialWindow (constructor)
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
        
        //add the Quantity and Rack row
        addToMainPanel(createRow(new JPanel[] {
            createQuantityInputPanel("How many pieces of material would you "
                                        + "like to ship?"),
            createInputPanel("Rack", "", "What rack is the material being "
                                    + "moved to?", 130)
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Cancel/Confirm panel
        addToMainPanel(createCancelConfirmPanel("Move", "Move the material."));
        
    }// end of MoveMaterialWindow::createGui
    //--------------------------------------------------------------------------

}//end of class MoveMaterialWindow
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
