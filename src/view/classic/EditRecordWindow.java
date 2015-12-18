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

import java.awt.Color;
import java.awt.Component;
import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
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
    
    private final Map<String, JTextField> inputs = new HashMap<>();
    
    JScrollPane inputsScrollPane;
    
    private final int maxHeight = 350;
    
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
        
        //now that the GUI has been created and packed, we can set the maximum
        //height of the frame
        if (getHeight()>maxHeight){Tools.setSizes(this, getWidth(), maxHeight);}
        
        //the scroll bar was previously set to always so that we can guarantee
        //that it would be included in the width, but we can change it to only
        //appear as needed now
        int policy = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
        inputsScrollPane.setVerticalScrollBarPolicy(policy);
        
        //repack gui components since we changed stuff
        pack();
        
        //center and make visible
        setVisible();
        
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
        
        //add the cancel confirm panel
        addToMainPanel(createCancelConfirmPanel());
        
    }// end of EditRecordWindow::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditRecordWindow::createCancelConfirmPanel
    //
    // Creates and returns a panel containing the Cancel and OK buttons.
    //

    private JPanel createCancelConfirmPanel()
    {

        //this outer panel is needed so that we can add a vertical spacer
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(BOTTOM_ALIGNMENT);
        panel.setBorder(BorderFactory.createMatteBorder(1,0,0,0, Color.GRAY));
        
        //vertical spacer
        panel.add(Tools.createVerticalSpacer(10));
        
        //panel to hold the buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.setAlignmentX(LEFT_ALIGNMENT);
        buttonsPanel.setAlignmentY(BOTTOM_ALIGNMENT);
        
        //horizontal center 
        //-- only works to "push" to the center if another glue is used
        buttonsPanel.add(Box.createHorizontalGlue());
        
        //add the Cancel button
        buttonsPanel.add(createButton("Cancel", "" , "")); //WIP HSS// -- need to pass in values
        
        //horizontal spacer
        buttonsPanel.add(Tools.createHorizontalSpacer(20));
        
        //add the OK button
        buttonsPanel.add(createButton("OK", "" , "")); //WIP HSS// -- need to pass in values
        
        //horizontal center 
        //-- only works to "push" to the center if another glue is used
        buttonsPanel.add(Box.createHorizontalGlue());
        
        panel.add(buttonsPanel);

        return panel;

    }// end of EditRecordWindow::createCancelConfirmPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditRecordWindow::createDescriptorInputsPanel
    //
    // Creates and returns a scroll pane containing all of the input panels for
    // the descriptors.
    //

    private JScrollPane createDescriptorInputsPanel()
    {

        //create a panel to go inside of a scroll pane
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        //get the descriptors from the table
        List<Descriptor> descriptors = table.getDescriptors();
        int count = descriptors.size();
        
        int panelsPerRow = 3;
        int panelSpacer = 10;
        
        List<JPanel> row = new ArrayList<>();
        for (int i=0; i<count; i++) {
            
            row.add(createInputPanel(descriptors.get(i)));
            
            //if we've reached the number of panels
            //allowed per row, or the end of the
            //descriptors, create a row panel from
            //the input panels in the row list, and
            //empty the list to start a new row
            if (row.size()>=panelsPerRow || i>=(count-1)) {
                panel.add(createRow(row, panelSpacer));
                panel.add(Tools.createVerticalSpacer(10));
                row.clear();
            }
            
        }
        
        //put the panel into a scroll pane and add it to the inputspanel
        inputsScrollPane = new JScrollPane(panel);
        inputsScrollPane.setAlignmentX(LEFT_ALIGNMENT);
        inputsScrollPane.setAlignmentY(TOP_ALIGNMENT);
        inputsScrollPane.setBorder(null);
        
        int policy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
        inputsScrollPane.setVerticalScrollBarPolicy(policy);
        
        return inputsScrollPane;

    }// end of EditRecordWindow::createDescriptorInputsPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // EditRecordWindow::createInputPanel
    //
    // Creates and returns an input panel containing a JLabel and a JTextField
    // using the information in pDescriptor.
    //
    // The JTextField contained in this input panel is stored in the inputs
    // map, using pDescriptor.getSkoonie() as the key.
    //

    private JPanel createInputPanel(Descriptor pDescriptor)
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel(pDescriptor.getName());
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        Tools.setSizes(field, inputPanelWidth, 25);
        
        String descKey = pDescriptor.getSkoonieKey();
        //set the text of the input field to the text for the record, if one
        //is specified
        if (recordSkoonieKey != null) {
            field.setText(table.getRecord(recordSkoonieKey).getValue(descKey));
        }
        //store the input field
        inputs.put(descKey, field);
        
        //add the field to the panel
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