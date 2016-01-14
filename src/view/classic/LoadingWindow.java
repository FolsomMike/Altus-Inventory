/*******************************************************************************
* Title: LoadingWindow.java
* Author: Hunter Schoonover
* Date: 01/06/16
*
* Purpose:
*
* This class is the Loading window.
*
*/

//------------------------------------------------------------------------------

package view.classic;

//------------------------------------------------------------------------------

import java.awt.Window;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class LoadingWindow
//

public class LoadingWindow extends AltusJDialog
{

    //--------------------------------------------------------------------------
    // LoadingWindow::LoadingWindow (constructor)
    //

    public LoadingWindow(Window pParent, ActionListener pListener)
    {

        super("Loading", pParent, pListener);

    }//end of LoadingWindow::LoadingWindow (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // LoadingWindow::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //
    
    @Override
    public void init() 
    {
        
        setUndecorated(true);
        
        super.init();
        
        //center and make visible
        setVisible();
        
    }// end of LoadingWindow::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // LoadingWindow::createGui
    //
    // Creates and adds the GUI to the window.
    //
    
    @Override
    protected void createGui() 
    {
        
        //set the main panel layout to add components left to right
        setMainPanelLayout(BoxLayout.Y_AXIS);
        
        JLabel label = new JLabel("Loading...");
        label.setAlignmentX(LEFT_ALIGNMENT);
        addToMainPanel(label);
        
        JProgressBar bar = new JProgressBar();
        bar.setIndeterminate(true); //make indefinite
        bar.setAlignmentX(LEFT_ALIGNMENT);
        addToMainPanel(bar);
        
    }// end of LoadingWindow::createGui
    //--------------------------------------------------------------------------

}//end of class LoadingWindow
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------