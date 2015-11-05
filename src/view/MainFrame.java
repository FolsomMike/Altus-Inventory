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

import hscomponents.jsplitbutton.JSplitButton;
import hscomponents.table.hsTable;
import java.awt.Color;
import java.awt.Component;
import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class MainFrame
//

public class MainFrame extends JFrame
{
    
    private final MainView mainView;
    private JPanel mainPanel;

    private GuiUpdater guiUpdater;
    private Help help;
    private About about;
    
    hsTable table = new hsTable();
    
    int numRowsChecked = 0;
    
    //material action buttons
    private JButton btnMoveMaterial;
    private JButton btnReserveMaterial;
    private JButton btnShipMaterial;
    private JButton btnTransferMaterial;
    //material action buttons tool tips
    private final String disabledToolTipAddon 
                        = " Check a material in the table below to enable.";
    private final String moveMaterialToolTip 
                        = "Move material to a different rack.";
    private final String reserveMaterialToolTip 
                        = "Reserve material for future use.";
    private final String shipMaterialToolTip 
                        = "Ship material from the yard.";
    private final String transferMaterialToolTip 
                        = "Transfer material from one customer to another.";

    //--------------------------------------------------------------------------
    // MainFrame::MainFrame (constructor)
    //

    public MainFrame(MainView pMainView)
    {

        mainView = pMainView;

    }//end of MainFrame::MainFrame (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainFrame::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    public void init()
    {

        setUpFrame();

        //create an object to handle thread safe updates of GUI components
        guiUpdater = new GuiUpdater(this);
        guiUpdater.init();

        //add a menu to the main form, passing this as the action listener
        setJMenuBar(new MainMenu(mainView));

        //create user interface: buttons, displays, etc.
        setupGui();

        //arrange all the GUI items
        pack();

        //display the main frame
        setVisible(true);

    }// end of MainFrame::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::checkBoxChanged
    //
    // Enables/disables the material action buttons based on the number of 
    // checkboxes checked.
    //
    // Checks whether or not the checkbox in the passed in row is checked (true)
    // or unchecked (false).
    //
    // If it is checked, the 1 is added to numRowsChecked; if it's not then 1 is
    // subtracted.
    //

    public void checkBoxChanged(int pRow)
    {

        if ((boolean)table.getValueAt(pRow, 0)) { ++numRowsChecked; }
        else { --numRowsChecked; }
        
        if (numRowsChecked <= 0) { enableMaterialActionButtons(false); }
        else { enableMaterialActionButtons(true); }

    }//end of MainFrame::checkBoxChanged
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

