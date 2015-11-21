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
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
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
    
    private CustomTable customersTable;
    private DefaultTableModel model;
    
    //declared as object so that they can be easily added to the table
    private final List<String> customerIds = new ArrayList<>();
    private final List<String> customerNames = new ArrayList<>();

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
        
        getCustomerInfoFromDatabase();
        setupCustomersTable();
        
        super.init();
        
        //DEBUG HSS//
        EditCustomerDialog d = new EditCustomerDialog(mainView, this);
        d.init();
        //DEBUG HSS//
        
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
    // CustomersFrame::createButton
    //
    // Creates and returns a JButton for the Buttons Panel, using the parameters
    // for individuality.
    //
    
    private JButton createButton(String pText, String pTip) 
    {
        
        CustomTextButton btn = new CustomTextButton(pText);
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
        
        //Edit Customer button
        panel.add(createButton("Edit Customer", "Edit information about the "
                                                    + "selected customer."));
        
        return panel;
        
    }// end of CustomersFrame::createButtonsPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersFrame::getCustomerInfoFromDatabase
    //
    // Retrieves the customer info from the MySQL database and stores the
    // ids and names.
    //
    
    private void getCustomerInfoFromDatabase() 
    {
        
        MySQLDatabase db = new MySQLDatabase();
        db.init();
        
        ArrayList<Customer> customers = db.getCustomers();
        for (Customer c : customers) {
            customerIds.add(c.getId());
            customerNames.add(c.getDisplayName());
        }
        
    }// end of CustomersFrame::getCustomerInfoFromDatabase
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersFrame::setupCustomersTable
    //
    // Initializes customersTable and sets it up for use.
    //
    
    private void setupCustomersTable() 
    {
        
        String[] columnNames = {"Id", "Customer"};
        
        String[][] data = new String[customerIds.size()][];
        
        for (int i=0; i<data.length; i++) {
            data[i] = new String[]{customerIds.get(i), customerNames.get(i)};
        }
        
        //create a model that allows no editable cells
        model = new DefaultTableModel(data, columnNames) {
            @Override public boolean isCellEditable(int pR, int pC) {
                return false;
            }
        };
        
        customersTable = new CustomTable(model);
        customersTable.init();
        
        //select the first row of the table
        customersTable.setRowSelectionInterval(0, 0);
        
        //set the widths of the columns
        TableColumnModel m =  customersTable.getColumnModel();
        m.getColumn(0).setPreferredWidth(100);
        m.getColumn(1).setPreferredWidth(300);
        
    }// end of CustomersFrame::setupCustomersTable
    //--------------------------------------------------------------------------

}//end of class CustomersFrame
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
    private final JFrame parent;
    
    private final String actionId = "EditCustomerDialog";
    
    private JPanel mainPanel;
    
    //--------------------------------------------------------------------------
    // EditCustomerDialog::EditCustomerDialog (constructor)
    //

    public EditCustomerDialog(MainView pMainView, JFrame pParent)
    {

        super(pParent);
        
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
        setResizable(false);
        
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

        CancelConfirmPanel panel = new CancelConfirmPanel("Confirm", 
                                                    "Confirm the changes made.", 
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
        
    }// end of EditCustomerDialog::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditCustomerDialog::createInputPanel
    //
    // Creates and returns an input panel.
    //

    protected final JPanel createInputPanel(String pLabelText, String pToolTip,
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
        panel.add(field);

        return panel;

    }// end of EditCustomerDialog::createInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ActionFrame::createRow
    //
    // Creates and returns a row using pArray.
    //

    protected final JPanel createRow(JPanel[] pInputPanels)
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

    }// end of ActionFrame::createRow
    //--------------------------------------------------------------------------
    
}//end of class EditCustomerDialog
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------