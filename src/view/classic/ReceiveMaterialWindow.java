/*******************************************************************************
* Title: ReceiveMaterialWindow.java
* Author: Hunter Schoonover
* Date: 01/08/16
*
* Purpose:
*
* This class displays the Receive Material window to the user, providing a GUI
* for receiving a material.
*
*/

//------------------------------------------------------------------------------

package view.classic;

//------------------------------------------------------------------------------

import command.Command;
import command.CommandHandler;
import java.awt.Component;
import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import shared.Descriptor;
import shared.Record;
import shared.Table;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class ReceiveMaterialWindow
//

public class ReceiveMaterialWindow extends AltusJDialog implements CommandHandler
{
    
    private Table receivementsTable;
    private JPanel receivementPanel;
    
    private Table batchesTable;
    private JPanel batchPanel;
    
    //this is used for the skoonie keys of the batch and receivement records
    //created using the user input
    private final String keyForNew = "new";
    
    private JPanel inputsPanel;
    private final int padding = 10;
    private final int inputPanelWidth = 200;
    private final int inputPanelsPerRow = 3;
    private final int inputPanelSpacer = 10;
    
    //key=Input Name; value=text field for the input
    private final Map<String, JTextField> inputs = new HashMap<>();
    
    //Names of descriptors that aren't in the descriptors themselves
    //For example, both batch and receivement have Id in their descriptors. To
    //distinguish between the two: "Receivement Id" and "Batch Id"
    private final String idDescriptorName = "Id";
    private final String receivementIdDescriptorName = "Receivement Id";
    private final String batchIdDescriptorName = "Batch Id";
    //Date is here so we can decide which order it goes in
    private final String dateDescriptorName = "Date";
    
    private final List<JButton> buttonsToDisable = new ArrayList<>();
    
    private Image loadingImage;
    private boolean loading = false;

    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::ReceiveMaterialWindow (constructor)
    //

    public ReceiveMaterialWindow(Window pParent, ActionListener pListener)
    {

        super("Receive Material", pParent, pListener);

    }//end of ReceiveMaterialWindow::ReceiveMaterialWindow (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //
    
    @Override
    public void init() 
    {
        
        super.init();
        
        //get the loading image from file
        String path = "src/view/images/loading.gif";
        loadingImage = Toolkit.getDefaultToolkit().createImage(path);
        
        //set loading
        setLoading(true);
        (new Command(Command.GET_RECIEVEMENT_AND_BATCH_DESCRIPTORS)).perform();
        
        //repack gui components since we changed stuff
        pack();
        
        //center and make visible
        setVisible();
        
    }// end of ReceiveMaterialWindow::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::createGui
    //
    // Creates and adds the GUI to the window.
    //
    
    @Override
    protected void createGui() 
    {
        
        //set the main panel layout to add components top to bottom
        setMainPanelLayout(BoxLayout.Y_AXIS);
        
        //create and add the inputs panel to the main panel
        addToMainPanel(createInputsPanel());
        
        //add the cancel confirm panel
        addToMainPanel(createCancelConfirmPanel());
        
    }// end of ReceiveMaterialWindow::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::handleCommand
    //
    // Performs different actions depending on pCommand.
    //
    
    @Override
    public void handleCommand(Command pCommand) 
    {
        
        switch (pCommand.getMessage()) {
            
            case Command.RECIEVEMENT_AND_BATCH_DESCRIPTORS:
                displayReceivementAndBatchInputs(pCommand);
                break;
            
            case "ReceiveMaterialWindow -- cancel":
                dispose();
                break;
                
            case "ReceiveMaterialWindow -- confirm":
                confirm();
                break;
                
        }
        
    }//end of ReceiveMaterialWindow::handleCommand
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::checkBadInputs
    //
    // If there are no bad inputs in pBadInputs, the function just returns true.
    //
    // If there are any bad inputs in pBadInputs, a message saying which inputs
    // are bad is displayed to the user and the function returns false.
    //
    
    private boolean checkBadInputs(List<String> pBadInputs) 
    {
        
        //used to mark whether the user input was good or bad
        boolean good = true;
            
        //if there are bad inputs, display a message to the user indicating 
        //which inputs are bad and set good to false
        if (!pBadInputs.isEmpty()) {
            
            String title = "Inputs Cannot Be Empty";

            //create the message string
            String msg = "<html>The following inputs cannot be empty:<ul>";
            for (String s : pBadInputs) { msg += "<li><b>" +s+ "</b></li>"; }
            msg += "</ul></html>";

            //put the message into a JLabel so html can be used
            JLabel label = new JLabel(msg);
            label.setFont(new Font("Times New Roman", Font.PLAIN, 14));

            //display the warning to the user
            JOptionPane.showMessageDialog(this, label, title, 
                                            JOptionPane.ERROR_MESSAGE);

            //input was bad
            good = false;
            
         }
        
        return good;
        
    }// end of ReceiveMaterialWindow::checkBadInputs
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::confirm
    //
    // Confirms that the user wants to use their inputs to either create or edit
    // a Record, depending on whether or not the Skoonie Key passed in upon 
    // construction is null or not.
    //
    // If the user input is bad, a message is displayed to the user and this 
    // function is returned.
    //
    
