/*******************************************************************************
* Title: MainFrame.java
* Author: Hunter Schoonover
* Date: 07/20/15
*
* Purpose:
*
* This class is the main window of the program. It is used to display buttons
* and functions and data to the user.
*
*/

//------------------------------------------------------------------------------

package view;

//------------------------------------------------------------------------------

import java.awt.Component;
import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import model.Batch;
import model.MySQLDatabase;
import skooniecomponents.frame.SkoonieFrame;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class MainFrame
//

public class MainFrame extends SkoonieFrame
{
    
    static private final String actionId = "MainFrame";
    static public String getActionId() { return actionId; }
    
    private final MainView mainView;

    private AltusJDialog activeDialog;
    public void setActiveDialog(AltusJDialog pDialog) {activeDialog = pDialog;}
    private CustomersWindow customersWindow;
    private RacksWindow racksWindow;
    private Help help;
    private About about;
    
    private final MySQLDatabase db = new MySQLDatabase();
    private ArrayList<Batch> batches;
    private CustomTable batchesTable;
    private DefaultTableModel model;

    //--------------------------------------------------------------------------
    // MainFrame::MainFrame (constructor)
    //

    public MainFrame(MainView pMainView)
    {
        
        super("Altus Inventory", pMainView, pMainView);

        mainView = pMainView;
        
        //exit the program when the window closes
        defaultCloseOperation = EXIT_ON_CLOSE;

    }//end of MainFrame::MainFrame (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //
    
    @Override
    public void init() 
    {
        
        //initialize database
        db.init();
        
        //initialize model
        //create a model that allows no editable cells
        model = new DefaultTableModel() {
            @Override public boolean isCellEditable(int pR, int pC) {
                return false;
            }
        };
        
        initializeBatchesTable();
        retrieveBatchesFromDatabase();
        
        super.init();
        
    }// end of MainFrame::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::createGui
    //
    // Creates and adds the GUI to the mainPanel.
    //
    
    @Override
    protected void createGui() 
    {
        
        //add a menu
        MainMenu m = new MainMenu(mainView);
        m.init();
        setJMenuBar(m);
        
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        //add the control panel
        mainPanel.add(createControlPanel());

        //vertical spacer
        mainPanel.add(Box.createRigidArea(new Dimension(0,10)));

        //add a horizontal separator to separate the control panel
        //and the display panel
        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        Dimension d = sep.getPreferredSize();
        d.width = sep.getMaximumSize().width;
        sep.setMaximumSize(d);
        mainPanel.add(sep);

        //vertical spacer
        mainPanel.add(Box.createRigidArea(new Dimension(0,10)));

        //add the display panel
        mainPanel.add(createDisplayPanel());
        
    }//end of MainFrame::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::cancelAltusJDialogAction
    //
    // Cancels whatever action may be happening through the active AltusJDialog.
    //

    public void cancelAltusJDialogAction()
    {

        activeDialog.cancel();

    }// end of MainFrame::cancelAltusJDialogAction
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::confirmAltusJDialogAction
    //
    // Confirms whatever action may be happening through the active 
    // AltusJDialog.
    //

    public void confirmAltusJDialogAction()
    {

        activeDialog.confirm();

    }//end of MainFrame::confirmAltusJDialogAction
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::createDisplayPanel
    //
    // Creates and returns the display panel.
    //
    // The display panel displays all of the pipe in the yard to the user.
    //

