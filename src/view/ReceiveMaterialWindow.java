/*******************************************************************************
* Title: ReceiveMaterialWindow.java
* Author: Hunter Schoonover
* Date: 07/25/15
*
* Purpose:
*
* This class is the Receive Material window.
* 
* It presents the user with input fields about the a new material that they
* want to receive.
* 
* Currently, it has input fields for:
*       Id, Customer, Date, Truck Company, Truck Number, Truck Driver, Quantity,
*       Length, Rack, Range, Grade, Diameter, Wall, Facility
*
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
import model.Customer;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class ReceiveMaterialWindow
//

public class ReceiveMaterialWindow extends AltusJDialog
{
    
    static private final String actionId = "ReceiveMaterialWindow";
    static public String getActionId() { return actionId; }
    
    private ArrayList<Customer> customers;

    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::ReceiveMaterialWindow (constructor)
    //

    public ReceiveMaterialWindow(MainFrame pMainFrame, MainView pMainView)
    {

        super("Receive Material", pMainFrame, pMainView);
        
    }// end of ReceiveMaterialWindow::ReceiveMaterialWindow (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::createGui
    //
    // Creates and adds the GUI to the mainPanel.
    //
    
    @Override
    protected void createGui() 
    {
        
        setMainPanelLayout(BoxLayout.Y_AXIS);
        
        int w = 130;
        
        //add the Id, Customer, and Date row
        addToMainPanel(createRow(new JPanel[] {
            createInputPanel("Id", "", 
                                "Give the material a reference ID.", w),
            createOwnerPanel(),
            createInputPanel("Date (YYYY-MM-DD)", "", 
                                "What date was the material received?", w)               
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Truck Company, Truck Number, and Truck Driver row
        addToMainPanel(createRow(new JPanel[] {
            createInputPanel("Truck Company", "", 
                                "What truck company brought the material to "
                                        + "the yard?", w),
            createInputPanel("Truck Number", "", 
                                "What is the number of the truck that brought "
                                        + "the material to the yard?", w),
            createInputPanel("Truck Driver", "", 
                                "Who was the driver of the truck that brought"
                                        + " the material to the yard?", w)               
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Quanity, Length, and Rack row
        addToMainPanel(createRow(new JPanel[] {
            createInputPanel("Quantity", "", "How many pieces of pipe?", w),
            createInputPanel("Length", "", "How much pipe was received?", w),
            createInputPanel("Rack", "", 
                                "What rack is the material stored on?", w)               
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Range, Grade, and Diameter row
        addToMainPanel(createRow(new JPanel[] {
            createInputPanel("Range", "", "What is the range of the pipe?", w),
            createInputPanel("Grade", "", "What is the grade of the pipe?", w),
            createInputPanel("Diameter", "", 
                                "What is the outside diameter of the pipe?", w)               
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Wall and Facility row
        addToMainPanel(createRow(new JPanel[] {
            createInputPanel("Wall", "", 
                                "What is the wall thickness of the pipe?", w),
            createInputPanel("Facility", "", 
                                "What facility is the pipe for?", w)            
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Cancel/Confirm panel
        addToMainPanel(createCancelConfirmPanel(
                                        "Receive", 
                                        "Receive the material into the yard."));
        
    }// end of ReceiveMaterialWindow::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::createOwnerPanel
    //
    // Creates and returns the Owner panel.
    //

    private JPanel createOwnerPanel()
    {
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel("Owner");
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
        JComboBox combo = new JComboBox(names);
        combo.addActionListener(getMainView());
        combo.setAlignmentX(LEFT_ALIGNMENT);
        combo.setAlignmentY(TOP_ALIGNMENT);
        combo.setToolTipText("What customer owns the material?");
        combo.setSelectedIndex(0);
        combo.setBackground(Color.white);
        Tools.setSizes(combo, 135, getInputFieldHeight());
        panel.add(combo);
        
        return panel;

    }// end of ReceiveMaterialWindow::createOwnerPanel
    //--------------------------------------------------------------------------

}// end of class ReceiveMaterialWindow
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
