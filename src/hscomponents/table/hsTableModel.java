/*******************************************************************************
* Title: hsTableModel.java
* Author: Hunter Schoonover
* Date: 07/21/15
*
* Purpose:
*
* This class 
*
*/

//------------------------------------------------------------------------------

package hscomponents.table;

//------------------------------------------------------------------------------

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

//------------------------------------------------------------------------------
// class hsTableModel
//

public class hsTableModel extends AbstractTableModel
{
    
    //--------------------------------------------------------------------------
    // hsTable::hsTable (constructor)
    //

    public hsTableModel()
    {

    }//end of hsTable::hsTable(constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // hsTable::init
    //
    // Initializes the object.  Must be called immediately after instantiation.
    //

    public void init()
    {



    }// end of hsTable::init
    //--------------------------------------------------------------------------
    
    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount() {
      return 1;
    }

    @Override
    public Object getValueAt(int row, int column) {
      return null;
    }

    //--------------------------------------------------------------------------
    // hsTable::HSTableCellRenderer
    //
    // This class is a custom table cell renderer, used to customize the color of
    // rows.
    //

    class HSTableCellRenderer extends DefaultTableCellRenderer 
                                                implements TableCellRenderer 
    {

        @Override
        public Component getTableCellRendererComponent 
            (JTable pTable, Object pValue, boolean pIsSelected, boolean pHasFocus, 
                int pRow, int pColumn) 
        {

            setBackground(null);

            super.getTableCellRendererComponent
                            (pTable, pValue, pIsSelected, pHasFocus, pRow, pColumn);

            //set the text of the cell
            setText(String.valueOf(pValue));

            //give the cell no border
            setBorder(noFocusBorder);

            //alternate the color of the rows
            //row number is odd
            if (pRow % 2 != 0) { setBackground(Color.decode("#E6E6E6")); }
            //row number is even
            else if (pRow % 2 == 0) { setBackground(Color.WHITE); }

            //if the cell is part of the selected row, highlight the cell
            if (pTable.hasFocus() && pRow == pTable.getSelectedRow()) {  
                setBackground(Color.BLUE);
            }

            //force the table to r
            if (!pTable.hasFocus()) {
               // mod.getValueAt(TOP, TOP)

            }

            return this;

        }

        //This method updates the Row of table
        public void updateRow(int index,String[] values)
        {
            for (int i = 0 ; i < values.length ; i++)
            {
                //setValueAt(values[i],index,i);
            }
        }

    }// hsTable::HSTableCellRenderer
    //--------------------------------------------------------------------------

}//end of class hsTable
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
