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
import hscomponents.jsplitbutton.SplitButtonActionListener;
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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class MainView
//

public class MainView implements ActionListener, WindowListener, ChangeListener,
        SplitButtonActionListener, TableModelListener
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
    // MainView::buttonClicked
    //
    // Called when the button part of a JSplitbutton is clicked.
    //
    
    @Override
    public void buttonClicked(ActionEvent e) {
        
        eventHandler.actionPerformed(e);
        
    }
    //end of MainView::buttonClicked
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
    // MainView::tableChanged
    //
    
    @Override
    public void tableChanged(TableModelEvent tme) {
        
        eventHandler.tableChanged(tme);
        
    }//end of MainView::tableChanged
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

    }//end of MainView::windowClosing
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainView::cancelAltusJDialogAction
    //
    // Cancels the action of the active AltusJDialog.
    //

    public void cancelAltusJDialogAction()
    {

        mainFrame.cancelAltusJDialogAction();

    }//end of MainView::cancelAltusJDialogAction
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainView::checkBoxChanged
    //
    // Calls a handling function in MainFrame.
    //

    public void checkBoxChanged(int pRow)
    {

    }//end of MainView::checkBoxChanged
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
    // MainView::confirmAltusJDialogAction
    //
    // Confirms the action of the active AltusJDialog.
    //

    public void confirmAltusJDialogAction()
    {

        mainFrame.confirmAltusJDialogAction();

    }//end of MainView::confirmAltusJDialogAction
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainView::deleteCustomer
    //
    // Deletes the customer selected in the Customers window.
    //

    public void deleteCustomer()
    {

        mainFrame.deleteCustomer();

    }//end of MainView::deleteCustomer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainView::deleteRack
    //
    // Deletes the rack selected in the Racks window.
    //

    public void deleteRack()
    {

        mainFrame.deleteRack();

    }//end of MainView::deleteRack
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainView::deleteTruckCompany
    //
    // Deletes the truck company selected in the Truck Companies window.
    //
    
    public void deleteTruckCompany() 
    {
        
        mainFrame.deleteTruckCompany();
        
    }// end of MainView::deleteTruckCompany
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainView::deleteTruck
    //
    // Deletes the truck selected in the Trucks window.
    //
    
    public void deleteTruck() 
    {
        
        mainFrame.deleteTruck();
        
    }// end of MainView::deleteTruck
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MainView::displayAbout
    //
    // Displays about information.
    //

    public void displayAbout()
    {

        mainFrame.displayAbout();

    }//end of MainView::displayAbout
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainView::displayCreateCustomerWindow
    //
    // Displays the Create Customer window.
    //

    public void displayCreateCustomerWindow()
    {

        mainFrame.displayCreateCustomerWindow();

    }//end of MainView::displayCreateCustomerWindow
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
    // MainView::displayCreateReportWindow
    //
    // Displays the Create Report window.
    //

    public void displayCreateReportWindow()
    {

        CreateReportFrame frame = new CreateReportFrame(this);
        frame.init();

    }//end of MainView::displayCreateReportWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainView::displayCreateRackWindow
    //
    // Displays the Create Rack window.
    //

    public void displayCreateRackWindow()
    {

        mainFrame.displayCreateRackWindow();

    }//end of MainView::displayCreateRackWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainView::displayCreateTruckCompanyWindow
    //
    // Displays the Create Truck Company window.
    //

    public void displayCreateTruckCompanyWindow()
    {

        mainFrame.displayCreateTruckCompanyWindow();

    }//end of MainView::displayCreateTruckCompanyWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainView::displayCreateTruckWindow
    //
    // Displays the Create Truck window.
    //

    public void displayCreateTruckWindow()
    {

        mainFrame.displayCreateTruckWindow();

    }//end of MainView::displayCreateTruckWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainView::displayCustomersWindow
    //
    // Displays the Customers window.
    //

    public void displayCustomersWindow()
    {

        mainFrame.displayCustomersWindow();

    }//end of MainView::displayCustomersWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainView::displayEditCustomerWindow
    //
    // Displays the Edit Customer dialog.
    //

    public void displayEditCustomerWindow()
    {

        mainFrame.displayEditCustomerWindow();

    }//end of MainView::displayEditCustomerWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainView::displayEditRackWindow
    //
    // Displays the Edit Rack dialog.
    //

    public void displayEditRackWindow()
    {

        mainFrame.displayEditRackWindow();

    }//end of MainView::displayEditRackWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainView::displayEditTruckCompanyWindow
    //
    // Displays the Edit Truck Company window.
    //

    public void displayEditTruckCompanyWindow()
    {

        mainFrame.displayEditTruckCompanyWindow();

    }//end of MainView::displayEditTruckCompanyWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainView::displayEditTruckWindow
    //
    // Displays the Edit Truck window.
    //

    public void displayEditTruckWindow()
    {

        mainFrame.displayEditTruckWindow();

    }//end of MainView::displayEditTruckWindow
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
    // MainView::displayInvoicesWindow
    //
    // Displays the Invoices window.
    //

    public void displayInvoicesWindow()
    {

        InvoicesFrame frame = new InvoicesFrame(this);
        frame.init();

    }//end of MainView::displayInvoicesWindow
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
    // MainView::displayMaterialInfoWindow
    //
    // Displays the Material Info window.
    //

    public void displayMaterialInfoWindow()
    {

        mainFrame.displayMaterialInfoWindow();

    }//end of MainView::displayMaterialInfoWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainView::displayMoveMaterialWindow
    //
    // Displays the Move Material window.
    //

    public void displayMoveMaterialWindow()
    {

        mainFrame.displayMoveMaterialWindow();

    }//end of MainView::displayMoveMaterialWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainView::displayRacksWindow
    //
    // Displays the Racks window.
    //

    public void displayRacksWindow()
    {

        mainFrame.displayRacksWindow();

    }//end of MainView::displayRacksWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainView::displayReceiveMaterialWindow
    //
    // Displays the Receive Material window.
    //

    public void displayReceiveMaterialWindow()
    {
        
        mainFrame.displayReceiveMaterialWindow();

    }//end of MainView::displayReceiveMaterialWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainView::displayReserveMaterialWindow
    //
    // Displays the Reserve Material window.
    //

    public void displayReserveMaterialWindow()
    {

        mainFrame.displayReserveMaterialWindow();

    }//end of MainView::displayReserveMaterialWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::displayShipMaterialWindow
    //
    // Displays the Ship Material window.
    //

    public void displayShipMaterialWindow()
    {

        mainFrame.displayShipMaterialWindow();

    }//end of MainFrame::displayShipMaterialWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::displayTransferMaterialWindow
    //
    // Displays the Transfer Material window.
    //

    public void displayTransferMaterialWindow()
    {

        mainFrame.displayTransferMaterialWindow();

    }//end of MainFrame::displayTransferMaterialWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::displayTruckCompaniesWindow
    //
    // Displays the Truck Companies window.
    //

    public void displayTruckCompaniesWindow()
    {

        mainFrame.displayTruckCompaniesWindow();

    }//end of MainFrame::displayTruckCompaniesWindow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::displayTrucksWindow
    //
    // Displays the Trucks window.
    //

    public void displayTrucksWindow()
    {

        mainFrame.displayTrucksWindow();

    }//end of MainFrame::displayTrucksWindow
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
        mainTimer.setActionCommand("MainView::Timer");
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

    //Must be overriden because of the SplitButtonActionListener interface.
    //Currently has no functionality.
    @Override
    public void splitButtonClicked(ActionEvent e) {}

}//end of class MainView
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
