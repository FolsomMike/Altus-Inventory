/*******************************************************************************
* Title: ConfigFile.java
* Author: Hunter Schoonover
* Date: 12/10/15
*
* Purpose:
*
* This class reads from a config file.
* 
* It is based on the Microsoft Windows format for ini files, using sections, 
* keywords, and values. The format has been modified and extended to allow for
* section hierarchy.
*
*/

//-----------------------------------------------------------------------------

package model;

import java.awt.Color;
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class ConfigFile
//
// This class reads from config files.
//
// The file will be read until the end is reached or until a line with the tag
// "[Header End]" is reached. This allows header of data files to be read by 
// this class without reading the data which follows.
//
// NOTE:    Do not call the save() function for data files read in this manner 
//          or the file will be overwritten with just the header info and the 
//          data will be lost.
//
// NOTE:    The save() function in this class should be the only way to cause
//          the file to be saved.  Placing save code in the finalize() function
//          is not very reliable because it won't be called until the Java
//          Virtual Machine discards the object from memory - it is not done 
//          when the object seems to be discarded in code.
//

public class ConfigFile extends Object
{

    private final String fileFormat = "UTF-8";

    private ArrayList<String> buffer;
    public String filename;

    DecimalFormat[] DecimalFormats;
    
    private String generateSectionStartTag(String pS) { return "["+pS+"]"; }
    private String generateSectionEndTag(String pS) { return "[/"+pS+"]"; }

    //--------------------------------------------------------------------------
    // ConfigFile::ConfigFile (constructor)
    //

    public ConfigFile(String pFilename)
    {

        filename = pFilename;

    }// end of ConfigFile::ConfigFile (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // ConfigFile::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    public void init() throws IOException
    {

        loadFile();

    }// end of ConfigFile::init
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // ConfigFile::getDecimalFormat
    //
    // Returns a decimal format object with the number of digits after the 
    // decimal point as specified by pPrecision.
    //

    public DecimalFormat getDecimalFormat(int pPrecision)
    {

        //if illegal value, use precision of 2
        if ((pPrecision < 0) || (pPrecision >= DecimalFormats.length)) {
            pPrecision = 2;
        }

        return DecimalFormats[pPrecision];

    }// end of ConfigFile::getDecimalFormat
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ConfigFile::getSectionBoundaries
    //
    // Finds the indexes for the start and end tags of pSection and stores them
    // in pParams.
    // 
    // To handle hierarchical structures, the function only searches the range
    // of the buffer specified by the start and end indexes in pParams for
    // the section.
    //
    // If the section you are searching for is emebedded within another section,
    // then set the start and end indexes inside pParams to the indexes of the
    // start and end tags of the parent section.
    //
    // If the section you are searching for is not embedded within another
    // section, then set the start and end indexes inside pParams to 0 and to
    // the length of the buffer. This will search the entire buffer instead of
    // just one section.
    //

    private void getSectionBoundaries(AttributesParameters pParams, 
                                        String pSection)
    {

        //default to -1 in case function returns without finding pSection
        int startIndex = -1; 
        int endIndex = -1;

        String startTag = generateSectionStartTag(pSection);
        String endTag = generateSectionEndTag(pSection);
        for (int i = pParams.startIndex; i<pParams.endIndex; i++) {

            if (buffer.get(i).startsWith(startTag)) { startIndex = i; }
            else if (buffer.get(i).startsWith(endTag)) { endIndex = i; break; }

        }

        pParams.startIndex = startIndex;
        pParams.endIndex = endIndex;

    }// end of ConfigFile::getSectionBoundaries
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // ConfigFile::getValue
    //
    // Returns a string containing the value of pKey found in pSection, or an
    // empty string if it could not be found.
    //
    // If the value you are attempting to get exists in the outer most section 
    // and no hierarchail system needs to be specified, then pSection should 
    // just be set to the section and pKey to the key.
    // For example: to grab the value of "lot number" in section "Group 3", you
    //              would set pSection to "Group 3" and pKey to "lot number"
    //
    // On the other hand, if a value is embedded deeply within sections, then 
    // pSection should consist of all the hierarchy members separated by the 
    // pipe operator (|) in descending order. 
    // For example: to grab the value of "tread" in section "Tire 1", which is 
    //              in  section "Car 3", which is in section "Group 3", you
    //              would set pSection to "Group 3|Car 3|Tire 1" and pKey to 
    //              "tread"
    //

