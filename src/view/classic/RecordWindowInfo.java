/*******************************************************************************
* Title: RecordWindowInfo.java
* Author: Hunter Schoonover
* Date: 01/06/15
*
* Purpose:
*
* This class is used to store information used by the RecordsWindow and 
* EditRecordWindow.
*
*/

//------------------------------------------------------------------------------

package view.classic;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class RecordWindowInfo
//

public class RecordWindowInfo
{
    
    private final String addRecordWindowTitle;
    public String getAddRecordWindowTitle() { return addRecordWindowTitle; }
    
    private final String editRecordWindowTitle;
    public String getEditRecordWindowTitle() { return editRecordWindowTitle; }
    
    private final String recordNameSingular;
    public String getRecordNameSingular() { return recordNameSingular; }
    
    private final String recordNamePlural;
    public String getRecordNamePlural() { return recordNamePlural; }
    
    private final String addCommandMessage;
    public String getAddCommandMessage() { return addCommandMessage; }
    
    private final String deleteCommandMessage;
    public String getDeleteCommandMessage() { return deleteCommandMessage; }
    
    private final String editCommandMessage;
    public String getEditCommandMessage() { return editCommandMessage; }
    
    private final String getCommandMessage;
    public String getGetCommandMessage() { return getCommandMessage; }
    
    //CUSTOMER, BATCH, etc.
    private final String typeSingluarCommandMessage;
    public String getTypeSingluarCommandMessage() { return typeSingluarCommandMessage; }
    
    private final String typePluralCommandMessage;
    public String getTypePluralCommandMessage() { return typePluralCommandMessage; }
    
    //--------------------------------------------------------------------------
    // RecordWindowInfo::RecordWindowInfo (constructor)
    //

    public RecordWindowInfo(String pAddRecordWindowTitle, 
                            String pEditRecordWindowTitle, 
                            String pRecordNameSingular, 
                            String pRecordNamePlural, 
                            String pAddCommandMessage,
                            String pDeleteCommandMessage,
                            String pEditCommandMessage, 
                            String pGetCommandMessage, 
                            String pTypeSingluarCommandMessage,
                            String pTypePluralCommandMessage)
    {
        
        addRecordWindowTitle = pAddRecordWindowTitle;
        editRecordWindowTitle = pEditRecordWindowTitle;
        recordNameSingular = pRecordNameSingular;
        recordNamePlural = pRecordNamePlural;
        addCommandMessage = pAddCommandMessage;
        deleteCommandMessage = pDeleteCommandMessage;
        editCommandMessage = pEditCommandMessage;
        getCommandMessage = pGetCommandMessage;
        typeSingluarCommandMessage = pTypeSingluarCommandMessage;
        typePluralCommandMessage = pTypePluralCommandMessage;

    }//end of RecordWindowInfo::RecordWindowInfo (constructor)
    //--------------------------------------------------------------------------
    
}//end of class RecordWindowInfo
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
