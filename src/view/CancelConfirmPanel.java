/*******************************************************************************
* Title: CancelConfirmPanel.java
* Author: Hunter Schoonover
* Date: 11/08/15
*
* Purpose:
* 
* This class is used to encapsulate the look and feel desired for all of the
* cancel confirm panels throughout the program.
* 
* Cancel buttons will always have the same text ("Cancel") and tool tip 
* ("Cancel.").
* 
* Confirm buttons have different texts and tool tips. 
*
*/

//------------------------------------------------------------------------------

package view;

//------------------------------------------------------------------------------

import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import toolkit.Tools;


//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class CancelConfirmPanel
//

public class CancelConfirmPanel extends JPanel 
{
    ActionListener listener;
    String actionId;
    
    String confirmButtonText;
    String confirmButtonToolTip;
    
    //--------------------------------------------------------------------------
    // CancelConfirmPanel::CancelConfirmPanel (constructor)
    //
    
    public CancelConfirmPanel (String pConfirmButtonText, 
                                String pConfirmButtonToolTip,
                                String pActionId, ActionListener pListener) 
    {
        
        super();
        
        confirmButtonText = pConfirmButtonText;
        confirmButtonToolTip = pConfirmButtonToolTip;
        actionId = pActionId;
        listener = pListener;
    
    }// end of CancelConfirmPanel::CancelConfirmPanel (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CancelConfirmPanel::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    public void init()
    {

        setLayoutAndAlignment();
        
        createGui();

    }// end of CancelConfirmPanel::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CancelConfirmPanel::createCancelButton
    //
    // Creates and returns the Cancel button.
    //

    private JButton createCancelButton()
    {

        //create button
        JButton btn = new JButton("Cancel");
        btn.addActionListener(listener);
        btn.setActionCommand(Tools.generateActionCommand(actionId, "Cancel"));
        btn.setAlignmentX(LEFT_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setToolTipText("Cancel.");
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        Tools.setSizes(btn, 80, 30);
        
        return btn;

    }// end of CancelConfirmPanel::createCancelButton
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CancelConfirmPanel::createConfirmButton
    //
    // Creates and returns the Confirm button.
    //

    private JButton createConfirmButton()
    {

        //create button
        JButton btn = new JButton(confirmButtonText);
        btn.addActionListener(listener);
        btn.setActionCommand(Tools.generateActionCommand(actionId, "Confirm"));
        btn.setAlignmentX(LEFT_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setToolTipText(confirmButtonToolTip);
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        Tools.setSizes(btn, 80, 30);
        
        return btn;

    }// end of CancelConfirmPanel::createConfirmButton
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CancelConfirmPanel::createGui
    //
    // Creates and adds the GUI to the panel.
    //

    private void createGui()
    {
        
        //horizontal center 
        //-- only works to "push" to the center if another glue is used
        add(Box.createHorizontalGlue());
        
        add(createCancelButton());
        
        //horizontal spacer
        add(Box.createRigidArea(new Dimension(20,0)));
        
        add(createConfirmButton());
        
        //horizontal center 
        //-- only works to "push" to the center if another glue is used
        add(Box.createHorizontalGlue());

    }// end of CancelConfirmPanel::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CancelConfirmPanel::setLayoutAndAlignment
    //
    // Sets up the panel's layout and alignment.
    //

    private void setLayoutAndAlignment()
    {

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setAlignmentX(LEFT_ALIGNMENT);
        setAlignmentY(TOP_ALIGNMENT);

    }// end of CancelConfirmPanel::setLayoutAndAlignment
    //--------------------------------------------------------------------------
    
}// end of class CancelConfirmPanel
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
