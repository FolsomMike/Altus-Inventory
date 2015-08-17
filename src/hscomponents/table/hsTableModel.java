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
    
    List<Object> columnNames = new ArrayList<>();
    
    //row by column (outer list is rows, inner list is columns)
    List<List<Object>> tableData = new ArrayList<>();
    
    boolean editableDefault = false;
    //row by column (outer is rows, inner is columns)
    //used to contain booleans determining whether or
    //not a cell is editable
    List<List<Boolean>> editableCell = new ArrayList<>();
    
    //used to store booleans representing whether or
    //not each row should be editable
    List<Boolean> editableRow = new ArrayList<>();
    
    //used to store booleans representing whether or
    //not each column should be editable
    List<Boolean> editableColumn = new ArrayList<>();
    
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
    public String getColumnName(int pCol)
    {
        
        //WIP HSS// -- you need to fix this
        return columnNames.get(pCol).toString();
        
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
    // hsTableModel::isCellEditable
    //
    // Return true if the cell at the specified row and column is editable;
    // false if not.
    //
    
    @Override
    public boolean isCellEditable(int pRow, int pCol) {

        return editableCell.get(pRow).get(pCol);
        
    }//end of hsTableModel::isCellEditable
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
    // Adds a column to the model, with the passed in object as the header.
    //

    public void addColumn(Object pName) 
    {
        
        columnNames.add(pName);
        addColumnToEditables();
        fireTableStructureChanged();

    }//end of hsTableModel::addColumn
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // hsTableModel::addColumn
    //
    // Adds a column to the model at the specified location, with the passed in 
    // object as the header.
    //

    public void addColumn(int pPos, Object pName) 
    {
        
        columnNames.add(pPos, pName);
        addColumnToEditables();
        fireTableStructureChanged();

    }//end of hsTableModel::addColumn
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // hsTableModel::addColumnToEditables
    //
    // Adds a new column to the editable lists.
    //

    private void addColumnToEditables() 
    {
        
        editableColumn.add(editableDefault);
        
        for (List<Boolean> col : editableCell) { col.add(editableDefault); }

    }//end of hsTableModel::addColumnToEditables
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // hsTableModel::addRow
    //
    // Adds a row to the model using the passed in list.
    //

    public void addRow(List<Object> pVals) 
    {
        
        tableData.add(pVals);
        
        addRowToEditables();
        
        fireTableRowsInserted(tableData.size()-1, tableData.size()-1);

    }//end of hsTableModel::addRow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // hsTableModel::addRowToEditables
    //
    // Adds a new row to the editable lists.
    //

    private void addRowToEditables() 
    {
        
        editableRow.add(editableDefault);
        
        List<Boolean> cols = new ArrayList();
        for (Boolean b : editableColumn) { cols.add(b); }
        editableCell.add(cols);

    }//end of hsTableModel::addRowToEditables
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
    
    //--------------------------------------------------------------------------
    // hsTableModel::setCellEditable
    //
    // Sets the cell at the specified row and column to editable or uneditable.
    //
    
    public void setCellEditable(int pRow, int pCol, boolean pBool) {

        editableCell.get(pRow).set(pCol, pBool);
        
    }//end of hsTableModel::setCellEditable
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // hsTableModel::setColumnEditable
    //
    // Sets all of the cells in the specified column to editable or uneditable.
    //
    
    public void setColumnEditable(int pCol, boolean pBool) {
        
        editableColumn.set(pCol, pBool);

        for (List<Boolean> l : editableCell) { l.set(pCol, pBool); }
        
    }//end of hsTableModel::setColumnEditable
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // hsTableModel::setRowEditable
    //
    // Sets all of the cells in the specified row to editable or uneditable.
    //
    
    public void setRowEditable(int pRow, boolean pBool) {
        
        editableRow.set(pRow, pBool);
        
        for (int i=0; i<editableCell.get(pRow).size(); i++) {
            editableCell.get(pRow).set(i, pBool);
        }
        
    }//end of hsTableModel::setRowEditable
    //--------------------------------------------------------------------------
        
}//end of class hsTableModel
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------