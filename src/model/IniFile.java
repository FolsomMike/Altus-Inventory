/******************************************************************************
* Title: IniFile.java
* Author: Mike Schoonover
* Date: 11/17/03
*
* Purpose:
*
* This class reads and writes to initialization files.  It uses the Microsoft
* Windows format for ini files, using sections, keywords, and values.
*
*
* Open Source Policy:
*
* This source code is Public Domain and free to any interested party.  Any
* person, company, or organization may do with it as they please.
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

//-----------------------------------------------------------------------------
// class Parameters
//
// This class is used to wrap variables for use as parameters in function calls.
//

class Parameters{

    int keyIndex;
    int sectionIndex;

//-----------------------------------------------------------------------------
// Parameters::Parameters (constructor)
//

public Parameters()
{

    keyIndex = 0;

}//end of Parameters::Parameters
//-----------------------------------------------------------------------------

}//end of class Parameters
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// class IniFile
//
// This class reads and/or writes to ini files in the style of Microsoft windows
// ini files.
//
// The file will be read until the end is reached or until a line with the tag
// "[end of header]" is reached.  This allows header of data files to be read
// by this class without reading the data which follows.
// NOTE: Do not call the save() function for data files read in this manner or
//    the file will be overwritten with just the header info and the data wil
//    be lost.
//
// NOTE: The save() function in this class should be the only way to cause
//       the file to be saved.  Placing save code in the finalize() function
//       is not very reliable because it won't be called until the Java Virtual
//       Machine discards the object from memory - it is not done when the
//       object seems to be discarded in code.
//

public class IniFile extends Object{

    String fileFormat;

    private ArrayList<String> buffer;
    public String filename;
    private boolean modified;

    DecimalFormat[] DecimalFormats;

//-----------------------------------------------------------------------------
// IniFile::IniFile (constructor)
//

public IniFile(String pFilename, String pFileFormat)
{

    fileFormat = pFileFormat;
    filename = pFilename;

}//end of IniFile::IniFile (constructor)
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// IniFile::init
//
// Initializes the object.  MUST be called by sub classes after instantiation.
//

public void init() throws IOException
{

    //create a vector to hold the lines of text read from the file
    buffer = new ArrayList<>(1000);

    modified = false; //no data has yet been modified or added

    //create various decimal formats
    DecimalFormats = new DecimalFormat[11];
    DecimalFormats[0] = new  DecimalFormat("#");
    DecimalFormats[1] = new  DecimalFormat("#.#");
    DecimalFormats[2] = new  DecimalFormat("#.##");
    DecimalFormats[3] = new  DecimalFormat("#.###");
    DecimalFormats[4] = new  DecimalFormat("#.####");
    DecimalFormats[5] = new  DecimalFormat("#.#####");
    DecimalFormats[6] = new  DecimalFormat("#.######");
    DecimalFormats[7] = new  DecimalFormat("#.#######");
    DecimalFormats[8] = new  DecimalFormat("#.########");
    DecimalFormats[9] = new  DecimalFormat("#.#########");
    DecimalFormats[10] = new DecimalFormat("#.##########");

    //create a buffered reader stream to the language file

    FileInputStream fileInputStream = null;
    InputStreamReader inputStreamReader = null;
    BufferedReader in = null;

    try{

        fileInputStream = new FileInputStream(filename);

        inputStreamReader = new InputStreamReader(fileInputStream, fileFormat);

        in = new BufferedReader(inputStreamReader);

        String line;

        //read until end of file reached or "[end of header]" tag reached

        while ((line = in.readLine()) != null){
            buffer.add(line);
            if (line.equals("[Header End]")) {break;}
        }
    }
    catch (FileNotFoundException e){
        //if an existing file was not found, add some header info to the buffer
        //so it will be saved when the file is created
        buffer.add("");
        buffer.add(";Do not erase blank line above -"
                   + " has hidden code needed by UTF-16 files.");
        buffer.add(";To make a new file, copy an existing ini file and change"
                   + " only data below this line.");
        buffer.add("");

    }
    catch(IOException e){
        logSevere(e.getMessage() + " - Error: 346");
        throw new IOException();
    }
    finally{
        if (in != null) {in.close();}
        if (inputStreamReader != null) {inputStreamReader.close();}
        if (fileInputStream != null) {fileInputStream.close();}
    }

}//end of IniFile::init
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// IniFile::save
//
// Writes the buffer contents to the file.
//

public void save()
{

    //create a buffered writer stream

    FileOutputStream fileOutputStream = null;
    OutputStreamWriter outputStreamWriter = null;
    BufferedWriter out = null;

    try{

        fileOutputStream = new FileOutputStream(filename);
        outputStreamWriter =
                        new OutputStreamWriter(fileOutputStream, fileFormat);
        out = new BufferedWriter(outputStreamWriter);

        ListIterator i;

        //write each line in the buffer to the file

        for (i = buffer.listIterator(); i.hasNext(); ){
            out.write((String)i.next());
            out.newLine();
            }

        //Note! You MUST flush to make sure everything is written.

        out.flush();

    }
    catch(IOException e){
        logSevere(e.getMessage() + " - Error: 394");
    }
    finally{
        try{if (out != null) {out.close();}}
        catch(IOException e){}
        try{if (outputStreamWriter != null) {outputStreamWriter.close();}}
        catch(IOException e){}
        try{if (fileOutputStream != null) {fileOutputStream.close();}}
        catch(IOException e){}
    }

}//end of IniFile::save
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// IniFile::getValue
//
// Returns a string containing the value for pSection and pKey.
//
// Parameter pParams.keyIndex will be set to the index position in buffer of
// the line containing the pKey string.
//
// Parameter pParams.sectionIndex will be set to the index position in buffer of
// the line containing the pSection string.
//
// If no matching Section/Key could be found or if the value is blank, the
// string returns empty and pParams.keyIndex and pParams.sectionIndex will be
// set to -1.  If the section was found but not the key, pParams.sectionIndex
// will be set to the index of the line containing the next section name or to
// a position beyond the end of the file if the section is the last one in the
// file. The pParams.sectionIndex value can be used in Vector.add() to insert a
// new line into the section or to create a new section.
//

String getValue(String pSection, String pKey, Parameters pParams)
{

    //default to -1 in case function returns without finding Section/Key
    pParams.keyIndex = -1; pParams.sectionIndex = -1;

    //create the section by concatenating brackets around the parameter
    String section = "[" + pSection + "]";

    ListIterator i;

    //search the buffer for the section name

    //NOTE: searching each line using startsWith and not worrying about case
    // sped the search for section up tremendously.  This does mean that the section
    // name is case-sensitive and there can be no whitespace at the beginning of
    // a section name.  A 4000 line file was taking more than 10 seconds to process
    // and now takes a second or less.

    for (i = buffer.listIterator(); i.hasNext(); ) {

        //stop if line found containing the section name
        if ( (((String)i.next()).startsWith(section))) {

            //set the index of the line containing the section name
            pParams.sectionIndex = i.previousIndex();

        break;
        }
    }

    //if match not found, section not found so return empty string - both
    //pParams.keyIndex and pParams.sectionIndex will be -1
    if (pParams.sectionIndex == -1) {return("");}

    //search the section for the key - if another section is found before finding
    //the key, then return empty string and the index of the section entry
    //add '=' to the search phrase to make sure a partial match will fail

    String key = pKey.toLowerCase() + "=";
    StringBuilder line = new StringBuilder(200);

    int matchIndex = -1;

    while(i.hasNext()) {

        line.setLength(0);

        line.append(((String)i.next()).toLowerCase());

        //if a new section name is found before the key is found, the key is not
        //in the section being searched so return empty string
        try {
            if (line.charAt(0) == '['){
                //return the index of the line containing the next section name
                pParams.sectionIndex = i.previousIndex();
                return("");
            }
        }
        catch(IndexOutOfBoundsException e){} //ignore this error

        //stop when line found containing the key name - index of key must be 0 to
        //insure that a partial match is not made
        if ((matchIndex = line.indexOf(key)) == 0) {break;}

    }

    //if match not found, key not found so return empty string
    if (matchIndex != 0){
        //return index at the end of the buffer
        pParams.sectionIndex = buffer.size();
        return("");
    }

    int indexOfEqual;

    //look for '=' symbol, if not found then return empty string
    if ( (indexOfEqual = line.indexOf("=")) == -1) {return("");}

    //return the part of the line after the '=' sign - on error return empty string
    try{
        //set return parameters to reflect the line found to contain the key
        pParams.keyIndex = i.previousIndex();

        return(((String)buffer.get(pParams.keyIndex)).substring(indexOfEqual + 1));

    }
    catch(StringIndexOutOfBoundsException e){
        pParams.keyIndex = -1;
        return("");
    }

}//end of IniFile::getValue
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// IniFile::readInt
//
// Finds pSection and pKey in the buffer and returns the associated integer.
// If section and key are not found, returns pDefault.
//

public int readInt(String pSection, String pKey, int pDefault)
{

    //if the ini file was never loaded from memory, return the default
    if (buffer == null) {return pDefault;}

    Parameters params = new Parameters();

    //get the value associated with pSection and PKey
    String valueText = getValue(pSection, pKey, params);

    //if Section/Key not found, return the default
    if (valueText.equals("")) {return(pDefault);}

    int value;

    //try to convert the remainder of the string after the '=' symbol to an
    //integer if an error occurs, return the default value

    try{
        value = Integer.parseInt(valueText);
    }
    catch(NumberFormatException e){return(pDefault);}

    return(value);

}//end of IniFile::readInt
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// IniFile::readDouble
//
// Finds pSection and pKey in the buffer and returns the associated double.
// If section and key are not found, returns pDefault.
//

public double readDouble(String pSection, String pKey, double pDefault)
{

    //if the ini file was never loaded from memory, return the default
    if (buffer == null) {return pDefault;}

    Parameters params = new Parameters();

    //get the value associated with pSection and PKey
    String valueText = getValue(pSection, pKey, params);

    //if Section/Key not found, return the default
    if (valueText.equals("")) {return(pDefault);}

    double value;

    //try to convert the remainder of the string after the '=' symbol to a double
    //if an error occurs, return the default value

    try{
        value = Double.parseDouble(valueText);
    }
    catch(NumberFormatException e){return(pDefault);}

    return(value);

}//end of IniFile::readDouble
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// IniFile::readColor
//
// Finds pSection and pKey in the buffer and returns the associated double.
// If section and key are not found, returns pDefault.
//

public Color readColor(String pSection, String pKey, Color pDefault)
{

    //if the ini file was never loaded from memory, return the default
    if (buffer == null) {return pDefault;}

    Parameters params = new Parameters();

    //get the value associated with pSection and PKey
    String valueText = getValue(pSection, pKey, params);

    //if Section/Key not found, return the default
    if (valueText.equals("")) {return(pDefault);}

    return(MColor.fromString(valueText, pDefault));

}//end of IniFile::readColor
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// IniFile::readString
//
// Finds pSection and pKey in the buffer and returns the associated string.
// If section and key are not found, returns pDefault.
//

public String readString(String pSection, String pKey, String pDefault)
{

    //if the ini file was never loaded from memory, return the default
    if (buffer == null) {return pDefault;}

    Parameters params = new Parameters();

    //get the value associated with pSection and PKey
    String valueText = getValue(pSection, pKey, params);

    //if Section/Key not found, return the default
    if (valueText.equals("")) {
        return(pDefault);
    }
    else {
        return(valueText);
    }

}//end of IniFile::readString
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// IniFile::readBoolean
//
// Finds pSection and pKey in the buffer and returns the associated boolean
// value - stored as "true" or "false".
// If section and key are not found, returns pDefault.
//

public boolean readBoolean(String pSection, String pKey, boolean pDefault)
{

    //if the ini file was never loaded from memory, return the default
    if (buffer == null) {return pDefault;}

    Parameters params = new Parameters();

    //get the value associated with pSection and PKey
    String valueText = getValue(pSection, pKey, params);

    //if Section/Key not found, return the default
    if (valueText.equals("")){return(pDefault);}

    //return true if value is "true", false if it is "false", default if neither
    if (valueText.equalsIgnoreCase("true")) {return true;}
    if (valueText.equalsIgnoreCase("false")) {return false;}
    return(pDefault);

}//end of IniFile::readString
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// IniFile::writeValue
//
// Finds pSection and pKey in the buffer and sets the entire line containing
// the key to pNewEntry.
//
// If the key is not found, pNewEntry is added to the section.
//
// If section and key are not found, the section is created and pNewEntry is
// added to the section.
//
// This is a helper function for writeInteger, writeDouble, writeString, etc.
//
// The modified flag will be set true so that the data buffer will be saved to
// disk when this object is discarded.
//

private void writeValue(String pSection, String pKey, String pNewEntry)
{

    modified = true; //force data to be saved when this object is discarded

    Parameters params = new Parameters();

    //use the getValue function to search for the section and key and retrieve
    //the index positions in the buffer of the section and key via params
    getValue(pSection, pKey, params);

    if (params.keyIndex != -1){

        //if the section/key was found, replace the line with the new key=value line
        buffer.set(params.keyIndex, pNewEntry);

    }
    else
    if (params.sectionIndex != -1){

        //if section found but not key, add new key=value line to end of section
        buffer.add(params.sectionIndex, pNewEntry);

    }
    else{

        //if section not found, add section and newkey=value line to end of file
        buffer.add("[" + pSection + "]");
        buffer.add(pNewEntry);

    }

}//end of IniFile::writeValue
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// IniFile::writeInt
//
// Finds pSection and pKey in the buffer and sets the associated value to
// pValue.
//
// If section and key are not found, the section and key are created and the
// value is set to pValue.
//
// The modified flag will be set true so that the data buffer will be saved to
// disk when this object is discarded.
//

public void writeInt(String pSection, String pKey, int pValue)
{

    //create the new key=value string to store in the buffer
    String newEntry = pKey + "=" + Integer.toString(pValue);

    //update the value in the buffer
    writeValue(pSection, pKey, newEntry);

}//end of IniFile::writeInt
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// IniFile::writeDouble
//
// Finds pSection and pKey in the buffer and sets the associated value to
// pValue.
//
// If section and key are not found, the section and key are created and the
// value is set to pValue.
//
// The modified flag will be set true so that the data buffer will be saved to
// disk when this object is discarded.
//

public void writeDouble(String pSection, String pKey, double pValue)
{

    //create the new key=value string to store in the buffer
    String newEntry = pKey + "=" + Double.toString(pValue);

    //update the value in the buffer
    writeValue(pSection, pKey, newEntry);

}//end of IniFile::writeDouble
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// IniFile::writeColor
//
// Finds pSection and pKey in the buffer and sets the associated value to
// pValue.
//
// If section and key are not found, the section and key are created and the
// value is set to pValue.
//
// The modified flag will be set true so that the data buffer will be saved to
// disk when this object is discarded.
//

public void writeColor(String pSection, String pKey, Color pColor)
{

    //create the new key=value string to store in the buffer
    String newEntry = pKey + "=" + MColor.toString(pColor);

    //update the value in the buffer
    writeValue(pSection, pKey, newEntry);

}//end of IniFile::writeColor
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// getDecimalFormat
//
// Returns a decimal format object with the number of digits after the decimal
// point as specified by pPrecision.
//

DecimalFormat getDecimalFormat(int pPrecision)
{

    //if illegal value, use precision of 2
    if ((pPrecision < 0) || (pPrecision >= DecimalFormats.length)){
        pPrecision = 2;
        }

    return DecimalFormats[pPrecision];

}//end of IniFile::getDecimalFormat
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// IniFile::writeDoubleFormatted
//
// Finds pSection and pKey in the buffer and sets the associated value to
// pValue.
//
// If section and key are not found, the section and key are created and the
// value is set to pValue.
//
// Unlike writeDouble, this method formats the double so that it has the
// specified precision.  This makes the saved string easier to read since it
// has no more digits after the decimal point than are necessary.
//
// The modified flag will be set true so that the data buffer will be saved to
// disk when this object is discarded.
//

public void writeDoubleFormatted(String pSection, String pKey, double pValue,
                                                                 int pPrecision)
{

    //create the new key=value string to store in the buffer
    String newEntry = pKey + "=" + getDecimalFormat(pPrecision).format(pValue);

    //update the value in the buffer
    writeValue(pSection, pKey, newEntry);

}//end of IniFile::writeDoubleFormatted
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// IniFile::writeBoolean
//
// Finds pSection and pKey in the buffer and sets the associated value to
// pValue.
//
// If section and key are not found, the section and key are created and the
// value is set to pValue.
//
// The modified flag will be set true so that the data buffer will be saved to
// disk when this object is discarded.
//

public void writeBoolean(String pSection, String pKey, boolean pValue)
{

    //create the new key=value string to store in the buffer
    String newEntry = pKey + "=" + Boolean.toString(pValue);

    //update the value in the buffer
    writeValue(pSection, pKey, newEntry);

}//end of IniFile::writeBoolean
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// IniFile::writeString
//
// Finds pSection and pKey in the buffer and sets the associated value to
// pValue.
//
// If section and key are not found, the section and key are created and the
// value is set to pValue.
//
// The modified flag will be set true so that the data buffer will be saved to
// disk when this object is discarded.
//

public void writeString(String pSection, String pKey, String pValue)
{

    //create the new key=value string to store in the buffer
    String newEntry = pKey + "=" + pValue;

    //update the value in the buffer
    writeValue(pSection, pKey, newEntry);

}//end of IniFile::writeString
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// IniFile::detectUTF16LEFormat
//
// Determines if the specified file is a UTF-16LE file created by this program,
// in which case it will have a readily identifiable header string which can
// be read in UTF-16LE format.
//
// Returns true if file was created in UTF-16LE format, false if otherwise.
//
// Throws IOException if the file is not found or on I/O error.
//

static public boolean detectUTF16LEFormat(String pFilename) throws IOException
{

    FileInputStream fileInputStream = null;
    InputStreamReader inputStreamReader = null;
    BufferedReader in = null;

    try{

        fileInputStream = new FileInputStream(pFilename);

        inputStreamReader = new InputStreamReader(fileInputStream, "UTF-16LE");

        in = new BufferedReader(inputStreamReader);

        int i = 0;

        String line;

        //read until header line found, max lines read, or end of file reached

        while ((line = in.readLine()) != null){

            //return true if the header line used in UTF-16LE files is found
            if (line.startsWith(";Do not erase")) {return(true);}
            //return false if header line not found near beginning
            if (i++ >= 5) {return(false);}

        }//while...

        //return false if header line not found before EOF reached
        return(false);

    }//try
    catch (FileNotFoundException e){
        return(false);
    }//catch
    catch(IOException e){
        throw new IOException(e.getMessage() + " - Error: 956");
    }//catch
    finally{
        try{if (in != null) {in.close();}}
            catch(IOException e){}
        try{if (inputStreamReader != null) {inputStreamReader.close();}}
            catch(IOException e){}
        try{if (fileInputStream != null) {fileInputStream.close();}}
            catch(IOException e){}
    }//finally

}//end of IniFile::detectUTF16LEFormat
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// IniFile::logSevere
//
// Logs pMessage with level SEVERE using the Java logger.
//

void logSevere(String pMessage)
{

    Logger.getLogger(getClass().getName()).log(Level.SEVERE, pMessage);

}//end of IniFile::logSevere
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// IniFile::logStackTrace
//
// Logs stack trace info for exception pE with pMessage at level SEVERE using
// the Java logger.
//

void logStackTrace(String pMessage, Exception pE)
{

    Logger.getLogger(getClass().getName()).log(Level.SEVERE, pMessage, pE);

}//end of IniFile::logStackTrace
//-----------------------------------------------------------------------------


//debug System.out.println(String.valueOf(value)); //debug mks

}//end of class IniFile
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
