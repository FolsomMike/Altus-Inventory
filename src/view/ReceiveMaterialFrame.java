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

import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.Insets;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class ReceiveMaterialFrame
//

public class ReceiveMaterialFrame extends ActionFrame
{

    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::ReceiveMaterialFrame (constructor)
    //

    public ReceiveMaterialFrame(MainView pMainView)
    {

        super("Receive Material", "ReceiveMaterialFrame", pMainView);
        
    }// end of ReceiveMaterialFrame::ReceiveMaterialFrame (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::createGui
    //
    // Creates and adds the GUI to the mainPanel.
    //
    
    @Override
    protected void createGui() 
    {
        
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        //add the Load from TallyZap button
        mainPanel.add(createLoadFromTallyZapButtonPanel());
        
        //vertical spacer
        mainPanel.add(createVerticalSpacer(30));

        //add Row 1
        mainPanel.add(createRow1());
        
        //row spacer
        mainPanel.add(createRowSpacer());

        //add Row 2
        mainPanel.add(createRow2());
        
        //row spacer
        mainPanel.add(createRowSpacer());
        
        //add Row 3
        mainPanel.add(createRow3());
        
        //row spacer
        mainPanel.add(createRowSpacer());
        
        //add Row 4
        mainPanel.add(createRow4());
        
        //row spacer
        mainPanel.add(createRowSpacer());
        
        //add Row 5
        mainPanel.add(createRow5());
        
        //vertical spacer
        mainPanel.add(createVerticalSpacer(30));
        
        //add the Cancel/Confirm panel
        mainPanel.add(createCancelConfirmPanel("Receive", 
                                                "Receive the material."));
        
    }// end of ReceiveMaterialFrame::createGui
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
    // ReceiveMaterialFrame::createRow1
    //
    // Creates and returns Row 1.
    //

    private JPanel createRow1()
    {
        
        JPanel input1 = createInputPanel("ID", 
                                "Give the material a reference ID.",
                                TEXT_FIELD_WIDTH_ONE_THIRD, textFieldHeight);
        
        JPanel input2 = createInputPanel("Customer", 
                                "What customer owns the material?", 
                                TEXT_FIELD_WIDTH_ONE_THIRD, textFieldHeight);
        
        JPanel input3 = createInputPanel("Date", 
                                "What date was the material received?", 
                                TEXT_FIELD_WIDTH_ONE_THIRD, textFieldHeight);

        return createRow(new JPanel[]{input1, input2, input3});

    }// end of ReceiveMaterialFrame::createRow1
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::createRow2
    //
    // Creates and returns Row 2.
    //

    private JPanel createRow2()
    {
        
        JPanel input1 
                = createInputPanel("Truck Company", 
                        "What truck company brought the material to the yard?", 
                        TEXT_FIELD_WIDTH_ONE_THIRD, textFieldHeight);
        
        JPanel input2
                = createInputPanel("Truck Number", 
                        "What is the number of the truck that brought the"
                            + " material to the yard?", 
                        TEXT_FIELD_WIDTH_ONE_THIRD, textFieldHeight);
        
        JPanel input3
                = createInputPanel("Truck Driver", 
                        "Who was the driver of the truck that brought the"
                            + " material to the yard?",
                        TEXT_FIELD_WIDTH_ONE_THIRD, textFieldHeight);

        return createRow(new JPanel[]{input1, input2, input3});

    }// end of ReceiveMaterialFrame::createRow2
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::createRow3
    //
    // Creates and returns Row 3.
    //

    private JPanel createRow3()
    {
        
        JPanel input1
                = createInputPanel("Quantity",  "How many pieces of pipe?",
                            TEXT_FIELD_WIDTH_ONE_THIRD, textFieldHeight);
        
        JPanel input2
                = createInputPanel("Length",  "How much pipe was received?",
                            TEXT_FIELD_WIDTH_ONE_THIRD, textFieldHeight);
        
        JPanel input3
                = createInputPanel("Rack",  
                            "What rack is the material stored on?",
                            TEXT_FIELD_WIDTH_ONE_THIRD, textFieldHeight);

        return createRow(new JPanel[]{input1, input2, input3});

    }// end of ReceiveMaterialFrame::createRow3
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::createRow4
    //
    // Creates and returns Row 4.
    //

    private JPanel createRow4()
    {
        
        JPanel input1
                = createInputPanel("Range", 
                                "What is the range of the pipe?",
                                TEXT_FIELD_WIDTH_ONE_THIRD, textFieldHeight);
        
        JPanel input2
                = createInputPanel("Grade",  "What is the grade of the pipe?",
                                TEXT_FIELD_WIDTH_ONE_THIRD, textFieldHeight);
        
        JPanel input3
                = createInputPanel("Diameter",  
                                "What is the diameter of the pipe?",
                                TEXT_FIELD_WIDTH_ONE_THIRD, textFieldHeight);

        return createRow(new JPanel[]{input1, input2, input3});

    }// end of ReceiveMaterialFrame::createRow4
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialFrame::createRow5
    //
    // Creates and returns Row 5.
    //

    private JPanel createRow5()
    {

        JPanel input1
                = createInputPanel("Wall", 
                                "What is the wall thickness of the pipe?",
                                TEXT_FIELD_WIDTH_ONE_THIRD, textFieldHeight);
        
        JPanel input2
                = createInputPanel("Facility",  
                                "What facility is the pipe for?",
                                TEXT_FIELD_WIDTH_ONE_THIRD, textFieldHeight);

        return createRow(new JPanel[]{input1, input2});

    }// end of ReceiveMaterialFrame::createRow5
    //--------------------------------------------------------------------------

}// end of class ReceiveMaterialFrame
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
