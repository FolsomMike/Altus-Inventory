/*******************************************************************************
* Title: CustomerSettingsWindow.java
* Author: Hunter Schoonover
* Date: 12/17/15
*
* Purpose:
*
* //WIP HSS//
*
*/

//------------------------------------------------------------------------------

package view.classic;

//------------------------------------------------------------------------------

import command.Command;
import command.CommandHandler;
import java.awt.Color;
import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import shared.Descriptor;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class CustomerSettingsWindow
//

public class CustomerSettingsWindow extends AltusJDialog 
        implements CommandHandler
{

    //--------------------------------------------------------------------------
    // CustomerSettingsWindow::CustomerSettingsWindow (constructor)
    //

    public CustomerSettingsWindow(Window pParent, ActionListener pListener)
    {

        super("Customer Settings", pParent, pListener);
        
    }//end of CustomerSettingsWindow::CustomerSettingsWindow (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomerSettingsWindow::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //
    
    @Override
    public void init() 
    {
        
        //perform a command to get the records
        (new Command("get customer descriptors")).perform();
        
        super.init();
        
        //repack gui components since we changed stuff
        pack();
        
        //center and make visible
        setVisible();
        
    }// end of CustomerSettingsWindow::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomerSettingsWindow::createGui
    //
    // Creates and adds the GUI to the window.
    //
    
    @Override
    protected void createGui() 
    {
        
        //set the main panel layout to add components top to bottom
        setMainPanelLayout(BoxLayout.Y_AXIS);
        
        //add the input panels for the descriptors to the gui
//        addToMainPanel(createDescriptorInputsPanel());
        
        //add the cancel confirm panel
        addToMainPanel(createCancelConfirmPanel());
        
    }// end of CustomerSettingsWindow::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomerSettingsWindow::handleCommand
    //
    // Performs different actions depending on pCommand.
    //
    
    @Override
    public void handleCommand(Command pCommand) 
    {
        
        switch (pCommand.getMessage()) {
            
            case "display customer descriptors":
                //displayDescriptors(pCommand.get("descriptors"));
                break;
                
        }
        
    }//end of CustomerSettingsWindow::handleCommand
    //--------------------------------------------------------------------------\
    
    //--------------------------------------------------------------------------
    // CustomerSettingsWindow::createCancelConfirmPanel
    //
    // Creates and returns a panel containing the Cancel and OK buttons.
    //

    private JPanel createCancelConfirmPanel()
    {

        //this outer panel is needed so that we can add a vertical spacer
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(BOTTOM_ALIGNMENT);
        panel.setBorder(BorderFactory.createMatteBorder(1,0,0,0, Color.GRAY));
        
        //vertical spacer
        panel.add(Tools.createVerticalSpacer(10));
        
        //panel to hold the buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.setAlignmentX(LEFT_ALIGNMENT);
        buttonsPanel.setAlignmentY(BOTTOM_ALIGNMENT);
        
        //horizontal center 
        //-- only works to "push" to the center if another glue is used
        buttonsPanel.add(Box.createHorizontalGlue());
        
        //add the Cancel button
        buttonsPanel.add(createButton("Cancel", "" , 
                                        "CustomerSettingsWindow -- cancel"));
        
        //horizontal spacer
        buttonsPanel.add(Tools.createHorizontalSpacer(20));
        
        //add the OK button
        buttonsPanel.add(createButton("OK", "" , "CustomerSettingsWindow -- confirm"));
        
        //horizontal center 
        //-- only works to "push" to the center if another glue is used
        buttonsPanel.add(Box.createHorizontalGlue());
        
        panel.add(buttonsPanel);

        return panel;

    }// end of CustomerSettingsWindow::createCancelConfirmPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomerSettingsWindow::displayDescriptors
    //
    // //WIP HSS//
    //

    private void displayDescriptors()
    {

        

    }// end of CustomerSettingsWindow::displayDescriptors
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomerSettingsWindow::createInputPanel
    //
    // Creates and returns an input panel containing a JLabel and a JTextField
    // using the information in pDescriptor.
    //
    // The JTextField contained in this input panel is stored in the inputs
    // map, using pDescriptor.getSkoonie() as the key.
    //

    private JPanel createInputPanel(Descriptor pDescriptor)
    {

        return null;
        
    }// end of CustomerSettingsWindow::createInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomerSettingsWindow::createRow
    //
    // Creates and returns a row of JPanels using pPanels, using pSpacer as the
    // spacing between the panels.
    //

    protected final JPanel createRow(List<JPanel> pPanels, int pSpacer)
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        for (int i=0; i<pPanels.size(); i++) {
            if (i>0) { panel.add(Tools.createHorizontalSpacer(pSpacer)); }
            panel.add(pPanels.get(i));
        }
        
        return panel;

    }// end of CustomerSettingsWindow::createRow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomerSettingsWindow::getUserInput
    //
    // Gets and checks the user input. If Descriptor.getRequired() is true, then
    // the input tied to that descriptor cannot be empty. If any are empty, the
    // user input is considered bad.
    //
    // Returns true if user input is good; false if not.
    //
    
    private boolean getUserInput() 
    {
        
        return false;
        
    }//end of CustomerSettingsWindow::getUserInput
    //--------------------------------------------------------------------------

}//end of class CustomerSettingsWindow
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------