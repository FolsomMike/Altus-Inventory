/*******************************************************************************
* Title:  MoveMaterialWindow.java
* Author: Hunter Schoonover
* Date: 07/26/15
*
* Purpose:
*
* This class is the Move Material window.
* 
* It presents the user with input fields to specify the quantity of the material
* and what rack to move it to.
* 
* Currently, it has input fields for:
*       Rack
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
import model.Batch;
import model.Rack;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class MoveMaterialWindow
//

public class  MoveMaterialWindow extends AltusJDialog
{
    
    static private final String actionId = "MoveMaterialWindow";
    static public String getActionId() { return actionId; }
    
    private JComboBox rackCombo;
    
    private ArrayList<Rack> racks;
    
    private final Batch batch;

    //--------------------------------------------------------------------------
    // MoveMaterialWindow::MoveMaterialWindow (constructor)
    //

    public MoveMaterialWindow(Batch pBatch, MainFrame pMainFrame, 
                                MainView pMainView)
    {

        super("Move Material", pMainFrame, pMainView);
        
        batch = pBatch;

    }// end of MoveMaterialWindow::MoveMaterialWindow (constructor)
    //-------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MoveMaterialWindow::createGui
    //
    // Creates and adds the GUI to the mainPanel.
    //
    
    @Override
    protected void createGui() 
    {
        
        setMainPanelLayout(BoxLayout.Y_AXIS);
        
        //add the Rack row
        addToMainPanel(createRow(new JPanel[] {
            createRackPanel()
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Cancel/Confirm panel
        addToMainPanel(createCancelConfirmPanel("Move", "Move the material."));
        
    }// end of MoveMaterialWindow::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MoveMaterialWindow::createRackPanel
    //
    // Creates and returns the Rack panel.
    //

    private JPanel createRackPanel()
    {
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel("Rack");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setAlignmentY(TOP_ALIGNMENT);
        panel.add(label);
        
        //get racks from the database
        racks = getDatabase().getRacks();
        
        String[] names = new String[racks.size()+1];
        names[0] = "--Select--";
        
        //extract names from racks
        for (int i=0; i<racks.size(); i++) {
            names[i+1] = racks.get(i).getName();
        }
        
        //Create the combo box, select item at index 0
        rackCombo = new JComboBox(names);
        rackCombo.addActionListener(getMainView());
        rackCombo.setAlignmentX(LEFT_ALIGNMENT);
        rackCombo.setAlignmentY(TOP_ALIGNMENT);
        rackCombo.setToolTipText("What rack is the material being moved to?");
        rackCombo.setSelectedIndex(0);
        rackCombo.setBackground(Color.white);
        Tools.setSizes(rackCombo, 130, getInputFieldHeight());
        panel.add(rackCombo);
        
        return panel;

    }// end of MoveMaterialWindow::createRackPanel
    //--------------------------------------------------------------------------

}// end of class MoveMaterialWindow
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
