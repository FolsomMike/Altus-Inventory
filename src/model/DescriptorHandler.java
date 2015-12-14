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
*   delete descriptor
*
*/

//------------------------------------------------------------------------------

package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class DescriptorHandler
//

public class DescriptorHandler extends RecordHandler
{
    
    private final List<String> descriptorKeys = new ArrayList<>();
    
    private final String descriptorsTable = "DESCRIPTORS";

    //--------------------------------------------------------------------------
    // DescriptorHandler::DescriptorHandler (constructor)
    //

    public DescriptorHandler(MySQLDatabase pDatabase)
    {

        super(pDatabase);

    }//end of DescriptorHandler::DescriptorHandler (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // DescriptorHandler::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    @Override
    public void init()
    {
        
        super.init();
        
        //set up the descriptor keys
        descriptorKeys.add("name");
        descriptorKeys.add("for_table");
        descriptorKeys.add("duplicates_allowed");
        descriptorKeys.add("removable");

    }// end of DescriptorHandler::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DescriptorHandler::addDescriptor
    //
    // Adds a descriptor using the information in pCommand.
    //
    // Adding a descriptor invovles three steps:
    //      1. adding the descriptor to the descriptors table
    //      2. creating the table to contain the values of the descriptor
    //      3. add a column to the proper table for the descriptor
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

        ///add the column for the descriptor to the proper table
        String column = "`" + key + "` INT(255) NULL";
        getDatabase().addColumn(pCommand.get("for_table"), column);
        
        getDatabase().closeDatabaseConnection();

    }//end of DescriptorHandler::addDescriptor
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DescriptorHandler::deleteDescriptor
    //
    // Deletes a descriptor using the information in pCommand.
    //
    // Deleting a descriptor invovles three steps:
    //      1. dropping the column from the table that the descriptor was
    //          created for
    //      2. dropping the descriptor table from the database
    //      3. deleting the descriptor from the descriptors table
    //

    public void deleteDescriptor(Map<String, String> pCommand)
    {
        
        getDatabase().connectToDatabase();
        
        //get the skoonie key of the descriptor from pCommand
        String key = pCommand.get("skoonie_key");
        
        //get the descriptor from the database so that we can use some 
        //information about it to get rid of it
        Record descriptor = getDatabase().getRecord(key, descriptorsTable);
        
        ///drop the descriptor from the table
        getDatabase().dropColumn(descriptor.getValue("for_table"), key);
        
        //drop the descriptor table from the database
        getDatabase().dropTable(key);
        
        //delete the descriptor record from the descriptors table
        deleteRecord(pCommand, descriptorsTable);
        
        getDatabase().closeDatabaseConnection();

    }//end of DescriptorHandler::deleteDescriptor
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DescriptorHandler::getDescriptors
    //
    // Gets the descriptors of pTable.
    //
    // Getting the descriptors invovles three steps:
    //      1. getting all the column names of pTable, excluding skoonie_key
    //      2. getting the information about the descriptor from the descriptors
    //          table
    //

    public void getDescriptors(String pTable)
    {
        
        //WIP HSS// -- do stuff

    }//end of DescriptorHandler::deleteDescriptor
    //--------------------------------------------------------------------------
    
}//end of class DescriptorHandler
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------