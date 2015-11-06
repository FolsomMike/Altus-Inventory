/*******************************************************************************
* Title: ReserveMaterialFrame.java
* Author: Hunter Schoonover
* Date: 07/25/15
*
* Purpose:
*
* This class is the Reserve Material window.
*
*/

//------------------------------------------------------------------------------

package view;

//------------------------------------------------------------------------------

import java.awt.Color;
import java.awt.Component;
import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import mksystems.mswing.MFloatSpinner;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class ReserveMaterialFrame
//

public class ReserveMaterialFrame extends JFrame
{
    
    private final MainView mainView;
    private JPanel mainPanel;

    private GuiUpdater guiUpdater;

    //--------------------------------------------------------------------------
    // ReserveMaterialFrame::ReserveMaterialFrame (constructor)
    //

    public ReserveMaterialFrame(MainView pMainView)
    {

        mainView = pMainView;

    }//end of ReserveMaterialFrame::ReserveMaterialFrame (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // ReserveMaterialFrame::init
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

    }// end of ReserveMaterialFrame::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReserveMaterialFrame::centerJFrame
    //
    // Centers a passed in JFrame according its size and the available screen
    // size.
    //

    public void centerJFrame(JFrame pFrame)
    {
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, 
                            dim.height/2-this.getSize().height/2);

    }// end of ReserveMaterialFrame::centerJFrame
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::createCancelReservePanel
    //
    // Creates and returns the panel used to hold the Cancel and Reserve
    // buttons.
    //

    private JPanel createCancelReservePanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        //horizontal center 
        //-- only works to "push" to the center if another glue is used
        panel.add(Box.createHorizontalGlue());
        
        //create button
        JButton btn = new JButton("Cancel");
        btn.addActionListener(mainView);
        btn.setActionCommand("ReserveMaterialFrame--Cancel");
        btn.setAlignmentX(LEFT_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setToolTipText("Cancel.");
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        Tools.setSizes(btn, 80, 30);
        panel.add(btn);
        
        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(20,0)));
        
        //create button
        JButton btn2 = new JButton("Reserve");
        btn2.addActionListener(mainView);
        btn2.setActionCommand("ReserveMaterialFrame--Reserve");
        btn2.setAlignmentX(LEFT_ALIGNMENT);
        btn2.setFocusPainted(false);
        btn2.setHorizontalTextPosition(SwingConstants.CENTER);
        btn2.setToolTipText("Reserve the material.");
        btn2.setVerticalTextPosition(SwingConstants.BOTTOM);   
        Tools.setSizes(btn2, 80, 30);
        panel.add(btn2);
        
        //horizontal center 
        //-- only works to "push" to the center if another glue is used
        panel.add(Box.createHorizontalGlue());

        return panel;

    }// end of ReceiveMaterialFrame::createCancelReservePanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReserveMaterialFrame::createQuantityInputPanel
    //
    // Creates and returns the Quantity input panel.
    //

    private JPanel createQuantityInputPanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel("Quantity");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        //WIP HSS// -- Float spinner value and max should be set according to
        //              the material that is being shipped
        MFloatSpinner spinner 
                        = new MFloatSpinner(1000, 0, 1000, 1, "##0", 75, 30);
        //DEBUG HSS//cuttingHeadPositionSpinner.addChangeListener(this);
        spinner.setName("ReserveMaterialFrame--Quantity Spinner");
        spinner.setToolTipText("How many pieces of material would you like to"
                                + " reserve?");
        spinner.centerText();
        spinner.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(spinner);

        return panel;

    }// end of ReserveMaterialFrame::createQuantityInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReserveMaterialFrame::creatReserveForPanel
    //
    // Creates and returns the "Reserve for" panel.
    //

    private JPanel createReserveForPanel()
    {
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel("Reserve for");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setAlignmentY(TOP_ALIGNMENT);
        panel.add(label);
        
        //populate an array of strings with items for a combo box
        String[] strings = { "--Select--", "Shipment", "Move", "Transfer" };
        //Create the combo box, select item at index 0
        JComboBox combo = new JComboBox(strings);
        combo.addActionListener(mainView);     
        combo.setActionCommand("ReserveMaterialFrame::Reserve for");
        combo.setAlignmentX(LEFT_ALIGNMENT);
        combo.setAlignmentY(TOP_ALIGNMENT);
        combo.setSelectedIndex(0);
        combo.setBackground(Color.white);
        Tools.setSizes(combo, 135, 30);
        panel.add(combo);
        
        return panel;

    }// end of ReserveMaterialFrame::createReserveForPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReserveMaterialFrame::createRow1
    //
    // Creates and returns a JPanel containing the Quantity input panel.
    //
    
    private JPanel createRow1() {
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        //add Quantity input panel
        panel.add(createQuantityInputPanel());
        
        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(20,0)));
        
        //add Reserve for panel
        panel.add(createReserveForPanel());

        return panel;
        
    }// end of ReserveMaterialFrame::createRow1
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // ReserveMaterialFrame::setUpFrame
    //
    // Sets up the JFrame by setting various options and styles.
    //

    private void setUpFrame()
    {

        //set the title of the frame
        setTitle("Reserve Material");

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
        setContentPane(mainPanel);
        
        //disable window resizing
        setResizable(false);

    }// end of ReserveMaterialFrame::setUpFrame
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // ReserveMaterialFrame::setupGUI
    //
    // Sets up the user interface on the mainPanel: buttons, displays, etc.
    //

    private void setupGui()
    {
        
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        //add padding/margins to the main panel
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        //vertical spacer
        mainPanel.add(Box.createRigidArea(new Dimension(0,20)));
        
        //add Row 1
        mainPanel.add(createRow1());
        
        //vertical spacer
        mainPanel.add(Box.createRigidArea(new Dimension(0,30)));
        
        //add the Cancel Reserve panel
        mainPanel.add(createCancelReservePanel());

    }// end of ReserveMaterialFrame::setupGui
    //--------------------------------------------------------------------------

}// end of class ReserveMaterialFrame
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
