/*******************************************************************************
* Title: AltusJDialog.java
* Author: Hunter Schoonover
* Date: 11/23/15
*
* Purpose:
*
* This class is a custom JDialog designed specifically for the Altus Inventory
* program. It provides base functions and actions that most of the JDialogs in
* the program require.
*
*/

//------------------------------------------------------------------------------

package view;

//------------------------------------------------------------------------------

import java.awt.Color;
import java.awt.Component;
import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.Dialog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.Batch;
import model.Customer;
import model.MySQLDatabase;
import model.Rack;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class AltusJDialog
//

public abstract class AltusJDialog extends JDialog
{
    
    protected abstract void createGui();
    
    static private final String actionId = "AltusJDialog";
    static public String getActionId() { return actionId; }
    
    private final MainFrame mainFrame;
    protected final MainFrame getMainFrame() { return mainFrame; }
    private final MainView mainView;
    protected final MainView getMainView() { return mainView; }
    private JPanel mainPanel;
    protected final void addToMainPanel(Component pC) { mainPanel.add(pC); }
    
    private final MySQLDatabase db = new MySQLDatabase();
    protected final MySQLDatabase getDatabase() { return db; }
    
    private ArrayList<Customer> customers;
    protected final ArrayList<Customer> getCustomers() { return customers; }
    private ArrayList<Rack> racks;
    protected final ArrayList<Rack> getRacks() { return racks; }
    
    private final int rowSpacer = 20;
    
    //Input Field Variables
    private final Map<String, InputField> inputFields = new HashMap<>();
    private final int inputWidth            = 130;
    private final int inputWidthWide        = 200;
    private final int inputWidthFull        = 410;
    private final int inputHeight           = 25;
    protected final int getInputHeight() { return 25; }
    
    //Labels
    private final String addressLine1Label = "Address Line 1";
    public String getAddressLine1Label() { return addressLine1Label; }
    
    private final String addressLine2Label = "Address Line 2";
    public String getAddressLine2Label() { return addressLine2Label; }
    
    private final String cityLabel = "City";
    public String getCityLabel() { return cityLabel; }
    
    private final String customerLabel = "Customer";
    public String getCustomerLabel() { return customerLabel; }
    
    private final String dateLabel = "Date (YYYY-MM-DD)";
    public String getDateLabel() { return dateLabel; }
    
    private final String idLabel = "Id";
    public String getIdLabel() { return idLabel; }
    
    private final String nameLabel = "Name";
    public String getNameLabel() { return nameLabel; }
    
    private final String quantityLabel = "Quantity";
    public String getQuantityLabel() { return quantityLabel; }
    
    private final String rackLabel = "Rack";
    public String getRackLabel() { return rackLabel; }
    
    private final String stateLabel = "State";
    public String getStateLabel() { return stateLabel; }
    
    private final String totalLengthLabel = "Total Length";
    public String getTotalLengthLabel() { return totalLengthLabel; }
    
    private final String zipCodeLabel = "Zip Code";
    public String getZipCodeLabel() { return zipCodeLabel; }
    //end of Labels
    
    //Value Getters -- only call after the create function for the input field
    //                  has been called
    public String getAddressLine1Input() 
    { return inputFields.get(addressLine1Label).getValue(); }
    
    public String getAddressLine2Input() 
    { return inputFields.get(addressLine2Label).getValue(); }
    
    public String getCityInput() 
    { return inputFields.get(cityLabel).getValue(); }
    
    public String getCustomerInput() 
    { return getCustomerKey(inputFields.get(customerLabel).getValue()); }
    
    public String getDateInput() 
    { return inputFields.get(dateLabel).getValue(); }
    
    public String getIdInput() 
    { return inputFields.get(idLabel).getValue(); }
    
    public String getNameInput() 
    { return inputFields.get(nameLabel).getValue(); }
    
    public String getQuantityInput() 
    { return inputFields.get(quantityLabel).getValue(); }
    
    public String getRackInput() 
    { return getRackKey(inputFields.get(rackLabel).getValue()); }
    
    public String getStateInput() 
    { return inputFields.get(stateLabel).getValue(); }
    
    public String getTotalLengthInput() 
    { return inputFields.get(totalLengthLabel).getValue(); }
    
    public String getZipCodeInput() 
    { return inputFields.get(zipCodeLabel).getValue(); }
    //end of Value Getters
    //end of Input Field Variables

