/*******************************************************************************
* Title: MainView.java
* Author: Hunter Schoonover
* Date: 12/06/15
*
* Purpose:
*
* This class is the MainView in a Model-View-Controller architecture. 
* 
* It receives commands from the MainController. When it receives commands, it
* can choose to perform actions or pass it on to the MainDisplay command handler
* or do both.
* 
* It sends commands to the MainController via the MainController's
* CommandHandler passed in upon construction.
* 
* It knows about the Model, and the Command Handler for the Controller, but not
* the Controller itself.
*
*/

//------------------------------------------------------------------------------

package view;

//------------------------------------------------------------------------------

import aa_altusinventory.CommandHandler;
import view.barebones.DisplayBareBones;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class MainView
//

public class MainView implements CommandHandler
{

    private final CommandHandler controller;
    
    private CommandHandler display;

    //--------------------------------------------------------------------------
    // MainView::MainView (constructor)
    //

    public MainView(CommandHandler pControllerCommandHandler)
    {

        controller = pControllerCommandHandler;

    }//end of MainView::MainView (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainView::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    @Override
    public void init()
    {
        
        //initialize the display
        display = new DisplayBareBones(this);
        display.init();

    }// end of MainView::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainView::performCommand
    //
    // Decides what actions to take depending on pCommand.
    //
    // If pCommand is intended for the controller, then it will be passed on to
    // to the controller.
    //
    // If pCommand is intended for the view, then it will be passed on to
    // to the dispaly or a generic action will be taken here.
    //

    @Override
    public void performCommand(String pCommand)
    {
        
        if (pCommand.startsWith("controller|")) {
            controller.performCommand(pCommand);
        }
        
        else if (pCommand.startsWith("view|")) {
            display.performCommand(pCommand);
        }

    }// end of MainView::performCommand
    //--------------------------------------------------------------------------

}//end of class MainView
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
