/*******************************************************************************
* Title: DisplayClassic.java
* Author: Hunter Schoonover
* Date: 12/11/15
*
* Purpose:
*
* This class is the Classic display version. It is the GUI that will be used for
* the original version of the program.
* 
* This class extends JFrame to become the main window of the program.
*
*/

//------------------------------------------------------------------------------

package view.classic;

//------------------------------------------------------------------------------

import command.Command;
import command.CommandHandler;
import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import shared.Record;
import toolkit.Tools;
import view.MainView;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class DisplayClassic
//

public class DisplayClassic extends JFrame implements CommandHandler,
                                                            ActionListener
{
    
    private final JPanel mainPanel;
    
    private CommandHandler downStream;
    
    private DatabaseErrorWindow dbErrorWindow;

    //--------------------------------------------------------------------------
    // DisplayClassic::DisplayClassic (constructor)
    //

    public DisplayClassic()
    {
        
        mainPanel = new JPanel();

    }//end of DisplayClassic::DisplayClassic (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // DisplayClassic::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    public void init()
    {
        
        //set up the frame
        setUpFrame();
        
        //create and add the GUI
        createGui();
        
        //arrange all the GUI items
        pack();

        //display the frame
        setVisible(true);
        
        //set up the database error window
        dbErrorWindow = new DatabaseErrorWindow(this, this);
        dbErrorWindow.init();

    }// end of DisplayClassic::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DisplayClassic::handleCommand
    //
    // Performs different actions depending on pCommand.
    //
    
    @Override
    public void handleCommand(Command pCommand) 
    {
        
        switch (pCommand.getMessage()) {
            
            case Command.DB_CONNECTION_ERROR:
                dbErrorWindow.displayNoDatabaseConnection();
                break;
            
            case Command.DB_CONNECTION_FIXED:
                dbErrorWindow.setVisible(false);
                break;
                
            //customer display actions
            case "display customers window":
                displayCustomersFrame();
                break;
                
            //rack display actions
            case "display racks window":
                displayRacksWindow();
                break;
                
            //settings display actions
            case "display customer descriptors window":
                displayCustomerDescriptorsFrame();
                break;
                
            //display receive material window
            case "display receive window":
                displayRecieveMaterialWindow();
                break;
                
            //display move material window
            case "display move window":
                displayMoveMaterialWindow();
                break;
                
            //display transfer material window
            case "display transfer window":
                displayTransferMaterialWindow();
                break;
                
        }
        
        //pass the command downstream
        if (downStream != null) { downStream.handleCommand(pCommand); }
        
    }//end of DisplayClassic::handleCommand
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DisplayClassic::actionPerformed
    //
    // Creates a command using the action event message and performs it.
    //

    @Override
    public void actionPerformed(ActionEvent e)
    {

        (new Command(e.getActionCommand())).perform();
        
    }//end of DisplayClassic::actionPerformed
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
                                            "display receive window",
                                            "Receive material into the yard."));
        
        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));
        
        //add Ship Material button
        panel.add(createControlPanelButton("Ship<br>Material", 
                                            "images/shipMaterial.png",
                                            "display ship window",
                                            "Ship material from the yard."));
        
        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));
        
        //add Move Material button
        panel.add(createControlPanelButton("Move<br>Material", 
                                            "images/moveMaterial.png",
                                            "display move window",
                                            "Move the selected material to a "
                                                + "different rack."));
        
        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));
        
        //add Transfer Material button
        panel.add(createControlPanelButton("Transfer<br>Material", 
                                            "images/transferMaterial.png",
                                            "display transfer window",
                                            "Transfer the selected material to "
                                                + "a different customer."));
        
        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));
        
        //add Reserve Material button
        panel.add(createControlPanelButton("Reserve<br>Material", 
                                            "images/reserveMaterial.png",
                                            "display reserve window",
                                            "Reserve the selected material for "
                                                + "future use."));
        
        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));
        
        //add Material Info button
        panel.add(createControlPanelButton("Material<br>Info", 
                                            "images/materialInfo.png",
                                            "display material info window",
                                            "View and edit information about "
                                                + "the selected material."));
        
        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));

        //add Create Report button
        panel.add(createControlPanelButton("Create<br>Report", 
                                            "images/createReport.png",
                                            "display create report window",
                                            "Create a report."));

        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));

        //add Create Invoice button
        panel.add(createControlPanelButton("Create<br>Invoice", 
                                            "images/createInvoice.png",
                                            "display create invoice window",
                                            "Create an invoice."));

        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));

        //add Make Payment button
        panel.add(createControlPanelButton("Make<br>Payment", 
                                            "images/makePayment.png",
                                            "display make payment window",
                                            "Record payment from a customer."));

        return panel;

    }// end of MainFrame::createControlPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::createControlPanelButton
    //
    // Creates and returns a button to be used in the control panel part of the
    // main window, using the parameters for individuality.
    //

    private JButton createControlPanelButton(String pText, String pImagePath,
                                                String pActionCommand,
                                                String pTip)
    {

        //create button
        JButton btn = new JButton("<html><center>" + pText + "</center></html>", 
                                        createImageIcon(pImagePath));
        btn.setAlignmentX(LEFT_ALIGNMENT);
        btn.addActionListener(this);
        btn.setActionCommand(pActionCommand);
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
    // MainFrame::createGui
    //
    // Creates and adds the GUI to the window.
    //

    private void createGui()
    {
        
        //add the main menu
        MainMenu m = new MainMenu(this);
        m.init();
        setJMenuBar(m);
        
        //add padding to the main panel
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        //set the layout to add items top to bottom
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        //add the control panel to the gui
        mainPanel.add(createControlPanel());

    }// end of MainFrame::createGui
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

    private ImageIcon createImageIcon(String pPath)
    {

        // have to use the MainView class because it is located in the same 
        // package as the file; specifying the class specifies the first 
        // portion of the path to the image, this concatenated with the pPath
        java.net.URL imgURL = MainView.class.getResource(pPath);

        if (imgURL != null) { return new ImageIcon(imgURL); }
        else {return null;}

    }//end of MainFrame::createImageIcon
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DisplayClassic::displayCustomersFrame
    //
    // Displays the Customers window and sets it to the downstream
    //
    
    private void displayCustomersFrame()
    {
        
        RecordWindowInfo info = new RecordWindowInfo("Add Customer", 
                                "Edit Customer", "customer", "customers", 
                                Command.ADD_CUSTOMER, Command.DELETE_CUSTOMER,
                                Command.EDIT_CUSTOMER, Command.GET_CUSTOMERS, 
                                Command.CUSTOMER, Command.CUSTOMERS, 
                                Command.CUSTOMER_DESCRIPTORS);
        downStream = new RecordsWindow("Customers", this, this, info);
        ((RecordsWindow)downStream).init();
        
        //this will not be called until after the customers window closes
        downStream = null;

    }//end of DisplayClassic::displayCustomersFrame
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DisplayClassic::displayCustomerDescriptorsFrame
    //
    // Displays the Customer Descriptors window and sets it to the downstream.
    //
    
    private void displayCustomerDescriptorsFrame()
    {
        
        downStream = new DescriptorsWindow("Customer Descriptors", this, this,
                                            "customer", "Add Descriptor", 
                                            "Edit Descriptor");
        ((DescriptorsWindow)downStream).init();
        
        //this will not be called until after the customers window closes
        downStream = null;

    }//end of DisplayClassic::displayCustomerDescriptorsFrame
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DisplayClassic::displayDatabaseConnectionErrorWindow
    //
    // Displays the database connection error window to the user depending on
    // pDisplay.
    //
    // If pDisplay is true, then window is displayed; hidden if not.
    //
    
    private void displayDatabaseConnectionErrorWindow(boolean pDisplay)
    {
        
        //DEBUG HSS//

    }//end of DisplayClassic::displayDatabaseConnectionErrorWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DisplayClassic::displayMoveMaterialWindow
    //
    // Displays the Move Material window and sets it to the downstream.
    //
    
    private void displayMoveMaterialWindow()
    {
        
        //WIP HSS// -- batch should actually be selected from table. this is
        //              just for testing
        Record batch = new Record();
        batch.setSkoonieKey("1");
        
        downStream = new MoveMaterialWindow(this, this, batch);
        ((MoveMaterialWindow)downStream).init();
        
        //this will not be called until after the window closes
        downStream = null;

    }//end of DisplayClassic::displayMoveMaterialWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DisplayClassic::displayRacksWindow
    //
    // Displays the Racks window and sets it to the downstream.
    //
    
    private void displayRacksWindow()
    {
        
        RecordWindowInfo info = new RecordWindowInfo("Add Rack", 
                                "Edit Rack", "rack", "racks", 
                                Command.ADD_RACK, Command.DELETE_RACK,
                                Command.EDIT_RACK, Command.GET_RACKS, 
                                Command.RACK, Command.RACKS, 
                                Command.RACK_DESCRIPTORS);
        downStream = new RecordsWindow("Racks", this, this, info);
        ((RecordsWindow)downStream).init();
        
        //this will not be called until after the customers window closes
        downStream = null;

    }//end of DisplayClassic::displayRacksWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DisplayClassic::displayRecieveMaterialWindow
    //
    // Displays the Receive Material window and sets it to the downstream.
    //
    
    private void displayRecieveMaterialWindow()
    {
        
        downStream = new ReceiveMaterialWindow(this, this);
        ((ReceiveMaterialWindow)downStream).init();
        
        //this will not be called until after the window closes
        downStream = null;

    }//end of DisplayClassic::displayRecieveMaterialWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DisplayClassic::displayTransferMaterialWindow
    //
    // Displays the Transfer Material window and sets it to the downstream.
    //
    
    private void displayTransferMaterialWindow()
    {
        
        //WIP HSS// -- batch should actually be selected from table. this is
        //              just for testing
        Record batch = new Record();
        batch.setSkoonieKey("1");
        
        downStream = new TransferMaterialWindow(this, this, batch);
        ((TransferMaterialWindow)downStream).init();
        
        //this will not be called until after the window closes
        downStream = null;

    }//end of DisplayClassic::displayTransferMaterialWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::setUpFrame
    //
    // Sets up the frame by setting various options and styles.
    //

    private void setUpFrame()
    {

        //set the title of the frame
        setTitle("Altus Inventory");

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

        //exit the program when the window closes
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //add a JPanel to the frame to provide a familiar container
        setContentPane(mainPanel);

        //maximize the frame
        setExtendedState(getExtendedState() | MAXIMIZED_BOTH);

    }// end of MainFrame::setUpFrame
    //--------------------------------------------------------------------------

}//end of class DisplayClassic
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------