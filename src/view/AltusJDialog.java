/*******************************************************************************
* Title: AltusJDialog.java
* Author: Hunter Schoonover
* Date: 11/23/15
*
* Purpose:
*
* This class is a custom JDialog designed specifically for the Altus Inventory
* program. It provides base functions and actions that all of the JDialogs in
* the program require.
*
*/

//------------------------------------------------------------------------------

package view;

//------------------------------------------------------------------------------

import java.awt.Component;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.Dialog;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
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
    
    static private final String actionId = "AltusJDialog";
    static public String getActionId() { return actionId; }
    
    private final MainFrame mainFrame;
    public final MainFrame getMainFrame() { return mainFrame; }
    private final MainView mainView;
    public final MainView getMainView() { return mainView; }
    private JPanel mainPanel;
    public final void addToMainPanel(Component pC) { mainPanel.add(pC); }
    
    private final MySQLDatabase db = new MySQLDatabase();
    public final MySQLDatabase getDatabase() { return db; }

    //--------------------------------------------------------------------------
    // AltusJDialog::AltusJDialog (constructor)
    //
    // Constructor used for AltusJDialogs that are children of MainFrame.
    //

    public AltusJDialog(String pTitle, MainFrame pMainFrame, MainView pMainView)
    {

        super(pMainFrame, pTitle);
        
        mainFrame   = pMainFrame;
        mainView    = pMainView;

    }//end of AltusJDialog::AltusJDialog (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::AltusJDialog (constructor)
    //
    // Constructor used for AltusJDialogs that are children of another JDialog.
    // 

    public AltusJDialog(String pTitle, JDialog pParent, MainFrame pMainFrame,
                            MainView pMainView)
    {

        super(pParent, pTitle);
        
        mainFrame   = pMainFrame;
        mainView    = pMainView;

    }//end of AltusJDialog::AltusJDialog (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //
    
    public void init() 
    {
        
        //tell the mainFrame that this is now the active dialog
        mainFrame.setActiveDialog(this);
        
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
        
        //center the dialog and make it visible
        Tools.centerJDialog(this, mainFrame);
        setVisible(true);
        
    }// end of AltusJDialog::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::cancel
    //
    // Cancels the action of the JDialog by disposing of it.
    //
    // Children classes can override this to perform specific actions.
    //
    
    public void cancel() 
    {

        dispose();
        
    }// end of AltusJDialog::cancel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::confirm
    //
    // Confirms the action of the JDialog.
    //
    // Children classes can override this to perform specific actions.
    //

    public void confirm()
    {

    }//end of AltusJDialog::confirm
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::createButton
    //
    // Creates and returns a JButton Panel, using the parameters for 
    // individuality.
    //
    
    protected final JButton createButton(String pText, String pTip, 
                                            String pActionId) 
    {
        
        CustomTextButton btn = new CustomTextButton(pText, 150, 30);
        btn.init();
        btn.addActionListener(mainView);
        btn.setToolTipText(pTip);
        btn.setActionCommand(Tools.generateActionCommand(pActionId, pText));
        btn.setAlignmentX(LEFT_ALIGNMENT);
        
        return btn;
        
    }// end of AltusJDialog::createButton
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ActionFrame::createCancelConfirmPanel
    //
    // Creates and returns a Cancel/Confirm panel.
    //

    protected final JPanel createCancelConfirmPanel(String pConfirmButtonText,
                                                String pConfirmButtonToolTip)
    {

        CancelConfirmPanel panel = new CancelConfirmPanel(pConfirmButtonText, 
                                                    pConfirmButtonToolTip, 
                                                    actionId, mainView);
        panel.init();

        return panel;

    }// end of ActionFrame::createCancelConfirmPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ActionFrame::setMainPanelLayout
    //
    // Sets the layout of mainPanel to pLayout.
    //

    protected final void setMainPanelLayout(int pLayout)
    {

        mainPanel.setLayout(new BoxLayout(mainPanel, pLayout));

    }// end of ActionFrame::createCancelConfirmPanel
    //--------------------------------------------------------------------------

}//end of class AltusJDialog
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------