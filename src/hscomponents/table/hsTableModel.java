/*******************************************************************************
* Title: hsTableModel.java
* Author: Hunter Schoonover
* Date: 07/21/15
*
* Purpose:
*
* This class is a custom table model.
*
*/

//------------------------------------------------------------------------------

package hscomponents.table;

//------------------------------------------------------------------------------

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class hsTableModel
//

public class hsTableModel extends AbstractTableModel 
{
    
    List<String> columnNames = new ArrayList<>();
    
    //row by column (outer list is rows, inner list is columns)
    List<List<Object>> tableData = new ArrayList<>();
    
    //--------------------------------------------------------------------------
    // hsTableModel::init
    //
    // Initializes the object.  Must be called immediately after instantiation.
    //
    
    public void init () 
    {
        
    }//end of hsTableModel::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // hsTableModel::getColumnClass
    //
    // Returns the class type of the specified column in the table.
    //
    
    @Override
    public Class<?> getColumnClass(int pCol)
    {
        if (tableData.isEmpty()) { return Object.class; }
        
        return tableData.get(0).get(pCol).getClass();
        
    }//end of hsTableModel::getColumnCount
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // hsTableModel::getColumnCount
    //
    // Return the number of columns.
    //
    
    @Override
    public int getColumnCount()
    {
        
        return columnNames.size();
        
    }//end of hsTableModel::getColumnCount
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // hsTableModel::getColumnName
    //
    // Return the name of the specified column.
    //
    
    @Override
    public String getColumnName(int col)
    {
        
        return columnNames.get(col);
        
    }//end of hsTableModel::getColumnName
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // hsTableModel::getRowCount
    //
    // Return the number of rows.
    //
    
    @Override 
    public int getRowCount()
    {
        
        return tableData.size();
        
    }//end of hsTableModel::getRowCount
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // hsTableModel::getValueAt
    //
    // Return the value of the specified cell.
    //
    
    @Override
    public Object getValueAt(int pRow, int pCol)
    {
        
        return tableData.get(pRow).get(pCol);
        
    }//end of hsTableModel::getValueAt
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // hsTableModel::setValueAt
    //
    // Sets the value of the specified cell to the passed in object
    //
    
    @Override
    public void setValueAt(Object pVal, int pRow, int pCol)
    {
        
        tableData.get(pRow).set(pCol, pVal);
        
        //notify listeners that the cell has been updated
        fireTableCellUpdated(pRow,pCol);
            
    }//end of hsTableModel::setValueAt
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // hsTableModel::addColumn
    //
    // Adds a column to the model, naming it the passed in string.
    //

    public void addColumn(String pName) 
    {
        
        columnNames.add(pName);
        fireTableStructureChanged();

    }//end of hsTableModel::addColumn
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // hsTableModel::addRow
    //
    // Adds a row to the model using the passed in list.
    //

    public void addRow(List<Object> pVals) 
    {
        
        tableData.add(pVals);
        fireTableRowsInserted(tableData.size()-1, tableData.size()-1);

    }//end of hsTableModel::addRow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // hsTableModel::changeValuesOfRow
    //
    // Changes the values of the specified row, using the passed in values.
    //

    public void changeValuesOfRow(int pRow, List<Object> pVals) 
    {
        
        tableData.set(pRow, pVals);
        
        //notify listeners that the row has been updated
        fireTableRowsUpdated(pRow, pRow);

    }//end of hsTableModel::changeValuesOfRow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // hsTableModel::getValuesOfRow
    //
    // Return the values of the specified row.
    //
    
    public List<Object> getValuesOfRow(int pRow)
    {
        
        return tableData.get(pRow);
        
    }//end of hsTableModel::getValuesOfRow
    //--------------------------------------------------------------------------
        
}//end of class hsTableModel
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------