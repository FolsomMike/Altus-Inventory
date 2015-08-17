/*******************************************************************************
* Title:  MakePaymentFrame.java
* Author: Hunter Schoonover
* Date: 08/10/15
*
* Purpose:
*
* This class is the Make Payment window.
* 
* Users can use this window to record payment from a customer.
*
*/

//------------------------------------------------------------------------------

package view;

//------------------------------------------------------------------------------

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class  MakePaymentFrame
//

public class  MakePaymentFrame extends JFrame
{
    
    private final MainView mainView;
    private JPanel mainPanel;

    private GuiUpdater guiUpdater;

    //--------------------------------------------------------------------------
    //  MakePaymentFrame::MakePaymentFrame (constructor)
    //

    public  MakePaymentFrame(MainView pMainView)
    {

        mainView = pMainView;

    }//end of  MakePaymentFrame::MakePaymentFrame (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    //  MakePaymentFrame::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    public void init()
    {

        setUpFrame();

        //create an object to handle thread safe updates of GUI components
        guiUpdater = new GuiUpdater(this);
        guiUpdater.init();

        //create user interface: buttons, displays, etc.
        setupGui();

        //arrange all the GUI items
        pack();

        //display the main frame
        setVisible(true);
        
        centerJFrame(this);

    }// end of  MakePaymentFrame::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    //  MakePaymentFrame::centerJFrame
    //
    // Centers a passed in JFrame according its size and the available screen
    // size.
    //

    public void centerJFrame(JFrame pFrame)
    {
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, 
                            dim.height/2-this.getSize().height/2);

    }// end of  MakePaymentFrame::centerJFrame
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    //  MakePaymentFrame::setUpFrame
    //
    // Sets up the JFrame by setting various options and styles.
    //

    private void setUpFrame()
    {

        //set the title of the frame
        setTitle("Make Payment");

        //turn off default bold for Metal look and feel
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        //force "look and feel" to Java style
        try {
            UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println("Could not set Look and Feel");
        }

        //add the mainView as a window listener
        addWindowListener(mainView);

        //release the window's resources when it is closed
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //add a JPanel to the frame to provide a familiar container
        mainPanel = new JPanel();
        Tools.setSizes(mainPanel, 300, 90);
        setContentPane(mainPanel);

    }// end of  MakePaymentFrame::setUpFrame
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    //  MakePaymentFrame::setupGUI
    //
    // Sets up the user interface on the mainPanel: buttons, displays, etc.
    //

    private void setupGui()
    {

    }// end of  MakePaymentFrame::setupGui
    //--------------------------------------------------------------------------

}//end of class  MakePaymentFrame
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
