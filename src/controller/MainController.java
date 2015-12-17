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

public class MainController implements CommandHandler, Runnable
{
    
    private final MainModel model = new MainModel();
    private CommandHandler view;

    private int displayUpdateTimer = 0;

    private boolean shutDown = false;

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
        view = new MainView();
        ((MainView)view).init();

        //start the control thread
        new Thread(this).start();

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
        
        switch (pCommand.getMessage()) {
            
            case "empty database": //DEBUG HSS// -- testing purposes only
                model.emptyDatabase();
                break;
                
            //batch actions
            case "delete batch": //DEBUG HSS -- for testing purposes only
                break;
                
            case "move batch": //WIP HSS// -- move the batch
                break;
                
            case "receive batch": //WIP HSS// -- receive the batch
                break;
                
            case "update batch": //WIP HSS// -- update the batch
                break;
                
            //customer actions
            case "add customer": //WIP HSS// -- add the customer
                break;
                
            case "delete customer": //WIP HSS// -- delete the customer
                model.deleteCustomer((String)pCommand.get("customer key"));
                pCommand.put("customers", model.getCustomers());
                pCommand.setMessage("display customers");
                break;
                
            case "get customers":
                pCommand.put("customers", model.getCustomers());
                pCommand.setMessage("display customers");
                break;
                
            case "update customer": //WIP HSS// -- update the customer
                break;
                
            //descriptor actions
            case "add descriptor": //WIP HSS// -- add the descriptor
                break;
                
            case "delete descriptor": //WIP HSS// -- delete the descriptor
                break;
        }
        
        //pass the command down the chain to view
        view.handleCommand(pCommand);

    }//end of MainController::commandPerformed
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainController::run
    //
    // This is the part which runs as a separate thread.  The actions of 
    // accessing remote devices occur here.  If they are done in a timer call 
    // instead, then buttons and displays get frozen during the sometimes 
    // lengthy calls to access the network.
    //
    // NOTE:  All functions called by this thread must wrap calls to alter GUI
    // components in the invokeLater function to be thread safe.
    //

    @Override
    public void run()
    {

        //call the control method repeatedly
        while(true){

            control();

            //sleep for 2 seconds -- all timing is based on this period
            threadSleep(2000);

        }

    }//end of MainController::run
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainController::control
    //
    // Performs all display and control.  Call this from a thread.
    //

    public void control()
    {

        //update the display every 30 seconds
        if (displayUpdateTimer++ == 14){
            displayUpdateTimer = 0;
            //call function to update stuff here
        }

        //If a shut down is initiated, clean up and exit the program.

        if(shutDown){
            //exit the program
            System.exit(0);
        }

    }//end of MainController::control
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

    //--------------------------------------------------------------------------
    // MainController::shutDown
    //
    // Performs appropriate shut down operations.
    //
    // This is done by setting a flag so that this class's thread can do the
    // actual work, thus avoiding thread contention.
    //

    public void shutDown()
    {

        shutDown = true;

    }//end of MainController::shutDown
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainController::threadSleep
    //
    // Calls the Thread.sleep function. Placed in a function to avoid the
    // "Thread.sleep called in a loop" warning -- yeah, it's cheezy.
    //

    public void threadSleep(int pSleepTime)
    {

        try {Thread.sleep(pSleepTime);} catch (InterruptedException e) { }

    }//end of MainController::threadSleep
    //--------------------------------------------------------------------------
    
}//end of class MainController
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------