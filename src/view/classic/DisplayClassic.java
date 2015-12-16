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

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class DisplayClassic
//

public class DisplayClassic implements CommandHandler, ActionListener
{
    
    private final MainFrame mainFrame;
    private CustomersWindow customersWindow;

    //--------------------------------------------------------------------------
    // DisplayClassic::DisplayClassic (constructor)
    //

    public DisplayClassic()
    {
        
        super();
        
        mainFrame = new MainFrame(this);

    }//end of DisplayClassic::DisplayClassic (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // DisplayClassic::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    public void init()
    {
        
        //initialize the MainFrame
        mainFrame.init();

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
            
            //customer display actions
            case "display customers window":
                displayCustomersFrame();
                break;
                
        }
        
        //pass the command to all command handlers that aren't null
        //DEBUG HSS//if (mainFrame != null) { mainFrame.handleCommand(pCommand); }
        if (customersWindow != null) {customersWindow.handleCommand(pCommand);}
        
    }//end of DisplayClassic::handleCommand
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DisplayClassic::displayCustomersFrame
    //
    // Displays the Customers window.
    //
    
    private void displayCustomersFrame()
    {
        
        customersWindow = new CustomersWindow(mainFrame, this);
        customersWindow.init();

    }//end of DisplayClassic::displayCustomersFrame
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