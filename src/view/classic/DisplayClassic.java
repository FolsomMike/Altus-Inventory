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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.MySQLDatabase;
import view.Display;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class DisplayClassic
//

public class DisplayClassic extends Display implements ActionListener
{
    
    private final MySQLDatabase db;
    
    private final MainFrame mainFrame;
    private CustomersWindow customersWindow;

    //--------------------------------------------------------------------------
    // DisplayClassic::DisplayClassic (constructor)
    //

    public DisplayClassic(CommandHandler pView, MySQLDatabase pDatabase)
    {
        
        super(pView);
        
        db = pDatabase;
        
        mainFrame = new MainFrame(this, db);

    }//end of DisplayClassic::DisplayClassic (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // DisplayClassic::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    @Override
    public void init()
    {
        
        super.init();
        
        //initialize the MainFrame
        mainFrame.init();

    }// end of DisplayClassic::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DisplayClassic::displayCustomersFrame
    //
    // Displays the Customers window.
    //

    @Override
    public void displayCustomersFrame()
    {
        
        customersWindow = new CustomersWindow(mainFrame, db, this);
        customersWindow.init();

    }//end of DisplayClassic::displayCustomersFrame
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DisplayClassic::displayAddCustomerFrame
    //
    // Displays the Add Customer window.
    //

    @Override
    public void displayAddCustomerFrame()
    {
        
        //WIP HSS// -- put stuff here

    }//end of DisplayClassic::displayCustomerFrame
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

}//end of class DisplayClassic
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------