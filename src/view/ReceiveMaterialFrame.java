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
import static view.MainFrame.createImageIcon;

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
        panel.add(btn2);
        
        //horizontal center 
        //-- only works to "push" to the center if another glue is used
        panel.add(Box.createHorizontalGlue());

        return panel;

    }// end of ReceiveMaterialFrame::createCancelReceivePanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::createCompanyInputPanel
    //
    // Creates and returns the company name input panel.
    //

    private JPanel createCompanyInputPanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel("Company");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        field.setToolTipText("What company owns the material?");
        Tools.setSizes(field, 100, 24);
        panel.add(field);

        return panel;

    }// end of ReceiveMaterialFrame::createCompanyInputPanel
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
    // ReceiveMaterialFrame::createDiameterWallAndFacilityRow
    //
    // Creates and returns a JPanel containing the Diameter, Wall, and Facility
    // input panels.
    //

    private JPanel createDiameterWallAndFacilityRow()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        //add Diameter input panel
        panel.add(createDiameterInputPanel());
        
        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(10,0)));
        
        //add Wall input panel
        panel.add(createWallInputPanel());
        
        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(10,0)));
        
        //add Facility input panel
        panel.add(createFacilityInputPanel());

        return panel;

    }// end of ReceiveMaterialFrame::createDiameterWallAndFacilityRow
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
    // ReceiveMaterialFrame::createIDCompanyAndDateRow
    //
    // Creates and returns a JPanel containing the ID, company, and date input
    // panels.
    //

    private JPanel createIDCompanyAndDateRow()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        //add ID input panel
        panel.add(createIDInputPanel());
        
        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(10,0)));
        
        //add company input panel
        panel.add(createCompanyInputPanel());
        
        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(10,0)));
        
        //add date input panel
        panel.add(createDateInputPanel());

        return panel;

    }// end of ReceiveMaterialFrame::createIDCompanyAndDateRow
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
    // ReceiveMaterialFrame::createInputPanel
    //
    // Creates and returns the input panel.
    //

    private JPanel createInputPanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        //add the Load from TallyZap button
        panel.add(createLoadFromTallyZapButtonPanel());
        
        //vertical spacer
        panel.add(Box.createRigidArea(new Dimension(0,30)));
        
        //add ID, company, and date row
        panel.add(createIDCompanyAndDateRow());
        
        //vertical spacer
        panel.add(Box.createRigidArea(new Dimension(0,20)));

        //add Truck, Quantity, and Length Row
        panel.add(createTruckQuantityAndLengthRow());
        
        //vertical spacer
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        
        //add Rack, Range, and Grade Row
        panel.add(createRackRangeAndGradeRow());
        
        //vertical spacer
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        
        //add Diameter, Wall, and Facility Row
        panel.add(createDiameterWallAndFacilityRow());
        
        //vertical spacer
        panel.add(Box.createRigidArea(new Dimension(0,30)));
        
        //add the cancel receive panel
        panel.add(createCancelReceivePanel());

        return panel;

    }// end of ReceiveMaterialFrame::createInputPanel
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
    // ReceiveMaterialFrame::createRackRangeAndGradeRow
    //
    // Creates and returns a JPanel containing the Rack, Range, and Grade
    // input panels.
    //

    private JPanel createRackRangeAndGradeRow()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        //add Rack input panel
        panel.add(createRackInputPanel());
        
        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(10,0)));
        
        //add Range input panel
        panel.add(createRangeInputPanel());
        
        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(10,0)));
        
        //add Grade input panel
        panel.add(createGradeInputPanel());

        return panel;

    }// end of ReceiveMaterialFrame::createRackRangeAndGradeRow
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
    // ReceiveMaterialFrame::createTruckInputPanel
    //
    // Creates and returns the truck input panel.
    //

    private JPanel createTruckInputPanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel("Truck");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        field.setToolTipText("What truck brought the material to the yard?");
        Tools.setSizes(field, 100, 24);
        panel.add(field);

        return panel;

    }// end of ReceiveMaterialFrame::createTruckInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::createTruckQuantityAndLengthRow
    //
    // Creates and returns a JPanel containing the Truck, Quantity, and Length
    // input panels.
    //

    private JPanel createTruckQuantityAndLengthRow()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        //add Truck input panel
        panel.add(createTruckInputPanel());
        
        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(10,0)));
        
        //add Quantity input panel
        panel.add(createQuantityInputPanel());
        
        //horizontal spacer
        panel.add(Box.createRigidArea(new Dimension(10,0)));
        
        //add Length input panel
        panel.add(createLengthInputPanel());

        return panel;

    }// end of ReceiveMaterialFrame::createTruckQuantityAndLengthRow
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
        //Tools.setSizes(mainPanel, 300, 300);
        setContentPane(mainPanel);

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
        
        //add the input panel
        mainPanel.add(createInputPanel());

    }// end of ReceiveMaterialFrame::setupGui
    //--------------------------------------------------------------------------

}//end of class ReceiveMaterialFrame
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
