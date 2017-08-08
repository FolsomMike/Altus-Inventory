/*******************************************************************************
* Title: CustomersFrame.java
* Author: Hunter Schoonover
* Date: 11/20/15
*
* Purpose:
*
* This class is the Customers window. It retrieves all of the customers from the
* database and displays their names in a table.
*
*/

//------------------------------------------------------------------------------

package view;

//------------------------------------------------------------------------------

import java.awt.Component;
import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.Dialog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import model.Customer;
import model.MySQLDatabase;
import skooniecomponents.frame.SkoonieFrame;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class  CustomersFrame
//

public class CustomersFrame extends SkoonieFrame
{
    
    protected final MainView mainView;
    
    private final MySQLDatabase db = new MySQLDatabase();
    private ArrayList<Customer> customers;
    private CustomTable customersTable;
    private DefaultTableModel model;
    
    private CreateCustomerDialog createCustomerDialog;
    private EditCustomerDialog editCustomerDialog;

    //--------------------------------------------------------------------------
    // CustomersFrame::CustomersFrame (constructor)
    //

    public  CustomersFrame(MainView pMainView)
    {

        super("Customers", "CustomersFrame", pMainView, pMainView);
        
        mainView = pMainView;
        
        //don't maximize
        maximize = false;
        
        //don't allow resize
        resizable = false;
        
        //center the frame
        center = true;

    }//end of CustomersFrame::CustomersFrame (constructor)
    //-------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersFrame::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //
    
    @Override
    public void init() 
    {
        
        //initialize database
        db.init();
        
        //initialize model
        //create a model that allows no editable cells
        model = new DefaultTableModel() {
            @Override public boolean isCellEditable(int pR, int pC) {
                return false;
            }
        };
        
        setupCustomersTable();
        retrieveCustomerInfoFromDatabase();
        
        super.init();
        
    }// end of CustomersFrame::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersFrame::createGui
    //
    // Creates and adds the GUI to the mainPanel.
    //
    
    @Override
    protected void createGui() 
    {
        
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        
        //put the table in a scroll pane
        JScrollPane sp = new JScrollPane(customersTable);
        sp.setAlignmentX(LEFT_ALIGNMENT);
        sp.setAlignmentY(TOP_ALIGNMENT);
        Tools.setSizes(sp, 400, 300);
        mainPanel.add(sp);
        
        mainPanel.add(this.createHorizontalSpacer(10));
        
        mainPanel.add(createButtonsPanel());
        
    }// end of CustomersFrame::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersFrame::cancelCreateCustomer
    //
    // Cancels the create customer process by disposing of the Create Customer
    // window.
    //
    
    public void cancelCreateCustomer() 
    {

        createCustomerDialog.dispose();
        
    }// end of CustomersFrame::cancelCreateCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersFrame::cancelEditCustomer
    //
    // Cancels the changes made in the Edit Customer window by just disposing
    // of it.
    //
    
    public void cancelEditCustomer() 
    {

        editCustomerDialog.dispose();
        
    }// end of CustomersFrame::cancelEditCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersFrame::confirmCreateCustomer
    //
    // Confirms that the user wants to use the inputs in the Create Customer
    // window to create a new customer.
    //

    public void confirmCreateCustomer()
    {

        createCustomerDialog.confirm();

    }//end of CustomersFrame::confirmCreateCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersFrame::confirmEditCustomer
    //
    // Confirms the changes made in the Edit Customer window.
    //
    
    public void confirmEditCustomer() 
    {

        editCustomerDialog.confirm();
        
    }// end of CustomersFrame::confirmEditCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersFrame::createButton
    //
    // Creates and returns a JButton for the Buttons Panel, using the parameters
    // for individuality.
    //
    
    private JButton createButton(String pText, String pTip) 
    {
        
        CustomTextButton btn = new CustomTextButton(pText, 150, 30);
        btn.init();
        btn.addActionListener(mainView);
        btn.setToolTipText(pTip);
        btn.setActionCommand(Tools.generateActionCommand(actionId, pText));
        btn.setAlignmentX(LEFT_ALIGNMENT);
        
        return btn;
        
    }// end of CustomersFrame::createButton
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersFrame::createButtonsPanel
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
        panel.add(createButton("Create Customer", "Create a new customer."));
        
        panel.add(Tools.createVerticalSpacer(20));
        
