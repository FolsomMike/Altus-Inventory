/*******************************************************************************
* Title: SkoonieFrame.java
* Author: Hunter Schoonover
* Date: 11/08/15
*
* Purpose:
*
* This class is the SkoonieFrame. It extends JFrame to produce a customized 
* frame that us Skoonies use.
*
*/

//------------------------------------------------------------------------------

package skooniecomponents.frame;

//------------------------------------------------------------------------------

import java.awt.Component;
import java.awt.Dimension;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class SkoonieFrame
//

public abstract class SkoonieFrame extends JFrame {
    
    //Abstract classes to be overriden by subclasses
    protected abstract void createGui();
    
    private SkoonieGuiUpdater guiUpdater;
    
    protected final String frameTitle;
    protected final String actionId;
    protected final ActionListener actionListener;
    protected final WindowListener windowListener;
    
    protected JPanel mainPanel;
    
    protected int defaultCloseOperation = DISPOSE_ON_CLOSE;
    protected boolean maximize = true;
    protected boolean resizable = true;
    protected boolean center = false;
    protected int padding = 10;
    
    //--------------------------------------------------------------------------
    // SkoonieFrame::SkoonieFrame (constructor)
    //

    public SkoonieFrame(String pTitle, String pActionId, 
                            ActionListener pAl, WindowListener pWl)
    {

        frameTitle = pTitle;
        actionId = pActionId;
        actionListener = pAl;
        windowListener = pWl;

    }//end of SkoonieFrame::SkoonieFrame (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // SkoonieFrame::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    public void init()
    {

        setUpFrame();

        //create an object to handle thread safe updates of GUI components
        guiUpdater = new SkoonieGuiUpdater(this);
        guiUpdater.init();

        //add padding to the main panel
        mainPanel.setBorder(BorderFactory.createEmptyBorder
                                        (padding, padding, padding, padding));
        
        //create and add the GUI
        createGui();

        //arrange all the GUI items
        pack();

        //display the main frame
        setVisible(true);
        
        //center the frame
        if (center) { centerFrame(); }

    }// end of SkoonieFrame::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // SkoonieFrame::centerFrame
    //
    // Centers frame according its size and the available screen size.
    //

    private void centerFrame()
    {
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, 
                            dim.height/2-this.getSize().height/2);

    }// end of SkoonieFrame::centerFrame
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // SkoonieFrame::createHorizontalSpacer
    //
    // Creates and returns a horizontal spacer using pWidth.
    //

    protected final Component createHorizontalSpacer(int pWidth)
    {

        return Box.createRigidArea(new Dimension(pWidth, 0));

    }// end of SkoonieFrame::createHorizontalSpacer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // SkoonieFrame::createVerticalSpacer
    //
    // Creates and returns a vertical spacer using pHeight.
    //

    protected final Component createVerticalSpacer(int pHeight)
    {

        return Box.createRigidArea(new Dimension(0, pHeight));

    }// end of SkoonieFrame::createVerticalSpacer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // SkoonieFrame::setUpFrame
    //
    // Sets up the JFrame by setting various options and styles.
    //

    private void setUpFrame()
    {

        //set the title of the frame
        setTitle(frameTitle);

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

        addWindowListener(windowListener);

        //sets the default close operation
        setDefaultCloseOperation(defaultCloseOperation);

        //add a JPanel to the frame to provide a familiar container
        mainPanel = new JPanel();
        setContentPane(mainPanel);
        
        //disable window resizing
        setResizable(resizable);

        //maximize the frame
        if (maximize) { setExtendedState(getExtendedState() | MAXIMIZED_BOTH); }

    }// end of SkoonieFrame::setUpFrame
    //--------------------------------------------------------------------------
    
}//end of class SkoonieFrame
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
