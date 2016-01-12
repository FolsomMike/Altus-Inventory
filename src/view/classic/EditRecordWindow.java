/*******************************************************************************
* Title: EditRecordWindow.java
* Author: Hunter Schoonover
* Date: 12/14/15
*
* Purpose:
*
* This class is the Edit Record window, which can be used to edit or create a 
* Record.
* 
* Create:
*   Displays inputs for each of the descriptors in the Table, allowing users to
*       define a new Record.
*   To use the class to create a new Record, then pass null in for the Skoonie
*       Key of the record.
* 
* Edit:
*   Displays the information stored in the Record passed in upon construction 
*       and allows the user to edit it.
*   To use the class to edit a Record, then pass in the Skoonie Key of the
*       Record in the Table -- both passed in upon construction.
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
    
    private final RecordsWindow recordsWindow;
    private final RecordWindowInfo info;
    
    private final Table table;
    private final String recordSkoonieKey;
    
    private Record record;
    
    private final List<DescriptorInput> inputs = new ArrayList<>();
    
    JScrollPane inputsScrollPane;
    
    private final int maxHeight = 350;
    
    private final int inputPanelWidth = 200;

    //--------------------------------------------------------------------------
    // EditRecordWindow::EditRecordWindow (constructor)
    //

    public EditRecordWindow(String pTitle, Window pParent, 
                                ActionListener pListener, 
                                RecordsWindow pRecordsWindow,
                                RecordWindowInfo pInfo, Table pTable, 
                                String pRecordSkoonieKey)
    {

        super(pTitle, pParent, pListener);
        
        recordsWindow = pRecordsWindow;
        
        info = pInfo;
        
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
        if (recordSkoonieKey != null) { //edit mode
            record = table.getRecord(recordSkoonieKey);
        }
        else { //add new mode
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
    // Confirms that the user wants to use their inputs to either create or edit
    // a Record, depending on whether or not the Skoonie Key passed in upon 
    // construction is null or not.
    //
    // If the user input is bad, a message is displayed to the user and this 
    // function is returned.
    //
    
    private void confirm() 
    {
        
        //return if user input is bad
        if (!getUserInput()) { return; }
        
        String message;
        
        //adding new record
        if (recordSkoonieKey == null ) { 
            message = info.getAddCommandMessage(); 
            record.setSkoonieKey("new");
        }
        //editing existing record
        else { 
            message = info.getEditCommandMessage();
        }
        
        //create the command
        Command command = new Command(message);
        
        //put the table into the command
        command.put(Command.TABLE, table);
        
        //put the skoonie key of the record into the command
        command.put(Command.RECORD_KEY, record.getSkoonieKey());
        
        command.perform();
        
        //tell the records window that he needs to show he's loading
        recordsWindow.setLoading(true);
        
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
        
        //create a DescriptorInput object for each of the Descriptors
        for (Descriptor d : table.getDescriptors()) {
            String descKey = d.getSkoonieKey();
            String val = "";
            if (recordSkoonieKey != null ) {
                Record r = table.getRecord(recordSkoonieKey);
                val = (val=r.getValue(descKey))!=null ? val : "";
            }
        
            DescriptorInput input = new DescriptorInput(d, val);
            input.init();
            inputs.add(input);
        }
        
        //add the inputs to the GUI
        int count = inputs.size();
        int panelsPerRow = 3;
        int panelSpacer = 10;
        
        List<JPanel> row = new ArrayList<>();
        for (int i=0; i<count; i++) {
            
            row.add(inputs.get(i));
            
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
    // EditRecordWindow::createRow
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

    }// end of EditRecordWindow::createRow
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
        
        for (DescriptorInput descriptorInput : inputs) {
            
            Descriptor d = descriptorInput.getDescriptor();
            String descKey = d.getSkoonieKey();
            String newValue = descriptorInput.getUserInput();
            boolean empty = newValue.isEmpty();
            
            //if input is empty but can't be, add desriptor name to bad list
            if (d.getRequired() && empty) { badInputs.add(d.getName()); }
            
            //only add value if its not empty or the empty value replaces a
            //previous value
            String oldValue = record.getValue(descKey);
            if (!empty || (oldValue!=null && !oldValue.equals(newValue))) { 
                record.addValue(descKey, newValue);
            }
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