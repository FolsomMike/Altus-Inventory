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

import java.awt.Color;
import java.awt.Component;
import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Batch;
import model.Customer;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class ReceiveMaterialWindow
//

public class ReceiveMaterialWindow extends AltusJDialog
{
    
    static private final String actionId = "ReceiveMaterialWindow";
    static public String getActionId() { return actionId; }
    
    private JComboBox ownerCombo;
    
    private ArrayList<Customer> customers;
    
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
        
        int w = 130;
        
        //add the Id, Customer, and Date row
        addToMainPanel(createRow(new JPanel[] {
            createInputPanel("Id", "", 
                                "Give the material a reference ID.", w),
            createInputPanel("Date (YYYY-MM-DD)", "", 
                                "What date was the material received?", w)               
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        addToMainPanel(createRow(new JPanel[] { createOwnerPanel() }));
        
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
        
        //add the Quanity, Length, and Rack row
        addToMainPanel(createRow(new JPanel[] {
            createInputPanel("Quantity", "", "How many pieces of pipe?", w),
            createInputPanel("Total Length", "", 
                                "Total length of the pipe was received?", w),
            //DEBUG HSS//createInputPanel("Rack", "", 
            //DEBUG HSS//                    "What rack is the material stored on?", w)               
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
        
        //get the user input
        getUserInput();
        
        //insert the batch into the database
        getDatabase().insertBatch(batch);
        
        //WIP HSS// -- inform the MainFrame to update its stuff
        
        //dispose of the window and its resources
        dispose();

    }// end of ReceiveMaterialWindow::confirm
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::createOwnerPanel
    //
    // Creates and returns the Owner panel.
    //

    private JPanel createOwnerPanel()
    {
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel("Owner");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setAlignmentY(TOP_ALIGNMENT);
        panel.add(label);
        
        //get customers from the database
        customers = getDatabase().getCustomers();
        
        String[] names = new String[customers.size()+1];
        names[0] = "--Select--";
        
        //extract names from customers
        for (int i=0; i<customers.size(); i++) {
            names[i+1] = customers.get(i).getName();
        }
        
        //Create the combo box, select item at index 0
        ownerCombo = new JComboBox(names);
        ownerCombo.addActionListener(getMainView());
        ownerCombo.setAlignmentX(LEFT_ALIGNMENT);
        ownerCombo.setAlignmentY(TOP_ALIGNMENT);
        ownerCombo.setToolTipText("What customer owns the material?");
        ownerCombo.setSelectedIndex(0);
        ownerCombo.setBackground(Color.white);
        Tools.setSizes(ownerCombo, 410, getInputFieldHeight());
        panel.add(ownerCombo);
        
        return panel;

    }// end of ReceiveMaterialWindow::createOwnerPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::getCustomerKey
    //
    // Returns the Skoonie Key associated with pCustomerName
    //

    private String getCustomerKey(String pCustomerName)
    {
        
        String key = "";
        
        for (Customer c : customers) {
            if(pCustomerName.equals(c.getName())) { key = c.getSkoonieKey(); }
        }
        
        return key;

    }// end of ReceiveMaterialWindow::getCustomerKey
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::getUserInput
    //
    // Gets the user input from the text fields and stores it in the Batch.
    //

    private void getUserInput()
    {
        
        String cusKey = getCustomerKey((String)ownerCombo.getSelectedItem());
        
        batch = new Batch(  "",
                            getInputFields().get("Id").getText(),
                            getInputFields().get("Date (YYYY-MM-DD)").getText(),
                            getInputFields().get("Quantity").getText(),
                            getInputFields().get("Total Length").getText(),
                            cusKey);

    }// end of ReceiveMaterialWindow::getUserInput
    //--------------------------------------------------------------------------

}// end of class ReceiveMaterialWindow
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
