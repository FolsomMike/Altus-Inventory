/*******************************************************************************
* Title: DescriptorInput.java
* Author: Hunter Schoonover
* Date: 01/12/16
*
* Purpose:
*
* This class is used to tie a Descriptor to an input. It determines what kind
* of input field is necessary based on objects and values found in the 
* Descriptor passed in upon construction. It also handles getting the user 
* input from the input field.
* 
*/

//------------------------------------------------------------------------------

package view.classic;

//------------------------------------------------------------------------------

import static java.awt.Component.LEFT_ALIGNMENT;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import shared.Descriptor;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class DescriptorInput
//

public class DescriptorInput extends JPanel
{
    
    private final Descriptor descriptor;
    private final String originalValue;
    
    private JTextField jTextFieldInput;
    private JComboBox<PresetValue> jComboBoxInput;
    
    private final int inputFieldWidth = 200;
    
    //--------------------------------------------------------------------------
    // DescriptorInput::DescriptorInput (constructor)
    //

    public DescriptorInput(Descriptor pDescriptor, String pOriginalValue)
    {
        
        descriptor = pDescriptor;
        originalValue = pOriginalValue;

    }//end of DescriptorInput::DescriptorInput (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DescriptorInput::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    public void init()
    {
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        //DescriptorInput objects should always be added to a panel with an 
        //X_AXIS BoxLayout, meaning we should only need to worry about the 
        //vertical alignment, but just in case, we'll take care of the vertical 
        //and horizontal
        setAlignmentX(LEFT_ALIGNMENT);
        setAlignmentY(TOP_ALIGNMENT);
        
        //add the Name label to the panel
        add(createNameLabel());
        
        //add the input field to the panel
        add(createInputField());

    }//end of DescriptorInput::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DescriptorInput::createInputField
    //
    // Sets up and returns the input field.
    //
    // If the descriptor uses preset values, then a combo box is set up and
    // returned. If the descriptor doesn't use preset values, then a text field
    // is set up and returned.
    //

    private JComponent createInputField()
    {
        
        if (descriptor.getUsesPresetValues()) { return setUpComboBox(); }
        else { return setUpTextField(); }

    }//end of DescriptorInput::createInputField
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DescriptorInput::createNameLabel
    //
    // Creates a JLabel for the descriptor name and returns it.
    //

    private JLabel createNameLabel()
    {
        
        JLabel label = new JLabel(descriptor.getName());
        label.setAlignmentX(LEFT_ALIGNMENT);
        
        return label;

    }//end of DescriptorInput::createNameLabel
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DescriptorInput::getUserInput
    //
    // Gets the user input from the input that has been initialized and returns
    // it. If none of the inputs have been initialized then an empty string is
    // returned.
    //

    public String getUserInput()
    {
        
        if (jTextFieldInput!=null) { 
            return jTextFieldInput.getText();
        }
        else if (jComboBoxInput!=null) { 
            return ((PresetValue)jComboBoxInput.getSelectedItem()).getKey();
        }
        else { return ""; }

    }//end of DescriptorInput::getUserInput
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DescriptorInput::setUpComboBox
    //
    // Sets up the combo box by adding all of the preset values obtained from
    // descriptor.getPresetValues() to the combo box and selecting the proper
    // value if any match the original value.
    //

    private JComboBox setUpComboBox()
    {
        
        jComboBoxInput = new JComboBox<>();
        jComboBoxInput.setAlignmentX(LEFT_ALIGNMENT);
        Tools.setSizes(jComboBoxInput, inputFieldWidth, 25);
        
        //put all of the preset values for the descriptor into the combo box
        for (Map.Entry<String, String> p : descriptor.getPresetValues()
                                                                    .entrySet())
        {
            PresetValue v = new PresetValue(p.getKey(), p.getValue());
            jComboBoxInput.addItem(v);
            
            //if the key of the value is equal to the orginal value, then set
            //the PresetValue object to the selected item
            if(p.getKey().equals(originalValue)) {
                jComboBoxInput.setSelectedItem(v);
            }
        }
        
        return jComboBoxInput;

    }//end of DescriptorInput::setUpComboBox
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DescriptorInput::setUpTextField
    //
    // Sets up the text field by initializing jTextFieldInput and setting the
    // text to the original value.
    //

    private JTextField setUpTextField()
    {
        
        jTextFieldInput = new JTextField();
        jTextFieldInput.setAlignmentX(LEFT_ALIGNMENT);
        Tools.setSizes(jTextFieldInput, inputFieldWidth, 25);
        
        jTextFieldInput.setText(originalValue);
        
        return jTextFieldInput;

    }//end of DescriptorInput::setUpTextField
    //--------------------------------------------------------------------------
    
}//end of class DescriptorInput
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class PresetValue
//
// Purpose:
// 
// This class is used to store the key and the display name of a preset value. 
// It overrides toString() and returns the display name so that it can be used 
// in a JComboBox.
//

class PresetValue {
    
    private final String key;
    public String getKey() { return key; }
    
    private final String displayName;
    public String getDisplayName() { return displayName; }

    //--------------------------------------------------------------------------
    // PresetValue::PresetValue (constructor)
    //

    public PresetValue(String pKey, String pDisplayName)
    {
        
        key = pKey;
        displayName = pDisplayName;

    }//end of PresetValue::PresetValue (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // PresetValue::toString
    //
    // Returns the display name; the textual representation of this class is the
    // display name passed in upon construction.
    //

    @Override
    public String toString()
    {
        
        return displayName;

    }//end of PresetValue::toString
    //--------------------------------------------------------------------------

}//end of class PresetValue
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------