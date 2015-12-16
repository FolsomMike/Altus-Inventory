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
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import shared.Descriptor;
import shared.Table;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class EditRecordWindow
//

public class EditRecordWindow extends AltusJDialog
{
    
    private final Table table;
    private final String recordSkoonieKey;
    
    private final int inputPanelWidth = 200;

    //--------------------------------------------------------------------------
    // EditRecordWindow::EditRecordWindow (constructor)
    //

    public EditRecordWindow(String pTitle, Window pParent, 
                                ActionListener pListener, Table pTable, 
                                String pRecordSkoonieKey)
    {

        super(pTitle, pParent, pListener);
        
        table = pTable;
        
        recordSkoonieKey = pRecordSkoonieKey;

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
        
        //add the input panels for the descriptors to the gui
        addToMainPanel(createDescriptorInputsPanel());
        
    }// end of EditRecordWindow::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditRecordWindow::createDescriptorInputsPanel
    //
    // //WIP HSS// -- describe this function
    //

    private JScrollPane createDescriptorInputsPanel()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        //get the descriptors from the table
        List<Descriptor> descriptors = table.getDescriptors();
        int count = descriptors.size();
        
        int panelsPerRow = 3;
        int panelSpacer = 10;
        
        //determine what the total number of panels per row will be
        int totalPanelsPerRow = panelsPerRow;
        if(count < panelsPerRow) { totalPanelsPerRow = count; }
        
        List<JPanel> row = new ArrayList<>();
        for (int i=0; i<count; i++) {
            
            row.add(createInputPanel(descriptors.get(i).getName(), "", ""));
            
            //if we've reached the number of panels
            //allowed per row, or the end of the
            //descriptors, create a row panel from
            //the input panels in the row list, empty
            //the list and start again
            //the column count back to 0
            if (i>=panelsPerRow || i>=count-1) {
                panel.add(createRow(row, panelSpacer));
                row.clear();
            }
            
        }
        
        //calculate the width of the panel
        int width = inputPanelWidth*totalPanelsPerRow 
                        + panelSpacer*(totalPanelsPerRow-1);
        
        //put the inputs panel into a scroll pane and add it to the main panel
        JScrollPane sp = new JScrollPane(panel);
        sp.setAlignmentX(LEFT_ALIGNMENT);
        sp.setAlignmentY(TOP_ALIGNMENT);
        sp.setBorder(null);
        sp.setMaximumSize(new Dimension(width, 350));
        
        return sp;

    }// end of EditRecordWindow::createDescriptorInputsPanel
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
        Tools.setSizes(field, inputPanelWidth, 25);
        panel.add(field);

        return panel;

    }// end of EditRecordWindow::createInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::createRow
    //
    // Creates and returns a row of JPanels using pPanels, using pSpacer as the
    // spacing between the panels.
    //

    protected final JPanel createRow(List<JPanel> pPanels, int pSpacer)
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        for (int i=0; i<pPanels.size(); i++) {
            if (i>0) { panel.add(Tools.createHorizontalSpacer(pSpacer)); }
            panel.add(pPanels.get(i));
        }
        
        return panel;

    }// end of AltusJDialog::createRow
    //--------------------------------------------------------------------------

}//end of class EditRecordWindow
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------