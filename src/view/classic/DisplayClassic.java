/*******************************************************************************
* Title: DisplayClassic.java
* Author: Hunter Schoonover
* Date: 12/11/15
*
* Purpose:
*
* This class is the Classic display version. It is the GUI that will be used for
* the original version of the program.
*
*/

//------------------------------------------------------------------------------

package view.classic;

//------------------------------------------------------------------------------

import command.Command;
import command.CommandHandler;
import command.CommandListener;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class DisplayClassic
//

public class DisplayClassic extends JFrame implements CommandListener
{
    
    private final JPanel mainPanel;

    //--------------------------------------------------------------------------
    // DisplayClassic::DisplayClassic (constructor)
    //

    public DisplayClassic()
    {
        
        mainPanel = new JPanel();

    }//end of DisplayClassic::DisplayClassic (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // DisplayClassic::init
    //
    // Initializes the object. Must be called immediately after instantiation.
    //

    public void init()
    {
        
        //register this as a view listener
        CommandHandler.registerViewListener(this);
        
        //set up the frame
        setUpFrame();
        
        //create and add the GUI
        createGui();
        
        //arrange all the GUI items
        pack();

        //display the frame
        setVisible(true);

    }// end of DisplayClassic::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DisplayClassic::commandPerformed
    //
    // Performs different actions depending on pCommand.
    //
    // The function will do nothing if pCommand was not intended for view.
    //
    // Called by the CommandHandler everytime a view command is performed.
    //

    @Override
    public void commandPerformed(String pCommand)
    {
        
        //return if this is not a view command
        if(!Command.isViewCommand(pCommand)) { return; }
        
        Map<String, String> command = Command.extractKeyValuePairs(pCommand);

    }//end of DisplayClassic::commandPerformed
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DisplayClassic::createGui
    //
    // Creates and adds the GUI to the window.
    //

    private void createGui()
    {
        
        //add padding to the main panel
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    }// end of DisplayClassic::createGui
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // DisplayClassic::setUpFrame
    //
    // Sets up the frame by setting various options and styles.
    //

    private void setUpFrame()
    {

        //set the title of the frame
        setTitle("Altus Inventory");

        //turn off default bold for Metal look and feel
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        //force "look and feel" to Java style
        try {
            UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println("Could not set Look and Feel");
        }

        //exit the program when the window closes
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //add a JPanel to the frame to provide a familiar container
        setContentPane(mainPanel);

        //maximize the frame
        setExtendedState(getExtendedState() | MAXIMIZED_BOTH);

    }// end of DisplayClassic::setUpFrame
    //--------------------------------------------------------------------------

}//end of class DisplayClassic
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
