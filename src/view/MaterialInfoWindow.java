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
        addToMainPanel(createRow(new JPanel[] { 
            createCustomerPanel(batch) 
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Quanity, Total Length, and Rack row
        addToMainPanel(createRow(new JPanel[] {
            createQuantityPanel(batch.getQuantity()),
            createTotalLengthPanel(batch.getTotalLength()),
            createRackPanel(batch)
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Truck Company row
        addToMainPanel(createRow(new JPanel[] {
            createTruckCompanyPanel(batch.getTruckCompanyKey())
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Truck row
        addToMainPanel(createRow(new JPanel[] {
            createTruckPanel(batch.getTruckKey())
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Truck Driver row
        addToMainPanel(createRow(new JPanel[] {
            createTruckDriverPanel(batch.getTruckDriverKey())
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Comments row
        addToMainPanel(createRow(new JPanel[] {
            createCommentsPanel(batch.getComments())
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

    }// end of MaterialInfoWindow::confirm
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MaterialInfoWindow::checkUserInput
    //
    // Checks the user input for errors.
    //
    // Returns true if no errors; false if there are.
    //

    private boolean checkUserInput()
    {
        
        //Check Id input
        if (getIdInput().isEmpty()) {
            displayError("Please give the material an Id.");
            return false;
        }
        else if (!getIdInput().equals(batch.getId())
                && getDatabase().checkForValue(getIdInput(), "BATCHES", "id")) 
        {
            displayError("The Id entered already exists in the database.");
            return false;
        }
        
        //Check Date input
        if (getDateInput().isEmpty()) {
            displayError("Please specify a date.");
            return false;
        }
        
        //Check Customer input
        if (!checkCustomerInput()) { 
            displayError("Please select a customer.");
            return false;
        }
        
        //Check Quantity input
        if (getQuantityInput().isEmpty()) {
            displayError("Please enter a Quantity.");
            return false;
        }
        
        //Check Total Length input
        if (getTotalLengthInput().isEmpty()) {
            displayError("Please enter a Total Length.");
            return false;
        }
        
        //Check Rack input
        if (!checkRackInput()) { 
            displayError("Please select a rack.");
            return false;
        }
        
        //Check Truck Company input
        if (!checkTruckCompanyInput()) { 
            displayError("Please select a truck company.");
            return false;
        }
        
        //Check Truck input
        if (!checkTruckInput()) { 
            displayError("Please select a truck.");
            return false;
        }
        
        //Check Truck Driver input
        if (!checkTruckDriverInput()) { 
            displayError("Please select a truck driver.");
            return false;
        }
        
        //we made it here, so there were no errors
        return true;

    }// end of MaterialInfoWindow::checkUserInput
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
        batch.setTruckCompanyKey(getTruckCompanyInput());
        batch.setTruckKey(getTruckInput());
        batch.setTruckDriverKey(getTruckDriverInput());
        batch.setComments(getCommentsInput());

    }// end of MaterialInfoWindow::getUserInput
    //--------------------------------------------------------------------------

}// end of class MaterialInfoWindow
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
