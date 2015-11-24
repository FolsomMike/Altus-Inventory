/*******************************************************************************
* Title: CustomersWindow.java
* Author: Hunter Schoonover
* Date: 11/20/15
*
* Purpose:
*
* This class is the Customers window. It retrieves all of the customers from the
* database and displays their names and ids in a table.
* 
* It allows users to perform actions relating to customers, currently:
*       Add Customer, Edit Customer
*
*/

//------------------------------------------------------------------------------

package view;

//------------------------------------------------------------------------------

import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import model.Customer;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class CustomersWindow
//

public class CustomersWindow extends AltusJDialog
{
    
    static private final String actionId = "CustomersWindow";
    static public String getActionId() { return actionId; }
    
    private ArrayList<Customer> customers;
    private CustomTable customersTable;
    private DefaultTableModel model;
    
    private CreateOrEditCustomerWindow createEditWindow;

    //--------------------------------------------------------------------------
    // CustomersWindow::CustomersWindow (constructor)
    //

    public CustomersWindow(MainFrame pMainFrame, MainView pMainView)
    {

        super("Customers", pMainFrame, pMainView);

    }//end of CustomersWindow::CustomersWindow (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //
    
    @Override
    public void init() 
    {
        
        initializeCustomersTable();
        retrieveCustomersFromDatabase();
        
        super.init();
        
    }// end of AltusJDialog::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersWindow::createGui
    //
    // Creates and adds the GUI to the window.
    //
    
    @Override
    protected void createGui() 
    {
        
        setMainPanelLayout(BoxLayout.X_AXIS);
        
        //put the table in a scroll pane
        JScrollPane sp = new JScrollPane(customersTable);
        sp.setAlignmentX(LEFT_ALIGNMENT);
        sp.setAlignmentY(TOP_ALIGNMENT);
        Tools.setSizes(sp, 400, 300);
        addToMainPanel(sp);
        
        addToMainPanel(Tools.createHorizontalSpacer(10));
        
        addToMainPanel(createButtonsPanel());
        
    }// end of CustomersWindow::createGui
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
        
        //Create Customer button
        panel.add(createButton("Create Customer", "Create a new customer.", 
                                actionId));
        
        panel.add(Tools.createVerticalSpacer(20));
        
        //Edit Customer button
        panel.add(createButton("Edit Customer", 
                                "Edit information about the selected customer.",
                                actionId));
        
        
        return panel;
        
    }// end of CustomersWindow::createButtonsPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersWindow::displayCreateCustomerWindow
    //
    // Displays the Create Customer window and sets it as the active dialog.
    //
    
    public void displayCreateCustomerWindow() 
    {
        
        
        createEditWindow = new CreateOrEditCustomerWindow(this, getMainFrame(), 
                                                            getMainView());
        createEditWindow.init();
        
    }// end of CustomersWindow::displayCreateCustomerWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersWindow::displayEditCustomerWindow
    //
    // Displays the Edit Customer window and sets it as the active dialog.
    //
    
    public void displayEditCustomerWindow() 
    {
        
        
        createEditWindow = new CreateOrEditCustomerWindow(
                                            getSelectedCustomer(), this, 
                                            getMainFrame(), getMainView());
        createEditWindow.init();
        
    }// end of CustomersWindow::displayEditCustomerWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersWindow::getSelectedCustomer
    //
    // Gets and returns the customer that is selected in the table.
    //
    
    private Customer getSelectedCustomer() 
    {
        
        int row = customersTable.getSelectedRow();
        String id = (String)customersTable.getValueAt(row, 0);
        
        //look for the customer that matches the selected id
        for (Customer c : customers) {
            if (id.equals(c.getId())) { return c; }
        }
        
        //no customer was found so return null
        return null;
        
    }// end of CustomersWindow::getSelectedCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersWindow::initializeCustomersTable
    //
    // Initializes customersTable for use.
    //
    
    private void initializeCustomersTable() 
    {
        
        //initialize model -- allows no editable cells
        model = new DefaultTableModel() {
            @Override public boolean isCellEditable(int pR, int pC) {
                return false;
            }
        };
        
        customersTable = new CustomTable(model);
        customersTable.init();
        
    }// end of CustomersWindow::initializeCustomersTable
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersWindow::retrieveCustomersFromDatabase
    //
    // Retrieves the customers from the MySQL database and puts the ids and 
    // names in the table model
    //
    
    public void retrieveCustomersFromDatabase() 
    {
        
        //get customers from the database
        customers = getDatabase().getCustomers();
        
        String[] columnNames = {"Id", "Customer"};
        
        String[][] data = new String[customers.size()][];
        
        //extract ids and names from customers
        for (int i=0; i<data.length; i++) {
            data[i] = new String[]{customers.get(i).getId(), 
                                        customers.get(i).getName()};
        }
        
        model.setDataVector(data, columnNames);
        
        //now that we've put data in the model, we can set some table settings
        
        //select the first row of the table
        if (customersTable.getRowCount() > 0) { 
            customersTable.setRowSelectionInterval(0, 0);
        }
        
        //set the widths of the columns
        TableColumnModel m =  customersTable.getColumnModel();
        m.getColumn(0).setPreferredWidth(100);
        m.getColumn(1).setPreferredWidth(300);
        
    }// end of CustomersWindow::retrieveCustomersFromDatabase
    //--------------------------------------------------------------------------

}//end of class CustomersWindow
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class CreateOrEditCustomerWindow
//
// This class is used for a window that serves as both the Create Customer and
// Edit Custmer windows, depending on the title.
//
// To make the class function as the Create Customer window, use the first
// constructor; to make it function as the Edit Customer Window, use the second
// constructor.
//
//

