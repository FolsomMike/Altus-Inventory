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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import model.MySQLDatabase;
import view.Display;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class DisplayClassic
//

public class DisplayClassic extends Display implements ActionListener
{
    
    private final MySQLDatabase db;

    //--------------------------------------------------------------------------
    // DisplayClassic::DisplayClassic (constructor)
    //

    public DisplayClassic(CommandHandler pView, MySQLDatabase pDatabase)
    {
        
        super(pView);
        
        db = pDatabase;

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

    }// end of DisplayClassic::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DisplayClassic::displayCustomers
    //
    // Displays the Customers window.
    //

    @Override
    public void displayCustomers()
    {
        
        //WIP HSS// -- DO STUFF

    }//end of DisplayClassic::displayCustomers
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DisplayClassic::displayAddCustomer
    //
    // Displays the Add Customer window.
    //

    @Override
    public void displayAddCustomer()
    {
        
        //WIP HSS// -- put stuff here

    }//end of DisplayClassic::displayCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DisplayClassic::actionPerformed
    //
    // Passes the action event message to the controller.
    //

    @Override
    public void actionPerformed(ActionEvent e)
    {
        
        Map<String, String> command = new HashMap<>();
        
        command.put("action", e.getActionCommand());

        sendCommandToController(command);
        
    }//end of DisplayClassic::actionPerformed
    //--------------------------------------------------------------------------

}//end of class DisplayClassic
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------