/*******************************************************************************
* Title: ShipMaterialFrame.java
* Author: Hunter Schoonover
* Date: 07/25/15
*
* Purpose:
*
* This class is the Ship Material window.
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
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class ShipMaterialFrame
//

public class ShipMaterialFrame extends JFrame
{
    
    private final MainView mainView;
    private JPanel mainPanel;

    private GuiUpdater guiUpdater;

    //--------------------------------------------------------------------------
    // ShipMaterialFrame::ShipMaterialFrame (constructor)
    //

    public ShipMaterialFrame(MainView pMainView)
    {

        mainView = pMainView;

    }//end of ShipMaterialFrame::ShipMaterialFrame (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // ShipMaterialFrame::init
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

    }// end of ShipMaterialFrame::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ShipMaterialFrame::centerJFrame
    //
    // Centers a passed in JFrame according its size and the available screen
    // size.
    //

    public void centerJFrame(JFrame pFrame)
    {
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, 
                            dim.height/2-this.getSize().height/2);

    }// end of ShipMaterialFrame::centerJFrame
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ShipMaterialFrame::createAddressLine1InputPanel
    //
    // Creates and returns the Address Line 1 input panel.
    //

    private JPanel createAddressLine1InputPanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel("Address Line 1");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        //WIP HSS// -- Should be automatically filled in when destination is
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        field.setToolTipText("Address line 1 of the destination the material is"
                                + " being shipped to.");
        Tools.setSizes(field, 160, 24);
        panel.add(field);

        return panel;

    }// end of ShipMaterialFrame::createAddressLine1InputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ShipMaterialFrame::createAddressLine2InputPanel
    //
    // Creates and returns the Address Line 2 input panel.
    //

    private JPanel createAddressLine2InputPanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel("Address Line 2");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        //WIP HSS// -- Should be automatically filled in when destination is
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        field.setToolTipText("Address line 2 of the destination the material is"
                                + " being shipped to.");
        Tools.setSizes(field, 160, 24);
        panel.add(field);

        return panel;

    }// end of ShipMaterialFrame::createAddressLine2InputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::createCancelShipPanel
    //
    // Creates and returns the panel used to hold the Cancel and Ship
    // buttons.
    //

    private JPanel createCancelShipPanel()
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
        btn.setActionCommand("ShipMaterialFrame--Cancel");
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
        JButton btn2 = new JButton("Ship");
        btn2.addActionListener(mainView);
        btn2.setActionCommand("ShipMaterialFrame--Ship");
        btn2.setAlignmentX(LEFT_ALIGNMENT);
        btn2.setFocusPainted(false);
        btn2.setHorizontalTextPosition(SwingConstants.CENTER);
        btn2.setToolTipText("Ship the material.");
        btn2.setVerticalTextPosition(SwingConstants.BOTTOM);   
        Tools.setSizes(btn2, 80, 30);
        panel.add(btn2);
        
        //horizontal center 
        //-- only works to "push" to the center if another glue is used
        panel.add(Box.createHorizontalGlue());

        return panel;

    }// end of ReceiveMaterialFrame::createCancelShipPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ShipMaterialFrame::createCityInputPanel
    //
    // Creates and returns the City input panel.
    //

    private JPanel createCityInputPanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel("City");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        //WIP HSS// -- Should be automatically filled in when destination is
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        field.setToolTipText("What city is the material being shipped to?");
        Tools.setSizes(field, 100, 24);
        panel.add(field);

        return panel;

    }// end of ShipMaterialFrame::createCityInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ShipMaterialFrame::createDestinationInputPanel
    //
    // Creates and returns the Destination input panel.
    //

    private JPanel createDestinationInputPanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel("Destination");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        //WIP HSS// -- Should be a drop down menu with auto completion features
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        field.setToolTipText
                        ("What destination is the material being shipped to?");
        Tools.setSizes(field, 320, 24);
        panel.add(field);

        return panel;

    }// end of ShipMaterialFrame::createDestinationInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ShipMaterialFrame::createInputPanel
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
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        
        //add Row 2
        panel.add(createRow2());
        
        //vertical spacer
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        
        //add Row 3
        panel.add(createRow3());
        
        //vertical spacer
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        
        //add Row 4
        panel.add(createRow4());
        
        //vertical spacer
        panel.add(Box.createRigidArea(new Dimension(0,30)));
        
        //add the Cancel Ship panel
        panel.add(createCancelShipPanel());

        return panel;

    }// end of ShipMaterialFrame::createInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ShipMaterialFrame::createRow1
    //
    // Creates and returns a JPanel containing the Destination input panel.
    //

    private JPanel createRow1()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        //add Destination input panel
        panel.add(createDestinationInputPanel());

        return panel;

    }// end of ShipMaterialFrame::createRow1
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ShipMaterialFrame::createRow2
    //
    // Creates and returns a JPanel containing the Truck Company, Truck Number,
    // and Truck Driver input panels.
    //

    private JPanel createRow2()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        //add Truck Company input panel
        panel.add(createTruckCompanyInputPanel());
        
        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(10,0)));
        
        //add Truck Number input panel
        panel.add(createTruckNumberInputPanel());
        
        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(10,0)));
        
        //add Truck Driver input panel
        panel.add(createTruckDriverInputPanel());

        return panel;

    }// end of ShipMaterialFrame::createRow2
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ShipMaterialFrame::createRow3
    //
    // Creates and returns a JPanel containing the Address Line 1 and Address
    // Line 2 input panels.
    //

    private JPanel createRow3()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        //add Address Line 1 input panel
        panel.add(createAddressLine1InputPanel());
        
        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(10,0)));
        
        //add Address Line 2 input panel
        panel.add(createAddressLine2InputPanel());

        return panel;

    }// end of ShipMaterialFrame::createRow3
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ShipMaterialFrame::createRow4
    //
    // Creates and returns a JPanel containing the City, State, and Zip Code
    // input panels.
    //

    private JPanel createRow4()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        //add City input panel
        panel.add(createCityInputPanel());
        
        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(10,0)));
        
        //add State input panel
        panel.add(createStateInputPanel());
        
        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(10,0)));
        
        //add Zip Code input panel
        panel.add(createZipCodeInputPanel());

        return panel;

    }// end of ShipMaterialFrame::createRow4
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ShipMaterialFrame::createStateInputPanel
    //
    // Creates and returns the State input panel.
    //

    private JPanel createStateInputPanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel("State");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        //WIP HSS// -- Should be automatically filled in when destination is
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        field.setToolTipText("What state is the material being shipped to?");
        Tools.setSizes(field, 100, 24);
        panel.add(field);

        return panel;

    }// end of ShipMaterialFrame::createStateInputPanel
    //--------------------------------------------------------------------------
    
        //--------------------------------------------------------------------------
    // ShipMaterialFrame::createTruckCompanyInputPanel
    //
    // Creates and returns the Truck Company input panel.
    //

    private JPanel createTruckCompanyInputPanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel("Truck Company");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        field.setToolTipText("What truck company is taking the material to the"
                                + " destination?");
        Tools.setSizes(field, 100, 24);
        panel.add(field);

        return panel;

    }// end of ShipMaterialFrame::createTruckCompanyInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ShipMaterialFrame::createTruckDriverInputPanel
    //
    // Creates and returns the Truck Driver input panel.
    //

    private JPanel createTruckDriverInputPanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel("Truck Driver");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        field.setToolTipText("Who is the driver of the truck that is taking the"
                                + " material to the destination?");
        Tools.setSizes(field, 100, 24);
        panel.add(field);

        return panel;

    }// end of ShipMaterialFrame::createTruckDriverInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ShipMaterialFrame::createTruckNumberInputPanel
    //
    // Creates and returns the Truck Number input panel.
    //

    private JPanel createTruckNumberInputPanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel("Truck Number");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        field.setToolTipText("What is the number of the truck that is taking"
                                + " the material to the destination?");
        Tools.setSizes(field, 100, 24);
        panel.add(field);

        return panel;

    }// end of ShipMaterialFrame::createTruckNumberInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ShipMaterialFrame::createZipCodeInputPanel
    //
    // Creates and returns the Zip Code input panel.
    //

    private JPanel createZipCodeInputPanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel("Zip Code");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        //WIP HSS// -- Should be automatically filled in when destination is
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        field.setToolTipText("What zip code is the material being shipped to?");
        Tools.setSizes(field, 100, 24);
        panel.add(field);

        return panel;

    }// end of ShipMaterialFrame::createZipCodeInputPanel
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // ShipMaterialFrame::setUpFrame
    //
    // Sets up the JFrame by setting various options and styles.
    //

    private void setUpFrame()
    {

        //set the title of the frame
        setTitle("Ship Material");

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

    }// end of ShipMaterialFrame::setUpFrame
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // ShipMaterialFrame::setupGUI
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

    }// end of ShipMaterialFrame::setupGui
    //--------------------------------------------------------------------------

}//end of class ShipMaterialFrame
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