    //--------------------------------------------------------------------------
    // AltusJDialog::AltusJDialog (constructor)
    //
    // Constructor used for AltusJDialogs that are children of MainFrame.
    //

    public AltusJDialog(String pTitle, MainFrame pMainFrame, MainView pMainView)
    {

        super(pMainFrame, pTitle);
        
        mainFrame   = pMainFrame;
        mainView    = pMainView;

    }//end of AltusJDialog::AltusJDialog (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::AltusJDialog (constructor)
    //
    // Constructor used for AltusJDialogs that are children of another JDialog.
    // 

    public AltusJDialog(String pTitle, JDialog pParent, MainFrame pMainFrame,
                            MainView pMainView)
    {

        super(pParent, pTitle);
        
        mainFrame   = pMainFrame;
        mainView    = pMainView;

    }//end of AltusJDialog::AltusJDialog (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //
    
    public void init() 
    {
        
        //tell the mainFrame that this is now the active dialog
        mainFrame.setActiveDialog(this);
        
        //initialize database
        db.init();
        
        //setup window
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        
        //add a JPanel to the dialog to provide a familiar container
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setContentPane(mainPanel);
        
        //add padding to the mainPanel
        int padding = 10;
        mainPanel.setBorder(BorderFactory.createEmptyBorder
                                        (padding, padding, padding, padding));
        
        //create the window's GUI
        createGui();
        
        //arrange all the GUI items
        pack();
        
        //center the dialog and make it visible
        Tools.centerJDialog(this, mainFrame);
        setVisible(true);
        
    }// end of AltusJDialog::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::cancel
    //
    // Cancels the action of the JDialog by disposing of it.
    //
    // Children classes can override this to perform specific actions.
    //
    
    public void cancel() 
    {

        dispose();
        
    }// end of AltusJDialog::cancel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::confirm
    //
    // Confirms the action of the JDialog.
    //
    // Children classes can override this to perform specific actions.
    //

    public void confirm()
    {

    }//end of AltusJDialog::confirm
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::createAddressLine1Panel
    //
    // Creates and returns a Address Line 1 input panel.
    //

    public JPanel createAddressLine1Panel(String pInputFieldText)
    {

        return createJTextFieldPanel(addressLine1Label, pInputFieldText,
                                        "Address line 1 for the location.",
                                        inputWidthWide);

    }// end of AltusJDialog::createAddressLine1Panel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::createAddressLine2Panel
    //
    // Creates and returns a Address Line 2 input panel.
    //

    public JPanel createAddressLine2Panel(String pInputFieldText)
    {

        return createJTextFieldPanel(addressLine2Label, pInputFieldText,
                                        "Address line 2 for the location.",
                                        inputWidthWide);

    }// end of AltusJDialog::createAddressLine2Panel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::createButton
    //
    // Creates and returns a JButton Panel, using the parameters for 
    // individuality.
    //
    
    protected final JButton createButton(String pText, String pTip, 
                                            String pActionId) 
    {
        
        CustomTextButton btn = new CustomTextButton(pText, 150, 30);
        btn.init();
        btn.addActionListener(mainView);
        btn.setToolTipText(pTip);
        btn.setActionCommand(Tools.generateActionCommand(pActionId, pText));
        btn.setAlignmentX(LEFT_ALIGNMENT);
        
        return btn;
        
    }// end of AltusJDialog::createButton
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::createCancelConfirmPanel
    //
    // Creates and returns a Cancel/Confirm panel.
    //

    protected final JPanel createCancelConfirmPanel(String pConfirmButtonText,
                                                String pConfirmButtonToolTip)
    {

        CancelConfirmPanel panel = new CancelConfirmPanel(pConfirmButtonText, 
                                                    pConfirmButtonToolTip, 
                                                    actionId, mainView);
        panel.init();

        return panel;

    }// end of AltusJDialog::createCancelConfirmPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::createCityPanel
    //
    // Creates and returns a City input panel.
    //

    public JPanel createCityPanel(String pInputFieldText)
    {

        return createJTextFieldPanel(cityLabel, pInputFieldText,
                                        "City for the location.",
                                        inputWidth);

    }// end of AltusJDialog::createCityPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::createComboBoxPanel
    //
    // Creates and returns a panel containing pLabel and a combo box populated
    // with pComboValues -- the value at index 0 is selected.
    //
    // The JComboBox contained in this input panel is stored in an InputField
    // which is then stored in inputFields, with pLabelText as they key.
    //

