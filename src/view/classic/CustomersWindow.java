/*******************************************************************************
* Title: CustomersWindow.java
* Author: Hunter Schoonover
* Date: 12/11/15
*
* Purpose:
*
* This class is the Customers window.
*
*/

//------------------------------------------------------------------------------

package view.classic;

//------------------------------------------------------------------------------

import command.Command;
import command.CommandHandler;
import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.Window;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import shared.Record;
import shared.Table;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class CustomersWindow
//

public class CustomersWindow extends AltusJDialog implements CommandHandler
{
    
    private CustomTable table;
    private DefaultTableModel model;
    
    private Table customers;
    
    private EditRecordWindow editRecordWindow;

    //--------------------------------------------------------------------------
    // CustomersWindow::CustomersWindow (constructor)
    //

    public CustomersWindow(Window pParent, ActionListener pListener)
    {

        super("Customers", pParent, pListener);

    }//end of CustomersWindow::CustomersWindow (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersWindow::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //
    
    @Override
    public void init() 
    {
        
        //set up the table model
        setupTableModel();
        
        //perform a command to get the customers
        (new Command("get customers")).perform();
        
        super.init();
        
        //center and make visible
        setVisible();
        
    }// end of CustomersWindow::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersWindow::createGui
    //
    // Creates and adds the GUI to the window.
    //
    
    @Override
    protected void createGui() 
    {
        
        //set the main panel layout to add components left to right
        setMainPanelLayout(BoxLayout.X_AXIS);
        
        //set up the table
        table = new CustomTable(model);
        table.init();
        
        //put the table in a scroll pane and add it to the main panel
        JScrollPane sp = new JScrollPane(table);
        sp.setAlignmentX(LEFT_ALIGNMENT);
        sp.setAlignmentY(TOP_ALIGNMENT);
        Tools.setSizes(sp, 400, 300);
        addToMainPanel(sp);
        
        //horizontal spacer
        addToMainPanel(Tools.createHorizontalSpacer(10));
        
        //add the buttons panel
        addToMainPanel(createButtonsPanel());
        
    }// end of CustomersWindow::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersWindow::handleCommand
    //
    // Performs different actions depending on pCommand.
    //
    
    @Override
    public void handleCommand(Command pCommand) 
    {
        
        switch (pCommand.getMessage()) {
            
            case "edit selected customer":
                editSelectedCustomer();
                break;
            
            case "display add customer window":
                displayAddCustomerWindow();
                break;
                
            case "delete selected customer":
                deleteSelectedCustomer();
                break;
            
            case "display customers":
                displayCustomers((Table)pCommand.get("customers"));
                break;
                
        }
        
        if (editRecordWindow != null) {
            //DEBUG HSS//editRecordWindow.handleCommand(pCommand);
        }
        
    }//end of CustomersWindow::handleCommand
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersWindow::createButtonsPanel
    //
    // Creates and returns a JPanel containing all of the buttons for the
    // window.
    //
    
    private JPanel createButtonsPanel() 
    {
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        int buttonSpacer = 20;
        
        //Add Customer button
        panel.add(createButton( "Add", 
                                "Add a new customer.", 
                                "display add customer window"));
        
        panel.add(Tools.createVerticalSpacer(buttonSpacer));
        
        //Edit Customer button
        panel.add(createButton( "Edit", 
                                "Edit information about the selected "
                                    + "customer.", 
                                "edit selected customer"));
        
        panel.add(Tools.createVerticalSpacer(buttonSpacer));
        
        //Delete Customer button
        panel.add(createButton("Delete", "Delete the selected customer.", 
                                "delete selected customer"));
        
        return panel;
        
    }// end of CustomersWindow::createButtonsPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersWindow::editSelectedCustomer
    //
    // Gets the selected customer skoonie key from the table and passes it on to
    // the EditRecordWindow.
    //
    
    private void editSelectedCustomer() 
    {
        
        Record rec = customers.getRecords().get(table.getSelectedRow());
        String key = rec.getSkoonieKey();
        editRecordWindow = new EditRecordWindow("Edit Customer", this, 
                                                getActionListener(),
                                                customers, key);
        editRecordWindow.init();
        
    }// end of CustomersWindow::editSelectedCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersWindow::deleteSelectedCustomer
    //
    // Deletes the customer selected in the table.
    //
    
    private void deleteSelectedCustomer() 
    {
        
        Command command = new Command("delete customer");
        Record rec = customers.getRecords().get(table.getSelectedRow());
        String key = rec.getSkoonieKey();
        command.put("customer key", key);
        command.perform();
        
    }// end of CustomersWindow::deleteSelectedCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersWindow::displayAddCustomerWindow
    //
    // Displays the Add Customer window.
    //
    
    private void displayAddCustomerWindow() 
    {
        
        editRecordWindow = new EditRecordWindow("Add Customer", this, 
                                                getActionListener(),
                                                customers, null);
        editRecordWindow.init();
        
    }// end of CustomersWindow::displayAddCustomerWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersWindow::displayCustomers
    //
    // Adds the customers in pCustomers to the table.
    //
    
    private void displayCustomers(Table pCustomers) 
    {
        
        //store the customers
        customers = pCustomers;
        
        //remove all of the data already in the model
        int rowCount = model.getRowCount();
        //Remove rows one by one from the end of the table
        for (int i=rowCount-1; i>=0; i--) { model.removeRow(i); }
        
        String idKey = customers.getDescriptorKeyByName("Id");
        String nameKey = customers.getDescriptorKeyByName("Name");
        
        //add the ids and names of the customers to the table
        for (Record customer : customers.getRecords()) {
            String id = customer.getValue(idKey);
            String name = customer.getValue(nameKey);
            model.addRow( new String[] { id, name });
        }
        
    }// end of CustomersWindow::displayCustomers
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersWindow::setupTableModel
    //
    // Sets up the table model for use
    //
    
    private void setupTableModel() 
    {
        
        //initialize model -- allows no editable cells
        model = new DefaultTableModel() {
            @Override public boolean isCellEditable(int pR, int pC) {
                return false;
            }
        };
        
        //add the column names to the model
        model.setColumnIdentifiers(new String[]{"Id", "Name"});
        
    }// end of CustomersWindow::setupTableModel
    //--------------------------------------------------------------------------

}//end of class CustomersWindow
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------