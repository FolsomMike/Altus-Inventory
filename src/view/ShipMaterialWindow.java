/*******************************************************************************
* Title: ShipMaterialWindow.java
* Author: Hunter Schoonover
* Date: 07/25/15
*
* Purpose:
*
* This class is the Ship Material window.
*
* It presents the user with input fields to specify information about the
* destination a material is being shipped to, including the address and the 
* truck company that is taking it there, and the quantity of the material.
* 
* Currently, it has input fields for:
*       Quantity, Destination, Address Line 1, Address Line 2, City, State,
*       Zip Code, Truck Company, Truck Driver
* 
*/

//------------------------------------------------------------------------------

package view;

//------------------------------------------------------------------------------

import javax.swing.BoxLayout;
import javax.swing.JPanel;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class ShipMaterialWindow
//

public class ShipMaterialWindow extends AltusJDialog
{
    
    static private final String actionId = "ShipMaterialWindow";
    static public String getActionId() { return actionId; }

    //--------------------------------------------------------------------------
    // ShipMaterialWindow::ShipMaterialWindow (constructor)
    //

    public ShipMaterialWindow(MainFrame pMainFrame, MainView pMainView)
    {

        super("Ship Material", pMainFrame, pMainView);

    }//end of ShipMaterialWindow::ShipMaterialWindow (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ShipMaterialWindow::createGui
    //
    // Creates and adds the GUI to the mainPanel.
    //
    
    @Override
    protected void createGui() 
    {
        
        setMainPanelLayout(BoxLayout.Y_AXIS);
        
        //add the Quantity row
        addToMainPanel(createRow(new JPanel[] {
            createQuantityPanel("")         
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
                
        //add the Destination row
        /*addToMainPanel(createRow(new JPanel[] {
            createInputPanel("Destination", "", 
                                "What destination is the material being "
                                    + "shipped to?", 410),
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Address Line 1 and Address Line 2 row
        addToMainPanel(createRow(new JPanel[] {
            createInputPanel("Address Line 1", "", 
                                "Address line 1 of the destination the "
                                    + "material is being shipped to.", 200),
            createInputPanel("Address Line 2", "", 
                                "Address line 2 of the destination the "
                                    + "material is being shipped to.", 200),             
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the City, State, and Zip Code row
        addToMainPanel(createRow(new JPanel[] {
            createInputPanel("City", "", "What city is the material being "
                                    + "shipped to?", w),
            createInputPanel("State", "", "What state is the material being "
                                    + "shipped to?", w),
            createInputPanel("Zip Code", "", "What zip code is the material "
                                    + "being shipped to?", w),
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
                
        //add the Truck Company, Truck Number, and Truck Driver row
        addToMainPanel(createRow(new JPanel[] {
            createInputPanel("Truck Company", "", 
                                "What truck company brought the material to "
                                        + "the yard?", w),
            createInputPanel("Truck Number", "", 
                                "What is the number of the truck that brought "
                                        + "the material to the yard?", w),
            createInputPanel("Truck Driver", "", 
                                "Who was the driver of the truck that brought"
                                        + " the material to the yard?", w)               
        }));*/
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Cancel/Confirm panel
        addToMainPanel(createCancelConfirmPanel("Ship", "Ship the material."));
        
    }// end of ShipMaterialWindow::createGui
    //--------------------------------------------------------------------------

}//end of class ShipMaterialWindow
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------