/*******************************************************************************
* Title: EditDescriptorWindow.java
* Author: Hunter Schoonover
* Date: 12/19/15
*
* Purpose:
*
* This class is the Edit Descriptor window, which can be used to edit or create
* a Descriptor
* 
* Create:
*   Displays empty inputs for each of the fields required to define a new
*       Descriptor.
*   To use the class to create a new Descriptor, then use the first
*       constructor.
* 
* Edit:
*   Displays the information stored in the Descriptor passed in upon 
*       construction and allows the user to edit it.
*   To use the class to edit a Descriptor, use the second constructor.
* 
* Currently displays inputs/information:
*       Name
*       Required
*
*/

//------------------------------------------------------------------------------

package view.classic;

//------------------------------------------------------------------------------

import command.Command;
import command.CommandHandler;
import java.awt.Color;
import java.awt.Component;
import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import shared.Descriptor;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class EditDescriptorWindow
//

public class EditDescriptorWindow extends AltusJDialog implements CommandHandler
{
    
    private final Descriptor descriptor;
    
    private final int maxHeight = 350;
    
    private JScrollPane inputsScrollPane;
    
    private JTextField nameInput;
    private JCheckBox requiredInput;
    
    private final int inputPanelWidth = 200;

    //--------------------------------------------------------------------------
    // EditDescriptorWindow::EditDescriptorWindow (constructor)
    //
    // Constructor to be used for creation.
    //

    public EditDescriptorWindow(String pTitle, Window pParent, 
                                ActionListener pListener)
    {

        super(pTitle, pParent, pListener);
        
        descriptor = new Descriptor();

    }//end of EditDescriptorWindow::EditDescriptorWindow (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditDescriptorWindow::EditDescriptorWindow (constructor)
    //
    // Constructor to be used for editing.
    //

    public EditDescriptorWindow(String pTitle, Window pParent, 
                                ActionListener pListener, Descriptor pDesc)
    {

        super(pTitle, pParent, pListener);
        
        descriptor = pDesc;

    }//end of EditDescriptorWindow::EditDescriptorWindow (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditDescriptorWindow::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //
    
    @Override
    public void init() 
    {
        
        super.init();
        
        //now that the GUI has been created and packed, we can set the maximum
        //height of the frame
        if (getHeight()>maxHeight){Tools.setSizes(this, getWidth(), maxHeight);}
        
        //the scroll bar was previously set to always so that we can guarantee
        //that it would be included in the width, but we can change it to only
        //appear as needed now
        int policy = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
        inputsScrollPane.setVerticalScrollBarPolicy(policy);
        
        //repack gui components since we changed stuff
        pack();
        
        //center and make visible
        setVisible();
        
    }// end of EditDescriptorWindow::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditDescriptorWindow::createGui
    //
    // Creates and adds the GUI to the window.
    //
    
    @Override
    protected void createGui() 
    {
        
        //set the main panel layout to add components top to bottom
        setMainPanelLayout(BoxLayout.Y_AXIS);
        
        //add the input panels to the gui
        addToMainPanel(createInputsPanel());
        
        //add the cancel confirm panel
        addToMainPanel(createCancelConfirmPanel());
        
    }// end of EditDescriptorWindow::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditDescriptorWindow::handleCommand
    //
    // Performs different actions depending on pCommand.
    //
    
    @Override
    public void handleCommand(Command pCommand) 
    {
        
        switch (pCommand.getMessage()) {
            
            case "EditDescriptorWindow -- cancel":
                dispose();
                break;
                
            case "EditDescriptorWindow -- confirm":
                confirm();
                break;
                
        }
        
    }//end of EditDescriptorWindow::handleCommand
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditDescriptorWindow::confirm
    //
    // Confirms that the user wants to user wants to confirm their inputs to
    // either create or edit a Record, depending on whether or not the Skoonie
    // Key passed in upon construction is null or not.
    //
    // If the user input is bad, a message is displayed to the user and this 
    // function is returned.
    //
    