    private String getValue(String pSection, String pKey)
    {
        
        //define a new paramters to be used while finding the value
        AttributesParameters params = new AttributesParameters(0, buffer.size());
        
        //perform hierarchical operations to ensure that the key is grabbed
        //from the proper section
        for (String section : pSection.split("\\|")) { 
            
            getSectionBoundaries(params, section); 
            
            //if the section does not exist, then the value will not be found
            //where specified -- just return empty string
            if (params.startIndex == -1) { return ""; }
            
        }
        
        return getValue(params, pKey);

    }// end of ConfigFile::getValue
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ConfigFile::getValue
    //
    // Returns a string containing the value for pKey if it exists within the
    // range specified by the start and end indexes in pParams.
    //
    // Parameter pParams.keyIndex will be set to the index position in buffer of
    // the line containing the pKey string.
    //
    // If no matching Key could be found, the string returns empty and 
    // pParams.keyIndex will be set to -1.
    //

    private String getValue(AttributesParameters pParams, String pKey)
    {
        
        //set keyIndex in pParams to -1 -- will be changed if getting value
        //is successful
        pParams.keyIndex = -1;
        
        //add '=' to the search phrase to make sure a partial match will fail
        String key = pKey.toLowerCase() + "=";  
        String line = "";      
        int matchIndex = -1;
        
        //search until the endIndex or the end of the buffer is reached
        for 
        (int i = pParams.startIndex; i<pParams.endIndex||i<buffer.size(); i++) 
        {
            line = buffer.get(i).toLowerCase();
            if (line.toLowerCase().startsWith(key)) { matchIndex = i; }
        }
        
        //return empty string if the key was not found
        if (matchIndex == -1) { return ""; }
        
        //look for '=' symbol, if not found then return empty string
        int indexOfEqual;
        if ((indexOfEqual = line.indexOf("=")) == -1) { return ""; }
        
        //return the part of the line after the '=' sign
        //return empty string on error
        try { return line.substring(indexOfEqual+1); }
        catch(StringIndexOutOfBoundsException e){ return ""; }

    }// end of ConfigFile::getValue
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ConfigFile::loadFile
    //
    // Load the file into the buffer.
    //
    // The file will be read until the end is reached or until a line with the 
    // tag "[Header End]" is reached.
    //

    public void loadFile() throws IOException
    {

        //create a vector to hold the lines of text read from the file
        buffer = new ArrayList<>(1000);

        //create various decimal formats
        DecimalFormats = new DecimalFormat[11];
        DecimalFormats[0]   = new DecimalFormat("#");
        DecimalFormats[1]   = new DecimalFormat("#.#");
        DecimalFormats[2]   = new DecimalFormat("#.##");
        DecimalFormats[3]   = new DecimalFormat("#.###");
        DecimalFormats[4]   = new DecimalFormat("#.####");
        DecimalFormats[5]   = new DecimalFormat("#.#####");
        DecimalFormats[6]   = new DecimalFormat("#.######");
        DecimalFormats[7]   = new DecimalFormat("#.#######");
        DecimalFormats[8]   = new DecimalFormat("#.########");
        DecimalFormats[9]   = new DecimalFormat("#.#########");
        DecimalFormats[10]  = new DecimalFormat("#.##########");

        //create a buffered reader stream to the language file
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader in = null;

        try {

            fileInputStream = new FileInputStream(filename);

            inputStreamReader = new InputStreamReader(fileInputStream, 
                                                                    fileFormat);

            in = new BufferedReader(inputStreamReader);

            String line;

            //read until end of file reached or "[Header End]" tag reached
            while ((line = in.readLine()) != null) {
                buffer.add(line);
                if (line.equals("[Header End]")) { break; }
            }

        }
        catch (FileNotFoundException e) {}
        catch(IOException e) {
            logSevere(e.getMessage() + " - Error: 284");
            throw new IOException();
        }
        finally {
            if (in != null) {in.close();}
            if (inputStreamReader != null) {inputStreamReader.close();}
            if (fileInputStream != null) {fileInputStream.close();}
        }

    }// end of ConfigFile::loadFile
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // ConfigFile::logSevere
    //
    // Logs pMessage with level SEVERE using the Java logger.
    //