class CreateOrEditCustomerWindow extends AltusJDialog
{
    
    static private final String actionId = "CreateOrEditCustomerWindow";
    static public String getActionId() { return actionId; }
    
    private final int mode;
    private final int mode_create   = 1;
    private final int mode_edit     = 2;
    
    private final Customer customer;
    private final CustomersWindow customersWindow;
    
    private final String confirmButtonText;
    private final String confirmButtonToolTip;
    
    //--------------------------------------------------------------------------
    // CreateOrEditCustomerWindow::CreateOrEditCustomerWindow (constructor)
    //
    // Constructor for using the Create Customer window.
    //

    public CreateOrEditCustomerWindow(CustomersWindow pCustomersWindow, 
                                        MainFrame pMainFrame, 
                                        MainView pMainView)
    {

        super("Create Customer", pCustomersWindow, pMainFrame, pMainView);
        
        mode            = mode_create;
        customer        = new Customer("", "", "", "", "", "", "", "");
        customersWindow = pCustomersWindow;
        
        //confirm button text and tool tip
        confirmButtonText       = "Create";
        confirmButtonToolTip    = "Create the new customer.";

    }//end of CreateOrEditCustomerWindow::CreateOrEditCustomerWindow (constructor)
    //-------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CreateOrEditCustomerWindow::CreateOrEditCustomerWindow (constructor)
    //
    // Constructor for using the Edit Customer window.
    //

    public CreateOrEditCustomerWindow(Customer pCustomer,
                                        CustomersWindow pCustomersWindow, 
                                        MainFrame pMainFrame, 
                                        MainView pMainView)
    {

        super("Edit Customer", pCustomersWindow, pMainFrame, pMainView);
        
        mode            = mode_edit;
        customer        = pCustomer;
        customersWindow = pCustomersWindow;
        
        //confirm button text and tool tip
        confirmButtonText       = "Apply";
        confirmButtonToolTip    = "Apply the changes made.";

    }//end of CreateOrEditCustomerWindow::CreateOrEditCustomerWindow (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CreateOrEditCustomerWindow::createGui
    //
    // Creates the gui for the window.
    //
    
    @Override
    protected void createGui() 
    {
        
        int rowSpacer = 20;
        
        //add the Id and Customer Name row
        addToMainPanel(createRow(new JPanel[] {
            createInputPanel("Id", customer.getId(),
                                "The id used for the customer.", 100),
            createInputPanel("Name", customer.getName(),
                                "The customer's name.", 200)
        }));
        
        addToMainPanel(Tools.createVerticalSpacer(rowSpacer));
        
        //add the Address Line 1 and Address Line 2 row
        addToMainPanel(createRow(new JPanel[] {
            createInputPanel("Address Line 1", customer.getAddressLine1(),
                                "Address line 1 for the customer's location.",
                                200),
            createInputPanel("Address Line 2", customer.getAddressLine2(),
                                "Address line 2 for the customer's location.",
                                200)
        }));
        
        addToMainPanel(Tools.createVerticalSpacer(rowSpacer));
        
        int w = 130;
        //add the City, State, and Zip Code row
        addToMainPanel(createRow(new JPanel[] {
            createInputPanel("City", customer.getCity(),
                                "City for the customer's location.", w),
            createInputPanel("State", customer.getState(),
                                "State for the customer's location.", w),
            createInputPanel("Zip Code", customer.getZipCode(),
                                "Zip code for the customer's location.", w)
        }));
        
        addToMainPanel(Tools.createVerticalSpacer(rowSpacer));
        
        addToMainPanel(createCancelConfirmPanel(confirmButtonText, 
                                                    confirmButtonToolTip));

    }// end of CreateOrEditCustomerWindow::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CreateOrEditCustomerWindow::confirm
    //
    // Confirms that the user wants to use the inputs to create a new customer.
    //

    @Override
    public void confirm()
    {
        
        //get the user input
        getUserInput();
        
        //insert the customer into the database if we are in create mode
        if (mode == mode_create) { getDatabase().insertCustomer(customer); }
        
        //update the customer if we are in edit mode
        else if (mode == mode_edit) { getDatabase().updateCustomer(customer); }
        
        //tell the Customers window to reload its data from the database since
        //we changed some stuff there
        customersWindow.retrieveCustomersFromDatabase();
        
        //dispose of the window and its resources
        dispose();

    }// end of CreateOrEditCustomerWindow::confirm
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CreateOrEditCustomerWindow::getUserInput
    //
    // Gets the user input from the text fields and stores it in the Customer.
    //

    private void getUserInput()
    {

        customer.setId(getInputFields().get("Id").getText());
        
        customer.setName(getInputFields().get("Name").getText());
        
        customer.setAddressLine1(getInputFields().get("Address Line 1").getText());
        
        customer.setAddressLine2(getInputFields().get("Address Line 2").getText());
        
        customer.setCity(getInputFields().get("City").getText());
        
        customer.setState(getInputFields().get("State").getText());
        
        customer.setZipCode(getInputFields().get("Zip Code").getText());

    }// end of CreateOrEditCustomerWindow::getUserInput
    //--------------------------------------------------------------------------
    
}//end of class CreateOrEditCustomerWindow
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------