    private void confirm() 
    {
        
        //return if user input is bad
        if (!getUserInput()) { return; }
        
        //create the command
        Command command = new Command(Command.RECEIVE_BATCH);
        
        //put the receivements table into the command
        command.put(Command.RECEIVEMENT, receivementsTable);
        
        //put the batches table into the command
        command.put(Command.BATCH, batchesTable);
        
        //put the skoonie key of the records into the command
        command.put(Command.RECORD_KEY, keyForNew);
        
        command.perform();
        
        //dispose of this the window
        dispose();
        
    }//end of ReceiveMaterialWindow::confirm
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::createCancelConfirmPanel
    //
    // Creates and returns a panel containing the Cancel and OK buttons.
    //
    // NOTE:    The OK button is added to the list of buttons to disable while
    //          loading, but the Cancel button is not.
    //

    private JPanel createCancelConfirmPanel()
    {

        //this outer panel is needed so that we can add a vertical spacer
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(BOTTOM_ALIGNMENT);
        
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
        buttonsPanel.add(createButton("Cancel", "" , 
                                        "ReceiveMaterialWindow -- cancel"));
        
        //horizontal spacer
        buttonsPanel.add(Tools.createHorizontalSpacer(20));
        
        //add the OK button
        JButton ok = createButton("OK", "" , 
                                        "ReceiveMaterialWindow -- confirm");
        buttonsToDisable.add(ok); //will be disabled while loading
        buttonsPanel.add(ok);
        
        //horizontal center 
        //-- only works to "push" to the center if another glue is used
        buttonsPanel.add(Box.createHorizontalGlue());
        
        panel.add(buttonsPanel);

        return panel;

    }// end of ReceiveMaterialWindow::createCancelConfirmPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::createInputPanel
    //
    // Creates and returns an input panel containing a JLabel and a JTextField
    // using pName.
    //
    // The JTextField contained in this input panel is stored in the pInputs
    // map, using pName as the key.
    //

    private JPanel createInputPanel(String pName,
                                        Map<String, JTextField> pInputs)
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel(pName);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        JTextField field = new JTextField();
        field.setAlignmentX(LEFT_ALIGNMENT);
        Tools.setSizes(field, inputPanelWidth, 25);
        
        //store the input field
        pInputs.put(pName, field);
        
        //add the field to the panel
        panel.add(field);

