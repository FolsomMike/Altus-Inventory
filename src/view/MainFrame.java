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

import hscomponents.table.hsTable;
import jsplitbutton.JSplitButton;
import java.awt.Color;
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
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
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
    // The display panel dsiplays all of the pipe in the yard to the user.
    //

    private JPanel createDisplayPanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);

        //add view combo box
        panel.add(createViewComboBox());

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
        btn.addActionListener(mainView);
        btn.setActionCommand("Create Invoice");
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
        menu.add(new JMenuItem("Edit Invoice"));
        menu.add(new JMenuItem("Delete Invoice"));
        menu.add(new JMenuItem("View All Invoices"));
        menu.setBorder(new BevelBorder(BevelBorder.RAISED));
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
        btn.setActionCommand("Create Report");
        btn.addActionListener(mainView);
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
        menu.add(new JMenuItem("Receiving Report"));
        menu.add(new JMenuItem("Shipping Report"));
        menu.add(new JMenuItem("Tally Report"));
        menu.add(new JMenuItem("Rack Report"));
        menu.add(new JMenuItem("Current Balance Report"));
        menu.setBorder(new BevelBorder(BevelBorder.RAISED));
        btn.setPopupMenu(menu);
        
        return btn;

    }// end of MainFrame::createCreateReportButton
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
        btn.setActionCommand("Make Payment");
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

        hsTable table = new hsTable();
        table.init();

        //change the background color of the header
        table.getTableHeader().setBackground(Color.decode("#C2E0FF"));
        table.getTableHeader().setFont(new Font("Times Roman", Font.BOLD, 15));
        table.setRowHeight(25);

        //make it so that the user can't reorder the columns
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionBackground(Color.decode("#000099"));
        table.setSelectionForeground(Color.WHITE);

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
        table.addRow(row);
        table.addRow(row2);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);
        table.addRow(row);

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
        JButton btn = new JButton("<html><center>Move<br>Material</html>", 
                                createImageIcon("images/moveMaterial.png"));
        btn.addActionListener(mainView);
        btn.setActionCommand("Move Material");
        btn.setAlignmentX(LEFT_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setMargin(new Insets(0,0,0,0));
        btn.setToolTipText("Move material to a different rack.");
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);        
        Tools.setSizes(btn, 70, 75);
        
        return btn;

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
        btn.setActionCommand("Receive Material");
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
        JButton btn = new JButton("<html><center>Reserve<br>Material</html>", 
                                createImageIcon("images/reserveMaterial.png"));
        btn.addActionListener(mainView);
        btn.setActionCommand("Reserve Material");
        btn.setAlignmentX(LEFT_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setMargin(new Insets(0,0,0,0));
        btn.setToolTipText("Reserve material for future use.");
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);        
        Tools.setSizes(btn, 70, 75);
        
        return btn;

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
        JButton btn = new JButton("<html><center>Ship<br>Material</html>", 
                                createImageIcon("images/shipMaterial.png"));
        btn.addActionListener(mainView);
        btn.setActionCommand("Ship Material");
        btn.setAlignmentX(LEFT_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setMargin(new Insets(0,0,0,0));
        btn.setToolTipText("Ship material from the yard.");
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);        
        Tools.setSizes(btn, 70, 75);
        
        return btn;

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
        JButton btn = new JButton("<html><center>Transfer<br>Material</html>", 
                                createImageIcon("images/transferMaterial.png"));
        btn.addActionListener(mainView);
        btn.setActionCommand("Transfer Material");
        btn.setAlignmentX(LEFT_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setMargin(new Insets(0,0,0,0));
        btn.setToolTipText("Transfer material from one customer to another.");
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);        
        Tools.setSizes(btn, 70, 75);
        
        return btn;

    }// end of MainFrame::createTransferMaterialButton
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainFrame::createViewComboBox
    //
    // Creates and returns the View combo box.
    //
    // The combo box is used to select what material the user wants to view.
    //      
    //

    private JComboBox createViewComboBox()
    {

        //populate an array of strings with items for the combo box
        String[] strings = { "All Material in Yard", "Available in Stock", 
                                "Reserved", "Shipped", "Moved", "Transferred" };

        //Create the combo box, select item at index 0
        JComboBox combo = new JComboBox(strings);
        combo.addActionListener(mainView);     
        combo.setActionCommand("Change View");
        combo.setAlignmentX(LEFT_ALIGNMENT);
        combo.setAlignmentY(TOP_ALIGNMENT);   
        combo.setSelectedIndex(0);
        Tools.setSizes(combo, 135, 20);

        return combo;

    }// end of MainFrame::createViewComboBox
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
