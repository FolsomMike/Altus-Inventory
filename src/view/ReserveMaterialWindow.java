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

public class ReserveMaterialWindow extends ActionFrame
{

    //--------------------------------------------------------------------------
    // ReserveMaterialWindow::ReserveMaterialWindow (constructor)
    //

    public ReserveMaterialWindow(MainView pMainView)
    {

        super("Reserve Material", "ReserveMaterialFrame", pMainView);

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
        
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        //vertical spacer
        mainPanel.add(createVerticalSpacer(20));
        
        //add Row 1
        mainPanel.add(createRow1());
        
        //vertical spacer
        mainPanel.add(createVerticalSpacer(30));
        
        //add the Cancel/Confirm panel
        mainPanel.add(createCancelConfirmPanel("Reserve", 
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
        combo.addActionListener(mainView);     
        combo.setActionCommand("ReserveMaterialFrame::Reserve for");
        combo.setAlignmentX(LEFT_ALIGNMENT);
        combo.setAlignmentY(TOP_ALIGNMENT);
        combo.setSelectedIndex(0);
        combo.setBackground(Color.white);
        Tools.setSizes(combo, 135, textFieldHeight);
        panel.add(combo);
        
        return panel;

    }// end of ReserveMaterialWindow::createReserveForPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReserveMaterialWindow::createRow1
    //
    // Creates and returns Row 1.
    //
    
    private JPanel createRow1() {
        
        String tip = "How many pieces of material would you like to reserve?";
        JPanel input1 = createQuantityInputPanel(tip);
        
        JPanel input2 = createReserveForPanel();

        return createRow(new JPanel[]{input1, input2});
        
    }// end of ReserveMaterialWindow::createRow1
    //--------------------------------------------------------------------------

}// end of class ReserveMaterialWindow
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
