/*******************************************************************************
* Title: ShipMaterialWindow.java
* Author: Hunter Schoonover
* Date: 07/25/15
*
* Purpose:
*
* This class is the Ship Material window.
*
* It presents the user with input fields to specify information about the
* destination a material is being shipped to, including the address and the 
* truck company that is taking it there, and the quantity of the material.
* 
* Currently, it has input fields for:
*       Quantity, Destination, Address Line 1, Address Line 2, City, State,
*       Zip Code, Truck Company, Truck Driver
* 
*/

//------------------------------------------------------------------------------

package view;

//------------------------------------------------------------------------------

import javax.swing.BoxLayout;
import javax.swing.JPanel;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class ShipMaterialWindow
//

public class ShipMaterialWindow extends ActionFrame
{

    //--------------------------------------------------------------------------
    // ShipMaterialWindow::ShipMaterialWindow (constructor)
    //

    public ShipMaterialWindow(MainView pMainView)
    {

        super("Ship Material", "ShipMaterialFrame", pMainView);

    }//end of ShipMaterialWindow::ShipMaterialWindow (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ShipMaterialWindow::createGui
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
        
    }// end of ShipMaterialWindow::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ShipMaterialWindow::createRow1
    //
    // Creates and returns Row 1.
    //
    
    private JPanel createRow1() {
        
        String tip = "How many pieces of material would you like to ship?";
        JPanel input1 = createQuantityInputPanel(tip);

        return createRow(new JPanel[]{input1});
        
    }// end of ShipMaterialWindow::createRow1
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ShipMaterialWindow::createRow2
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

    }// end of ShipMaterialWindow::createRow2
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ShipMaterialWindow::createRow3
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
        
    }// end of ShipMaterialWindow::createRow3
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ShipMaterialWindow::createRow4
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

    }// end of ShipMaterialWindow::createRow4
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ShipMaterialWindow::createRow5
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

    }// end of ShipMaterialWindow::createRow5
    //--------------------------------------------------------------------------

}//end of class ShipMaterialWindow
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------