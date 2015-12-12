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
import java.awt.Dialog;
import java.awt.Window;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import model.MySQLDatabase;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class AltusJDialog
//

public abstract class AltusJDialog extends JDialog
{
    
    protected abstract void createGui();
    
    private final Window parent;
    
    private final MySQLDatabase db;
    protected final MySQLDatabase getDatabase() { return db; }

    private JPanel mainPanel;
    protected final void addToMainPanel(Component pC) { mainPanel.add(pC); }
    
    //--------------------------------------------------------------------------
    // AltusJDialog::AltusJDialog (constructor)
    //

    public AltusJDialog(String pTitle, Window pParent, MySQLDatabase pDatabase)
    {

        super(pParent, pTitle);
        
        parent = pParent;
        db = pDatabase;

    }//end of AltusJDialog::AltusJDialog (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //
    
    public void init() 
    {
        
        //initialize database
        db.init();
        
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
        
        //DEBUG HSS//
        Tools.setSizes(this, 100, 100);
        
        //center the dialog and make it visible
        Tools.centerJDialog(this, parent);
        setVisible(true);
        
    }// end of AltusJDialog::init
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

}//end of class AltusJDialog
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------