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
import java.awt.Color;
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
import javax.swing.border.TitledBorder;
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
    
    private Table receivements;
    private Record receivement;
    private JPanel receivementPanel;
    
    private Table batches;
    private Record batch;
    private JPanel batchPanel;
    
    private final Map<String, JTextField> inputs = new HashMap<>();
    private final List<JButton> buttonsToDisable = new ArrayList<>();
    
    private final int inputPanelWidth = 200;
    
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
        //WIP HSS// -- add command call to get receivement and batch descriptors
        
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
        
        //add the Receivement and Batch panel to the main panel
        addToMainPanel(createReceivementAndBatchPanel());
        
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
        
        //dispose of this the window
        dispose();
        
    }//end of ReceiveMaterialWindow::confirm
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::createBatchPanel
    //
    // Creates and returns the Receivement panel
    //

    private JPanel createBatchPanel()
    {

        batchPanel = new JPanel();
        batchPanel.setLayout(new BoxLayout(batchPanel,
                                                    BoxLayout.Y_AXIS));
        batchPanel.setBorder(new TitledBorder("Batch"));
        
        return batchPanel;

    }// end of ReceiveMaterialWindow::createBatchPanel
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
        
        //store the input field
        inputs.put(pDescriptor.getSkoonieKey(), field);
        
        //add the field to the panel
        panel.add(field);

        return panel;

    }// end of ReceiveMaterialWindow::createInputPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::createReceivementAndBatchPanel
    //
    // Creates and returns a scrollpane to hold the Receivement and Batch
    // panels, overriding the paintComponent() function to paint the loading 
    // image when necessary.
    //

    private JScrollPane createReceivementAndBatchPanel()
    {

        //panel to hold the Batch and Receivement panels
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        //add the Receiement panel
        panel.add(createReceivementPanel());
        
        //vertical spacer
        panel.add(Tools.createVerticalSpacer(10));
        
        //add the Batch panel
        panel.add(createBatchPanel());
        
        //put the panel holding into a scroll pane
        JScrollPane sp = new JScrollPane(panel) {
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
        Tools.setSizes(sp, 400, 300);
        
        return sp;

    }// end of ReceiveMaterialWindow::createReceivementAndBatchPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::createReceivementPanel
    //
    // Creates and returns the Receivement panel
    //

    private JPanel createReceivementPanel()
    {

        receivementPanel = new JPanel();
        receivementPanel.setLayout(new BoxLayout(receivementPanel,
                                                    BoxLayout.Y_AXIS));
        receivementPanel.setBorder(new TitledBorder("Receivement"));
        
        return receivementPanel;

    }// end of ReceiveMaterialWindow::createReceivementPanel
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
        
        for (int i=0; i<pPanels.size(); i++) {
            if (i>0) { panel.add(Tools.createHorizontalSpacer(pSpacer)); }
            panel.add(pPanels.get(i));
        }
        
        return panel;

    }// end of ReceiveMaterialWindow::createRow
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
        
        boolean good = true;
        
        //names of all the descriptors whose inputs can't be empty, but are
        List<String> badInputs = new ArrayList<>();
        
        //if there are any bad inputs, display a message to the user and set
        //good to false
        if (!badInputs.isEmpty()) {
            String title = "Inputs Cannot Be Empty";
            
            //create the message string
            String msg = "<html>The following inputs cannot be empty:<ul>";
            for (String s : badInputs) { msg += "<li><b>" +s+ "</b></li>"; }
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
        
        for(JButton b : buttonsToDisable) { b.setEnabled(!loading); }
        
    }// end of ReceiveMaterialWindow::setLoading
    //--------------------------------------------------------------------------

}//end of class ReceiveMaterialWindow
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------