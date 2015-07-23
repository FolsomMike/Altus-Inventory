/******************************************************************************
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

//-----------------------------------------------------------------------------

package view;

//-----------------------------------------------------------------------------

import jsplitbutton.JSplitButton;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.font.TextAttribute;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import toolkit.Tools;

//-----------------------------------------------------------------------------
// class MainFrame
//

public class MainFrame extends JFrame
{
    
    private final MainView mainView;
    private MainMenu mainMenu;
    private JPanel mainPanel;

    private JTextField dataVersionTField;
    private JTextField dataTArea1;
    private JTextField dataTArea2;

    private GuiUpdater guiUpdater;
    private Help help;
    private About about;

    private javax.swing.Timer mainTimer;

    private Font blackSmallFont, redSmallFont;
    private Font redLargeFont, greenLargeFont, yellowLargeFont, blackLargeFont;

    private JLabel statusLabel, infoLabel;
    private JLabel progressLabel;

//-----------------------------------------------------------------------------
// MainFrame::MainFrame (constructor)
//

public MainFrame(MainView pMainView)
{
    
    mainView = pMainView;

}//end of MainFrame::MainFrame (constructor)
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// MainFrame::init
//
// Initializes the object.  Must be called immediately after instantiation.
//

public void init()
{

    setUpFrame();

    //create an object to handle thread safe updates of GUI components
    guiUpdater = new GuiUpdater(this);
    guiUpdater.init();
    
    //add a menu to the main form, passing this as the action listener
    setJMenuBar(mainMenu = new MainMenu(mainView));

    //create user interface: buttons, displays, etc.
    setupGui();

    //arrange all the GUI items
    pack();

    //display the main frame
    setVisible(true);

}// end of MainFrame::init
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
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
    
    //add material buttons panel
    panel.add(createMaterialButtonsPanel());
    
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
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// MainFrame::createDisplayPanel
//
// Creates and returns the display panel.
//
// The display panel dsiplays all of the pipe in the yard to the user.
//

private JPanel createDisplayPanel()
{
    
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
    panel.setAlignmentX(LEFT_ALIGNMENT);
    panel.setAlignmentY(TOP_ALIGNMENT);
    
    //add materials table panel
    panel.add(createMaterialsTablePanel());
    
    return panel;

}// end of MainFrame::createDisplayPanel
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// MainFrame::createFonts
//
// Creates fonts for use by the program.
//

public void createFonts()
{

    //create small and large red and green fonts for use with display objects
    HashMap<TextAttribute, Object> map = new HashMap<>();

    blackSmallFont = new Font("Dialog", Font.PLAIN, 12);

    map.put(TextAttribute.FOREGROUND, Color.RED);
    redSmallFont = blackSmallFont.deriveFont(map);

    //empty the map to use for creating the large fonts
    map.clear();

    blackLargeFont = new Font("Dialog", Font.PLAIN, 20);

    map.put(TextAttribute.FOREGROUND, Color.GREEN);
    greenLargeFont = blackLargeFont.deriveFont(map);

    map.put(TextAttribute.FOREGROUND, Color.RED);
    redLargeFont = blackLargeFont.deriveFont(map);

    map.put(TextAttribute.FOREGROUND, Color.YELLOW);
    yellowLargeFont = blackLargeFont.deriveFont(map);

}// end of MainFrame::createFonts
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// MainFrame::createImageIcon
//
// Returns an ImageIcon, or null if the path was invalid.
//
// ***************************************************************************
// NOTE: You must use forward slashes in the path names for the resource
// loader to find the image files in the JAR package.
// ***************************************************************************
//

protected static ImageIcon createImageIcon(String pPath)
{

    // have to use the MainFrame class because it is located in the same 
    // package as the file; specifying the class specifies the first portion of 
    // the path to the image, this concatenated with the pPath
    java.net.URL imgURL = MainFrame.class.getResource(pPath);

    if (imgURL != null) { return new ImageIcon(imgURL); }
    else {return null;}

}//end of MainFrame::createImageIcon
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// MainFrame::createCreateInvoiceButton
//
// Creates and returns the Create Invoice button.
//

private JButton createCreateInvoiceButton()
{
    
    //create button
    JSplitButton btn = new JSplitButton("<html><center>Create<br>Invoice</html>", 
                            createImageIcon("images/createInvoice.png"));
    Tools.setSizes(btn, 70, 75);
    btn.setVerticalTextPosition(SwingConstants.BOTTOM);
    btn.setHorizontalTextPosition(SwingConstants.CENTER);
    btn.setActionCommand("Create Invoice");
    btn.addActionListener(mainView);
    btn.setToolTipText("Create an invoice for a customer.");
    btn.setAlignmentX(LEFT_ALIGNMENT);
    btn.setFocusPainted(false);
    btn.setMargin(new Insets(0,0,0,20));
    btn.setArrowSize(10);
    JPopupMenu menu = new JPopupMenu();
    menu.add(new JMenuItem("Edit Invoice"));
    menu.add(new JMenuItem("Delete Invoice"));
    menu.add(new JMenuItem("View All Invoices"));
    menu.setBorder(new BevelBorder(BevelBorder.RAISED));
    btn.setPopupMenu(menu);
    return btn;

}// end of MainFrame::createCreateInvoiceButton
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// MainFrame::createCreateReportButton
//
// Creates and returns the Create Report button.
//

private JButton createCreateReportButton()
{

    //create button
    JSplitButton btn = new JSplitButton("<html><center>Create<br>Report</html>", 
                            createImageIcon("images/createReport.png"));
    Tools.setSizes(btn, 70, 75);
    btn.setVerticalTextPosition(SwingConstants.BOTTOM);
    btn.setHorizontalTextPosition(SwingConstants.CENTER);
    btn.setActionCommand("Create Report");
    btn.addActionListener(mainView);
    btn.setToolTipText("Create report.");
    btn.setAlignmentX(LEFT_ALIGNMENT);
    btn.setFocusPainted(false);
    btn.setMargin(new Insets(0,0,0,20));
    btn.setArrowSize(10);
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
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// MainFrame::createMakePaymentButton
//
// Creates and returns the Make Payment button.
//

private JButton createMakePaymentButton()
{
    
    //create button
    JButton btn = new JButton("<html><center>Make<br>Payment</html>", 
                            createImageIcon("images/makePayment.png"));
    Tools.setSizes(btn, 70, 75);
    btn.setVerticalTextPosition(SwingConstants.BOTTOM);
    btn.setHorizontalTextPosition(SwingConstants.CENTER);
    btn.setActionCommand("Make Payment");
    btn.addActionListener(mainView);
    btn.setToolTipText("Record payment from customer.");
    btn.setAlignmentX(LEFT_ALIGNMENT);
    btn.setFocusPainted(false);
    btn.setMargin(new Insets(0,0,0,0));
    return btn;

}// end of MainFrame::createMakePaymentButton
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// MainFrame::createMaterialButtonsPanel
//
// Creates and returns a panel with buttons used for handling material.
//

private JPanel createMaterialButtonsPanel()
{

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
    panel.setAlignmentX(LEFT_ALIGNMENT);
    
    //add Receive Material button
    JButton receiveBtn = new JButton("<html><center>Receive<br>Material</html>", 
                            createImageIcon("images/receiveMaterial.png"));
    receiveBtn.setMargin(new Insets(0,0,0,0));
    Tools.setSizes(receiveBtn, 70, 75);
    receiveBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
    receiveBtn.setActionCommand("Receive Material");
    receiveBtn.addActionListener(mainView);
    receiveBtn.setToolTipText("Receive material into the yard.");
    receiveBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
    receiveBtn.setHorizontalTextPosition(SwingConstants.CENTER);
    receiveBtn.setFocusPainted(false);
    panel.add(receiveBtn);
    
    //horizontal spacer
    panel.add(Box.createRigidArea(new Dimension(10,0)));
    
    //add Ship Material button
    JButton shipBtn = new JButton("<html><center>Ship<br>Material</html>", 
                        createImageIcon("images/shipMaterial.png"));
    shipBtn.setMargin(new Insets(0,0,0,0));
    Tools.setSizes(shipBtn, 70, 75);
    shipBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
    shipBtn.setActionCommand("Ship Material");
    shipBtn.addActionListener(mainView);
    shipBtn.setToolTipText("Ship material from the yard.");
    shipBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
    shipBtn.setHorizontalTextPosition(SwingConstants.CENTER);
    shipBtn.setFocusPainted(false); 
    panel.add(shipBtn);
    
    //horizontal spacer
    panel.add(Box.createRigidArea(new Dimension(10,0)));
    
    //add Move Material button
    JButton moveBtn = new JButton("<html><center>Move<br>Material</html>", 
                        createImageIcon("images/moveMaterial.png"));
    moveBtn.setMargin(new Insets(0,0,0,0));
    Tools.setSizes(moveBtn, 70, 75);
    moveBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
    moveBtn.setActionCommand("Move Material");
    moveBtn.addActionListener(mainView);
    moveBtn.setToolTipText("Move material to a different rack.");
    moveBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
    moveBtn.setHorizontalTextPosition(SwingConstants.CENTER);
    moveBtn.setFocusPainted(false);
    panel.add(moveBtn);
    
    //horizontal spacer
    panel.add(Box.createRigidArea(new Dimension(10,0)));
    
    //add Transfer Material button
    JButton transferBtn = new JButton("<html><center>Transfer<br>Material</html>", 
                        createImageIcon("images/transferMaterial.png"));
    transferBtn.setMargin(new Insets(0,0,0,0));
    Tools.setSizes(transferBtn, 70, 75);
    transferBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
    transferBtn.setActionCommand("Transfer Material");
    transferBtn.addActionListener(mainView);
    transferBtn.setToolTipText("Transfer material from one customer to another.");
    transferBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
    transferBtn.setHorizontalTextPosition(SwingConstants.CENTER);
    transferBtn.setFocusPainted(false);
    panel.add(transferBtn);
    
    //horizontal spacer
    panel.add(Box.createRigidArea(new Dimension(10,0)));
    
    //add Reserve Material button
    JButton reserveBtn = new JButton("<html><center>Reserve<br>Material</html>", 
                        createImageIcon("images/reserveMaterial.png"));
    reserveBtn.setMargin(new Insets(0,0,0,0));
    Tools.setSizes(reserveBtn, 70, 75);
    reserveBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
    reserveBtn.setActionCommand("Reserve Material From Yard");
    reserveBtn.addActionListener(mainView);
    reserveBtn.setToolTipText("Reserve material for future use.");
    reserveBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
    reserveBtn.setHorizontalTextPosition(SwingConstants.CENTER);
    reserveBtn.setFocusPainted(false);
    panel.add(reserveBtn);
    
    return panel;

}// end of MainFrame::createMaterialButtonsPanel
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// MainFrame::createMaterialsTablePanel
//
// Creates and returns the materials table panel.
//
// The materials table displays all of the pipe in the yard to the user.
//

private JPanel createMaterialsTablePanel()
{
    
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setAlignmentX(LEFT_ALIGNMENT);
    panel.setAlignmentY(TOP_ALIGNMENT);
    
    //add view combo box
    panel.add(createViewComboBox());
    
    //vertical spacer
    panel.add(Box.createRigidArea(new Dimension(0,10)));
    
    //add table
    DefaultTableModel model = new DefaultTableModel();
    JTable table = new JTable(model);
    table.setBackground(Color.WHITE);
    //change the background color of the header
    table.getTableHeader().setBackground(Color.decode("#C2E0FF"));
    //make it so that the user can't reorder the columns
    table.getTableHeader().setReorderingAllowed(false);
    
    //add columns to the table
    model.addColumn("ID");
    model.addColumn("Company");
    model.addColumn("Date");
    model.addColumn("Rack");
    model.addColumn("Status");
    model.addColumn("Quantity");
    model.addColumn("Length");
    model.addColumn("Diameter");
    model.addColumn("Wall");
    model.addColumn("Grade");
    model.addColumn("Range");
    model.addColumn("Facility");
    
    //ZZZZZ
    //assign each column a custom cell renderer
    TableColumnModel mod = table.getColumnModel();
    HSTableCellRenderer ren = new HSTableCellRenderer();
    for (int i=0; i<mod.getColumnCount(); i++) {
        mod.getColumn(i).setCellRenderer(ren);
    }
    
    //add a test row to the table -- //DEBUG HSS//
    model.addRow(new String[] {"1234", "RG NDT", "07/21/15", "4D", "RESERVED", "25", "1000", "", "", "13-CR", "R2", ""});
    model.addRow(new String[] {"1234", "RG NDT", "07/21/15", "4D", "RESERVED", "25", "1000", "", "", "13-CR", "R2", ""});
    model.addRow(new String[] {"1234", "RG NDT", "07/21/15", "4D", "RESERVED", "25", "1000", "", "", "13-CR", "R2", ""});
    
    
    //put the table in a scroll pane
    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setAlignmentX(LEFT_ALIGNMENT);
    scrollPane.setAlignmentY(TOP_ALIGNMENT);
    panel.add(scrollPane);
    
    return panel;

}// end of MainFrame::createMaterialsTablePanel
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
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
    combo.setSelectedIndex(0);
    Tools.setSizes(combo, 135, 20);
    combo.setAlignmentX(LEFT_ALIGNMENT);
    combo.setAlignmentY(TOP_ALIGNMENT);
    combo.addActionListener(mainView);
    combo.setActionCommand("Change View");
    
    return combo;

}// end of MainFrame::createViewComboBox
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// MainFrame::displayHelp
//
// Displays help information.
//

public void displayHelp()
{

    help = new Help(this);
    help = null;  //window will be released on close, so point should be null

}//end of MainFrame::displayHelp
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// MainFrame::displayAbout
//
// Displays about information.
//

public void displayAbout()
{

    about = new About(this);
    about = null;  //window will be released on close, so point should be null

}//end of MainFrame::displayAbout
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// MainFrame::displayErrorMessage
//
// Displays an error dialog with message pMessage.
//

public void displayErrorMessage(String pMessage)
{

    Tools.displayErrorMessage(pMessage, this);

}//end of MainFrame::displayErrorMessage
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// MainFrame::drawRectangle
//
// Draws a rectangle on mainPanel
//

public void drawRectangle()
{

    
    Graphics2D g2 = (Graphics2D)mainPanel.getGraphics();

     // draw Rectangle2D.Double
    g2.draw(new Rectangle2D.Double(20, 10,10, 10));
        
}//end of MainFrame::drawRectangle
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
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
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    //add a JPanel to the frame to provide a familiar container
    mainPanel = new JPanel();
    setContentPane(mainPanel);
    
    //maximize the jframe
    setExtendedState(getExtendedState() | MAXIMIZED_BOTH);

}// end of MainFrame::setUpFrame
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
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
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// MainFrame::HSTableCellRenderer
//
// This class is a custom table cell renderer, used to alternate the color of
// rows
//
    
class HSTableCellRenderer extends DefaultTableCellRenderer 
                                            implements TableCellRenderer 
{

    @Override
    public Component getTableCellRendererComponent 
        (JTable pTable, Object pValue, boolean pIsSelected, boolean pHasFocus, 
            int pRow, int pColumn) 
    {
        
        setBackground(null);
        
        super.getTableCellRendererComponent
                        (pTable, pValue, pIsSelected, pHasFocus, pRow, pColumn);
        
        //set the text of the cell
        setText(String.valueOf(pValue));
        
        //give the cell no border
        setBorder(noFocusBorder);
        
        //alternate the color of the rows
        //row number is odd
        if (pRow % 2 != 0) { setBackground(Color.decode("#E6E6E6")); }
        //row number is even
        else if (pRow % 2 == 0) { setBackground(Color.WHITE); }
        
        //if the cell is part of the selected row, highlight the cell
        if (pTable.hasFocus() && pRow == pTable.getSelectedRow()) {  
            setBackground(Color.BLUE);
        }
        
        //force the table to r
        if (!pTable.hasFocus()) {
            TableModel mod = pTable.getModel();
           // mod.getValueAt(TOP, TOP)
            
        }
        
        return this;
        
    }
        
    //This method updates the Row of table
    public void updateRow(int index,String[] values)
    {
        for (int i = 0 ; i < values.length ; i++)
        {
            //setValueAt(values[i],index,i);
        }
    }
    
}// MainFrame::HSTableCellRenderer
//-----------------------------------------------------------------------------

}//end of class MainFrame
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