        //Edit Customer button
        panel.add(createButton("Edit Customer", "Edit information about the "
                                                    + "selected customer."));
        
        
        return panel;
        
    }// end of CustomersFrame::createButtonsPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersFrame::displayCreateCustomerDialog
    //
    // Displays the Create Customer dialog.
    //
    
    public void displayCreateCustomerDialog() 
    {
        
        
        createCustomerDialog = new CreateCustomerDialog(this, mainView);
        createCustomerDialog.init();
        
    }// end of CustomersFrame::displayCreateCustomerDialog
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersFrame::displayEditCustomerDialog
    //
    // Displays the Edit Customer dialog.
    //
    
    public void displayEditCustomerDialog() 
    {
        
        
        editCustomerDialog = new EditCustomerDialog(getSelectedCustomer(), 
                                                        this, mainView);
        editCustomerDialog.init();
        
    }// end of CustomersFrame::displayEditCustomerDialog
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersFrame::getSelectedCustomer
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
        
    }// end of CustomersFrame::getSelectedCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersFrame::retrieveCustomerInfoFromDatabase
    //
    // Retrieves the customer info from the MySQL database and stores the
    // ids and names in the table model
    //
    
    public void retrieveCustomerInfoFromDatabase() 
    {
        
        //get customers from the database
        customers = db.getCustomers();
        
        String[] columnNames = {"Id", "Customer"};
        
        String[][] data = new String[customers.size()][];
        
        //extract ids and names from customers
        for (int i=0; i<data.length; i++) {
            data[i] = new String[]{customers.get(i).getId(), 
                                    customers.get(i).getDisplayName()};
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
        
    }// end of CustomersFrame::retrieveCustomerInfoFromDatabase
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersFrame::setupCustomersTable
    //
    // Initializes customersTable and sets it up for use.
    //
    
    private void setupCustomersTable() 
    {
        
        customersTable = new CustomTable(model);
        customersTable.init();
        
    }// end of CustomersFrame::setupCustomersTable
    //--------------------------------------------------------------------------

}//end of class CustomersFrame
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class CreateCustomerDialog
//
// This class is used for the Create Customer window.
//
//

class CreateCustomerDialog extends JDialog
{
    
    private final MainView mainView;
    private final CustomersFrame parent;
    
    private final String actionId = "CreateCustomerDialog";
    
    private final MySQLDatabase db = new MySQLDatabase();
    
    private JPanel mainPanel;
    private final Map<String, JTextField> inputFields = new HashMap<>();
    
    //--------------------------------------------------------------------------
    // CreateCustomerDialog::CreateCustomerDialog (constructor)
    //

    public CreateCustomerDialog(CustomersFrame pParent, MainView pMainView)
    {

        super(pParent);
        
        mainView    = pMainView;
        parent      = pParent;

    }//end of CreateCustomerDialog::CreateCustomerDialog (constructor)
    //-------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CreateCustomerDialog::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //
    
    public void init() 
    {
        
        setTitle("Create Customer");
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        
        //initialize the database
        db.init();
        
        //add a JPanel to the dialog to provide a familiar container
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setContentPane(mainPanel);
        
        createGui();
        
        //arrange all the GUI items
        pack();
        
        Tools.centerJDialog(this, parent);
        setVisible(true);
        
    }// end of CreateCustomerDialog::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ActionFrame::createCancelConfirmPanel
    //
    // Creates and returns a Cancel/Confirm panel.
    //

    private JPanel createCancelConfirmPanel()
    {

        CancelConfirmPanel panel = new CancelConfirmPanel("Create", 
                                                    "Create the new customer.", 
                                                    actionId, mainView);
        panel.init();

        return panel;

    }// end of ActionFrame::createCancelConfirmPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CreateCustomerDialog::createGui
    //
    // Creates the gui for the dialog and adds it to the mainPanel.
    //
    