        return panel;

    }// end of ReceiveMaterialWindow::createInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::createInputPanel
    //
    // Creates and returns an input panel containing a JLabel and a JTextField
    // using the information in pDescriptor.
    //
    // The JTextField contained in this input panel is stored in the pInputs
    // map, using pDescriptor.getSkoonie() as the key.
    //

    private List<JPanel> createInputPanels(
                                    List<Descriptor> pReceivementsDescriptors, 
                                    List<Descriptor> pBatchesDescriptors)
    {
        
        List<JPanel> panels = new ArrayList<>();
        
        //we know that Receivement Id and Batch Id and Date are always there and
        //always need to go first
        panels.add(createInputPanel(receivementIdDescriptorName, inputs));
        panels.add(createInputPanel(batchIdDescriptorName, inputs));
        panels.add(createInputPanel(dateDescriptorName, inputs));
        
        //add inputs for all of the batch descriptors, avoiding duplicates or 
        //those already stored
        for (Descriptor d : pBatchesDescriptors) {
            
            String name = d.getName();
            
            //skip this descriptor if one with a similar name has already been
            //stored or if it is "Id" or "Date"
            if (inputs.get(name)!=null || name.equals(idDescriptorName) || 
                    name.equals(dateDescriptorName)) {
                continue;
            }
            
            panels.add(createInputPanel(name, inputs));
            
        }

        //add inputs for all of the receivement descriptors, avoiding duplicates
        //or those already stored
        for (Descriptor d : pReceivementsDescriptors) {
            
            String name = d.getName();
            
            //skip this descriptor if one with a similar name has already been
            //stored or if it is "Id" or "Date"
            if (inputs.get(name)!=null || name.equals(idDescriptorName) || 
                    name.equals(dateDescriptorName)) {
                continue;
            }
            
            panels.add(createInputPanel(name, inputs));
            
        }

        return panels;

    }// end of ReceiveMaterialWindow::createInputPanels
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::createInputsPanel
    //
    // Creates and returns a scrollpane to hold the inputs for the Receivement
    // and Batch, overriding the paintComponent() function to paint the loading 
    // image when necessary.
    //
    // The panel given to the scroll pane is stored so that the input panels
    // may be added to it later when they are received from the model.
    //

    private JScrollPane createInputsPanel()
    {

        //panel to hold inputs panels
        inputsPanel = new JPanel();
        inputsPanel.setLayout(new BoxLayout(inputsPanel, BoxLayout.Y_AXIS));
        inputsPanel.setAlignmentX(LEFT_ALIGNMENT);
        inputsPanel.setAlignmentY(TOP_ALIGNMENT);
        //add padding to the inputsPanel
        inputsPanel.setBorder(BorderFactory.createEmptyBorder
                                        (padding, padding, padding, padding));
        
        //put the inputsPanel into a scroll pane, overriding paintComponent to
        //paint the loading image when necessary
        JScrollPane sp = new JScrollPane(inputsPanel) {
            @Override protected void paintComponent(Graphics g)
            {

                super.paintComponent(g);

                //paint the loading image if necessary
                if (loading) {
                    int x = getWidth()/2 - loadingImage.getWidth(null)/2;
                    int y = getHeight()/2 - loadingImage.getHeight(null)/2;
                    g.drawImage(loadingImage, x, y, this);
                    g.dispose();
                }

            }
        };
        
        sp.setAlignmentX(LEFT_ALIGNMENT);
        sp.setAlignmentY(TOP_ALIGNMENT);
        
        //make sure that the horizontal scroll bar never appears
        int policy = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
        sp.setHorizontalScrollBarPolicy(policy);
        
        //calculate and set the width of the scroll pane
        int width = padding*2 
                        + inputPanelsPerRow*inputPanelWidth 
                        + inputPanelSpacer*(inputPanelsPerRow-1) 
                        + 20; //20 is added in to account for the scroll bar
        Tools.setSizes(sp, width, 300);
        
        return sp;

    }// end of ReceiveMaterialWindow::createInputsPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::createRow
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
        
        //horizontal center 
        //-- only works to "push" to the center if another glue is used
        panel.add(Box.createHorizontalGlue());
        
        //add the input panels in pPanels to the panel until the number of 
        //panels allowed per row is reached or until there are no more input
        //panels to add. If the number of number of input panels to add falls
        //short of the number allowed per row, then spacers the same size as 
        //the inputs are added in place of the missing panels. This is to ensure
        //that the horizontal center works right
        for (int i=0; i<inputPanelsPerRow; i++) {
        
            if (i>0) { panel.add(Tools.createHorizontalSpacer(pSpacer)); }
            
            if (i<pPanels.size()) { panel.add(pPanels.get(i)); }
            else { panel.add(Tools.createHorizontalSpacer(inputPanelWidth)); }
        
        }
        
        //horizontal center 
        //-- only works to "push" to the center if another glue is used
        panel.add(Box.createHorizontalGlue());
        
        return panel;

    }// end of ReceiveMaterialWindow::createRow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::displayReceivementAndBatchInputs
    //
    // Displays the receivement and batch inputs using the descriptors extracted
    // from pCommand and then tells the window that we're done loading.
    //
    
    private void displayReceivementAndBatchInputs(Command pCommand) 
    {
        
        setLoading(false);
        
        //set up the receivements table using the descriptors
        receivementsTable = new Table();
        receivementsTable.setDescriptors((List<Descriptor>)pCommand
                                        .get(Command.RECIEVEMENT_DESCRIPTORS));
        
        //set up the batches table using the descriptors
        batchesTable = new Table();
        batchesTable.setDescriptors((List<Descriptor>)pCommand
                                        .get(Command.BATCH_DESCRIPTORS));
        
        
        //create the input panels for the receivement and batch descriptors
        List<JPanel> panels = createInputPanels(
                                            receivementsTable.getDescriptors(), 
                                            batchesTable.getDescriptors());
        
        //add the individual panels to the inputsPanel
        List<JPanel> row = new ArrayList<>();
        for (int i=0; i<panels.size(); i++) {
            
            //add the next panel to the row
            row.add(panels.get(i));
            
            //if we've reached the number of panels
            //allowed per row, or the end of the
            //panels, create a row from the input 
            //panels in the row list, and empty the
            //list to start a new row
            if (row.size()>=inputPanelsPerRow || i>=(panels.size()-1)) {
                inputsPanel.add(createRow(row, inputPanelSpacer));
                inputsPanel.add(Tools.createVerticalSpacer(10));
                row.clear();
            }
            
        }
        
        //repack
        pack();
        
    }//end of ReceiveMaterialWindow::displayReceivementAndBatchInputs
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::getUserInput
    //
    // Gets and checks the user input. If Descriptor.getRequired() is true, then
    // the input tied to that descriptor cannot be empty. If any are empty, the
    // user input is considered bad.
    //
    // Returns true if user input is good; false if not.
    //
    
    private boolean getUserInput() 
    {
        
        //used to mark whether the user input was good or bad
        boolean good = true;
        
        //names of all the descriptors whose inputs are empty but shouldn't be
        List<String> badInputs = new ArrayList<>();
        
        //records for the batch and receivement
        Record receivement = new Record(); receivement.setSkoonieKey(keyForNew);
        Record batch = new Record(); batch.setSkoonieKey(keyForNew);
        
        //for every stored input, decide whether to add it to the list of bad
        //inputs or to store it in the batch or receivement records (or both)
        //key=Name; value=input field
        for (Map.Entry<String, JTextField> i : inputs.entrySet()) {
            
            String name = i.getKey();
            String inputText = i.getValue().getText();
            
            //holds the records that the user input needs to be stored in,
            //mapped to the descriptors that the input is for
            Map<Record, Descriptor> records = new HashMap<>();
            
            //store in the receivement record if it's the receivement Id
            if (name.equals(receivementIdDescriptorName)) {
                
                String key = receivementsTable
                                    .getDescriptorKeyByName(idDescriptorName);
                
                records.put(receivement, receivementsTable.getDescriptor(key));
                
            }
            //store in the receivement record if it's the Date
            else if (name.equals(dateDescriptorName)) {
            
                String key = receivementsTable
                                    .getDescriptorKeyByName(dateDescriptorName);
                
                records.put(receivement, receivementsTable.getDescriptor(key));
            
            }
            //store in the batch record if it's the batch Id
            else if (name.equals(batchIdDescriptorName)) {
                
                String key = batchesTable
                                    .getDescriptorKeyByName(idDescriptorName);
                
                records.put(batch, batchesTable.getDescriptor(key));
                
            }
            //if it didn't match any of the special cases, then add the input to
            //both the batch and receivement records
            else {
                
                //for receivement
                String recTableKey = receivementsTable
                                                .getDescriptorKeyByName(name);
                records.put(receivement, 
                                receivementsTable.getDescriptor(recTableKey));
                
                //for batch
                String batchTableKey = batchesTable.getDescriptorKeyByName(name);
                records.put(batch, batchesTable.getDescriptor(batchTableKey));
            
            }
            
            storeInput(name, inputText, records, badInputs);
            
        }//end of for loop
        
        //check to see if there if were any bad inputs. If there were, then the
        //user input is bad
        if (!checkBadInputs(badInputs)) { good = false; }
        
        //if all the inputs are good, then store the records will be used and
        //need to be stored in their respective tables
        else { 
            receivementsTable.addRecord(receivement); 
            batchesTable.addRecord(batch);
        }
        
        return good;
        
    }//end of ReceiveMaterialWindow::getUserInput
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::setLoading
    //
    // Sets the window to loading or not loading, depending on pLoading
    //
    
    public void setLoading(boolean pLoading) 
    {
        
        loading = pLoading;
        
        for (JButton b : buttonsToDisable) { b.setEnabled(!loading); }
        
    }// end of ReceiveMaterialWindow::setLoading
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::storeInput
    //
    // This function either stores pName in pBadInputs or adds pInputText to all
    // the records in pRecords, depending on whether the input is considered
    // good or bad.
    //
    // For every Record in pRecords:
    //      If the Descriptor tied to the Record is required to have a value, 
    //      but pInputText is empty, then the input is considered to be bad and 
    //      pName is added to pBadInputs.
    //
    //      If pInputText is NOT empty, it is always add to all of the records
    //      in pRecords.
    // 
    //      If pInputText is empty but pDescriptor is NOT required, the input is
    //      considered good. But since it is empty, it will not be added to any
    //      of  the records.
    //
    // Note that in order for pName not to be added to pBadInputs, the input
    // must be considered good for all of the Records and Descriptors in 
    // pRecords.
    //
    
    private void storeInput(String pName, String pInputText, 
                            Map<Record, Descriptor> pRecords,
                            List<String> pBadInputs) 
    {
        
        boolean empty = pInputText.isEmpty();
        
        for (Map.Entry<Record, Descriptor> e : pRecords.entrySet()) {
        
            Record r = e.getKey();
            Descriptor d = e.getValue();
            
            //bad input
            if (d.getRequired() && empty) { pBadInputs.add(pName); }
            
            //good input, but only store if not empty
            else if (!empty) { r.addValue(d.getSkoonieKey(), pInputText); }
        
        }
        
    }// end of ReceiveMaterialWindow::setLoading
    //--------------------------------------------------------------------------

}//end of class ReceiveMaterialWindow
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------