    private JPanel createComboBoxPanel(String pLabel, String[] pComboValues,
                                        int pSelectIndex, String pToolTip,
                                        int pWidth)
    {
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        JLabel label = new JLabel(pLabel);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setAlignmentY(TOP_ALIGNMENT);
        panel.add(label);
        
        //put the selection phrase into the array
        pComboValues[0] = "--Select--";
        
        //Create the combo box, select item at pSelectIndex
        JComboBox combo = new JComboBox(pComboValues);
        combo.setAlignmentX(LEFT_ALIGNMENT);
        combo.setAlignmentY(TOP_ALIGNMENT);
        combo.setToolTipText(pToolTip);
        combo.setSelectedIndex(pSelectIndex);
        combo.setBackground(Color.white);
        Tools.setSizes(combo, pWidth, inputHeight);
        //store a reference to the input field
        inputFields.put(pLabel, new InputField(combo));
        panel.add(combo);
        
        return panel;

    }// end of AltusJDialog::createComboBoxPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::createCustomerPanel
    //
    // Creates and returns a Customer input panel.
    //

    public JPanel createCustomerPanel(Batch pBatch)
    {
        
        //get customers from the database
        customers = db.getCustomers();
        
        //extract the names from customers, leaving an empty space at index 0 
        //for the selection phrase
        int selectIndex = 0;
        String[] names = new String[customers.size()+1];
        for (int i=0; i<customers.size(); i++) {
            names[i+1] = customers.get(i).getName();
            
            //if the customer matches the customer for
            //pBatch, then set the selection index to the
            //index of that customer
            if (pBatch != null && pBatch.getCustomerKey()
                                    .equals(customers.get(i).getSkoonieKey())) {
                selectIndex = i+1;
            }
        }
        
        return createComboBoxPanel(customerLabel, names, selectIndex,
                                    "Select a customer.", inputWidthFull);

    }// end of AltusJDialog::createCustomerPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::createDatePanel
    //
    // Creates and returns a Date input panel.
    //

    public JPanel createDatePanel(String pInputFieldText)
    {

        return createJTextFieldPanel(dateLabel, pInputFieldText,
                                        "What date was the material received?",
                                        inputWidth);

    }// end of AltusJDialog::createDatePanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::createIdPanel
    //
    // Creates and returns an Id input panel.
    //

    public JPanel createIdPanel(String pInputFieldText)
    {

        return createJTextFieldPanel(idLabel, pInputFieldText, 
                                        "Give the material a reference ID.", 
                                        inputWidth);

    }// end of AltusJDialog::createIdPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::createJTextFieldPanel
    //
    // Creates and returns an input panel containing a JLabel and a JTextField.
    //
    // The JTextField contained in this input panel is stored in an InputField
    // which is then stored in inputFields, with pLabel as they key.
    //

    private JPanel createJTextFieldPanel(String pLabel, 
                                            String pInputFieldText,
                                            String pToolTip, int pWidth)
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
        Tools.setSizes(field, pWidth, inputHeight);
        //store a reference to the input field
        inputFields.put(pLabel, new InputField(field));
        panel.add(field);

