/*******************************************************************************
* Title: TruckCompaniesWindow.java
* Author: Hunter Schoonover
* Date: 12/04/15
*
* Purpose:
*
* This class is the Truck Companies window. It retrieves all of the truck
* companies from the database and displays their names and Ids in a table.
* 
* It allows users to perform actions relating to customers, currently:
*       Add Company, Edit Company, Delete Company
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
import model.TruckCompany;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class TruckCompaniesWindow
//

public class TruckCompaniesWindow extends AltusJDialog
{
    
    static private final String actionId = "TruckCompaniesWindow";
    static public String getActionId() { return actionId; }
    
    private ArrayList<TruckCompany> companies;
    private CustomTable table;
    private DefaultTableModel model;
    
    private CreateOrEditTruckCompanyWindow createEditWindow;

    //--------------------------------------------------------------------------
    // TruckCompaniesWindow::TruckCompaniesWindow (constructor)
    //

    public TruckCompaniesWindow(MainFrame pMainFrame, MainView pMainView)
    {

        super("Truck Companies", pMainFrame, pMainView);

    }//end of TruckCompaniesWindow::TruckCompaniesWindow (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TruckCompaniesWindow::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //
    
    @Override
    public void init() 
    {
        
        initializeTable();
        retrieveDataFromDatabase();
        
        super.init();
        
    }// end of TruckCompaniesWindow::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TruckCompaniesWindow::createGui
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
        
    }// end of TruckCompaniesWindow::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TruckCompaniesWindow::createButtonsPanel
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
        panel.add(createButton("Create Company", "Create a new truck company.", 
                                actionId));
        
        panel.add(Tools.createVerticalSpacer(buttonSpacer));
        
        //Edit Company button
        panel.add(createButton("Edit Company", "Edit information about the "
                                + "selected truck company.", actionId));
        
        panel.add(Tools.createVerticalSpacer(buttonSpacer));
        
        //Delete Company button
        panel.add(createButton("Delete Company", 
                                "Delete the selected truck company.",
                                actionId));
        
        
        return panel;
        
    }// end of TruckCompaniesWindow::createButtonsPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TruckCompaniesWindow::deleteTruckCompany
    //
    // Deletes the truck company selected in the table.
    //
    
    public void deleteTruckCompany() 
    {
       
       getDatabase().deleteTruckCompany(getSelectedCompany());
       
       //reload the data from the database now that we've changed stuff
       retrieveDataFromDatabase();
        
    }// end of TruckCompaniesWindow::deleteTruckCompany
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TruckCompaniesWindow::displayCreateTruckCompanyWindow
    //
    // Displays the Create Truck Company window and sets it as the active 
    // dialog.
    //
    
    public void displayCreateTruckCompanyWindow() 
    {
        
        
        createEditWindow = new CreateOrEditTruckCompanyWindow(this, 
                                                                getMainFrame(), 
                                                                getMainView());
        createEditWindow.init();
        
    }// end of TruckCompaniesWindow::displayCreateTruckCompanyWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TruckCompaniesWindow::displayEditTruckCompanyWindow
    //
    // Displays the Edit Customer window and sets it as the active dialog.
    //
    
    public void displayEditTruckCompanyWindow() 
    {
        
        
        createEditWindow = new CreateOrEditTruckCompanyWindow(
                                            getSelectedCompany(), this, 
                                            getMainFrame(), getMainView());
        createEditWindow.init();
        
    }// end of TruckCompaniesWindow::displayEditTruckCompanyWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TruckCompaniesWindow::getSelectedCompany
    //
    // Gets and returns the truck company that is selected in the table.
    //
    
    private TruckCompany getSelectedCompany() 
    {
        
        int row = table.getSelectedRow();
        String id = (String)table.getValueAt(row, 0);
        
        //look for the company that matches the selected id
        for (TruckCompany c : companies) {
            if (id.equals(c.getId())) { return c; }
        }
        
        //no customer was found so return null
        return null;
        
    }// end of TruckCompaniesWindow::getSelectedCompany
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TruckCompaniesWindow::initializeTable
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
        
    }// end of TruckCompaniesWindow::initializeTable
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // TruckCompaniesWindow::retrieveDataFromDatabase
    //
    // Retrieves the truck companies from the MySQL database and puts the ids
    // and names in the table model
    //
    
    public void retrieveDataFromDatabase() 
    {
        
        //get truck companies from the database
        companies = getDatabase().getTruckCompanies();
        
        String[] columnNames = {"Id", "Truck Company"};
        
        String[][] data = new String[companies.size()][];
        
        //extract ids and names from companies
        for (int i=0; i<data.length; i++) {
            data[i] = new String[]{companies.get(i).getId(), 
                                        companies.get(i).getName()};
        }
        
        model.setDataVector(data, columnNames);
        
        //now that we've put data in the model, we can set some table settings
        
        //select the first row of the table
        if (table.getRowCount() > 0) { table.setRowSelectionInterval(0, 0); }
        
        //set the widths of the columns
        TableColumnModel m =  table.getColumnModel();
        m.getColumn(0).setPreferredWidth(100);
        m.getColumn(1).setPreferredWidth(300);
        
    }// end of TruckCompaniesWindow::retrieveDataFromDatabase
    //--------------------------------------------------------------------------

}//end of class TruckCompaniesWindow
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class CreateOrEditTruckCompanyWindow
//
// This class is used for a window that serves as both the Create Truck Company
// and Edit Truck Company windows, depending on the constructor used.
//
// To make the class function as the Create Truck Company window, use the first
// constructor; to make it function as the Edit Truck Company Window, use the
// second constructor.
//
//

class CreateOrEditTruckCompanyWindow extends AltusJDialog
{
    
    static private final String actionId = "CreateOrEditTruckCompanyWindow";
    static public String getActionId() { return actionId; }
    
    private final int mode;
    private final int mode_create   = 1;
    private final int mode_edit     = 2;
    
    private final TruckCompany truckCompany;
    private final TruckCompaniesWindow truckCompaniesWindow;
    
    private final String confirmButtonText;
    private final String confirmButtonToolTip;
    
    //--------------------------------------------------------------------------
    // CreateOrEditTruckCompanyWindow::CreateOrEditTruckCompanyWindow (constructor)
    //
    // Constructor for using the Create Truck Company window.
    //

    public CreateOrEditTruckCompanyWindow (
                                    TruckCompaniesWindow pTruckCompaniesWindow, 
                                    MainFrame pMainFrame, 
                                    MainView pMainView)
    {

        super("Create Truck Company", pTruckCompaniesWindow, pMainFrame,
                        pMainView);
        
        mode                    = mode_create;
        truckCompany            = new TruckCompany("", "", "");
        truckCompaniesWindow    = pTruckCompaniesWindow;
        
        //confirm button text and tool tip
        confirmButtonText       = "Create";
        confirmButtonToolTip    = "Create the new truck company.";

    }//end of CreateOrEditTruckCompanyWindow::CreateOrEditTruckCompanyWindow (constructor)
    //-------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CreateOrEditTruckCompanyWindow::CreateOrEditTruckCompanyWindow (constructor)
    //
    // Constructor for using the Edit Truck Company window.
    //

    public CreateOrEditTruckCompanyWindow(
                                    TruckCompany pTruckCompany,
                                    TruckCompaniesWindow pTruckCompaniesWindow, 
                                    MainFrame pMainFrame, 
                                    MainView pMainView)
    {

        super("Edit Truck Company", pTruckCompaniesWindow, pMainFrame, 
                    pMainView);
        
        mode                    = mode_edit;
        truckCompany            = pTruckCompany;
        truckCompaniesWindow    = pTruckCompaniesWindow;
        
        //confirm button text and tool tip
        confirmButtonText       = "Apply";
        confirmButtonToolTip    = "Apply the changes made.";

    }//end of CreateOrEditTruckCompanyWindow::CreateOrEditTruckCompanyWindow (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CreateOrEditTruckCompanyWindow::createGui
    //
    // Creates the gui for the window.
    //
    
    @Override
    protected void createGui() 
    {
        
        //add the Id and Name row
        addToMainPanel(createRow(new JPanel[] {
            createIdPanel(truckCompany.getId()),
            createNamePanel(truckCompany.getName())
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Cancel/Confirm panel
        addToMainPanel(createCancelConfirmPanel(confirmButtonText, 
                                                    confirmButtonToolTip));

    }// end of CreateOrEditTruckCompanyWindow::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CreateOrEditTruckCompanyWindow::confirm
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
        
        //insert the truck company into the database if we are in create mode
        if (mode == mode_create) {
            getDatabase().insertTruckCompany(truckCompany);
        }
        
        //update the truck company if we are in edit mode
        else if (mode == mode_edit) { 
            getDatabase().updateTruckCompany(truckCompany);
        }
        
        //tell the Truck Companies window to reload its data from the database
        //since we changed some stuff there
        truckCompaniesWindow.retrieveDataFromDatabase();
        
        //dispose of the window and its resources
        dispose();

    }// end of CreateOrEditTruckCompanyWindow::confirm
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CreateOrEditTruckCompanyWindow::checkUserInput
    //
    // Checks the user input for errors.
    //
    // Returns true if no errors; false if there are.
    //

    private boolean checkUserInput()
    {
        
        //Check Id input
        if (getIdInput().isEmpty()) {
            displayError("Please give the truck company an Id.");
            return false;
        }
        else if (!getIdInput().equals(truckCompany.getId()) && 
                    getDatabase().checkForValue(getIdInput(), 
                                                    "TRUCK_COMPANIES", "id"))
        {
            displayError("The Id entered already exists in the database.");
            return false;
        }
        
        //Check Name input
        if (getNameInput().isEmpty()) {
            displayError("Please give the truck company a name.");
            return false;
        }
        else if (!getNameInput().equals(truckCompany.getName()) && 
                    getDatabase().checkForValue(getIdInput(), 
                                                    "TRUCK_COMPANIES", "name"))
        {
            displayError("The name entered already exists in the database.");
            return false;
        }
        
        //we made it here, so there were no errors
        return true;

    }// end of CreateOrEditTruckCompanyWindow::checkUserInput
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CreateOrEditTruckCompanyWindow::getUserInput
    //
    // Gets the user input from the text fields and stores it in the Truck 
    // Company.
    //

    private void getUserInput()
    {

        truckCompany.setId(getIdInput());
        
        truckCompany.setName(getNameInput());

    }// end of CreateOrEditTruckCompanyWindow::getUserInput
    //--------------------------------------------------------------------------
    
}//end of class CreateOrEditTruckCompanyWindow
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------