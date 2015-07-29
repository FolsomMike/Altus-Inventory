/*******************************************************************************
* Title: hsTable.java
* Author: Hunter Schoonover
* Date: 07/21/15
*
* Purpose:
*
* This class is a customized JTable with added functionality and options.
*
*/

//------------------------------------------------------------------------------

package hscomponents.table;

//------------------------------------------------------------------------------

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class hsTable
//

public class hsTable extends JTable
{

    hsTableModel model = new hsTableModel();
    
    //this is used to determine which color
    //to use out of the alternate background
    //color list
    int rowBackgroundNum = 0;
    
    Color rowBackgroundColor = Color.WHITE;
    boolean alternateRowBackgrounds = true;
    List<Color> alternateRowBackgroundColors = new ArrayList<>();
    
    //this is used to determine which color
    //to use out of the alternate foregrond
    //color list
    int rowForegroundNum = 0;
    
    Color rowForegroundColor = Color.BLACK;
    boolean alternateRowForegrounds = false;
    List<Color> alternateRowForegroundColors = new ArrayList<>();
    
    //--------------------------------------------------------------------------
    // hsTable::hsTable (constructor)
    //

    public hsTable()
    {
        
        super();

    }//end of hsTable::hsTable(constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // hsTable::init
    //
    // Initializes the object.  Must be called immediately after instantiation.
    //

    public void init()
    {
        
        setModel(model);
        
        //populate the alternate row background colors list with default
        //alternate colors
        alternateRowBackgroundColors.add(Color.decode("#E6E6E6"));
        alternateRowBackgroundColors.add(Color.WHITE);
        
        //the alternate row foreground colors list is not populated because
        //by default, the foreground colors are not alternated

    }//end of hsTable::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // hsTable::getValueAt
    //
    // Return the value of the specified cell.
    //
    
    @Override
    public Object getValueAt(int pRow, int pCol)
    {
        
        return model.getValueAt(pRow, pCol);
        
    }//end of hsTable::getValueAt
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // hsTable::prepareRenderer
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
        
        setBackgroundColorOfRow(comp, pRow);
        setForegroundColorOfRow(comp, pRow);

        return comp;
            
    }//end of hsTable::prepareRenderer
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // hsTable::setValueAt
    //
    // Sets the value of the specified cell to the passed in object.
    //

    @Override
    public void setValueAt(Object pVal, int pRow, int pCol)
    {
        
        model.setValueAt(pVal, pRow, pCol);

    }//end of hsTable::setValueAt
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // hsTable::addRow
    //
    // Adds a row to the table.
    //

    public void addRow(List<Object> pVals)
    {
        
        model.addRow(pVals);

    }//end of hsTable::addRow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // hsTable::addColumn
    //
    // Adds a column to the table, with the passed in name.
    //

    public void addColumn(String pCol)
    {
        
        model.addColumn(pCol);

    }//end of hsTable::addColumn
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // hsTable::changeAlternateRowBackgroundColors
    //
    // Changes the list of colors used to alternate the row background colors
    // to the passed in list.
    //

    public void changeAlternateRowBackgroundColors(List<Color> pColors)
    {
        
        alternateRowBackgroundColors = pColors;

    }//end of hsTable::changeAlternateRowBackgroundColors
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // hsTable::changeAlternateRowForegroundColors
    //
    // Changes the list of colors used to alternate the row foreground colors
    // to the passed in list.
    //

    public void changeAlternateRowForegroundColors(List<Color> pColors)
    {
        
        alternateRowForegroundColors = pColors;

    }//end of hsTable::changeAlternateRowForegroundColors
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // hsTable::changeValuesOfRow
    //
    // Changes the values of the specified row, using the passed in values.
    //

    public void changeValuesOfRow(int pRow, List<Object> pVals) 
    {
        
        model.changeValuesOfRow(pRow, pVals);

    }//end of hsTable::changeValuesOfRow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // hsTable::getValuesOfRow
    //
    // Return the values of the specified row.
    //
    
    public List<Object> getValuesOfRow(int pRow)
    {
        
        return model.getValuesOfRow(pRow);
        
    }//end of hsTable::getValuesOfRow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // hsTable::setAlternateRowBackgroundColors
    //
    // Passing in true tells the program to alternate the background colors
    // of the rows; passing in false tells it not to.
    //
    // Default is true.
    //

    public void setAlternateRowBackgroundColors(boolean pAlternate)
    {
        
        alternateRowBackgrounds = pAlternate;

    }//end of hsTable::setAlternateRowBackgroundColors
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // hsTable::setAlternateRowForegroundColors
    //
    // Passing in true tells the program to alternate the foregroun colors
    // of the rows; passing in false tells it not to.
    //
    // Default is false.
    //

    public void setAlternateRowForegroundColors(boolean pAlternate)
    {
        
        alternateRowForegrounds = pAlternate;

    }//end of hsTable::setAlternateRowForegroundColors
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // hsTable::setBackgroundColorOfRow
    //
    // Determines and sets the background color of the passed in row/JComponent.
    //

    private void setBackgroundColorOfRow(JComponent comp, int pRow)
    {
        
        //set the background color to the row background color
        //if the background colors should not be alternated
        if (!alternateRowBackgrounds) { 
            comp.setBackground(rowBackgroundColor);
        }
        //determine and set the background color of the row 
        //based on the row background number
        else {
            int size = alternateRowBackgroundColors.size();
            if (size == 0) { return; }
            
            Color c;
            if (pRow < size) { c = alternateRowBackgroundColors.get(pRow); }
            else { 
                
                int multiple = 0;
                while (size*(multiple+1) <= pRow) { ++multiple; }
                
                int num = pRow - size*multiple;
                c = alternateRowBackgroundColors.get(num);
                
            }

            comp.setBackground(c);
        }

    }//end of hsTable::setBackgroundColorOfRow
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // hsTable::setForegroundColorOfRow
    //
    // Determines and sets the foreground color of the passed in row/JComponent.
    //

    private void setForegroundColorOfRow(JComponent comp, int pRow)
    {
        
        //set the foreground color to the row foreground color
        //if the foreground colors should not be alternated
        if (!alternateRowForegrounds) { 
            comp.setForeground(rowForegroundColor);
        }
        //determine and set the background color of the row 
        //based on the row background number
        else {
            int size = alternateRowForegroundColors.size();
            if (size == 0) { return; }
            
            Color c;
            if (pRow < size) { c = alternateRowForegroundColors.get(pRow); }
            else { 
                
                int multiple = 0;
                while (size*(multiple+1) <= pRow) { ++multiple; }
                
                int num = pRow - size*multiple;
                c = alternateRowForegroundColors.get(num);
                
            }

            comp.setBackground(c);
        }

    }//end of hsTable::setForegroundColorOfRow
    //--------------------------------------------------------------------------

}//end of class hsTable
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------