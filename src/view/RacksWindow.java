/*******************************************************************************
* Title: RacksWindow.java
* Author: Hunter Schoonover
* Date: 11/29/15
*
* Purpose:
*
* This class is the Racks window. It retrieves all of the racks from the
* database and displays their names and ids in a table.
* 
* It allows users to perform actions relating to racks, currently:
*       Add Rack, Edit Rack, Delete Rack
*
*/

//------------------------------------------------------------------------------

package view;

//------------------------------------------------------------------------------

import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import model.Rack;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class RacksWindow
//

public class RacksWindow extends AltusJDialog
{
    
    static private final String actionId = "RacksWindow";
    static public String getActionId() { return actionId; }
    
    private ArrayList<Rack> racks;
    private CustomTable racksTable;
    private DefaultTableModel model;
    
    private CreateOrEditRackWindow createEditWindow;

    //--------------------------------------------------------------------------
    // RacksWindow::RacksWindow (constructor)
    //

    public RacksWindow(MainFrame pMainFrame, MainView pMainView)
    {

        super("Racks", pMainFrame, pMainView);

    }//end of RacksWindow::RacksWindow (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //
    
    @Override
    public void init() 
    {
        
        initializeRacksTable();
        retrieveRacksFromDatabase();
        
        super.init();
        
    }// end of AltusJDialog::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RacksWindow::createGui
    //
    // Creates and adds the GUI to the window.
    //
    
    @Override
    protected void createGui() 
    {
        
        setMainPanelLayout(BoxLayout.X_AXIS);
        
        //put the table in a scroll pane
        JScrollPane sp = new JScrollPane(racksTable);
        sp.setAlignmentX(LEFT_ALIGNMENT);
        sp.setAlignmentY(TOP_ALIGNMENT);
        Tools.setSizes(sp, 400, 300);
        addToMainPanel(sp);
        
        addToMainPanel(Tools.createHorizontalSpacer(10));
        
        addToMainPanel(createButtonsPanel());
        
    }// end of RacksWindow::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RacksWindow::createButtonsPanel
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
        
        //Create Rack button
        panel.add(createButton("Create Rack", "Create a new rack.", actionId));
        
        panel.add(Tools.createVerticalSpacer(buttonSpacer));
        
        //Edit Rack button
        panel.add(createButton("Edit Rack", 
                                "Edit information about the selected rack.",
                                actionId));
        
        panel.add(Tools.createVerticalSpacer(buttonSpacer));
        
        //Delete Rack button
        panel.add(createButton("Delete Rack", "Delete the selected rack.",
                                actionId));
        
        return panel;
        
    }// end of RacksWindow::createButtonsPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RacksWindow::displayCreateRackWindow
    //
    // Displays the Create Rack window and sets it as the active dialog.
    //
    
    public void displayCreateRackWindow() 
    {
        
        
        createEditWindow = new CreateOrEditRackWindow(this, getMainFrame(), 
                                                            getMainView());
        createEditWindow.init();
        
    }// end of RacksWindow::displayCreateRackWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RacksWindow::displayEditRackWindow
    //
    // Displays the Edit Rack window and sets it as the active dialog.
    //
    
    public void displayEditRackWindow() 
    {
        
        
        createEditWindow = new CreateOrEditRackWindow(
                                            getSelectedRack(), this, 
                                            getMainFrame(), getMainView());
        createEditWindow.init();
        
    }// end of RacksWindow::displayEditRackWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RacksWindow::getSelectedRack
    //
    // Gets and returns the rack that is selected in the table.
    //
    
    private Rack getSelectedRack() 
    {
        
        int row = racksTable.getSelectedRow();
        String id = (String)racksTable.getValueAt(row, 0);
        
        //look for the rack that matches the selected id
        for (Rack r : racks) {
            if (id.equals(r.getId())) { return r; }
        }
        
        //no rack was found so return null
        return null;
        
    }// end of RacksWindow::getSelectedCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RacksWindow::initializeRacksTable
    //
    // Initializes racksTable for use.
    //
    
    private void initializeRacksTable() 
    {
        
        //initialize model -- allows no editable cells
        model = new DefaultTableModel() {
            @Override public boolean isCellEditable(int pR, int pC) {
                return false;
            }
        };
        
        racksTable = new CustomTable(model);
        racksTable.init();
        
    }// end of RacksWindow::initializeRacksTable
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // RacksWindow::retrieveRacksFromDatabase
    //
    // Retrieves the racks from the MySQL database and puts the ids and names in
    // the table model
    //
    
    public void retrieveRacksFromDatabase() 
    {
        
        //get racks from the database
        racks = getDatabase().getRacks();
        
        String[] columnNames = {"Id", "Rack"};
        
        String[][] data = new String[racks.size()][];
        
        //extract ids and names from racks
        for (int i=0; i<data.length; i++) {
            data[i] = new String[]{racks.get(i).getId(),racks.get(i).getName()};
        }
        
        model.setDataVector(data, columnNames);
        
        //now that we've put data in the model, we can set some table settings
        
        //select the first row of the table
        if (racksTable.getRowCount() > 0) { 
            racksTable.setRowSelectionInterval(0, 0);
        }
        
        //set the widths of the columns
        TableColumnModel m = racksTable.getColumnModel();
        m.getColumn(0).setPreferredWidth(100);
        m.getColumn(1).setPreferredWidth(300);
        
    }// end of RacksWindow::retrieveRacksFromDatabase
    //--------------------------------------------------------------------------

}//end of class RacksWindow
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class CreateOrEditRackWindow
//
// This class is used for a window that serves as both the Create Rack and Edit
// Rack windows, depending on the title.
//
// To make the class function as the Create Rack window, use the first
// constructor; to make it function as the Edit Rack Window, use the second
// constructor.
//
//

