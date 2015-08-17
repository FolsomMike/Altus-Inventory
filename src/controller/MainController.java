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
* Open Source Policy:
*
* This source code is Public Domain and free to any interested party.  Any
* person, company, or organization may do with it as they please.
*
*/

//------------------------------------------------------------------------------

package controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import model.Options;
import view.MainView;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class MainController
//

public class MainController implements EventHandler, Runnable
{

    private MainView view;

    private Options options;

    private String errorMessage;

    private SwingWorker workerThread;

    private final DecimalFormat decimalFormat1 = new DecimalFormat("#.0");

    private Font tSafeFont;
    private String tSafeText;

    private int displayUpdateTimer = 0;

    private String XMLPageFromRemote;

    private boolean shutDown = false;

    private final String newline = "\n";

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
    // Initializes the object.  Must be called immediately after instantiation.
    //

    public void init()
    {

        view = new MainView(this);
        view.init();

        //create and load the program options
        options = new Options();

        //start the control thread
        new Thread(this).start();

        view.setupAndStartMainTimer();

    }// end of MainController::init
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainController::actionPerformed
    //
    // Responds to events.
    //
    // This is identical to the method employed by  ActionListener objects. 
    // This object is not an ActionListener, but uses the same concept for 
    // clarity. The "MainView" (MVC Concept) objects catch GUI events and call 
    // this method to pass those events to this "MainController" object.
    //

    @Override
    public void actionPerformed(ActionEvent e)
    {
        
        if ("MainFrame--Create Invoice".equals(e.getActionCommand())) {
            view.displayCreateInvoiceWindow();
        }
        
        if ("MainFrame--Create Invoice/View All Invoices".equals
            (e.getActionCommand())) 
        {
            view.displayInvoicesWindow();
        }
        
        if ("MainFrame--Create Report".equals(e.getActionCommand())) {
            view.displayCreateReportWindow();
        }
        
        if ("MainFrame--Make Payment".equals(e.getActionCommand())) {
            view.displayMakePaymentWindow();
        }
        
        if ("MainFrame--Move Material".equals(e.getActionCommand())) {
            view.displayMoveMaterialWindow();
        }
        
        if ("MainFrame--Receive Material".equals(e.getActionCommand())) {
            view.displayReceiveMaterialWindow();
        }
        
        if ("MainFrame--Reserve Material".equals(e.getActionCommand())) {
            view.displayReserveMaterialWindow();
        }
        
        if ("MainFrame--Ship Material".equals(e.getActionCommand())) {
            view.displayShipMaterialWindow();
        }
        
        if ("MainFrame--Transfer Material".equals(e.getActionCommand())) {
            view.displayTransferMaterialWindow();
        }
        
        if ("MainMenu--Display About".equals(e.getActionCommand())) {
            view.displayAbout();
        }
        
        if ("MainMenu--Display Help".equals(e.getActionCommand())) {
            view.displayHelp();
        }
        
        if ("MainMenu--Exit".equals(e.getActionCommand())) {
            shutDown();
        }

        if ("Timer".equals(e.getActionCommand())) { 
            doTimerActions(); 
        }

    }//end of MainController::actionPerformed
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
    // MainController::stateChanged
    //

    @Override
    public void stateChanged(ChangeEvent ce)

    {

    }//end of MainController::stateChanged
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainController::windowClosing
    //
    // Handles actions necessary when the window is closing
    //

    @Override
    public void windowClosing(WindowEvent e)
    {

        //perform all shut down procedures

    }//end of MainController::windowClosing
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
    // MainController::displayErrorMessage
    //
    // Displays an error dialog with message pMessage.
    //

    public void displayErrorMessage(String pMessage)
    {

        view.displayErrorMessage(pMessage);

    }//end of MainController::displayErrorMessage
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainController::doSomethingInWorkerThread
    //
    // Does nothing right now -- modify it to call a function which takes a 
    // long time to finish. It will be run in a background thread so the GUI 
    // is still responsive.
    //
    // -- CHANGE THE NAME TO REFLECT THE ACTION BEING DONE --
    //

    private void doSomethingInWorkerThread()
    {

        //define and instantiate a worker thread to create the file


        //----------------------------------------------------------------------
        //class SwingWorker
        //

        workerThread = new SwingWorker<Void, String>() {
            @Override
            public Void doInBackground() {

                //do the work here by calling a function

                return(null);

            }//end of doInBackground

            @Override
            public void done() {

                //clear in progress message here if one is being displayed

                try {

                    //use get(); function here to retrieve results if necessary
                    //note that Void type here and above would be replaced with
                    //the type of variable to be returned

                    Void v = get();

                } catch (InterruptedException ignore) {}
                catch (java.util.concurrent.ExecutionException e) {
                    String why;
                    Throwable cause = e.getCause();
                    if (cause != null) {
                        why = cause.getMessage();
                    } else {
                        why = e.getMessage();
                    }
                    System.err.println("Error creating file: " + why);
                }//catch

            }//end of done

            @Override
            protected void process(java.util.List <String> pairs) {

                //this method is not used by this application as it 
                //is limited the publish method cannot be easily called 
                //outside the class, so messages are displayed using a 
                //ThreadSafeLogger object and status components are 
                //updated using a GUIUpdater object

            }//end of process

        };//end of class SwingWorker
        //----------------------------------------------------------------------

    }//end of MainController::doSomethingInWorkerThread
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainController::doTimerActions
    //
    // Performs actions driven by the timer.
    //
    // Not used for accessing network -- see run function for details.
    //

    public void doTimerActions()
    {


    }//end of MainController::doTimerActions
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

    //--------------------------------------------------------------------------
    // MainController::(various window listener functions)
    //
    // These functions are implemented per requirements of interface 
    // WindowListener but do nothing at the present time.  As code is added to 
    // each function, it should be moved from this section and formatted 
    // properly.
    //

    @Override
    public void windowActivated(WindowEvent e){}
    @Override
    public void windowDeactivated(WindowEvent e){}
    @Override
    public void windowOpened(WindowEvent e){}
    //@Override
    //public void windowClosing(WindowEvent e){}
    @Override
    public void windowClosed(WindowEvent e){}
    @Override
    public void windowIconified(WindowEvent e){}
    @Override
    public void windowDeiconified(WindowEvent e){}

    //end of MainController::(various window listener functions)
    //--------------------------------------------------------------------------
     
}//end of class MainController
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
