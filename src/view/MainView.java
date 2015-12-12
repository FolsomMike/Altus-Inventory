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

import command.CommandListener;
import command.Command;
import model.MySQLDatabase;
import view.barebones.DisplayBareBones;
import view.classic.DisplayClassic;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class MainView
//

public class MainView implements CommandListener
{
    
    private final MySQLDatabase db;
    private final String displayMode;
    private final String displayModeBareBones = "BareBones";
    private final String displayModeClassic = "Classic";

    //--------------------------------------------------------------------------
    // MainView::MainView (constructor)
    //

    public MainView(MySQLDatabase pDatabase)
    {

        db = pDatabase;
        
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
    // MainView::commandPerformed
    //
    // Performs different actions depending on pCommand.
    //
    // The function will do nothing if pCommand was not intended for view.
    //
    // Called by the CommandHandler everytime a view command is performed.
    //

    @Override
    public void commandPerformed(String pCommand)
    {
        
        if (!Command.isControllerCommand(pCommand)) { return; }

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
            case displayModeBareBones:
                DisplayBareBones b = new DisplayBareBones();
                b.init();
                break;
                
            case displayModeClassic:
                DisplayClassic c = new DisplayClassic();
                c.init();
        }

    }//end of MainView::setupDisplay
    //--------------------------------------------------------------------------

}//end of class MainView
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
