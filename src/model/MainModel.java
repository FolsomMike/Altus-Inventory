/*******************************************************************************
* Title: MainModel.java
* Author: Hunter Schoonover
* Date: 12/14/15
*
* Purpose:
*
* //WIP HSS// -- describe this!!!!
* 
*/

//------------------------------------------------------------------------------

package model;

import command.Command;
import command.CommandHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class MainModel
//

public class MainModel implements CommandHandler, Runnable
{
    
    private final Thread thread;
    
    private final DatabaseHandler dbHandler = new DatabaseHandler();
    
    private Command command;
    synchronized public void setCommand(Command pC) { command = pC; }
    synchronized public Command getCommand() { return command; }
    synchronized public Command copyCommand() { 
        return command==null ? null:command.copy();
    }

    //--------------------------------------------------------------------------
    // MainModel::MainModel (constructor)
    //

    public MainModel()
    {
        
        thread = new Thread(this);

    }//end of MainModel::MainModel (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainModel::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    public void init()
    {
        
        //start the thread
        thread.start();

    }// end of MainModel::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainModel::handleCommand
    //
    // Stores pCommand in the commands list to be handled later. This can be
    // called from another thread so long as this only accesses variables
    // through synchronized functions.
    //

    @Override
    public void handleCommand(Command pCommand)
    {
        
        //set the command if it's handled by the database handler
        if (dbHandler.handlesCommand(pCommand)) { 
            setCommand(pCommand);
            //notify the thread to stop waiting
            synchronized(thread) { thread.notify(); }
        }

    }//end of MainModel::handleCommand
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainModel::run
    //
    // All actions here will run that in the thread that this class was passed
    // to.
    //

    @Override
    public void run()
    {
        
        //this will be only be called when the thread first starts
        dbHandler.init();
        
        //run through these actions continuously
        while (true) {
            
            //check the database connection every time the
            //thread loops throug            

            //handle the commmand if there is one or just check the database
            //connection if this is just a scheduled loop through
            Command c = copyCommand();
            if (c!=null) { dbHandler.handleCommand(c); setCommand(null); }
            else { dbHandler.checkDatabaseConnection(); }
            
            //WIP HSS// -- add a function to check the database for changes
            
            //this will cause to wait for 5 seconds or until notified before
            //looping through again
            makeThreadWait(5000);
        
        }

    }//end of MainModel::run
    //--------------------------------------------------------------------------    
    
    //--------------------------------------------------------------------------
    // MainModel::logSevere
    //
    // Logs pMessage with level SEVERE using the Java logger.
    //

    private void logSevere(String pMessage)
    {

        Logger.getLogger(getClass().getName()).log(Level.SEVERE, pMessage);

    }//end of MainModel::logSevere
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainModel::logStackTrace
    //
    // Logs stack trace info for exception pE with pMessage at level SEVERE 
    // using the Java logger.
    //

    private void logStackTrace(String pMessage, Exception pE)
    {

        Logger.getLogger(getClass().getName()).log(Level.SEVERE, pMessage, pE);

    }//end of MainModel::logStackTrace
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainController::makeThreadWait
    //
    // Causes the thread to wait for the specified amount of time or until it
    // is notified. The thread uses himself as the monitor.
    //

    private void makeThreadWait(int pWaitTime)
    {

        synchronized (thread) {
            try { thread.wait(pWaitTime); } catch (InterruptedException e) { }
        }

    }//end of MainController::makeThreadWait
    //--------------------------------------------------------------------------
    
}//end of class MainModel
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------