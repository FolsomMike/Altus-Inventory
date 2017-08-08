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
import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
import javax.swing.ScrollPaneConstants;
import shared.Descriptor;
import shared.Record;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class ReceiveMaterialWindow
//

public class ReceiveMaterialWindow extends AltusJDialog implements CommandHandler
{
    
    private Record receivement;
    
    private List<?> descriptors;
    
    private final List<DescriptorInput> inputs = new ArrayList<>();
    
    private JPanel inputsPanel;
    private final int padding = 10;
    private final int inputPanelWidth = 200;
    private final int inputPanelsPerRow = 3;
    private final int inputPanelSpacer = 10;
    
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
        (new Command(Command.GET_RECEIVEMENT_DESCRIPTORS)).perform();
        
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
            
            case Command.RECIEVEMENT_DESCRIPTORS:
                displayReceivementInputs(pCommand);
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
        command.put(Command.RECEIVEMENT, receivement);
        
        //put the descriptors into the command
        command.put(Command.RECIEVEMENT_DESCRIPTORS, descriptors);
        
        command.perform();
        
        //dispose of this the window
        dispose();
        
    }//end of ReceiveMaterialWindow::confirm
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::createAndStoreDescriptorInput
    //
    // Creates and stores a DescriptorInput object for pDescriptor.
    //

    private void createAndStoreDescriptorInput(Descriptor pDescriptor,
                                                String pName)
    {

        //create and store a DescriptorInput for the Descriptor
        DescriptorInput input = new DescriptorInput(pDescriptor, "");
        input.setDisplayName(pName);
        input.init();
        inputs.add(input);

    }// end of ReceiveMaterialWindow::createAndStoreDescriptorInput
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
    // ReceiveMaterialWindow::createInputs
    //
    // Creates DescriptorInput objects for all of the descriptors in 
    // pDescriptors.
    //

    private void createInputs(List<?> pDescriptors)
    {
        
        for (Object o : pDescriptors) {
            
            Descriptor d = (Descriptor)o;
            
            String name;
            
            switch (d.getName()) {
            
                //descriptor is the batch id
                case idDescriptorName:
                    name = "Batch Id";
                    break;
                    
                default:
                    name = d.getName();
                    break;
                    
            }
            
            //create and store a DescriptorInput for this Descriptor
            createAndStoreDescriptorInput(d, name);

        }

    }// end of ReceiveMaterialWindow::createInputs
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
    // ReceiveMaterialWindow::displayReceivementInputs
    //
    // Displays the receivement inputs using the descriptors extracted
    // from pCommand and then tells the window that we're done loading.
    //
    
    private void displayReceivementInputs(Command pCommand) 
    {
        
        //set up the receivements table using the descriptors
        descriptors = (List<?>)pCommand.get(Command.RECIEVEMENT_DESCRIPTORS);

        createInputs(descriptors);
        
        //make sure the inputs list in the right order
        reorderDescriptorInputs(inputs);
        
        setLoading(false);
        
        //add the DescriptorInput objects to the inputsPanel
        List<JPanel> row = new ArrayList<>();
        for (int i=0; i<inputs.size(); i++) {
            
            //add the next panel to the row
            row.add(inputs.get(i));
            
            //if we've reached the number of panels
            //allowed per row, or the end of the
            //panels, create a row from the input 
            //panels in the row list, and empty the
            //list to start a new row
            if (row.size()>=inputPanelsPerRow || i>=(inputs.size()-1)) {
                inputsPanel.add(createRow(row, inputPanelSpacer));
                inputsPanel.add(Tools.createVerticalSpacer(10));
                row.clear();
            }
            
        }
        
        //repack
        pack();
        
    }//end of ReceiveMaterialWindow::displayReceivementInputs
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
        
        receivement = new Record();
        
        //for every stored input, decide whether to add it to the list of bad
        //inputs or to store it in the receivement record
        for (DescriptorInput input : inputs) {
            
            String name = input.getDisplayName();
            String text = input.getUserInput();
            boolean required = input.getDescriptor().getRequired();
            
            //this is a bad input if the there is no text, but there should be
            if (text.isEmpty() && required) {
                badInputs.add(name);
            }
            
            //only add if it's not empty
            else if (!text.isEmpty()) {
                receivement.addValue(input.getDescriptor().getSkoonieKey(), 
                                        text);
            }
            
        }
                
        //check to see if there if were any bad inputs. If there were, then the
        //user input is bad
        if (!checkBadInputs(badInputs)) { good = false; }
        
        return good;
        
    }//end of ReceiveMaterialWindow::getUserInput
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::reorderDescriptorInputs
    //
    // Reorders the DescriptorInputs found in pDescriptorInputs by making sure
    // that the input representing the receivement id is always first, that the
    // input representing the batch id is always second, and that the input
    // representing the date is always third.
    //
    
    private void reorderDescriptorInputs(List<DescriptorInput> pDescriptorInputs) 
    {
        
        //we only need to ensure that 3 inputs are moved to the top
        //of the list, so we only need an array with a size of 3
        DescriptorInput[] firstInputs = new DescriptorInput[3];
        
        for (DescriptorInput input : pDescriptorInputs) {
            switch (input.getDisplayName()) {
                
                //make sure receivement id is first input
                case receivementIdDescriptorName:
                    firstInputs[2] = input;
                    break;
                    
                //make sure batch id is second input
                case batchIdDescriptorName:
                    firstInputs[1] = input;
                    break;
                
                //make sure date is input
                case dateDescriptorName:
                    firstInputs[0] = input;
                    break;
            
            }
        }
        
        //remove the DescriptorInputs in the firstInputs array from wherever
        //they are in the inputs list and add them to the beggining
        for (DescriptorInput d : firstInputs) { 
            pDescriptorInputs.remove(d);
            pDescriptorInputs.add(0, d);
        }
        
    }// end of ReceiveMaterialWindow::reorderDescriptorInputs
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
            
            //bad input -- add pName to pBadInputs if its not already in there
            if (d.getRequired() && empty && !pBadInputs.contains(pName)) {
                pBadInputs.add(pName);
            }
            
            //good input, but only store if not empty
            else if (!empty) { r.addValue(d.getSkoonieKey(), pInputText); }
        
        }
        
    }// end of ReceiveMaterialWindow::storeInput
    //--------------------------------------------------------------------------

}//end of class ReceiveMaterialWindow
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------