    private void createGui() 
    {
        
        //add padding to the main panel
        int padding = 10;
        mainPanel.setBorder(BorderFactory.createEmptyBorder
                                        (padding, padding, padding, padding));
        
        int rowSpacer = 20;
        
        //add the Id and Customer Name row
        mainPanel.add(createRow(new JPanel[] {
            createInputPanel("Id", "The id used for the customer.", 100),
            createInputPanel("Name", "The customer's name.", 200)
        }));
        
        mainPanel.add(Tools.createVerticalSpacer(rowSpacer));
        
        //add the Address Line 1 and Address Line 2 row
        mainPanel.add(createRow(new JPanel[] {
            createInputPanel("Address Line 1",
                                "Address line 1 for the customer's location.",
                                200),
            createInputPanel("Address Line 2",
                                "Address line 2 for the customer's location.",
                                200)
        }));
        
        mainPanel.add(Tools.createVerticalSpacer(rowSpacer));
        
        int w = 130;
        //add the City, State, and Zip Code row
        mainPanel.add(createRow(new JPanel[] {
            createInputPanel("City", "City for the customer's location.", w),
            createInputPanel("State", "State for the customer's location.", w),
            createInputPanel("Zip Code", 
                                "Zip code for the customer's location.", w)
        }));
        
        mainPanel.add(Tools.createVerticalSpacer(rowSpacer));
        
        mainPanel.add(createCancelConfirmPanel());
        
    }// end of CreateCustomerDialog::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CreateCustomerDialog::confirm
    //
    // Confirms that the user wants to use the inputs to create a new customer.
    //

    public void confirm()
    {
        
        //use the user input to insert a customer into the database
        db.insertCustomer(getUserInput());
        
        //tell the Customers window to reload its data from the server since
        //we changed some stuff there
        parent.retrieveCustomerInfoFromDatabase();
        
        //dispose of the window and its resources
        dispose();

    }// end of CreateCustomerDialog::confirm
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CreateCustomerDialog::createInputPanel
    //
    // Creates and returns an input panel.
    //

    private JPanel createInputPanel(String pLabelText, String pToolTip,
                                        int pWidth)
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel(pLabelText);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        field.setToolTipText(pToolTip);
        Tools.setSizes(field, pWidth, 25);
        //store a reference to the field
        inputFields.put(pLabelText, field);
        panel.add(field);

        return panel;

    }// end of CreateCustomerDialog::createInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CreateCustomerDialog::createRow
    //
    // Creates and returns a row using pArray.
    //

    private JPanel createRow(JPanel[] pInputPanels)
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        for (int i=0; i<pInputPanels.length; i++) {
            if (i>0) { panel.add(Tools.createHorizontalSpacer(10)); }
            panel.add(pInputPanels[i]);
        }
        
        return panel;

    }// end of CreateCustomerDialog::createRow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CreateCustomerDialog::getUserInput
    //
    // Gets the user input from the text fields and returns it in a Customer.
    //

    private Customer getUserInput()
    {

        return new Customer (
                        inputFields.get("Id").getText(), 
                        inputFields.get("Name").getText(),
                        inputFields.get("Address Line 1").getText(),
                        inputFields.get("Address Line 2").getText(),
                        inputFields.get("City").getText(),
                        inputFields.get("State").getText(),
                        inputFields.get("Zip Code").getText()
                    );

    }// end of CreateCustomerDialog::getUserInput
    //--------------------------------------------------------------------------
    
}//end of class CreateCustomerDialog
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class EditCustomerDialog
//
// This class is used for the Edit Customer window.
//
//

class EditCustomerDialog extends JDialog
{
    
    private final MainView mainView;
    private final CustomersFrame parent;
    private final Customer customer;
    
    private final String actionId = "EditCustomerDialog";
    
    private final MySQLDatabase db = new MySQLDatabase();
    
    private JPanel mainPanel;
    private final Map<String, JTextField> inputFields = new HashMap<>();
    
    //--------------------------------------------------------------------------
    // EditCustomerDialog::EditCustomerDialog (constructor)
    //

    public EditCustomerDialog(Customer pC, CustomersFrame pParent, 
                                MainView pMainView)
    {

        super(pParent);
        
        customer    = pC;
        mainView    = pMainView;
        parent      = pParent;

    }//end of EditCustomerDialog::EditCustomerDialog (constructor)
    //-------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditCustomerDialog::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //
    
    public void init() 
    {
        
        setTitle("Edit Customer");
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        
        //initialize the database
        db.init();
        
        //add a JPanel to the dialog to provide a familiar container
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setContentPane(mainPanel);
        
        createGui();
        
        //arrange all the GUI items
        pack();
        
        Tools.centerJDialog(this, parent);
        setVisible(true);
        
    }// end of EditCustomerDialog::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ActionFrame::createCancelConfirmPanel
    //
    // Creates and returns a Cancel/Confirm panel.
    //

