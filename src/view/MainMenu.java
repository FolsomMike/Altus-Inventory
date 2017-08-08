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

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class MainMenu
//
// This class creates the main menu and sub menus for the main form.
//

public class MainMenu extends JMenuBar
{

    ActionListener actionListener;

    JMenu fileMenu;
    JMenuItem exitMenuItem;
    
    JMenu customerMenu;
    JMenuItem viewAllCustomersMenuItem;

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
        viewAllCustomersMenuItem.setActionCommand
                                            ("MainMenu--View All Customers");
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
        exitMenuItem.setActionCommand("MainMenu--Exit");
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
        aboutMenuItem.setActionCommand("MainMenu--Display About");
        aboutMenuItem.addActionListener(actionListener);
        helpMenu.add(aboutMenuItem);

        //Help/Help menu item
        helpMenuItem = new JMenuItem("Help");
        helpMenuItem.setMnemonic(KeyEvent.VK_H);
        helpMenuItem.setToolTipText("Display the Help window.");
        helpMenuItem.setActionCommand("MainMenu--Display Help");
        helpMenuItem.addActionListener(actionListener);
        helpMenu.add(helpMenuItem);

    }//end of MainMenu::createHelpMenu
    //--------------------------------------------------------------------------

}//end of class MainMenu
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------