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

import java.awt.Color;
import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
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
    
    private CustomTable customersTable;
    private DefaultTableModel model;
    
    //declared as object so that they can be easily added to the table
    private List<String> customerIds = new ArrayList<>();
    private List<String> customerNames = new ArrayList<>();

    //--------------------------------------------------------------------------
    // CustomersFrame::CustomersFrame (constructor)
    //

    public  CustomersFrame(MainView pMainView)
    {

        super("Customers", "CustomersFrame", pMainView, pMainView);
        
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
        
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        setupCustomersTable();
        
        //put the table in a scroll pane
        JScrollPane sp = new JScrollPane(customersTable);
        sp.setAlignmentX(LEFT_ALIGNMENT);
        sp.setAlignmentY(TOP_ALIGNMENT);
        //sp.setBorder(BorderFactory.createEmptyBorder());
        Tools.setSizes(sp, 400, 300);
        mainPanel.add(sp);
        
    }// end of CustomersFrame::createGui
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
        
        //set the widths of the columns
        TableColumnModel m =  customersTable.getColumnModel();
        m.getColumn(0).setPreferredWidth(100);
        m.getColumn(1).setPreferredWidth(300);
        
    }// end of CustomersFrame::setupCustomersTable
    //--------------------------------------------------------------------------

}//end of class  CustomersFrame
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
