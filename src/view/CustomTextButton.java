/*******************************************************************************
* Title: CustomTextButton.java
* Author: Hunter Schoonover
* Date: 11/21/15
*
* Purpose:
*
* This class is a customized JButton, with a preset look and feel specifically
* designed for text.
*
*/

//------------------------------------------------------------------------------

package view;

//------------------------------------------------------------------------------

import java.awt.Font;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.SwingConstants;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class CustomTextButton
//

public class CustomTextButton extends JButton
{
    
    //--------------------------------------------------------------------------
    // CustomTextButton::CustomTextButton (constructor)
    //

    public CustomTextButton(String pText)
    {
        
        super(pText);

    }//end of CustomTextButton::CustomTextButton (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomTextButton::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    public void init()
    {
        
        //set look and feel of button
        setFont(new Font("Arial", Font.PLAIN, 13));
        setFocusPainted(false);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setVerticalTextPosition(SwingConstants.BOTTOM);
        setMargin(new Insets(5, 10, 5, 10));

    }//end of CustomTextButton::init
    //--------------------------------------------------------------------------
    
}//end of class CustomTextButton
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
