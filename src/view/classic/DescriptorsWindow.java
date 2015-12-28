/*******************************************************************************
* Title: DescriptorsWindow.java
* Author: Hunter Schoonover
* Date: 12/19/15
*
* Purpose:
*
* This class is the Descriptors window. It is used to view, add, edit, and 
* delete descriptors.
*
*/

//------------------------------------------------------------------------------

package view.classic;

//------------------------------------------------------------------------------

import command.Command;
import command.CommandHandler;
import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import shared.Descriptor;
import shared.Record;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class DescriptorsWindow
//

public class DescriptorsWindow extends AltusJDialog implements CommandHandler
{
    
    private final String type; // E.g. customer, batch, etc.
    private final String addWindowTitle;
    private final String editWindowTitle;
    
    private final String getDescriptorsCommand;
    
    private CustomTable table;
    private DefaultTableModel model;
    
    private final Map<Integer, Descriptor> descriptors = new HashMap<>();
    
    private CommandHandler downStream;

    //--------------------------------------------------------------------------
    // DescriptorsWindow::DescriptorsWindow (constructor)
    //

    public DescriptorsWindow(String pTitle, Window pParent,
                                ActionListener pListener, String pType,
                                String pAddWindowTitle, 
                                String pEditWindowTitle)
    {

        super(pTitle, pParent, pListener);
        
        addWindowTitle = pAddWindowTitle;
        editWindowTitle = pEditWindowTitle;
        
        type = pType;
        getDescriptorsCommand = "get " + pType + " descriptors";

    }//end of DescriptorsWindow::DescriptorsWindow (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DescriptorsWindow::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //
    
    @Override
    public void init() 
    {
        
        //set up the table model
        setupTableModel();
        
        //perform a command to get the records
        (new Command(getDescriptorsCommand)).perform();
        
        super.init();
        
        //center and make visible
        setVisible();
        
    }// end of DescriptorsWindow::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DescriptorsWindow::createGui
    //
    // Creates and adds the GUI to the window.
    //
    
    @Override
    protected void createGui() 
    {
        
        //set the main panel layout to add components left to right
        setMainPanelLayout(BoxLayout.X_AXIS);
        
        //set up the table
        table = new CustomTable(model);
        table.init();
        
        //put the table in a scroll pane and add it to the main panel
        JScrollPane sp = new JScrollPane(table);
        sp.setAlignmentX(LEFT_ALIGNMENT);
        sp.setAlignmentY(TOP_ALIGNMENT);
        Tools.setSizes(sp, 400, 300);
        addToMainPanel(sp);
        
        //horizontal spacer
        addToMainPanel(Tools.createHorizontalSpacer(10));
        
        //add the buttons panel
        addToMainPanel(createButtonsPanel());
        
    }// end of DescriptorsWindow::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DescriptorsWindow::handleCommand
    //
    // Performs different actions depending on pCommand.
    //
    
    @Override
    public void handleCommand(Command pCommand) 
    {
        
        String msg = pCommand.getMessage();
        
        switch (msg) {
            case "display descriptors":
                //NOTE: Last time I looked online, there was no way to get rid of
                //      the "unchecked or unsafe operations" error
                displayDescriptors((List<Descriptor>)pCommand.get("descriptors"));
                break;
                
            case "display add window":
                displayAddWindow();
                break;
                
            case "edit selected descriptor":
                editSelectedDescriptor();
                break;
                
            case "delete selected descriptor":
                deleteSelectedDescriptor();
                break;
        }
        
        if (downStream != null) { downStream.handleCommand(pCommand); }
        
    }//end of DescriptorsWindow::handleCommand
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DescriptorsWindow::createButtonsPanel
    //
    // Creates and returns a JPanel containing all of the buttons for the
    // window.
    //
    
    private JPanel createButtonsPanel() 
    {
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        int buttonSpacer = 20;
        
        //Add button
        panel.add(createButton( "Add", 
                                "Add a new descriptor.", 
                                "display add window"));
        
        panel.add(Tools.createVerticalSpacer(buttonSpacer));
        
        //Edit button
        panel.add(createButton( "Edit", 
                                "Edit information about the selected "
                                    + "descriptor.", 
                                "edit selected descriptor"));
        
        panel.add(Tools.createVerticalSpacer(buttonSpacer));
        
        //Delete button
        panel.add(createButton( "Delete", "Delete the selected descriptor.", 
                                "delete selected descriptor"));
        
        panel.add(Tools.createVerticalSpacer(buttonSpacer+30));
        
        //Move Up button
        panel.add(createButton( "Move Up", "Move the selected descriptor up.", 
                                "move selected descriptor up"));
        
        panel.add(Tools.createVerticalSpacer(buttonSpacer));
        
        //Move Down button
        panel.add(createButton( "Move Down", 
                                "Move the selected descriptor down.", 
                                "move selected descriptor down"));
        
        return panel;
        
    }// end of DescriptorsWindow::createButtonsPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DescriptorsWindow::editSelectedDescriptor
    //
    // Gets the selected descriptor from the table and passes it on to the 
    // EditDescriptorWindow for editing.
    //
    
