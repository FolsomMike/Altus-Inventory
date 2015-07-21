/******************************************************************************
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

//-----------------------------------------------------------------------------

package view;

//-----------------------------------------------------------------------------

import controller.EventHandler;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.font.TextAttribute;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import model.ADataClass;
import toolkit.Tools;

//-----------------------------------------------------------------------------
// class MainView
//

public class MainView implements ActionListener, WindowListener, ChangeListener
{

    private MainFrame mainFrame;
    private JPanel mainPanel;

    private final ADataClass aDataClass;

    private MainMenu mainMenu;

    private JTextField dataVersionTField;
    private JTextField dataTArea1;
    private JTextField dataTArea2;

    private GuiUpdater guiUpdater;
    private Help help;
    private About about;

    private javax.swing.Timer mainTimer;

    private final EventHandler eventHandler;

    private Font blackSmallFont, redSmallFont;
    private Font redLargeFont, greenLargeFont, yellowLargeFont, blackLargeFont;

    private JLabel statusLabel, infoLabel;
    private JLabel progressLabel;

//-----------------------------------------------------------------------------
// MainView::MainView (constructor)
//

public MainView(EventHandler pEventHandler, ADataClass pADataClass)
{

    eventHandler = pEventHandler;
    aDataClass = pADataClass;

}//end of MainView::MainView (constructor)
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
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
    
    //add a menu to the main form, passing this as the action listener
    mainFrame.setJMenuBar(mainMenu = new MainMenu(this));

    //create various fonts for use by the program
    createFonts();

}// end of MainView::init
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// MainView::setupMainFrame
//
// Creates and initializes the main frame for the program.
//

public void setupMainFrame()
{

    mainFrame = new MainFrame(this);
    mainFrame.init();

}// end of MainView::setupMainFrame
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// MainView::createFonts
//
// Creates fonts for use by the program.
//

public void createFonts()
{

    //create small and large red and green fonts for use with display objects
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
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// MainView::displayHelp
//
// Displays help information.
//

public void displayHelp()
{

    help = new Help(mainFrame);
    help = null;  //window will be released on close, so point should be null

}//end of MainView::displayHelp
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// MainView::displayAbout
//
// Displays about information.
//

public void displayAbout()
{

    about = new About(mainFrame);
    about = null;  //window will be released on close, so point should be null

}//end of MainView::displayAbout
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// MainView::displayErrorMessage
//
// Displays an error dialog with message pMessage.
//

public void displayErrorMessage(String pMessage)
{

    Tools.displayErrorMessage(pMessage, mainFrame);

}//end of MainView::displayErrorMessage
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// MainView::updateGUIDataSet1
//
// Updates some of the GUI with data from the model.
//

public void updateGUIDataSet1()
{

    dataVersionTField.setText(aDataClass.getDataVersion());

    dataTArea1.setText(aDataClass.getDataItem(0));

    dataTArea2.setText(aDataClass.getDataItem(1));

}//end of MainView::updateGUIDataSet1
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// MainView::drawRectangle
//
// Draws a rectangle on mainPanel
//

public void drawRectangle()
{

    
    Graphics2D g2 = (Graphics2D)mainPanel.getGraphics();

     // draw Rectangle2D.Double
    g2.draw(new Rectangle2D.Double(20, 10,10, 10));
        
}//end of MainView::drawRectangle
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// MainView::updateModelDataSet1
//
// Updates some of the model data with values in the GUI.
//

public void updateModelDataSet1()
{

    aDataClass.setDataVersion(dataVersionTField.getText());

    aDataClass.setDataItem(0, dataTArea1.getText());

    aDataClass.setDataItem(1, dataTArea2.getText());

}//end of MainView::updateModelDataSet1
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// MainView::setupAndStartMainTimer
//
// Prepares and starts a Java Swing timer.
//

public void setupAndStartMainTimer()
{

    //main timer has 2 second period
    mainTimer = new javax.swing.Timer (2000, this);
    mainTimer.setActionCommand ("Timer");
    mainTimer.start();

}// end of MainView::setupAndStartMainTimer
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// MainView::setTextForDataTArea1
//
// Sets the text value for text box.
//

public void setTextForDataTArea1(String pText)
{

    dataTArea1.setText(pText);

}// end of MainView::setTextForDataTArea1
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// MainView::setTextForDataTArea2
//
// Sets the text value for text box.
//

public void setTextForDataTArea2(String pText)
{

    dataTArea2.setText(pText);

}// end of MainView::setTextForDataTArea2
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
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
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// MainView::stateChanged
//

@Override
public void stateChanged(ChangeEvent ce) {
   
    eventHandler.stateChanged(ce);
    
}//end of MainView::stateChanged
//-----------------------------------------------------------------------------



//-----------------------------------------------------------------------------
// MainView::windowClosing
//
// Handles actions necessary when the window is closing
//

@Override
public void windowClosing(WindowEvent e)
{

    eventHandler.windowClosing(e);

}//end of Controller::windowClosing
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// MainView::(various window listener functions)
//
// These functions are implemented per requirements of interface WindowListener
// but do nothing at the present time.  As code is added to each function, it
// should be moved from this section and formatted properly.
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
//-----------------------------------------------------------------------------

}//end of class MainView
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