        //add Receive Material button
        panel.add(createReceiveMaterialButton());
        
        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(10,0)));
        
        //add Ship Material button
        panel.add(createShipMaterialButton());
        
        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(10,0)));
        
        //add Move Material button
        panel.add(createMoveMaterialButton());
        
        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(10,0)));
        
        //add Transfer Material button
        panel.add(createTransferMaterialButton());
        
        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(10,0)));
        
        //add Reserve Material button
        panel.add(createReserveMaterialButton());
        
        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(10,0)));

        //add create report button
        panel.add(createCreateReportButton());

        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(10,0)));

        //add create invoice button
        panel.add(createCreateInvoiceButton());

        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(10,0)));

        //add make payment button
        panel.add(createMakePaymentButton());

        return panel;

    }// end of MainFrame::createControlPanel
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

        //add materials table panel
        panel.add(createMaterialsTable());

        return panel;

    }// end of MainFrame::createDisplayPanel
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
    // MainFrame::createCreateInvoiceButton
    //
    // Creates and returns the Create Invoice button.
    //

    private JButton createCreateInvoiceButton()
    {

        //create button
        JSplitButton btn = new JSplitButton(
                                "<html><center>Create<br>Invoice</html>", 
                                createImageIcon("images/createInvoice.png"));
        btn.init();
        btn.addSplitButtonActionListener(mainView);
        btn.setActionCommand("MainFrame--Create Invoice");
        btn.setAlignmentX(LEFT_ALIGNMENT);
        btn.setArrowSize(10);
        btn.setFocusPainted(false);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setMargin(new Insets(0,0,0,20));
        btn.setToolTipText("Create an invoice for a customer.");
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        Tools.setSizes(btn, 70, 75);
        
        //create a popup menu and add it to the button
        JPopupMenu menu = new JPopupMenu();
        menu.setBorder(new BevelBorder(BevelBorder.RAISED));
        
        //Create Invoice/View All Invoices menu item
        JMenuItem viewAllInvoicesItem = new JMenuItem("View All Invoices");
        viewAllInvoicesItem.setToolTipText("View, edit, or delete invoices.");
        viewAllInvoicesItem.setActionCommand
                                ("MainFrame--Create Invoice/View All Invoices");
        viewAllInvoicesItem.addActionListener(mainView);
        menu.add(viewAllInvoicesItem);
        
        btn.setPopupMenu(menu);
        
        return btn;

    }// end of MainFrame::createCreateInvoiceButton
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainFrame::createCreateReportButton
    //
    // Creates and returns the Create Report button.
    //

    private JButton createCreateReportButton()
    {

        //create button
        JSplitButton btn = new JSplitButton(
                                "<html><center>Create<br>Report</html>", 
                                createImageIcon("images/createReport.png"));
        btn.init();
        btn.addSplitButtonActionListener(mainView);
        btn.setActionCommand("MainFrame--Create Report");
        btn.setAlignmentX(LEFT_ALIGNMENT);
        btn.setArrowSize(10);
        btn.setFocusPainted(false);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setMargin(new Insets(0,0,0,20));
        btn.setToolTipText("Create report.");
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        Tools.setSizes(btn, 70, 75);
        
        //create a popup menu and add it to the button
        JPopupMenu menu = new JPopupMenu();
        
        //Create Report/Receiving Report menu item
        JMenuItem receivingReportMenuItem = new JMenuItem("Receiving Report");
        receivingReportMenuItem.setToolTipText("Create a receiving report.");
        receivingReportMenuItem.setActionCommand("MainFrame--Receiving Report");
        receivingReportMenuItem.addActionListener(mainView);
        menu.add(receivingReportMenuItem);
        
        //Create Report/Shipping Report menu item
        JMenuItem shippingReportMenuItem = new JMenuItem("Shipping Report");
        shippingReportMenuItem.setToolTipText("Create a shipping report.");
        shippingReportMenuItem.setActionCommand("MainFrame--Shipping Report");
        shippingReportMenuItem.addActionListener(mainView);
        menu.add(shippingReportMenuItem);
        
        //Create Report/Tally Report menu item
        JMenuItem tallyReportMenuItem = new JMenuItem("Tally Report");
        tallyReportMenuItem.setToolTipText("Create a tally report.");
        tallyReportMenuItem.setActionCommand("MainFrame--Tally Report");
        tallyReportMenuItem.addActionListener(mainView);
        menu.add(tallyReportMenuItem);
        
        //Create Report/Rack Report menu item
        JMenuItem rackReportMenuItem = new JMenuItem("Rack Report");
        rackReportMenuItem.setToolTipText("Create a rack report.");
        rackReportMenuItem.setActionCommand("MainFrame--Rack Report");
        rackReportMenuItem.addActionListener(mainView);
        menu.add(rackReportMenuItem);
        
        //Create Report/Current Balance Report menu item
        JMenuItem currentBalanceReportMenuItem = 
                                        new JMenuItem("Current Balance Report");
        currentBalanceReportMenuItem.setToolTipText
                            ("Create a current balance report for a customer.");
        currentBalanceReportMenuItem.setActionCommand
                            ("MainFrame--Current Balance Report");
        currentBalanceReportMenuItem.addActionListener(mainView);
        menu.add(currentBalanceReportMenuItem);
        
        menu.setBorder(new BevelBorder(BevelBorder.RAISED));
        btn.setPopupMenu(menu);
        
        return btn;

    }// end of MainFrame::createCreateReportButton
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
        String[] strings2 = {"ID", "Company", "Date", "Status", "Truck", 
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
    // MainFrame::createMakePaymentButton
    //
    // Creates and returns the Make Payment button.
    //

    private JButton createMakePaymentButton()
    {

        //create button
        JButton btn = new JButton("<html><center>Make<br>Payment</html>", 
                                    createImageIcon("images/makePayment.png"));
        btn.addActionListener(mainView);
        btn.setActionCommand("MainFrame--Make Payment");
        btn.setAlignmentX(LEFT_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setMargin(new Insets(0,0,0,0));
        btn.setToolTipText("Record payment from customer.");
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);        
        Tools.setSizes(btn, 70, 75);
        
        return btn;

    }// end of MainFrame::createMakePaymentButton
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainFrame::createMaterialsTable
    //
    // Creates and returns the materials table.
    //
    // The materials table displays all of the pipe in the yard to the user.
    //

    private JPanel createMaterialsTable()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);

        table.init();
        
        //setup the table
        table.getModel().addTableModelListener(mainView);
        table.getTableHeader().setBackground(Color.decode("#C2E0FF"));
        table.getTableHeader().setFont(new Font("Times Roman", Font.BOLD, 15));
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(25);
        table.setSelectionBackground(Color.decode("#000099"));
        table.setSelectionForeground(Color.WHITE);
        
        table.addColumn("");
        table.setColumnEditable(0, true);
        table.addColumn("ID");
        table.addColumn("Company");
        table.addColumn("Date");
        table.addColumn("Status");
        table.addColumn("Truck");
        table.addColumn("Quantity");
        table.addColumn("Length");
        table.addColumn("Rack");
        table.addColumn("Range");
        table.addColumn("Grade");
        table.addColumn("Diameter");
        table.addColumn("Wall");
        table.addColumn("Facility");

        List<Object> row = new ArrayList<>();
        row.add(false);
        row.add("1111");
        row.add("RG NDT");
        row.add("07/21/15");
        row.add("IN STOCK");
        row.add("Frogger Trucking");
        row.add("29");
        row.add("522");
        row.add("");
        row.add("R2");
        row.add("13-CR");
        row.add("");
        row.add("");
        row.add("");
        
        List<Object> row2 = new ArrayList<>();
        row2.add(false);
        row2.add("2222");
        row2.add("Oil Frack Stack");
        row2.add("07/25/15");
        row2.add("RESERVED");
        row2.add("Mountain Inc. Trucking");
        row2.add("55");
        row2.add("1210");
        row2.add("");
        row2.add("R3");
        row2.add("13-CR");
        row2.add("");
        row2.add("");
        row2.add("");

        //add test rows to the table -- //DEBUG HSS//
        for (int i=0; i<30; i++) {
            if (i%2 == 0) { table.addRow(new ArrayList(row));}
            else { table.addRow(new ArrayList(row2)); }
        }

        //put the table in a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setAlignmentX(LEFT_ALIGNMENT);
        scrollPane.setAlignmentY(TOP_ALIGNMENT);
        panel.add(scrollPane);

        return panel;

    }// end of MainFrame::createMaterialsTable
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::createMoveMaterialButton
    //
    // Creates and returns the Receive Material button.
    //

    private JButton createMoveMaterialButton()
    {

        //create button
        btnMoveMaterial = new JButton("<html><center>Move<br>Material</html>", 
                                createImageIcon("images/moveMaterial.png"));
        btnMoveMaterial.addActionListener(mainView);
        btnMoveMaterial.setActionCommand("MainFrame--Move Material");
        btnMoveMaterial.setAlignmentX(LEFT_ALIGNMENT);
        btnMoveMaterial.setEnabled(false);
        btnMoveMaterial.setFocusPainted(false);
        btnMoveMaterial.setHorizontalTextPosition(SwingConstants.CENTER);
        btnMoveMaterial.setMargin(new Insets(0,0,0,0));
        btnMoveMaterial.setToolTipText(moveMaterialToolTip 
                                                        + disabledToolTipAddon);
        btnMoveMaterial.setVerticalTextPosition(SwingConstants.BOTTOM);        
        Tools.setSizes(btnMoveMaterial, 70, 75);
        
        return btnMoveMaterial;

    }// end of MainFrame::createMoveMaterialButton
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::createReceiveMaterialButton
    //
    // Creates and returns the Receive Material button.
    //

    private JButton createReceiveMaterialButton()
    {

        //create button
        JButton btn = new JButton("<html><center>Receive<br>Material</html>", 
                                createImageIcon("images/receiveMaterial.png"));
        btn.addActionListener(mainView);
        btn.setActionCommand("MainFrame--Receive Material");
        btn.setAlignmentX(LEFT_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setMargin(new Insets(0,0,0,0));
        btn.setToolTipText("Receive material into the yard.");
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);        
        Tools.setSizes(btn, 70, 75);
        
        return btn;

    }// end of MainFrame::createReceiveMaterialButton
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::createReserveMaterialButton
    //
    // Creates and returns the Reserve Material button.
    //

    private JButton createReserveMaterialButton()
    {

        //create button
        btnReserveMaterial = new JButton
                                ("<html><center>Reserve<br>Material</html>", 
                                createImageIcon("images/reserveMaterial.png"));
        btnReserveMaterial.addActionListener(mainView);
        btnReserveMaterial.setActionCommand("MainFrame--Reserve Material");
        btnReserveMaterial.setAlignmentX(LEFT_ALIGNMENT);
        btnReserveMaterial.setEnabled(false);
        btnReserveMaterial.setFocusPainted(false);
        btnReserveMaterial.setHorizontalTextPosition(SwingConstants.CENTER);
        btnReserveMaterial.setMargin(new Insets(0,0,0,0));
        btnReserveMaterial.setToolTipText(reserveMaterialToolTip 
                                                        + disabledToolTipAddon);
        btnReserveMaterial.setVerticalTextPosition(SwingConstants.BOTTOM);        
        Tools.setSizes(btnReserveMaterial, 70, 75);
        
        return btnReserveMaterial;

    }// end of MainFrame::createReserveMaterialButton
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::createShipMaterialButton
    //
    // Creates and returns the Ship Material button.
    //

    private JButton createShipMaterialButton()
    {

        //create button
        btnShipMaterial = new JButton("<html><center>Ship<br>Material</html>", 
                                createImageIcon("images/shipMaterial.png"));
        btnShipMaterial.addActionListener(mainView);
        btnShipMaterial.setActionCommand("MainFrame--Ship Material");
        btnShipMaterial.setAlignmentX(LEFT_ALIGNMENT);
        btnShipMaterial.setEnabled(false);
        btnShipMaterial.setFocusPainted(false);
        btnShipMaterial.setHorizontalTextPosition(SwingConstants.CENTER);
        btnShipMaterial.setMargin(new Insets(0,0,0,0));
        btnShipMaterial.setToolTipText(shipMaterialToolTip 
                                                        + disabledToolTipAddon);
        btnShipMaterial.setVerticalTextPosition(SwingConstants.BOTTOM);        
        Tools.setSizes(btnShipMaterial, 70, 75);
        
        return btnShipMaterial;

    }// end of MainFrame::createShipMaterialButton
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::createTransferMaterialButton
    //
    // Creates and returns the Transfer Material button.
    //

    private JButton createTransferMaterialButton()
    {

        //create button
        btnTransferMaterial = new JButton("<html><center>Transfer<br>Material</html>", 
                                createImageIcon("images/transferMaterial.png"));
        btnTransferMaterial.addActionListener(mainView);
        btnTransferMaterial.setActionCommand("MainFrame--Transfer Material");
        btnTransferMaterial.setAlignmentX(LEFT_ALIGNMENT);
        btnTransferMaterial.setEnabled(false);
        btnTransferMaterial.setFocusPainted(false);
        btnTransferMaterial.setHorizontalTextPosition(SwingConstants.CENTER);
        btnTransferMaterial.setMargin(new Insets(0,0,0,0));
        btnTransferMaterial.setToolTipText(transferMaterialToolTip 
                                                        + disabledToolTipAddon);
        btnTransferMaterial.setVerticalTextPosition(SwingConstants.BOTTOM);        
        Tools.setSizes(btnTransferMaterial, 70, 75);
        
        return btnTransferMaterial;

    }// end of MainFrame::createTransferMaterialButton
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::enableMaterialActionButtons
    //
    // Enables/disables the material action buttons based on the passed in
    // boolean.
    //

    private void enableMaterialActionButtons(boolean pEnable)
    {

        btnMoveMaterial.setEnabled(pEnable);
        btnReserveMaterial.setEnabled(pEnable);
        btnShipMaterial.setEnabled(pEnable);
        btnTransferMaterial.setEnabled(pEnable);
        
        //determine which tool tips to use
        if (pEnable) {
            btnMoveMaterial.setToolTipText(moveMaterialToolTip);
            btnReserveMaterial.setToolTipText(reserveMaterialToolTip);
            btnShipMaterial.setToolTipText(shipMaterialToolTip);
            btnTransferMaterial.setToolTipText(transferMaterialToolTip);
        }
        else {
            btnMoveMaterial.setToolTipText
                            (moveMaterialToolTip + disabledToolTipAddon);
            btnReserveMaterial.setToolTipText
                            (reserveMaterialToolTip + disabledToolTipAddon);
            btnShipMaterial.setToolTipText
                            (shipMaterialToolTip + disabledToolTipAddon);
            btnTransferMaterial.setToolTipText
                            (transferMaterialToolTip + disabledToolTipAddon);
        }

    }//end of MainFrame::enableMaterialActionButtons
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
    // MainFrame::setUpFrame
    //
    // Sets up the JFrame by setting various options and styles.
    //

    private void setUpFrame()
    {

        //set the title of the frame
        setTitle("TallyZap Inventory Software");

        //turn off default bold for Metal look and feel
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        //force "look and feel" to Java style
        try {
            UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println("Could not set Look and Feel");
        }

        //add the mainView as a window listener
        addWindowListener(mainView);

        //sets the default close operation
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //add a JPanel to the frame to provide a familiar container
        mainPanel = new JPanel();
        setContentPane(mainPanel);

        //maximize the jframe
        setExtendedState(getExtendedState() | MAXIMIZED_BOTH);

    }// end of MainFrame::setUpFrame
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainFrame::setupGUI
    //
    // Sets up the user interface on the mainPanel: buttons, displays, etc.
    //

    private void setupGui()
    {

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        //add padding/margins to the main panel
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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

    }// end of MainFrame::setupGui
    //--------------------------------------------------------------------------

}//end of class MainFrame
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