    public void logSevere(String pMessage)
    {

        Logger.getLogger(getClass().getName()).log(Level.SEVERE, pMessage);

    }// end of ConfigFile::logSevere
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // ConfigFile::logStackTrace
    //
    // Logs stack trace info for exception pE with pMessage at level SEVERE using
    // the Java logger.
    //

    public void logStackTrace(String pMessage, Exception pE)
    {

        Logger.getLogger(getClass().getName()).log(Level.SEVERE, pMessage, pE);

    }// end of ConfigFile::logStackTrace
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // ConfigFile::readBoolean
    //
    // Finds pSection and pKey in the buffer and returns the associated boolean
    // value - stored as "true" or "false".
    //
    // If section and key are not found, returns pDefault.
    //

    public boolean readBoolean(String pSection, String pKey, boolean pDefault)
    {

        //if the ini file was never loaded from memory, return the default
        if (buffer == null) { return pDefault; }

        //get the value associated with pSection and pKey
        String value = getValue(pSection, pKey);

        //if value not found, return the default
        if (value.equals("")){ return pDefault; }

        //return true if value is "true", false if it is "false",
        //default if neither
        if (value.equalsIgnoreCase("true")) { return true; }
        else if (value.equalsIgnoreCase("false")) { return false; }
        
        return pDefault;

    }// end of ConfigFile::readBoolean
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // ConfigFile::readColor
    //
    // Finds pSection and pKey in the buffer and returns the associated double.
    //
    // If section and key are not found, returns pDefault.
    //

    public Color readColor(String pSection, String pKey, Color pDefault)
    {

        //if the ini file was never loaded from memory, return the default
        if (buffer == null) { return pDefault; }

        //get the value associated with pSection and pKey
        String value = getValue(pSection, pKey);

        //if value not found, return the default
        if (value.equals("")) { return pDefault; }

        return MColor.fromString(value, pDefault);

    }//end of ConfigFile::readColor
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // ConfigFile::readDouble
    //
    // Finds pSection and pKey in the buffer and returns the associated double.
    //
    // If section and key are not found, returns pDefault.
    //

    public double readDouble(String pSection, String pKey, double pDefault)
    {

        //if the ini file was never loaded from memory, return the default
        if (buffer == null) { return pDefault; }

        //get the value associated with pSection and pKey
        String value = getValue(pSection, pKey);

        //if value not found, return the default
        if (value.equals("")) { return pDefault; }

        //try to convert the value to a double 
        //if an error occurs, return the default value
        try { return Double.parseDouble(value); }
        catch(NumberFormatException e){ return pDefault; }

    }// end of ConfigFile::readDouble
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // ConfigFile::readInt
    //
    // Finds pSection and pKey in the buffer and returns the associated integer.
    //
    // If section and key are not found, returns pDefault.
    //

    public int readInt(String pSection, String pKey, int pDefault)
    {

        //if the ini file was never loaded from memory, return the default
        if (buffer == null) { return pDefault; }

        //get the value associated with pSection and pKey
        String value = getValue(pSection, pKey);

        //if value not found, return the default
        if (value.equals("")) { return pDefault; }

        //try to convert the value to a double 
        //if an error occurs, return the default value
        try{ return Integer.parseInt(value); }
        catch(NumberFormatException e){ return pDefault; }

    }// end of ConfigFile::readInt
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // ConfigFile::readString
    //
    // Finds pSection and pKey in the buffer and returns the associated string.
    // If section and key are not found, returns pDefault.
    //

    public String readString(String pSection, String pKey, String pDefault)
    {

        //if the ini file was never loaded from memory, return the default
        if (buffer == null) { return pDefault; }

        //get the value associated with pSection and pKey
        String value = getValue(pSection, pKey);

        //if value not found, return the default
        if (value.equals("")) { return pDefault; }

        return value;

    }// end of ConfigFile::readString
    //--------------------------------------------------------------------------

}// end of class ConfigFile
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class AttributesParameters
//
// This class is used to wrap variables for use as parameters in function calls.
//

class AttributesParameters
{

    int startIndex;
    int endIndex;
    int keyIndex;
    
    //--------------------------------------------------------------------------
    // AttributesParameters::AttributesParameters (constructor)
    //

    public AttributesParameters(int pStartIndex, int pEndIndex)
    {

        startIndex = pStartIndex;
        endIndex = pEndIndex;
        keyIndex = -1;

    }// end of AttributesParameters::AttributesParameters (constructor)
    //--------------------------------------------------------------------------

}// end of class AttributesParameters
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------