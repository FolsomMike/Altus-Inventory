/*******************************************************************************
* Title: ReceiveMaterialWindow.java
* Author: Hunter Schoonover
* Date: 07/25/15
*
* Purpose:
*
* This class is the Receive Material window.
* 
* It presents the user with input fields about the a new material that they
* want to receive.
* 
* Currently, it has input fields for:
*       Id, Customer, Date, Truck Company, Truck Number, Truck Driver, Quantity,
*       Length, Rack, Range, Grade, Diameter, Wall, Facility
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
// class ReceiveMaterialWindow
//

public class ReceiveMaterialWindow extends AltusJDialog
{
    
    static private final String actionId = "ReceiveMaterialWindow";
    static public String getActionId() { return actionId; }
    
    private Batch batch;

    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::ReceiveMaterialWindow (constructor)
    //

    public ReceiveMaterialWindow(MainFrame pMainFrame, MainView pMainView)
    {

        super("Receive Material", pMainFrame, pMainView);
        
    }// end of ReceiveMaterialWindow::ReceiveMaterialWindow (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::createGui
    //
    // Creates and adds the GUI to the mainPanel.
    //
    
    @Override
    protected void createGui() 
    {
        
        setMainPanelLayout(BoxLayout.Y_AXIS);
        
        //add the Id and Date row
        addToMainPanel(createRow(new JPanel[] {
            createIdPanel(""),
            createDatePanel("")              
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Customer row
        addToMainPanel(createRow(new JPanel[] { 
            createCustomerPanel(null)
        }));
        
        //add the Truck Company, Truck Number, and Truck Driver row
        /*//DEBUG HSS//addToMainPanel(createRow(new JPanel[] {
            createInputPanel("Truck Company", "", 
                                "What truck company brought the material to "
                                        + "the yard?", w),
            createInputPanel("Truck Number", "", 
                                "What is the number of the truck that brought "
                                        + "the material to the yard?", w),
            createInputPanel("Truck Driver", "", 
                                "Who was the driver of the truck that brought"
                                        + " the material to the yard?", w)               
        }));*///DEBUG HSS//
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Quanity, Total Length, and Rack row
        addToMainPanel(createRow(new JPanel[] {
            createQuantityPanel(""),
            createTotalLengthPanel(""),
            createRackPanel(null)
        }));
        
        //add the Range, Grade, and Diameter row
        /*//DEBUG HSS//addToMainPanel(createRow(new JPanel[] {
            createInputPanel("Range", "", "What is the range of the pipe?", w),
            createInputPanel("Grade", "", "What is the grade of the pipe?", w),
            createInputPanel("Diameter", "", 
                                "What is the outside diameter of the pipe?", w)               
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Wall and Facility row
        addToMainPanel(createRow(new JPanel[] {
            createInputPanel("Wall", "", 
                                "What is the wall thickness of the pipe?", w),
            createInputPanel("Facility", "", 
                                "What facility is the pipe for?", w)            
        }));*///DEBUG HSS//
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Cancel/Confirm panel
        addToMainPanel(createCancelConfirmPanel(
                                        "Receive", 
                                        "Receive the material into the yard."));
        
    }// end of ReceiveMaterialWindow::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::confirm
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
        
        //insert the batch into the database
        getDatabase().insertBatch(batch);
        
        //tell the MainFrame to reload its data from the database since we 
        //changed some stuff there
        getMainFrame().retrieveBatchesFromDatabase();
        
        //dispose of the window and its resources
        dispose();

    }// end of ReceiveMaterialWindow::confirm
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::checkUserInput
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
        else if (getDatabase().checkForValue(getIdInput(), "BATCHES", "id")) {
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
        
        //we made it here, so there were no errors
        return true;

    }// end of ReceiveMaterialWindow::checkUserInput
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::getUserInput
    //
    // Gets the user input from the text fields and stores it in the Batch.
    //

    private void getUserInput()
    {
        
        batch = new Batch(  "",
                            getIdInput(),
                            getDateInput(),
                            getQuantityInput(),
                            getTotalLengthInput(),
                            getCustomerInput(),
                            getRackInput(),
                            "", "", "");

    }// end of ReceiveMaterialWindow::getUserInput
    //--------------------------------------------------------------------------

}// end of class ReceiveMaterialWindow
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
