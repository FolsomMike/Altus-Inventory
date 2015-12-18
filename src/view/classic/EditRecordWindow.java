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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import shared.Descriptor;
import shared.Record;
import shared.Table;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class EditRecordWindow
//

public class EditRecordWindow extends AltusJDialog implements CommandHandler
{
    
    private final String recordType;
    private final Table table;
    private final String recordSkoonieKey;
    private Record record;
    
    private final Map<String, JTextField> inputs = new HashMap<>();
    
    JScrollPane inputsScrollPane;
    
    private final int maxHeight = 350;
    
    private final int inputPanelWidth = 200;

    //--------------------------------------------------------------------------
    // EditRecordWindow::EditRecordWindow (constructor)
    //

    public EditRecordWindow(String pTitle, Window pParent, 
                                ActionListener pListener, String pRecordType,
                                Table pTable, String pRecordSkoonieKey)
    {

        super(pTitle, pParent, pListener);
        
        recordType = pRecordType;
        
        table = pTable;
        
        recordSkoonieKey = pRecordSkoonieKey;

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
        
        //get the record from the table, or create a new one
        if (recordSkoonieKey != null) { 
            record = table.getRecord(recordSkoonieKey);
        }
        else {
            record = new Record();
            table.addRecord(record);
        }
        
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
        
        //add the input panels for the descriptors to the gui
        addToMainPanel(createDescriptorInputsPanel());
        
        //add the cancel confirm panel
        addToMainPanel(createCancelConfirmPanel());
        
    }// end of EditRecordWindow::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditRecordWindow::handleCommand
    //
    // Performs different actions depending on pCommand.
    //
    
    @Override
    public void handleCommand(Command pCommand) 
    {
        
        switch (pCommand.getMessage()) {
            
            case "EditRecordWindow -- cancel":
                dispose();
                break;
                
            case "EditRecordWindow -- confirm":
                confirm();
                break;
                
        }
        
    }//end of EditRecordWindow::handleCommand
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditRecordWindow::confirm
    //
    // Confirms the edit of the record. //WIP HSS// -- better description
    //
    
    private void confirm() 
    {
        
        //return if user input is bad
        if (!getUserInput()) { return; }
        
        String message;
        
        //editing existing record -- need to update, not add
        if (recordSkoonieKey != null ) { message = "update "; }
        //adding new record -- need to add, not update
        else { message = "add "; record.setSkoonieKey("new"); }
        
        //add the record type to the message
        message += recordType;
        
        //create the command
        Command command = new Command(message);
        
        //put the table into the command
        command.put("table", table);
        
        //put the skoonie key of the record into the command
        command.put("record key", record.getSkoonieKey());
        
        command.perform();
        
        //dispose of this the window
        dispose();
        
    }//end of EditRecordWindow::confirm
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditRecordWindow::createCancelConfirmPanel
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
                                        "EditRecordWindow -- cancel"));
        
        //horizontal spacer
        buttonsPanel.add(Tools.createHorizontalSpacer(20));
        
        //add the OK button
        buttonsPanel.add(createButton("OK", "" , "EditRecordWindow -- confirm"));
        
        //horizontal center 
        //-- only works to "push" to the center if another glue is used
        buttonsPanel.add(Box.createHorizontalGlue());
        
        panel.add(buttonsPanel);

        return panel;

    }// end of EditRecordWindow::createCancelConfirmPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditRecordWindow::createDescriptorInputsPanel
    //
    // Creates and returns a scroll pane containing all of the input panels for
    // the descriptors.
    //

    private JScrollPane createDescriptorInputsPanel()
    {

        //create a panel to go inside of a scroll pane
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        //get the descriptors from the table
        List<Descriptor> descriptors = table.getDescriptors();
        int count = descriptors.size();
        
        int panelsPerRow = 3;
        int panelSpacer = 10;
        
        List<JPanel> row = new ArrayList<>();
        for (int i=0; i<count; i++) {
            
            row.add(createInputPanel(descriptors.get(i)));
            
            //if we've reached the number of panels
            //allowed per row, or the end of the
            //descriptors, create a row panel from
            //the input panels in the row list, and
            //empty the list to start a new row
            if (row.size()>=panelsPerRow || i>=(count-1)) {
                panel.add(createRow(row, panelSpacer));
                panel.add(Tools.createVerticalSpacer(10));
                row.clear();
            }
            
        }
        
        //put the panel into a scroll pane and add it to the inputspanel
        inputsScrollPane = new JScrollPane(panel);
        inputsScrollPane.setAlignmentX(LEFT_ALIGNMENT);
        inputsScrollPane.setAlignmentY(TOP_ALIGNMENT);
        inputsScrollPane.setBorder(null);
        
        int policy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
        inputsScrollPane.setVerticalScrollBarPolicy(policy);
        
        return inputsScrollPane;

    }// end of EditRecordWindow::createDescriptorInputsPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditRecordWindow::createInputPanel
    //
    // Creates and returns an input panel containing a JLabel and a JTextField
    // using the information in pDescriptor.
    //
    // The JTextField contained in this input panel is stored in the inputs
    // map, using pDescriptor.getSkoonie() as the key.
    //

    private JPanel createInputPanel(Descriptor pDescriptor)
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel(pDescriptor.getName());
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        Tools.setSizes(field, inputPanelWidth, 25);
        
        String descKey = pDescriptor.getSkoonieKey();
        //set the text of the input field to the text for the record, if one
        //is specified
        if (recordSkoonieKey != null) {
            field.setText(table.getRecord(recordSkoonieKey).getValue(descKey));
        }
        //store the input field
        inputs.put(descKey, field);
        
        //add the field to the panel
        panel.add(field);

        return panel;

    }// end of EditRecordWindow::createInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::createRow
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

    }// end of AltusJDialog::createRow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditRecordWindow::getUserInput
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
        
        for (Descriptor d : table.getDescriptors()) {
            String descKey = d.getSkoonieKey();
            String input = inputs.get(descKey).getText();
            
            boolean empty = input.isEmpty();
            
            //if input is empty but can't be, add desriptor name to list
            if (d.getRequired() && empty) { badInputs.add(d.getName()); }
            
            //only add the value if its not empty
            if (!empty) { record.addValue(descKey, input);} //WIP HSS// -- needs to add if value has changed
        }
        
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
        
    }//end of EditRecordWindow::getUserInput
    //--------------------------------------------------------------------------

}//end of class EditRecordWindow
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------