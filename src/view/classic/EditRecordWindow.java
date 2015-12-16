/*******************************************************************************
* Title: EditRecordWindow.java
* Author: Hunter Schoonover
* Date: 12/14/15
*
* Purpose:
*
* This class is the Edit Record window. It displays the information stored in
* the Record passed in upon construction and allows the user to edit it.
*
*/

//------------------------------------------------------------------------------

package view.classic;

//------------------------------------------------------------------------------

import java.awt.Component;
import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.Window;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import shared.Table;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class EditRecordWindow
//

public class EditRecordWindow extends AltusJDialog
{
    
    private final Table record;

    //--------------------------------------------------------------------------
    // EditRecordWindow::EditRecordWindow (constructor)
    //

    public EditRecordWindow(String pTitle, Window pParent, 
                                ActionListener pListener, Table pRecord)
    {

        super(pTitle, pParent, pListener);
        
        record = pRecord;

    }//end of EditRecordWindow::EditRecordWindow (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditRecordWindow::EditRecordWindow (constructor)
    //

    public EditRecordWindow(String pTitle, Window pParent, 
                                ActionListener pListener)
    {

        super(pTitle, pParent, pListener);
        
        record = null;
        
        //WIP HSS//

    }//end of EditRecordWindow::EditRecordWindow (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditRecordWindow::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //
    
    @Override
    public void init() 
    {
        
        super.init();
        
    }// end of EditRecordWindow::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditRecordWindow::createGui
    //
    // Creates and adds the GUI to the window.
    //
    
    @Override
    protected void createGui() 
    {
        
        //set the main panel layout to add components top to bottom
        setMainPanelLayout(BoxLayout.Y_AXIS);
        
    }// end of EditRecordWindow::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditRecordWindow::createInputPanel
    //
    // Creates and returns an input panel containing a JLabel and a JTextField.
    //
    // The JTextField contained in this input panel is stored in an InputField
    // which is then stored in inputFields, with pLabel as they key.
    //

    private JPanel createInputPanel(String pLabel, String pInputFieldText,
                                        String pToolTip)
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel(pLabel);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        field.setToolTipText(pToolTip);
        field.setText(pInputFieldText);
        Tools.setSizes(field, 200, 25);
        panel.add(field);

        return panel;

    }// end of EditRecordWindow::createInputPanel
    //--------------------------------------------------------------------------

}//end of class EditRecordWindow
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------