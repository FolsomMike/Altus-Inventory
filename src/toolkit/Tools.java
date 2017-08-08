/******************************************************************************
* Title: Tools.java
* Author: Mike Schoonover
* Date: 9/30/13
*
* Purpose:
*
* This class contains useful tools.
*
* Open Source Policy:
*
* This source code is Public Domain and free to any interested party.  Any
* person, company, or organization may do with it as they please.
*
*/

package toolkit;

//-----------------------------------------------------------------------------

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;
import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

//

public class Tools extends Object{

    //-----------------------------------------------------------------------------
    // Tools::Toolls (constructor)
    //

    public Tools()
    {


    }//end of Tools::Tools (constructor)
    //-----------------------------------------------------------------------------

    //-----------------------------------------------------------------------------
    // Tools::init
    //
    // Initializes the object.  MUST be called by sub classes after instantiation.
    //

    public void init()
    {


    }//end of Tools::init
    //-----------------------------------------------------------------------------

    //-----------------------------------------------------------------------------
    // Tools::setSizes
    //
    // Sets the min, max, and preferred sizes of pComponent to pWidth and pHeight.
    //

    static public void setSizes(Component pComponent, int pWidth, int pHeight)
    {

        pComponent.setMinimumSize(new Dimension(pWidth, pHeight));
        pComponent.setPreferredSize(new Dimension(pWidth, pHeight));
        pComponent.setMaximumSize(new Dimension(pWidth, pHeight));

    }//end of Tools::setSizes
    //-----------------------------------------------------------------------------

    //-----------------------------------------------------------------------------
    // Tools::generateActionCommand
    //
    // Generates and returns an action command using pActionId and pCommand.
    //

    public static String generateActionCommand (String pActionId, 
                                                String pCommand)
    {

        return pActionId + "->" + pCommand;

    }// end of Tools::generateActionCommand
    //-----------------------------------------------------------------------------

    //-----------------------------------------------------------------------------
    // Tools::displayErrorMessage
    //
    // Displays an error dialog with message pMessage.
    //

    static public void displayErrorMessage(String pMessage, JFrame pMainFrame)
    {

        JOptionPane.showMessageDialog(pMainFrame, pMessage,
                                                "Error", JOptionPane.ERROR_MESSAGE);

    }//end of Tools::displayErrorMessage
    //-----------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // Tools::centerJDialog
    //
    // Centers a passed in JDialog according to the location and size of the 
    // passed in parent dialog and the JDialog's size.
    //

    static public void centerJDialog(JDialog pDialog, Window pParent)
    {

        int parentFrameXPos = (int)pParent.getX();
        int parentFrameHalfWidth = (int)pParent.getWidth()/2;

        int parentFrameYPos = (int)pParent.getY();
        int parentFrameHalfHeight = (int)pParent.getHeight()/2;

        int parentFrameXCenter = parentFrameXPos + parentFrameHalfWidth;
        int parentFrameYCenter = parentFrameYPos + parentFrameHalfHeight;

        int dialogWidthCenter = (int)pDialog.getWidth()/2;
        int dialogHeightCenter = (int)pDialog.getHeight()/2;

        int xPosition = parentFrameXCenter - dialogWidthCenter;
        int yPosition = parentFrameYCenter - dialogHeightCenter;

        pDialog.setLocation(xPosition, yPosition);

    }// end of Tools::centerJDialog
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // Tools::createHorizontalSpacer
    //
    // Creates and returns a horizontal spacer using pWidth.
    //

    static public Component createHorizontalSpacer(int pWidth)
    {

        return Box.createRigidArea(new Dimension(pWidth, 0));

    }// end of Tools::createHorizontalSpacer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // Tools::createVerticalSpacer
    //
    // Creates and returns a vertical spacer using pHeight.
    //

    static public Component createVerticalSpacer(int pHeight)
    {

        return Box.createRigidArea(new Dimension(0, pHeight));

    }// end of Tools::createVerticalSpacer
    //--------------------------------------------------------------------------

}//end of class Tools
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
