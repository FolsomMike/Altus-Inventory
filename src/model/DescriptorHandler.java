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

import shared.Descriptor;
import model.database.MySQLDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.database.DatabaseEntry;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class DescriptorHandler
//

public class DescriptorHandler extends RecordHandler
{
    
    //this is the table that this instance takes care of descriptors for
    private final String forTable;
    private final String descriptorsTable = "DESCRIPTORS";
    
    private final List<String> descriptorKeys = new ArrayList<>();

    //--------------------------------------------------------------------------
    // DescriptorHandler::DescriptorHandler (constructor)
    //

    public DescriptorHandler(MySQLDatabase pDatabase, String pForTable)
    {

        super(pDatabase);
        
        forTable = pForTable;

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
    // //WIP HSS// -- add function description
    //
    // Adding a descriptor invovles three steps:
    //      1. adding the descriptor to the descriptors table
    //      2. creating the table to contain the values of the descriptor
    //      3. add a column to the proper table for the descriptor
    //

    public void addDescriptor(Map<String, String> pCommand)
    {
        
        //WIP HSS// -- need to do some stuff!
        
        /*getDatabase().connectToDatabase();
        
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
        
        getDatabase().closeDatabaseConnection();*/

    }//end of DescriptorHandler::addDescriptor
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DescriptorHandler::deleteDescriptor
    //
    // //WIP HSS// -- add function description
    //
    // Deleting a descriptor invovles three steps:
    //      1. dropping the column from the table that the descriptor was
    //          created for
    //      2. dropping the descriptor table from the database
    //      3. deleting the descriptor from the descriptors table
    //

    public void deleteDescriptor(Map<String, String> pCommand)
    {
        
        //WIP HSS// -- need to do stuff!
        
        /*getDatabase().connectToDatabase();
        
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
        
        getDatabase().closeDatabaseConnection();*/

    }//end of DescriptorHandler::deleteDescriptor
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DescriptorHandler::getDescriptors
    //
    // Gets and returns all of the descriptors from the descriptors table.
    //
    // For convenience purposes, each descriptor is mapped to its skoonie key.
    //
    // NOTE: Assumes database connection is opened and closed elsewhere.
    //

    public Map<String, Descriptor> getDescriptors()
    {
        
        Map<String, Descriptor> descriptors = new HashMap<>();
        
        for (DatabaseEntry e : getDatabase().getEntries(descriptorsTable)) {
            
            String key = e.getValue("skoonie_key");
            
            Descriptor d = new Descriptor();
            d.setSkoonieKey(key);
            d.setName(e.getValue("name"));
            d.setValuesTable(e.getValue("values_table"));
            
            descriptors.put(key, d);
        }
        
        return descriptors;

    }//end of DescriptorHandler::getDescriptors
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DescriptorHandler::writeDescriptors
    //
    // //WIP HSS// -- add function description
    //
    // Adding a descriptor invovles three steps:
    //      1. adding the descriptor to the descriptors table
    //      2. creating the table to contain the values of the descriptor
    //      3. add a column to the proper table for the descriptor
    //

    public void writeDescriptors(List<Descriptor> pDescriptors)
    {
        
        //WIP HSS// -- DO THIS

    }//end of DescriptorHandler::addDescriptor
    //--------------------------------------------------------------------------
    
}//end of class DescriptorHandler
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------