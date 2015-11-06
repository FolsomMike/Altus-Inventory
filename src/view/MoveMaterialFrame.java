/*******************************************************************************
* Title:  MoveMaterialFrame.java
* Author: Hunter Schoonover
* Date: 07/26/15
*
* Purpose:
*
* This class is the Move Material window.
*
*/

//------------------------------------------------------------------------------

package view;

//------------------------------------------------------------------------------

import java.awt.Component;
import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import mksystems.mswing.MFloatSpinner;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class  MoveMaterialFrame
//

public class  MoveMaterialFrame extends JFrame
{
    
    private final MainView mainView;
    private JPanel mainPanel;

    private GuiUpdater guiUpdater;

    //--------------------------------------------------------------------------
    //  MoveMaterialFrame:: MoveMaterialFrame (constructor)
    //

    public  MoveMaterialFrame(MainView pMainView)
    {

        mainView = pMainView;

    }//end of  MoveMaterialFrame:: MoveMaterialFrame (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    //  MoveMaterialFrame::init
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

    }// end of  MoveMaterialFrame::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    //  MoveMaterialFrame::centerJFrame
    //
    // Centers a passed in JFrame according its size and the available screen
    // size.
    //

    public void centerJFrame(JFrame pFrame)
    {
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, 
                            dim.height/2-this.getSize().height/2);

    }// end of  MoveMaterialFrame::centerJFrame
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MoveMaterialFrame::createCancelMovePanel
    //
    // Creates and returns the panel used to hold the Cancel and Move
    // buttons.
    //

    private JPanel createCancelMovePanel()
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
        btn.setActionCommand("MoveMaterialFrame--Cancel");
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
        JButton btn2 = new JButton("Move");
        btn2.addActionListener(mainView);
        btn2.setActionCommand("MoveMaterialFrame--Move");
        btn2.setAlignmentX(LEFT_ALIGNMENT);
        btn2.setFocusPainted(false);
        btn2.setHorizontalTextPosition(SwingConstants.CENTER);
        btn2.setToolTipText("Move the material.");
        btn2.setVerticalTextPosition(SwingConstants.BOTTOM);   
        Tools.setSizes(btn2, 80, 30);
        panel.add(btn2);

        return panel;

    }// end of MoveMaterialFrame::createCancelMovePanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MoveMaterialFrame::createInputPanel
    //
    // Creates and returns the input panel.
    //

    private JPanel createInputPanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        //vertical spacer
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        
        //add Row 1
        panel.add(createRow1());
        
        //vertical spacer
        panel.add(Box.createRigidArea(new Dimension(0,30)));
        
        //add the Cancel Move panel
        panel.add(createCancelMovePanel());

        return panel;

    }// end of MoveMaterialFrame::createInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MoveMaterialFrame::createQuantityInputPanel
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
        spinner.setName("MoveMaterialFrame--Quantity Spinner");
        spinner.setToolTipText("How many pieces of material would you like to"
                                + " move?");
        spinner.centerText();
        spinner.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(spinner);

        return panel;

    }// end of MoveMaterialFrame::createQuantityInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MoveMaterialFrame::createRackInputPanel
    //
    // Creates and returns the Rack input panel.
    //

    private JPanel createRackInputPanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel("Rack");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        field.setToolTipText("What rack is the material being moved to?");
        Tools.setSizes(field, 100, 30);
        panel.add(field);

        return panel;

    }// end of MoveMaterialFrame::createRackInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MoveMaterialFrame::createRow1
    //
    // Creates and returns a JPanel containing the Quantity and Rack input 
    // panels.
    //
    
    private JPanel createRow1() {
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        //add Quantity input panel
        panel.add(createQuantityInputPanel());
        
        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(10,0)));
        
        //add Rack input panel
        panel.add(createRackInputPanel());

        return panel;
        
    }// end of MoveMaterialFrame::createRow1
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MoveMaterialFrame::setUpFrame
    //
    // Sets up the JFrame by setting various options and styles.
    //

    private void setUpFrame()
    {

        //set the title of the frame
        setTitle("Move Material");

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

    }// end of  MoveMaterialFrame::setUpFrame
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    //  MoveMaterialFrame::setupGUI
    //
    // Sets up the user interface on the mainPanel: buttons, displays, etc.
    //

    private void setupGui()
    {
        
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        //add padding/margins to the main panel
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        //add the input panel
        mainPanel.add(createInputPanel());

    }// end of  MoveMaterialFrame::setupGui
    //--------------------------------------------------------------------------

}//end of class  MoveMaterialFrame
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
