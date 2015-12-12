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
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import model.MySQLDatabase;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class CustomersWindow
//

public class CustomersWindow extends AltusJDialog
{
    
    private CustomTable table;
    private DefaultTableModel model;

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
        
        //initialize model -- allows no editable cells
        model = new DefaultTableModel() {
            @Override public boolean isCellEditable(int pR, int pC) {
                return false;
            }
        };
        
        //add the columns to the model
        model.setColumnIdentifiers(new String[]{"Id", "Name"});
        
        //set up the table
        table = new CustomTable(model);
        table.init();
        
        //put the table in a scroll pane and add it to the main panel
        JScrollPane sp = new JScrollPane(table);
        sp.setAlignmentX(LEFT_ALIGNMENT);
        sp.setAlignmentY(TOP_ALIGNMENT);
        Tools.setSizes(sp, 400, 300);
        addToMainPanel(sp);
        
    }// end of CustomersWindow::createGui
    //--------------------------------------------------------------------------

}//end of class CustomersWindow
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------