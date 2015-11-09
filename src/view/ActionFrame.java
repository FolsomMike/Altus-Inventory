/*******************************************************************************
* Title: ActionFrame.java
* Author: Hunter Schoonover
* Date: 11/08/15
*
* Purpose:
*
* This class performs operations commonly performed by action frames. It to be 
* extended and customized by classes such as "ShipMaterialFrame" and 
* "ReceiveMaterialFrame", along with any other action frames.
*
*/

//------------------------------------------------------------------------------

package view;

//------------------------------------------------------------------------------

import java.awt.Component;
import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import mksystems.mswing.MFloatSpinner;
import skoonie.components.frame.SkoonieFrame;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class ActionFrame
//

public abstract class ActionFrame extends SkoonieFrame 
{
    
    //Abstract classes to be overriden by subclasses
    @Override protected abstract void createGui();
    
    protected final MainView mainView;
    
    protected CancelConfirmPanel cancelConfirmPanel;
    
    //Althoug the width of frame is not explicitly set using this value, it 
    //should end up to be this value because everything is calculated to fit
    //in this width
    private final int frameWidth = 340;
    
    protected final int inputPanelSpacer = 10;
    protected final int rowSpacer = 20;
    
    protected final int textFieldHeight = 25;
    protected static final int TEXT_FIELD_WIDTH_FULL = 0;
    protected static final int TEXT_FIELD_WIDTH_HALF = 1;
    protected static final int TEXT_FIELD_WIDTH_ONE_THIRD = 2;
    
    //--------------------------------------------------------------------------
    // ActionFrame::ActionFrame (constructor)
    //

    public ActionFrame(String pTitle, String pActionId, MainView pView)
    {
        
        super(pTitle, pActionId, pView, pView);
        
        mainView = pView;
        
        //don't maximize
        maximize = false;
        
        //don't all resize
        resizable = false;
        
        //center the frame
        center = true;

    }// end of ActionFrame::ActionFrame (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ActionFrame::createCancelConfirmPanel
    //
    // Creates, stores, and returns the Cancel/Confirm panel.
    //

    protected final JPanel createCancelConfirmPanel(String pConfirmButtonText, 
                                                String pConfirmButtonToolTip)
    {

        cancelConfirmPanel = new CancelConfirmPanel(pConfirmButtonText, 
                                                    pConfirmButtonToolTip, 
                                                    actionId, mainView);
        cancelConfirmPanel.init();

        return cancelConfirmPanel;

    }// end of ActionFrame::createCancelConfirmPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ActionFrame::createInputPanel
    //
    // Creates and returns an input panel.
    //

    protected final JPanel createInputPanel(String pLabelText, String pToolTip,
                                                int pWidthSize, int pHeight)
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel(pLabelText);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        field.setToolTipText(pToolTip);
        Tools.setSizes(field, determineWidth(pWidthSize), pHeight);
        panel.add(field);

        return panel;

    }// end of ActionFrame::createInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ActionFrame::createQuantityInputPanel
    //
    // Creates and returns a Quantity input panel.
    //
    // This is only designed to be called once for each object. Any more will
    // create spinners with the same.
    //

    protected final JPanel createQuantityInputPanel(String pToolTip)
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel("Quantity");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        //WIP HSS// -- Float spinner value and max should be set according to
        //              the material that is being quantified
        MFloatSpinner spinner = new MFloatSpinner(1000, 0, 1000, 1, "##0", 75, 
                                                    textFieldHeight);
        spinner.addChangeListener(mainView);
        spinner.setName(Tools.generateActionCommand(actionId, 
                                                        "Quantity Spinner"));
        spinner.setToolTipText(pToolTip);
        spinner.centerText();
        spinner.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(spinner);

        return panel;

    }// end of ActionFrame::createQuantityInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ActionFrame::createRow
    //
    // Creates and returns a row using pArray.
    //
    // Note that the code is only designed to hold between one and three input
    // panels per row.
    //

    protected final JPanel createRow(JPanel[] pInputPanels)
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        for (int i=0; i<pInputPanels.length; i++) {
            if (i>0) { panel.add(createHorizontalSpacer(inputPanelSpacer)); }
            panel.add(pInputPanels[i]);
        }
        
        return panel;

    }// end of ActionFrame::createRow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ActionFrame::createRowSpacer
    //
    // Creates and returns a row spacer.
    //

    protected final Component createRowSpacer()
    {

        return Box.createRigidArea(new Dimension(0, rowSpacer));

    }// end of ActionFrame::createRowSpacer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ActionFrame::determineWidth
    //
    // Calculates and returns the width based on pWidthSize.
    //
    // Note that the code is only designed to hold between one and three input
    // panels per row.
    //

    private int determineWidth(int pWidthSize)
    {

        int fullWidth = frameWidth - padding*2;
        
        int width = 0;
        switch (pWidthSize) {
            case TEXT_FIELD_WIDTH_FULL: 
                width = fullWidth;
                break;
            case TEXT_FIELD_WIDTH_HALF: 
                width = (fullWidth-inputPanelSpacer) / 2 ;
                break;
            case TEXT_FIELD_WIDTH_ONE_THIRD: 
                width = (fullWidth-inputPanelSpacer*2) / 3;
                break;
        }

        return width;

    }// end of ActionFrame::determineWidth
    //--------------------------------------------------------------------------
    
}// end of class ActionFrame
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
