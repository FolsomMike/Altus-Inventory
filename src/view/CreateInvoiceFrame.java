/*******************************************************************************
* Title:  CreateInvoiceFrame.java
* Author: Hunter Schoonover
* Date: 08/13/15
*
* Purpose:
*
* This class is the Create Invoice window.
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
// class  CreateInvoiceFrame
//

public class  CreateInvoiceFrame extends JFrame
{
    
    private final MainView mainView;
    private JPanel mainPanel;

    private GuiUpdater guiUpdater;

    //--------------------------------------------------------------------------
    //  CreateInvoiceFrame:: CreateInvoiceFrame (constructor)
    //

    public  CreateInvoiceFrame(MainView pMainView)
    {

        mainView = pMainView;

    }//end of  CreateInvoiceFrame:: CreateInvoiceFrame (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    //  CreateInvoiceFrame::init
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

    }// end of  CreateInvoiceFrame::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    //  CreateInvoiceFrame::centerJFrame
    //
    // Centers a passed in JFrame according its size and the available screen
    // size.
    //

    public void centerJFrame(JFrame pFrame)
    {
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, 
                            dim.height/2-this.getSize().height/2);

    }// end of  CreateInvoiceFrame::centerJFrame
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    //  CreateInvoiceFrame::setUpFrame
    //
    // Sets up the JFrame by setting various options and styles.
    //

    private void setUpFrame()
    {

        //set the title of the frame
        setTitle("Create Invoice");

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

    }// end of  CreateInvoiceFrame::setUpFrame
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    //  CreateInvoiceFrame::setupGUI
    //
    // Sets up the user interface on the mainPanel: buttons, displays, etc.
    //

    private void setupGui()
    {

    }// end of  CreateInvoiceFrame::setupGui
    //--------------------------------------------------------------------------

}//end of class  CreateInvoiceFrame
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
