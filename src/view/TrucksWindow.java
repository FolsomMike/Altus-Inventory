/*******************************************************************************
* Title: TrucksWindow.java
* Author: Hunter Schoonover
* Date: 12/04/15
*
* Purpose:
*
* This class is the Trucks window. It retrieves all of the trucks from the 
* database and displays their Ids and names in a table.
* 
* It allows users to perform actions relating to customers, currently:
*       Add Truck, Edit Truck, Delete Truck
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
import model.Truck;
import model.TruckCompany;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class TrucksWindow
//

public class TrucksWindow extends AltusJDialog
{
    
    static private final String actionId = "TrucksWindow";
    static public String getActionId() { return actionId; }
    
    private ArrayList<Truck> trucks;
    private CustomTable table;
    private DefaultTableModel model;
    
    private CreateOrEditTruckWindow createEditWindow;

    //--------------------------------------------------------------------------
    // TrucksWindow::TrucksWindow (constructor)
    //

    public TrucksWindow(MainFrame pMainFrame, MainView pMainView)
    {

        super("Trucks", pMainFrame, pMainView);

    }//end of TrucksWindow::TrucksWindow (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TrucksWindow::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //
    
    @Override
    public void init() 
    {
        
        initializeTable();
        retrieveDataFromDatabase();
        
        super.init();
        
    }// end of TrucksWindow::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TrucksWindow::createGui
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
        
    }// end of TrucksWindow::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TrucksWindow::createButtonsPanel
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
        
        //Create Company button
        panel.add(createButton("Create Truck", "Create a new truck.", 
                                actionId));
        
        panel.add(Tools.createVerticalSpacer(buttonSpacer));
        
        //Edit Company button
        panel.add(createButton("Edit Truck", "Edit information about the "
                                + "selected truck.", actionId));
        
        panel.add(Tools.createVerticalSpacer(buttonSpacer));
        
        //Delete Company button
        panel.add(createButton("Delete Truck", 
                                "Delete the selected truck.",
                                actionId));
        
        
        return panel;
        
    }// end of TrucksWindow::createButtonsPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TrucksWindow::deleteTruck
    //
    // Deletes the truck selected in the table.
    //
    
    public void deleteTruck() 
    {
       
       getDatabase().deleteTruck(getSelectedTruck());
       
       //reload the data from the database now that we've changed stuff
       retrieveDataFromDatabase();
        
    }// end of TrucksWindow::deleteTruck
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TrucksWindow::displayCreateTruckWindow
    //
    // Displays the Create Truck window and sets it as the active dialog.
    //
    
    public void displayCreateTruckWindow() 
    {
        
        
        createEditWindow = new CreateOrEditTruckWindow(this, getMainFrame(), 
                                                                getMainView());
        createEditWindow.init();
        
    }// end of TrucksWindow::displayCreateTruckWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TrucksWindow::displayEditTruckWindow
    //
    // Displays the Edit Truck window and sets it as the active dialog.
    //
    
    public void displayEditTruckWindow() 
    {
        
        
        createEditWindow = new CreateOrEditTruckWindow( getSelectedTruck(), 
                                                        this, 
                                                        getMainFrame(),
                                                        getMainView());
        createEditWindow.init();
        
    }// end of TrucksWindow::displayEditTruckWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TrucksWindow::getSelectedTruck
    //
    // Gets and returns the truck that is selected in the table.
    //
    
    private Truck getSelectedTruck() 
    {
        
        int row = table.getSelectedRow();
        String id = (String)table.getValueAt(row, 0);
        
        //look for the truck that matches the selected id
        for (Truck t : trucks) {
            if (id.equals(t.getId())) { return t; }
        }
        
        //no truck was found so return null
        return null;
        
    }// end of TrucksWindow::getSelectedTruck
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TrucksWindow::initializeTable
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
        
    }// end of TrucksWindow::initializeTable
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TrucksWindow::retrieveDataFromDatabase
    //
    // Retrieves the trucks from the MySQL database and puts the ids and names
    // in the table model
    //
    
    public void retrieveDataFromDatabase() 
    {
        
        //get trucks from the database
        trucks = getDatabase().getTrucks();
        
        String[] columnNames = {"Id", "Truck"};
        
        String[][] data = new String[trucks.size()][];
        
        //extract ids and names from trucks
        for (int i=0; i<data.length; i++) {
            data[i] = new String[]{trucks.get(i).getId(), 
                                        trucks.get(i).getName()};
        }
        
        model.setDataVector(data, columnNames);
        
        //now that we've put data in the model, we can set some table settings
        
        //select the first row of the table
        if (table.getRowCount() > 0) { table.setRowSelectionInterval(0, 0); }
        
        //set the widths of the columns
        TableColumnModel m =  table.getColumnModel();
        m.getColumn(0).setPreferredWidth(100);
        m.getColumn(1).setPreferredWidth(300);
        
    }// end of TrucksWindow::retrieveDataFromDatabase
    //--------------------------------------------------------------------------

}//end of class TrucksWindow
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class CreateOrEditTruckWindow
//
// This class is used for a window that serves as both the Create Truck and Edit
// Truck windows, depending on the constructor used.
//
// To make the class function as the Create Truck window, use the first 
// constructor; to make it function as the Edit Truck window, use the second 
// constructor.
//
//

class CreateOrEditTruckWindow extends AltusJDialog
{
    
    static private final String actionId = "CreateOrEditTruckWindow";
    static public String getActionId() { return actionId; }
    
    private final int mode;
    private final int mode_create   = 1;
    private final int mode_edit     = 2;
    
    private final Truck truck;
    private final TrucksWindow trucksWindow;
    
    private final String confirmButtonText;
    private final String confirmButtonToolTip;
    
    //--------------------------------------------------------------------------
    // CreateOrEditTruckWindow::CreateOrEditTruckWindow (constructor)
    //
    // Constructor for using the Create Truck window.
    //

    public CreateOrEditTruckWindow (    TrucksWindow pTrucksWindow, 
                                        MainFrame pMainFrame, 
                                        MainView pMainView)
    {

        super("Create Truck", pTrucksWindow, pMainFrame, pMainView);
        
        mode                    = mode_create;
        truck                   = new Truck("", "", "", "");
        trucksWindow            = pTrucksWindow;
        
        //confirm button text and tool tip
        confirmButtonText       = "Create";
        confirmButtonToolTip    = "Create the new truck.";

    }//end of CreateOrEditTruckWindow::CreateOrEditTruckWindow (constructor)
    //-------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CreateOrEditTruckWindow::CreateOrEditTruckWindow (constructor)
    //
    // Constructor for using the Edit Truck Company window.
    //

    public CreateOrEditTruckWindow( Truck pTruck,
                                    TrucksWindow pTrucksWindow, 
                                    MainFrame pMainFrame, 
                                    MainView pMainView)
    {

        super("Edit Truck", pTrucksWindow, pMainFrame, pMainView);
        
        mode                    = mode_edit;
        truck                   = pTruck;
        trucksWindow            = pTrucksWindow;
        
        //confirm button text and tool tip
        confirmButtonText       = "Apply";
        confirmButtonToolTip    = "Apply the changes made.";

    }//end of CreateOrEditTruckWindow::CreateOrEditTruckWindow (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CreateOrEditTruckWindow::createGui
    //
    // Creates the gui for the window.
    //
    
    @Override
    protected void createGui() 
    {
        
        //add the Id and Name row
        addToMainPanel(createRow(new JPanel[] {
            createIdPanel(truck.getId()),
            createNamePanel(truck.getName())
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Cancel/Confirm panel
        addToMainPanel(createCancelConfirmPanel(confirmButtonText, 
                                                    confirmButtonToolTip));

    }// end of CreateOrEditTruckWindow::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CreateOrEditTruckWindow::confirm
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
            getDatabase().insertTruck(truck);
        }
        
        //update the truck if we are in edit mode
        else if (mode == mode_edit) { 
            getDatabase().updateTruck(truck);
        }
        
        //tell the Trucks window to reload its data from the database since we
        //changed some stuff there
        trucksWindow.retrieveDataFromDatabase();
        
        //dispose of the window and its resources
        dispose();

    }// end of CreateOrEditTruckWindow::confirm
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CreateOrEditTruckWindow::checkUserInput
    //
    // Checks the user input for errors.
    //
    // Returns true if no errors; false if there are.
    //

    private boolean checkUserInput()
    {
        
        //Check Id input
        if (getIdInput().isEmpty()) {
            displayError("Please give the truck an Id.");
            return false;
        }
        else if (!getIdInput().equals(truck.getId()) && 
                    getDatabase().checkForValue(getIdInput(), "TRUCKS", "id"))
        {
            displayError("The Id entered already exists in the database.");
            return false;
        }
        
        //Check Name input
        if (getNameInput().isEmpty()) {
            displayError("Please give the truck a name.");
            return false;
        }
        else if (!getNameInput().equals(truck.getName()) && 
                    getDatabase().checkForValue(getIdInput(), "TRUCKS", "name"))
        {
            displayError("The name entered already exists in the database.");
            return false;
        }
        
        //we made it here, so there were no errors
        return true;

    }// end of CreateOrEditTruckWindow::checkUserInput
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CreateOrEditTruckWindow::getUserInput
    //
    // Gets the user input from the text fields and stores it in the Truck.
    //

    private void getUserInput()
    {

        truck.setId(getIdInput());
        
        truck.setName(getNameInput());

    }// end of CreateOrEditTruckWindow::getUserInput
    //--------------------------------------------------------------------------
    
}//end of class CreateOrEditTruckWindow
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------