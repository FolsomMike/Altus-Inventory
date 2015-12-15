/*******************************************************************************
* Title: EditRecordWindow.java
* Author: Hunter Schoonover
* Date: 12/14/15
*
* Purpose:
*
* This class is the Edit Record window. It displays the information stored in
* the Record passed in upon construction and allows the user to edit it.
*
*/

//------------------------------------------------------------------------------

package view.classic;

//------------------------------------------------------------------------------

import java.awt.Component;
import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Table;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class EditRecordWindow
//

public class EditRecordWindow extends AltusJDialog
{
    
    private final Table record;

    //--------------------------------------------------------------------------
    // EditRecordWindow::EditRecordWindow (constructor)
    //

    public EditRecordWindow(String pTitle, Window pParent, 
                                ActionListener pListener, Table pRecord)
    {

        super(pTitle, pParent, pListener);
        
        record = pRecord;

    }//end of EditRecordWindow::EditRecordWindow (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditRecordWindow::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //
    
    @Override
    public void init() 
    {
        
        super.init();
        
    }// end of EditRecordWindow::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditRecordWindow::createGui
    //
    // Creates and adds the GUI to the window.
    //
    
    @Override
    protected void createGui() 
    {
        
        //set the main panel layout to add components top to bottom
        setMainPanelLayout(BoxLayout.Y_AXIS);

        for (Map.Entry<String, String> entry : record.getColumns().entrySet()) 
        {
            createInputPanel(entry.getKey(), entry.getValue(), "");
            
            //vertical spacer
            addToMainPanel(Tools.createHorizontalSpacer(20));
        }
        
    }// end of EditRecordWindow::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditRecordWindow::createInputPanel
    //
    // Creates and returns an input panel containing a JLabel and a JTextField.
    //
    // The JTextField contained in this input panel is stored in an InputField
    // which is then stored in inputFields, with pLabel as they key.
    //

    private JPanel createInputPanel(String pLabel, String pInputFieldText,
                                        String pToolTip)
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel(pLabel);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        field.setToolTipText(pToolTip);
        field.setText(pInputFieldText);
        Tools.setSizes(field, 200, 25);
        panel.add(field);

        return panel;

    }// end of EditRecordWindow::createInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditRecordWindow::displayCustomers
    //
    // Adds the customers in pCustomers to the table.
    //
    
    public void displayCustomers(List<Table> pCustomers) 
    {
        
        //store the customers
        customers = pCustomers;
        
        //remove all of the data already in the model
        int rowCount = model.getRowCount();
        //Remove rows one by one from the end of the table
        for (int i=rowCount-1; i>=0; i--) { model.removeRow(i); }
        
        //add the ids and names of the customers to the table
        for (Table customer : customers) {
            model.addRow(new String[] { customer.getValue("id"), 
                                        customer.getValue("name")
                                        });
        }
        
    }// end of EditRecordWindow::displayCustomers
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditRecordWindow::setupTableModel
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
        
    }// end of EditRecordWindow::setupTableModel
    //--------------------------------------------------------------------------

}//end of class EditRecordWindow
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------