    private JPanel createDisplayPanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);

        //add the filter panel box
        panel.add(createFilterPanel());

        //vertical spacer
        panel.add(Box.createRigidArea(new Dimension(0,10)));

        //add Batches table panel
        panel.add(createBatchesTablePanel());

        return panel;

    }// end of MainFrame::createDisplayPanel
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainFrame::createControlPanel
    //
    // Creates and returns the control panel.
    //
    // The control panel contains all of the main button and functions for the 
    // program.
    //

    private JPanel createControlPanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        int spacer = 10;

        //add Receive Material button
        panel.add(createControlPanelButton("Receive<br>Material", 
                                            "images/receiveMaterial.png",
                                            "Receive material into the yard.",
                                            "Receive Material"));
        
        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));
        
        //add Ship Material button
        panel.add(createControlPanelButton("Ship<br>Material", 
                                            "images/shipMaterial.png",
                                            "Ship material from the yard.",
                                            "Ship Material"));
        
        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));
        
        //add Move Material button
        panel.add(createControlPanelButton("Move<br>Material", 
                                            "images/moveMaterial.png",
                                            "Move the selected material to a "
                                                + "different rack.",
                                            "Move Material"));
        
        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));
        
        //add Transfer Material button
        panel.add(createControlPanelButton("Transfer<br>Material", 
                                            "images/transferMaterial.png",
                                            "Transfer the selected material to "
                                                + "a different customer.",
                                            "Transfer Material"));
        
        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));
        
        //add Reserve Material button
        panel.add(createControlPanelButton("Reserve<br>Material", 
                                            "images/reserveMaterial.png",
                                            "Reserve the selected material for "
                                                + "future use.",
                                            "Reserve Material"));
        
        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));
        
        //add Material Info button
        panel.add(createControlPanelButton("Material<br>Info", 
                                            "images/materialInfo.png",
                                            "View and edit information about "
                                                + "the selected material.",
                                            "Material Info"));
        
        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));

        //add Create Report button
        panel.add(createControlPanelButton("Create<br>Report", 
                                            "images/createReport.png",
                                            "Create a report.",
                                            "Create Report"));

        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));

        //add Create Invoice button
        panel.add(createControlPanelButton("Create<br>Invoice", 
                                            "images/createInvoice.png",
                                            "Create an invoice.",
                                            "Create Invoice"));

        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));

        //add Make Payment button
        panel.add(createControlPanelButton("Make<br>Payment", 
                                            "images/makePayment.png",
                                            "Record payment from a customer.",
                                            "Make Payment"));

        return panel;

    }// end of MainFrame::createControlPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::createControlPanelButton
    //
    // Creates and returns a button to be used in the control panel part of the
    // MainFrame, using the parameters for individuality.
    //

    private JButton createControlPanelButton(String pText, String pImagePath,
                                                String pTip, String pCommand)
    {

        //create button
        JButton btn = new JButton("<html><center>" + pText + "</center></html>", 
                                        createImageIcon(pImagePath));
        btn.addActionListener(mainView);
        btn.setActionCommand(Tools.generateActionCommand(actionId, pCommand));
        btn.setAlignmentX(LEFT_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setMargin(new Insets(0,0,0,0));
        btn.setToolTipText(pTip);
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);        
        Tools.setSizes(btn, 70, 75);
        
        return btn;

    }// end of MainFrame::createControlPanelButton
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainFrame::createBatchesTablePanel
    //
    // Creates and returns the scroll pane containing the Batches table.
    //

    private JScrollPane createBatchesTablePanel()
    {

        JScrollPane sp = new JScrollPane(batchesTable);
        sp.setAlignmentX(LEFT_ALIGNMENT);
        sp.setAlignmentY(TOP_ALIGNMENT);
        
        return sp;

    }// end of MainFrame::createBatchesTablePanel
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainFrame::createImageIcon
    //
    // Returns an ImageIcon, or null if the path was invalid.
    //
    // *************************************************************************
    // NOTE: You must use forward slashes in the path names for the resource
    // loader to find the image files in the JAR package.
    // *************************************************************************
    //

    protected static ImageIcon createImageIcon(String pPath)
    {

        // have to use the MainFrame class because it is located in the same 
        // package as the file; specifying the class specifies the first 
        // portion of the path to the image, this concatenated with the pPath
        java.net.URL imgURL = MainFrame.class.getResource(pPath);

        if (imgURL != null) { return new ImageIcon(imgURL); }
        else {return null;}

    }//end of MainFrame::createImageIcon
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ShipMaterialFrame::createFilterComboBoxesAndFilterTextField
    //
    // Creates and returns a JPanel containing the filter by combo box and the
    // filter text box.
    //

    private JPanel createFilterComboBoxesAndFilterTextField()
    {
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        
        //populate an array of strings with items for a combo box
        String[] strings = { "All Material in Yard", "Available in Stock", 
                                "Reserved", "Shipped", "Moved", "Transferred" };
        //Create the combo box, select item at index 0
        JComboBox combo = new JComboBox(strings);
        combo.addActionListener(mainView);     
        combo.setActionCommand("MainFrame::Change View");
        combo.setAlignmentX(LEFT_ALIGNMENT);
        combo.setAlignmentY(TOP_ALIGNMENT);
        combo.setSelectedIndex(0);
        Tools.setSizes(combo, 135, 20);
        panel.add(combo);
        
        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(10,0)));
        
        //populate an array of strings with items for a combo box
        String[] strings2 = {"ID", "Customer", "Date", "Status", "Truck", 
                            "Quantity", "Length", "Rack", "Range", "Grade",
                            "Diameter", "Wall", "Facility"};
        //Create the combo box, select item at index 0
        JComboBox combo2 = new JComboBox(strings2);
        combo2.addActionListener(mainView);     
        combo2.setActionCommand("MainFrame::Ship Material Change Filter By");
        combo2.setAlignmentX(LEFT_ALIGNMENT);
        combo2.setAlignmentY(TOP_ALIGNMENT);
        combo2.setSelectedIndex(0);
        Tools.setSizes(combo2, 90, 20);
        panel.add(combo2);
        
        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(10,0)));
        
        //create the filter text box
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        field.setAlignmentY(TOP_ALIGNMENT);
        field.setToolTipText("Type a phrase to filter the material by.");
        Tools.setSizes(field, 200, 20);
        panel.add(field);
        
        return panel;

    }// end of ShipMaterialFrame::createFilterComboBoxesAndFilterTextField
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::creatFilterPanel
    //
    // Creates and returns the filter panel.
    //

    private JPanel createFilterPanel()
    {
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel("Filter:");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        //vertical spacer
        panel.add(Box.createRigidArea(new Dimension(0,5)));
        
        panel.add(createFilterComboBoxesAndFilterTextField());
        
        return panel;

    }// end of MainFrame::createFilterPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::deleteCustomer
    //
    // Deletes the customer selected in the Customers window.
    //
    
    public void deleteCustomer() 
    {
        
        customersWindow.deleteCustomer();
        
    }// end of MainFrame::deleteCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::deleteRack
    //
    // Deletes the rack selected in the Racks window.
    //
    
    public void deleteRack() 
    {
        
        racksWindow.deleteRack();
        
    }// end of MainFrame::deleteRack
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainFrame::displayAbout
    //
    // Displays about information.
    //

    public void displayAbout()
    {

        about = new About(this);
        //window will be released on close, so point should be null
        about = null;

    }//end of MainFrame::displayAbout
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::displayCreateCustomerWindow
    //
    // Displays the Create Customer window.
    //
    
    public void displayCreateCustomerWindow() 
    {
        
        customersWindow.displayCreateCustomerWindow();
        
    }// end of MainFrame::displayCreateCustomerWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::displayCreateRackWindow
    //
    // Displays the Create Rack window.
    //
    
    public void displayCreateRackWindow() 
    {
        
        racksWindow.displayCreateRackWindow();
        
    }// end of MainFrame::displayCreateRackWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::displayCustomersWindow
    //
    // Displays the Customers window and stores a reference to it.
    //
    
    public void displayCustomersWindow() 
    {
        
        customersWindow = new CustomersWindow(this, mainView);
        customersWindow.init();
        
    }// end of MainFrame::displayCustomersWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::displayEditCustomerWindow
    //
    // Displays the Edit Customer window.
    //
    
    public void displayEditCustomerWindow() 
    {
        
        customersWindow.displayEditCustomerWindow();
        
    }// end of MainFrame::displayEditCustomerWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::displayEditRackWindow
    //
    // Displays the Edit Customer window.
    //
    
    public void displayEditRackWindow() 
    {
        
        racksWindow.displayEditRackWindow();
        
    }// end of MainFrame::displayEditCustomerWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::displayHelp
    //
    // Displays help information.
    //

    public void displayHelp()
    {

        help = new Help(this);
        //window will be released on close, so point should be null
        help = null;

    }//end of MainFrame::displayHelp
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::displayMoveMaterialWindow
    //
    // Displays the Move Material window.
    //
    
    public void displayMoveMaterialWindow() 
    {
        
        MoveMaterialWindow w = new MoveMaterialWindow(getSelectedBatch(), this,
                                                        mainView);
        w.init();
        
    }// end of MainFrame::displayMoveMaterialWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::displayRacksWindow
    //
    // Displays the Racks window and stores a reference to it.
    //
    
    public void displayRacksWindow() 
    {
        
        racksWindow = new RacksWindow(this, mainView);
        racksWindow.init();
        
    }// end of MainFrame::displayRacksWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::displayReceiveMaterialWindow
    //
    // Displays the Receive Material window.
    //
    
    public void displayReceiveMaterialWindow() 
    {
        
        ReceiveMaterialWindow w = new ReceiveMaterialWindow(this, mainView);
        w.init();
        
    }// end of MainFrame::displayReceiveMaterialWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::displayReserveMaterialWindow
    //
    // Displays the Reserve Material window.
    //
    
    public void displayReserveMaterialWindow() 
    {
        
        ReserveMaterialWindow w = new ReserveMaterialWindow(this, mainView);
        w.init();
        
    }// end of MainFrame::displayTransferMaterialWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::displayShipMaterialWindow
    //
    // Displays the Ship Material window.
    //
    
    public void displayShipMaterialWindow() 
    {
        
        ShipMaterialWindow w = new ShipMaterialWindow(this, mainView);
        w.init();
        
    }// end of MainFrame::displayShipMaterialWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::displayTransferMaterialWindow
    //
    // Displays the Transfer Material window.
    //
    
    public void displayTransferMaterialWindow() 
    {
        
        TransferMaterialWindow w = new TransferMaterialWindow(
                                            getSelectedBatch(),this, mainView);
        w.init();
        
    }// end of MainFrame::displayTransferMaterialWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::getSelectedBatch
    //
    // Gets and returns the batch that is selected in the table.
    //
    
    private Batch getSelectedBatch() 
    {
        
        int row = batchesTable.getSelectedRow();
        String id = (String)batchesTable.getValueAt(row, 0);
        
        //look for the customer that matches the selected id
        for (Batch b : batches) {
            if (id.equals(b.getId())) { return b; }
        }
        
        //no customer was found so return null
        return null;
        
    }// end of MainFrame::getSelectedBatch
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::initializeBatchesTable
    //
    // Initializes the Batches table.
    //
    // The batches table displays all of the batches to the user.
    //

    private void initializeBatchesTable()
    {
        
        batchesTable = new CustomTable(model);
        batchesTable.init();

    }// end of MainFrame::initializeBatchesTable
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::retrieveBatchesFromDatabase
    //
    // Retrieves and stores the batches from the MySQL database.
    //
    
    public void retrieveBatchesFromDatabase() 
    {
        
        //get batches from the database
        batches = db.getBatches();
        
        String[] columnNames = {"Id", "Date", "Quantity", 
                                    "Total Length", "Owner", "Rack"};
        
        String[][] data = new String[batches.size()][];
        
        //extract ids and names from customers
        for (int i=0; i<data.length; i++) {
            String cusName = db.getCustomer(batches.get(i).getCustomerKey())
                                                            .getName();
            String rackName = db.getRack(batches.get(i).getRackKey())
                                                            .getName();
            data[i] = new String[]{ batches.get(i).getId(), 
                                    batches.get(i).getDate(),
                                    batches.get(i).getQuantity(),
                                    batches.get(i).getTotalLength(),
                                    cusName,
                                    rackName};
        }
        
        model.setDataVector(data, columnNames);
        
        //select the first row of the table
        if (batchesTable.getRowCount() > 0) { 
            batchesTable.setRowSelectionInterval(0, 0);
        }
        
    }// end of MainFrame::retrieveBatchesFromDatabase
    //--------------------------------------------------------------------------

}//end of class MainFrame
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------