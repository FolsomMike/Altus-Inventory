/*******************************************************************************
* Title: DisplayClassic.java
* Author: Hunter Schoonover
* Date: 12/11/15
*
* Purpose:
*
* This class is the Classic display version. It is the GUI that will be used for
* the original version of the program.
* 
* This class extends JFrame to become the main window of the program.
*
*/

//------------------------------------------------------------------------------

package view.classic;

//------------------------------------------------------------------------------

import command.Command;
import command.CommandHandler;
import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import toolkit.Tools;
import view.MainView;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class DisplayClassic
//

public class DisplayClassic extends JFrame implements CommandHandler,
                                                            ActionListener
{
    
    private final JPanel mainPanel;
    
    private CustomersWindow customersWindow;

    //--------------------------------------------------------------------------
    // DisplayClassic::DisplayClassic (constructor)
    //

    public DisplayClassic()
    {
        
        mainPanel = new JPanel();

    }//end of DisplayClassic::DisplayClassic (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // DisplayClassic::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    public void init()
    {
        
        //set up the frame
        setUpFrame();
        
        //create and add the GUI
        createGui();
        
        //arrange all the GUI items
        pack();

        //display the frame
        setVisible(true);

    }// end of DisplayClassic::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DisplayClassic::handleCommand
    //
    // Performs different actions depending on pCommand.
    //
    
    @Override
    public void handleCommand(Command pCommand) 
    {
        
        switch (pCommand.getMessage()) {
            
            //customer display actions
            case "display customers window":
                displayCustomersFrame();
                break;
                
        }
        
        //pass the command to all command handlers that aren't null
        //DEBUG HSS//if (mainFrame != null) { mainFrame.handleCommand(pCommand); }
        if (customersWindow != null) {customersWindow.handleCommand(pCommand);}
        
    }//end of DisplayClassic::handleCommand
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DisplayClassic::actionPerformed
    //
    // Creates a command using the action event message and performs it.
    //

    @Override
    public void actionPerformed(ActionEvent e)
    {

        (new Command(e.getActionCommand())).perform();
        
    }//end of DisplayClassic::actionPerformed
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::createControlPanel
    //
    // Creates and returns the control panel.
    //
    // The control panel contains all of the main button and functions for the 
    // program.
    //

    private JPanel createControlPanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        int spacer = 10;

        //add Receive Material button
        panel.add(createControlPanelButton("Receive<br>Material", 
                                            "images/receiveMaterial.png",
                                            "Receive material into the yard."));
        
        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));
        
        //add Ship Material button
        panel.add(createControlPanelButton("Ship<br>Material", 
                                            "images/shipMaterial.png",
                                            "Ship material from the yard."));
        
        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));
        
        //add Move Material button
        panel.add(createControlPanelButton("Move<br>Material", 
                                            "images/moveMaterial.png",
                                            "Move the selected material to a "
                                                + "different rack."));
        
        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));
        
        //add Transfer Material button
        panel.add(createControlPanelButton("Transfer<br>Material", 
                                            "images/transferMaterial.png",
                                            "Transfer the selected material to "
                                                + "a different customer."));
        
        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));
        
        //add Reserve Material button
        panel.add(createControlPanelButton("Reserve<br>Material", 
                                            "images/reserveMaterial.png",
                                            "Reserve the selected material for "
                                                + "future use."));
        
        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));
        
        //add Material Info button
        panel.add(createControlPanelButton("Material<br>Info", 
                                            "images/materialInfo.png",
                                            "View and edit information about "
                                                + "the selected material."));
        
        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));

        //add Create Report button
        panel.add(createControlPanelButton("Create<br>Report", 
                                            "images/createReport.png",
                                            "Create a report."));

        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));

        //add Create Invoice button
        panel.add(createControlPanelButton("Create<br>Invoice", 
                                            "images/createInvoice.png",
                                            "Create an invoice."));

        //horizontal spacer
        panel.add(Tools.createHorizontalSpacer(spacer));

        //add Make Payment button
        panel.add(createControlPanelButton("Make<br>Payment", 
                                            "images/makePayment.png",
                                            "Record payment from a customer."));

        return panel;

    }// end of MainFrame::createControlPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::createControlPanelButton
    //
    // Creates and returns a button to be used in the control panel part of the
    // main window, using the parameters for individuality.
    //

    private JButton createControlPanelButton(String pText, String pImagePath,
                                                String pTip)
    {

        //create button
        JButton btn = new JButton("<html><center>" + pText + "</center></html>", 
                                        createImageIcon(pImagePath));
        btn.setAlignmentX(LEFT_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setMargin(new Insets(0,0,0,0));
        btn.setToolTipText(pTip);
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);        
        Tools.setSizes(btn, 70, 75);
        
        return btn;

    }// end of MainFrame::createControlPanelButton
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::createGui
    //
    // Creates and adds the GUI to the window.
    //

    private void createGui()
    {
        
        //add the main menu
        MainMenu m = new MainMenu(this);
        m.init();
        setJMenuBar(m);
        
        //add padding to the main panel
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        //set the layout to add items top to bottom
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        //add the control panel to the gui
        mainPanel.add(createControlPanel());

    }// end of MainFrame::createGui
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
    // DisplayClassic::displayCustomersFrame
    //
    // Displays the Customers window.
    //
    
    private void displayCustomersFrame()
    {
        
        customersWindow = new CustomersWindow(this, this);
        customersWindow.init();

    }//end of DisplayClassic::displayCustomersFrame
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MainFrame::setUpFrame
    //
    // Sets up the frame by setting various options and styles.
    //

    private void setUpFrame()
    {

        //set the title of the frame
        setTitle("Altus Inventory");

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

        //exit the program when the window closes
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //add a JPanel to the frame to provide a familiar container
        setContentPane(mainPanel);

        //maximize the frame
        setExtendedState(getExtendedState() | MAXIMIZED_BOTH);

    }// end of MainFrame::setUpFrame
    //--------------------------------------------------------------------------

}//end of class DisplayClassic
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------