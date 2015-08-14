/*******************************************************************************
* Title: MainView.java
* Author: Hunter Schoonover
* Date: 07/20/15
*
* Purpose:
*
* This class is the Main View in a Model-View-Controller architecture.
* It creates and handles all GUI components.
* It knows about the Model, but not the Controller.
* 
* There may be many classes in the view package which handle different aspects
* of the GUI.
*
* All GUI control events, including Timer events are caught by this object
* and passed on to the "Controller" object pointed by the class member
* "eventHandler" for final handling.
*
*/

//------------------------------------------------------------------------------

package view;

//------------------------------------------------------------------------------

import controller.EventHandler;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class MainView
//

public class MainView implements ActionListener, WindowListener, ChangeListener
{

    private MainFrame mainFrame;

    private GuiUpdater guiUpdater;

    private javax.swing.Timer mainTimer;

    private final EventHandler eventHandler;

    private Font blackSmallFont, redSmallFont;
    private Font redLargeFont, greenLargeFont, yellowLargeFont, blackLargeFont;

    //--------------------------------------------------------------------------
    // MainView::MainView (constructor)
    //

    public MainView(EventHandler pEventHandler)
    {

        eventHandler = pEventHandler;

    }//end of MainView::MainView (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainView::init
    //
    // Initializes the object.  Must be called immediately after instantiation.
    //

    public void init()
    {

        setupMainFrame();

        //create an object to handle thread safe updates of GUI components
        guiUpdater = new GuiUpdater(mainFrame);
        guiUpdater.init();

        //create various fonts for use by the program
        createFonts();

    }// end of MainView::init
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainView::actionPerformed
    //
    // Responds to events and passes them on to the "Controller" (MVC Concept)
    // objects.
    //

    @Override
    public void actionPerformed(ActionEvent e)
    {

        eventHandler.actionPerformed(e);

    }//end of MainView::actionPerformed
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainView::stateChanged
    //

    @Override
    public void stateChanged(ChangeEvent ce) {

        eventHandler.stateChanged(ce);

    }//end of MainView::stateChanged
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainView::windowClosing
    //
    // Handles actions necessary when the window is closing
    //

    @Override
    public void windowClosing(WindowEvent e)
    {

        eventHandler.windowClosing(e);

    }//end of Controller::windowClosing
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainView::createFonts
    //
    // Creates fonts for use by the program.
    //

    public void createFonts()
    {

        //create small and large red and 
        //green fonts for use with display objects
        HashMap<TextAttribute, Object> map = new HashMap<>();

        blackSmallFont = new Font("Dialog", Font.PLAIN, 12);

        map.put(TextAttribute.FOREGROUND, Color.RED);
        redSmallFont = blackSmallFont.deriveFont(map);

        //empty the map to use for creating the large fonts
        map.clear();

        blackLargeFont = new Font("Dialog", Font.PLAIN, 20);

        map.put(TextAttribute.FOREGROUND, Color.GREEN);
        greenLargeFont = blackLargeFont.deriveFont(map);

        map.put(TextAttribute.FOREGROUND, Color.RED);
        redLargeFont = blackLargeFont.deriveFont(map);

        map.put(TextAttribute.FOREGROUND, Color.YELLOW);
        yellowLargeFont = blackLargeFont.deriveFont(map);

    }// end of MainView::createFonts
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainFrame::displayAbout
    //
    // Displays about information.
    //

    public void displayAbout()
    {

        mainFrame.displayAbout();

    }//end of MainFrame::displayAbout
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainView::displayCreateInvoiceWindow
    //
    // Displays the Create Invoice window.
    //

    public void displayCreateInvoiceWindow()
    {

        CreateInvoiceFrame frame = new CreateInvoiceFrame(this);
        frame.init();

    }//end of MainView::displayCreateInvoiceWindow
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainView::displayErrorMessage
    //
    // Displays an error dialog with message pMessage.
    //

    public void displayErrorMessage(String pMessage)
    {

        Tools.displayErrorMessage(pMessage, mainFrame);

    }//end of MainView::displayErrorMessage
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainView:displayHelp
    //
    // Displays help information.
    //

    public void displayHelp()
    {

        mainFrame.displayHelp();

    }//end of MainView::displayHelp
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainView::displayMakePaymentWindow
    //
    // Displays the Make Payment window.
    //

    public void displayMakePaymentWindow()
    {

        MakePaymentFrame frame = new MakePaymentFrame(this);
        frame.init();

    }//end of MainView::displayMakePaymentWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainView::displayMoveMaterialWindow
    //
    // Displays the Move Material window.
    //

    public void displayMoveMaterialWindow()
    {

        MoveMaterialFrame frame = new MoveMaterialFrame(this);
        frame.init();

    }//end of MainView::displayMoveMaterialWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainView::displayReceiveMaterialWindow
    //
    // Displays the Receive Material window.
    //

    public void displayReceiveMaterialWindow()
    {

        ReceiveMaterialFrame frame = new ReceiveMaterialFrame(this);
        frame.init();

    }//end of MainView::displayReceiveMaterialWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainView::displayReserveMaterialWindow
    //
    // Displays the Reserve Material window.
    //

    public void displayReserveMaterialWindow()
    {

        ReserveMaterialFrame frame = new ReserveMaterialFrame(this);
        frame.init();

    }//end of MainView::displayReserveMaterialWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::displayShipMaterialWindow
    //
    // Displays the Ship Material window.
    //

    public void displayShipMaterialWindow()
    {

        ShipMaterialFrame frame = new ShipMaterialFrame(this);
        frame.init();

    }//end of MainFrame::displayShipMaterialWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::displayTransferMaterialWindow
    //
    // Displays the Transfer Material window.
    //

    public void displayTransferMaterialWindow()
    {

        TransferMaterialFrame frame = new TransferMaterialFrame(this);
        frame.init();

    }//end of MainFrame::displayTransferMaterialWindow
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainView::setupAndStartMainTimer
    //
    // Prepares and starts a Java Swing timer.
    //

    public void setupAndStartMainTimer()
    {

        //main timer has 2 second period
        mainTimer = new javax.swing.Timer (2000, this);
        mainTimer.setActionCommand ("MainView::Timer");
        mainTimer.start();

    }// end of MainView::setupAndStartMainTimer
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainView::setupMainFrame
    //
    // Creates and initializes the main frame for the program.
    //

    public void setupMainFrame()
    {

        mainFrame = new MainFrame(this);
        mainFrame.init();

    }// end of MainView::setupMainFrame
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainView::(various window listener functions)
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

    //end of MainView::(various window listener functions)
    //--------------------------------------------------------------------------

}//end of class MainView
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
