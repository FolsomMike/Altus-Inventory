/*******************************************************************************
* Title: ReceiveMaterialFrame.java
* Author: Hunter Schoonover
* Date: 07/25/15
*
* Purpose:
*
* This class is the Receive Material window.
*
*/

//------------------------------------------------------------------------------

package view;

//------------------------------------------------------------------------------

import java.awt.Component;
import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.Dimension;
import java.awt.Insets;
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
// class ReceiveMaterialFrame
//

public class ReceiveMaterialFrame extends JFrame
{
    
    private final MainView mainView;
    private JPanel mainPanel;

    private GuiUpdater guiUpdater;

    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::ReceiveMaterialFrame (constructor)
    //

    public ReceiveMaterialFrame(MainView pMainView)
    {

        mainView = pMainView;

    }//end of ReceiveMaterialFrame::ReceiveMaterialFrame (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::init
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

    }// end of ReceiveMaterialFrame::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::centerJFrame
    //
    // Centers a passed in JFrame according its size and the available screen
    // size.
    //

    public void centerJFrame(JFrame pFrame)
    {
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, 
                            dim.height/2-this.getSize().height/2);

    }// end of ReceiveMaterialFrame::centerJFrame
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::createCancelReceivePanel
    //
    // Creates and returns the panel used to hold the cancel and receive
    // buttons.
    //

    private JPanel createCancelReceivePanel()
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
        btn.setActionCommand("Cancel Receive Material");
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
        JButton btn2 = new JButton("Receive");
        btn2.addActionListener(mainView);
        btn2.setActionCommand("Receive Receive Material");
        btn2.setAlignmentX(LEFT_ALIGNMENT);
        btn2.setFocusPainted(false);
        btn2.setHorizontalTextPosition(SwingConstants.CENTER);
        btn2.setToolTipText("Receive the material.");
        btn2.setVerticalTextPosition(SwingConstants.BOTTOM);
        Tools.setSizes(btn2, 80, 30);
        panel.add(btn2);
        
        //horizontal center 
        //-- only works to "push" to the center if another glue is used
        panel.add(Box.createHorizontalGlue());

        return panel;

    }// end of ReceiveMaterialFrame::createCancelReceivePanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::createCustomerInputPanel
    //
    // Creates and returns the Customer input panel.
    //

    private JPanel createCustomerInputPanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel("Customer");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        field.setToolTipText("What customer owns the material?");
        Tools.setSizes(field, 100, 24);
        panel.add(field);

        return panel;

    }// end of ReceiveMaterialFrame::createCustomerInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::createDateInputPanel
    //
    // Creates and returns the date input panel.
    //

    private JPanel createDateInputPanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel("Date");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        field.setToolTipText("What date was the material received?");
        Tools.setSizes(field, 100, 24);
        panel.add(field);

        return panel;

    }// end of ReceiveMaterialFrame::createDateInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::createDiameterInputPanel
    //
    // Creates and returns the diameter input panel.
    //

    private JPanel createDiameterInputPanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel("Diameter");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        field.setToolTipText("What is the diameter of the pipe?");
        Tools.setSizes(field, 100, 24);
        panel.add(field);

        return panel;

    }// end of ReceiveMaterialFrame::createDiameterInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::createFacilityInputPanel
    //
    // Creates and returns the facility input panel.
    //

    private JPanel createFacilityInputPanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel("Facility");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        field.setToolTipText("What facility is the pipe for?");
        Tools.setSizes(field, 100, 24);
        panel.add(field);

        return panel;

    }// end of ReceiveMaterialFrame::createFacilityInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::createGradeInputPanel
    //
    // Creates and returns the grade input panel.
    //

    private JPanel createGradeInputPanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel("Grade");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        field.setToolTipText("What is the grade of the pipe?");
        Tools.setSizes(field, 100, 24);
        panel.add(field);

        return panel;

    }// end of ReceiveMaterialFrame::createGradeInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::createLengthInputPanel
    //
    // Creates and returns the length input panel.
    //

    private JPanel createLengthInputPanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel("Length");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        field.setToolTipText("How much pipe was received?");
        Tools.setSizes(field, 100, 24);
        panel.add(field);

        return panel;

    }// end of ReceiveMaterialFrame::createLengthInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::createLoadFromTallyZapButtonPanel
    //
    // Creates and returns the Load from Tally Zap button panel
    //

    private JPanel createLoadFromTallyZapButtonPanel()
    {
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        // create a filler to "push" everything in this panel to the right
        panel.add(Box.createHorizontalGlue());
        
        //create button
        JButton btn = new JButton("Load from TallyZap");
        btn.setActionCommand("Load from TallyZap");
        btn.addActionListener(mainView);
        btn.setAlignmentX(LEFT_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setMargin(new Insets(0,10,0,10));
        btn.setToolTipText("Load material from TallyZap.");
        panel.add(btn);

        return panel;

    }// end of ReceiveMaterialFrame::createLoadFromTallyDeviceButtonPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::createIDInputPanel
    //
    // Creates and returns the ID input panel.
    //

    private JPanel createIDInputPanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel("ID");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        field.setToolTipText("Give the material a reference ID.");
        Tools.setSizes(field, 100, 24);
        panel.add(field);

        return panel;

    }// end of ReceiveMaterialFrame::createIDInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::createQuantityInputPanel
    //
    // Creates and returns the quantity input panel.
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
        
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        field.setToolTipText("How many pieces of pipe?");
        Tools.setSizes(field, 100, 24);
        panel.add(field);

        return panel;

    }// end of ReceiveMaterialFrame::createQuantityInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::createRackInputPanel
    //
    // Creates and returns the rack input panel.
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
        field.setToolTipText("What rack is the material stored on?");
        Tools.setSizes(field, 100, 24);
        panel.add(field);

        return panel;

    }// end of ReceiveMaterialFrame::createRackInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::createRangeInputPanel
    //
    // Creates and returns the range input panel.
    //

    private JPanel createRangeInputPanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel("Range");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        field.setToolTipText("What is the range of the pipe?");
        Tools.setSizes(field, 100, 24);
        panel.add(field);

        return panel;

    }// end of ReceiveMaterialFrame::createRangeInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::createRow1
    //
    // Creates and returns a JPanel containing the ID, Customer, and Date input
    // panels.
    //

    private JPanel createRow1()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        //add ID input panel
        panel.add(createIDInputPanel());
        
        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(10,0)));
        
        //add Customer input panel
        panel.add(createCustomerInputPanel());
        
        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(10,0)));
        
        //add date input panel
        panel.add(createDateInputPanel());

        return panel;

    }// end of ReceiveMaterialFrame::createRow1
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::createRow2
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

    }// end of ReceiveMaterialFrame::createRow2
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::createRow3
    //
    // Creates and returns a JPanel containing the Quantity, Length, and Rack
    // input panels.
    //

    private JPanel createRow3()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        //add Quantity input panel
        panel.add(createQuantityInputPanel());
        
        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(10,0)));
        
        //add Length input panel
        panel.add(createLengthInputPanel());
        
        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(10,0)));
        
        //add Rack input panel
        panel.add(createRackInputPanel());

        return panel;

    }// end of ReceiveMaterialFrame::createRow3
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::createRow4
    //
    // Creates and returns a JPanel containing the Range, Grade, and Diameter
    // input panels.
    //

    private JPanel createRow4()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        //add Range input panel
        panel.add(createRangeInputPanel());
        
        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(10,0)));
        
        //add Grade input panel
        panel.add(createGradeInputPanel());
        
        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(10,0)));
        
        //add Diameter input panel
        panel.add(createDiameterInputPanel());

        return panel;

    }// end of ReceiveMaterialFrame::createRow4
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::createRow5
    //
    // Creates and returns a JPanel containing the Wall and Facility input 
    // panels.
    //

    private JPanel createRow5()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        //add Wall input panel
        panel.add(createWallInputPanel());
        
        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(10,0)));
        
        //add Facility input panel
        panel.add(createFacilityInputPanel());

        return panel;

    }// end of ReceiveMaterialFrame::createRow5
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::createTruckCompanyInputPanel
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
        field.setToolTipText
                    ("What truck company brought the material to the yard?");
        Tools.setSizes(field, 100, 24);
        panel.add(field);

        return panel;

    }// end of ReceiveMaterialFrame::createTruckCompanyInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::createTruckDriverInputPanel
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
        field.setToolTipText("Who was the driver of the truck that brought the"
                                + " material to the yard?");
        Tools.setSizes(field, 100, 24);
        panel.add(field);

        return panel;

    }// end of ReceiveMaterialFrame::createTruckDriverInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::createTruckNumberInputPanel
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
        field.setToolTipText("What is the number of the truck that brought the"
                                + " material to the yard?");
        Tools.setSizes(field, 100, 24);
        panel.add(field);

        return panel;

    }// end of ReceiveMaterialFrame::createTruckNumberInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::createWallInputPanel
    //
    // Creates and returns the wall input panel.
    //

    private JPanel createWallInputPanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel("Wall");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        field.setToolTipText("What is the wall thickness of the pipe?");
        Tools.setSizes(field, 100, 24);
        panel.add(field);

        return panel;

    }// end of ReceiveMaterialFrame::createWallInputPanel
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::setUpFrame
    //
    // Sets up the JFrame by setting various options and styles.
    //

    private void setUpFrame()
    {

        //set the title of the frame
        setTitle("Receive Material");

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

    }// end of ReceiveMaterialFrame::setUpFrame
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::setupGUI
    //
    // Sets up the user interface on the mainPanel: buttons, displays, etc.
    //

    private void setupGui()
    {
        
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        //add padding/margins to the main panel
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        //add the Load from TallyZap button
        mainPanel.add(createLoadFromTallyZapButtonPanel());
        
        //vertical spacer
        mainPanel.add(Box.createRigidArea(new Dimension(0,30)));
        
        //add Row 1
        mainPanel.add(createRow1());
        
        //vertical spacer
        mainPanel.add(Box.createRigidArea(new Dimension(0,20)));

        //add Row 2
        mainPanel.add(createRow2());
        
        //vertical spacer
        mainPanel.add(Box.createRigidArea(new Dimension(0,20)));
        
        //add Row 3
        mainPanel.add(createRow3());
        
        //vertical spacer
        mainPanel.add(Box.createRigidArea(new Dimension(0,20)));
        
        //add Row 4
        mainPanel.add(createRow4());
        
        //vertical spacer
        mainPanel.add(Box.createRigidArea(new Dimension(0,20)));
        
        //add Row 5
        mainPanel.add(createRow5());
        
        //vertical spacer
        mainPanel.add(Box.createRigidArea(new Dimension(0,30)));
        
        //add the cancel receive panel
        mainPanel.add(createCancelReceivePanel());

    }// end of ReceiveMaterialFrame::setupGui
    //--------------------------------------------------------------------------

}//end of class ReceiveMaterialFrame
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
