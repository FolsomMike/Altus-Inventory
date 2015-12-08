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

import aa_altusinventory.CommandHandler;
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
import model.MySQLDatabase;
import model.Record;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class MainController
//

public class MainController implements CommandHandler, Runnable
{

    private CommandHandler view;
    private final MySQLDatabase db = new MySQLDatabase();

    private int displayUpdateTimer = 0;

    private boolean shutDown = false;
    
    //command array index variables
    int controllerIndex     = 0;
    int recordTypeIndex     = 1;
    int actionIndex         = 2;
    int skoonieKeyIndex     = 3;
    //where the key-value pairs start when Skoonie Key is NOT there
    int noSkKeyAttrsIndex   = 3;
    //where the key-value pairs start when Skoonie Key is there
    int skKeyAttrsIndex     = 4;
    
    //Record attributes --  record attributes are the keys to search for in a
    //                      command array for a specific record type
    private final String[] batchAttributes =    { 
                                                    "customer_key",
                                                    "id",
                                                    "quantity",
                                                    "rack_key", 
                                                    "total_length"
                                                };
    
    private final String[] movementAttributes =  {};
    
    private final String[] receivementAttributes =  {
                                                        "rack_key",
                                                        "truck_key",
                                                        "truck_company_key", 
                                                        "truck_driver_key"
                                                    };
    
    //Table names -- back quotes so that they can be easily put in cmd strings
    private final String batchesTable = "`BATCHES`";
    private final String customersTable = "`CUSTOMERS`";
    private final String movementsTable = "`MOVEMENTS`";
    private final String racksTable = "`RACKS`";
    private final String receivementsTable = "`RECEIVEMENTS`";
    private final String truckCompaniesTable = "`TRUCK_COMPANIES`";
    private final String truckDriversTable = "`TRUCK_DRIVERS`";
    private final String trucksTable = "`TRUCKS`";

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

    @Override
    public void init()
    {
        
        //set up the logger
        setupJavaLogger();
        
        //initialize database
        db.init();

        //set up the view
        view = new MainView(this);
        view.init();

        //start the control thread
        new Thread(this).start();

    }// end of MainController::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainController::performCommand
    //
    // Performs different actions depending on pCommand.
    //
    // The function will do nothing if pCommand was not intended for controller.
    //

    @Override
    public void performCommand(String pCommand)
    {
        
        String[] command = pCommand.split("\\|");
        
        //return if not meant for controller
        if (!command[controllerIndex].equals("controller")) { return; }
        
        switch(command[recordTypeIndex]) {
            case "batch":
                handleBatchCommand(command);
        }

    }//end of MainController::performCommand
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
    // MainController::extractAttributes
    //
    // Extracts pAttributes from pCommand and puts them in pRec.
    //

    private void extractAttributes(Record pRec, String[] pCommand, 
                                            String[] pAttributes)
    {
        
        //return if there are no attributes to check for
        if (pAttributes.length <= 0) { return; }
        
        int attrsIndex = -1;
        for (int i=0; i<pCommand.length; i++) {
            if (pCommand[i].equals("begin attributes")) { attrsIndex = ++i; }
        }
        
        //return if there are no attributes in pCommand
        if (attrsIndex==-1) { return; }
        
        for (int i=attrsIndex; i<pCommand.length; i++) {
            
            //get key-value pair -- key index is 0; value index is 1
            String[] pair = pCommand[i].split(":");
            
            //check to see if key matches an attribute, store pair if it does
            for (String attr : pAttributes) {
                if (pair[0].equals(attr)) { 
                    pRec.addAttr(pair[0], pair[1]);
                    break;
                }
            }
            
        }

    }//end of MainController::extractAttributes
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
    // MainController::deleteRecord
    //
    // Deletes the Record associated with the Skoonie Key found in pCommand
    // from pTable.
    //

    private void deleteRecord(String[] pCommand, String pTable)
    {
        
        //extract the skoonie key from pCommand
        Record r = new Record();
        r.setSkoonieKey(pCommand[skoonieKeyIndex]);
        
        db.deleteRecord(r, pTable);

    }//end of MainController::deleteRecord
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainController::handleBatchCommand
    //
    // Performs different actions depending on pCommand.
    //

    private void handleBatchCommand(String[] pCommand)
    {
        
        switch(pCommand[actionIndex]) {
            case "delete":  deleteRecord(pCommand, batchesTable); break;
            case "move":    moveBatch(pCommand); break;
            case "receive": receiveBatch(pCommand); break;
            case "update":  updateRecord(pCommand, batchesTable); break;
        }

    }//end of MainController::handleBatchCommand
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainController::insertRecord
    //
    // Inserts a Record using the key-value pairs contained in pCommand into
    // pTable.
    //

