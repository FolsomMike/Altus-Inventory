/*******************************************************************************
* Title: TruckDriversWindow.java
* Author: Hunter Schoonover
* Date: 12/06/15
*
* Purpose:
*
* This class is the Truck Drivers window. It retrieves all of the truck drivers
* from the database and displays their Ids and names in a table.
* 
* It allows users to perform actions relating to customers, currently:
*       Add Driver, Edit Driver, Delete Driver
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
import model.TruckDriver;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class TruckDriversWindow
//

public class TruckDriversWindow extends AltusJDialog
{
    
    static private final String actionId = "TruckDriversWindow";
    static public String getActionId() { return actionId; }
    
    private ArrayList<TruckDriver> truckDrivers;
    private CustomTable table;
    private DefaultTableModel model;
    
    private CreateOrEditTruckDriverWindow createEditWindow;

    //--------------------------------------------------------------------------
    // TruckDriversWindow::TruckDriversWindow (constructor)
    //

    public TruckDriversWindow(MainFrame pMainFrame, MainView pMainView)
    {

        super("Truck Drivers", pMainFrame, pMainView);

    }//end of TruckDriversWindow::TruckDriversWindow (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TruckDriversWindow::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //
    
    @Override
    public void init() 
    {
        
        initializeTable();
        retrieveDataFromDatabase();
        
        super.init();
        
    }// end of TruckDriversWindow::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TruckDriversWindow::createGui
    //
    // Creates and adds the GUI to the window.
    //
    
    @Override
    protected void createGui() 
    {
        
        setMainPanelLayout(BoxLayout.X_AXIS);
        
        //put the table in a scroll pane
        JScrollPane sp = new JScrollPane(table);
        sp.setAlignmentX(LEFT_ALIGNMENT);
        sp.setAlignmentY(TOP_ALIGNMENT);
        Tools.setSizes(sp, 400, 300);
        addToMainPanel(sp);
        
        addToMainPanel(Tools.createHorizontalSpacer(10));
        
        addToMainPanel(createButtonsPanel());
        
    }// end of TruckDriversWindow::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TruckDriversWindow::createButtonsPanel
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
        
        //Create Driver button
        panel.add(createButton("Create Driver", "Create a new truck driver.", 
                                actionId));
        
        panel.add(Tools.createVerticalSpacer(buttonSpacer));
        
        //Edit Driver button
        panel.add(createButton("Edit Driver", "Edit information about the "
                                + "selected truck driver.", actionId));
        
        panel.add(Tools.createVerticalSpacer(buttonSpacer));
        
        //Delete Driver button
        panel.add(createButton("Delete Driver", 
                                "Delete the selected truck driver.",
                                actionId));
        
        
        return panel;
        
    }// end of TruckDriversWindow::createButtonsPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TruckDriversWindow::deleteTruckDriver
    //
    // Deletes the truck driver selected in the table.
    //
    
    public void deleteTruckDriver() 
    {
       
       getDatabase().deleteTruckDriver(getSelectedTruckDriver());
       
       //reload the data from the database now that we've changed stuff
       retrieveDataFromDatabase();
        
    }// end of TruckDriversWindow::deleteTruckDriver
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TruckDriversWindow::displayCreateTruckDriverWindow
    //
    // Displays the Create Truck Driver window and sets it as the active dialog.
    //
    
    public void displayCreateTruckDriverWindow() 
    {
        
        
        createEditWindow = new CreateOrEditTruckDriverWindow(   this, 
                                                                getMainFrame(), 
                                                                getMainView());
        createEditWindow.init();
        
    }// end of TruckDriversWindow::displayCreateTruckDriverWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TruckDriversWindow::displayEditTruckDriverWindow
    //
    // Displays the Edit Truck Driver window and sets it as the active dialog.
    //
    
    public void displayEditTruckDriverWindow() 
    {
        
        
        createEditWindow = new CreateOrEditTruckDriverWindow ( 
                                                    getSelectedTruckDriver(), 
                                                    this, 
                                                    getMainFrame(),
                                                    getMainView());
        createEditWindow.init();
        
    }// end of TruckDriversWindow::displayEditTruckDriverWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TruckDriversWindow::getSelectedTruckDriver
    //
    // Gets and returns the truck driver that is selected in the table.
    //
    
    private TruckDriver getSelectedTruckDriver() 
    {
        
        int row = table.getSelectedRow();
        String id = (String)table.getValueAt(row, 0);
        
        //look for the truck driver that matches the selected id
        for (TruckDriver d : truckDrivers) {
            if (id.equals(d.getId())) { return d; }
        }
        
        //no truck driver was found so return null
        return null;
        
    }// end of TruckDriversWindow::getSelectedTruckDriver
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TruckDriversWindow::initializeTable
    //
    // Initializes table for use.
    //
    
    private void initializeTable() 
    {
        
        //initialize model -- allows no editable cells
        model = new DefaultTableModel() {
            @Override public boolean isCellEditable(int pR, int pC) {
                return false;
            }
        };
        
        table = new CustomTable(model);
        table.init();
        
    }// end of TruckDriversWindow::initializeTable
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TruckDriversWindow::retrieveDataFromDatabase
    //
    // Retrieves the truck drivers from the MySQL database and puts the ids and
    // names in the table model
    //
    
    public void retrieveDataFromDatabase() 
    {
        
        //get trucks from the database
        truckDrivers = getDatabase().getTruckDrivers();
        
        String[] columnNames = {"Id", "Driver"};
        
        String[][] data = new String[truckDrivers.size()][];
        
        //extract ids and names from trucks
        for (int i=0; i<data.length; i++) {
            data[i] = new String[]{truckDrivers.get(i).getId(), 
                                        truckDrivers.get(i).getName()};
        }
        
        model.setDataVector(data, columnNames);
        
        //now that we've put data in the model, we can set some table settings
        
        //select the first row of the table
        if (table.getRowCount() > 0) { table.setRowSelectionInterval(0, 0); }
        
        //set the widths of the columns
        TableColumnModel m =  table.getColumnModel();
        m.getColumn(0).setPreferredWidth(100);
        m.getColumn(1).setPreferredWidth(300);
        
    }// end of TruckDriversWindow::retrieveDataFromDatabase
    //--------------------------------------------------------------------------

}//end of class TruckDriversWindow
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class CreateOrEditTruckDriversWindow
//
// This class is used for a window that serves as both the Create Truck Driver
// and Edit Truck Driver windows, depending on the constructor used.
//
// To make the class function as the Create Truck Driver window, use the first 
// constructor; to make it function as the Edit Truck Driver window, use the
// second constructor.
//
//

class CreateOrEditTruckDriverWindow extends AltusJDialog
{
    
    static private final String actionId = "CreateOrEditTruckDriverWindow";
    static public String getActionId() { return actionId; }
    
    private final int mode;
    private final int mode_create   = 1;
    private final int mode_edit     = 2;
    
    private final TruckDriver truckDriver;
    private final TruckDriversWindow trucksWindow;
    
    private final String confirmButtonText;
    private final String confirmButtonToolTip;
    
    //--------------------------------------------------------------------------
    // CreateOrEditTruckDriverWindow::CreateOrEditTruckDriverWindow (constructor)
    //
    // Constructor for using the Create Truck Driver window.
    //

    public CreateOrEditTruckDriverWindow (
                                        TruckDriversWindow pTruckDriversWindow, 
                                        MainFrame pMainFrame, 
                                        MainView pMainView)
    {

        super("Create Truck Driver", pTruckDriversWindow, pMainFrame, pMainView);
        
        mode                    = mode_create;
        truckDriver             = new TruckDriver("", "", "", "");
        trucksWindow            = pTruckDriversWindow;
        
        //confirm button text and tool tip
        confirmButtonText       = "Create";
        confirmButtonToolTip    = "Create the new truck driver.";

    }//end of CreateOrEditTruckDriverWindow::CreateOrEditTruckDriverWindow (constructor)
    //-------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CreateOrEditTruckDriverWindow::CreateOrEditTruckDriverWindow (constructor)
    //
    // Constructor for using the Edit Truck Company window.
    //

    public CreateOrEditTruckDriverWindow( 
                                    TruckDriver pTruckDriver,
                                    TruckDriversWindow pTruckDriversWindow, 
                                    MainFrame pMainFrame, 
                                    MainView pMainView)
    {

        super("Edit Truck", pTruckDriversWindow, pMainFrame, pMainView);
        
        mode                    = mode_edit;
        truckDriver             = pTruckDriver;
        trucksWindow            = pTruckDriversWindow;
        
        //confirm button text and tool tip
        confirmButtonText       = "Apply";
        confirmButtonToolTip    = "Apply the changes made.";

    }//end of CreateOrEditTruckDriverWindow::CreateOrEditTruckDriverWindow (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CreateOrEditTruckDriverWindow::createGui
    //
    // Creates the gui for the window.
    //
    
    @Override
    protected void createGui() 
    {
        
        //add the Id and Name row
        addToMainPanel(createRow(new JPanel[] {
            createIdPanel(truckDriver.getId()),
            createNamePanel(truckDriver.getName())
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Truck Company row
        addToMainPanel(createRow(new JPanel[] {
            createTruckCompanyPanel(truckDriver.getTruckCompanyKey())
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Cancel/Confirm panel
        addToMainPanel(createCancelConfirmPanel(confirmButtonText, 
                                                    confirmButtonToolTip));

    }// end of CreateOrEditTruckDriverWindow::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CreateOrEditTruckDriverWindow::confirm
    //
    // Confirms that the user wants to use the inputs to create a new truck
    // company.
    //

    @Override
    public void confirm()
    {
        
        //check user input for errors
        if (!checkUserInput()) { return; }
        
        //get the user input
        getUserInput();
        
        //insert the truck into the database if we are in create mode
        if (mode == mode_create) {
            getDatabase().insertTruckDriver(truckDriver);
        }
        
        //update the truck if we are in edit mode
        else if (mode == mode_edit) { 
            getDatabase().updateTruckDriver(truckDriver);
        }
        
        //tell the Truck Drivers window to reload its data from the database 
        //since we changed some stuff there
        trucksWindow.retrieveDataFromDatabase();
        
        //dispose of the window and its resources
        dispose();

    }// end of CreateOrEditTruckDriverWindow::confirm
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CreateOrEditTruckDriverWindow::checkUserInput
    //
    // Checks the user input for errors.
    //
    // Returns true if no errors; false if there are.
    //

    private boolean checkUserInput()
    {
        
        //Check Id input
        if (getIdInput().isEmpty()) {
            displayError("Please give the truck driver an Id.");
            return false;
        }
        else if (!getIdInput().equals(truckDriver.getId()) && 
                    getDatabase().checkForValue(getIdInput(), 
                                                    "TRUCK_DRIVERS", "id"))
        {
            displayError("The Id entered already exists in the database.");
            return false;
        }
        
        //Check Name input
        if (getNameInput().isEmpty()) {
            displayError("Please give the truck driver a name.");
            return false;
        }
        else if (!getNameInput().equals(truckDriver.getName()) && 
                    getDatabase().checkForValue(getIdInput(), 
                                                    "TRUCK_DRIVERS", "name"))
        {
            displayError("The name entered already exists in the database.");
            return false;
        }
        
        //Check Truck Company input
        if (!checkTruckCompanyInput()) { 
            displayError("Please select a truck company.");
            return false;
        }
        
        //we made it here, so there were no errors
        return true;

    }// end of CreateOrEditTruckDriverWindow::checkUserInput
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CreateOrEditTruckDriverWindow::getUserInput
    //
    // Gets the user input from the text fields and stores it in the 
    // TruckDriver.
    //

    private void getUserInput()
    {

        truckDriver.setId(getIdInput());
        
        truckDriver.setName(getNameInput());
        
        truckDriver.setTruckCompanyKey(getTruckCompanyInput());

    }// end of CreateOrEditTruckDriverWindow::getUserInput
    //--------------------------------------------------------------------------
    
}//end of class CreateOrEditTruckDriverWindow
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------