class CreateOrEditRackWindow extends AltusJDialog
{
    
    static private final String actionId = "CreateOrEditRackWindow";
    static public String getActionId() { return actionId; }
    
    private final int mode;
    private final int mode_create   = 1;
    private final int mode_edit     = 2;
    
    private final Rack rack;
    private final RacksWindow racksWindow;
    
    private final String confirmButtonText;
    private final String confirmButtonToolTip;
    
    //--------------------------------------------------------------------------
    // CreateOrEditRackWindow::CreateOrEditRackWindow (constructor)
    //
    // Constructor for using the Create Customer window.
    //

    public CreateOrEditRackWindow(RacksWindow pRacksWindow, 
                                        MainFrame pMainFrame, 
                                        MainView pMainView)
    {

        super("Create Rack", pRacksWindow, pMainFrame, pMainView);
        
        mode            = mode_create;
        rack            = new Rack("", "", "");
        racksWindow     = pRacksWindow;
        
        //confirm button text and tool tip
        confirmButtonText       = "Create";
        confirmButtonToolTip    = "Create the new rack.";

    }//end of CreateOrEditRackWindow::CreateOrEditRackWindow (constructor)
    //-------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CreateOrEditRackWindow::CreateOrEditRackWindow (constructor)
    //
    // Constructor for using the Edit Customer window.
    //

    public CreateOrEditRackWindow(Rack pRack,
                                        RacksWindow pRacksWindow, 
                                        MainFrame pMainFrame, 
                                        MainView pMainView)
    {

        super("Edit Rack", pRacksWindow, pMainFrame, pMainView);
        
        mode            = mode_edit;
        rack            = pRack;
        racksWindow     = pRacksWindow;
        
        //confirm button text and tool tip
        confirmButtonText       = "Apply";
        confirmButtonToolTip    = "Apply the changes made.";

    }//end of CreateOrEditRackWindow::CreateOrEditRackWindow (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CreateOrEditRackWindow::createGui
    //
    // Creates the gui for the window.
    //
    
    @Override
    protected void createGui() 
    {
        
        //add the Id and Rack Name row
        addToMainPanel(createRow(new JPanel[] {
            createInputPanel("Id", rack.getId(),
                                "The id used for the rack.", 100),
            createInputPanel("Name", rack.getName(),
                                "The rack's name.", 200)
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Cancel/Confirm panel
        addToMainPanel(createCancelConfirmPanel(confirmButtonText, 
                                                    confirmButtonToolTip));

    }// end of CreateOrEditRackWindow::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CreateOrEditRackWindow::confirm
    //
    // Confirms that the user wants to use the inputs to create a new customer.
    //

    @Override
    public void confirm()
    {
        
        //get the user input
        getUserInput();
        
        //insert the rack into the database if we are in create mode
        if (mode == mode_create) { getDatabase().insertRack(rack); }
        
        //update the rack if we are in edit mode
        else if (mode == mode_edit) { getDatabase().updateRack(rack); }
        
        //tell the Racks window to reload its data from the database since
        //we changed some stuff there
        racksWindow.retrieveRacksFromDatabase();
        
        //dispose of the window and its resources
        dispose();

    }// end of CreateOrEditRackWindow::confirm
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CreateOrEditRackWindow::getUserInput
    //
    // Gets the user input from the text fields and stores it in the Rack.
    //

    private void getUserInput()
    {

        rack.setId(getInputFields().get("Id").getText());
        
        rack.setName(getInputFields().get("Name").getText());

    }// end of CreateOrEditRackWindow::getUserInput
    //--------------------------------------------------------------------------
    
}//end of class CreateOrEditRackWindow
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------