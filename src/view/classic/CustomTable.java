/*******************************************************************************
* Title: CustomTable.java
* Author: Hunter Schoonover
* Date: 11/20/15
*
* Purpose:
*
* This class is a customized JTable with added functionality and options.
*
*/

//------------------------------------------------------------------------------

package view.classic;

//------------------------------------------------------------------------------

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class CustomTable
//

public class CustomTable extends JTable
{
    
    Color oddRowColor   = Color.decode("#E6E6E6");
    Color evenRowColor  = Color.WHITE;;
    
    //--------------------------------------------------------------------------
    // CustomTable::CustomTable (constructor)
    //

    public CustomTable(Object[][] pRowData, Object[] pColumnNames, 
                            final boolean pEditable)
    {
        
        super(new DefaultTableModel(pRowData, pColumnNames) {
            @Override public boolean isCellEditable(int pR, int pC) {
                return pEditable;
            }
        });

    }//end of CustomTable::CustomTable (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomTable::CustomTable (constructor)
    //

    public CustomTable(TableModel pModel)
    {
        
        super(pModel);

    }//end of CustomTable::CustomTable (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomTable::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    public void init()
    {
        
        setRowHeight(25);
        setFont(new Font("Arial", Font.PLAIN, 12));
        
        //header specific settings
        getTableHeader().setBackground(Color.decode("#C2E0FF"));
        getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        getTableHeader().setReorderingAllowed(false);
        
        //selection specific settings
        setSelectionBackground(Color.decode("#000099"));
        setSelectionForeground(Color.WHITE);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }//end of CustomTable::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomTable::prepareRenderer
    //
    // Changes the background color and text color of the row depending on
    // whether it's even or odd or if it's selected.
    //
    
    @Override
    public Component prepareRenderer(TableCellRenderer pRen, int pRow, int pCol)
    {
        
        JComponent comp = (JComponent)super.prepareRenderer(pRen, pRow, pCol); 
        
        //give the cell no border
        comp.setBorder(null);
        
        comp.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        
        //if component is in the selected row, return the
        //component without making any more changes
        if (pRow == getSelectedRow()) { return comp; }
        
        //if it's not selected then we set
        //the background color
        setBackgroundColorOfRow(comp, pRow);

        return comp;
            
    }//end of CustomTable::prepareRenderer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // CustomTable::setBackgroundColorOfRow
    //
    // Determines and sets the background color of the passed in row/JComponent.
    //

    private void setBackgroundColorOfRow(JComponent comp, int pRow)
    {

        Color c;
        if (pRow%2 == 0) { c = evenRowColor; }
        else { c = oddRowColor; }

        comp.setBackground(c);

    }//end of CustomTable::setBackgroundColorOfRow
    //--------------------------------------------------------------------------
    
}//end of class CustomTable
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
