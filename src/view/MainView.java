/*******************************************************************************
* Title: MainView.java
* Author: Hunter Schoonover
* Date: 12/06/15
*
* Purpose:
* 
* //WIP HSS//
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

import command.Command;
import command.CommandHandler;
import view.classic.DisplayClassic;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class MainView
//

public class MainView implements CommandHandler
{
    
    private final CommandHandler controller;
    
    private CommandHandler display;
    private final String displayMode;
    private final String displayModeBareBones = "BareBones";
    private final String displayModeClassic = "Classic";

    //--------------------------------------------------------------------------
    // MainView::MainView (constructor)
    //

    public MainView(CommandHandler pController)
    {
        
        controller = pController;
        
        displayMode = displayModeClassic;
        
    }//end of MainView::MainView (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainView::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    public void init()
    {
        
        setupDisplay();

    }// end of MainView::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainView::handleCommand
    //
    // Performs different actions depending on pCommand.
    //
    // Command will always be sent down the chain to display after handling has
    // been done here.
    //

    @Override
    public void handleCommand(Command pCommand)
    {
        
        //hand the command down to the display
        display.handleCommand(pCommand);

    }//end of MainView::commandPerformed
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainView::setupDisplay
    //
    // Uses the display mode to determien which display to use.
    //

    private void setupDisplay()
    {
        
        switch (displayMode) {  
            case displayModeClassic:
                display = new DisplayClassic();
                ((DisplayClassic)display).init();
                break;
        }

    }//end of MainView::setupDisplay
    //--------------------------------------------------------------------------

}//end of class MainView
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
