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
*/

//------------------------------------------------------------------------------

package view.classic;

//------------------------------------------------------------------------------

import command.Command;
import command.CommandHandler;
import command.CommandListener;
import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import toolkit.Tools;
import view.MainView;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class DisplayClassic
//

public class DisplayClassic extends JFrame implements CommandListener, 
                                                        ActionListener
{
    
    private final JPanel mainPanel;

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
        
        //register this as a view listener
        CommandHandler.registerViewListener(this);
        
        //set up the frame
        setUpFrame();
        
        //create and add the GUI
        createGui();
        
        //arrange all the GUI items
        pack();

        //display the frame
        setVisible(true);

    }// end of DisplayClassic::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DisplayClassic::commandPerformed
    //
    // Performs different actions depending on pCommand.
    //
    // The function will do nothing if pCommand was not intended for view or the
    // classic display class.
    //
    // Called by the CommandHandler everytime a view command is performed.
    //

    @Override
    public void commandPerformed(String pCommand)
    {
        
        //return if this is not a view command or a display classic command
        if(!Command.isViewCommand(pCommand) 
                || !pCommand.contains("display=Classic")) { return; }
        
        Map<String, String> command = Command.extractKeyValuePairs(pCommand);
        
        switch(command.get("action")) {
            case "display customers":
                displayCustomers();
                break;
        }

    }//end of DisplayClassic::commandPerformed
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DisplayClassic::actionPerformed
    //
    // Passes the action event message on to the command handler.
    //

    @Override
    public void actionPerformed(ActionEvent e)
    {
        
        CommandHandler.performCommand(e.getActionCommand());

    }//end of DisplayClassic::actionPerformed
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DisplayClassic::createControlPanel
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
                                            "Receive material into the yard."));
        
        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));
        
        //add Ship Material button
        panel.add(createControlPanelButton("Ship<br>Material", 
                                            "images/shipMaterial.png",
                                            "Ship material from the yard."));
        
        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));
        
        //add Move Material button
        panel.add(createControlPanelButton("Move<br>Material", 
                                            "images/moveMaterial.png",
                                            "Move the selected material to a "
                                                + "different rack."));
        
        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));
        
        //add Transfer Material button
        panel.add(createControlPanelButton("Transfer<br>Material", 
                                            "images/transferMaterial.png",
                                            "Transfer the selected material to "
                                                + "a different customer."));
        
        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));
        
        //add Reserve Material button
        panel.add(createControlPanelButton("Reserve<br>Material", 
                                            "images/reserveMaterial.png",
                                            "Reserve the selected material for "
                                                + "future use."));
        
        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));
        
        //add Material Info button
        panel.add(createControlPanelButton("Material<br>Info", 
                                            "images/materialInfo.png",
                                            "View and edit information about "
                                                + "the selected material."));
        
        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));

        //add Create Report button
        panel.add(createControlPanelButton("Create<br>Report", 
                                            "images/createReport.png",
                                            "Create a report."));

        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));

        //add Create Invoice button
        panel.add(createControlPanelButton("Create<br>Invoice", 
                                            "images/createInvoice.png",
                                            "Create an invoice."));

        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));

        //add Make Payment button
        panel.add(createControlPanelButton("Make<br>Payment", 
                                            "images/makePayment.png",
                                            "Record payment from a customer."));

        return panel;

    }// end of DisplayClassic::createControlPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DisplayClassic::createControlPanelButton
    //
    // Creates and returns a button to be used in the control panel part of the
    // main window, using the parameters for individuality.
    //

    private JButton createControlPanelButton(String pText, String pImagePath,
                                                String pTip)
    {

        //create button
        JButton btn = new JButton("<html><center>" + pText + "</center></html>", 
                                        createImageIcon(pImagePath));
        btn.setAlignmentX(LEFT_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setMargin(new Insets(0,0,0,0));
        btn.setToolTipText(pTip);
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);        
        Tools.setSizes(btn, 70, 75);
        
        return btn;

    }// end of DisplayClassic::createControlPanelButton
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DisplayClassic::createGui
    //
    // Creates and adds the GUI to the window.
    //

    private void createGui()
    {
        
        //add the menu
        Menu m = new Menu(this);
        m.init();
        setJMenuBar(m);
        
        //add padding to the main panel
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        //set the layout to add items top to bottom
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        //add the control panel to the gui
        mainPanel.add(createControlPanel());

    }// end of DisplayClassic::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DisplayClassic::createImageIcon
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

    }//end of DisplayClassic::createImageIcon
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DisplayClassic::displayCustomers
    //
    // Displays the Customers window.
    //

    private void displayCustomers()
    {

    }//end of DisplayClassic::displayCustomers
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DisplayClassic::setUpFrame
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

    }// end of DisplayClassic::setUpFrame
    //--------------------------------------------------------------------------

}//end of class DisplayClassic
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class Menu
//
// This class creates the main menu and sub menus for the main window.
//

class Menu extends JMenuBar
{
    
    private final ActionListener actionListener;
    
    JMenu viewMenu;
    JMenuItem customersMenuItem;

    //--------------------------------------------------------------------------
    // Menu::Menu (constructor)
    //

    public Menu(ActionListener pListener)
    {
        
        actionListener = pListener;

    }//end of Menu::Menu (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // Menu::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    public void init()
    {

        createViewMenu();

    }//end of Menu::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // Menu::isSelected
    //
    // Returns true is any of the top level menu items are selected.
    //
    // NOTE: this is a workaround for JMenuBar.isSelected which once true never
    // seems to go back false when the menu is no longer selected.
    //

    @Override
    public boolean isSelected()
    {

        //return true if any top level menu item is selected

        boolean selected = false;

        if (viewMenu.isSelected())
        {
            selected = true;
        }

        return selected;

    }//end of Menu::isSelected
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // Menu::createViewMenu
    //
    // Creates the Customer menu and adds it to the menu bar.
    //

    private void createViewMenu()
    {
        
        //View menu
        viewMenu = new JMenu("View");
        viewMenu.setMnemonic(KeyEvent.VK_V);
        viewMenu.setToolTipText("View");
        add(viewMenu);

        //View/Customers menu item
        customersMenuItem = new JMenuItem("Customers");
        customersMenuItem.setMnemonic(KeyEvent.VK_C);
        customersMenuItem.setToolTipText("View, edit, and delete customers.");
        String cmd = Command.createViewCommand("display=Classic|"
                                                + "action=display customers");
        customersMenuItem.setActionCommand(cmd);
        customersMenuItem.addActionListener(actionListener);
        viewMenu.add(customersMenuItem);

    }//end of Menu::createViewMenu
    //--------------------------------------------------------------------------

}//end of class Menu
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------