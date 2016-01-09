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
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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
    
    private final RecordWindowInfo info;
    
    private CustomTable table;
    private DefaultTableModel model;
    
    private Table records;
    
    private CommandHandler downStream;
    
    private final List<JButton> buttons = new ArrayList<>();
    
    private Image loadingImage;
    private boolean loading = false;

    //--------------------------------------------------------------------------
    // RecordsWindow::RecordsWindow (constructor)
    //

    public RecordsWindow(String pTitle, Window pParent, 
                            ActionListener pListener, RecordWindowInfo pInfo)
    {

        super(pTitle, pParent, pListener);
        
        info = pInfo;

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
        
        //get the loading image from file
        String path = "src/view/images/loading.gif";
        loadingImage = Toolkit.getDefaultToolkit().createImage(path);
        
        //set up the table model
        setupTableModel();
        
        //perform a command to get the records
        (new Command(info.getGetCommandMessage())).perform();
        setLoading(true);
        
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
        
        //put the table inside of a scroll pane
        setUpScrollPane();
        
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
            
        if (msg.equals(info.getTypePluralCommandMessage())) {
            displayRecords((Table)pCommand.get(Command.TABLE));
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
        
        String recordName = info.getRecordNameSingular();
        
        //Add Record button
        JButton addButton = createButton( "Add", 
                                "Add a new " + recordName + ".", 
                                "display add record window");
        buttons.add(addButton);
        panel.add(addButton);
        
        panel.add(Tools.createVerticalSpacer(buttonSpacer));
        
        //Edit Record button
        JButton editButton = createButton( "Edit", 
                                "Edit information about the selected " 
                                    + recordName + ".", 
                                "edit selected record");
        buttons.add(editButton);
        panel.add(editButton);
        
        panel.add(Tools.createVerticalSpacer(buttonSpacer));
        
        //Delete Record button
        JButton deleteButton = createButton("Delete", 
                                    "Delete the selected "+recordName+".", 
                                    "delete selected record");
        buttons.add(deleteButton);
        panel.add(deleteButton);
        
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
        downStream = new EditRecordWindow(info.getEditRecordWindowTitle(), this, 
                                            getActionListener(), this, info,
                                            records, key);
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
        
        String recordName = info.getRecordNameSingular();
        
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
        
        setLoading(true);
        Command command = new Command(info.getDeleteCommandMessage());
        String key = rec.getSkoonieKey();
        command.put(Command.SKOONIE_KEY, key);
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
        
        downStream = new EditRecordWindow(info.getAddRecordWindowTitle(), this, 
                                            getActionListener(), this, info,
                                            records, null);
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
        
        //we are about to add the rows, so tell it we're done loading
        setLoading(false);
        
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
            JOptionPane.showMessageDialog(this, "Nothing is selected.");
        }
        else { rec = records.getRecords().get(row); }
        
        return rec;
        
    }// end of RecordsWindow::getSelectedRecord
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordsWindow::setLoading
    //
    // Sets the window to loading or not loading, depending on pLoading
    //
    
    public void setLoading(boolean pLoading) 
    {
        
        loading = pLoading;
        
        for(JButton b : buttons) { b.setEnabled(!loading); }
        
    }// end of RecordsWindow::setLoading
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RecordsWindow::setUpScrollPane
    //
    // Sets up the scroll pane, putting the table inside of it. The 
    // paintComponent() function of the scroll pane is overridden to paint the
    // loading image when necessary.
    //
    
    private void setUpScrollPane() 
    {
        
        JScrollPane sp = new JScrollPane(table) {
            @Override protected void paintComponent(Graphics g)
            {

                super.paintComponent(g);

                //paint the loading image if necessary
                if (loading) {
                    int x = getWidth()/2 - loadingImage.getWidth(null)/2;
                    int y = getHeight()/2 - loadingImage.getHeight(null)/2;
                    g.drawImage(loadingImage, x, y, this);
                    g.dispose();
                }

            }
        };
        sp.setAlignmentX(LEFT_ALIGNMENT);
        sp.setAlignmentY(TOP_ALIGNMENT);
        Tools.setSizes(sp, 400, 300);
        addToMainPanel(sp);
        
    }// end of RecordsWindow::setupTableModel
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