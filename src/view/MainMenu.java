/*******************************************************************************
* Title: MainMenu.java
* Author: Hunter Schoonover
* Date: 11/20/12
*
* Purpose:
*
* This class creates the main menu and sub-menus for the main form.
*
*/

//------------------------------------------------------------------------------

package view;

//------------------------------------------------------------------------------

import java.awt.event.*;
import javax.swing.*;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class MainMenu
//
// This class creates the main menu and sub menus for the main form.
//

public class MainMenu extends JMenuBar
{
    
    static private final String actionId = "MainMenu";
    static public String getActionId() { return actionId; }

    ActionListener actionListener;

    JMenu fileMenu;
    JMenuItem exitMenuItem;
    
    JMenu customerMenu;
    JMenuItem viewAllCustomersMenuItem;
    
    JMenu rackMenu;
    JMenuItem viewAllRacksMenuItem;
    
    JMenu truckMenu;
    JMenuItem viewAllTruckCompaniesMenuItem;

    JMenu helpMenu;
    JMenuItem aboutMenuItem, helpMenuItem;

    //--------------------------------------------------------------------------
    // MainMenu::MainMenu (constructor)
    //

    String language;

    public MainMenu(ActionListener pActionListener)
    {

        actionListener = pActionListener;

    }//end of MainMenu::MainMenu (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainMenu::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    public void init()
    {
        
        createFileMenu();

        createCustomerMenu();
        
        createRackMenu();
        
        createTruckMenu();

        createHelpMenu();

    }//end of MainMenu::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainMenu::isSelected
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

        if (fileMenu.isSelected() 
                || helpMenu.isSelected() 
                || customerMenu.isSelected())
        {
            selected = true;
        }

        return selected;

    }//end of MainMenu::isSelected
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainMenu::createCustomerMenu
    //
    // Creates the Customer menu and adds it to the menu bar.
    //

    private void createCustomerMenu()
    {
        
        //Customer menu
        customerMenu = new JMenu("Customer");
        customerMenu.setMnemonic(KeyEvent.VK_C);
        customerMenu.setToolTipText("Customer");
        add(customerMenu);

        //Customer/View All Customers menu item
        viewAllCustomersMenuItem = new JMenuItem("View All Customers");
        viewAllCustomersMenuItem.setToolTipText("View all customers.");
        viewAllCustomersMenuItem.setActionCommand(Tools.generateActionCommand
                                                    (actionId, 
                                                        "View All Customers"));
        viewAllCustomersMenuItem.addActionListener(actionListener);
        customerMenu.add(viewAllCustomersMenuItem);

    }//end of MainMenu::createCustomerMenu
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainMenu::createFileMenu
    //
    // Creates the File menu and adds it to the menu bar.
    //

    private void createFileMenu()
    {
        
        //File menu
        fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.setToolTipText("File");
        add(fileMenu);

        //File/Exit menu item
        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setMnemonic(KeyEvent.VK_X);
        exitMenuItem.setToolTipText("Exit the program.");
        exitMenuItem.setActionCommand(Tools.generateActionCommand(actionId, 
                                                                    "Exit"));
        exitMenuItem.addActionListener(actionListener);
        fileMenu.add(exitMenuItem);

    }//end of MainMenu::createFileMenu
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainMenu::createHelpMenu
    //
    // Creates the Help menu and adds it to the menu bar.
    //

    private void createHelpMenu()
    {
        
        //Help menu
        helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);
        helpMenu.setToolTipText("Help");
        add(helpMenu);

        //Help/About menu item
        aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.setMnemonic(KeyEvent.VK_A);
        aboutMenuItem.setToolTipText("Display the About window.");
        aboutMenuItem.setActionCommand(Tools.generateActionCommand
                                            (actionId, "Display About"));
        aboutMenuItem.addActionListener(actionListener);
        helpMenu.add(aboutMenuItem);

        //Help/Help menu item
        helpMenuItem = new JMenuItem("Help");
        helpMenuItem.setMnemonic(KeyEvent.VK_H);
        helpMenuItem.setToolTipText("Display the Help window.");
        helpMenuItem.setActionCommand(Tools.generateActionCommand
                                            (actionId, "Display Help"));
        helpMenuItem.addActionListener(actionListener);
        helpMenu.add(helpMenuItem);

    }//end of MainMenu::createHelpMenu
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainMenu::createRackMenu
    //
    // Creates the Rack menu and adds it to the menu bar.
    //

    private void createRackMenu()
    {
        
        //Rack menu
        rackMenu = new JMenu("Rack");
        rackMenu.setMnemonic(KeyEvent.VK_R);
        rackMenu.setToolTipText("Rack");
        add(rackMenu);

        //Rack/View All Racks menu item
        viewAllRacksMenuItem = new JMenuItem("View All Racks");
        viewAllRacksMenuItem.setToolTipText("View all racks.");
        viewAllRacksMenuItem.setActionCommand(Tools.generateActionCommand
                                                (actionId, "View All Racks"));
        viewAllRacksMenuItem.addActionListener(actionListener);
        rackMenu.add(viewAllRacksMenuItem);

    }//end of MainMenu::createRackMenu
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainMenu::createTruckMenu
    //
    // Creates the Truck menu and adds it to the menu bar.
    //

    private void createTruckMenu()
    {
        
        //Truck menu
        truckMenu = new JMenu("Truck");
        truckMenu.setMnemonic(KeyEvent.VK_T);
        truckMenu.setToolTipText("Truck Company");
        add(truckMenu);

        //Truck/View All Truck Companies menu item
        viewAllTruckCompaniesMenuItem 
                                = new JMenuItem("View All Truck Companies");
        viewAllTruckCompaniesMenuItem
                                .setToolTipText("View all truck companies.");
        viewAllTruckCompaniesMenuItem.setActionCommand
                                (Tools.generateActionCommand
                                    (actionId, "View All Truck Companies"));
        viewAllTruckCompaniesMenuItem.addActionListener(actionListener);
        truckMenu.add(viewAllTruckCompaniesMenuItem);

    }//end of MainMenu::createTruckMenu
    //--------------------------------------------------------------------------

}//end of class MainMenu
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------