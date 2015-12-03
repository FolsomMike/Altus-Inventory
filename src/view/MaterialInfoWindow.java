/*******************************************************************************
* Title: MaterialInfoWindow.java
* Author: Hunter Schoonover
* Date: 11/30/15
*
* Purpose:
*
* This class is the Material Info window.
* 
* It is intended to present the user with editable input fields containing 
* information about the material.
* 
* Currently, it has input fields for:
*       Id, Customer, Date, Quantity, Total Length, Owner, Rack
*
*/

//------------------------------------------------------------------------------

package view;

//------------------------------------------------------------------------------

import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import model.Batch;
import model.Customer;
import model.Rack;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class MaterialInfoWindow
//

public class MaterialInfoWindow extends AltusJDialog
{
    
    static private final String actionId = "MaterialInfoWindow";
    static public String getActionId() { return actionId; }
    
    private JComboBox ownerCombo;
    private JComboBox rackCombo;
    
    private ArrayList<Customer> customers;
    private ArrayList<Rack> racks;
    
    private final Batch batch;

    //--------------------------------------------------------------------------
    // MaterialInfoWindow::MaterialInfoWindow (constructor)
    //

    public MaterialInfoWindow(Batch pBatch, MainFrame pMainFrame, 
                                MainView pMainView)
    {

        super("Material Info", pMainFrame, pMainView);
        
        batch = pBatch;
        
    }// end of MaterialInfoWindow::MaterialInfoWindow (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MaterialInfoWindow::createGui
    //
    // Creates and adds the GUI to the mainPanel.
    //
    
    @Override
    protected void createGui() 
    {
        
        setMainPanelLayout(BoxLayout.Y_AXIS);
        
        //add the Id, Date row
        addToMainPanel(createRow(new JPanel[] {
            createIdPanel(batch.getId()),
            createDatePanel(batch.getDate())               
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Customer row
        addToMainPanel(createRow(new JPanel[] { createCustomerPanel() }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Quanity, Total Length, and Rack row
        addToMainPanel(createRow(new JPanel[] {
            createQuantityPanel(batch.getQuantity()),
            createTotalLengthPanel(batch.getTotalLength()),
            createRackPanel()
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Cancel/Confirm panel
        addToMainPanel(createCancelConfirmPanel("Apply", 
                                                "Apply the changes made."));
        
    }// end of MaterialInfoWindow::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MaterialInfoWindow::confirm
    //
    // Confirms that the user wants to receive a material using the inputs.
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

    }// end of MaterialInfoWindow::confirm
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MaterialInfoWindow::getUserInput
    //
    // Gets the user input from the text fields and stores it in the Batch.
    //

    private void getUserInput()
    {
        
        batch.setId(getIdInput());
        batch.setDate(getDateInput());
        batch.setQuantity(getQuantityInput());
        batch.setTotalLength(getTotalLengthInput());
        batch.setCustomerKey(getCustomerInput());
        batch.setRackKey(getRackInput());

    }// end of MaterialInfoWindow::getUserInput
    //--------------------------------------------------------------------------

}// end of class MaterialInfoWindow
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