        return panel;

    }// end of AltusJDialog::createJTextFieldPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::createNamePanel
    //
    // Creates and returns a Name input panel.
    //

    public JPanel createNamePanel(String pInputFieldText)
    {

        return createJTextFieldPanel(nameLabel, pInputFieldText, 
                                        "The display name.", 
                                        inputWidth);

    }// end of AltusJDialog::createNamePanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::createQuantityPanel
    //
    // Creates and returns a Quantity input panel.
    //

    public JPanel createQuantityPanel(String pInputFieldText)
    {

        return createJTextFieldPanel(quantityLabel, pInputFieldText, 
                                        "How many pieces of pipe?", 
                                        inputWidth);

    }// end of AltusJDialog::createQuantityPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::createRackPanel
    //
    // Creates and returns a Rack input panel.
    //

    public JPanel createRackPanel(Batch pBatch)
    {
        
        //get racks from the database
        racks = db.getRacks();
        
        //extract the names from racks, leaving an empty space at index 0 for 
        //the selection phrase 
        int selectIndex = 0;
        String[] names = new String[racks.size()+1];
        for (int i=0; i<racks.size(); i++) {
            names[i+1] = racks.get(i).getName();
            
            //if the customer matches the customer for
            //pBatch, then set the selection index to the
            //index of that customer
            if (pBatch != null && pBatch.getRackKey()
                                    .equals(racks.get(i).getSkoonieKey())) {
                selectIndex = i+1;
            }
        }
        
        return createComboBoxPanel(rackLabel, names, selectIndex,
                                    "Select a rack.", inputWidth);

    }// end of AltusJDialog::createRackPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::createRow
    //
    // Creates and returns a row of input panels using pInputPanels.
    //

    protected final JPanel createRow(JPanel[] pInputPanels)
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
        
        for (int i=0; i<pInputPanels.length; i++) {
            if (i>0) { panel.add(Tools.createHorizontalSpacer(10)); }
            panel.add(pInputPanels[i]);
        }
        
        return panel;

    }// end of AltusJDialog::createRow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::createRowSpacer
    //
    // Creates and returns a vertical spacer the height of rowSpacer.
    //

    protected final Component createRowSpacer()
    {

        return Tools.createVerticalSpacer(rowSpacer);

    }// end of AltusJDialog::createRowSpacer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::createStatePanel
    //
    // Creates and returns a State input panel.
    //

    public JPanel createStatePanel(String pInputFieldText)
    {

        return createJTextFieldPanel(stateLabel, pInputFieldText,
                                        "State for the location.",
                                        inputWidth);

    }// end of AltusJDialog::createStatePanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::createTotalLengthPanel
    //
    // Creates and returns a Total Length input panel.
    //

    public JPanel createTotalLengthPanel(String pInputFieldText)
    {

        return createJTextFieldPanel(totalLengthLabel, pInputFieldText, 
                                        "What is the total length of the pipe?", 
                                        inputWidth);

    }// end of AltusJDialog::createTotalLengthPanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::createZipCodePanel
    //
    // Creates and returns a Zip Code input panel.
    //

    public JPanel createZipCodePanel(String pInputFieldText)
    {

        return createJTextFieldPanel(zipCodeLabel, pInputFieldText, 
                                        "Zip code for the location.",
                                        inputWidth);

    }// end of AltusJDialog::createZipCodePanel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::getCustomerKey
    //
    // Returns the Skoonie Key associated with pCustomerName
    //

    private String getCustomerKey(String pCustomerName)
    {
        
        String key = "";
        
        for (Customer c : customers) {
            if(pCustomerName.equals(c.getName())) { key = c.getSkoonieKey(); }
        }
        
        return key;

    }// end of AltusJDialog::getCustomerKey
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::getRackKey
    //
    // Returns the Skoonie Key associated with pRackName
    //

    private String getRackKey(String pRackName)
    {
        
        String key = "";
        
        for (Rack r : racks) {
            if(pRackName.equals(r.getName())) { key = r.getSkoonieKey(); }
        }
        
        return key;

    }// end of AltusJDialog::getRackKey
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // AltusJDialog::setMainPanelLayout
    //
    // Sets the layout of mainPanel to pLayout.
    //

    protected final void setMainPanelLayout(int pLayout)
    {

        mainPanel.setLayout(new BoxLayout(mainPanel, pLayout));

    }// end of AltusJDialog::setMainPanelLayout
    //--------------------------------------------------------------------------

}//end of class AltusJDialog
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class InputField
//
// This class is used to store an instance to an input field, such as a 
// JTextField or a JComboBox, and retrieve the value contained inside of it.
//
// NOTES:   Should only be used for input fields that hold strings. 
//          Should be able to get the selected value of the JComboBox by casting
//              the selected object as a String.
//
//

class InputField extends Object
{
    
    private final JTextField jTextField;
    private final JComboBox jComboBox;
    
    //--------------------------------------------------------------------------
    // InputField::InputField (constructor)
    //
    // Constructor for use when input is a JTextField.
    //

    public InputField(JTextField pField)
    {
        
        jTextField   = pField;
        jComboBox    = null;

    }//end of InputField::InputField (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // InputField::InputField (constructor)
    //
    // Constructor for use when input is a JComboBox.
    //

    public InputField(JComboBox pCombo)
    {
        
        jComboBox    = pCombo;
        jTextField   = null;

    }//end of InputField::InputField (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // InputField::getValue
    //
    // Gets the String value contained in the Input that was passed in upon 
    // construction.
    //

    public String getValue()
    {
        
        if (jComboBox != null) { return (String)jComboBox.getSelectedItem(); }
        
        if (jTextField != null) { return jTextField.getText(); }
        
        return "";

    }//end of InputField::getValue
    //--------------------------------------------------------------------------

}//end of class InputField
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------