/*******************************************************************************
* Title: ReserveMaterialWindow.java
* Author: Hunter Schoonover
* Date: 07/25/15
*
* Purpose:
*
* This class is the Reserve Material window.
*
* It presents the user with input fields to specify the quantity of the material
* and what to reserve it for.
* 
* //WIP HSS// -- should eventually have different input fields based on what
*                   they want to reserve it for
* 
* Currently, it has input fields for:
*       Quantity, Reserve for
*
*/

//------------------------------------------------------------------------------

package view;

//------------------------------------------------------------------------------

import java.awt.Color;
import java.awt.Component;
import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class ReserveMaterialWindow
//

public class ReserveMaterialWindow extends AltusJDialog
{
    
    static private final String actionId = "ReserveMaterialWindow";
    static public String getActionId() { return actionId; }

    //--------------------------------------------------------------------------
    // ReserveMaterialWindow::ReserveMaterialWindow (constructor)
    //

    public ReserveMaterialWindow(MainFrame pMainFrame, MainView pMainView)
    {

        super("Reserve Material", pMainFrame, pMainView);

    }//end of ReserveMaterialWindow::ReserveMaterialWindow (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReserveMaterialWindow::createGui
    //
    // Creates and adds the GUI to the mainPanel.
    //
    
    @Override
    protected void createGui() 
    {
        
        setMainPanelLayout(BoxLayout.Y_AXIS);
        
        //add the Quantity and Rack row
        addToMainPanel(createRow(new JPanel[] {
            createQuantityPanel(""),
            createReserveForPanel()
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Cancel/Confirm panel
        addToMainPanel(createCancelConfirmPanel("Reserve", 
                                                    "Reserve the material."));
        
    }// end of ReserveMaterialWindow::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReserveMaterialWindow::creatReserveForPanel
    //
    // Creates and returns the "Reserve for" panel.
    //

    private JPanel createReserveForPanel()
    {
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel("Reserve for");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setAlignmentY(TOP_ALIGNMENT);
        panel.add(label);
        
        //populate an array of strings with items for a combo box
        String[] strings = { "--Select--", "Shipment", "Move", "Transfer" };
        //Create the combo box, select item at index 0
        JComboBox combo = new JComboBox(strings);
        combo.addActionListener(getMainView());     
        combo.setActionCommand(Tools.generateActionCommand(actionId, 
                                                                "Reserve for"));
        combo.setAlignmentX(LEFT_ALIGNMENT);
        combo.setAlignmentY(TOP_ALIGNMENT);
        combo.setSelectedIndex(0);
        combo.setBackground(Color.white);
        Tools.setSizes(combo, 135, getInputHeight());
        panel.add(combo);
        
        return panel;

    }// end of ReserveMaterialWindow::createReserveForPanel
    //--------------------------------------------------------------------------

}// end of class ReserveMaterialWindow
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
