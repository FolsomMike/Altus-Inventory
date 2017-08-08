/*******************************************************************************
* Title: DatabaseErrorWindow.java
* Author: Hunter Schoonover
* Date: 01/17/16
*
* Purpose:
*
* This class is used to display database errors to users, such as no connection,
* last action failed, etc.
*
*/

//------------------------------------------------------------------------------

package view.classic;

//------------------------------------------------------------------------------

import command.Command;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import toolkit.Tools;
import view.MainView;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class DatabaseErrorWindow
//

public class DatabaseErrorWindow extends AltusJDialog
{
    
    private final JLabel primaryLabel;
    private final JLabel secondaryLabel;

    //--------------------------------------------------------------------------
    // DatabaseErrorWindow::DatabaseErrorWindow (constructor)
    //

    public DatabaseErrorWindow(Window pParent, ActionListener pListener)
    {

        super("Database Error", pParent, pListener);
        
        primaryLabel = new JLabel();
        secondaryLabel = new JLabel();

    }//end of DatabaseErrorWindow::DatabaseErrorWindow (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseErrorWindow::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //
    
    @Override
    public void init() 
    {
        
        super.init();
        
        //disable the red "X" button
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
    }// end of DatabaseErrorWindow::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseErrorWindow::createGui
    //
    // Creates and adds the GUI to the window.
    //
    
    @Override
    protected void createGui() 
    {
        
        //set the main panel layout to add components left to right
        setMainPanelLayout(BoxLayout.Y_AXIS);
        
        //add the message panel to the main panel
        addToMainPanel(createMessagePanel());
        
        //vertical spacer
        addToMainPanel(Tools.createVerticalSpacer(20));
        
        //add the Exit Program button
        CustomTextButton btn = new CustomTextButton("Exit Program", 150, 30);
        btn.init();
        btn.setToolTipText("Exit the program.");
        btn.setActionCommand(Command.EXIT_PROGRAM);
        btn.addActionListener(getActionListener());
        btn.setAlignmentX(CENTER_ALIGNMENT);
        addToMainPanel(btn);
        
        
    }// end of DatabaseErrorWindow::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::createImageIcon
    //
    // Returns an ImageIcon, or null if the path was invalid.
    //
    // *************************************************************************
    // NOTE: You must use forward slashes in the path names for the resource
    // loader to find the image files in the JAR package.
    // *************************************************************************
    //

    private ImageIcon createImageIcon(String pPath)
    {

        // have to use the MainView class because it is located in the same 
        // package as the file; specifying the class specifies the first 
        // portion of the path to the image, this concatenated with the pPath
        java.net.URL imgURL = MainView.class.getResource(pPath);

        if (imgURL != null) { return new ImageIcon(imgURL); }
        else {return null;}

    }//end of MainFrame::createImageIcon
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::createMessagePanel
    //
    // Creates and returns the message panel, which contains the error image,
    // primary label, and sencondary label.
    //

    private JPanel createMessagePanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(CENTER_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel image = new JLabel(createImageIcon("images/error.png"));
        image.setAlignmentY(CENTER_ALIGNMENT);
        panel.add(image);
        
        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(20));
        
        //panel to hold the primary and secondary labels
        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.Y_AXIS));
        labelsPanel.setAlignmentY(CENTER_ALIGNMENT);
        
        primaryLabel.setAlignmentX(LEFT_ALIGNMENT);
        primaryLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        labelsPanel.add(primaryLabel);
        
        //vertical spacer
        labelsPanel.add(Tools.createVerticalSpacer(10));
        
        secondaryLabel.setAlignmentX(LEFT_ALIGNMENT);
        secondaryLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        labelsPanel.add(secondaryLabel);
        
        panel.add(labelsPanel);
        
        return panel;

    }//end of MainFrame::createMessagePanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseErrorWindow::displayNoDatabaseConnection
    //
    // Display an error to the user saying that a connection to the database
    // can't be established.
    //
    
    public void displayNoDatabaseConnection() 
    {
        
        String primary = "A connection to the database cannot be established.";
        String secondary = "You can wait for the connection to be restored, or "
                            + "you can <br>choose to exit the program by "
                            + "clicking the button below.";
        
        setPrimaryMessage(primary);
        setSecondaryMessage(secondary);
        
        //pack, set the width of the warning label, center, and make visible
        pack();
        setVisible(); 
        
    }// end of DatabaseErrorWindow::displayNoDatabaseConnection
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseErrorWindow::setPrimaryMessage
    //
    // Sets the text of the primary label.
    //
    
    private void setPrimaryMessage(String pMessage) 
    {
        
        primaryLabel.setText("<html>" + pMessage + "</html>");
        
    }// end of DatabaseErrorWindow::setPrimaryMessage
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DatabaseErrorWindow::setSecondaryMessage
    //
    // Sets the text of the secondary label.
    //
    
    private void setSecondaryMessage(String pMessage) 
    {
        
        secondaryLabel.setText("<html>" + pMessage + "</html>");
        
    }// end of DatabaseErrorWindow::setSecondaryMessage
    //--------------------------------------------------------------------------

}//end of class DatabaseErrorWindow
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------