    private void confirm() 
    {
        
        //return if user input is bad
        if (!getUserInput()) { return; }
        
        String message;
        
        //create the command
        /*Command command = new Command(message);
        
        //put the table into the command
        command.put("table", table);
        
        //put the skoonie key of the record into the command
        command.put("record key", record.getSkoonieKey());
        
        command.perform();*/ //WIP HSS// -- need to perform command
        
        //dispose of this the window
        dispose();
        
    }//end of EditDescriptorWindow::confirm
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditDescriptorWindow::createCancelConfirmPanel
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
                                        "EditDescriptorWindow -- cancel"));
        
        //horizontal spacer
        buttonsPanel.add(Tools.createHorizontalSpacer(20));
        
        //add the OK button
        buttonsPanel.add(createButton("OK", "" , "EditDescriptorWindow -- confirm"));
        
        //horizontal center 
        //-- only works to "push" to the center if another glue is used
        buttonsPanel.add(Box.createHorizontalGlue());
        
        panel.add(buttonsPanel);

        return panel;

    }// end of EditDescriptorWindow::createCancelConfirmPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditDescriptorWindow::createInputsPanel
    //
    // Creates and returns a panel containing all of the input panels for
    // the descriptor.
    //

    private JScrollPane createInputsPanel()
    {

        //create a panel to hold inputs inside of a scroll pane
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        //add the Name input
        panel.add(createNamePanel());
        
        //vertical spacer
        panel.add(Tools.createVerticalSpacer(10));
        
        //add the Required input
        panel.add(createRequiredPanel());
        
        //vertical spacer
        panel.add(Tools.createVerticalSpacer(20));
        
        //put the panel into a scroll pane and add it to the inputspanel
        inputsScrollPane = new JScrollPane(panel);
        inputsScrollPane.setAlignmentX(LEFT_ALIGNMENT);
        inputsScrollPane.setAlignmentY(TOP_ALIGNMENT);
        inputsScrollPane.setBorder(null);
        
        int policy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
        inputsScrollPane.setVerticalScrollBarPolicy(policy);
        
        return inputsScrollPane;

    }// end of EditDescriptorWindow::createInputsPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditDescriptorWindow::createNamePanel
    //
    // Creates and returns the input panel for Name.
    //

    private JPanel createNamePanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        //add the title label to the panel
        JLabel label = new JLabel("Name");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        //add the text field to the panel
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        Tools.setSizes(field, inputPanelWidth, 25);
        field.setText(descriptor.getName());
        panel.add(field);

        return panel;

    }// end of EditDescriptorWindow::createNamePanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditDescriptorWindow::createRequiredPanel
    //
    // Creates and returns the input panel for Required.
    //

    private JPanel createRequiredPanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        //add the check box to the panel
        JCheckBox input = new JCheckBox();
        input.setSelected(descriptor.getRequired());
        panel.add(input);
        
        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(5));
        
        //add the title label to the panel
        JLabel label = new JLabel("Required");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);

        return panel;

    }// end of EditDescriptorWindow::createRequiredPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditDescriptorWindow::getUserInput
    //
    // Gets and checks the user input. If Descriptor.getRequired() is true, then
    // the input tied to that descriptor cannot be empty. If any are empty, the
    // user input is considered bad.
    //
    // Returns true if user input is good; false if not.
    //
    
    private boolean getUserInput() 
    {
        
        boolean good = true;
        
        //names of all the descriptors whose inputs can't be empty, but are
        List<String> badInputs = new ArrayList<>();
        
        //get and check Name input
        String name = nameInput.getText();
        if (name.isEmpty()) { badInputs.add("Name"); }
        else { descriptor.setName(name); }
        
        //get and store the Required input -- no need to check
        descriptor.setRequired(requiredInput.isSelected());
        
        //if there are any bad inputs, display a message to the user and set
        //good to false
        if (!badInputs.isEmpty()) {
            String title = "Inputs Cannot Be Empty";
            
            //create the message string
            String msg = "<html>The following inputs cannot be empty:<ul>";
            for (String s : badInputs) { msg += "<li><b>" +s+ "</b></li>"; }
            msg += "</ul></html>";
            
            //put the message into a JLabel so html can be used
            JLabel label = new JLabel(msg);
            label.setFont(new Font("Times New Roman", Font.PLAIN, 14));
            
            //display the warning to the user
            JOptionPane.showMessageDialog(this, label, title, 
                                            JOptionPane.ERROR_MESSAGE);
            
            //input was bad
            good = false;
        }
        
        return good;
        
    }//end of EditDescriptorWindow::getUserInput
    //--------------------------------------------------------------------------

}//end of class EditDescriptorWindow
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------