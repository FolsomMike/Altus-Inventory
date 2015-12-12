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

import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.Window;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import model.MySQLDatabase;
import model.Record;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class CustomersWindow
//

public class CustomersWindow extends AltusJDialog
{
    
    private CustomTable table;
    private DefaultTableModel model;
    
    private ArrayList<Record> customers;
    
    //Table names -- back quotes so that they can be easily put in cmd strings
    private final String customersDbTable = "`CUSTOMERS`";

    //--------------------------------------------------------------------------
    // CustomersWindow::CustomersWindow (constructor)
    //

    public CustomersWindow(Window pParent, MySQLDatabase db)
    {

        super("Customers", pParent, db);

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
        
        //has to be called before data is loaded
        setupTableModel();
        
        //has to be called before call to super's init because super displays
        //modal which will stop all other execution of code in this function
        loadDataFromDatabase();
        
        super.init();
        
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
        
        //Create Customer button
        panel.add(createButton("Add", "Create a new customer."));
        
        panel.add(Tools.createVerticalSpacer(buttonSpacer));
        
        //Edit Customer button
        panel.add(createButton("Edit", "Edit information about the selected "
                                        + "customer."));
        
        panel.add(Tools.createVerticalSpacer(buttonSpacer));
        
        //Delete Customer button
        panel.add(createButton("Delete", "Delete the selected customer."));
        
        
        return panel;
        
    }// end of CustomersWindow::createButtonsPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomersWindow::loadDataFromDatabase
    //
    // Loads the customers from the database and stores them in the customers
    // list. Once the data has been loaded, the ids and names are put into the
    // model for the table.
    //
    
    public void loadDataFromDatabase() 
    {
        
        //remove all of the data already in the model
        int rowCount = model.getRowCount();
        //Remove rows one by one from the end of the table
        for (int i=rowCount-1; i>=0; i--) { model.removeRow(i); }
        
        //get customers from the database
        getDatabase().connectToDatabase();
        customers = getDatabase().getRecords(customersDbTable);
        getDatabase().closeDatabaseConnection();
        
        //extract ids and names from customers
        for (Record customer : customers) {
            model.addRow(new String[] { customer.getValue("id"), 
                                        customer.getValue("name")
                                        });
        }
        
    }// end of CustomersWindow::loadDataFromDatabase
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