    private void editSelectedDescriptor() 
    {
        
        Descriptor desc;
        
        //return if there was a problem when getting the selected descriptor
        if ((desc=getSelectedDescriptor())==null) { return; }
        
        downStream = new EditDescriptorWindow(editWindowTitle, this, 
                                                getActionListener(), type, 
                                                model.getRowCount(), desc);
        ((EditDescriptorWindow)downStream).init();
        
    }// end of DescriptorsWindow::editSelectedRecord
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DescriptorsWindow::deleteSelectedDescriptor
    //
    // Deletes the descriptor selected in the table, if verified by the user.
    //
    
    private void deleteSelectedDescriptor() 
    {
        
        //WIP HSS// -- do stuff
        Descriptor descriptor;
        
        //return if there was a problem when getting the selected descriptor
        if ((descriptor=getSelectedDescriptor())==null) { return; }
        
        String msg = "Are you sure you want to delete \"" + descriptor.getName()
                        + "\"? This cannot be undone.";

        //verify the delete action
        
        //yes and no are actually backwards -- done for display purposes
        int no = JOptionPane.OK_OPTION;
        Object[] options = { "No", "Yes" };
        int verify = JOptionPane.showOptionDialog(this, msg, "Verify Delete", 
                                        JOptionPane.DEFAULT_OPTION, 
                                        JOptionPane.QUESTION_MESSAGE, 
                                        null, options, null);
        
        //return if the user closed window or selected no
        if (verify == no || verify == JOptionPane.CLOSED_OPTION) { return; }
        
        
        Command command = new Command("delete " + type + " descriptor");
        command.put("descriptor", descriptor);
        command.perform();
        
    }// end of DescriptorsWindow::deleteSelectedDescriptor
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DescriptorsWindow::displayAddrWindow
    //
    // Displays the Add Descriptor window.
    //
    
    private void displayAddWindow() 
    {
        
        downStream = new EditDescriptorWindow(addWindowTitle, this, 
                                                getActionListener(), type,
                                                model.getRowCount());
        ((EditDescriptorWindow)downStream).init();
        
    }// end of DescriptorsWindow::displayAddWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DescriptorsWindow::displayDesciptors
    //
    // Adds the records in pRecords to the table.
    //
    
    private void displayDescriptors(List<Descriptor> pDescriptors) 
    {
        
        //remove all of the data already in the model
        int rowCount = model.getRowCount();
        //Remove rows one by one from the end of the table
        for (int i=rowCount-1; i>=0; i--) { model.removeRow(i); }
        
        //remove all of the data from the descriptors list
        descriptors.clear();
        
        //add the descriptors to the map
        for (Descriptor d : pDescriptors) {
            descriptors.put(Integer.parseInt(d.getOrderNumber()), d);
        }
        
        //add the descriptor names to the table
        for (int i=0; i<descriptors.size(); i++) {
            model.addRow(new String[]{ descriptors.get(i).getName() });
        }
        
        
    }// end of DescriptorsWindow::displayRecords
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DescriptorsWindow::getSelectedDescriptor
    //
    // Get the selected descriptor from the table, or displays a message and 
    // return null if one is not selected.
    //
    
    private Descriptor getSelectedDescriptor() 
    {
        Descriptor desc = null;
        
        int row = table.getSelectedRow();
        if (row==-1) {
            JOptionPane.showMessageDialog(this, "No descriptor selected.");
        }
        else { desc = descriptors.get(row); }
        
        return desc;
        
    }// end of DescriptorsWindow::getSelectedDescriptor
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DescriptorsWindow::setupTableModel
    //
    // Sets up the table model for use.
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
        model.setColumnIdentifiers(new String[]{"Name"});
        
    }// end of DescriptorsWindow::setupTableModel
    //--------------------------------------------------------------------------

}//end of class DescriptorsWindow
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------