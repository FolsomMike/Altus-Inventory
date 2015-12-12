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
* NOTE:
*       To set the size of the button, pass in a width and height through the
*       constructor. To let the button just wrap around the text, pass in -1
*       for both pWidth and pHeight.
*
*/

//------------------------------------------------------------------------------

package view.classic;

//------------------------------------------------------------------------------

import java.awt.Font;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import toolkit.Tools;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class CustomTextButton
//

public class CustomTextButton extends JButton
{
    
    private final int width;
    private final int height;
    
    //--------------------------------------------------------------------------
    // CustomTextButton::CustomTextButton (constructor)
    //

    public CustomTextButton(String pText, int pWidth, int pHeight)
    {
        
        super(pText);
        
        width = pWidth;
        height = pHeight;

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
        
        //set the margin if the width and height are not meant to be set,
        //otherwise set the width and height
        if (width == -1 || height == -1) {setMargin(new Insets(5, 10, 5, 10));}
        else { Tools.setSizes(this, width, height); }

    }//end of CustomTextButton::init
    //--------------------------------------------------------------------------
    
}//end of class CustomTextButton
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
