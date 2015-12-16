/*******************************************************************************
* Title: MColor.java
* Author: Hunter Schoonover
* Date: 12/10/15
*
* Purpose:
*
* This class adds functionality to the Color class.
*
*/

//-----------------------------------------------------------------------------

package model;

//------------------------------------------------------------------------------

import java.awt.Color;

//------------------------------------------------------------------------------
// class MColor
//

public class MColor extends Color 
{

    //--------------------------------------------------------------------------
    // MColor::MColor (constructor)
    //

    MColor(int pR, int pG, int pB)
    {

        super(pR,pG,pB);

    }// end of MColor::MColor
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MColor::MColor (constructor)
    //

    //public MColor()
    MColor(Color pColor)
    {

        super(pColor.getRed(), pColor.getGreen(), pColor.getBlue());

    }// end of MColor::MColor (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MColor::fromString (static)
    //
    // Calls fromString(String pString, Color pDefault) with default color of 
    // WHITE.
    //

    static public MColor fromString(String pString)
    {

        return MColor.fromString(pString, Color.WHITE);

    }// end of MColor::fromString (static)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MColor::fromString (static)
    //
    // Creates a color to match that defined by pString.  If pString matches one
    // of the standard colors (BLACK, WHITE, RED, etc.) the color will be set
    // accordingly. If pString does not match a standard color, the color will 
    // be set assuming the string is in the format rrr,ggg,bbb.  If a color 
    // value is greater than 255 or less than 0, it will be forced to 255 or 0 
    // respectively.
    //
    // If an error occurs parsing the string, the color will be set to pDefault.
    //

    static public MColor fromString(String pString, Color pDefault)
    {

        Color match = pDefault;
        boolean exit = false;

        pString = pString.toUpperCase();

        //if the color name matches a standard color, use that color
        switch (pString) {
            case "BLACK":
                match = Color.BLACK;
                exit = true;
                break;
            case "BLUE":
                match = Color.BLUE;
                exit = true;
                break;
            case "CYAN":
                match = Color.CYAN;
                exit = true;
                break;
            case "DARK_GRAY":
                match = Color.DARK_GRAY;
                exit = true;
                break;
            case "GRAY":
                match = Color.GRAY;
                exit = true;
                break;
            case "GREEN":
                match = Color.GREEN;
                exit = true;
                break;
            case "LIGHT GRAY":
                match = Color.LIGHT_GRAY;
                exit=true;
                break;
            case "MAGENTA":
                match = Color.MAGENTA;
                exit = true;
                break;
            case "ORANGE":
                match = Color.ORANGE;
                exit = true;
                break;
            case "PINK":
                match = Color.PINK;
                exit = true;
                break;
            case "RED":
                match = Color.RED;
                exit = true;
                break;
            case "WHITE":
                match = Color.WHITE;
                exit = true;
                break;
            case "YELLOW":
                match = Color.YELLOW;
                exit = true;
                break;
        }

        //if color found, exit with that color
        if (exit) { return new MColor(match); }

        //string does not name a standard color so assume it is rrr,ggg,bbb 
        //format
        //if a format error occurs, return the default color
        String rgb; int lRed; int lGreen; int lBlue;
        int comma, prevComma;
        try{
            //extract red value and convert to integer
            comma = pString.indexOf(',');
            if (comma == -1) { return new MColor(pDefault); }
            rgb = pString.substring(0, comma).trim();
            lRed = Integer.valueOf(rgb);

            //extract green value and convert to integer
            prevComma = comma; comma = pString.indexOf(',', prevComma+1);
            if (comma == -1) { return new MColor(pDefault); }
            rgb = pString.substring(prevComma+1, comma).trim();
            lGreen = Integer.valueOf(rgb);

            //extract blue value and convert to integer
            prevComma = comma;
            rgb = pString.substring(prevComma+1).trim();
            lBlue = Integer.valueOf(rgb);

        }
        catch(NumberFormatException e){
            //format error so return default color
            return new MColor(pDefault);
        }

        //correct illegal values
        if (lRed < 0) {lRed = 0;} if (lRed > 255) {lRed = 255;}
        if (lGreen < 0) {lGreen = 0;} if (lGreen > 255) {lGreen = 255;}
        if (lBlue < 0) {lBlue = 0;} if (lBlue > 255) {lBlue = 255;}

        //create a new MColor from the rgb values
        return new MColor(lRed, lGreen, lBlue);

    }// end of MColor::fromString (static)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MColor::toString
    //
    // Returns a string representing the color.  If the color matches one of the
    // standard colors (BLACK, WHITE, RED, etc.) the string returned will match
    // those names. If color does not match a standard color, the string 
    // returned will be in the format rrr,ggg,bbb.
    //

    @Override
    public String toString()
    {

        return toString(this);

    }//end of MColor::toString
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // MColor::toString
    //
    // Returns a string representing pColor.  If pColor matches one of the
    // standard colors (BLACK, WHITE, RED, etc.) the string returned will match
    // those names. If pColor does not match a standard color, the string 
    // returned will be in the format rrr,ggg,bbb.
    //

    static public String toString(Color pColor)
    {

        //if the color matches a standard color, return that name
        String color;
        if (pColor.equals(Color.BLACK)) { color = "BLACK"; }
        else if (pColor.equals(Color.BLUE)) { color = "BLUE"; }
        else if (pColor.equals(Color.CYAN)) { color = "CYAN"; }
        else if (pColor.equals(Color.DARK_GRAY)) { color = "DARK_GRAY"; }
        else if (pColor.equals(Color.GRAY)) { color = "GRAY"; }
        else if (pColor.equals(Color.GREEN)) { color = "GREEN"; }
        else if (pColor.equals(Color.LIGHT_GRAY)) { color = "LIGHT_GRAY"; }
        else if (pColor.equals(Color.MAGENTA)) { color = "MAGENTA"; }
        else if (pColor.equals(Color.ORANGE)) { color = "ORANGE"; }
        else if (pColor.equals(Color.PINK)) { color = "PINK"; }
        else if (pColor.equals(Color.RED)) { color = "RED"; }
        else if (pColor.equals(Color.WHITE)) { color = "WHITE"; }
        else if (pColor.equals(Color.YELLOW)) { color = "YELLOW"; }
        
        //if not a standard color, return as rrr,ggg,bbb
        else { 
            color = pColor.getRed() + "," 
                    + pColor.getGreen() + "," 
                    + pColor.getBlue();
        }

        return color;

    }//end of MColor::toString
    //--------------------------------------------------------------------------

}// end of class MColor
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------