    private JPanel createCancelConfirmPanel()
    {

        CancelConfirmPanel panel = new CancelConfirmPanel("Apply", 
                                                    "Apply the changes made.", 
                                                    actionId, mainView);
        panel.init();

        return panel;

    }// end of ActionFrame::createCancelConfirmPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditCustomerDialog::createGui
    //
    // Creates the gui for the dialog and adds it to the mainPanel.
    //
    
    private void createGui() 
    {
        
        //add padding to the main panel
        int padding = 10;
        mainPanel.setBorder(BorderFactory.createEmptyBorder
                                        (padding, padding, padding, padding));
        
        int rowSpacer = 20;
        
        //add the Id and Customer Name row
        mainPanel.add(createRow(new JPanel[] {
            createInputPanel("Id", customer.getId(),
                                "The id used for the customer.", 100),
            createInputPanel("Name", customer.getDisplayName(),
                                "The customer's name.", 200)
        }));
        
        mainPanel.add(Tools.createVerticalSpacer(rowSpacer));
        
        //add the Address Line 1 and Address Line 2 row
        mainPanel.add(createRow(new JPanel[] {
            createInputPanel("Address Line 1", customer.getAddressLine1(),
                                "Address line 1 for the customer's location.",
                                200),
            createInputPanel("Address Line 2", customer.getAddressLine2(),
                                "Address line 2 for the customer's location.",
                                200)
        }));
        
        mainPanel.add(Tools.createVerticalSpacer(rowSpacer));
        
        int w = 130;
        //add the City, State, and Zip Code row
        mainPanel.add(createRow(new JPanel[] {
            createInputPanel("City", customer.getCity(),
                                "City for the customer's location.", w),
            createInputPanel("State", customer.getState(),
                                "State for the customer's location.", w),
            createInputPanel("Zip Code", customer.getZipCode(),
                                "Zip code for the customer's location.", w)
        }));
        
        mainPanel.add(Tools.createVerticalSpacer(rowSpacer));
        
        mainPanel.add(createCancelConfirmPanel());
        
    }// end of EditCustomerDialog::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditCustomerDialog::confirm
    //
    // Confirms the changes made to the customer by getting all of the user
    // inputs and sending them to the database.
    //

    public void confirm()
    {

        String oldId = customer.getId();
        
        getUserInput();
        
        db.updateCustomer(oldId, customer);
        
        //tell the Customers window to reload its data from the server since
        //we changed some stuff there
        parent.retrieveCustomerInfoFromDatabase();
        
        //dispose of the window and its resources
        dispose();

    }// end of EditCustomerDialog::confirm
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditCustomerDialog::createInputPanel
    //
    // Creates and returns an input panel.
    //

    private JPanel createInputPanel(String pLabelText, 
                                            String pInputFieldText,
                                            String pToolTip, int pWidth)
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel(pLabelText);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        field.setToolTipText(pToolTip);
        field.setText(pInputFieldText);
        Tools.setSizes(field, pWidth, 25);
        //store a reference to the field
        inputFields.put(pLabelText, field);
        panel.add(field);

        return panel;

    }// end of EditCustomerDialog::createInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditCustomerDialog::createRow
    //
    // Creates and returns a row using pArray.
    //

    private JPanel createRow(JPanel[] pInputPanels)
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        for (int i=0; i<pInputPanels.length; i++) {
            if (i>0) { panel.add(Tools.createHorizontalSpacer(10)); }
            panel.add(pInputPanels[i]);
        }
        
        return panel;

    }// end of EditCustomerDialog::createRow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditCustomerDialog::getUserInput
    //
    // Gets the user input from the text fields and stores it in the Customer.
    //

    private void getUserInput()
    {

        customer.setId(inputFields.get("Id").getText());
        
        customer.setDisplayName(inputFields.get("Name").getText());
        
        customer.setAddressLine1(inputFields.get("Address Line 1").getText());
        
        customer.setAddressLine2(inputFields.get("Address Line 2").getText());
        
        customer.setCity(inputFields.get("City").getText());
        
        customer.setState(inputFields.get("State").getText());
        
        customer.setZipCode(inputFields.get("Zip Code").getText());

    }// end of EditCustomerDialog::getUserInput
    //--------------------------------------------------------------------------
    
}//end of class EditCustomerDialog
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------