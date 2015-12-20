/*******************************************************************************
* Title: MainMenu.java
* Author: Hunter Schoonover
* Date: 12/17/15
*
* Purpose:
*
* This class is the main menu of the program, intended for use as the menu the
* main window.
*
*/

//------------------------------------------------------------------------------

package view.classic;

//------------------------------------------------------------------------------

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class MainMenu
//
// This class creates the main menu and sub menus for the main window.
//

class MainMenu extends JMenuBar
{
    
    private final ActionListener actionListener;
    
    JMenu viewMenu;
    JMenuItem customersMenuItem;
    
    JMenu settingsMenu;
    JMenuItem settingsCustomerDescriptorsMenuItem;

    //--------------------------------------------------------------------------
    // MainMenu::MainMenu (constructor)
    //

    public MainMenu(ActionListener pListener)
    {
        
        actionListener = pListener;

    }//end of MainMenu::MainMenu (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainMenu::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    public void init()
    {
        
        addSpacer(5);

        createViewMenu();
        
        createSettingsMenu();

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

        if (viewMenu.isSelected() || settingsMenu.isSelected())
        {
            selected = true;
        }

        return selected;

    }//end of ::isSelected
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainMenu::addSpacer
    //
    // Creates and adds a spacer of pWidth to the menu bar.
    //

    private void addSpacer(int pWidth)
    {

        JMenu spacer = new JMenu();
        spacer.setEnabled(false);
        Tools.setSizes(spacer, pWidth, 1);
        add(spacer);

    }//end of MainMenu::addSpacer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainMenu::createSettingsMenu
    //
    // Creates the Settings menu and adds it to the menu bar.
    //

    private void createSettingsMenu()
    {
        
        //Settings menu
        settingsMenu = new JMenu("Settings");
        settingsMenu.setMnemonic(KeyEvent.VK_S);
        settingsMenu.setToolTipText("Settings");
        add(settingsMenu);

        //Settings/Customer Descriptors menu item
        settingsCustomerDescriptorsMenuItem 
                                    = new JMenuItem("Customer Descriptors");
        
        settingsCustomerDescriptorsMenuItem.setMnemonic(KeyEvent.VK_C);
        
        settingsCustomerDescriptorsMenuItem
                                    .setToolTipText("Customer Descriptors.");
        
        settingsCustomerDescriptorsMenuItem
                                    .setActionCommand("display customer "
                                                        + "descriptors window");
        settingsCustomerDescriptorsMenuItem.addActionListener(actionListener);
        settingsMenu.add(settingsCustomerDescriptorsMenuItem);

    }//end of MainMenu::createSettingsMenu
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainMenu::createViewMenu
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
        customersMenuItem.setActionCommand("display customers window");
        customersMenuItem.addActionListener(actionListener);
        viewMenu.add(customersMenuItem);

    }//end of MainMenu::createViewMenu
    //--------------------------------------------------------------------------

}//end of class MainMenu
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------