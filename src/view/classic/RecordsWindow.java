/*******************************************************************************
* Title: RecordsWindow.java
* Author: Hunter Schoonover
* Date: 12/18/15
*
* Purpose:
*
* This class is the Records window. It is used to view, add, edit, and delete 
* different types of records.
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
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import shared.Record;
import shared.Table;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class RecordsWindow
//

public class RecordsWindow extends AltusJDialog implements CommandHandler
{
    
    private final String addRecordWindowTitle;
    private final String editRecordWindowTitle;
    private final String recordName;
    private final String recordNamePlural;
    
    private CustomTable table;
    private DefaultTableModel model;
    
    private Table records;
    
    private CommandHandler downStream;

    //--------------------------------------------------------------------------
    // RecordsWindow::RecordsWindow (constructor)
    //

    public RecordsWindow(String pTitle, String pAddRecordWindowTitle, 
                            String pEditRecordWindowTitle, String pRecordName, 
                            String pRecordNamePlural, Window pParent,
                            ActionListener pListener)
    {

        super(pTitle, pParent, pListener);
        
        addRecordWindowTitle = pAddRecordWindowTitle;
        editRecordWindowTitle = pEditRecordWindowTitle;
        
        recordName = pRecordName;
        recordNamePlural = pRecordNamePlural;

    }//end of RecordsWindow::RecordsWindow (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordsWindow::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //
    
    @Override
    public void init() 
    {
        
        //set up the table model
        setupTableModel();
        
        //perform a command to get the records
        (new Command("get " + recordNamePlural)).perform();
        
        super.init();
        
        //center and make visible
        setVisible();
        
    }// end of RecordsWindow::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordsWindow::createGui
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
        
    }// end of RecordsWindow::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordsWindow::handleCommand
    //
    // Performs different actions depending on pCommand.
    //
    
    @Override
    public void handleCommand(Command pCommand) 
    {
        
        String msg = pCommand.getMessage();
        
        if (msg.equals("display " + recordNamePlural)) {
            displayRecords((Table)pCommand.get("table"));
        }
        else if (msg.equals("display add record window")) {
            displayAddRecordWindow();
        }
        else if (msg.equals("edit selected record")) {
            editSelectedRecord();
        }
        else if (msg.equals("delete selected record")) {
            deleteSelectedRecord();
        }
        
        if (downStream != null) { downStream.handleCommand(pCommand); }
        
    }//end of RecordsWindow::handleCommand
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordsWindow::createButtonsPanel
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
        
        //Add Record button
        panel.add(createButton( "Add", 
                                "Add a new " + recordName + ".", 
                                "display add record window"));
        
        panel.add(Tools.createVerticalSpacer(buttonSpacer));
        
        //Edit Record button
        panel.add(createButton( "Edit", 
                                "Edit information about the selected " 
                                    + recordName + ".", 
                                "edit selected record"));
        
        panel.add(Tools.createVerticalSpacer(buttonSpacer));
        
        //Delete Record button
        panel.add(createButton("Delete", "Delete the selected "+recordName+".", 
                                "delete selected record"));
        
        return panel;
        
    }// end of RecordsWindow::createButtonsPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordsWindow::editSelectedRecord
    //
    // Gets the selected record skoonie key from the table and passes it on to
    // the EditRecordWindow.
    //
    
    private void editSelectedRecord() 
    {
        
        Record rec;
        
        //return if there was a problem when getting the selected record
        if ((rec=getSelectedRecord())==null) { return; }
        
        String key = rec.getSkoonieKey();
        downStream = new EditRecordWindow(editRecordWindowTitle, this, 
                                                getActionListener(),
                                                recordName, records, key);
        ((EditRecordWindow)downStream).init();
        
    }// end of RecordsWindow::editSelectedRecord
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordsWindow::deleteSelectedRecord
    //
    // Deletes the record selected in the table, if verified by the user.
    //
    
    private void deleteSelectedRecord() 
    {
        
        Record rec;
        
        //return if there was a problem when getting the selected record
        if ((rec=getSelectedRecord())==null) { return; }
        
        String msg = "Are you sure you want to delete " + recordName + " \"" 
                        + rec.getValue(records.getDescriptorKeyByName("Name"))
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
        
        
        Command command = new Command("delete " + recordName);
        String key = rec.getSkoonieKey();
        command.put("record key", key);
        command.perform();
        
    }// end of RecordsWindow::deleteSelectedRecord
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordsWindow::displayAddRecordWindow
    //
    // Displays the Add Record window.
    //
    
    private void displayAddRecordWindow() 
    {
        
        downStream = new EditRecordWindow(addRecordWindowTitle, this, 
                                                getActionListener(),
                                                recordName, records, null);
        ((EditRecordWindow)downStream).init();
        
    }// end of RecordsWindow::displayAddRecordWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordsWindow::displayRecords
    //
    // Adds the records in pRecords to the table.
    //
    
    private void displayRecords(Table pRecords) 
    {
        
        //store the records
        records = pRecords;
        
        //remove all of the data already in the model
        int rowCount = model.getRowCount();
        //Remove rows one by one from the end of the table
        for (int i=rowCount-1; i>=0; i--) { model.removeRow(i); }
        
        //everything sent here should ALWAYS have Id and Name
        String idKey = records.getDescriptorKeyByName("Id");
        String nameKey = records.getDescriptorKeyByName("Name");
        
        //add the ids and names of the records to the table
        for (Record rec : records.getRecords()) {
            String id = rec.getValue(idKey);
            String name = rec.getValue(nameKey);
            model.addRow( new String[] { id, name });
        }
        
    }// end of RecordsWindow::displayRecords
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordsWindow::getSelectedRecord
    //
    // Get the selected record from the table, or display a message and return
    // null if one is not selected.
    //
    
    private Record getSelectedRecord() 
    {
        Record rec = null;
        
        int row = table.getSelectedRow();
        if (row==-1) {
            JOptionPane.showMessageDialog(this, "No "+recordName+" selected.");
        }
        else { rec = records.getRecords().get(row); }
        
        return rec;
        
    }// end of RecordsWindow::getSelectedRecord
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordsWindow::setupTableModel
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
        
    }// end of RecordsWindow::setupTableModel
    //--------------------------------------------------------------------------

}//end of class RecordsWindow
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------