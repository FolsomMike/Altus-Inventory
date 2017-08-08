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

import javax.swing.BoxLayout;
import javax.swing.JPanel;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class  MoveMaterialFrame
//

public class  MoveMaterialFrame extends ActionFrame
{

    //--------------------------------------------------------------------------
    //  MoveMaterialFrame:: MoveMaterialFrame (constructor)
    //

    public  MoveMaterialFrame(MainView pMainView)
    {

        super("Move Material", "MoveMaterialFrame", pMainView);

    }//end of  MoveMaterialFrame:: MoveMaterialFrame (constructor)
    //-------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MoveMaterialFrame::createGui
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
        
        //vertical spacer
        mainPanel.add(createVerticalSpacer(30));
        
        //add the Cancel/Confirm panel
        mainPanel.add(createCancelConfirmPanel("Move", "Move the material."));
        
    }// end of MoveMaterialFrame::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // MoveMaterialFrame::createRow1
    //
    // Creates and returns Row 1.
    //
    
    private JPanel createRow1() 
    {
        
        String tip = "How many pieces of material would you like to move?";
        JPanel input1 = createQuantityInputPanel(tip);
        
        JPanel input2 
                    = createInputPanel("Rack", 
                            "What rack is the material being moved to?", 
                            TEXT_FIELD_WIDTH_ONE_THIRD, textFieldHeight);

        return createRow(new JPanel[]{input1, input2});
        
    }// end of MoveMaterialFrame::createRow1
    //--------------------------------------------------------------------------

}//end of class  MoveMaterialFrame
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
