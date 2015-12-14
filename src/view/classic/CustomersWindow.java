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
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
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
        
        //has to be called before data is loaded
        setupTableModel();
        
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
        
        //Add Customer button
        panel.add(createButton( "Add", 
                                "Add a new customer.", ""));
        
        panel.add(Tools.createVerticalSpacer(buttonSpacer));
        
        //Edit Customer button
        panel.add(createButton( "Edit", 
                                "Edit information about the selected "
                                    + "customer.", 
                                "")); //WIP HSS// -- add action command
        
        panel.add(Tools.createVerticalSpacer(buttonSpacer));
        
        //Delete Customer button
        panel.add(createButton("Delete", "Delete the selected customer.", "")); //WIP HSS// -- add action command
        
        
        return panel;
        
    }// end of CustomersWindow::createButtonsPanel
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