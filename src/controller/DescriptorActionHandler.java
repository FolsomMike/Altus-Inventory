/*******************************************************************************
* Title: DescriptorActionHandler.java
* Author: Hunter Schoonover
* Date: 12/08/15
*
* Purpose:
*
* This class handles commands pertaining to descriptors. 
*
* Currently handles actions:
*   add descriptor
*
*/

//------------------------------------------------------------------------------

package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.MySQLDatabase;
import model.Record;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class DescriptorActionHandler
//

public class DescriptorActionHandler extends RecordActionHandler
{
    
    private final List<String> descriptorKeys = new ArrayList<>();
    
    private final String descriptorsTable = "DESCRIPTORS";

    //--------------------------------------------------------------------------
    // DescriptorActionHandler::DescriptorActionHandler (constructor)
    //

    public DescriptorActionHandler(MySQLDatabase pDatabase)
    {

        super(pDatabase);

    }//end of DescriptorActionHandler::DescriptorActionHandler (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // DescriptorActionHandler::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    @Override
    public void init()
    {
        
        super.init();
        
        //set up the descriptor keys
        descriptorKeys.add("name");
        descriptorKeys.add("duplicates_allowed");
        descriptorKeys.add("removable");

    }// end of DescriptorActionHandler::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DescriptorActionHandler::addDescriptor
    //
    // Adds a descriptor using the information in pCommand.
    //

    public void addDescriptor(Map<String, String> pCommand)
    {
        
        getDatabase().connectToDatabase();
        
        //insert the descriptor into the database and store the skoonie key
        Record descriptor = new Record();
        getValues(descriptor, pCommand, descriptorKeys);
        String key = Integer.toString(
                            getDatabase().insertRecord(descriptor, 
                                                        descriptorsTable));
       
        //create the table for the descriptor
        String[] columns = {"`skoonie_key` INT(255) NOT NULL AUTO_INCREMENT",
                            "`value` VARCHAR(2000) NULL",
                            "PRIMARY KEY(`skoonie_key`)"};
        
        getDatabase().createTable(key, columns);

        ///add the descriptor to the proper table
        String column = "`" + key + "` INT(255) NULL";
        getDatabase().addColumn(pCommand.get("to table"), column);
        
        getDatabase().closeDatabaseConnection();

    }//end of DescriptorActionHandler::addDescriptor
    //--------------------------------------------------------------------------
    
}//end of class DescriptorActionHandler
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------