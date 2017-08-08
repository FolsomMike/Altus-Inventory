/*******************************************************************************
* Title: MainController.java
* Author: Hunter Schoonover
* Date: 07/25/15
*
* Purpose:
*
* This class is the Main Controller in a Model-View-Controller architecture.
* It creates the Model and the View.
* It tells the View to update its display of the data in the model.
* It handles user input from the View (button pushes, etc.)*
* It tells the Model what to do with its data based on these inputs and tells
*   the View when to update or change the way it is displaying the data.
* 
* There may be many classes in the controller package which handle different
* aspects of the control functions.
*
* In this implementation:
*   the Model knows only about itself
*   the View knows only about the Model and can get data from it
*   the Controller knows about the Model and the View and interacts with both
*
* In this specific MVC implementation, the Model does not send messages to
* the View -- it expects the Controller to trigger the View to request data
* from the Model when necessary.
*
* The View sends messages to the Controller in the form of action messages
* to an EventHandler object -- in this case the Controller is designated to the
* View as the EventHandler.
*
*/

//------------------------------------------------------------------------------

package controller;

import command.CommandHandler;
import command.Command;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import view.MainView;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import model.MainModel;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class MainController
//

public class MainController implements CommandHandler
{
    
    private final MainModel model = new MainModel();
    private CommandHandler downStream;

    //--------------------------------------------------------------------------
    // MainController::MainController (constructor)
    //

    public MainController()
    {

    }//end of MainController::MainController (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainController::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    public void init()
    {
        
        //register this as a command handler -- should be the only registered
        //one throughout the entire program
        Command.registerHandler(this);
        
        //set up the logger
        setupJavaLogger();
        
        //set up the model
        model.init();

        //set up the view
        downStream = new MainView();
        ((MainView)downStream).init();

    }// end of MainController::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainController::handleCommand
    //
    // Performs different actions depending on pCommand.
    //
    // Command will always be sent down the chain to view after handling has
    // been done here.
    //

    @Override
    public void handleCommand(Command pCommand)
    {
        
        //exit the program if necessary
        if (pCommand.getMessage().equals(Command.EXIT_PROGRAM)) { 
            System.exit(0);
        }
        
        //pass the command to model -- if he doesn't need to do anything
        //then he won't
        model.handleCommand(pCommand);
        
        //pass the command down the stream to view
        downStream.handleCommand(pCommand);

    }//end of MainController::commandPerformed
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainController::deleteFileIfOverSizeLimit
    //
    // If file pFilename is larger than pLimit, the file is deleted.
    //

    private void deleteFileIfOverSizeLimit(String pFilename, int pLimit)
    {

        //delete the logging file if it has become too large

        Path p1 = Paths.get(pFilename);

        try { if (Files.size(p1) > pLimit){ Files.delete(p1); } }
        catch(NoSuchFileException nsfe){
            //do nothing if file not found -- will be recreated as needed
        }
        catch (IOException e) {
            //do nothing if error on deletion -- will be deleted next time
        }

    }//end of MainController::deleteFileIfOverSizeLimit
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainController::logSevere
    //
    // Logs pMessage with level SEVERE using the Java logger.
    //

    void logSevere(String pMessage)
    {

        Logger.getLogger(getClass().getName()).log(Level.SEVERE, pMessage);

    }//end of MainController::logSevere
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainController::logStackTrace
    //
    // Logs stack trace info for exception pE with pMessage at level SEVERE 
    // using the Java logger.
    //

    void logStackTrace(String pMessage, Exception pE)
    {

        Logger.getLogger(getClass().getName()).log(Level.SEVERE, pMessage, pE);

    }//end of MainController::logStackTrace
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainController::setupJavaLogger
    //
    // Prepares the Java logging system for use. Output is directed to a file.
    //
    // Each time the method is called, it checks to see if the file is larger
    // than the maximum allowable size and deletes the file if so.
    //

    private void setupJavaLogger()
    {

        String logFilename = "Java Logger File.txt";

        //prevent the logging file from getting too big
        deleteFileIfOverSizeLimit(logFilename, 10000);

        //remove all existing handlers from the root logger (and thus all child
        //loggers) so the output is not sent to the console

        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        for(Handler handler : handlers) {
            rootLogger.removeHandler(handler);
        }

        //add a new handler to send the output to a file

        Handler fh;

        try {

            //write log to logFilename, 10000 byte limit on each file, rotate
            //between two files, append the the current file each startup

            fh = new FileHandler(logFilename, 10000, 2, true);

            //direct output to a file for the root logger and  all child loggers
            Logger.getLogger("").addHandler(fh);

            //use simple text output rather than default XML format
            fh.setFormatter(new SimpleFormatter());

            //record all log messages
            Logger.getLogger("").setLevel(Level.WARNING);

        }
        catch(IOException e){ }

    }//end of MainController::setupJavaLogger
    //--------------------------------------------------------------------------
    
}//end of class MainController
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------