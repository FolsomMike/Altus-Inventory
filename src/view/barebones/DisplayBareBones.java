/*******************************************************************************
* Title: DisplayBareBones.java
* Author: Hunter Schoonover
* Date: 12/06/15
*
* Purpose:
*
* This class is the BareBones version of the display. It the most bare-bone, 
* basic GUI possible: 
*   It reads input from the console and sends it directly to Controller
*   No information is displayed to the user except for "Next command:"
* 
* It receives commands from the DisplayBareBones.
* 
* It sends commands to the MainView via the passed in command handler. All
* button clicks, GUI changes, etc. should be sent to the MainView for 
* processing. Most, if not all of the commands will actually be sent back to
* this class via CommandHandler, although MainView may choose not to do so.
* 
* It knows about the CommandHandler for MainView, but nothing else.
*
*/

//------------------------------------------------------------------------------

package view.barebones;

//------------------------------------------------------------------------------

import aa_altusinventory.CommandHandler;
import java.util.Scanner;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class DisplayBareBones
//

public class DisplayBareBones implements CommandHandler
{

    private final CommandHandler view;

    //--------------------------------------------------------------------------
    // DisplayBareBones::DisplayBareBones (constructor)
    //

    public DisplayBareBones(CommandHandler pViewCommandHandler)
    {

        view = pViewCommandHandler;

    }//end of DisplayBareBones::DisplayBareBones (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // DisplayBareBones::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    @Override
    public void init()
    {
        
        //start listening for console input
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Next command:");
            view.performCommand(scanner.nextLine());
        }

    }// end of DisplayBareBones::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DisplayBareBones::performCommand
    //
    // Decides what actions to take depending on pCommand.
    //
    // The function will do nothing if pCommand does not start with "view|"
    // because only commands intended for view should be handled here.
    //

    @Override
    public void performCommand(String pCommand)
    {
        
        //return if we accidentally received a command that doesn't belong
        //to view
        if (!pCommand.startsWith("view|")) { return; }

    }// end of DisplayBareBones::performCommand
    //--------------------------------------------------------------------------

}//end of class DisplayBareBones
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
