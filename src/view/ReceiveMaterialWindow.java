/*******************************************************************************
* Title: ReceiveMaterialWindow.java
* Author: Hunter Schoonover
* Date: 07/25/15
*
* Purpose:
*
* This class is the Receive Material window.
* 
* It presents the user with input fields about the a new material that they
* want to receive.
* 
* Currently, it has input fields for:
*       Id, Customer, Date, Truck Company, Truck Number, Truck Driver, Quantity,
*       Length, Rack, Range, Grade, Diameter, Wall, Facility
*
*/

//------------------------------------------------------------------------------

package view;

//------------------------------------------------------------------------------

import javax.swing.BoxLayout;
import javax.swing.JPanel;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class ReceiveMaterialWindow
//

public class ReceiveMaterialWindow extends AltusJDialog
{
    
    static private final String actionId = "ReceiveMaterialWindow";
    static public String getActionId() { return actionId; }

    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::ReceiveMaterialWindow (constructor)
    //

    public ReceiveMaterialWindow(MainFrame pMainFrame, MainView pMainView)
    {

        super("Receive Material", pMainFrame, pMainView);
        
    }// end of ReceiveMaterialWindow::ReceiveMaterialWindow (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // ReceiveMaterialWindow::createGui
    //
    // Creates and adds the GUI to the mainPanel.
    //
    
    @Override
    protected void createGui() 
    {
        
        setMainPanelLayout(BoxLayout.Y_AXIS);
        
        int w = 130;
        
        //add the Id, Customer, and Date row
        addToMainPanel(createRow(new JPanel[] {
            createInputPanel("Id", "", 
                                "Give the material a reference ID.", w),
            createInputPanel("Owner", "", 
                                "What customer owns the material?", w),
            createInputPanel("Date", "", 
                                "What date was the material received?", w)               
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Truck Company, Truck Number, and Truck Driver row
        addToMainPanel(createRow(new JPanel[] {
            createInputPanel("Truck Company", "", 
                                "What truck company brought the material to "
                                        + "the yard?", w),
            createInputPanel("Truck Number", "", 
                                "What is the number of the truck that brought "
                                        + "the material to the yard?", w),
            createInputPanel("Truck Driver", "", 
                                "Who was the driver of the truck that brought"
                                        + " the material to the yard?", w)               
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Quanity, Length, and Rack row
        addToMainPanel(createRow(new JPanel[] {
            createInputPanel("Quantity", "", "How many pieces of pipe?", w),
            createInputPanel("Length", "", "How much pipe was received?", w),
            createInputPanel("Rack", "", 
                                "What rack is the material stored on?", w)               
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Range, Grade, and Diameter row
        addToMainPanel(createRow(new JPanel[] {
            createInputPanel("Range", "", "What is the range of the pipe?", w),
            createInputPanel("Grade", "", "What is the grade of the pipe?", w),
            createInputPanel("Diameter", "", 
                                "What is the outside diameter of the pipe?", w)               
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Wall and Facility row
        addToMainPanel(createRow(new JPanel[] {
            createInputPanel("Wall", "", 
                                "What is the wall thickness of the pipe?", w),
            createInputPanel("Facility", "", 
                                "What facility is the pipe for?", w)            
        }));
        
        //spacer between rows
        addToMainPanel(createRowSpacer());
        
        //add the Cancel/Confirm panel
        addToMainPanel(createCancelConfirmPanel(
                                        "Receive", 
                                        "Receive the material into the yard."));
        
    }// end of ReceiveMaterialWindow::createGui
    //--------------------------------------------------------------------------

}// end of class ReceiveMaterialWindow
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
