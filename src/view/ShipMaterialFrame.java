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

import java.awt.Color;
import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.Insets;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class ShipMaterialFrame
//

public class ShipMaterialFrame extends ActionFrame
{

    //--------------------------------------------------------------------------
    // ShipMaterialFrame::ShipMaterialFrame (constructor)
    //

    public ShipMaterialFrame(MainView pMainView)
    {

        super("Ship Material", "ShipMaterialFrame", pMainView);

    }//end of ShipMaterialFrame::ShipMaterialFrame (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ShipMaterialFrame::createGui
    //
    // Creates and adds the GUI to the mainPanel.
    //
    
    @Override
    protected void createGui() 
    {
        
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        //vertical spacer
        mainPanel.add(createVerticalSpacer(20));

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
        mainPanel.add(createCancelConfirmPanel("Ship", 
                                                "Ship the material."));
        
    }// end of ShipMaterialFrame::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ShipMaterialFrame::createNewIDInputPanel
    //
    // Creates and returns the ID input panel.
    //

    private JPanel createNewIDInputPanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        /*JLabel label = new JLabel("New ID");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);*/ //DEBUG HSS//
        
        JButton btn = new JButton("What's this?");
        HashMap<TextAttribute, Object> textAttrMap = new HashMap<>();
        textAttrMap.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        textAttrMap.put(TextAttribute.FOREGROUND, Color.BLUE);
        btn.setFont(btn.getFont().deriveFont(textAttrMap));
        btn.addActionListener(mainView);
        btn.setActionCommand("ShipMaterialFrame--What's this?");
        btn.setAlignmentX(LEFT_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setMargin(new Insets(0, 0, 0, 0));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setOpaque(false);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setToolTipText("Learn more about why you need a new ID.");
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);  
        btn.setForeground(Color.BLUE);
  
        Tools.setSizes(btn, 80, 30);
        panel.add(btn);
        
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        field.setToolTipText("You are splitting up the material into two"
                                + " separate batches. What would you like the"
                                + " ID for the new batch to be?.");
        Tools.setSizes(field, 100, 24);
        panel.add(field);

        return panel;

    }// end of ShipMaterialFrame::createNewIDInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ShipMaterialFrame::createRow1
    //
    // Creates and returns Row 1.
    //
    
    private JPanel createRow1() {
        
        String tip = "How many pieces of material would you like to ship?";
        JPanel input1 = createQuantityInputPanel(tip);
        
        JPanel input2 = createNewIDInputPanel();

        return createRow(new JPanel[]{input1, input2});
        
    }// end of ShipMaterialFrame::createRow1
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ShipMaterialFrame::createRow2
    //
    // Creates and returns Row 2.
    //

    private JPanel createRow2()
    {
        
        JPanel input1 = createInputPanel("Destination", 
                                "What destination is the material being shipped"
                                    + " to?",
                                TEXT_FIELD_WIDTH_FULL, textFieldHeight);

        return createRow(new JPanel[]{input1});

    }// end of ShipMaterialFrame::createRow2
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ShipMaterialFrame::createRow3
    //
    // Creates and returns a JPanel containing the Truck Company, Truck Number,
    // and Truck Driver input panels.
    //

    private JPanel createRow3()
    {
        
        JPanel input1 
                = createInputPanel("Truck Company", 
                        "What truck company is taking the material to the"
                            + " destination?",
                        TEXT_FIELD_WIDTH_ONE_THIRD, textFieldHeight);
        
        JPanel input2 
                = createInputPanel("Truck Number", 
                        "What is the number of the truck that is taking"
                            + " the material to the destination?",
                        TEXT_FIELD_WIDTH_ONE_THIRD, textFieldHeight);
        
        JPanel input3 
                = createInputPanel("Truck Driver", 
                       "Who is the driver of the truck that is taking the"
                            + " material to the destination?",
                        TEXT_FIELD_WIDTH_ONE_THIRD, textFieldHeight);

        return createRow(new JPanel[]{input1, input2, input3});
        
    }// end of ShipMaterialFrame::createRow3
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ShipMaterialFrame::createRow4
    //
    // Creates and returns a JPanel containing the Address Line 1 and Address
    // Line 2 input panels.
    //

    private JPanel createRow4()
    {
        
        JPanel input1 
                = createInputPanel("Address Line 1", 
                        "Address line 1 of the destination the material is"
                            + " being shipped to.",
                        TEXT_FIELD_WIDTH_HALF, textFieldHeight);
        
        JPanel input2 
                = createInputPanel("Address Line 2", 
                        "Address line 2 of the destination the material is"
                            + " being shipped to.",
                        TEXT_FIELD_WIDTH_HALF, textFieldHeight);

        return createRow(new JPanel[]{input1, input2});

    }// end of ShipMaterialFrame::createRow4
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ShipMaterialFrame::createRow5
    //
    // Creates and returns a JPanel containing the City, State, and Zip Code
    // input panels.
    //

    private JPanel createRow5()
    {
        
        JPanel input1 
                = createInputPanel("City", 
                        "What city is the material being shipped to?",
                        TEXT_FIELD_WIDTH_ONE_THIRD, textFieldHeight);
        
        JPanel input2 
                = createInputPanel("State", 
                        "What state is the material being shipped to?",
                        TEXT_FIELD_WIDTH_ONE_THIRD, textFieldHeight);
        
        JPanel input3 
                = createInputPanel("Zip Code", 
                        "What zip code is the material being shipped to?",
                        TEXT_FIELD_WIDTH_ONE_THIRD, textFieldHeight);

        return createRow(new JPanel[]{input1, input2, input3});

    }// end of ShipMaterialFrame::createRow5
    //--------------------------------------------------------------------------

}//end of class ShipMaterialFrame
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------