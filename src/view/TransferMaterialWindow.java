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
// class TransferMaterialWindow
//

public class TransferMaterialWindow extends AltusJDialog
{
    
    static private final String actionId = "TransferMaterialWindow";
    static public String getActionId() { return actionId; }
    
    private JComboBox customerCombo;
    
    private ArrayList<Customer> customers;
    
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
        
        //DEBUG HSS//
        System.out.println("Batch Id: " + batch.getId());
        
        setMainPanelLayout(BoxLayout.Y_AXIS);
        
        //add the Quantity and Customer row
        addToMainPanel(createRow(new JPanel[] {
            createCustomerPanel()
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
    // TransferMaterialWindow::createCustomerPanel
    //
    // Creates and returns the Customer panel.
    //

    private JPanel createCustomerPanel()
    {
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel("Transfer to customer:");
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
        customerCombo = new JComboBox(names);
        customerCombo.addActionListener(getMainView());
        customerCombo.setAlignmentX(LEFT_ALIGNMENT);
        customerCombo.setAlignmentY(TOP_ALIGNMENT);
        customerCombo.setToolTipText("What customer is the material being "
                                        + "transferred to?");
        customerCombo.setSelectedIndex(0);
        customerCombo.setBackground(Color.white);
        Tools.setSizes(customerCombo, 410, getInputFieldHeight());
        panel.add(customerCombo);
        
        return panel;

    }// end of TransferMaterialWindow::createCustomerPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TransferMaterialWindow::getCustomerKey
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

    }// end of TransferMaterialWindow::getCustomerKey
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TransferMaterialWindow::getUserInput
    //
    // Gets the user input from the text fields and stores it in the Batch.
    //

    private void getUserInput()
    {
        
        batch.setCustomerKey(getCustomerKey((String)customerCombo
                                                        .getSelectedItem()));

    }// end of TransferMaterialWindow::getUserInput
    //--------------------------------------------------------------------------

}//end of class TransferMaterialWindow
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
