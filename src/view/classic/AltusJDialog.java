/*******************************************************************************
* Title: AltusJDialog.java
* Author: Hunter Schoonover
* Date: 12/11/15
*
* Purpose:
*
* This class is a custom JDialog designed specifically for the Altus Inventory
* program. It provides base functions and actions that most of the JDialogs in
* the program require.
*
*/

//------------------------------------------------------------------------------

package view.classic;

//------------------------------------------------------------------------------

import java.awt.Component;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class AltusJDialog
//

public abstract class AltusJDialog extends JDialog
{
    
    protected abstract void createGui();
    
    private final Window parent;
    
    private final ActionListener actionListener;
    public ActionListener getActionListener() { return actionListener; }

    private JPanel mainPanel;
    protected final void addToMainPanel(Component pC) { mainPanel.add(pC); }
    
    //--------------------------------------------------------------------------
    // AltusJDialog::AltusJDialog (constructor)
    //

    public AltusJDialog(String pTitle, Window pParent, ActionListener pListener)
    {

        super(pParent, pTitle);
        
        parent = pParent;
        actionListener = pListener;

    }//end of AltusJDialog::AltusJDialog (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //
    
    public void init() 
    {
        
        //setup window
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        
        //add a JPanel to the dialog to provide a familiar container
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setContentPane(mainPanel);
        
        //add padding to the mainPanel
        int padding = 10;
        mainPanel.setBorder(BorderFactory.createEmptyBorder
                                        (padding, padding, padding, padding));
        
        //create the window's GUI
        createGui();
        
        //arrange all the GUI items
        pack();
        
    }// end of AltusJDialog::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::createButton
    //
    // Creates and returns a CustomTextButton, using the parameters for 
    // individuality.
    //
    
    protected final JButton createButton(String pText, String pTip, 
                                            String pActionCommand)
    {
        
        CustomTextButton btn = new CustomTextButton(pText, 150, 30);
        btn.init();
        btn.setToolTipText(pTip);
        btn.setActionCommand(pActionCommand);
        btn.addActionListener(actionListener);
        btn.setAlignmentX(LEFT_ALIGNMENT);
        
        return btn;
        
    }// end of AltusJDialog::createButton
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::setMainPanelLayout
    //
    // Sets the layout of mainPanel to pLayout.
    //

    protected final void setMainPanelLayout(int pLayout)
    {

        mainPanel.setLayout(new BoxLayout(mainPanel, pLayout));

    }// end of AltusJDialog::setMainPanelLayout
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::setVisible
    //
    // Center the dialog and make it visible.
    //

    public void setVisible()
    {

        Tools.centerJDialog(this, parent);
        setVisible(true);

    }// end of AltusJDialog::setVisible
    //--------------------------------------------------------------------------

}//end of class AltusJDialog
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------