    private void insertRecord(String[] pCommand, String pTable)
    {
        
        //extract the key-value pairs from pCommand
        Record r = new Record();
        for (int i=noSkKeyAttrsIndex; i<pCommand.length; i++) {
            String[] pairs = pCommand[i].split(":");
            r.addAttr(pairs[0], pairs[1]);
        }
        
        db.insertRecord(r, pTable);

    }//end of MainController::insertRecord
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
    // MainController::moveBatch
    //
    // Moves a batch using the information in pCommand.
    //
    // A record to document the movement is created and inserted into the 
    // movements table.
    //

    private void moveBatch(String[] pCommand)
    {
        
        //extract values from pCommand
        String batchKey     = pCommand[3];
        String moveId       = pCommand[4];
        String date         = pCommand[5];
        String toRackKey    = pCommand[6];
        String fromRackKey  = db.getRecord(batchKey, batchesTable)
                                                        .getAttr("rack_key");
        
        //verify the move
        if (!verifyMove(toRackKey, fromRackKey)) { return; }
       
        //document the movement
        Record moveRecord = new Record();
        moveRecord.addAttr("batch_key",      batchKey);
        moveRecord.addAttr("id",             moveId);
        moveRecord.addAttr("date",           date);
        moveRecord.addAttr("from_rack_key",  fromRackKey);
        moveRecord.addAttr("to_rack_key",    toRackKey);
        extractAttributes(moveRecord, pCommand, movementAttributes);
        db.insertRecord(moveRecord, movementsTable);
       
        //update the batch with the new rack
        Record batchRecord = new Record(batchKey);
        batchRecord.addAttr("rack_key", toRackKey);
        extractAttributes(batchRecord, pCommand, batchAttributes);
        db.updateRecord(batchRecord, batchesTable);

    }//end of MainController::moveBatch
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainController::receiveBatch
    //
    // Receives a batch using the information in pCommand.
    //
    // Two records are created and inserted into the database:
    //      one for a receivement
    //      one for a new batch
    //

    private void receiveBatch(String[] pCommand)
    {
        
        //extract values from pCommand
        String receiveId    = pCommand[3];
        String receiveDate  = pCommand[4];
        
        //record for the batch
        Record batchRecord = new Record();
        extractAttributes(batchRecord, pCommand, batchAttributes);
        
        //before we go any farther, verify the receivement
        if(!verifyReceivement(receiveId, batchRecord.getAttr("id"))) { return; }
       
        //record for the receivement
        Record receiveRecord = new Record();
        receiveRecord.addAttr("id", receiveId);
        receiveRecord.addAttr("date", receiveDate);
        extractAttributes(receiveRecord, pCommand, receivementAttributes);

        //insert the batch into the database and store the skoonie key
        int skoonieKey = db.insertRecord(batchRecord, batchesTable);

        //add the batch skoonie key to the receivement
        receiveRecord.addAttr("batch_key", Integer.toString(skoonieKey));

        //insert the receivement into the database
        db.insertRecord(receiveRecord, receivementsTable);

    }//end of MainController::receiveBatch
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
    
    //--------------------------------------------------------------------------
    // MainController::updateRecord
    //
    // Updates the Record in pTable using the Skoonie Key and key-value pairs
    // found in pCommand.
    //

    private void updateRecord(String[] pCommand, String pTable)
    {
        
        //extract the skoonie key from pCommand
        Record r = new Record();
        r.setSkoonieKey(pCommand[skoonieKeyIndex]);
        
        for (int i=skKeyAttrsIndex; i<pCommand.length; i++) {
            String[] pairs = pCommand[i].split(":");
            r.addAttr(pairs[0], pairs[1]);
        }
        
        db.updateRecord(r, pTable);

    }//end of MainController::updateRecord
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainController::verifyMove
    //
    // Verifies the move of a batch from pFromRackKey to pToRackKey.
    //
    // If the keys are the same or if the pFromRackKey is empty, then the move
    // should not be made. 
    //
    // Returns true if move should be made; false if not.
    //

    private boolean verifyMove(String pFromRackKey, String pToRackKey)
    {
        
        boolean shouldMove = true;
        
        if (pFromRackKey.isEmpty() || pFromRackKey.equals(pToRackKey)) {
            shouldMove = false;
        }
        
        return shouldMove;

    }//end of MainController::verifyMove
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainController::verifyReceivement
    //
    // Verifies the receivement of a batch.
    //
    // If pReceiveId or pBatchId already exist in the database or are empty,
    // then the receivement should not be made.
    //
    // Returns true if receivement should be made; false if not.
    //

    private boolean verifyReceivement(String pReceiveId, String pBatchId)
    {
        
        boolean shouldReceive = true;
        
        if (pReceiveId.isEmpty() || pBatchId.isEmpty() 
            || db.checkForValue(pBatchId, batchesTable, "id")
            || db.checkForValue(pReceiveId, receivementsTable, "id"))
        {
            shouldReceive = false;
        }
        
        return shouldReceive;

    }//end of MainController::verifyReceivement
    //--------------------------------------------------------------------------
    
}//end of class MainController
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------